/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Object;
import org.omg.PortableGroup.NotAGroupObject;
import org.omg.PortableServer.POAOperations;

public interface GOAOperations
extends POAOperations {
    public byte[] create_id_for_reference(Object var1) throws NotAGroupObject;

    public byte[][] reference_to_ids(Object var1) throws NotAGroupObject;

    public void associate_reference_with_id(Object var1, byte[] var2) throws NotAGroupObject;

    public void disassociate_reference_with_id(Object var1, byte[] var2) throws NotAGroupObject;
}

