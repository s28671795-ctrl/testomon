/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import javax.xml.namespace.QName;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.headers.Header;

public class SoapHeader
extends Header {
    private boolean mustUnderstand;
    private String actor;

    public SoapHeader(QName q, Object o) {
        super(q, o);
    }

    public SoapHeader(QName q, Object o, DataBinding b) {
        super(q, o, b);
    }

    public SoapHeader(QName q, Object o, DataBinding b, boolean mustUnderstand, String actor) {
        super(q, o, b);
        this.mustUnderstand = mustUnderstand;
        this.actor = actor;
    }

    public SoapHeader(QName q, Object o, DataBinding b, boolean mustUnderstand) {
        super(q, o, b);
        this.mustUnderstand = mustUnderstand;
    }

    public String getActor() {
        return this.actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public boolean isMustUnderstand() {
        return this.mustUnderstand;
    }

    public void setMustUnderstand(boolean mustUnderstand) {
        this.mustUnderstand = mustUnderstand;
    }
}

