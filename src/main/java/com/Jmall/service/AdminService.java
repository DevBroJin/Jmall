package com.Jmall.service;

import com.Jmall.domain.AdminVO;

public interface AdminService {

	AdminVO admin_ok(String admin_id);
	
	void login_date(String admin_id);
}
