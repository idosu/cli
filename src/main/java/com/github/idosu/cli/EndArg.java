package com.github.idosu.cli;

import com.github.idosu.cli.parser.DefaultParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * TODO: desc
 *
 * @author
 *      <br>12 Dec 2016 idosu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EndArg {
    String description() default "";
    // TODO:
    Class<? extends Parser> parser() default DefaultParser.class;
}
