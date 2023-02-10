package com.appsdeveloperblog.app.ws.io.repository;

import com.appsdeveloperblog.app.ws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    static boolean recordsCreated = false;

    @BeforeEach
    void setUp() {
        //createRecords();
    }

    @Test
    void findAllUsersWithUnconfirmedEmailAddress() {
        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> pages = userRepository.findAllUsersWithUnconfirmedEmailAddress(pageableRequest);

        assertNotNull(pages);

        List<UserEntity> users = pages.getContent();
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    void findUsersByFirstName() {
        String firstName = "Alinur";
        List<UserEntity> usersByFirstName = userRepository.findUsersByFirstName(firstName);
        assertEquals(usersByFirstName.size(), 1);
    }

    @Test
    void findUsersByLastName() {
        String lastName = "Kargopolov";
        List<UserEntity> usersByFirstName = userRepository.findUsersByLastName(lastName);
        assertEquals(usersByFirstName.size(), 2);
    }

    @Test
    void findUsersByKeyword() {
        String keyword = "ma";
        List<UserEntity> usersByFirstName = userRepository.findUsersByKeyword(keyword);
        assertEquals(usersByFirstName.size(), 2);

        UserEntity userEntity = usersByFirstName.get(0);
        assertTrue(userEntity.getFirstName().contains(keyword) ||
                userEntity.getLastName().contains(keyword));
    }

    @Test
    final void findUserFirstNameAndLastNameByKeyword() {
        String keyword = "erg";
        List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
        assertNotNull(users);
        assertEquals(2, users.size());

        Object[] user = users.get(0);

        assertEquals(2, user.length);

        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println("First name = " + userFirstName);
        System.out.println("Last name = " + userLastName);
    }

    @Test
    final void testUpdateUserEmailVerificationStatus() {
        boolean newEmailVerificationStatus = true;
        userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, "12lkemqwda;skm");

        UserEntity storedUserDetails = userRepository.findByUserId("12lkemqwda;skm");
        boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
        assertEquals(storedEmailVerificationStatus, newEmailVerificationStatus);
    }

    //JPQL
    @Test
    final void findUserByUserId() {
        String userId = "12lkemqwda;skm";
        UserEntity user = userRepository.findUserByUserId(userId);
        assertNotNull(user);
        assertEquals(user.getUserId(), userId);
    }

    @Test
    final void testGetUserEntityFullNameById() {
        String userId = "1a2b3c";
        List<Object[]> records = userRepository.findUserFullNameById(userId);

        assertNotNull(records);
        assertEquals(1, records.size());

        Object[] userDetails = records.get(0);

        String firstName = String.valueOf(userDetails[0]);
        String lastName = String.valueOf(userDetails[1]);

        assertNotNull(firstName);
        assertNotNull(lastName);
    }

    @Test
    final void findUserFullNameByKeyword() {
        String keyword = "erg";
        List<Object[]> users = userRepository.findUserFullNameByKeyword(keyword);
        assertNotNull(users);
        assertEquals(2, users.size());

        Object[] user = users.get(0);

        assertEquals(2, user.length);

        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println("First name = " + userFirstName);
        System.out.println("Last name = " + userLastName);

    }

    @Test
    final void testUpdateUserEntityEmailVerificationStatus() {
        boolean newEmailVerificationStatus = true;
        userRepository.updateUserEntityEmailVerificationStatus(newEmailVerificationStatus, "L5SNdzDlDROsLuI5vB3pKYvuiZDL76");

        UserEntity storedUserDetails = userRepository.findByUserId("L5SNdzDlDROsLuI5vB3pKYvuiZDL76");
        boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
        assertEquals(storedEmailVerificationStatus, newEmailVerificationStatus);
    }

    private void createRecords() {
        // Prepare User Entity
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Kargopolov");
        userEntity.setUserId("1a2b3c");
        userEntity.setEncryptedPassword("xxx");
        userEntity.setEmail("test@test.com");
        userEntity.setEmailVerificationStatus(true);

        // Prepare User Addresses
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setType("shipping");
        addressEntity.setAddressId("ahgyt74hfy");
        addressEntity.setCity("Vancouver");
        addressEntity.setCountry("Canada");
        addressEntity.setPostalCode("ABCCDA");
        addressEntity.setStreetName("123 Street Address");

        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);

        userEntity.setAddresses(addresses);

        userRepository.save(userEntity);


        // Prepare User Entity
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Sergey");
        userEntity2.setLastName("Kargopolov");
        userEntity2.setUserId("1a2b3cddddd");
        userEntity2.setEncryptedPassword("xxx");
        userEntity2.setEmail("test@test.com");
        userEntity2.setEmailVerificationStatus(true);

        // Prepare User Addresses
        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setType("shipping");
        addressEntity2.setAddressId("ahgyt74hfywwww");
        addressEntity2.setCity("Vancouver");
        addressEntity2.setCountry("Canada");
        addressEntity2.setPostalCode("ABCCDA");
        addressEntity2.setStreetName("123 Street Address");

        List<AddressEntity> addresses2 = new ArrayList<>();
        addresses2.add(addressEntity2);

        userEntity2.setAddresses(addresses2);

        userRepository.save(userEntity2);

        recordsCreated = true;
    }
}