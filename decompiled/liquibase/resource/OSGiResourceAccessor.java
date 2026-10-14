/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.wiring.BundleWiring
 */
package liquibase.resource;

import liquibase.resource.ClassLoaderResourceAccessor;
import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

public class OSGiResourceAccessor
extends ClassLoaderResourceAccessor {
    public OSGiResourceAccessor(Bundle bundle2) {
        super(((BundleWiring)bundle2.adapt(BundleWiring.class)).getClassLoader());
    }
}

