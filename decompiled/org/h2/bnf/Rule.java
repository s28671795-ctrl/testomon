/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf;

import java.util.HashMap;
import org.h2.bnf.BnfVisitor;
import org.h2.bnf.RuleHead;
import org.h2.bnf.Sentence;

public interface Rule {
    public void setLinks(HashMap<String, RuleHead> var1);

    public boolean autoComplete(Sentence var1);

    public void accept(BnfVisitor var1);
}

