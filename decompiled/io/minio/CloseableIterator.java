/*
 * Decompiled with CFR 0.152.
 */
package io.minio;

import java.io.Closeable;
import java.util.Iterator;

public interface CloseableIterator<T>
extends Iterator<T>,
Closeable {
}

