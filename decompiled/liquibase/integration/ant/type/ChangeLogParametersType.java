/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tools.ant.Project
 *  org.apache.tools.ant.taskdefs.Property
 *  org.apache.tools.ant.types.DataType
 *  org.apache.tools.ant.types.PropertySet
 *  org.apache.tools.ant.types.Reference
 */
package liquibase.integration.ant.type;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import liquibase.Liquibase;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Property;
import org.apache.tools.ant.types.DataType;
import org.apache.tools.ant.types.PropertySet;
import org.apache.tools.ant.types.Reference;

public class ChangeLogParametersType
extends DataType {
    private final List<PropertySet> propertySets;
    private final List<Property> parameters;

    public ChangeLogParametersType(Project project) {
        this.setProject(project);
        this.propertySets = new LinkedList<PropertySet>();
        this.parameters = new LinkedList<Property>();
    }

    public void applyParameters(Liquibase liquibase) {
        for (Property parameter : this.getChangeLogParameters()) {
            liquibase.setChangeLogParameter(parameter.getName(), parameter.getValue());
        }
        for (PropertySet propertySet : this.getPropertySets()) {
            Properties properties = propertySet.getProperties();
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                liquibase.setChangeLogParameter((String)entry.getKey(), entry.getValue());
            }
        }
    }

    public void setRefid(Reference ref) {
        if (!this.propertySets.isEmpty() || !this.parameters.isEmpty()) {
            throw this.tooManyAttributes();
        }
        super.setRefid(ref);
    }

    public List<PropertySet> getPropertySets() {
        return this.isReference() ? ((ChangeLogParametersType)((Object)this.getCheckedRef())).getPropertySets() : this.propertySets;
    }

    public void addConfigured(PropertySet propertySet) {
        this.propertySets.add(propertySet);
    }

    public List<Property> getChangeLogParameters() {
        return this.isReference() ? ((ChangeLogParametersType)((Object)this.getCheckedRef())).getChangeLogParameters() : this.parameters;
    }

    public void addConfiguredChangeLogParameter(Property parameter) {
        this.parameters.add(parameter);
    }
}

