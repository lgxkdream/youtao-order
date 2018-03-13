package com.youtao.order.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @title: ValidateUtil
 * @description: 参数检验工具
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月13日 下午1:12:21
 */
public class ValidateUtil { 
    
    private static Validator validator; // 它是线程安全的 
 
    static { 
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); 
        validator = factory.getValidator(); 
    } 
     
    public static <T> void validate(T t) throws Exception { 
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t); 
        if(constraintViolations.size() > 0) { 
            String validateError = ""; 
            for(ConstraintViolation<T> constraintViolation: constraintViolations) { 
                validateError += constraintViolation.getMessage() + ";"; 
            } 
            throw new Exception(validateError); 
        } 
    } 
     
} 
