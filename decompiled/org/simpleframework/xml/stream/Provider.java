/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.io.InputStream;
import java.io.Reader;
import org.simpleframework.xml.stream.EventReader;

public interface Provider {
    public EventReader provide(InputStream var1) throws Exception;

    public EventReader provide(Reader var1) throws Exception;
}

