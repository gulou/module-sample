package com.gulou.test.common.service;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * <p>唯一id生成器</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/08/02 12:59
 */

@Service
public class UniqueIdGenService {

    public Long nextId(){
        return new Random().nextLong();
    }
}
