/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import io.minio.messages.EventType;
import io.minio.messages.Filter;
import io.minio.messages.FilterRule;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public abstract class NotificationCommonConfiguration {
    @Element(name="Id", required=false)
    private String id;
    @ElementList(name="Event", inline=true)
    private List<EventType> events;
    @Element(name="Filter", required=false)
    private Filter filter;

    public String id() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<EventType> events() {
        return Collections.unmodifiableList(this.events == null ? new LinkedList() : this.events);
    }

    public void setEvents(List<EventType> events) {
        this.events = Collections.unmodifiableList(events);
    }

    public void setPrefixRule(String value) throws IllegalArgumentException {
        if (this.filter == null) {
            this.filter = new Filter();
        }
        this.filter.setPrefixRule(value);
    }

    public void setSuffixRule(String value) throws IllegalArgumentException {
        if (this.filter == null) {
            this.filter = new Filter();
        }
        this.filter.setSuffixRule(value);
    }

    public List<FilterRule> filterRuleList() {
        return Collections.unmodifiableList(this.filter == null ? new LinkedList() : this.filter.filterRuleList());
    }
}

