/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import liquibase.statement.AbstractSqlStatement;
import liquibase.util.StringUtil;

public class RawParameterizedSqlStatement
extends AbstractSqlStatement {
    private final String sql;
    private final List<Object> parameters = new ArrayList<Object>();

    public RawParameterizedSqlStatement(String sql, Object ... parameters) {
        this.sql = sql;
        if (parameters != null) {
            this.parameters.addAll(Arrays.asList(parameters));
        }
    }

    public String getSql() {
        return this.sql;
    }

    public List<Object> getParameters() {
        return this.parameters;
    }

    public RawParameterizedSqlStatement addParameter(Object parameter) {
        this.parameters.add(parameter);
        return this;
    }

    public String toString() {
        return this.sql + " with " + StringUtil.join(this.parameters, ",", (StringUtil.StringUtilFormatter)new StringUtil.ToStringFormatter());
    }
}

