/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.saml.utils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.cxf.common.security.SimpleGroup;
import org.apache.cxf.message.Message;
import org.apache.cxf.rt.security.claims.Claim;
import org.apache.cxf.rt.security.claims.ClaimCollection;
import org.apache.cxf.rt.security.claims.SAMLClaim;
import org.apache.cxf.rt.security.utils.SecurityUtils;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.Attribute;
import org.opensaml.saml.saml1.core.AttributeStatement;
import org.w3c.dom.Element;

public final class SAMLUtils {
    private SAMLUtils() {
    }

    public static ClaimCollection getClaims(SamlAssertionWrapper assertion) {
        ClaimCollection claims = new ClaimCollection();
        if (assertion.getSamlVersion().equals(SAMLVersion.VERSION_20)) {
            List<org.opensaml.saml.saml2.core.AttributeStatement> statements = assertion.getSaml2().getAttributeStatements();
            for (org.opensaml.saml.saml2.core.AttributeStatement as : statements) {
                for (org.opensaml.saml.saml2.core.Attribute atr : as.getAttributes()) {
                    SAMLClaim claim = new SAMLClaim();
                    claim.setClaimType(atr.getName());
                    claim.setName(atr.getName());
                    claim.setNameFormat(atr.getNameFormat());
                    claim.setFriendlyName(atr.getFriendlyName());
                    for (XMLObject o : atr.getAttributeValues()) {
                        String attrValue = o.getDOM().getTextContent();
                        claim.getValues().add(attrValue);
                    }
                    claims.add(claim);
                }
            }
        } else {
            List<AttributeStatement> attributeStatements = assertion.getSaml1().getAttributeStatements();
            for (AttributeStatement statement : attributeStatements) {
                for (Attribute atr : statement.getAttributes()) {
                    SAMLClaim claim = new SAMLClaim();
                    String claimType = atr.getAttributeName();
                    if (atr.getAttributeNamespace() != null) {
                        claimType = atr.getAttributeNamespace() + "/" + claimType;
                    }
                    claim.setClaimType(claimType);
                    claim.setName(atr.getAttributeName());
                    claim.setNameFormat(atr.getAttributeNamespace());
                    for (XMLObject o : atr.getAttributeValues()) {
                        String attrValue = o.getDOM().getTextContent();
                        claim.getValues().add(attrValue);
                    }
                    claims.add(claim);
                }
            }
        }
        return claims;
    }

    public static Set<Principal> parseRolesFromClaims(ClaimCollection claims, String name, String nameFormat) {
        String roleAttributeName = name;
        if (roleAttributeName == null) {
            roleAttributeName = "http://schemas.xmlsoap.org/ws/2005/05/identity/claims/role";
        }
        HashSet<Principal> roles = new HashSet<Principal>();
        for (Claim claim : claims) {
            if (!(claim instanceof SAMLClaim) || !((SAMLClaim)claim).getName().equals(name) || nameFormat != null && !nameFormat.equals(((SAMLClaim)claim).getNameFormat())) continue;
            for (Object claimValue : claim.getValues()) {
                if (!(claimValue instanceof String)) continue;
                roles.add(new SimpleGroup((String)claimValue));
            }
            if (claim.getValues().size() <= 1) continue;
            break;
        }
        return roles;
    }

    public static String getIssuer(Object assertion) {
        return ((SamlAssertionWrapper)assertion).getIssuerString();
    }

    public static Element getAssertionElement(Object assertion) {
        return ((SamlAssertionWrapper)assertion).getElement();
    }

    public static List<String> getAudienceRestrictions(Message msg, boolean enableByDefault) {
        boolean enableAudienceRestriction = SecurityUtils.getSecurityPropertyBoolean("security.validate.audience-restriction", msg, enableByDefault);
        if (enableAudienceRestriction) {
            ArrayList<String> audiences = new ArrayList<String>();
            String audienceRestrictions = (String)SecurityUtils.getSecurityPropertyValue("security.audience-restrictions", msg);
            if (audienceRestrictions != null) {
                for (String audienceRestriction : audienceRestrictions.split(",")) {
                    audiences.add(audienceRestriction);
                }
            }
            if (audiences.isEmpty()) {
                if (msg.get("org.apache.cxf.request.url") != null) {
                    audiences.add((String)msg.get("org.apache.cxf.request.url"));
                } else if (msg.get("org.apache.cxf.request.uri") != null) {
                    audiences.add((String)msg.get("org.apache.cxf.request.uri"));
                }
                if (msg.getContextualProperty("javax.xml.ws.wsdl.service") != null) {
                    audiences.add(msg.getContextualProperty("javax.xml.ws.wsdl.service").toString());
                }
            }
            return audiences;
        }
        return Collections.emptyList();
    }
}

