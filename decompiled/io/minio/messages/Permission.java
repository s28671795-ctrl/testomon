/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Root;

@Root(name="Permission")
public enum Permission {
    FULL_CONTROL,
    WRITE,
    WRITE_ACP,
    READ,
    READ_ACP;

}

