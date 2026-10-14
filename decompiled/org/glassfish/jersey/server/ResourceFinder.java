/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server;

import java.io.InputStream;
import java.util.Iterator;

public interface ResourceFinder
extends Iterator<String>,
AutoCloseable {
    public InputStream open();

    @Override
    public void close();

    public void reset();

    @Override
    public void remove();
}

