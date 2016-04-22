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
package com.jk.faces.mb;

import com.jk.db.dataaccess.plain.JKPlainDataAccess;
import com.jk.db.datasource.JKDataSourceFactory;

/**
 * The Class JKDbManagedBean.
 *
 * @author Jalal Kiswani
 */
public class JKPlainDataAccessManagedBean extends JKManagedBean {

	protected JKPlainDataAccess dataAccess = JKDataSourceFactory.getPlainDataAccess();

	/**
	 * Execute.
	 *
	 * @param query
	 *            the query
	 * @param params
	 *            the params
	 */
	protected void execute(String query, Object... params) {
		dataAccess.execute(query, params);
	}

}
