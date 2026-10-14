/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 */
package org.apache.cxf.binding.soap.blueprint;

import org.apache.cxf.binding.soap.blueprint.SoapBindingBPHandler;
import org.apache.cxf.bus.blueprint.BlueprintNameSpaceHandlerFactory;
import org.apache.cxf.bus.blueprint.NamespaceHandlerRegisterer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator
implements BundleActivator {
    public void start(BundleContext context) throws Exception {
        try {
            BlueprintNameSpaceHandlerFactory factory2 = new BlueprintNameSpaceHandlerFactory(){

                @Override
                public Object createNamespaceHandler() {
                    return new SoapBindingBPHandler();
                }
            };
            NamespaceHandlerRegisterer.register(context, factory2, "http://cxf.apache.org/blueprint/bindings/soap");
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            // empty catch block
        }
    }

    public void stop(BundleContext context) throws Exception {
    }
}

