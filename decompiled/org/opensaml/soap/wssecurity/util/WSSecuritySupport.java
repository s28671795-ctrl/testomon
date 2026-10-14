/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wssecurity.util;

import java.util.List;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wssecurity.IdBearing;
import org.opensaml.soap.wssecurity.TokenTypeBearing;
import org.opensaml.soap.wssecurity.UsageBearing;

public final class WSSecuritySupport {
    private WSSecuritySupport() {
    }

    public static void addWSUId(XMLObject soapObject, String id) {
        if (soapObject instanceof IdBearing) {
            ((IdBearing)((Object)soapObject)).setWSUId(id);
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(IdBearing.WSU_ID_ATTR_NAME, id);
        } else {
            throw new IllegalArgumentException("Specified object was neither IdBearing nor AttributeExtensible");
        }
    }

    public static String getWSUId(XMLObject soapObject) {
        String value = null;
        if (soapObject instanceof IdBearing && (value = StringSupport.trimOrNull(((IdBearing)((Object)soapObject)).getWSUId())) != null) {
            return value;
        }
        if (soapObject instanceof AttributeExtensibleXMLObject) {
            value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(IdBearing.WSU_ID_ATTR_NAME));
            return value;
        }
        return null;
    }

    public static void addWSSE11TokenType(XMLObject soapObject, String tokenType) {
        if (soapObject instanceof TokenTypeBearing) {
            ((TokenTypeBearing)((Object)soapObject)).setWSSE11TokenType(tokenType);
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(TokenTypeBearing.WSSE11_TOKEN_TYPE_ATTR_NAME, tokenType);
        } else {
            throw new IllegalArgumentException("Specified object was neither TokenTypeBearing nor AttributeExtensible");
        }
    }

    public static String getWSSE11TokenType(XMLObject soapObject) {
        String value = null;
        if (soapObject instanceof TokenTypeBearing && (value = StringSupport.trimOrNull(((TokenTypeBearing)((Object)soapObject)).getWSSE11TokenType())) != null) {
            return value;
        }
        if (soapObject instanceof AttributeExtensibleXMLObject) {
            value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(TokenTypeBearing.WSSE11_TOKEN_TYPE_ATTR_NAME));
            return value;
        }
        return null;
    }

    public static void addWSSEUsage(XMLObject soapObject, String usage) {
        if (soapObject instanceof UsageBearing) {
            UsageBearing usageBearing = (UsageBearing)((Object)soapObject);
            List<String> list = usageBearing.getWSSEUsages();
            if (list == null) {
                list = new LazyList<String>();
                usageBearing.setWSSEUsages(list);
            }
            list.add(usage);
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            AttributeMap am = ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes();
            String list = am.get(UsageBearing.WSSE_USAGE_ATTR_NAME);
            list = list == null ? usage : list + " " + usage;
            am.put(UsageBearing.WSSE_USAGE_ATTR_NAME, list);
        } else {
            throw new IllegalArgumentException("Specified object was neither UsageBearing nor AttributeExtensible");
        }
    }

    public static void addWSSEUsages(XMLObject soapObject, List<String> usages) {
        if (soapObject instanceof UsageBearing) {
            ((UsageBearing)((Object)soapObject)).setWSSEUsages(usages);
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(UsageBearing.WSSE_USAGE_ATTR_NAME, StringSupport.listToStringValue(usages, " "));
        } else {
            throw new IllegalArgumentException("Specified object was neither UsageBearing nor AttributeExtensible");
        }
    }

    public static List<String> getWSSEUsages(XMLObject soapObject) {
        Object value;
        if (soapObject instanceof UsageBearing && (value = ((UsageBearing)((Object)soapObject)).getWSSEUsages()) != null) {
            return value;
        }
        if (soapObject instanceof AttributeExtensibleXMLObject && (value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(UsageBearing.WSSE_USAGE_ATTR_NAME))) != null) {
            StringSupport.stringToList((String)value, " \n\r\t");
        }
        return null;
    }
}

