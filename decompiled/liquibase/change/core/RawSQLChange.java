/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.AbstractSQLChange;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.util.StringUtil;

@DatabaseChange(name="sql", description="Allows you to specify raw SQL to execute against the database", priority=1)
public class RawSQLChange
extends AbstractSQLChange {
    private String comment;
    private Boolean rerunnable;

    public RawSQLChange() {
    }

    public RawSQLChange(String sql) {
        this.setSql(sql);
    }

    @Override
    @DatabaseChangeProperty(serializationType=LiquibaseSerializable.SerializationType.DIRECT_VALUE, exampleValue="insert into person (name) values ('Bob')", requiredForDatabase={"all"})
    public String getSql() {
        return super.getSql();
    }

    @DatabaseChangeProperty(serializationType=LiquibaseSerializable.SerializationType.NESTED_OBJECT, description="A brief descriptive inline comment. Not stored in the database", exampleValue="What about Bob?")
    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String getConfirmationMessage() {
        return "Custom SQL executed";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public void customLoadLogic(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        String nestedSql = StringUtil.trimToNull(parsedNode.getValue(String.class));
        if (nestedSql != null) {
            this.setSql(nestedSql);
        }
    }

    public boolean isRerunnable() {
        if (this.rerunnable == null) {
            return false;
        }
        return this.rerunnable;
    }

    public void setRerunnable(Boolean rerunnable) {
        if (rerunnable == null) {
            this.rerunnable = false;
        }
        this.rerunnable = rerunnable;
    }
}

