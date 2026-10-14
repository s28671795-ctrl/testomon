/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.xjc.runtime;

import javax.xml.bind.JAXBElement;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class JAXBToStringStyleImpl
extends ToStringStyle {
    private static final long serialVersionUID = -584903238590491314L;

    JAXBToStringStyleImpl() {
    }

    JAXBToStringStyleImpl(boolean multiLine) {
        if (multiLine) {
            this.setContentStart("[");
            this.setFieldSeparator(System.lineSeparator() + "  ");
            this.setFieldSeparatorAtStart(true);
            this.setContentEnd(System.lineSeparator() + "]");
        } else {
            this.setUseClassName(false);
            this.setUseIdentityHashCode(false);
            this.setUseFieldNames(false);
            this.setContentStart("");
            this.setContentEnd("");
        }
    }

    @Override
    protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
        if (value instanceof JAXBElement) {
            buffer.append(ToStringBuilder.reflectionToString(value, this));
        } else {
            buffer.append(value);
        }
    }
}

