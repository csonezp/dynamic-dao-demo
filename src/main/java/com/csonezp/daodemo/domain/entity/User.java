package com.csonezp.daodemo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends AbstractEntity{

    private Long id;

    private String name;

    private Integer age;


    @Override
    protected void checkIntegrity() throws Exception {

    }

}
