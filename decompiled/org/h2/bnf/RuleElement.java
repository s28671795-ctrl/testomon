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
import org.h2.util.StringUtils;

public class RuleElement
implements Rule {
    private final boolean keyword;
    private final String name;
    private Rule link;
    private final int type;

    public RuleElement(String string, String string2) {
        this.name = string;
        this.keyword = string.length() == 1 || string.equals(StringUtils.toUpperEnglish(string));
        string2 = StringUtils.toLowerEnglish(string2);
        this.type = string2.startsWith("function") ? 2 : 1;
    }

    @Override
    public void accept(BnfVisitor bnfVisitor) {
        bnfVisitor.visitRuleElement(this.keyword, this.name, this.link);
    }

    @Override
    public void setLinks(HashMap<String, RuleHead> hashMap) {
        if (this.link != null) {
            this.link.setLinks(hashMap);
        }
        if (this.keyword) {
            return;
        }
        String string = Bnf.getRuleMapKey(this.name);
        for (int i = 0; i < string.length(); ++i) {
            String string2 = string.substring(i);
            RuleHead ruleHead = hashMap.get(string2);
            if (ruleHead == null) continue;
            this.link = ruleHead.getRule();
            return;
        }
        throw new AssertionError((Object)("Unknown " + this.name + "/" + string));
    }

    @Override
    public boolean autoComplete(Sentence sentence) {
        sentence.stopIfRequired();
        if (this.keyword) {
            String string = sentence.getQuery();
            String string2 = string.trim();
            String string3 = sentence.getQueryUpper().trim();
            if (string3.startsWith(this.name)) {
                string = string.substring(this.name.length());
                while (!"_".equals(this.name) && Bnf.startWithSpace(string)) {
                    string = string.substring(1);
                }
                sentence.setQuery(string);
                return true;
            }
            if ((string2.length() == 0 || this.name.startsWith(string3)) && string2.length() < this.name.length()) {
                sentence.add(this.name, this.name.substring(string2.length()), this.type);
            }
            return false;
        }
        return this.link.autoComplete(sentence);
    }

    public String toString() {
        return this.name;
    }
}

