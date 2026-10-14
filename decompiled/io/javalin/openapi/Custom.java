/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Repeatable;
import kotlin.jvm.internal.RepeatableContainer;

@kotlin.annotation.Target(allowedTargets={AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD})
@kotlin.annotation.Retention(value=AnnotationRetention.SOURCE)
@Repeatable
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.FIELD, ElementType.METHOD})
@java.lang.annotation.Repeatable(value=Container.class)
@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0087\u0002\u0018\u00002\u00020\u0001B\u0010\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0005R\u000f\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lio/javalin/openapi/Custom;", "", "name", "", "value", "()Ljava/lang/String;", "openapi-specification"})
public @interface Custom {
    public String name();

    public String value();

    @kotlin.annotation.Target(allowedTargets={AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD})
    @kotlin.annotation.Retention(value=AnnotationRetention.SOURCE)
    @RepeatableContainer
    @Retention(value=RetentionPolicy.SOURCE)
    @Target(value={ElementType.FIELD, ElementType.METHOD})
    @Metadata(mv={1, 7, 1}, k=3, xi=48)
    public static @interface Container {
        public Custom[] value();
    }
}

