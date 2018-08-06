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
package com.jk.faces.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UISelectItems;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;

import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.submenu.UISubmenu;

import com.jk.util.annotations.Author;

/**
 * The Class UIViewVisitor.
 *
 * @author Jalal Kiswani
 */
@Author(name = "Jalal H. Kiswani", date = "1/10/2014", version = "1.0")
public class UIFacesVisitor implements VisitCallback {

	/** The inputs. */
	private final List<UIInput> inputs = new ArrayList<>();

	/** The forms. */
	private final List<UIForm> forms = new ArrayList<>();

	/** The commands. */
	private final List<UICommand> commands = new ArrayList<>();

	/** The outputs. */
	private final List<UIOutput> outputs = new ArrayList<>();

	/** The sub menus. */
	private final List<UISubmenu> subMenus = new ArrayList<>();

	/** The columns. */
	private final List<Column> columns = new ArrayList<>();

	/** The tables. */
	private final List<DataTable> tables = new ArrayList<>();

	/** The select items. */
	private final List<UISelectItems> selectItems = new ArrayList<>();

	/** The panel grids. */
	private List<PanelGrid> panelGrids = new ArrayList<>();

	/**
	 * Gets the columns.
	 *
	 * @return the columns
	 */
	public List<Column> getColumns() {
		return this.columns;
	}

	/**
	 * Gets the commands.
	 *
	 * @return the commands
	 */
	public List<UICommand> getCommands() {
		return this.commands;
	}

	/**
	 * Gets the forms.
	 *
	 * @return the forms
	 */
	public List<UIForm> getForms() {
		return this.forms;
	}

	/**
	 * Gets the inputs.
	 *
	 * @return the inputs
	 */
	public List<UIInput> getInputs() {
		return this.inputs;
	}

	/**
	 * Gets the outputs.
	 *
	 * @return the outputs
	 */
	public List<UIOutput> getOutputs() {
		return this.outputs;
	}

	/**
	 * Gets the panel grids.
	 *
	 * @return the panel grids
	 */
	public List<PanelGrid> getPanelGrids() {
		return this.panelGrids;
	}

	/**
	 * Gets the select items.
	 *
	 * @return the select items
	 */
	public List<UISelectItems> getSelectItems() {
		return this.selectItems;
	}

	/**
	 * Gets the sub menus.
	 *
	 * @return the sub menus
	 */
	public List<UISubmenu> getSubMenus() {
		return this.subMenus;
	}

	/**
	 * Gets the tables.
	 *
	 * @return the tables
	 */
	public List<DataTable> getTables() {
		return this.tables;
	}

	/**
	 * Sets the panel grids.
	 *
	 * @param panelGrids
	 *            the new panel grids
	 */
	public void setPanelGrids(final List<PanelGrid> panelGrids) {
		this.panelGrids = panelGrids;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * javax.faces.component.visit.VisitCallback#visit(javax.faces.component
	 * .visit.VisitContext, javax.faces.component.UIComponent)
	 */
	@Override
	public VisitResult visit(final VisitContext context, final UIComponent target) {
		// if (!target.isRendered()) {
		// return VisitResult.REJECT;
		// }

		if (target instanceof UIInput) {
			this.inputs.add((UIInput) target);
		}
		if (target instanceof UIForm) {
			this.forms.add((UIForm) target);
		}

		if (target instanceof UICommand) {
			this.commands.add((UICommand) target);
		}
		if (target instanceof UIOutput) {
			this.outputs.add((UIOutput) target);
		}
		if (target instanceof UISubmenu) {
			this.subMenus.add((UISubmenu) target);
		}
		if (target instanceof Column) {
			this.columns.add((Column) target);
		}
		if (target instanceof DataTable) {
			this.tables.add((DataTable) target);
		}
		if (target instanceof UISelectItems) {
			this.selectItems.add((UISelectItems) target);
		}
		if (target instanceof PanelGrid) {
			this.panelGrids.add((PanelGrid) target);
		}
		return VisitResult.ACCEPT;
	}

}
