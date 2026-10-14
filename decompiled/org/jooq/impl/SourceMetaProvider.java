/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Meta;
import org.jooq.MetaProvider;
import org.jooq.SQLDialect;
import org.jooq.Source;
import org.jooq.impl.InformationSchemaMetaProvider;
import org.jooq.impl.InterpreterMetaProvider;
import org.jooq.impl.TranslatingMetaProvider;
import org.jooq.tools.StringUtils;

final class SourceMetaProvider
implements MetaProvider {
    private final Configuration configuration;
    private final Source[] sources;

    SourceMetaProvider(Configuration configuration, Source ... sources) {
        this.configuration = configuration;
        this.sources = sources;
    }

    @Override
    public final Meta provide() {
        if (this.sources.length > 0) {
            String s2 = this.sources[0].readString();
            this.sources[0] = Source.of(s2);
            if (s2.startsWith("<?xml") || s2.startsWith("<information_schema") || s2.startsWith("<!--")) {
                return new InformationSchemaMetaProvider(this.configuration, this.sources).provide();
            }
        }
        SQLDialect dialect = this.configuration.settings().getInterpreterDialect();
        switch (StringUtils.defaultIfNull(dialect, SQLDialect.DEFAULT).family()) {
            case DEFAULT: {
                return new InterpreterMetaProvider(this.configuration, this.sources).provide();
            }
            case DERBY: 
            case H2: 
            case HSQLDB: 
            case SQLITE: {
                return new TranslatingMetaProvider(this.configuration, this.sources).provide();
            }
        }
        throw new UnsupportedOperationException("Interpreter dialect not yet supported: " + String.valueOf((Object)dialect));
    }
}

