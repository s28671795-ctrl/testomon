/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.FilterRule;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="Filter", strict=false)
public class Filter {
    @ElementList(name="S3Key")
    private List<FilterRule> filterRuleList;

    private void setRule(String name, String value) throws IllegalArgumentException {
        if (value.length() > 1024) {
            throw new IllegalArgumentException("value '" + value + "' is more than 1024 long");
        }
        if (this.filterRuleList == null) {
            this.filterRuleList = new LinkedList<FilterRule>();
        }
        for (FilterRule rule : this.filterRuleList) {
            if (!rule.name().equals(name)) continue;
            this.filterRuleList.remove(rule);
        }
        this.filterRuleList.add(new FilterRule(name, value));
    }

    public void setPrefixRule(String value) throws IllegalArgumentException {
        this.setRule("prefix", value);
    }

    public void setSuffixRule(String value) throws IllegalArgumentException {
        this.setRule("suffix", value);
    }

    public List<FilterRule> filterRuleList() {
        return Collections.unmodifiableList(this.filterRuleList == null ? new LinkedList() : this.filterRuleList);
    }
}

