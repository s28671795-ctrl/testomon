/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.activemq.jaas.GroupPrincipal
 *  org.apache.activemq.jaas.LDAPLoginModule
 */
package org.apache.activemq.security;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import org.apache.activemq.advisory.AdvisorySupport;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.filter.DestinationMap;
import org.apache.activemq.jaas.GroupPrincipal;
import org.apache.activemq.jaas.LDAPLoginModule;
import org.apache.activemq.security.AuthorizationMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LDAPAuthorizationMap
implements AuthorizationMap {
    public static final String INITIAL_CONTEXT_FACTORY = "initialContextFactory";
    public static final String CONNECTION_URL = "connectionURL";
    public static final String CONNECTION_USERNAME = "connectionUsername";
    public static final String CONNECTION_PASSWORD = "connectionPassword";
    public static final String CONNECTION_PROTOCOL = "connectionProtocol";
    public static final String AUTHENTICATION = "authentication";
    public static final String TOPIC_SEARCH_MATCHING = "topicSearchMatching";
    public static final String TOPIC_SEARCH_SUBTREE = "topicSearchSubtree";
    public static final String QUEUE_SEARCH_MATCHING = "queueSearchMatching";
    public static final String QUEUE_SEARCH_SUBTREE = "queueSearchSubtree";
    public static final String ADMIN_BASE = "adminBase";
    public static final String ADMIN_ATTRIBUTE = "adminAttribute";
    public static final String READ_BASE = "readBase";
    public static final String READ_ATTRIBUTE = "readAttribute";
    public static final String WRITE_BASE = "writeBAse";
    public static final String WRITE_ATTRIBUTE = "writeAttribute";
    private static final Logger LOG = LoggerFactory.getLogger(LDAPLoginModule.class);
    private String initialContextFactory;
    private String connectionURL;
    private String connectionUsername;
    private String connectionPassword;
    private String connectionProtocol;
    private String authentication;
    private DirContext context;
    private MessageFormat topicSearchMatchingFormat;
    private MessageFormat queueSearchMatchingFormat;
    private String advisorySearchBase = "uid=ActiveMQ.Advisory,ou=topics,ou=destinations,o=ActiveMQ,dc=example,dc=com";
    private String tempSearchBase = "uid=ActiveMQ.Temp,ou=topics,ou=destinations,o=ActiveMQ,dc=example,dc=com";
    private boolean topicSearchSubtreeBool = true;
    private boolean queueSearchSubtreeBool = true;
    private boolean useAdvisorySearchBase = true;
    private String adminBase;
    private String adminAttribute;
    private String readBase;
    private String readAttribute;
    private String writeBase;
    private String writeAttribute;

    public LDAPAuthorizationMap() {
        this.initialContextFactory = "com.sun.jndi.ldap.LdapCtxFactory";
        this.connectionURL = "ldap://localhost:10389";
        this.connectionUsername = "uid=admin,ou=system";
        this.connectionProtocol = "s";
        this.authentication = "simple";
        this.topicSearchMatchingFormat = new MessageFormat("uid={0},ou=topics,ou=destinations,o=ActiveMQ,dc=example,dc=com");
        this.queueSearchMatchingFormat = new MessageFormat("uid={0},ou=queues,ou=destinations,o=ActiveMQ,dc=example,dc=com");
        this.adminBase = "(cn=admin)";
        this.adminAttribute = "uniqueMember";
        this.readBase = "(cn=read)";
        this.readAttribute = "uniqueMember";
        this.writeBase = "(cn=write)";
        this.writeAttribute = "uniqueMember";
    }

    public LDAPAuthorizationMap(Map<String, String> options) {
        this.initialContextFactory = options.get(INITIAL_CONTEXT_FACTORY);
        this.connectionURL = options.get(CONNECTION_URL);
        this.connectionUsername = options.get(CONNECTION_USERNAME);
        this.connectionPassword = options.get(CONNECTION_PASSWORD);
        this.connectionProtocol = options.get(CONNECTION_PROTOCOL);
        this.authentication = options.get(AUTHENTICATION);
        this.adminBase = options.get(ADMIN_BASE);
        this.adminAttribute = options.get(ADMIN_ATTRIBUTE);
        this.readBase = options.get(READ_BASE);
        this.readAttribute = options.get(READ_ATTRIBUTE);
        this.writeBase = options.get(WRITE_BASE);
        this.writeAttribute = options.get(WRITE_ATTRIBUTE);
        String topicSearchMatching = options.get(TOPIC_SEARCH_MATCHING);
        String topicSearchSubtree = options.get(TOPIC_SEARCH_SUBTREE);
        String queueSearchMatching = options.get(QUEUE_SEARCH_MATCHING);
        String queueSearchSubtree = options.get(QUEUE_SEARCH_SUBTREE);
        this.topicSearchMatchingFormat = new MessageFormat(topicSearchMatching);
        this.queueSearchMatchingFormat = new MessageFormat(queueSearchMatching);
        this.topicSearchSubtreeBool = Boolean.valueOf(topicSearchSubtree);
        this.queueSearchSubtreeBool = Boolean.valueOf(queueSearchSubtree);
    }

    public Set<GroupPrincipal> getTempDestinationAdminACLs() {
        try {
            this.context = this.open();
        }
        catch (NamingException e) {
            LOG.error(e.toString());
            return new HashSet<GroupPrincipal>();
        }
        SearchControls constraints = new SearchControls();
        constraints.setReturningAttributes(new String[]{this.adminAttribute});
        return this.getACLs(this.tempSearchBase, constraints, this.adminBase, this.adminAttribute);
    }

    public Set<GroupPrincipal> getTempDestinationReadACLs() {
        try {
            this.context = this.open();
        }
        catch (NamingException e) {
            LOG.error(e.toString());
            return new HashSet<GroupPrincipal>();
        }
        SearchControls constraints = new SearchControls();
        constraints.setReturningAttributes(new String[]{this.readAttribute});
        return this.getACLs(this.tempSearchBase, constraints, this.readBase, this.readAttribute);
    }

    public Set<GroupPrincipal> getTempDestinationWriteACLs() {
        try {
            this.context = this.open();
        }
        catch (NamingException e) {
            LOG.error(e.toString());
            return new HashSet<GroupPrincipal>();
        }
        SearchControls constraints = new SearchControls();
        constraints.setReturningAttributes(new String[]{this.writeAttribute});
        return this.getACLs(this.tempSearchBase, constraints, this.writeBase, this.writeAttribute);
    }

    public Set<GroupPrincipal> getAdminACLs(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            return this.getCompositeACLs(destination, this.adminBase, this.adminAttribute);
        }
        return this.getACLs(destination, this.adminBase, this.adminAttribute);
    }

    public Set<GroupPrincipal> getReadACLs(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            return this.getCompositeACLs(destination, this.readBase, this.readAttribute);
        }
        return this.getACLs(destination, this.readBase, this.readAttribute);
    }

    public Set<GroupPrincipal> getWriteACLs(ActiveMQDestination destination) {
        if (destination.isComposite()) {
            return this.getCompositeACLs(destination, this.writeBase, this.writeAttribute);
        }
        return this.getACLs(destination, this.writeBase, this.writeAttribute);
    }

    public String getAdminAttribute() {
        return this.adminAttribute;
    }

    public void setAdminAttribute(String adminAttribute) {
        this.adminAttribute = adminAttribute;
    }

    public String getAdminBase() {
        return this.adminBase;
    }

    public void setAdminBase(String adminBase) {
        this.adminBase = adminBase;
    }

    public String getAuthentication() {
        return this.authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
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

    public DirContext getContext() {
        return this.context;
    }

    public void setContext(DirContext context) {
        this.context = context;
    }

    public String getInitialContextFactory() {
        return this.initialContextFactory;
    }

    public void setInitialContextFactory(String initialContextFactory) {
        this.initialContextFactory = initialContextFactory;
    }

    public MessageFormat getQueueSearchMatchingFormat() {
        return this.queueSearchMatchingFormat;
    }

    public void setQueueSearchMatchingFormat(MessageFormat queueSearchMatchingFormat) {
        this.queueSearchMatchingFormat = queueSearchMatchingFormat;
    }

    public boolean isQueueSearchSubtreeBool() {
        return this.queueSearchSubtreeBool;
    }

    public void setQueueSearchSubtreeBool(boolean queueSearchSubtreeBool) {
        this.queueSearchSubtreeBool = queueSearchSubtreeBool;
    }

    public String getReadAttribute() {
        return this.readAttribute;
    }

    public void setReadAttribute(String readAttribute) {
        this.readAttribute = readAttribute;
    }

    public String getReadBase() {
        return this.readBase;
    }

    public void setReadBase(String readBase) {
        this.readBase = readBase;
    }

    public MessageFormat getTopicSearchMatchingFormat() {
        return this.topicSearchMatchingFormat;
    }

    public void setTopicSearchMatchingFormat(MessageFormat topicSearchMatchingFormat) {
        this.topicSearchMatchingFormat = topicSearchMatchingFormat;
    }

    public boolean isTopicSearchSubtreeBool() {
        return this.topicSearchSubtreeBool;
    }

    public void setTopicSearchSubtreeBool(boolean topicSearchSubtreeBool) {
        this.topicSearchSubtreeBool = topicSearchSubtreeBool;
    }

    public String getWriteAttribute() {
        return this.writeAttribute;
    }

    public void setWriteAttribute(String writeAttribute) {
        this.writeAttribute = writeAttribute;
    }

    public String getWriteBase() {
        return this.writeBase;
    }

    public void setWriteBase(String writeBase) {
        this.writeBase = writeBase;
    }

    public boolean isUseAdvisorySearchBase() {
        return this.useAdvisorySearchBase;
    }

    public void setUseAdvisorySearchBase(boolean useAdvisorySearchBase) {
        this.useAdvisorySearchBase = useAdvisorySearchBase;
    }

    public String getAdvisorySearchBase() {
        return this.advisorySearchBase;
    }

    public void setAdvisorySearchBase(String advisorySearchBase) {
        this.advisorySearchBase = advisorySearchBase;
    }

    public String getTempSearchBase() {
        return this.tempSearchBase;
    }

    public void setTempSearchBase(String tempSearchBase) {
        this.tempSearchBase = tempSearchBase;
    }

    protected Set<GroupPrincipal> getCompositeACLs(ActiveMQDestination destination, String roleBase, String roleAttribute) {
        ActiveMQDestination dest;
        ActiveMQDestination[] dests = destination.getCompositeDestinations();
        Set acls = null;
        ActiveMQDestination[] activeMQDestinationArray = dests;
        int n = activeMQDestinationArray.length;
        for (int i = 0; i < n && (acls = DestinationMap.union(acls, this.getACLs(dest = activeMQDestinationArray[i], roleBase, roleAttribute))) != null && !acls.isEmpty(); ++i) {
        }
        return acls;
    }

    protected Set<GroupPrincipal> getACLs(ActiveMQDestination destination, String roleBase, String roleAttribute) {
        try {
            this.context = this.open();
        }
        catch (NamingException e) {
            LOG.error(e.toString());
            return new HashSet<GroupPrincipal>();
        }
        String destinationBase = "";
        SearchControls constraints = new SearchControls();
        if (AdvisorySupport.isAdvisoryTopic(destination) && this.useAdvisorySearchBase) {
            destinationBase = this.advisorySearchBase;
        } else {
            if ((destination.getDestinationType() & 1) == 1) {
                destinationBase = this.queueSearchMatchingFormat.format(new String[]{destination.getPhysicalName()});
                if (this.queueSearchSubtreeBool) {
                    constraints.setSearchScope(2);
                } else {
                    constraints.setSearchScope(1);
                }
            }
            if ((destination.getDestinationType() & 2) == 2) {
                destinationBase = this.topicSearchMatchingFormat.format(new String[]{destination.getPhysicalName()});
                if (this.topicSearchSubtreeBool) {
                    constraints.setSearchScope(2);
                } else {
                    constraints.setSearchScope(1);
                }
            }
        }
        constraints.setReturningAttributes(new String[]{roleAttribute});
        return this.getACLs(destinationBase, constraints, roleBase, roleAttribute);
    }

    protected Set<GroupPrincipal> getACLs(String destinationBase, SearchControls constraints, String roleBase, String roleAttribute) {
        try {
            HashSet<GroupPrincipal> roles = new HashSet<GroupPrincipal>();
            Set<String> acls = new HashSet<String>();
            NamingEnumeration<SearchResult> results = this.context.search(destinationBase, roleBase, constraints);
            while (results.hasMore()) {
                SearchResult result = results.next();
                Attributes attrs = result.getAttributes();
                if (attrs == null) continue;
                acls = this.addAttributeValues(roleAttribute, attrs, acls);
            }
            for (String roleName : acls) {
                LdapName ldapname = new LdapName(roleName);
                Rdn rdn = ldapname.getRdn(ldapname.size() - 1);
                LOG.debug("Found role: [" + rdn.getValue().toString() + "]");
                roles.add(new GroupPrincipal(rdn.getValue().toString()));
            }
            return roles;
        }
        catch (NamingException e) {
            LOG.error(e.toString());
            return new HashSet<GroupPrincipal>();
        }
    }

    protected Set<String> addAttributeValues(String attrId, Attributes attrs, Set<String> values2) throws NamingException {
        Attribute attr;
        if (attrId == null || attrs == null) {
            return values2;
        }
        if (values2 == null) {
            values2 = new HashSet<String>();
        }
        if ((attr = attrs.get(attrId)) == null) {
            return values2;
        }
        NamingEnumeration<?> e = attr.getAll();
        while (e.hasMore()) {
            String value = (String)e.next();
            values2.add(value);
        }
        return values2;
    }

    protected DirContext open() throws NamingException {
        if (this.context != null) {
            return this.context;
        }
        try {
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put("java.naming.factory.initial", this.initialContextFactory);
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
            this.context = new InitialDirContext(env);
        }
        catch (NamingException e) {
            LOG.error(e.toString());
            throw e;
        }
        return this.context;
    }
}

