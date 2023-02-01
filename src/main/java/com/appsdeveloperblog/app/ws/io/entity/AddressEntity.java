package com.appsdeveloperblog.app.ws.io.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = 983719493498108280L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String addressId;

    @Column(nullable = false, length = 15)
    private String city;

    @Column(nullable = false, length = 15)
    private String country;

    @Column(nullable = false, length = 100)
    private String streetName;

    @Column(nullable = false, length = 7)
    private String postalCode;

    @Column(nullable = false, length = 10)
    private String type;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonBackReference
    private UserEntity userDetails;
}
