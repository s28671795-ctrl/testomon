/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.credentials.authenticator;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.PlainJWT;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.credentials.Credentials;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.credentials.authenticator.Authenticator;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.exception.TechnicalException;
import org.pac4j.core.exception.http.HttpAction;
import org.pac4j.core.profile.ProfileHelper;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.profile.definition.ProfileDefinitionAware;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.generator.ValueGenerator;
import org.pac4j.jwt.config.encryption.EncryptionConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.profile.JwtProfileDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtAuthenticator
extends ProfileDefinitionAware
implements Authenticator {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private List<EncryptionConfiguration> encryptionConfigurations = new ArrayList<EncryptionConfiguration>();
    private List<SignatureConfiguration> signatureConfigurations = new ArrayList<SignatureConfiguration>();
    private String realmName = "authentication required";
    private Date expirationTime;
    private ValueGenerator identifierGenerator;

    public JwtAuthenticator() {
    }

    public JwtAuthenticator(List<SignatureConfiguration> signatureConfigurations) {
        this.signatureConfigurations = signatureConfigurations;
    }

    public JwtAuthenticator(List<SignatureConfiguration> signatureConfigurations, List<EncryptionConfiguration> encryptionConfigurations) {
        this.signatureConfigurations = signatureConfigurations;
        this.encryptionConfigurations = encryptionConfigurations;
    }

    public JwtAuthenticator(SignatureConfiguration signatureConfiguration) {
        this.setSignatureConfiguration(signatureConfiguration);
    }

    public JwtAuthenticator(SignatureConfiguration signatureConfiguration, EncryptionConfiguration encryptionConfiguration) {
        this.setSignatureConfiguration(signatureConfiguration);
        this.setEncryptionConfiguration(encryptionConfiguration);
    }

    @Override
    protected void internalInit(boolean forceReinit) {
        CommonHelper.assertNotBlank("realmName", this.realmName);
        this.defaultProfileDefinition(new JwtProfileDefinition());
        if (this.signatureConfigurations.isEmpty()) {
            this.logger.warn("No signature configurations have been defined: non-signed JWT will be accepted!");
        }
    }

    public Map<String, Object> validateTokenAndGetClaims(String token) {
        UserProfile profile = this.validateToken(token);
        HashMap<String, Object> claims = new HashMap<String, Object>(profile.getAttributes());
        claims.put("sub", profile.getId());
        return claims;
    }

    public UserProfile validateToken(String token) {
        TokenCredentials credentials = new TokenCredentials(token);
        try {
            this.validate(credentials, null, null);
        }
        catch (HttpAction e) {
            throw new TechnicalException(e);
        }
        catch (CredentialsException e) {
            this.logger.info("Failed to retrieve or validate credentials: {}", (Object)e.getMessage());
            this.logger.debug("Failed to retrieve or validate credentials", e);
            return null;
        }
        return credentials.getUserProfile();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void validate(Credentials cred, WebContext context, SessionStore sessionStore) {
        this.init();
        TokenCredentials credentials = (TokenCredentials)cred;
        String token = credentials.getToken();
        if (context != null) {
            context.setResponseHeader("WWW-Authenticate", "Bearer realm=\"" + this.realmName + "\"");
        }
        try {
            JWT jwt = JWTParser.parse(token);
            if (jwt instanceof PlainJWT) {
                if (!this.signatureConfigurations.isEmpty()) throw new CredentialsException("A non-signed JWT cannot be accepted as signature configurations have been defined");
                this.logger.debug("JWT is not signed and no signature configurations -> verified");
            } else {
                boolean found;
                SignedJWT signedJWT = null;
                if (jwt instanceof SignedJWT) {
                    signedJWT = (SignedJWT)jwt;
                }
                if (jwt instanceof EncryptedJWT) {
                    this.logger.debug("JWT is encrypted");
                    EncryptedJWT encryptedJWT = (EncryptedJWT)jwt;
                    found = false;
                    JWEHeader header = encryptedJWT.getHeader();
                    JWEAlgorithm algorithm = header.getAlgorithm();
                    EncryptionMethod method2 = header.getEncryptionMethod();
                    for (EncryptionConfiguration config : this.encryptionConfigurations) {
                        if (!config.supports(algorithm, method2)) continue;
                        this.logger.debug("Using encryption configuration: {}", (Object)config);
                        try {
                            config.decrypt(encryptedJWT);
                            signedJWT = encryptedJWT.getPayload().toSignedJWT();
                            if (signedJWT != null) {
                                jwt = signedJWT;
                            }
                            found = true;
                            break;
                        }
                        catch (JOSEException e) {
                            this.logger.debug("Decryption fails with encryption configuration: {}, passing to the next one", (Object)config);
                        }
                    }
                    if (!found) {
                        throw new CredentialsException("No encryption algorithm found for JWT: " + token);
                    }
                }
                if (signedJWT != null) {
                    this.logger.debug("JWT is signed");
                    boolean verified = false;
                    found = false;
                    JWSAlgorithm algorithm = signedJWT.getHeader().getAlgorithm();
                    for (SignatureConfiguration config : this.signatureConfigurations) {
                        if (!config.supports(algorithm)) continue;
                        this.logger.debug("Using signature configuration: {}", (Object)config);
                        try {
                            verified = config.verify(signedJWT);
                            found = true;
                            if (!verified) continue;
                            break;
                        }
                        catch (JOSEException e) {
                            this.logger.debug("Verification fails with signature configuration: {}, passing to the next one", (Object)config);
                        }
                    }
                    if (!found) {
                        throw new CredentialsException("No signature algorithm found for JWT: " + token);
                    }
                    if (!verified) {
                        throw new CredentialsException("JWT verification failed: " + token);
                    }
                }
            }
            this.createJwtProfile(credentials, jwt, context, sessionStore);
            return;
        }
        catch (ParseException e) {
            throw new CredentialsException("Cannot decrypt / verify JWT", e);
        }
    }

    protected void createJwtProfile(TokenCredentials credentials, JWT jwt, WebContext context, SessionStore sessionStore) throws ParseException {
        Date expTime;
        JWTClaimsSet claimSet = jwt.getJWTClaimsSet();
        String subject = claimSet.getSubject();
        if (subject == null) {
            if (this.identifierGenerator != null) {
                subject = this.identifierGenerator.generateValue(context, sessionStore);
            }
            if (subject == null) {
                throw new TechnicalException("The JWT must contain a subject or an id must be generated via the identifierGenerator");
            }
        }
        if ((expTime = claimSet.getExpirationTime()) != null) {
            Date now = new Date();
            if (expTime.before(now)) {
                this.logger.warn("The JWT is expired: no profile is built");
                return;
            }
            if (this.expirationTime != null && expTime.after(this.expirationTime)) {
                this.logger.warn("The JWT is expired: no profile is built");
                return;
            }
        }
        HashMap<String, Object> attributes = new HashMap<String, Object>(claimSet.getClaims());
        attributes.remove("sub");
        List roles = (List)attributes.get("$int_roles");
        attributes.remove("$int_roles");
        List permissions = (List)attributes.get("$int_perms");
        attributes.remove("$int_perms");
        String linkedId = (String)attributes.get("$int_linkid");
        attributes.remove("$int_linkid");
        UserProfile profile = this.getProfileDefinition().newProfile(subject);
        profile.setId(ProfileHelper.sanitizeIdentifier(subject));
        this.getProfileDefinition().convertAndAdd(profile, attributes, null);
        if (roles != null) {
            profile.addRoles(roles);
        }
        if (permissions != null) {
            profile.addPermissions(permissions);
        }
        if (linkedId != null) {
            profile.setLinkedId(linkedId);
        }
        credentials.setUserProfile(profile);
    }

    public List<SignatureConfiguration> getSignatureConfigurations() {
        return this.signatureConfigurations;
    }

    public void setSignatureConfiguration(SignatureConfiguration signatureConfiguration) {
        this.addSignatureConfiguration(signatureConfiguration);
    }

    public void addSignatureConfiguration(SignatureConfiguration signatureConfiguration) {
        CommonHelper.assertNotNull("signatureConfiguration", signatureConfiguration);
        this.signatureConfigurations.add(signatureConfiguration);
    }

    public void setSignatureConfigurations(List<SignatureConfiguration> signatureConfigurations) {
        CommonHelper.assertNotNull("signatureConfigurations", signatureConfigurations);
        this.signatureConfigurations = signatureConfigurations;
    }

    public List<EncryptionConfiguration> getEncryptionConfigurations() {
        return this.encryptionConfigurations;
    }

    public void setEncryptionConfiguration(EncryptionConfiguration encryptionConfiguration) {
        this.addEncryptionConfiguration(encryptionConfiguration);
    }

    public void addEncryptionConfiguration(EncryptionConfiguration encryptionConfiguration) {
        CommonHelper.assertNotNull("encryptionConfiguration", encryptionConfiguration);
        this.encryptionConfigurations.add(encryptionConfiguration);
    }

    public void setEncryptionConfigurations(List<EncryptionConfiguration> encryptionConfigurations) {
        CommonHelper.assertNotNull("encryptionConfigurations", encryptionConfigurations);
        this.encryptionConfigurations = encryptionConfigurations;
    }

    public String getRealmName() {
        return this.realmName;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = new Date(expirationTime.getTime());
    }

    public Date getExpirationTime() {
        return new Date(this.expirationTime.getTime());
    }

    public ValueGenerator getIdentifierGenerator() {
        return this.identifierGenerator;
    }

    public void setIdentifierGenerator(ValueGenerator identifierGenerator) {
        this.identifierGenerator = identifierGenerator;
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "signatureConfigurations", this.signatureConfigurations, "encryptionConfigurations", this.encryptionConfigurations, "realmName", this.realmName, "identifierGenerator", this.identifierGenerator);
    }
}

