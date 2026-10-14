/*
 * Decompiled with CFR 0.152.
 */
package liquibase.statement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import liquibase.statement.AbstractSqlStatement;
import liquibase.statement.CallableSqlStatement;

public class StoredProcedureStatement
extends AbstractSqlStatement
implements CallableSqlStatement {
    private final String procedureName;
    private final List<String> parameters = new ArrayList<String>();
    private final List<Integer> types = new ArrayList<Integer>();

    public StoredProcedureStatement(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getProcedureName() {
        return this.procedureName;
    }

    public List<String> getParameters() {
        return Collections.unmodifiableList(this.parameters);
    }

    public void addParameter(String param2, int type) {
        this.parameters.add(param2);
        this.types.add(type);
    }

    public int getParameterType(String param2) {
        for (int i = 0; i < this.parameters.size(); ++i) {
            if (!this.parameters.get(i).equals(param2)) continue;
            return this.types.get(i);
        }
        return -1;
    }
}

