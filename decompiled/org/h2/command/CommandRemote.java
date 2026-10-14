/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command;

import java.io.IOException;
import java.util.ArrayList;
import org.h2.command.CommandInterface;
import org.h2.engine.GeneratedKeysMode;
import org.h2.engine.SessionRemote;
import org.h2.engine.SysProperties;
import org.h2.expression.ParameterInterface;
import org.h2.expression.ParameterRemote;
import org.h2.message.DbException;
import org.h2.message.Trace;
import org.h2.result.ResultInterface;
import org.h2.result.ResultRemote;
import org.h2.result.ResultWithGeneratedKeys;
import org.h2.util.Utils;
import org.h2.value.Transfer;
import org.h2.value.Value;
import org.h2.value.ValueLob;
import org.h2.value.ValueNull;

public class CommandRemote
implements CommandInterface {
    private final ArrayList<Transfer> transferList;
    private final ArrayList<ParameterInterface> parameters;
    private final Trace trace;
    private final String sql;
    private final int fetchSize;
    private SessionRemote session;
    private int id;
    private boolean isQuery;
    private int cmdType = 0;
    private boolean readonly;
    private final int created;

    public CommandRemote(SessionRemote sessionRemote, ArrayList<Transfer> arrayList, String string, int n) {
        this.transferList = arrayList;
        this.trace = sessionRemote.getTrace();
        this.sql = string;
        this.parameters = Utils.newSmallArrayList();
        this.prepare(sessionRemote, true);
        this.session = sessionRemote;
        this.fetchSize = n;
        this.created = sessionRemote.getLastReconnect();
    }

    @Override
    public void stop() {
    }

    private void prepare(SessionRemote sessionRemote, boolean bl) {
        this.id = sessionRemote.getNextId();
        int n = 0;
        for (int i = 0; i < this.transferList.size(); ++i) {
            try {
                Transfer transfer = this.transferList.get(i);
                if (bl) {
                    sessionRemote.traceOperation("SESSION_PREPARE_READ_PARAMS2", this.id);
                    transfer.writeInt(18).writeInt(this.id).writeString(this.sql);
                } else {
                    sessionRemote.traceOperation("SESSION_PREPARE", this.id);
                    transfer.writeInt(0).writeInt(this.id).writeString(this.sql);
                }
                sessionRemote.done(transfer);
                this.isQuery = transfer.readBoolean();
                this.readonly = transfer.readBoolean();
                this.cmdType = bl ? transfer.readInt() : 0;
                int n2 = transfer.readInt();
                if (!bl) continue;
                this.parameters.clear();
                for (int j = 0; j < n2; ++j) {
                    ParameterRemote parameterRemote = new ParameterRemote(j);
                    parameterRemote.readMetaData(transfer);
                    this.parameters.add(parameterRemote);
                }
                continue;
            }
            catch (IOException iOException) {
                sessionRemote.removeServer(iOException, i--, ++n);
            }
        }
    }

    @Override
    public boolean isQuery() {
        return this.isQuery;
    }

    public ArrayList<ParameterInterface> getParameters() {
        return this.parameters;
    }

    private void prepareIfRequired() {
        if (this.session.getLastReconnect() != this.created) {
            this.id = Integer.MIN_VALUE;
        }
        this.session.checkClosed();
        if (this.id <= this.session.getCurrentId() - SysProperties.SERVER_CACHED_OBJECTS) {
            this.prepare(this.session, false);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ResultInterface getMetaData() {
        SessionRemote sessionRemote = this.session;
        sessionRemote.lock();
        try {
            if (!this.isQuery) {
                ResultInterface resultInterface = null;
                return resultInterface;
            }
            int n = sessionRemote.getNextId();
            ResultRemote resultRemote = null;
            int n2 = 0;
            for (int i = 0; i < this.transferList.size(); ++i) {
                this.prepareIfRequired();
                Transfer transfer = this.transferList.get(i);
                try {
                    sessionRemote.traceOperation("COMMAND_GET_META_DATA", this.id);
                    transfer.writeInt(10).writeInt(this.id).writeInt(n);
                    sessionRemote.done(transfer);
                    int n3 = transfer.readInt();
                    resultRemote = new ResultRemote(sessionRemote, transfer, n, n3, Integer.MAX_VALUE);
                    break;
                }
                catch (IOException iOException) {
                    sessionRemote.removeServer(iOException, i--, ++n2);
                    continue;
                }
            }
            sessionRemote.autoCommitIfCluster();
            ResultRemote resultRemote2 = resultRemote;
            return resultRemote2;
        }
        finally {
            sessionRemote.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ResultInterface executeQuery(long l, boolean bl) {
        this.checkParameters();
        SessionRemote sessionRemote = this.session;
        sessionRemote.lock();
        try {
            int n = sessionRemote.getNextId();
            ResultRemote resultRemote = null;
            int n2 = 0;
            for (int i = 0; i < this.transferList.size(); ++i) {
                this.prepareIfRequired();
                Transfer transfer = this.transferList.get(i);
                try {
                    sessionRemote.traceOperation("COMMAND_EXECUTE_QUERY", this.id);
                    transfer.writeInt(2).writeInt(this.id).writeInt(n);
                    transfer.writeRowCount(l);
                    int n3 = sessionRemote.isClustered() || bl ? Integer.MAX_VALUE : this.fetchSize;
                    transfer.writeInt(n3);
                    this.sendParameters(transfer);
                    sessionRemote.done(transfer);
                    int n4 = transfer.readInt();
                    if (resultRemote != null) {
                        resultRemote.close();
                        resultRemote = null;
                    }
                    resultRemote = new ResultRemote(sessionRemote, transfer, n, n4, n3);
                    if (!this.readonly) continue;
                    break;
                }
                catch (IOException iOException) {
                    sessionRemote.removeServer(iOException, i--, ++n2);
                }
            }
            sessionRemote.autoCommitIfCluster();
            sessionRemote.readSessionState();
            ResultRemote resultRemote2 = resultRemote;
            return resultRemote2;
        }
        finally {
            sessionRemote.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ResultWithGeneratedKeys executeUpdate(Object object) {
        this.checkParameters();
        int n = GeneratedKeysMode.valueOf(object);
        boolean bl = n != 0;
        int n2 = bl ? this.session.getNextId() : 0;
        SessionRemote sessionRemote = this.session;
        sessionRemote.lock();
        try {
            long l = 0L;
            ResultRemote resultRemote = null;
            boolean bl2 = false;
            int n3 = 0;
            for (int i = 0; i < this.transferList.size(); ++i) {
                this.prepareIfRequired();
                Transfer transfer = this.transferList.get(i);
                try {
                    sessionRemote.traceOperation("COMMAND_EXECUTE_UPDATE", this.id);
                    transfer.writeInt(3).writeInt(this.id);
                    this.sendParameters(transfer);
                    transfer.writeInt(n);
                    switch (n) {
                        case 2: {
                            int[] nArray = (int[])object;
                            transfer.writeInt(nArray.length);
                            for (int n4 : nArray) {
                                transfer.writeInt(n4);
                            }
                            break;
                        }
                        case 3: {
                            String[] stringArray = (String[])object;
                            transfer.writeInt(stringArray.length);
                            for (String string : stringArray) {
                                transfer.writeString(string);
                            }
                            break;
                        }
                    }
                    sessionRemote.done(transfer);
                    l = transfer.readRowCount();
                    bl2 = transfer.readBoolean();
                    if (!bl) continue;
                    int n6 = transfer.readInt();
                    if (resultRemote != null) {
                        resultRemote.close();
                        resultRemote = null;
                    }
                    resultRemote = new ResultRemote(sessionRemote, transfer, n2, n6, Integer.MAX_VALUE);
                    continue;
                }
                catch (IOException iOException) {
                    sessionRemote.removeServer(iOException, i--, ++n3);
                }
            }
            sessionRemote.setAutoCommitFromServer(bl2);
            sessionRemote.autoCommitIfCluster();
            sessionRemote.readSessionState();
            if (resultRemote != null) {
                ResultWithGeneratedKeys.WithKeys withKeys = new ResultWithGeneratedKeys.WithKeys(l, resultRemote);
                return withKeys;
            }
            ResultWithGeneratedKeys resultWithGeneratedKeys = ResultWithGeneratedKeys.of(l);
            return resultWithGeneratedKeys;
        }
        finally {
            sessionRemote.unlock();
        }
    }

    private void checkParameters() {
        if (this.cmdType != 60) {
            for (ParameterInterface parameterInterface : this.parameters) {
                parameterInterface.checkSet();
            }
        }
    }

    private void sendParameters(Transfer transfer) throws IOException {
        int n = this.parameters.size();
        transfer.writeInt(n);
        for (ParameterInterface parameterInterface : this.parameters) {
            Value value = parameterInterface.getParamValue();
            if (value == null && this.cmdType == 60) {
                value = ValueNull.INSTANCE;
            }
            transfer.writeValue(value);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() {
        SessionRemote sessionRemote = this.session;
        if (sessionRemote == null || sessionRemote.isClosed()) {
            return;
        }
        sessionRemote.lock();
        try {
            sessionRemote.traceOperation("COMMAND_CLOSE", this.id);
            for (Transfer object : this.transferList) {
                try {
                    object.writeInt(4).writeInt(this.id);
                }
                catch (IOException iOException) {
                    this.trace.error(iOException, "close");
                }
            }
        }
        finally {
            sessionRemote.unlock();
        }
        this.session = null;
        try {
            for (ParameterInterface parameterInterface : this.parameters) {
                Value value = parameterInterface.getParamValue();
                if (!(value instanceof ValueLob)) continue;
                ((ValueLob)value).remove();
            }
        }
        catch (DbException dbException) {
            this.trace.error(dbException, "close");
        }
        this.parameters.clear();
    }

    @Override
    public void cancel() {
        this.session.cancelStatement(this.id);
    }

    public String toString() {
        return this.sql + Trace.formatParams(this.getParameters());
    }

    @Override
    public int getCommandType() {
        return this.cmdType;
    }
}

