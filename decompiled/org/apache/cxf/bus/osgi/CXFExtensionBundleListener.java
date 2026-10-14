/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.BundleEvent
 *  org.osgi.framework.ServiceReference
 *  org.osgi.framework.SynchronousBundleListener
 */
package org.apache.cxf.bus.osgi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.extension.Extension;
import org.apache.cxf.bus.extension.ExtensionException;
import org.apache.cxf.bus.extension.ExtensionRegistry;
import org.apache.cxf.bus.extension.TextExtensionFragmentParser;
import org.apache.cxf.bus.osgi.CXFActivator;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.SynchronousBundleListener;

public class CXFExtensionBundleListener
implements SynchronousBundleListener {
    private static final Logger LOG = LogUtils.getL7dLogger(CXFActivator.class);
    private long id;
    private ConcurrentMap<Long, List<OSGiExtension>> extensions = new ConcurrentHashMap<Long, List<OSGiExtension>>(16, 0.75f, 4);

    public CXFExtensionBundleListener(long bundleId) {
        this.id = bundleId;
    }

    public void registerExistingBundles(BundleContext context) throws IOException {
        for (Bundle bundle2 : context.getBundles()) {
            if (bundle2.getState() != 4 && bundle2.getState() != 8 && bundle2.getState() != 32 && bundle2.getState() != 16 || bundle2.getBundleId() == context.getBundle().getBundleId()) continue;
            this.register(bundle2);
        }
    }

    public void bundleChanged(BundleEvent event) {
        if (event.getType() == 32 && this.id != event.getBundle().getBundleId()) {
            this.register(event.getBundle());
        } else if (event.getType() == 64 || event.getType() == 16) {
            this.unregister(event.getBundle().getBundleId());
        }
    }

    protected void register(Bundle bundle2) {
        Enumeration e = bundle2.findEntries("META-INF/cxf/", "bus-extensions.txt", false);
        while (e != null && e.hasMoreElements()) {
            List<Extension> orig = new TextExtensionFragmentParser(null).getExtensions((URL)e.nextElement());
            this.addExtensions(bundle2, orig);
        }
    }

    private boolean addExtensions(Bundle bundle2, List<Extension> orig) {
        if (orig.isEmpty()) {
            return false;
        }
        ArrayList<String> names = new ArrayList<String>(orig.size());
        for (Extension ext : orig) {
            names.add(ext.getName());
        }
        LOG.info("Adding the extensions from bundle " + bundle2.getSymbolicName() + " (" + bundle2.getBundleId() + ") " + names);
        List<OSGiExtension> list = (CopyOnWriteArrayList<OSGiExtension>)this.extensions.get(bundle2.getBundleId());
        if (list == null) {
            list = new CopyOnWriteArrayList<OSGiExtension>();
            List preList = this.extensions.putIfAbsent(bundle2.getBundleId(), list);
            if (preList != null) {
                list = preList;
            }
        }
        for (Extension ext : orig) {
            list.add(new OSGiExtension(ext, bundle2));
        }
        ExtensionRegistry.addExtensions(list);
        return !list.isEmpty();
    }

    protected void unregister(long bundleId) {
        List list = (List)this.extensions.remove(bundleId);
        if (list != null) {
            LOG.info("Removing the extensions for bundle " + bundleId);
            ExtensionRegistry.removeExtensions(list);
        }
    }

    public void shutdown() {
        while (!this.extensions.isEmpty()) {
            this.unregister((Long)this.extensions.keySet().iterator().next());
        }
    }

    public class OSGiExtension
    extends Extension {
        final Bundle bundle;
        Object serviceObject;

        public OSGiExtension(Extension e, Bundle b) {
            super(e);
            this.bundle = b;
        }

        public void setServiceObject(Object o) {
            this.serviceObject = o;
            this.obj = o;
        }

        @Override
        public Object load(ClassLoader cl, Bus b) {
            ServiceReference ref;
            if (this.interfaceName == null && this.bundle.getBundleContext() != null && (ref = this.bundle.getBundleContext().getServiceReference(this.className)) != null && ref.getBundle().getBundleId() == this.bundle.getBundleId()) {
                Object o;
                this.serviceObject = o = this.bundle.getBundleContext().getService(ref);
                this.obj = o;
                return this.obj;
            }
            return super.load(cl, b);
        }

        @Override
        protected Class<?> tryClass(String name, ClassLoader cl) {
            Class c = null;
            Throwable origExc = null;
            try {
                c = this.bundle.loadClass(this.className);
            }
            catch (Throwable e) {
                origExc = e;
            }
            if (c == null) {
                try {
                    return super.tryClass(name, cl);
                }
                catch (ExtensionException ee) {
                    if (origExc != null) {
                        throw new ExtensionException(new Message("PROBLEM_LOADING_EXTENSION_CLASS", Extension.LOG, name), origExc);
                    }
                    throw ee;
                }
            }
            return c;
        }

        @Override
        public Extension cloneNoObject() {
            OSGiExtension ext = new OSGiExtension(this, this.bundle);
            ext.obj = this.serviceObject;
            return ext;
        }
    }
}

