package com.jk.faces.tags;

import com.jk.faces.util.JKJsfUtil;
import com.jk.util.JKIOUtil;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;
import com.sun.faces.facelets.el.VariableMapperWrapper;
import com.sun.faces.taglib.html_basic.PanelGroupTag;
import com.sun.faces.util.FacesLogger;

import javax.el.VariableMapper;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.facelets.TagException;
import javax.faces.view.facelets.TagHandler;

import org.primefaces.component.panel.Panel;

public final class JKTagIncludeHandler extends TagHandler {

	private final JKLogger logger = JKLoggerFactory.getLogger(getClass());

	private final TagAttribute value;

	/**
	 * @param config
	 */
	public JKTagIncludeHandler(TagConfig config) {
		super(config);
		TagAttribute attr = null;
		attr = this.getAttribute("value");
		if (null == attr) {
			throw new TagException(this.tag, "Attribute 'value'");
		}
		this.value = attr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sun.facelets.FaceletHandler#apply(com.sun.facelets.FaceletContext,
	 * javax.faces.component.UIComponent)
	 */
	public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
		VariableMapper orig = ctx.getVariableMapper();
		ctx.setVariableMapper(new VariableMapperWrapper(orig));

		logger.trace("JK include tag ...");
		String value = this.value.getValue(ctx);// (String) JKJsfUtil.evaluateExpressionToObject(this.value.getValue(ctx));
		if (!value.equals("")) {
			File file = JKIOUtil.writeDataToTempFile(value, "xhtml");
			logger.trace("Including value file ({}))", file.getAbsolutePath());
			ctx.setVariableMapper(new VariableMapperWrapper(orig));
			try {
				this.nextHandler.apply(ctx, null);
				ctx.includeFacelet(parent, file.toURI().toURL());
			} catch (Exception e) {
				logger.error("Failed to include file ({})", JKIOUtil.readFile(file));
				throw new TagAttributeException(this.tag, this.value, e);
			} finally {
				ctx.setVariableMapper(orig);
				file.deleteOnExit();
			}
		}
	}
}
