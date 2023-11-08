package com.Jmall.mapper;

import com.Jmall.domain.AdminVO;

public interface AdminMapper {
	
	AdminVO admin_ok(String admin_id);
	
	void login_date(String admin_id);
}
