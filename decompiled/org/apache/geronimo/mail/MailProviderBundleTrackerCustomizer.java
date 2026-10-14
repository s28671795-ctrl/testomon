/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleEvent
 *  org.osgi.util.tracker.BundleTrackerCustomizer
 */
package org.apache.geronimo.mail;

import org.apache.geronimo.mail.Activator;
import org.apache.geronimo.mail.MailProviderRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTrackerCustomizer;

public class MailProviderBundleTrackerCustomizer
implements BundleTrackerCustomizer {
    private Activator activator;
    private Bundle activationBundle;

    public MailProviderBundleTrackerCustomizer(Activator a, Bundle b) {
        this.activator = a;
        this.activationBundle = b;
    }

    public Object addingBundle(Bundle bundle2, BundleEvent event) {
        if (bundle2.equals(this.activationBundle)) {
            return null;
        }
        return MailProviderRegistry.registerBundle(bundle2);
    }

    public void modifiedBundle(Bundle bundle2, BundleEvent event, Object object) {
        MailProviderRegistry.registerBundle(bundle2);
    }

    public void removedBundle(Bundle bundle2, BundleEvent event, Object object) {
        MailProviderRegistry.unregisterBundle(bundle2);
    }

    private void log(int level, String message) {
        this.activator.log(level, message);
    }

    private void log(int level, String message, Throwable th) {
        this.activator.log(level, message, th);
    }
}

