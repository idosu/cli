package com.github.idosu.cli;

import java.lang.annotation.*;

/**
 * TODO: desc
 *
 * @author
 *      <br>12 Dec 2016 idosu
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Args {
    Arg[] value();
}
