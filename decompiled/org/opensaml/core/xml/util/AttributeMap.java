/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.util;

import com.google.common.base.Strings;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.NamespaceManager;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NotThreadSafe
public class AttributeMap
implements Map<QName, String> {
    private final Logger log = LoggerFactory.getLogger(AttributeMap.class);
    private final XMLObject attributeOwner;
    private Map<QName, String> attributes;
    private Set<QName> idAttribNames;
    private Set<QName> qnameAttribNames;
    private boolean inferQNameValues;

    public AttributeMap(@Nonnull XMLObject newOwner) {
        Constraint.isNotNull(newOwner, "Attribute owner XMLObject cannot be null");
        this.attributeOwner = newOwner;
        this.attributes = new LazyMap<QName, String>();
        this.idAttribNames = new LazySet<QName>();
        this.qnameAttribNames = new LazySet<QName>();
    }

    @Override
    public String put(QName attributeName, String value) {
        Constraint.isNotNull(attributeName, "Attribute name cannot be null");
        String oldValue = this.get(attributeName);
        if (!Objects.equals(value, oldValue)) {
            this.releaseDOM();
            this.attributes.put(attributeName, value);
            if (this.isIDAttribute(attributeName) || XMLObjectProviderRegistrySupport.isIDAttribute(attributeName)) {
                this.attributeOwner.getIDIndex().deregisterIDMapping(oldValue);
                this.attributeOwner.getIDIndex().registerIDMapping(value, this.attributeOwner);
            }
            if (!Strings.isNullOrEmpty(attributeName.getNamespaceURI())) {
                if (value == null) {
                    this.attributeOwner.getNamespaceManager().deregisterAttributeName(attributeName);
                } else {
                    this.attributeOwner.getNamespaceManager().registerAttributeName(attributeName);
                }
            }
            this.checkAndDeregisterQNameValue(attributeName, oldValue);
            this.checkAndRegisterQNameValue(attributeName, value);
        }
        return oldValue;
    }

    @Override
    public QName put(QName attributeName, QName value) {
        Constraint.isNotNull(attributeName, "Attribute name cannot be null");
        String oldValueString = this.get(attributeName);
        QName oldValue = null;
        if (!Strings.isNullOrEmpty(oldValueString)) {
            oldValue = this.resolveQName(oldValueString, true);
        }
        if (!Objects.equals(oldValue, value)) {
            this.releaseDOM();
            if (value != null) {
                String newStringValue = this.constructAttributeValue(value);
                this.attributes.put(attributeName, newStringValue);
                this.registerQNameValue(attributeName, value);
                this.attributeOwner.getNamespaceManager().registerAttributeName(attributeName);
            } else {
                this.deregisterQNameValue(attributeName);
                this.attributeOwner.getNamespaceManager().deregisterAttributeName(attributeName);
            }
        }
        return oldValue;
    }

    @Override
    public void clear() {
        LazySet<QName> keys2 = new LazySet<QName>();
        keys2.addAll((Collection<QName>)this.attributes.keySet());
        for (QName attributeName : keys2) {
            this.remove(attributeName);
        }
    }

    @Override
    public Set<QName> keySet() {
        return Collections.unmodifiableSet(this.attributes.keySet());
    }

    @Override
    public int size() {
        return this.attributes.size();
    }

    @Override
    public boolean isEmpty() {
        return this.attributes.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.attributes.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.attributes.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return this.attributes.get(key);
    }

    @Override
    public String remove(Object key) {
        String removedValue = this.attributes.remove(key);
        if (removedValue != null) {
            this.releaseDOM();
            QName attributeName = (QName)key;
            if (this.isIDAttribute(attributeName) || XMLObjectProviderRegistrySupport.isIDAttribute(attributeName)) {
                this.attributeOwner.getIDIndex().deregisterIDMapping(removedValue);
            }
            this.attributeOwner.getNamespaceManager().deregisterAttributeName(attributeName);
            this.checkAndDeregisterQNameValue(attributeName, removedValue);
        }
        return removedValue;
    }

    @Override
    public void putAll(Map<? extends QName, ? extends String> t2) {
        if (t2 != null && t2.size() > 0) {
            for (Map.Entry<? extends QName, ? extends String> entry : t2.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Collection<String> values() {
        return Collections.unmodifiableCollection(this.attributes.values());
    }

    @Override
    public Set<Map.Entry<QName, String>> entrySet() {
        return Collections.unmodifiableSet(this.attributes.entrySet());
    }

    public void registerID(QName attributeName) {
        if (!this.idAttribNames.contains(attributeName)) {
            this.idAttribNames.add(attributeName);
        }
        if (this.containsKey(attributeName)) {
            this.attributeOwner.getIDIndex().registerIDMapping(this.get(attributeName), this.attributeOwner);
        }
    }

    public void deregisterID(QName attributeName) {
        if (this.idAttribNames.contains(attributeName)) {
            this.idAttribNames.remove(attributeName);
        }
        if (this.containsKey(attributeName)) {
            this.attributeOwner.getIDIndex().deregisterIDMapping(this.get(attributeName));
        }
    }

    public boolean isIDAttribute(QName attributeName) {
        return this.idAttribNames.contains(attributeName);
    }

    public void registerQNameAttribute(QName attributeName) {
        this.qnameAttribNames.add(attributeName);
    }

    public void deregisterQNameAttribute(QName attributeName) {
        this.qnameAttribNames.remove(attributeName);
    }

    public boolean isQNameAttribute(QName attributeName) {
        return this.qnameAttribNames.contains(attributeName);
    }

    public boolean isInferQNameValues() {
        return this.inferQNameValues;
    }

    public void setInferQNameValues(boolean flag) {
        this.inferQNameValues = flag;
    }

    private void releaseDOM() {
        this.attributeOwner.releaseDOM();
        this.attributeOwner.releaseParentDOM(true);
    }

    private void checkAndRegisterQNameValue(QName attributeName, String attributeValue) {
        if (attributeValue == null) {
            return;
        }
        QName qnameValue = this.checkQName(attributeName, attributeValue);
        if (qnameValue != null) {
            this.log.trace("Attribute '{}' with value '{}' was evaluated to be QName type", (Object)attributeName, (Object)attributeValue);
            this.registerQNameValue(attributeName, qnameValue);
        } else {
            this.log.trace("Attribute '{}' with value '{}' was not evaluated to be QName type", (Object)attributeName, (Object)attributeValue);
        }
    }

    private void registerQNameValue(QName attributeName, QName attributeValue) {
        if (attributeValue == null) {
            return;
        }
        String attributeID = NamespaceManager.generateAttributeID(attributeName);
        this.log.trace("Registering QName attribute value '{}' under attibute ID '{}'", (Object)attributeValue, (Object)attributeID);
        this.attributeOwner.getNamespaceManager().registerAttributeValue(attributeID, attributeValue);
    }

    private void checkAndDeregisterQNameValue(QName attributeName, String attributeValue) {
        if (attributeValue == null) {
            return;
        }
        QName qnameValue = this.checkQName(attributeName, attributeValue);
        if (qnameValue != null) {
            this.log.trace("Attribute '{}' with value '{}' was evaluated to be QName type", (Object)attributeName, (Object)attributeValue);
            this.deregisterQNameValue(attributeName);
        } else {
            this.log.trace("Attribute '{}' with value '{}' was not evaluated to be QName type", (Object)attributeName, (Object)attributeValue);
        }
    }

    private void deregisterQNameValue(QName attributeName) {
        String attributeID = NamespaceManager.generateAttributeID(attributeName);
        this.log.trace("Deregistering QName attribute with attibute ID '{}'", (Object)attributeID);
        this.attributeOwner.getNamespaceManager().deregisterAttributeValue(attributeID);
    }

    private QName checkQName(QName attributeName, String attributeValue) {
        this.log.trace("Checking whether attribute '{}' with value {} is a QName type", (Object)attributeName, (Object)attributeValue);
        if (attributeValue == null) {
            this.log.trace("Attribute value was null, returning null");
            return null;
        }
        if (this.isQNameAttribute(attributeName)) {
            this.log.trace("Configuration indicates attribute with name '{}' is a QName type, resolving value QName", (Object)attributeName);
            QName valueName = this.resolveQName(attributeValue, true);
            if (valueName != null) {
                this.log.trace("Successfully resolved attribute value to QName: {}", (Object)valueName);
            } else {
                this.log.trace("Could not resolve attribute value to QName, returning null");
            }
            return valueName;
        }
        if (this.isInferQNameValues()) {
            this.log.trace("Attempting to infer whether attribute value is a QName");
            QName valueName = this.resolveQName(attributeValue, false);
            if (valueName != null) {
                this.log.trace("Resolved attribute as a QName: '{}'", (Object)valueName);
            } else {
                this.log.trace("Attribute value was not resolveable to a QName, returning null");
            }
            return valueName;
        }
        this.log.trace("Attribute was not registered in configuration as a QName type and QName inference is disabled");
        return null;
    }

    private QName resolveQName(String attributeValue, boolean isDefaultNSOK) {
        if (attributeValue == null) {
            return null;
        }
        this.log.trace("Attemtping to resolve QName from attribute value '{}'", (Object)attributeValue);
        String candidatePrefix = null;
        String localPart = null;
        int ci = attributeValue.indexOf(58);
        if (ci > -1) {
            candidatePrefix = attributeValue.substring(0, ci);
            this.log.trace("Evaluating candiate namespace prefix '{}'", (Object)candidatePrefix);
            localPart = attributeValue.substring(ci + 1);
        } else if (isDefaultNSOK) {
            candidatePrefix = null;
            this.log.trace("Value did not contain a colon, evaluating as default namespace");
            localPart = attributeValue;
        } else {
            this.log.trace("Value did not contain a colon, default namespace is disallowed, returning null");
            return null;
        }
        this.log.trace("Evaluated QName local part as '{}'", (Object)localPart);
        String nsURI = XMLObjectSupport.lookupNamespaceURI(this.attributeOwner, candidatePrefix);
        this.log.trace("Resolved namespace URI '{}'", (Object)nsURI);
        if (nsURI != null) {
            QName name = QNameSupport.constructQName(nsURI, localPart, candidatePrefix);
            this.log.trace("Resolved QName '{}'", (Object)name);
            return name;
        }
        this.log.trace("Namespace URI for candidate prefix '{}' could not be resolved", (Object)candidatePrefix);
        this.log.trace("Value was either not a QName, or namespace URI could not be resolved");
        return null;
    }

    private String constructAttributeValue(QName attributeValue) {
        String trimmedLocalName = StringSupport.trimOrNull(attributeValue.getLocalPart());
        if (trimmedLocalName == null) {
            throw new IllegalArgumentException("Local name may not be null or empty");
        }
        String trimmedPrefix = StringSupport.trimOrNull(attributeValue.getPrefix());
        String qualifiedName = trimmedPrefix != null ? trimmedPrefix + ":" + StringSupport.trimOrNull(trimmedLocalName) : StringSupport.trimOrNull(trimmedLocalName);
        return qualifiedName;
    }
}

