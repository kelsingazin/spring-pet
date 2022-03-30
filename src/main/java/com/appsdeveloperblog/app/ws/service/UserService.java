package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.shared.dto.UserDto;

public interface UserService{
	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto updateUser(String userId, UserDto user);
	void deleteUser(String userId);
}
