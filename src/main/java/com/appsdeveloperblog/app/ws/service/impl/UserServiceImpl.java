package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repository.UserRepository;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDto;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Utils utils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    //private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("User created");
        if (Objects.nonNull(userRepository.findByEmail(userDto.getEmail())))
            throw new RuntimeException("Email is already taken!");

        for (int i = 0; i < userDto.getAddresses().size(); i++) {
            AddressDto address = userDto.getAddresses().get(i);
            address.setUserDetails(userDto);
            address.setAddressId(utils.generateAddressId(30));
            userDto.getAddresses().set(i, address);
        }
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setUserId(utils.generateUserId(30));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        UserEntity storedUserDetails = userRepository.save(userEntity);

        return modelMapper.map(storedUserDetails, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = safelyGetUserEntityByEmail(email);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {
        log.info("Specific user retrieved");
        UserEntity userEntity = safelyGetUserEntityByEmail(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto returnValue = new UserDto();
        UserEntity userEntity = safelyGetUserEntityByUserId(userId);
        BeanUtils.copyProperties(userEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {
        log.info("UpdateUser method was called!");
        UserDto returnValue = new UserDto();
        UserEntity userEntity = safelyGetUserEntityByUserId(userId);

        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntity);
        BeanUtils.copyProperties(updatedUserDetails, returnValue);

        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        log.info("DeleteUser method was called");
        UserEntity userEntity = safelyGetUserEntityByUserId(userId);

        userRepository.delete(userEntity);
        log.info("User with userId {} was deleted", userId);
    }

    private UserEntity safelyGetUserEntityByUserId(String userId) throws UserServiceException {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (Objects.isNull(userEntity)) {
            throw new UserServiceException("User with userId: " + userId + " is not found");
        }
        return userEntity;
    }

    private UserEntity safelyGetUserEntityByEmail(String email) throws UserServiceException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("User with email: " + email + " is not found");
        }
        return userEntity;
    }

    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();

        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        for (UserEntity userEntity : users) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(userEntity, userDto);
            returnValue.add(userDto);
        }

        return returnValue;
    }
}
