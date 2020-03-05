package com.joneying.common.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Session implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean admin = false;
	
	private List<String> auths = new ArrayList<String>();//权限集合

	public List<String> getAuths() {
		return auths;
	}

	public void setAuths(List<String> auths) {
		this.auths = auths;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
}
