/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityAttributesFilter
implements MetadataFilter {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(EntityAttributesFilter.class);
    @Nonnull
    @NonnullElements
    private Multimap<Predicate<EntityDescriptor>, Attribute> applyMap;
    @Nonnull
    private final SAMLObjectBuilder<Extensions> extBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Extensions.DEFAULT_ELEMENT_NAME);
    @Nonnull
    private final SAMLObjectBuilder<EntityAttributes> entityAttributesBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(EntityAttributes.DEFAULT_ELEMENT_NAME);

    public void setRules(@Nonnull @NonnullElements Map<Predicate<EntityDescriptor>, Collection<Attribute>> rules) {
        Constraint.isNotNull(rules, "Rules map cannot be null");
        this.applyMap = ArrayListMultimap.create(rules.size(), 1);
        for (Map.Entry<Predicate<EntityDescriptor>, Collection<Attribute>> entry : rules.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) continue;
            this.applyMap.putAll(entry.getKey(), Collections2.filter(entry.getValue(), Predicates.notNull()));
        }
    }

    @Override
    @Nullable
    public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
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
        for (Map.Entry<Predicate<EntityDescriptor>, Collection<Attribute>> entry : this.applyMap.asMap().entrySet()) {
            List<XMLObject> entityAttributesCollection;
            if (entry.getValue().isEmpty() || !entry.getKey().apply(descriptor)) continue;
            Extensions extensions = descriptor.getExtensions();
            if (extensions == null) {
                extensions = this.extBuilder.buildObject();
                descriptor.setExtensions(extensions);
            }
            if ((entityAttributesCollection = extensions.getUnknownXMLObjects(EntityAttributes.DEFAULT_ELEMENT_NAME)).isEmpty()) {
                entityAttributesCollection.add(this.entityAttributesBuilder.buildObject());
            }
            EntityAttributes entityAttributes = (EntityAttributes)entityAttributesCollection.iterator().next();
            for (Attribute attribute : entry.getValue()) {
                try {
                    this.log.info("Adding EntityAttribute ({}) to EntityDescriptor ({})", (Object)attribute.getName(), (Object)descriptor.getEntityID());
                    Attribute copy = XMLObjectSupport.cloneXMLObject(attribute);
                    entityAttributes.getAttributes().add(copy);
                }
                catch (MarshallingException | UnmarshallingException e) {
                    this.log.error("Error cloning Attribute", e);
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

