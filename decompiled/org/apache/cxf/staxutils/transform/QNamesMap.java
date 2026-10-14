/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils.transform;

import javax.xml.namespace.QName;

class QNamesMap {
    private QName[] keys;
    private QName[] values;
    private int index;

    QNamesMap(int size) {
        this.keys = new QName[size];
        this.values = new QName[size];
    }

    public void put(QName key, QName value) {
        this.keys[this.index] = key;
        this.values[this.index] = value;
        ++this.index;
    }

    public QName get(QName key) {
        for (int i = 0; i < this.keys.length; ++i) {
            if (!this.keys[i].getNamespaceURI().equals(key.getNamespaceURI())) continue;
            if (this.keys[i].getLocalPart().equals(key.getLocalPart())) {
                return this.values[i];
            }
            if (!"*".equals(this.keys[i].getLocalPart())) continue;
            return new QName(this.values[i].getNamespaceURI(), key.getLocalPart());
        }
        return null;
    }

    public int size() {
        return this.index;
    }
}

