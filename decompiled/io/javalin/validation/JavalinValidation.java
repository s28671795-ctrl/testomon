/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.validation;

import io.javalin.Javalin;
import io.javalin.validation.BaseValidator;
import io.javalin.validation.JavalinValidation;
import io.javalin.validation.MissingConverterException;
import io.javalin.validation.ValidationError;
import io.javalin.validation.ValidationException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007JE\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000f0\u000e0\r2\u001a\u0010\u0010\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00120\u0011\"\u0006\u0012\u0002\b\u00030\u0012H\u0007\u00a2\u0006\u0002\u0010\u0013J6\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\u0007\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000f0\u000e0\r2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\u0014H\u0007J)\u0010\u0015\u001a\u0002H\u0016\"\u0004\b\u0000\u0010\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u00052\b\u0010\u0018\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\u0019J\u001a\u0010\u001a\u001a\u00020\u001b\"\u0004\b\u0000\u0010\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0005J:\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u00062\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00052\u0014\u0010\u001d\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006H\u0007R,\u0010\u0003\u001a \u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"Lio/javalin/validation/JavalinValidation;", "", "()V", "converters", "", "Ljava/lang/Class;", "Lkotlin/Function1;", "", "addValidationExceptionMapper", "", "app", "Lio/javalin/Javalin;", "collectErrors", "", "", "Lio/javalin/validation/ValidationError;", "validators", "", "Lio/javalin/validation/BaseValidator;", "([Lio/javalin/validation/BaseValidator;)Ljava/util/Map;", "", "convertValue", "T", "clazz", "value", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Object;", "hasConverter", "", "register", "converter", "javalin"})
@SourceDebugExtension(value={"SMAP\nJavalinValidation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JavalinValidation.kt\nio/javalin/validation/JavalinValidation\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,59:1\n1360#2:60\n1446#2,5:61\n1179#2,2:66\n1253#2,4:68\n*S KotlinDebug\n*F\n+ 1 JavalinValidation.kt\nio/javalin/validation/JavalinValidation\n*L\n46#1:60\n46#1:61,5\n46#1:66,2\n46#1:68,4\n*E\n"})
public final class JavalinValidation {
    @NotNull
    public static final JavalinValidation INSTANCE = new JavalinValidation();
    @NotNull
    private static final Map<Class<?>, Function1<String, Object>> converters;

    private JavalinValidation() {
    }

    public final <T> T convertValue(@NotNull Class<T> clazz, @Nullable String value) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Function1<String, Object> function1 = converters.get(clazz);
        if (function1 == null) {
            String string = clazz.getName();
            Intrinsics.checkNotNullExpressionValue(string, "clazz.name");
            throw new MissingConverterException(string);
        }
        Function1<String, Object> converter = function1;
        String string = value;
        return (T)(string != null ? converter.invoke(string) : null);
    }

    public final <T> boolean hasConverter(@NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return converters.get(clazz) != null;
    }

    @JvmStatic
    @Nullable
    public static final Function1<String, Object> register(@NotNull Class<?> clazz, @NotNull Function1<? super String, ? extends Object> converter) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(converter, "converter");
        return converters.put(clazz, converter);
    }

    @JvmStatic
    @NotNull
    public static final Map<String, List<ValidationError<? extends Object>>> collectErrors(BaseValidator<?> ... validators) {
        Intrinsics.checkNotNullParameter(validators, "validators");
        return JavalinValidation.collectErrors((Iterable)ArraysKt.toList(validators));
    }

    /*
     * WARNING - void declaration
     */
    @JvmStatic
    @NotNull
    public static final Map<String, List<ValidationError<? extends Object>>> collectErrors(@NotNull Iterable<? extends BaseValidator<?>> validators) {
        void $this$associateTo$iv$iv;
        void $this$flatMapTo$iv$iv;
        Intrinsics.checkNotNullParameter(validators, "validators");
        Iterable<? extends BaseValidator<?>> $this$flatMap$iv = validators;
        boolean $i$f$flatMap = false;
        Iterable<? extends BaseValidator<?>> iterable = $this$flatMap$iv;
        Iterable destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            BaseValidator it = (BaseValidator)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = it.errors().entrySet();
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
        }
        Iterable $this$associate$iv = (List)destination$iv$iv;
        boolean $i$f$associate = false;
        int capacity$iv = RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$associate$iv, 10)), 16);
        destination$iv$iv = $this$associate$iv;
        Map destination$iv$iv2 = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateTo = false;
        for (Object element$iv$iv : $this$associateTo$iv$iv) {
            Map map = destination$iv$iv2;
            Map.Entry it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            Pair pair = TuplesKt.to(it.getKey(), it.getValue());
            map.put(pair.getFirst(), pair.getSecond());
        }
        return destination$iv$iv2;
    }

    @JvmStatic
    public static final void addValidationExceptionMapper(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        app.exception(ValidationException.class, addValidationExceptionMapper.1.INSTANCE);
    }

    static {
        Pair[] pairArray = new Pair[]{TuplesKt.to(Boolean.class, converters.1.INSTANCE), TuplesKt.to(Double.class, converters.2.INSTANCE), TuplesKt.to(Float.class, converters.3.INSTANCE), TuplesKt.to(Integer.class, converters.4.INSTANCE), TuplesKt.to(Long.class, converters.5.INSTANCE), TuplesKt.to(String.class, converters.6.INSTANCE), TuplesKt.to(Boolean.TYPE, converters.7.INSTANCE), TuplesKt.to(Double.TYPE, converters.8.INSTANCE), TuplesKt.to(Float.TYPE, converters.9.INSTANCE), TuplesKt.to(Integer.TYPE, converters.10.INSTANCE), TuplesKt.to(Long.TYPE, converters.11.INSTANCE), TuplesKt.to(String.class, converters.12.INSTANCE)};
        converters = MapsKt.mutableMapOf(pairArray);
    }
}

