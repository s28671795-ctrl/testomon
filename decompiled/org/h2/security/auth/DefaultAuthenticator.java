/*
 * Decompiled with CFR 0.152.
 */
package org.h2.security.auth;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.h2.api.CredentialsValidator;
import org.h2.api.UserToRolesMapper;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.Right;
import org.h2.engine.Role;
import org.h2.engine.Session;
import org.h2.engine.SessionLocal;
import org.h2.engine.SysProperties;
import org.h2.engine.User;
import org.h2.engine.UserBuilder;
import org.h2.message.Trace;
import org.h2.security.auth.AuthConfigException;
import org.h2.security.auth.AuthenticationException;
import org.h2.security.auth.AuthenticationInfo;
import org.h2.security.auth.Authenticator;
import org.h2.security.auth.ConfigProperties;
import org.h2.security.auth.Configurable;
import org.h2.security.auth.H2AuthConfig;
import org.h2.security.auth.H2AuthConfigXml;
import org.h2.security.auth.RealmConfig;
import org.h2.security.auth.UserToRolesMapperConfig;
import org.h2.security.auth.impl.AssignRealmNameRole;
import org.h2.security.auth.impl.JaasCredentialsValidator;
import org.h2.util.StringUtils;
import org.xml.sax.SAXException;

public class DefaultAuthenticator
implements Authenticator {
    public static final String DEFAULT_REALMNAME = "H2";
    private Map<String, CredentialsValidator> realms = new HashMap<String, CredentialsValidator>();
    private List<UserToRolesMapper> userToRolesMappers = new ArrayList<UserToRolesMapper>();
    private boolean allowUserRegistration;
    private boolean persistUsers;
    private boolean createMissingRoles;
    private boolean skipDefaultInitialization;
    private boolean initialized;
    private static DefaultAuthenticator instance;

    protected static final DefaultAuthenticator getInstance() {
        if (instance == null) {
            instance = new DefaultAuthenticator();
        }
        return instance;
    }

    public DefaultAuthenticator() {
    }

    public DefaultAuthenticator(boolean bl) {
        this.skipDefaultInitialization = bl;
    }

    public boolean isPersistUsers() {
        return this.persistUsers;
    }

    public void setPersistUsers(boolean bl) {
        this.persistUsers = bl;
    }

    public boolean isAllowUserRegistration() {
        return this.allowUserRegistration;
    }

    public void setAllowUserRegistration(boolean bl) {
        this.allowUserRegistration = bl;
    }

    public boolean isCreateMissingRoles() {
        return this.createMissingRoles;
    }

    public void setCreateMissingRoles(boolean bl) {
        this.createMissingRoles = bl;
    }

    public void addRealm(String string, CredentialsValidator credentialsValidator) {
        this.realms.put(StringUtils.toUpperEnglish(string), credentialsValidator);
    }

    public List<UserToRolesMapper> getUserToRolesMappers() {
        return this.userToRolesMappers;
    }

    public void setUserToRolesMappers(UserToRolesMapper ... userToRolesMapperArray) {
        ArrayList<UserToRolesMapper> arrayList = new ArrayList<UserToRolesMapper>();
        for (UserToRolesMapper userToRolesMapper : userToRolesMapperArray) {
            arrayList.add(userToRolesMapper);
        }
        this.userToRolesMappers = arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void init(Database database) throws AuthConfigException {
        if (this.skipDefaultInitialization) {
            return;
        }
        if (this.initialized) {
            return;
        }
        DefaultAuthenticator defaultAuthenticator = this;
        synchronized (defaultAuthenticator) {
            if (this.initialized) {
                return;
            }
            Trace trace = database.getTrace(2);
            URL uRL = null;
            try {
                String string = SysProperties.AUTH_CONFIG_FILE;
                if (string != null) {
                    if (trace.isDebugEnabled()) {
                        trace.debug("DefaultAuthenticator.config: configuration read from system property h2auth.configurationfile={0}", string);
                    }
                    uRL = new URL(string);
                }
                if (uRL == null) {
                    if (trace.isDebugEnabled()) {
                        trace.debug("DefaultAuthenticator.config: default configuration");
                    }
                    this.defaultConfiguration();
                } else {
                    this.configureFromUrl(uRL);
                }
            }
            catch (Exception exception) {
                trace.error(exception, "DefaultAuthenticator.config: an error occurred during configuration from {0} ", uRL);
                throw new AuthConfigException("Failed to configure authentication from " + uRL, exception);
            }
            this.initialized = true;
        }
    }

    private void defaultConfiguration() {
        this.createMissingRoles = false;
        this.allowUserRegistration = true;
        this.realms = new HashMap<String, CredentialsValidator>();
        JaasCredentialsValidator jaasCredentialsValidator = new JaasCredentialsValidator();
        jaasCredentialsValidator.configure(new ConfigProperties());
        this.realms.put(DEFAULT_REALMNAME, jaasCredentialsValidator);
        AssignRealmNameRole assignRealmNameRole = new AssignRealmNameRole();
        assignRealmNameRole.configure(new ConfigProperties());
        this.userToRolesMappers.add(assignRealmNameRole);
    }

    public void configureFromUrl(URL uRL) throws AuthenticationException, SAXException, IOException, ParserConfigurationException {
        H2AuthConfig h2AuthConfig = H2AuthConfigXml.parseFrom(uRL);
        this.configureFrom(h2AuthConfig);
    }

    private void configureFrom(H2AuthConfig h2AuthConfig) throws AuthenticationException {
        Configurable configurable;
        this.allowUserRegistration = h2AuthConfig.isAllowUserRegistration();
        this.createMissingRoles = h2AuthConfig.isCreateMissingRoles();
        HashMap<String, CredentialsValidator> hashMap = new HashMap<String, CredentialsValidator>();
        for (RealmConfig object : h2AuthConfig.getRealms()) {
            String string = object.getName();
            if (string == null) {
                throw new AuthenticationException("Missing realm name");
            }
            String string2 = string.toUpperCase();
            configurable = null;
            try {
                configurable = (CredentialsValidator)Class.forName(object.getValidatorClass()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception exception) {
                throw new AuthenticationException("invalid validator class fo realm " + string2, exception);
            }
            configurable.configure(new ConfigProperties(object.getProperties()));
            if (hashMap.putIfAbsent(object.getName().toUpperCase(), (CredentialsValidator)configurable) == null) continue;
            throw new AuthenticationException("Duplicate realm " + object.getName());
        }
        this.realms = hashMap;
        ArrayList arrayList = new ArrayList();
        for (UserToRolesMapperConfig userToRolesMapperConfig : h2AuthConfig.getUserToRolesMappers()) {
            configurable = null;
            try {
                configurable = (UserToRolesMapper)Class.forName(userToRolesMapperConfig.getClassName()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception exception) {
                throw new AuthenticationException("Invalid class in UserToRolesMapperConfig", exception);
            }
            configurable.configure(new ConfigProperties(userToRolesMapperConfig.getProperties()));
            arrayList.add(configurable);
        }
        this.userToRolesMappers = arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean updateRoles(AuthenticationInfo authenticationInfo, User user, Database database) throws AuthenticationException {
        Object object;
        boolean bl = false;
        HashSet<String> hashSet = new HashSet<String>();
        for (UserToRolesMapper object2 : this.userToRolesMappers) {
            object = object2.mapUserToRoles(authenticationInfo);
            if (object == null || object.isEmpty()) continue;
            hashSet.addAll((Collection<String>)object);
        }
        for (String string : hashSet) {
            Object object2;
            if (string == null || string.isEmpty()) continue;
            object = database.findRole(string);
            if (object == null && this.isCreateMissingRoles()) {
                object2 = database.getSystemSession();
                ((Session)object2).lock();
                try {
                    object = new Role(database, database.allocateObjectId(), string, false);
                    database.addDatabaseObject(database.getSystemSession(), (DbObject)object);
                    database.getSystemSession().commit(false);
                    bl = true;
                }
                finally {
                    ((Session)object2).unlock();
                }
            }
            if (object == null || user.getRightForRole((Role)object) != null) continue;
            object2 = new Right(database, -1, user, (Role)object);
            ((DbObject)object2).setTemporary(true);
            user.grantRole((Role)object, (Right)object2);
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final User authenticate(AuthenticationInfo authenticationInfo, Database database) throws AuthenticationException {
        String string = authenticationInfo.getFullyQualifiedName();
        User user = database.findUser(string);
        if (user == null && !this.isAllowUserRegistration()) {
            throw new AuthenticationException("User " + string + " not found in db");
        }
        CredentialsValidator credentialsValidator = this.realms.get(authenticationInfo.getRealm());
        if (credentialsValidator == null) {
            throw new AuthenticationException("realm " + authenticationInfo.getRealm() + " not configured");
        }
        try {
            if (!credentialsValidator.validateCredentials(authenticationInfo)) {
                return null;
            }
        }
        catch (Exception exception) {
            throw new AuthenticationException(exception);
        }
        if (user == null) {
            SessionLocal sessionLocal = database.getSystemSession();
            sessionLocal.lock();
            try {
                user = UserBuilder.buildUser(authenticationInfo, database, this.isPersistUsers());
                database.addDatabaseObject(database.getSystemSession(), user);
                database.getSystemSession().commit(false);
            }
            finally {
                sessionLocal.unlock();
            }
        }
        user.revokeTemporaryRightsOnRoles();
        this.updateRoles(authenticationInfo, user, database);
        return user;
    }
}

