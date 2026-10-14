/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.soap.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.soap11.ActorBearing;
import org.opensaml.soap.soap11.Detail;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Fault;
import org.opensaml.soap.soap11.FaultActor;
import org.opensaml.soap.soap11.FaultCode;
import org.opensaml.soap.soap11.FaultString;
import org.opensaml.soap.soap12.EncodingStyleBearing;
import org.opensaml.soap.soap12.MustUnderstandBearing;
import org.opensaml.soap.soap12.RelayBearing;
import org.opensaml.soap.soap12.RoleBearing;

public final class SOAPSupport {
    private SOAPSupport() {
    }

    public static void addSOAP11MustUnderstandAttribute(@Nonnull XMLObject soapObject, boolean mustUnderstand) {
        if (soapObject instanceof org.opensaml.soap.soap11.MustUnderstandBearing) {
            ((org.opensaml.soap.soap11.MustUnderstandBearing)((Object)soapObject)).setSOAP11MustUnderstand(new XSBooleanValue(mustUnderstand, true));
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(org.opensaml.soap.soap11.MustUnderstandBearing.SOAP11_MUST_UNDERSTAND_ATTR_NAME, new XSBooleanValue(mustUnderstand, true).toString());
        } else {
            throw new IllegalArgumentException("Specified object was neither MustUnderstandBearing nor AttributeExtensible");
        }
    }

    public static boolean getSOAP11MustUnderstandAttribute(@Nonnull XMLObject soapObject) {
        Object value;
        if (soapObject instanceof org.opensaml.soap.soap11.MustUnderstandBearing && (value = ((org.opensaml.soap.soap11.MustUnderstandBearing)((Object)soapObject)).isSOAP11MustUnderstandXSBoolean()) != null) {
            return ((XSBooleanValue)value).getValue();
        }
        if (soapObject instanceof AttributeExtensibleXMLObject) {
            value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(org.opensaml.soap.soap11.MustUnderstandBearing.SOAP11_MUST_UNDERSTAND_ATTR_NAME));
            return Objects.equals("1", value) || Objects.equals("true", value);
        }
        return false;
    }

    public static void addSOAP11ActorAttribute(@Nonnull XMLObject soapObject, @Nonnull String actorURI) {
        String value = Constraint.isNotNull(StringSupport.trimOrNull(actorURI), "Actor URI cannot be null or empty");
        if (soapObject instanceof ActorBearing) {
            ((ActorBearing)((Object)soapObject)).setSOAP11Actor(value);
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(ActorBearing.SOAP11_ACTOR_ATTR_NAME, value);
        } else {
            throw new IllegalArgumentException("Specified object was neither ActorBearing nor AttributeExtensible");
        }
    }

    @Nullable
    public static String getSOAP11ActorAttribute(@Nonnull XMLObject soapObject) {
        String value = null;
        if (soapObject instanceof ActorBearing && (value = StringSupport.trimOrNull(((ActorBearing)((Object)soapObject)).getSOAP11Actor())) != null) {
            return value;
        }
        if (soapObject instanceof AttributeExtensibleXMLObject) {
            value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(ActorBearing.SOAP11_ACTOR_ATTR_NAME));
            return value;
        }
        return null;
    }

    public static void addSOAP11EncodingStyle(@Nonnull XMLObject soapObject, @Nonnull String encodingStyle) {
        String value = Constraint.isNotNull(StringSupport.trimOrNull(encodingStyle), "Encoding style to add cannot be null or empty");
        if (soapObject instanceof org.opensaml.soap.soap11.EncodingStyleBearing) {
            org.opensaml.soap.soap11.EncodingStyleBearing esb = (org.opensaml.soap.soap11.EncodingStyleBearing)((Object)soapObject);
            List<String> list = esb.getSOAP11EncodingStyles();
            if (list == null) {
                list = new LazyList<String>();
                esb.setSOAP11EncodingStyles(list);
            }
            list.add(value);
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            AttributeMap am = ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes();
            String list = am.get(org.opensaml.soap.soap11.EncodingStyleBearing.SOAP11_ENCODING_STYLE_ATTR_NAME);
            list = list == null ? value : list + " " + value;
            am.put(org.opensaml.soap.soap11.EncodingStyleBearing.SOAP11_ENCODING_STYLE_ATTR_NAME, list);
        } else {
            throw new IllegalArgumentException("Specified object was neither EncodingStyleBearing nor AttributeExtensible");
        }
    }

    public static void addSOAP11EncodingStyles(@Nonnull XMLObject soapObject, @Nonnull List<String> encodingStyles) {
        Constraint.isNotEmpty(encodingStyles, "Encoding styles list cannot be empty");
        if (soapObject instanceof org.opensaml.soap.soap11.EncodingStyleBearing) {
            ((org.opensaml.soap.soap11.EncodingStyleBearing)((Object)soapObject)).setSOAP11EncodingStyles(encodingStyles);
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(org.opensaml.soap.soap11.EncodingStyleBearing.SOAP11_ENCODING_STYLE_ATTR_NAME, StringSupport.listToStringValue(encodingStyles, " "));
        } else {
            throw new IllegalArgumentException("Specified object was neither EncodingStyleBearing nor AttributeExtensible");
        }
    }

    @Nullable
    public static List<String> getSOAP11EncodingStyles(@Nonnull XMLObject soapObject) {
        Object value;
        if (soapObject instanceof org.opensaml.soap.soap11.EncodingStyleBearing && (value = ((org.opensaml.soap.soap11.EncodingStyleBearing)((Object)soapObject)).getSOAP11EncodingStyles()) != null) {
            return value;
        }
        if (soapObject instanceof AttributeExtensibleXMLObject && (value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(org.opensaml.soap.soap11.EncodingStyleBearing.SOAP11_ENCODING_STYLE_ATTR_NAME))) != null) {
            StringSupport.stringToList((String)value, " \n\r\t");
        }
        return null;
    }

    public static void addSOAP12EncodingStyleAttribute(@Nonnull XMLObject soapObject, @Nonnull String style) {
        String value = Constraint.isNotNull(StringSupport.trimOrNull(style), "Encoding style to add cannot be null or empty");
        if (soapObject instanceof EncodingStyleBearing) {
            ((EncodingStyleBearing)((Object)soapObject)).setSOAP12EncodingStyle(value);
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(EncodingStyleBearing.SOAP12_ENCODING_STYLE_ATTR_NAME, value);
        } else {
            throw new IllegalArgumentException("Specified object was neither EncodingStyleBearing nor AttribtueExtensible");
        }
    }

    @Nullable
    public static String getSOAP12EncodingStyleAttribute(@Nonnull XMLObject soapObject) {
        String style = null;
        if (soapObject instanceof EncodingStyleBearing) {
            style = ((EncodingStyleBearing)((Object)soapObject)).getSOAP12EncodingStyle();
        }
        if (style == null && soapObject instanceof AttributeExtensibleXMLObject) {
            style = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(EncodingStyleBearing.SOAP12_ENCODING_STYLE_ATTR_NAME));
        }
        return style;
    }

    public static void addSOAP12MustUnderstandAttribute(@Nonnull XMLObject soapObject, boolean mustUnderstand) {
        if (soapObject instanceof MustUnderstandBearing) {
            ((MustUnderstandBearing)((Object)soapObject)).setSOAP12MustUnderstand(new XSBooleanValue(mustUnderstand, false));
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(MustUnderstandBearing.SOAP12_MUST_UNDERSTAND_ATTR_NAME, new XSBooleanValue(mustUnderstand, false).toString());
        } else {
            throw new IllegalArgumentException("Specified object was neither MustUnderstandBearing nor AttributeExtensible");
        }
    }

    public static boolean getSOAP12MustUnderstandAttribute(@Nonnull XMLObject soapObject) {
        Object value;
        if (soapObject instanceof MustUnderstandBearing && (value = ((MustUnderstandBearing)((Object)soapObject)).isSOAP12MustUnderstandXSBoolean()) != null) {
            return ((XSBooleanValue)value).getValue();
        }
        if (soapObject instanceof AttributeExtensibleXMLObject) {
            value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(MustUnderstandBearing.SOAP12_MUST_UNDERSTAND_ATTR_NAME));
            return Objects.equals("1", value) || Objects.equals("true", value);
        }
        return false;
    }

    public static void addSOAP12RelayAttribute(@Nonnull XMLObject soapObject, boolean relay) {
        if (soapObject instanceof RelayBearing) {
            ((RelayBearing)((Object)soapObject)).setSOAP12Relay(new XSBooleanValue(relay, false));
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(RelayBearing.SOAP12_RELAY_ATTR_NAME, new XSBooleanValue(relay, false).toString());
        } else {
            throw new IllegalArgumentException("Specified object was neither RelayBearing nor AttributeExtensible");
        }
    }

    public static boolean getSOAP12RelayAttribute(@Nonnull XMLObject soapObject) {
        Object value;
        if (soapObject instanceof RelayBearing && (value = ((RelayBearing)((Object)soapObject)).isSOAP12RelayXSBoolean()) != null) {
            return ((XSBooleanValue)value).getValue();
        }
        if (soapObject instanceof AttributeExtensibleXMLObject) {
            value = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(RelayBearing.SOAP12_RELAY_ATTR_NAME));
            return Objects.equals("1", value) || Objects.equals("true", value);
        }
        return false;
    }

    public static void addSOAP12RoleAttribute(@Nonnull XMLObject soapObject, @Nonnull String role) {
        String value = Constraint.isNotNull(StringSupport.trimOrNull(role), "Role cannot be null or empty");
        if (soapObject instanceof RoleBearing) {
            ((RoleBearing)((Object)soapObject)).setSOAP12Role(value);
        } else if (soapObject instanceof AttributeExtensibleXMLObject) {
            ((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().put(RoleBearing.SOAP12_ROLE_ATTR_NAME, value);
        } else {
            throw new IllegalArgumentException("Specified object was neither RoleBearing nor AttribtueExtensible");
        }
    }

    @Nullable
    public static String getSOAP12RoleAttribute(@Nonnull XMLObject soapObject) {
        String role = null;
        if (soapObject instanceof RoleBearing) {
            role = ((RoleBearing)((Object)soapObject)).getSOAP12Role();
        }
        if (role == null && soapObject instanceof AttributeExtensibleXMLObject) {
            role = StringSupport.trimOrNull(((AttributeExtensibleXMLObject)soapObject).getUnknownAttributes().get(RoleBearing.SOAP12_ROLE_ATTR_NAME));
        }
        return role;
    }

    public static void addHeaderBlock(@Nonnull MessageContext messageContext, @Nonnull XMLObject headerBlock) {
        SOAPMessagingSupport.addHeaderBlock(messageContext, headerBlock);
    }

    public static void addSOAP11HeaderBlock(@Nonnull Envelope envelope, @Nonnull XMLObject headerBlock) {
        SOAPMessagingSupport.addSOAP11HeaderBlock(envelope, headerBlock);
    }

    @Nonnull
    public static List<XMLObject> getInboundHeaderBlock(@Nonnull MessageContext messageContext, @Nonnull QName headerName, @Nullable Set<String> targetNodes, boolean isFinalDestination) {
        return SOAPMessagingSupport.getHeaderBlock(messageContext, headerName, targetNodes, isFinalDestination);
    }

    @Nonnull
    public static List<XMLObject> getSOAP11HeaderBlock(@Nonnull Envelope envelope, @Nonnull QName headerName, @Nullable Set<String> targetNodes, boolean isFinalDestination) {
        return SOAPMessagingSupport.getSOAP11HeaderBlock(envelope, headerName, targetNodes, isFinalDestination);
    }

    public static boolean isSOAP11HeaderTargetedToNode(@Nonnull XMLObject header, @Nullable Set<String> nodeActors, boolean isFinalDestination) {
        return SOAPMessagingSupport.isSOAP11HeaderTargetedToNode(header, nodeActors, isFinalDestination);
    }

    public static boolean isSOAPMessage(@Nonnull MessageContext<? extends XMLObject> messageContext) {
        return SOAPMessagingSupport.isSOAPMessage(messageContext);
    }

    public static Fault buildSOAP11Fault(@Nonnull QName faultCode, @Nonnull String faultString, @Nullable String faultActor, @Nullable List<XMLObject> detailChildren, @Nullable Map<QName, String> detailAttributes) {
        Constraint.isNotNull(faultCode, "faultcode cannot be null");
        Constraint.isNotNull(faultString, "faultstring cannot be null");
        XMLObjectBuilderFactory builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
        Fault faultObj = (Fault)builderFactory.getBuilder(Fault.DEFAULT_ELEMENT_NAME).buildObject(Fault.DEFAULT_ELEMENT_NAME);
        FaultCode faultCodeObj = (FaultCode)builderFactory.getBuilder(FaultCode.DEFAULT_ELEMENT_NAME).buildObject(FaultCode.DEFAULT_ELEMENT_NAME);
        FaultString faultStringObj = (FaultString)builderFactory.getBuilder(FaultString.DEFAULT_ELEMENT_NAME).buildObject(FaultString.DEFAULT_ELEMENT_NAME);
        faultCodeObj.setValue(faultCode);
        faultObj.setCode(faultCodeObj);
        faultStringObj.setValue(faultString);
        faultObj.setMessage(faultStringObj);
        if (faultActor != null) {
            FaultActor faultActorObj = (FaultActor)builderFactory.getBuilder(FaultActor.DEFAULT_ELEMENT_NAME).buildObject(FaultActor.DEFAULT_ELEMENT_NAME);
            faultActorObj.setValue(faultActor);
            faultObj.setActor(faultActorObj);
        }
        ElementExtensibleXMLObject detailObj = null;
        if (detailChildren != null && !detailChildren.isEmpty()) {
            detailObj = (Detail)builderFactory.getBuilder(Detail.DEFAULT_ELEMENT_NAME).buildObject(Detail.DEFAULT_ELEMENT_NAME);
            for (XMLObject xMLObject : Iterables.filter(detailChildren, Predicates.notNull())) {
                detailObj.getUnknownXMLObjects().add(xMLObject);
            }
        }
        if (detailAttributes != null && !detailAttributes.isEmpty()) {
            if (detailObj == null) {
                detailObj = (Detail)builderFactory.getBuilder(Detail.DEFAULT_ELEMENT_NAME).buildObject(Detail.DEFAULT_ELEMENT_NAME);
            }
            for (Map.Entry entry : detailAttributes.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) continue;
                detailObj.getUnknownAttributes().put((QName)entry.getKey(), (String)entry.getValue());
            }
        }
        if (!(detailObj == null || detailObj.getUnknownXMLObjects().isEmpty() && detailObj.getUnknownAttributes().isEmpty())) {
            faultObj.setDetail((Detail)detailObj);
        }
        return faultObj;
    }
}

