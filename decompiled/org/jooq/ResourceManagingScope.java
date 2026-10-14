/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Closeable;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLXML;
import org.jooq.Scope;

public interface ResourceManagingScope
extends Scope {
    public Array autoFree(Array var1);

    public Blob autoFree(Blob var1);

    public Clob autoFree(Clob var1);

    public SQLXML autoFree(SQLXML var1);

    public <R extends Closeable> R autoClose(R var1);

    public <R extends AutoCloseable> R autoClose(R var1);
}

