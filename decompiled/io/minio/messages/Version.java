/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.Item;
import org.simpleframework.xml.Root;

@Root(name="Version", strict=false)
public class Version
extends Item {
    public Version() {
    }

    public Version(String prefix) {
        super(prefix);
    }
}

