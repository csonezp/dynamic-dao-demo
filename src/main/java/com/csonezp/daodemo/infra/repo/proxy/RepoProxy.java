package com.csonezp.daodemo.infra.repo.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.Table;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * 这里实际上就是repo的代理类了，jdbc操作需要在这里完成。
 * 实际上调用userRepo.xxx时，就会走到这里
 */
@Slf4j
public class RepoProxy<T, ID> implements InvocationHandler {
    String tableName;
    Class<T> entityClass;
    Class<ID> idClass;
    Field[] fields;
    JdbcTemplate jdbcTemplate;

    public RepoProxy(Class<T> entityClass, Class<ID> idClass, JdbcTemplate jdbcTemplate) {
        this.entityClass = entityClass;
        this.jdbcTemplate = jdbcTemplate;
        this.idClass = idClass;
        this.tableName = getTableName(entityClass);
        this.fields = entityClass.getDeclaredFields();
    }

    /**
     * userRepo.xxx对应的代理方法
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //如果传进来是一个已实现的具体类（本次演示略过此逻辑)
        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                return method.invoke(this, args);
            } catch (Throwable t) {
                t.printStackTrace();
            }
            //如果传进来的是一个接口（核心)
        } else {
            return run(method, args);
        }
        return null;
    }

    private T run(Method method, Object[] args) throws InstantiationException, IllegalAccessException {
        String methodName = method.getName();

        switch (methodName) {
            case "findById":
                Long id = (Long) args[0];
                Object resObj = jdbcTemplate.queryForObject("select * from " + tableName + " where id=?",
                        new BeanPropertyRowMapper<>(entityClass), id);
                return (T) resObj;
            case "create":
                break;
            case "update":
                break;
            default:
                log.error("no support method");
                break;
        }
        return (T) method.getReturnType().newInstance();
    }


    /**
     * 去entity注解上的表名，为了简单省略异常处理
     *
     * @param entityClass
     * @return
     */
    private String getTableName(Class<?> entityClass) {
        Table table = entityClass.getAnnotation(Table.class);
        tableName = table.name();
        return tableName;
    }
}
