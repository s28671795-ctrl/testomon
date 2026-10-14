/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.kerberos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Key;
import java.security.Principal;
import java.security.PrivilegedExceptionAction;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.kerberos.KerberosContext;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

public class KerberosClientExceptionAction
implements PrivilegedExceptionAction<KerberosContext> {
    private static final boolean IS_IBM_VENDOR = System.getProperty("java.vendor").startsWith("IBM");
    private static final String SUN_JGSS_INQUIRE_TYPE_CLASS = "com.sun.security.jgss.InquireType";
    private static final String SUN_JGSS_EXT_GSSCTX_CLASS = "com.sun.security.jgss.ExtendedGSSContext";
    private static final String IBM_JGSS_INQUIRE_TYPE_CLASS = "com.ibm.security.jgss.InquireType";
    private static final String IBM_JGSS_EXT_GSSCTX_CLASS = "com.ibm.security.jgss.ExtendedGSSContext";
    private static final String JGSS_KERBEROS_TICKET_OID = "1.2.840.113554.1.2.2";
    private static final String JGSS_SPNEGO_TICKET_OID = "1.3.6.1.5.5.2";
    private Principal clientPrincipal;
    private String serviceName;
    private boolean isUsernameServiceNameForm;
    private boolean requestCredDeleg;
    private GSSCredential delegatedCredential;
    private boolean spnego;
    private boolean mutualAuth;

    public KerberosClientExceptionAction(Principal clientPrincipal, String serviceName, boolean isUsernameServiceNameForm, boolean requestCredDeleg) {
        this(clientPrincipal, serviceName, isUsernameServiceNameForm, requestCredDeleg, null, false, false);
    }

    public KerberosClientExceptionAction(Principal clientPrincipal, String serviceName, boolean isUsernameServiceNameForm, boolean requestCredDeleg, GSSCredential delegatedCredential, boolean spnego, boolean mutualAuth) {
        this.clientPrincipal = clientPrincipal;
        this.serviceName = serviceName;
        this.isUsernameServiceNameForm = isUsernameServiceNameForm;
        this.requestCredDeleg = requestCredDeleg;
        this.delegatedCredential = delegatedCredential;
        this.spnego = spnego;
        this.mutualAuth = mutualAuth;
    }

    @Override
    public KerberosContext run() throws GSSException, WSSecurityException {
        GSSManager gssManager = GSSManager.getInstance();
        GSSName gssService = gssManager.createName(this.serviceName, this.isUsernameServiceNameForm ? GSSName.NT_USER_NAME : GSSName.NT_HOSTBASED_SERVICE);
        Oid oid = null;
        GSSCredential credentials = this.delegatedCredential;
        if (this.spnego) {
            oid = new Oid(JGSS_SPNEGO_TICKET_OID);
        } else {
            oid = new Oid(JGSS_KERBEROS_TICKET_OID);
            if (credentials == null) {
                GSSName gssClient = gssManager.createName(this.clientPrincipal.getName(), GSSName.NT_USER_NAME);
                credentials = gssManager.createCredential(gssClient, 0, oid, 1);
            }
        }
        GSSContext secContext = gssManager.createContext(gssService, oid, credentials, 0);
        secContext.requestMutualAuth(this.mutualAuth);
        secContext.requestCredDeleg(this.requestCredDeleg);
        byte[] token = new byte[]{};
        byte[] returnedToken = secContext.initSecContext(token, 0, token.length);
        KerberosContext krbCtx = new KerberosContext();
        krbCtx.setGssContext(secContext);
        krbCtx.setKerberosToken(returnedToken);
        try {
            Class<?> inquireType = Class.forName(IS_IBM_VENDOR ? IBM_JGSS_INQUIRE_TYPE_CLASS : SUN_JGSS_INQUIRE_TYPE_CLASS);
            Class<?> extendedGSSContext = Class.forName(IS_IBM_VENDOR ? IBM_JGSS_EXT_GSSCTX_CLASS : SUN_JGSS_EXT_GSSCTX_CLASS);
            Method inquireSecContext = extendedGSSContext.getMethod("inquireSecContext", inquireType);
            Key key = (Key)inquireSecContext.invoke((Object)secContext, Enum.valueOf(inquireType, "KRB5_GET_SESSION_KEY"));
            krbCtx.setSecretKey(key);
        }
        catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e, "kerberosServiceTicketError");
        }
        return krbCtx;
    }
}

