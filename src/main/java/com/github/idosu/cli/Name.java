package com.github.idosu.cli;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO: desc
 *
 * @author
 *      <br>12 Dec 2016 idosu
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {
    String[] value() default { };
    String valueString() default "";
}
