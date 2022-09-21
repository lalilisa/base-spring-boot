package com.example.base.implement.user;

import com.example.base.common.dto.QueryDto;
import com.example.base.common.dto.ResponseListAll;
import com.example.base.entity.user.UserEntity;
import com.example.base.implement.base.AbstractJpaDAO;
import com.example.base.repository.user.UserRepository;
import com.example.base.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.List;

@Service
public class UserServiceImpl extends AbstractJpaDAO implements UserService {


    private UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        super(UserEntity.class);
        this.userRepository=userRepository;
    }
    @Override
    public ResponseListAll findAlls(QueryDto queryDto) throws JsonProcessingException {
        return this.findsEntity(queryDto);
    }

    @Override
    public List<UserEntity> findAll(QueryDto queryDto) throws JsonProcessingException {
        return null;
    }

    @Override
    public UserEntity findOneById(long id) {
        return this.userRepository.findUserEntitiesById(id);
    }

    @Override
    public UserEntity create(UserEntity entity) {
        return this.userRepository.save(entity);
    }

    @Override
    public UserEntity update(UserEntity entity) {

        return this.userRepository.save(entity);
    }

    @Override
    public void deleleById(long id) {
         this.userRepository.deleteById(id);
    }


}
