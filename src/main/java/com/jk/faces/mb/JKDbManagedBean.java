package com.jk.faces.mb;

import com.jk.db.dataaccess.JKDefaultDao;

public class JKDbManagedBean extends JKManagedBean {
	JKDefaultDao dao = new JKDefaultDao();

	protected void execute(String query, Object... params) {
		dao.executeUpdate(query, params);
	}

}
