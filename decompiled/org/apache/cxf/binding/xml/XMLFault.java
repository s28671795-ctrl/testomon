/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.xml;

import java.util.ResourceBundle;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.interceptor.Fault;

public class XMLFault
extends Fault {
    public static final String XML_FAULT_PREFIX = "xfns";
    public static final String XML_FAULT_ROOT = "XMLFault";
    public static final String XML_FAULT_STRING = "faultstring";
    public static final String XML_FAULT_DETAIL = "detail";
    public static final String XML_FAULT_CODE_SERVER = "SERVER";
    public static final String XML_FAULT_CODE_CLIENT = "CLIENT";
    static final long serialVersionUID = 100000L;

    public XMLFault(Message message, Throwable throwable) {
        super(message, throwable);
    }

    public XMLFault(Message message) {
        super(message);
    }

    public XMLFault(String message) {
        super(new Message(message, (ResourceBundle)null, new Object[0]));
    }

    public static XMLFault createFault(Fault f) {
        if (f instanceof XMLFault) {
            return (XMLFault)f;
        }
        Throwable th = f.getCause();
        XMLFault xmlFault = new XMLFault(new Message(f.getMessage(), (ResourceBundle)null, new Object[0]), th);
        xmlFault.setDetail(f.getDetail());
        return xmlFault;
    }
}

