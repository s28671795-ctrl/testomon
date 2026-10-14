/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Comment;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.table.Table;

public class SetComment
extends DefineCommand {
    private String schemaName;
    private String objectName;
    private boolean column;
    private String columnName;
    private int objectType;
    private Expression expr;

    public SetComment(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public long update() {
        DbObject dbObject;
        Object object;
        Database database = this.getDatabase();
        Object object2 = null;
        int n = 50000;
        if (this.schemaName == null) {
            this.schemaName = this.session.getCurrentSchemaName();
        }
        switch (this.objectType) {
            case 11: {
                object = database.getSchema(this.schemaName);
                this.session.getUser().checkSchemaOwner((Schema)object);
                object2 = ((Schema)object).getConstant(this.objectName);
                break;
            }
            case 5: {
                object = database.getSchema(this.schemaName);
                this.session.getUser().checkSchemaOwner((Schema)object);
                object2 = ((Schema)object).getConstraint(this.objectName);
                break;
            }
            case 9: {
                object = database.getSchema(this.schemaName);
                this.session.getUser().checkSchemaOwner((Schema)object);
                object2 = ((Schema)object).findFunction(this.objectName);
                n = 90077;
                break;
            }
            case 1: {
                object = database.getSchema(this.schemaName);
                this.session.getUser().checkSchemaOwner((Schema)object);
                object2 = ((Schema)object).getIndex(this.objectName);
                break;
            }
            case 7: {
                this.session.getUser().checkAdmin();
                this.schemaName = null;
                object2 = database.findRole(this.objectName);
                n = 90070;
                break;
            }
            case 10: {
                this.schemaName = null;
                object = database.getSchema(this.objectName);
                this.session.getUser().checkSchemaOwner((Schema)object);
                object2 = object;
                break;
            }
            case 3: {
                object = database.getSchema(this.schemaName);
                this.session.getUser().checkSchemaOwner((Schema)object);
                object2 = ((Schema)object).getSequence(this.objectName);
                break;
            }
            case 0: {
                object = database.getSchema(this.schemaName);
                this.session.getUser().checkSchemaOwner((Schema)object);
                object2 = ((Schema)object).getTableOrView(this.session, this.objectName);
                break;
            }
            case 4: {
                object = database.getSchema(this.schemaName);
                this.session.getUser().checkSchemaOwner((Schema)object);
                object2 = ((Schema)object).findTrigger(this.objectName);
                n = 90042;
                break;
            }
            case 2: {
                this.session.getUser().checkAdmin();
                this.schemaName = null;
                object2 = database.getUser(this.objectName);
                break;
            }
            case 12: {
                object = database.getSchema(this.schemaName);
                this.session.getUser().checkSchemaOwner((Schema)object);
                object2 = ((Schema)object).findDomain(this.objectName);
                n = 90120;
                break;
            }
        }
        if (object2 == null) {
            throw DbException.get(n, this.objectName);
        }
        object = this.expr.optimize(this.session).getValue(this.session).getString();
        if (object != null && ((String)object).isEmpty()) {
            object = null;
        }
        if (this.column) {
            dbObject = (Table)object2;
            ((Table)dbObject).getColumn(this.columnName).setComment((String)object);
        } else {
            ((DbObject)object2).setComment((String)object);
        }
        if (this.column || this.objectType == 0 || this.objectType == 2 || this.objectType == 1 || this.objectType == 5) {
            database.updateMeta(this.session, (DbObject)object2);
        } else {
            dbObject = database.findComment((DbObject)object2);
            if (dbObject == null) {
                if (object != null) {
                    int n2 = this.getObjectId();
                    dbObject = new Comment(database, n2, (DbObject)object2);
                    ((Comment)dbObject).setCommentText((String)object);
                    database.addDatabaseObject(this.session, dbObject);
                }
            } else if (object == null) {
                database.removeDatabaseObject(this.session, dbObject);
            } else {
                ((Comment)dbObject).setCommentText((String)object);
                database.updateMeta(this.session, dbObject);
            }
        }
        return 0L;
    }

    public void setCommentExpression(Expression expression) {
        this.expr = expression;
    }

    public void setObjectName(String string) {
        this.objectName = string;
    }

    public void setObjectType(int n) {
        this.objectType = n;
    }

    public void setColumnName(String string) {
        this.columnName = string;
    }

    public void setSchemaName(String string) {
        this.schemaName = string;
    }

    public void setColumn(boolean bl) {
        this.column = bl;
    }

    @Override
    public int getType() {
        return 52;
    }
}

