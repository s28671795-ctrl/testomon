/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils.transform;

import javax.xml.namespace.QName;

class ElementProperty {
    private QName name;
    private String text;
    private boolean child;

    ElementProperty(QName name, String text, boolean child) {
        this.name = name;
        this.text = text;
        this.child = child;
    }

    public QName getName() {
        return this.name;
    }

    public String getText() {
        return this.text;
    }

    public boolean isChild() {
        return this.child;
    }
}

