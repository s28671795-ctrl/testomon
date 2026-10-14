/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import liquibase.change.ColumnConfig;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.structure.core.Column;

public class AddColumnConfig
extends ColumnConfig {
    private String afterColumn;
    private String beforeColumn;
    private Integer position;

    public AddColumnConfig(Column columnSnapshot) {
        super(columnSnapshot);
    }

    public AddColumnConfig() {
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        super.load(parsedNode, resourceAccessor);
        this.beforeColumn = parsedNode.getChildValue((String)null, "beforeColumn", String.class);
        this.afterColumn = parsedNode.getChildValue((String)null, "afterColumn", String.class);
        this.position = parsedNode.getChildValue((String)null, "position", Integer.class);
    }

    public String getAfterColumn() {
        return this.afterColumn;
    }

    public String getBeforeColumn() {
        return this.beforeColumn;
    }

    public Integer getPosition() {
        return this.position;
    }

    public void setAfterColumn(String afterColumn) {
        this.afterColumn = afterColumn;
    }

    public void setBeforeColumn(String beforeColumn) {
        this.beforeColumn = beforeColumn;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}

