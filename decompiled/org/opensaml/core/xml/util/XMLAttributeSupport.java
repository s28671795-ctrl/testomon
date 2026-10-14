/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.util;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.BaseBearing;
import org.opensaml.core.xml.IdBearing;
import org.opensaml.core.xml.LangBearing;
import org.opensaml.core.xml.SpaceBearing;
import org.opensaml.core.xml.XMLObject;

public final class XMLAttributeSupport {
    private XMLAttributeSupport() {
    }

    public static void addXMLId(XMLObject xmlObject, String id) {
        if (xmlObject instanceof IdBearing) {
            ((IdBearing)((Object)xmlObject)).setXMLId(id);
        } else if (xmlObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().put(IdBearing.XML_ID_ATTR_NAME, id);
        } else {
            throw new IllegalArgumentException("Specified object was neither IdBearing nor AttributeExtensible");
        }
    }

    public static String getXMLId(XMLObject xmlObject) {
        String value = null;
        if (xmlObject instanceof IdBearing && (value = StringSupport.trimOrNull(((IdBearing)((Object)xmlObject)).getXMLId())) != null) {
            return value;
        }
        if (xmlObject instanceof AttributeExtensibleXMLObject) {
            value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().get(IdBearing.XML_ID_ATTR_NAME));
            return value;
        }
        return null;
    }

    public static void addXMLLang(XMLObject xmlObject, String lang) {
        if (xmlObject instanceof LangBearing) {
            ((LangBearing)((Object)xmlObject)).setXMLLang(lang);
        } else if (xmlObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().put(LangBearing.XML_LANG_ATTR_NAME, lang);
        } else {
            throw new IllegalArgumentException("Specified object was neither LangBearing nor AttributeExtensible");
        }
    }

    public static String getXMLLang(XMLObject xmlObject) {
        String value = null;
        if (xmlObject instanceof LangBearing && (value = StringSupport.trimOrNull(((LangBearing)((Object)xmlObject)).getXMLLang())) != null) {
            return value;
        }
        if (xmlObject instanceof AttributeExtensibleXMLObject) {
            value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().get(LangBearing.XML_LANG_ATTR_NAME));
            return value;
        }
        return null;
    }

    public static void addXMLBase(XMLObject xmlObject, String base) {
        if (xmlObject instanceof BaseBearing) {
            ((BaseBearing)((Object)xmlObject)).setXMLBase(base);
        } else if (xmlObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().put(BaseBearing.XML_BASE_ATTR_NAME, base);
        } else {
            throw new IllegalArgumentException("Specified object was neither BaseBearing nor AttributeExtensible");
        }
    }

    public static String getXMLBase(XMLObject xmlObject) {
        String value = null;
        if (xmlObject instanceof BaseBearing && (value = StringSupport.trimOrNull(((BaseBearing)((Object)xmlObject)).getXMLBase())) != null) {
            return value;
        }
        if (xmlObject instanceof AttributeExtensibleXMLObject) {
            value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().get(BaseBearing.XML_BASE_ATTR_NAME));
            return value;
        }
        return null;
    }

    public static void addXMLSpace(XMLObject xmlObject, SpaceBearing.XMLSpaceEnum space) {
        if (xmlObject instanceof SpaceBearing) {
            ((SpaceBearing)((Object)xmlObject)).setXMLSpace(space);
        } else if (xmlObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().put(SpaceBearing.XML_SPACE_ATTR_NAME, space.toString());
        } else {
            throw new IllegalArgumentException("Specified object was neither SpaceBearing nor AttributeExtensible");
        }
    }

    public static SpaceBearing.XMLSpaceEnum getXMLSpace(XMLObject xmlObject) {
        SpaceBearing.XMLSpaceEnum valueEnum = null;
        if (xmlObject instanceof SpaceBearing && (valueEnum = ((SpaceBearing)((Object)xmlObject)).getXMLSpace()) != null) {
            return valueEnum;
        }
        String valueString = null;
        if (xmlObject instanceof AttributeExtensibleXMLObject && (valueString = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)xmlObject).getUnknownAttributes().get(SpaceBearing.XML_SPACE_ATTR_NAME))) != null) {
            return SpaceBearing.XMLSpaceEnum.parseValue(valueString);
        }
        return null;
    }
}

