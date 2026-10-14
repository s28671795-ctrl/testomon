/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.staxex.util;

import javax.xml.stream.Location;

public final class DummyLocation
implements Location {
    public static final Location INSTANCE = new DummyLocation();

    private DummyLocation() {
    }

    public int getCharacterOffset() {
        return -1;
    }

    public int getColumnNumber() {
        return -1;
    }

    public int getLineNumber() {
        return -1;
    }

    public String getPublicId() {
        return null;
    }

    public String getSystemId() {
        return null;
    }
}

