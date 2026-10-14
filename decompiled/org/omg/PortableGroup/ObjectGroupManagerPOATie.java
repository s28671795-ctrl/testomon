/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.PortableGroup.CannotMeetCriteria;
import org.omg.PortableGroup.InvalidCriteria;
import org.omg.PortableGroup.MemberAlreadyPresent;
import org.omg.PortableGroup.MemberNotFound;
import org.omg.PortableGroup.NoFactory;
import org.omg.PortableGroup.ObjectGroupManager;
import org.omg.PortableGroup.ObjectGroupManagerHelper;
import org.omg.PortableGroup.ObjectGroupManagerOperations;
import org.omg.PortableGroup.ObjectGroupManagerPOA;
import org.omg.PortableGroup.ObjectGroupNotFound;
import org.omg.PortableGroup.ObjectNotAdded;
import org.omg.PortableGroup.ObjectNotCreated;
import org.omg.PortableGroup.Property;
import org.omg.PortableServer.POA;

public class ObjectGroupManagerPOATie
extends ObjectGroupManagerPOA {
    private ObjectGroupManagerOperations _delegate;
    private POA _poa;

    public ObjectGroupManagerPOATie(ObjectGroupManagerOperations delegate) {
        this._delegate = delegate;
    }

    public ObjectGroupManagerPOATie(ObjectGroupManagerOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public ObjectGroupManager _this() {
        Object __o = this._this_object();
        ObjectGroupManager __r = ObjectGroupManagerHelper.narrow(__o);
        return __r;
    }

    @Override
    public ObjectGroupManager _this(ORB orb) {
        Object __o = this._this_object(orb);
        ObjectGroupManager __r = ObjectGroupManagerHelper.narrow(__o);
        return __r;
    }

    public ObjectGroupManagerOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(ObjectGroupManagerOperations delegate) {
        this._delegate = delegate;
    }

    @Override
    public POA _default_POA() {
        if (this._poa != null) {
            return this._poa;
        }
        return super._default_POA();
    }

    @Override
    public Object create_member(Object object_group, NameComponent[] the_location, String type_id, Property[] the_criteria) throws ObjectNotCreated, MemberAlreadyPresent, ObjectGroupNotFound, InvalidCriteria, CannotMeetCriteria, NoFactory {
        return this._delegate.create_member(object_group, the_location, type_id, the_criteria);
    }

    @Override
    public NameComponent[][] locations_of_members(Object object_group) throws ObjectGroupNotFound {
        return this._delegate.locations_of_members(object_group);
    }

    @Override
    public Object get_member_ref(Object object_group, NameComponent[] loc) throws ObjectGroupNotFound, MemberNotFound {
        return this._delegate.get_member_ref(object_group, loc);
    }

    @Override
    public long get_object_group_id(Object object_group) throws ObjectGroupNotFound {
        return this._delegate.get_object_group_id(object_group);
    }

    @Override
    public Object get_object_group_ref(Object object_group) throws ObjectGroupNotFound {
        return this._delegate.get_object_group_ref(object_group);
    }

    @Override
    public Object remove_member(Object object_group, NameComponent[] the_location) throws ObjectGroupNotFound, MemberNotFound {
        return this._delegate.remove_member(object_group, the_location);
    }

    @Override
    public Object add_member(Object object_group, NameComponent[] the_location, Object member) throws INV_OBJREF, MemberAlreadyPresent, ObjectGroupNotFound, ObjectNotAdded {
        return this._delegate.add_member(object_group, the_location, member);
    }
}

