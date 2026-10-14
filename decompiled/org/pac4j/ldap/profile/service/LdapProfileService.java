/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.ldap.profile.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ldaptive.AddOperation;
import org.ldaptive.AddRequest;
import org.ldaptive.AttributeModification;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.Credential;
import org.ldaptive.DeleteOperation;
import org.ldaptive.DeleteRequest;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.ModifyOperation;
import org.ldaptive.ModifyRequest;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.auth.AuthenticationRequest;
import org.ldaptive.auth.AuthenticationResponse;
import org.ldaptive.auth.AuthenticationResultCode;
import org.ldaptive.auth.Authenticator;
import org.ldaptive.handler.ResultPredicate;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.exception.AccountNotFoundException;
import org.pac4j.core.exception.BadCredentialsException;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.profile.definition.CommonProfileDefinition;
import org.pac4j.core.profile.service.AbstractProfileService;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.serializer.JsonSerializer;
import org.pac4j.ldap.profile.LdapProfile;

public class LdapProfileService
extends AbstractProfileService<LdapProfile> {
    private Authenticator ldapAuthenticator;
    private ConnectionFactory connectionFactory;
    private String usersDn;

    public LdapProfileService() {
    }

    public LdapProfileService(Authenticator ldapAuthenticator) {
        this.ldapAuthenticator = ldapAuthenticator;
    }

    public LdapProfileService(Authenticator ldapAuthenticator, String attributes) {
        this.ldapAuthenticator = ldapAuthenticator;
        this.setAttributes(attributes);
    }

    public LdapProfileService(ConnectionFactory connectionFactory, Authenticator ldapAuthenticator, String usersDn) {
        this.connectionFactory = connectionFactory;
        this.ldapAuthenticator = ldapAuthenticator;
        this.usersDn = usersDn;
    }

    public LdapProfileService(ConnectionFactory connectionFactory, Authenticator ldapAuthenticator, String attributes, String usersDn) {
        this.connectionFactory = connectionFactory;
        this.ldapAuthenticator = ldapAuthenticator;
        this.setAttributes(attributes);
        this.usersDn = usersDn;
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        CommonHelper.assertNotNull("ldapAuthenticator", this.ldapAuthenticator);
        CommonHelper.assertNotNull("connectionFactory", this.connectionFactory);
        CommonHelper.assertNull("passwordEncoder", this.getPasswordEncoder());
        CommonHelper.assertNotBlank("usersDn", this.usersDn);
        this.defaultProfileDefinition(new CommonProfileDefinition(x -> new LdapProfile()));
        this.setSerializer(new JsonSerializer(LdapProfile.class));
        super.internalInit(forceReinit);
    }

    @Override
    protected void insert(Map<String, Object> attributes) {
        attributes.put("objectClass", "person");
        LdapEntry ldapEntry = (LdapEntry)LdapEntry.builder().dn(this.getEntryId(attributes)).attributes(this.getLdapAttributes(attributes)).build();
        try {
            AddOperation add = new AddOperation(this.connectionFactory);
            add.setThrowCondition(ResultPredicate.NOT_SUCCESS);
            add.execute(new AddRequest(ldapEntry.getDn(), ldapEntry.getAttributes()));
        }
        catch (LdapException e) {
            throw new TechnicalException(e);
        }
    }

    protected String getEntryId(Map<String, Object> attributes) {
        return this.getIdAttribute() + "=" + attributes.get(this.getIdAttribute()) + "," + this.usersDn;
    }

    protected List<LdapAttribute> getLdapAttributes(Map<String, Object> attributes) {
        ArrayList<LdapAttribute> ldapAttributes = new ArrayList<LdapAttribute>();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            LdapAttribute ldapAttribute;
            Object value = entry.getValue();
            if (value == null) continue;
            String key = entry.getKey();
            if (value instanceof String) {
                ldapAttribute = new LdapAttribute(key, (String)value);
            } else if (value instanceof List) {
                List list = (List)value;
                ldapAttribute = new LdapAttribute(key, list.toArray(new String[list.size()]));
            } else {
                ldapAttribute = new LdapAttribute(key, value.toString());
            }
            ldapAttributes.add(ldapAttribute);
        }
        return ldapAttributes;
    }

    @Override
    protected void update(Map<String, Object> attributes) {
        try {
            ModifyOperation modify = new ModifyOperation(this.connectionFactory);
            modify.setThrowCondition(ResultPredicate.NOT_SUCCESS);
            ArrayList<AttributeModification> modifications = new ArrayList<AttributeModification>();
            for (LdapAttribute attribute : this.getLdapAttributes(attributes)) {
                modifications.add(new AttributeModification(AttributeModification.Type.REPLACE, attribute));
            }
            ModifyRequest modifyRequest = new ModifyRequest(this.getEntryId(attributes), modifications.toArray(new AttributeModification[modifications.size()]));
            modify.execute(modifyRequest);
        }
        catch (LdapException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    protected void deleteById(String id) {
        try {
            DeleteOperation delete = new DeleteOperation(this.connectionFactory);
            delete.setThrowCondition(ResultPredicate.NOT_SUCCESS);
            delete.execute(new DeleteRequest(this.getIdAttribute() + "=" + id + "," + this.usersDn));
        }
        catch (LdapException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    protected List<Map<String, Object>> read(List<String> names, String key, String value) {
        ArrayList<Map<String, Object>> listAttributes = new ArrayList<Map<String, Object>>();
        try {
            SearchOperation search = new SearchOperation(this.connectionFactory);
            SearchResponse result = search.execute(new SearchRequest(this.usersDn, key + "=" + value, names.toArray(new String[names.size()])));
            for (LdapEntry entry : result.getEntries()) {
                listAttributes.add(this.getAttributesFromEntry(entry));
            }
        }
        catch (LdapException e) {
            throw new TechnicalException(e);
        }
        return listAttributes;
    }

    protected Map<String, Object> getAttributesFromEntry(LdapEntry entry) {
        HashMap<String, Object> attributes = new HashMap<String, Object>();
        for (LdapAttribute attribute : entry.getAttributes()) {
            String name = attribute.getName();
            if (attribute.size() > 1) {
                attributes.put(name, attribute.getStringValues());
                continue;
            }
            attributes.put(name, attribute.getStringValue());
        }
        return attributes;
    }

    @Override
    public void validate(Credentials cred, WebContext context, SessionStore sessionStore) {
        AuthenticationResponse response;
        this.init();
        UsernamePasswordCredentials credentials = (UsernamePasswordCredentials)cred;
        String username = credentials.getUsername();
        CommonHelper.assertNotBlank("username", username);
        try {
            this.logger.debug("Attempting LDAP authentication for: {}", (Object)credentials);
            List<String> attributesToRead = this.defineAttributesToRead();
            AuthenticationRequest request = new AuthenticationRequest(username, new Credential(credentials.getPassword()), attributesToRead.toArray(new String[attributesToRead.size()]));
            response = this.ldapAuthenticator.authenticate(request);
        }
        catch (LdapException e) {
            throw new TechnicalException("Unexpected LDAP error", e);
        }
        this.logger.debug("LDAP response: {}", (Object)response);
        if (response.isSuccess()) {
            LdapEntry entry = response.getLdapEntry();
            ArrayList<Map<String, Object>> listAttributes = new ArrayList<Map<String, Object>>();
            listAttributes.add(this.getAttributesFromEntry(entry));
            LdapProfile profile = (LdapProfile)this.convertAttributesToProfile(listAttributes, username);
            credentials.setUserProfile(profile);
            return;
        }
        if (AuthenticationResultCode.DN_RESOLUTION_FAILURE == response.getAuthenticationResultCode()) {
            throw new AccountNotFoundException(username + " not found");
        }
        throw new BadCredentialsException("Invalid credentials for: " + username);
    }

    public ConnectionFactory getConnectionFactory() {
        return this.connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String getUsersDn() {
        return this.usersDn;
    }

    public void setUsersDn(String usersDn) {
        this.usersDn = usersDn;
    }

    public Authenticator getLdapAuthenticator() {
        return this.ldapAuthenticator;
    }

    public void setLdapAuthenticator(Authenticator ldapAuthenticator) {
        this.ldapAuthenticator = ldapAuthenticator;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "connectionFactory", this.connectionFactory, "ldapAuthenticator", this.ldapAuthenticator, "usersDn", this.usersDn, "idAttribute", this.getIdAttribute(), "attributes", this.getAttributes(), "profileDefinition", this.getProfileDefinition());
    }
}

