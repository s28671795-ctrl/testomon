/*
 * Decompiled with CFR 0.152.
 */
package akka.protobufv3.internal;

import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.MessageOrBuilder;
import java.util.List;

public interface FieldMaskOrBuilder
extends MessageOrBuilder {
    public List<String> getPathsList();

    public int getPathsCount();

    public String getPaths(int var1);

    public ByteString getPathsBytes(int var1);
}

