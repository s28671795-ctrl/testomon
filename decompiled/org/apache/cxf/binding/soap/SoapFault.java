/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.interceptor.Fault;

public class SoapFault
extends Fault {
    public static final QName ATTACHMENT_IO = new QName("http://www.w3.org/2003/05/soap-envelope", "AttachmentIOError");
    private static final long serialVersionUID = 5775857720028582429L;
    private List<QName> subCodes;
    private String role;
    private String node;
    private Map<String, String> namespaces = new HashMap<String, String>();

    public SoapFault(Message message, Throwable throwable, QName faultCode) {
        super(message, throwable, faultCode);
    }

    public SoapFault(Message message, QName faultCode) {
        super(message, faultCode);
    }

    public SoapFault(String message, QName faultCode) {
        super(new Message(message, (ResourceBundle)null, new Object[0]), faultCode);
    }

    public SoapFault(String message, ResourceBundle bundle2, QName faultCode) {
        super(new Message(message, bundle2, new Object[0]), faultCode);
    }

    public SoapFault(String message, ResourceBundle bundle2, Throwable t2, QName faultCode) {
        super(new Message(message, bundle2, new Object[0]), t2, faultCode);
    }

    public SoapFault(String message, ResourceBundle bundle2, QName faultCode, Object ... params) {
        super(new Message(message, bundle2, params), faultCode);
    }

    public SoapFault(String message, Throwable t2, QName faultCode) {
        super(new Message(message, (ResourceBundle)null, new Object[0]), t2, faultCode);
    }

    public String getCodeString(String prefix, String defaultPrefix) {
        return this.getFaultCodeString(prefix, defaultPrefix, this.getFaultCode());
    }

    public String getSubCodeString(String prefix, String defaultPrefix) {
        return this.getFaultCodeString(prefix, defaultPrefix, this.getRootSubCode());
    }

    private String getFaultCodeString(String prefix, String defaultPrefix, QName fCode) {
        String codePrefix = null;
        if (StringUtils.isEmpty(prefix)) {
            codePrefix = fCode.getPrefix();
            if (StringUtils.isEmpty(codePrefix)) {
                codePrefix = defaultPrefix;
            }
        } else {
            codePrefix = prefix;
        }
        return codePrefix + ":" + fCode.getLocalPart();
    }

    private QName getRootSubCode() {
        return this.subCodes != null && !this.subCodes.isEmpty() ? this.subCodes.get(0) : null;
    }

    private void setRootSubCode(QName subCode) {
        if (this.subCodes == null) {
            this.subCodes = new LinkedList<QName>();
        } else {
            this.subCodes.clear();
        }
        this.subCodes.add(subCode);
    }

    public String getReason() {
        return this.getMessage();
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String actor) {
        this.role = actor;
    }

    public String getNode() {
        return this.node;
    }

    public void setNode(String n) {
        this.node = n;
    }

    public QName getSubCode() {
        return this.getRootSubCode();
    }

    public List<QName> getSubCodes() {
        return this.subCodes;
    }

    public void setSubCode(QName subCode) {
        this.setRootSubCode(subCode);
    }

    public void setSubCodes(List<QName> subCodes) {
        this.subCodes = subCodes;
    }

    public void addSubCode(QName subCode) {
        if (this.subCodes == null) {
            this.subCodes = new LinkedList<QName>();
        }
        this.subCodes.add(subCode);
    }

    public Map<String, String> getNamespaces() {
        return this.namespaces;
    }

    public void setNamespaces(Map<String, String> namespaces) {
        this.namespaces = namespaces;
    }

    private static void updateSoap12FaultCodes(SoapFault f) {
        QName fc = f.getFaultCode();
        Soap12 v = Soap12.getInstance();
        if (fc.getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope") && (fc.equals(v.getReceiver()) || fc.equals(v.getSender()) || fc.equals(v.getMustUnderstand()) || fc.equals(v.getDateEncodingUnknown()) || fc.equals(v.getVersionMismatch()))) {
            return;
        }
        f.setFaultCode(Soap12.getInstance().getReceiver());
        if (f.getSubCodes() == null) {
            f.setRootSubCode(fc);
        } else if (!f.getSubCodes().contains(fc)) {
            f.getSubCodes().add(fc);
        }
    }

    public static SoapFault createFault(Fault f, SoapVersion v) {
        if (f instanceof SoapFault) {
            if (f.getFaultCode().getNamespaceURI().equals(Fault.FAULT_CODE_CLIENT.getNamespaceURI())) {
                QName fc = f.getFaultCode();
                if (Fault.FAULT_CODE_CLIENT.equals(fc)) {
                    fc = v.getSender();
                } else if (Fault.FAULT_CODE_SERVER.equals(fc)) {
                    fc = v.getReceiver();
                }
                f.setFaultCode(fc);
            }
            if (v == Soap12.getInstance()) {
                SoapFault.updateSoap12FaultCodes((SoapFault)f);
            }
            return (SoapFault)f;
        }
        QName fc = f.getFaultCode();
        if (Fault.FAULT_CODE_CLIENT.equals(fc)) {
            fc = v.getSender();
        } else if (Fault.FAULT_CODE_SERVER.equals(fc)) {
            fc = v.getReceiver();
        }
        SoapFault soapFault = new SoapFault(new Message(f.getMessage(), (ResourceBundle)null, new Object[0]), f.getCause(), fc);
        if (v == Soap12.getInstance()) {
            SoapFault.updateSoap12FaultCodes(soapFault);
        }
        soapFault.setDetail(f.getDetail());
        return soapFault;
    }
}

