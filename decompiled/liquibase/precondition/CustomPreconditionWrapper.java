/*
 * Decompiled with CFR 0.152.
 */
package liquibase.precondition;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.CustomPreconditionErrorException;
import liquibase.exception.CustomPreconditionFailedException;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.precondition.AbstractPrecondition;
import liquibase.precondition.CustomPrecondition;
import liquibase.precondition.ErrorPrecondition;
import liquibase.precondition.FailedPrecondition;
import liquibase.resource.ResourceAccessor;
import liquibase.util.ObjectUtil;

public class CustomPreconditionWrapper
extends AbstractPrecondition {
    private String className;
    private final SortedSet<String> params = new TreeSet<String>();
    private final Map<String, String> paramValues = new LinkedHashMap<String, String>();

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClass(String className) {
        this.className = className;
    }

    public String getParamValue(String key) {
        return this.paramValues.get(key);
    }

    public void setParam(String name, String value) {
        this.params.add(name);
        this.paramValues.put(name, value);
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public ValidationErrors validate(Database database) {
        return new ValidationErrors();
    }

    @Override
    public void check(Database database, DatabaseChangeLog changeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        CustomPrecondition customPrecondition;
        try {
            try {
                customPrecondition = (CustomPrecondition)Class.forName(this.className, true, Scope.getCurrentScope().getClassLoader()).getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (ClassCastException e) {
                customPrecondition = (CustomPrecondition)Class.forName(this.className).getConstructor(new Class[0]).newInstance(new Object[0]);
            }
        }
        catch (Exception e) {
            throw new PreconditionFailedException("Could not open custom precondition class " + this.className, changeLog, this);
        }
        for (String param2 : this.params) {
            try {
                ObjectUtil.setProperty((Object)customPrecondition, param2, this.paramValues.get(param2));
            }
            catch (Exception e) {
                throw new PreconditionFailedException("Error setting parameter " + param2 + " on custom precondition " + this.className, changeLog, this);
            }
        }
        try {
            customPrecondition.check(database);
        }
        catch (CustomPreconditionFailedException e) {
            throw new PreconditionFailedException(new FailedPrecondition("Custom Precondition Failed: " + e.getMessage(), changeLog, this));
        }
        catch (CustomPreconditionErrorException e) {
            throw new PreconditionErrorException(new ErrorPrecondition(e, changeLog, this));
        }
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public Set<String> getSerializableFields() {
        return new LinkedHashSet<String>(Arrays.asList("className", "param"));
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        return field2.equals("param") ? this.paramValues : super.getSerializableFieldValue(field2);
    }

    @Override
    public String getName() {
        return "customPrecondition";
    }

    @Override
    protected boolean shouldAutoLoad(ParsedNode node) {
        if ("params".equals(node.getName()) || "param".equals(node.getName())) {
            return false;
        }
        return super.shouldAutoLoad(node);
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        this.setClassName(parsedNode.getChildValue((String)null, "className", String.class));
        ParsedNode paramsNode = parsedNode.getChild(null, "params");
        if (paramsNode == null) {
            paramsNode = parsedNode;
        }
        for (ParsedNode child : paramsNode.getChildren(null, "param")) {
            Object value = child.getValue();
            if (value == null) {
                value = child.getChildValue(null, "value");
            }
            if (value != null) {
                value = value.toString();
            }
            this.setParam(child.getChildValue((String)null, "name", String.class), (String)value);
        }
        super.load(parsedNode, resourceAccessor);
    }
}

