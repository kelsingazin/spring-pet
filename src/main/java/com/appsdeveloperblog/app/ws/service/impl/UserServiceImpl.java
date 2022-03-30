package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	@Override
	public UserDto createUser(UserDto user) {
		return null;
	}

	@Override
	public UserDto getUser(String email) {
		return null;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		return null;
	}

	@Override
	public void deleteUser(String userId) {

	}
}
