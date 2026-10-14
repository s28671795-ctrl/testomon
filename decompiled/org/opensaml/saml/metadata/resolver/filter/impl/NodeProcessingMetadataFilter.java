/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.metadata.resolver.filter.MetadataNodeProcessor;

public class NodeProcessingMetadataFilter
extends AbstractInitializableComponent
implements MetadataFilter {
    @Nonnull
    @NonnullElements
    private List<MetadataNodeProcessor> processors = Collections.emptyList();

    @Nonnull
    @NonnullElements
    @Live
    public List<MetadataNodeProcessor> getNodeProcessors() {
        return this.processors;
    }

    public void setNodeProcessors(@Nonnull @NonnullElements List<MetadataNodeProcessor> newProcessors) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
        Constraint.isNotNull(newProcessors, "MetadataNodeProcessor list cannot be null");
        this.processors = new ArrayList<MetadataNodeProcessor>(Collections2.filter(newProcessors, Predicates.notNull()));
    }

    @Override
    @Nullable
    public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
        ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
        if (metadata == null) {
            return null;
        }
        this.processNode(metadata);
        return metadata;
    }

    @Override
    protected void doDestroy() {
        this.processors = null;
        super.doDestroy();
    }

    protected void processNode(XMLObject node) throws FilterException {
        for (MetadataNodeProcessor processor : this.getNodeProcessors()) {
            processor.process(node);
        }
        List<XMLObject> children = node.getOrderedChildren();
        if (children != null) {
            for (XMLObject child : node.getOrderedChildren()) {
                if (child == null) continue;
                this.processNode(child);
            }
        }
    }
}

