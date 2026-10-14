/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="AbortIncompleteMultipartUpload")
public class AbortIncompleteMultipartUpload {
    @Element(name="DaysAfterInitiation")
    private int daysAfterInitiation;

    public AbortIncompleteMultipartUpload(@Element(name="DaysAfterInitiation") int daysAfterInitiation) {
        this.daysAfterInitiation = daysAfterInitiation;
    }

    public int daysAfterInitiation() {
        return this.daysAfterInitiation;
    }
}

