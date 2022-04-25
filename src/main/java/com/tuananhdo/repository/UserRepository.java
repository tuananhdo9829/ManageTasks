package com.tuananhdo.repository;

import com.tuananhdo.entity.AuthenticationType;
import com.tuananhdo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByEmail(String email);

    Long countById(Integer id);

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User getUserByUsername(String username);

    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    void updateEnableStatus(Integer id, boolean enabled);


    @Query("UPDATE User u SET u.enabled = true WHERE u.id =?1")
    @Modifying
    void activeUser(Integer id);

    @Query("SELECT u FROM User u WHERE u.verificationCode = ?1")
    User findByVerificationCode(String code);

    @Query("UPDATE User u SET u.authenticationType = ?2 WHERE u.id = ?1")
    @Modifying
    void updateAuthenticationType(Integer userId, AuthenticationType authenticationType);
}
