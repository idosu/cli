package com.github.idosu.cli.parser;

import com.github.idosu.cli.Parser;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * TODO: desc
 *
 * @author
 *      <br>13 Dec 2016 idosu
 */
public class DefaultParser implements Parser {
    boolean isBoolean(Class<?> type) {
        return type == boolean.class || type == Boolean.class;
    }

    boolean isArray(Class<?> type) {
        return type.isArray() || Collection.class.isAssignableFrom(type);
    }

    @Override
    public String getValueString(Field field, String valueString) {
        if (isBoolean(field.getType())) {
            return null;
        }
        // TODO: uppercase?
        if (isArray(field.getType())) {
            return valueString + "...";
        }
        return valueString;
    }
}
