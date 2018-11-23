package com.jk.web.security;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.jk.web.controllers.JKManagedBeanWithOrmSupport;

@ManagedBean(name="mbRoles")
@ViewScoped
public class MB_Roles extends JKManagedBeanWithOrmSupport<Role>{

}
