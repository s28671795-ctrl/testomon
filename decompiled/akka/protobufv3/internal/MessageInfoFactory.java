/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.MessageInfo;

interface MessageInfoFactory {
    public boolean isSupported(Class<?> var1);

    public MessageInfo messageInfoFor(Class<?> var1);
}

