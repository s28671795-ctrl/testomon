/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Any;
import org.omg.CORBA.AnyHolder;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableGroup.CannotMeetCriteria;
import org.omg.PortableGroup.GenericFactory;
import org.omg.PortableGroup.GenericFactoryHelper;
import org.omg.PortableGroup.GenericFactoryOperations;
import org.omg.PortableGroup.GenericFactoryPOA;
import org.omg.PortableGroup.InvalidCriteria;
import org.omg.PortableGroup.InvalidProperty;
import org.omg.PortableGroup.NoFactory;
import org.omg.PortableGroup.ObjectNotCreated;
import org.omg.PortableGroup.ObjectNotFound;
import org.omg.PortableGroup.Property;
import org.omg.PortableServer.POA;

public class GenericFactoryPOATie
extends GenericFactoryPOA {
    private GenericFactoryOperations _delegate;
    private POA _poa;

    public GenericFactoryPOATie(GenericFactoryOperations delegate) {
        this._delegate = delegate;
    }

    public GenericFactoryPOATie(GenericFactoryOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public GenericFactory _this() {
        Object __o = this._this_object();
        GenericFactory __r = GenericFactoryHelper.narrow(__o);
        return __r;
    }

    @Override
    public GenericFactory _this(ORB orb) {
        Object __o = this._this_object(orb);
        GenericFactory __r = GenericFactoryHelper.narrow(__o);
        return __r;
    }

    public GenericFactoryOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(GenericFactoryOperations delegate) {
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
    public Object create_object(String type_id, Property[] the_criteria, AnyHolder factory_creation_id) throws InvalidProperty, ObjectNotCreated, InvalidCriteria, CannotMeetCriteria, NoFactory {
        return this._delegate.create_object(type_id, the_criteria, factory_creation_id);
    }

    @Override
    public void delete_object(Any factory_creation_id) throws ObjectNotFound {
        this._delegate.delete_object(factory_creation_id);
    }
}

