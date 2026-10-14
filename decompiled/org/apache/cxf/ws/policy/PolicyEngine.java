/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.policy;

import java.util.Collection;
import java.util.List;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.ws.policy.AlternativeSelector;
import org.apache.cxf.ws.policy.Assertor;
import org.apache.cxf.ws.policy.EffectivePolicy;
import org.apache.cxf.ws.policy.EndpointPolicy;
import org.apache.neethi.Assertion;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.PolicyRegistry;

public interface PolicyEngine {
    public boolean isEnabled();

    public void setEnabled(boolean var1);

    public AlternativeSelector getAlternativeSelector();

    public void setAlternativeSelector(AlternativeSelector var1);

    public boolean isIgnoreUnknownAssertions();

    public void setIgnoreUnknownAssertions(boolean var1);

    public boolean supportsAlternative(Collection<? extends PolicyComponent> var1, Assertor var2, Message var3);

    public EffectivePolicy getEffectiveClientRequestPolicy(EndpointInfo var1, BindingOperationInfo var2, Conduit var3, Message var4);

    public void setEffectiveClientRequestPolicy(EndpointInfo var1, BindingOperationInfo var2, EffectivePolicy var3);

    public EffectivePolicy getEffectiveServerResponsePolicy(EndpointInfo var1, BindingOperationInfo var2, Destination var3, List<List<Assertion>> var4, Message var5);

    public void setEffectiveServerResponsePolicy(EndpointInfo var1, BindingOperationInfo var2, EffectivePolicy var3);

    public EffectivePolicy getEffectiveServerFaultPolicy(EndpointInfo var1, BindingOperationInfo var2, BindingFaultInfo var3, Destination var4, Message var5);

    public void setEffectiveServerFaultPolicy(EndpointInfo var1, BindingFaultInfo var2, EffectivePolicy var3);

    public EndpointPolicy getClientEndpointPolicy(EndpointInfo var1, Conduit var2, Message var3);

    public EndpointPolicy getServerEndpointPolicy(EndpointInfo var1, Destination var2, Message var3);

    public void setServerEndpointPolicy(EndpointInfo var1, EndpointPolicy var2);

    public void setClientEndpointPolicy(EndpointInfo var1, EndpointPolicy var2);

    public EffectivePolicy getEffectiveServerRequestPolicy(EndpointInfo var1, BindingOperationInfo var2, Message var3);

    public void setEffectiveServerRequestPolicy(EndpointInfo var1, BindingOperationInfo var2, EffectivePolicy var3);

    public EffectivePolicy getEffectiveClientResponsePolicy(EndpointInfo var1, BindingOperationInfo var2, Message var3);

    public void setEffectiveClientResponsePolicy(EndpointInfo var1, BindingOperationInfo var2, EffectivePolicy var3);

    public EffectivePolicy getEffectiveClientFaultPolicy(EndpointInfo var1, BindingOperationInfo var2, BindingFaultInfo var3, Message var4);

    public void setEffectiveClientFaultPolicy(EndpointInfo var1, BindingFaultInfo var2, EffectivePolicy var3);

    public void addPolicy(Policy var1);

    public PolicyRegistry getRegistry();
}

