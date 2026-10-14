/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import java.util.List;

public interface ProtocolStringList
extends List<String> {
    public List<ByteString> asByteStringList();
}

