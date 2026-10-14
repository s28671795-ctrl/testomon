/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.velocity.VelocityContext
 *  org.apache.velocity.app.VelocityEngine
 *  org.apache.velocity.context.Context
 */
package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.URISupport;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateRequestURLBuilder
implements Function<String, String> {
    public static final String CONTEXT_KEY_ENTITY_ID = "entityID";
    private final Logger log = LoggerFactory.getLogger(TemplateRequestURLBuilder.class);
    private Template template;
    private String templateText;
    private Function<String, String> transformer;
    private boolean encodeEntityID;

    public TemplateRequestURLBuilder(@Nonnull VelocityEngine engine, @Nonnull @NotEmpty String templateString, boolean encoded) {
        this(engine, templateString, encoded, null, StandardCharsets.US_ASCII);
    }

    public TemplateRequestURLBuilder(@Nonnull VelocityEngine engine, @Nonnull @NotEmpty String templateString, boolean encoded, @Nullable Function<String, String> transform) {
        this(engine, templateString, encoded, transform, StandardCharsets.US_ASCII);
    }

    public TemplateRequestURLBuilder(@Nonnull VelocityEngine engine, @Nonnull @NotEmpty String templateString, boolean encoded, @Nullable Function<String, String> transform, @Nullable Charset charSet) {
        Constraint.isNotNull(engine, "VelocityEngine was null");
        String trimmedTemplate = StringSupport.trimOrNull(templateString);
        this.templateText = Constraint.isNotNull(trimmedTemplate, "Template string was null or empty");
        this.transformer = transform;
        this.template = charSet != null ? Template.fromTemplate(engine, trimmedTemplate, charSet) : Template.fromTemplate(engine, trimmedTemplate);
        this.encodeEntityID = encoded;
    }

    @Override
    @Nullable
    public String apply(@Nonnull String input) {
        String entityID = Constraint.isNotNull(input, "Entity ID was null");
        this.log.debug("Saw input entityID '{}'", (Object)entityID);
        if (this.transformer != null) {
            entityID = this.transformer.apply(entityID);
            this.log.debug("Transformed entityID is '{}'", (Object)entityID);
            if (entityID == null) {
                this.log.debug("Transformed entityID was null");
                return null;
            }
        }
        VelocityContext context = new VelocityContext();
        if (this.encodeEntityID) {
            context.put(CONTEXT_KEY_ENTITY_ID, (Object)URISupport.doURLEncode(entityID));
        } else {
            context.put(CONTEXT_KEY_ENTITY_ID, (Object)entityID);
        }
        try {
            String result = this.template.merge((Context)context);
            this.log.debug("From entityID '{}' and template text '{}', built request URL: {}", entityID, this.templateText, result);
            return result;
        }
        catch (Throwable t2) {
            this.log.error("Encountered fatal error attempting to build request URL", t2);
            return null;
        }
    }
}

