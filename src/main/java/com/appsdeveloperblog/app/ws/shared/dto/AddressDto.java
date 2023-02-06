package com.appsdeveloperblog.app.ws.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {

    private static final long serialVersionUID = 6835191090898364280L;
    private long id;
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
    private UserDto userDetails;
}
