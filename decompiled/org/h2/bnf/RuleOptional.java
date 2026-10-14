/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf;

import java.util.HashMap;
import org.h2.bnf.BnfVisitor;
import org.h2.bnf.Rule;
import org.h2.bnf.RuleHead;
import org.h2.bnf.RuleList;
import org.h2.bnf.Sentence;

public class RuleOptional
implements Rule {
    private final Rule rule;
    private boolean mapSet;

    public RuleOptional(Rule rule) {
        this.rule = rule;
    }

    @Override
    public void accept(BnfVisitor bnfVisitor) {
        if (this.rule instanceof RuleList) {
            RuleList ruleList = (RuleList)this.rule;
            if (ruleList.or) {
                bnfVisitor.visitRuleOptional(ruleList.list);
                return;
            }
        }
        bnfVisitor.visitRuleOptional(this.rule);
    }

    @Override
    public void setLinks(HashMap<String, RuleHead> hashMap) {
        if (!this.mapSet) {
            this.rule.setLinks(hashMap);
            this.mapSet = true;
        }
    }

    @Override
    public boolean autoComplete(Sentence sentence) {
        sentence.stopIfRequired();
        this.rule.autoComplete(sentence);
        return true;
    }

    public String toString() {
        return '[' + this.rule.toString() + ']';
    }
}

