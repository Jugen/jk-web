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

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.event.PhaseId;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;

import com.jk.annotations.Author;
import com.jk.faces.components.layouts.UIFields;
import com.jk.faces.util.JKJsfUtil;
import com.jk.util.StringUtil;
import com.jk.util.StringUtil;

/**
 * The Class UIFormLayout.
 *
 * @author Jalal Kiswani
 */
@Author(name = "Jalal H. Kiswani", date = "1/11/2014", version = "1.0")
public class UIFormLayout extends ComponentHandler {

	/**
	 * Instantiates a new UI form layout.
	 *
	 * @param config
	 *            the config
	 */
	public UIFormLayout(final ComponentConfig config) {
		super(config);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * javax.faces.view.facelets.ComponentHandler#onComponentCreated(javax.faces
	 * .view.facelets.FaceletContext, javax.faces.component.UIComponent,
	 * javax.faces.component.UIComponent)
	 */
	@Override
	public void onComponentCreated(final FaceletContext ctx, final UIComponent c, final UIComponent parent) {
		super.onComponentCreated(ctx, c, parent);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * javax.faces.view.facelets.ComponentHandler#onComponentPopulated(javax.
	 * faces.view.facelets.FaceletContext, javax.faces.component.UIComponent,
	 * javax.faces.component.UIComponent)
	 */
	@Override
	public void onComponentPopulated(final FaceletContext ctx, final UIComponent c, final UIComponent parent1) {
		super.onComponentPopulated(ctx, c, parent1);
		// don't ask me why
		final PhaseId phase = ctx.getFacesContext().getCurrentPhaseId();
		// boolean postback = ctx.getFacesContext().isPostback();
		if (phase == PhaseId.RENDER_RESPONSE) {// || (postback && phase ==
												// PhaseId.RESTORE_VIEW)) {
			// if ((!ctx.getFacesContext().isPostback()) || (!&&
			// ctx.getFacesContext().isPostback())) {
			// in the form level
			UIComponent parentComponent = c;
			if (parentComponent.getChildCount() > 0) {
				if (parentComponent.getChildren().get(0) instanceof UIFields) {
					parentComponent = parentComponent.getChildren().get(0);
				}
				final List<UIComponent> children = parentComponent.getChildren();
				for (int i = 0; i < children.size(); i++) {
					final UIComponent child = children.get(i);
					// in the component level
					if (JKJsfUtil.getBooleanAttribute(child, TagAttributeConstants.INPUT_SHOW_LABEL, true)) {
						String labelKey = (String) child.getAttributes().get(TagAttributeConstants.LABEL);
						if (labelKey == null) {
							labelKey = "lbl_".concat(child.getId());
						}
						final String id = "lbl_".concat(labelKey);
						final HtmlOutputLabel out = JSFComponentFactory.createLabel(labelKey);
						out.setId(StringUtil.trim(id));
						out.setFor(child.getId());
						out.getAttributes().put(TagAttributeConstants.STYLE_CLASS, TagAttributeConstants.LABEL_STYLE);
						children.add(i++, out);
					}
				}
			}
		} else {
		}
	}
}
