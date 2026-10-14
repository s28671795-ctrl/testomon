/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.policy.custom;

import org.apache.cxf.Bus;
import org.apache.neethi.Policy;
import org.apache.wss4j.policy.SPConstants;
import org.apache.wss4j.policy.model.AlgorithmSuite;

public interface AlgorithmSuiteLoader {
    public AlgorithmSuite getAlgorithmSuite(Bus var1, SPConstants.SPVersion var2, Policy var3);
}

