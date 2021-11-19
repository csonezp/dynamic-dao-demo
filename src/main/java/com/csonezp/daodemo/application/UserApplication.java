package com.csonezp.daodemo.application;

import com.csonezp.daodemo.domain.entity.User;
import com.csonezp.daodemo.domain.repo.UserRepo;
import com.csonezp.daodemo.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserApplication implements UserFacade {

    @Autowired
    UserRepo userRepo;

    @Override
    public User findById(Long uid) {
        return userRepo.findById(uid);
    }

    @Override
    public User createNew(String name, Integer age) {
        return null;
    }
}
