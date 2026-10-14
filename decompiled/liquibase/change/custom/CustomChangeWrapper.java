/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change.custom;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import liquibase.Scope;
import liquibase.change.AbstractChange;
import liquibase.change.CheckSum;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.custom.CustomChange;
import liquibase.change.custom.CustomChangeChecksum;
import liquibase.change.custom.CustomSqlChange;
import liquibase.change.custom.CustomSqlRollback;
import liquibase.change.custom.CustomTaskChange;
import liquibase.change.custom.CustomTaskRollback;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.RollbackImpossibleException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.statement.SqlStatement;
import liquibase.util.ObjectUtil;
import liquibase.util.OsgiUtil;

@DatabaseChange(name="customChange", description="Although Liquibase tries to provide a wide range of database refactorings, there are times you may want to create your own custom refactoring class.\n\nTo create your own custom refactoring, simply create a class that implements the liquibase.change.custom.CustomSqlChange or liquibase.change.custom.CustomTaskChange interface and use the <custom> tag in your changeset.\n\nIf your change can be rolled back, implement the liquibase.change.custom.CustomSqlRollback interface as well.\n\nFor a sample custom change class, see liquibase.change.custom.ExampleCustomSqlChange", priority=1)
public class CustomChangeWrapper
extends AbstractChange {
    CustomChange customChange;
    private String className;
    private final SortedSet<String> params = new TreeSet<String>();
    private final Map<String, String> paramValues = new LinkedHashMap<String, String>();
    private boolean configured;

    @Override
    public boolean generateStatementsVolatile(Database database) {
        return true;
    }

    @DatabaseChangeProperty(isChangeProperty=false)
    public CustomChange getCustomChange() {
        return this.customChange;
    }

    public CustomChangeWrapper setClass(String className) throws CustomChangeException {
        block8: {
            if (className == null) {
                return this;
            }
            this.className = className;
            try {
                Boolean osgiPlatform = (Boolean)((Object)Scope.getCurrentScope().get((Enum)Scope.Attr.osgiPlatform, Boolean.class));
                if (Boolean.TRUE.equals(osgiPlatform)) {
                    this.customChange = (CustomChange)OsgiUtil.loadClass(className).getConstructor(new Class[0]).newInstance(new Object[0]);
                    break block8;
                }
                try {
                    this.customChange = (CustomChange)Class.forName(className, true, Scope.getCurrentScope().getClassLoader()).getConstructor(new Class[0]).newInstance(new Object[0]);
                }
                catch (ClassCastException e) {
                    try {
                        this.customChange = (CustomChange)Thread.currentThread().getContextClassLoader().loadClass(className).getConstructor(new Class[0]).newInstance(new Object[0]);
                    }
                    catch (ClassNotFoundException e1) {
                        this.customChange = (CustomChange)Class.forName(className).getConstructor(new Class[0]).newInstance(new Object[0]);
                    }
                }
            }
            catch (Exception e) {
                throw new CustomChangeException(e);
            }
        }
        return this;
    }

    @DatabaseChangeProperty(description="Name class that implements the custom change.")
    public String getClassName() {
        return this.className;
    }

    public void setParam(String name, String value) {
        this.params.add(name);
        this.paramValues.put(name, value);
    }

    @DatabaseChangeProperty(isChangeProperty=false)
    public SortedSet<String> getParams() {
        return Collections.unmodifiableSortedSet(this.params);
    }

    public String getParamValue(String key) {
        return this.paramValues.get(key);
    }

    @Override
    public ValidationErrors validate(Database database) {
        if (!this.configured) {
            try {
                this.configureCustomChange();
            }
            catch (CustomChangeException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        try {
            return this.customChange.validate(database);
        }
        catch (Exception e) {
            return new ValidationErrors().addError("Exception thrown calling " + this.getClassName() + ".validate():" + e.getMessage());
        }
    }

    @Override
    public Warnings warn(Database database) {
        return new Warnings();
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        SqlStatement[] statements;
        block5: {
            statements = null;
            boolean shouldExecute = Scope.getCurrentScope().get("shouldExecute", Boolean.TRUE);
            try {
                this.configureCustomChange();
                if (!shouldExecute) break block5;
                if (this.customChange instanceof CustomSqlChange) {
                    statements = ((CustomSqlChange)this.customChange).generateStatements(database);
                    break block5;
                }
                if (this.customChange instanceof CustomTaskChange) {
                    ((CustomTaskChange)this.customChange).execute(database);
                    break block5;
                }
                throw new UnexpectedLiquibaseException(this.customChange.getClass().getName() + " does not implement " + CustomSqlChange.class.getName() + " or " + CustomTaskChange.class.getName());
            }
            catch (CustomChangeException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        if (statements == null) {
            statements = SqlStatement.EMPTY_SQL_STATEMENT;
        }
        return statements;
    }

    @Override
    public SqlStatement[] generateRollbackStatements(Database database) throws RollbackImpossibleException {
        SqlStatement[] statements;
        block5: {
            statements = null;
            try {
                this.configureCustomChange();
                if (this.customChange instanceof CustomSqlRollback) {
                    statements = ((CustomSqlRollback)((Object)this.customChange)).generateRollbackStatements(database);
                    break block5;
                }
                if (this.customChange instanceof CustomTaskRollback) {
                    ((CustomTaskRollback)((Object)this.customChange)).rollback(database);
                    break block5;
                }
                throw new RollbackImpossibleException("Unknown rollback type: " + this.customChange.getClass().getName());
            }
            catch (CustomChangeException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        if (statements == null) {
            statements = SqlStatement.EMPTY_SQL_STATEMENT;
        }
        return statements;
    }

    @Override
    public CheckSum generateCheckSum() {
        try {
            this.configureCustomChange();
            if (this.customChange instanceof CustomChangeChecksum) {
                return ((CustomChangeChecksum)((Object)this.customChange)).generateChecksum();
            }
            return super.generateCheckSum();
        }
        catch (CustomChangeException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public boolean supportsRollback(Database database) {
        return this.customChange instanceof CustomSqlRollback || this.customChange instanceof CustomTaskRollback;
    }

    @Override
    public String getConfirmationMessage() {
        try {
            this.configureCustomChange();
        }
        catch (CustomChangeException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        return this.customChange.getConfirmationMessage();
    }

    private void configureCustomChange() throws CustomChangeException {
        if (this.configured) {
            return;
        }
        try {
            for (String param2 : this.params) {
                ObjectUtil.setProperty((Object)this.customChange, param2, this.paramValues.get(param2));
            }
            this.customChange.setFileOpener(Scope.getCurrentScope().getResourceAccessor());
            this.customChange.setUp();
            this.configured = true;
        }
        catch (Exception e) {
            throw new CustomChangeException(e);
        }
    }

    @Override
    public LiquibaseSerializable.SerializationType getSerializableFieldType(String field2) {
        switch (field2) {
            case "class": 
            case "param": {
                return LiquibaseSerializable.SerializationType.NAMED_FIELD;
            }
        }
        throw new UnexpectedLiquibaseException("Unexpected CustomChangeWrapper field " + field2);
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        switch (field2) {
            case "class": {
                return this.getClassName();
            }
            case "param": {
                return this.paramValues;
            }
        }
        throw new UnexpectedLiquibaseException("Unexpected CustomChangeWrapper field " + field2);
    }

    @Override
    public Set<String> getSerializableFields() {
        return new HashSet<String>(Arrays.asList("class", "param"));
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        try {
            String classNameValue = parsedNode.getChildValue((String)null, "class", String.class);
            if (classNameValue == null) {
                throw new ParsedNodeException("Custom change node has no 'class' attribute!");
            }
            this.setClass(classNameValue);
        }
        catch (CustomChangeException e) {
            throw new ParsedNodeException(e);
        }
        super.load(parsedNode, resourceAccessor);
    }

    @Override
    public void customLoadLogic(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        CustomChange localCustomChange;
        ParsedNode paramsNode = parsedNode.getChild(null, "params");
        if (paramsNode == null) {
            paramsNode = parsedNode;
        }
        for (ParsedNode child : paramsNode.getChildren(null, "param")) {
            String paramName;
            Object value = child.getValue();
            if (value == null) {
                value = child.getChildValue(null, "value");
            }
            if (value != null) {
                value = value.toString();
            }
            if ((paramName = child.getChildValue((String)null, "name", String.class)) == null) {
                throw new ParsedNodeException("Custom change param " + child + " does not have a 'name' attribute");
            }
            this.setParam(paramName, (String)value);
        }
        try {
            Boolean osgiPlatform = (Boolean)((Object)Scope.getCurrentScope().get((Enum)Scope.Attr.osgiPlatform, Boolean.class));
            localCustomChange = Boolean.TRUE.equals(osgiPlatform) ? (CustomChange)OsgiUtil.loadClass(this.className).getConstructor(new Class[0]).newInstance(new Object[0]) : (CustomChange)Class.forName(this.className, false, Scope.getCurrentScope().getClassLoader()).getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
        for (ParsedNode node : parsedNode.getChildren()) {
            Object value = node.getValue();
            if (value == null || !ObjectUtil.hasProperty(localCustomChange, node.getName())) continue;
            this.setParam(node.getName(), value.toString());
        }
    }
}

