/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Initiator", strict=false)
public class Initiator {
    @Element(name="ID", required=false)
    private String id;
    @Element(name="DisplayName", required=false)
    private String displayName;

    public String id() {
        return this.id;
    }

    public String displayName() {
        return this.displayName;
    }
}

