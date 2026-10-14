/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf;

import java.util.ArrayList;
import java.util.HashMap;
import org.h2.bnf.BnfVisitor;
import org.h2.bnf.Rule;
import org.h2.bnf.RuleHead;
import org.h2.bnf.Sentence;
import org.h2.util.Utils;

public class RuleList
implements Rule {
    final boolean or;
    final ArrayList<Rule> list = Utils.newSmallArrayList();
    private boolean mapSet;

    public RuleList(Rule rule, Rule rule2, boolean bl) {
        if (rule instanceof RuleList && ((RuleList)rule).or == bl) {
            this.list.addAll(((RuleList)rule).list);
        } else {
            this.list.add(rule);
        }
        if (rule2 instanceof RuleList && ((RuleList)rule2).or == bl) {
            this.list.addAll(((RuleList)rule2).list);
        } else {
            this.list.add(rule2);
        }
        this.or = bl;
    }

    @Override
    public void accept(BnfVisitor bnfVisitor) {
        bnfVisitor.visitRuleList(this.or, this.list);
    }

    @Override
    public void setLinks(HashMap<String, RuleHead> hashMap) {
        if (!this.mapSet) {
            for (Rule rule : this.list) {
                rule.setLinks(hashMap);
            }
            this.mapSet = true;
        }
    }

    @Override
    public boolean autoComplete(Sentence sentence) {
        sentence.stopIfRequired();
        String string = sentence.getQuery();
        if (this.or) {
            for (Rule rule : this.list) {
                sentence.setQuery(string);
                if (!rule.autoComplete(sentence)) continue;
                return true;
            }
            return false;
        }
        for (Rule rule : this.list) {
            if (rule.autoComplete(sentence)) continue;
            sentence.setQuery(string);
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int n = this.list.size();
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                if (this.or) {
                    stringBuilder.append(" | ");
                } else {
                    stringBuilder.append(' ');
                }
            }
            stringBuilder.append(this.list.get(i).toString());
        }
        return stringBuilder.toString();
    }
}

