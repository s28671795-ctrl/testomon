/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.DOMTypeSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import net.shibboleth.utilities.java.support.xml.XMLConstants;
import net.shibboleth.utilities.java.support.xml.XMLSpace;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public final class AttributeSupport {
    private AttributeSupport() {
    }

    public static void addXMLBase(@Nonnull Element element, @Nonnull String base) {
        Constraint.isNotNull(element, "Element may not be null");
        Constraint.isNotNull(base, "base attribute value may not be null");
        Attr attr = AttributeSupport.constructAttribute(element.getOwnerDocument(), XMLConstants.XML_BASE_ATTRIB_NAME);
        attr.setValue(base);
        element.setAttributeNodeNS(attr);
    }

    public static void addXMLId(@Nonnull Element element, @Nonnull String id) {
        Constraint.isNotNull(element, "Element may not be null");
        Constraint.isNotNull(id, "id attribute value may not be null");
        Attr attr = AttributeSupport.constructAttribute(element.getOwnerDocument(), XMLConstants.XML_ID_ATTRIB_NAME);
        attr.setValue(id);
        element.setAttributeNodeNS(attr);
        element.setIdAttributeNode(attr, true);
    }

    public static void addXMLLang(@Nonnull Element element, @Nonnull String lang) {
        Constraint.isNotNull(element, "Element may not be null");
        Constraint.isNotNull(lang, "lang attribute value may not be null");
        Attr attr = AttributeSupport.constructAttribute(element.getOwnerDocument(), XMLConstants.XML_LANG_ATTRIB_NAME);
        attr.setValue(lang);
        element.setAttributeNodeNS(attr);
    }

    public static void addXMLSpace(@Nonnull Element element, @Nonnull XMLSpace space) {
        Constraint.isNotNull(element, "Element may not be null");
        Constraint.isNotNull(space, "space attribute value may not be null");
        Attr attr = AttributeSupport.constructAttribute(element.getOwnerDocument(), XMLConstants.XML_SPACE_ATTRIB_NAME);
        attr.setValue(space.toString());
        element.setAttributeNodeNS(attr);
    }

    public static void appendAttribute(@Nonnull Element element, @Nonnull QName attributeName, List<String> attributeValues, boolean isIDAttribute) {
        AttributeSupport.appendAttribute(element, attributeName, StringSupport.listToStringValue(attributeValues, " "), isIDAttribute);
    }

    public static void appendAttribute(@Nonnull Element element, @Nonnull QName attributeName, @Nonnull String attributeValue) {
        AttributeSupport.appendAttribute(element, attributeName, attributeValue, false);
    }

    public static void appendAttribute(@Nonnull Element element, @Nonnull QName attributeName, @Nonnull String attributeValue, boolean isIDAttribute) {
        Constraint.isNotNull(element, "Element may not be null");
        Constraint.isNotNull(attributeName, "Attribute name may not be null");
        Constraint.isNotNull(attributeValue, "Attribute value may not be null");
        Document document = element.getOwnerDocument();
        Attr attribute = AttributeSupport.constructAttribute(document, attributeName);
        attribute.setValue(attributeValue);
        element.setAttributeNodeNS(attribute);
        if (isIDAttribute) {
            element.setIdAttributeNode(attribute, true);
        }
    }

    public static void appendDateTimeAttribute(@Nonnull Element element, @Nonnull QName attributeName, long duration) {
        AttributeSupport.appendAttribute(element, attributeName, DOMTypeSupport.longToDateTime(duration));
    }

    public static void appendDurationAttribute(@Nonnull Element element, @Nonnull QName attributeName, long duration) {
        AttributeSupport.appendAttribute(element, attributeName, DOMTypeSupport.longToDuration(duration));
    }

    @Nonnull
    public static Attr constructAttribute(@Nonnull Document owningDocument, @Nonnull QName attributeName) {
        Constraint.isNotNull(attributeName, "Attribute name can not be null");
        return AttributeSupport.constructAttribute(owningDocument, attributeName.getNamespaceURI(), attributeName.getLocalPart(), attributeName.getPrefix());
    }

    @Nonnull
    public static Attr constructAttribute(@Nonnull Document document, @Nullable String namespaceURI, @Nonnull String localName, @Nullable String prefix) {
        Constraint.isNotNull(document, "Document may not null");
        String trimmedLocalName = Constraint.isNotNull(StringSupport.trimOrNull(localName), "Attribute local name may not be null or empty");
        String trimmedPrefix = StringSupport.trimOrNull(prefix);
        String qualifiedName = trimmedPrefix != null ? trimmedPrefix + ":" + StringSupport.trimOrNull(trimmedLocalName) : StringSupport.trimOrNull(trimmedLocalName);
        return document.createAttributeNS(StringSupport.trimOrNull(namespaceURI), qualifiedName);
    }

    @Nonnull
    public static Attr getAttribute(@Nullable Element element, @Nullable QName attributeName) {
        if (element == null || attributeName == null) {
            return null;
        }
        return element.getAttributeNodeNS(StringSupport.trimOrNull(attributeName.getNamespaceURI()), attributeName.getLocalPart());
    }

    @Nullable
    public static String getAttributeValue(@Nullable Element element, @Nullable QName attributeName) {
        if (element == null || attributeName == null) {
            return null;
        }
        return AttributeSupport.getAttributeValue(element, StringSupport.trimOrNull(attributeName.getNamespaceURI()), attributeName.getLocalPart());
    }

    @Nullable
    public static String getAttributeValue(@Nullable Element element, @Nullable String namespace, @Nullable String attributeLocalName) {
        if (element == null || attributeLocalName == null) {
            return null;
        }
        Attr attr = element.getAttributeNodeNS(namespace, attributeLocalName);
        if (attr == null) {
            return null;
        }
        return attr.getValue();
    }

    @Nullable
    public static Boolean getAttributeValueAsBoolean(@Nullable Attr attribute) {
        if (attribute == null) {
            return null;
        }
        String valueStr = StringSupport.trimOrNull(attribute.getValue());
        if ("0".equals(valueStr) || "false".equals(valueStr)) {
            return Boolean.FALSE;
        }
        if ("1".equals(valueStr) || "true".equals(valueStr)) {
            return Boolean.TRUE;
        }
        return null;
    }

    @Nonnull
    public static List<String> getAttributeValueAsList(@Nullable Attr attribute) {
        if (attribute == null) {
            return Collections.emptyList();
        }
        return StringSupport.stringToList(attribute.getValue(), " \n\r\t");
    }

    @Nullable
    public static QName getAttributeValueAsQName(@Nullable Attr attribute) {
        if (attribute == null) {
            return null;
        }
        String attributeValue = StringSupport.trimOrNull(attribute.getTextContent());
        if (attributeValue == null) {
            return null;
        }
        String[] valueComponents = attributeValue.split(":");
        if (valueComponents.length == 1) {
            return QNameSupport.constructQName(attribute.lookupNamespaceURI(null), valueComponents[0], null);
        }
        return QNameSupport.constructQName(attribute.lookupNamespaceURI(valueComponents[0]), valueComponents[1], valueComponents[0]);
    }

    @Nullable
    public static Long getDateTimeAttributeAsLong(@Nullable Attr attribute) {
        if (attribute == null || StringSupport.trimOrNull(attribute.getValue()) == null) {
            return null;
        }
        return DOMTypeSupport.dateTimeToLong(attribute.getValue());
    }

    @Nullable
    public static Long getDurationAttributeValueAsLong(@Nullable Attr attribute) {
        if (attribute == null || StringSupport.trimOrNull(attribute.getValue()) == null) {
            return null;
        }
        return DOMTypeSupport.durationToLong(attribute.getValue());
    }

    @Nullable
    public static Attr getIdAttribute(@Nullable Element element) {
        if (element == null || !element.hasAttributes()) {
            return null;
        }
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); ++i) {
            Attr attribute = (Attr)attributes.item(i);
            if (!attribute.isId()) continue;
            return attribute;
        }
        return null;
    }

    @Nullable
    public static String getXMLBase(@Nullable Element element) {
        return AttributeSupport.getAttributeValue(element, XMLConstants.XML_BASE_ATTRIB_NAME);
    }

    @Nullable
    public static String getXMLId(@Nullable Element element) {
        return AttributeSupport.getAttributeValue(element, XMLConstants.XML_ID_ATTRIB_NAME);
    }

    @Nullable
    public static String getXMLLang(@Nullable Element element) {
        return AttributeSupport.getAttributeValue(element, XMLConstants.XML_LANG_ATTRIB_NAME);
    }

    @Nullable
    public static Locale getXMLLangAsLocale(@Nullable Element element) {
        if (element == null) {
            return null;
        }
        String lang = StringSupport.trimOrNull(AttributeSupport.getXMLLang(element));
        if (lang != null) {
            if (lang.contains("-")) {
                lang = lang.substring(0, lang.indexOf("-"));
            }
            return new Locale(lang.toUpperCase());
        }
        return Locale.getDefault();
    }

    @Nullable
    public static XMLSpace getXMLSpace(@Nullable Element element) {
        if (null == element) {
            return null;
        }
        String value = AttributeSupport.getAttributeValue(element, XMLConstants.XML_SPACE_ATTRIB_NAME);
        if (null == value) {
            return null;
        }
        try {
            return XMLSpace.parseValue(value);
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean hasAttribute(@Nullable Element element, @Nullable QName name) {
        if (element == name || name == null) {
            return false;
        }
        return element.hasAttributeNS(StringSupport.trimOrNull(name.getNamespaceURI()), name.getLocalPart());
    }

    public static boolean removeAttribute(@Nullable Element element, @Nullable QName attributeName) {
        if (AttributeSupport.hasAttribute(element, attributeName)) {
            element.removeAttributeNS(StringSupport.trimOrNull(attributeName.getNamespaceURI()), attributeName.getLocalPart());
            return true;
        }
        return false;
    }
}

