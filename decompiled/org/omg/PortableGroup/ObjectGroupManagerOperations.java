/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.PortableGroup.CannotMeetCriteria;
import org.omg.PortableGroup.InvalidCriteria;
import org.omg.PortableGroup.MemberAlreadyPresent;
import org.omg.PortableGroup.MemberNotFound;
import org.omg.PortableGroup.NoFactory;
import org.omg.PortableGroup.ObjectGroupNotFound;
import org.omg.PortableGroup.ObjectNotAdded;
import org.omg.PortableGroup.ObjectNotCreated;
import org.omg.PortableGroup.Property;

public interface ObjectGroupManagerOperations {
    public Object create_member(Object var1, NameComponent[] var2, String var3, Property[] var4) throws ObjectNotCreated, MemberAlreadyPresent, ObjectGroupNotFound, InvalidCriteria, CannotMeetCriteria, NoFactory;

    public Object add_member(Object var1, NameComponent[] var2, Object var3) throws INV_OBJREF, MemberAlreadyPresent, ObjectGroupNotFound, ObjectNotAdded;

    public Object remove_member(Object var1, NameComponent[] var2) throws ObjectGroupNotFound, MemberNotFound;

    public NameComponent[][] locations_of_members(Object var1) throws ObjectGroupNotFound;

    public long get_object_group_id(Object var1) throws ObjectGroupNotFound;

    public Object get_object_group_ref(Object var1) throws ObjectGroupNotFound;

    public Object get_member_ref(Object var1, NameComponent[] var2) throws ObjectGroupNotFound, MemberNotFound;
}

