/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter.impl;

import java.io.InputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Validator;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.ClasspathResolver;
import net.shibboleth.utilities.java.support.xml.SchemaBuilder;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.xml.SAMLSchemaBuilder;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class SchemaValidationFilter
implements MetadataFilter {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(SchemaValidationFilter.class);
    @Nullable
    private SchemaBuilder schemaBuilder;
    @Nonnull
    private SAMLSchemaBuilder samlSchemaBuilder;

    public SchemaValidationFilter(@Nonnull SAMLSchemaBuilder builder) {
        this(builder, null);
    }

    public SchemaValidationFilter(@Nonnull SAMLSchemaBuilder builder, @Nullable @NonnullElements String[] extensionSchemas) {
        this.samlSchemaBuilder = Constraint.isNotNull(builder, "SAMLSchemaBuilder cannot be null");
        if (extensionSchemas != null) {
            this.log.info("Overriding SchemaBuilder used to construct schemas to accomodate extension schemas");
            this.log.warn("Supplying extension schemas directly to metadata filter is deprecated");
            SchemaBuilder overriddenSchemaBuilder = new SchemaBuilder();
            overriddenSchemaBuilder.setResourceResolver(new ClasspathResolver());
            Class<SAMLSchemaBuilder> clazz = SAMLSchemaBuilder.class;
            for (String extension : extensionSchemas) {
                InputStream stream;
                String trimmed = StringSupport.trimOrNull(extension);
                if (trimmed == null || (stream = clazz.getResourceAsStream(trimmed)) == null) continue;
                overriddenSchemaBuilder.addSchema(stream);
            }
            this.samlSchemaBuilder.setSchemaBuilder(overriddenSchemaBuilder);
        }
    }

    @Override
    @Nullable
    public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
        Validator schemaValidator;
        if (metadata == null) {
            return null;
        }
        try {
            schemaValidator = this.samlSchemaBuilder.getSAMLSchema().newValidator();
        }
        catch (SAXException e) {
            this.log.error("Unable to build metadata validation schema", e);
            throw new FilterException("Unable to build metadata validation schema", e);
        }
        try {
            schemaValidator.validate(new DOMSource(metadata.getDOM()));
        }
        catch (Exception e) {
            this.log.error("Incoming metadata was not schema valid", e);
            throw new FilterException("Incoming metadata was not schema valid", e);
        }
        return metadata;
    }
}

