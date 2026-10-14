/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Meta;
import org.jooq.MetaProvider;
import org.jooq.Source;
import org.jooq.impl.InformationSchemaMetaImpl;
import org.jooq.util.jaxb.tools.MiniJAXB;
import org.jooq.util.xml.jaxb.InformationSchema;

public class InformationSchemaMetaProvider
implements MetaProvider {
    private final Configuration configuration;
    private final InformationSchema schema;
    private final Source[] sources;

    public InformationSchemaMetaProvider(Configuration configuration, Source ... sources) {
        this.configuration = configuration;
        this.schema = null;
        this.sources = sources;
    }

    public InformationSchemaMetaProvider(Configuration configuration, InformationSchema schema) {
        this.configuration = configuration;
        this.schema = schema;
        this.sources = null;
    }

    @Override
    public Meta provide() {
        InformationSchema s2 = this.schema;
        if (s2 == null) {
            s2 = new InformationSchema();
            for (Source source2 : this.sources) {
                MiniJAXB.append(s2, MiniJAXB.unmarshal(source2.reader(), InformationSchema.class));
            }
        }
        return new InformationSchemaMetaImpl(this.configuration, s2);
    }
}

