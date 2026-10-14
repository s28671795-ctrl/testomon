/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser;

import liquibase.parser.LiquibaseSqlParser;
import liquibase.plugin.AbstractPluginFactory;

public class SqlParserFactory
extends AbstractPluginFactory<LiquibaseSqlParser> {
    @Override
    protected Class<LiquibaseSqlParser> getPluginClass() {
        return LiquibaseSqlParser.class;
    }

    @Override
    protected int getPriority(LiquibaseSqlParser obj, Object ... args2) {
        return obj.getPriority();
    }

    public LiquibaseSqlParser getSqlParser() {
        return (LiquibaseSqlParser)this.getPlugin(new Object[0]);
    }

    public void unregister(LiquibaseSqlParser liquibaseSqlParser) {
        this.removeInstance(liquibaseSqlParser);
    }

    private SqlParserFactory() {
    }
}

