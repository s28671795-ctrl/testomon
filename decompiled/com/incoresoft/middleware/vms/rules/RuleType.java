/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.rules;

import com.incoresoft.middleware.vms.rules.RuleMessage;
import com.incoresoft.middleware.vms.rules.RuleMessagePostProcessor;
import com.incoresoft.middleware.vms.rules.RuleOptions;
import com.incoresoft.middleware.vms.rules.RuleSource;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RuleType {
    public String getTypeId();

    public String getTypeLocaleKey();

    public String getPluginName();

    public Class<? extends RuleOptions> getRuleDataClass();

    public Class<? extends RuleMessage> getRuleMessageClass();

    default public Set<UUID> getDynamicSources(RuleOptions ruleOptions) {
        return Set.of();
    }

    default public Collection<RuleSource> getSources() {
        return List.of();
    }

    default public boolean requiresSources(RuleOptions ruleOptions) {
        return true;
    }

    default public RuleMessagePostProcessor getRuleMessagePostProcessor() {
        return (ruleMessage, user) -> ruleMessage;
    }
}

