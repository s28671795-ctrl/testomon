/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.PortableGroup.InvalidProperty;
import org.omg.PortableGroup.ObjectGroupNotFound;
import org.omg.PortableGroup.Property;
import org.omg.PortableGroup.PropertyManager;
import org.omg.PortableGroup.PropertyManagerHelper;
import org.omg.PortableGroup.PropertyManagerOperations;
import org.omg.PortableGroup.PropertyManagerPOA;
import org.omg.PortableGroup.UnsupportedProperty;
import org.omg.PortableServer.POA;

public class PropertyManagerPOATie
extends PropertyManagerPOA {
    private PropertyManagerOperations _delegate;
    private POA _poa;

    public PropertyManagerPOATie(PropertyManagerOperations delegate) {
        this._delegate = delegate;
    }

    public PropertyManagerPOATie(PropertyManagerOperations delegate, POA poa) {
        this._delegate = delegate;
        this._poa = poa;
    }

    @Override
    public PropertyManager _this() {
        Object __o = this._this_object();
        PropertyManager __r = PropertyManagerHelper.narrow(__o);
        return __r;
    }

    @Override
    public PropertyManager _this(ORB orb) {
        Object __o = this._this_object(orb);
        PropertyManager __r = PropertyManagerHelper.narrow(__o);
        return __r;
    }

    public PropertyManagerOperations _delegate() {
        return this._delegate;
    }

    public void _delegate(PropertyManagerOperations delegate) {
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
    public void remove_default_properties(Property[] props) throws InvalidProperty, UnsupportedProperty {
        this._delegate.remove_default_properties(props);
    }

    @Override
    public void set_properties_dynamically(Object object_group, Property[] overrides) throws InvalidProperty, ObjectGroupNotFound, UnsupportedProperty {
        this._delegate.set_properties_dynamically(object_group, overrides);
    }

    @Override
    public Property[] get_default_properties() {
        return this._delegate.get_default_properties();
    }

    @Override
    public Property[] get_type_properties(String type_id) {
        return this._delegate.get_type_properties(type_id);
    }

    @Override
    public void set_type_properties(String type_id, Property[] overrides) throws InvalidProperty, UnsupportedProperty {
        this._delegate.set_type_properties(type_id, overrides);
    }

    @Override
    public Property[] get_properties(Object object_group) throws ObjectGroupNotFound {
        return this._delegate.get_properties(object_group);
    }

    @Override
    public void set_default_properties(Property[] props) throws InvalidProperty, UnsupportedProperty {
        this._delegate.set_default_properties(props);
    }

    @Override
    public void remove_type_properties(String type_id, Property[] props) throws InvalidProperty, UnsupportedProperty {
        this._delegate.remove_type_properties(type_id, props);
    }
}

