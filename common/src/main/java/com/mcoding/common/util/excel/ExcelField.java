package com.mcoding.common.util.excel;

import java.lang.annotation.*;

/**
 * @author wzt on 2020/2/12.
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelField {

    String title();
    Class<? extends ObjToStrConverter> objToStrConverter() default ObjToStrConverter.class;
}
