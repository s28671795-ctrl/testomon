/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils.transform;

import javax.xml.namespace.QName;

class ParsingEvent {
    private int event;
    private QName name;
    private String value;

    ParsingEvent(int event, QName name, String value) {
        this.event = event;
        this.name = name;
        this.value = value;
    }

    public String toString() {
        return "Event(" + this.event + ", " + this.name + ", " + this.value + ')';
    }

    public int getEvent() {
        return this.event;
    }

    public QName getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}

