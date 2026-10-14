/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.Seq;
import scala.runtime.LambdaDeserialize;
import scala.runtime.MethodHandleConstants;
import scala.runtime.ScalaRunTime$;

public final class LambdaDeserialize$ {
    public static final LambdaDeserialize$ MODULE$ = new LambdaDeserialize$();

    public CallSite bootstrap(MethodHandles.Lookup lookup, String invokedName, MethodType invokedType, MethodHandle ... targetMethods) {
        return this.bootstrap(lookup, invokedName, invokedType, ScalaRunTime$.MODULE$.wrapRefArray(targetMethods));
    }

    public CallSite bootstrap(MethodHandles.Lookup lookup, String invokedName, MethodType invokedType, Seq<MethodHandle> targetMethods) throws Throwable {
        MethodHandle[] targetMethodsArray = (MethodHandle[])((ArraySeq)targetMethods).unsafeArray();
        MethodHandle exact = MethodHandleConstants.LAMBDA_DESERIALIZE_DESERIALIZE_LAMBDA.bindTo(new LambdaDeserialize(lookup, targetMethodsArray)).asType(invokedType);
        return new ConstantCallSite(exact);
    }

    public String nameAndDescriptorKey(String name, String descriptor) {
        return new StringBuilder(0).append(name).append(descriptor).toString();
    }

    private LambdaDeserialize$() {
    }
}

