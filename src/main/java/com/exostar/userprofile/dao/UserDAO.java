package com.exostar.userprofile.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;
import com.exostar.userprofile.entity.UserEntity;

@Repository
@EnableJpaAuditing
public interface UserDAO extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    List<UserEntity> findByFirstName(String firstName);

    List<UserEntity> findByLastName(String lastName);

    List<UserEntity> findByAgeLessThan(Integer age);

    List<UserEntity> findByAgeLessThanEqual(Integer age);

    List<UserEntity> findByAgeGreaterThan(Integer age);

    List<UserEntity> findByAgeGreaterThanEqual(Integer age);

    List<UserEntity> findByAgeEquals(Integer age);
}
