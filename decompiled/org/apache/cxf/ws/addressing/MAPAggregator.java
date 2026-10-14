/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import java.util.Collection;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.PhaseInterceptor;
import org.apache.cxf.ws.addressing.MessageIdCache;
import org.apache.cxf.ws.addressing.WSAddressingFeature;

public class MAPAggregator
extends AbstractPhaseInterceptor<Message> {
    public static final String USING_ADDRESSING = MAPAggregator.class.getName() + ".usingAddressing";
    public static final String ADDRESSING_DISABLED = MAPAggregator.class.getName() + ".addressingDisabled";
    public static final String DECOUPLED_DESTINATION = MAPAggregator.class.getName() + ".decoupledDestination";
    public static final String ACTION_VERIFIED = MAPAggregator.class.getName() + ".actionVerified";
    public static final String ADDRESSING_NAMESPACE = MAPAggregator.class.getName() + ".addressingNamespace";
    protected MessageIdCache messageIdCache;
    protected boolean usingAddressingAdvisory = true;
    protected boolean addressingRequired;
    protected boolean allowDuplicates = true;
    protected WSAddressingFeature.AddressingResponses addressingResponses = WSAddressingFeature.AddressingResponses.ALL;
    private MAPAggregator impl;

    public MAPAggregator() {
        super(MAPAggregator.class.getName(), "pre-logical");
        this.addBefore("org.apache.cxf.interceptor.OneWayProcessorInterceptor");
    }

    public boolean allowDuplicates() {
        if (this.impl != null) {
            return this.impl.allowDuplicates();
        }
        return this.allowDuplicates;
    }

    public void setAllowDuplicates(boolean ad) {
        if (this.impl != null) {
            this.impl.setAllowDuplicates(ad);
        }
        this.allowDuplicates = ad;
    }

    public boolean isUsingAddressingAdvisory() {
        if (this.impl != null) {
            return this.impl.isUsingAddressingAdvisory();
        }
        return this.usingAddressingAdvisory;
    }

    public void setUsingAddressingAdvisory(boolean advisory) {
        if (this.impl != null) {
            this.impl.setUsingAddressingAdvisory(advisory);
        }
        this.usingAddressingAdvisory = advisory;
    }

    public boolean isAddressingRequired() {
        if (this.impl != null) {
            return this.impl.addressingRequired;
        }
        return this.addressingRequired;
    }

    public void setAddressingRequired(boolean required) {
        if (this.impl != null) {
            this.impl.setAddressingRequired(required);
        }
        this.addressingRequired = required;
    }

    public void setAddressingResponses(WSAddressingFeature.AddressingResponses responses) {
        if (this.impl != null) {
            this.impl.setAddressingResponses(responses);
        }
        this.addressingResponses = responses;
    }

    public MessageIdCache getMessageIdCache() {
        if (this.impl != null) {
            return this.impl.getMessageIdCache();
        }
        return this.messageIdCache;
    }

    public void setMessageIdCache(MessageIdCache messageIdCache) {
        if (messageIdCache == null) {
            throw new NullPointerException("messageIdCache cannot be null.");
        }
        if (this.impl != null) {
            this.impl.setMessageIdCache(messageIdCache);
        }
        this.messageIdCache = messageIdCache;
    }

    public WSAddressingFeature.AddressingResponses getAddressingResponses() {
        if (this.impl != null) {
            return this.impl.getAddressingResponses();
        }
        return this.addressingResponses;
    }

    @Override
    public void handleMessage(Message message) {
        if (this.impl == null) {
            MAPAggregatorLoader loader = message.getExchange().getBus().getExtension(MAPAggregatorLoader.class);
            this.impl = loader.createImplementation(this);
        }
        this.impl.handleMessage(message);
    }

    @Override
    public void handleFault(Message message) {
        if (this.impl != null) {
            this.impl.handleFault(message);
        }
    }

    @Override
    public Collection<PhaseInterceptor<? extends Message>> getAdditionalInterceptors() {
        if (this.impl != null) {
            return this.impl.getAdditionalInterceptors();
        }
        return super.getAdditionalInterceptors();
    }

    public static interface MAPAggregatorLoader {
        public MAPAggregator createImplementation(MAPAggregator var1);
    }
}

