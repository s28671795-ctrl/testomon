/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.Item;
import org.simpleframework.xml.Root;

@Root(name="Contents", strict=false)
public class Contents
extends Item {
    public Contents() {
    }

    public Contents(String prefix) {
        super(prefix);
    }
}

