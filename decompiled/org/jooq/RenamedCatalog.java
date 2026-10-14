/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.List;
import org.jooq.Catalog;
import org.jooq.Schema;
import org.jooq.impl.CatalogImpl;

final class RenamedCatalog
extends CatalogImpl {
    private final Catalog delegate;

    RenamedCatalog(Catalog delegate, String rename) {
        super(rename, delegate.getComment());
        this.delegate = delegate;
    }

    @Override
    public final List<Schema> getSchemas() {
        return this.delegate.getSchemas();
    }
}

