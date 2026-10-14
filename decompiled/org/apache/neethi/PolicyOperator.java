/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi;

import java.util.List;
import org.apache.neethi.PolicyComponent;

public interface PolicyOperator
extends PolicyComponent {
    public void addPolicyComponent(PolicyComponent var1);

    public List<PolicyComponent> getPolicyComponents();

    public boolean isEmpty();
}

