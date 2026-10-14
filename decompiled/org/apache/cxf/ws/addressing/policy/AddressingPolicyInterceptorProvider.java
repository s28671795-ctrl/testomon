/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing.policy;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.namespace.QName;
import org.apache.cxf.Bus;
import org.apache.cxf.ws.addressing.MAPAggregator;
import org.apache.cxf.ws.addressing.impl.MAPAggregatorImpl;
import org.apache.cxf.ws.addressing.policy.MetadataConstants;
import org.apache.cxf.ws.addressing.soap.MAPCodec;
import org.apache.cxf.ws.policy.AbstractPolicyInterceptorProvider;

public class AddressingPolicyInterceptorProvider
extends AbstractPolicyInterceptorProvider {
    private static final long serialVersionUID = -1018053541795476992L;
    private static final Collection<QName> ASSERTION_TYPES;
    private static final MAPAggregator MAP_AGGREGATOR;

    public AddressingPolicyInterceptorProvider(Bus b) {
        super(ASSERTION_TYPES);
        MAPCodec mapCodec = MAPCodec.getInstance(b);
        this.getInInterceptors().add(MAP_AGGREGATOR);
        this.getInInterceptors().add(mapCodec);
        this.getOutInterceptors().add(MAP_AGGREGATOR);
        this.getOutInterceptors().add(mapCodec);
        this.getInFaultInterceptors().add(MAP_AGGREGATOR);
        this.getInFaultInterceptors().add(mapCodec);
        this.getOutFaultInterceptors().add(MAP_AGGREGATOR);
        this.getOutFaultInterceptors().add(mapCodec);
    }

    static {
        MAP_AGGREGATOR = new MAPAggregatorImpl();
        ArrayList<QName> types = new ArrayList<QName>();
        types.add(MetadataConstants.ADDRESSING_ASSERTION_QNAME);
        types.add(MetadataConstants.ANON_RESPONSES_ASSERTION_QNAME);
        types.add(MetadataConstants.NON_ANON_RESPONSES_ASSERTION_QNAME);
        types.add(MetadataConstants.USING_ADDRESSING_2004_QNAME);
        types.add(MetadataConstants.USING_ADDRESSING_2005_QNAME);
        types.add(MetadataConstants.USING_ADDRESSING_2006_QNAME);
        ASSERTION_TYPES = types;
    }
}

