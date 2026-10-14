/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.management.JMException;
import javax.management.ObjectName;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.jmx.AnnotatedMBean;
import org.apache.activemq.broker.jmx.BrokerMBeanSupport;
import org.apache.activemq.broker.jmx.VirtualDestinationSelectorCacheView;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.command.ConsumerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubQueueSelectorCacheBroker
extends BrokerFilter
implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(SubQueueSelectorCacheBroker.class);
    public static final String MATCH_EVERYTHING = "TRUE";
    private ConcurrentMap<String, Set<String>> subSelectorCache = new ConcurrentHashMap<String, Set<String>>();
    private final File persistFile;
    private boolean singleSelectorPerDestination = false;
    private boolean ignoreWildcardSelectors = false;
    private ObjectName objectName;
    private boolean running = true;
    private final Thread persistThread;
    private long persistInterval = 600000L;
    public static final long MAX_PERSIST_INTERVAL = 600000L;
    private static final String SELECTOR_CACHE_PERSIST_THREAD_NAME = "SelectorCachePersistThread";

    public SubQueueSelectorCacheBroker(Broker next, File persistFile) {
        super(next);
        this.persistFile = persistFile;
        LOG.info("Using persisted selector cache from[{}]", (Object)persistFile);
        this.readCache();
        this.persistThread = new Thread(this, SELECTOR_CACHE_PERSIST_THREAD_NAME);
        this.persistThread.start();
        this.enableJmx();
    }

    private void enableJmx() {
        BrokerService broker = this.getBrokerService();
        if (broker.isUseJmx()) {
            VirtualDestinationSelectorCacheView view = new VirtualDestinationSelectorCacheView(this);
            try {
                this.objectName = BrokerMBeanSupport.createVirtualDestinationSelectorCacheName(broker.getBrokerObjectName(), "plugin", "virtualDestinationCache");
                LOG.trace("virtualDestinationCacheSelector mbean name; " + this.objectName.toString());
                AnnotatedMBean.registerMBean(broker.getManagementContext(), view, this.objectName);
            }
            catch (Exception e) {
                LOG.warn("JMX is enabled, but when installing the VirtualDestinationSelectorCache, couldn't install the JMX mbeans. Continuing without installing the mbeans.");
            }
        }
    }

    @Override
    public void stop() throws Exception {
        this.running = false;
        if (this.persistThread != null) {
            this.persistThread.interrupt();
            this.persistThread.join();
        }
        this.unregisterMBeans();
    }

    private void unregisterMBeans() {
        BrokerService broker = this.getBrokerService();
        if (broker.isUseJmx() && this.objectName != null) {
            try {
                broker.getManagementContext().unregisterMBean(this.objectName);
            }
            catch (JMException e) {
                LOG.warn("Trying uninstall VirtualDestinationSelectorCache; couldn't uninstall mbeans, continuting...");
            }
        }
    }

    @Override
    public Subscription addConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        if (!(AdvisorySupport.isAdvisoryTopic(info.getDestination()) || info.getDestination().isTemporary() || info.isBrowser())) {
            String selector;
            String destinationName = info.getDestination().getQualifiedName();
            LOG.debug("Caching consumer selector [{}] on  '{}'", (Object)info.getSelector(), (Object)destinationName);
            String string = selector = info.getSelector() == null ? MATCH_EVERYTHING : info.getSelector();
            if (!this.ignoreWildcardSelectors || !SubQueueSelectorCacheBroker.hasWildcards(selector)) {
                Set<String> selectors = (Set<String>)this.subSelectorCache.get(destinationName);
                if (selectors == null) {
                    selectors = Collections.synchronizedSet(new HashSet());
                } else if (this.singleSelectorPerDestination && !MATCH_EVERYTHING.equals(selector)) {
                    boolean containsMatchEverything = selectors.contains(MATCH_EVERYTHING);
                    selectors.clear();
                    if (containsMatchEverything) {
                        selectors.add(MATCH_EVERYTHING);
                    }
                }
                LOG.debug("adding new selector: into cache " + selector);
                selectors.add(selector);
                LOG.debug("current selectors in cache: " + selectors);
                this.subSelectorCache.put(destinationName, selectors);
            }
        }
        return super.addConsumer(context, info);
    }

    static boolean hasWildcards(String selector) {
        return WildcardFinder.hasWildcards(selector);
    }

    @Override
    public void removeConsumer(ConnectionContext context, ConsumerInfo info) throws Exception {
        if (!AdvisorySupport.isAdvisoryTopic(info.getDestination()) && !info.getDestination().isTemporary() && this.singleSelectorPerDestination) {
            String destinationName = info.getDestination().getQualifiedName();
            Set selectors = (Set)this.subSelectorCache.get(destinationName);
            if (info.getSelector() == null && selectors.size() > 1) {
                boolean removed = selectors.remove(MATCH_EVERYTHING);
                LOG.debug("A non-selector consumer has dropped. Removing the catchall matching pattern 'TRUE'. Successful? " + removed);
            }
        }
        super.removeConsumer(context, info);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void readCache() {
        if (this.persistFile != null && this.persistFile.exists()) {
            try (FileInputStream fis = new FileInputStream(this.persistFile);
                 SubSelectorClassObjectInputStream in = new SubSelectorClassObjectInputStream(fis);){
                LOG.debug("Reading selector cache....");
                this.subSelectorCache = (ConcurrentHashMap)in.readObject();
                if (LOG.isDebugEnabled()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Selector cache data loaded from: ").append(this.persistFile.getAbsolutePath()).append("\n");
                    sb.append("The following entries were loaded from the cache file: \n");
                    this.subSelectorCache.forEach((k, v) -> sb.append("\t").append((String)k).append(": ").append(v).append("\n"));
                    LOG.debug(sb.toString());
                }
            }
            catch (IOException ex) {
                LOG.error("Unable to read persisted selector cache...it will be ignored!", ex);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void persistCache() {
        LOG.debug("Persisting selector cache....");
        try (FileOutputStream fos = new FileOutputStream(this.persistFile);){
            ObjectOutputStream out = new ObjectOutputStream(fos);
            try {
                out.writeObject(this.subSelectorCache);
            }
            finally {
                out.flush();
                out.close();
            }
        }
        catch (IOException ex) {
            LOG.error("Unable to access file[{}]", (Object)this.persistFile, (Object)ex);
        }
    }

    public Set<String> getSelector(String destination) {
        return (Set)this.subSelectorCache.get(destination);
    }

    @Override
    public void run() {
        while (this.running) {
            try {
                Thread.sleep(this.persistInterval);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
            this.persistCache();
        }
    }

    public boolean isSingleSelectorPerDestination() {
        return this.singleSelectorPerDestination;
    }

    public void setSingleSelectorPerDestination(boolean singleSelectorPerDestination) {
        this.singleSelectorPerDestination = singleSelectorPerDestination;
    }

    public Set<String> getSelectorsForDestination(String destinationName) {
        if (this.subSelectorCache.containsKey(destinationName)) {
            return new HashSet<String>((Collection)this.subSelectorCache.get(destinationName));
        }
        return Collections.EMPTY_SET;
    }

    public long getPersistInterval() {
        return this.persistInterval;
    }

    public void setPersistInterval(long persistInterval) {
        this.persistInterval = persistInterval;
    }

    public boolean deleteSelectorForDestination(String destinationName, String selector) {
        if (this.subSelectorCache.containsKey(destinationName)) {
            Set cachedSelectors = (Set)this.subSelectorCache.get(destinationName);
            return cachedSelectors.remove(selector);
        }
        return false;
    }

    public boolean deleteAllSelectorsForDestination(String destinationName) {
        if (this.subSelectorCache.containsKey(destinationName)) {
            Set cachedSelectors = (Set)this.subSelectorCache.get(destinationName);
            cachedSelectors.clear();
        }
        return true;
    }

    public boolean isIgnoreWildcardSelectors() {
        return this.ignoreWildcardSelectors;
    }

    public void setIgnoreWildcardSelectors(boolean ignoreWildcardSelectors) {
        this.ignoreWildcardSelectors = ignoreWildcardSelectors;
    }

    private static class SubSelectorClassObjectInputStream
    extends ObjectInputStream {
        public SubSelectorClassObjectInputStream(InputStream is) throws IOException {
            super(is);
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            if (!desc.getName().equals("java.lang.String") && !desc.getName().startsWith("java.util.")) {
                throw new InvalidClassException("Unauthorized deserialization attempt", desc.getName());
            }
            return super.resolveClass(desc);
        }
    }

    static class WildcardFinder {
        private static final Pattern LIKE_PATTERN = Pattern.compile("\\bLIKE\\s+'(?<like>([^']|'')+)'(\\s+ESCAPE\\s+'(?<escape>.)')?", 2);
        private static final String REGEX_SPECIAL = ".+?*(){}[]\\-";

        WildcardFinder() {
        }

        private static String getLike(Matcher matcher) {
            return matcher.group("like");
        }

        private static boolean hasLikeOperator(Matcher matcher) {
            return matcher.find();
        }

        private static String getEscape(Matcher matcher) {
            String escapeChar = matcher.group("escape");
            if (escapeChar == null) {
                return null;
            }
            if (REGEX_SPECIAL.contains(escapeChar)) {
                escapeChar = "\\" + escapeChar;
            }
            return escapeChar;
        }

        private static boolean hasWildcardInCurrentMatch(Matcher matcher) {
            String wildcards = "[_%]";
            if (WildcardFinder.getEscape(matcher) != null) {
                wildcards = "(^|[^" + WildcardFinder.getEscape(matcher) + "])" + wildcards;
            }
            return Pattern.compile(wildcards).matcher(WildcardFinder.getLike(matcher)).find();
        }

        public static boolean hasWildcards(String selector) {
            Matcher matcher = LIKE_PATTERN.matcher(selector);
            while (WildcardFinder.hasLikeOperator(matcher)) {
                if (!WildcardFinder.hasWildcardInCurrentMatch(matcher)) continue;
                return true;
            }
            return false;
        }
    }
}

