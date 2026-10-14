/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.xml;

import java.io.InputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.xml.validation.Schema;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.xml.ClasspathResolver;
import net.shibboleth.utilities.java.support.xml.SchemaBuilder;
import org.opensaml.core.xml.XMLRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

@ThreadSafe
public class SAMLSchemaBuilder {
    @Nonnull
    @NonnullElements
    @NotEmpty
    private static String[] baseXMLSchemas = new String[]{"/schema/xml.xsd", "/schema/XMLSchema.xsd", "/schema/xmldsig-core-schema.xsd", "/schema/xenc-schema.xsd", "/schema/xmldsig11-schema.xsd", "/schema/xenc11-schema.xsd"};
    @Nonnull
    @NonnullElements
    @NotEmpty
    private static String[] soapSchemas = new String[]{"/schema/soap-envelope.xsd"};
    @Nonnull
    @NonnullElements
    @NotEmpty
    private static String[] saml10Schemas = new String[]{"/schema/oasis-sstc-saml-schema-assertion-1.0.xsd", "/schema/oasis-sstc-saml-schema-protocol-1.0.xsd"};
    @Nonnull
    @NonnullElements
    @NotEmpty
    private static String[] saml11Schemas = new String[]{"/schema/oasis-sstc-saml-schema-assertion-1.1.xsd", "/schema/oasis-sstc-saml-schema-protocol-1.1.xsd"};
    @Nonnull
    @NonnullElements
    @NotEmpty
    private static String[] saml20Schemas = new String[]{"/schema/saml-schema-assertion-2.0.xsd", "/schema/saml-schema-protocol-2.0.xsd", "/schema/saml-schema-metadata-2.0.xsd", "/schema/saml-schema-authn-context-2.0.xsd", "/schema/saml-schema-authn-context-auth-telephony-2.0.xsd", "/schema/saml-schema-authn-context-ip-2.0.xsd", "/schema/saml-schema-authn-context-ippword-2.0.xsd", "/schema/saml-schema-authn-context-kerberos-2.0.xsd", "/schema/saml-schema-authn-context-mobileonefactor-reg-2.0.xsd", "/schema/saml-schema-authn-context-mobileonefactor-unreg-2.0.xsd", "/schema/saml-schema-authn-context-mobiletwofactor-reg-2.0.xsd", "/schema/saml-schema-authn-context-mobiletwofactor-unreg-2.0.xsd", "/schema/saml-schema-authn-context-nomad-telephony-2.0.xsd", "/schema/saml-schema-authn-context-personal-telephony-2.0.xsd", "/schema/saml-schema-authn-context-pgp-2.0.xsd", "/schema/saml-schema-authn-context-ppt-2.0.xsd", "/schema/saml-schema-authn-context-pword-2.0.xsd", "/schema/saml-schema-authn-context-session-2.0.xsd", "/schema/saml-schema-authn-context-smartcard-2.0.xsd", "/schema/saml-schema-authn-context-smartcardpki-2.0.xsd", "/schema/saml-schema-authn-context-softwarepki-2.0.xsd", "/schema/saml-schema-authn-context-spki-2.0.xsd", "/schema/saml-schema-authn-context-srp-2.0.xsd", "/schema/saml-schema-authn-context-sslcert-2.0.xsd", "/schema/saml-schema-authn-context-telephony-2.0.xsd", "/schema/saml-schema-authn-context-timesync-2.0.xsd", "/schema/saml-schema-authn-context-x509-2.0.xsd", "/schema/saml-schema-authn-context-xmldsig-2.0.xsd", "/schema/saml-schema-dce-2.0.xsd", "/schema/saml-schema-ecp-2.0.xsd", "/schema/saml-schema-x500-2.0.xsd", "/schema/saml-schema-xacml-2.0.xsd"};
    @Nonnull
    @NonnullElements
    @NotEmpty
    private static String[] baseExtSchemas = new String[]{"/schema/sstc-saml1x-metadata.xsd", "/schema/sstc-saml-idp-discovery.xsd", "/schema/sstc-saml-protocol-ext-thirdparty.xsd", "/schema/sstc-saml-metadata-ext-query.xsd", "/schema/sstc-saml-delegation.xsd", "/schema/sstc-saml-metadata-ui-v1.0.xsd", "/schema/sstc-metadata-attr.xsd", "/schema/saml-metadata-rpi-v1.0.xsd", "/schema/sstc-saml-metadata-algsupport-v1.0.xsd", "/schema/sstc-saml-channel-binding-ext-v1.0.xsd", "/schema/saml-async-slo-v1.0.xsd", "/schema/ietf-kitten-sasl-saml-ec.xsd"};
    private Logger log = LoggerFactory.getLogger(SAMLSchemaBuilder.class);
    private boolean unresolvedSchemaFatal = true;
    @Nullable
    private Schema cachedSchema;
    @Nonnull
    @NonnullElements
    @NotEmpty
    private String[] saml1xSchemas;
    @Nonnull
    private SchemaBuilder schemaBuilder;

    public SAMLSchemaBuilder(@Nonnull @ParameterName(name="ver") SAML1Version ver) {
        this.saml1xSchemas = ver == SAML1Version.SAML_11 ? saml11Schemas : saml10Schemas;
    }

    public void setUnresolvedSchemaFatal(boolean flag) {
        this.unresolvedSchemaFatal = flag;
    }

    public synchronized void setSchemaBuilder(@Nonnull SchemaBuilder builder) {
        this.schemaBuilder = Constraint.isNotNull(builder, "SchemaBuilder cannot be null");
        this.cachedSchema = null;
        this.configureBuilder();
    }

    @Nonnull
    public synchronized Schema getSAMLSchema() throws SAXException {
        if (this.cachedSchema == null) {
            if (this.schemaBuilder == null) {
                try {
                    this.schemaBuilder = new SchemaBuilder();
                    this.schemaBuilder.setResourceResolver(new ClasspathResolver());
                    this.configureBuilder();
                }
                catch (RuntimeException e) {
                    this.schemaBuilder = null;
                    throw e;
                }
            }
            this.cachedSchema = this.schemaBuilder.buildSchema();
            return this.cachedSchema;
        }
        return this.cachedSchema;
    }

    @Nonnull
    private void configureBuilder() {
        for (String source2 : baseXMLSchemas) {
            this.addSchemaToBuilder(source2);
        }
        for (String source2 : soapSchemas) {
            this.addSchemaToBuilder(source2);
        }
        for (String source2 : this.saml1xSchemas) {
            this.addSchemaToBuilder(source2);
        }
        for (String source2 : saml20Schemas) {
            this.addSchemaToBuilder(source2);
        }
        for (String source2 : baseExtSchemas) {
            this.addSchemaToBuilder(source2);
        }
    }

    private void addSchemaToBuilder(@Nonnull String source2) {
        Class<SAMLSchemaBuilder> clazz = SAMLSchemaBuilder.class;
        InputStream stream = clazz.getResourceAsStream(source2);
        if (stream != null) {
            this.schemaBuilder.addSchema(stream);
        } else {
            this.log.error("Failed to locate schema resource: {}", (Object)source2);
            if (this.unresolvedSchemaFatal) {
                throw new XMLRuntimeException("Failed to locate schema resource: " + source2);
            }
        }
    }

    public static enum SAML1Version {
        SAML_10,
        SAML_11;

    }
}

