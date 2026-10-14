/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.Message;
import akka.protobufv3.internal.RpcController;
import akka.protobufv3.internal.ServiceException;

public interface BlockingService {
    public Descriptors.ServiceDescriptor getDescriptorForType();

    public Message callBlockingMethod(Descriptors.MethodDescriptor var1, RpcController var2, Message var3) throws ServiceException;

    public Message getRequestPrototype(Descriptors.MethodDescriptor var1);

    public Message getResponsePrototype(Descriptors.MethodDescriptor var1);
}

