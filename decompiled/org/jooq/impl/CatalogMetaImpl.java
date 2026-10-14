/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import org.jooq.Catalog;
import org.jooq.Configuration;
import org.jooq.Meta;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.impl.AbstractMeta;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.CatalogImpl;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.Tools;

final class CatalogMetaImpl
extends AbstractMeta {
    private final Catalog[] catalogs;

    private CatalogMetaImpl(Configuration configuration, Catalog[] catalogs) {
        super(configuration);
        this.catalogs = catalogs;
    }

    @Override
    final List<Catalog> getCatalogs0() {
        return Arrays.asList(this.catalogs);
    }

    static final Meta filterCatalogs(Configuration configuration, Catalog[] catalogs) {
        return CatalogMetaImpl.filterCatalogs0(configuration, catalogs, new LinkedHashSet<Catalog>(Arrays.asList(catalogs)));
    }

    static final Meta filterCatalogs(Configuration configuration, Set<Catalog> catalogs) {
        return CatalogMetaImpl.filterCatalogs0(configuration, catalogs.toArray(Tools.EMPTY_CATALOG), catalogs);
    }

    private static final Meta filterCatalogs0(Configuration configuration, Catalog[] array, Set<Catalog> set) {
        return new CatalogMetaImpl(configuration, array).filterCatalogs(set::contains);
    }

    static final Meta filterSchemas(Configuration configuration, Schema[] schemas) {
        return CatalogMetaImpl.filterSchemas(configuration, new LinkedHashSet<Schema>(Arrays.asList(schemas)));
    }

    static final Meta filterSchemas(Configuration configuration, Set<Schema> schemas) {
        LinkedHashMap<Name, Catalog> c = new LinkedHashMap<Name, Catalog>();
        final LinkedHashMap<Name, List> mapping = new LinkedHashMap<Name, List>();
        for (Schema schema : schemas) {
            mapping.computeIfAbsent(AbstractNamed.nameOrDefault(schema.getCatalog()), k -> new ArrayList()).add(schema);
        }
        for (Schema schema : schemas) {
            c.computeIfAbsent(AbstractNamed.nameOrDefault(schema.getCatalog()), k -> new CatalogImpl((Name)k){

                @Override
                public List<Schema> getSchemas() {
                    return (List)mapping.get(this.getQualifiedName());
                }
            });
        }
        return CatalogMetaImpl.filterCatalogs(configuration, new LinkedHashSet<Catalog>(c.values())).filterSchemas(schemas::contains);
    }

    static final Meta filterTables(Configuration configuration, Table<?>[] tables) {
        return CatalogMetaImpl.filterTables(configuration, new LinkedHashSet(Arrays.asList(tables)));
    }

    static final Meta filterTables(Configuration configuration, Set<Table<?>> tables) {
        LinkedHashMap<Name, Schema> s2 = new LinkedHashMap<Name, Schema>();
        final LinkedHashMap<Name, List> mapping = new LinkedHashMap<Name, List>();
        for (Table<?> table : tables) {
            mapping.computeIfAbsent(AbstractNamed.nameOrDefault(table.getCatalog()).append(AbstractNamed.nameOrDefault(table.getSchema())), k -> new ArrayList()).add(table);
        }
        for (Table<?> table : tables) {
            s2.computeIfAbsent(AbstractNamed.nameOrDefault(table.getCatalog()).append(AbstractNamed.nameOrDefault(table.getSchema())), k -> new SchemaImpl((Name)k, table.getCatalog()){

                @Override
                public List<Table<?>> getTables() {
                    return (List)mapping.get(this.getQualifiedName());
                }
            });
        }
        return CatalogMetaImpl.filterSchemas(configuration, new LinkedHashSet<Schema>(s2.values())).filterTables(tables::contains).filterSequences(CatalogMetaImpl.none()).filterDomains(CatalogMetaImpl.none());
    }

    static final <Q extends QueryPart> Predicate<Q> none() {
        return t2 -> false;
    }
}

