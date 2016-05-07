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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.jk.db.datasource.JKDataSourceFactory;
import com.jk.exceptions.JKEmptyReportException;
import com.jk.exceptions.JKReportException;

@ManagedBean(name = "mbReports")
@ApplicationScoped
public class MBReportManager extends JKManagedBean {
	Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * @param reportName
	 * @param paramters
	 * @return
	 * @throws JKReportException
	 */
	public StreamedContent getReport(String reportName, HashMap<String, Object> paramters) throws JKReportException {
		logger.fine("Printing report : ".concat(reportName));
		logger.fine("Paramaters :".concat(paramters.toString()));

		String jasperFileName = "/resources/reports/" + reportName + ".jasper";
		Connection connection = JKDataSourceFactory.getDataSource().getConnection();
		try {
			// Generate jasper print
			InputStream inStream = getClass().getResourceAsStream(jasperFileName);
			paramters.put("SUBREPORT_DIR", "/resources/reports/");
			JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(inStream, paramters, connection);
			if (jprint.getPages().size() == 0) {
				throw new JKEmptyReportException();
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jprint, out);
			DefaultStreamedContent content = new DefaultStreamedContent(new ByteArrayInputStream(out.toByteArray()));
			content.setContentType("application/pdf");
			content.setName(reportName + ".pdf");
			return content;
		} catch (Exception e) {
			throw new JKReportException("Unable to print report : ", e);
		} finally {
			JKDataSourceFactory.getDataSource().close(connection);
		}
	}

}
