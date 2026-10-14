/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf;

import org.h2.bnf.Rule;

public class RuleHead {
    private final String section;
    private final String topic;
    private Rule rule;

    RuleHead(String string, String string2, Rule rule) {
        this.section = string;
        this.topic = string2;
        this.rule = rule;
    }

    public String getTopic() {
        return this.topic;
    }

    public Rule getRule() {
        return this.rule;
    }

    void setRule(Rule rule) {
        this.rule = rule;
    }

    public String getSection() {
        return this.section;
    }
}

