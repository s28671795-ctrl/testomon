/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.FrameworkUtil
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.osgi.io.OsgiBundleResourcePatternResolver
 *  org.springframework.osgi.util.BundleDelegatingClassLoader
 */
package org.apache.cxf.common.util;

import org.apache.cxf.common.util.SpringClasspathScanner;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.osgi.io.OsgiBundleResourcePatternResolver;
import org.springframework.osgi.util.BundleDelegatingClassLoader;

final class SpringOsgiUtil {
    private SpringOsgiUtil() {
    }

    public static ResourcePatternResolver getResolver(ClassLoader loader) {
        if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader();
        }
        Bundle bundle2 = loader instanceof BundleDelegatingClassLoader ? ((BundleDelegatingClassLoader)loader).getBundle() : FrameworkUtil.getBundle(SpringClasspathScanner.class);
        return bundle2 != null ? new OsgiBundleResourcePatternResolver(bundle2) : null;
    }
}

