/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.filter;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.filter.FilterException;

public interface MetadataNodeProcessor {
    public void process(XMLObject var1) throws FilterException;
}

