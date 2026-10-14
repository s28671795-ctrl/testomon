/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.Meta;
import org.jooq.MetaProvider;
import org.jooq.impl.CatalogMetaImpl;
import org.jooq.impl.Tools;

public class CatalogMetaProvider
implements MetaProvider {
    private final Configuration configuration;
    private final Catalog[] catalogs;

    public CatalogMetaProvider(Configuration configuration, Catalog ... catalogs) {
        this.configuration = configuration;
        this.catalogs = catalogs;
    }

    public CatalogMetaProvider(Configuration configuration, Collection<? extends Catalog> catalogs) {
        this(configuration, catalogs.toArray(Tools.EMPTY_CATALOG));
    }

    @Override
    public Meta provide() {
        return CatalogMetaImpl.filterCatalogs(this.configuration, this.catalogs);
    }
}

