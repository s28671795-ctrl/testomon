/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.util.logging.Logger;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.AbstractConduitSelector;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.Conduit;

public class UpfrontConduitSelector
extends AbstractConduitSelector {
    private static final Logger LOG = LogUtils.getL7dLogger(UpfrontConduitSelector.class);

    public UpfrontConduitSelector() {
    }

    public UpfrontConduitSelector(Conduit c) {
        super(c);
    }

    @Override
    public void prepare(Message message) {
        Conduit c = message.get(Conduit.class);
        if (c == null) {
            c = this.getSelectedConduit(message);
            message.put(Conduit.class, c);
        }
    }

    @Override
    public Conduit selectConduit(Message message) {
        Conduit c = message.get(Conduit.class);
        if (c == null) {
            c = this.getSelectedConduit(message);
            message.put(Conduit.class, c);
        }
        return c;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }
}

