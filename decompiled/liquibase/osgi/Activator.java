/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.osgi.framework.Bundle
 *  org.osgi.framework.BundleActivator
 *  org.osgi.framework.BundleContext
 *  org.osgi.framework.BundleEvent
 *  org.osgi.util.tracker.BundleTracker
 *  org.osgi.util.tracker.BundleTrackerCustomizer
 */
package liquibase.osgi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import liquibase.osgi.ContainerChecker;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

public class Activator
implements BundleActivator,
BundleTrackerCustomizer<LiquibaseBundle> {
    private static final String LIQUIBASE_CUSTOM_CHANGE_WRAPPER_PACKAGES = "Liquibase-Custom-Change-Packages";
    private BundleTracker<LiquibaseBundle> bundleTracker;
    private static final List<LiquibaseBundle> liquibaseBundles = new CopyOnWriteArrayList<LiquibaseBundle>();

    public void start(BundleContext bc) throws Exception {
        ContainerChecker.osgiPlatform();
        this.bundleTracker = new BundleTracker(bc, 32, (BundleTrackerCustomizer)this);
        this.bundleTracker.open();
    }

    public void stop(BundleContext context) throws Exception {
        this.bundleTracker.close();
        liquibaseBundles.clear();
    }

    public static List<LiquibaseBundle> getLiquibaseBundles() {
        return Collections.unmodifiableList(liquibaseBundles);
    }

    public LiquibaseBundle addingBundle(Bundle bundle2, BundleEvent event) {
        if (bundle2.getBundleId() == 0L) {
            return null;
        }
        String customWrapperPackages = (String)bundle2.getHeaders().get(LIQUIBASE_CUSTOM_CHANGE_WRAPPER_PACKAGES);
        if (customWrapperPackages != null) {
            LiquibaseBundle lb = new LiquibaseBundle(bundle2, customWrapperPackages);
            liquibaseBundles.add(lb);
            return lb;
        }
        return null;
    }

    public void modifiedBundle(Bundle bundle2, BundleEvent event, LiquibaseBundle liquibaseBundle) {
    }

    public void removedBundle(Bundle bundle2, BundleEvent event, LiquibaseBundle liquibaseBundle) {
        if (liquibaseBundle != null) {
            liquibaseBundles.remove(liquibaseBundle);
        }
    }

    public static class LiquibaseBundle {
        private final Bundle bundle;
        private final List<String> allowedPackages;

        public LiquibaseBundle(Bundle bundle2, String allowedPackages) {
            if (bundle2 == null) {
                throw new IllegalArgumentException("bundle cannot be empty");
            }
            if (allowedPackages == null || allowedPackages.isEmpty()) {
                throw new IllegalArgumentException("packages cannot be empty");
            }
            this.bundle = bundle2;
            this.allowedPackages = Collections.unmodifiableList(Arrays.asList(allowedPackages.split(",")));
        }

        public boolean allowedAllPackages() {
            return this.allowedPackages.size() == 1 && "*".equals(this.allowedPackages.get(0));
        }

        public Bundle getBundle() {
            return this.bundle;
        }

        public List<String> getAllowedPackages() {
            return this.allowedPackages;
        }
    }

    @Deprecated
    public static class OSGIContainerChecker
    extends ContainerChecker {
    }
}

