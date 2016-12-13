package com.github.idosu.cli;

import com.github.idosu.cli.parser.DefaultParser;

import java.lang.annotation.*;
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
public @interface Arg {
    // TODO: Little yuc
    Arg DEFAULT = (Arg)Proxy.newProxyInstance(Arg.class.getClassLoader(), new Class<?>[]{Arg.class}, new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return method.getDefaultValue();
        }
    });

    Name name() default @Name;
    String description() default "";
    // TODO:
    Class<? extends Parser> parser() default DefaultParser.class;
}
