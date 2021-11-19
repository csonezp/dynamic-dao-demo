package com.csonezp.daodemo.infra.repo.register;

import com.csonezp.daodemo.infra.repo.proxy.RepoInvoker;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;

public class RepoBeanFactory<T> implements FactoryBean {

    private Class<T> interfaceType;

    private JdbcTemplate jdbcTemplate;

    public RepoBeanFactory(Class<T> interfaceType, JdbcTemplate jdbcTemplate) {
        this.interfaceType = interfaceType;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 实际构建bean的地方
     * @return
     * @throws Exception
     */
    @Override
    public Object getObject() throws Exception {
        return (T) new RepoInvoker().getInstance(interfaceType,jdbcTemplate);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return FactoryBean.super.isSingleton();
    }
}
