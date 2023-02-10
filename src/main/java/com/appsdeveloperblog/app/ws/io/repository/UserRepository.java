package com.appsdeveloperblog.app.ws.io.repository;

import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    UserEntity findByUserId(String userId);

    //Native SQL query
    @Query(value = "SELECT * FROM Users u where u.email_verification_status = 'true'",
            countQuery = "SELECT count (*) FROM Users u where u.email_verification_status = 'true'",
            nativeQuery = true)
    Page<UserEntity> findAllUsersWithUnconfirmedEmailAddress(Pageable pageableRequest);

    @Query(value = "SELECT * FROM USERS U where u.first_name = ?1", nativeQuery = true)
    List<UserEntity> findUsersByFirstName(String firstName);

    @Query(value = "SELECT * FROM USERS u where u.last_name = :lastName", nativeQuery = true)
    List<UserEntity> findUsersByLastName(@Param("lastName") String lastName);

    @Query(value = "SELECT * FROM USERS u where u.first_name LIKE %:keyword% OR u.last_name LIKE %:keyword%", nativeQuery = true)
    List<UserEntity> findUsersByKeyword(@Param("keyword") String keyword);

    @Query(value = "select u.first_name, u.last_name from Users u where u.first_name LIKE %:keyword% or u.last_name LIKE %:keyword%", nativeQuery = true)
    List<Object[]> findUserFirstNameAndLastNameByKeyword(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Users u SET u.email_verification_status = :emailVerificationStatus WHERE u.user_id = :userId", nativeQuery = true)
    void updateUserEmailVerificationStatus(@Param("emailVerificationStatus") boolean emailVerificationStatus,
                                           @Param("userId") String userId);

    // JPQL

    @Query("SELECT user from UserEntity user where user.userId = :userId")
    UserEntity findUserByUserId(@Param("userId") String userId);

    @Query("select user.firstName, user.lastName from UserEntity user where user.userId = :userId")
    List<Object[]> findUserFullNameById(@Param("userId") String userId);

    @Query(value = "select user.firstName, user.lastName from UserEntity user where user.firstName LIKE %:keyword% or user.lastName LIKE %:keyword%")
    List<Object[]> findUserFullNameByKeyword(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query(value = "UPDATE UserEntity u SET u.emailVerificationStatus = :emailVerificationStatus WHERE u.userId = :userId")
    void updateUserEntityEmailVerificationStatus(
            @Param("emailVerificationStatus") boolean emailVerificationStatus,
            @Param("userId") String userId);
}
