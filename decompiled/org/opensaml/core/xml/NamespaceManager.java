/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import com.google.common.base.Strings;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.Namespace;
import org.opensaml.core.xml.XMLObject;

public class NamespaceManager {
    public static final String DEFAULT_NS_TOKEN = "#default";
    private static final Namespace XML_NAMESPACE = new Namespace("http://www.w3.org/XML/1998/namespace", "xml");
    private static final Namespace XSI_NAMESPACE = new Namespace("http://www.w3.org/2001/XMLSchema-instance", "xsi");
    @Nonnull
    private final XMLObject owner;
    private Namespace elementName;
    private Namespace elementType;
    @Nonnull
    private final Set<Namespace> decls;
    @Nonnull
    private final Set<Namespace> attrNames;
    @Nonnull
    private final Map<String, Namespace> attrValues;
    private Namespace contentValue;

    public NamespaceManager(@Nonnull XMLObject owningObject) {
        this.owner = Constraint.isNotNull(owningObject, "Owner XMLObject cannot be null");
        this.decls = new LazySet<Namespace>();
        this.attrNames = new LazySet<Namespace>();
        this.attrValues = new LazyMap<String, Namespace>();
    }

    @Nonnull
    @NotEmpty
    public static String generateAttributeID(@Nonnull QName name) {
        return name.toString();
    }

    @Nonnull
    public XMLObject getOwner() {
        return this.owner;
    }

    @Nonnull
    public Set<Namespace> getNamespaces() {
        Set<Namespace> namespaces = this.mergeNamespaceCollections(this.decls, this.attrNames, this.attrValues.values());
        this.addNamespace(namespaces, this.getElementNameNamespace());
        this.addNamespace(namespaces, this.getElementTypeNamespace());
        this.addNamespace(namespaces, this.contentValue);
        return Collections.unmodifiableSet(namespaces);
    }

    public void registerNamespaceDeclaration(@Nonnull Namespace namespace) {
        this.addNamespace(this.decls, namespace);
    }

    public void deregisterNamespaceDeclaration(@Nonnull Namespace namespace) {
        this.removeNamespace(this.decls, namespace);
    }

    @Nonnull
    public Set<Namespace> getNamespaceDeclarations() {
        return Collections.unmodifiableSet(this.decls);
    }

    public void registerAttributeName(@Nonnull QName attributeName) {
        if (this.checkQName(attributeName)) {
            this.addNamespace(this.attrNames, this.buildNamespace(attributeName));
        }
    }

    public void deregisterAttributeName(@Nonnull QName attributeName) {
        if (this.checkQName(attributeName)) {
            this.removeNamespace(this.attrNames, this.buildNamespace(attributeName));
        }
    }

    public void registerAttributeValue(@Nonnull String attributeID, @Nonnull QName attributeValue) {
        if (this.checkQName(attributeValue)) {
            this.attrValues.put(attributeID, this.buildNamespace(attributeValue));
        }
    }

    public void deregisterAttributeValue(@Nonnull String attributeID) {
        this.attrValues.remove(attributeID);
    }

    public void registerContentValue(@Nonnull QName content) {
        if (this.checkQName(content)) {
            this.contentValue = this.buildNamespace(content);
        }
    }

    public void deregisterContentValue() {
        this.contentValue = null;
    }

    @Nonnull
    public Set<String> getNonVisibleNamespacePrefixes() {
        LazySet<String> prefixes = new LazySet<String>();
        this.addPrefixes(prefixes, this.getNonVisibleNamespaces());
        return prefixes;
    }

    @Nonnull
    public Set<Namespace> getNonVisibleNamespaces() {
        LazySet<Namespace> nonVisibleCandidates = new LazySet<Namespace>();
        List<XMLObject> children = this.getOwner().getOrderedChildren();
        if (children != null) {
            for (XMLObject child : children) {
                Set<Namespace> childNonVisibleNamespaces;
                if (child == null || (childNonVisibleNamespaces = child.getNamespaceManager().getNonVisibleNamespaces()).isEmpty()) continue;
                nonVisibleCandidates.addAll((Collection<Namespace>)childNonVisibleNamespaces);
            }
        }
        nonVisibleCandidates.addAll((Collection<Namespace>)this.getNonVisibleNamespaceCandidates());
        nonVisibleCandidates.removeAll(this.getVisibleNamespaces());
        nonVisibleCandidates.remove(XML_NAMESPACE);
        return nonVisibleCandidates;
    }

    @Nonnull
    public Set<Namespace> getAllNamespacesInSubtreeScope() {
        LazySet<Namespace> namespaces = new LazySet<Namespace>();
        List<XMLObject> children = this.getOwner().getOrderedChildren();
        if (children != null) {
            for (XMLObject child : children) {
                Set<Namespace> childNamespaces;
                if (child == null || (childNamespaces = child.getNamespaceManager().getAllNamespacesInSubtreeScope()).isEmpty()) continue;
                namespaces.addAll((Collection<Namespace>)childNamespaces);
            }
        }
        for (Namespace myNS : this.getNamespaces()) {
            namespaces.add(myNS);
        }
        return namespaces;
    }

    public void registerElementName(@Nonnull QName name) {
        if (this.checkQName(name)) {
            this.elementName = this.buildNamespace(name);
        }
    }

    public void registerElementType(@Nullable QName type) {
        if (type != null) {
            if (this.checkQName(type)) {
                this.elementType = this.buildNamespace(type);
            }
        } else {
            this.elementType = null;
        }
    }

    @Nullable
    private Namespace getElementNameNamespace() {
        if (this.elementName == null && this.checkQName(this.owner.getElementQName())) {
            this.elementName = this.buildNamespace(this.owner.getElementQName());
        }
        return this.elementName;
    }

    @Nullable
    private Namespace getElementTypeNamespace() {
        QName type;
        if (this.elementType == null && (type = this.owner.getSchemaType()) != null && this.checkQName(type)) {
            this.elementType = this.buildNamespace(type);
        }
        return this.elementType;
    }

    @Nonnull
    private Namespace buildNamespace(@Nonnull QName name) {
        Constraint.isNotNull(name, "QName cannot be null");
        String uri = Constraint.isNotNull(StringSupport.trimOrNull(name.getNamespaceURI()), "Namespace URI of QName cannot be null");
        String prefix = StringSupport.trimOrNull(name.getPrefix());
        return new Namespace(uri, prefix);
    }

    private void addNamespace(@Nonnull Set<Namespace> namespaces, @Nullable Namespace newNamespace) {
        if (newNamespace == null) {
            return;
        }
        namespaces.add(newNamespace);
    }

    private void removeNamespace(@Nonnull Set<Namespace> namespaces, @Nullable Namespace oldNamespace) {
        if (oldNamespace == null) {
            return;
        }
        namespaces.remove(oldNamespace);
    }

    @Nonnull
    private Set<Namespace> mergeNamespaceCollections(Collection<Namespace> ... namespaces) {
        LazySet<Namespace> newNamespaces = new LazySet<Namespace>();
        for (Collection<Namespace> nsCollection : namespaces) {
            for (Namespace ns : nsCollection) {
                if (ns == null) continue;
                this.addNamespace(newNamespaces, ns);
            }
        }
        return newNamespaces;
    }

    @Nonnull
    private Set<Namespace> getVisibleNamespaces() {
        LazySet<Namespace> namespaces = new LazySet<Namespace>();
        if (this.getElementNameNamespace() != null) {
            namespaces.add(this.getElementNameNamespace());
        }
        if (this.getElementTypeNamespace() != null) {
            namespaces.add(XSI_NAMESPACE);
        }
        for (Namespace attribName : this.attrNames) {
            if (attribName == null) continue;
            namespaces.add(attribName);
        }
        return namespaces;
    }

    @Nonnull
    private Set<Namespace> getNonVisibleNamespaceCandidates() {
        LazySet<Namespace> namespaces = new LazySet<Namespace>();
        if (this.getElementTypeNamespace() != null) {
            namespaces.add(this.getElementTypeNamespace());
        }
        for (Namespace attribValue : this.attrValues.values()) {
            if (attribValue == null) continue;
            namespaces.add(attribValue);
        }
        if (this.contentValue != null) {
            namespaces.add(this.contentValue);
        }
        return namespaces;
    }

    private void addPrefixes(@Nonnull Set<String> prefixes, @Nonnull Collection<Namespace> namespaces) {
        for (Namespace ns : namespaces) {
            String prefix = StringSupport.trimOrNull(ns.getNamespacePrefix());
            if (prefix == null) {
                prefix = DEFAULT_NS_TOKEN;
            }
            prefixes.add(prefix);
        }
    }

    private boolean checkQName(@Nullable QName name) {
        if (name != null) {
            return !Strings.isNullOrEmpty(name.getNamespaceURI());
        }
        return false;
    }
}

