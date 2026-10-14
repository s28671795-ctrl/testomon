/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changeset;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.ModifyChangeSets;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.plugin.Plugin;

public interface ChangeSetService
extends Plugin {
    public int getPriority();

    public ChangeSet createChangeSet(String var1, String var2, boolean var3, boolean var4, String var5, String var6, String var7, String var8, String var9, boolean var10, ObjectQuotingStrategy var11, DatabaseChangeLog var12);

    public ChangeSet createChangeSet(String var1, String var2, boolean var3, boolean var4, String var5, String var6, String var7, DatabaseChangeLog var8);

    public ChangeSet createChangeSet(DatabaseChangeLog var1);

    public ModifyChangeSets createModifyChangeSets(ParsedNode var1) throws ParsedNodeException;

    default public ModifyChangeSets createModifyChangeSets(String runWith, String runWithSpool) {
        return new ModifyChangeSets(runWith, runWithSpool);
    }

    public void modifyChangeSets(ChangeSet var1, ModifyChangeSets var2);

    default public String getOverrideDelimiter(String endDelimiter) {
        return endDelimiter;
    }

    default public String getEndDelimiter(ChangeSet changeSet) {
        return null;
    }
}

