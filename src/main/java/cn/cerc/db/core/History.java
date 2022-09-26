package cn.cerc.db.core;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RUNTIME)
public @interface History {
    boolean master() default false;

    boolean insert() default false;

    boolean delete() default false;

    boolean update() default true;
}
