/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Connection;
import java.util.Locale;
import java.util.regex.Pattern;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Meta;
import org.jooq.MetaProvider;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Source;
import org.jooq.VisitListener;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultMetaProvider;
import org.jooq.impl.Snapshot;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;

final class TranslatingMetaProvider
implements MetaProvider {
    private static final JooqLogger log = JooqLogger.getLogger(TranslatingMetaProvider.class);
    private static final Pattern P_NAME = Pattern.compile("(?s:.*?\"([^\"]*)\".*)");
    private final Configuration configuration;
    private final Source[] scripts;

    public TranslatingMetaProvider(Configuration configuration, Source ... scripts) {
        this.configuration = Tools.configuration(configuration);
        this.scripts = scripts;
    }

    @Override
    public Meta provide() {
        try (DDLDatabaseInitializer initializer = new DDLDatabaseInitializer();){
            for (Source script : this.scripts) {
                initializer.loadScript(script);
            }
            Snapshot snapshot = new Snapshot(new DefaultMetaProvider(this.configuration.derive().set(initializer.connection).set(this.configuration.settings().getInterpreterDialect())).provide());
            return snapshot;
        }
    }

    final class DDLDatabaseInitializer
    implements AutoCloseable {
        private Connection connection;
        private DSLContext ctx;

        private DDLDatabaseInitializer() {
            try {
                Settings settings = TranslatingMetaProvider.this.configuration.settings();
                this.connection = TranslatingMetaProvider.this.configuration.interpreterConnectionProvider().acquire();
                this.ctx = DSL.using(this.connection, settings.getInterpreterDialect(), settings);
                this.ctx.data("org.jooq.ddl.ignore-storage-clauses", true);
                this.ctx.data("org.jooq.ddl.parse-for-ddldatabase", true);
                RenderNameCase nameCase = settings.getRenderNameCase();
                Locale locale = SettingsTools.interpreterLocale(this.ctx.settings());
                if (nameCase != null && nameCase != RenderNameCase.AS_IS) {
                    this.ctx.configuration().set(VisitListener.onVisitStart(c -> {
                        QueryPart patt4880$temp = c.queryPart();
                        if (patt4880$temp instanceof Name) {
                            Name n = (Name)patt4880$temp;
                            Name[] parts = n.parts();
                            boolean changed = false;
                            for (int i = 0; i < parts.length; ++i) {
                                Name replacement = parts[i];
                                switch (nameCase) {
                                    case LOWER_IF_UNQUOTED: {
                                        if (parts[i].quoted() == Name.Quoted.QUOTED) break;
                                    }
                                    case LOWER: {
                                        replacement = DSL.quotedName(parts[i].first().toLowerCase(locale));
                                        break;
                                    }
                                    case UPPER_IF_UNQUOTED: {
                                        if (parts[i].quoted() == Name.Quoted.QUOTED) break;
                                    }
                                    case UPPER: {
                                        replacement = DSL.quotedName(parts[i].first().toUpperCase(locale));
                                        break;
                                    }
                                }
                                if (replacement.equals(parts[i])) continue;
                                parts[i] = replacement;
                                changed = true;
                            }
                            if (changed) {
                                c.queryPart(DSL.name(parts));
                            }
                        }
                    }));
                }
            }
            catch (Exception e) {
                throw new DataAccessException("Error while exporting schema", e);
            }
        }

        /*
         * Exception decompiling
         */
        private final void loadScript(Source source) {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        @Override
        public void close() {
            TranslatingMetaProvider.this.configuration.interpreterConnectionProvider().release(this.connection);
        }
    }
}

