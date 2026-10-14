/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.attachment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.activation.DataHandler;
import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.message.Attachment;

public class AttachmentImpl
implements Attachment {
    private DataHandler dataHandler;
    private String id;
    private Map<String, String> headers = new HashMap<String, String>();
    private boolean xop;

    public AttachmentImpl(String idParam) {
        this.id = idParam;
    }

    public AttachmentImpl(String idParam, DataHandler handlerParam) {
        this.id = idParam;
        this.dataHandler = handlerParam;
        this.dataHandler.setCommandMap(AttachmentUtil.getCommandMap());
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public DataHandler getDataHandler() {
        return this.dataHandler;
    }

    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
        this.dataHandler.setCommandMap(AttachmentUtil.getCommandMap());
    }

    public void setHeader(String name, String value) {
        this.headers.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String value = this.headers.get(name);
        return value == null ? this.headers.get(name.toLowerCase()) : value;
    }

    @Override
    public Iterator<String> getHeaderNames() {
        return this.headers.keySet().iterator();
    }

    @Override
    public boolean isXOP() {
        return this.xop;
    }

    public void setXOP(boolean xopParam) {
        this.xop = xopParam;
    }
}

