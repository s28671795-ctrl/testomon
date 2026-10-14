/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.spi.ior.IdentifiableFactoryFinder;
import org.jboss.com.sun.corba.se.spi.ior.TaggedComponent;
import org.omg.CORBA.ORB;

public interface TaggedComponentFactoryFinder
extends IdentifiableFactoryFinder {
    public TaggedComponent create(ORB var1, org.omg.IOP.TaggedComponent var2);
}

