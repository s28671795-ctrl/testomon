/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.Transforms;
import org.opensaml.xmlsec.signature.support.ConfigurableContentReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URIContentReference
implements ConfigurableContentReference {
    private final Logger log = LoggerFactory.getLogger(URIContentReference.class);
    private final String referenceID;
    private String digestAlgorithm;
    private final List<String> transforms;

    public URIContentReference(@Nullable String refID) {
        this.referenceID = refID;
        this.transforms = new LinkedList<String>();
        this.digestAlgorithm = "http://www.w3.org/2001/04/xmlenc#sha256";
    }

    @Nonnull
    public List<String> getTransforms() {
        return this.transforms;
    }

    @Override
    @Nullable
    public String getDigestAlgorithm() {
        return this.digestAlgorithm;
    }

    @Override
    public void setDigestAlgorithm(@Nonnull String newAlgorithm) {
        this.digestAlgorithm = Constraint.isNotNull(StringSupport.trimOrNull(newAlgorithm), "Digest algorithm cannot be empty or null");
    }

    @Override
    public void createReference(@Nonnull XMLSignature signature) {
        try {
            Transforms dsigTransforms = new Transforms(signature.getDocument());
            for (String transform : this.getTransforms()) {
                dsigTransforms.addTransform(transform);
            }
            signature.addDocument(this.referenceID, dsigTransforms, this.digestAlgorithm);
        }
        catch (TransformationException e) {
            this.log.error("Error while creating transforms", e);
        }
        catch (XMLSignatureException e) {
            this.log.error("Error while adding content reference", e);
        }
    }
}

