package com.csonezp.daodemo.infra.repo.proxy;

import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;


public class RepoInvoker {
    /**
     * cls就是repo接口
     * @param cls
     * @param jdbcTemplate
     * @return
     */
    public Object getInstance(Class<?> cls, JdbcTemplate jdbcTemplate) throws ClassNotFoundException {
        Type[] types = cls.getGenericInterfaces();

        //获取接口的泛型列表 <T,ID>
        //注意这里为了方便硬编码了，实际还是要用更完善的方式去获取
        ParameterizedType parameterizedType = (ParameterizedType) types[0];

        //获取<T,ID>中的T
        Type tableTypeArgument = parameterizedType.getActualTypeArguments()[0];
        //获取<T,ID>中的ID
        Type idTypeArgument = parameterizedType.getActualTypeArguments()[1];

        String typeName = tableTypeArgument.getTypeName();
        Class<?> entityClass = Class.forName(typeName);

        String idClassName = idTypeArgument.getTypeName();
        Class<?> idClass = Class.forName(idClassName);

        RepoProxy invocationHandler = new RepoProxy(entityClass,idClass,jdbcTemplate);
        Object newProxyInstance = Proxy.newProxyInstance(
                cls.getClassLoader(),
                new Class[] { cls },
                invocationHandler);
        return newProxyInstance;
    }
}
