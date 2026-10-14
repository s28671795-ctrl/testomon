/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import org.h2.command.Prepared;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.message.DbException;
import org.h2.result.ResultInterface;

public class TransactionCommand
extends Prepared {
    private final int type;
    private String savepointName;
    private String transactionName;

    public TransactionCommand(SessionLocal sessionLocal, int n) {
        super(sessionLocal);
        this.type = n;
    }

    public void setSavepointName(String string) {
        this.savepointName = string;
    }

    @Override
    public long update() {
        switch (this.type) {
            case 69: {
                this.session.setAutoCommit(true);
                break;
            }
            case 70: {
                this.session.setAutoCommit(false);
                break;
            }
            case 83: {
                this.session.begin();
                break;
            }
            case 71: {
                this.session.commit(false);
                break;
            }
            case 72: {
                this.session.rollback();
                break;
            }
            case 73: {
                this.session.getUser().checkAdmin();
                this.getDatabase().checkpoint();
                break;
            }
            case 74: {
                this.session.addSavepoint(this.savepointName);
                break;
            }
            case 75: {
                this.session.rollbackToSavepoint(this.savepointName);
                break;
            }
            case 76: {
                this.session.getUser().checkAdmin();
                this.getDatabase().sync();
                break;
            }
            case 77: {
                this.session.prepareCommit(this.transactionName);
                break;
            }
            case 78: {
                this.session.getUser().checkAdmin();
                this.session.setPreparedTransaction(this.transactionName, true);
                break;
            }
            case 79: {
                this.session.getUser().checkAdmin();
                this.session.setPreparedTransaction(this.transactionName, false);
                break;
            }
            case 80: 
            case 82: 
            case 84: {
                this.session.commit(false);
            }
            case 81: {
                this.session.getUser().checkAdmin();
                this.session.throttle();
                Database database = this.getDatabase();
                if (!database.setExclusiveSession(this.session, true)) break;
                database.setCompactMode(this.type);
                database.setCloseDelay(0);
                this.session.close();
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.type);
            }
        }
        return 0L;
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    @Override
    public boolean needRecompile() {
        return false;
    }

    public void setTransactionName(String string) {
        this.transactionName = string;
    }

    @Override
    public ResultInterface queryMeta() {
        return null;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }
}

