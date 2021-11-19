package com.csonezp.daodemo.infra.repo.register;

import com.csonezp.daodemo.domain.repo.base.BaseRepo;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Set;

public class RepoBeanDefinitionRegistry implements BeanDefinitionRegistryPostProcessor {

    private JdbcTemplate jdbcTemplate;

    public RepoBeanDefinitionRegistry(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        //获取所有的实现了BaseRepo的接口
        //为了演示方便都是硬编码，实际上应该是配置化或者基于注解、class等
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.csonezp.daodemo.domain.repo"))
                .setScanners(Scanners.SubTypes)
                .filterInputsBy(new FilterBuilder().excludePackage("com.csonezp.daodemo.domain.repo.base"))
        );
        Set<Class<? extends BaseRepo>> beanClazzs = reflections.getSubTypesOf(BaseRepo.class);


        //循环repo列表，每个repo interface都要构建一个bean放到spring容器中
        for (Class beanClazz : beanClazzs) {

            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            //给BeanFactory添加构造函数参数；会按照addGenericArgumentValue的顺序来传递构造函数参数。
            //RepoBeanFactory有两个构造函数，clazz和jdbcTemplate，分别添加进去即可。
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClazz);
            definition.getConstructorArgumentValues().addGenericArgumentValue(jdbcTemplate);
            //设置BeanFactory。RepoBeanFactory里用动态代理、反射的方式给repo接口生成了实际的代理类（RepoProxy)
            definition.setBeanClass(RepoBeanFactory.class);

            //设置bean autowire方式
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            registry.registerBeanDefinition(beanClazz.getSimpleName(), definition);
        }
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

}
