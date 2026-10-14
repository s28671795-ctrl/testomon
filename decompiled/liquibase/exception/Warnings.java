/*
 * Decompiled with CFR 0.152.
 */
package liquibase.exception;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class Warnings {
    private final LinkedHashSet<String> messages = new LinkedHashSet();

    public Warnings addWarning(String warning) {
        this.messages.add(warning);
        return this;
    }

    public Warnings addAll(Warnings warnings) {
        if (warnings != null) {
            this.messages.addAll(warnings.getMessages());
        }
        return this;
    }

    public List<String> getMessages() {
        return new ArrayList<String>(this.messages);
    }

    public boolean hasWarnings() {
        return !this.messages.isEmpty();
    }
}

