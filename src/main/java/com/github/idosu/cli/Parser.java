package com.github.idosu.cli;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * TODO: desc
 *
 * @author
 *      <br>13 Dec 2016 idosu
 */
public interface Parser {/*
    static boolean isBoolean(Class<?> type) {
        return type == boolean.class || type == Boolean.class;
    }

    static boolean isArray(Class<?> type) {
        return type.isArray() || Collection.class.isAssignableFrom(type);
    }
*/
    String getValueString(Field field, String valueString);/* {
        if (isBoolean(field.getType())) {
            return null;
        }
        if (isArray(field.getType())) {
            return valueString + "...";
        }
        return valueString;
    }*/
}
