/*
 * Decompiled with CFR 0.152.
 */
package org.h2.bnf;

import java.util.ArrayList;
import org.h2.bnf.Rule;

public interface BnfVisitor {
    public void visitRuleElement(boolean var1, String var2, Rule var3);

    public void visitRuleRepeat(boolean var1, Rule var2);

    public void visitRuleFixed(int var1);

    public void visitRuleList(boolean var1, ArrayList<Rule> var2);

    public void visitRuleOptional(Rule var1);

    public void visitRuleOptional(ArrayList<Rule> var1);

    public void visitRuleExtension(Rule var1, boolean var2);
}

