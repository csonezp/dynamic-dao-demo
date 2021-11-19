/*
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 */

package com.csonezp.daodemo.domain.entity;


/**
 * 领域对象抽象类。
 *
 * @author skeleton
 * @date 06/21/2021
 **/
public abstract class AbstractEntity {




    /**
     * 模型完整性校验.
     *
     * @throws Exception 当领域对象不完整时抛出 {@link Exception}
     */
    protected abstract void checkIntegrity() throws Exception;



}
