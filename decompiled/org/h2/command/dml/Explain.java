/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command.dml;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import org.h2.command.Prepared;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.mvstore.db.Store;
import org.h2.result.LocalResult;
import org.h2.result.ResultInterface;
import org.h2.table.Column;
import org.h2.value.TypeInfo;
import org.h2.value.ValueVarchar;

public class Explain
extends Prepared {
    private Prepared command;
    private LocalResult result;
    private boolean executeCommand;

    public Explain(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public void setCommand(Prepared prepared) {
        this.command = prepared;
    }

    public Prepared getCommand() {
        return this.command;
    }

    @Override
    public void prepare() {
        this.command.prepare();
    }

    public void setExecuteCommand(boolean bl) {
        this.executeCommand = bl;
    }

    @Override
    public ResultInterface queryMeta() {
        return this.query(-1L);
    }

    @Override
    protected void checkParameters() {
        if (this.executeCommand) {
            super.checkParameters();
        }
    }

    @Override
    public ResultInterface query(long l) {
        Database database = this.getDatabase();
        Expression[] expressionArray = new Expression[]{new ExpressionColumn(database, new Column("PLAN", TypeInfo.TYPE_VARCHAR))};
        this.result = new LocalResult(this.session, expressionArray, 1, 1);
        int n = 8;
        if (l >= 0L) {
            String string;
            if (this.executeCommand) {
                Store store = null;
                if (database.isPersistent()) {
                    store = database.getStore();
                    store.statisticsStart();
                }
                if (this.command.isQuery()) {
                    this.command.query(l);
                } else {
                    this.command.update();
                }
                string = this.command.getPlanSQL(n);
                Map<String, Integer> map = null;
                if (store != null) {
                    map = store.statisticsEnd();
                }
                if (map != null) {
                    int n2 = 0;
                    for (Integer object : map.values()) {
                        n2 += object.intValue();
                    }
                    if (n2 > 0) {
                        map = new TreeMap<String, Integer>(map);
                        StringBuilder stringBuilder = new StringBuilder();
                        if (map.size() > 1) {
                            stringBuilder.append("total: ").append(n2).append('\n');
                        }
                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
                            int n3 = entry.getValue();
                            int n4 = (int)(100L * (long)n3 / (long)n2);
                            stringBuilder.append(entry.getKey()).append(": ").append(n3);
                            if (map.size() > 1) {
                                stringBuilder.append(" (").append(n4).append("%)");
                            }
                            stringBuilder.append('\n');
                        }
                        string = string + "\n/*\n" + stringBuilder + "*/";
                    }
                }
            } else {
                string = this.command.getPlanSQL(n);
            }
            this.add(string);
        }
        this.result.done();
        return this.result;
    }

    private void add(String string) {
        this.result.addRow(ValueVarchar.get(string));
    }

    @Override
    public boolean isQuery() {
        return true;
    }

    @Override
    public boolean isTransactional() {
        return true;
    }

    @Override
    public boolean isReadOnly() {
        return this.command.isReadOnly();
    }

    @Override
    public int getType() {
        return this.executeCommand ? 86 : 60;
    }

    @Override
    public void collectDependencies(HashSet<DbObject> hashSet) {
        this.command.collectDependencies(hashSet);
    }
}

