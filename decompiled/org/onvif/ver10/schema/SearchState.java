/*
 * Decompiled with CFR 0.152.
 */
package org.onvif.ver10.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="SearchState")
@XmlEnum
public enum SearchState {
    QUEUED("Queued"),
    SEARCHING("Searching"),
    COMPLETED("Completed"),
    UNKNOWN("Unknown");

    private final String value;

    private SearchState(String v) {
        this.value = v;
    }

    public String value() {
        return this.value;
    }

    public static SearchState fromValue(String v) {
        for (SearchState c : SearchState.values()) {
            if (!c.value.equals(v)) continue;
            return c;
        }
        throw new IllegalArgumentException(v);
    }
}

