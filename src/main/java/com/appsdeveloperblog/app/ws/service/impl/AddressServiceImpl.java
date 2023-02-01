package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.AddressServiceException;
import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repository.AddressRepository;
import com.appsdeveloperblog.app.ws.io.repository.UserRepository;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) return returnValue;

        List<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);

        for (AddressEntity addressEntity : addresses) {
            returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
        }

        return returnValue;
    }

    @Override
    public AddressDto getAddress(String addressId) {
        if (Objects.isNull(addressId)) {
            throw new AddressServiceException("Address with addressId: " + addressId + " is not found");
        }

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        return modelMapper.map(addressEntity, AddressDto.class);
    }
}
