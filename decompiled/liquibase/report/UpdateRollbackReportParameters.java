/*
 * Decompiled with CFR 0.152.
 */
package liquibase.report;

import java.util.Date;
import liquibase.report.CustomData;
import liquibase.report.OperationInfo;
import liquibase.report.RuntimeInfo;

public interface UpdateRollbackReportParameters {
    public RuntimeInfo getRuntimeInfo();

    public OperationInfo getOperationInfo();

    public CustomData getCustomData();

    public Date getDate();

    public String getChangelogArgValue();

    public String getJdbcUrl();

    public String getCommandTitle();
}

