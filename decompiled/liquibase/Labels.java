/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import liquibase.util.StringUtil;

public class Labels {
    private final Set<String> labels = new LinkedHashSet<String>();

    public Labels() {
    }

    public Labels(String ... labels) {
        if (labels.length == 1) {
            this.parseLabelString(labels[0]);
        } else {
            for (String label : labels) {
                this.labels.add(label.toLowerCase());
            }
        }
    }

    public Labels(String labels) {
        this.parseLabelString(labels);
    }

    private void parseLabelString(String labels) {
        if ((labels = StringUtil.trimToNull(labels)) == null) {
            return;
        }
        for (String label : StringUtil.splitAndTrim(labels, ",")) {
            this.labels.add(label.toLowerCase());
        }
    }

    public Labels(Collection<String> labels) {
        if (labels != null) {
            for (String label : labels) {
                this.labels.add(label.toLowerCase());
            }
        }
    }

    public boolean add(String label) {
        return this.labels.add(label.toLowerCase());
    }

    public boolean remove(String label) {
        return this.labels.remove(label.toLowerCase());
    }

    public String toString() {
        return StringUtil.join(new LinkedHashSet<String>(this.labels), ",");
    }

    public boolean isEmpty() {
        return this.labels.isEmpty();
    }

    public Set<String> getLabels() {
        return Collections.unmodifiableSet(this.labels);
    }
}

