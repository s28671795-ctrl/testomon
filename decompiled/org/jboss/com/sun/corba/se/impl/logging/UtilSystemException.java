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
import org.omg.CORBA.DATA_CONVERSION;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.UNKNOWN;

public class UtilSystemException
extends LogWrapperBase {
    private static LogWrapperFactory factory = new LogWrapperFactory(){

        @Override
        public LogWrapperBase create(Logger logger2) {
            return new UtilSystemException(logger2);
        }
    };
    public static final int STUB_FACTORY_COULD_NOT_MAKE_STUB = 1398080889;
    public static final int ERROR_IN_MAKE_STUB_FROM_REPOSITORY_ID = 1398080890;
    public static final int CLASS_CAST_EXCEPTION_IN_LOAD_STUB = 1398080891;
    public static final int EXCEPTION_IN_LOAD_STUB = 1398080892;
    public static final int NO_POA = 1398080890;
    public static final int CONNECT_WRONG_ORB = 1398080891;
    public static final int CONNECT_NO_TIE = 1398080892;
    public static final int CONNECT_TIE_WRONG_ORB = 1398080893;
    public static final int CONNECT_TIE_NO_SERVANT = 1398080894;
    public static final int LOAD_TIE_FAILED = 1398080895;
    public static final int BAD_HEX_DIGIT = 1398080889;
    public static final int UNABLE_LOCATE_VALUE_HELPER = 1398080890;
    public static final int INVALID_INDIRECTION = 1398080891;
    public static final int OBJECT_NOT_CONNECTED = 1398080889;
    public static final int COULD_NOT_LOAD_STUB = 1398080890;
    public static final int OBJECT_NOT_EXPORTED = 1398080891;
    public static final int ERROR_SET_OBJECT_FIELD = 1398080889;
    public static final int ERROR_SET_BOOLEAN_FIELD = 1398080890;
    public static final int ERROR_SET_BYTE_FIELD = 1398080891;
    public static final int ERROR_SET_CHAR_FIELD = 1398080892;
    public static final int ERROR_SET_SHORT_FIELD = 1398080893;
    public static final int ERROR_SET_INT_FIELD = 1398080894;
    public static final int ERROR_SET_LONG_FIELD = 1398080895;
    public static final int ERROR_SET_FLOAT_FIELD = 1398080896;
    public static final int ERROR_SET_DOUBLE_FIELD = 1398080897;
    public static final int ILLEGAL_FIELD_ACCESS = 1398080898;
    public static final int BAD_BEGIN_UNMARSHAL_CUSTOM_VALUE = 1398080899;
    public static final int CLASS_NOT_FOUND = 1398080900;
    public static final int UNKNOWN_SYSEX = 1398080889;

    public UtilSystemException(Logger logger2) {
        super(logger2);
    }

    public static UtilSystemException get(ORB orb, String logDomain) {
        UtilSystemException wrapper = (UtilSystemException)orb.getLogWrapper(logDomain, "UTIL", factory);
        return wrapper;
    }

    public static UtilSystemException get(String logDomain) {
        UtilSystemException wrapper = (UtilSystemException)ORB.staticGetLogWrapper(logDomain, "UTIL", factory);
        return wrapper;
    }

    public BAD_OPERATION stubFactoryCouldNotMakeStub(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398080889, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "UTIL.stubFactoryCouldNotMakeStub", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION stubFactoryCouldNotMakeStub(CompletionStatus cs) {
        return this.stubFactoryCouldNotMakeStub(cs, null);
    }

    public BAD_OPERATION stubFactoryCouldNotMakeStub(Throwable t2) {
        return this.stubFactoryCouldNotMakeStub(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION stubFactoryCouldNotMakeStub() {
        return this.stubFactoryCouldNotMakeStub(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION errorInMakeStubFromRepositoryId(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398080890, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "UTIL.errorInMakeStubFromRepositoryId", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION errorInMakeStubFromRepositoryId(CompletionStatus cs) {
        return this.errorInMakeStubFromRepositoryId(cs, null);
    }

    public BAD_OPERATION errorInMakeStubFromRepositoryId(Throwable t2) {
        return this.errorInMakeStubFromRepositoryId(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION errorInMakeStubFromRepositoryId() {
        return this.errorInMakeStubFromRepositoryId(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION classCastExceptionInLoadStub(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398080891, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "UTIL.classCastExceptionInLoadStub", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION classCastExceptionInLoadStub(CompletionStatus cs) {
        return this.classCastExceptionInLoadStub(cs, null);
    }

    public BAD_OPERATION classCastExceptionInLoadStub(Throwable t2) {
        return this.classCastExceptionInLoadStub(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION classCastExceptionInLoadStub() {
        return this.classCastExceptionInLoadStub(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_OPERATION exceptionInLoadStub(CompletionStatus cs, Throwable t2) {
        BAD_OPERATION exc = new BAD_OPERATION(1398080892, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "UTIL.exceptionInLoadStub", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_OPERATION exceptionInLoadStub(CompletionStatus cs) {
        return this.exceptionInLoadStub(cs, null);
    }

    public BAD_OPERATION exceptionInLoadStub(Throwable t2) {
        return this.exceptionInLoadStub(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_OPERATION exceptionInLoadStub() {
        return this.exceptionInLoadStub(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM noPoa(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398080890, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "UTIL.noPoa", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM noPoa(CompletionStatus cs) {
        return this.noPoa(cs, null);
    }

    public BAD_PARAM noPoa(Throwable t2) {
        return this.noPoa(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM noPoa() {
        return this.noPoa(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM connectWrongOrb(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398080891, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = null;
            this.doLog(Level.FINE, "UTIL.connectWrongOrb", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM connectWrongOrb(CompletionStatus cs) {
        return this.connectWrongOrb(cs, null);
    }

    public BAD_PARAM connectWrongOrb(Throwable t2) {
        return this.connectWrongOrb(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM connectWrongOrb() {
        return this.connectWrongOrb(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM connectNoTie(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398080892, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "UTIL.connectNoTie", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM connectNoTie(CompletionStatus cs) {
        return this.connectNoTie(cs, null);
    }

    public BAD_PARAM connectNoTie(Throwable t2) {
        return this.connectNoTie(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM connectNoTie() {
        return this.connectNoTie(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM connectTieWrongOrb(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398080893, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "UTIL.connectTieWrongOrb", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM connectTieWrongOrb(CompletionStatus cs) {
        return this.connectTieWrongOrb(cs, null);
    }

    public BAD_PARAM connectTieWrongOrb(Throwable t2) {
        return this.connectTieWrongOrb(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM connectTieWrongOrb() {
        return this.connectTieWrongOrb(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM connectTieNoServant(CompletionStatus cs, Throwable t2) {
        BAD_PARAM exc = new BAD_PARAM(1398080894, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "UTIL.connectTieNoServant", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM connectTieNoServant(CompletionStatus cs) {
        return this.connectTieNoServant(cs, null);
    }

    public BAD_PARAM connectTieNoServant(Throwable t2) {
        return this.connectTieNoServant(CompletionStatus.COMPLETED_NO, t2);
    }

    public BAD_PARAM connectTieNoServant() {
        return this.connectTieNoServant(CompletionStatus.COMPLETED_NO, null);
    }

    public BAD_PARAM loadTieFailed(CompletionStatus cs, Throwable t2, Object arg0) {
        BAD_PARAM exc = new BAD_PARAM(1398080895, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.FINE)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.FINE, "UTIL.loadTieFailed", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public BAD_PARAM loadTieFailed(CompletionStatus cs, Object arg0) {
        return this.loadTieFailed(cs, null, arg0);
    }

    public BAD_PARAM loadTieFailed(Throwable t2, Object arg0) {
        return this.loadTieFailed(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public BAD_PARAM loadTieFailed(Object arg0) {
        return this.loadTieFailed(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public DATA_CONVERSION badHexDigit(CompletionStatus cs, Throwable t2) {
        DATA_CONVERSION exc = new DATA_CONVERSION(1398080889, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "UTIL.badHexDigit", parameters, UtilSystemException.class, exc);
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

    public MARSHAL unableLocateValueHelper(CompletionStatus cs, Throwable t2) {
        MARSHAL exc = new MARSHAL(1398080890, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "UTIL.unableLocateValueHelper", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public MARSHAL unableLocateValueHelper(CompletionStatus cs) {
        return this.unableLocateValueHelper(cs, null);
    }

    public MARSHAL unableLocateValueHelper(Throwable t2) {
        return this.unableLocateValueHelper(CompletionStatus.COMPLETED_NO, t2);
    }

    public MARSHAL unableLocateValueHelper() {
        return this.unableLocateValueHelper(CompletionStatus.COMPLETED_NO, null);
    }

    public MARSHAL invalidIndirection(CompletionStatus cs, Throwable t2, Object arg0) {
        MARSHAL exc = new MARSHAL(1398080891, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "UTIL.invalidIndirection", parameters, UtilSystemException.class, exc);
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

    public INV_OBJREF objectNotConnected(CompletionStatus cs, Throwable t2, Object arg0) {
        INV_OBJREF exc = new INV_OBJREF(1398080889, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "UTIL.objectNotConnected", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INV_OBJREF objectNotConnected(CompletionStatus cs, Object arg0) {
        return this.objectNotConnected(cs, null, arg0);
    }

    public INV_OBJREF objectNotConnected(Throwable t2, Object arg0) {
        return this.objectNotConnected(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INV_OBJREF objectNotConnected(Object arg0) {
        return this.objectNotConnected(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INV_OBJREF couldNotLoadStub(CompletionStatus cs, Throwable t2, Object arg0) {
        INV_OBJREF exc = new INV_OBJREF(1398080890, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "UTIL.couldNotLoadStub", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INV_OBJREF couldNotLoadStub(CompletionStatus cs, Object arg0) {
        return this.couldNotLoadStub(cs, null, arg0);
    }

    public INV_OBJREF couldNotLoadStub(Throwable t2, Object arg0) {
        return this.couldNotLoadStub(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INV_OBJREF couldNotLoadStub(Object arg0) {
        return this.couldNotLoadStub(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INV_OBJREF objectNotExported(CompletionStatus cs, Throwable t2, Object arg0) {
        INV_OBJREF exc = new INV_OBJREF(1398080891, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "UTIL.objectNotExported", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INV_OBJREF objectNotExported(CompletionStatus cs, Object arg0) {
        return this.objectNotExported(cs, null, arg0);
    }

    public INV_OBJREF objectNotExported(Throwable t2, Object arg0) {
        return this.objectNotExported(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INV_OBJREF objectNotExported(Object arg0) {
        return this.objectNotExported(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL errorSetObjectField(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398080889, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "UTIL.errorSetObjectField", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorSetObjectField(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.errorSetObjectField(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetObjectField(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.errorSetObjectField(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL errorSetObjectField(Object arg0, Object arg1, Object arg2) {
        return this.errorSetObjectField(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetBooleanField(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398080890, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "UTIL.errorSetBooleanField", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorSetBooleanField(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.errorSetBooleanField(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetBooleanField(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.errorSetBooleanField(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL errorSetBooleanField(Object arg0, Object arg1, Object arg2) {
        return this.errorSetBooleanField(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetByteField(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398080891, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "UTIL.errorSetByteField", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorSetByteField(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.errorSetByteField(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetByteField(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.errorSetByteField(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL errorSetByteField(Object arg0, Object arg1, Object arg2) {
        return this.errorSetByteField(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetCharField(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398080892, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "UTIL.errorSetCharField", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorSetCharField(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.errorSetCharField(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetCharField(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.errorSetCharField(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL errorSetCharField(Object arg0, Object arg1, Object arg2) {
        return this.errorSetCharField(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetShortField(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398080893, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "UTIL.errorSetShortField", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorSetShortField(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.errorSetShortField(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetShortField(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.errorSetShortField(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL errorSetShortField(Object arg0, Object arg1, Object arg2) {
        return this.errorSetShortField(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetIntField(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398080894, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "UTIL.errorSetIntField", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorSetIntField(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.errorSetIntField(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetIntField(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.errorSetIntField(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL errorSetIntField(Object arg0, Object arg1, Object arg2) {
        return this.errorSetIntField(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetLongField(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398080895, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "UTIL.errorSetLongField", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorSetLongField(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.errorSetLongField(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetLongField(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.errorSetLongField(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL errorSetLongField(Object arg0, Object arg1, Object arg2) {
        return this.errorSetLongField(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetFloatField(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398080896, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "UTIL.errorSetFloatField", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorSetFloatField(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.errorSetFloatField(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetFloatField(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.errorSetFloatField(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL errorSetFloatField(Object arg0, Object arg1, Object arg2) {
        return this.errorSetFloatField(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetDoubleField(CompletionStatus cs, Throwable t2, Object arg0, Object arg1, Object arg2) {
        INTERNAL exc = new INTERNAL(1398080897, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0, arg1, arg2};
            this.doLog(Level.WARNING, "UTIL.errorSetDoubleField", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL errorSetDoubleField(CompletionStatus cs, Object arg0, Object arg1, Object arg2) {
        return this.errorSetDoubleField(cs, null, arg0, arg1, arg2);
    }

    public INTERNAL errorSetDoubleField(Throwable t2, Object arg0, Object arg1, Object arg2) {
        return this.errorSetDoubleField(CompletionStatus.COMPLETED_NO, t2, arg0, arg1, arg2);
    }

    public INTERNAL errorSetDoubleField(Object arg0, Object arg1, Object arg2) {
        return this.errorSetDoubleField(CompletionStatus.COMPLETED_NO, null, arg0, arg1, arg2);
    }

    public INTERNAL illegalFieldAccess(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398080898, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "UTIL.illegalFieldAccess", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL illegalFieldAccess(CompletionStatus cs, Object arg0) {
        return this.illegalFieldAccess(cs, null, arg0);
    }

    public INTERNAL illegalFieldAccess(Throwable t2, Object arg0) {
        return this.illegalFieldAccess(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL illegalFieldAccess(Object arg0) {
        return this.illegalFieldAccess(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public INTERNAL badBeginUnmarshalCustomValue(CompletionStatus cs, Throwable t2) {
        INTERNAL exc = new INTERNAL(1398080899, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "UTIL.badBeginUnmarshalCustomValue", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL badBeginUnmarshalCustomValue(CompletionStatus cs) {
        return this.badBeginUnmarshalCustomValue(cs, null);
    }

    public INTERNAL badBeginUnmarshalCustomValue(Throwable t2) {
        return this.badBeginUnmarshalCustomValue(CompletionStatus.COMPLETED_NO, t2);
    }

    public INTERNAL badBeginUnmarshalCustomValue() {
        return this.badBeginUnmarshalCustomValue(CompletionStatus.COMPLETED_NO, null);
    }

    public INTERNAL classNotFound(CompletionStatus cs, Throwable t2, Object arg0) {
        INTERNAL exc = new INTERNAL(1398080900, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = new Object[]{arg0};
            this.doLog(Level.WARNING, "UTIL.classNotFound", parameters, UtilSystemException.class, exc);
        }
        return exc;
    }

    public INTERNAL classNotFound(CompletionStatus cs, Object arg0) {
        return this.classNotFound(cs, null, arg0);
    }

    public INTERNAL classNotFound(Throwable t2, Object arg0) {
        return this.classNotFound(CompletionStatus.COMPLETED_NO, t2, arg0);
    }

    public INTERNAL classNotFound(Object arg0) {
        return this.classNotFound(CompletionStatus.COMPLETED_NO, null, arg0);
    }

    public UNKNOWN unknownSysex(CompletionStatus cs, Throwable t2) {
        UNKNOWN exc = new UNKNOWN(1398080889, cs);
        if (t2 != null) {
            exc.initCause(t2);
        }
        if (this.logger.isLoggable(Level.WARNING)) {
            Object[] parameters = null;
            this.doLog(Level.WARNING, "UTIL.unknownSysex", parameters, UtilSystemException.class, exc);
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
}

