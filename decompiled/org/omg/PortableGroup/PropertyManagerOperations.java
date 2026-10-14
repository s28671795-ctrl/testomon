/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableGroup;

import org.omg.CORBA.Object;
import org.omg.PortableGroup.InvalidProperty;
import org.omg.PortableGroup.ObjectGroupNotFound;
import org.omg.PortableGroup.Property;
import org.omg.PortableGroup.UnsupportedProperty;

public interface PropertyManagerOperations {
    public void set_default_properties(Property[] var1) throws InvalidProperty, UnsupportedProperty;

    public Property[] get_default_properties();

    public void remove_default_properties(Property[] var1) throws InvalidProperty, UnsupportedProperty;

    public void set_type_properties(String var1, Property[] var2) throws InvalidProperty, UnsupportedProperty;

    public Property[] get_type_properties(String var1);

    public void remove_type_properties(String var1, Property[] var2) throws InvalidProperty, UnsupportedProperty;

    public void set_properties_dynamically(Object var1, Property[] var2) throws InvalidProperty, ObjectGroupNotFound, UnsupportedProperty;

    public Property[] get_properties(Object var1) throws ObjectGroupNotFound;
}

