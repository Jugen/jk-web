package com.app.security;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jk.db.dataaccess.orm.JKObjectDataAccess;
import com.jk.util.JK;
import com.jk.util.exceptions.JKSecurityException;
import com.jk.util.factory.JKFactory;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;

public class UserService implements UserDetailsService {
	private static final String ROLE_USER = "ROLE_USER";
	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	static JKLogger logger = JKLoggerFactory.getLogger(UserService.class);
	JKObjectDataAccess dataAccess = JKFactory.instance(JKObjectDataAccess.class);

	//////////////////////////////////////////////////////////////////////////////
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = dataAccess.findOneByFieldName(User.class, "username", username);
		if (user == null) {
			if (username.equals("admin")) {
				if (Boolean.parseBoolean(System.getProperty("jk.allow-admin", "true"))) {
					logger.info("Admin account doesnot exist, create one with admin/admin");
					user = createAdminAccount();
				}
			} else {
				throw new UsernameNotFoundException("User doesn't exist");
			}
		}
		return user;

	}

	//////////////////////////////////////////////////////////////////////////////
	private User createAdminAccount() {
		String password = new BCryptPasswordEncoder().encode("admin");
		dataAccess.insert(new Role(ROLE_ADMIN));
		dataAccess.insert(new Role(ROLE_USER));
		User user = new User("admin", password, getAllRoles());
		dataAccess.insert(user);
		return user;
	}

	//////////////////////////////////////////////////////////////////////////////
	public List<Role> getAllRoles() {
		return dataAccess.getList(Role.class);
	}

	//////////////////////////////////////////////////////////////////////////////
	public void updateUserPassword(String userName, String password, String newPassword) {
		User user = loadUserByUsername(userName);
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
			user.setPassword(bCryptPasswordEncoder.encode(newPassword));
			dataAccess.update(user);
		} else {
			throw new JKSecurityException("Current password is not correct");
		}
	}

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("admin"));
	}
}
