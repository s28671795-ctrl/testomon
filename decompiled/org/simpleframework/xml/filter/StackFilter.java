/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.filter;

import java.util.Stack;
import org.simpleframework.xml.filter.Filter;

public class StackFilter
implements Filter {
    private Stack<Filter> stack = new Stack();

    public void push(Filter filter2) {
        this.stack.push(filter2);
    }

    @Override
    public String replace(String text) {
        int i = this.stack.size();
        while (--i >= 0) {
            String value = ((Filter)this.stack.get(i)).replace(text);
            if (value == null) continue;
            return value;
        }
        return null;
    }
}

