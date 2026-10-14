/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.TransactionInProgressException;
import javax.jms.TransactionRolledBackException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.LocalTransactionEventListener;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.DataArrayResponse;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.IntegerResponse;
import org.apache.activemq.command.LocalTransactionId;
import org.apache.activemq.command.TransactionId;
import org.apache.activemq.command.TransactionInfo;
import org.apache.activemq.command.XATransactionId;
import org.apache.activemq.transaction.Synchronization;
import org.apache.activemq.util.JMSExceptionSupport;
import org.apache.activemq.util.LongSequenceGenerator;
import org.apache.activemq.util.XASupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionContext
implements XAResource {
    public static final String xaErrorCodeMarker = "xaErrorCode:";
    private static final Logger LOG = LoggerFactory.getLogger(TransactionContext.class);
    private static final HashMap<TransactionId, List<TransactionContext>> ENDED_XA_TRANSACTION_CONTEXTS = new HashMap();
    private ActiveMQConnection connection;
    private final LongSequenceGenerator localTransactionIdGenerator;
    private List<Synchronization> synchronizations;
    private Xid associatedXid;
    private TransactionId transactionId;
    private LocalTransactionEventListener localTransactionEventListener;
    private int beforeEndIndex;
    private volatile boolean rollbackOnly;

    public TransactionContext() {
        this.localTransactionIdGenerator = null;
    }

    public TransactionContext(ActiveMQConnection connection) {
        this.connection = connection;
        this.localTransactionIdGenerator = connection.getLocalTransactionIdGenerator();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isInXATransaction() {
        if (this.transactionId != null && this.transactionId.isXATransaction()) {
            return true;
        }
        HashMap<TransactionId, List<TransactionContext>> hashMap = ENDED_XA_TRANSACTION_CONTEXTS;
        synchronized (hashMap) {
            for (List<TransactionContext> transactions : ENDED_XA_TRANSACTION_CONTEXTS.values()) {
                if (!transactions.contains(this)) continue;
                return true;
            }
        }
        return false;
    }

    public void setRollbackOnly(boolean val) {
        this.rollbackOnly = val;
    }

    public boolean isInLocalTransaction() {
        return this.transactionId != null && this.transactionId.isLocalTransaction();
    }

    public boolean isInTransaction() {
        return this.transactionId != null;
    }

    public LocalTransactionEventListener getLocalTransactionEventListener() {
        return this.localTransactionEventListener;
    }

    public void setLocalTransactionEventListener(LocalTransactionEventListener localTransactionEventListener) {
        this.localTransactionEventListener = localTransactionEventListener;
    }

    public void addSynchronization(Synchronization s2) {
        if (this.synchronizations == null) {
            this.synchronizations = new ArrayList<Synchronization>(10);
        }
        this.synchronizations.add(s2);
    }

    private void afterRollback() throws JMSException {
        if (this.synchronizations == null) {
            return;
        }
        Throwable firstException = null;
        int size = this.synchronizations.size();
        for (int i = 0; i < size; ++i) {
            try {
                this.synchronizations.get(i).afterRollback();
                continue;
            }
            catch (Throwable t2) {
                LOG.debug("Exception from afterRollback on {}", (Object)this.synchronizations.get(i), (Object)t2);
                if (firstException != null) continue;
                firstException = t2;
            }
        }
        this.synchronizations = null;
        if (firstException != null) {
            throw JMSExceptionSupport.create(firstException);
        }
    }

    private void afterCommit() throws JMSException {
        if (this.synchronizations == null) {
            return;
        }
        Throwable firstException = null;
        int size = this.synchronizations.size();
        for (int i = 0; i < size; ++i) {
            try {
                this.synchronizations.get(i).afterCommit();
                continue;
            }
            catch (Throwable t2) {
                LOG.debug("Exception from afterCommit on {}", (Object)this.synchronizations.get(i), (Object)t2);
                if (firstException != null) continue;
                firstException = t2;
            }
        }
        this.synchronizations = null;
        if (firstException != null) {
            throw JMSExceptionSupport.create(firstException);
        }
    }

    private void beforeEnd() throws JMSException {
        if (this.synchronizations == null) {
            return;
        }
        int size = this.synchronizations.size();
        try {
            while (this.beforeEndIndex < size) {
                this.synchronizations.get(this.beforeEndIndex++).beforeEnd();
            }
        }
        catch (JMSException e) {
            throw e;
        }
        catch (Throwable e) {
            throw JMSExceptionSupport.create(e);
        }
    }

    public TransactionId getTransactionId() {
        return this.transactionId;
    }

    public void begin() throws JMSException {
        if (this.isInXATransaction()) {
            throw new TransactionInProgressException("Cannot start local transaction.  XA transaction is already in progress.");
        }
        if (this.transactionId == null) {
            this.synchronizations = null;
            this.beforeEndIndex = 0;
            this.setRollbackOnly(false);
            this.transactionId = new LocalTransactionId(this.getConnectionId(), this.localTransactionIdGenerator.getNextSequenceId());
            TransactionInfo info = new TransactionInfo(this.getConnectionId(), this.transactionId, 0);
            this.connection.ensureConnectionInfoSent();
            this.connection.asyncSendPacket(info);
            if (this.localTransactionEventListener != null) {
                this.localTransactionEventListener.beginEvent();
            }
            LOG.debug("Begin:{}", (Object)this.transactionId);
        }
    }

    public void rollback() throws JMSException {
        if (this.isInXATransaction()) {
            throw new TransactionInProgressException("Cannot rollback() if an XA transaction is already in progress ");
        }
        try {
            this.beforeEnd();
        }
        catch (TransactionRolledBackException canOcurrOnFailover) {
            LOG.warn("rollback processing error", canOcurrOnFailover);
        }
        if (this.transactionId != null) {
            LOG.debug("Rollback: {} syncCount: {}", (Object)this.transactionId, (Object)(this.synchronizations != null ? this.synchronizations.size() : 0));
            TransactionInfo info = new TransactionInfo(this.getConnectionId(), this.transactionId, 4);
            this.transactionId = null;
            this.connection.syncSendPacket((Command)info, this.connection.isClosing() ? this.connection.getCloseTimeout() : 0);
            if (this.localTransactionEventListener != null) {
                this.localTransactionEventListener.rollbackEvent();
            }
        }
        this.afterRollback();
    }

    public void commit() throws JMSException {
        if (this.isInXATransaction()) {
            throw new TransactionInProgressException("Cannot commit() if an XA transaction is already in progress ");
        }
        try {
            this.beforeEnd();
        }
        catch (JMSException e) {
            this.rollback();
            throw e;
        }
        if (this.transactionId != null && this.rollbackOnly) {
            String message = "Commit of " + this.transactionId + "  failed due to rollback only request; typically due to failover with pending acks";
            try {
                this.rollback();
            }
            finally {
                LOG.warn(message);
                throw new TransactionRolledBackException(message);
            }
        }
        if (this.transactionId != null) {
            LOG.debug("Commit: {} syncCount: {}", (Object)this.transactionId, (Object)(this.synchronizations != null ? this.synchronizations.size() : 0));
            TransactionInfo info = new TransactionInfo(this.getConnectionId(), this.transactionId, 2);
            this.transactionId = null;
            try {
                this.connection.syncSendPacket(info);
                if (this.localTransactionEventListener != null) {
                    this.localTransactionEventListener.commitEvent();
                }
                this.afterCommit();
            }
            catch (JMSException cause) {
                LOG.info("commit failed for transaction {}", (Object)info.getTransactionId(), (Object)cause);
                if (this.localTransactionEventListener != null) {
                    this.localTransactionEventListener.rollbackEvent();
                }
                this.afterRollback();
                throw cause;
            }
        }
    }

    @Override
    public void start(Xid xid, int flags) throws XAException {
        LOG.debug("Start: {}, flags: {}", (Object)xid, (Object)XASupport.toString(flags));
        if (this.isInLocalTransaction()) {
            throw new XAException(-6);
        }
        if (this.associatedXid != null) {
            throw new XAException(-6);
        }
        this.synchronizations = null;
        this.beforeEndIndex = 0;
        this.setRollbackOnly(false);
        this.setXid(xid);
    }

    private ConnectionId getConnectionId() {
        return this.connection.getConnectionInfo().getConnectionId();
    }

    @Override
    public void end(Xid xid, int flags) throws XAException {
        LOG.debug("End: {}, flags: {}", (Object)xid, (Object)XASupport.toString(flags));
        if (this.isInLocalTransaction()) {
            throw new XAException(-6);
        }
        if ((flags & 0x22000000) != 0) {
            if (!this.equals(this.associatedXid, xid)) {
                throw new XAException(-6);
            }
            this.invokeBeforeEnd();
        } else if ((flags & 0x4000000) == 0x4000000) {
            if (this.equals(this.associatedXid, xid)) {
                this.invokeBeforeEnd();
            }
        } else {
            throw new XAException(-5);
        }
    }

    private void invokeBeforeEnd() throws XAException {
        boolean throwingException = false;
        try {
            this.beforeEnd();
        }
        catch (JMSException e) {
            throwingException = true;
            throw this.toXAException(e);
        }
        finally {
            block10: {
                try {
                    this.setXid(null);
                }
                catch (XAException ignoreIfWillMask) {
                    if (throwingException) break block10;
                    throw ignoreIfWillMask;
                }
            }
        }
    }

    private boolean equals(Xid xid1, Xid xid2) {
        if (xid1 == xid2) {
            return true;
        }
        if (xid1 == null ^ xid2 == null) {
            return false;
        }
        return xid1.getFormatId() == xid2.getFormatId() && Arrays.equals(xid1.getBranchQualifier(), xid2.getBranchQualifier()) && Arrays.equals(xid1.getGlobalTransactionId(), xid2.getGlobalTransactionId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int prepare(Xid xid) throws XAException {
        LOG.debug("Prepare: {}", (Object)xid);
        if (xid == null || this.equals(this.associatedXid, xid)) {
            throw new XAException(-6);
        }
        XATransactionId x = new XATransactionId(xid);
        if (this.rollbackOnly) {
            LOG.warn("prepare of: " + x + " failed because it was marked rollback only; typically due to failover with pending acks");
            throw new XAException(103);
        }
        try {
            TransactionInfo info = new TransactionInfo(this.getConnectionId(), x, 1);
            IntegerResponse response = (IntegerResponse)this.connection.syncSendPacket(info);
            if (3 == response.getResult()) {
                List<TransactionContext> l;
                HashMap<TransactionId, List<TransactionContext>> hashMap = ENDED_XA_TRANSACTION_CONTEXTS;
                synchronized (hashMap) {
                    l = ENDED_XA_TRANSACTION_CONTEXTS.remove(x);
                }
                if (l != null && !l.isEmpty()) {
                    LOG.debug("firing afterCommit callbacks on XA_RDONLY from prepare: {}", (Object)xid);
                    for (TransactionContext ctx : l) {
                        ctx.afterCommit();
                    }
                }
            }
            return response.getResult();
        }
        catch (JMSException e) {
            List<TransactionContext> l;
            LOG.warn("prepare of: " + x + " failed with: " + e, e);
            HashMap<TransactionId, List<TransactionContext>> hashMap = ENDED_XA_TRANSACTION_CONTEXTS;
            synchronized (hashMap) {
                l = ENDED_XA_TRANSACTION_CONTEXTS.remove(x);
            }
            if (l != null) {
                for (TransactionContext ctx : l) {
                    try {
                        ctx.afterRollback();
                    }
                    catch (Throwable ignored) {
                        LOG.debug("failed to firing afterRollback callbacks on prepare failure, txid: {}, context: {}", x, ctx, ignored);
                    }
                }
            }
            throw this.toXAException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void rollback(Xid xid) throws XAException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rollback: " + xid);
        }
        if (xid == null) {
            throw new XAException(-6);
        }
        XATransactionId x = this.equals(this.associatedXid, xid) ? (XATransactionId)this.transactionId : new XATransactionId(xid);
        try {
            List<TransactionContext> l;
            this.connection.checkClosedOrFailed();
            this.connection.ensureConnectionInfoSent();
            TransactionInfo info = new TransactionInfo(this.getConnectionId(), x, 4);
            this.connection.syncSendPacket(info);
            HashMap<TransactionId, List<TransactionContext>> hashMap = ENDED_XA_TRANSACTION_CONTEXTS;
            synchronized (hashMap) {
                l = ENDED_XA_TRANSACTION_CONTEXTS.remove(x);
            }
            if (l != null) {
                for (TransactionContext ctx : l) {
                    ctx.afterRollback();
                }
            }
        }
        catch (JMSException e) {
            throw this.toXAException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void commit(Xid xid, boolean onePhase) throws XAException {
        LOG.debug("Commit: {}, onePhase={}", (Object)xid, (Object)onePhase);
        if (xid == null || this.equals(this.associatedXid, xid)) {
            throw new XAException(-6);
        }
        XATransactionId x = new XATransactionId(xid);
        if (this.rollbackOnly) {
            LOG.warn("commit of: " + x + " failed because it was marked rollback only; typically due to failover with pending acks");
            throw new XAException(103);
        }
        try {
            List<TransactionContext> l;
            this.connection.checkClosedOrFailed();
            this.connection.ensureConnectionInfoSent();
            TransactionInfo info = new TransactionInfo(this.getConnectionId(), x, onePhase ? (byte)2 : 3);
            this.connection.syncSendPacket(info);
            HashMap<TransactionId, List<TransactionContext>> hashMap = ENDED_XA_TRANSACTION_CONTEXTS;
            synchronized (hashMap) {
                l = ENDED_XA_TRANSACTION_CONTEXTS.remove(x);
            }
            if (l != null) {
                for (TransactionContext ctx : l) {
                    try {
                        ctx.afterCommit();
                    }
                    catch (Exception ignored) {
                        LOG.debug("ignoring exception from after completion on ended transaction: {}", (Object)ignored, (Object)ignored);
                    }
                }
            }
        }
        catch (JMSException e) {
            LOG.warn("commit of: " + x + " failed with: " + e, e);
            if (onePhase) {
                List<TransactionContext> l;
                HashMap<TransactionId, List<TransactionContext>> hashMap = ENDED_XA_TRANSACTION_CONTEXTS;
                synchronized (hashMap) {
                    l = ENDED_XA_TRANSACTION_CONTEXTS.remove(x);
                }
                if (l != null) {
                    for (TransactionContext ctx : l) {
                        try {
                            ctx.afterRollback();
                        }
                        catch (Throwable ignored) {
                            LOG.debug("failed to firing afterRollback callbacks commit failure, txid: {}, context: {}", x, ctx, ignored);
                        }
                    }
                }
            }
            throw this.toXAException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void forget(Xid xid) throws XAException {
        LOG.debug("Forget: {}", (Object)xid);
        if (xid == null) {
            throw new XAException(-6);
        }
        XATransactionId x = this.equals(this.associatedXid, xid) ? (XATransactionId)this.transactionId : new XATransactionId(xid);
        TransactionInfo info = new TransactionInfo(this.getConnectionId(), x, 6);
        try {
            this.connection.syncSendPacket(info);
        }
        catch (JMSException e) {
            throw this.toXAException(e);
        }
        HashMap<TransactionId, List<TransactionContext>> hashMap = ENDED_XA_TRANSACTION_CONTEXTS;
        synchronized (hashMap) {
            ENDED_XA_TRANSACTION_CONTEXTS.remove(x);
        }
    }

    @Override
    public boolean isSameRM(XAResource xaResource) throws XAException {
        if (xaResource == null) {
            return false;
        }
        if (!(xaResource instanceof TransactionContext)) {
            return false;
        }
        TransactionContext xar = (TransactionContext)xaResource;
        try {
            return this.getResourceManagerId().equals(xar.getResourceManagerId());
        }
        catch (Throwable e) {
            throw (XAException)new XAException("Could not get resource manager id.").initCause(e);
        }
    }

    @Override
    public Xid[] recover(int flag) throws XAException {
        Xid[] answer;
        LOG.debug("recover({})", (Object)flag);
        if (0 == flag) {
            answer = new XATransactionId[]{};
        } else {
            TransactionInfo info = new TransactionInfo(this.getConnectionId(), null, 5);
            try {
                this.connection.checkClosedOrFailed();
                this.connection.ensureConnectionInfoSent();
                DataArrayResponse receipt = (DataArrayResponse)this.connection.syncSendPacket(info);
                DataStructure[] data = receipt.getData();
                if (data instanceof XATransactionId[]) {
                    answer = (XATransactionId[])data;
                } else {
                    answer = new XATransactionId[data.length];
                    System.arraycopy(data, 0, answer, 0, data.length);
                }
            }
            catch (JMSException e) {
                throw this.toXAException(e);
            }
        }
        LOG.debug("recover({})={}", (Object)flag, (Object)answer);
        return answer;
    }

    @Override
    public int getTransactionTimeout() throws XAException {
        return 0;
    }

    @Override
    public boolean setTransactionTimeout(int seconds) throws XAException {
        return false;
    }

    protected String getResourceManagerId() throws JMSException {
        return this.connection.getResourceManagerId();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setXid(Xid xid) throws XAException {
        try {
            this.connection.checkClosedOrFailed();
            this.connection.ensureConnectionInfoSent();
        }
        catch (JMSException e) {
            this.disassociate();
            throw this.toXAException(e);
        }
        if (xid != null) {
            this.associatedXid = xid;
            this.transactionId = new XATransactionId(xid);
            TransactionInfo info = new TransactionInfo(this.getConnectionId(), this.transactionId, 0);
            try {
                this.connection.asyncSendPacket(info);
                LOG.debug("{} started XA transaction {}", (Object)this, (Object)this.transactionId);
            }
            catch (JMSException e) {
                this.disassociate();
                throw this.toXAException(e);
            }
        }
        if (this.transactionId != null) {
            TransactionInfo info = new TransactionInfo(this.getConnectionId(), this.transactionId, 7);
            try {
                this.connection.syncSendPacket(info);
                LOG.debug("{} ended XA transaction {}", (Object)this, (Object)this.transactionId);
            }
            catch (JMSException e) {
                this.disassociate();
                throw this.toXAException(e);
            }
            HashMap<TransactionId, List<TransactionContext>> hashMap = ENDED_XA_TRANSACTION_CONTEXTS;
            synchronized (hashMap) {
                List<TransactionContext> l = ENDED_XA_TRANSACTION_CONTEXTS.get(this.transactionId);
                if (l == null) {
                    l = new ArrayList<TransactionContext>(3);
                    ENDED_XA_TRANSACTION_CONTEXTS.put(this.transactionId, l);
                }
                if (!l.contains(this)) {
                    l.add(this);
                }
            }
        }
        this.disassociate();
    }

    private void disassociate() {
        this.associatedXid = null;
        this.transactionId = null;
    }

    private XAException toXAException(JMSException e) {
        if (e.getCause() != null && e.getCause() instanceof XAException) {
            XAException original = (XAException)e.getCause();
            XAException xae = new XAException(original.getMessage());
            if (original != null) {
                xae.errorCode = original.errorCode;
            }
            if (original != null && xae != null && xae.errorCode == 0) {
                xae.errorCode = this.parseFromMessageOr(original.getMessage(), -3);
            }
            if (original != null) {
                xae.initCause(original);
            }
            return xae;
        }
        XAException xae = new XAException(e.getMessage());
        xae.errorCode = -7;
        xae.initCause(e);
        return xae;
    }

    private int parseFromMessageOr(String message, int fallbackCode) {
        String marker = xaErrorCodeMarker;
        int index = message.lastIndexOf(xaErrorCodeMarker);
        if (index > -1) {
            try {
                return Integer.parseInt(message.substring(index + xaErrorCodeMarker.length()));
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return fallbackCode;
    }

    public ActiveMQConnection getConnection() {
        return this.connection;
    }

    public ActiveMQConnection setConnection(ActiveMQConnection connection) {
        ActiveMQConnection existing = this.connection;
        this.connection = connection;
        return existing;
    }

    public void cleanup() {
        this.associatedXid = null;
        this.transactionId = null;
    }

    public String toString() {
        return "TransactionContext{transactionId=" + this.transactionId + ",connection=" + this.connection + '}';
    }
}

