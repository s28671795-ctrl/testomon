/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import org.h2.command.ddl.AlterDomainAddConstraint;
import org.h2.command.ddl.SchemaOwnerCommand;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.schema.Domain;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.util.Utils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public class CreateDomain
extends SchemaOwnerCommand {
    private String typeName;
    private boolean ifNotExists;
    private TypeInfo dataType;
    private Domain parentDomain;
    private Expression defaultExpression;
    private Expression onUpdateExpression;
    private String comment;
    private ArrayList<AlterDomainAddConstraint> constraintCommands;

    public CreateDomain(SessionLocal sessionLocal, Schema schema) {
        super(sessionLocal, schema);
    }

    public void setTypeName(String string) {
        this.typeName = string;
    }

    public void setIfNotExists(boolean bl) {
        this.ifNotExists = bl;
    }

    public void setDataType(TypeInfo typeInfo) {
        this.dataType = typeInfo;
    }

    public void setParentDomain(Domain domain) {
        this.parentDomain = domain;
    }

    public void setDefaultExpression(Expression expression) {
        this.defaultExpression = expression;
    }

    public void setOnUpdateExpression(Expression expression) {
        this.onUpdateExpression = expression;
    }

    public void setComment(String string) {
        this.comment = string;
    }

    @Override
    long update(Schema schema) {
        SchemaObject schemaObject;
        DataType dataType;
        if (schema.findDomain(this.typeName) != null) {
            if (this.ifNotExists) {
                return 0L;
            }
            throw DbException.get(90119, this.typeName);
        }
        if (this.typeName.indexOf(32) < 0 && (dataType = DataType.getTypeByName(this.typeName, this.getDatabase().getMode())) != null) {
            if (this.getDatabase().equalsIdentifiers(this.typeName, Value.getTypeName(dataType.type))) {
                throw DbException.get(90119, this.typeName);
            }
            schemaObject = this.getDatabase().getFirstUserTable();
            if (schemaObject != null) {
                StringBuilder stringBuilder = new StringBuilder(this.typeName).append(" (");
                schemaObject.getSQL(stringBuilder, 3).append(')');
                throw DbException.get(90119, stringBuilder.toString());
            }
        }
        int n = this.getObjectId();
        schemaObject = new Domain(schema, n, this.typeName);
        ((Domain)schemaObject).setDataType(this.dataType != null ? this.dataType : this.parentDomain.getDataType());
        ((Domain)schemaObject).setDomain(this.parentDomain);
        ((Domain)schemaObject).setDefaultExpression(this.session, this.defaultExpression);
        ((Domain)schemaObject).setOnUpdateExpression(this.session, this.onUpdateExpression);
        schemaObject.setComment(this.comment);
        schema.getDatabase().addSchemaObject(this.session, schemaObject);
        if (this.constraintCommands != null) {
            for (AlterDomainAddConstraint alterDomainAddConstraint : this.constraintCommands) {
                alterDomainAddConstraint.update();
            }
        }
        return 0L;
    }

    @Override
    public int getType() {
        return 33;
    }

    public void addConstraintCommand(AlterDomainAddConstraint alterDomainAddConstraint) {
        if (this.constraintCommands == null) {
            this.constraintCommands = Utils.newSmallArrayList();
        }
        this.constraintCommands.add(alterDomainAddConstraint);
    }
}

