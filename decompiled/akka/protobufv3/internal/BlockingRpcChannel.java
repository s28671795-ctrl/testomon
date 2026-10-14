/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.Descriptors;
import akka.protobufv3.internal.Message;
import akka.protobufv3.internal.RpcController;
import akka.protobufv3.internal.ServiceException;

public interface BlockingRpcChannel {
    public Message callBlockingMethod(Descriptors.MethodDescriptor var1, RpcController var2, Message var3, Message var4) throws ServiceException;
}

