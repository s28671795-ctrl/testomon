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
import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.DATA_CONVERSION;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.CORBA.TRANSIENT;
import org.omg.CORBA.UNKNOWN;

public class ORBUtilSystemException
extends LogWrapperBase {
    private static LogWrapperFactory factory = new LogWrapperFactory(){

        @Override
        public LogWrapperBase create(Logger logger2) {
            return new ORBUtilSystemException(logger2);
        }
    };
    public static final int ADAPTER_ID_NOT_AVAILABLE = 1398079689;
    public static final int SERVER_ID_NOT_AVAILABLE = 1398079690;
    public static final int ORB_ID_NOT_AVAILABLE = 1398079691;
    public static final int OBJECT_ADAPTER_ID_NOT_AVAILABLE = 1398079692;
    public static final int CONNECTING_SERVANT = 1398079693;
    public static final int EXTRACT_WRONG_TYPE = 1398079694;
    public static final int EXTRACT_WRONG_TYPE_LIST = 1398079695;
    public static final int BAD_STRING_BOUNDS = 1398079696;
    public static final int INSERT_OBJECT_INCOMPATIBLE = 1398079698;
    public static final int INSERT_OBJECT_FAILED = 1398079699;
    public static final int EXTRACT_OBJECT_INCOMPATIBLE = 1398079700;
    public static final int FIXED_NOT_MATCH = 1398079701;
    public static final int FIXED_BAD_TYPECODE = 1398079702;
    public static final int SET_EXCEPTION_CALLED_NULL_ARGS = 1398079711;
    public static final int SET_EXCEPTION_CALLED_BAD_TYPE = 1398079712;
    public static final int CONTEXT_CALLED_OUT_OF_ORDER = 1398079713;
    public static final int BAD_ORB_CONFIGURATOR = 1398079714;
    public static final int ORB_CONFIGURATOR_ERROR = 1398079715;
    public static final int ORB_DESTROYED = 1398079716;
    public static final int NEGATIVE_BOUNDS = 1398079717;
    public static final int EXTRACT_NOT_INITIALIZED = 1398079718;
    public static final int EXTRACT_OBJECT_FAILED = 1398079719;
    public static final int METHOD_NOT_FOUND_IN_TIE = 1398079720;
    public static final int CLASS_NOT_FOUND1 = 1398079721;
    public static final int CLASS_NOT_FOUND2 = 1398079722;
    public static final int CLASS_NOT_FOUND3 = 1398079723;
    public static final int GET_DELEGATE_SERVANT_NOT_ACTIVE = 1398079724;
    public static final int GET_DELEGATE_WRONG_POLICY = 1398079725;
    public static final int SET_DELEGATE_REQUIRES_STUB = 1398079726;
    public static final int GET_DELEGATE_REQUIRES_STUB = 1398079727;
    public static final int GET_TYPE_IDS_REQUIRES_STUB = 1398079728;
    public static final int GET_ORB_REQUIRES_STUB = 1398079729;
    public static final int CONNECT_REQUIRES_STUB = 1398079730;
    public static final int IS_LOCAL_REQUIRES_STUB = 1398079731;
    public static final int REQUEST_REQUIRES_STUB = 1398079732;
    public static final int BAD_ACTIVATE_TIE_CALL = 1398079733;
    public static final int IO_EXCEPTION_ON_CLOSE = 1398079734;
    public static final int NULL_PARAM = 1398079689;
    public static final int UNABLE_FIND_VALUE_FACTORY = 1398079690;
    public static final int ABSTRACT_FROM_NON_ABSTRACT = 1398079691;
    public static final int INVALID_TAGGED_PROFILE = 1398079692;
    public static final int OBJREF_FROM_FOREIGN_ORB = 1398079693;
    public static final int LOCAL_OBJECT_NOT_ALLOWED = 1398079694;
    public static final int NULL_OBJECT_REFERENCE = 1398079695;
    public static final int COULD_NOT_LOAD_CLASS = 1398079696;
    public static final int BAD_URL = 1398079697;
    public static final int FIELD_NOT_FOUND = 1398079698;
    public static final int ERROR_SETTING_FIELD = 1398079699;
    public static final int BOUNDS_ERROR_IN_DII_REQUEST = 1398079700;
    public static final int PERSISTENT_SERVER_INIT_ERROR = 1398079701;
    public static final int COULD_NOT_CREATE_ARRAY = 1398079702;
    public static final int COULD_NOT_SET_ARRAY = 1398079703;
    public static final int ILLEGAL_BOOTSTRAP_OPERATION = 1398079704;
    public static final int BOOTSTRAP_RUNTIME_EXCEPTION = 1398079705;
    public static final int BOOTSTRAP_EXCEPTION = 1398079706;
    public static final int STRING_EXPECTED = 1398079707;
    public static final int INVALID_TYPECODE_KIND = 1398079708;
    public static final int SOCKET_FACTORY_AND_CONTACT_INFO_LIST_AT_SAME_TIME = 1398079709;
    public static final int ACCEPTORS_AND_LEGACY_SOCKET_FACTORY_AT_SAME_TIME = 1398079710;
    public static final int BAD_ORB_FOR_SERVANT = 1398079711;
    public static final int INVALID_REQUEST_PARTITIONING_POLICY_VALUE = 1398079712;
    public static final int INVALID_REQUEST_PARTITIONING_COMPONENT_VALUE = 1398079713;
    public static final int INVALID_REQUEST_PARTITIONING_ID = 1398079714;
    public static final int ERROR_IN_SETTING_DYNAMIC_STUB_FACTORY_FACTORY = 1398079715;
    public static final int DSIMETHOD_NOTCALLED = 1398079689;
    public static final int ARGUMENTS_CALLED_MULTIPLE = 1398079690;
    public static final int ARGUMENTS_CALLED_AFTER_EXCEPTION = 1398079691;
    public static final int ARGUMENTS_CALLED_NULL_ARGS = 1398079692;
    public static final int ARGUMENTS_NOT_CALLED = 1398079693;
    public static final int SET_RESULT_CALLED_MULTIPLE = 1398079694;
    public static final int SET_RESULT_AFTER_EXCEPTION = 1398079695;
    public static final int SET_RESULT_CALLED_NULL_ARGS = 1398079696;
    public static final int BAD_REMOTE_TYPECODE = 1398079689;
    public static final int UNRESOLVED_RECURSIVE_TYPECODE = 1398079690;
    public static final int CONNECT_FAILURE = 1398079689;
    public static final int CONNECTION_CLOSE_REBIND = 1398079690;
    public static final int WRITE_ERROR_SEND = 1398079691;
    public static final int GET_PROPERTIES_ERROR = 1398079692;
    public static final int BOOTSTRAP_SERVER_NOT_AVAIL = 1398079693;
    public static final int INVOKE_ERROR = 1398079694;
    public static final int DEFAULT_CREATE_SERVER_SOCKET_GIVEN_NON_IIOP_CLEAR_TEXT = 1398079695;
    public static final int CONNECTION_ABORT = 1398079696;
    public static final int CONNECTION_REBIND = 1398079697;
    public static final int RECV_MSG_ERROR = 1398079698;
    public static final int IOEXCEPTION_WHEN_READING_CONNECTION = 1398079699;
    public static final int SELECTION_KEY_INVALID = 1398079700;
    public static final int EXCEPTION_IN_ACCEPT = 1398079701;
    public static final int SECURITY_EXCEPTION_IN_ACCEPT = 1398079702;
    public static final int TRANSPORT_READ_TIMEOUT_EXCEEDED = 1398079703;
    public static final int CREATE_LISTENER_FAILED = 1398079704;
    public static final int BUFFER_READ_MANAGER_TIMEOUT = 1398079705;
    public static final int BAD_STRINGIFIED_IOR_LEN = 1398079689;
    public static final int BAD_STRINGIFIED_IOR = 1398079690;
    public static final int BAD_MODIFIER = 1398079691;
    public static final int CODESET_INCOMPATIBLE = 1398079692;
    public static final int BAD_HEX_DIGIT = 1398079693;
    public static final int BAD_UNICODE_PAIR = 1398079694;
    public static final int BTC_RESULT_MORE_THAN_ONE_CHAR = 1398079695;
    public static final int BAD_CODESETS_FROM_CLIENT = 1398079696;
    public static final int INVALID_SINGLE_CHAR_CTB = 1398079697;
    public static final int BAD_GIOP_1_1_CTB = 1398079698;
    public static final int BAD_SEQUENCE_BOUNDS = 1398079700;
    public static final int ILLEGAL_SOCKET_FACTORY_TYPE = 1398079701;
    public static final int BAD_CUSTOM_SOCKET_FACTORY = 1398079702;
    public static final int FRAGMENT_SIZE_MINIMUM = 1398079703;
    public static final int FRAGMENT_SIZE_DIV = 1398079704;
    public static final int ORB_INITIALIZER_FAILURE = 1398079705;
    public static final int ORB_INITIALIZER_TYPE = 1398079706;
    public static final int ORB_INITIALREFERENCE_SYNTAX = 1398079707;
    public static final int ACCEPTOR_INSTANTIATION_FAILURE = 1398079708;
    public static final int ACCEPTOR_INSTANTIATION_TYPE_FAILURE = 1398079709;
    public static final int ILLEGAL_CONTACT_INFO_LIST_FACTORY_TYPE = 1398079710;
    public static final int BAD_CONTACT_INFO_LIST_FACTORY = 1398079711;
    public static final int ILLEGAL_IOR_TO_SOCKET_INFO_TYPE = 1398079712;
    public static final int BAD_CUSTOM_IOR_TO_SOCKET_INFO = 1398079713;
    public static final int ILLEGAL_IIOP_PRIMARY_TO_CONTACT_INFO_TYPE = 1398079714;
    public static final int BAD_CUSTOM_IIOP_PRIMARY_TO_CONTACT_INFO = 1398079715;
    public static final int BAD_CORBALOC_STRING = 1398079689;
    public static final int NO_PROFILE_PRESENT = 1398079690;
    public static final int CANNOT_CREATE_ORBID_DB = 1398079689;
    public static final int CANNOT_READ_ORBID_DB = 1398079690;
    public static final int CANNOT_WRITE_ORBID_DB = 1398079691;
    public static final int GET_SERVER_PORT_CALLED_BEFORE_ENDPOINTS_INITIALIZED = 1398079692;
    public static final int PERSISTENT_SERVERPORT_NOT_SET = 1398079693;
    public static final int PERSISTENT_SERVERID_NOT_SET = 1398079694;
    public static final int NON_EXISTENT_ORBID = 1398079689;
    public static final int NO_SERVER_SUBCONTRACT = 1398079690;
    public static final int SERVER_SC_TEMP_SIZE = 1398079691;
    public static final int NO_CLIENT_SC_CLASS = 1398079692;
    public static final int SERVER_SC_NO_IIOP_PROFILE = 1398079693;
    public static final int GET_SYSTEM_EX_RETURNED_NULL = 1398079694;
    public static final int PEEKSTRING_FAILED = 1398079695;
    public static final int GET_LOCAL_HOST_FAILED = 1398079696;
    public static final int BAD_LOCATE_REQUEST_STATUS = 1398079698;
    public static final int STRINGIFY_WRITE_ERROR = 1398079699;
    public static final int BAD_GIOP_REQUEST_TYPE = 1398079700;
    public static final int ERROR_UNMARSHALING_USEREXC = 1398079701;
    public static final int RequestDispatcherRegistry_ERROR = 1398079702;
    public static final int LOCATIONFORWARD_ERROR = 1398079703;
    public static final int WRONG_CLIENTSC = 1398079704;
    public static final int BAD_SERVANT_READ_OBJECT = 1398079705;
    public static final int MULT_IIOP_PROF_NOT_SUPPORTED = 1398079706;
    public static final int GIOP_MAGIC_ERROR = 1398079708;
    public static final int GIOP_VERSION_ERROR = 1398079709;
    public static final int ILLEGAL_REPLY_STATUS = 1398079710;
    public static final int ILLEGAL_GIOP_MSG_TYPE = 1398079711;
    public static final int FRAGMENTATION_DISALLOWED = 1398079712;
    public static final int BAD_REPLYSTATUS = 1398079713;
    public static final int CTB_CONVERTER_FAILURE = 1398079714;
    public static final int BTC_CONVERTER_FAILURE = 1398079715;
    public static final int WCHAR_ARRAY_UNSUPPORTED_ENCODING = 1398079716;
    public static final int ILLEGAL_TARGET_ADDRESS_DISPOSITION = 1398079717;
    public static final int NULL_REPLY_IN_GET_ADDR_DISPOSITION = 1398079718;
    public static final int ORB_TARGET_ADDR_PREFERENCE_IN_EXTRACT_OBJECTKEY_INVALID = 1398079719;
    public static final int INVALID_ISSTREAMED_TCKIND = 1398079720;
    public static final int INVALID_JDK1_3_1_PATCH_LEVEL = 1398079721;
    public static final int SVCCTX_UNMARSHAL_ERROR = 1398079722;
    public static final int NULL_IOR = 1398079723;
    public static final int UNSUPPORTED_GIOP_VERSION = 1398079724;
    public static final int APPLICATION_EXCEPTION_IN_SPECIAL_METHOD = 1398079725;
    public static final int STATEMENT_NOT_REACHABLE1 = 1398079726;
    public static final int STATEMENT_NOT_REACHABLE2 = 1398079727;
    public static final int STATEMENT_NOT_REACHABLE3 = 1398079728;
    public static final int STATEMENT_NOT_REACHABLE4 = 1398079729;
    public static final int STATEMENT_NOT_REACHABLE5 = 1398079730;
    public static final int STATEMENT_NOT_REACHABLE6 = 1398079731;
    public static final int UNEXPECTED_DII_EXCEPTION = 1398079732;
    public static final int METHOD_SHOULD_NOT_BE_CALLED = 1398079733;
    public static final int CANCEL_NOT_SUPPORTED = 1398079734;
    public static final int EMPTY_STACK_RUN_SERVANT_POST_INVOKE = 1398079735;
    public static final int PROBLEM_WITH_EXCEPTION_TYPECODE = 1398079736;
    public static final int ILLEGAL_SUBCONTRACT_ID = 1398079737;
    public static final int BAD_SYSTEM_EXCEPTION_IN_LOCATE_REPLY = 1398079738;
    public static final int BAD_SYSTEM_EXCEPTION_IN_REPLY = 1398079739;
    public static final int BAD_COMPLETION_STATUS_IN_LOCATE_REPLY = 1398079740;
    public static final int BAD_COMPLETION_STATUS_IN_REPLY = 1398079741;
    public static final int BADKIND_CANNOT_OCCUR = 1398079742;
    public static final int ERROR_RESOLVING_ALIAS = 1398079743;
    public static final int TK_LONG_DOUBLE_NOT_SUPPORTED = 1398079744;
    public static final int TYPECODE_NOT_SUPPORTED = 1398079745;
    public static final int BOUNDS_CANNOT_OCCUR = 1398079747;
    public static final int NUM_INVOCATIONS_ALREADY_ZERO = 1398079749;
    public static final int ERROR_INIT_BADSERVERIDHANDLER = 1398079750;
    public static final int NO_TOA = 1398079751;
    public static final int NO_POA = 1398079752;
    public static final int INVOCATION_INFO_STACK_EMPTY = 1398079753;
    public static final int BAD_CODE_SET_STRING = 1398079754;
    public static final int UNKNOWN_NATIVE_CODESET = 1398079755;
    public static final int UNKNOWN_CONVERSION_CODE_SET = 1398079756;
    public static final int INVALID_CODE_SET_NUMBER = 1398079757;
    public static final int INVALID_CODE_SET_STRING = 1398079758;
    public static final int INVALID_CTB_CONVERTER_NAME = 1398079759;
    public static final int INVALID_BTC_CONVERTER_NAME = 1398079760;
    public static final int COULD_NOT_DUPLICATE_CDR_INPUT_STREAM = 1398079761;
    public static final int BOOTSTRAP_APPLICATION_EXCEPTION = 1398079762;
    public static final int DUPLICATE_INDIRECTION_OFFSET = 1398079763;
    public static final int BAD_MESSAGE_TYPE_FOR_CANCEL = 1398079764;
    public static final int DUPLICATE_EXCEPTION_DETAIL_MESSAGE = 1398079765;
    public static final int BAD_EXCEPTION_DETAIL_MESSAGE_SERVICE_CONTEXT_TYPE = 1398079766;
    public static final int UNEXPECTED_DIRECT_BYTE_BUFFER_WITH_NON_CHANNEL_SOCKET = 1398079767;
    public static final int UNEXPECTED_NON_DIRECT_BYTE_BUFFER_WITH_CHANNEL_SOCKET = 1398079768;
    public static final int INVALID_CONTACT_INFO_LIST_ITERATOR_FAILURE_EXCEPTION = 1398079770;
    public static final int REMARSHAL_WITH_NOWHERE_TO_GO = 1398079771;
    public static final int EXCEPTION_WHEN_SENDING_CLOSE_CONNECTION = 1398079772;
    public static final int INVOCATION_ERROR_IN_REFLECTIVE_TIE = 1398079773;
    public static final int BAD_HELPER_WRITE_METHOD = 1398079774;
    public static final int BAD_HELPER_READ_METHOD = 1398079775;
    public static final int BAD_HELPER_ID_METHOD = 1398079776;
    public static final int WRITE_UNDECLARED_EXCEPTION = 1398079777;
    public static final int READ_UNDECLARED_EXCEPTION = 1398079778;
    public static final int UNABLE_TO_SET_SOCKET_FACTORY_ORB = 1398079779;
    public static final int UNEXPECTED_EXCEPTION = 1398079780;
    public static final int NO_INVOCATION_HANDLER = 1398079781;
    public static final int INVALID_BUFF_MGR_STRATEGY = 1398079782;
    public static final int JAVA_STREAM_INIT_FAILED = 1398079783;
    public static final int DUPLICATE_ORB_VERSION_SERVICE_CONTEXT = 1398079784;
    public static final int DUPLICATE_SENDING_CONTEXT_SERVICE_CONTEXT = 1398079785;
    public static final int WORK_QUEUE_THREAD_INTERRUPTED = 1398079786;
    public static final int WORKER_THREAD_CREATED = 1398079792;
    public static final int WORKER_THREAD_THROWABLE_FROM_REQUEST_WORK = 1398079797;
    public static final int WORKER_THREAD_NOT_NEEDED = 1398079798;
    public static final int WORKER_THREAD_DO_WORK_THROWABLE = 1398079799;
    public static final int WORKER_THREAD_CAUGHT_UNEXPECTED_THROWABLE = 1398079800;
    public static final int WORKER_THREAD_CREATION_FAILURE = 1398079801;
    public static final int WORKER_THREAD_SET_NAME_FAILURE = 1398079802;
    public static final int WORK_QUEUE_REQUEST_WORK_NO_WORK_FOUND = 1398079804;
    public static final int THREAD_POOL_CLOSE_ERROR = 1398079814;
    public static final int THREAD_GROUP_IS_DESTROYED = 1398079815;
    public static final int THREAD_GROUP_HAS_ACTIVE_THREADS_IN_CLOSE = 1398079816;
    public static final int THREAD_GROUP_HAS_SUB_GROUPS_IN_CLOSE = 1398079817;
    public static final int THREAD_GROUP_DESTROY_FAILED = 1398079818;
    public static final int INTERRUPTED_JOIN_CALL_WHILE_CLOSING_THREAD_POOL = 1398079819;
    public static final int CHUNK_OVERFLOW = 1398079689;
    public static final int UNEXPECTED_EOF = 1398079690;
    public static final int READ_OBJECT_EXCEPTION = 1398079691;
    public static final int CHARACTER_OUTOFRANGE = 1398079692;
    public static final int DSI_RESULT_EXCEPTION = 1398079693;
    public static final int IIOPINPUTSTREAM_GROW = 1398079694;
    public static final int END_OF_STREAM = 1398079695;
    public static final int INVALID_OBJECT_KEY = 1398079696;
    public static final int MALFORMED_URL = 1398079697;
    public static final int VALUEHANDLER_READ_ERROR = 1398079698;
    public static final int VALUEHANDLER_READ_EXCEPTION = 1398079699;
    public static final int BAD_KIND = 1398079700;
    public static final int CNFE_READ_CLASS = 1398079701;
    public static final int BAD_REP_ID_INDIRECTION = 1398079702;
    public static final int BAD_CODEBASE_INDIRECTION = 1398079703;
    public static final int UNKNOWN_CODESET = 1398079704;
    public static final int WCHAR_DATA_IN_GIOP_1_0 = 1398079705;
    public static final int NEGATIVE_STRING_LENGTH = 1398079706;
    public static final int EXPECTED_TYPE_NULL_AND_NO_REP_ID = 1398079707;
    public static final int READ_VALUE_AND_NO_REP_ID = 1398079708;
    public static final int UNEXPECTED_ENCLOSING_VALUETYPE = 1398079710;
    public static final int POSITIVE_END_TAG = 1398079711;
    public static final int NULL_OUT_CALL = 1398079712;
    public static final int WRITE_LOCAL_OBJECT = 1398079713;
    public static final int BAD_INSERTOBJ_PARAM = 1398079714;
    public static final int CUSTOM_WRAPPER_WITH_CODEBASE = 1398079715;
    public static final int CUSTOM_WRAPPER_INDIRECTION = 1398079716;
    public static final int CUSTOM_WRAPPER_NOT_SINGLE_REPID = 1398079717;
    public static final int BAD_VALUE_TAG = 1398079718;
    public static final int BAD_TYPECODE_FOR_CUSTOM_VALUE = 1398079719;
    public static final int ERROR_INVOKING_HELPER_WRITE = 1398079720;
    public static final int BAD_DIGIT_IN_FIXED = 1398079721;
    public static final int REF_TYPE_INDIR_TYPE = 1398079722;
    public static final int BAD_RESERVED_LENGTH = 1398079723;
    public static final int NULL_NOT_ALLOWED = 1398079724;
    public static final int UNION_DISCRIMINATOR_ERROR = 1398079726;
    public static final int CANNOT_MARSHAL_NATIVE = 1398079727;
    public static final int CANNOT_MARSHAL_BAD_TCKIND = 1398079728;
    public static final int INVALID_INDIRECTION = 1398079729;
    public static final int INDIRECTION_NOT_FOUND = 1398079730;
    public static final int RECURSIVE_TYPECODE_ERROR = 1398079731;
    public static final int INVALID_SIMPLE_TYPECODE = 1398079732;
    public static final int INVALID_COMPLEX_TYPECODE = 1398079733;
    public static final int INVALID_TYPECODE_KIND_MARSHAL = 1398079734;
    public static final int UNEXPECTED_UNION_DEFAULT = 1398079735;
    public static final int ILLEGAL_UNION_DISCRIMINATOR_TYPE = 1398079736;
    public static final int COULD_NOT_SKIP_BYTES = 1398079737;
    public static final int BAD_CHUNK_LENGTH = 1398079738;
    public static final int UNABLE_TO_LOCATE_REP_ID_ARRAY = 1398079739;
    public static final int BAD_FIXED = 1398079740;
    public static final int READ_OBJECT_LOAD_CLASS_FAILURE = 1398079741;
    public static final int COULD_NOT_INSTANTIATE_HELPER = 1398079742;
    public static final int BAD_TOA_OAID = 1398079743;
    public static final int COULD_NOT_INVOKE_HELPER_READ_METHOD = 1398079744;
    public static final int COULD_NOT_FIND_CLASS = 1398079745;
    public static final int BAD_ARGUMENTS_NVLIST = 1398079746;
    public static final int STUB_CREATE_ERROR = 1398079747;
    public static final int JAVA_SERIALIZATION_EXCEPTION = 1398079748;
    public static final int GENERIC_NO_IMPL = 1398079689;
    public static final int CONTEXT_NOT_IMPLEMENTED = 1398079690;
    public static final int GETINTERFACE_NOT_IMPLEMENTED = 1398079691;
    public static final int SEND_DEFERRED_NOTIMPLEMENTED = 1398079692;
    public static final int LONG_DOUBLE_NOT_IMPLEMENTED = 1398079693;
    public static final int NO_SERVER_SC_IN_DISPATCH = 1398079689;
    public static final int ORB_CONNECT_ERROR = 1398079690;
    public static final int ADAPTER_INACTIVE_IN_ACTIVATION = 1398079691;
    public static final int LOCATE_UNKNOWN_OBJECT = 1398079689;
    public static final int BAD_SERVER_ID = 1398079690;
    public static final int BAD_SKELETON = 1398079691;
    public static final int SERVANT_NOT_FOUND = 1398079692;
    public static final int NO_OBJECT_ADAPTER_FACTORY = 1398079693;
    public static final int BAD_ADAPTER_ID = 1398079694;
    public static final int DYN_ANY_DESTROYED = 1398079695;
    public static final int REQUEST_CANCELED = 1398079689;
    public static final int UNKNOWN_CORBA_EXC = 1398079689;
    public static final int RUNTIMEEXCEPTION = 1398079690;
    public static final int UNKNOWN_SERVER_ERROR = 1398079691;
    public static final int UNKNOWN_DSI_SYSEX = 1398079692;
    public static final int UNKNOWN_SYSEX = 1398079693;
    public static final int WRONG_INTERFACE_DEF = 1398079694;
    public static final int NO_INTERFACE_DEF_STUB = 1398079695;
    public static final int UNKNOWN_EXCEPTION_IN_DISPATCH = 1398079697;

    public ORBUtilSystemException(Logger logger2) {
        super(logger2);
    }

    public static ORBUtilSystemException get(ORB orb, String logDomain) {
        ORBUtilSystemException wrapper = (ORBUtilSystemException)orb.getLogWrapper(logDomain, "ORBUTIL", factory);
        return wrapper;
    }

    public static ORBUtilSystemException get(String logDomain) {
        ORBUtilSystemException wrapper = (ORBUtilSystemException)ORB.staticGetLogWrapper(logDomain, "ORBUTIL", factory);
        return wrapper;
    }

    public BAD_OPERATION adapterIdNotAvailable(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.adapterIdNotAvailable", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION adapterIdNotAvailable(CompletionStatus cs) {
        return this.adapterIdNotAvailable(cs, null);
    }

    public BAD_OPERATION adapterIdNotAvailable(Throwable t2) {
        return this.adapterIdNotAvailable(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION adapterIdNotAvailable() {
        return this.adapterIdNotAvailable(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION serverIdNotAvailable(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.serverIdNotAvailable", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION serverIdNotAvailable(CompletionStatus cs) {
        return this.serverIdNotAvailable(cs, null);
    }

    public BAD_OPERATION serverIdNotAvailable(Throwable t2) {
        return this.serverIdNotAvailable(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION serverIdNotAvailable() {
        return this.serverIdNotAvailable(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION orbIdNotAvailable(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.orbIdNotAvailable", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION orbIdNotAvailable(CompletionStatus cs) {
        return this.orbIdNotAvailable(cs, null);
    }

    public BAD_OPERATION orbIdNotAvailable(Throwable t2) {
        return this.orbIdNotAvailable(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION orbIdNotAvailable() {
        return this.orbIdNotAvailable(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION objectAdapterIdNotAvailable(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.objectAdapterIdNotAvailable", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION objectAdapterIdNotAvailable(CompletionStatus cs) {
        return this.objectAdapterIdNotAvailable(cs, null);
    }

    public BAD_OPERATION objectAdapterIdNotAvailable(Throwable t2) {
        return this.objectAdapterIdNotAvailable(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION objectAdapterIdNotAvailable() {
        return this.objectAdapterIdNotAvailable(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION connectingServant(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.connectingServant", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION connectingServant(CompletionStatus cs) {
        return this.connectingServant(cs, null);
    }

    public BAD_OPERATION connectingServant(Throwable t2) {
        return this.connectingServant(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION connectingServant() {
        return this.connectingServant(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION extractWrongType(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079694, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.FINE, "ORBUTIL.extractWrongType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION extractWrongType(CompletionStatus cs, Object arg0, Object arg1) {
        return this.extractWrongType(cs, null, arg0, arg1);
    }

    public BAD_OPERATION extractWrongType(Throwable t2, Object arg0, Object arg1) {
        return this.extractWrongType(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public BAD_OPERATION extractWrongType(Object arg0, Object arg1) {
        return this.extractWrongType(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public BAD_OPERATION extractWrongTypeList(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079695, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.extractWrongTypeList", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION extractWrongTypeList(CompletionStatus cs, Object arg0, Object arg1) {
        return this.extractWrongTypeList(cs, null, arg0, arg1);
    }

    public BAD_OPERATION extractWrongTypeList(Throwable t2, Object arg0, Object arg1) {
        return this.extractWrongTypeList(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public BAD_OPERATION extractWrongTypeList(Object arg0, Object arg1) {
        return this.extractWrongTypeList(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public BAD_OPERATION badStringBounds(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079696, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.badStringBounds", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION badStringBounds(CompletionStatus cs, Object arg0, Object arg1) {
        return this.badStringBounds(cs, null, arg0, arg1);
    }

    public BAD_OPERATION badStringBounds(Throwable t2, Object arg0, Object arg1) {
        return this.badStringBounds(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public BAD_OPERATION badStringBounds(Object arg0, Object arg1) {
        return this.badStringBounds(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public BAD_OPERATION insertObjectIncompatible(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079698, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.insertObjectIncompatible", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION insertObjectIncompatible(CompletionStatus cs) {
        return this.insertObjectIncompatible(cs, null);
    }

    public BAD_OPERATION insertObjectIncompatible(Throwable t2) {
        return this.insertObjectIncompatible(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION insertObjectIncompatible() {
        return this.insertObjectIncompatible(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION insertObjectFailed(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079699, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.insertObjectFailed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION insertObjectFailed(CompletionStatus cs) {
        return this.insertObjectFailed(cs, null);
    }

    public BAD_OPERATION insertObjectFailed(Throwable t2) {
        return this.insertObjectFailed(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION insertObjectFailed() {
        return this.insertObjectFailed(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION extractObjectIncompatible(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079700, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.extractObjectIncompatible", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION extractObjectIncompatible(CompletionStatus cs) {
        return this.extractObjectIncompatible(cs, null);
    }

    public BAD_OPERATION extractObjectIncompatible(Throwable t2) {
        return this.extractObjectIncompatible(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION extractObjectIncompatible() {
        return this.extractObjectIncompatible(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION fixedNotMatch(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079701, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.fixedNotMatch", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION fixedNotMatch(CompletionStatus cs) {
        return this.fixedNotMatch(cs, null);
    }

    public BAD_OPERATION fixedNotMatch(Throwable t2) {
        return this.fixedNotMatch(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION fixedNotMatch() {
        return this.fixedNotMatch(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION fixedBadTypecode(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079702, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.fixedBadTypecode", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION fixedBadTypecode(CompletionStatus cs) {
        return this.fixedBadTypecode(cs, null);
    }

    public BAD_OPERATION fixedBadTypecode(Throwable t2) {
        return this.fixedBadTypecode(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION fixedBadTypecode() {
        return this.fixedBadTypecode(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION setExceptionCalledNullArgs(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079711, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.setExceptionCalledNullArgs", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION setExceptionCalledNullArgs(CompletionStatus cs) {
        return this.setExceptionCalledNullArgs(cs, null);
    }

    public BAD_OPERATION setExceptionCalledNullArgs(Throwable t2) {
        return this.setExceptionCalledNullArgs(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION setExceptionCalledNullArgs() {
        return this.setExceptionCalledNullArgs(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION setExceptionCalledBadType(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079712, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.setExceptionCalledBadType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION setExceptionCalledBadType(CompletionStatus cs) {
        return this.setExceptionCalledBadType(cs, null);
    }

    public BAD_OPERATION setExceptionCalledBadType(Throwable t2) {
        return this.setExceptionCalledBadType(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION setExceptionCalledBadType() {
        return this.setExceptionCalledBadType(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION contextCalledOutOfOrder(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079713, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.contextCalledOutOfOrder", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION contextCalledOutOfOrder(CompletionStatus cs) {
        return this.contextCalledOutOfOrder(cs, null);
    }

    public BAD_OPERATION contextCalledOutOfOrder(Throwable t2) {
        return this.contextCalledOutOfOrder(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION contextCalledOutOfOrder() {
        return this.contextCalledOutOfOrder(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION badOrbConfigurator(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079714, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badOrbConfigurator", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION badOrbConfigurator(CompletionStatus cs, Object arg0) {
        return this.badOrbConfigurator(cs, null, arg0);
    }

    public BAD_OPERATION badOrbConfigurator(Throwable t2, Object arg0) {
        return this.badOrbConfigurator(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_OPERATION badOrbConfigurator(Object arg0) {
        return this.badOrbConfigurator(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_OPERATION orbConfiguratorError(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079715, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.orbConfiguratorError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION orbConfiguratorError(CompletionStatus cs) {
        return this.orbConfiguratorError(cs, null);
    }

    public BAD_OPERATION orbConfiguratorError(Throwable t2) {
        return this.orbConfiguratorError(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION orbConfiguratorError() {
        return this.orbConfiguratorError(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION orbDestroyed(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079716, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.orbDestroyed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION orbDestroyed(CompletionStatus cs) {
        return this.orbDestroyed(cs, null);
    }

    public BAD_OPERATION orbDestroyed(Throwable t2) {
        return this.orbDestroyed(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION orbDestroyed() {
        return this.orbDestroyed(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION negativeBounds(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079717, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.negativeBounds", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION negativeBounds(CompletionStatus cs) {
        return this.negativeBounds(cs, null);
    }

    public BAD_OPERATION negativeBounds(Throwable t2) {
        return this.negativeBounds(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION negativeBounds() {
        return this.negativeBounds(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION extractNotInitialized(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079718, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.extractNotInitialized", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION extractNotInitialized(CompletionStatus cs) {
        return this.extractNotInitialized(cs, null);
    }

    public BAD_OPERATION extractNotInitialized(Throwable t2) {
        return this.extractNotInitialized(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION extractNotInitialized() {
        return this.extractNotInitialized(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION extractObjectFailed(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079719, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.extractObjectFailed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION extractObjectFailed(CompletionStatus cs) {
        return this.extractObjectFailed(cs, null);
    }

    public BAD_OPERATION extractObjectFailed(Throwable t2) {
        return this.extractObjectFailed(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION extractObjectFailed() {
        return this.extractObjectFailed(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION methodNotFoundInTie(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079720, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.FINE, "ORBUTIL.methodNotFoundInTie", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION methodNotFoundInTie(CompletionStatus cs, Object arg0, Object arg1) {
        return this.methodNotFoundInTie(cs, null, arg0, arg1);
    }

    public BAD_OPERATION methodNotFoundInTie(Throwable t2, Object arg0, Object arg1) {
        return this.methodNotFoundInTie(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public BAD_OPERATION methodNotFoundInTie(Object arg0, Object arg1) {
        return this.methodNotFoundInTie(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public BAD_OPERATION classNotFound1(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079721, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.FINE, "ORBUTIL.classNotFound1", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION classNotFound1(CompletionStatus cs, Object arg0) {
        return this.classNotFound1(cs, null, arg0);
    }

    public BAD_OPERATION classNotFound1(Throwable t2, Object arg0) {
        return this.classNotFound1(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_OPERATION classNotFound1(Object arg0) {
        return this.classNotFound1(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_OPERATION classNotFound2(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079722, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.FINE, "ORBUTIL.classNotFound2", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION classNotFound2(CompletionStatus cs, Object arg0) {
        return this.classNotFound2(cs, null, arg0);
    }

    public BAD_OPERATION classNotFound2(Throwable t2, Object arg0) {
        return this.classNotFound2(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_OPERATION classNotFound2(Object arg0) {
        return this.classNotFound2(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_OPERATION classNotFound3(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079723, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.FINE, "ORBUTIL.classNotFound3", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION classNotFound3(CompletionStatus cs, Object arg0) {
        return this.classNotFound3(cs, null, arg0);
    }

    public BAD_OPERATION classNotFound3(Throwable t2, Object arg0) {
        return this.classNotFound3(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_OPERATION classNotFound3(Object arg0) {
        return this.classNotFound3(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_OPERATION getDelegateServantNotActive(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079724, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.getDelegateServantNotActive", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION getDelegateServantNotActive(CompletionStatus cs) {
        return this.getDelegateServantNotActive(cs, null);
    }

    public BAD_OPERATION getDelegateServantNotActive(Throwable t2) {
        return this.getDelegateServantNotActive(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION getDelegateServantNotActive() {
        return this.getDelegateServantNotActive(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION getDelegateWrongPolicy(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079725, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.getDelegateWrongPolicy", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION getDelegateWrongPolicy(CompletionStatus cs) {
        return this.getDelegateWrongPolicy(cs, null);
    }

    public BAD_OPERATION getDelegateWrongPolicy(Throwable t2) {
        return this.getDelegateWrongPolicy(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION getDelegateWrongPolicy() {
        return this.getDelegateWrongPolicy(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION setDelegateRequiresStub(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079726, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.setDelegateRequiresStub", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION setDelegateRequiresStub(CompletionStatus cs) {
        return this.setDelegateRequiresStub(cs, null);
    }

    public BAD_OPERATION setDelegateRequiresStub(Throwable t2) {
        return this.setDelegateRequiresStub(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION setDelegateRequiresStub() {
        return this.setDelegateRequiresStub(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION getDelegateRequiresStub(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079727, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.getDelegateRequiresStub", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION getDelegateRequiresStub(CompletionStatus cs) {
        return this.getDelegateRequiresStub(cs, null);
    }

    public BAD_OPERATION getDelegateRequiresStub(Throwable t2) {
        return this.getDelegateRequiresStub(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION getDelegateRequiresStub() {
        return this.getDelegateRequiresStub(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION getTypeIdsRequiresStub(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079728, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.getTypeIdsRequiresStub", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION getTypeIdsRequiresStub(CompletionStatus cs) {
        return this.getTypeIdsRequiresStub(cs, null);
    }

    public BAD_OPERATION getTypeIdsRequiresStub(Throwable t2) {
        return this.getTypeIdsRequiresStub(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION getTypeIdsRequiresStub() {
        return this.getTypeIdsRequiresStub(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION getOrbRequiresStub(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079729, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.getOrbRequiresStub", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION getOrbRequiresStub(CompletionStatus cs) {
        return this.getOrbRequiresStub(cs, null);
    }

    public BAD_OPERATION getOrbRequiresStub(Throwable t2) {
        return this.getOrbRequiresStub(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION getOrbRequiresStub() {
        return this.getOrbRequiresStub(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION connectRequiresStub(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079730, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.connectRequiresStub", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION connectRequiresStub(CompletionStatus cs) {
        return this.connectRequiresStub(cs, null);
    }

    public BAD_OPERATION connectRequiresStub(Throwable t2) {
        return this.connectRequiresStub(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION connectRequiresStub() {
        return this.connectRequiresStub(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION isLocalRequiresStub(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079731, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.isLocalRequiresStub", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION isLocalRequiresStub(CompletionStatus cs) {
        return this.isLocalRequiresStub(cs, null);
    }

    public BAD_OPERATION isLocalRequiresStub(Throwable t2) {
        return this.isLocalRequiresStub(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION isLocalRequiresStub() {
        return this.isLocalRequiresStub(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION requestRequiresStub(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079732, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.requestRequiresStub", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION requestRequiresStub(CompletionStatus cs) {
        return this.requestRequiresStub(cs, null);
    }

    public BAD_OPERATION requestRequiresStub(Throwable t2) {
        return this.requestRequiresStub(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION requestRequiresStub() {
        return this.requestRequiresStub(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION badActivateTieCall(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079733, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badActivateTieCall", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION badActivateTieCall(CompletionStatus cs) {
        return this.badActivateTieCall(cs, null);
    }

    public BAD_OPERATION badActivateTieCall(Throwable t2) {
        return this.badActivateTieCall(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION badActivateTieCall() {
        return this.badActivateTieCall(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION ioExceptionOnClose(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398079734, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.ioExceptionOnClose", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION ioExceptionOnClose(CompletionStatus cs) {
        return this.ioExceptionOnClose(cs, null);
    }

    public BAD_OPERATION ioExceptionOnClose(Throwable t2) {
        return this.ioExceptionOnClose(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION ioExceptionOnClose() {
        return this.ioExceptionOnClose(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM nullParam(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.nullParam", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM nullParam(CompletionStatus cs) {
        return this.nullParam(cs, null);
    }

    public BAD_PARAM nullParam(Throwable t2) {
        return this.nullParam(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM nullParam() {
        return this.nullParam(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM unableFindValueFactory(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.unableFindValueFactory", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM unableFindValueFactory(CompletionStatus cs) {
        return this.unableFindValueFactory(cs, null);
    }

    public BAD_PARAM unableFindValueFactory(Throwable t2) {
        return this.unableFindValueFactory(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM unableFindValueFactory() {
        return this.unableFindValueFactory(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM abstractFromNonAbstract(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.abstractFromNonAbstract", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM abstractFromNonAbstract(CompletionStatus cs) {
        return this.abstractFromNonAbstract(cs, null);
    }

    public BAD_PARAM abstractFromNonAbstract(Throwable t2) {
        return this.abstractFromNonAbstract(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM abstractFromNonAbstract() {
        return this.abstractFromNonAbstract(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM invalidTaggedProfile(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.invalidTaggedProfile", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM invalidTaggedProfile(CompletionStatus cs) {
        return this.invalidTaggedProfile(cs, null);
    }

    public BAD_PARAM invalidTaggedProfile(Throwable t2) {
        return this.invalidTaggedProfile(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM invalidTaggedProfile() {
        return this.invalidTaggedProfile(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM objrefFromForeignOrb(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.objrefFromForeignOrb", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM objrefFromForeignOrb(CompletionStatus cs) {
        return this.objrefFromForeignOrb(cs, null);
    }

    public BAD_PARAM objrefFromForeignOrb(Throwable t2) {
        return this.objrefFromForeignOrb(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM objrefFromForeignOrb() {
        return this.objrefFromForeignOrb(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM localObjectNotAllowed(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079694, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.localObjectNotAllowed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM localObjectNotAllowed(CompletionStatus cs) {
        return this.localObjectNotAllowed(cs, null);
    }

    public BAD_PARAM localObjectNotAllowed(Throwable t2) {
        return this.localObjectNotAllowed(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM localObjectNotAllowed() {
        return this.localObjectNotAllowed(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM nullObjectReference(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079695, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.nullObjectReference", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM nullObjectReference(CompletionStatus cs) {
        return this.nullObjectReference(cs, null);
    }

    public BAD_PARAM nullObjectReference(Throwable t2) {
        return this.nullObjectReference(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM nullObjectReference() {
        return this.nullObjectReference(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM couldNotLoadClass(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_PARAM exc = new BAD_PARAM(1398079696, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.couldNotLoadClass", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM couldNotLoadClass(CompletionStatus cs, Object arg0) {
        return this.couldNotLoadClass(cs, null, arg0);
    }

    public BAD_PARAM couldNotLoadClass(Throwable t2, Object arg0) {
        return this.couldNotLoadClass(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_PARAM couldNotLoadClass(Object arg0) {
        return this.couldNotLoadClass(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_PARAM badUrl(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_PARAM exc = new BAD_PARAM(1398079697, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badUrl", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM badUrl(CompletionStatus cs, Object arg0) {
        return this.badUrl(cs, null, arg0);
    }

    public BAD_PARAM badUrl(Throwable t2, Object arg0) {
        return this.badUrl(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_PARAM badUrl(Object arg0) {
        return this.badUrl(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_PARAM fieldNotFound(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_PARAM exc = new BAD_PARAM(1398079698, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.fieldNotFound", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM fieldNotFound(CompletionStatus cs, Object arg0) {
        return this.fieldNotFound(cs, null, arg0);
    }

    public BAD_PARAM fieldNotFound(Throwable t2, Object arg0) {
        return this.fieldNotFound(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_PARAM fieldNotFound(Object arg0) {
        return this.fieldNotFound(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_PARAM errorSettingField(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        BAD_PARAM exc = new BAD_PARAM(1398079699, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.errorSettingField", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM errorSettingField(CompletionStatus cs, Object arg0, Object arg1) {
        return this.errorSettingField(cs, null, arg0, arg1);
    }

    public BAD_PARAM errorSettingField(Throwable t2, Object arg0, Object arg1) {
        return this.errorSettingField(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public BAD_PARAM errorSettingField(Object arg0, Object arg1) {
        return this.errorSettingField(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public BAD_PARAM boundsErrorInDiiRequest(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079700, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.boundsErrorInDiiRequest", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM boundsErrorInDiiRequest(CompletionStatus cs) {
        return this.boundsErrorInDiiRequest(cs, null);
    }

    public BAD_PARAM boundsErrorInDiiRequest(Throwable t2) {
        return this.boundsErrorInDiiRequest(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM boundsErrorInDiiRequest() {
        return this.boundsErrorInDiiRequest(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM persistentServerInitError(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079701, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.persistentServerInitError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM persistentServerInitError(CompletionStatus cs) {
        return this.persistentServerInitError(cs, null);
    }

    public BAD_PARAM persistentServerInitError(Throwable t2) {
        return this.persistentServerInitError(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM persistentServerInitError() {
        return this.persistentServerInitError(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM couldNotCreateArray(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        BAD_PARAM exc = new BAD_PARAM(1398079702, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "ORBUTIL.couldNotCreateArray", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM couldNotCreateArray(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.couldNotCreateArray(cs, null, arg0, arg1, arg2);
    }

    public BAD_PARAM couldNotCreateArray(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.couldNotCreateArray(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public BAD_PARAM couldNotCreateArray(Object arg0, Object arg1, Object arg2) {
        return this.couldNotCreateArray(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public BAD_PARAM couldNotSetArray(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        BAD_PARAM exc = new BAD_PARAM(1398079703, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2, arg3, arg4};
            this.doLog(Level.WARNING, "ORBUTIL.couldNotSetArray", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM couldNotSetArray(CompletionStatus cs, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        return this.couldNotSetArray(cs, null, arg0, arg1, arg2, arg3, arg4);
    }

    public BAD_PARAM couldNotSetArray(Throwable t2, Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        return this.couldNotSetArray(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2, arg3, arg4);
    }

    public BAD_PARAM couldNotSetArray(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        return this.couldNotSetArray(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2, arg3, arg4);
    }

    public BAD_PARAM illegalBootstrapOperation(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_PARAM exc = new BAD_PARAM(1398079704, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.illegalBootstrapOperation", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM illegalBootstrapOperation(CompletionStatus cs, Object arg0) {
        return this.illegalBootstrapOperation(cs, null, arg0);
    }

    public BAD_PARAM illegalBootstrapOperation(Throwable t2, Object arg0) {
        return this.illegalBootstrapOperation(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_PARAM illegalBootstrapOperation(Object arg0) {
        return this.illegalBootstrapOperation(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_PARAM bootstrapRuntimeException(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079705, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.bootstrapRuntimeException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM bootstrapRuntimeException(CompletionStatus cs) {
        return this.bootstrapRuntimeException(cs, null);
    }

    public BAD_PARAM bootstrapRuntimeException(Throwable t2) {
        return this.bootstrapRuntimeException(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM bootstrapRuntimeException() {
        return this.bootstrapRuntimeException(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM bootstrapException(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079706, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.bootstrapException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM bootstrapException(CompletionStatus cs) {
        return this.bootstrapException(cs, null);
    }

    public BAD_PARAM bootstrapException(Throwable t2) {
        return this.bootstrapException(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM bootstrapException() {
        return this.bootstrapException(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM stringExpected(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079707, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.stringExpected", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM stringExpected(CompletionStatus cs) {
        return this.stringExpected(cs, null);
    }

    public BAD_PARAM stringExpected(Throwable t2) {
        return this.stringExpected(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM stringExpected() {
        return this.stringExpected(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM invalidTypecodeKind(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_PARAM exc = new BAD_PARAM(1398079708, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.invalidTypecodeKind", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM invalidTypecodeKind(CompletionStatus cs, Object arg0) {
        return this.invalidTypecodeKind(cs, null, arg0);
    }

    public BAD_PARAM invalidTypecodeKind(Throwable t2, Object arg0) {
        return this.invalidTypecodeKind(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_PARAM invalidTypecodeKind(Object arg0) {
        return this.invalidTypecodeKind(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_PARAM socketFactoryAndContactInfoListAtSameTime(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079709, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.socketFactoryAndContactInfoListAtSameTime", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM socketFactoryAndContactInfoListAtSameTime(CompletionStatus cs) {
        return this.socketFactoryAndContactInfoListAtSameTime(cs, null);
    }

    public BAD_PARAM socketFactoryAndContactInfoListAtSameTime(Throwable t2) {
        return this.socketFactoryAndContactInfoListAtSameTime(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM socketFactoryAndContactInfoListAtSameTime() {
        return this.socketFactoryAndContactInfoListAtSameTime(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM acceptorsAndLegacySocketFactoryAtSameTime(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079710, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.acceptorsAndLegacySocketFactoryAtSameTime", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM acceptorsAndLegacySocketFactoryAtSameTime(CompletionStatus cs) {
        return this.acceptorsAndLegacySocketFactoryAtSameTime(cs, null);
    }

    public BAD_PARAM acceptorsAndLegacySocketFactoryAtSameTime(Throwable t2) {
        return this.acceptorsAndLegacySocketFactoryAtSameTime(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM acceptorsAndLegacySocketFactoryAtSameTime() {
        return this.acceptorsAndLegacySocketFactoryAtSameTime(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM badOrbForServant(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398079711, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badOrbForServant", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM badOrbForServant(CompletionStatus cs) {
        return this.badOrbForServant(cs, null);
    }

    public BAD_PARAM badOrbForServant(Throwable t2) {
        return this.badOrbForServant(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM badOrbForServant() {
        return this.badOrbForServant(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM invalidRequestPartitioningPolicyValue(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        BAD_PARAM exc = new BAD_PARAM(1398079712, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "ORBUTIL.invalidRequestPartitioningPolicyValue", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM invalidRequestPartitioningPolicyValue(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.invalidRequestPartitioningPolicyValue(cs, null, arg0, arg1, arg2);
    }

    public BAD_PARAM invalidRequestPartitioningPolicyValue(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.invalidRequestPartitioningPolicyValue(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public BAD_PARAM invalidRequestPartitioningPolicyValue(Object arg0, Object arg1, Object arg2) {
        return this.invalidRequestPartitioningPolicyValue(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public BAD_PARAM invalidRequestPartitioningComponentValue(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        BAD_PARAM exc = new BAD_PARAM(1398079713, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "ORBUTIL.invalidRequestPartitioningComponentValue", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM invalidRequestPartitioningComponentValue(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.invalidRequestPartitioningComponentValue(cs, null, arg0, arg1, arg2);
    }

    public BAD_PARAM invalidRequestPartitioningComponentValue(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.invalidRequestPartitioningComponentValue(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public BAD_PARAM invalidRequestPartitioningComponentValue(Object arg0, Object arg1, Object arg2) {
        return this.invalidRequestPartitioningComponentValue(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public BAD_PARAM invalidRequestPartitioningId(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        BAD_PARAM exc = new BAD_PARAM(1398079714, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "ORBUTIL.invalidRequestPartitioningId", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM invalidRequestPartitioningId(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.invalidRequestPartitioningId(cs, null, arg0, arg1, arg2);
    }

    public BAD_PARAM invalidRequestPartitioningId(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.invalidRequestPartitioningId(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public BAD_PARAM invalidRequestPartitioningId(Object arg0, Object arg1, Object arg2) {
        return this.invalidRequestPartitioningId(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public BAD_PARAM errorInSettingDynamicStubFactoryFactory(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_PARAM exc = new BAD_PARAM(1398079715, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.FINE, "ORBUTIL.errorInSettingDynamicStubFactoryFactory", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM errorInSettingDynamicStubFactoryFactory(CompletionStatus cs, Object arg0) {
        return this.errorInSettingDynamicStubFactoryFactory(cs, null, arg0);
    }

    public BAD_PARAM errorInSettingDynamicStubFactoryFactory(Throwable t2, Object arg0) {
        return this.errorInSettingDynamicStubFactoryFactory(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_PARAM errorInSettingDynamicStubFactoryFactory(Object arg0) {
        return this.errorInSettingDynamicStubFactoryFactory(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_INV_ORDER dsimethodNotcalled(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.dsimethodNotcalled", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER dsimethodNotcalled(CompletionStatus cs) {
        return this.dsimethodNotcalled(cs, null);
    }

    public BAD_INV_ORDER dsimethodNotcalled(Throwable t2) {
        return this.dsimethodNotcalled(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER dsimethodNotcalled() {
        return this.dsimethodNotcalled(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER argumentsCalledMultiple(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.argumentsCalledMultiple", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER argumentsCalledMultiple(CompletionStatus cs) {
        return this.argumentsCalledMultiple(cs, null);
    }

    public BAD_INV_ORDER argumentsCalledMultiple(Throwable t2) {
        return this.argumentsCalledMultiple(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER argumentsCalledMultiple() {
        return this.argumentsCalledMultiple(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER argumentsCalledAfterException(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.argumentsCalledAfterException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER argumentsCalledAfterException(CompletionStatus cs) {
        return this.argumentsCalledAfterException(cs, null);
    }

    public BAD_INV_ORDER argumentsCalledAfterException(Throwable t2) {
        return this.argumentsCalledAfterException(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER argumentsCalledAfterException() {
        return this.argumentsCalledAfterException(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER argumentsCalledNullArgs(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1398079692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.argumentsCalledNullArgs", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER argumentsCalledNullArgs(CompletionStatus cs) {
        return this.argumentsCalledNullArgs(cs, null);
    }

    public BAD_INV_ORDER argumentsCalledNullArgs(Throwable t2) {
        return this.argumentsCalledNullArgs(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER argumentsCalledNullArgs() {
        return this.argumentsCalledNullArgs(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER argumentsNotCalled(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1398079693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.argumentsNotCalled", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER argumentsNotCalled(CompletionStatus cs) {
        return this.argumentsNotCalled(cs, null);
    }

    public BAD_INV_ORDER argumentsNotCalled(Throwable t2) {
        return this.argumentsNotCalled(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER argumentsNotCalled() {
        return this.argumentsNotCalled(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER setResultCalledMultiple(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1398079694, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.setResultCalledMultiple", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER setResultCalledMultiple(CompletionStatus cs) {
        return this.setResultCalledMultiple(cs, null);
    }

    public BAD_INV_ORDER setResultCalledMultiple(Throwable t2) {
        return this.setResultCalledMultiple(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER setResultCalledMultiple() {
        return this.setResultCalledMultiple(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER setResultAfterException(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1398079695, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.setResultAfterException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER setResultAfterException(CompletionStatus cs) {
        return this.setResultAfterException(cs, null);
    }

    public BAD_INV_ORDER setResultAfterException(Throwable t2) {
        return this.setResultAfterException(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER setResultAfterException() {
        return this.setResultAfterException(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_INV_ORDER setResultCalledNullArgs(CompletionStatus cs, Throwable t2) {
        BAD_INV_ORDER exc = new BAD_INV_ORDER(1398079696, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.setResultCalledNullArgs", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_INV_ORDER setResultCalledNullArgs(CompletionStatus cs) {
        return this.setResultCalledNullArgs(cs, null);
    }

    public BAD_INV_ORDER setResultCalledNullArgs(Throwable t2) {
        return this.setResultCalledNullArgs(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_INV_ORDER setResultCalledNullArgs() {
        return this.setResultCalledNullArgs(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_TYPECODE badRemoteTypecode(CompletionStatus cs, Throwable t2) {
        BAD_TYPECODE exc = new BAD_TYPECODE(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badRemoteTypecode", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_TYPECODE badRemoteTypecode(CompletionStatus cs) {
        return this.badRemoteTypecode(cs, null);
    }

    public BAD_TYPECODE badRemoteTypecode(Throwable t2) {
        return this.badRemoteTypecode(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_TYPECODE badRemoteTypecode() {
        return this.badRemoteTypecode(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_TYPECODE unresolvedRecursiveTypecode(CompletionStatus cs, Throwable t2) {
        BAD_TYPECODE exc = new BAD_TYPECODE(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unresolvedRecursiveTypecode", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_TYPECODE unresolvedRecursiveTypecode(CompletionStatus cs) {
        return this.unresolvedRecursiveTypecode(cs, null);
    }

    public BAD_TYPECODE unresolvedRecursiveTypecode(Throwable t2) {
        return this.unresolvedRecursiveTypecode(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_TYPECODE unresolvedRecursiveTypecode() {
        return this.unresolvedRecursiveTypecode(CompletionStatus.COMPLETED_NO, null);
    }

    public COMM_FAILURE connectFailure(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "ORBUTIL.connectFailure", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE connectFailure(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.connectFailure(cs, null, arg0, arg1, arg2);
    }

    public COMM_FAILURE connectFailure(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.connectFailure(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public COMM_FAILURE connectFailure(Object arg0, Object arg1, Object arg2) {
        return this.connectFailure(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public COMM_FAILURE connectionCloseRebind(CompletionStatus cs, Throwable t2) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.connectionCloseRebind", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE connectionCloseRebind(CompletionStatus cs) {
        return this.connectionCloseRebind(cs, null);
    }

    public COMM_FAILURE connectionCloseRebind(Throwable t2) {
        return this.connectionCloseRebind(CompletionStatus.COMPLETED_NO, t2);
    }

    public COMM_FAILURE connectionCloseRebind() {
        return this.connectionCloseRebind(CompletionStatus.COMPLETED_NO, null);
    }

    public COMM_FAILURE writeErrorSend(CompletionStatus cs, Throwable t2) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.writeErrorSend", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE writeErrorSend(CompletionStatus cs) {
        return this.writeErrorSend(cs, null);
    }

    public COMM_FAILURE writeErrorSend(Throwable t2) {
        return this.writeErrorSend(CompletionStatus.COMPLETED_NO, t2);
    }

    public COMM_FAILURE writeErrorSend() {
        return this.writeErrorSend(CompletionStatus.COMPLETED_NO, null);
    }

    public COMM_FAILURE getPropertiesError(CompletionStatus cs, Throwable t2) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.getPropertiesError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE getPropertiesError(CompletionStatus cs) {
        return this.getPropertiesError(cs, null);
    }

    public COMM_FAILURE getPropertiesError(Throwable t2) {
        return this.getPropertiesError(CompletionStatus.COMPLETED_NO, t2);
    }

    public COMM_FAILURE getPropertiesError() {
        return this.getPropertiesError(CompletionStatus.COMPLETED_NO, null);
    }

    public COMM_FAILURE bootstrapServerNotAvail(CompletionStatus cs, Throwable t2) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.bootstrapServerNotAvail", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE bootstrapServerNotAvail(CompletionStatus cs) {
        return this.bootstrapServerNotAvail(cs, null);
    }

    public COMM_FAILURE bootstrapServerNotAvail(Throwable t2) {
        return this.bootstrapServerNotAvail(CompletionStatus.COMPLETED_NO, t2);
    }

    public COMM_FAILURE bootstrapServerNotAvail() {
        return this.bootstrapServerNotAvail(CompletionStatus.COMPLETED_NO, null);
    }

    public COMM_FAILURE invokeError(CompletionStatus cs, Throwable t2) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079694, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.invokeError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE invokeError(CompletionStatus cs) {
        return this.invokeError(cs, null);
    }

    public COMM_FAILURE invokeError(Throwable t2) {
        return this.invokeError(CompletionStatus.COMPLETED_NO, t2);
    }

    public COMM_FAILURE invokeError() {
        return this.invokeError(CompletionStatus.COMPLETED_NO, null);
    }

    public COMM_FAILURE defaultCreateServerSocketGivenNonIiopClearText(CompletionStatus cs, Throwable t2, Object arg0) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079695, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.defaultCreateServerSocketGivenNonIiopClearText", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE defaultCreateServerSocketGivenNonIiopClearText(CompletionStatus cs, Object arg0) {
        return this.defaultCreateServerSocketGivenNonIiopClearText(cs, null, arg0);
    }

    public COMM_FAILURE defaultCreateServerSocketGivenNonIiopClearText(Throwable t2, Object arg0) {
        return this.defaultCreateServerSocketGivenNonIiopClearText(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public COMM_FAILURE defaultCreateServerSocketGivenNonIiopClearText(Object arg0) {
        return this.defaultCreateServerSocketGivenNonIiopClearText(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public COMM_FAILURE connectionAbort(CompletionStatus cs, Throwable t2) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079696, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.connectionAbort", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE connectionAbort(CompletionStatus cs) {
        return this.connectionAbort(cs, null);
    }

    public COMM_FAILURE connectionAbort(Throwable t2) {
        return this.connectionAbort(CompletionStatus.COMPLETED_NO, t2);
    }

    public COMM_FAILURE connectionAbort() {
        return this.connectionAbort(CompletionStatus.COMPLETED_NO, null);
    }

    public COMM_FAILURE connectionRebind(CompletionStatus cs, Throwable t2) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079697, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.connectionRebind", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE connectionRebind(CompletionStatus cs) {
        return this.connectionRebind(cs, null);
    }

    public COMM_FAILURE connectionRebind(Throwable t2) {
        return this.connectionRebind(CompletionStatus.COMPLETED_NO, t2);
    }

    public COMM_FAILURE connectionRebind() {
        return this.connectionRebind(CompletionStatus.COMPLETED_NO, null);
    }

    public COMM_FAILURE recvMsgError(CompletionStatus cs, Throwable t2) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079698, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.recvMsgError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE recvMsgError(CompletionStatus cs) {
        return this.recvMsgError(cs, null);
    }

    public COMM_FAILURE recvMsgError(Throwable t2) {
        return this.recvMsgError(CompletionStatus.COMPLETED_NO, t2);
    }

    public COMM_FAILURE recvMsgError() {
        return this.recvMsgError(CompletionStatus.COMPLETED_NO, null);
    }

    public COMM_FAILURE ioexceptionWhenReadingConnection(CompletionStatus cs, Throwable t2) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079699, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.ioexceptionWhenReadingConnection", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE ioexceptionWhenReadingConnection(CompletionStatus cs) {
        return this.ioexceptionWhenReadingConnection(cs, null);
    }

    public COMM_FAILURE ioexceptionWhenReadingConnection(Throwable t2) {
        return this.ioexceptionWhenReadingConnection(CompletionStatus.COMPLETED_NO, t2);
    }

    public COMM_FAILURE ioexceptionWhenReadingConnection() {
        return this.ioexceptionWhenReadingConnection(CompletionStatus.COMPLETED_NO, null);
    }

    public COMM_FAILURE selectionKeyInvalid(CompletionStatus cs, Throwable t2, Object arg0) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079700, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.FINE, "ORBUTIL.selectionKeyInvalid", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE selectionKeyInvalid(CompletionStatus cs, Object arg0) {
        return this.selectionKeyInvalid(cs, null, arg0);
    }

    public COMM_FAILURE selectionKeyInvalid(Throwable t2, Object arg0) {
        return this.selectionKeyInvalid(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public COMM_FAILURE selectionKeyInvalid(Object arg0) {
        return this.selectionKeyInvalid(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public COMM_FAILURE exceptionInAccept(CompletionStatus cs, Throwable t2, Object arg0) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079701, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.FINE, "ORBUTIL.exceptionInAccept", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE exceptionInAccept(CompletionStatus cs, Object arg0) {
        return this.exceptionInAccept(cs, null, arg0);
    }

    public COMM_FAILURE exceptionInAccept(Throwable t2, Object arg0) {
        return this.exceptionInAccept(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public COMM_FAILURE exceptionInAccept(Object arg0) {
        return this.exceptionInAccept(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public COMM_FAILURE securityExceptionInAccept(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079702, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.FINE, "ORBUTIL.securityExceptionInAccept", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE securityExceptionInAccept(CompletionStatus cs, Object arg0, Object arg1) {
        return this.securityExceptionInAccept(cs, null, arg0, arg1);
    }

    public COMM_FAILURE securityExceptionInAccept(Throwable t2, Object arg0, Object arg1) {
        return this.securityExceptionInAccept(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public COMM_FAILURE securityExceptionInAccept(Object arg0, Object arg1) {
        return this.securityExceptionInAccept(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public COMM_FAILURE transportReadTimeoutExceeded(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2, Object arg3) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079703, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2, arg3};
            this.doLog(Level.WARNING, "ORBUTIL.transportReadTimeoutExceeded", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE transportReadTimeoutExceeded(CompletionStatus cs, Object arg0, Object arg1, Object arg2, Object arg3) {
        return this.transportReadTimeoutExceeded(cs, null, arg0, arg1, arg2, arg3);
    }

    public COMM_FAILURE transportReadTimeoutExceeded(Throwable t2, Object arg0, Object arg1, Object arg2, Object arg3) {
        return this.transportReadTimeoutExceeded(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2, arg3);
    }

    public COMM_FAILURE transportReadTimeoutExceeded(Object arg0, Object arg1, Object arg2, Object arg3) {
        return this.transportReadTimeoutExceeded(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2, arg3);
    }

    public COMM_FAILURE createListenerFailed(CompletionStatus cs, Throwable t2, Object arg0) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079704, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.SEVERE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.SEVERE, "ORBUTIL.createListenerFailed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE createListenerFailed(CompletionStatus cs, Object arg0) {
        return this.createListenerFailed(cs, null, arg0);
    }

    public COMM_FAILURE createListenerFailed(Throwable t2, Object arg0) {
        return this.createListenerFailed(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public COMM_FAILURE createListenerFailed(Object arg0) {
        return this.createListenerFailed(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public COMM_FAILURE bufferReadManagerTimeout(CompletionStatus cs, Throwable t2) {
        COMM_FAILURE exc = new COMM_FAILURE(1398079705, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.bufferReadManagerTimeout", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public COMM_FAILURE bufferReadManagerTimeout(CompletionStatus cs) {
        return this.bufferReadManagerTimeout(cs, null);
    }

    public COMM_FAILURE bufferReadManagerTimeout(Throwable t2) {
        return this.bufferReadManagerTimeout(CompletionStatus.COMPLETED_NO, t2);
    }

    public COMM_FAILURE bufferReadManagerTimeout() {
        return this.bufferReadManagerTimeout(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION badStringifiedIorLen(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badStringifiedIorLen", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badStringifiedIorLen(CompletionStatus cs) {
        return this.badStringifiedIorLen(cs, null);
    }

    public DATA_CONVERSION badStringifiedIorLen(Throwable t2) {
        return this.badStringifiedIorLen(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION badStringifiedIorLen() {
        return this.badStringifiedIorLen(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION badStringifiedIor(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badStringifiedIor", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badStringifiedIor(CompletionStatus cs) {
        return this.badStringifiedIor(cs, null);
    }

    public DATA_CONVERSION badStringifiedIor(Throwable t2) {
        return this.badStringifiedIor(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION badStringifiedIor() {
        return this.badStringifiedIor(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION badModifier(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badModifier", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badModifier(CompletionStatus cs) {
        return this.badModifier(cs, null);
    }

    public DATA_CONVERSION badModifier(Throwable t2) {
        return this.badModifier(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION badModifier() {
        return this.badModifier(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION codesetIncompatible(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.codesetIncompatible", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION codesetIncompatible(CompletionStatus cs) {
        return this.codesetIncompatible(cs, null);
    }

    public DATA_CONVERSION codesetIncompatible(Throwable t2) {
        return this.codesetIncompatible(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION codesetIncompatible() {
        return this.codesetIncompatible(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION badHexDigit(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badHexDigit", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badHexDigit(CompletionStatus cs) {
        return this.badHexDigit(cs, null);
    }

    public DATA_CONVERSION badHexDigit(Throwable t2) {
        return this.badHexDigit(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION badHexDigit() {
        return this.badHexDigit(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION badUnicodePair(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079694, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badUnicodePair", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badUnicodePair(CompletionStatus cs) {
        return this.badUnicodePair(cs, null);
    }

    public DATA_CONVERSION badUnicodePair(Throwable t2) {
        return this.badUnicodePair(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION badUnicodePair() {
        return this.badUnicodePair(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION btcResultMoreThanOneChar(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079695, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.btcResultMoreThanOneChar", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION btcResultMoreThanOneChar(CompletionStatus cs) {
        return this.btcResultMoreThanOneChar(cs, null);
    }

    public DATA_CONVERSION btcResultMoreThanOneChar(Throwable t2) {
        return this.btcResultMoreThanOneChar(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION btcResultMoreThanOneChar() {
        return this.btcResultMoreThanOneChar(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION badCodesetsFromClient(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079696, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badCodesetsFromClient", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badCodesetsFromClient(CompletionStatus cs) {
        return this.badCodesetsFromClient(cs, null);
    }

    public DATA_CONVERSION badCodesetsFromClient(Throwable t2) {
        return this.badCodesetsFromClient(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION badCodesetsFromClient() {
        return this.badCodesetsFromClient(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION invalidSingleCharCtb(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079697, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.invalidSingleCharCtb", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION invalidSingleCharCtb(CompletionStatus cs) {
        return this.invalidSingleCharCtb(cs, null);
    }

    public DATA_CONVERSION invalidSingleCharCtb(Throwable t2) {
        return this.invalidSingleCharCtb(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION invalidSingleCharCtb() {
        return this.invalidSingleCharCtb(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION badGiop11Ctb(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079698, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badGiop11Ctb", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badGiop11Ctb(CompletionStatus cs) {
        return this.badGiop11Ctb(cs, null);
    }

    public DATA_CONVERSION badGiop11Ctb(Throwable t2) {
        return this.badGiop11Ctb(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION badGiop11Ctb() {
        return this.badGiop11Ctb(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION badSequenceBounds(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079700, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.badSequenceBounds", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badSequenceBounds(CompletionStatus cs, Object arg0, Object arg1) {
        return this.badSequenceBounds(cs, null, arg0, arg1);
    }

    public DATA_CONVERSION badSequenceBounds(Throwable t2, Object arg0, Object arg1) {
        return this.badSequenceBounds(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public DATA_CONVERSION badSequenceBounds(Object arg0, Object arg1) {
        return this.badSequenceBounds(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public DATA_CONVERSION illegalSocketFactoryType(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079701, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.illegalSocketFactoryType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION illegalSocketFactoryType(CompletionStatus cs, Object arg0) {
        return this.illegalSocketFactoryType(cs, null, arg0);
    }

    public DATA_CONVERSION illegalSocketFactoryType(Throwable t2, Object arg0) {
        return this.illegalSocketFactoryType(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION illegalSocketFactoryType(Object arg0) {
        return this.illegalSocketFactoryType(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION badCustomSocketFactory(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079702, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badCustomSocketFactory", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badCustomSocketFactory(CompletionStatus cs, Object arg0) {
        return this.badCustomSocketFactory(cs, null, arg0);
    }

    public DATA_CONVERSION badCustomSocketFactory(Throwable t2, Object arg0) {
        return this.badCustomSocketFactory(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION badCustomSocketFactory(Object arg0) {
        return this.badCustomSocketFactory(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION fragmentSizeMinimum(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079703, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.fragmentSizeMinimum", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION fragmentSizeMinimum(CompletionStatus cs, Object arg0, Object arg1) {
        return this.fragmentSizeMinimum(cs, null, arg0, arg1);
    }

    public DATA_CONVERSION fragmentSizeMinimum(Throwable t2, Object arg0, Object arg1) {
        return this.fragmentSizeMinimum(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public DATA_CONVERSION fragmentSizeMinimum(Object arg0, Object arg1) {
        return this.fragmentSizeMinimum(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public DATA_CONVERSION fragmentSizeDiv(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079704, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.fragmentSizeDiv", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION fragmentSizeDiv(CompletionStatus cs, Object arg0, Object arg1) {
        return this.fragmentSizeDiv(cs, null, arg0, arg1);
    }

    public DATA_CONVERSION fragmentSizeDiv(Throwable t2, Object arg0, Object arg1) {
        return this.fragmentSizeDiv(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public DATA_CONVERSION fragmentSizeDiv(Object arg0, Object arg1) {
        return this.fragmentSizeDiv(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public DATA_CONVERSION orbInitializerFailure(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079705, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.orbInitializerFailure", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION orbInitializerFailure(CompletionStatus cs, Object arg0) {
        return this.orbInitializerFailure(cs, null, arg0);
    }

    public DATA_CONVERSION orbInitializerFailure(Throwable t2, Object arg0) {
        return this.orbInitializerFailure(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION orbInitializerFailure(Object arg0) {
        return this.orbInitializerFailure(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION orbInitializerType(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079706, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.orbInitializerType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION orbInitializerType(CompletionStatus cs, Object arg0) {
        return this.orbInitializerType(cs, null, arg0);
    }

    public DATA_CONVERSION orbInitializerType(Throwable t2, Object arg0) {
        return this.orbInitializerType(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION orbInitializerType(Object arg0) {
        return this.orbInitializerType(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION orbInitialreferenceSyntax(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079707, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.orbInitialreferenceSyntax", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION orbInitialreferenceSyntax(CompletionStatus cs) {
        return this.orbInitialreferenceSyntax(cs, null);
    }

    public DATA_CONVERSION orbInitialreferenceSyntax(Throwable t2) {
        return this.orbInitialreferenceSyntax(CompletionStatus.COMPLETED_NO, t2);
    }

    public DATA_CONVERSION orbInitialreferenceSyntax() {
        return this.orbInitialreferenceSyntax(CompletionStatus.COMPLETED_NO, null);
    }

    public DATA_CONVERSION acceptorInstantiationFailure(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079708, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.acceptorInstantiationFailure", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION acceptorInstantiationFailure(CompletionStatus cs, Object arg0) {
        return this.acceptorInstantiationFailure(cs, null, arg0);
    }

    public DATA_CONVERSION acceptorInstantiationFailure(Throwable t2, Object arg0) {
        return this.acceptorInstantiationFailure(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION acceptorInstantiationFailure(Object arg0) {
        return this.acceptorInstantiationFailure(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION acceptorInstantiationTypeFailure(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079709, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.acceptorInstantiationTypeFailure", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION acceptorInstantiationTypeFailure(CompletionStatus cs, Object arg0) {
        return this.acceptorInstantiationTypeFailure(cs, null, arg0);
    }

    public DATA_CONVERSION acceptorInstantiationTypeFailure(Throwable t2, Object arg0) {
        return this.acceptorInstantiationTypeFailure(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION acceptorInstantiationTypeFailure(Object arg0) {
        return this.acceptorInstantiationTypeFailure(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION illegalContactInfoListFactoryType(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079710, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.illegalContactInfoListFactoryType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION illegalContactInfoListFactoryType(CompletionStatus cs, Object arg0) {
        return this.illegalContactInfoListFactoryType(cs, null, arg0);
    }

    public DATA_CONVERSION illegalContactInfoListFactoryType(Throwable t2, Object arg0) {
        return this.illegalContactInfoListFactoryType(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION illegalContactInfoListFactoryType(Object arg0) {
        return this.illegalContactInfoListFactoryType(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION badContactInfoListFactory(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079711, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badContactInfoListFactory", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badContactInfoListFactory(CompletionStatus cs, Object arg0) {
        return this.badContactInfoListFactory(cs, null, arg0);
    }

    public DATA_CONVERSION badContactInfoListFactory(Throwable t2, Object arg0) {
        return this.badContactInfoListFactory(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION badContactInfoListFactory(Object arg0) {
        return this.badContactInfoListFactory(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION illegalIorToSocketInfoType(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079712, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.illegalIorToSocketInfoType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION illegalIorToSocketInfoType(CompletionStatus cs, Object arg0) {
        return this.illegalIorToSocketInfoType(cs, null, arg0);
    }

    public DATA_CONVERSION illegalIorToSocketInfoType(Throwable t2, Object arg0) {
        return this.illegalIorToSocketInfoType(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION illegalIorToSocketInfoType(Object arg0) {
        return this.illegalIorToSocketInfoType(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION badCustomIorToSocketInfo(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079713, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badCustomIorToSocketInfo", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badCustomIorToSocketInfo(CompletionStatus cs, Object arg0) {
        return this.badCustomIorToSocketInfo(cs, null, arg0);
    }

    public DATA_CONVERSION badCustomIorToSocketInfo(Throwable t2, Object arg0) {
        return this.badCustomIorToSocketInfo(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION badCustomIorToSocketInfo(Object arg0) {
        return this.badCustomIorToSocketInfo(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION illegalIiopPrimaryToContactInfoType(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079714, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.illegalIiopPrimaryToContactInfoType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION illegalIiopPrimaryToContactInfoType(CompletionStatus cs, Object arg0) {
        return this.illegalIiopPrimaryToContactInfoType(cs, null, arg0);
    }

    public DATA_CONVERSION illegalIiopPrimaryToContactInfoType(Throwable t2, Object arg0) {
        return this.illegalIiopPrimaryToContactInfoType(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION illegalIiopPrimaryToContactInfoType(Object arg0) {
        return this.illegalIiopPrimaryToContactInfoType(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION badCustomIiopPrimaryToContactInfo(CompletionStatus cs, Throwable t2, Object arg0) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398079715, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badCustomIiopPrimaryToContactInfo", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public DATA_CONVERSION badCustomIiopPrimaryToContactInfo(CompletionStatus cs, Object arg0) {
        return this.badCustomIiopPrimaryToContactInfo(cs, null, arg0);
    }

    public DATA_CONVERSION badCustomIiopPrimaryToContactInfo(Throwable t2, Object arg0) {
        return this.badCustomIiopPrimaryToContactInfo(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public DATA_CONVERSION badCustomIiopPrimaryToContactInfo(Object arg0) {
        return this.badCustomIiopPrimaryToContactInfo(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INV_OBJREF badCorbalocString(CompletionStatus cs, Throwable t2) {
        INV_OBJREF exc = new INV_OBJREF(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badCorbalocString", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INV_OBJREF badCorbalocString(CompletionStatus cs) {
        return this.badCorbalocString(cs, null);
    }

    public INV_OBJREF badCorbalocString(Throwable t2) {
        return this.badCorbalocString(CompletionStatus.COMPLETED_NO, t2);
    }

    public INV_OBJREF badCorbalocString() {
        return this.badCorbalocString(CompletionStatus.COMPLETED_NO, null);
    }

    public INV_OBJREF noProfilePresent(CompletionStatus cs, Throwable t2) {
        INV_OBJREF exc = new INV_OBJREF(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.noProfilePresent", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INV_OBJREF noProfilePresent(CompletionStatus cs) {
        return this.noProfilePresent(cs, null);
    }

    public INV_OBJREF noProfilePresent(Throwable t2) {
        return this.noProfilePresent(CompletionStatus.COMPLETED_NO, t2);
    }

    public INV_OBJREF noProfilePresent() {
        return this.noProfilePresent(CompletionStatus.COMPLETED_NO, null);
    }

    public INITIALIZE cannotCreateOrbidDb(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.cannotCreateOrbidDb", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INITIALIZE cannotCreateOrbidDb(CompletionStatus cs) {
        return this.cannotCreateOrbidDb(cs, null);
    }

    public INITIALIZE cannotCreateOrbidDb(Throwable t2) {
        return this.cannotCreateOrbidDb(CompletionStatus.COMPLETED_NO, t2);
    }

    public INITIALIZE cannotCreateOrbidDb() {
        return this.cannotCreateOrbidDb(CompletionStatus.COMPLETED_NO, null);
    }

    public INITIALIZE cannotReadOrbidDb(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.cannotReadOrbidDb", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INITIALIZE cannotReadOrbidDb(CompletionStatus cs) {
        return this.cannotReadOrbidDb(cs, null);
    }

    public INITIALIZE cannotReadOrbidDb(Throwable t2) {
        return this.cannotReadOrbidDb(CompletionStatus.COMPLETED_NO, t2);
    }

    public INITIALIZE cannotReadOrbidDb() {
        return this.cannotReadOrbidDb(CompletionStatus.COMPLETED_NO, null);
    }

    public INITIALIZE cannotWriteOrbidDb(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.cannotWriteOrbidDb", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INITIALIZE cannotWriteOrbidDb(CompletionStatus cs) {
        return this.cannotWriteOrbidDb(cs, null);
    }

    public INITIALIZE cannotWriteOrbidDb(Throwable t2) {
        return this.cannotWriteOrbidDb(CompletionStatus.COMPLETED_NO, t2);
    }

    public INITIALIZE cannotWriteOrbidDb() {
        return this.cannotWriteOrbidDb(CompletionStatus.COMPLETED_NO, null);
    }

    public INITIALIZE getServerPortCalledBeforeEndpointsInitialized(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1398079692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.getServerPortCalledBeforeEndpointsInitialized", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INITIALIZE getServerPortCalledBeforeEndpointsInitialized(CompletionStatus cs) {
        return this.getServerPortCalledBeforeEndpointsInitialized(cs, null);
    }

    public INITIALIZE getServerPortCalledBeforeEndpointsInitialized(Throwable t2) {
        return this.getServerPortCalledBeforeEndpointsInitialized(CompletionStatus.COMPLETED_NO, t2);
    }

    public INITIALIZE getServerPortCalledBeforeEndpointsInitialized() {
        return this.getServerPortCalledBeforeEndpointsInitialized(CompletionStatus.COMPLETED_NO, null);
    }

    public INITIALIZE persistentServerportNotSet(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1398079693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.persistentServerportNotSet", parameters, ORBUtilSystemException.class, exc);
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

    public INITIALIZE persistentServeridNotSet(CompletionStatus cs, Throwable t2) {
        INITIALIZE exc = new INITIALIZE(1398079694, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.persistentServeridNotSet", parameters, ORBUtilSystemException.class, exc);
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

    public INTERNAL nonExistentOrbid(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.nonExistentOrbid", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL nonExistentOrbid(CompletionStatus cs) {
        return this.nonExistentOrbid(cs, null);
    }

    public INTERNAL nonExistentOrbid(Throwable t2) {
        return this.nonExistentOrbid(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL nonExistentOrbid() {
        return this.nonExistentOrbid(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL noServerSubcontract(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.noServerSubcontract", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL noServerSubcontract(CompletionStatus cs) {
        return this.noServerSubcontract(cs, null);
    }

    public INTERNAL noServerSubcontract(Throwable t2) {
        return this.noServerSubcontract(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL noServerSubcontract() {
        return this.noServerSubcontract(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL serverScTempSize(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.serverScTempSize", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL serverScTempSize(CompletionStatus cs) {
        return this.serverScTempSize(cs, null);
    }

    public INTERNAL serverScTempSize(Throwable t2) {
        return this.serverScTempSize(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL serverScTempSize() {
        return this.serverScTempSize(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL noClientScClass(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.noClientScClass", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL noClientScClass(CompletionStatus cs) {
        return this.noClientScClass(cs, null);
    }

    public INTERNAL noClientScClass(Throwable t2) {
        return this.noClientScClass(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL noClientScClass() {
        return this.noClientScClass(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL serverScNoIiopProfile(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.serverScNoIiopProfile", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL serverScNoIiopProfile(CompletionStatus cs) {
        return this.serverScNoIiopProfile(cs, null);
    }

    public INTERNAL serverScNoIiopProfile(Throwable t2) {
        return this.serverScNoIiopProfile(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL serverScNoIiopProfile() {
        return this.serverScNoIiopProfile(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL getSystemExReturnedNull(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079694, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.getSystemExReturnedNull", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL getSystemExReturnedNull(CompletionStatus cs) {
        return this.getSystemExReturnedNull(cs, null);
    }

    public INTERNAL getSystemExReturnedNull(Throwable t2) {
        return this.getSystemExReturnedNull(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL getSystemExReturnedNull() {
        return this.getSystemExReturnedNull(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL peekstringFailed(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079695, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.peekstringFailed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL peekstringFailed(CompletionStatus cs) {
        return this.peekstringFailed(cs, null);
    }

    public INTERNAL peekstringFailed(Throwable t2) {
        return this.peekstringFailed(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL peekstringFailed() {
        return this.peekstringFailed(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL getLocalHostFailed(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079696, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.getLocalHostFailed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL getLocalHostFailed(CompletionStatus cs) {
        return this.getLocalHostFailed(cs, null);
    }

    public INTERNAL getLocalHostFailed(Throwable t2) {
        return this.getLocalHostFailed(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL getLocalHostFailed() {
        return this.getLocalHostFailed(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL badLocateRequestStatus(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079698, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badLocateRequestStatus", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badLocateRequestStatus(CompletionStatus cs) {
        return this.badLocateRequestStatus(cs, null);
    }

    public INTERNAL badLocateRequestStatus(Throwable t2) {
        return this.badLocateRequestStatus(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badLocateRequestStatus() {
        return this.badLocateRequestStatus(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL stringifyWriteError(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079699, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.stringifyWriteError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL stringifyWriteError(CompletionStatus cs) {
        return this.stringifyWriteError(cs, null);
    }

    public INTERNAL stringifyWriteError(Throwable t2) {
        return this.stringifyWriteError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL stringifyWriteError() {
        return this.stringifyWriteError(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL badGiopRequestType(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079700, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badGiopRequestType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badGiopRequestType(CompletionStatus cs) {
        return this.badGiopRequestType(cs, null);
    }

    public INTERNAL badGiopRequestType(Throwable t2) {
        return this.badGiopRequestType(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badGiopRequestType() {
        return this.badGiopRequestType(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL errorUnmarshalingUserexc(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079701, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.errorUnmarshalingUserexc", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorUnmarshalingUserexc(CompletionStatus cs) {
        return this.errorUnmarshalingUserexc(cs, null);
    }

    public INTERNAL errorUnmarshalingUserexc(Throwable t2) {
        return this.errorUnmarshalingUserexc(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL errorUnmarshalingUserexc() {
        return this.errorUnmarshalingUserexc(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL requestdispatcherregistryError(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079702, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.requestdispatcherregistryError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL requestdispatcherregistryError(CompletionStatus cs) {
        return this.requestdispatcherregistryError(cs, null);
    }

    public INTERNAL requestdispatcherregistryError(Throwable t2) {
        return this.requestdispatcherregistryError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL requestdispatcherregistryError() {
        return this.requestdispatcherregistryError(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL locationforwardError(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079703, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.locationforwardError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL locationforwardError(CompletionStatus cs) {
        return this.locationforwardError(cs, null);
    }

    public INTERNAL locationforwardError(Throwable t2) {
        return this.locationforwardError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL locationforwardError() {
        return this.locationforwardError(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL wrongClientsc(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079704, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.wrongClientsc", parameters, ORBUtilSystemException.class, exc);
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

    public INTERNAL badServantReadObject(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079705, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badServantReadObject", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badServantReadObject(CompletionStatus cs) {
        return this.badServantReadObject(cs, null);
    }

    public INTERNAL badServantReadObject(Throwable t2) {
        return this.badServantReadObject(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badServantReadObject() {
        return this.badServantReadObject(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL multIiopProfNotSupported(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079706, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.multIiopProfNotSupported", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL multIiopProfNotSupported(CompletionStatus cs) {
        return this.multIiopProfNotSupported(cs, null);
    }

    public INTERNAL multIiopProfNotSupported(Throwable t2) {
        return this.multIiopProfNotSupported(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL multIiopProfNotSupported() {
        return this.multIiopProfNotSupported(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL giopMagicError(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079708, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.giopMagicError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL giopMagicError(CompletionStatus cs) {
        return this.giopMagicError(cs, null);
    }

    public INTERNAL giopMagicError(Throwable t2) {
        return this.giopMagicError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL giopMagicError() {
        return this.giopMagicError(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL giopVersionError(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079709, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.giopVersionError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL giopVersionError(CompletionStatus cs) {
        return this.giopVersionError(cs, null);
    }

    public INTERNAL giopVersionError(Throwable t2) {
        return this.giopVersionError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL giopVersionError() {
        return this.giopVersionError(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL illegalReplyStatus(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079710, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.illegalReplyStatus", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL illegalReplyStatus(CompletionStatus cs) {
        return this.illegalReplyStatus(cs, null);
    }

    public INTERNAL illegalReplyStatus(Throwable t2) {
        return this.illegalReplyStatus(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL illegalReplyStatus() {
        return this.illegalReplyStatus(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL illegalGiopMsgType(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079711, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.illegalGiopMsgType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL illegalGiopMsgType(CompletionStatus cs) {
        return this.illegalGiopMsgType(cs, null);
    }

    public INTERNAL illegalGiopMsgType(Throwable t2) {
        return this.illegalGiopMsgType(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL illegalGiopMsgType() {
        return this.illegalGiopMsgType(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL fragmentationDisallowed(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079712, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.fragmentationDisallowed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL fragmentationDisallowed(CompletionStatus cs) {
        return this.fragmentationDisallowed(cs, null);
    }

    public INTERNAL fragmentationDisallowed(Throwable t2) {
        return this.fragmentationDisallowed(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL fragmentationDisallowed() {
        return this.fragmentationDisallowed(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL badReplystatus(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079713, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badReplystatus", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badReplystatus(CompletionStatus cs) {
        return this.badReplystatus(cs, null);
    }

    public INTERNAL badReplystatus(Throwable t2) {
        return this.badReplystatus(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badReplystatus() {
        return this.badReplystatus(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL ctbConverterFailure(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079714, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.ctbConverterFailure", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL ctbConverterFailure(CompletionStatus cs) {
        return this.ctbConverterFailure(cs, null);
    }

    public INTERNAL ctbConverterFailure(Throwable t2) {
        return this.ctbConverterFailure(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL ctbConverterFailure() {
        return this.ctbConverterFailure(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL btcConverterFailure(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079715, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.btcConverterFailure", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL btcConverterFailure(CompletionStatus cs) {
        return this.btcConverterFailure(cs, null);
    }

    public INTERNAL btcConverterFailure(Throwable t2) {
        return this.btcConverterFailure(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL btcConverterFailure() {
        return this.btcConverterFailure(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL wcharArrayUnsupportedEncoding(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079716, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.wcharArrayUnsupportedEncoding", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL wcharArrayUnsupportedEncoding(CompletionStatus cs) {
        return this.wcharArrayUnsupportedEncoding(cs, null);
    }

    public INTERNAL wcharArrayUnsupportedEncoding(Throwable t2) {
        return this.wcharArrayUnsupportedEncoding(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL wcharArrayUnsupportedEncoding() {
        return this.wcharArrayUnsupportedEncoding(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL illegalTargetAddressDisposition(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079717, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.illegalTargetAddressDisposition", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL illegalTargetAddressDisposition(CompletionStatus cs) {
        return this.illegalTargetAddressDisposition(cs, null);
    }

    public INTERNAL illegalTargetAddressDisposition(Throwable t2) {
        return this.illegalTargetAddressDisposition(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL illegalTargetAddressDisposition() {
        return this.illegalTargetAddressDisposition(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL nullReplyInGetAddrDisposition(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079718, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.nullReplyInGetAddrDisposition", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL nullReplyInGetAddrDisposition(CompletionStatus cs) {
        return this.nullReplyInGetAddrDisposition(cs, null);
    }

    public INTERNAL nullReplyInGetAddrDisposition(Throwable t2) {
        return this.nullReplyInGetAddrDisposition(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL nullReplyInGetAddrDisposition() {
        return this.nullReplyInGetAddrDisposition(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL orbTargetAddrPreferenceInExtractObjectkeyInvalid(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079719, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.orbTargetAddrPreferenceInExtractObjectkeyInvalid", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL orbTargetAddrPreferenceInExtractObjectkeyInvalid(CompletionStatus cs) {
        return this.orbTargetAddrPreferenceInExtractObjectkeyInvalid(cs, null);
    }

    public INTERNAL orbTargetAddrPreferenceInExtractObjectkeyInvalid(Throwable t2) {
        return this.orbTargetAddrPreferenceInExtractObjectkeyInvalid(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL orbTargetAddrPreferenceInExtractObjectkeyInvalid() {
        return this.orbTargetAddrPreferenceInExtractObjectkeyInvalid(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL invalidIsstreamedTckind(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079720, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.invalidIsstreamedTckind", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invalidIsstreamedTckind(CompletionStatus cs, Object arg0) {
        return this.invalidIsstreamedTckind(cs, null, arg0);
    }

    public INTERNAL invalidIsstreamedTckind(Throwable t2, Object arg0) {
        return this.invalidIsstreamedTckind(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL invalidIsstreamedTckind(Object arg0) {
        return this.invalidIsstreamedTckind(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL invalidJdk131PatchLevel(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079721, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.invalidJdk131PatchLevel", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invalidJdk131PatchLevel(CompletionStatus cs) {
        return this.invalidJdk131PatchLevel(cs, null);
    }

    public INTERNAL invalidJdk131PatchLevel(Throwable t2) {
        return this.invalidJdk131PatchLevel(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL invalidJdk131PatchLevel() {
        return this.invalidJdk131PatchLevel(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL svcctxUnmarshalError(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079722, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.svcctxUnmarshalError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL svcctxUnmarshalError(CompletionStatus cs) {
        return this.svcctxUnmarshalError(cs, null);
    }

    public INTERNAL svcctxUnmarshalError(Throwable t2) {
        return this.svcctxUnmarshalError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL svcctxUnmarshalError() {
        return this.svcctxUnmarshalError(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL nullIor(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079723, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.nullIor", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL nullIor(CompletionStatus cs) {
        return this.nullIor(cs, null);
    }

    public INTERNAL nullIor(Throwable t2) {
        return this.nullIor(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL nullIor() {
        return this.nullIor(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL unsupportedGiopVersion(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079724, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.unsupportedGiopVersion", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL unsupportedGiopVersion(CompletionStatus cs, Object arg0) {
        return this.unsupportedGiopVersion(cs, null, arg0);
    }

    public INTERNAL unsupportedGiopVersion(Throwable t2, Object arg0) {
        return this.unsupportedGiopVersion(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL unsupportedGiopVersion(Object arg0) {
        return this.unsupportedGiopVersion(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL applicationExceptionInSpecialMethod(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079725, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.applicationExceptionInSpecialMethod", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL applicationExceptionInSpecialMethod(CompletionStatus cs) {
        return this.applicationExceptionInSpecialMethod(cs, null);
    }

    public INTERNAL applicationExceptionInSpecialMethod(Throwable t2) {
        return this.applicationExceptionInSpecialMethod(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL applicationExceptionInSpecialMethod() {
        return this.applicationExceptionInSpecialMethod(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL statementNotReachable1(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079726, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.statementNotReachable1", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL statementNotReachable1(CompletionStatus cs) {
        return this.statementNotReachable1(cs, null);
    }

    public INTERNAL statementNotReachable1(Throwable t2) {
        return this.statementNotReachable1(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL statementNotReachable1() {
        return this.statementNotReachable1(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL statementNotReachable2(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079727, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.statementNotReachable2", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL statementNotReachable2(CompletionStatus cs) {
        return this.statementNotReachable2(cs, null);
    }

    public INTERNAL statementNotReachable2(Throwable t2) {
        return this.statementNotReachable2(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL statementNotReachable2() {
        return this.statementNotReachable2(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL statementNotReachable3(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079728, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.statementNotReachable3", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL statementNotReachable3(CompletionStatus cs) {
        return this.statementNotReachable3(cs, null);
    }

    public INTERNAL statementNotReachable3(Throwable t2) {
        return this.statementNotReachable3(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL statementNotReachable3() {
        return this.statementNotReachable3(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL statementNotReachable4(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079729, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.statementNotReachable4", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL statementNotReachable4(CompletionStatus cs) {
        return this.statementNotReachable4(cs, null);
    }

    public INTERNAL statementNotReachable4(Throwable t2) {
        return this.statementNotReachable4(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL statementNotReachable4() {
        return this.statementNotReachable4(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL statementNotReachable5(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079730, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.statementNotReachable5", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL statementNotReachable5(CompletionStatus cs) {
        return this.statementNotReachable5(cs, null);
    }

    public INTERNAL statementNotReachable5(Throwable t2) {
        return this.statementNotReachable5(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL statementNotReachable5() {
        return this.statementNotReachable5(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL statementNotReachable6(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079731, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.statementNotReachable6", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL statementNotReachable6(CompletionStatus cs) {
        return this.statementNotReachable6(cs, null);
    }

    public INTERNAL statementNotReachable6(Throwable t2) {
        return this.statementNotReachable6(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL statementNotReachable6() {
        return this.statementNotReachable6(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL unexpectedDiiException(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079732, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unexpectedDiiException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL unexpectedDiiException(CompletionStatus cs) {
        return this.unexpectedDiiException(cs, null);
    }

    public INTERNAL unexpectedDiiException(Throwable t2) {
        return this.unexpectedDiiException(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL unexpectedDiiException() {
        return this.unexpectedDiiException(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL methodShouldNotBeCalled(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079733, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.methodShouldNotBeCalled", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL methodShouldNotBeCalled(CompletionStatus cs) {
        return this.methodShouldNotBeCalled(cs, null);
    }

    public INTERNAL methodShouldNotBeCalled(Throwable t2) {
        return this.methodShouldNotBeCalled(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL methodShouldNotBeCalled() {
        return this.methodShouldNotBeCalled(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL cancelNotSupported(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079734, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.cancelNotSupported", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL cancelNotSupported(CompletionStatus cs) {
        return this.cancelNotSupported(cs, null);
    }

    public INTERNAL cancelNotSupported(Throwable t2) {
        return this.cancelNotSupported(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL cancelNotSupported() {
        return this.cancelNotSupported(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL emptyStackRunServantPostInvoke(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079735, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.emptyStackRunServantPostInvoke", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL emptyStackRunServantPostInvoke(CompletionStatus cs) {
        return this.emptyStackRunServantPostInvoke(cs, null);
    }

    public INTERNAL emptyStackRunServantPostInvoke(Throwable t2) {
        return this.emptyStackRunServantPostInvoke(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL emptyStackRunServantPostInvoke() {
        return this.emptyStackRunServantPostInvoke(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL problemWithExceptionTypecode(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079736, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.problemWithExceptionTypecode", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL problemWithExceptionTypecode(CompletionStatus cs) {
        return this.problemWithExceptionTypecode(cs, null);
    }

    public INTERNAL problemWithExceptionTypecode(Throwable t2) {
        return this.problemWithExceptionTypecode(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL problemWithExceptionTypecode() {
        return this.problemWithExceptionTypecode(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL illegalSubcontractId(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079737, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.illegalSubcontractId", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL illegalSubcontractId(CompletionStatus cs, Object arg0) {
        return this.illegalSubcontractId(cs, null, arg0);
    }

    public INTERNAL illegalSubcontractId(Throwable t2, Object arg0) {
        return this.illegalSubcontractId(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL illegalSubcontractId(Object arg0) {
        return this.illegalSubcontractId(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL badSystemExceptionInLocateReply(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079738, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badSystemExceptionInLocateReply", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badSystemExceptionInLocateReply(CompletionStatus cs) {
        return this.badSystemExceptionInLocateReply(cs, null);
    }

    public INTERNAL badSystemExceptionInLocateReply(Throwable t2) {
        return this.badSystemExceptionInLocateReply(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badSystemExceptionInLocateReply() {
        return this.badSystemExceptionInLocateReply(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL badSystemExceptionInReply(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079739, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badSystemExceptionInReply", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badSystemExceptionInReply(CompletionStatus cs) {
        return this.badSystemExceptionInReply(cs, null);
    }

    public INTERNAL badSystemExceptionInReply(Throwable t2) {
        return this.badSystemExceptionInReply(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badSystemExceptionInReply() {
        return this.badSystemExceptionInReply(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL badCompletionStatusInLocateReply(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079740, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badCompletionStatusInLocateReply", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badCompletionStatusInLocateReply(CompletionStatus cs, Object arg0) {
        return this.badCompletionStatusInLocateReply(cs, null, arg0);
    }

    public INTERNAL badCompletionStatusInLocateReply(Throwable t2, Object arg0) {
        return this.badCompletionStatusInLocateReply(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL badCompletionStatusInLocateReply(Object arg0) {
        return this.badCompletionStatusInLocateReply(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL badCompletionStatusInReply(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079741, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badCompletionStatusInReply", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badCompletionStatusInReply(CompletionStatus cs, Object arg0) {
        return this.badCompletionStatusInReply(cs, null, arg0);
    }

    public INTERNAL badCompletionStatusInReply(Throwable t2, Object arg0) {
        return this.badCompletionStatusInReply(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL badCompletionStatusInReply(Object arg0) {
        return this.badCompletionStatusInReply(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL badkindCannotOccur(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079742, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badkindCannotOccur", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badkindCannotOccur(CompletionStatus cs) {
        return this.badkindCannotOccur(cs, null);
    }

    public INTERNAL badkindCannotOccur(Throwable t2) {
        return this.badkindCannotOccur(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badkindCannotOccur() {
        return this.badkindCannotOccur(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL errorResolvingAlias(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079743, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.errorResolvingAlias", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorResolvingAlias(CompletionStatus cs) {
        return this.errorResolvingAlias(cs, null);
    }

    public INTERNAL errorResolvingAlias(Throwable t2) {
        return this.errorResolvingAlias(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL errorResolvingAlias() {
        return this.errorResolvingAlias(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL tkLongDoubleNotSupported(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079744, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.tkLongDoubleNotSupported", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL tkLongDoubleNotSupported(CompletionStatus cs) {
        return this.tkLongDoubleNotSupported(cs, null);
    }

    public INTERNAL tkLongDoubleNotSupported(Throwable t2) {
        return this.tkLongDoubleNotSupported(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL tkLongDoubleNotSupported() {
        return this.tkLongDoubleNotSupported(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL typecodeNotSupported(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079745, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.typecodeNotSupported", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL typecodeNotSupported(CompletionStatus cs) {
        return this.typecodeNotSupported(cs, null);
    }

    public INTERNAL typecodeNotSupported(Throwable t2) {
        return this.typecodeNotSupported(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL typecodeNotSupported() {
        return this.typecodeNotSupported(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL boundsCannotOccur(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079747, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.boundsCannotOccur", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL boundsCannotOccur(CompletionStatus cs) {
        return this.boundsCannotOccur(cs, null);
    }

    public INTERNAL boundsCannotOccur(Throwable t2) {
        return this.boundsCannotOccur(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL boundsCannotOccur() {
        return this.boundsCannotOccur(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL numInvocationsAlreadyZero(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079749, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.numInvocationsAlreadyZero", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL numInvocationsAlreadyZero(CompletionStatus cs) {
        return this.numInvocationsAlreadyZero(cs, null);
    }

    public INTERNAL numInvocationsAlreadyZero(Throwable t2) {
        return this.numInvocationsAlreadyZero(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL numInvocationsAlreadyZero() {
        return this.numInvocationsAlreadyZero(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL errorInitBadserveridhandler(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079750, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.errorInitBadserveridhandler", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorInitBadserveridhandler(CompletionStatus cs) {
        return this.errorInitBadserveridhandler(cs, null);
    }

    public INTERNAL errorInitBadserveridhandler(Throwable t2) {
        return this.errorInitBadserveridhandler(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL errorInitBadserveridhandler() {
        return this.errorInitBadserveridhandler(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL noToa(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079751, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.noToa", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL noToa(CompletionStatus cs) {
        return this.noToa(cs, null);
    }

    public INTERNAL noToa(Throwable t2) {
        return this.noToa(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL noToa() {
        return this.noToa(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL noPoa(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079752, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.noPoa", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL noPoa(CompletionStatus cs) {
        return this.noPoa(cs, null);
    }

    public INTERNAL noPoa(Throwable t2) {
        return this.noPoa(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL noPoa() {
        return this.noPoa(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL invocationInfoStackEmpty(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079753, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.invocationInfoStackEmpty", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invocationInfoStackEmpty(CompletionStatus cs) {
        return this.invocationInfoStackEmpty(cs, null);
    }

    public INTERNAL invocationInfoStackEmpty(Throwable t2) {
        return this.invocationInfoStackEmpty(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL invocationInfoStackEmpty() {
        return this.invocationInfoStackEmpty(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL badCodeSetString(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079754, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badCodeSetString", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badCodeSetString(CompletionStatus cs) {
        return this.badCodeSetString(cs, null);
    }

    public INTERNAL badCodeSetString(Throwable t2) {
        return this.badCodeSetString(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badCodeSetString() {
        return this.badCodeSetString(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL unknownNativeCodeset(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079755, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.unknownNativeCodeset", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL unknownNativeCodeset(CompletionStatus cs, Object arg0) {
        return this.unknownNativeCodeset(cs, null, arg0);
    }

    public INTERNAL unknownNativeCodeset(Throwable t2, Object arg0) {
        return this.unknownNativeCodeset(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL unknownNativeCodeset(Object arg0) {
        return this.unknownNativeCodeset(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL unknownConversionCodeSet(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079756, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.unknownConversionCodeSet", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL unknownConversionCodeSet(CompletionStatus cs, Object arg0) {
        return this.unknownConversionCodeSet(cs, null, arg0);
    }

    public INTERNAL unknownConversionCodeSet(Throwable t2, Object arg0) {
        return this.unknownConversionCodeSet(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL unknownConversionCodeSet(Object arg0) {
        return this.unknownConversionCodeSet(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL invalidCodeSetNumber(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079757, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.invalidCodeSetNumber", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invalidCodeSetNumber(CompletionStatus cs) {
        return this.invalidCodeSetNumber(cs, null);
    }

    public INTERNAL invalidCodeSetNumber(Throwable t2) {
        return this.invalidCodeSetNumber(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL invalidCodeSetNumber() {
        return this.invalidCodeSetNumber(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL invalidCodeSetString(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079758, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.invalidCodeSetString", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invalidCodeSetString(CompletionStatus cs, Object arg0) {
        return this.invalidCodeSetString(cs, null, arg0);
    }

    public INTERNAL invalidCodeSetString(Throwable t2, Object arg0) {
        return this.invalidCodeSetString(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL invalidCodeSetString(Object arg0) {
        return this.invalidCodeSetString(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL invalidCtbConverterName(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079759, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.invalidCtbConverterName", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invalidCtbConverterName(CompletionStatus cs, Object arg0) {
        return this.invalidCtbConverterName(cs, null, arg0);
    }

    public INTERNAL invalidCtbConverterName(Throwable t2, Object arg0) {
        return this.invalidCtbConverterName(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL invalidCtbConverterName(Object arg0) {
        return this.invalidCtbConverterName(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL invalidBtcConverterName(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079760, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.invalidBtcConverterName", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invalidBtcConverterName(CompletionStatus cs, Object arg0) {
        return this.invalidBtcConverterName(cs, null, arg0);
    }

    public INTERNAL invalidBtcConverterName(Throwable t2, Object arg0) {
        return this.invalidBtcConverterName(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL invalidBtcConverterName(Object arg0) {
        return this.invalidBtcConverterName(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL couldNotDuplicateCdrInputStream(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079761, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.couldNotDuplicateCdrInputStream", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL couldNotDuplicateCdrInputStream(CompletionStatus cs) {
        return this.couldNotDuplicateCdrInputStream(cs, null);
    }

    public INTERNAL couldNotDuplicateCdrInputStream(Throwable t2) {
        return this.couldNotDuplicateCdrInputStream(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL couldNotDuplicateCdrInputStream() {
        return this.couldNotDuplicateCdrInputStream(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL bootstrapApplicationException(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079762, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.bootstrapApplicationException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL bootstrapApplicationException(CompletionStatus cs) {
        return this.bootstrapApplicationException(cs, null);
    }

    public INTERNAL bootstrapApplicationException(Throwable t2) {
        return this.bootstrapApplicationException(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL bootstrapApplicationException() {
        return this.bootstrapApplicationException(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL duplicateIndirectionOffset(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079763, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.duplicateIndirectionOffset", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL duplicateIndirectionOffset(CompletionStatus cs) {
        return this.duplicateIndirectionOffset(cs, null);
    }

    public INTERNAL duplicateIndirectionOffset(Throwable t2) {
        return this.duplicateIndirectionOffset(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL duplicateIndirectionOffset() {
        return this.duplicateIndirectionOffset(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL badMessageTypeForCancel(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079764, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badMessageTypeForCancel", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badMessageTypeForCancel(CompletionStatus cs) {
        return this.badMessageTypeForCancel(cs, null);
    }

    public INTERNAL badMessageTypeForCancel(Throwable t2) {
        return this.badMessageTypeForCancel(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badMessageTypeForCancel() {
        return this.badMessageTypeForCancel(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL duplicateExceptionDetailMessage(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079765, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.duplicateExceptionDetailMessage", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL duplicateExceptionDetailMessage(CompletionStatus cs) {
        return this.duplicateExceptionDetailMessage(cs, null);
    }

    public INTERNAL duplicateExceptionDetailMessage(Throwable t2) {
        return this.duplicateExceptionDetailMessage(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL duplicateExceptionDetailMessage() {
        return this.duplicateExceptionDetailMessage(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL badExceptionDetailMessageServiceContextType(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079766, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badExceptionDetailMessageServiceContextType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badExceptionDetailMessageServiceContextType(CompletionStatus cs) {
        return this.badExceptionDetailMessageServiceContextType(cs, null);
    }

    public INTERNAL badExceptionDetailMessageServiceContextType(Throwable t2) {
        return this.badExceptionDetailMessageServiceContextType(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badExceptionDetailMessageServiceContextType() {
        return this.badExceptionDetailMessageServiceContextType(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL unexpectedDirectByteBufferWithNonChannelSocket(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079767, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unexpectedDirectByteBufferWithNonChannelSocket", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL unexpectedDirectByteBufferWithNonChannelSocket(CompletionStatus cs) {
        return this.unexpectedDirectByteBufferWithNonChannelSocket(cs, null);
    }

    public INTERNAL unexpectedDirectByteBufferWithNonChannelSocket(Throwable t2) {
        return this.unexpectedDirectByteBufferWithNonChannelSocket(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL unexpectedDirectByteBufferWithNonChannelSocket() {
        return this.unexpectedDirectByteBufferWithNonChannelSocket(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL unexpectedNonDirectByteBufferWithChannelSocket(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079768, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unexpectedNonDirectByteBufferWithChannelSocket", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL unexpectedNonDirectByteBufferWithChannelSocket(CompletionStatus cs) {
        return this.unexpectedNonDirectByteBufferWithChannelSocket(cs, null);
    }

    public INTERNAL unexpectedNonDirectByteBufferWithChannelSocket(Throwable t2) {
        return this.unexpectedNonDirectByteBufferWithChannelSocket(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL unexpectedNonDirectByteBufferWithChannelSocket() {
        return this.unexpectedNonDirectByteBufferWithChannelSocket(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL invalidContactInfoListIteratorFailureException(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079770, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.invalidContactInfoListIteratorFailureException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invalidContactInfoListIteratorFailureException(CompletionStatus cs) {
        return this.invalidContactInfoListIteratorFailureException(cs, null);
    }

    public INTERNAL invalidContactInfoListIteratorFailureException(Throwable t2) {
        return this.invalidContactInfoListIteratorFailureException(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL invalidContactInfoListIteratorFailureException() {
        return this.invalidContactInfoListIteratorFailureException(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL remarshalWithNowhereToGo(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079771, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.remarshalWithNowhereToGo", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL remarshalWithNowhereToGo(CompletionStatus cs) {
        return this.remarshalWithNowhereToGo(cs, null);
    }

    public INTERNAL remarshalWithNowhereToGo(Throwable t2) {
        return this.remarshalWithNowhereToGo(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL remarshalWithNowhereToGo() {
        return this.remarshalWithNowhereToGo(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL exceptionWhenSendingCloseConnection(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079772, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.exceptionWhenSendingCloseConnection", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL exceptionWhenSendingCloseConnection(CompletionStatus cs) {
        return this.exceptionWhenSendingCloseConnection(cs, null);
    }

    public INTERNAL exceptionWhenSendingCloseConnection(Throwable t2) {
        return this.exceptionWhenSendingCloseConnection(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL exceptionWhenSendingCloseConnection() {
        return this.exceptionWhenSendingCloseConnection(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL invocationErrorInReflectiveTie(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        INTERNAL exc = new INTERNAL(1398079773, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.invocationErrorInReflectiveTie", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invocationErrorInReflectiveTie(CompletionStatus cs, Object arg0, Object arg1) {
        return this.invocationErrorInReflectiveTie(cs, null, arg0, arg1);
    }

    public INTERNAL invocationErrorInReflectiveTie(Throwable t2, Object arg0, Object arg1) {
        return this.invocationErrorInReflectiveTie(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public INTERNAL invocationErrorInReflectiveTie(Object arg0, Object arg1) {
        return this.invocationErrorInReflectiveTie(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public INTERNAL badHelperWriteMethod(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079774, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badHelperWriteMethod", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badHelperWriteMethod(CompletionStatus cs, Object arg0) {
        return this.badHelperWriteMethod(cs, null, arg0);
    }

    public INTERNAL badHelperWriteMethod(Throwable t2, Object arg0) {
        return this.badHelperWriteMethod(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL badHelperWriteMethod(Object arg0) {
        return this.badHelperWriteMethod(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL badHelperReadMethod(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079775, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badHelperReadMethod", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badHelperReadMethod(CompletionStatus cs, Object arg0) {
        return this.badHelperReadMethod(cs, null, arg0);
    }

    public INTERNAL badHelperReadMethod(Throwable t2, Object arg0) {
        return this.badHelperReadMethod(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL badHelperReadMethod(Object arg0) {
        return this.badHelperReadMethod(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL badHelperIdMethod(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079776, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badHelperIdMethod", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badHelperIdMethod(CompletionStatus cs, Object arg0) {
        return this.badHelperIdMethod(cs, null, arg0);
    }

    public INTERNAL badHelperIdMethod(Throwable t2, Object arg0) {
        return this.badHelperIdMethod(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL badHelperIdMethod(Object arg0) {
        return this.badHelperIdMethod(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL writeUndeclaredException(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079777, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.writeUndeclaredException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL writeUndeclaredException(CompletionStatus cs, Object arg0) {
        return this.writeUndeclaredException(cs, null, arg0);
    }

    public INTERNAL writeUndeclaredException(Throwable t2, Object arg0) {
        return this.writeUndeclaredException(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL writeUndeclaredException(Object arg0) {
        return this.writeUndeclaredException(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL readUndeclaredException(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079778, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.readUndeclaredException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL readUndeclaredException(CompletionStatus cs, Object arg0) {
        return this.readUndeclaredException(cs, null, arg0);
    }

    public INTERNAL readUndeclaredException(Throwable t2, Object arg0) {
        return this.readUndeclaredException(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL readUndeclaredException(Object arg0) {
        return this.readUndeclaredException(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL unableToSetSocketFactoryOrb(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079779, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unableToSetSocketFactoryOrb", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL unableToSetSocketFactoryOrb(CompletionStatus cs) {
        return this.unableToSetSocketFactoryOrb(cs, null);
    }

    public INTERNAL unableToSetSocketFactoryOrb(Throwable t2) {
        return this.unableToSetSocketFactoryOrb(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL unableToSetSocketFactoryOrb() {
        return this.unableToSetSocketFactoryOrb(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL unexpectedException(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079780, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unexpectedException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL unexpectedException(CompletionStatus cs) {
        return this.unexpectedException(cs, null);
    }

    public INTERNAL unexpectedException(Throwable t2) {
        return this.unexpectedException(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL unexpectedException() {
        return this.unexpectedException(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL noInvocationHandler(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079781, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.noInvocationHandler", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL noInvocationHandler(CompletionStatus cs, Object arg0) {
        return this.noInvocationHandler(cs, null, arg0);
    }

    public INTERNAL noInvocationHandler(Throwable t2, Object arg0) {
        return this.noInvocationHandler(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL noInvocationHandler(Object arg0) {
        return this.noInvocationHandler(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL invalidBuffMgrStrategy(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079782, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.invalidBuffMgrStrategy", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invalidBuffMgrStrategy(CompletionStatus cs, Object arg0) {
        return this.invalidBuffMgrStrategy(cs, null, arg0);
    }

    public INTERNAL invalidBuffMgrStrategy(Throwable t2, Object arg0) {
        return this.invalidBuffMgrStrategy(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL invalidBuffMgrStrategy(Object arg0) {
        return this.invalidBuffMgrStrategy(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL javaStreamInitFailed(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079783, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.javaStreamInitFailed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL javaStreamInitFailed(CompletionStatus cs) {
        return this.javaStreamInitFailed(cs, null);
    }

    public INTERNAL javaStreamInitFailed(Throwable t2) {
        return this.javaStreamInitFailed(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL javaStreamInitFailed() {
        return this.javaStreamInitFailed(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL duplicateOrbVersionServiceContext(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079784, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.duplicateOrbVersionServiceContext", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL duplicateOrbVersionServiceContext(CompletionStatus cs) {
        return this.duplicateOrbVersionServiceContext(cs, null);
    }

    public INTERNAL duplicateOrbVersionServiceContext(Throwable t2) {
        return this.duplicateOrbVersionServiceContext(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL duplicateOrbVersionServiceContext() {
        return this.duplicateOrbVersionServiceContext(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL duplicateSendingContextServiceContext(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079785, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.duplicateSendingContextServiceContext", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL duplicateSendingContextServiceContext(CompletionStatus cs) {
        return this.duplicateSendingContextServiceContext(cs, null);
    }

    public INTERNAL duplicateSendingContextServiceContext(Throwable t2) {
        return this.duplicateSendingContextServiceContext(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL duplicateSendingContextServiceContext() {
        return this.duplicateSendingContextServiceContext(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL workQueueThreadInterrupted(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        INTERNAL exc = new INTERNAL(1398079786, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.FINE, "ORBUTIL.workQueueThreadInterrupted", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL workQueueThreadInterrupted(CompletionStatus cs, Object arg0, Object arg1) {
        return this.workQueueThreadInterrupted(cs, null, arg0, arg1);
    }

    public INTERNAL workQueueThreadInterrupted(Throwable t2, Object arg0, Object arg1) {
        return this.workQueueThreadInterrupted(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public INTERNAL workQueueThreadInterrupted(Object arg0, Object arg1) {
        return this.workQueueThreadInterrupted(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public INTERNAL workerThreadCreated(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        INTERNAL exc = new INTERNAL(1398079792, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.FINE, "ORBUTIL.workerThreadCreated", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL workerThreadCreated(CompletionStatus cs, Object arg0, Object arg1) {
        return this.workerThreadCreated(cs, null, arg0, arg1);
    }

    public INTERNAL workerThreadCreated(Throwable t2, Object arg0, Object arg1) {
        return this.workerThreadCreated(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public INTERNAL workerThreadCreated(Object arg0, Object arg1) {
        return this.workerThreadCreated(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public INTERNAL workerThreadThrowableFromRequestWork(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398079797, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.FINE, "ORBUTIL.workerThreadThrowableFromRequestWork", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL workerThreadThrowableFromRequestWork(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.workerThreadThrowableFromRequestWork(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL workerThreadThrowableFromRequestWork(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.workerThreadThrowableFromRequestWork(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL workerThreadThrowableFromRequestWork(Object arg0, Object arg1, Object arg2) {
        return this.workerThreadThrowableFromRequestWork(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL workerThreadNotNeeded(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398079798, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.FINE, "ORBUTIL.workerThreadNotNeeded", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL workerThreadNotNeeded(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.workerThreadNotNeeded(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL workerThreadNotNeeded(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.workerThreadNotNeeded(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL workerThreadNotNeeded(Object arg0, Object arg1, Object arg2) {
        return this.workerThreadNotNeeded(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL workerThreadDoWorkThrowable(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        INTERNAL exc = new INTERNAL(1398079799, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.FINE, "ORBUTIL.workerThreadDoWorkThrowable", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL workerThreadDoWorkThrowable(CompletionStatus cs, Object arg0, Object arg1) {
        return this.workerThreadDoWorkThrowable(cs, null, arg0, arg1);
    }

    public INTERNAL workerThreadDoWorkThrowable(Throwable t2, Object arg0, Object arg1) {
        return this.workerThreadDoWorkThrowable(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public INTERNAL workerThreadDoWorkThrowable(Object arg0, Object arg1) {
        return this.workerThreadDoWorkThrowable(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public INTERNAL workerThreadCaughtUnexpectedThrowable(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        INTERNAL exc = new INTERNAL(1398079800, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.workerThreadCaughtUnexpectedThrowable", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL workerThreadCaughtUnexpectedThrowable(CompletionStatus cs, Object arg0, Object arg1) {
        return this.workerThreadCaughtUnexpectedThrowable(cs, null, arg0, arg1);
    }

    public INTERNAL workerThreadCaughtUnexpectedThrowable(Throwable t2, Object arg0, Object arg1) {
        return this.workerThreadCaughtUnexpectedThrowable(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public INTERNAL workerThreadCaughtUnexpectedThrowable(Object arg0, Object arg1) {
        return this.workerThreadCaughtUnexpectedThrowable(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public INTERNAL workerThreadCreationFailure(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079801, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.SEVERE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.SEVERE, "ORBUTIL.workerThreadCreationFailure", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL workerThreadCreationFailure(CompletionStatus cs, Object arg0) {
        return this.workerThreadCreationFailure(cs, null, arg0);
    }

    public INTERNAL workerThreadCreationFailure(Throwable t2, Object arg0) {
        return this.workerThreadCreationFailure(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL workerThreadCreationFailure(Object arg0) {
        return this.workerThreadCreationFailure(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL workerThreadSetNameFailure(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398079802, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "ORBUTIL.workerThreadSetNameFailure", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL workerThreadSetNameFailure(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.workerThreadSetNameFailure(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL workerThreadSetNameFailure(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.workerThreadSetNameFailure(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL workerThreadSetNameFailure(Object arg0, Object arg1, Object arg2) {
        return this.workerThreadSetNameFailure(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL workQueueRequestWorkNoWorkFound(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        INTERNAL exc = new INTERNAL(1398079804, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.workQueueRequestWorkNoWorkFound", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL workQueueRequestWorkNoWorkFound(CompletionStatus cs, Object arg0, Object arg1) {
        return this.workQueueRequestWorkNoWorkFound(cs, null, arg0, arg1);
    }

    public INTERNAL workQueueRequestWorkNoWorkFound(Throwable t2, Object arg0, Object arg1) {
        return this.workQueueRequestWorkNoWorkFound(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public INTERNAL workQueueRequestWorkNoWorkFound(Object arg0, Object arg1) {
        return this.workQueueRequestWorkNoWorkFound(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public INTERNAL threadPoolCloseError(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398079814, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.threadPoolCloseError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL threadPoolCloseError(CompletionStatus cs) {
        return this.threadPoolCloseError(cs, null);
    }

    public INTERNAL threadPoolCloseError(Throwable t2) {
        return this.threadPoolCloseError(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL threadPoolCloseError() {
        return this.threadPoolCloseError(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL threadGroupIsDestroyed(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079815, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.threadGroupIsDestroyed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL threadGroupIsDestroyed(CompletionStatus cs, Object arg0) {
        return this.threadGroupIsDestroyed(cs, null, arg0);
    }

    public INTERNAL threadGroupIsDestroyed(Throwable t2, Object arg0) {
        return this.threadGroupIsDestroyed(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL threadGroupIsDestroyed(Object arg0) {
        return this.threadGroupIsDestroyed(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL threadGroupHasActiveThreadsInClose(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        INTERNAL exc = new INTERNAL(1398079816, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.threadGroupHasActiveThreadsInClose", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL threadGroupHasActiveThreadsInClose(CompletionStatus cs, Object arg0, Object arg1) {
        return this.threadGroupHasActiveThreadsInClose(cs, null, arg0, arg1);
    }

    public INTERNAL threadGroupHasActiveThreadsInClose(Throwable t2, Object arg0, Object arg1) {
        return this.threadGroupHasActiveThreadsInClose(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public INTERNAL threadGroupHasActiveThreadsInClose(Object arg0, Object arg1) {
        return this.threadGroupHasActiveThreadsInClose(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public INTERNAL threadGroupHasSubGroupsInClose(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        INTERNAL exc = new INTERNAL(1398079817, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.threadGroupHasSubGroupsInClose", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL threadGroupHasSubGroupsInClose(CompletionStatus cs, Object arg0, Object arg1) {
        return this.threadGroupHasSubGroupsInClose(cs, null, arg0, arg1);
    }

    public INTERNAL threadGroupHasSubGroupsInClose(Throwable t2, Object arg0, Object arg1) {
        return this.threadGroupHasSubGroupsInClose(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public INTERNAL threadGroupHasSubGroupsInClose(Object arg0, Object arg1) {
        return this.threadGroupHasSubGroupsInClose(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public INTERNAL threadGroupDestroyFailed(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398079818, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.threadGroupDestroyFailed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL threadGroupDestroyFailed(CompletionStatus cs, Object arg0) {
        return this.threadGroupDestroyFailed(cs, null, arg0);
    }

    public INTERNAL threadGroupDestroyFailed(Throwable t2, Object arg0) {
        return this.threadGroupDestroyFailed(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL threadGroupDestroyFailed(Object arg0) {
        return this.threadGroupDestroyFailed(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL interruptedJoinCallWhileClosingThreadPool(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        INTERNAL exc = new INTERNAL(1398079819, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.interruptedJoinCallWhileClosingThreadPool", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL interruptedJoinCallWhileClosingThreadPool(CompletionStatus cs, Object arg0, Object arg1) {
        return this.interruptedJoinCallWhileClosingThreadPool(cs, null, arg0, arg1);
    }

    public INTERNAL interruptedJoinCallWhileClosingThreadPool(Throwable t2, Object arg0, Object arg1) {
        return this.interruptedJoinCallWhileClosingThreadPool(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public INTERNAL interruptedJoinCallWhileClosingThreadPool(Object arg0, Object arg1) {
        return this.interruptedJoinCallWhileClosingThreadPool(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public MARSHAL chunkOverflow(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.chunkOverflow", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL chunkOverflow(CompletionStatus cs) {
        return this.chunkOverflow(cs, null);
    }

    public MARSHAL chunkOverflow(Throwable t2) {
        return this.chunkOverflow(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL chunkOverflow() {
        return this.chunkOverflow(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL unexpectedEof(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unexpectedEof", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL unexpectedEof(CompletionStatus cs) {
        return this.unexpectedEof(cs, null);
    }

    public MARSHAL unexpectedEof(Throwable t2) {
        return this.unexpectedEof(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL unexpectedEof() {
        return this.unexpectedEof(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL readObjectException(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.readObjectException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL readObjectException(CompletionStatus cs) {
        return this.readObjectException(cs, null);
    }

    public MARSHAL readObjectException(Throwable t2) {
        return this.readObjectException(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL readObjectException() {
        return this.readObjectException(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL characterOutofrange(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.characterOutofrange", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL characterOutofrange(CompletionStatus cs) {
        return this.characterOutofrange(cs, null);
    }

    public MARSHAL characterOutofrange(Throwable t2) {
        return this.characterOutofrange(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL characterOutofrange() {
        return this.characterOutofrange(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL dsiResultException(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.dsiResultException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL dsiResultException(CompletionStatus cs) {
        return this.dsiResultException(cs, null);
    }

    public MARSHAL dsiResultException(Throwable t2) {
        return this.dsiResultException(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL dsiResultException() {
        return this.dsiResultException(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL iiopinputstreamGrow(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079694, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.iiopinputstreamGrow", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL iiopinputstreamGrow(CompletionStatus cs) {
        return this.iiopinputstreamGrow(cs, null);
    }

    public MARSHAL iiopinputstreamGrow(Throwable t2) {
        return this.iiopinputstreamGrow(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL iiopinputstreamGrow() {
        return this.iiopinputstreamGrow(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL endOfStream(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079695, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.endOfStream", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL endOfStream(CompletionStatus cs) {
        return this.endOfStream(cs, null);
    }

    public MARSHAL endOfStream(Throwable t2) {
        return this.endOfStream(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL endOfStream() {
        return this.endOfStream(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL invalidObjectKey(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079696, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.invalidObjectKey", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL invalidObjectKey(CompletionStatus cs) {
        return this.invalidObjectKey(cs, null);
    }

    public MARSHAL invalidObjectKey(Throwable t2) {
        return this.invalidObjectKey(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL invalidObjectKey() {
        return this.invalidObjectKey(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL malformedUrl(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        MARSHAL exc = new MARSHAL(1398079697, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.malformedUrl", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL malformedUrl(CompletionStatus cs, Object arg0, Object arg1) {
        return this.malformedUrl(cs, null, arg0, arg1);
    }

    public MARSHAL malformedUrl(Throwable t2, Object arg0, Object arg1) {
        return this.malformedUrl(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public MARSHAL malformedUrl(Object arg0, Object arg1) {
        return this.malformedUrl(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public MARSHAL valuehandlerReadError(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079698, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.valuehandlerReadError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL valuehandlerReadError(CompletionStatus cs) {
        return this.valuehandlerReadError(cs, null);
    }

    public MARSHAL valuehandlerReadError(Throwable t2) {
        return this.valuehandlerReadError(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL valuehandlerReadError() {
        return this.valuehandlerReadError(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL valuehandlerReadException(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079699, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.valuehandlerReadException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL valuehandlerReadException(CompletionStatus cs) {
        return this.valuehandlerReadException(cs, null);
    }

    public MARSHAL valuehandlerReadException(Throwable t2) {
        return this.valuehandlerReadException(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL valuehandlerReadException() {
        return this.valuehandlerReadException(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL badKind(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079700, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badKind", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badKind(CompletionStatus cs) {
        return this.badKind(cs, null);
    }

    public MARSHAL badKind(Throwable t2) {
        return this.badKind(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL badKind() {
        return this.badKind(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL cnfeReadClass(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079701, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.cnfeReadClass", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL cnfeReadClass(CompletionStatus cs, Object arg0) {
        return this.cnfeReadClass(cs, null, arg0);
    }

    public MARSHAL cnfeReadClass(Throwable t2, Object arg0) {
        return this.cnfeReadClass(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL cnfeReadClass(Object arg0) {
        return this.cnfeReadClass(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL badRepIdIndirection(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079702, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badRepIdIndirection", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badRepIdIndirection(CompletionStatus cs, Object arg0) {
        return this.badRepIdIndirection(cs, null, arg0);
    }

    public MARSHAL badRepIdIndirection(Throwable t2, Object arg0) {
        return this.badRepIdIndirection(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL badRepIdIndirection(Object arg0) {
        return this.badRepIdIndirection(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL badCodebaseIndirection(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079703, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badCodebaseIndirection", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badCodebaseIndirection(CompletionStatus cs, Object arg0) {
        return this.badCodebaseIndirection(cs, null, arg0);
    }

    public MARSHAL badCodebaseIndirection(Throwable t2, Object arg0) {
        return this.badCodebaseIndirection(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL badCodebaseIndirection(Object arg0) {
        return this.badCodebaseIndirection(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL unknownCodeset(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079704, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.unknownCodeset", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL unknownCodeset(CompletionStatus cs, Object arg0) {
        return this.unknownCodeset(cs, null, arg0);
    }

    public MARSHAL unknownCodeset(Throwable t2, Object arg0) {
        return this.unknownCodeset(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL unknownCodeset(Object arg0) {
        return this.unknownCodeset(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL wcharDataInGiop10(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079705, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.wcharDataInGiop10", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL wcharDataInGiop10(CompletionStatus cs) {
        return this.wcharDataInGiop10(cs, null);
    }

    public MARSHAL wcharDataInGiop10(Throwable t2) {
        return this.wcharDataInGiop10(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL wcharDataInGiop10() {
        return this.wcharDataInGiop10(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL negativeStringLength(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079706, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.negativeStringLength", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL negativeStringLength(CompletionStatus cs, Object arg0) {
        return this.negativeStringLength(cs, null, arg0);
    }

    public MARSHAL negativeStringLength(Throwable t2, Object arg0) {
        return this.negativeStringLength(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL negativeStringLength(Object arg0) {
        return this.negativeStringLength(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL expectedTypeNullAndNoRepId(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079707, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.expectedTypeNullAndNoRepId", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL expectedTypeNullAndNoRepId(CompletionStatus cs) {
        return this.expectedTypeNullAndNoRepId(cs, null);
    }

    public MARSHAL expectedTypeNullAndNoRepId(Throwable t2) {
        return this.expectedTypeNullAndNoRepId(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL expectedTypeNullAndNoRepId() {
        return this.expectedTypeNullAndNoRepId(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL readValueAndNoRepId(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079708, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.readValueAndNoRepId", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL readValueAndNoRepId(CompletionStatus cs) {
        return this.readValueAndNoRepId(cs, null);
    }

    public MARSHAL readValueAndNoRepId(Throwable t2) {
        return this.readValueAndNoRepId(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL readValueAndNoRepId() {
        return this.readValueAndNoRepId(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL unexpectedEnclosingValuetype(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        MARSHAL exc = new MARSHAL(1398079710, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.unexpectedEnclosingValuetype", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL unexpectedEnclosingValuetype(CompletionStatus cs, Object arg0, Object arg1) {
        return this.unexpectedEnclosingValuetype(cs, null, arg0, arg1);
    }

    public MARSHAL unexpectedEnclosingValuetype(Throwable t2, Object arg0, Object arg1) {
        return this.unexpectedEnclosingValuetype(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public MARSHAL unexpectedEnclosingValuetype(Object arg0, Object arg1) {
        return this.unexpectedEnclosingValuetype(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public MARSHAL positiveEndTag(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        MARSHAL exc = new MARSHAL(1398079711, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.positiveEndTag", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL positiveEndTag(CompletionStatus cs, Object arg0, Object arg1) {
        return this.positiveEndTag(cs, null, arg0, arg1);
    }

    public MARSHAL positiveEndTag(Throwable t2, Object arg0, Object arg1) {
        return this.positiveEndTag(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public MARSHAL positiveEndTag(Object arg0, Object arg1) {
        return this.positiveEndTag(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public MARSHAL nullOutCall(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079712, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.nullOutCall", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL nullOutCall(CompletionStatus cs) {
        return this.nullOutCall(cs, null);
    }

    public MARSHAL nullOutCall(Throwable t2) {
        return this.nullOutCall(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL nullOutCall() {
        return this.nullOutCall(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL writeLocalObject(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079713, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.writeLocalObject", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL writeLocalObject(CompletionStatus cs) {
        return this.writeLocalObject(cs, null);
    }

    public MARSHAL writeLocalObject(Throwable t2) {
        return this.writeLocalObject(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL writeLocalObject() {
        return this.writeLocalObject(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL badInsertobjParam(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079714, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badInsertobjParam", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badInsertobjParam(CompletionStatus cs, Object arg0) {
        return this.badInsertobjParam(cs, null, arg0);
    }

    public MARSHAL badInsertobjParam(Throwable t2, Object arg0) {
        return this.badInsertobjParam(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL badInsertobjParam(Object arg0) {
        return this.badInsertobjParam(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL customWrapperWithCodebase(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079715, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.customWrapperWithCodebase", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL customWrapperWithCodebase(CompletionStatus cs) {
        return this.customWrapperWithCodebase(cs, null);
    }

    public MARSHAL customWrapperWithCodebase(Throwable t2) {
        return this.customWrapperWithCodebase(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL customWrapperWithCodebase() {
        return this.customWrapperWithCodebase(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL customWrapperIndirection(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079716, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.customWrapperIndirection", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL customWrapperIndirection(CompletionStatus cs) {
        return this.customWrapperIndirection(cs, null);
    }

    public MARSHAL customWrapperIndirection(Throwable t2) {
        return this.customWrapperIndirection(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL customWrapperIndirection() {
        return this.customWrapperIndirection(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL customWrapperNotSingleRepid(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079717, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.customWrapperNotSingleRepid", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL customWrapperNotSingleRepid(CompletionStatus cs) {
        return this.customWrapperNotSingleRepid(cs, null);
    }

    public MARSHAL customWrapperNotSingleRepid(Throwable t2) {
        return this.customWrapperNotSingleRepid(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL customWrapperNotSingleRepid() {
        return this.customWrapperNotSingleRepid(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL badValueTag(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079718, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.badValueTag", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badValueTag(CompletionStatus cs, Object arg0) {
        return this.badValueTag(cs, null, arg0);
    }

    public MARSHAL badValueTag(Throwable t2, Object arg0) {
        return this.badValueTag(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL badValueTag(Object arg0) {
        return this.badValueTag(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL badTypecodeForCustomValue(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079719, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badTypecodeForCustomValue", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badTypecodeForCustomValue(CompletionStatus cs) {
        return this.badTypecodeForCustomValue(cs, null);
    }

    public MARSHAL badTypecodeForCustomValue(Throwable t2) {
        return this.badTypecodeForCustomValue(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL badTypecodeForCustomValue() {
        return this.badTypecodeForCustomValue(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL errorInvokingHelperWrite(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079720, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.errorInvokingHelperWrite", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL errorInvokingHelperWrite(CompletionStatus cs) {
        return this.errorInvokingHelperWrite(cs, null);
    }

    public MARSHAL errorInvokingHelperWrite(Throwable t2) {
        return this.errorInvokingHelperWrite(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL errorInvokingHelperWrite() {
        return this.errorInvokingHelperWrite(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL badDigitInFixed(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079721, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badDigitInFixed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badDigitInFixed(CompletionStatus cs) {
        return this.badDigitInFixed(cs, null);
    }

    public MARSHAL badDigitInFixed(Throwable t2) {
        return this.badDigitInFixed(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL badDigitInFixed() {
        return this.badDigitInFixed(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL refTypeIndirType(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079722, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.refTypeIndirType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL refTypeIndirType(CompletionStatus cs) {
        return this.refTypeIndirType(cs, null);
    }

    public MARSHAL refTypeIndirType(Throwable t2) {
        return this.refTypeIndirType(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL refTypeIndirType() {
        return this.refTypeIndirType(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL badReservedLength(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079723, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badReservedLength", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badReservedLength(CompletionStatus cs) {
        return this.badReservedLength(cs, null);
    }

    public MARSHAL badReservedLength(Throwable t2) {
        return this.badReservedLength(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL badReservedLength() {
        return this.badReservedLength(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL nullNotAllowed(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079724, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.nullNotAllowed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL nullNotAllowed(CompletionStatus cs) {
        return this.nullNotAllowed(cs, null);
    }

    public MARSHAL nullNotAllowed(Throwable t2) {
        return this.nullNotAllowed(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL nullNotAllowed() {
        return this.nullNotAllowed(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL unionDiscriminatorError(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079726, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unionDiscriminatorError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL unionDiscriminatorError(CompletionStatus cs) {
        return this.unionDiscriminatorError(cs, null);
    }

    public MARSHAL unionDiscriminatorError(Throwable t2) {
        return this.unionDiscriminatorError(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL unionDiscriminatorError() {
        return this.unionDiscriminatorError(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL cannotMarshalNative(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079727, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.cannotMarshalNative", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL cannotMarshalNative(CompletionStatus cs) {
        return this.cannotMarshalNative(cs, null);
    }

    public MARSHAL cannotMarshalNative(Throwable t2) {
        return this.cannotMarshalNative(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL cannotMarshalNative() {
        return this.cannotMarshalNative(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL cannotMarshalBadTckind(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079728, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.cannotMarshalBadTckind", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL cannotMarshalBadTckind(CompletionStatus cs) {
        return this.cannotMarshalBadTckind(cs, null);
    }

    public MARSHAL cannotMarshalBadTckind(Throwable t2) {
        return this.cannotMarshalBadTckind(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL cannotMarshalBadTckind() {
        return this.cannotMarshalBadTckind(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL invalidIndirection(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079729, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.invalidIndirection", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL invalidIndirection(CompletionStatus cs, Object arg0) {
        return this.invalidIndirection(cs, null, arg0);
    }

    public MARSHAL invalidIndirection(Throwable t2, Object arg0) {
        return this.invalidIndirection(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL invalidIndirection(Object arg0) {
        return this.invalidIndirection(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL indirectionNotFound(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079730, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.FINE, "ORBUTIL.indirectionNotFound", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL indirectionNotFound(CompletionStatus cs, Object arg0) {
        return this.indirectionNotFound(cs, null, arg0);
    }

    public MARSHAL indirectionNotFound(Throwable t2, Object arg0) {
        return this.indirectionNotFound(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL indirectionNotFound(Object arg0) {
        return this.indirectionNotFound(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL recursiveTypecodeError(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079731, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.recursiveTypecodeError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL recursiveTypecodeError(CompletionStatus cs) {
        return this.recursiveTypecodeError(cs, null);
    }

    public MARSHAL recursiveTypecodeError(Throwable t2) {
        return this.recursiveTypecodeError(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL recursiveTypecodeError() {
        return this.recursiveTypecodeError(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL invalidSimpleTypecode(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079732, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.invalidSimpleTypecode", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL invalidSimpleTypecode(CompletionStatus cs) {
        return this.invalidSimpleTypecode(cs, null);
    }

    public MARSHAL invalidSimpleTypecode(Throwable t2) {
        return this.invalidSimpleTypecode(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL invalidSimpleTypecode() {
        return this.invalidSimpleTypecode(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL invalidComplexTypecode(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079733, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.invalidComplexTypecode", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL invalidComplexTypecode(CompletionStatus cs) {
        return this.invalidComplexTypecode(cs, null);
    }

    public MARSHAL invalidComplexTypecode(Throwable t2) {
        return this.invalidComplexTypecode(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL invalidComplexTypecode() {
        return this.invalidComplexTypecode(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL invalidTypecodeKindMarshal(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079734, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.invalidTypecodeKindMarshal", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL invalidTypecodeKindMarshal(CompletionStatus cs) {
        return this.invalidTypecodeKindMarshal(cs, null);
    }

    public MARSHAL invalidTypecodeKindMarshal(Throwable t2) {
        return this.invalidTypecodeKindMarshal(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL invalidTypecodeKindMarshal() {
        return this.invalidTypecodeKindMarshal(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL unexpectedUnionDefault(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079735, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unexpectedUnionDefault", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL unexpectedUnionDefault(CompletionStatus cs) {
        return this.unexpectedUnionDefault(cs, null);
    }

    public MARSHAL unexpectedUnionDefault(Throwable t2) {
        return this.unexpectedUnionDefault(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL unexpectedUnionDefault() {
        return this.unexpectedUnionDefault(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL illegalUnionDiscriminatorType(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079736, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.illegalUnionDiscriminatorType", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL illegalUnionDiscriminatorType(CompletionStatus cs) {
        return this.illegalUnionDiscriminatorType(cs, null);
    }

    public MARSHAL illegalUnionDiscriminatorType(Throwable t2) {
        return this.illegalUnionDiscriminatorType(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL illegalUnionDiscriminatorType() {
        return this.illegalUnionDiscriminatorType(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL couldNotSkipBytes(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        MARSHAL exc = new MARSHAL(1398079737, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.couldNotSkipBytes", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL couldNotSkipBytes(CompletionStatus cs, Object arg0, Object arg1) {
        return this.couldNotSkipBytes(cs, null, arg0, arg1);
    }

    public MARSHAL couldNotSkipBytes(Throwable t2, Object arg0, Object arg1) {
        return this.couldNotSkipBytes(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public MARSHAL couldNotSkipBytes(Object arg0, Object arg1) {
        return this.couldNotSkipBytes(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public MARSHAL badChunkLength(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        MARSHAL exc = new MARSHAL(1398079738, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.badChunkLength", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badChunkLength(CompletionStatus cs, Object arg0, Object arg1) {
        return this.badChunkLength(cs, null, arg0, arg1);
    }

    public MARSHAL badChunkLength(Throwable t2, Object arg0, Object arg1) {
        return this.badChunkLength(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public MARSHAL badChunkLength(Object arg0, Object arg1) {
        return this.badChunkLength(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public MARSHAL unableToLocateRepIdArray(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079739, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.unableToLocateRepIdArray", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL unableToLocateRepIdArray(CompletionStatus cs, Object arg0) {
        return this.unableToLocateRepIdArray(cs, null, arg0);
    }

    public MARSHAL unableToLocateRepIdArray(Throwable t2, Object arg0) {
        return this.unableToLocateRepIdArray(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL unableToLocateRepIdArray(Object arg0) {
        return this.unableToLocateRepIdArray(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL badFixed(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        MARSHAL exc = new MARSHAL(1398079740, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.badFixed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badFixed(CompletionStatus cs, Object arg0, Object arg1) {
        return this.badFixed(cs, null, arg0, arg1);
    }

    public MARSHAL badFixed(Throwable t2, Object arg0, Object arg1) {
        return this.badFixed(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public MARSHAL badFixed(Object arg0, Object arg1) {
        return this.badFixed(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public MARSHAL readObjectLoadClassFailure(CompletionStatus cs, Throwable t2, Object arg0, Object arg1) {
        MARSHAL exc = new MARSHAL(1398079741, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1};
            this.doLog(Level.WARNING, "ORBUTIL.readObjectLoadClassFailure", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL readObjectLoadClassFailure(CompletionStatus cs, Object arg0, Object arg1) {
        return this.readObjectLoadClassFailure(cs, null, arg0, arg1);
    }

    public MARSHAL readObjectLoadClassFailure(Throwable t2, Object arg0, Object arg1) {
        return this.readObjectLoadClassFailure(CompletionStatus.COMPLETED_NO, t2, arg0, arg1);
    }

    public MARSHAL readObjectLoadClassFailure(Object arg0, Object arg1) {
        return this.readObjectLoadClassFailure(CompletionStatus.COMPLETED_NO, null, arg0, arg1);
    }

    public MARSHAL couldNotInstantiateHelper(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079742, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.couldNotInstantiateHelper", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL couldNotInstantiateHelper(CompletionStatus cs, Object arg0) {
        return this.couldNotInstantiateHelper(cs, null, arg0);
    }

    public MARSHAL couldNotInstantiateHelper(Throwable t2, Object arg0) {
        return this.couldNotInstantiateHelper(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL couldNotInstantiateHelper(Object arg0) {
        return this.couldNotInstantiateHelper(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL badToaOaid(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079743, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badToaOaid", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badToaOaid(CompletionStatus cs) {
        return this.badToaOaid(cs, null);
    }

    public MARSHAL badToaOaid(Throwable t2) {
        return this.badToaOaid(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL badToaOaid() {
        return this.badToaOaid(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL couldNotInvokeHelperReadMethod(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079744, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.couldNotInvokeHelperReadMethod", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL couldNotInvokeHelperReadMethod(CompletionStatus cs, Object arg0) {
        return this.couldNotInvokeHelperReadMethod(cs, null, arg0);
    }

    public MARSHAL couldNotInvokeHelperReadMethod(Throwable t2, Object arg0) {
        return this.couldNotInvokeHelperReadMethod(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL couldNotInvokeHelperReadMethod(Object arg0) {
        return this.couldNotInvokeHelperReadMethod(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public MARSHAL couldNotFindClass(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079745, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.couldNotFindClass", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL couldNotFindClass(CompletionStatus cs) {
        return this.couldNotFindClass(cs, null);
    }

    public MARSHAL couldNotFindClass(Throwable t2) {
        return this.couldNotFindClass(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL couldNotFindClass() {
        return this.couldNotFindClass(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL badArgumentsNvlist(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079746, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.badArgumentsNvlist", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL badArgumentsNvlist(CompletionStatus cs) {
        return this.badArgumentsNvlist(cs, null);
    }

    public MARSHAL badArgumentsNvlist(Throwable t2) {
        return this.badArgumentsNvlist(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL badArgumentsNvlist() {
        return this.badArgumentsNvlist(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL stubCreateError(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398079747, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.stubCreateError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL stubCreateError(CompletionStatus cs) {
        return this.stubCreateError(cs, null);
    }

    public MARSHAL stubCreateError(Throwable t2) {
        return this.stubCreateError(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL stubCreateError() {
        return this.stubCreateError(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL javaSerializationException(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398079748, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "ORBUTIL.javaSerializationException", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL javaSerializationException(CompletionStatus cs, Object arg0) {
        return this.javaSerializationException(cs, null, arg0);
    }

    public MARSHAL javaSerializationException(Throwable t2, Object arg0) {
        return this.javaSerializationException(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public MARSHAL javaSerializationException(Object arg0) {
        return this.javaSerializationException(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public NO_IMPLEMENT genericNoImpl(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.genericNoImpl", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT genericNoImpl(CompletionStatus cs) {
        return this.genericNoImpl(cs, null);
    }

    public NO_IMPLEMENT genericNoImpl(Throwable t2) {
        return this.genericNoImpl(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT genericNoImpl() {
        return this.genericNoImpl(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT contextNotImplemented(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.contextNotImplemented", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT contextNotImplemented(CompletionStatus cs) {
        return this.contextNotImplemented(cs, null);
    }

    public NO_IMPLEMENT contextNotImplemented(Throwable t2) {
        return this.contextNotImplemented(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT contextNotImplemented() {
        return this.contextNotImplemented(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT getinterfaceNotImplemented(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.getinterfaceNotImplemented", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT getinterfaceNotImplemented(CompletionStatus cs) {
        return this.getinterfaceNotImplemented(cs, null);
    }

    public NO_IMPLEMENT getinterfaceNotImplemented(Throwable t2) {
        return this.getinterfaceNotImplemented(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT getinterfaceNotImplemented() {
        return this.getinterfaceNotImplemented(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT sendDeferredNotimplemented(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1398079692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.sendDeferredNotimplemented", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT sendDeferredNotimplemented(CompletionStatus cs) {
        return this.sendDeferredNotimplemented(cs, null);
    }

    public NO_IMPLEMENT sendDeferredNotimplemented(Throwable t2) {
        return this.sendDeferredNotimplemented(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT sendDeferredNotimplemented() {
        return this.sendDeferredNotimplemented(CompletionStatus.COMPLETED_NO, null);
    }

    public NO_IMPLEMENT longDoubleNotImplemented(CompletionStatus cs, Throwable t2) {
        NO_IMPLEMENT exc = new NO_IMPLEMENT(1398079693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.longDoubleNotImplemented", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public NO_IMPLEMENT longDoubleNotImplemented(CompletionStatus cs) {
        return this.longDoubleNotImplemented(cs, null);
    }

    public NO_IMPLEMENT longDoubleNotImplemented(Throwable t2) {
        return this.longDoubleNotImplemented(CompletionStatus.COMPLETED_NO, t2);
    }

    public NO_IMPLEMENT longDoubleNotImplemented() {
        return this.longDoubleNotImplemented(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER noServerScInDispatch(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.noServerScInDispatch", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER noServerScInDispatch(CompletionStatus cs) {
        return this.noServerScInDispatch(cs, null);
    }

    public OBJ_ADAPTER noServerScInDispatch(Throwable t2) {
        return this.noServerScInDispatch(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER noServerScInDispatch() {
        return this.noServerScInDispatch(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER orbConnectError(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.orbConnectError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER orbConnectError(CompletionStatus cs) {
        return this.orbConnectError(cs, null);
    }

    public OBJ_ADAPTER orbConnectError(Throwable t2) {
        return this.orbConnectError(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER orbConnectError() {
        return this.orbConnectError(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJ_ADAPTER adapterInactiveInActivation(CompletionStatus cs, Throwable t2) {
        OBJ_ADAPTER exc = new OBJ_ADAPTER(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.adapterInactiveInActivation", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public OBJ_ADAPTER adapterInactiveInActivation(CompletionStatus cs) {
        return this.adapterInactiveInActivation(cs, null);
    }

    public OBJ_ADAPTER adapterInactiveInActivation(Throwable t2) {
        return this.adapterInactiveInActivation(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJ_ADAPTER adapterInactiveInActivation() {
        return this.adapterInactiveInActivation(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST locateUnknownObject(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.locateUnknownObject", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST locateUnknownObject(CompletionStatus cs) {
        return this.locateUnknownObject(cs, null);
    }

    public OBJECT_NOT_EXIST locateUnknownObject(Throwable t2) {
        return this.locateUnknownObject(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST locateUnknownObject() {
        return this.locateUnknownObject(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST badServerId(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.badServerId", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST badServerId(CompletionStatus cs) {
        return this.badServerId(cs, null);
    }

    public OBJECT_NOT_EXIST badServerId(Throwable t2) {
        return this.badServerId(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST badServerId() {
        return this.badServerId(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST badSkeleton(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badSkeleton", parameters, ORBUtilSystemException.class, exc);
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

    public OBJECT_NOT_EXIST servantNotFound(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398079692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.servantNotFound", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST servantNotFound(CompletionStatus cs) {
        return this.servantNotFound(cs, null);
    }

    public OBJECT_NOT_EXIST servantNotFound(Throwable t2) {
        return this.servantNotFound(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST servantNotFound() {
        return this.servantNotFound(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST noObjectAdapterFactory(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398079693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.noObjectAdapterFactory", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST noObjectAdapterFactory(CompletionStatus cs) {
        return this.noObjectAdapterFactory(cs, null);
    }

    public OBJECT_NOT_EXIST noObjectAdapterFactory(Throwable t2) {
        return this.noObjectAdapterFactory(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST noObjectAdapterFactory() {
        return this.noObjectAdapterFactory(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST badAdapterId(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398079694, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.badAdapterId", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST badAdapterId(CompletionStatus cs) {
        return this.badAdapterId(cs, null);
    }

    public OBJECT_NOT_EXIST badAdapterId(Throwable t2) {
        return this.badAdapterId(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST badAdapterId() {
        return this.badAdapterId(CompletionStatus.COMPLETED_NO, null);
    }

    public OBJECT_NOT_EXIST dynAnyDestroyed(CompletionStatus cs, Throwable t2) {
        OBJECT_NOT_EXIST exc = new OBJECT_NOT_EXIST(1398079695, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.dynAnyDestroyed", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public OBJECT_NOT_EXIST dynAnyDestroyed(CompletionStatus cs) {
        return this.dynAnyDestroyed(cs, null);
    }

    public OBJECT_NOT_EXIST dynAnyDestroyed(Throwable t2) {
        return this.dynAnyDestroyed(CompletionStatus.COMPLETED_NO, t2);
    }

    public OBJECT_NOT_EXIST dynAnyDestroyed() {
        return this.dynAnyDestroyed(CompletionStatus.COMPLETED_NO, null);
    }

    public TRANSIENT requestCanceled(CompletionStatus cs, Throwable t2) {
        TRANSIENT exc = new TRANSIENT(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.requestCanceled", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public TRANSIENT requestCanceled(CompletionStatus cs) {
        return this.requestCanceled(cs, null);
    }

    public TRANSIENT requestCanceled(Throwable t2) {
        return this.requestCanceled(CompletionStatus.COMPLETED_NO, t2);
    }

    public TRANSIENT requestCanceled() {
        return this.requestCanceled(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN unknownCorbaExc(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398079689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unknownCorbaExc", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN unknownCorbaExc(CompletionStatus cs) {
        return this.unknownCorbaExc(cs, null);
    }

    public UNKNOWN unknownCorbaExc(Throwable t2) {
        return this.unknownCorbaExc(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN unknownCorbaExc() {
        return this.unknownCorbaExc(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN runtimeexception(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398079690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.runtimeexception", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN runtimeexception(CompletionStatus cs) {
        return this.runtimeexception(cs, null);
    }

    public UNKNOWN runtimeexception(Throwable t2) {
        return this.runtimeexception(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN runtimeexception() {
        return this.runtimeexception(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN unknownServerError(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398079691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unknownServerError", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN unknownServerError(CompletionStatus cs) {
        return this.unknownServerError(cs, null);
    }

    public UNKNOWN unknownServerError(Throwable t2) {
        return this.unknownServerError(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN unknownServerError() {
        return this.unknownServerError(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN unknownDsiSysex(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398079692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unknownDsiSysex", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN unknownDsiSysex(CompletionStatus cs) {
        return this.unknownDsiSysex(cs, null);
    }

    public UNKNOWN unknownDsiSysex(Throwable t2) {
        return this.unknownDsiSysex(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN unknownDsiSysex() {
        return this.unknownDsiSysex(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN unknownSysex(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398079693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.unknownSysex", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN unknownSysex(CompletionStatus cs) {
        return this.unknownSysex(cs, null);
    }

    public UNKNOWN unknownSysex(Throwable t2) {
        return this.unknownSysex(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN unknownSysex() {
        return this.unknownSysex(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN wrongInterfaceDef(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398079694, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.wrongInterfaceDef", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN wrongInterfaceDef(CompletionStatus cs) {
        return this.wrongInterfaceDef(cs, null);
    }

    public UNKNOWN wrongInterfaceDef(Throwable t2) {
        return this.wrongInterfaceDef(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN wrongInterfaceDef() {
        return this.wrongInterfaceDef(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN noInterfaceDefStub(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398079695, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "ORBUTIL.noInterfaceDefStub", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN noInterfaceDefStub(CompletionStatus cs) {
        return this.noInterfaceDefStub(cs, null);
    }

    public UNKNOWN noInterfaceDefStub(Throwable t2) {
        return this.noInterfaceDefStub(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN noInterfaceDefStub() {
        return this.noInterfaceDefStub(CompletionStatus.COMPLETED_NO, null);
    }

    public UNKNOWN unknownExceptionInDispatch(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398079697, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "ORBUTIL.unknownExceptionInDispatch", parameters, ORBUtilSystemException.class, exc);
        }
        return exc;
    }

    public UNKNOWN unknownExceptionInDispatch(CompletionStatus cs) {
        return this.unknownExceptionInDispatch(cs, null);
    }

    public UNKNOWN unknownExceptionInDispatch(Throwable t2) {
        return this.unknownExceptionInDispatch(CompletionStatus.COMPLETED_NO, t2);
    }

    public UNKNOWN unknownExceptionInDispatch() {
        return this.unknownExceptionInDispatch(CompletionStatus.COMPLETED_NO, null);
    }
}

