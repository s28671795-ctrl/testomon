/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.io;

import java.io.IOException;
import org.ldaptive.SearchResponse;

public interface SearchResultReader {
    public SearchResponse read() throws IOException;
}

