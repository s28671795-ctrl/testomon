/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.trust;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import org.apache.cxf.Bus;
import org.apache.cxf.BusException;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.security.SimplePrincipal;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.rt.security.claims.ClaimCollection;
import org.apache.cxf.rt.security.saml.utils.SAMLUtils;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.ws.security.tokenstore.EHCacheTokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.tokenstore.TokenStoreFactory;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.cxf.ws.security.trust.STSTokenValidator;
import org.apache.cxf.ws.security.trust.STSUtils;
import org.apache.cxf.ws.security.trust.claims.RoleClaimsCallbackHandler;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.util.Loader;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.wss4j.dom.message.token.UsernameToken;
import org.apache.wss4j.dom.validate.Credential;
import org.w3c.dom.Document;

public class STSLoginModule
implements LoginModule {
    public static final String REQUIRE_ROLES = "require.roles";
    public static final String DISABLE_ON_BEHALF_OF = "disable.on.behalf.of";
    public static final String DISABLE_CACHING = "disable.caching";
    public static final String WSDL_LOCATION = "wsdl.location";
    public static final String SERVICE_NAME = "service.name";
    public static final String ENDPOINT_NAME = "endpoint.name";
    public static final String KEY_SIZE = "key.size";
    public static final String KEY_TYPE = "key.type";
    public static final String TOKEN_TYPE = "token.type";
    public static final String WS_TRUST_NAMESPACE = "ws.trust.namespace";
    public static final String CXF_SPRING_CFG = "cxf.spring.config";
    private static final Logger LOG = LogUtils.getL7dLogger(STSLoginModule.class);
    private static final String TOKEN_STORE_KEY = "sts.login.module.tokenstore";
    private Set<Principal> roles = new HashSet<Principal>();
    private Principal userPrincipal;
    private Subject subject;
    private CallbackHandler callbackHandler;
    private boolean requireRoles;
    private boolean disableOnBehalfOf;
    private boolean disableCaching;
    private String wsdlLocation;
    private String serviceName;
    private String endpointName;
    private String cxfSpringCfg;
    private int keySize;
    private String keyType = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Bearer";
    private String tokenType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0";
    private String namespace;
    private Map<String, Object> stsClientProperties = new HashMap<String, Object>();
    private boolean succeeded;
    private boolean commitSucceeded;

    @Override
    public void initialize(Subject subj, CallbackHandler cbHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subj;
        this.callbackHandler = cbHandler;
        if (options.containsKey(REQUIRE_ROLES)) {
            this.requireRoles = Boolean.parseBoolean((String)options.get(REQUIRE_ROLES));
        }
        if (options.containsKey(DISABLE_ON_BEHALF_OF)) {
            this.disableOnBehalfOf = Boolean.parseBoolean((String)options.get(DISABLE_ON_BEHALF_OF));
        }
        if (options.containsKey(DISABLE_CACHING)) {
            this.disableCaching = Boolean.parseBoolean((String)options.get(DISABLE_CACHING));
        }
        if (options.containsKey(WSDL_LOCATION)) {
            this.wsdlLocation = (String)options.get(WSDL_LOCATION);
        }
        if (options.containsKey(SERVICE_NAME)) {
            this.serviceName = (String)options.get(SERVICE_NAME);
        }
        if (options.containsKey(ENDPOINT_NAME)) {
            this.endpointName = (String)options.get(ENDPOINT_NAME);
        }
        if (options.containsKey(KEY_SIZE)) {
            this.keySize = Integer.parseInt((String)options.get(KEY_SIZE));
        }
        if (options.containsKey(KEY_TYPE)) {
            this.keyType = (String)options.get(KEY_TYPE);
        }
        if (options.containsKey(TOKEN_TYPE)) {
            this.tokenType = (String)options.get(TOKEN_TYPE);
        }
        if (options.containsKey(WS_TRUST_NAMESPACE)) {
            this.namespace = (String)options.get(WS_TRUST_NAMESPACE);
        }
        if (options.containsKey(CXF_SPRING_CFG)) {
            this.cxfSpringCfg = (String)options.get(CXF_SPRING_CFG);
        }
        this.stsClientProperties.clear();
        for (String s2 : SecurityConstants.ALL_PROPERTIES) {
            if (!options.containsKey(s2)) continue;
            this.stsClientProperties.put(s2, options.get(s2));
        }
    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks = new Callback[]{new NameCallback("Username: "), new PasswordCallback("Password: ", false)};
        try {
            this.callbackHandler.handle(callbacks);
        }
        catch (IOException ioException) {
            throw new LoginException(ioException.getMessage());
        }
        catch (UnsupportedCallbackException unsupportedCallbackException) {
            throw new LoginException(unsupportedCallbackException.getMessage() + " not available to obtain information from user.");
        }
        String user = ((NameCallback)callbacks[0]).getName();
        char[] tmpPassword = ((PasswordCallback)callbacks[1]).getPassword();
        if (tmpPassword == null) {
            tmpPassword = new char[]{};
        }
        String password = new String(tmpPassword);
        this.roles = new HashSet<Principal>();
        this.userPrincipal = null;
        STSTokenValidator validator = new STSTokenValidator(true);
        validator.setUseIssueBinding(this.requireRoles);
        validator.setUseOnBehalfOf(!this.disableOnBehalfOf);
        validator.setDisableCaching(!this.requireRoles || this.disableCaching);
        try {
            UsernameToken token = this.convertToToken(user, password);
            Credential credential = new Credential();
            credential.setUsernametoken(token);
            RequestData data = new RequestData();
            Message message = PhaseInterceptorChain.getCurrentMessage();
            STSClient stsClient = this.configureSTSClient(message);
            if (message != null) {
                message.put("security.sts.client", stsClient);
                data.setMsgContext(message);
            } else {
                TokenStore tokenStore = this.configureTokenStore();
                validator.setStsClient(stsClient);
                validator.setTokenStore(tokenStore);
            }
            credential = validator.validate(credential, data);
            this.userPrincipal = new SimplePrincipal(user);
            this.roles.addAll(this.getRoles(message, credential));
        }
        catch (Exception e) {
            LOG.log(Level.INFO, "User " + user + " authentication failed", e);
            throw new LoginException("User " + user + " authentication failed: " + e.getMessage());
        }
        this.succeeded = true;
        return true;
    }

    private STSClient configureSTSClient(Message msg) throws BusException, EndpointException {
        STSClient c = null;
        if (this.cxfSpringCfg != null) {
            SpringBusFactory bf = new SpringBusFactory();
            URL busFile = Loader.getResource(this.cxfSpringCfg);
            Bus bus = bf.createBus(busFile.toString());
            BusFactory.setDefaultBus(bus);
            BusFactory.setThreadDefaultBus(bus);
            c = new STSClient(bus);
        } else if (msg == null) {
            Bus bus = BusFactory.getDefaultBus(true);
            c = new STSClient(bus);
        } else {
            c = STSUtils.getClient(msg, "sts");
        }
        if (this.wsdlLocation != null) {
            c.setWsdlLocation(this.wsdlLocation);
        }
        if (this.serviceName != null) {
            c.setServiceName(this.serviceName);
        }
        if (this.endpointName != null) {
            c.setEndpointName(this.endpointName);
        }
        if (this.keySize > 0) {
            c.setKeySize(this.keySize);
        }
        if (this.keyType != null) {
            c.setKeyType(this.keyType);
        }
        if (this.tokenType != null) {
            c.setTokenType(this.tokenType);
        }
        if (this.namespace != null) {
            c.setNamespace(this.namespace);
        }
        c.setProperties(this.stsClientProperties);
        if (this.requireRoles && c.getClaimsCallbackHandler() == null) {
            c.setClaimsCallbackHandler(new RoleClaimsCallbackHandler());
        }
        return c;
    }

    private TokenStore configureTokenStore() throws MalformedURLException {
        if (TokenStoreFactory.isEhCacheInstalled()) {
            String cfg = "cxf-ehcache.xml";
            URL url2 = ClassLoaderUtils.getResource(cfg, STSLoginModule.class);
            if (url2 == null) {
                url2 = new URL(cfg);
            }
            return new EHCacheTokenStore(TOKEN_STORE_KEY, BusFactory.getDefaultBus(), url2);
        }
        return null;
    }

    private UsernameToken convertToToken(String username, String password) throws Exception {
        Document doc = DOMUtils.getEmptyDocument();
        UsernameToken token = new UsernameToken(false, doc, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
        token.setName(username);
        token.setPassword(password);
        return token;
    }

    private Set<Principal> getRoles(Message msg, Credential credential) {
        SamlAssertionWrapper samlAssertion = credential.getTransformedToken();
        if (samlAssertion == null) {
            samlAssertion = credential.getSamlAssertion();
        }
        if (samlAssertion != null) {
            String roleAttributeName = null;
            if (msg != null) {
                roleAttributeName = (String)SecurityUtils.getSecurityPropertyValue("security.saml-role-attributename", msg);
            }
            if (roleAttributeName == null || roleAttributeName.length() == 0) {
                roleAttributeName = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role";
            }
            ClaimCollection claims = SAMLUtils.getClaims(samlAssertion);
            return SAMLUtils.parseRolesFromClaims(claims, roleAttributeName, null);
        }
        return Collections.emptySet();
    }

    @Override
    public boolean commit() throws LoginException {
        if (!this.succeeded || this.userPrincipal == null) {
            this.roles.clear();
            this.succeeded = false;
            this.userPrincipal = null;
            return false;
        }
        this.subject.getPrincipals().add(this.userPrincipal);
        this.subject.getPrincipals().addAll(this.roles);
        this.commitSucceeded = true;
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        if (!this.succeeded) {
            return false;
        }
        if (this.commitSucceeded) {
            this.logout();
        } else {
            this.roles.clear();
            this.userPrincipal = null;
            this.succeeded = false;
        }
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        this.subject.getPrincipals().remove(this.userPrincipal);
        this.subject.getPrincipals().removeAll(this.roles);
        this.roles.clear();
        this.userPrincipal = null;
        this.succeeded = false;
        this.commitSucceeded = false;
        return true;
    }
}

