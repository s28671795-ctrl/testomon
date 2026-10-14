/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.spi.ior;

import org.jboss.com.sun.corba.se.spi.ior.Identifiable;
import org.jboss.com.sun.corba.se.spi.ior.MakeImmutable;
import org.jboss.com.sun.corba.se.spi.ior.ObjectId;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKey;
import org.jboss.com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.jboss.com.sun.corba.se.spi.ior.TaggedProfileTemplate;

public interface TaggedProfile
extends Identifiable,
MakeImmutable {
    public TaggedProfileTemplate getTaggedProfileTemplate();

    public ObjectId getObjectId();

    public ObjectKeyTemplate getObjectKeyTemplate();

    public ObjectKey getObjectKey();

    public boolean isEquivalent(TaggedProfile var1);

    public org.omg.IOP.TaggedProfile getIOPProfile();

    public boolean isLocal();
}

