/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sql.visitor;

import java.util.Set;
import liquibase.ContextExpression;
import liquibase.Labels;
import liquibase.change.CheckSum;
import liquibase.database.Database;
import liquibase.serializer.LiquibaseSerializable;

public interface SqlVisitor
extends LiquibaseSerializable {
    public String modifySql(String var1, Database var2);

    public String getName();

    public Set<String> getApplicableDbms();

    public void setApplicableDbms(Set<String> var1);

    public void setApplyToRollback(boolean var1);

    public boolean isApplyToRollback();

    public ContextExpression getContextFilter();

    public void setContextFilter(ContextExpression var1);

    public Labels getLabels();

    public void setLabels(Labels var1);

    public CheckSum generateCheckSum();
}

