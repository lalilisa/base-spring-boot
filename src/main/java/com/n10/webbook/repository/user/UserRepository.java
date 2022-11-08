package com.n10.webbook.repository.user;

import com.n10.webbook.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findUserEntitiesById(Long id);
    UserEntity findUserEntitiesByUsername(String username);
}
