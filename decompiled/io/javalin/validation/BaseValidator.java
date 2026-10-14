/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.validation;

import io.javalin.json.JsonMapper;
import io.javalin.util.ConcurrencyUtilKt;
import io.javalin.util.JavalinLogger;
import io.javalin.validation.BaseValidator;
import io.javalin.validation.BodyValidator;
import io.javalin.validation.JavalinValidation;
import io.javalin.validation.NullableValidator;
import io.javalin.validation.Rule;
import io.javalin.validation.StringSource;
import io.javalin.validation.ValidationError;
import io.javalin.validation.Validator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B3\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\u0002\u0010\nB'\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\b\u0010\u000b\u001a\u0004\u0018\u00018\u0000\u0012\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\r\u00a2\u0006\u0002\u0010\u000eJF\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0007\u001a\u00020\u00042 \u0010&\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020(0'j\n\u0012\u0006\u0012\u0004\u0018\u00018\u0000`)2\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000\u0012H\u0004J@\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0007\u001a\u00020\u00042 \u0010&\u001a\u001c\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\u0004\u0012\u00020(0'j\n\u0012\u0006\u0012\u0004\u0018\u00018\u0000`)2\u0006\u0010*\u001a\u00020\u0004H\u0004J\u001e\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0004\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00120\u00110\u0010J\u000f\u0010+\u001a\u0004\u0018\u00018\u0000H\u0016\u00a2\u0006\u0002\u0010!J?\u0010,\u001a\u0004\u0018\u00018\u00002.\u0010-\u001a*\u0012\u001c\u0012\u001a\u0012\u0004\u0012\u00020\u0004\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00120\u00110\u0010\u0012\b\u0012\u00060.j\u0002`/0'H\u0016\u00a2\u0006\u0002\u00100R3\u0010\u000f\u001a\u001a\u0012\u0004\u0012\u00020\u0004\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00120\u00110\u00108BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R \u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001b0\u001aX\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u001c\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\rX\u0084\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u001e\u0010\u000b\u001a\u0004\u0018\u00018\u0000X\u0084\u000e\u00a2\u0006\u0010\n\u0002\u0010$\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#\u00a8\u00061"}, d2={"Lio/javalin/validation/BaseValidator;", "T", "", "stringValue", "", "clazz", "Ljava/lang/Class;", "fieldName", "jsonMapper", "Lio/javalin/json/JsonMapper;", "(Ljava/lang/String;Ljava/lang/Class;Ljava/lang/String;Lio/javalin/json/JsonMapper;)V", "typedValue", "stringSource", "Lio/javalin/validation/StringSource;", "(Ljava/lang/String;Ljava/lang/Object;Lio/javalin/validation/StringSource;)V", "errors", "", "", "Lio/javalin/validation/ValidationError;", "getErrors", "()Ljava/util/Map;", "errors$delegate", "Lkotlin/Lazy;", "getFieldName", "()Ljava/lang/String;", "rules", "", "Lio/javalin/validation/Rule;", "getRules$javalin", "()Ljava/util/List;", "getStringSource", "()Lio/javalin/validation/StringSource;", "getTypedValue", "()Ljava/lang/Object;", "setTypedValue", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "addRule", "check", "Lkotlin/Function1;", "", "Lio/javalin/validation/Check;", "error", "get", "getOrThrow", "exceptionFunction", "Ljava/lang/Exception;", "Lkotlin/Exception;", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "javalin"})
public class BaseValidator<T> {
    @NotNull
    private final String fieldName;
    @Nullable
    private T typedValue;
    @Nullable
    private final StringSource<T> stringSource;
    @NotNull
    private final List<Rule<T>> rules;
    @NotNull
    private final Lazy errors$delegate;

    public BaseValidator(@NotNull String fieldName, @Nullable T typedValue, @Nullable StringSource<T> stringSource) {
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        this.fieldName = fieldName;
        this.typedValue = typedValue;
        this.stringSource = stringSource;
        this.rules = new ArrayList();
        this.errors$delegate = ConcurrencyUtilKt.javalinLazy$default(null, new Function0<Map<String, ? extends List<? extends ValidationError<T>>>>(this){
            final /* synthetic */ BaseValidator<T> this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Map<String, List<ValidationError<T>>> invoke() {
                void $this$mapValuesTo$iv$iv;
                void $this$forEach$iv;
                Object it;
                Iterator<T> $this$filterTo$iv$iv;
                Iterable $this$filter$iv;
                if (this.this$0.getStringSource() != null) {
                    if (this.this$0 instanceof BodyValidator) {
                        try {
                            JsonMapper jsonMapper = this.this$0.getStringSource().getJsonMapper();
                            Intrinsics.checkNotNull(jsonMapper);
                            String string = this.this$0.getStringSource().getStringValue();
                            Intrinsics.checkNotNull(string);
                            this.this$0.setTypedValue(jsonMapper.fromJsonString(string, this.this$0.getStringSource().getClazz()));
                        }
                        catch (Exception e) {
                            JavalinLogger.info("Couldn't deserialize body to " + this.this$0.getStringSource().getClazz().getSimpleName(), e);
                            return MapsKt.mapOf(TuplesKt.to("REQUEST_BODY", CollectionsKt.listOf(new ValidationError<T>("DESERIALIZATION_FAILED", null, this.this$0.getStringSource().getStringValue(), 2, null))));
                        }
                    }
                    if (this.this$0 instanceof NullableValidator || this.this$0 instanceof Validator) {
                        try {
                            this.this$0.setTypedValue(JavalinValidation.INSTANCE.convertValue(this.this$0.getStringSource().getClazz(), this.this$0.getStringSource().getStringValue()));
                        }
                        catch (Exception e) {
                            JavalinLogger.info$default("Parameter '" + this.this$0.getFieldName() + "' with value '" + this.this$0.getStringSource().getStringValue() + "' is not a valid " + this.this$0.getStringSource().getClazz().getSimpleName(), null, 2, null);
                            return MapsKt.mapOf(TuplesKt.to(this.this$0.getFieldName(), CollectionsKt.listOf(new ValidationError<T>("TYPE_CONVERSION_FAILED", null, this.this$0.getStringSource().getStringValue(), 2, null))));
                        }
                        if (!(this.this$0 instanceof NullableValidator) && this.this$0.getTypedValue() == null) {
                            return MapsKt.mapOf(TuplesKt.to(this.this$0.getFieldName(), CollectionsKt.listOf(new ValidationError<T>("NULLCHECK_FAILED", null, this.this$0.getStringSource().getStringValue(), 2, null))));
                        }
                    }
                }
                Map errors2 = new LinkedHashMap<K, V>();
                Iterable iterable = this.this$0.getRules$javalin();
                BaseValidator<T> baseValidator = this.this$0;
                boolean $i$f$filter = false;
                void var5_9 = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$filterTo = false;
                Iterator<T> iterator2 = $this$filterTo$iv$iv.iterator();
                while (iterator2.hasNext()) {
                    T element$iv$iv = iterator2.next();
                    it = (Rule)element$iv$iv;
                    boolean bl2 = false;
                    if (!(((Rule)it).getCheck().invoke(baseValidator.getTypedValue()) == false)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filter$iv = (List)destination$iv$iv;
                baseValidator = this.this$0;
                boolean $i$f$forEach22 = false;
                for (T element$iv : $this$forEach$iv) {
                    Object object;
                    Rule failedRule = (Rule)element$iv;
                    boolean bl = false;
                    errors2.computeIfAbsent(failedRule.getFieldName(), arg_0 -> errors.2.invoke$lambda$3$lambda$1(errors.2.1.INSTANCE, arg_0));
                    V v = errors2.get(failedRule.getFieldName());
                    Intrinsics.checkNotNull(v);
                    it = object = failedRule.getError();
                    List bl2 = (List)v;
                    boolean bl3 = false;
                    ((ValidationError)it).setValue(baseValidator.getTypedValue());
                    bl2.add(object);
                }
                Map $this$mapValues$iv = errors2;
                boolean $i$f$mapValues = false;
                Map $i$f$forEach22 = $this$mapValues$iv;
                Map destination$iv$iv2 = new LinkedHashMap<K, V>(MapsKt.mapCapacity($this$mapValues$iv.size()));
                boolean $i$f$mapValuesTo = false;
                Iterable $this$associateByTo$iv$iv$iv = $this$mapValuesTo$iv$iv.entrySet();
                boolean $i$f$associateByTo = false;
                for (E element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
                    void it2;
                    void it$iv$iv;
                    Map.Entry bl2 = (Map.Entry)element$iv$iv$iv;
                    Map map = destination$iv$iv2;
                    boolean bl = false;
                    Map.Entry entry = (Map.Entry)element$iv$iv$iv;
                    K k = it$iv$iv.getKey();
                    Map map2 = map;
                    boolean bl4 = false;
                    List<T> list = CollectionsKt.toList((Iterable)it2.getValue());
                    map2.put(k, list);
                }
                return MapsKt.toMap(destination$iv$iv2);
            }

            private static final List invoke$lambda$3$lambda$1(Function1 $tmp0, Object p0) {
                Intrinsics.checkNotNullParameter($tmp0, "$tmp0");
                return (List)$tmp0.invoke(p0);
            }
        }, 1, null);
    }

    @NotNull
    public final String getFieldName() {
        return this.fieldName;
    }

    @Nullable
    protected final T getTypedValue() {
        return this.typedValue;
    }

    protected final void setTypedValue(@Nullable T t2) {
        this.typedValue = t2;
    }

    @Nullable
    protected final StringSource<T> getStringSource() {
        return this.stringSource;
    }

    @NotNull
    public final List<Rule<T>> getRules$javalin() {
        return this.rules;
    }

    public BaseValidator(@Nullable String stringValue, @NotNull Class<T> clazz, @NotNull String fieldName, @Nullable JsonMapper jsonMapper) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        this(fieldName, null, new StringSource<T>(stringValue, clazz, jsonMapper));
    }

    public /* synthetic */ BaseValidator(String string, Class clazz, String string2, JsonMapper jsonMapper, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            jsonMapper = null;
        }
        this(string, clazz, string2, jsonMapper);
    }

    private final Map<String, List<ValidationError<T>>> getErrors() {
        Lazy lazy = this.errors$delegate;
        return (Map)lazy.getValue();
    }

    @NotNull
    protected final BaseValidator<T> addRule(@NotNull String fieldName, @NotNull Function1<? super T, Boolean> check2, @NotNull String error) {
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        this.rules.add(new Rule<T>(fieldName, check2, new ValidationError(error, null, null, 6, null)));
        return this;
    }

    @NotNull
    protected final BaseValidator<T> addRule(@NotNull String fieldName, @NotNull Function1<? super T, Boolean> check2, @NotNull ValidationError<T> error) {
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        Intrinsics.checkNotNullParameter(check2, "check");
        Intrinsics.checkNotNullParameter(error, "error");
        this.rules.add(new Rule<T>(fieldName, check2, error));
        return this;
    }

    @Nullable
    public T get() {
        return this.getOrThrow(get.1.INSTANCE);
    }

    @Nullable
    public T getOrThrow(@NotNull Function1<? super Map<String, ? extends List<ValidationError<Object>>>, ? extends Exception> exceptionFunction) {
        Intrinsics.checkNotNullParameter(exceptionFunction, "exceptionFunction");
        if (!this.getErrors().isEmpty()) {
            Map<String, List<ValidationError<T>>> map = this.getErrors();
            Intrinsics.checkNotNull(map, "null cannot be cast to non-null type kotlin.collections.Map<kotlin.String, kotlin.collections.List<io.javalin.validation.ValidationError<kotlin.Any>>>");
            throw (Throwable)exceptionFunction.invoke(map);
        }
        return this.typedValue;
    }

    @NotNull
    public final Map<String, List<ValidationError<T>>> errors() {
        return this.getErrors();
    }
}

