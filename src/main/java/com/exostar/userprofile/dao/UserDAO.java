package com.exostar.userprofile.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exostar.userprofile.entity.UserEntity;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    List<UserEntity> findByFirstName(String firstName);
}
