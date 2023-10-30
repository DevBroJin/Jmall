package com.Jmall.service;

import com.Jmall.dto.EmailDTO;

public interface EmailService {

	void sendMail(EmailDTO dto, String message);
}
