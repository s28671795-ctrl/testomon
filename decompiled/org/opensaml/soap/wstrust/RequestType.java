/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.soap.wstrust;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.soap.wstrust.WSTrustObject;

public interface RequestType
extends XSURI,
WSTrustObject {
    public static final String ELEMENT_LOCAL_NAME = "RequestType";
    public static final QName ELEMENT_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestType", "wst");
    public static final String TYPE_LOCAL_NAME = "RequestTypeOpenEnum";
    public static final QName TYPE_NAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestTypeOpenEnum", "wst");
    public static final String ISSUE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Issue";
    public static final String RENEW = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Renew";
    public static final String CANCEL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Cancel";
    public static final String STSCANCEL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/STSCancel";
    public static final String VALIDATE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/Validate";
    public static final String KET = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/KET";
    public static final String BATCH_ISSUE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/BatchIssue";
    public static final String BATCH_RENEW = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/BatchRenew";
    public static final String BATCH_CANCEL = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/BatchCancel";
    public static final String BATCH_VALIDATE = "http://docs.oasis-open.org/ws-sx/ws-trust/200512/BatchValidate";
}

