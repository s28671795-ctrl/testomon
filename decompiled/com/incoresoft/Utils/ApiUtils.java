/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONValidator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import io.javalin.http.Context;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ApiUtils {
    private static final Pattern LONG_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^\\d+\\.?\\d*$");

    public static JSONObject getQueryParamJSON(Context ctx) {
        return ApiUtils.mapToJSON(ctx.queryParamMap());
    }

    public static JSONObject getFormParamJSON(Context ctx) {
        return ApiUtils.mapToJSON(ctx.formParamMap());
    }

    public static <T> T getQueryParamAsClass(Context ctx, Class<T> tClass, boolean useDefaultValues) {
        if (useDefaultValues) {
            return ApiUtils.getQueryParamAsClass(ctx, tClass);
        }
        return JSON.parseObject(ApiUtils.getQueryParamJSON(ctx).toJSONString(), tClass);
    }

    public static <T> T getQueryParamAsClass(Context ctx, Class<T> tClass) {
        return ApiUtils.getJSONAsClass(ApiUtils.getQueryParamJSON(ctx), tClass);
    }

    public static <T> T getJSONAsClass(JSONObject json, Class<T> tClass) {
        for (Field value : tClass.getDeclaredFields()) {
            try {
                Field field2 = tClass.getDeclaredField(value.getName());
                field2.setAccessible(true);
                Optional.of(field2).map(ApiUtils::getJSONFieldName).filter(it -> !json.containsKey(it)).ifPresent(it -> json.put((String)it, ApiUtils.getFieldDefaultValue(field2)));
            }
            catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        return JSON.parseObject(json.toJSONString(), tClass);
    }

    private static <T> String getJSONFieldName(Field field2) {
        return Optional.of(field2).filter(it -> it.isAnnotationPresent(JSONField.class)).map(it -> it.getAnnotation(JSONField.class)).map(JSONField::name).filter(it -> !it.isBlank()).orElse(field2.getName());
    }

    private static Object getFieldDefaultValue(Field field2) {
        return Optional.of(field2).filter(it -> it.isAnnotationPresent(JSONField.class)).map(it -> it.getAnnotation(JSONField.class)).map(JSONField::defaultValue).map(it -> ApiUtils.castToJson(it, field2)).orElse(null);
    }

    private static Object castToJson(String it, Field field2) {
        if (it.startsWith("[") && it.endsWith("]")) {
            return JSON.parseArray(it);
        }
        return TypeUtils.cast((Object)it, field2.getType(), ParserConfig.getGlobalInstance());
    }

    public static Object getDefaultValue(Type paramType) {
        if (paramType == Integer.TYPE) {
            return 0;
        }
        if (paramType == Long.TYPE) {
            return 0L;
        }
        if (paramType == Float.TYPE) {
            return Float.valueOf(0.0f);
        }
        if (paramType == Double.TYPE) {
            return 0.0;
        }
        if (paramType == Boolean.TYPE) {
            return Boolean.FALSE;
        }
        if (paramType == Short.TYPE) {
            return (short)0;
        }
        if (paramType == Byte.TYPE) {
            return (byte)0;
        }
        if (paramType == Character.TYPE) {
            return Character.valueOf('\u0000');
        }
        if (paramType == Optional.class) {
            return Optional.empty();
        }
        if (paramType == OptionalInt.class) {
            return OptionalInt.empty();
        }
        if (paramType == OptionalLong.class) {
            return OptionalLong.empty();
        }
        if (paramType == OptionalDouble.class) {
            return OptionalDouble.empty();
        }
        return null;
    }

    public static <T> T getFormParamAsClass(Context ctx, Class<T> tClass) {
        return JSON.parseObject(ApiUtils.getFormParamJSON(ctx).toJSONString(), tClass);
    }

    private static JSONObject mapToJSON(Map<String, List<String>> map) {
        JSONObject json = new JSONObject();
        map.entrySet().stream().filter(it -> !((List)it.getValue()).isEmpty()).filter(it -> !((String)((List)it.getValue()).get(0)).isBlank()).collect(Collectors.toMap(Map.Entry::getKey, it -> ((String)((List)it.getValue()).get(0)).replace("\r\n", ""))).forEach((key, value) -> {
            JSONValidator validator = JSONValidator.from(value);
            if (validator.validate()) {
                if (validator.getType() == JSONValidator.Type.Object || validator.getType() == JSONValidator.Type.Array) {
                    json.put((String)key, JSON.parse(value));
                } else if (LONG_PATTERN.matcher((CharSequence)value).matches()) {
                    json.put((String)key, (Object)Long.parseLong(value));
                } else if (DOUBLE_PATTERN.matcher((CharSequence)value).matches()) {
                    json.put((String)key, (Object)Double.parseDouble(value));
                } else {
                    json.put((String)key, value);
                }
            } else {
                json.put((String)key, value);
            }
        });
        return json;
    }
}

