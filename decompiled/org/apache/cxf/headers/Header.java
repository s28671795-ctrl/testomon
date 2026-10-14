/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.headers;

import javax.xml.namespace.QName;
import org.apache.cxf.databinding.DataBinding;

public class Header {
    public static final String HEADER_LIST = Header.class.getName() + ".list";
    private DataBinding dataBinding;
    private QName name;
    private Object object;
    private Direction direction = Direction.DIRECTION_OUT;

    public Header(QName q, Object o) {
        this(q, o, null);
    }

    public Header(QName q, Object o, DataBinding b) {
        this.object = o;
        this.name = q;
        this.dataBinding = b;
    }

    public DataBinding getDataBinding() {
        return this.dataBinding;
    }

    public void setDataBinding(DataBinding dataBinding) {
        this.dataBinding = dataBinding;
    }

    public QName getName() {
        return this.name;
    }

    public void setName(QName name) {
        this.name = name;
    }

    public Object getObject() {
        return this.object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setDirection(Direction hdrDirection) {
        this.direction = hdrDirection;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public static enum Direction {
        DIRECTION_IN,
        DIRECTION_OUT,
        DIRECTION_INOUT;

    }
}

