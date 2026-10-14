/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.generated;

import com.incoresoft.vezha.generated.DefaultSchema;
import java.util.Arrays;
import java.util.List;
import org.jooq.Schema;
import org.jooq.impl.CatalogImpl;

public class DefaultCatalog
extends CatalogImpl {
    private static final long serialVersionUID = 1L;
    public static final DefaultCatalog DEFAULT_CATALOG = new DefaultCatalog();
    public final DefaultSchema DEFAULT_SCHEMA = DefaultSchema.DEFAULT_SCHEMA;
    private static final String REQUIRE_RUNTIME_JOOQ_VERSION = "3.18";

    private DefaultCatalog() {
        super("");
    }

    @Override
    public final List<Schema> getSchemas() {
        return Arrays.asList(DefaultSchema.DEFAULT_SCHEMA);
    }
}

