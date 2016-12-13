package com.github.idosu.cli;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;

/**
 * TODO: desc
 *
 * @author
 *      <br>12 Dec 2016 idosu
 */
public class Cli<T> {
    private class ArgInfo {
        final Field field;
        final Parser parser;
        final String name;
        final String valueString;
        final Arg annotation;

        private ArgInfo(Field field, Parser parser, String name, String valueString, Arg annotation) {
            this.field = field;
            this.parser = parser;
            this.name = name;
            this.valueString = valueString;
            this.annotation = annotation;
        }

        @Override
        public String toString() {
            // TODO: Save?
            return "-"
                + (name.length() == 1 ? "" : "-")
                + name
                + (valueString == null
                    ? ""
                    : (name.length() == 1 ? " " : "=") + valueString
                );
        }
    }

    private class Ending {
        final Field field;
        final Parser parser;
        final EndArg annotation;

        private Ending(Field field, Parser parser, EndArg annotation) {
            this.field = field;
            this.parser = parser;
            this.annotation = annotation;
        }
    }

    private final Class<T> type;
    private final Map<String, ArgInfo> metadata;
    private final Ending ending;

    private static Collection<String> getDefaultNames(String fieldName) {
        List<String> names = new ArrayList<>(2);

        // Simple name, ex. 'maxCount' -> 'm'
        names.add(Character.toString(fieldName.charAt(0)));

        // Complex name, ex. 'maxCount' -> 'max-count'
        StringBuilder name = new StringBuilder((int)(fieldName.length() * 1.5));
        for (int i = 0, length = fieldName.length(); i < length; i++) {
            char ch = fieldName.charAt(i);
            if (Character.isUpperCase(ch)) {
                ch = Character.toLowerCase(ch);
                name.append('-');
            }
            name.append(ch);
        }
        names.add(name.toString());
        /*names.add(
            fieldName.chars()
                .flatMap(x -> Arrays.stream(
                    isUpperCase(x)
                        ? new int[] { '-', toLowerCase(x) }
                        : new int[] { x }
                ))
                .mapToObj(x -> Character.toString((char)x))
                .collect(joining())
        );*/

        return names;
    }

    public Cli(Class<T> type) {
        Map<String, ArgInfo> metadata = new HashMap<>();
        Ending ending = null;

        for (Field field : type.getFields()) {
            EndArg endArg = field.getAnnotation(EndArg.class);
            if (endArg != null) {
                // TODO: Check no Arg or Args
                // TODO: mandatory
                // TODO: Check dup
                ending = new Ending(field, getParserFor(endArg.parser()), endArg);
            } else {
                for (Arg arg : getArgs(field)) {
                    Name name = arg.name();
                    Parser parser = getParserFor(arg.parser());

                    Collection<String> names;
                    String valueString;

                    if (name.value().length == 0) {
                        names = getDefaultNames(field.getName());
                        valueString = field.getName();
                    } else  {
                        names = asList(name.value());
                        valueString = name.valueString();
                        if (valueString.isEmpty()) {
                            if (names.size() == 1) {
                                valueString = names.iterator().next();
                            } else {
                                valueString = field.getName();
                            }
                        }
                    }

                    // Asserts that there are no duplicates names
                    for (String n : names) {
                        ArgInfo duplicate = metadata.get(n);
                        if (duplicate != null) {
                            // TODO: Exception message and type
                            throw new IllegalStateException();
                        }
                    }

                    // Add names to the metadata
                    valueString = parser.getValueString(field, valueString);

                    for (String n : names) {
                        // Every name creates a separate entry in the metadata, TODO: maybe add a layer for field({ field1: [ arg1, arg2 ], field2 : [ arg1 ] })
                        metadata.put(n, new ArgInfo(field, parser, n, valueString, arg));
                    }
                }
            }
        }

        this.type = type;
        this.metadata = unmodifiableMap(metadata);
        this.ending = ending;
    }

    private static Collection<Arg> getArgs(Field field) {
        // TODO: Check ignore
        List<Arg> args = new LinkedList<>();
        Arg argAnnot = field.getAnnotation(Arg.class);
        if (argAnnot != null) {
            args.add(argAnnot);
        }
        Args argsAnnot = field.getAnnotation(Args.class);
        if (argsAnnot != null) {
            Collections.addAll(args, argsAnnot.value());
        }

        if (args.isEmpty()) {
            args.add(Arg.DEFAULT);
        }

        return args;
    }

    private Parser getParserFor(Class<? extends Parser> parserClass) {
        // Create a new instance and save in cache - also asserts singleton
        try {
            return parserClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO:
            throw new RuntimeException(e);
        }
    }
}
