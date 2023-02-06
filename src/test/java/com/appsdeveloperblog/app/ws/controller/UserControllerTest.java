package com.appsdeveloperblog.app.ws.controller;

import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDto;
import com.appsdeveloperblog.app.ws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserDto userDto;

    private static final String USER_ID = "qweq21ek0dowef";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);


        userDto = new UserDto();
        userDto.setAddresses(getAddresses());
        userDto.setFirstName("Alinur");
        userDto.setLastName("Amangazy");
        userDto.setPassword("pass123123");
        userDto.setEmail("kiselek.games@gmail.com");
        userDto.setUserId(USER_ID);
    }

    @Test
    void getUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(USER_ID);

        assertNotNull(userRest);
        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertEquals(userDto.getLastName(), userRest.getLastName());
        assertEquals(userDto.getEmail(), userRest.getEmail());
        assertEquals(userDto.getAddresses().size(), userRest.getAddresses().size());
        assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
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

}