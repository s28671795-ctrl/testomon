/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import liquibase.Contexts;
import liquibase.util.ExpressionMatcher;
import liquibase.util.StringUtil;

public class ContextExpression {
    private HashSet<String> contexts = new HashSet();
    private String originalString;

    public ContextExpression(String ... contexts) {
        if (contexts.length == 1) {
            this.parseContextString(contexts[0]);
            this.originalString = contexts[0];
        } else {
            for (String context : contexts) {
                this.parseContextString(context.toLowerCase());
            }
            this.originalString = StringUtil.join(contexts, ",");
        }
    }

    public ContextExpression(String contexts) {
        this.parseContextString(contexts);
        this.originalString = contexts;
    }

    public ContextExpression(Collection<String> contexts) {
        if (contexts != null) {
            for (String context : contexts) {
                this.contexts.add(context.toLowerCase());
            }
            this.originalString = StringUtil.join(contexts, ",");
        }
    }

    private void parseContextString(String contexts) {
        if ((contexts = StringUtil.trimToNull(contexts)) == null) {
            return;
        }
        for (String context : StringUtil.splitAndTrim(contexts, ",")) {
            this.contexts.add(context.toLowerCase());
        }
    }

    public boolean add(String context) {
        return this.contexts.add(context.toLowerCase());
    }

    public Set<String> getContexts() {
        return Collections.unmodifiableSet(this.contexts);
    }

    public String toString() {
        if (this.originalString != null) {
            return this.originalString;
        }
        return "(" + StringUtil.join(new TreeSet<String>(this.contexts), "), (") + ")";
    }

    public boolean matches(Contexts runtimeContexts) {
        if (runtimeContexts == null) {
            runtimeContexts = new Contexts();
        }
        if (this.contexts.isEmpty()) {
            return true;
        }
        for (String expression : this.contexts) {
            if (!this.matches(expression, runtimeContexts)) continue;
            return true;
        }
        return false;
    }

    private boolean matches(String expression, Contexts runtimeContexts) {
        if (runtimeContexts == null) {
            runtimeContexts = new Contexts();
        }
        return ExpressionMatcher.matches(expression, runtimeContexts.getContexts());
    }

    public boolean isEmpty() {
        return this.contexts.isEmpty();
    }

    public static boolean matchesAll(Collection<ContextExpression> expressions, Contexts contexts) {
        if (expressions == null || expressions.isEmpty()) {
            return true;
        }
        for (ContextExpression expression : expressions) {
            if (expression.matches(contexts)) continue;
            return false;
        }
        return true;
    }

    public ContextExpression() {
    }

    public void setContexts(HashSet<String> contexts) {
        this.contexts = contexts;
    }

    public void setOriginalString(String originalString) {
        this.originalString = originalString;
    }

    public String getOriginalString() {
        return this.originalString;
    }
}

