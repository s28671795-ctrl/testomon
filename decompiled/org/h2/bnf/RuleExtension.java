/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf;

import java.util.HashMap;
import org.h2.bnf.BnfVisitor;
import org.h2.bnf.Rule;
import org.h2.bnf.RuleHead;
import org.h2.bnf.Sentence;

public class RuleExtension
implements Rule {
    private final Rule rule;
    private final boolean compatibility;
    private boolean mapSet;

    public RuleExtension(Rule rule, boolean bl) {
        this.rule = rule;
        this.compatibility = bl;
    }

    @Override
    public void accept(BnfVisitor bnfVisitor) {
        bnfVisitor.visitRuleExtension(this.rule, this.compatibility);
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
        return (this.compatibility ? "@c@ " : "@h2@ ") + this.rule.toString();
    }
}

