/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

interface Attribute {
    public String getName();

    public String getValue();

    public String getReference();

    public String getPrefix();

    public Object getSource();

    public boolean isReserved();
}

