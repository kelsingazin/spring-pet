package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto user);

    UserDto getUser(String email);

    UserDto updateUser(String userId, UserDto user);

    void deleteUser(String userId);

    UserDto getUserByUserId(String id);

    List<UserDto> getUsers(int page, int limit);

    List<UserDto> getUsersWithUnconfirmedEmail(int page, int limit);

    List<UserDto> getUsersByFirstName(String firstName);
}
