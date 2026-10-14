/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Any;
import org.omg.CORBA.AnyHolder;
import org.omg.CORBA.Object;
import org.omg.PortableGroup.CannotMeetCriteria;
import org.omg.PortableGroup.InvalidCriteria;
import org.omg.PortableGroup.InvalidProperty;
import org.omg.PortableGroup.NoFactory;
import org.omg.PortableGroup.ObjectNotCreated;
import org.omg.PortableGroup.ObjectNotFound;
import org.omg.PortableGroup.Property;

public interface GenericFactoryOperations {
    public Object create_object(String var1, Property[] var2, AnyHolder var3) throws InvalidProperty, ObjectNotCreated, InvalidCriteria, CannotMeetCriteria, NoFactory;

    public void delete_object(Any var1) throws ObjectNotFound;
}

