/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.core;

import liquibase.change.ColumnConfig;
import liquibase.change.core.LoadDataChange;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;

public class LoadDataColumnConfig
extends ColumnConfig {
    private Integer index;
    private String header;
    private Boolean allowUpdate;
    private LoadDataChange.LOAD_DATA_TYPE loadType;

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Boolean getAllowUpdate() {
        return this.allowUpdate;
    }

    public void setAllowUpdate(Boolean getAllowUpdate) {
        this.allowUpdate = getAllowUpdate;
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        super.load(parsedNode, resourceAccessor);
        this.index = parsedNode.getChildValue((String)null, "index", Integer.class);
        this.header = parsedNode.getChildValue((String)null, "header", String.class);
        this.allowUpdate = parsedNode.getChildValue((String)null, "allowUpdate", Boolean.class);
    }

    public ColumnConfig setType(LoadDataChange.LOAD_DATA_TYPE value) {
        super.setType(value.toString());
        this.loadType = value;
        return this;
    }

    public LoadDataChange.LOAD_DATA_TYPE getTypeEnum() {
        String type = this.getType();
        if (type == null) {
            return null;
        }
        if (this.loadType == null) {
            try {
                this.loadType = LoadDataChange.LOAD_DATA_TYPE.valueOf(type.toUpperCase());
            }
            catch (IllegalArgumentException e) {
                return LoadDataChange.LOAD_DATA_TYPE.UNKNOWN;
            }
        }
        return this.loadType;
    }
}

