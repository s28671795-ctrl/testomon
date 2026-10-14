/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.Contents;
import io.minio.messages.Item;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="CommonPrefixes", strict=false)
public class Prefix {
    @Element(name="Prefix")
    private String prefix;

    public Item toItem() {
        return new Contents(this.prefix);
    }
}

