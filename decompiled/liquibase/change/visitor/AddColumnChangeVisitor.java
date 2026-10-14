/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.visitor;

import java.util.HashSet;
import java.util.Set;
import liquibase.change.visitor.AbstractChangeVisitor;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.util.StringUtil;

public class AddColumnChangeVisitor
extends AbstractChangeVisitor {
    private String change;
    private Set<String> dbms;
    private String remove;

    @Override
    public String getName() {
        return "addColumn";
    }

    @Override
    public String getChange() {
        return this.change;
    }

    @Override
    public Set<String> getDbms() {
        return this.dbms;
    }

    @Override
    public String getSerializedObjectName() {
        return this.getName();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public void load(ParsedNode node, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        this.change = node.getChildValue((String)null, "change", String.class);
        this.remove = node.getChildValue((String)null, "remove", String.class);
        String dbmsString = StringUtil.trimToNull(node.getChildValue((String)null, "dbms", String.class));
        this.dbms = new HashSet<String>();
        if (dbmsString != null) {
            this.dbms.addAll(StringUtil.splitAndTrim(dbmsString, ","));
        }
    }

    public String getRemove() {
        return this.remove;
    }
}

