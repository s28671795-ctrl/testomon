/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.handler;

import org.ldaptive.LdapUtils;
import org.ldaptive.SearchResponse;
import org.ldaptive.handler.AbstractEntryHandler;
import org.ldaptive.handler.SearchResultHandler;

public class SortResultHandler
extends AbstractEntryHandler<SearchResponse>
implements SearchResultHandler {
    private static final int HASH_CODE_SEED = 853;

    @Override
    public SearchResponse apply(SearchResponse response) {
        return SearchResponse.sort(response);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof SortResultHandler;
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(853, new Object[0]);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "]";
    }
}

