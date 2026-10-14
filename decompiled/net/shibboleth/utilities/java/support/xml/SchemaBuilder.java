/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NullableElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resource.Resource;
import net.shibboleth.utilities.java.support.xml.ClasspathResolver;
import net.shibboleth.utilities.java.support.xml.LoggingErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

@NotThreadSafe
public class SchemaBuilder {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(SchemaBuilder.class);
    @Nonnull
    private SchemaLanguage schemaLang = SchemaLanguage.XML;
    @Nonnull
    @NonnullElements
    private List<Source> sources = new ArrayList<Source>();
    @Nullable
    private LSResourceResolver resourceResolver;
    @Nullable
    private ErrorHandler errorHandler;
    @Nonnull
    private Map<String, Boolean> features = new HashMap<String, Boolean>();
    @Nonnull
    private Map<String, Object> properties = new HashMap<String, Object>();
    private boolean alreadyBuilt = false;

    public SchemaBuilder() {
        this.errorHandler = new LoggingErrorHandler(this.log);
    }

    @Nonnull
    public SchemaBuilder setSchemaLanguage(@Nonnull SchemaLanguage lang) {
        this.schemaLang = Constraint.isNotNull(lang, "SchemaLanguage cannot be null");
        return this;
    }

    @Nonnull
    public SchemaBuilder setResourceResolver(@Nullable LSResourceResolver resolver2) {
        this.resourceResolver = resolver2;
        return this;
    }

    @Nonnull
    public SchemaBuilder setErrorHandler(@Nullable ErrorHandler handler) {
        this.errorHandler = handler;
        return this;
    }

    public void setFeature(@Nonnull @NotEmpty String name, boolean value) {
        this.features.put(name, value);
    }

    public void setProperty(@Nonnull @NotEmpty String name, @Nullable Object object) {
        this.properties.put(name, object);
    }

    @Nonnull
    public SchemaBuilder resetSchemas() {
        this.sources.clear();
        return this;
    }

    @Nonnull
    public void setSchemas(@Nonnull @NullableElements Collection<Source> schemaSources) {
        Constraint.isNotNull(schemaSources, "Schema source file paths cannot be null");
        this.resetSchemas();
        for (Source schemaSource : schemaSources) {
            if (schemaSource == null) continue;
            this.addSchema(schemaSource);
        }
    }

    @Nonnull
    public void setSchemaResources(@Nonnull @NullableElements Collection<Resource> schemaResources) {
        Constraint.isNotNull(schemaResources, "Schema resources cannot be null");
        this.resetSchemas();
        for (Resource schemaResource : schemaResources) {
            if (schemaResource == null) continue;
            this.addSchema(schemaResource);
        }
    }

    @Nonnull
    public SchemaBuilder addSchema(@Nonnull InputStream schemaSource) {
        Constraint.isNotNull(schemaSource, "Schema source input stream cannot be null");
        this.addSchema(new StreamSource(schemaSource));
        return this;
    }

    @Nonnull
    public SchemaBuilder addSchema(@Nonnull Source schemaSource) {
        Constraint.isNotNull(schemaSource, "Schema source inputstreams can not be null");
        this.sources.add(schemaSource);
        return this;
    }

    @Nonnull
    public SchemaBuilder addSchema(@Nonnull Resource resource) {
        Constraint.isNotNull(resource, "Schema resource cannot be null");
        try {
            this.addSchema(resource.getInputStream());
        }
        catch (IOException e) {
            this.log.error("IO error adding schema from resource: {}", (Object)resource.getDescription(), (Object)e);
        }
        return this;
    }

    @Nonnull
    public synchronized Schema buildSchema() throws SAXException {
        SchemaFactory schemaFactory;
        block11: {
            if (this.alreadyBuilt) {
                throw new IllegalStateException("Schema already built, cannot build a second time");
            }
            Constraint.isNotEmpty(this.sources, "No schema sources specified");
            schemaFactory = this.schemaLang.getSchemaFactory();
            if (this.features.isEmpty()) {
                this.log.debug("No SchemaFactory features set, setting FEATURE_SECURE_PROCESSING by default");
                schemaFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
                if (this.resourceResolver != null && !(this.resourceResolver instanceof ClasspathResolver)) {
                    this.log.warn("Custom LSResourceResolver supplied, may interact badly with secure processing mode");
                } else {
                    try {
                        schemaFactory.setProperty("http://javax.xml.XMLConstants/property/accessExternalSchema", "all");
                        if (this.resourceResolver == null) {
                            this.log.info("Allowing schema and DTD access to non-remote resources (LSResourceResolver unset)");
                            break block11;
                        }
                        this.log.debug("Allowing schema and DTD access to non-remote resources (ClasspathResolver set)");
                    }
                    catch (SAXException e) {
                        this.log.info("Unable to set ACCESS_EXTERNAL_SCHEMA property, classpath-based schema lookup might fail");
                    }
                }
            } else {
                for (Map.Entry<String, Boolean> entry : this.features.entrySet()) {
                    schemaFactory.setFeature(entry.getKey(), entry.getValue());
                }
            }
        }
        for (Map.Entry<String, Object> entry : this.properties.entrySet()) {
            schemaFactory.setProperty(entry.getKey(), entry.getValue());
        }
        schemaFactory.setErrorHandler(this.errorHandler);
        if (this.resourceResolver != null) {
            schemaFactory.setResourceResolver(this.resourceResolver);
        }
        this.alreadyBuilt = true;
        return schemaFactory.newSchema(this.sources.toArray(new Source[this.sources.size()]));
    }

    public static enum SchemaLanguage {
        XML("http://www.w3.org/2001/XMLSchema"),
        RELAX("http://relaxng.org/ns/structure/1.0");

        @Nonnull
        private String schemaFactoryURI;

        private SchemaLanguage(String uri) {
            this.schemaFactoryURI = Constraint.isNotNull(StringSupport.trimOrNull(uri), "URI cannot be null or empty");
        }

        @Nonnull
        public SchemaFactory getSchemaFactory() {
            return SchemaFactory.newInstance(this.schemaFactoryURI);
        }
    }
}

