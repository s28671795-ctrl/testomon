/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.logging;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.com.sun.corba.se.spi.logging.LogWrapperBase;
import org.jboss.com.sun.corba.se.spi.logging.LogWrapperFactory;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.CORBA.TRANSIENT;
import org.omg.CORBA.UNKNOWN;

public class POASystemException
extends LogWrapperBase {
    private static LogWrapperFactory factory = new LogWrapperFactory(){

        @Override
        public LogWrapperBase create(Logger logger2) {
            return new POASystemException(logger2);
        }
    };
    public static final int SERVANT_MANAGER_ALREADY_SET = 1398080489;
    public static final int DESTROY_DEADLOCK = 1398080490;
    public static final int SERVANT_ORB = 1398080489;
    public static final int BAD_SERVANT = 1398080490;
    public static final int ILLEGAL_FORWARD_REQUEST = 1398080491;
    public static final int BAD_TRANSACTION_CONTEXT = 1398080489;
    public static final int BAD_REPOSITORY_ID = 1398080490;
    public static final int INVOKESETUP = 1398080489;
    public static final int BAD_LOCALREPLYSTATUS = 1398080490;
    public static final int PERSISTENT_SERVERPORT_ERROR = 1398080491;
    public static final int SERVANT_DISPATCH = 1398080492;
    public static final int WRONG_CLIENTSC = 1398080493;
    public static final int CANT_CLONE_TEMPLATE = 1398080494;
    public static final int POACURRENT_UNBALANCED_STACK = 1398080495;
    public static final int POACURRENT_NULL_FIELD = 1398080496;
    public static final int POA_INTERNAL_GET_SERVANT_ERROR = 1398080497;
    public static final int MAKE_FACTORY_NOT_POA = 1398080498;
    public static final int DUPLICATE_ORB_VERSION_SC = 1398080499;
    public static final int PREINVOKE_CLONE_ERROR = 1398080500;
    public static final int PREINVOKE_POA_DESTROYED = 1398080501;
    public static final int PMF_CREATE_RETAIN = 1398080502;
    public static final int PMF_CREATE_NON_RETAIN = 1398080503;
    public static final int POLICY_MEDIATOR_BAD_POLICY_IN_FACTORY = 1398080504;
    public static final int SERVANT_TO_ID_OAA = 1398080505;
    public static final int SERVANT_TO_ID_SAA = 1398080506;
    public static final int SERVANT_TO_ID_WP = 1398080507;
    public static final int CANT_RESOLVE_ROOT_POA = 1398080508;
    public static final int SERVANT_MUST_BE_LOCAL = 1398080509;
    public static final int NO_PROFILES_IN_IOR = 1398080510;
    public static final int AOM_ENTRY_DEC_ZERO = 1398080511;
    public static final int ADD_POA_INACTIVE = 1398080512;
    public static final int ILLEGAL_POA_STATE_TRANS = 1398080513;
    public static final int UNEXPECTED_EXCEPTION = 1398080514;
    public static final int SINGLE_THREAD_NOT_SUPPORTED = 1398080489;
    public static final int METHOD_NOT_IMPLEMENTED = 1398080490;
    public static final int POA_LOOKUP_ERROR = 1398080489;
    public static final int POA_INACTIVE = 1398080490;
    public static final int POA_NO_SERVANT_MANAGER = 1398080491;
    public static final int POA_NO_DEFAULT_SERVANT = 1398080492;
    public static final int POA_SERVANT_NOT_UNIQUE = 1398080493;
    public static final int POA_WRONG_POLICY = 1398080494;
    public static final int FINDPOA_ERROR = 1398080495;
    public static final int POA_SERVANT_ACTIVATOR_LOOKUP_FAILED = 1398080497;
    public static final int POA_BAD_SERVANT_MANAGER = 1398080498;
    public static final int POA_SERVANT_LOCATOR_LOOKUP_FAILED = 1398080499;
    public static final int POA_UNKNOWN_POLICY = 1398080500;
    public static final int POA_NOT_FOUND = 1398080501;
    public static final int SERVANT_LOOKUP = 1398080502;
    public static final int LOCAL_SERVANT_LOOKUP = 1398080503;
    public static final int SERVANT_MANAGER_BAD_TYPE = 1398080504;
    public static final int DEFAULT_POA_NOT_POAIMPL = 1398080505;
    public static final int WRONG_POLICIES_FOR_THIS_OBJECT = 1398080506;
    public static final int THIS_OBJECT_SERVANT_NOT_ACTIVE = 1398080507;
    public static final int THIS_OBJECT_WRONG_POLICY = 1398080508;
    public static final int NO_CONTEXT = 1398080509;
    public static final int INCARNATE_RETURNED_NULL = 1398080510;
    public static final int JTS_INIT_ERROR = 1398080489;
    public static final int PERSISTENT_SERVERID_NOT_SET = 1398080490;
    public static final int PERSISTENT_SERVERPORT_NOT_SET = 1398080491;
    public static final int ORBD_ERROR = 1398080492;
    public static final int BOOTSTRAP_ERROR = 1398080493;
    public static final int POA_DISCARDING = 1398080489;
    public static final int OTSHOOKEXCEPTION = 1398080489;
    public static final int UNKNOWN_SERVER_EXCEPTION = 1398080490;
    public static final int UNKNOWN_SERVERAPP_EXCEPTION = 1398080491;
    public static final int UNKNOWN_LOCALINVOCATION_ERROR = 1398080492;
    public static final int ADAPTER_ACTIVATOR_NONEXISTENT = 1398080489;
    public static final int ADAPTER_ACTIVATOR_FAILED = 1398080490;
    public static final int BAD_SKELETON = 1398080491;
    public static final int NULL_SERVANT = 1398080492;
    public static final int ADAPTER_DESTROYED = 1398080493;

    public POASystemException(Logger logger2) {
        super(logger2);
    }

    public static POASystemException get(ORB orb, String logDomain) {
        POASystemException wrapper = (POASystemException)orb.getLogWrapper(logDomain, "POA", factory);
        return wrapper;
    }

    public static POASystemException get(String logDomain) {
        POASystemException wrapper = (POASystemException)ORB.staticGetLogWrapper(logDomain, "POA", factory);
        return wrapper;
    }

    public BAD_INV_ORDER servantManagerAlreadySet(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1398080489, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.servantManagerAlreadySet", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER servantManagerAlreadySet(CompletionStatus cs) {
        return this.servantManagerAlreadySet(cs, null);
    }

    public BAD_INV_ORDER servantManagerAlreadySet(Throwable t2) {
        return this.servantManagerAlreadySet(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER servantManagerAlreadySet() {
        return this.servantManagerAlreadySet(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER destroyDeadlock(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1398080490, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.destroyDeadlock", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER destroyDeadlock(CompletionStatus cs) {
        return this.destroyDeadlock(cs, null);
    }

    public BAD_INV_ORDER destroyDeadlock(Throwable t2) {
        return this.destroyDeadlock(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER destroyDeadlock() {
        return this.destroyDeadlock(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION servantOrb(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398080489, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.servantOrb", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION servantOrb(CompletionStatus cs) {
        return this.servantOrb(cs, null);
    }

    public BAD_OPERATION servantOrb(Throwable t2) {
        return this.servantOrb(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION servantOrb() {
        return this.servantOrb(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION badServant(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398080490, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.badServant", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION badServant(CompletionStatus cs) {
        return this.badServant(cs, null);
    }

    public BAD_OPERATION badServant(Throwable t2) {
        return this.badServant(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION badServant() {
        return this.badServant(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION illegalForwardRequest(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398080491, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.illegalForwardRequest", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION illegalForwardRequest(CompletionStatus cs) {
        return this.illegalForwardRequest(cs, null);
    }

    public BAD_OPERATION illegalForwardRequest(Throwable t2) {
        return this.illegalForwardRequest(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION illegalForwardRequest() {
        return this.illegalForwardRequest(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM badTransactionContext(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398080489, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.badTransactionContext", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM badTransactionContext(CompletionStatus cs) {
        return this.badTransactionContext(cs, null);
    }

    public BAD_PARAM badTransactionContext(Throwable t2) {
        return this.badTransactionContext(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM badTransactionContext() {
        return this.badTransactionContext(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM badRepositoryId(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398080490, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.badRepositoryId", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM badRepositoryId(CompletionStatus cs) {
        return this.badRepositoryId(cs, null);
    }

    public BAD_PARAM badRepositoryId(Throwable t2) {
        return this.badRepositoryId(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM badRepositoryId() {
        return this.badRepositoryId(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL invokesetup(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080489, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.invokesetup", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invokesetup(CompletionStatus cs) {
        return this.invokesetup(cs, null);
    }

    public INTERNAL invokesetup(Throwable t2) {
        return this.invokesetup(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL invokesetup() {
        return this.invokesetup(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL badLocalreplystatus(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080490, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.badLocalreplystatus", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badLocalreplystatus(CompletionStatus cs) {
        return this.badLocalreplystatus(cs, null);
    }

    public INTERNAL badLocalreplystatus(Throwable t2) {
        return this.badLocalreplystatus(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badLocalreplystatus() {
        return this.badLocalreplystatus(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL persistentServerportError(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080491, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.persistentServerportError", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL persistentServerportError(CompletionStatus cs) {
        return this.persistentServerportError(cs, null);
    }

    public INTERNAL persistentServerportError(Throwable t2) {
        return this.persistentServerportError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL persistentServerportError() {
        return this.persistentServerportError(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL servantDispatch(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080492, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.servantDispatch", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL servantDispatch(CompletionStatus cs) {
        return this.servantDispatch(cs, null);
    }

    public INTERNAL servantDispatch(Throwable t2) {
        return this.servantDispatch(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL servantDispatch() {
        return this.servantDispatch(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL wrongClientsc(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080493, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.wrongClientsc", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL wrongClientsc(CompletionStatus cs) {
        return this.wrongClientsc(cs, null);
    }

    public INTERNAL wrongClientsc(Throwable t2) {
        return this.wrongClientsc(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL wrongClientsc() {
        return this.wrongClientsc(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL cantCloneTemplate(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080494, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.cantCloneTemplate", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL cantCloneTemplate(CompletionStatus cs) {
        return this.cantCloneTemplate(cs, null);
    }

    public INTERNAL cantCloneTemplate(Throwable t2) {
        return this.cantCloneTemplate(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL cantCloneTemplate() {
        return this.cantCloneTemplate(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL poacurrentUnbalancedStack(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080495, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poacurrentUnbalancedStack", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL poacurrentUnbalancedStack(CompletionStatus cs) {
        return this.poacurrentUnbalancedStack(cs, null);
    }

    public INTERNAL poacurrentUnbalancedStack(Throwable t2) {
        return this.poacurrentUnbalancedStack(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL poacurrentUnbalancedStack() {
        return this.poacurrentUnbalancedStack(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL poacurrentNullField(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080496, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poacurrentNullField", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL poacurrentNullField(CompletionStatus cs) {
        return this.poacurrentNullField(cs, null);
    }

    public INTERNAL poacurrentNullField(Throwable t2) {
        return this.poacurrentNullField(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL poacurrentNullField() {
        return this.poacurrentNullField(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL poaInternalGetServantError(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080497, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poaInternalGetServantError", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL poaInternalGetServantError(CompletionStatus cs) {
        return this.poaInternalGetServantError(cs, null);
    }

    public INTERNAL poaInternalGetServantError(Throwable t2) {
        return this.poaInternalGetServantError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL poaInternalGetServantError() {
        return this.poaInternalGetServantError(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL makeFactoryNotPoa(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398080498, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "POA.makeFactoryNotPoa", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL makeFactoryNotPoa(CompletionStatus cs, Object arg0) {
        return this.makeFactoryNotPoa(cs, null, arg0);
    }

    public INTERNAL makeFactoryNotPoa(Throwable t2, Object arg0) {
        return this.makeFactoryNotPoa(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL makeFactoryNotPoa(Object arg0) {
        return this.makeFactoryNotPoa(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL duplicateOrbVersionSc(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080499, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.duplicateOrbVersionSc", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL duplicateOrbVersionSc(CompletionStatus cs) {
        return this.duplicateOrbVersionSc(cs, null);
    }

    public INTERNAL duplicateOrbVersionSc(Throwable t2) {
        return this.duplicateOrbVersionSc(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL duplicateOrbVersionSc() {
        return this.duplicateOrbVersionSc(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL preinvokeCloneError(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080500, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.preinvokeCloneError", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL preinvokeCloneError(CompletionStatus cs) {
        return this.preinvokeCloneError(cs, null);
    }

    public INTERNAL preinvokeCloneError(Throwable t2) {
        return this.preinvokeCloneError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL preinvokeCloneError() {
        return this.preinvokeCloneError(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL preinvokePoaDestroyed(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080501, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.preinvokePoaDestroyed", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL preinvokePoaDestroyed(CompletionStatus cs) {
        return this.preinvokePoaDestroyed(cs, null);
    }

    public INTERNAL preinvokePoaDestroyed(Throwable t2) {
        return this.preinvokePoaDestroyed(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL preinvokePoaDestroyed() {
        return this.preinvokePoaDestroyed(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL pmfCreateRetain(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080502, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.pmfCreateRetain", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL pmfCreateRetain(CompletionStatus cs) {
        return this.pmfCreateRetain(cs, null);
    }

    public INTERNAL pmfCreateRetain(Throwable t2) {
        return this.pmfCreateRetain(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL pmfCreateRetain() {
        return this.pmfCreateRetain(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL pmfCreateNonRetain(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080503, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.pmfCreateNonRetain", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL pmfCreateNonRetain(CompletionStatus cs) {
        return this.pmfCreateNonRetain(cs, null);
    }

    public INTERNAL pmfCreateNonRetain(Throwable t2) {
        return this.pmfCreateNonRetain(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL pmfCreateNonRetain() {
        return this.pmfCreateNonRetain(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL policyMediatorBadPolicyInFactory(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080504, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.policyMediatorBadPolicyInFactory", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL policyMediatorBadPolicyInFactory(CompletionStatus cs) {
        return this.policyMediatorBadPolicyInFactory(cs, null);
    }

    public INTERNAL policyMediatorBadPolicyInFactory(Throwable t2) {
        return this.policyMediatorBadPolicyInFactory(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL policyMediatorBadPolicyInFactory() {
        return this.policyMediatorBadPolicyInFactory(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL servantToIdOaa(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080505, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.servantToIdOaa", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL servantToIdOaa(CompletionStatus cs) {
        return this.servantToIdOaa(cs, null);
    }

    public INTERNAL servantToIdOaa(Throwable t2) {
        return this.servantToIdOaa(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL servantToIdOaa() {
        return this.servantToIdOaa(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL servantToIdSaa(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080506, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.servantToIdSaa", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL servantToIdSaa(CompletionStatus cs) {
        return this.servantToIdSaa(cs, null);
    }

    public INTERNAL servantToIdSaa(Throwable t2) {
        return this.servantToIdSaa(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL servantToIdSaa() {
        return this.servantToIdSaa(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL servantToIdWp(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080507, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.servantToIdWp", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL servantToIdWp(CompletionStatus cs) {
        return this.servantToIdWp(cs, null);
    }

    public INTERNAL servantToIdWp(Throwable t2) {
        return this.servantToIdWp(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL servantToIdWp() {
        return this.servantToIdWp(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL cantResolveRootPoa(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080508, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.cantResolveRootPoa", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL cantResolveRootPoa(CompletionStatus cs) {
        return this.cantResolveRootPoa(cs, null);
    }

    public INTERNAL cantResolveRootPoa(Throwable t2) {
        return this.cantResolveRootPoa(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL cantResolveRootPoa() {
        return this.cantResolveRootPoa(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL servantMustBeLocal(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080509, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.servantMustBeLocal", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL servantMustBeLocal(CompletionStatus cs) {
        return this.servantMustBeLocal(cs, null);
    }

    public INTERNAL servantMustBeLocal(Throwable t2) {
        return this.servantMustBeLocal(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL servantMustBeLocal() {
        return this.servantMustBeLocal(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL noProfilesInIor(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080510, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.noProfilesInIor", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL noProfilesInIor(CompletionStatus cs) {
        return this.noProfilesInIor(cs, null);
    }

    public INTERNAL noProfilesInIor(Throwable t2) {
        return this.noProfilesInIor(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL noProfilesInIor() {
        return this.noProfilesInIor(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL aomEntryDecZero(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080511, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.aomEntryDecZero", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL aomEntryDecZero(CompletionStatus cs) {
        return this.aomEntryDecZero(cs, null);
    }

    public INTERNAL aomEntryDecZero(Throwable t2) {
        return this.aomEntryDecZero(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL aomEntryDecZero() {
        return this.aomEntryDecZero(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL addPoaInactive(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080512, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.addPoaInactive", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL addPoaInactive(CompletionStatus cs) {
        return this.addPoaInactive(cs, null);
    }

    public INTERNAL addPoaInactive(Throwable t2) {
        return this.addPoaInactive(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL addPoaInactive() {
        return this.addPoaInactive(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL illegalPoaStateTrans(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080513, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.illegalPoaStateTrans", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL illegalPoaStateTrans(CompletionStatus cs) {
        return this.illegalPoaStateTrans(cs, null);
    }

    public INTERNAL illegalPoaStateTrans(Throwable t2) {
        return this.illegalPoaStateTrans(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL illegalPoaStateTrans() {
        return this.illegalPoaStateTrans(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL unexpectedException(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398080514, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "POA.unexpectedException", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL unexpectedException(CompletionStatus cs, Object arg0) {
        return this.unexpectedException(cs, null, arg0);
    }

    public INTERNAL unexpectedException(Throwable t2, Object arg0) {
        return this.unexpectedException(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL unexpectedException(Object arg0) {
        return this.unexpectedException(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public NO_IMPLEMENT singleThreadNotSupported(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1398080489, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.singleThreadNotSupported", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT singleThreadNotSupported(CompletionStatus cs) {
        return this.singleThreadNotSupported(cs, null);
    }

    public NO_IMPLEMENT singleThreadNotSupported(Throwable t2) {
        return this.singleThreadNotSupported(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT singleThreadNotSupported() {
        return this.singleThreadNotSupported(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT methodNotImplemented(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1398080490, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.methodNotImplemented", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT methodNotImplemented(CompletionStatus cs) {
        return this.methodNotImplemented(cs, null);
    }

    public NO_IMPLEMENT methodNotImplemented(Throwable t2) {
        return this.methodNotImplemented(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT methodNotImplemented() {
        return this.methodNotImplemented(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER poaLookupError(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080489, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poaLookupError", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER poaLookupError(CompletionStatus cs) {
        return this.poaLookupError(cs, null);
    }

    public OBJ_ADAPTER poaLookupError(Throwable t2) {
        return this.poaLookupError(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER poaLookupError() {
        return this.poaLookupError(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER poaInactive(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080490, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "POA.poaInactive", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER poaInactive(CompletionStatus cs) {
        return this.poaInactive(cs, null);
    }

    public OBJ_ADAPTER poaInactive(Throwable t2) {
        return this.poaInactive(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER poaInactive() {
        return this.poaInactive(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER poaNoServantManager(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080491, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poaNoServantManager", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER poaNoServantManager(CompletionStatus cs) {
        return this.poaNoServantManager(cs, null);
    }

    public OBJ_ADAPTER poaNoServantManager(Throwable t2) {
        return this.poaNoServantManager(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER poaNoServantManager() {
        return this.poaNoServantManager(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER poaNoDefaultServant(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080492, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poaNoDefaultServant", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER poaNoDefaultServant(CompletionStatus cs) {
        return this.poaNoDefaultServant(cs, null);
    }

    public OBJ_ADAPTER poaNoDefaultServant(Throwable t2) {
        return this.poaNoDefaultServant(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER poaNoDefaultServant() {
        return this.poaNoDefaultServant(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER poaServantNotUnique(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080493, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poaServantNotUnique", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER poaServantNotUnique(CompletionStatus cs) {
        return this.poaServantNotUnique(cs, null);
    }

    public OBJ_ADAPTER poaServantNotUnique(Throwable t2) {
        return this.poaServantNotUnique(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER poaServantNotUnique() {
        return this.poaServantNotUnique(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER poaWrongPolicy(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080494, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poaWrongPolicy", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER poaWrongPolicy(CompletionStatus cs) {
        return this.poaWrongPolicy(cs, null);
    }

    public OBJ_ADAPTER poaWrongPolicy(Throwable t2) {
        return this.poaWrongPolicy(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER poaWrongPolicy() {
        return this.poaWrongPolicy(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER findpoaError(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080495, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.findpoaError", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER findpoaError(CompletionStatus cs) {
        return this.findpoaError(cs, null);
    }

    public OBJ_ADAPTER findpoaError(Throwable t2) {
        return this.findpoaError(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER findpoaError() {
        return this.findpoaError(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER poaServantActivatorLookupFailed(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080497, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poaServantActivatorLookupFailed", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER poaServantActivatorLookupFailed(CompletionStatus cs) {
        return this.poaServantActivatorLookupFailed(cs, null);
    }

    public OBJ_ADAPTER poaServantActivatorLookupFailed(Throwable t2) {
        return this.poaServantActivatorLookupFailed(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER poaServantActivatorLookupFailed() {
        return this.poaServantActivatorLookupFailed(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER poaBadServantManager(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080498, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poaBadServantManager", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER poaBadServantManager(CompletionStatus cs) {
        return this.poaBadServantManager(cs, null);
    }

    public OBJ_ADAPTER poaBadServantManager(Throwable t2) {
        return this.poaBadServantManager(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER poaBadServantManager() {
        return this.poaBadServantManager(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER poaServantLocatorLookupFailed(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080499, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poaServantLocatorLookupFailed", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER poaServantLocatorLookupFailed(CompletionStatus cs) {
        return this.poaServantLocatorLookupFailed(cs, null);
    }

    public OBJ_ADAPTER poaServantLocatorLookupFailed(Throwable t2) {
        return this.poaServantLocatorLookupFailed(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER poaServantLocatorLookupFailed() {
        return this.poaServantLocatorLookupFailed(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER poaUnknownPolicy(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080500, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poaUnknownPolicy", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER poaUnknownPolicy(CompletionStatus cs) {
        return this.poaUnknownPolicy(cs, null);
    }

    public OBJ_ADAPTER poaUnknownPolicy(Throwable t2) {
        return this.poaUnknownPolicy(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER poaUnknownPolicy() {
        return this.poaUnknownPolicy(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER poaNotFound(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080501, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.poaNotFound", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER poaNotFound(CompletionStatus cs) {
        return this.poaNotFound(cs, null);
    }

    public OBJ_ADAPTER poaNotFound(Throwable t2) {
        return this.poaNotFound(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER poaNotFound() {
        return this.poaNotFound(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER servantLookup(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080502, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.servantLookup", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER servantLookup(CompletionStatus cs) {
        return this.servantLookup(cs, null);
    }

    public OBJ_ADAPTER servantLookup(Throwable t2) {
        return this.servantLookup(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER servantLookup() {
        return this.servantLookup(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER localServantLookup(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080503, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.localServantLookup", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER localServantLookup(CompletionStatus cs) {
        return this.localServantLookup(cs, null);
    }

    public OBJ_ADAPTER localServantLookup(Throwable t2) {
        return this.localServantLookup(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER localServantLookup() {
        return this.localServantLookup(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER servantManagerBadType(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080504, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.servantManagerBadType", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER servantManagerBadType(CompletionStatus cs) {
        return this.servantManagerBadType(cs, null);
    }

    public OBJ_ADAPTER servantManagerBadType(Throwable t2) {
        return this.servantManagerBadType(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER servantManagerBadType() {
        return this.servantManagerBadType(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER defaultPoaNotPoaimpl(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080505, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.defaultPoaNotPoaimpl", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER defaultPoaNotPoaimpl(CompletionStatus cs) {
        return this.defaultPoaNotPoaimpl(cs, null);
    }

    public OBJ_ADAPTER defaultPoaNotPoaimpl(Throwable t2) {
        return this.defaultPoaNotPoaimpl(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER defaultPoaNotPoaimpl() {
        return this.defaultPoaNotPoaimpl(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER wrongPoliciesForThisObject(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080506, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.wrongPoliciesForThisObject", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER wrongPoliciesForThisObject(CompletionStatus cs) {
        return this.wrongPoliciesForThisObject(cs, null);
    }

    public OBJ_ADAPTER wrongPoliciesForThisObject(Throwable t2) {
        return this.wrongPoliciesForThisObject(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER wrongPoliciesForThisObject() {
        return this.wrongPoliciesForThisObject(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER thisObjectServantNotActive(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080507, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.thisObjectServantNotActive", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER thisObjectServantNotActive(CompletionStatus cs) {
        return this.thisObjectServantNotActive(cs, null);
    }

    public OBJ_ADAPTER thisObjectServantNotActive(Throwable t2) {
        return this.thisObjectServantNotActive(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER thisObjectServantNotActive() {
        return this.thisObjectServantNotActive(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER thisObjectWrongPolicy(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080508, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.thisObjectWrongPolicy", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER thisObjectWrongPolicy(CompletionStatus cs) {
        return this.thisObjectWrongPolicy(cs, null);
    }

    public OBJ_ADAPTER thisObjectWrongPolicy(Throwable t2) {
        return this.thisObjectWrongPolicy(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER thisObjectWrongPolicy() {
        return this.thisObjectWrongPolicy(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER noContext(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080509, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "POA.noContext", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER noContext(CompletionStatus cs) {
        return this.noContext(cs, null);
    }

    public OBJ_ADAPTER noContext(Throwable t2) {
        return this.noContext(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER noContext() {
        return this.noContext(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER incarnateReturnedNull(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398080510, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.incarnateReturnedNull", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER incarnateReturnedNull(CompletionStatus cs) {
        return this.incarnateReturnedNull(cs, null);
    }

    public OBJ_ADAPTER incarnateReturnedNull(Throwable t2) {
        return this.incarnateReturnedNull(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER incarnateReturnedNull() {
        return this.incarnateReturnedNull(CompletionStatus.COMPLETED_NO, null);
    }

    public INITIALIZE jtsInitError(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1398080489, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.jtsInitError", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INITIALIZE jtsInitError(CompletionStatus cs) {
        return this.jtsInitError(cs, null);
    }

    public INITIALIZE jtsInitError(Throwable t2) {
        return this.jtsInitError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INITIALIZE jtsInitError() {
        return this.jtsInitError(CompletionStatus.COMPLETED_NO, null);
    }

    public INITIALIZE persistentServeridNotSet(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1398080490, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.persistentServeridNotSet", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INITIALIZE persistentServeridNotSet(CompletionStatus cs) {
        return this.persistentServeridNotSet(cs, null);
    }

    public INITIALIZE persistentServeridNotSet(Throwable t2) {
        return this.persistentServeridNotSet(CompletionStatus.COMPLETED_NO, t2);
    }

    public INITIALIZE persistentServeridNotSet() {
        return this.persistentServeridNotSet(CompletionStatus.COMPLETED_NO, null);
    }

    public INITIALIZE persistentServerportNotSet(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1398080491, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.persistentServerportNotSet", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INITIALIZE persistentServerportNotSet(CompletionStatus cs) {
        return this.persistentServerportNotSet(cs, null);
    }

    public INITIALIZE persistentServerportNotSet(Throwable t2) {
        return this.persistentServerportNotSet(CompletionStatus.COMPLETED_NO, t2);
    }

    public INITIALIZE persistentServerportNotSet() {
        return this.persistentServerportNotSet(CompletionStatus.COMPLETED_NO, null);
    }

    public INITIALIZE orbdError(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1398080492, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.orbdError", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INITIALIZE orbdError(CompletionStatus cs) {
        return this.orbdError(cs, null);
    }

    public INITIALIZE orbdError(Throwable t2) {
        return this.orbdError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INITIALIZE orbdError() {
        return this.orbdError(CompletionStatus.COMPLETED_NO, null);
    }

    public INITIALIZE bootstrapError(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1398080493, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.bootstrapError", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public INITIALIZE bootstrapError(CompletionStatus cs) {
        return this.bootstrapError(cs, null);
    }

    public INITIALIZE bootstrapError(Throwable t2) {
        return this.bootstrapError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INITIALIZE bootstrapError() {
        return this.bootstrapError(CompletionStatus.COMPLETED_NO, null);
    }

    public TRANSIENT poaDiscarding(CompletionStatus cs, Throwable t2) {
        TRANSIENT exc = new TRANSIENT(1398080489, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "POA.poaDiscarding", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public TRANSIENT poaDiscarding(CompletionStatus cs) {
        return this.poaDiscarding(cs, null);
    }

    public TRANSIENT poaDiscarding(Throwable t2) {
        return this.poaDiscarding(CompletionStatus.COMPLETED_NO, t2);
    }

    public TRANSIENT poaDiscarding() {
        return this.poaDiscarding(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN otshookexception(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398080489, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.otshookexception", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN otshookexception(CompletionStatus cs) {
        return this.otshookexception(cs, null);
    }

    public UNKNOWN otshookexception(Throwable t2) {
        return this.otshookexception(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN otshookexception() {
        return this.otshookexception(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN unknownServerException(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398080490, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.unknownServerException", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN unknownServerException(CompletionStatus cs) {
        return this.unknownServerException(cs, null);
    }

    public UNKNOWN unknownServerException(Throwable t2) {
        return this.unknownServerException(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN unknownServerException() {
        return this.unknownServerException(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN unknownServerappException(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398080491, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.unknownServerappException", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN unknownServerappException(CompletionStatus cs) {
        return this.unknownServerappException(cs, null);
    }

    public UNKNOWN unknownServerappException(Throwable t2) {
        return this.unknownServerappException(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN unknownServerappException() {
        return this.unknownServerappException(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN unknownLocalinvocationError(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398080492, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.unknownLocalinvocationError", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN unknownLocalinvocationError(CompletionStatus cs) {
        return this.unknownLocalinvocationError(cs, null);
    }

    public UNKNOWN unknownLocalinvocationError(Throwable t2) {
        return this.unknownLocalinvocationError(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN unknownLocalinvocationError() {
        return this.unknownLocalinvocationError(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST adapterActivatorNonexistent(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398080489, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.adapterActivatorNonexistent", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST adapterActivatorNonexistent(CompletionStatus cs) {
        return this.adapterActivatorNonexistent(cs, null);
    }

    public OBJECT_NOT_EXIST adapterActivatorNonexistent(Throwable t2) {
        return this.adapterActivatorNonexistent(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST adapterActivatorNonexistent() {
        return this.adapterActivatorNonexistent(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST adapterActivatorFailed(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398080490, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.adapterActivatorFailed", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST adapterActivatorFailed(CompletionStatus cs) {
        return this.adapterActivatorFailed(cs, null);
    }

    public OBJECT_NOT_EXIST adapterActivatorFailed(Throwable t2) {
        return this.adapterActivatorFailed(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST adapterActivatorFailed() {
        return this.adapterActivatorFailed(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST badSkeleton(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398080491, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.badSkeleton", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST badSkeleton(CompletionStatus cs) {
        return this.badSkeleton(cs, null);
    }

    public OBJECT_NOT_EXIST badSkeleton(Throwable t2) {
        return this.badSkeleton(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST badSkeleton() {
        return this.badSkeleton(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST nullServant(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398080492, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "POA.nullServant", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST nullServant(CompletionStatus cs) {
        return this.nullServant(cs, null);
    }

    public OBJECT_NOT_EXIST nullServant(Throwable t2) {
        return this.nullServant(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST nullServant() {
        return this.nullServant(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST adapterDestroyed(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398080493, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "POA.adapterDestroyed", parameters, POASystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST adapterDestroyed(CompletionStatus cs) {
        return this.adapterDestroyed(cs, null);
    }

    public OBJECT_NOT_EXIST adapterDestroyed(Throwable t2) {
        return this.adapterDestroyed(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST adapterDestroyed() {
        return this.adapterDestroyed(CompletionStatus.COMPLETED_NO, null);
    }
}

