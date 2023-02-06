package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repository.UserRepository;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDto;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    String userId = "12emlkqewdm";

    String addressId = "kjbkjninliuoijn";

    String encryptedPassword = "qwdmx3209323eqweere";

    UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userEntity = UserEntity.builder()
                .id(1L)
                .userId(userId)
                .firstName("Alinur")
                .lastName("Amangazy")
                .email("kiselek.games@gmail.com")
                .addresses(getAddressEntities())
                .build();
    }

    @Test
    void createUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        when(utils.generateAddressId(anyInt())).thenReturn(addressId);
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto userDto = new UserDto();
        userDto.setAddresses(getAddresses());
        userDto.setFirstName("Alinur");
        userDto.setLastName("Amangazy");
        userDto.setPassword("pass123123");
        userDto.setEmail("kiselek.games@gmail.com");

        //Do nothing
//      Mockito.doNothing().when(userRepository).save(any(UserEntity.class));

        UserDto storedUserDto = userService.createUser(userDto);

        assertNotNull(storedUserDto);
        assertEquals(storedUserDto.getFirstName(), userEntity.getFirstName());
        assertEquals(storedUserDto.getLastName(), userEntity.getLastName());
        assertEquals(storedUserDto.getEmail(), userEntity.getEmail());
        assertNotNull(storedUserDto.getUserId());
        assertEquals(storedUserDto.getAddresses().size(), userEntity.getAddresses().size());
        verify(utils, times(2)).generateAddressId(30);
        verify(utils, times(1)).generateUserId(30);
        verify(bCryptPasswordEncoder, times(1)).encode("pass123123");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUser_crateUserServiceException() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = new UserDto();
        userDto.setAddresses(getAddresses());
        userDto.setFirstName("Alinur");
        userDto.setLastName("Amangazy");
        userDto.setPassword("pass123123");
        userDto.setEmail("kiselek.games@gmail.com");

        assertThrows(UserServiceException.class,
                () -> {
                    userService.createUser(userDto);
                }
        );
    }

    private List<AddressDto> getAddresses() {
        AddressDto shippingAddress = new AddressDto();
        shippingAddress.setType("shipping");
        shippingAddress.setCity("Almaty");
        shippingAddress.setCountry("Kazakhstan");
        shippingAddress.setPostalCode("005599");
        shippingAddress.setStreetName("Bayzak 12");

        AddressDto billingAddress = new AddressDto();
        billingAddress.setType("billing");
        billingAddress.setCity("Almaty");
        billingAddress.setCountry("Kazakhstan");
        billingAddress.setPostalCode("005599");
        billingAddress.setStreetName("Bayzak 12");

        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(shippingAddress);
        addresses.add(billingAddress);
        return addresses;
    }

    public List<AddressEntity> getAddressEntities() {
        List<AddressDto> addresses = getAddresses();

        Type listType = new TypeToken<List<AddressEntity>>() {
        }.getType();
        return new ModelMapper().map(addresses, listType);
    }

    @Test
    void getUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser(anyString());

        assertNotNull(userDto.getUserId());
        assertEquals("Alinur", userDto.getFirstName());
    }

    @Test
    void getUser_UsernameNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> {
                    userService.getUser(anyString());
                }
        );
    }

    @Test
    void getUserByUserId() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUsers() {
    }
}