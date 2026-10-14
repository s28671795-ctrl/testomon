/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_CONTEXT;
import org.omg.CORBA.BAD_CONTEXTHelper;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_INV_ORDERHelper;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_OPERATIONHelper;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.BAD_PARAMHelper;
import org.omg.CORBA.BAD_QOS;
import org.omg.CORBA.BAD_QOSHelper;
import org.omg.CORBA.BAD_TYPECODE;
import org.omg.CORBA.BAD_TYPECODEHelper;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.omg.CORBA.CODESET_INCOMPATIBLEHelper;
import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.COMM_FAILUREHelper;
import org.omg.CORBA.CompletionStatusHelper;
import org.omg.CORBA.DATA_CONVERSION;
import org.omg.CORBA.DATA_CONVERSIONHelper;
import org.omg.CORBA.FREE_MEM;
import org.omg.CORBA.FREE_MEMHelper;
import org.omg.CORBA.IMP_LIMIT;
import org.omg.CORBA.IMP_LIMITHelper;
import org.omg.CORBA.INITIALIZE;
import org.omg.CORBA.INITIALIZEHelper;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.INTERNALHelper;
import org.omg.CORBA.INTF_REPOS;
import org.omg.CORBA.INTF_REPOSHelper;
import org.omg.CORBA.INVALID_TRANSACTION;
import org.omg.CORBA.INVALID_TRANSACTIONHelper;
import org.omg.CORBA.INV_FLAG;
import org.omg.CORBA.INV_FLAGHelper;
import org.omg.CORBA.INV_IDENT;
import org.omg.CORBA.INV_IDENTHelper;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.INV_OBJREFHelper;
import org.omg.CORBA.INV_POLICY;
import org.omg.CORBA.INV_POLICYHelper;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.MARSHALHelper;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.NO_IMPLEMENTHelper;
import org.omg.CORBA.NO_MEMORY;
import org.omg.CORBA.NO_MEMORYHelper;
import org.omg.CORBA.NO_PERMISSION;
import org.omg.CORBA.NO_PERMISSIONHelper;
import org.omg.CORBA.NO_RESOURCES;
import org.omg.CORBA.NO_RESOURCESHelper;
import org.omg.CORBA.NO_RESPONSE;
import org.omg.CORBA.NO_RESPONSEHelper;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.OBJECT_NOT_EXISTHelper;
import org.omg.CORBA.OBJ_ADAPTER;
import org.omg.CORBA.OBJ_ADAPTERHelper;
import org.omg.CORBA.ORB;
import org.omg.CORBA.PERSIST_STORE;
import org.omg.CORBA.PERSIST_STOREHelper;
import org.omg.CORBA.REBIND;
import org.omg.CORBA.REBINDHelper;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TIMEOUT;
import org.omg.CORBA.TIMEOUTHelper;
import org.omg.CORBA.TRANSACTION_MODE;
import org.omg.CORBA.TRANSACTION_MODEHelper;
import org.omg.CORBA.TRANSACTION_REQUIRED;
import org.omg.CORBA.TRANSACTION_REQUIREDHelper;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import org.omg.CORBA.TRANSACTION_ROLLEDBACKHelper;
import org.omg.CORBA.TRANSACTION_UNAVAILABLE;
import org.omg.CORBA.TRANSACTION_UNAVAILABLEHelper;
import org.omg.CORBA.TRANSIENT;
import org.omg.CORBA.TRANSIENTHelper;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UNKNOWN;
import org.omg.CORBA.UNKNOWNHelper;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public abstract class SystemExceptionHelper {
    private static TypeCode _type = ORB.init().create_exception_tc(SystemExceptionHelper.id(), "SystemException", new StructMember[]{new StructMember("minor", ORB.init().get_primitive_tc(TCKind.from_int(5)), null), new StructMember("completed", ORB.init().create_enum_tc(CompletionStatusHelper.id(), "CompletionStatus", new String[]{"COMPLETED_YES", "COMPLETED_NO", "COMPLETED_MAYBE"}), null)});

    public static void insert(Any any, SystemException s2) {
        any.type(SystemExceptionHelper.getTypeCode(s2));
        SystemExceptionHelper.write(any.create_output_stream(), s2);
    }

    public static SystemException extract(Any any) {
        return SystemExceptionHelper.read(any.create_input_stream());
    }

    public static TypeCode type() {
        return _type;
    }

    public static String id() {
        return "IDL:omg.org/CORBA/SystemException:1.0";
    }

    public static SystemException read(InputStream in) {
        String id = in.read_string();
        SystemException result = SystemExceptionHelper.getSystemException(id);
        result.minor = in.read_ulong();
        result.completed = CompletionStatusHelper.read(in);
        return result;
    }

    public static void write(OutputStream out, SystemException s2) {
        out.write_string(SystemExceptionHelper.getId(s2));
        out.write_ulong(s2.minor);
        CompletionStatusHelper.write(out, s2.completed);
    }

    private static TypeCode getTypeCode(SystemException s2) {
        if (s2 instanceof BAD_CONTEXT) {
            return BAD_CONTEXTHelper.type();
        }
        if (s2 instanceof BAD_INV_ORDER) {
            return BAD_INV_ORDERHelper.type();
        }
        if (s2 instanceof BAD_OPERATION) {
            return BAD_OPERATIONHelper.type();
        }
        if (s2 instanceof BAD_PARAM) {
            return BAD_PARAMHelper.type();
        }
        if (s2 instanceof BAD_QOS) {
            return BAD_QOSHelper.type();
        }
        if (s2 instanceof BAD_TYPECODE) {
            return BAD_TYPECODEHelper.type();
        }
        if (s2 instanceof CODESET_INCOMPATIBLE) {
            return CODESET_INCOMPATIBLEHelper.type();
        }
        if (s2 instanceof COMM_FAILURE) {
            return COMM_FAILUREHelper.type();
        }
        if (s2 instanceof DATA_CONVERSION) {
            return DATA_CONVERSIONHelper.type();
        }
        if (s2 instanceof FREE_MEM) {
            return FREE_MEMHelper.type();
        }
        if (s2 instanceof IMP_LIMIT) {
            return IMP_LIMITHelper.type();
        }
        if (s2 instanceof INITIALIZE) {
            return INITIALIZEHelper.type();
        }
        if (s2 instanceof INTERNAL) {
            return INTERNALHelper.type();
        }
        if (s2 instanceof INTF_REPOS) {
            return INTF_REPOSHelper.type();
        }
        if (s2 instanceof INVALID_TRANSACTION) {
            return INVALID_TRANSACTIONHelper.type();
        }
        if (s2 instanceof INV_FLAG) {
            return INV_FLAGHelper.type();
        }
        if (s2 instanceof INV_IDENT) {
            return INV_IDENTHelper.type();
        }
        if (s2 instanceof INV_OBJREF) {
            return INV_OBJREFHelper.type();
        }
        if (s2 instanceof INV_POLICY) {
            return INV_POLICYHelper.type();
        }
        if (s2 instanceof MARSHAL) {
            return MARSHALHelper.type();
        }
        if (s2 instanceof NO_IMPLEMENT) {
            return NO_IMPLEMENTHelper.type();
        }
        if (s2 instanceof NO_MEMORY) {
            return NO_MEMORYHelper.type();
        }
        if (s2 instanceof NO_PERMISSION) {
            return NO_PERMISSIONHelper.type();
        }
        if (s2 instanceof NO_RESOURCES) {
            return NO_RESOURCESHelper.type();
        }
        if (s2 instanceof NO_RESPONSE) {
            return NO_RESPONSEHelper.type();
        }
        if (s2 instanceof OBJECT_NOT_EXIST) {
            return OBJECT_NOT_EXISTHelper.type();
        }
        if (s2 instanceof OBJ_ADAPTER) {
            return OBJ_ADAPTERHelper.type();
        }
        if (s2 instanceof PERSIST_STORE) {
            return PERSIST_STOREHelper.type();
        }
        if (s2 instanceof REBIND) {
            return REBINDHelper.type();
        }
        if (s2 instanceof TIMEOUT) {
            return TIMEOUTHelper.type();
        }
        if (s2 instanceof TRANSACTION_MODE) {
            return TRANSACTION_MODEHelper.type();
        }
        if (s2 instanceof TRANSACTION_REQUIRED) {
            return TRANSACTION_REQUIREDHelper.type();
        }
        if (s2 instanceof TRANSACTION_ROLLEDBACK) {
            return TRANSACTION_ROLLEDBACKHelper.type();
        }
        if (s2 instanceof TRANSACTION_UNAVAILABLE) {
            return TRANSACTION_UNAVAILABLEHelper.type();
        }
        if (s2 instanceof TRANSIENT) {
            return TRANSIENTHelper.type();
        }
        if (s2 instanceof UNKNOWN) {
            return UNKNOWNHelper.type();
        }
        throw new BAD_PARAM("unrecognized system exception: " + s2.getClass().getName());
    }

    private static String getId(SystemException s2) {
        if (s2 instanceof BAD_CONTEXT) {
            return BAD_CONTEXTHelper.id();
        }
        if (s2 instanceof BAD_INV_ORDER) {
            return BAD_INV_ORDERHelper.id();
        }
        if (s2 instanceof BAD_OPERATION) {
            return BAD_OPERATIONHelper.id();
        }
        if (s2 instanceof BAD_PARAM) {
            return BAD_PARAMHelper.id();
        }
        if (s2 instanceof BAD_QOS) {
            return BAD_QOSHelper.id();
        }
        if (s2 instanceof BAD_TYPECODE) {
            return BAD_TYPECODEHelper.id();
        }
        if (s2 instanceof CODESET_INCOMPATIBLE) {
            return CODESET_INCOMPATIBLEHelper.id();
        }
        if (s2 instanceof COMM_FAILURE) {
            return COMM_FAILUREHelper.id();
        }
        if (s2 instanceof DATA_CONVERSION) {
            return DATA_CONVERSIONHelper.id();
        }
        if (s2 instanceof FREE_MEM) {
            return FREE_MEMHelper.id();
        }
        if (s2 instanceof IMP_LIMIT) {
            return IMP_LIMITHelper.id();
        }
        if (s2 instanceof INITIALIZE) {
            return INITIALIZEHelper.id();
        }
        if (s2 instanceof INTERNAL) {
            return INTERNALHelper.id();
        }
        if (s2 instanceof INTF_REPOS) {
            return INTF_REPOSHelper.id();
        }
        if (s2 instanceof INVALID_TRANSACTION) {
            return INVALID_TRANSACTIONHelper.id();
        }
        if (s2 instanceof INV_FLAG) {
            return INV_FLAGHelper.id();
        }
        if (s2 instanceof INV_IDENT) {
            return INV_IDENTHelper.id();
        }
        if (s2 instanceof INV_OBJREF) {
            return INV_OBJREFHelper.id();
        }
        if (s2 instanceof INV_POLICY) {
            return INV_POLICYHelper.id();
        }
        if (s2 instanceof MARSHAL) {
            return MARSHALHelper.id();
        }
        if (s2 instanceof NO_IMPLEMENT) {
            return NO_IMPLEMENTHelper.id();
        }
        if (s2 instanceof NO_MEMORY) {
            return NO_MEMORYHelper.id();
        }
        if (s2 instanceof NO_PERMISSION) {
            return NO_PERMISSIONHelper.id();
        }
        if (s2 instanceof NO_RESOURCES) {
            return NO_RESOURCESHelper.id();
        }
        if (s2 instanceof NO_RESPONSE) {
            return NO_RESPONSEHelper.id();
        }
        if (s2 instanceof OBJECT_NOT_EXIST) {
            return OBJECT_NOT_EXISTHelper.id();
        }
        if (s2 instanceof OBJ_ADAPTER) {
            return OBJ_ADAPTERHelper.id();
        }
        if (s2 instanceof PERSIST_STORE) {
            return PERSIST_STOREHelper.id();
        }
        if (s2 instanceof REBIND) {
            return REBINDHelper.id();
        }
        if (s2 instanceof TIMEOUT) {
            return TIMEOUTHelper.id();
        }
        if (s2 instanceof TRANSACTION_MODE) {
            return TRANSACTION_MODEHelper.id();
        }
        if (s2 instanceof TRANSACTION_REQUIRED) {
            return TRANSACTION_REQUIREDHelper.id();
        }
        if (s2 instanceof TRANSACTION_ROLLEDBACK) {
            return TRANSACTION_ROLLEDBACKHelper.id();
        }
        if (s2 instanceof TRANSACTION_UNAVAILABLE) {
            return TRANSACTION_UNAVAILABLEHelper.id();
        }
        if (s2 instanceof TRANSIENT) {
            return TRANSIENTHelper.id();
        }
        if (s2 instanceof UNKNOWN) {
            return UNKNOWNHelper.id();
        }
        throw new BAD_PARAM("unrecognized system exception: " + s2.getClass().getName());
    }

    private static SystemException getSystemException(String id) {
        if (id.equals(BAD_CONTEXTHelper.id())) {
            return new BAD_CONTEXT();
        }
        if (id.equals(BAD_INV_ORDERHelper.id())) {
            return new BAD_INV_ORDER();
        }
        if (id.equals(BAD_OPERATIONHelper.id())) {
            return new BAD_OPERATION();
        }
        if (id.equals(BAD_PARAMHelper.id())) {
            return new BAD_PARAM();
        }
        if (id.equals(BAD_QOSHelper.id())) {
            return new BAD_QOS();
        }
        if (id.equals(BAD_TYPECODEHelper.id())) {
            return new BAD_TYPECODE();
        }
        if (id.equals(CODESET_INCOMPATIBLEHelper.id())) {
            return new CODESET_INCOMPATIBLE();
        }
        if (id.equals(COMM_FAILUREHelper.id())) {
            return new COMM_FAILURE();
        }
        if (id.equals(DATA_CONVERSIONHelper.id())) {
            return new DATA_CONVERSION();
        }
        if (id.equals(FREE_MEMHelper.id())) {
            return new FREE_MEM();
        }
        if (id.equals(IMP_LIMITHelper.id())) {
            return new IMP_LIMIT();
        }
        if (id.equals(INITIALIZEHelper.id())) {
            return new INITIALIZE();
        }
        if (id.equals(INTERNALHelper.id())) {
            return new INTERNAL();
        }
        if (id.equals(INTF_REPOSHelper.id())) {
            return new INTF_REPOS();
        }
        if (id.equals(INVALID_TRANSACTIONHelper.id())) {
            return new INVALID_TRANSACTION();
        }
        if (id.equals(INV_FLAGHelper.id())) {
            return new INV_FLAG();
        }
        if (id.equals(INV_IDENTHelper.id())) {
            return new INV_IDENT();
        }
        if (id.equals(INV_OBJREFHelper.id())) {
            return new INV_OBJREF();
        }
        if (id.equals(INV_POLICYHelper.id())) {
            return new INV_POLICY();
        }
        if (id.equals(MARSHALHelper.id())) {
            return new MARSHAL();
        }
        if (id.equals(NO_IMPLEMENTHelper.id())) {
            return new NO_IMPLEMENT();
        }
        if (id.equals(NO_MEMORYHelper.id())) {
            return new NO_MEMORY();
        }
        if (id.equals(NO_PERMISSIONHelper.id())) {
            return new NO_PERMISSION();
        }
        if (id.equals(NO_RESOURCESHelper.id())) {
            return new NO_RESOURCES();
        }
        if (id.equals(NO_RESPONSEHelper.id())) {
            return new NO_RESPONSE();
        }
        if (id.equals(OBJECT_NOT_EXISTHelper.id())) {
            return new OBJECT_NOT_EXIST();
        }
        if (id.equals(OBJ_ADAPTERHelper.id())) {
            return new OBJ_ADAPTER();
        }
        if (id.equals(PERSIST_STOREHelper.id())) {
            return new PERSIST_STORE();
        }
        if (id.equals(REBINDHelper.id())) {
            return new REBIND();
        }
        if (id.equals(TIMEOUTHelper.id())) {
            return new TIMEOUT();
        }
        if (id.equals(TRANSACTION_MODEHelper.id())) {
            return new TRANSACTION_MODE();
        }
        if (id.equals(TRANSACTION_REQUIREDHelper.id())) {
            return new TRANSACTION_REQUIRED();
        }
        if (id.equals(TRANSACTION_ROLLEDBACKHelper.id())) {
            return new TRANSACTION_ROLLEDBACK();
        }
        if (id.equals(TRANSACTION_UNAVAILABLEHelper.id())) {
            return new TRANSACTION_UNAVAILABLE();
        }
        if (id.equals(TRANSIENTHelper.id())) {
            return new TRANSIENT();
        }
        if (id.equals(UNKNOWNHelper.id())) {
            return new UNKNOWN();
        }
        throw new MARSHAL("wrong or unrecognized id: " + id);
    }
}

