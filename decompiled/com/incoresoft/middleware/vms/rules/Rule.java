/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.rules;

import com.incoresoft.middleware.model.Model;
import com.incoresoft.middleware.vms.rules.RuleOptions;
import com.incoresoft.middleware.vms.rules.RuleType;

public interface Rule
extends Model {
    public String getName();

    public RuleType getType();

    public RuleOptions getOptions();
}

