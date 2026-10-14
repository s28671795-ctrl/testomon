/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.net.URI;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.CommunicationException;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.event.EventDirContext;
import javax.naming.event.NamespaceChangeListener;
import javax.naming.event.NamingEvent;
import javax.naming.event.NamingExceptionEvent;
import javax.naming.event.NamingListener;
import javax.naming.event.ObjectChangeListener;
import org.apache.activemq.network.NetworkConnector;
import org.apache.activemq.util.URISupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LdapNetworkConnector
extends NetworkConnector
implements NamespaceChangeListener,
ObjectChangeListener {
    private static final Logger LOG = LoggerFactory.getLogger(LdapNetworkConnector.class);
    private static final String REQUIRED_OBJECT_CLASS_FILTER = "(&(objectClass=ipHost)(objectClass=ipService))";
    private URI[] availableURIs = null;
    private int availableURIsIndex = 0;
    private String base = null;
    private boolean failover = false;
    private long curReconnectDelay = 1000L;
    private long maxReconnectDelay = 30000L;
    private String user = null;
    private String password = null;
    private boolean anonymousAuthentication = false;
    private SearchControls searchControls = new SearchControls();
    private String searchFilter = "(&(objectClass=ipHost)(objectClass=ipService))";
    private boolean searchEventListener = false;
    private Map<URI, NetworkConnector> connectorMap = new ConcurrentHashMap<URI, NetworkConnector>();
    private Map<URI, Integer> referenceMap = new ConcurrentHashMap<URI, Integer>();
    private Map<String, URI> uuidMap = new ConcurrentHashMap<String, URI>();
    private DirContext context = null;
    private URI ldapURI = null;

    public URI getUri() {
        return this.availableURIs[++this.availableURIsIndex % this.availableURIs.length];
    }

    public void setUri(URI uri) throws Exception {
        URISupport.CompositeData data = URISupport.parseComposite(uri);
        if (data.getScheme().equals("failover")) {
            this.availableURIs = data.getComponents();
            this.failover = true;
        } else {
            this.availableURIs = new URI[]{uri};
        }
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    public void setAnonymousAuthentication(boolean anonymousAuthentication) {
        this.anonymousAuthentication = anonymousAuthentication;
    }

    public void setSearchScope(String searchScope) throws Exception {
        int scope;
        if (searchScope.equals("OBJECT_SCOPE")) {
            scope = 0;
        } else if (searchScope.equals("ONELEVEL_SCOPE")) {
            scope = 1;
        } else if (searchScope.equals("SUBTREE_SCOPE")) {
            scope = 2;
        } else {
            throw new Exception("ERR: unknown LDAP search scope specified: " + searchScope);
        }
        this.searchControls.setSearchScope(scope);
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = "(&(&(objectClass=ipHost)(objectClass=ipService))(" + searchFilter + "))";
    }

    public void setSearchEventListener(boolean searchEventListener) {
        this.searchEventListener = searchEventListener;
    }

    @Override
    public void start() throws Exception {
        LOG.info("connecting...");
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
        this.ldapURI = this.getUri();
        LOG.debug("    URI [{}]", (Object)this.ldapURI);
        env.put("java.naming.provider.url", this.ldapURI.toString());
        if (this.anonymousAuthentication) {
            LOG.debug("    login credentials [anonymous]");
            env.put("java.naming.security.authentication", "none");
        } else {
            LOG.debug("    login credentials [{}:******]", (Object)this.user);
            if (this.user == null || "".equals(this.user)) {
                throw new Exception("Empty username is not allowed");
            }
            env.put("java.naming.security.principal", this.user);
            if (this.password != null && !"".equals(this.password)) {
                env.put("java.naming.security.credentials", this.password);
            } else {
                throw new Exception("Empty password is not allowed");
            }
        }
        boolean isConnected = false;
        while (!isConnected) {
            try {
                this.context = new InitialDirContext(env);
                isConnected = true;
            }
            catch (CommunicationException err) {
                if (this.failover) {
                    this.ldapURI = this.getUri();
                    LOG.error("connection error [{}], failover connection to [{}]", env.get("java.naming.provider.url"), (Object)this.ldapURI.toString());
                    env.put("java.naming.provider.url", this.ldapURI.toString());
                    Thread.sleep(this.curReconnectDelay);
                    this.curReconnectDelay = Math.min(this.curReconnectDelay * 2L, this.maxReconnectDelay);
                    continue;
                }
                throw err;
            }
        }
        LOG.info("searching for network connectors...");
        LOG.debug("    base   [{}]", (Object)this.base);
        LOG.debug("    filter [{}]", (Object)this.searchFilter);
        LOG.debug("    scope  [{}]", (Object)this.searchControls.getSearchScope());
        NamingEnumeration<SearchResult> results = this.context.search(this.base, this.searchFilter, this.searchControls);
        while (results.hasMore()) {
            this.addConnector(results.next());
        }
        if (this.searchEventListener) {
            LOG.info("registering persistent search listener...");
            EventDirContext eventContext = (EventDirContext)this.context.lookup("");
            eventContext.addNamingListener(this.base, this.searchFilter, this.searchControls, (NamingListener)this);
        } else {
            this.context.close();
        }
    }

    @Override
    public void stop() throws Exception {
        LOG.info("stopping context...");
        for (NetworkConnector connector : this.connectorMap.values()) {
            connector.stop();
        }
        this.connectorMap.clear();
        this.referenceMap.clear();
        this.uuidMap.clear();
        this.context.close();
    }

    public String toString() {
        return this.getClass().getName() + this.getName() + "[" + this.ldapURI.toString() + "]";
    }

    protected synchronized void addConnector(SearchResult result) throws Exception {
        String uuid = this.toUUID(result);
        if (this.uuidMap.containsKey(uuid)) {
            LOG.warn("connector already regsitered for UUID [{}]", (Object)uuid);
            return;
        }
        URI connectorURI = this.toURI(result);
        if (this.connectorMap.containsKey(connectorURI)) {
            int referenceCount = this.referenceMap.get(connectorURI) + 1;
            LOG.warn("connector reference added for URI [{}], UUID [{}], total reference(s) [{}]", connectorURI, uuid, referenceCount);
            this.referenceMap.put(connectorURI, referenceCount);
            this.uuidMap.put(uuid, connectorURI);
            return;
        }
        NetworkConnector connector = this.getBrokerService().addNetworkConnector(connectorURI);
        connector.setDynamicOnly(this.isDynamicOnly());
        connector.setDecreaseNetworkConsumerPriority(this.isDecreaseNetworkConsumerPriority());
        connector.setNetworkTTL(this.getNetworkTTL());
        connector.setConsumerTTL(this.getConsumerTTL());
        connector.setMessageTTL(this.getMessageTTL());
        connector.setConduitSubscriptions(this.isConduitSubscriptions());
        connector.setExcludedDestinations(this.getExcludedDestinations());
        connector.setDynamicallyIncludedDestinations(this.getDynamicallyIncludedDestinations());
        connector.setDuplex(this.isDuplex());
        connector.setLocalUri(this.getBrokerService().getVmConnectorURI());
        connector.setBrokerName(this.getBrokerService().getBrokerName());
        connector.setDurableDestinations(this.getBrokerService().getBroker().getDurableDestinations());
        this.connectorMap.put(connectorURI, connector);
        this.referenceMap.put(connectorURI, 1);
        this.uuidMap.put(uuid, connectorURI);
        connector.start();
        LOG.info("connector added with URI [{}]", (Object)connectorURI);
    }

    protected synchronized void removeConnector(SearchResult result) throws Exception {
        String uuid = this.toUUID(result);
        if (!this.uuidMap.containsKey(uuid)) {
            LOG.warn("connector not registered for UUID [{}]", (Object)uuid);
            return;
        }
        URI connectorURI = this.uuidMap.get(uuid);
        if (!this.connectorMap.containsKey(connectorURI)) {
            LOG.warn("connector not registered for URI [{}]", (Object)connectorURI);
            return;
        }
        int referenceCount = this.referenceMap.get(connectorURI) - 1;
        this.referenceMap.put(connectorURI, referenceCount);
        this.uuidMap.remove(uuid);
        LOG.debug("connector referenced removed for URI [{}], UUID[{}], remaining reference(s) [{}]", connectorURI, uuid, referenceCount);
        if (referenceCount > 0) {
            return;
        }
        NetworkConnector connector = this.connectorMap.remove(connectorURI);
        connector.stop();
        LOG.info("connector removed with URI [{}]", (Object)connectorURI);
    }

    protected URI toURI(SearchResult result) throws Exception {
        Attributes attributes = result.getAttributes();
        String address = (String)attributes.get("iphostnumber").get();
        String port = (String)attributes.get("ipserviceport").get();
        String protocol = (String)attributes.get("ipserviceprotocol").get();
        URI connectorURI = new URI("static:(" + protocol + "://" + address + ":" + port + ")");
        LOG.debug("retrieved URI from SearchResult [{}]", (Object)connectorURI);
        return connectorURI;
    }

    protected String toUUID(SearchResult result) {
        String uuid = result.getNameInNamespace();
        LOG.debug("retrieved UUID from SearchResult [{}]", (Object)uuid);
        return uuid;
    }

    @Override
    public void objectAdded(NamingEvent event) {
        LOG.debug("entry added");
        try {
            this.addConnector((SearchResult)event.getNewBinding());
        }
        catch (Exception err) {
            LOG.error("ERR: caught unexpected exception", err);
        }
    }

    @Override
    public void objectRemoved(NamingEvent event) {
        LOG.debug("entry removed");
        try {
            this.removeConnector((SearchResult)event.getOldBinding());
        }
        catch (Exception err) {
            LOG.error("ERR: caught unexpected exception", err);
        }
    }

    @Override
    public void objectRenamed(NamingEvent event) {
        LOG.debug("entry renamed");
        String uuidOld = event.getOldBinding().getName();
        String uuidNew = event.getNewBinding().getName();
        URI connectorURI = this.uuidMap.remove(uuidOld);
        this.uuidMap.put(uuidNew, connectorURI);
        LOG.debug("connector reference renamed for URI [{}], Old UUID [{}], New UUID [{}]", connectorURI, uuidOld, uuidNew);
    }

    @Override
    public void objectChanged(NamingEvent event) {
        LOG.debug("entry changed");
        try {
            SearchResult result = (SearchResult)event.getNewBinding();
            this.removeConnector(result);
            this.addConnector(result);
        }
        catch (Exception err) {
            LOG.error("ERR: caught unexpected exception", err);
        }
    }

    @Override
    public void namingExceptionThrown(NamingExceptionEvent event) {
        LOG.error("ERR: caught unexpected exception", event.getException());
    }
}

