/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.MessageLite;
import akka.protobufv3.internal.ProtoSyntax;

interface MessageInfo {
    public ProtoSyntax getSyntax();

    public boolean isMessageSetWireFormat();

    public MessageLite getDefaultInstance();
}

