/*
 * Decompiled with CFR 0.152.
 */
package liquibase.parser;

import liquibase.changelog.ChangeSet;
import liquibase.plugin.Plugin;
import liquibase.util.StringClauses;

public interface LiquibaseSqlParser
extends Plugin {
    public StringClauses parse(String var1);

    public StringClauses parse(String var1, boolean var2, boolean var3);

    default public StringClauses parse(String sqlBlock, boolean preserveWhitespace, boolean preserveComments, ChangeSet changeSet) {
        return this.parse(sqlBlock, preserveWhitespace, preserveComments);
    }

    public int getPriority();
}

