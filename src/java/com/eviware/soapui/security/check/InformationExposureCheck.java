/*
 *  soapUI, copyright (C) 2004-2011 eviware.com 
 *
 *  soapUI is free software; you can redistribute it and/or modify it under the 
 *  terms of version 2.1 of the GNU Lesser General Public License as published by 
 *  the Free Software Foundation.
 *
 *  soapUI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 *  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 *  See the GNU Lesser General Public License for more details at gnu.org.
 */

package com.eviware.soapui.security.check;

import java.util.List;

import com.eviware.soapui.config.SecurityCheckConfig;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.HttpResponseMessageExchange;
import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestInterface;
import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestStepInterface;
import com.eviware.soapui.impl.wsdl.teststeps.RestTestRequestStep;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.security.SecurityCheckRequestResult;
import com.eviware.soapui.security.SecurityTestRunContext;
import com.eviware.soapui.security.SecurityCheckRequestResult.SecurityCheckStatus;
import com.eviware.soapui.security.log.JSecurityTestRunLog;
import com.eviware.soapui.security.log.SecurityTestLogMessageEntry;
import com.eviware.soapui.security.log.SecurityTestLogModel;
import com.eviware.soapui.security.monitor.HttpSecurityAnalyser;
import com.eviware.soapui.security.ui.SecurityCheckConfigPanel;
import com.eviware.soapui.support.SecurityCheckUtil;
import com.eviware.soapui.support.types.StringToObjectMap;

public class InformationExposureCheck extends AbstractSecurityCheck implements HttpSecurityAnalyser
{

	private List<String> exposureList;
	public static final String TYPE = "InformationExposureCheck";
	public static final String LABEL = "Information Exposure";
	private boolean next = true;

	public InformationExposureCheck( TestStep testStep, SecurityCheckConfig config, ModelItem parent, String icon )
	{
		super( testStep, config, parent, icon );
		if( config == null )
		{
			config = SecurityCheckConfig.Factory.newInstance();
		}
		exposureList = SecurityCheckUtil.entriesList();
	}

	@Override
	protected void executeNew( TestStep testStep, SecurityTestRunContext context )
	{
		if( acceptsTestStep( testStep ) )
		{
			WsdlTestCaseRunner testCaseRunner = new WsdlTestCaseRunner( ( WsdlTestCase )testStep.getTestCase(),
					new StringToObjectMap() );

			testStep.run( testCaseRunner, testCaseRunner.getRunContext() );
			next = false;
		}
	}

	@Override
	protected void analyzeNew( TestStep testStep, SecurityTestRunContext context )
	{
		if( acceptsTestStep( testStep ) )
		{
			HttpTestRequestStepInterface testStepwithProperties = ( HttpTestRequestStepInterface )testStep;
			HttpTestRequestInterface<?> request = testStepwithProperties.getTestRequest();
			MessageExchange messageExchange = new HttpResponseMessageExchange( request );

			securityCheckReqResult.setMessageExchange( messageExchange );
			for( String exposureContent : exposureList )
			{
				if( SecurityCheckUtil.contains( context, new String( messageExchange.getRawResponseData() ),
						exposureContent, false ) )
				{
					// logSecurityInfo( messageExchange, securityTestLog,
					// exposureContent );
					String message = " sensitive information '" + exposureContent + "' is detected in response.";
					securityCheckReqResult.addMessage( message );
					securityCheckReqResult.setStatus( SecurityCheckStatus.FAILED );
				}
			}
			// if( getStatus() != Status.FAILED )
			// {
			// setStatus( Status.FINISHED );
			// }
		}
	}

	protected boolean hasNext()
	{
		return next;
	}
	// private AssertionStatus assertContains( SecurityTestRunContext context,
	// HttpTestRequestStepInterface testStep,
	// MessageExchange messageExchange, String exposureContent )
	// {
	// TestAssertionConfig assertionConfig =
	// TestAssertionConfig.Factory.newInstance();
	// assertionConfig.setType( SimpleContainsAssertion.ID );
	//
	// SimpleContainsAssertion containsAssertion = ( SimpleContainsAssertion
	// )TestAssertionRegistry.getInstance()
	// .buildAssertion( assertionConfig, testStep );
	// containsAssertion.setToken( exposureContent );
	// containsAssertion.assertResponse( messageExchange, context );
	// return containsAssertion.getStatus();
	// }

	@Override
	public boolean acceptsTestStep( TestStep testStep )
	{
		return testStep instanceof HttpTestRequestStep || testStep instanceof RestTestRequestStep;
	}

	@Override
	public SecurityCheckConfigPanel getComponent()
	{

		return null;
	}

	@Override
	public String getType()
	{
		return TYPE;
	}

	@Override
	public void analyzeHttpConnection( MessageExchange messageExchange, JSecurityTestRunLog securityTestLog )
	{
		String responseContent = messageExchange.getResponseContent();
		for( String exposureContent : exposureList )
		{
			if( responseContent.contains( exposureContent ) )
			{
				logSecurityInfo( messageExchange, securityTestLog, exposureContent );
			}
		}

	}

	private void logSecurityInfo( MessageExchange messageExchange, JSecurityTestRunLog securityTestLog,
			String exposureContent )
	{
		// TODO refactor through SecurityCheckResult
//		securityTestLog.addEntry( new SecurityTestLogMessageEntry( "In Test Step = '"
//				+ messageExchange.getModelItem().getName() + "' sensitive information '" + exposureContent
//				+ "' is detected in response. ", messageExchange ) );
	}

	private void logSecurityInfo( MessageExchange messageExchange, SecurityTestLogModel securityTestLog,
			String exposureContent )
	{
		// TODO refactor through SecurityCheckResult
//		securityTestLog.addEntry( new SecurityTestLogMessageEntry( "In Test Step = '"
//				+ messageExchange.getModelItem().getName() + "' sensitive information '" + exposureContent
//				+ "' is detected in response. ", messageExchange ) );
	}

	@Override
	public boolean canRun()
	{
		return true;
	}

	@Override
	public boolean configure()
	{
		return false;
	}

	// TODO next two to be deleted after refactoring is done
	@Override
	public SecurityCheckRequestResult analyze( TestStep testStep, SecurityTestRunContext context,
			SecurityTestLogModel securityTestLog, SecurityCheckRequestResult securityCheckResult )
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected SecurityCheckRequestResult execute( TestStep testStep, SecurityTestRunContext context,
			SecurityTestLogModel securityTestLog, SecurityCheckRequestResult securityChekResult )
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void buildDialog()
	{
		super.buildDialogOld();		
	}

}
