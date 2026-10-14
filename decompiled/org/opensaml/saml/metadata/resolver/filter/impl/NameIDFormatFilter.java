/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.PDPDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameIDFormatFilter
extends AbstractInitializableComponent
implements MetadataFilter {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(NameIDFormatFilter.class);
    @Nonnull
    @NonnullElements
    private Multimap<Predicate<EntityDescriptor>, String> applyMap;
    @Nonnull
    private final SAMLObjectBuilder<NameIDFormat> formatBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameIDFormat.DEFAULT_ELEMENT_NAME);

    public void setRules(@Nonnull @NonnullElements Map<Predicate<EntityDescriptor>, Collection<String>> rules) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        Constraint.isNotNull(rules, "Rules map cannot be null");
        this.applyMap = ArrayListMultimap.create(rules.size(), 1);
        for (Map.Entry<Predicate<EntityDescriptor>, Collection<String>> entry : rules.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) continue;
            this.applyMap.putAll(entry.getKey(), StringSupport.normalizeStringCollection(entry.getValue()));
        }
    }

    @Override
    @Nullable
    public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        if (metadata == null) {
            return null;
        }
        if (metadata instanceof EntitiesDescriptor) {
            this.filterEntitiesDescriptor((EntitiesDescriptor)metadata);
        } else {
            this.filterEntityDescriptor((EntityDescriptor)metadata);
        }
        return metadata;
    }

    protected void filterEntityDescriptor(@Nonnull EntityDescriptor descriptor) {
        for (Map.Entry<Predicate<EntityDescriptor>, Collection<String>> entry : this.applyMap.asMap().entrySet()) {
            if (entry.getValue().isEmpty() || !entry.getKey().apply(descriptor)) continue;
            for (String format : entry.getValue()) {
                this.log.info("Adding NameIDFormat '{}' to EntityDescriptor '{}'", (Object)format, (Object)descriptor.getEntityID());
                for (RoleDescriptor role : descriptor.getRoleDescriptors()) {
                    NameIDFormat nif;
                    if (role instanceof SPSSODescriptor) {
                        nif = this.formatBuilder.buildObject();
                        nif.setFormat(format);
                        ((SPSSODescriptor)role).getNameIDFormats().add(nif);
                        continue;
                    }
                    if (role instanceof AttributeAuthorityDescriptor) {
                        nif = this.formatBuilder.buildObject();
                        nif.setFormat(format);
                        ((AttributeAuthorityDescriptor)role).getNameIDFormats().add(nif);
                        continue;
                    }
                    if (!(role instanceof PDPDescriptor)) continue;
                    nif = this.formatBuilder.buildObject();
                    nif.setFormat(format);
                    ((PDPDescriptor)role).getNameIDFormats().add(nif);
                }
            }
        }
    }

    protected void filterEntitiesDescriptor(@Nonnull EntitiesDescriptor descriptor) {
        for (EntitiesDescriptor group : descriptor.getEntitiesDescriptors()) {
            this.filterEntitiesDescriptor(group);
        }
        for (EntityDescriptor entity : descriptor.getEntityDescriptors()) {
            this.filterEntityDescriptor(entity);
        }
    }
}

