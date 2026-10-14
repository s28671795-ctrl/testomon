/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.Message;
import akka.protobufv3.internal.RpcCallback;
import akka.protobufv3.internal.RpcController;

public interface Service {
    public Descriptors.ServiceDescriptor getDescriptorForType();

    public void callMethod(Descriptors.MethodDescriptor var1, RpcController var2, Message var3, RpcCallback<Message> var4);

    public Message getRequestPrototype(Descriptors.MethodDescriptor var1);

    public Message getResponsePrototype(Descriptors.MethodDescriptor var1);
}

