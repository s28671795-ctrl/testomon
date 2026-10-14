/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 */
package com.ctc.wstx.osgi;

import com.ctc.wstx.osgi.InputFactoryProviderImpl;
import com.ctc.wstx.osgi.OutputFactoryProviderImpl;
import com.ctc.wstx.osgi.ValidationSchemaFactoryProviderImpl;
import java.util.Dictionary;
import org.codehaus.stax2.osgi.Stax2InputFactoryProvider;
import org.codehaus.stax2.osgi.Stax2OutputFactoryProvider;
import org.codehaus.stax2.osgi.Stax2ValidationSchemaFactoryProvider;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class WstxBundleActivator
implements BundleActivator {
    public void start(BundleContext ctxt) {
        InputFactoryProviderImpl inputP = new InputFactoryProviderImpl();
        ctxt.registerService(Stax2InputFactoryProvider.class.getName(), (Object)inputP, (Dictionary)inputP.getProperties());
        OutputFactoryProviderImpl outputP = new OutputFactoryProviderImpl();
        ctxt.registerService(Stax2OutputFactoryProvider.class.getName(), (Object)outputP, (Dictionary)outputP.getProperties());
        for (ValidationSchemaFactoryProviderImpl impl : ValidationSchemaFactoryProviderImpl.createAll()) {
            ctxt.registerService(Stax2ValidationSchemaFactoryProvider.class.getName(), (Object)impl, (Dictionary)impl.getProperties());
        }
    }

    public void stop(BundleContext ctxt) {
    }
}

