package com.csonezp.daodemo.facade;

import com.csonezp.daodemo.domain.entity.User;

public interface UserFacade {
    User findById(Long uid);

    User createNew(String name,Integer age);
}
