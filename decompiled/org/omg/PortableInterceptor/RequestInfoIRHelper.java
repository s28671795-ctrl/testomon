/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableInterceptor;

import java.util.Hashtable;

public class RequestInfoIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("response_expected", "attribute;boolean");
        irInfo.put("arguments", "attribute;org.omg.Dynamic.ParameterList");
        irInfo.put("exceptions", "attribute;org.omg.Dynamic.ExceptionList");
        irInfo.put("get_slot", "(in:id org.omg.PortableInterceptor.SlotId)");
        irInfo.put("request_id", "attribute;int");
        irInfo.put("operation", "attribute;java.lang.String");
        irInfo.put("forward_reference", "attribute;org.omg.CORBA.Object");
        irInfo.put("contexts", "attribute;org.omg.Dynamic.ContextList");
        irInfo.put("reply_status", "attribute;org.omg.PortableInterceptor.ReplyStatus");
        irInfo.put("get_request_service_context", "(in:id org.omg.IOP.ServiceId)");
        irInfo.put("sync_scope", "attribute;org.omg.Messaging.SyncScope");
        irInfo.put("get_reply_service_context", "(in:id org.omg.IOP.ServiceId)");
        irInfo.put("operation_context", "attribute;org.omg.Dynamic.RequestContext");
        irInfo.put("result", "attribute;org.omg.CORBA.Any");
    }
}

