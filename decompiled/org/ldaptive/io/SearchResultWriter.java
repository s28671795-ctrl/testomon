/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.io;

import java.io.IOException;
import org.ldaptive.SearchResponse;

public interface SearchResultWriter {
    public void write(SearchResponse var1) throws IOException;
}

