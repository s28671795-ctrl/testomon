/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.activemq.jaas.UserPrincipal
 */
package org.apache.activemq.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.naming.Binding;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
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
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.filter.DestinationMapEntry;
import org.apache.activemq.jaas.UserPrincipal;
import org.apache.activemq.security.AuthorizationEntry;
import org.apache.activemq.security.AuthorizationMap;
import org.apache.activemq.security.DefaultAuthorizationMap;
import org.apache.activemq.security.TempDestinationAuthorizationEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleCachedLDAPAuthorizationMap
implements AuthorizationMap {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleCachedLDAPAuthorizationMap.class);
    private final String initialContextFactory = "com.sun.jndi.ldap.LdapCtxFactory";
    private String connectionURL = "ldap://localhost:1024";
    private String connectionUsername = "uid=admin,ou=system";
    private String connectionPassword;
    private String connectionProtocol = "s";
    private String authentication = "simple";
    private int queuePrefixLength = 4;
    private int topicPrefixLength = 4;
    private int tempPrefixLength = 4;
    private String queueSearchBase = "ou=Queue,ou=Destination,ou=ActiveMQ,ou=system";
    private String topicSearchBase = "ou=Topic,ou=Destination,ou=ActiveMQ,ou=system";
    private String tempSearchBase = "ou=Temp,ou=Destination,ou=ActiveMQ,ou=system";
    private String permissionGroupMemberAttribute = "member";
    private String adminPermissionGroupSearchFilter = "(cn=Admin)";
    private String readPermissionGroupSearchFilter = "(cn=Read)";
    private String writePermissionGroupSearchFilter = "(cn=Write)";
    private boolean legacyGroupMapping = true;
    private String groupObjectClass = "groupOfNames";
    private String userObjectClass = "person";
    private String groupNameAttribute = "cn";
    private String userNameAttribute = "uid";
    private int refreshInterval = -1;
    private boolean refreshDisabled = false;
    protected String groupClass = "org.apache.activemq.jaas.GroupPrincipal";
    private long lastUpdated = -1L;
    private static String ANY_DESCENDANT = "\\$";
    protected DirContext context;
    private EventDirContext eventContext;
    private final AtomicReference<DefaultAuthorizationMap> map = new AtomicReference<DefaultAuthorizationMap>(new DefaultAuthorizationMap());
    private final ThreadPoolExecutor updaterService;
    protected Map<ActiveMQDestination, AuthorizationEntry> entries = new ConcurrentHashMap<ActiveMQDestination, AuthorizationEntry>();

    public SimpleCachedLDAPAuthorizationMap() {
        this.updaterService = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2), new ThreadFactory(){

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "SimpleCachedLDAPAuthorizationMap update thread");
            }
        });
        this.updaterService.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
    }

    protected DirContext createContext() throws NamingException {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
        if (this.connectionUsername == null || "".equals(this.connectionUsername)) {
            throw new NamingException("Empty username is not allowed");
        }
        env.put("java.naming.security.principal", this.connectionUsername);
        if (this.connectionPassword == null || "".equals(this.connectionPassword)) {
            throw new NamingException("Empty password is not allowed");
        }
        env.put("java.naming.security.credentials", this.connectionPassword);
        env.put("java.naming.security.protocol", this.connectionProtocol);
        env.put("java.naming.provider.url", this.connectionURL);
        env.put("java.naming.security.authentication", this.authentication);
        return new InitialDirContext(env);
    }

    protected boolean isContextAlive() {
        boolean alive = false;
        if (this.context != null) {
            try {
                this.context.getAttributes("");
                alive = true;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return alive;
    }

    protected DirContext open() throws NamingException {
        if (this.isContextAlive()) {
            return this.context;
        }
        try {
            this.context = this.createContext();
            if (this.refreshInterval == -1 && !this.refreshDisabled) {
                this.eventContext = (EventDirContext)this.context.lookup("");
                SearchControls constraints = new SearchControls();
                constraints.setSearchScope(2);
                for (PermissionType permissionType : PermissionType.values()) {
                    this.eventContext.addNamingListener(this.queueSearchBase, this.getFilterForPermissionType(permissionType), constraints, (NamingListener)new CachedLDAPAuthorizationMapNamespaceChangeListener(DestinationType.QUEUE, permissionType));
                }
                this.eventContext.addNamingListener(this.queueSearchBase, "cn=*", new SearchControls(), (NamingListener)new CachedLDAPAuthorizationMapNamespaceChangeListener(DestinationType.QUEUE, null));
                for (PermissionType permissionType : PermissionType.values()) {
                    this.eventContext.addNamingListener(this.topicSearchBase, this.getFilterForPermissionType(permissionType), constraints, (NamingListener)new CachedLDAPAuthorizationMapNamespaceChangeListener(DestinationType.TOPIC, permissionType));
                }
                this.eventContext.addNamingListener(this.topicSearchBase, "cn=*", new SearchControls(), (NamingListener)new CachedLDAPAuthorizationMapNamespaceChangeListener(DestinationType.TOPIC, null));
                for (PermissionType permissionType : PermissionType.values()) {
                    this.eventContext.addNamingListener(this.tempSearchBase, this.getFilterForPermissionType(permissionType), constraints, (NamingListener)new CachedLDAPAuthorizationMapNamespaceChangeListener(DestinationType.TEMP, permissionType));
                }
            }
        }
        catch (NamingException e) {
            this.context = null;
            throw e;
        }
        return this.context;
    }

    protected synchronized void query() throws Exception {
        DirContext currentContext = this.open();
        this.entries.clear();
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(2);
        DefaultAuthorizationMap newMap = new DefaultAuthorizationMap();
        for (PermissionType permissionType : PermissionType.values()) {
            try {
                this.processQueryResults(newMap, currentContext.search(this.queueSearchBase, this.getFilterForPermissionType(permissionType), constraints), DestinationType.QUEUE, permissionType);
            }
            catch (Exception e) {
                LOG.error("Policy not applied!.  Error processing policy under '{}' with filter '{}'", (Object)new Object[]{this.queueSearchBase, this.getFilterForPermissionType(permissionType)}, (Object)e);
            }
        }
        for (PermissionType permissionType : PermissionType.values()) {
            try {
                this.processQueryResults(newMap, currentContext.search(this.topicSearchBase, this.getFilterForPermissionType(permissionType), constraints), DestinationType.TOPIC, permissionType);
            }
            catch (Exception e) {
                LOG.error("Policy not applied!.  Error processing policy under '{}' with filter '{}'", (Object)new Object[]{this.topicSearchBase, this.getFilterForPermissionType(permissionType)}, (Object)e);
            }
        }
        for (PermissionType permissionType : PermissionType.values()) {
            try {
                this.processQueryResults(newMap, currentContext.search(this.tempSearchBase, this.getFilterForPermissionType(permissionType), constraints), DestinationType.TEMP, permissionType);
            }
            catch (Exception e) {
                LOG.error("Policy not applied!.  Error processing policy under '{}' with filter '{}'", (Object)new Object[]{this.tempSearchBase, this.getFilterForPermissionType(permissionType)}, (Object)e);
            }
        }
        newMap.setAuthorizationEntries(new ArrayList<DestinationMapEntry>(this.entries.values()));
        newMap.setGroupClass(this.groupClass);
        this.map.set(newMap);
        this.updated();
    }

    protected void processQueryResults(DefaultAuthorizationMap map, NamingEnumeration<SearchResult> results, DestinationType destinationType, PermissionType permissionType) throws Exception {
        while (results.hasMore()) {
            SearchResult result = results.next();
            AuthorizationEntry entry = null;
            try {
                entry = this.getEntry(map, new LdapName(result.getNameInNamespace()), destinationType);
            }
            catch (Exception e) {
                LOG.error("Policy not applied!  Error parsing authorization policy entry under {}", (Object)result.getNameInNamespace(), (Object)e);
                continue;
            }
            this.applyACL(entry, result, permissionType);
        }
    }

    protected void updated() {
        this.lastUpdated = System.currentTimeMillis();
    }

    protected AuthorizationEntry getEntry(DefaultAuthorizationMap map, LdapName dn, DestinationType destinationType) {
        AuthorizationEntry entry = null;
        switch (destinationType) {
            case TEMP: {
                if (dn.size() != this.getPrefixLengthForDestinationType(destinationType) + 1) {
                    throw new IllegalArgumentException("Malformed policy structure for a temporary destination policy entry.  The permission group entries should be immediately below the temporary policy base DN.");
                }
                entry = map.getTempDestinationAuthorizationEntry();
                if (entry != null) break;
                entry = new TempDestinationAuthorizationEntry();
                map.setTempDestinationAuthorizationEntry((TempDestinationAuthorizationEntry)entry);
                break;
            }
            case QUEUE: 
            case TOPIC: {
                if (dn.size() != this.getPrefixLengthForDestinationType(destinationType) + 2) {
                    throw new IllegalArgumentException("Malformed policy structure for a queue or topic destination policy entry.  The destination pattern and permission group entries should be nested below the queue or topic policy base DN.");
                }
                ActiveMQDestination dest = this.formatDestination(dn, destinationType);
                if (dest == null || (entry = this.entries.get(dest)) != null) break;
                entry = new AuthorizationEntry();
                entry.setDestination(dest);
                this.entries.put(dest, entry);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown destination type " + (Object)((Object)destinationType));
            }
        }
        return entry;
    }

    protected void applyACL(AuthorizationEntry entry, SearchResult result, PermissionType permissionType) throws NamingException {
        Attribute memberAttribute = result.getAttributes().get(this.permissionGroupMemberAttribute);
        NamingEnumeration<?> memberAttributeEnum = memberAttribute.getAll();
        HashSet<Object> members = new HashSet<Object>();
        while (memberAttributeEnum.hasMoreElements()) {
            String memberDn = (String)memberAttributeEnum.nextElement();
            boolean group = false;
            boolean user = false;
            String principalName = null;
            if (!this.legacyGroupMapping) {
                Attributes memberAttributes;
                try {
                    memberAttributes = this.context.getAttributes(memberDn, new String[]{"objectClass", this.groupNameAttribute, this.userNameAttribute});
                }
                catch (NamingException e) {
                    LOG.error("Policy not applied! Unknown member {} in policy entry {}", (Object)new Object[]{memberDn, result.getNameInNamespace()}, (Object)e);
                    continue;
                }
                Attribute memberEntryObjectClassAttribute = memberAttributes.get("objectClass");
                NamingEnumeration<?> memberEntryObjectClassAttributeEnum = memberEntryObjectClassAttribute.getAll();
                while (memberEntryObjectClassAttributeEnum.hasMoreElements()) {
                    Attribute name;
                    String objectClass = (String)memberEntryObjectClassAttributeEnum.nextElement();
                    if (objectClass.equalsIgnoreCase(this.groupObjectClass)) {
                        group = true;
                        name = memberAttributes.get(this.groupNameAttribute);
                        if (name == null) {
                            LOG.error("Policy not applied! Group {} does not have name attribute {} under entry {}", memberDn, this.groupNameAttribute, result.getNameInNamespace());
                            break;
                        }
                        principalName = (String)name.get();
                    }
                    if (!objectClass.equalsIgnoreCase(this.userObjectClass)) continue;
                    user = true;
                    name = memberAttributes.get(this.userNameAttribute);
                    if (name == null) {
                        LOG.error("Policy not applied! User {} does not have name attribute {} under entry {}", memberDn, this.userNameAttribute, result.getNameInNamespace());
                        break;
                    }
                    principalName = (String)name.get();
                }
            } else {
                group = true;
                principalName = memberDn.replaceAll("(cn|CN)=", "");
            }
            if (!group && !user || group && user) {
                LOG.error("Policy not applied! Can't determine type of member {} under entry {}", (Object)memberDn, (Object)result.getNameInNamespace());
                continue;
            }
            if (principalName == null) continue;
            DefaultAuthorizationMap map = this.map.get();
            if (group && !user) {
                try {
                    members.add(DefaultAuthorizationMap.createGroupPrincipal(principalName, map.getGroupClass()));
                    continue;
                }
                catch (Exception e) {
                    NamingException ne = new NamingException("Can't create a group " + principalName + " of class " + map.getGroupClass());
                    ne.initCause(e);
                    throw ne;
                }
            }
            if (group || !user) continue;
            members.add(new UserPrincipal(principalName));
        }
        try {
            this.applyAcl(entry, permissionType, members);
        }
        catch (Exception e) {
            LOG.error("Policy not applied! Error adding principals to ACL under {}", (Object)result.getNameInNamespace(), (Object)e);
        }
    }

    protected void applyAcl(AuthorizationEntry entry, PermissionType permissionType, Set<Object> acls) {
        switch (permissionType) {
            case READ: {
                entry.setReadACLs(acls);
                break;
            }
            case WRITE: {
                entry.setWriteACLs(acls);
                break;
            }
            case ADMIN: {
                entry.setAdminACLs(acls);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown permission " + (Object)((Object)permissionType) + ".");
            }
        }
    }

    protected ActiveMQDestination formatDestination(LdapName dn, DestinationType destinationType) {
        ActiveMQDestination destination = null;
        switch (destinationType) {
            case QUEUE: 
            case TOPIC: {
                if (dn.size() == this.getPrefixLengthForDestinationType(destinationType) + 2) {
                    destination = this.formatDestination(dn.getRdn(dn.size() - 2), destinationType);
                    break;
                }
                if (dn.size() == this.getPrefixLengthForDestinationType(destinationType) + 1) {
                    destination = this.formatDestination(dn.getRdn(dn.size() - 1), destinationType);
                    break;
                }
                throw new IllegalArgumentException("Malformed DN for representing a permission or destination entry.");
            }
            default: {
                throw new IllegalArgumentException("Cannot format destination for destination type " + (Object)((Object)destinationType));
            }
        }
        return destination;
    }

    protected ActiveMQDestination formatDestination(Rdn destinationName, DestinationType destinationType) {
        ActiveMQDestination dest = null;
        switch (destinationType) {
            case QUEUE: {
                dest = new ActiveMQQueue(this.formatDestinationName(destinationName));
                break;
            }
            case TOPIC: {
                dest = new ActiveMQTopic(this.formatDestinationName(destinationName));
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown destination type: " + (Object)((Object)destinationType));
            }
        }
        return dest;
    }

    protected String formatDestinationName(Rdn destinationName) {
        return destinationName.getValue().toString().replaceAll(ANY_DESCENDANT, ">");
    }

    protected <T> Set<T> transcribeSet(Set<T> source2) {
        if (source2 != null) {
            return new HashSet<T>(source2);
        }
        return null;
    }

    protected String getFilterForPermissionType(PermissionType permissionType) {
        String filter2 = null;
        switch (permissionType) {
            case ADMIN: {
                filter2 = this.adminPermissionGroupSearchFilter;
                break;
            }
            case READ: {
                filter2 = this.readPermissionGroupSearchFilter;
                break;
            }
            case WRITE: {
                filter2 = this.writePermissionGroupSearchFilter;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown permission type " + (Object)((Object)permissionType));
            }
        }
        return filter2;
    }

    protected int getPrefixLengthForDestinationType(DestinationType destinationType) {
        int filter2 = 0;
        switch (destinationType) {
            case QUEUE: {
                filter2 = this.queuePrefixLength;
                break;
            }
            case TOPIC: {
                filter2 = this.topicPrefixLength;
                break;
            }
            case TEMP: {
                filter2 = this.tempPrefixLength;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown permission type " + (Object)((Object)destinationType));
            }
        }
        return filter2;
    }

    protected void checkForUpdates() {
        if (this.lastUpdated == -1L) {
            try {
                this.query();
                return;
            }
            catch (Exception e) {
                LOG.error("Error updating authorization map.  Partial policy may be applied until the next successful update.", e);
            }
        }
        if (this.context != null && this.refreshDisabled) {
            return;
        }
        if (this.context == null || !this.refreshDisabled && this.refreshInterval != -1 && System.currentTimeMillis() >= this.lastUpdated + (long)this.refreshInterval) {
            this.updaterService.execute(new Runnable(){

                @Override
                public void run() {
                    if (SimpleCachedLDAPAuthorizationMap.this.context == null || !SimpleCachedLDAPAuthorizationMap.this.refreshDisabled && SimpleCachedLDAPAuthorizationMap.this.refreshInterval != -1 && System.currentTimeMillis() >= SimpleCachedLDAPAuthorizationMap.this.lastUpdated + (long)SimpleCachedLDAPAuthorizationMap.this.refreshInterval) {
                        if (!SimpleCachedLDAPAuthorizationMap.this.isContextAlive()) {
                            try {
                                SimpleCachedLDAPAuthorizationMap.this.context = SimpleCachedLDAPAuthorizationMap.this.createContext();
                            }
                            catch (NamingException ne) {
                                return;
                            }
                        }
                        LOG.debug("Updating authorization map!");
                        try {
                            SimpleCachedLDAPAuthorizationMap.this.query();
                        }
                        catch (Exception e) {
                            LOG.error("Error updating authorization map.  Partial policy may be applied until the next successful update.", e);
                        }
                    }
                }
            });
        }
    }

    public Set<Object> getTempDestinationAdminACLs() {
        this.checkForUpdates();
        DefaultAuthorizationMap map = this.map.get();
        return this.transcribeSet(map.getTempDestinationAdminACLs());
    }

    public Set<Object> getTempDestinationReadACLs() {
        this.checkForUpdates();
        DefaultAuthorizationMap map = this.map.get();
        return this.transcribeSet(map.getTempDestinationReadACLs());
    }

    public Set<Object> getTempDestinationWriteACLs() {
        this.checkForUpdates();
        DefaultAuthorizationMap map = this.map.get();
        return this.transcribeSet(map.getTempDestinationWriteACLs());
    }

    public Set<Object> getAdminACLs(ActiveMQDestination destination) {
        this.checkForUpdates();
        DefaultAuthorizationMap map = this.map.get();
        return map.getAdminACLs(destination);
    }

    public Set<Object> getReadACLs(ActiveMQDestination destination) {
        this.checkForUpdates();
        DefaultAuthorizationMap map = this.map.get();
        return map.getReadACLs(destination);
    }

    public Set<Object> getWriteACLs(ActiveMQDestination destination) {
        this.checkForUpdates();
        DefaultAuthorizationMap map = this.map.get();
        return map.getWriteACLs(destination);
    }

    public void objectAdded(NamingEvent namingEvent, DestinationType destinationType, PermissionType permissionType) {
        LOG.debug("Adding object: {}", (Object)namingEvent.getNewBinding());
        SearchResult result = (SearchResult)namingEvent.getNewBinding();
        try {
            DefaultAuthorizationMap map = this.map.get();
            LdapName name = new LdapName(result.getName());
            AuthorizationEntry entry = this.getEntry(map, name, destinationType);
            this.applyACL(entry, result, permissionType);
            if (!(entry instanceof TempDestinationAuthorizationEntry)) {
                map.put(entry.getDestination(), entry);
            }
        }
        catch (InvalidNameException e) {
            LOG.error("Policy not applied!  Error parsing DN for addition of {}", (Object)result.getName(), (Object)e);
        }
        catch (Exception e) {
            LOG.error("Policy not applied!  Error processing object addition for addition of {}", (Object)result.getName(), (Object)e);
        }
    }

    public void objectRemoved(NamingEvent namingEvent, DestinationType destinationType, PermissionType permissionType) {
        LOG.debug("Removing object: {}", (Object)namingEvent.getOldBinding());
        Binding result = namingEvent.getOldBinding();
        try {
            DefaultAuthorizationMap map = this.map.get();
            LdapName name = new LdapName(result.getName());
            AuthorizationEntry entry = this.getEntry(map, name, destinationType);
            this.applyAcl(entry, permissionType, new HashSet<Object>());
        }
        catch (InvalidNameException e) {
            LOG.error("Policy not applied!  Error parsing DN for object removal for removal of {}", (Object)result.getName(), (Object)e);
        }
        catch (Exception e) {
            LOG.error("Policy not applied!  Error processing object removal for removal of {}", (Object)result.getName(), (Object)e);
        }
    }

    public void objectRenamed(NamingEvent namingEvent, DestinationType destinationType, PermissionType permissionType) {
        Binding oldBinding = namingEvent.getOldBinding();
        Binding newBinding = namingEvent.getNewBinding();
        LOG.debug("Renaming object: {} to {}", (Object)oldBinding, (Object)newBinding);
        try {
            LdapName oldName = new LdapName(oldBinding.getName());
            ActiveMQDestination oldDest = this.formatDestination(oldName, destinationType);
            LdapName newName = new LdapName(newBinding.getName());
            ActiveMQDestination newDest = this.formatDestination(newName, destinationType);
            if (permissionType != null) {
                this.objectRemoved(namingEvent, destinationType, permissionType);
                SearchControls controls = new SearchControls();
                controls.setSearchScope(0);
                boolean matchedToType = false;
                for (PermissionType newPermissionType : PermissionType.values()) {
                    NamingEnumeration<SearchResult> results = this.context.search((Name)newName, this.getFilterForPermissionType(newPermissionType), controls);
                    if (!results.hasMore()) continue;
                    this.objectAdded(namingEvent, destinationType, newPermissionType);
                    matchedToType = true;
                    break;
                }
                if (!matchedToType) {
                    LOG.error("Policy not applied!  Error processing object rename for rename of {} to {}. Could not determine permission type of new object.", (Object)oldBinding.getName(), (Object)newBinding.getName());
                }
            } else if (oldDest != null && newDest != null) {
                AuthorizationEntry entry = this.entries.remove(oldDest);
                if (entry != null) {
                    entry.setDestination(newDest);
                    DefaultAuthorizationMap map = this.map.get();
                    map.put(newDest, entry);
                    map.remove(oldDest, entry);
                    this.entries.put(newDest, entry);
                } else {
                    LOG.warn("No authorization entry for {}", (Object)oldDest);
                }
            }
        }
        catch (InvalidNameException e) {
            LOG.error("Policy not applied!  Error parsing DN for object rename for rename of {} to {}", (Object)new Object[]{oldBinding.getName(), newBinding.getName()}, (Object)e);
        }
        catch (Exception e) {
            LOG.error("Policy not applied!  Error processing object rename for rename of {} to {}", (Object)new Object[]{oldBinding.getName(), newBinding.getName()}, (Object)e);
        }
    }

    public void objectChanged(NamingEvent namingEvent, DestinationType destinationType, PermissionType permissionType) {
        LOG.debug("Changing object {} to {}", (Object)namingEvent.getOldBinding(), (Object)namingEvent.getNewBinding());
        this.objectRemoved(namingEvent, destinationType, permissionType);
        this.objectAdded(namingEvent, destinationType, permissionType);
    }

    public void namingExceptionThrown(NamingExceptionEvent namingExceptionEvent) {
        this.context = null;
        LOG.error("Caught unexpected exception.", namingExceptionEvent.getException());
    }

    public void afterPropertiesSet() throws Exception {
        this.query();
    }

    public void destroy() throws Exception {
        if (this.eventContext != null) {
            this.eventContext.close();
            this.eventContext = null;
        }
        if (this.context != null) {
            this.context.close();
            this.context = null;
        }
    }

    public String getConnectionURL() {
        return this.connectionURL;
    }

    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    public String getConnectionUsername() {
        return this.connectionUsername;
    }

    public void setConnectionUsername(String connectionUsername) {
        this.connectionUsername = connectionUsername;
    }

    public String getConnectionPassword() {
        return this.connectionPassword;
    }

    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }

    public String getConnectionProtocol() {
        return this.connectionProtocol;
    }

    public void setConnectionProtocol(String connectionProtocol) {
        this.connectionProtocol = connectionProtocol;
    }

    public String getAuthentication() {
        return this.authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getQueueSearchBase() {
        return this.queueSearchBase;
    }

    public void setQueueSearchBase(String queueSearchBase) {
        try {
            LdapName baseName = new LdapName(queueSearchBase);
            this.queuePrefixLength = baseName.size();
            this.queueSearchBase = queueSearchBase;
        }
        catch (InvalidNameException e) {
            throw new IllegalArgumentException("Invalid base DN value " + queueSearchBase, e);
        }
    }

    public String getTopicSearchBase() {
        return this.topicSearchBase;
    }

    public void setTopicSearchBase(String topicSearchBase) {
        try {
            LdapName baseName = new LdapName(topicSearchBase);
            this.topicPrefixLength = baseName.size();
            this.topicSearchBase = topicSearchBase;
        }
        catch (InvalidNameException e) {
            throw new IllegalArgumentException("Invalid base DN value " + topicSearchBase, e);
        }
    }

    public String getTempSearchBase() {
        return this.tempSearchBase;
    }

    public void setTempSearchBase(String tempSearchBase) {
        try {
            LdapName baseName = new LdapName(tempSearchBase);
            this.tempPrefixLength = baseName.size();
            this.tempSearchBase = tempSearchBase;
        }
        catch (InvalidNameException e) {
            throw new IllegalArgumentException("Invalid base DN value " + tempSearchBase, e);
        }
    }

    public String getPermissionGroupMemberAttribute() {
        return this.permissionGroupMemberAttribute;
    }

    public void setPermissionGroupMemberAttribute(String permissionGroupMemberAttribute) {
        this.permissionGroupMemberAttribute = permissionGroupMemberAttribute;
    }

    public String getAdminPermissionGroupSearchFilter() {
        return this.adminPermissionGroupSearchFilter;
    }

    public void setAdminPermissionGroupSearchFilter(String adminPermissionGroupSearchFilter) {
        this.adminPermissionGroupSearchFilter = adminPermissionGroupSearchFilter;
    }

    public String getReadPermissionGroupSearchFilter() {
        return this.readPermissionGroupSearchFilter;
    }

    public void setReadPermissionGroupSearchFilter(String readPermissionGroupSearchFilter) {
        this.readPermissionGroupSearchFilter = readPermissionGroupSearchFilter;
    }

    public String getWritePermissionGroupSearchFilter() {
        return this.writePermissionGroupSearchFilter;
    }

    public void setWritePermissionGroupSearchFilter(String writePermissionGroupSearchFilter) {
        this.writePermissionGroupSearchFilter = writePermissionGroupSearchFilter;
    }

    public boolean isLegacyGroupMapping() {
        return this.legacyGroupMapping;
    }

    public void setLegacyGroupMapping(boolean legacyGroupMapping) {
        this.legacyGroupMapping = legacyGroupMapping;
    }

    public String getGroupObjectClass() {
        return this.groupObjectClass;
    }

    public void setGroupObjectClass(String groupObjectClass) {
        this.groupObjectClass = groupObjectClass;
    }

    public String getUserObjectClass() {
        return this.userObjectClass;
    }

    public void setUserObjectClass(String userObjectClass) {
        this.userObjectClass = userObjectClass;
    }

    public String getGroupNameAttribute() {
        return this.groupNameAttribute;
    }

    public void setGroupNameAttribute(String groupNameAttribute) {
        this.groupNameAttribute = groupNameAttribute;
    }

    public String getUserNameAttribute() {
        return this.userNameAttribute;
    }

    public void setUserNameAttribute(String userNameAttribute) {
        this.userNameAttribute = userNameAttribute;
    }

    public boolean isRefreshDisabled() {
        return this.refreshDisabled;
    }

    public void setRefreshDisabled(boolean refreshDisabled) {
        this.refreshDisabled = refreshDisabled;
    }

    public int getRefreshInterval() {
        return this.refreshInterval;
    }

    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public String getGroupClass() {
        return this.groupClass;
    }

    public void setGroupClass(String groupClass) {
        this.groupClass = groupClass;
        this.map.get().setGroupClass(groupClass);
    }

    protected class CachedLDAPAuthorizationMapNamespaceChangeListener
    implements NamespaceChangeListener,
    ObjectChangeListener {
        private final DestinationType destinationType;
        private final PermissionType permissionType;

        public CachedLDAPAuthorizationMapNamespaceChangeListener(DestinationType destinationType, PermissionType permissionType) {
            this.destinationType = destinationType;
            this.permissionType = permissionType;
        }

        @Override
        public void namingExceptionThrown(NamingExceptionEvent evt) {
            SimpleCachedLDAPAuthorizationMap.this.namingExceptionThrown(evt);
        }

        @Override
        public void objectAdded(NamingEvent evt) {
            if (this.permissionType != null) {
                SimpleCachedLDAPAuthorizationMap.this.objectAdded(evt, this.destinationType, this.permissionType);
            }
        }

        @Override
        public void objectRemoved(NamingEvent evt) {
            if (this.permissionType != null) {
                SimpleCachedLDAPAuthorizationMap.this.objectRemoved(evt, this.destinationType, this.permissionType);
            }
        }

        @Override
        public void objectRenamed(NamingEvent evt) {
            SimpleCachedLDAPAuthorizationMap.this.objectRenamed(evt, this.destinationType, this.permissionType);
        }

        @Override
        public void objectChanged(NamingEvent evt) {
            if (this.permissionType != null) {
                SimpleCachedLDAPAuthorizationMap.this.objectChanged(evt, this.destinationType, this.permissionType);
            }
        }
    }

    protected static enum PermissionType {
        READ,
        WRITE,
        ADMIN;

    }

    protected static enum DestinationType {
        QUEUE,
        TOPIC,
        TEMP;

    }
}

