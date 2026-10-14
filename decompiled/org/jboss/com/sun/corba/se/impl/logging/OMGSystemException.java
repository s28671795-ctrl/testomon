/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.logging;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.com.sun.corba.se.spi.logging.LogWrapperBase;
import org.jboss.com.sun.corba.se.spi.logging.LogWrapperFactory;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.BAD_CONTEXT;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.DATA_CONVERSION;
import org.omg.CORBA.IMP_LIMIT;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.INTF_REPOS;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.INV_POLICY;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NO_RESOURCES;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import org.omg.CORBA.TRANSIENT;
import org.omg.CORBA.UNKNOWN;

public class OMGSystemException
extends LogWrapperBase {
    private static LogWrapperFactory factory = new LogWrapperFactory(){

        @Override
        public LogWrapperBase create(Logger logger2) {
            return new OMGSystemException(logger2);
        }
    };
    public static final int IDL_CONTEXT_NOT_FOUND = 1330446337;
    public static final int NO_MATCHING_IDL_CONTEXT = 1330446338;
    public static final int DEP_PREVENT_DESTRUCTION = 1330446337;
    public static final int DESTROY_INDESTRUCTIBLE = 1330446338;
    public static final int SHUTDOWN_WAIT_FOR_COMPLETION_DEADLOCK = 1330446339;
    public static final int BAD_OPERATION_AFTER_SHUTDOWN = 1330446340;
    public static final int BAD_INVOKE = 1330446341;
    public static final int BAD_SET_SERVANT_MANAGER = 1330446342;
    public static final int BAD_ARGUMENTS_CALL = 1330446343;
    public static final int BAD_CTX_CALL = 1330446344;
    public static final int BAD_RESULT_CALL = 1330446345;
    public static final int BAD_SEND = 1330446346;
    public static final int BAD_POLL_BEFORE = 1330446347;
    public static final int BAD_POLL_AFTER = 1330446348;
    public static final int BAD_POLL_SYNC = 1330446349;
    public static final int INVALID_PI_CALL1 = 1330446350;
    public static final int INVALID_PI_CALL2 = 1330446350;
    public static final int INVALID_PI_CALL3 = 1330446350;
    public static final int INVALID_PI_CALL4 = 1330446350;
    public static final int SERVICE_CONTEXT_ADD_FAILED = 1330446351;
    public static final int POLICY_FACTORY_REG_FAILED = 1330446352;
    public static final int CREATE_POA_DESTROY = 1330446353;
    public static final int PRIORITY_REASSIGN = 1330446354;
    public static final int XA_START_OUTSIZE = 1330446355;
    public static final int XA_START_PROTO = 1330446356;
    public static final int BAD_SERVANT_MANAGER_TYPE = 1330446337;
    public static final int OPERATION_UNKNOWN_TO_TARGET = 1330446338;
    public static final int UNABLE_REGISTER_VALUE_FACTORY = 1330446337;
    public static final int RID_ALREADY_DEFINED = 1330446338;
    public static final int NAME_USED_IFR = 1330446339;
    public static final int TARGET_NOT_CONTAINER = 1330446340;
    public static final int NAME_CLASH = 1330446341;
    public static final int NOT_SERIALIZABLE = 1330446342;
    public static final int SO_BAD_SCHEME_NAME = 1330446343;
    public static final int SO_BAD_ADDRESS = 1330446344;
    public static final int SO_BAD_SCHEMA_SPECIFIC = 1330446345;
    public static final int SO_NON_SPECIFIC = 1330446346;
    public static final int IR_DERIVE_ABS_INT_BASE = 1330446347;
    public static final int IR_VALUE_SUPPORT = 1330446348;
    public static final int INCOMPLETE_TYPECODE = 1330446349;
    public static final int INVALID_OBJECT_ID = 1330446350;
    public static final int TYPECODE_BAD_NAME = 1330446351;
    public static final int TYPECODE_BAD_REPID = 1330446352;
    public static final int TYPECODE_INV_MEMBER = 1330446353;
    public static final int TC_UNION_DUP_LABEL = 1330446354;
    public static final int TC_UNION_INCOMPATIBLE = 1330446355;
    public static final int TC_UNION_BAD_DISC = 1330446356;
    public static final int SET_EXCEPTION_BAD_ANY = 1330446357;
    public static final int SET_EXCEPTION_UNLISTED = 1330446358;
    public static final int NO_CLIENT_WCHAR_CODESET_CTX = 1330446359;
    public static final int ILLEGAL_SERVICE_CONTEXT = 1330446360;
    public static final int ENUM_OUT_OF_RANGE = 1330446361;
    public static final int INVALID_SERVICE_CONTEXT_ID = 1330446362;
    public static final int RIR_WITH_NULL_OBJECT = 1330446363;
    public static final int INVALID_COMPONENT_ID = 1330446364;
    public static final int INVALID_PROFILE_ID = 1330446365;
    public static final int POLICY_TYPE_DUPLICATE = 1330446366;
    public static final int BAD_ONEWAY_DEFINITION = 1330446367;
    public static final int DII_FOR_IMPLICIT_OPERATION = 1330446368;
    public static final int XA_CALL_INVAL = 1330446369;
    public static final int UNION_BAD_DISCRIMINATOR = 1330446370;
    public static final int CTX_ILLEGAL_PROPERTY_NAME = 1330446371;
    public static final int CTX_ILLEGAL_SEARCH_STRING = 1330446372;
    public static final int CTX_ILLEGAL_NAME = 1330446373;
    public static final int CTX_NON_EMPTY = 1330446374;
    public static final int INVALID_STREAM_FORMAT_VERSION = 1330446375;
    public static final int NOT_A_VALUEOUTPUTSTREAM = 1330446376;
    public static final int NOT_A_VALUEINPUTSTREAM = 1330446377;
    public static final int MARSHALL_INCOMPLETE_TYPECODE = 1330446337;
    public static final int BAD_MEMBER_TYPECODE = 1330446338;
    public static final int ILLEGAL_PARAMETER = 1330446339;
    public static final int CHAR_NOT_IN_CODESET = 1330446337;
    public static final int PRIORITY_MAP_FAILRE = 1330446338;
    public static final int NO_USABLE_PROFILE = 1330446337;
    public static final int PRIORITY_RANGE_RESTRICT = 1330446337;
    public static final int NO_SERVER_WCHAR_CODESET_CMP = 1330446337;
    public static final int CODESET_COMPONENT_REQUIRED = 1330446338;
    public static final int IOR_POLICY_RECONCILE_ERROR = 1330446337;
    public static final int POLICY_UNKNOWN = 1330446338;
    public static final int NO_POLICY_FACTORY = 1330446339;
    public static final int XA_RMERR = 1330446337;
    public static final int XA_RMFAIL = 1330446338;
    public static final int NO_IR = 1330446337;
    public static final int NO_INTERFACE_IN_IR = 1330446338;
    public static final int UNABLE_LOCATE_VALUE_FACTORY = 1330446337;
    public static final int SET_RESULT_BEFORE_CTX = 1330446338;
    public static final int BAD_NVLIST = 1330446339;
    public static final int NOT_AN_OBJECT_IMPL = 1330446340;
    public static final int WCHAR_BAD_GIOP_VERSION_SENT = 1330446341;
    public static final int WCHAR_BAD_GIOP_VERSION_RETURNED = 1330446342;
    public static final int UNSUPPORTED_FORMAT_VERSION = 1330446343;
    public static final int RMIIIOP_OPTIONAL_DATA_INCOMPATIBLE1 = 1330446344;
    public static final int RMIIIOP_OPTIONAL_DATA_INCOMPATIBLE2 = 1330446344;
    public static final int RMIIIOP_OPTIONAL_DATA_INCOMPATIBLE3 = 1330446344;
    public static final int MISSING_LOCAL_VALUE_IMPL = 1330446337;
    public static final int INCOMPATIBLE_VALUE_IMPL = 1330446338;
    public static final int NO_USABLE_PROFILE_2 = 1330446339;
    public static final int DII_LOCAL_OBJECT = 1330446340;
    public static final int BIO_RESET = 1330446341;
    public static final int BIO_META_NOT_AVAILABLE = 1330446342;
    public static final int BIO_GENOMIC_NO_ITERATOR = 1330446343;
    public static final int PI_OPERATION_NOT_SUPPORTED1 = 1330446337;
    public static final int PI_OPERATION_NOT_SUPPORTED2 = 1330446337;
    public static final int PI_OPERATION_NOT_SUPPORTED3 = 1330446337;
    public static final int PI_OPERATION_NOT_SUPPORTED4 = 1330446337;
    public static final int PI_OPERATION_NOT_SUPPORTED5 = 1330446337;
    public static final int PI_OPERATION_NOT_SUPPORTED6 = 1330446337;
    public static final int PI_OPERATION_NOT_SUPPORTED7 = 1330446337;
    public static final int PI_OPERATION_NOT_SUPPORTED8 = 1330446337;
    public static final int NO_CONNECTION_PRIORITY = 1330446338;
    public static final int XA_RB = 1330446337;
    public static final int XA_NOTA = 1330446338;
    public static final int XA_END_TRUE_ROLLBACK_DEFERRED = 1330446339;
    public static final int POA_REQUEST_DISCARD = 1330446337;
    public static final int NO_USABLE_PROFILE_3 = 1330446338;
    public static final int REQUEST_CANCELLED = 1330446339;
    public static final int POA_DESTROYED = 1330446340;
    public static final int UNREGISTERED_VALUE_AS_OBJREF = 1330446337;
    public static final int NO_OBJECT_ADAPTOR = 1330446338;
    public static final int BIO_NOT_AVAILABLE = 1330446339;
    public static final int OBJECT_ADAPTER_INACTIVE = 1330446340;
    public static final int ADAPTER_ACTIVATOR_EXCEPTION = 1330446337;
    public static final int BAD_SERVANT_TYPE = 1330446338;
    public static final int NO_DEFAULT_SERVANT = 1330446339;
    public static final int NO_SERVANT_MANAGER = 1330446340;
    public static final int BAD_POLICY_INCARNATE = 1330446341;
    public static final int PI_EXC_COMP_ESTABLISHED = 1330446342;
    public static final int NULL_SERVANT_RETURNED = 1330446343;
    public static final int UNKNOWN_USER_EXCEPTION = 1330446337;
    public static final int UNSUPPORTED_SYSTEM_EXCEPTION = 1330446338;
    public static final int PI_UNKNOWN_USER_EXCEPTION = 1330446339;

    public OMGSystemException(Logger logger2) {
        super(logger2);
    }

    public static OMGSystemException get(ORB orb, String logDomain) {
        OMGSystemException wrapper = (OMGSystemException)orb.getLogWrapper(logDomain, "OMG", factory);
        return wrapper;
    }

    public static OMGSystemException get(String logDomain) {
        OMGSystemException wrapper = (OMGSystemException)ORB.staticGetLogWrapper(logDomain, "OMG", factory);
        return wrapper;
    }

    public BAD_CONTEXT idlContextNotFound(CompletionStatus cs, Throwable t2) {
        BAD_CONTEXT exc = new BAD_CONTEXT(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.idlContextNotFound", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_CONTEXT idlContextNotFound(CompletionStatus cs) {
        return this.idlContextNotFound(cs, null);
    }

    public BAD_CONTEXT idlContextNotFound(Throwable t2) {
        return this.idlContextNotFound(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_CONTEXT idlContextNotFound() {
        return this.idlContextNotFound(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_CONTEXT noMatchingIdlContext(CompletionStatus cs, Throwable t2) {
        BAD_CONTEXT exc = new BAD_CONTEXT(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noMatchingIdlContext", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_CONTEXT noMatchingIdlContext(CompletionStatus cs) {
        return this.noMatchingIdlContext(cs, null);
    }

    public BAD_CONTEXT noMatchingIdlContext(Throwable t2) {
        return this.noMatchingIdlContext(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_CONTEXT noMatchingIdlContext() {
        return this.noMatchingIdlContext(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER depPreventDestruction(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.depPreventDestruction", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER depPreventDestruction(CompletionStatus cs) {
        return this.depPreventDestruction(cs, null);
    }

    public BAD_INV_ORDER depPreventDestruction(Throwable t2) {
        return this.depPreventDestruction(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER depPreventDestruction() {
        return this.depPreventDestruction(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER destroyIndestructible(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.destroyIndestructible", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER destroyIndestructible(CompletionStatus cs) {
        return this.destroyIndestructible(cs, null);
    }

    public BAD_INV_ORDER destroyIndestructible(Throwable t2) {
        return this.destroyIndestructible(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER destroyIndestructible() {
        return this.destroyIndestructible(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER shutdownWaitForCompletionDeadlock(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446339, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.shutdownWaitForCompletionDeadlock", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER shutdownWaitForCompletionDeadlock(CompletionStatus cs) {
        return this.shutdownWaitForCompletionDeadlock(cs, null);
    }

    public BAD_INV_ORDER shutdownWaitForCompletionDeadlock(Throwable t2) {
        return this.shutdownWaitForCompletionDeadlock(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER shutdownWaitForCompletionDeadlock() {
        return this.shutdownWaitForCompletionDeadlock(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER badOperationAfterShutdown(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446340, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badOperationAfterShutdown", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER badOperationAfterShutdown(CompletionStatus cs) {
        return this.badOperationAfterShutdown(cs, null);
    }

    public BAD_INV_ORDER badOperationAfterShutdown(Throwable t2) {
        return this.badOperationAfterShutdown(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER badOperationAfterShutdown() {
        return this.badOperationAfterShutdown(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER badInvoke(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446341, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badInvoke", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER badInvoke(CompletionStatus cs) {
        return this.badInvoke(cs, null);
    }

    public BAD_INV_ORDER badInvoke(Throwable t2) {
        return this.badInvoke(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER badInvoke() {
        return this.badInvoke(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER badSetServantManager(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446342, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badSetServantManager", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER badSetServantManager(CompletionStatus cs) {
        return this.badSetServantManager(cs, null);
    }

    public BAD_INV_ORDER badSetServantManager(Throwable t2) {
        return this.badSetServantManager(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER badSetServantManager() {
        return this.badSetServantManager(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER badArgumentsCall(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446343, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badArgumentsCall", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER badArgumentsCall(CompletionStatus cs) {
        return this.badArgumentsCall(cs, null);
    }

    public BAD_INV_ORDER badArgumentsCall(Throwable t2) {
        return this.badArgumentsCall(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER badArgumentsCall() {
        return this.badArgumentsCall(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER badCtxCall(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446344, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badCtxCall", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER badCtxCall(CompletionStatus cs) {
        return this.badCtxCall(cs, null);
    }

    public BAD_INV_ORDER badCtxCall(Throwable t2) {
        return this.badCtxCall(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER badCtxCall() {
        return this.badCtxCall(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER badResultCall(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446345, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badResultCall", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER badResultCall(CompletionStatus cs) {
        return this.badResultCall(cs, null);
    }

    public BAD_INV_ORDER badResultCall(Throwable t2) {
        return this.badResultCall(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER badResultCall() {
        return this.badResultCall(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER badSend(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446346, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badSend", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER badSend(CompletionStatus cs) {
        return this.badSend(cs, null);
    }

    public BAD_INV_ORDER badSend(Throwable t2) {
        return this.badSend(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER badSend() {
        return this.badSend(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER badPollBefore(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446347, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badPollBefore", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER badPollBefore(CompletionStatus cs) {
        return this.badPollBefore(cs, null);
    }

    public BAD_INV_ORDER badPollBefore(Throwable t2) {
        return this.badPollBefore(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER badPollBefore() {
        return this.badPollBefore(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER badPollAfter(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446348, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badPollAfter", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER badPollAfter(CompletionStatus cs) {
        return this.badPollAfter(cs, null);
    }

    public BAD_INV_ORDER badPollAfter(Throwable t2) {
        return this.badPollAfter(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER badPollAfter() {
        return this.badPollAfter(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER badPollSync(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446349, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badPollSync", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER badPollSync(CompletionStatus cs) {
        return this.badPollSync(cs, null);
    }

    public BAD_INV_ORDER badPollSync(Throwable t2) {
        return this.badPollSync(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER badPollSync() {
        return this.badPollSync(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER invalidPiCall1(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446350, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.invalidPiCall1", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER invalidPiCall1(CompletionStatus cs) {
        return this.invalidPiCall1(cs, null);
    }

    public BAD_INV_ORDER invalidPiCall1(Throwable t2) {
        return this.invalidPiCall1(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER invalidPiCall1() {
        return this.invalidPiCall1(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER invalidPiCall2(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446350, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.invalidPiCall2", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER invalidPiCall2(CompletionStatus cs) {
        return this.invalidPiCall2(cs, null);
    }

    public BAD_INV_ORDER invalidPiCall2(Throwable t2) {
        return this.invalidPiCall2(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER invalidPiCall2() {
        return this.invalidPiCall2(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER invalidPiCall3(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446350, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.invalidPiCall3", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER invalidPiCall3(CompletionStatus cs) {
        return this.invalidPiCall3(cs, null);
    }

    public BAD_INV_ORDER invalidPiCall3(Throwable t2) {
        return this.invalidPiCall3(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER invalidPiCall3() {
        return this.invalidPiCall3(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER invalidPiCall4(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446350, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.invalidPiCall4", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER invalidPiCall4(CompletionStatus cs) {
        return this.invalidPiCall4(cs, null);
    }

    public BAD_INV_ORDER invalidPiCall4(Throwable t2) {
        return this.invalidPiCall4(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER invalidPiCall4() {
        return this.invalidPiCall4(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER serviceContextAddFailed(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446351, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.FINE, "OMG.serviceContextAddFailed", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER serviceContextAddFailed(CompletionStatus cs, Object arg0) {
        return this.serviceContextAddFailed(cs, null, arg0);
    }

    public BAD_INV_ORDER serviceContextAddFailed(Throwable t2, Object arg0) {
        return this.serviceContextAddFailed(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_INV_ORDER serviceContextAddFailed(Object arg0) {
        return this.serviceContextAddFailed(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_INV_ORDER policyFactoryRegFailed(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446352, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "OMG.policyFactoryRegFailed", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER policyFactoryRegFailed(CompletionStatus cs, Object arg0) {
        return this.policyFactoryRegFailed(cs, null, arg0);
    }

    public BAD_INV_ORDER policyFactoryRegFailed(Throwable t2, Object arg0) {
        return this.policyFactoryRegFailed(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_INV_ORDER policyFactoryRegFailed(Object arg0) {
        return this.policyFactoryRegFailed(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_INV_ORDER createPoaDestroy(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446353, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.createPoaDestroy", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER createPoaDestroy(CompletionStatus cs) {
        return this.createPoaDestroy(cs, null);
    }

    public BAD_INV_ORDER createPoaDestroy(Throwable t2) {
        return this.createPoaDestroy(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER createPoaDestroy() {
        return this.createPoaDestroy(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER priorityReassign(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446354, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.priorityReassign", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER priorityReassign(CompletionStatus cs) {
        return this.priorityReassign(cs, null);
    }

    public BAD_INV_ORDER priorityReassign(Throwable t2) {
        return this.priorityReassign(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER priorityReassign() {
        return this.priorityReassign(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER xaStartOutsize(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446355, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.xaStartOutsize", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER xaStartOutsize(CompletionStatus cs) {
        return this.xaStartOutsize(cs, null);
    }

    public BAD_INV_ORDER xaStartOutsize(Throwable t2) {
        return this.xaStartOutsize(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER xaStartOutsize() {
        return this.xaStartOutsize(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER xaStartProto(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1330446356, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.xaStartProto", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER xaStartProto(CompletionStatus cs) {
        return this.xaStartProto(cs, null);
    }

    public BAD_INV_ORDER xaStartProto(Throwable t2) {
        return this.xaStartProto(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER xaStartProto() {
        return this.xaStartProto(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION badServantManagerType(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badServantManagerType", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION badServantManagerType(CompletionStatus cs) {
        return this.badServantManagerType(cs, null);
    }

    public BAD_OPERATION badServantManagerType(Throwable t2) {
        return this.badServantManagerType(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION badServantManagerType() {
        return this.badServantManagerType(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION operationUnknownToTarget(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.operationUnknownToTarget", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION operationUnknownToTarget(CompletionStatus cs) {
        return this.operationUnknownToTarget(cs, null);
    }

    public BAD_OPERATION operationUnknownToTarget(Throwable t2) {
        return this.operationUnknownToTarget(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION operationUnknownToTarget() {
        return this.operationUnknownToTarget(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM unableRegisterValueFactory(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.unableRegisterValueFactory", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM unableRegisterValueFactory(CompletionStatus cs) {
        return this.unableRegisterValueFactory(cs, null);
    }

    public BAD_PARAM unableRegisterValueFactory(Throwable t2) {
        return this.unableRegisterValueFactory(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM unableRegisterValueFactory() {
        return this.unableRegisterValueFactory(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM ridAlreadyDefined(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.ridAlreadyDefined", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM ridAlreadyDefined(CompletionStatus cs) {
        return this.ridAlreadyDefined(cs, null);
    }

    public BAD_PARAM ridAlreadyDefined(Throwable t2) {
        return this.ridAlreadyDefined(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM ridAlreadyDefined() {
        return this.ridAlreadyDefined(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM nameUsedIfr(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446339, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.nameUsedIfr", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM nameUsedIfr(CompletionStatus cs) {
        return this.nameUsedIfr(cs, null);
    }

    public BAD_PARAM nameUsedIfr(Throwable t2) {
        return this.nameUsedIfr(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM nameUsedIfr() {
        return this.nameUsedIfr(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM targetNotContainer(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446340, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.targetNotContainer", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM targetNotContainer(CompletionStatus cs) {
        return this.targetNotContainer(cs, null);
    }

    public BAD_PARAM targetNotContainer(Throwable t2) {
        return this.targetNotContainer(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM targetNotContainer() {
        return this.targetNotContainer(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM nameClash(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446341, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.nameClash", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM nameClash(CompletionStatus cs) {
        return this.nameClash(cs, null);
    }

    public BAD_PARAM nameClash(Throwable t2) {
        return this.nameClash(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM nameClash() {
        return this.nameClash(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM notSerializable(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_PARAM exc = new BAD_PARAM(1330446342, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "OMG.notSerializable", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM notSerializable(CompletionStatus cs, Object arg0) {
        return this.notSerializable(cs, null, arg0);
    }

    public BAD_PARAM notSerializable(Throwable t2, Object arg0) {
        return this.notSerializable(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_PARAM notSerializable(Object arg0) {
        return this.notSerializable(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_PARAM soBadSchemeName(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446343, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.soBadSchemeName", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM soBadSchemeName(CompletionStatus cs) {
        return this.soBadSchemeName(cs, null);
    }

    public BAD_PARAM soBadSchemeName(Throwable t2) {
        return this.soBadSchemeName(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM soBadSchemeName() {
        return this.soBadSchemeName(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM soBadAddress(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446344, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.soBadAddress", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM soBadAddress(CompletionStatus cs) {
        return this.soBadAddress(cs, null);
    }

    public BAD_PARAM soBadAddress(Throwable t2) {
        return this.soBadAddress(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM soBadAddress() {
        return this.soBadAddress(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM soBadSchemaSpecific(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446345, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.soBadSchemaSpecific", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM soBadSchemaSpecific(CompletionStatus cs) {
        return this.soBadSchemaSpecific(cs, null);
    }

    public BAD_PARAM soBadSchemaSpecific(Throwable t2) {
        return this.soBadSchemaSpecific(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM soBadSchemaSpecific() {
        return this.soBadSchemaSpecific(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM soNonSpecific(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446346, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.soNonSpecific", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM soNonSpecific(CompletionStatus cs) {
        return this.soNonSpecific(cs, null);
    }

    public BAD_PARAM soNonSpecific(Throwable t2) {
        return this.soNonSpecific(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM soNonSpecific() {
        return this.soNonSpecific(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM irDeriveAbsIntBase(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446347, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.irDeriveAbsIntBase", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM irDeriveAbsIntBase(CompletionStatus cs) {
        return this.irDeriveAbsIntBase(cs, null);
    }

    public BAD_PARAM irDeriveAbsIntBase(Throwable t2) {
        return this.irDeriveAbsIntBase(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM irDeriveAbsIntBase() {
        return this.irDeriveAbsIntBase(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM irValueSupport(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446348, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.irValueSupport", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM irValueSupport(CompletionStatus cs) {
        return this.irValueSupport(cs, null);
    }

    public BAD_PARAM irValueSupport(Throwable t2) {
        return this.irValueSupport(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM irValueSupport() {
        return this.irValueSupport(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM incompleteTypecode(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446349, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.incompleteTypecode", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM incompleteTypecode(CompletionStatus cs) {
        return this.incompleteTypecode(cs, null);
    }

    public BAD_PARAM incompleteTypecode(Throwable t2) {
        return this.incompleteTypecode(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM incompleteTypecode() {
        return this.incompleteTypecode(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM invalidObjectId(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446350, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.invalidObjectId", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM invalidObjectId(CompletionStatus cs) {
        return this.invalidObjectId(cs, null);
    }

    public BAD_PARAM invalidObjectId(Throwable t2) {
        return this.invalidObjectId(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM invalidObjectId() {
        return this.invalidObjectId(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM typecodeBadName(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446351, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.typecodeBadName", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM typecodeBadName(CompletionStatus cs) {
        return this.typecodeBadName(cs, null);
    }

    public BAD_PARAM typecodeBadName(Throwable t2) {
        return this.typecodeBadName(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM typecodeBadName() {
        return this.typecodeBadName(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM typecodeBadRepid(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446352, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.typecodeBadRepid", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM typecodeBadRepid(CompletionStatus cs) {
        return this.typecodeBadRepid(cs, null);
    }

    public BAD_PARAM typecodeBadRepid(Throwable t2) {
        return this.typecodeBadRepid(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM typecodeBadRepid() {
        return this.typecodeBadRepid(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM typecodeInvMember(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446353, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.typecodeInvMember", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM typecodeInvMember(CompletionStatus cs) {
        return this.typecodeInvMember(cs, null);
    }

    public BAD_PARAM typecodeInvMember(Throwable t2) {
        return this.typecodeInvMember(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM typecodeInvMember() {
        return this.typecodeInvMember(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM tcUnionDupLabel(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446354, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.tcUnionDupLabel", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM tcUnionDupLabel(CompletionStatus cs) {
        return this.tcUnionDupLabel(cs, null);
    }

    public BAD_PARAM tcUnionDupLabel(Throwable t2) {
        return this.tcUnionDupLabel(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM tcUnionDupLabel() {
        return this.tcUnionDupLabel(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM tcUnionIncompatible(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446355, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.tcUnionIncompatible", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM tcUnionIncompatible(CompletionStatus cs) {
        return this.tcUnionIncompatible(cs, null);
    }

    public BAD_PARAM tcUnionIncompatible(Throwable t2) {
        return this.tcUnionIncompatible(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM tcUnionIncompatible() {
        return this.tcUnionIncompatible(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM tcUnionBadDisc(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446356, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.tcUnionBadDisc", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM tcUnionBadDisc(CompletionStatus cs) {
        return this.tcUnionBadDisc(cs, null);
    }

    public BAD_PARAM tcUnionBadDisc(Throwable t2) {
        return this.tcUnionBadDisc(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM tcUnionBadDisc() {
        return this.tcUnionBadDisc(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM setExceptionBadAny(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446357, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.setExceptionBadAny", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM setExceptionBadAny(CompletionStatus cs) {
        return this.setExceptionBadAny(cs, null);
    }

    public BAD_PARAM setExceptionBadAny(Throwable t2) {
        return this.setExceptionBadAny(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM setExceptionBadAny() {
        return this.setExceptionBadAny(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM setExceptionUnlisted(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446358, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.setExceptionUnlisted", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM setExceptionUnlisted(CompletionStatus cs) {
        return this.setExceptionUnlisted(cs, null);
    }

    public BAD_PARAM setExceptionUnlisted(Throwable t2) {
        return this.setExceptionUnlisted(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM setExceptionUnlisted() {
        return this.setExceptionUnlisted(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM noClientWcharCodesetCtx(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446359, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noClientWcharCodesetCtx", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM noClientWcharCodesetCtx(CompletionStatus cs) {
        return this.noClientWcharCodesetCtx(cs, null);
    }

    public BAD_PARAM noClientWcharCodesetCtx(Throwable t2) {
        return this.noClientWcharCodesetCtx(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM noClientWcharCodesetCtx() {
        return this.noClientWcharCodesetCtx(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM illegalServiceContext(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446360, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.illegalServiceContext", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM illegalServiceContext(CompletionStatus cs) {
        return this.illegalServiceContext(cs, null);
    }

    public BAD_PARAM illegalServiceContext(Throwable t2) {
        return this.illegalServiceContext(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM illegalServiceContext() {
        return this.illegalServiceContext(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM enumOutOfRange(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446361, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.enumOutOfRange", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM enumOutOfRange(CompletionStatus cs) {
        return this.enumOutOfRange(cs, null);
    }

    public BAD_PARAM enumOutOfRange(Throwable t2) {
        return this.enumOutOfRange(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM enumOutOfRange() {
        return this.enumOutOfRange(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM invalidServiceContextId(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446362, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.invalidServiceContextId", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM invalidServiceContextId(CompletionStatus cs) {
        return this.invalidServiceContextId(cs, null);
    }

    public BAD_PARAM invalidServiceContextId(Throwable t2) {
        return this.invalidServiceContextId(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM invalidServiceContextId() {
        return this.invalidServiceContextId(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM rirWithNullObject(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446363, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.rirWithNullObject", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM rirWithNullObject(CompletionStatus cs) {
        return this.rirWithNullObject(cs, null);
    }

    public BAD_PARAM rirWithNullObject(Throwable t2) {
        return this.rirWithNullObject(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM rirWithNullObject() {
        return this.rirWithNullObject(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM invalidComponentId(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_PARAM exc = new BAD_PARAM(1330446364, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.FINE, "OMG.invalidComponentId", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM invalidComponentId(CompletionStatus cs, Object arg0) {
        return this.invalidComponentId(cs, null, arg0);
    }

    public BAD_PARAM invalidComponentId(Throwable t2, Object arg0) {
        return this.invalidComponentId(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_PARAM invalidComponentId(Object arg0) {
        return this.invalidComponentId(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_PARAM invalidProfileId(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446365, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.invalidProfileId", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM invalidProfileId(CompletionStatus cs) {
        return this.invalidProfileId(cs, null);
    }

    public BAD_PARAM invalidProfileId(Throwable t2) {
        return this.invalidProfileId(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM invalidProfileId() {
        return this.invalidProfileId(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM policyTypeDuplicate(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446366, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.policyTypeDuplicate", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM policyTypeDuplicate(CompletionStatus cs) {
        return this.policyTypeDuplicate(cs, null);
    }

    public BAD_PARAM policyTypeDuplicate(Throwable t2) {
        return this.policyTypeDuplicate(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM policyTypeDuplicate() {
        return this.policyTypeDuplicate(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM badOnewayDefinition(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446367, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badOnewayDefinition", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM badOnewayDefinition(CompletionStatus cs) {
        return this.badOnewayDefinition(cs, null);
    }

    public BAD_PARAM badOnewayDefinition(Throwable t2) {
        return this.badOnewayDefinition(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM badOnewayDefinition() {
        return this.badOnewayDefinition(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM diiForImplicitOperation(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446368, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.diiForImplicitOperation", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM diiForImplicitOperation(CompletionStatus cs) {
        return this.diiForImplicitOperation(cs, null);
    }

    public BAD_PARAM diiForImplicitOperation(Throwable t2) {
        return this.diiForImplicitOperation(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM diiForImplicitOperation() {
        return this.diiForImplicitOperation(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM xaCallInval(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446369, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.xaCallInval", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM xaCallInval(CompletionStatus cs) {
        return this.xaCallInval(cs, null);
    }

    public BAD_PARAM xaCallInval(Throwable t2) {
        return this.xaCallInval(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM xaCallInval() {
        return this.xaCallInval(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM unionBadDiscriminator(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446370, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.unionBadDiscriminator", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM unionBadDiscriminator(CompletionStatus cs) {
        return this.unionBadDiscriminator(cs, null);
    }

    public BAD_PARAM unionBadDiscriminator(Throwable t2) {
        return this.unionBadDiscriminator(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM unionBadDiscriminator() {
        return this.unionBadDiscriminator(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM ctxIllegalPropertyName(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446371, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.ctxIllegalPropertyName", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM ctxIllegalPropertyName(CompletionStatus cs) {
        return this.ctxIllegalPropertyName(cs, null);
    }

    public BAD_PARAM ctxIllegalPropertyName(Throwable t2) {
        return this.ctxIllegalPropertyName(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM ctxIllegalPropertyName() {
        return this.ctxIllegalPropertyName(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM ctxIllegalSearchString(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446372, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.ctxIllegalSearchString", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM ctxIllegalSearchString(CompletionStatus cs) {
        return this.ctxIllegalSearchString(cs, null);
    }

    public BAD_PARAM ctxIllegalSearchString(Throwable t2) {
        return this.ctxIllegalSearchString(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM ctxIllegalSearchString() {
        return this.ctxIllegalSearchString(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM ctxIllegalName(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446373, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.ctxIllegalName", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM ctxIllegalName(CompletionStatus cs) {
        return this.ctxIllegalName(cs, null);
    }

    public BAD_PARAM ctxIllegalName(Throwable t2) {
        return this.ctxIllegalName(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM ctxIllegalName() {
        return this.ctxIllegalName(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM ctxNonEmpty(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446374, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.ctxNonEmpty", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM ctxNonEmpty(CompletionStatus cs) {
        return this.ctxNonEmpty(cs, null);
    }

    public BAD_PARAM ctxNonEmpty(Throwable t2) {
        return this.ctxNonEmpty(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM ctxNonEmpty() {
        return this.ctxNonEmpty(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM invalidStreamFormatVersion(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_PARAM exc = new BAD_PARAM(1330446375, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "OMG.invalidStreamFormatVersion", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM invalidStreamFormatVersion(CompletionStatus cs, Object arg0) {
        return this.invalidStreamFormatVersion(cs, null, arg0);
    }

    public BAD_PARAM invalidStreamFormatVersion(Throwable t2, Object arg0) {
        return this.invalidStreamFormatVersion(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_PARAM invalidStreamFormatVersion(Object arg0) {
        return this.invalidStreamFormatVersion(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_PARAM notAValueoutputstream(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446376, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.notAValueoutputstream", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM notAValueoutputstream(CompletionStatus cs) {
        return this.notAValueoutputstream(cs, null);
    }

    public BAD_PARAM notAValueoutputstream(Throwable t2) {
        return this.notAValueoutputstream(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM notAValueoutputstream() {
        return this.notAValueoutputstream(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM notAValueinputstream(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1330446377, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.notAValueinputstream", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM notAValueinputstream(CompletionStatus cs) {
        return this.notAValueinputstream(cs, null);
    }

    public BAD_PARAM notAValueinputstream(Throwable t2) {
        return this.notAValueinputstream(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM notAValueinputstream() {
        return this.notAValueinputstream(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_TYPECODE marshallIncompleteTypecode(CompletionStatus cs, Throwable t2) {
        BAD_TYPECODE exc = new BAD_TYPECODE(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.marshallIncompleteTypecode", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_TYPECODE marshallIncompleteTypecode(CompletionStatus cs) {
        return this.marshallIncompleteTypecode(cs, null);
    }

    public BAD_TYPECODE marshallIncompleteTypecode(Throwable t2) {
        return this.marshallIncompleteTypecode(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_TYPECODE marshallIncompleteTypecode() {
        return this.marshallIncompleteTypecode(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_TYPECODE badMemberTypecode(CompletionStatus cs, Throwable t2) {
        BAD_TYPECODE exc = new BAD_TYPECODE(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badMemberTypecode", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_TYPECODE badMemberTypecode(CompletionStatus cs) {
        return this.badMemberTypecode(cs, null);
    }

    public BAD_TYPECODE badMemberTypecode(Throwable t2) {
        return this.badMemberTypecode(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_TYPECODE badMemberTypecode() {
        return this.badMemberTypecode(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_TYPECODE illegalParameter(CompletionStatus cs, Throwable t2) {
        BAD_TYPECODE exc = new BAD_TYPECODE(1330446339, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.illegalParameter", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public BAD_TYPECODE illegalParameter(CompletionStatus cs) {
        return this.illegalParameter(cs, null);
    }

    public BAD_TYPECODE illegalParameter(Throwable t2) {
        return this.illegalParameter(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_TYPECODE illegalParameter() {
        return this.illegalParameter(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION charNotInCodeset(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.charNotInCodeset", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION charNotInCodeset(CompletionStatus cs) {
        return this.charNotInCodeset(cs, null);
    }

    public DATA_CONVERSION charNotInCodeset(Throwable t2) {
        return this.charNotInCodeset(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION charNotInCodeset() {
        return this.charNotInCodeset(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION priorityMapFailre(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.priorityMapFailre", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION priorityMapFailre(CompletionStatus cs) {
        return this.priorityMapFailre(cs, null);
    }

    public DATA_CONVERSION priorityMapFailre(Throwable t2) {
        return this.priorityMapFailre(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION priorityMapFailre() {
        return this.priorityMapFailre(CompletionStatus.COMPLETED_NO, null);
    }

    public IMP_LIMIT noUsableProfile(CompletionStatus cs, Throwable t2) {
        IMP_LIMIT exc = new IMP_LIMIT(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noUsableProfile", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public IMP_LIMIT noUsableProfile(CompletionStatus cs) {
        return this.noUsableProfile(cs, null);
    }

    public IMP_LIMIT noUsableProfile(Throwable t2) {
        return this.noUsableProfile(CompletionStatus.COMPLETED_NO, t2);
    }

    public IMP_LIMIT noUsableProfile() {
        return this.noUsableProfile(CompletionStatus.COMPLETED_NO, null);
    }

    public INITIALIZE priorityRangeRestrict(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.priorityRangeRestrict", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public INITIALIZE priorityRangeRestrict(CompletionStatus cs) {
        return this.priorityRangeRestrict(cs, null);
    }

    public INITIALIZE priorityRangeRestrict(Throwable t2) {
        return this.priorityRangeRestrict(CompletionStatus.COMPLETED_NO, t2);
    }

    public INITIALIZE priorityRangeRestrict() {
        return this.priorityRangeRestrict(CompletionStatus.COMPLETED_NO, null);
    }

    public INV_OBJREF noServerWcharCodesetCmp(CompletionStatus cs, Throwable t2) {
        INV_OBJREF exc = new INV_OBJREF(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noServerWcharCodesetCmp", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public INV_OBJREF noServerWcharCodesetCmp(CompletionStatus cs) {
        return this.noServerWcharCodesetCmp(cs, null);
    }

    public INV_OBJREF noServerWcharCodesetCmp(Throwable t2) {
        return this.noServerWcharCodesetCmp(CompletionStatus.COMPLETED_NO, t2);
    }

    public INV_OBJREF noServerWcharCodesetCmp() {
        return this.noServerWcharCodesetCmp(CompletionStatus.COMPLETED_NO, null);
    }

    public INV_OBJREF codesetComponentRequired(CompletionStatus cs, Throwable t2) {
        INV_OBJREF exc = new INV_OBJREF(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.codesetComponentRequired", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public INV_OBJREF codesetComponentRequired(CompletionStatus cs) {
        return this.codesetComponentRequired(cs, null);
    }

    public INV_OBJREF codesetComponentRequired(Throwable t2) {
        return this.codesetComponentRequired(CompletionStatus.COMPLETED_NO, t2);
    }

    public INV_OBJREF codesetComponentRequired() {
        return this.codesetComponentRequired(CompletionStatus.COMPLETED_NO, null);
    }

    public INV_POLICY iorPolicyReconcileError(CompletionStatus cs, Throwable t2) {
        INV_POLICY exc = new INV_POLICY(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.iorPolicyReconcileError", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public INV_POLICY iorPolicyReconcileError(CompletionStatus cs) {
        return this.iorPolicyReconcileError(cs, null);
    }

    public INV_POLICY iorPolicyReconcileError(Throwable t2) {
        return this.iorPolicyReconcileError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INV_POLICY iorPolicyReconcileError() {
        return this.iorPolicyReconcileError(CompletionStatus.COMPLETED_NO, null);
    }

    public INV_POLICY policyUnknown(CompletionStatus cs, Throwable t2) {
        INV_POLICY exc = new INV_POLICY(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.policyUnknown", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public INV_POLICY policyUnknown(CompletionStatus cs) {
        return this.policyUnknown(cs, null);
    }

    public INV_POLICY policyUnknown(Throwable t2) {
        return this.policyUnknown(CompletionStatus.COMPLETED_NO, t2);
    }

    public INV_POLICY policyUnknown() {
        return this.policyUnknown(CompletionStatus.COMPLETED_NO, null);
    }

    public INV_POLICY noPolicyFactory(CompletionStatus cs, Throwable t2) {
        INV_POLICY exc = new INV_POLICY(1330446339, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noPolicyFactory", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public INV_POLICY noPolicyFactory(CompletionStatus cs) {
        return this.noPolicyFactory(cs, null);
    }

    public INV_POLICY noPolicyFactory(Throwable t2) {
        return this.noPolicyFactory(CompletionStatus.COMPLETED_NO, t2);
    }

    public INV_POLICY noPolicyFactory() {
        return this.noPolicyFactory(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL xaRmerr(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.xaRmerr", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL xaRmerr(CompletionStatus cs) {
        return this.xaRmerr(cs, null);
    }

    public INTERNAL xaRmerr(Throwable t2) {
        return this.xaRmerr(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL xaRmerr() {
        return this.xaRmerr(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL xaRmfail(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.xaRmfail", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL xaRmfail(CompletionStatus cs) {
        return this.xaRmfail(cs, null);
    }

    public INTERNAL xaRmfail(Throwable t2) {
        return this.xaRmfail(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL xaRmfail() {
        return this.xaRmfail(CompletionStatus.COMPLETED_NO, null);
    }

    public INTF_REPOS noIr(CompletionStatus cs, Throwable t2) {
        INTF_REPOS exc = new INTF_REPOS(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noIr", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public INTF_REPOS noIr(CompletionStatus cs) {
        return this.noIr(cs, null);
    }

    public INTF_REPOS noIr(Throwable t2) {
        return this.noIr(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTF_REPOS noIr() {
        return this.noIr(CompletionStatus.COMPLETED_NO, null);
    }

    public INTF_REPOS noInterfaceInIr(CompletionStatus cs, Throwable t2) {
        INTF_REPOS exc = new INTF_REPOS(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noInterfaceInIr", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public INTF_REPOS noInterfaceInIr(CompletionStatus cs) {
        return this.noInterfaceInIr(cs, null);
    }

    public INTF_REPOS noInterfaceInIr(Throwable t2) {
        return this.noInterfaceInIr(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTF_REPOS noInterfaceInIr() {
        return this.noInterfaceInIr(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL unableLocateValueFactory(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.unableLocateValueFactory", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL unableLocateValueFactory(CompletionStatus cs) {
        return this.unableLocateValueFactory(cs, null);
    }

    public MARSHAL unableLocateValueFactory(Throwable t2) {
        return this.unableLocateValueFactory(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL unableLocateValueFactory() {
        return this.unableLocateValueFactory(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL setResultBeforeCtx(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.setResultBeforeCtx", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL setResultBeforeCtx(CompletionStatus cs) {
        return this.setResultBeforeCtx(cs, null);
    }

    public MARSHAL setResultBeforeCtx(Throwable t2) {
        return this.setResultBeforeCtx(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL setResultBeforeCtx() {
        return this.setResultBeforeCtx(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL badNvlist(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1330446339, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badNvlist", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badNvlist(CompletionStatus cs) {
        return this.badNvlist(cs, null);
    }

    public MARSHAL badNvlist(Throwable t2) {
        return this.badNvlist(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL badNvlist() {
        return this.badNvlist(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL notAnObjectImpl(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1330446340, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.notAnObjectImpl", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL notAnObjectImpl(CompletionStatus cs) {
        return this.notAnObjectImpl(cs, null);
    }

    public MARSHAL notAnObjectImpl(Throwable t2) {
        return this.notAnObjectImpl(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL notAnObjectImpl() {
        return this.notAnObjectImpl(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL wcharBadGiopVersionSent(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1330446341, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.wcharBadGiopVersionSent", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL wcharBadGiopVersionSent(CompletionStatus cs) {
        return this.wcharBadGiopVersionSent(cs, null);
    }

    public MARSHAL wcharBadGiopVersionSent(Throwable t2) {
        return this.wcharBadGiopVersionSent(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL wcharBadGiopVersionSent() {
        return this.wcharBadGiopVersionSent(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL wcharBadGiopVersionReturned(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1330446342, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.wcharBadGiopVersionReturned", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL wcharBadGiopVersionReturned(CompletionStatus cs) {
        return this.wcharBadGiopVersionReturned(cs, null);
    }

    public MARSHAL wcharBadGiopVersionReturned(Throwable t2) {
        return this.wcharBadGiopVersionReturned(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL wcharBadGiopVersionReturned() {
        return this.wcharBadGiopVersionReturned(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL unsupportedFormatVersion(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1330446343, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.unsupportedFormatVersion", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL unsupportedFormatVersion(CompletionStatus cs) {
        return this.unsupportedFormatVersion(cs, null);
    }

    public MARSHAL unsupportedFormatVersion(Throwable t2) {
        return this.unsupportedFormatVersion(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL unsupportedFormatVersion() {
        return this.unsupportedFormatVersion(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL rmiiiopOptionalDataIncompatible1(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1330446344, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.rmiiiopOptionalDataIncompatible1", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL rmiiiopOptionalDataIncompatible1(CompletionStatus cs) {
        return this.rmiiiopOptionalDataIncompatible1(cs, null);
    }

    public MARSHAL rmiiiopOptionalDataIncompatible1(Throwable t2) {
        return this.rmiiiopOptionalDataIncompatible1(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL rmiiiopOptionalDataIncompatible1() {
        return this.rmiiiopOptionalDataIncompatible1(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL rmiiiopOptionalDataIncompatible2(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1330446344, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.rmiiiopOptionalDataIncompatible2", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL rmiiiopOptionalDataIncompatible2(CompletionStatus cs) {
        return this.rmiiiopOptionalDataIncompatible2(cs, null);
    }

    public MARSHAL rmiiiopOptionalDataIncompatible2(Throwable t2) {
        return this.rmiiiopOptionalDataIncompatible2(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL rmiiiopOptionalDataIncompatible2() {
        return this.rmiiiopOptionalDataIncompatible2(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL rmiiiopOptionalDataIncompatible3(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1330446344, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.rmiiiopOptionalDataIncompatible3", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL rmiiiopOptionalDataIncompatible3(CompletionStatus cs) {
        return this.rmiiiopOptionalDataIncompatible3(cs, null);
    }

    public MARSHAL rmiiiopOptionalDataIncompatible3(Throwable t2) {
        return this.rmiiiopOptionalDataIncompatible3(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL rmiiiopOptionalDataIncompatible3() {
        return this.rmiiiopOptionalDataIncompatible3(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT missingLocalValueImpl(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.missingLocalValueImpl", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT missingLocalValueImpl(CompletionStatus cs) {
        return this.missingLocalValueImpl(cs, null);
    }

    public NO_IMPLEMENT missingLocalValueImpl(Throwable t2) {
        return this.missingLocalValueImpl(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT missingLocalValueImpl() {
        return this.missingLocalValueImpl(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT incompatibleValueImpl(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.incompatibleValueImpl", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT incompatibleValueImpl(CompletionStatus cs) {
        return this.incompatibleValueImpl(cs, null);
    }

    public NO_IMPLEMENT incompatibleValueImpl(Throwable t2) {
        return this.incompatibleValueImpl(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT incompatibleValueImpl() {
        return this.incompatibleValueImpl(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT noUsableProfile2(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1330446339, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noUsableProfile2", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT noUsableProfile2(CompletionStatus cs) {
        return this.noUsableProfile2(cs, null);
    }

    public NO_IMPLEMENT noUsableProfile2(Throwable t2) {
        return this.noUsableProfile2(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT noUsableProfile2() {
        return this.noUsableProfile2(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT diiLocalObject(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1330446340, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.diiLocalObject", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT diiLocalObject(CompletionStatus cs) {
        return this.diiLocalObject(cs, null);
    }

    public NO_IMPLEMENT diiLocalObject(Throwable t2) {
        return this.diiLocalObject(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT diiLocalObject() {
        return this.diiLocalObject(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT bioReset(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1330446341, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.bioReset", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT bioReset(CompletionStatus cs) {
        return this.bioReset(cs, null);
    }

    public NO_IMPLEMENT bioReset(Throwable t2) {
        return this.bioReset(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT bioReset() {
        return this.bioReset(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT bioMetaNotAvailable(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1330446342, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.bioMetaNotAvailable", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT bioMetaNotAvailable(CompletionStatus cs) {
        return this.bioMetaNotAvailable(cs, null);
    }

    public NO_IMPLEMENT bioMetaNotAvailable(Throwable t2) {
        return this.bioMetaNotAvailable(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT bioMetaNotAvailable() {
        return this.bioMetaNotAvailable(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT bioGenomicNoIterator(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1330446343, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.bioGenomicNoIterator", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT bioGenomicNoIterator(CompletionStatus cs) {
        return this.bioGenomicNoIterator(cs, null);
    }

    public NO_IMPLEMENT bioGenomicNoIterator(Throwable t2) {
        return this.bioGenomicNoIterator(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT bioGenomicNoIterator() {
        return this.bioGenomicNoIterator(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_RESOURCES piOperationNotSupported1(CompletionStatus cs, Throwable t2) {
        NO_RESOURCES exc = new NO_RESOURCES(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.piOperationNotSupported1", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_RESOURCES piOperationNotSupported1(CompletionStatus cs) {
        return this.piOperationNotSupported1(cs, null);
    }

    public NO_RESOURCES piOperationNotSupported1(Throwable t2) {
        return this.piOperationNotSupported1(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_RESOURCES piOperationNotSupported1() {
        return this.piOperationNotSupported1(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_RESOURCES piOperationNotSupported2(CompletionStatus cs, Throwable t2) {
        NO_RESOURCES exc = new NO_RESOURCES(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.piOperationNotSupported2", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_RESOURCES piOperationNotSupported2(CompletionStatus cs) {
        return this.piOperationNotSupported2(cs, null);
    }

    public NO_RESOURCES piOperationNotSupported2(Throwable t2) {
        return this.piOperationNotSupported2(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_RESOURCES piOperationNotSupported2() {
        return this.piOperationNotSupported2(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_RESOURCES piOperationNotSupported3(CompletionStatus cs, Throwable t2) {
        NO_RESOURCES exc = new NO_RESOURCES(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.piOperationNotSupported3", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_RESOURCES piOperationNotSupported3(CompletionStatus cs) {
        return this.piOperationNotSupported3(cs, null);
    }

    public NO_RESOURCES piOperationNotSupported3(Throwable t2) {
        return this.piOperationNotSupported3(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_RESOURCES piOperationNotSupported3() {
        return this.piOperationNotSupported3(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_RESOURCES piOperationNotSupported4(CompletionStatus cs, Throwable t2) {
        NO_RESOURCES exc = new NO_RESOURCES(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.piOperationNotSupported4", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_RESOURCES piOperationNotSupported4(CompletionStatus cs) {
        return this.piOperationNotSupported4(cs, null);
    }

    public NO_RESOURCES piOperationNotSupported4(Throwable t2) {
        return this.piOperationNotSupported4(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_RESOURCES piOperationNotSupported4() {
        return this.piOperationNotSupported4(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_RESOURCES piOperationNotSupported5(CompletionStatus cs, Throwable t2) {
        NO_RESOURCES exc = new NO_RESOURCES(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.piOperationNotSupported5", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_RESOURCES piOperationNotSupported5(CompletionStatus cs) {
        return this.piOperationNotSupported5(cs, null);
    }

    public NO_RESOURCES piOperationNotSupported5(Throwable t2) {
        return this.piOperationNotSupported5(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_RESOURCES piOperationNotSupported5() {
        return this.piOperationNotSupported5(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_RESOURCES piOperationNotSupported6(CompletionStatus cs, Throwable t2) {
        NO_RESOURCES exc = new NO_RESOURCES(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.piOperationNotSupported6", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_RESOURCES piOperationNotSupported6(CompletionStatus cs) {
        return this.piOperationNotSupported6(cs, null);
    }

    public NO_RESOURCES piOperationNotSupported6(Throwable t2) {
        return this.piOperationNotSupported6(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_RESOURCES piOperationNotSupported6() {
        return this.piOperationNotSupported6(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_RESOURCES piOperationNotSupported7(CompletionStatus cs, Throwable t2) {
        NO_RESOURCES exc = new NO_RESOURCES(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.piOperationNotSupported7", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_RESOURCES piOperationNotSupported7(CompletionStatus cs) {
        return this.piOperationNotSupported7(cs, null);
    }

    public NO_RESOURCES piOperationNotSupported7(Throwable t2) {
        return this.piOperationNotSupported7(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_RESOURCES piOperationNotSupported7() {
        return this.piOperationNotSupported7(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_RESOURCES piOperationNotSupported8(CompletionStatus cs, Throwable t2) {
        NO_RESOURCES exc = new NO_RESOURCES(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.piOperationNotSupported8", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_RESOURCES piOperationNotSupported8(CompletionStatus cs) {
        return this.piOperationNotSupported8(cs, null);
    }

    public NO_RESOURCES piOperationNotSupported8(Throwable t2) {
        return this.piOperationNotSupported8(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_RESOURCES piOperationNotSupported8() {
        return this.piOperationNotSupported8(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_RESOURCES noConnectionPriority(CompletionStatus cs, Throwable t2) {
        NO_RESOURCES exc = new NO_RESOURCES(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noConnectionPriority", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public NO_RESOURCES noConnectionPriority(CompletionStatus cs) {
        return this.noConnectionPriority(cs, null);
    }

    public NO_RESOURCES noConnectionPriority(Throwable t2) {
        return this.noConnectionPriority(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_RESOURCES noConnectionPriority() {
        return this.noConnectionPriority(CompletionStatus.COMPLETED_NO, null);
    }

    public TRANSACTION_ROLLEDBACK xaRb(CompletionStatus cs, Throwable t2) {
        TRANSACTION_ROLLEDBACK exc = new TRANSACTION_ROLLEDBACK(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.xaRb", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public TRANSACTION_ROLLEDBACK xaRb(CompletionStatus cs) {
        return this.xaRb(cs, null);
    }

    public TRANSACTION_ROLLEDBACK xaRb(Throwable t2) {
        return this.xaRb(CompletionStatus.COMPLETED_NO, t2);
    }

    public TRANSACTION_ROLLEDBACK xaRb() {
        return this.xaRb(CompletionStatus.COMPLETED_NO, null);
    }

    public TRANSACTION_ROLLEDBACK xaNota(CompletionStatus cs, Throwable t2) {
        TRANSACTION_ROLLEDBACK exc = new TRANSACTION_ROLLEDBACK(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.xaNota", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public TRANSACTION_ROLLEDBACK xaNota(CompletionStatus cs) {
        return this.xaNota(cs, null);
    }

    public TRANSACTION_ROLLEDBACK xaNota(Throwable t2) {
        return this.xaNota(CompletionStatus.COMPLETED_NO, t2);
    }

    public TRANSACTION_ROLLEDBACK xaNota() {
        return this.xaNota(CompletionStatus.COMPLETED_NO, null);
    }

    public TRANSACTION_ROLLEDBACK xaEndTrueRollbackDeferred(CompletionStatus cs, Throwable t2) {
        TRANSACTION_ROLLEDBACK exc = new TRANSACTION_ROLLEDBACK(1330446339, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.xaEndTrueRollbackDeferred", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public TRANSACTION_ROLLEDBACK xaEndTrueRollbackDeferred(CompletionStatus cs) {
        return this.xaEndTrueRollbackDeferred(cs, null);
    }

    public TRANSACTION_ROLLEDBACK xaEndTrueRollbackDeferred(Throwable t2) {
        return this.xaEndTrueRollbackDeferred(CompletionStatus.COMPLETED_NO, t2);
    }

    public TRANSACTION_ROLLEDBACK xaEndTrueRollbackDeferred() {
        return this.xaEndTrueRollbackDeferred(CompletionStatus.COMPLETED_NO, null);
    }

    public TRANSIENT poaRequestDiscard(CompletionStatus cs, Throwable t2) {
        TRANSIENT exc = new TRANSIENT(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.poaRequestDiscard", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public TRANSIENT poaRequestDiscard(CompletionStatus cs) {
        return this.poaRequestDiscard(cs, null);
    }

    public TRANSIENT poaRequestDiscard(Throwable t2) {
        return this.poaRequestDiscard(CompletionStatus.COMPLETED_NO, t2);
    }

    public TRANSIENT poaRequestDiscard() {
        return this.poaRequestDiscard(CompletionStatus.COMPLETED_NO, null);
    }

    public TRANSIENT noUsableProfile3(CompletionStatus cs, Throwable t2) {
        TRANSIENT exc = new TRANSIENT(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noUsableProfile3", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public TRANSIENT noUsableProfile3(CompletionStatus cs) {
        return this.noUsableProfile3(cs, null);
    }

    public TRANSIENT noUsableProfile3(Throwable t2) {
        return this.noUsableProfile3(CompletionStatus.COMPLETED_NO, t2);
    }

    public TRANSIENT noUsableProfile3() {
        return this.noUsableProfile3(CompletionStatus.COMPLETED_NO, null);
    }

    public TRANSIENT requestCancelled(CompletionStatus cs, Throwable t2) {
        TRANSIENT exc = new TRANSIENT(1330446339, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.requestCancelled", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public TRANSIENT requestCancelled(CompletionStatus cs) {
        return this.requestCancelled(cs, null);
    }

    public TRANSIENT requestCancelled(Throwable t2) {
        return this.requestCancelled(CompletionStatus.COMPLETED_NO, t2);
    }

    public TRANSIENT requestCancelled() {
        return this.requestCancelled(CompletionStatus.COMPLETED_NO, null);
    }

    public TRANSIENT poaDestroyed(CompletionStatus cs, Throwable t2) {
        TRANSIENT exc = new TRANSIENT(1330446340, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.poaDestroyed", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public TRANSIENT poaDestroyed(CompletionStatus cs) {
        return this.poaDestroyed(cs, null);
    }

    public TRANSIENT poaDestroyed(Throwable t2) {
        return this.poaDestroyed(CompletionStatus.COMPLETED_NO, t2);
    }

    public TRANSIENT poaDestroyed() {
        return this.poaDestroyed(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST unregisteredValueAsObjref(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.unregisteredValueAsObjref", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST unregisteredValueAsObjref(CompletionStatus cs) {
        return this.unregisteredValueAsObjref(cs, null);
    }

    public OBJECT_NOT_EXIST unregisteredValueAsObjref(Throwable t2) {
        return this.unregisteredValueAsObjref(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST unregisteredValueAsObjref() {
        return this.unregisteredValueAsObjref(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST noObjectAdaptor(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.noObjectAdaptor", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST noObjectAdaptor(CompletionStatus cs) {
        return this.noObjectAdaptor(cs, null);
    }

    public OBJECT_NOT_EXIST noObjectAdaptor(Throwable t2) {
        return this.noObjectAdaptor(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST noObjectAdaptor() {
        return this.noObjectAdaptor(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST bioNotAvailable(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1330446339, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.bioNotAvailable", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST bioNotAvailable(CompletionStatus cs) {
        return this.bioNotAvailable(cs, null);
    }

    public OBJECT_NOT_EXIST bioNotAvailable(Throwable t2) {
        return this.bioNotAvailable(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST bioNotAvailable() {
        return this.bioNotAvailable(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST objectAdapterInactive(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1330446340, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.objectAdapterInactive", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST objectAdapterInactive(CompletionStatus cs) {
        return this.objectAdapterInactive(cs, null);
    }

    public OBJECT_NOT_EXIST objectAdapterInactive(Throwable t2) {
        return this.objectAdapterInactive(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST objectAdapterInactive() {
        return this.objectAdapterInactive(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER adapterActivatorException(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "OMG.adapterActivatorException", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER adapterActivatorException(CompletionStatus cs, Object arg0, Object arg1) {
        return this.adapterActivatorException(cs, null, arg0, arg1);
    }

    public OBJ_ADAPTER adapterActivatorException(Throwable t2, Object arg0, Object arg1) {
        return this.adapterActivatorException(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public OBJ_ADAPTER adapterActivatorException(Object arg0, Object arg1) {
        return this.adapterActivatorException(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public OBJ_ADAPTER badServantType(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badServantType", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER badServantType(CompletionStatus cs) {
        return this.badServantType(cs, null);
    }

    public OBJ_ADAPTER badServantType(Throwable t2) {
        return this.badServantType(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER badServantType() {
        return this.badServantType(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER noDefaultServant(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1330446339, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noDefaultServant", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER noDefaultServant(CompletionStatus cs) {
        return this.noDefaultServant(cs, null);
    }

    public OBJ_ADAPTER noDefaultServant(Throwable t2) {
        return this.noDefaultServant(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER noDefaultServant() {
        return this.noDefaultServant(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER noServantManager(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1330446340, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.noServantManager", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER noServantManager(CompletionStatus cs) {
        return this.noServantManager(cs, null);
    }

    public OBJ_ADAPTER noServantManager(Throwable t2) {
        return this.noServantManager(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER noServantManager() {
        return this.noServantManager(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER badPolicyIncarnate(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1330446341, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.badPolicyIncarnate", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER badPolicyIncarnate(CompletionStatus cs) {
        return this.badPolicyIncarnate(cs, null);
    }

    public OBJ_ADAPTER badPolicyIncarnate(Throwable t2) {
        return this.badPolicyIncarnate(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER badPolicyIncarnate() {
        return this.badPolicyIncarnate(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER piExcCompEstablished(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1330446342, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.piExcCompEstablished", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER piExcCompEstablished(CompletionStatus cs) {
        return this.piExcCompEstablished(cs, null);
    }

    public OBJ_ADAPTER piExcCompEstablished(Throwable t2) {
        return this.piExcCompEstablished(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER piExcCompEstablished() {
        return this.piExcCompEstablished(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER nullServantReturned(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1330446343, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.nullServantReturned", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER nullServantReturned(CompletionStatus cs) {
        return this.nullServantReturned(cs, null);
    }

    public OBJ_ADAPTER nullServantReturned(Throwable t2) {
        return this.nullServantReturned(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER nullServantReturned() {
        return this.nullServantReturned(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN unknownUserException(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1330446337, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "OMG.unknownUserException", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN unknownUserException(CompletionStatus cs) {
        return this.unknownUserException(cs, null);
    }

    public UNKNOWN unknownUserException(Throwable t2) {
        return this.unknownUserException(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN unknownUserException() {
        return this.unknownUserException(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN unsupportedSystemException(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1330446338, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.unsupportedSystemException", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN unsupportedSystemException(CompletionStatus cs) {
        return this.unsupportedSystemException(cs, null);
    }

    public UNKNOWN unsupportedSystemException(Throwable t2) {
        return this.unsupportedSystemException(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN unsupportedSystemException() {
        return this.unsupportedSystemException(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN piUnknownUserException(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1330446339, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "OMG.piUnknownUserException", parameters, OMGSystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN piUnknownUserException(CompletionStatus cs) {
        return this.piUnknownUserException(cs, null);
    }

    public UNKNOWN piUnknownUserException(Throwable t2) {
        return this.piUnknownUserException(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN piUnknownUserException() {
        return this.piUnknownUserException(CompletionStatus.COMPLETED_NO, null);
    }
}

