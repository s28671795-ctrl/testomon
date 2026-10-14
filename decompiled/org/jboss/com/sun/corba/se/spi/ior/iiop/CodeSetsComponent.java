/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior.iiop;

import org.jboss.com.sun.corba.se.impl.encoding.CodeSetComponentInfo;
import org.jboss.com.sun.corba.se.spi.ior.TaggedComponent;

public interface CodeSetsComponent
extends TaggedComponent {
    public CodeSetComponentInfo getCodeSetComponentInfo();
}

