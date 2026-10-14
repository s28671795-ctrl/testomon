/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 */
package org.apache.cxf.ws.policy.blueprint;

import org.apache.cxf.bus.blueprint.BlueprintNameSpaceHandlerFactory;
import org.apache.cxf.bus.blueprint.NamespaceHandlerRegisterer;
import org.apache.cxf.ws.policy.blueprint.PolicyBPHandler;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator
implements BundleActivator {
    public void start(BundleContext context) throws Exception {
        try {
            BlueprintNameSpaceHandlerFactory factory2 = new BlueprintNameSpaceHandlerFactory(){

                @Override
                public Object createNamespaceHandler() {
                    return new PolicyBPHandler();
                }
            };
            NamespaceHandlerRegisterer.register(context, factory2, "http://cxf.apache.org/policy", "http://www.w3.org/ns/ws-policy", "http://www.w3.org/2006/07/ws-policy", "http://schemas.xmlsoap.org/ws/2004/09/policy", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "http://www.w3.org/2000/09/xmldsig#", "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702");
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            // empty catch block
        }
    }

    public void stop(BundleContext context) throws Exception {
    }
}

