/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.plexus.interpolation.fixed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.codehaus.plexus.interpolation.RecursionInterceptor;
import org.codehaus.plexus.interpolation.SimpleRecursionInterceptor;
import org.codehaus.plexus.interpolation.fixed.FixedStringSearchInterpolator;

public class InterpolationState {
    private final List<String> messages = new ArrayList<String>();
    private final List<Throwable> causes = new ArrayList<Throwable>();
    final Set<String> unresolvable = new HashSet<String>();
    RecursionInterceptor recursionInterceptor = new SimpleRecursionInterceptor();
    FixedStringSearchInterpolator root = null;

    public void addFeedback(String message, Throwable cause) {
        this.messages.add(message);
        this.causes.add(cause);
    }

    public List asList() {
        ArrayList<Object> items = new ArrayList<Object>();
        for (int i = 0; i < this.messages.size(); ++i) {
            Throwable cause;
            String msg = this.messages.get(i);
            if (msg != null) {
                items.add(msg);
            }
            if ((cause = this.causes.get(i)) == null) continue;
            items.add(cause);
        }
        return items.size() > 0 ? items : null;
    }

    public void clear() {
        this.messages.clear();
        this.causes.clear();
        this.unresolvable.clear();
        this.recursionInterceptor.clear();
        this.root = null;
    }

    public void setRecursionInterceptor(RecursionInterceptor recursionInterceptor) {
        this.recursionInterceptor = recursionInterceptor;
    }
}

