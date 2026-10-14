/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.rules;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.vms.rules.RuleMessage;

public interface RuleMessagePostProcessor {
    public RuleMessage processRuleMessage(RuleMessage var1, User var2);
}

