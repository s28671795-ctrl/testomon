/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="NoncurrentVersionExpiration")
public class NoncurrentVersionExpiration {
    @Element(name="NoncurrentDays")
    private int noncurrentDays;

    public NoncurrentVersionExpiration(@Element(name="NoncurrentDays", required=false) int noncurrentDays) {
        this.noncurrentDays = noncurrentDays;
    }

    public int noncurrentDays() {
        return this.noncurrentDays;
    }
}

