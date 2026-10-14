/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.h2.command.Command;
import org.h2.command.CommandContainer;
import org.h2.command.Prepared;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Parameter;
import org.h2.expression.ParameterInterface;
import org.h2.result.ResultInterface;
import org.h2.result.ResultWithGeneratedKeys;

class CommandList
extends Command {
    private CommandContainer command;
    private final ArrayList<Prepared> commands;
    private final ArrayList<Parameter> parameters;
    private String remaining;
    private Command remainingCommand;

    CommandList(SessionLocal sessionLocal, String string, CommandContainer commandContainer, ArrayList<Prepared> arrayList, ArrayList<Parameter> arrayList2, String string2) {
        super(sessionLocal, string);
        this.command = commandContainer;
        this.commands = arrayList;
        this.parameters = arrayList2;
        this.remaining = string2;
    }

    @Override
    public ArrayList<? extends ParameterInterface> getParameters() {
        return this.parameters;
    }

    private void executeRemaining() {
        for (Prepared prepared : this.commands) {
            prepared.prepare();
            if (prepared.isQuery()) {
                prepared.query(0L);
                continue;
            }
            prepared.update();
        }
        if (this.remaining != null) {
            this.remainingCommand = this.session.prepareLocal(this.remaining);
            this.remaining = null;
            if (this.remainingCommand.isQuery()) {
                this.remainingCommand.query(0L);
            } else {
                this.remainingCommand.update(null);
            }
        }
    }

    @Override
    public ResultWithGeneratedKeys update(Object object) {
        ResultWithGeneratedKeys resultWithGeneratedKeys = this.command.executeUpdate(null);
        this.executeRemaining();
        return resultWithGeneratedKeys;
    }

    @Override
    public ResultInterface query(long l) {
        ResultInterface resultInterface = this.command.query(l);
        this.executeRemaining();
        return resultInterface;
    }

    @Override
    public void stop() {
        this.command.stop();
        for (Prepared prepared : this.commands) {
            CommandContainer.clearCTE(this.session, prepared);
        }
        if (this.remainingCommand != null) {
            this.remainingCommand.stop();
        }
    }

    @Override
    public boolean isQuery() {
        return this.command.isQuery();
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public ResultInterface queryMeta() {
        return this.command.queryMeta();
    }

    @Override
    public int getCommandType() {
        return this.command.getCommandType();
    }

    @Override
    public Set<DbObject> getDependencies() {
        HashSet<DbObject> hashSet = new HashSet<DbObject>();
        for (Prepared prepared : this.commands) {
            prepared.collectDependencies(hashSet);
        }
        return hashSet;
    }

    @Override
    protected boolean isRetryable() {
        if (!this.command.isRetryable()) {
            return false;
        }
        for (Prepared prepared : this.commands) {
            if (prepared.isRetryable()) continue;
            return false;
        }
        return this.remainingCommand == null || this.remainingCommand.isRetryable();
    }
}

