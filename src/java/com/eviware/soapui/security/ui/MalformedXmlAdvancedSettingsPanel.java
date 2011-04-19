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
package com.eviware.soapui.security.ui;

import javax.swing.JComponent;

import com.eviware.soapui.config.MalformedXmlAttributeConfig;
import com.eviware.soapui.config.MalformedXmlConfig;
import com.eviware.x.form.XFormField;
import com.eviware.x.form.XFormFieldListener;
import com.eviware.x.form.support.ADialogBuilder;
import com.eviware.x.form.support.AField;
import com.eviware.x.form.support.AForm;
import com.eviware.x.form.support.AField.AFieldType;
import com.eviware.x.impl.swing.JFormDialog;

public class MalformedXmlAdvancedSettingsPanel
{

	private JFormDialog dialog;
	private MalformedXmlConfig configuration;
	private MalformedXmlAttributeConfig attributeConfig;

	public MalformedXmlAdvancedSettingsPanel( MalformedXmlConfig malformedXmlConfig )
	{
		this.configuration = malformedXmlConfig;
		this.attributeConfig = malformedXmlConfig.getAttributeMutation();
		dialog = ( JFormDialog )ADialogBuilder.buildDialog( AdvancedSettings.class );
		initDialog();
	}

	private void initDialog()
	{
		dialog.setBooleanValue( AdvancedSettings.INSERT_NEW_ELEMENT, configuration.getInsertNewElement() );
		dialog.setValue( AdvancedSettings.NEW_ELEMENT_VALUE, configuration.getNewElementValue() );
		dialog.setBooleanValue( AdvancedSettings.CHANGE_TAG_NAME, configuration.getChangeTagName() );
		dialog.setBooleanValue( AdvancedSettings.LEAVE_TAG_OPEN, configuration.getLeaveTagOpen() );

		dialog.setBooleanValue( AdvancedSettings.MUTATE_ATTRIBUTES, attributeConfig.getMutateAttributes() );
		dialog.setBooleanValue( AdvancedSettings.INSERT_INVALID_CHARS, attributeConfig.getInsertInvalidChars() );
		dialog.setBooleanValue( AdvancedSettings.LEAVE_ATTRIBUTE_OPEN, attributeConfig.getLeaveAttributeOpen() );
		dialog.setBooleanValue( AdvancedSettings.ADD_NEW_ATTRIBUTE, attributeConfig.getAddNewAttribute() );
		dialog.setValue( AdvancedSettings.NEW_ATTRIBUTE_NAME, attributeConfig.getNewAttributeName() );
		dialog.setValue( AdvancedSettings.NEW_ATTIBUTE_VALUE, attributeConfig.getNewAttributeValue() );

		// listeners...
		dialog.getFormField( AdvancedSettings.INSERT_NEW_ELEMENT ).addFormFieldListener( new XFormFieldListener()
		{
			
			@Override
			public void valueChanged( XFormField sourceField, String newValue, String oldValue )
			{
				configuration.setInsertNewElement( Boolean.parseBoolean( newValue ) );
			}
		});
		dialog.getFormField( AdvancedSettings.NEW_ELEMENT_VALUE ).addFormFieldListener( new XFormFieldListener()
		{
			
			@Override
			public void valueChanged( XFormField sourceField, String newValue, String oldValue )
			{
				configuration.setNewElementValue( newValue );
			}
		});
		dialog.getFormField( AdvancedSettings.CHANGE_TAG_NAME ).addFormFieldListener( new XFormFieldListener()
		{
			
			@Override
			public void valueChanged( XFormField sourceField, String newValue, String oldValue )
			{
				configuration.setChangeTagName( Boolean.parseBoolean( newValue ) );
			}
		});
		dialog.getFormField( AdvancedSettings.LEAVE_TAG_OPEN ).addFormFieldListener( new XFormFieldListener()
		{
			
			@Override
			public void valueChanged( XFormField sourceField, String newValue, String oldValue )
			{
				configuration.setLeaveTagOpen( Boolean.parseBoolean( newValue ) );
			}
		});
		dialog.getFormField( AdvancedSettings.MUTATE_ATTRIBUTES ).addFormFieldListener( new XFormFieldListener()
		{
			
			@Override
			public void valueChanged( XFormField sourceField, String newValue, String oldValue )
			{
				attributeConfig.setMutateAttributes( Boolean.parseBoolean( newValue ) );
			}
		});
		dialog.getFormField( AdvancedSettings.INSERT_INVALID_CHARS ).addFormFieldListener( new XFormFieldListener()
		{
			
			@Override
			public void valueChanged( XFormField sourceField, String newValue, String oldValue )
			{
				attributeConfig.setInsertInvalidChars( Boolean.parseBoolean( newValue ) );
			}
		});
		dialog.getFormField( AdvancedSettings.LEAVE_ATTRIBUTE_OPEN ).addFormFieldListener( new XFormFieldListener()
		{
			
			@Override
			public void valueChanged( XFormField sourceField, String newValue, String oldValue )
			{
				attributeConfig.setLeaveAttributeOpen( Boolean.parseBoolean( newValue ) );
			}
		});
		dialog.getFormField( AdvancedSettings.ADD_NEW_ATTRIBUTE ).addFormFieldListener( new XFormFieldListener()
		{
			
			@Override
			public void valueChanged( XFormField sourceField, String newValue, String oldValue )
			{
				attributeConfig.setAddNewAttribute( Boolean.parseBoolean( newValue ) );
			}
		});
		dialog.getFormField( AdvancedSettings.NEW_ATTRIBUTE_NAME ).addFormFieldListener( new XFormFieldListener()
		{
			
			@Override
			public void valueChanged( XFormField sourceField, String newValue, String oldValue )
			{
				attributeConfig.setNewAttributeName( newValue );
			}
		});
		dialog.getFormField( AdvancedSettings.NEW_ATTIBUTE_VALUE ).addFormFieldListener( new XFormFieldListener()
		{
			
			@Override
			public void valueChanged( XFormField sourceField, String newValue, String oldValue )
			{
				attributeConfig.setNewAttributeValue( newValue );
			}
		});
		
	}

	public JComponent getPanel()
	{
		return dialog.getPanel();
	}

	@AForm( description = "Malformed Xml Configuration", name = "Malformed Xml Configuration" )
	protected interface AdvancedSettings
	{

		@AField( description = "Insert new element", name = "Insert new element", type = AFieldType.BOOLEAN )
		public final static String INSERT_NEW_ELEMENT = "Insert new element";

		@AField( description = "New element value", name = "New element value", type = AFieldType.STRING )
		public final static String NEW_ELEMENT_VALUE = "New element value";

		@AField( description = "Change Tag Name", name = "Change tag name", type = AFieldType.BOOLEAN )
		public final static String CHANGE_TAG_NAME = "Change tag name";

		@AField( description = "Leave tag open", name = "Leave tag open", type = AFieldType.BOOLEAN )
		public final static String LEAVE_TAG_OPEN = "Leave tag open";

		@AField( description = "Mutate attributes", name = "Mutate attributes", type = AFieldType.BOOLEAN )
		public final static String MUTATE_ATTRIBUTES = "Mutate attributes";

		@AField( description = "Insert invalid chars in xml", name = "Insert invalid chars in xml", type = AFieldType.BOOLEAN )
		public final static String INSERT_INVALID_CHARS = "Insert invalid chars in xml";

		@AField( description = "Leave attribute open", name = "Leave attribute open", type = AFieldType.BOOLEAN )
		public final static String LEAVE_ATTRIBUTE_OPEN = "Leave attribute open";

		@AField( description = "Add new attribute", name = "Add new attribute", type = AFieldType.BOOLEAN )
		public final static String ADD_NEW_ATTRIBUTE = "Add new attribute";

		@AField( description = "New attribute name", name = "New attribute name", type = AFieldType.STRING )
		public final static String NEW_ATTRIBUTE_NAME = "New attribute name";

		@AField( description = "New attribute value", name = "New attribute value", type = AFieldType.STRING )
		public final static String NEW_ATTIBUTE_VALUE = "New attribute value";

	}

}