package com.example.base.repository.user;

import com.example.base.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntitiesById(Long id);
    UserEntity findUserEntitiesByUsername(String username);
}
