/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;

public class ModifyChangeSets {
    private final String runWith;
    private final String runWithSpool;

    public ModifyChangeSets(ParsedNode node) throws ParsedNodeException {
        this.runWith = (String)node.getChildValue(null, "runWith");
        this.runWithSpool = (String)node.getChildValue(null, "runWithSpoolFile");
    }

    public ModifyChangeSets(String runWith, String runWithSpool) {
        this.runWith = runWith;
        this.runWithSpool = runWithSpool;
    }

    public String getRunWith() {
        return this.runWith;
    }

    public String getRunWithSpool() {
        return this.runWithSpool;
    }
}

