/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.jwt.profile;

import com.nimbusds.jose.JOSEObject;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import java.util.Date;
import java.util.Map;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.jwt.config.encryption.EncryptionConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;

public class JwtGenerator {
    public static final String INTERNAL_ROLES = "$int_roles";
    public static final String INTERNAL_PERMISSIONS = "$int_perms";
    public static final String INTERNAL_LINKEDID = "$int_linkid";
    private SignatureConfiguration signatureConfiguration;
    private EncryptionConfiguration encryptionConfiguration;
    private Date expirationTime;

    public JwtGenerator() {
    }

    public JwtGenerator(SignatureConfiguration signatureConfiguration) {
        this.signatureConfiguration = signatureConfiguration;
    }

    public JwtGenerator(SignatureConfiguration signatureConfiguration, EncryptionConfiguration encryptionConfiguration) {
        this.signatureConfiguration = signatureConfiguration;
        this.encryptionConfiguration = encryptionConfiguration;
    }

    public String generate(Map<String, Object> claims) {
        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }
        if (this.expirationTime != null) {
            builder.expirationTime(this.expirationTime);
        }
        return this.internalGenerate(builder.build());
    }

    public String generate(UserProfile profile) {
        this.verifyProfile(profile);
        return this.internalGenerate(this.buildJwtClaimsSet(profile));
    }

    protected String internalGenerate(JWTClaimsSet claimsSet) {
        JOSEObject jwt = this.signatureConfiguration == null ? new PlainJWT(claimsSet) : this.signatureConfiguration.sign(claimsSet);
        if (this.encryptionConfiguration != null) {
            return this.encryptionConfiguration.encrypt((JWT)((Object)jwt));
        }
        return jwt.serialize();
    }

    protected void verifyProfile(UserProfile profile) {
        CommonHelper.assertNotNull("profile", profile);
        CommonHelper.assertNull(INTERNAL_ROLES, profile.getAttribute(INTERNAL_ROLES));
        CommonHelper.assertNull(INTERNAL_PERMISSIONS, profile.getAttribute(INTERNAL_PERMISSIONS));
        CommonHelper.assertNull(INTERNAL_LINKEDID, profile.getAttribute(INTERNAL_LINKEDID));
    }

    protected JWTClaimsSet buildJwtClaimsSet(UserProfile profile) {
        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder().issueTime(new Date());
        if (this.expirationTime != null) {
            builder.expirationTime(this.expirationTime);
        }
        Map<String, Object> attributes = profile.getAttributes();
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            builder.claim(entry.getKey(), entry.getValue());
        }
        builder.claim(INTERNAL_ROLES, profile.getRoles());
        builder.claim(INTERNAL_PERMISSIONS, profile.getPermissions());
        builder.claim(INTERNAL_LINKEDID, profile.getLinkedId());
        builder.subject(profile.getTypedId());
        return builder.build();
    }

    public SignatureConfiguration getSignatureConfiguration() {
        return this.signatureConfiguration;
    }

    public void setSignatureConfiguration(SignatureConfiguration signatureConfiguration) {
        this.signatureConfiguration = signatureConfiguration;
    }

    public EncryptionConfiguration getEncryptionConfiguration() {
        return this.encryptionConfiguration;
    }

    public void setEncryptionConfiguration(EncryptionConfiguration encryptionConfiguration) {
        this.encryptionConfiguration = encryptionConfiguration;
    }

    public Date getExpirationTime() {
        return new Date(this.expirationTime.getTime());
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = new Date(expirationTime.getTime());
    }

    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "signatureConfiguration", this.signatureConfiguration, "encryptionConfiguration", this.encryptionConfiguration);
    }
}

