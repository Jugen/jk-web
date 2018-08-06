/*
 * Copyright 2002-2018 Jalal Kiswani. 
 * E-mail: Kiswani.Jalal@Gmail.com
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
package com.jk.faces.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.facelets.FaceletContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;

import com.jk.faces.components.TagAttributeConstants;
import com.jk.util.JKConversionUtil;
import com.jk.util.annotations.Author;
import com.jk.util.exceptions.handler.JKExceptionUtil;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * <B>JSFUtil</B> is class that contains JSF helpful methods, that helps to
 * search, edit or manipulate JSF contents.
 *
 * @author Jalal H. Kiswani
 * @version 1.0
 */
@Author(name = "Jalal Kiswani", date = "3/9/2014", version = "1.0")
public class JKJsfUtil {

	/** The Constant CHECKSUM_POSTFIX. */
	private static final String CHECKSUM_POSTFIX = "-checksum";
	/** The logger. */
	private static JKLogger logger = JKLoggerFactory.getLogger(JKJsfUtil.class);

	/**
	 * add String <code>contents</code> in HTML row.
	 *
	 * @param contents
	 *            the contents
	 * @param colSpan
	 *            the col span
	 * @param style
	 *            the style
	 * @throws IOException
	 *             if an input/output error occurs during response writing
	 */
	public static void addFullRow(final String contents, final int colSpan, final String style) throws IOException {
		if (contents != null) {
			final ResponseWriter writer = JKJsfUtil.context().getResponseWriter();
			writer.startElement("tr", null);
			writer.startElement("td", null);
			writer.writeAttribute("align", "center", null);
			writer.writeAttribute("colspan", colSpan, null);
			writer.writeAttribute("class", style, null);
			writer.writeText(contents, null);
			writer.endElement("td");
			writer.endElement("tr");
		}
	}

	/**
	 * add component <code>comp</code> in HTML row.
	 *
	 * @param comp
	 *            the comp
	 * @param colSpan
	 *            the col span
	 * @param style
	 *            the style
	 * @throws IOException
	 *             if an input/output error occurs during response writing
	 */
	public static void addFullRow(final UIComponent comp, final int colSpan, final String style) throws IOException {
		if (comp != null) {
			final ResponseWriter writer = JKJsfUtil.context().getResponseWriter();
			writer.startElement("tr", null);
			writer.startElement("td", null);
			// TODO : convert the following to use the TagConstants class
			JKJsfUtil.writeAttribue(comp, "align", "center");
			JKJsfUtil.writeAttribue(comp, "colspan", colSpan);
			JKJsfUtil.writeAttribue(comp, "styleClass", "class", style);
			comp.encodeAll(JKJsfUtil.context());
			writer.endElement("td");
			writer.endElement("tr");
		}
	}

	/**
	 * Appends value to an existing attribute in component
	 * <code>component</code>.
	 *
	 * @param component
	 *            the component
	 * @param sourceKey
	 *            the source key
	 * @param targetKey
	 *            the target key
	 * @param valueToAppend
	 *            the value to append
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void appendAttribute(final UIComponent component, final String sourceKey, final String targetKey, final String valueToAppend)
			throws IOException {
		String value = (String) component.getAttributes().get(sourceKey);
		if (value == null) {
			value = valueToAppend;
		} else {
			value = value.concat(" ").concat(valueToAppend);
		}
		JKJsfUtil.context().getResponseWriter().writeAttribute(targetKey, value, null);
	}

	/**
	 * Builds the view.
	 *
	 * @param context
	 *            the context
	 * @param viewId
	 *            the view id
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String buildView(final FacesContext context, final String viewId) throws IOException {
		final UIViewRoot view = JKJsfUtil.createView(viewId);
		view.encodeAll(FacesContext.getCurrentInstance());

		final ResponseWriter originalWriter = context.getResponseWriter();
		final StringWriter writer = new StringWriter();

		try {
			context.setResponseWriter(context.getRenderKit().createResponseWriter(writer, "text/html", "UTF-8"));
			view.encodeAll(context);
		} finally {
			if (originalWriter != null) {
				context.setResponseWriter(originalWriter);
			}
		}
		return writer.toString();
	}

	/**
	 * Builds the view.
	 *
	 * @param viewId
	 *            the view id
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String buildView(final String viewId) throws IOException {
		return JKJsfUtil.buildView(FacesContext.getCurrentInstance(), viewId);
	}

	/**
	 * Calculate checksum.
	 *
	 * @param component
	 *            the component
	 * @return the long
	 */
	public static long calculateChecksum(final UIComponent component) {
		try {
			final Checksum checksumHandler = new CRC32();
			final UIFacesVisitor visitors = JKJsfUtil.visitComponent(component);
			final List<UIInput> inputs = visitors.getInputs();
			for (final UIInput uiInput : inputs) {
				if (uiInput.getValue() == null) {
					checksumHandler.update("null".getBytes(), 0, 0);
				} else {
					final byte[] bytes = uiInput.getValue().toString().getBytes("UTF-8");
					checksumHandler.update(bytes, 0, bytes.length);
				}
			}
			return checksumHandler.getValue();
		} catch (final Exception e) {
			JKExceptionUtil.handle(e);
			// unreachable
			return -1;
		}
	}

	/**
	 * Calculate current view checksum.
	 *
	 * @return the long
	 */
	public static long calculateCurrentViewChecksum() {
		return JKJsfUtil.calculateChecksum(FacesContext.getCurrentInstance().getViewRoot());
	}

	/**
	 * Clear view states.
	 */
	public static void clearViewStates() {
		JKJsfUtil.getViewMap().clear();
	}

	/**
	 * Context.
	 *
	 * @return the faces context
	 */
	private static FacesContext context() {
		return FacesContext.getCurrentInstance();
	}

	/*
	 *
	 */
	/**
	 * Creates the method expression.
	 *
	 * @param expression
	 *            the expression
	 * @param returnType
	 *            the return type
	 * @return the method expression
	 */
	public static MethodExpression createMethodExpression(final String expression, final Class<?> returnType) {
		Assert.assertNotNull(expression);
		// TODO : check the below line????
		JKJsfUtil.logger.debug("createMethodEpression:".concat(expression));
		final FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getExpressionFactory().createMethodExpression(context.getELContext(), expression, returnType, new Class[0]);
	}

	/**
	 * Creates the value exception.
	 *
	 * @param value
	 *            the value
	 * @return the value expression
	 */
	public static ValueExpression createValueException(final String value) {
		return JKJsfUtil.createValueException(value, Object.class);
	}

	/**
	 * Creates the value exception.
	 *
	 * @param value
	 *            the value
	 * @param clas
	 *            the clas
	 * @return the value expression
	 */
	public static ValueExpression createValueException(final String value, final Class<?> clas) {
		final ExpressionFactory expressionFactory = JKJsfUtil.getExpressionFactory();
		if (expressionFactory != null) {
			final ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			final ValueExpression ve1 = expressionFactory.createValueExpression(elContext, value, clas);
			return ve1;
		} else {
			final ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			final ValueExpression ve1 = FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(elContext,
					value, clas);
			return ve1;
		}
	}

	/**
	 * Creates the value exception with value.
	 *
	 * @param originalValue
	 *            the original value
	 * @return the value expression
	 */
	public static ValueExpression createValueExceptionWithValue(final Object originalValue) {
		return FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(originalValue,
				originalValue.getClass());
	}

	/**
	 * Creates the view.
	 *
	 * @param viewId
	 *            the view id
	 * @return the UI view root
	 */
	public static UIViewRoot createView(final String viewId) {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
		final UIViewRoot view = viewHandler.createView(facesContext, viewId);
		try {
			viewHandler.getViewDeclarationLanguage(facesContext, viewId).buildView(facesContext, view);

		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return view;
	}

	/**
	 * Error.
	 *
	 * @param message
	 *            the message
	 */
	public static void error(final String message) {
		final FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * Evaluate expression to object.
	 *
	 * @param el
	 *            the el
	 * @return the object
	 */
	public static Object evaluateExpressionToObject(final String el) {
		if (el == null) {
			return null;
		}
		final Application application = FacesContext.getCurrentInstance().getApplication();
		return application.evaluateExpressionGet(FacesContext.getCurrentInstance(), el, Object.class);
	}

	/**
	 * Evaluate expression to object.
	 *
	 * @param valueExpression
	 *            the value expression
	 * @return the object
	 */
	public static Object evaluateExpressionToObject(final ValueExpression valueExpression) {
		if (valueExpression == null) {
			return null;
		}
		return JKJsfUtil.evaluateExpressionToObject(valueExpression.getExpressionString());
	}

	/**
	 * Attempts to find a value associated with the specified <code>key</code> ,
	 * using the <code> stateHelper </code> if no such value is found it gets
	 * the attribute value, in component <code>component</code> with Key
	 * <code>key</code> if the attribute's value is <code>null</code> it return
	 * <code>null</code>.
	 *
	 * @param component
	 *            the component
	 * @param stateHelper
	 *            the state helper
	 * @param key
	 *            the key
	 * @return {@link Object}
	 */
	public static Object getAttribute(final UIComponent component, final StateHelper stateHelper, final String key) {
		final Object value = stateHelper.eval(key);
		return value == null ? JKJsfUtil.getAttribute(component, key, null) : value;
	}

	/**
	 * gets the attribute value, in component <code>uiComponent</code> with Key
	 * <code>key</code> if the attribute's value is <code>null</code> it return
	 * the value of <code>defaultValue</code>.
	 *
	 * @param component
	 *            the component
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return the attribute
	 */
	public static Object getAttribute(final UIComponent component, final String key, final Object defaultValue) {
		final Object value = component.getAttributes().get(key);
		return value == null ? defaultValue : value;
	}

	/**
	 * gets the attribute <code>Boolean</code> value, in component
	 * <code>uiComponent</code> with Key <code>key</code> </br>
	 * if the attribute's value is <code>null</code> it return the value of
	 * <code>defaultValue</code>.
	 *
	 * @param uiComponent
	 *            the ui component
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return attribute value
	 */
	public static boolean getBooleanAttribute(final UIComponent uiComponent, final String key, final boolean defaultValue) {
		return new Boolean(JKJsfUtil.getAttribute(uiComponent, key, defaultValue).toString());
	}

	/**
	 * Gets the checksum key.
	 *
	 * @param currentView
	 *            the current view
	 * @return the checksum key
	 */
	private static String getChecksumKey(final String currentView) {
		return currentView.concat(JKJsfUtil.CHECKSUM_POSTFIX);
	}

	/**
	 * Gets the component attribute.
	 *
	 * @param comp
	 *            the comp
	 * @param attributeName
	 *            the attribute name
	 * @return the component attribute
	 */
	public static Object getComponentAttribute(final UIComponent comp, final String attributeName) {
		final Map map = JKJsfUtil.getComponentMap(comp);
		return map.get(attributeName);
	}

	/**
	 * Gets the component map.
	 *
	 * @param comp
	 *            the comp
	 * @return the component map
	 */
	private static Map getComponentMap(final UIComponent comp) {
		final Map<String, Map<String, Map>> viewMap = JKJsfUtil.getViewMap();
		Map componentMap = viewMap.get(comp.getClientId());
		if (componentMap == null) {
			componentMap = new HashMap();
			viewMap.put(comp.getClientId(), componentMap);
		}
		return componentMap;
	}

	/**
	 * Gets the current view.
	 *
	 * @return the current view
	 */
	public static String getCurrentView() {
		Object viewName = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(TagAttributeConstants.CURRENT_VIEW);
		if (viewName != null) {
			return viewName.toString();
		}
		viewName = FacesContext.getCurrentInstance().getAttributes().get(TagAttributeConstants.CURRENT_VIEW);
		if (viewName != null) {
			return viewName.toString();
		}
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}

	/**
	 * Gets the current view original checksum.
	 *
	 * @return the current view original checksum
	 */
	public static long getCurrentViewOriginalChecksum() {
		final String currentView = JKJsfUtil.getCurrentView();
		if (currentView == null) {
			throw new IllegalStateException("current view is null");
		}
		final String key = JKJsfUtil.getChecksumKey(currentView);
		final Object object = JKJsfUtil.getSessionMap().get(key);
		if (object == null) {
			throw new IllegalStateException("key : ".concat(key).concat(" not found on sessino ,call saveCurrentViewChecksum before this"));
		}
		return (Long) object;
	}

	/**
	 * Gets the expression factory.
	 *
	 * @return the expression factory
	 */
	public static ExpressionFactory getExpressionFactory() {
		if (JKJsfUtil.getFaceletsContext() != null) {
			return JKJsfUtil.getFaceletsContext().getExpressionFactory();
		} else {
			return null;
		}

	}

	/**
	 * Gets the facelets context.
	 *
	 * @return the facelets context
	 */
	public static FaceletContext getFaceletsContext() {
		return (FaceletContext) FacesContext.getCurrentInstance().getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
	}

	/**
	 * gets the attribute <code>int</code> value, in component
	 * <code>uiComponent</code> with Key <code>key</code> </br>
	 * if the attribute's value is <code>null</code> it return the value of
	 * <code>defaultValue</code>.
	 *
	 * @param component
	 *            the component
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return attribute value
	 */
	public static int getIntegerAttribute(final UIComponent component, final String key, final Object defaultValue) {
		return new Integer(JKJsfUtil.getAttribute(component, key, defaultValue).toString());
	}

	/**
	 * gets JSF information like JSF version and Faces context.
	 *
	 * @return the JSF info
	 */
	public static Map<String, Object> getJSFInfo() {
		final LinkedHashMap<String, Object> details = new LinkedHashMap<String, Object>();
		final FacesContext context = FacesContext.getCurrentInstance();
		final Application application = context.getApplication();
		final ViewHandler viewHandler = application.getViewHandler();
		final ViewDeclarationLanguage vdl = viewHandler.getViewDeclarationLanguage(context, context.getViewRoot().getViewId());
		final LifecycleFactory LifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);

		details.put("JSF-Version", FacesContext.class.getPackage().getImplementationVersion());
		details.put("JSF-Version-Package", FacesContext.class.getPackage().getName());
		details.put("FacesContext", context.getClass());
		details.put("Application", application.getClass());
		details.put("ViewHandler", viewHandler.getClass());
		details.put("ViewDeclarationLanguage", vdl.getClass());
		details.put("LifecycleFactory", LifecycleFactory.getClass());

		return details;
	}

	/**
	 * Gets the request attribute as boolean.
	 *
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @return the request attribute as boolean
	 */
	public static boolean getRequestAttributeAsBoolean(final String key, final Object defaultValue) {
		final Object value = JKJsfUtil.getRequestMap().get(key);
		return JKConversionUtil.toBoolean(value == null ? defaultValue : value);
	}

	/**
	 * Gets the request map.
	 *
	 * @return the request map
	 */
	public static Map<String, Object> getRequestMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
	}

	/**
	 * Gets the session map.
	 *
	 * @return the session map
	 */
	public static Map<String, Object> getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}

	/**
	 * Gets the view map.
	 *
	 * @return the view map
	 */
	private static Map<String, Map<String, Map>> getViewMap() {
		final String viewName = JKJsfUtil.getCurrentView();
		Map<String, Map<String, Map>> viewMap = (Map) JKJsfUtil.getSessionMap().get(viewName);
		if (viewMap == null) {
			viewMap = new HashMap<>();
			JKJsfUtil.getSessionMap().put(viewName, viewMap);
		}
		return viewMap;
	}

	/**
	 * Checks if is JS f22.
	 *
	 * @return true, if is JS f22
	 */
	public static boolean isJSF22() {
		final String version = FacesContext.class.getPackage().getImplementationVersion();

		if (version != null) {
			return version.startsWith("2.2");
		} else {
			// fallback
			try {
				Class.forName("javax.faces.flow.Flow");
				return true;
			} catch (final ClassNotFoundException ex) {
				return false;
			}
		}
	}

	/**
	 * Save current view checksum.
	 */
	public static void saveCurrentViewChecksum() {
		final String currentView = JKJsfUtil.getCurrentView();
		if (currentView == null) {
			throw new IllegalStateException("current view is null");
		}
		final long checksum = JKJsfUtil.calculateCurrentViewChecksum();
		JKJsfUtil.getSessionMap().put(JKJsfUtil.getChecksumKey(currentView), checksum);
	}

	/**
	 * Sets the component attribute.
	 *
	 * @param comp
	 *            the comp
	 * @param attributeName
	 *            the attribute name
	 * @param atributeValue
	 *            the atribute value
	 */
	public static void setComponentAttribute(final UIComponent comp, final String attributeName, final Object atributeValue) {
		final Map componentMap = JKJsfUtil.getComponentMap(comp);
		componentMap.put(attributeName, atributeValue);
		System.err.println("Set Compnent Attribute : " + attributeName + " : " + atributeValue);
	}

	/**
	 * Sets the request attribute.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public static void setRequestAttribute(final String key, final Object value) {
		JKJsfUtil.getRequestMap().put(key, value);
	}

	/**
	 * Sets the session attribute.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public static void setSessionAttribute(final String key, final Object value) {
		JKJsfUtil.getSessionMap().put(key, value);
	}

	/**
	 * Success.
	 *
	 * @param message
	 *            the message
	 */
	public static void success(final String message) {

		final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", message);

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * Visit view.
	 *
	 * @param component
	 *            the component
	 * @return the UI view visitor
	 */
	public static UIFacesVisitor visitComponent(final UIComponent component) {
		final UIFacesVisitor visitor = new UIFacesVisitor();
		component.visitTree(VisitContext.createVisitContext(FacesContext.getCurrentInstance()), visitor);
		return visitor;
	}

	/**
	 * Visit current view.
	 *
	 * @return the UI faces visitor
	 */
	public static UIFacesVisitor visitCurrentView() {
		return JKJsfUtil.visitComponent(FacesContext.getCurrentInstance().getViewRoot());
	}

	/**
	 * Visit view.
	 *
	 * @param viewId
	 *            the view id
	 * @return the UI view visitor
	 */
	public static UIFacesVisitor visitView(final String viewId) {
		final UIViewRoot view = JKJsfUtil.createView(viewId);
		final UIFacesVisitor visitor = new UIFacesVisitor();
		view.visitTree(VisitContext.createVisitContext(FacesContext.getCurrentInstance()), visitor);
		return visitor;
	}

	/**
	 * Warning.
	 *
	 * @param message
	 *            the message
	 */
	public static void warning(final String message) {
		final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", message);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * writes an attribute to component <code>component</code> with key
	 * <code>key</code> and default value <code>defaultValue</code>.
	 *
	 * @param component
	 *            the component
	 * @param key
	 *            the key
	 * @param defaultValue
	 *            the default value
	 * @throws IOException
	 *             if an input/output error occurs during response writing
	 */
	public static void writeAttribue(final UIComponent component, final String key, final Object defaultValue) throws IOException {
		JKJsfUtil.writeAttribue(component, key, null, defaultValue);
	}

	/**
	 * reads the value of with Key <code>sourceKey</code> in component
	 * <code>component</code>, then gets the response writer and add attribute
	 * with with Key <code>targetKey</code> and value equal to the retrieved
	 * previous value in case the retrieved value equal null then the value of
	 * <code>defaultValue</code> will be used instead Also in case
	 * <code>targetKey</code> equal null then <code>sourceKey</code> will be
	 * used instead.
	 *
	 * @param component
	 *            the component
	 * @param sourceKey
	 *            the attribute's key in the UI component
	 * @param targetKey
	 *            the attribute's key that will be used to add the attribute
	 * @param defaultValue
	 *            the default value
	 * @throws IOException
	 *             if an input/output error occurs during response writing
	 */
	public static void writeAttribue(final UIComponent component, final String sourceKey, final String targetKey, final Object defaultValue)
			throws IOException {
		final Object value = JKJsfUtil.getAttribute(component, sourceKey, defaultValue);
		JKJsfUtil.context().getResponseWriter().writeAttribute(targetKey == null ? sourceKey : targetKey, value, null);
	}

	/**
	 * Invalidate session.
	 *
	 * @param keepAttributes
	 *            the keep attributes
	 */
	public static void invalidateSession(String... keepAttributes) {
		if (FacesContext.getCurrentInstance() != null) {
			// original map
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = new HashMap(context.getSessionMap());
			context.invalidateSession();
			HttpSession session = (HttpSession) context.getSession(true);
			// restore original attributes
			for (String attribute : keepAttributes) {
				session.setAttribute(attribute, sessionMap.get(attribute));
			}
		}
	}

	/**
	 * Gets the local name from Q name.
	 *
	 * @param qName
	 *            the q name
	 * @return the local name from Q name
	 */
	public static String getLocalNameFromQName(final String qName) {
		final String[] split = qName.split(":");
		return split.length == 2 ? split[1] : split[0];
	}

	/**
	 * Gets the namespace letter from Q name.
	 *
	 * @param qName
	 *            the q name
	 * @return the namespace letter from Q name
	 */
	public static String getNamespaceLetterFromQName(final String qName) {
		final String[] split = qName.split(":");
		if (split.length == 1) {
			return null;
		}
		return split[0];

	}

	/**
	 * Find component in root.
	 *
	 * @param id
	 *            the id
	 * @return the UI component
	 */
	public static UIComponent findComponentInRoot(String id) {
		UIComponent component = null;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			UIComponent root = facesContext.getViewRoot();
			component = findComponent(root, id);
		}

		return component;
	}

	/**
	 * Find component.
	 *
	 * @param base
	 *            the base
	 * @param id
	 *            the id
	 * @return the UI component
	 */
	public static UIComponent findComponent(UIComponent base, String id) {
		if (id.equals(base.getId()))
			return base;

		UIComponent kid = null;
		UIComponent result = null;
		Iterator kids = base.getFacetsAndChildren();
		while (kids.hasNext() && (result == null)) {
			kid = (UIComponent) kids.next();
			if (id.equals(kid.getId())) {
				result = kid;
				break;
			}
			result = findComponent(kid, id);
			if (result != null) {
				break;
			}
		}
		return result;
	}

	/**
	 * Find components.
	 *
	 * @param parent
	 *            the parent
	 * @param type
	 *            the type
	 * @return the list
	 */
	///////////////////////////////////////////////////////////////////////////
	public static List<UIComponent> findComponents(UIComponent parent, Class type) {
		List<UIComponent> result = new Vector<>();
		List<UIComponent> children = parent.getChildren();
		for (UIComponent uiComponent : children) {
			if (uiComponent.getClass().equals(type)) {
				result.add(uiComponent);
			}
			result.addAll(findComponents(uiComponent, type));
		}
		return result;
	}

	/**
	 * Gets the remote host name.
	 *
	 * @return the remote host name
	 */
	public static String getRemoteHostName() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
			String ip = httpServletRequest.getHeader("X-FORWARDED-FOR");
			if (ip == null) {
				ip = httpServletRequest.getRemoteAddr();
			}
			return ip;
		}
		return null;
	}

	/**
	 * Redirect.
	 *
	 * @param path
	 *            the path
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void redirect(String path) throws IOException {
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		if (currentInstance != null) {
			// ExternalContext externalContext =
			// currentInstance.getExternalContext();
			// HttpServletRequest request = (HttpServletRequest)
			// externalContext.getRequest();
			// HttpServletResponse response = (HttpServletResponse)
			// externalContext.getResponse();
			redirect(path, null, null);
		} else {
			throw new IllegalStateException("your are calling redirect assuming FacesContext is there!!!");
		}
	}

	/**
	 * add full.
	 *
	 * @param path
	 *            the path
	 * @param request
	 *            the request
	 * @param response
	 *            relative to context
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void redirect(String path, HttpServletRequest request, HttpServletResponse response) throws IOException {
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		if (currentInstance != null) {
			request = (HttpServletRequest) currentInstance.getExternalContext().getRequest();
			response = (HttpServletResponse) currentInstance.getExternalContext().getResponse();
		}
		String fullPath = request.getContextPath() + path;
		// if (currentInstance != null) {
		// }
		String facesRequest = request.getHeader("Faces-Request");
		if (facesRequest != null && "partial/ajax".equals(facesRequest)) {
			if (currentInstance != null) {
				currentInstance.getExternalContext().redirect(fullPath);
				currentInstance.responseComplete();
			} else {
				// // It's a JSF ajax request.
				// // the below will useful in case of ajax requests which
				// doesn't
				// // Recognize redirect

				response.setContentType("text/xml");
				response.getWriter().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
						.printf("<partial-response><redirect url=\"%s\"></redirect></partial-response>", fullPath);
			}
		} else {
			// normal full request
			response.sendRedirect(fullPath);
		}
	}

	/**
	 * Force clear cache.
	 *
	 * @param resp
	 *            the resp
	 */
	public static void forceClearCache(ServletResponse resp) {
		HttpServletResponse response = (HttpServletResponse) resp;
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
		// 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
	}

	/**
	 * Gets the servlet context.
	 *
	 * @return the servlet context
	 */
	public static ServletContext getServletContext() {
		return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	}

}
