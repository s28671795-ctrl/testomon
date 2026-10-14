/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import java.util.Map;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.AbstractConfiguration;
import org.eclipse.jetty.webapp.FragmentDescriptor;
import org.eclipse.jetty.webapp.MetaData;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class FragmentConfiguration
extends AbstractConfiguration {
    public static final String FRAGMENT_RESOURCES = "org.eclipse.jetty.webFragments";

    public FragmentConfiguration() {
        this.addDependencies(MetaInfConfiguration.class, WebXmlConfiguration.class);
    }

    @Override
    public void preConfigure(WebAppContext context) throws Exception {
        this.addWebFragments(context, context.getMetaData());
    }

    @Override
    public void postConfigure(WebAppContext context) throws Exception {
        context.setAttribute(FRAGMENT_RESOURCES, null);
    }

    public void addWebFragments(WebAppContext context, MetaData metaData) throws Exception {
        Map frags = (Map)context.getAttribute(FRAGMENT_RESOURCES);
        if (frags != null) {
            for (Map.Entry entry : frags.entrySet()) {
                metaData.addFragmentDescriptor((Resource)entry.getKey(), new FragmentDescriptor((Resource)entry.getValue()));
            }
        }
    }
}

