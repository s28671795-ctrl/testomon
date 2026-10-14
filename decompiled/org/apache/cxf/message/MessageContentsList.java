/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.MessagePartInfo;

public class MessageContentsList
extends ArrayList<Object> {
    public static final Object REMOVED_MARKER = new Object();
    private static final long serialVersionUID = -5780720048950696258L;

    public MessageContentsList() {
        super(6);
    }

    public MessageContentsList(Object ... values2) {
        super(Arrays.asList(values2));
    }

    public MessageContentsList(List<?> values2) {
        super(values2);
    }

    public static MessageContentsList getContentsList(Message msg) {
        List o = CastUtils.cast(msg.getContent(List.class));
        if (o == null) {
            return null;
        }
        if (!(o instanceof MessageContentsList)) {
            MessageContentsList l2 = new MessageContentsList(o);
            msg.setContent(List.class, l2);
            return l2;
        }
        return (MessageContentsList)o;
    }

    @Override
    public Object set(int idx, Object value) {
        this.ensureSize(idx);
        return super.set(idx, value);
    }

    private void ensureSize(int idx) {
        while (idx >= this.size()) {
            this.add(REMOVED_MARKER);
        }
    }

    public Object put(MessagePartInfo key, Object value) {
        this.ensureSize(key.getIndex());
        return super.set(key.getIndex(), value);
    }

    public boolean hasValue(MessagePartInfo key) {
        if (key.getIndex() >= this.size()) {
            return false;
        }
        return super.get(key.getIndex()) != REMOVED_MARKER;
    }

    public Object get(MessagePartInfo key) {
        Object o = super.get(key.getIndex());
        return o == REMOVED_MARKER ? null : o;
    }

    public void remove(MessagePartInfo key) {
        this.put(key, REMOVED_MARKER);
    }
}

