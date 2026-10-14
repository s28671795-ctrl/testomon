/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.Item;
import org.simpleframework.xml.Root;

@Root(name="DeleteMarker", strict=false)
public class DeleteMarker
extends Item {
    public DeleteMarker() {
    }

    public DeleteMarker(String prefix) {
        super(prefix);
    }
}

