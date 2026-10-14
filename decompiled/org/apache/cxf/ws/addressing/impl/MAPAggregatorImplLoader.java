/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.impl;

import org.apache.cxf.ws.addressing.MAPAggregator;
import org.apache.cxf.ws.addressing.impl.MAPAggregatorImpl;

public class MAPAggregatorImplLoader
implements MAPAggregator.MAPAggregatorLoader {
    @Override
    public MAPAggregator createImplementation(MAPAggregator mag) {
        return new MAPAggregatorImpl(mag);
    }
}

