/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf;

import java.util.HashMap;
import org.h2.bnf.Bnf;
import org.h2.bnf.BnfVisitor;
import org.h2.bnf.Rule;
import org.h2.bnf.RuleHead;
import org.h2.bnf.Sentence;

public class RuleRepeat
implements Rule {
    private final Rule rule;
    private final boolean comma;

    public RuleRepeat(Rule rule, boolean bl) {
        this.rule = rule;
        this.comma = bl;
    }

    @Override
    public void accept(BnfVisitor bnfVisitor) {
        bnfVisitor.visitRuleRepeat(this.comma, this.rule);
    }

    @Override
    public void setLinks(HashMap<String, RuleHead> hashMap) {
    }

    @Override
    public boolean autoComplete(Sentence sentence) {
        sentence.stopIfRequired();
        while (this.rule.autoComplete(sentence)) {
        }
        String string = sentence.getQuery();
        while (Bnf.startWithSpace(string)) {
            string = string.substring(1);
        }
        sentence.setQuery(string);
        return true;
    }

    public String toString() {
        return this.comma ? ", ..." : " ...";
    }
}

