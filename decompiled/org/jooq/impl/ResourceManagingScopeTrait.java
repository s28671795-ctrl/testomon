/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.Closeable;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLXML;
import org.jooq.ResourceManagingScope;
import org.jooq.impl.DefaultExecuteContext;

interface ResourceManagingScopeTrait
extends ResourceManagingScope {
    @Override
    default public Array autoFree(Array array) {
        DefaultExecuteContext.register(array);
        return array;
    }

    @Override
    default public Blob autoFree(Blob blob) {
        DefaultExecuteContext.register(blob);
        return blob;
    }

    @Override
    default public Clob autoFree(Clob clob) {
        DefaultExecuteContext.register(clob);
        return clob;
    }

    @Override
    default public SQLXML autoFree(SQLXML xml) {
        DefaultExecuteContext.register(xml);
        return xml;
    }

    @Override
    default public <R extends Closeable> R autoClose(R closeable) {
        DefaultExecuteContext.register(closeable);
        return closeable;
    }

    @Override
    default public <R extends AutoCloseable> R autoClose(R closeable) {
        DefaultExecuteContext.register(closeable);
        return closeable;
    }
}

