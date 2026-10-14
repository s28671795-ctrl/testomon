/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal;

import com.google.inject.TypeLiteral;
import com.google.inject.internal.AbstractProcessor;
import com.google.inject.internal.Errors;
import com.google.inject.internal.InjectorImpl;
import com.google.inject.internal.util.SourceProvider;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.TypeConverter;
import com.google.inject.spi.TypeConverterBinding;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

final class TypeConverterBindingProcessor
extends AbstractProcessor {
    TypeConverterBindingProcessor(Errors errors2) {
        super(errors2);
    }

    static void prepareBuiltInConverters(InjectorImpl injector) {
        TypeConverterBindingProcessor.convertToPrimitiveType(injector, Integer.TYPE, Integer.class);
        TypeConverterBindingProcessor.convertToPrimitiveType(injector, Long.TYPE, Long.class);
        TypeConverterBindingProcessor.convertToPrimitiveType(injector, Boolean.TYPE, Boolean.class);
        TypeConverterBindingProcessor.convertToPrimitiveType(injector, Byte.TYPE, Byte.class);
        TypeConverterBindingProcessor.convertToPrimitiveType(injector, Short.TYPE, Short.class);
        TypeConverterBindingProcessor.convertToPrimitiveType(injector, Float.TYPE, Float.class);
        TypeConverterBindingProcessor.convertToPrimitiveType(injector, Double.TYPE, Double.class);
        TypeConverterBindingProcessor.convertToClass(injector, Character.class, new TypeConverter(){

            @Override
            public Object convert(String value, TypeLiteral<?> toType) {
                if ((value = value.trim()).length() != 1) {
                    throw new RuntimeException("Length != 1.");
                }
                return Character.valueOf(value.charAt(0));
            }

            public String toString() {
                return "TypeConverter<Character>";
            }
        });
        TypeConverterBindingProcessor.convertToClasses(injector, Matchers.subclassesOf(Enum.class), new TypeConverter(){

            @Override
            public Object convert(String value, TypeLiteral<?> toType) {
                return Enum.valueOf(toType.getRawType(), value);
            }

            public String toString() {
                return "TypeConverter<E extends Enum<E>>";
            }
        });
        TypeConverterBindingProcessor.internalConvertToTypes(injector, new AbstractMatcher<TypeLiteral<?>>(){

            @Override
            public boolean matches(TypeLiteral<?> typeLiteral) {
                return typeLiteral.getRawType() == Class.class;
            }

            public String toString() {
                return "Class<?>";
            }
        }, new TypeConverter(){

            @Override
            public Object convert(String value, TypeLiteral<?> toType) {
                try {
                    return Class.forName(value);
                }
                catch (ClassNotFoundException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }

            public String toString() {
                return "TypeConverter<Class<?>>";
            }
        });
    }

    private static <T> void convertToPrimitiveType(InjectorImpl injector, Class<T> primitiveType, final Class<T> wrapperType) {
        try {
            final Method parser = wrapperType.getMethod("parse" + TypeConverterBindingProcessor.capitalize(primitiveType.getName()), String.class);
            TypeConverter typeConverter = new TypeConverter(){

                @Override
                public Object convert(String value, TypeLiteral<?> toType) {
                    try {
                        return parser.invoke(null, value);
                    }
                    catch (IllegalAccessException e) {
                        throw new AssertionError((Object)e);
                    }
                    catch (InvocationTargetException e) {
                        throw new RuntimeException(e.getTargetException().getMessage());
                    }
                }

                public String toString() {
                    return "TypeConverter<" + wrapperType.getSimpleName() + ">";
                }
            };
            TypeConverterBindingProcessor.convertToClass(injector, wrapperType, typeConverter);
        }
        catch (NoSuchMethodException e) {
            throw new AssertionError((Object)e);
        }
    }

    private static <T> void convertToClass(InjectorImpl injector, Class<T> type, TypeConverter converter) {
        TypeConverterBindingProcessor.convertToClasses(injector, Matchers.identicalTo(type), converter);
    }

    private static void convertToClasses(InjectorImpl injector, final Matcher<? super Class<?>> typeMatcher, TypeConverter converter) {
        TypeConverterBindingProcessor.internalConvertToTypes(injector, new AbstractMatcher<TypeLiteral<?>>(){

            @Override
            public boolean matches(TypeLiteral<?> typeLiteral) {
                Type type = typeLiteral.getType();
                if (!(type instanceof Class)) {
                    return false;
                }
                Class clazz = (Class)type;
                return typeMatcher.matches(clazz);
            }

            public String toString() {
                return typeMatcher.toString();
            }
        }, converter);
    }

    private static void internalConvertToTypes(InjectorImpl injector, Matcher<? super TypeLiteral<?>> typeMatcher, TypeConverter converter) {
        injector.getBindingData().addConverter(new TypeConverterBinding(SourceProvider.UNKNOWN_SOURCE, typeMatcher, converter));
    }

    @Override
    public Boolean visit(TypeConverterBinding command) {
        this.injector.getBindingData().addConverter(new TypeConverterBinding(command.getSource(), command.getTypeMatcher(), command.getTypeConverter()));
        return true;
    }

    private static String capitalize(String s2) {
        char capitalized;
        if (s2.length() == 0) {
            return s2;
        }
        char first = s2.charAt(0);
        return first == (capitalized = Character.toUpperCase(first)) ? s2 : capitalized + s2.substring(1);
    }
}

