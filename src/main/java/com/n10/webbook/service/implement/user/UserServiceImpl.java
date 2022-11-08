package com.n10.webbook.service.implement.user;

import com.n10.webbook.common.dto.QueryDto;
import com.n10.webbook.common.dto.ResponseListAll;
import com.n10.webbook.entity.user.UserEntity;
import com.n10.webbook.service.implement.base.AbstractJpaDAO;
import com.n10.webbook.repository.user.UserRepository;
import com.n10.webbook.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

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
