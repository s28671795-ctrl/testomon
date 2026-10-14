/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.ddl;

import java.util.ArrayList;
import org.h2.command.Prepared;
import org.h2.command.ddl.DefineCommand;
import org.h2.engine.Procedure;
import org.h2.engine.SessionLocal;
import org.h2.expression.Parameter;

public class PrepareProcedure
extends DefineCommand {
    private String procedureName;
    private Prepared prepared;

    public PrepareProcedure(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    @Override
    public void checkParameters() {
    }

    @Override
    public long update() {
        Procedure procedure = new Procedure(this.procedureName, this.prepared);
        this.prepared.setParameterList(this.parameters);
        this.prepared.setPrepareAlways(this.prepareAlways);
        this.prepared.prepare();
        this.session.addProcedure(procedure);
        return 0L;
    }

    public void setProcedureName(String string) {
        this.procedureName = string;
    }

    public void setPrepared(Prepared prepared) {
        this.prepared = prepared;
    }

    @Override
    public ArrayList<Parameter> getParameters() {
        return new ArrayList<Parameter>(0);
    }

    @Override
    public int getType() {
        return 51;
    }
}

