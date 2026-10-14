/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import liquibase.Labels;
import liquibase.util.ExpressionMatcher;
import liquibase.util.StringUtil;

public class LabelExpression {
    private final HashSet<String> labels = new LinkedHashSet<String>();
    private String originalString;

    public LabelExpression() {
    }

    public LabelExpression(String ... labels) {
        if (labels.length == 1) {
            this.parseLabelString(labels[0]);
            this.originalString = labels[0];
        } else {
            for (String label : labels) {
                this.parseLabelString(label.toLowerCase());
            }
            this.originalString = StringUtil.join(labels, ",");
        }
    }

    public LabelExpression(String labels) {
        if (labels != null) {
            labels = labels.replace("\\", "");
        }
        this.parseLabelString(labels);
        this.originalString = labels;
    }

    public LabelExpression(Collection<String> labels) {
        if (labels != null) {
            for (String label : labels) {
                this.labels.add(label.toLowerCase());
            }
            this.originalString = StringUtil.join(labels, ",");
        }
    }

    private void parseLabelString(String labels) {
        if ((labels = StringUtil.trimToNull(labels)) == null) {
            return;
        }
        for (String label : StringUtil.splitAndTrim(labels, ",")) {
            this.labels.add(label.toLowerCase());
        }
    }

    public boolean add(String label) {
        return this.labels.add(label.toLowerCase());
    }

    public Set<String> getLabels() {
        return Collections.unmodifiableSet(this.labels);
    }

    public String toString() {
        if (this.originalString != null) {
            return this.originalString;
        }
        return "(" + StringUtil.join(new TreeSet<String>(this.labels), "), (") + ")";
    }

    public boolean matches(Labels runtimeLabels) {
        if (runtimeLabels == null) {
            runtimeLabels = new Labels();
        }
        if (this.labels.isEmpty()) {
            return true;
        }
        for (String expression : this.labels) {
            if (!this.matches(expression, runtimeLabels)) continue;
            return true;
        }
        return false;
    }

    public static boolean matchesAll(Collection<Labels> changesetLabels, LabelExpression labelExpression) {
        if (changesetLabels == null || changesetLabels.isEmpty()) {
            return true;
        }
        for (Labels changesetLabel : changesetLabels) {
            if (labelExpression.matches(changesetLabel)) continue;
            return false;
        }
        return true;
    }

    private boolean matches(String expression, Labels runtimeLabels) {
        if (runtimeLabels == null) {
            runtimeLabels = new Labels();
        }
        return ExpressionMatcher.matches(expression, runtimeLabels.getLabels());
    }

    public boolean isEmpty() {
        return this.labels.isEmpty();
    }

    public String getOriginalString() {
        return this.originalString;
    }
}

