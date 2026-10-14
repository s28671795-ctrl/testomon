/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.rt.security.saml.xacml2;

import java.util.UUID;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.xacml.XACMLObjectBuilder;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.profile.saml.XACMLAuthzDecisionQueryType;

public final class SamlRequestComponentBuilder {
    private static volatile XACMLObjectBuilder<XACMLAuthzDecisionQueryType> xacmlAuthzDecisionQueryTypeBuilder;
    private static volatile SAMLObjectBuilder<Issuer> issuerBuilder;
    private static volatile XMLObjectBuilderFactory builderFactory;

    private SamlRequestComponentBuilder() {
    }

    public static XACMLAuthzDecisionQueryType createAuthzDecisionQuery(String issuerValue, RequestType request, String namespace) {
        return SamlRequestComponentBuilder.createAuthzDecisionQuery(false, false, issuerValue, request, namespace);
    }

    public static XACMLAuthzDecisionQueryType createAuthzDecisionQuery(boolean inputContextOnly, boolean returnContext, String issuerValue, RequestType request, String namespace) {
        if (xacmlAuthzDecisionQueryTypeBuilder == null) {
            xacmlAuthzDecisionQueryTypeBuilder = (XACMLObjectBuilder)builderFactory.getBuilder(XACMLAuthzDecisionQueryType.DEFAULT_ELEMENT_NAME_XACML20);
        }
        XACMLAuthzDecisionQueryType authzQuery = (XACMLAuthzDecisionQueryType)xacmlAuthzDecisionQueryTypeBuilder.buildObject(namespace, "XACMLAuthzDecisionQuery", "xacml-samlp");
        authzQuery.setID("_" + UUID.randomUUID().toString());
        authzQuery.setVersion(SAMLVersion.VERSION_20);
        authzQuery.setIssueInstant(new DateTime());
        authzQuery.setInputContextOnly(inputContextOnly);
        authzQuery.setReturnContext(returnContext);
        if (issuerValue != null) {
            Issuer issuer = SamlRequestComponentBuilder.createIssuer(issuerValue);
            authzQuery.setIssuer(issuer);
        }
        authzQuery.setRequest(request);
        return authzQuery;
    }

    public static Issuer createIssuer(String issuerValue) {
        if (issuerBuilder == null) {
            issuerBuilder = (SAMLObjectBuilder)builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
        }
        Issuer issuer = issuerBuilder.buildObject();
        issuer.setValue(issuerValue);
        return issuer;
    }

    static {
        builderFactory = XMLObjectProviderRegistrySupport.getBuilderFactory();
    }
}

