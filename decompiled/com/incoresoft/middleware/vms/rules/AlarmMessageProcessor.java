/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.rules;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.vms.rules.RuleMessage;

public interface AlarmMessageProcessor {
    public RuleMessage processAlarmMessage(RuleMessage var1, User var2);
}

