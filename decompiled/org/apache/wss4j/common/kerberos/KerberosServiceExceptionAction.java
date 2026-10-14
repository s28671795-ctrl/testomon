/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.kerberos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Key;
import java.security.PrivilegedExceptionAction;
import javax.security.auth.kerberos.KerberosPrincipal;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.kerberos.KerberosServiceContext;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

public class KerberosServiceExceptionAction
implements PrivilegedExceptionAction<KerberosServiceContext> {
    private static final boolean IS_IBM_VENDOR = System.getProperty("java.vendor").startsWith("IBM");
    private static final String SUN_JGSS_INQUIRE_TYPE_CLASS = "com.sun.security.jgss.InquireType";
    private static final String SUN_JGSS_EXT_GSSCTX_CLASS = "com.sun.security.jgss.ExtendedGSSContext";
    private static final String IBM_JGSS_INQUIRE_TYPE_CLASS = "com.ibm.security.jgss.InquireType";
    private static final String IBM_JGSS_EXT_GSSCTX_CLASS = "com.ibm.security.jgss.ExtendedGSSContext";
    private static final String EXTENDED_JGSS_CONTEXT_INQUIRE_SEC_CONTEXT_METHOD_NAME = "inquireSecContext";
    private static final String EXTENDED_JGSS_CONTEXT_INQUIRE_TYPE_KRB5_GET_SESSION_KEY = "KRB5_GET_SESSION_KEY";
    private static final String JGSS_KERBEROS_TICKET_OID = "1.2.840.113554.1.2.2";
    private static final String JGSS_SPNEGO_TICKET_OID = "1.3.6.1.5.5.2";
    private static final String KERBEROS_TICKET_VALIDATION_ERROR_MSG_ID = "kerberosTicketValidationError";
    private byte[] ticket;
    private String serviceName;
    private boolean isUsernameServiceNameForm;
    private boolean spnego;

    public KerberosServiceExceptionAction(byte[] ticket, String serviceName, boolean isUsernameServiceNameForm, boolean spnego) {
        this.ticket = ticket;
        this.serviceName = serviceName;
        this.isUsernameServiceNameForm = isUsernameServiceNameForm;
        this.spnego = spnego;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public KerberosServiceContext run() throws GSSException, WSSecurityException {
        Oid oid;
        GSSManager gssManager = GSSManager.getInstance();
        GSSContext secContext = null;
        GSSName gssService = gssManager.createName(this.serviceName, this.isUsernameServiceNameForm ? GSSName.NT_USER_NAME : GSSName.NT_HOSTBASED_SERVICE);
        if (this.spnego) {
            oid = new Oid(JGSS_SPNEGO_TICKET_OID);
            secContext = gssManager.createContext(gssService, oid, null, 0);
        } else {
            oid = new Oid(JGSS_KERBEROS_TICKET_OID);
            GSSCredential credentials = gssManager.createCredential(gssService, 0, oid, 2);
            secContext = gssManager.createContext(credentials);
        }
        KerberosServiceContext krbServiceCtx = null;
        try {
            byte[] returnedToken = secContext.acceptSecContext(this.ticket, 0, this.ticket.length);
            krbServiceCtx = new KerberosServiceContext();
            if (secContext.getCredDelegState()) {
                krbServiceCtx.setDelegationCredential(secContext.getDelegCred());
            }
            GSSName clientName = secContext.getSrcName();
            krbServiceCtx.setPrincipal(new KerberosPrincipal(clientName.toString()));
            krbServiceCtx.setGssContext(secContext);
            krbServiceCtx.setKerberosToken(returnedToken);
            try {
                Class<?> inquireType = Class.forName(IS_IBM_VENDOR ? IBM_JGSS_INQUIRE_TYPE_CLASS : SUN_JGSS_INQUIRE_TYPE_CLASS);
                Class<?> extendedGSSContext = Class.forName(IS_IBM_VENDOR ? IBM_JGSS_EXT_GSSCTX_CLASS : SUN_JGSS_EXT_GSSCTX_CLASS);
                Method inquireSecContext = extendedGSSContext.getMethod(EXTENDED_JGSS_CONTEXT_INQUIRE_SEC_CONTEXT_METHOD_NAME, inquireType);
                Object obj = Enum.valueOf(inquireType, EXTENDED_JGSS_CONTEXT_INQUIRE_TYPE_KRB5_GET_SESSION_KEY);
                Key key = (Key)inquireSecContext.invoke((Object)secContext, obj);
                krbServiceCtx.setSessionKey(key);
            }
            catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, KERBEROS_TICKET_VALIDATION_ERROR_MSG_ID);
            }
        }
        finally {
            if (null != secContext && !this.spnego) {
                secContext.dispose();
            }
        }
        return krbServiceCtx;
    }
}

