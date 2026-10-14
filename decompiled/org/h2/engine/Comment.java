/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.util.StringUtils;

public final class Comment
extends DbObject {
    private final int objectType;
    private final String quotedObjectName;
    private String commentText;

    public Comment(Database database, int n, DbObject dbObject) {
        super(database, n, Comment.getKey(dbObject), 2);
        this.objectType = dbObject.getType();
        this.quotedObjectName = dbObject.getSQL(0);
    }

    private static String getTypeName(int n) {
        switch (n) {
            case 11: {
                return "CONSTANT";
            }
            case 5: {
                return "CONSTRAINT";
            }
            case 9: {
                return "ALIAS";
            }
            case 1: {
                return "INDEX";
            }
            case 7: {
                return "ROLE";
            }
            case 10: {
                return "SCHEMA";
            }
            case 3: {
                return "SEQUENCE";
            }
            case 0: {
                return "TABLE";
            }
            case 4: {
                return "TRIGGER";
            }
            case 2: {
                return "USER";
            }
            case 12: {
                return "DOMAIN";
            }
        }
        return "type" + n;
    }

    @Override
    public String getCreateSQL() {
        StringBuilder stringBuilder = new StringBuilder("COMMENT ON ");
        stringBuilder.append(Comment.getTypeName(this.objectType)).append(' ').append(this.quotedObjectName).append(" IS ");
        if (this.commentText == null) {
            stringBuilder.append("NULL");
        } else {
            StringUtils.quoteStringSQL(stringBuilder, this.commentText);
        }
        return stringBuilder.toString();
    }

    @Override
    public int getType() {
        return 13;
    }

    @Override
    public void removeChildrenAndResources(SessionLocal sessionLocal) {
        this.database.removeMeta(sessionLocal, this.getId());
    }

    @Override
    public void checkRename() {
        throw DbException.getInternalError();
    }

    static String getKey(DbObject dbObject) {
        StringBuilder stringBuilder = new StringBuilder(Comment.getTypeName(dbObject.getType())).append(' ');
        dbObject.getSQL(stringBuilder, 0);
        return stringBuilder.toString();
    }

    public void setCommentText(String string) {
        this.commentText = string;
    }
}

