package com.csonezp.daodemo.domain.repo.base;

public interface BaseRepo<T, ID>{
    /**
     * 查找
     * @param id
     * @return
     */
    T findById(ID id);

    /**
     * 创建
     * @param one
     * @param <S>
     * @return
     */
    <S extends T> S create(S one);

    /**
     * 更新
     * @param one
     * @param <S>
     * @return
     */
    <S extends T> S update(S one);
}
