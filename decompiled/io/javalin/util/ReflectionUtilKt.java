/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u001e\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0014\u0010\u0015\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u0001H\u0000\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\"\u0018\u0010\b\u001a\u00020\u0006*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0007\"\u0018\u0010\t\u001a\u00020\u0006*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\u0007\"\u0018\u0010\n\u001a\u00020\u0006*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u0007\"\u0018\u0010\u000b\u001a\u00020\u0006*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\u0007\"\u0018\u0010\f\u001a\u00020\u0006*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\u0007\"\u001a\u0010\r\u001a\u0004\u0018\u00010\u0001*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u0004\"\u0018\u0010\u000f\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0004\"\u001c\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0012*\u00020\u00028@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0017"}, d2={"implementingClassName", "", "", "getImplementingClassName", "(Ljava/lang/Object;)Ljava/lang/String;", "isClass", "", "(Ljava/lang/Object;)Z", "isJavaAnonymousLambda", "isJavaField", "isKotlinAnonymousLambda", "isKotlinField", "isKotlinMethodReference", "javaFieldName", "getJavaFieldName", "kotlinFieldName", "getKotlinFieldName", "parentClass", "Ljava/lang/Class;", "getParentClass", "(Ljava/lang/Object;)Ljava/lang/Class;", "runMethod", "name", "javalin"})
@SourceDebugExtension(value={"SMAP\nReflectionUtil.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReflectionUtil.kt\nio/javalin/util/ReflectionUtilKt\n+ 2 _Strings.kt\nkotlin/text/StringsKt___StringsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,30:1\n643#2,5:31\n643#2,5:37\n1#3:36\n12904#4,3:42\n12744#4,2:45\n*S KotlinDebug\n*F\n+ 1 ReflectionUtil.kt\nio/javalin/util/ReflectionUtilKt\n*L\n4#1:31,5\n13#1:37,5\n21#1:42,3\n23#1:45,2\n*E\n"})
public final class ReflectionUtilKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String getKotlinFieldName(@NotNull Object $this$kotlinFieldName) {
        String string;
        block1: {
            void var1_1;
            Intrinsics.checkNotNullParameter($this$kotlinFieldName, "<this>");
            String string2 = $this$kotlinFieldName.getClass().toString();
            Intrinsics.checkNotNullExpressionValue(string2, "this.javaClass.toString()");
            String $this$takeWhile$iv = StringsKt.removePrefix(string2, (CharSequence)(ReflectionUtilKt.getParentClass($this$kotlinFieldName).toString() + "$"));
            boolean $i$f$takeWhile = false;
            int n = $this$takeWhile$iv.length();
            for (int index$iv = 0; index$iv < n; ++index$iv) {
                char it = $this$takeWhile$iv.charAt(index$iv);
                boolean bl = false;
                if (it != '$') continue;
                String string3 = $this$takeWhile$iv;
                int n2 = 0;
                String string4 = string3.substring(n2, index$iv);
                string = string4;
                Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                break block1;
            }
            string = var1_1;
        }
        return string;
    }

    @Nullable
    public static final String getJavaFieldName(@NotNull Object $this$javaFieldName) {
        String string;
        Intrinsics.checkNotNullParameter($this$javaFieldName, "<this>");
        try {
            Object object;
            block3: {
                Field[] fieldArray = ReflectionUtilKt.getParentClass($this$javaFieldName).getDeclaredFields();
                Intrinsics.checkNotNullExpressionValue(fieldArray, "parentClass.declaredFields");
                for (Object object2 : (Object[])fieldArray) {
                    Field it = (Field)object2;
                    boolean bl = false;
                    it.setAccessible(true);
                    if (!Intrinsics.areEqual(it.get(it), $this$javaFieldName)) continue;
                    object = object2;
                    break block3;
                }
                object = null;
            }
            Field field2 = (Field)object;
            string = field2 != null ? field2.getName() : null;
        }
        catch (Exception ignored) {
            string = null;
        }
        return string;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final Class<?> getParentClass(@NotNull Object $this$parentClass) {
        String string;
        block1: {
            void var1_1;
            Intrinsics.checkNotNullParameter($this$parentClass, "<this>");
            String string2 = $this$parentClass.getClass().getName();
            Intrinsics.checkNotNullExpressionValue(string2, "this.javaClass.name");
            String $this$takeWhile$iv = string2;
            boolean $i$f$takeWhile = false;
            int n = $this$takeWhile$iv.length();
            for (int index$iv = 0; index$iv < n; ++index$iv) {
                char it = $this$takeWhile$iv.charAt(index$iv);
                boolean bl = false;
                if (it != '$') continue;
                String string3 = $this$takeWhile$iv;
                int n2 = 0;
                String string4 = string3.substring(n2, index$iv);
                string = string4;
                Intrinsics.checkNotNullExpressionValue(string4, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                break block1;
            }
            string = var1_1;
        }
        Class<?> clazz = Class.forName(string, false, $this$parentClass.getClass().getClassLoader());
        Intrinsics.checkNotNullExpressionValue(clazz, "forName(this.javaClass.n\u2026is.javaClass.classLoader)");
        return clazz;
    }

    @Nullable
    public static final String getImplementingClassName(@NotNull Object $this$implementingClassName) {
        Intrinsics.checkNotNullParameter($this$implementingClassName, "<this>");
        return $this$implementingClassName.getClass().getName();
    }

    public static final boolean isClass(@NotNull Object $this$isClass) {
        Intrinsics.checkNotNullParameter($this$isClass, "<this>");
        return $this$isClass instanceof Class;
    }

    public static final boolean isKotlinAnonymousLambda(@NotNull Object $this$isKotlinAnonymousLambda) {
        Intrinsics.checkNotNullParameter($this$isKotlinAnonymousLambda, "<this>");
        return $this$isKotlinAnonymousLambda.getClass().getEnclosingMethod() != null;
    }

    /*
     * WARNING - void declaration
     */
    public static final boolean isKotlinMethodReference(@NotNull Object $this$isKotlinMethodReference) {
        void var3_3;
        Intrinsics.checkNotNullParameter($this$isKotlinMethodReference, "<this>");
        Field[] fieldArray = $this$isKotlinMethodReference.getClass().getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(fieldArray, "this.javaClass.declaredFields");
        Object[] $this$count$iv = fieldArray;
        boolean $i$f$count = false;
        int count$iv = 0;
        for (Object element$iv : $this$count$iv) {
            Field it = (Field)element$iv;
            boolean bl = false;
            if (!(Intrinsics.areEqual(it.getName(), "function") || Intrinsics.areEqual(it.getName(), "$tmp0"))) continue;
            ++count$iv;
        }
        return var3_3 == true;
    }

    public static final boolean isKotlinField(@NotNull Object $this$isKotlinField) {
        boolean bl;
        block1: {
            Intrinsics.checkNotNullParameter($this$isKotlinField, "<this>");
            Field[] fieldArray = $this$isKotlinField.getClass().getFields();
            Intrinsics.checkNotNullExpressionValue(fieldArray, "this.javaClass.fields");
            Object[] $this$any$iv = fieldArray;
            boolean $i$f$any = false;
            for (Object element$iv : $this$any$iv) {
                Field it = (Field)element$iv;
                boolean bl2 = false;
                if (!Intrinsics.areEqual(it.getName(), "INSTANCE")) continue;
                bl = true;
                break block1;
            }
            bl = false;
        }
        return bl;
    }

    public static final boolean isJavaAnonymousLambda(@NotNull Object $this$isJavaAnonymousLambda) {
        Intrinsics.checkNotNullParameter($this$isJavaAnonymousLambda, "<this>");
        return $this$isJavaAnonymousLambda.getClass().isSynthetic();
    }

    public static final boolean isJavaField(@NotNull Object $this$isJavaField) {
        Intrinsics.checkNotNullParameter($this$isJavaField, "<this>");
        return ReflectionUtilKt.getJavaFieldName($this$isJavaField) != null;
    }

    @NotNull
    public static final Object runMethod(@NotNull Object $this$runMethod, @NotNull String name) {
        Method method2;
        Intrinsics.checkNotNullParameter($this$runMethod, "<this>");
        Intrinsics.checkNotNullParameter(name, "name");
        Method $this$runMethod_u24lambda_u245 = method2 = $this$runMethod.getClass().getMethod(name, new Class[0]);
        boolean bl = false;
        $this$runMethod_u24lambda_u245.setAccessible(true);
        Object object = method2.invoke($this$runMethod, new Object[0]);
        Intrinsics.checkNotNullExpressionValue(object, "this.javaClass.getMethod\u2026ble = true }.invoke(this)");
        return object;
    }
}

