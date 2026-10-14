/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import org.apache.cxf.Bus;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.feature.AbstractFeature;
import org.apache.cxf.interceptor.InterceptorProvider;
import org.apache.cxf.ws.addressing.MessageIdCache;

@NoJSR250Annotations
public class WSAddressingFeature
extends AbstractFeature {
    boolean allowDuplicates = true;
    boolean usingAddressingAdvisory = true;
    boolean required;
    MessageIdCache cache;
    AddressingResponses responses = AddressingResponses.ALL;

    @Override
    protected void initializeProvider(InterceptorProvider provider, Bus bus) {
        bus.getExtension(WSAddressingFeatureApplier.class).initializeProvider(this, provider, bus);
    }

    public void setAllowDuplicates(boolean allow) {
        this.allowDuplicates = allow;
    }

    public boolean isAllowDuplicates() {
        return this.allowDuplicates;
    }

    public void setUsingAddressingAdvisory(boolean advisory) {
        this.usingAddressingAdvisory = advisory;
    }

    public boolean isUsingAddressingAdvisory() {
        return this.usingAddressingAdvisory;
    }

    public boolean isAddressingRequired() {
        return this.required;
    }

    public void setAddressingRequired(boolean r) {
        this.required = r;
    }

    public MessageIdCache getMessageIdCache() {
        return this.cache;
    }

    public void setMessageIdCache(MessageIdCache messageIdCache) {
        this.cache = messageIdCache;
    }

    public void setResponses(AddressingResponses r) {
        this.responses = r;
    }

    public void setResponses(String r) {
        this.responses = AddressingResponses.valueOf(r);
    }

    public AddressingResponses getResponses() {
        return this.responses;
    }

    public static interface WSAddressingFeatureApplier {
        public void initializeProvider(WSAddressingFeature var1, InterceptorProvider var2, Bus var3);
    }

    public static enum AddressingResponses {
        ALL,
        NON_ANONYMOUS,
        ANONYMOUS;

    }
}

