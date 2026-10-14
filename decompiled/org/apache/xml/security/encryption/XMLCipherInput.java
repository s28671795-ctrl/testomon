/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.encryption;

import java.io.IOException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.encryption.CipherData;
import org.apache.xml.security.encryption.CipherReference;
import org.apache.xml.security.encryption.EncryptedType;
import org.apache.xml.security.encryption.Transforms;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.resolver.ResourceResolver;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;

public class XMLCipherInput {
    private static final Logger LOG = LoggerFactory.getLogger(XMLCipherInput.class);
    private CipherData cipherData;
    private int mode;
    private boolean secureValidation;

    public XMLCipherInput(CipherData data) throws XMLEncryptionException {
        this.cipherData = data;
        this.mode = 2;
        if (this.cipherData == null) {
            throw new XMLEncryptionException("CipherData is null");
        }
    }

    public XMLCipherInput(EncryptedType input) throws XMLEncryptionException {
        this.cipherData = input == null ? null : input.getCipherData();
        this.mode = 2;
        if (this.cipherData == null) {
            throw new XMLEncryptionException("CipherData is null");
        }
    }

    public void setSecureValidation(boolean secureValidation) {
        this.secureValidation = secureValidation;
    }

    public byte[] getBytes() throws XMLEncryptionException {
        if (this.mode == 2) {
            return this.getDecryptBytes();
        }
        return null;
    }

    private byte[] getDecryptBytes() throws XMLEncryptionException {
        String base64EncodedEncryptedOctets = null;
        if (this.cipherData.getDataType() == 2) {
            LOG.debug("Found a reference type CipherData");
            CipherReference cr = this.cipherData.getCipherReference();
            Attr uriAttr = cr.getURIAsAttr();
            XMLSignatureInput input = null;
            try {
                ResourceResolver resolver2 = ResourceResolver.getInstance(uriAttr, null, this.secureValidation);
                input = resolver2.resolve(uriAttr, null, this.secureValidation);
            }
            catch (ResourceResolverException ex) {
                throw new XMLEncryptionException(ex);
            }
            if (input != null) {
                LOG.debug("Managed to resolve URI \"{}\"", (Object)cr.getURI());
            } else {
                LOG.debug("Failed to resolve URI \"{}\"", (Object)cr.getURI());
            }
            Transforms transforms = cr.getTransforms();
            if (transforms != null) {
                LOG.debug("Have transforms in cipher reference");
                try {
                    org.apache.xml.security.transforms.Transforms dsTransforms = transforms.getDSTransforms();
                    dsTransforms.setSecureValidation(this.secureValidation);
                    input = dsTransforms.performTransforms(input);
                }
                catch (TransformationException ex) {
                    throw new XMLEncryptionException(ex);
                }
            }
            try {
                return input.getBytes();
            }
            catch (IOException ex) {
                throw new XMLEncryptionException(ex);
            }
            catch (CanonicalizationException ex) {
                throw new XMLEncryptionException(ex);
            }
        }
        if (this.cipherData.getDataType() != 1) {
            throw new XMLEncryptionException("CipherData.getDataType() returned unexpected value");
        }
        base64EncodedEncryptedOctets = this.cipherData.getCipherValue().getValue();
        LOG.debug("Encrypted octets:\n{}", (Object)base64EncodedEncryptedOctets);
        return XMLUtils.decode(base64EncodedEncryptedOctets);
    }
}

