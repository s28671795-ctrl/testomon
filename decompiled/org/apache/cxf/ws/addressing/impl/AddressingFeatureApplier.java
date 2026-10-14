/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.impl;

import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.ws.addressing.WSAddressingFeature;
import org.apache.cxf.ws.addressing.impl.MAPAggregatorImpl;
import org.apache.cxf.ws.addressing.soap.MAPCodec;

@NoJSR250Annotations
public class AddressingFeatureApplier
implements WSAddressingFeature.WSAddressingFeatureApplier {
    @Override
    public void initializeProvider(WSAddressingFeature feature, InterceptorProvider provider, Bus bus) {
        MAPCodec mapCodec = MAPCodec.getInstance(bus);
        MAPAggregatorImpl mapAggregator = new MAPAggregatorImpl();
        mapAggregator.setAllowDuplicates(feature.isAllowDuplicates());
        mapAggregator.setUsingAddressingAdvisory(feature.isUsingAddressingAdvisory());
        mapAggregator.setAddressingRequired(feature.isAddressingRequired());
        if (feature.getMessageIdCache() != null) {
            mapAggregator.setMessageIdCache(feature.getMessageIdCache());
        }
        mapAggregator.setAddressingResponses(feature.getResponses());
        provider.getInInterceptors().add(mapAggregator);
        provider.getInInterceptors().add(mapCodec);
        provider.getOutInterceptors().add(mapAggregator);
        provider.getOutInterceptors().add(mapCodec);
        provider.getInFaultInterceptors().add(mapAggregator);
        provider.getInFaultInterceptors().add(mapCodec);
        provider.getOutFaultInterceptors().add(mapAggregator);
        provider.getOutFaultInterceptors().add(mapCodec);
    }
}

