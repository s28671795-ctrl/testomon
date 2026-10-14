/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LockableClassToInstanceMultiMap;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import net.shibboleth.utilities.java.support.xml.XMLConstants;
import org.opensaml.core.xml.Namespace;
import org.opensaml.core.xml.NamespaceManager;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.IDIndex;
import org.opensaml.core.xml.util.XMLObjectSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public abstract class AbstractXMLObject
implements XMLObject {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AbstractXMLObject.class);
    private XMLObject parent;
    @Nonnull
    private QName elementQname;
    private String schemaLocation;
    private String noNamespaceSchemaLocation;
    private QName typeQname;
    private Element dom;
    private XSBooleanValue nil;
    private NamespaceManager nsManager = new NamespaceManager(this);
    @Nonnull
    private final LockableClassToInstanceMultiMap<Object> objectMetadata;
    private final IDIndex idIndex = new IDIndex(this);

    protected AbstractXMLObject(@Nullable String namespaceURI, @Nonnull @NotEmpty String elementLocalName, @Nullable String namespacePrefix) {
        this.elementQname = QNameSupport.constructQName(namespaceURI, elementLocalName, namespacePrefix);
        if (namespaceURI != null) {
            this.setElementNamespacePrefix(namespacePrefix);
        }
        this.objectMetadata = new LockableClassToInstanceMultiMap(true);
    }

    @Override
    public void detach() {
        this.releaseParentDOM(true);
        this.parent = null;
    }

    @Override
    @Nullable
    public Element getDOM() {
        return this.dom;
    }

    @Override
    @Nonnull
    public QName getElementQName() {
        return this.elementQname;
    }

    @Override
    @Nonnull
    public IDIndex getIDIndex() {
        return this.idIndex;
    }

    @Override
    @Nonnull
    public NamespaceManager getNamespaceManager() {
        return this.nsManager;
    }

    @Override
    @Nonnull
    public Set<Namespace> getNamespaces() {
        return this.getNamespaceManager().getNamespaces();
    }

    @Override
    @Nullable
    public String getNoNamespaceSchemaLocation() {
        return this.noNamespaceSchemaLocation;
    }

    @Override
    @Nullable
    public XMLObject getParent() {
        return this.parent;
    }

    @Override
    @Nullable
    public String getSchemaLocation() {
        return this.schemaLocation;
    }

    @Override
    @Nullable
    public QName getSchemaType() {
        return this.typeQname;
    }

    @Override
    public boolean hasChildren() {
        List<XMLObject> children = this.getOrderedChildren();
        return children != null && children.size() > 0;
    }

    @Override
    public boolean hasParent() {
        return this.getParent() != null;
    }

    protected void manageQualifiedAttributeNamespace(@Nonnull QName attributeName, boolean hasValue) {
        if (hasValue) {
            this.getNamespaceManager().registerAttributeName(attributeName);
        } else {
            this.getNamespaceManager().deregisterAttributeName(attributeName);
        }
    }

    @Nullable
    protected QName prepareElementContentForAssignment(@Nullable QName oldValue, @Nullable QName newValue) {
        if (oldValue == null) {
            if (newValue != null) {
                this.getNamespaceManager().registerContentValue(newValue);
                this.releaseThisandParentDOM();
                return newValue;
            }
            return null;
        }
        this.getNamespaceManager().deregisterContentValue();
        if (!oldValue.equals(newValue)) {
            if (newValue != null) {
                this.getNamespaceManager().registerContentValue(newValue);
            }
            this.releaseThisandParentDOM();
        }
        return newValue;
    }

    @Nullable
    protected QName prepareAttributeValueForAssignment(@Nonnull String attributeID, @Nullable QName oldValue, @Nullable QName newValue) {
        if (oldValue == null) {
            if (newValue != null) {
                this.getNamespaceManager().registerAttributeValue(attributeID, newValue);
                this.releaseThisandParentDOM();
                return newValue;
            }
            return null;
        }
        this.getNamespaceManager().deregisterAttributeValue(attributeID);
        if (!oldValue.equals(newValue)) {
            if (newValue != null) {
                this.getNamespaceManager().registerAttributeValue(attributeID, newValue);
            }
            this.releaseThisandParentDOM();
        }
        return newValue;
    }

    @Nullable
    protected String prepareForAssignment(@Nullable String oldValue, @Nullable String newValue) {
        return this.prepareForAssignment(oldValue, newValue, true);
    }

    @Nullable
    protected String prepareForAssignment(@Nullable String oldValue, @Nullable String newValue, boolean normalize) {
        String newString = newValue;
        if (normalize) {
            newString = StringSupport.trimOrNull(newString);
        }
        if (!Objects.equals(oldValue, newString)) {
            this.releaseThisandParentDOM();
        }
        return newString;
    }

    @Nullable
    protected <T> T prepareForAssignment(@Nullable T oldValue, @Nullable T newValue) {
        if (oldValue == null) {
            if (newValue != null) {
                this.releaseThisandParentDOM();
                return newValue;
            }
            return null;
        }
        if (!oldValue.equals(newValue)) {
            this.releaseThisandParentDOM();
        }
        return newValue;
    }

    @Nullable
    protected <T extends XMLObject> T prepareForAssignment(@Nullable T oldValue, @Nullable T newValue) {
        if (newValue != null && newValue.hasParent()) {
            throw new IllegalArgumentException(newValue.getClass().getName() + " cannot be added - it is already the child of another XML Object");
        }
        if (oldValue == null) {
            if (newValue != null) {
                this.releaseThisandParentDOM();
                newValue.setParent(this);
                this.idIndex.registerIDMappings(newValue.getIDIndex());
                return newValue;
            }
            return null;
        }
        if (!oldValue.equals(newValue)) {
            oldValue.setParent(null);
            this.releaseThisandParentDOM();
            this.idIndex.deregisterIDMappings(oldValue.getIDIndex());
            if (newValue != null) {
                newValue.setParent(this);
                this.idIndex.registerIDMappings(newValue.getIDIndex());
            }
        }
        return newValue;
    }

    protected void registerOwnID(@Nullable String oldID, @Nullable String newID) {
        String newString = StringSupport.trimOrNull(newID);
        if (!Objects.equals(oldID, newString)) {
            if (oldID != null) {
                this.idIndex.deregisterIDMapping(oldID);
            }
            if (newString != null) {
                this.idIndex.registerIDMapping(newString, this);
            }
        }
    }

    @Override
    public void releaseChildrenDOM(boolean propagateRelease) {
        this.log.trace("Releasing cached DOM reprsentation for children of {} with propagation set to {}", (Object)this.getElementQName(), (Object)propagateRelease);
        if (this.getOrderedChildren() != null) {
            for (XMLObject child : this.getOrderedChildren()) {
                if (child == null) continue;
                child.releaseDOM();
                if (!propagateRelease) continue;
                child.releaseChildrenDOM(propagateRelease);
            }
        }
    }

    @Override
    public void releaseDOM() {
        this.log.trace("Releasing cached DOM reprsentation for {}", (Object)this.getElementQName());
        this.setDOM(null);
        if (this.getObjectMetadata().containsKey(XMLObjectSource.class)) {
            this.log.trace("Releasing cached XMLObjectSource for {}", (Object)this.getElementQName());
            this.getObjectMetadata().remove(XMLObjectSource.class);
        }
    }

    @Override
    public void releaseParentDOM(boolean propagateRelease) {
        this.log.trace("Releasing cached DOM reprsentation for parent of {} with propagation set to {}", (Object)this.getElementQName(), (Object)propagateRelease);
        XMLObject parentElement = this.getParent();
        if (parentElement != null) {
            this.parent.releaseDOM();
            if (propagateRelease) {
                this.parent.releaseParentDOM(propagateRelease);
            }
        }
    }

    public void releaseThisAndChildrenDOM() {
        if (this.getDOM() != null) {
            this.releaseDOM();
            this.releaseChildrenDOM(true);
        }
    }

    public void releaseThisandParentDOM() {
        if (this.getDOM() != null) {
            this.releaseDOM();
            this.releaseParentDOM(true);
        }
    }

    @Override
    @Nullable
    public XMLObject resolveID(@Nonnull @NotEmpty String id) {
        return this.idIndex.lookup(id);
    }

    @Override
    @Nullable
    public XMLObject resolveIDFromRoot(@Nonnull @NotEmpty String id) {
        XMLObject root = this;
        while (root.hasParent()) {
            root = root.getParent();
        }
        return root.resolveID(id);
    }

    @Override
    public void setDOM(@Nullable Element newDom) {
        this.dom = newDom;
    }

    public void setElementNamespacePrefix(@Nullable String prefix) {
        this.elementQname = prefix == null ? new QName(this.elementQname.getNamespaceURI(), this.elementQname.getLocalPart()) : new QName(this.elementQname.getNamespaceURI(), this.elementQname.getLocalPart(), prefix);
        this.getNamespaceManager().registerElementName(this.elementQname);
    }

    protected void setElementQName(@Nonnull QName name) {
        Constraint.isNotNull(name, "Element QName cannot be null");
        this.elementQname = QNameSupport.constructQName(name.getNamespaceURI(), name.getLocalPart(), name.getPrefix());
        this.getNamespaceManager().registerElementName(this.elementQname);
    }

    @Override
    public void setNoNamespaceSchemaLocation(@Nullable String location) {
        this.noNamespaceSchemaLocation = StringSupport.trimOrNull(location);
        this.manageQualifiedAttributeNamespace(XMLConstants.XSI_NO_NAMESPACE_SCHEMA_LOCATION_ATTRIB_NAME, this.noNamespaceSchemaLocation != null);
    }

    @Override
    public void setParent(@Nullable XMLObject newParent) {
        this.parent = newParent;
    }

    @Override
    public void setSchemaLocation(@Nullable String location) {
        this.schemaLocation = StringSupport.trimOrNull(location);
        this.manageQualifiedAttributeNamespace(XMLConstants.XSI_SCHEMA_LOCATION_ATTRIB_NAME, this.schemaLocation != null);
    }

    protected void setSchemaType(@Nullable QName type) {
        this.typeQname = type;
        this.getNamespaceManager().registerElementType(this.typeQname);
        this.manageQualifiedAttributeNamespace(XMLConstants.XSI_TYPE_ATTRIB_NAME, this.typeQname != null);
    }

    @Override
    public Boolean isNil() {
        if (this.nil != null) {
            return this.nil.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public XSBooleanValue isNilXSBoolean() {
        return this.nil;
    }

    @Override
    public void setNil(@Nullable Boolean newNil) {
        this.nil = newNil != null ? this.prepareForAssignment(this.nil, new XSBooleanValue(newNil, false)) : (XSBooleanValue)this.prepareForAssignment((T)this.nil, (T)null);
        this.manageQualifiedAttributeNamespace(XMLConstants.XSI_NIL_ATTRIB_NAME, this.nil != null);
    }

    @Override
    public void setNil(@Nullable XSBooleanValue newNil) {
        this.nil = this.prepareForAssignment(this.nil, newNil);
        this.manageQualifiedAttributeNamespace(XMLConstants.XSI_NIL_ATTRIB_NAME, this.nil != null);
    }

    @Override
    @Nonnull
    public LockableClassToInstanceMultiMap<Object> getObjectMetadata() {
        return this.objectMetadata;
    }
}

