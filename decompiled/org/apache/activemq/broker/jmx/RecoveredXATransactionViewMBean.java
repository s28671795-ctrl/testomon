/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.jmx.MBeanInfo;

public interface RecoveredXATransactionViewMBean {
    @MBeanInfo(value="The raw xid formatId.")
    public int getFormatId();

    @MBeanInfo(value="The raw xid branchQualifier.")
    public byte[] getBranchQualifier();

    @MBeanInfo(value="The raw xid globalTransactionId.")
    public byte[] getGlobalTransactionId();

    @MBeanInfo(value="force heusistic commit of this transaction")
    public void heuristicCommit() throws Exception;

    @MBeanInfo(value="force heusistic rollback of this transaction")
    public void heuristicRollback() throws Exception;
}

