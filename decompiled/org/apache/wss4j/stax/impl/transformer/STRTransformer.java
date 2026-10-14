/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.transformer;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.Transformer;
import org.apache.xml.security.stax.impl.transformer.TransformIdentity;
import org.apache.xml.security.stax.impl.transformer.canonicalizer.Canonicalizer20010315_Excl;

public class STRTransformer
extends TransformIdentity {
    @Override
    public void setTransformer(Transformer transformer) throws XMLSecurityException {
        if (!(transformer instanceof Canonicalizer20010315_Excl)) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
        ArrayList<String> inclusiveNamespacesPrefixList = new ArrayList<String>();
        inclusiveNamespacesPrefixList.add("#default");
        HashMap<String, Object> transformerProperties = new HashMap<String, Object>();
        transformerProperties.put("inclusiveNamespacePrefixList", inclusiveNamespacesPrefixList);
        transformerProperties.put("propagateDefaultNamespace", Boolean.TRUE);
        transformer.setProperties(transformerProperties);
        super.setTransformer(transformer);
    }
}

