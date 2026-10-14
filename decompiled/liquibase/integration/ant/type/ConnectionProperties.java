/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.taskdefs.Property
 *  org.apache.tools.ant.types.PropertySet
 */
package liquibase.integration.ant.type;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.tools.ant.taskdefs.Property;
import org.apache.tools.ant.types.PropertySet;

public class ConnectionProperties {
    private final List<PropertySet> propertySets = new LinkedList<PropertySet>();
    private final List<Property> properties = new LinkedList<Property>();

    public Properties buildProperties() {
        Properties retProps = new Properties();
        for (PropertySet propertySet : this.propertySets) {
            retProps.putAll((Map<?, ?>)propertySet.getProperties());
        }
        for (Property property : this.properties) {
            retProps.setProperty(property.getName(), property.getValue());
        }
        return retProps;
    }

    public void add(PropertySet propertySet) {
        this.propertySets.add(propertySet);
    }

    public void addConnectionProperty(Property property) {
        this.properties.add(property);
    }
}

