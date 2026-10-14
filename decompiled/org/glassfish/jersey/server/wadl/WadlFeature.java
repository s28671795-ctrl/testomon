/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl;

import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.wadl.WadlApplicationContext;
import org.glassfish.jersey.server.wadl.internal.WadlApplicationContextImpl;
import org.glassfish.jersey.server.wadl.processor.WadlModelProcessor;

public class WadlFeature
implements Feature {
    private static final Logger LOGGER = Logger.getLogger(WadlFeature.class.getName());

    @Override
    public boolean configure(FeatureContext context) {
        boolean disabled = PropertiesHelper.isProperty(context.getConfiguration().getProperty("jersey.config.server.wadl.disableWadl"));
        if (disabled) {
            return false;
        }
        if (!ReflectionHelper.isJaxbAvailable()) {
            LOGGER.warning(LocalizationMessages.WADL_FEATURE_DISABLED_NOJAXB());
            return false;
        }
        if (!ReflectionHelper.isXmlTransformAvailable()) {
            LOGGER.warning(LocalizationMessages.WADL_FEATURE_DISABLED_NOTRANSFORM());
            return false;
        }
        if (!WadlApplicationContextImpl.isJaxbImplAvailable()) {
            LOGGER.warning(LocalizationMessages.WADL_FEATURE_DISABLED());
            return false;
        }
        context.register(WadlModelProcessor.class);
        context.register(new AbstractBinder(){

            @Override
            protected void configure() {
                ((ClassBinding)this.bind(WadlApplicationContextImpl.class).to(WadlApplicationContext.class)).in(Singleton.class);
            }
        });
        return true;
    }
}

