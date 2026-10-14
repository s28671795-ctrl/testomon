/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.phase;

import java.util.Collection;
import java.util.Set;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.common.util.SortedArraySet;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.PhaseInterceptor;

public abstract class AbstractPhaseInterceptor<T extends Message>
implements PhaseInterceptor<T> {
    private final String id;
    private final String phase;
    private final Set<String> before = new SortedArraySet<String>();
    private final Set<String> after = new SortedArraySet<String>();

    public AbstractPhaseInterceptor(String phase) {
        this(null, phase, false);
    }

    public AbstractPhaseInterceptor(String i, String p) {
        this(i, p, false);
    }

    public AbstractPhaseInterceptor(String phase, boolean uniqueId) {
        this(null, phase, uniqueId);
    }

    public AbstractPhaseInterceptor(String i, String p, boolean uniqueId) {
        if (i == null) {
            i = this.getClass().getName();
        }
        if (uniqueId) {
            i = i + System.identityHashCode(this);
        }
        this.id = i;
        this.phase = p;
    }

    public void setBefore(Collection<String> i) {
        this.before.clear();
        this.before.addAll(i);
    }

    public void setAfter(Collection<String> i) {
        this.after.clear();
        this.after.addAll(i);
    }

    public void addBefore(Collection<String> i) {
        this.before.addAll(i);
    }

    public void addAfter(Collection<String> i) {
        this.after.addAll(i);
    }

    public void addBefore(String i) {
        this.before.add(i);
    }

    public void addAfter(String i) {
        this.after.add(i);
    }

    @Override
    public final Set<String> getAfter() {
        return this.after;
    }

    @Override
    public final Set<String> getBefore() {
        return this.before;
    }

    @Override
    public Collection<PhaseInterceptor<? extends Message>> getAdditionalInterceptors() {
        return null;
    }

    @Override
    public final String getId() {
        return this.id;
    }

    @Override
    public final String getPhase() {
        return this.phase;
    }

    @Override
    public void handleFault(T message) {
    }

    public boolean isGET(T message) {
        String method2 = (String)message.get("org.apache.cxf.request.method");
        return "GET".equals(method2) && message.getContent(XMLStreamReader.class) == null;
    }

    protected boolean isRequestor(T message) {
        return MessageUtils.isRequestor(message);
    }
}

