/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common;

import java.util.List;
import javax.annotation.Nonnull;
import org.opensaml.core.xml.Namespace;
import org.opensaml.saml.common.SAMLObjectContentReference;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.xmlsec.signature.support.ContentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SAMLObjectSupport {
    private SAMLObjectSupport() {
    }

    public static void declareNonVisibleNamespaces(@Nonnull SignableSAMLObject signableObject) {
        Logger log = SAMLObjectSupport.getLogger();
        if (signableObject.getDOM() == null && signableObject.getSignature() != null) {
            log.debug("Examining signed object for content references with exclusive canonicalization transform");
            boolean sawExclusive = false;
            for (ContentReference cr : signableObject.getSignature().getContentReferences()) {
                List<String> transforms;
                if (!(cr instanceof SAMLObjectContentReference) || !(transforms = ((SAMLObjectContentReference)cr).getTransforms()).contains("http://www.w3.org/2001/10/xml-exc-c14n#WithComments") && !transforms.contains("http://www.w3.org/2001/10/xml-exc-c14n#")) continue;
                sawExclusive = true;
                break;
            }
            if (sawExclusive) {
                log.debug("Saw exclusive transform, declaring non-visible namespaces on signed object");
                for (Namespace ns : signableObject.getNamespaceManager().getNonVisibleNamespaces()) {
                    signableObject.getNamespaceManager().registerNamespaceDeclaration(ns);
                }
            }
        }
    }

    @Nonnull
    private static Logger getLogger() {
        return LoggerFactory.getLogger(SAMLObjectSupport.class);
    }
}

