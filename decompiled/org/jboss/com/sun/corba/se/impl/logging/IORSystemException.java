/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.logging;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.com.sun.corba.se.spi.logging.LogWrapperBase;
import org.jboss.com.sun.corba.se.spi.logging.LogWrapperFactory;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.INV_OBJREF;

public class IORSystemException
extends LogWrapperBase {
    private static LogWrapperFactory factory = new LogWrapperFactory(){

        @Override
        public LogWrapperBase create(Logger logger2) {
            return new IORSystemException(logger2);
        }
    };
    public static final int ORT_NOT_INITIALIZED = 1398080689;
    public static final int NULL_POA = 1398080690;
    public static final int BAD_MAGIC = 1398080691;
    public static final int STRINGIFY_WRITE_ERROR = 1398080692;
    public static final int TAGGED_PROFILE_TEMPLATE_FACTORY_NOT_FOUND = 1398080693;
    public static final int INVALID_JDK1_3_1_PATCH_LEVEL = 1398080694;
    public static final int GET_LOCAL_SERVANT_FAILURE = 1398080695;
    public static final int ADAPTER_ID_NOT_AVAILABLE = 1398080689;
    public static final int SERVER_ID_NOT_AVAILABLE = 1398080690;
    public static final int ORB_ID_NOT_AVAILABLE = 1398080691;
    public static final int OBJECT_ADAPTER_ID_NOT_AVAILABLE = 1398080692;
    public static final int BAD_OID_IN_IOR_TEMPLATE_LIST = 1398080689;
    public static final int INVALID_TAGGED_PROFILE = 1398080690;
    public static final int BAD_IIOP_ADDRESS_PORT = 1398080691;
    public static final int IOR_MUST_HAVE_IIOP_PROFILE = 1398080689;

    public IORSystemException(Logger logger2) {
        super(logger2);
    }

    public static IORSystemException get(ORB orb, String logDomain) {
        IORSystemException wrapper = (IORSystemException)orb.getLogWrapper(logDomain, "IOR", factory);
        return wrapper;
    }

    public static IORSystemException get(String logDomain) {
        IORSystemException wrapper = (IORSystemException)ORB.staticGetLogWrapper(logDomain, "IOR", factory);
        return wrapper;
    }

    public INTERNAL ortNotInitialized(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "IOR.ortNotInitialized", parameters, IORSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL ortNotInitialized(CompletionStatus cs) {
        return this.ortNotInitialized(cs, null);
    }

    public INTERNAL ortNotInitialized(Throwable t2) {
        return this.ortNotInitialized(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL ortNotInitialized() {
        return this.ortNotInitialized(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL nullPoa(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "IOR.nullPoa", parameters, IORSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL nullPoa(CompletionStatus cs) {
        return this.nullPoa(cs, null);
    }

    public INTERNAL nullPoa(Throwable t2) {
        return this.nullPoa(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL nullPoa() {
        return this.nullPoa(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL badMagic(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398080691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "IOR.badMagic", parameters, IORSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badMagic(CompletionStatus cs, Object arg0) {
        return this.badMagic(cs, null, arg0);
    }

    public INTERNAL badMagic(Throwable t2, Object arg0) {
        return this.badMagic(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL badMagic(Object arg0) {
        return this.badMagic(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL stringifyWriteError(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "IOR.stringifyWriteError", parameters, IORSystemException.class, exc);
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

    public INTERNAL taggedProfileTemplateFactoryNotFound(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398080693, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "IOR.taggedProfileTemplateFactoryNotFound", parameters, IORSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL taggedProfileTemplateFactoryNotFound(CompletionStatus cs, Object arg0) {
        return this.taggedProfileTemplateFactoryNotFound(cs, null, arg0);
    }

    public INTERNAL taggedProfileTemplateFactoryNotFound(Throwable t2, Object arg0) {
        return this.taggedProfileTemplateFactoryNotFound(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL taggedProfileTemplateFactoryNotFound(Object arg0) {
        return this.taggedProfileTemplateFactoryNotFound(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL invalidJdk131PatchLevel(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398080694, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "IOR.invalidJdk131PatchLevel", parameters, IORSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL invalidJdk131PatchLevel(CompletionStatus cs, Object arg0) {
        return this.invalidJdk131PatchLevel(cs, null, arg0);
    }

    public INTERNAL invalidJdk131PatchLevel(Throwable t2, Object arg0) {
        return this.invalidJdk131PatchLevel(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL invalidJdk131PatchLevel(Object arg0) {
        return this.invalidJdk131PatchLevel(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL getLocalServantFailure(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398080695, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.FINE, "IOR.getLocalServantFailure", parameters, IORSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL getLocalServantFailure(CompletionStatus cs, Object arg0) {
        return this.getLocalServantFailure(cs, null, arg0);
    }

    public INTERNAL getLocalServantFailure(Throwable t2, Object arg0) {
        return this.getLocalServantFailure(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL getLocalServantFailure(Object arg0) {
        return this.getLocalServantFailure(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public BAD_OPERATION adapterIdNotAvailable(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398080689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "IOR.adapterIdNotAvailable", parameters, IORSystemException.class, exc);
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
        BAD_OPERATION exc = new BAD_OPERATION(1398080690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "IOR.serverIdNotAvailable", parameters, IORSystemException.class, exc);
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
        BAD_OPERATION exc = new BAD_OPERATION(1398080691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "IOR.orbIdNotAvailable", parameters, IORSystemException.class, exc);
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
        BAD_OPERATION exc = new BAD_OPERATION(1398080692, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "IOR.objectAdapterIdNotAvailable", parameters, IORSystemException.class, exc);
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

    public BAD_PARAM badOidInIorTemplateList(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398080689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "IOR.badOidInIorTemplateList", parameters, IORSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM badOidInIorTemplateList(CompletionStatus cs) {
        return this.badOidInIorTemplateList(cs, null);
    }

    public BAD_PARAM badOidInIorTemplateList(Throwable t2) {
        return this.badOidInIorTemplateList(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM badOidInIorTemplateList() {
        return this.badOidInIorTemplateList(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM invalidTaggedProfile(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398080690, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "IOR.invalidTaggedProfile", parameters, IORSystemException.class, exc);
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

    public BAD_PARAM badIiopAddressPort(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_PARAM exc = new BAD_PARAM(1398080691, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "IOR.badIiopAddressPort", parameters, IORSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM badIiopAddressPort(CompletionStatus cs, Object arg0) {
        return this.badIiopAddressPort(cs, null, arg0);
    }

    public BAD_PARAM badIiopAddressPort(Throwable t2, Object arg0) {
        return this.badIiopAddressPort(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_PARAM badIiopAddressPort(Object arg0) {
        return this.badIiopAddressPort(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INV_OBJREF iorMustHaveIiopProfile(CompletionStatus cs, Throwable t2) {
        INV_OBJREF exc = new INV_OBJREF(1398080689, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "IOR.iorMustHaveIiopProfile", parameters, IORSystemException.class, exc);
        }
        return exc;
    }

    public INV_OBJREF iorMustHaveIiopProfile(CompletionStatus cs) {
        return this.iorMustHaveIiopProfile(cs, null);
    }

    public INV_OBJREF iorMustHaveIiopProfile(Throwable t2) {
        return this.iorMustHaveIiopProfile(CompletionStatus.COMPLETED_NO, t2);
    }

    public INV_OBJREF iorMustHaveIiopProfile() {
        return this.iorMustHaveIiopProfile(CompletionStatus.COMPLETED_NO, null);
    }
}

