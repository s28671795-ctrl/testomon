/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.velocity.app.VelocityEngine
 *  org.apache.velocity.context.Context
 *  org.apache.velocity.exception.ResourceNotFoundException
 *  org.apache.velocity.exception.VelocityException
 *  org.apache.velocity.runtime.resource.loader.StringResourceLoader
 *  org.apache.velocity.runtime.resource.util.StringResourceRepository
 */
package net.shibboleth.utilities.java.support.velocity;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;

public final class Template {
    private final VelocityEngine engine;
    private final String templateName;
    private final String templateEncoding;

    private Template(@Nonnull VelocityEngine velocityEngine, @Nonnull @NotEmpty String velocityTemplateName, @Nonnull String velocityTemplateEncoding) {
        this.engine = Constraint.isNotNull(velocityEngine, "Velocity engine can not be null");
        this.templateName = Constraint.isNotNull(StringSupport.trimOrNull(velocityTemplateName), "Velocity template name can not be null or empty");
        this.templateEncoding = Constraint.isNotNull(StringSupport.trimOrNull(velocityTemplateEncoding), "Velocity template encoding name can not be null or empty");
    }

    @Nonnull
    public static Template fromTemplate(@Nonnull VelocityEngine engine, @Nonnull @NotEmpty String template) {
        return Template.fromTemplate(engine, template, Charsets.US_ASCII);
    }

    @Nonnull
    public static Template fromTemplate(@Nonnull VelocityEngine engine, @Nonnull @NotEmpty String template, @Nonnull Charset encoding) {
        String templateName;
        String trimmedTemplate = Constraint.isNotNull(StringSupport.trimOrNull(template), "Velocity template can not be null or empty");
        Constraint.isNotNull(encoding, "Template encoding character set can not be null");
        StringResourceRepository templateRepo = StringResourceLoader.getRepository();
        while (templateRepo.getStringResource(templateName = UUID.randomUUID().toString()) != null) {
        }
        templateRepo.putStringResource(templateName, trimmedTemplate, encoding.name());
        if (!engine.resourceExists(templateName)) {
            throw new VelocityException("Velocity engine is not configured to load templates from the default StringResourceRepository");
        }
        try {
            engine.getTemplate(templateName);
        }
        catch (VelocityException e) {
            throw new VelocityException("The following template is not valid:\n" + trimmedTemplate, (Throwable)e);
        }
        return new Template(engine, templateName, encoding.name());
    }

    public static Template fromTemplateName(@Nonnull VelocityEngine engine, @Nonnull @NotEmpty String templateName) {
        return Template.fromTemplateName(engine, templateName, Charsets.US_ASCII);
    }

    public static Template fromTemplateName(@Nonnull VelocityEngine engine, @Nonnull @NotEmpty String name, @Nonnull Charset encoding) {
        String trimmedName = Constraint.isNotNull(StringSupport.trimOrNull(name), "Velocity template name can not be null or empty");
        Constraint.isNotNull(encoding, "Template encoding character set can not be null");
        if (!engine.resourceExists(name)) {
            throw new VelocityException("No template with the name " + trimmedName + " is available to the velocity engine");
        }
        try {
            engine.getTemplate(trimmedName);
        }
        catch (VelocityException e) {
            throw new VelocityException("Template '" + trimmedName + "' is not a valid template", (Throwable)e);
        }
        return new Template(engine, trimmedName, encoding.name());
    }

    @Nonnull
    public String getTemplateName() {
        return this.templateName;
    }

    public String merge(Context templateContext) {
        StringWriter output = new StringWriter();
        this.merge(templateContext, output);
        return output.toString();
    }

    public void merge(Context templateContext, Writer output) {
        try {
            this.engine.mergeTemplate(this.templateName, this.templateEncoding, templateContext, output);
        }
        catch (ResourceNotFoundException e) {
            throw new VelocityException("Velocity template " + this.templateName + " has been removed since this object was constructed");
        }
        catch (Exception e) {
            throw new VelocityException("Velocity template " + this.templateName + " threw an exception", (Throwable)e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Template)) {
            return false;
        }
        Template otherTemplate = (Template)obj;
        return this.engine.equals(otherTemplate.engine) && this.templateName.equals(otherTemplate.templateName);
    }

    public int hashCode() {
        return Objects.hashCode(this.engine, this.templateName);
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("templateName", this.templateName).toString();
    }
}

