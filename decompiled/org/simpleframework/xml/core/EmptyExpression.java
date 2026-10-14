/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.Style;

class EmptyExpression
implements Expression {
    private final List<String> list = new LinkedList<String>();
    private final Style style;

    public EmptyExpression(Format format) {
        this.style = format.getStyle();
    }

    @Override
    public Iterator<String> iterator() {
        return this.list.iterator();
    }

    @Override
    public int getIndex() {
        return 0;
    }

    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public String getFirst() {
        return null;
    }

    @Override
    public String getLast() {
        return null;
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public String getElement(String name) {
        return this.style.getElement(name);
    }

    @Override
    public String getAttribute(String name) {
        return this.style.getAttribute(name);
    }

    @Override
    public Expression getPath(int from) {
        return null;
    }

    @Override
    public Expression getPath(int from, int trim) {
        return null;
    }

    @Override
    public boolean isAttribute() {
        return false;
    }

    @Override
    public boolean isPath() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}

