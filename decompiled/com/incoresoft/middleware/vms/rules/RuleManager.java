/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.rules;

import com.incoresoft.middleware.vms.rules.Alarm;
import com.incoresoft.middleware.vms.rules.RegisterStatus;
import com.incoresoft.middleware.vms.rules.Rule;
import com.incoresoft.middleware.vms.rules.RuleType;
import java.util.Collection;

public interface RuleManager {
    public RegisterStatus registerRuleType(RuleType var1);

    public void unregisterRuleType(RuleType var1);

    public Collection<Rule> getRulesOfType(RuleType var1);

    default public void triggerRule(Rule rule) {
        this.triggerRule(rule, null);
    }

    public void triggerRule(Rule var1, Alarm var2);
}

