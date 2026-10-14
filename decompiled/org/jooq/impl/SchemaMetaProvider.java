/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Configuration;
import org.jooq.Meta;
import org.jooq.MetaProvider;
import org.jooq.Schema;
import org.jooq.impl.CatalogMetaImpl;
import org.jooq.impl.Tools;

public class SchemaMetaProvider
implements MetaProvider {
    private final Configuration configuration;
    private final Schema[] schemas;

    public SchemaMetaProvider(Configuration configuration, Schema ... schemas) {
        this.configuration = configuration;
        this.schemas = schemas;
    }

    public SchemaMetaProvider(Configuration configuration, Collection<? extends Schema> schemas) {
        this(configuration, schemas.toArray(Tools.EMTPY_SCHEMA));
    }

    @Override
    public Meta provide() {
        return CatalogMetaImpl.filterSchemas(this.configuration, this.schemas);
    }
}

