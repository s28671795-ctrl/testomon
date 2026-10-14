/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.incoresoft.middleware.http.serialization.Serialization;
import com.incoresoft.plugin.commons.exceptions.http.HttpQueryParamsProcessingException;
import com.incoresoft.plugin.commons.validation.BeanValidator;
import io.javalin.http.Context;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class QueryParamsParser {
    private final BeanValidator validator;

    @Inject
    public QueryParamsParser(@NotNull BeanValidator validator) {
        this.validator = Objects.requireNonNull(validator);
    }

    public <T> T from(Context context, Class<T> clazz) {
        T params;
        Map<String, Object> queryParams2 = context.queryParamMap().entrySet().stream().filter(e -> !((List)e.getValue()).isEmpty()).filter(e -> !((String)((List)e.getValue()).getFirst()).equals("null")).collect(Collectors.toMap(Map.Entry::getKey, e -> QueryParamsParser.parseValue(((String)((List)e.getValue()).getFirst()).trim())));
        try {
            params = Serialization.objectMapper().readValue(Serialization.writeValueAsString(queryParams2), clazz);
        }
        catch (JsonProcessingException e2) {
            throw new HttpQueryParamsProcessingException(e2);
        }
        return this.validator.validateObjectOrThrow(params, new Class[0]);
    }

    public static <T> List<T> asList(Context context, String name, Class<T> clazz) {
        String value = context.queryParam(name);
        if (value == null) {
            return List.of();
        }
        try {
            return (List)Serialization.objectMapper().readerForListOf(clazz).readValue(value);
        }
        catch (JsonProcessingException e) {
            throw new HttpQueryParamsProcessingException(e);
        }
    }

    private static Object parseValue(String e) {
        String input = e.trim();
        if (input.startsWith("[") && input.endsWith("]")) {
            return Serialization.readAsList(input, Object.class);
        }
        if (QueryParamsParser.isNumeric(input)) {
            return Long.parseLong(input);
        }
        return input;
    }

    private static boolean isNumeric(String str) {
        String regex2 = "-?[0-9]+";
        return Pattern.matches(regex2, str);
    }
}

