/*
 * Copyright 2002-2016 Jalal Kiswani.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jk.faces.components;

import javax.el.ValueExpression;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;

import org.primefaces.behavior.ajax.AjaxBehavior;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.column.Column;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;
import org.primefaces.component.tree.Tree;
import org.primefaces.extensions.component.timepicker.TimePicker;

import com.jk.faces.components.layouts.UIActions;
import com.jk.faces.components.layouts.UIFields;
import com.jk.faces.components.layouts.UIFormLayout;
import com.jk.faces.util.JKJsfUtil;
import com.jk.util.JKObjectUtil;
import com.jk.util.annotations.Author;
import com.sun.faces.RIConstants;

/**
 * A factory for creating JSFComponent objects.
 */
@Author(name = "Jalal H. Kiswani", date = "1/10/2014", version = "1.0")
public class JSFComponentFactory {

	/** The Constant HtmlOutputLabel. */
	private static final String HtmlOutputLabel = null;

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @return the UI actions
	 */
	public static UIActions createActionsRegion() {
		return (UIActions) JSFComponentFactory.createComponent(UIActions.COMPONENT_TYPE);
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @return the ajax behavior
	 */
	public static org.primefaces.behavior.ajax.AjaxBehavior createAjax() {
		final Behavior behavior = FacesContext.getCurrentInstance().getApplication().createBehavior(AjaxBehavior.BEHAVIOR_ID);
		return (AjaxBehavior) behavior;
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @return the UI column
	 */
	public static Column createColumn() {
		return (Column) JSFComponentFactory.createComponent(Column.COMPONENT_TYPE);
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @param value
	 *            the value
	 * @return the UI command
	 */
	public static UICommand createCommandButton(final String value) {
		final UICommand cmd = (UICommand) JSFComponentFactory.createComponent(CommandButton.COMPONENT_TYPE);
		cmd.setValueExpression(TagAttributeConstants.VALUE, JSFComponentFactory.createLabelValueExpression(value));
		return cmd;
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @param componentType
	 *            the component type
	 * @return the UI component
	 */
	public static UIComponent createComponent(final String componentType) {
		final UIComponent component = FacesContext.getCurrentInstance().getApplication().createComponent(componentType);
		// Dont ask about the # 100 , i would say its something related to the
		// algorithm of JSF view Handler during the tree build process , but the
		// fact is : its just random number :)
		// @Update @Jalal: i found it , ComponentSupport.addComponent , check it
		// out ;)
		component.getAttributes().put(RIConstants.DYNAMIC_COMPONENT, Integer.MAX_VALUE);
		return component;
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @return the UI data
	 */
	public static UIData createDataTable() {
		return (UIData) JSFComponentFactory.createComponent(org.primefaces.component.datatable.DataTable.COMPONENT_TYPE);
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @return the UI fields
	 */
	public static UIFields createFieldsRegion() {
		return (UIFields) JSFComponentFactory.createComponent(UIFields.COMPONENT_TYPE);
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @return the UI component
	 */
	public static UIComponent createForm() {
		return JSFComponentFactory.createComponent(UIForm.COMPONENT_TYPE);
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @return the UI form layout
	 */
	public static UIFormLayout createFormLayout() {
		return (UIFormLayout) JSFComponentFactory.createComponent(UIFormLayout.JK_LAYOUT_FORM_LAYOUT);
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @param label
	 *            the label
	 * @param type
	 *            the type
	 * @return the UI input
	 */
	public static UIInput createInput(final String label, final Class type) {
		UIInput input = null;
		if (JKObjectUtil.isTime(type)) {
			input = (javax.faces.component.UIInput) JSFComponentFactory.createComponent(TimePicker.COMPONENT_TYPE);
		} else if (JKObjectUtil.isTimeStamp(type)) {
			input = (javax.faces.component.UIInput) JSFComponentFactory.createComponent(Calendar.COMPONENT_TYPE);
		} else if (JKObjectUtil.isDate(type)) {
			input = (javax.faces.component.UIInput) JSFComponentFactory.createComponent(Calendar.COMPONENT_TYPE);
		} else if (JKObjectUtil.isBoolean(type)) {
			input = (javax.faces.component.UIInput) JSFComponentFactory.createComponent(SelectBooleanCheckbox.COMPONENT_TYPE);
		} else {
			input = JSFComponentFactory.createInputText(null);
		}
		input.getAttributes().put("label", label);
		return input;
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @param label
	 *            the label
	 * @return the UI input
	 */
	public static UIInput createInputText(final String label) {
		final UIInput comp = (javax.faces.component.UIInput) JSFComponentFactory.createComponent(InputText.COMPONENT_TYPE);
		if (label != null) {
			comp.getAttributes().put("label", label);
		}
		return comp;
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @param labelId
	 *            the label id
	 * @return the html output label
	 */
	public static HtmlOutputLabel createLabel(final String labelId) {
		final HtmlOutputLabel label = (HtmlOutputLabel) JSFComponentFactory.createComponent(OutputLabel.COMPONENT_TYPE);
		label.setValueExpression(TagAttributeConstants.VALUE, JSFComponentFactory.createLabelValueExpression(labelId));
		return label;
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @param key
	 *            the key
	 * @return the value expression
	 */
	public static ValueExpression createLabelValueExpression(String key) {
		key = "#{msg.get('".concat(key).concat("')}");
		final ValueExpression exp = JKJsfUtil.createValueException(key, String.class);
		return exp;
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @return the UI output
	 */
	public static UIOutput createOutputText() {
		return JSFComponentFactory.createOutputText(null);
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @param value
	 *            the value
	 * @return the UI output
	 */
	public static UIOutput createOutputText(final String value) {
		final UIOutput out = (UIOutput) JSFComponentFactory.createComponent(UIOutput.COMPONENT_TYPE);
		if (value != null) {
			out.setValueExpression("value", JSFComponentFactory.createLabelValueExpression(value));
		}
		return out;
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @param el
	 *            the el
	 * @param b
	 *            the b
	 * @return the UI output
	 */
	public static UIOutput createOutputText(final String el, final boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @return the UI input
	 */
	public static UIInput createTextArea() {
		return (UIInput) JSFComponentFactory.createComponent(HtmlInputTextarea.COMPONENT_TYPE);
	}

	/**
	 * Creates a new JSFComponent object.
	 *
	 * @return the UI component
	 */
	public static UIComponent createTreeComponent() {
		return JSFComponentFactory.createComponent(Tree.COMPONENT_TYPE);
	}
}
