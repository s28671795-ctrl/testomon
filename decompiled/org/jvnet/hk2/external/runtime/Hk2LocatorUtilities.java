/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.external.runtime;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.jvnet.hk2.internal.DefaultClassAnalyzer;
import org.jvnet.hk2.internal.DynamicConfigurationServiceImpl;
import org.jvnet.hk2.internal.InstantiationServiceImpl;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.ServiceLocatorRuntimeImpl;
import org.jvnet.hk2.internal.ThreeThirtyResolver;

public class Hk2LocatorUtilities {
    private static final Filter NO_INITIAL_SERVICES_FILTER = new Filter(){
        private final List<String> INITIAL_SERVICES = Arrays.asList(ServiceLocatorImpl.class.getName(), ThreeThirtyResolver.class.getName(), DynamicConfigurationServiceImpl.class.getName(), DefaultClassAnalyzer.class.getName(), ServiceLocatorRuntimeImpl.class.getName(), InstantiationServiceImpl.class.getName());
        private final HashSet<String> INITIAL_SERVICE_SET = new HashSet<String>(this.INITIAL_SERVICES);

        @Override
        public boolean matches(Descriptor d) {
            return !this.INITIAL_SERVICE_SET.contains(d.getImplementation());
        }
    };

    public static Filter getNoInitialServicesFilter() {
        return NO_INITIAL_SERVICES_FILTER;
    }
}

