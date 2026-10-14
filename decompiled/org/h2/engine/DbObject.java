/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.util.ArrayList;
import org.h2.command.ParserBase;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.table.Table;
import org.h2.util.HasSQL;
import org.h2.util.ParserUtil;

public abstract class DbObject
implements HasSQL {
    public static final int TABLE_OR_VIEW = 0;
    public static final int INDEX = 1;
    public static final int USER = 2;
    public static final int SEQUENCE = 3;
    public static final int TRIGGER = 4;
    public static final int CONSTRAINT = 5;
    public static final int SETTING = 6;
    public static final int ROLE = 7;
    public static final int RIGHT = 8;
    public static final int FUNCTION_ALIAS = 9;
    public static final int SCHEMA = 10;
    public static final int CONSTANT = 11;
    public static final int DOMAIN = 12;
    public static final int COMMENT = 13;
    public static final int AGGREGATE = 14;
    public static final int SYNONYM = 15;
    protected Database database;
    protected Trace trace;
    protected String comment;
    private int id;
    private String objectName;
    private long modificationId;
    private boolean temporary;

    protected DbObject(Database database, int n, String string, int n2) {
        this.database = database;
        this.trace = database.getTrace(n2);
        this.id = n;
        this.objectName = string;
        this.modificationId = database.getModificationMetaId();
    }

    public final void setModified() {
        this.modificationId = this.database == null ? -1L : this.database.getNextModificationMetaId();
    }

    public final long getModificationId() {
        return this.modificationId;
    }

    protected final void setObjectName(String string) {
        this.objectName = string;
    }

    @Override
    public String getSQL(int n) {
        return ParserBase.quoteIdentifier(this.objectName, n);
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return ParserUtil.quoteIdentifier(stringBuilder, this.objectName, n);
    }

    public ArrayList<DbObject> getChildren() {
        return null;
    }

    public final Database getDatabase() {
        return this.database;
    }

    public final int getId() {
        return this.id;
    }

    public final String getName() {
        return this.objectName;
    }

    protected void invalidate() {
        if (this.id == -1) {
            throw DbException.getInternalError();
        }
        this.setModified();
        this.id = -1;
        this.database = null;
        this.trace = null;
        this.objectName = null;
    }

    public final boolean isValid() {
        return this.id != -1;
    }

    public String getCreateSQLForCopy(Table table, String string) {
        throw DbException.getInternalError(this.toString());
    }

    public String getCreateSQLForMeta() {
        return this.getCreateSQL();
    }

    public abstract String getCreateSQL();

    public String getDropSQL() {
        return null;
    }

    public abstract int getType();

    public abstract void removeChildrenAndResources(SessionLocal var1);

    public void checkRename() {
    }

    public void rename(String string) {
        this.checkRename();
        this.objectName = string;
        this.setModified();
    }

    public boolean isTemporary() {
        return this.temporary;
    }

    public void setTemporary(boolean bl) {
        this.temporary = bl;
    }

    public void setComment(String string) {
        this.comment = string != null && !string.isEmpty() ? string : null;
    }

    public String getComment() {
        return this.comment;
    }

    public String toString() {
        return this.objectName + ":" + this.id + ":" + super.toString();
    }
}

