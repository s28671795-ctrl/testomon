/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import liquibase.util.StringUtil;

public class Contexts {
    private final HashSet<String> contextStore = new HashSet();

    public Contexts() {
    }

    public Contexts(String ... contexts) {
        if (contexts.length == 1) {
            this.parseContextString(contexts[0]);
        } else {
            for (String context : contexts) {
                this.contextStore.add(context.toLowerCase());
            }
        }
    }

    public Contexts(String contexts) {
        if (contexts != null) {
            contexts = contexts.replace("\\", "");
        }
        this.parseContextString(contexts);
    }

    public Contexts(Collection<String> contexts) {
        if (contexts != null) {
            for (String context : contexts) {
                this.contextStore.add(context.toLowerCase());
            }
        }
    }

    private void parseContextString(String contexts) {
        if ((contexts = StringUtil.trimToNull(contexts)) == null) {
            return;
        }
        for (String context : StringUtil.splitAndTrim(contexts, ",")) {
            this.contextStore.add(context.toLowerCase());
        }
    }

    public boolean add(String context) {
        return this.contextStore.add(context.toLowerCase());
    }

    public boolean remove(String context) {
        return this.contextStore.remove(context.toLowerCase());
    }

    public String toString() {
        return StringUtil.join(new TreeSet<String>(this.contextStore), ",");
    }

    public boolean isEmpty() {
        return this.contextStore.isEmpty();
    }

    public Set<String> getContexts() {
        return Collections.unmodifiableSet(this.contextStore);
    }
}

