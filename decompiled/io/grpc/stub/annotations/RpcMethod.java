/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.stub.annotations;

import io.grpc.MethodDescriptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.METHOD})
public @interface RpcMethod {
    public String fullMethodName();

    public Class<?> requestType();

    public Class<?> responseType();

    public MethodDescriptor.MethodType methodType();
}

