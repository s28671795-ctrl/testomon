/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;

public final class Setting
extends DbObject {
    private int intValue;
    private String stringValue;

    public Setting(Database database, int n, String string) {
        super(database, n, string, 10);
    }

    @Override
    public String getSQL(int n) {
        return this.getName();
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return stringBuilder.append(this.getName());
    }

    public void setIntValue(int n) {
        this.intValue = n;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public void setStringValue(String string) {
        this.stringValue = string;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    @Override
    public String getCreateSQL() {
        StringBuilder stringBuilder = new StringBuilder("SET ");
        this.getSQL(stringBuilder, 0).append(' ');
        if (this.stringValue != null) {
            stringBuilder.append(this.stringValue);
        } else {
            stringBuilder.append(this.intValue);
        }
        return stringBuilder.toString();
    }

    @Override
    public int getType() {
        return 6;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.database.removeMeta(sessionLocal, this.getId());
        this.invalidate();
    }

    @Override
    public void checkRename() {
        throw DbException.getUnsupportedException("RENAME");
    }
}

