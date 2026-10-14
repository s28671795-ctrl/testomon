/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.io.Externalizable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.rmi.CORBA.Util;
import javax.rmi.PortableRemoteObject;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.ExceptionHandlerImpl;
import org.jboss.com.sun.corba.se.spi.orb.ORB;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.DynamicMethodMarshaller;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class DynamicMethodMarshallerImpl
implements DynamicMethodMarshaller {
    Method method;
    ExceptionHandler ehandler;
    boolean hasArguments = true;
    boolean hasVoidResult = true;
    boolean needsArgumentCopy;
    boolean needsResultCopy;
    ReaderWriter[] argRWs = null;
    ReaderWriter resultRW = null;
    private static ReaderWriter booleanRW = new ReaderWriterBase("boolean"){

        @Override
        public java.lang.Object read(InputStream is) {
            boolean value = is.read_boolean();
            return new Boolean(value);
        }

        @Override
        public void write(OutputStream os, java.lang.Object value) {
            Boolean val = (Boolean)value;
            os.write_boolean(val);
        }
    };
    private static ReaderWriter byteRW = new ReaderWriterBase("byte"){

        @Override
        public java.lang.Object read(InputStream is) {
            byte value = is.read_octet();
            return new Byte(value);
        }

        @Override
        public void write(OutputStream os, java.lang.Object value) {
            Byte val = (Byte)value;
            os.write_octet(val);
        }
    };
    private static ReaderWriter charRW = new ReaderWriterBase("char"){

        @Override
        public java.lang.Object read(InputStream is) {
            char value = is.read_wchar();
            return new Character(value);
        }

        @Override
        public void write(OutputStream os, java.lang.Object value) {
            Character val = (Character)value;
            os.write_wchar(val.charValue());
        }
    };
    private static ReaderWriter shortRW = new ReaderWriterBase("short"){

        @Override
        public java.lang.Object read(InputStream is) {
            short value = is.read_short();
            return new Short(value);
        }

        @Override
        public void write(OutputStream os, java.lang.Object value) {
            Short val = (Short)value;
            os.write_short(val);
        }
    };
    private static ReaderWriter intRW = new ReaderWriterBase("int"){

        @Override
        public java.lang.Object read(InputStream is) {
            int value = is.read_long();
            return new Integer(value);
        }

        @Override
        public void write(OutputStream os, java.lang.Object value) {
            Integer val = (Integer)value;
            os.write_long(val);
        }
    };
    private static ReaderWriter longRW = new ReaderWriterBase("long"){

        @Override
        public java.lang.Object read(InputStream is) {
            long value = is.read_longlong();
            return new Long(value);
        }

        @Override
        public void write(OutputStream os, java.lang.Object value) {
            Long val = (Long)value;
            os.write_longlong(val);
        }
    };
    private static ReaderWriter floatRW = new ReaderWriterBase("float"){

        @Override
        public java.lang.Object read(InputStream is) {
            float value = is.read_float();
            return new Float(value);
        }

        @Override
        public void write(OutputStream os, java.lang.Object value) {
            Float val = (Float)value;
            os.write_float(val.floatValue());
        }
    };
    private static ReaderWriter doubleRW = new ReaderWriterBase("double"){

        @Override
        public java.lang.Object read(InputStream is) {
            double value = is.read_double();
            return new Double(value);
        }

        @Override
        public void write(OutputStream os, java.lang.Object value) {
            Double val = (Double)value;
            os.write_double(val);
        }
    };
    private static ReaderWriter corbaObjectRW = new ReaderWriterBase("org.omg.CORBA.Object"){

        @Override
        public java.lang.Object read(InputStream is) {
            return is.read_Object();
        }

        @Override
        public void write(OutputStream os, java.lang.Object value) {
            os.write_Object((Object)value);
        }
    };
    private static ReaderWriter anyRW = new ReaderWriterBase("any"){

        @Override
        public java.lang.Object read(InputStream is) {
            return Util.readAny(is);
        }

        @Override
        public void write(OutputStream os, java.lang.Object value) {
            Util.writeAny(os, value);
        }
    };
    private static ReaderWriter abstractInterfaceRW = new ReaderWriterBase("abstract_interface"){

        @Override
        public java.lang.Object read(InputStream is) {
            return is.read_abstract_interface();
        }

        @Override
        public void write(OutputStream os, java.lang.Object value) {
            Util.writeAbstractObject(os, value);
        }
    };

    private static boolean isAnyClass(Class<?> cls) {
        return cls.equals(java.lang.Object.class) || cls.equals(Serializable.class) || cls.equals(Externalizable.class);
    }

    private static boolean isAbstractInterface(Class<?> cls) {
        if (IDLEntity.class.isAssignableFrom(cls)) {
            return cls.isInterface();
        }
        return cls.isInterface() && DynamicMethodMarshallerImpl.allMethodsThrowRemoteException(cls);
    }

    private static boolean allMethodsThrowRemoteException(Class<?> cls) {
        Method[] methods = cls.getMethods();
        for (int ctr = 0; ctr < methods.length; ++ctr) {
            Method method2 = methods[ctr];
            if (method2.getDeclaringClass() == java.lang.Object.class || DynamicMethodMarshallerImpl.throwsRemote(method2)) continue;
            return false;
        }
        return true;
    }

    private static boolean throwsRemote(Method method2) {
        Class<?>[] exceptionTypes = method2.getExceptionTypes();
        for (int ctr = 0; ctr < exceptionTypes.length; ++ctr) {
            Class<?> exceptionType = exceptionTypes[ctr];
            if (!RemoteException.class.isAssignableFrom(exceptionType)) continue;
            return true;
        }
        return false;
    }

    public static ReaderWriter makeReaderWriter(final Class<?> cls) {
        if (cls.equals(Boolean.TYPE)) {
            return booleanRW;
        }
        if (cls.equals(Byte.TYPE)) {
            return byteRW;
        }
        if (cls.equals(Character.TYPE)) {
            return charRW;
        }
        if (cls.equals(Short.TYPE)) {
            return shortRW;
        }
        if (cls.equals(Integer.TYPE)) {
            return intRW;
        }
        if (cls.equals(Long.TYPE)) {
            return longRW;
        }
        if (cls.equals(Float.TYPE)) {
            return floatRW;
        }
        if (cls.equals(Double.TYPE)) {
            return doubleRW;
        }
        if (Remote.class.isAssignableFrom(cls)) {
            return new ReaderWriterBase("remote(" + cls.getName() + ")"){

                @Override
                public java.lang.Object read(InputStream is) {
                    return PortableRemoteObject.narrow(is.read_Object(), cls);
                }

                @Override
                public void write(OutputStream os, java.lang.Object value) {
                    Util.writeRemoteObject(os, value);
                }
            };
        }
        if (cls.equals(Object.class)) {
            return corbaObjectRW;
        }
        if (Object.class.isAssignableFrom(cls)) {
            return new ReaderWriterBase("org.omg.CORBA.Object(" + cls.getName() + ")"){

                @Override
                public java.lang.Object read(InputStream is) {
                    return is.read_Object(cls);
                }

                @Override
                public void write(OutputStream os, java.lang.Object value) {
                    os.write_Object((Object)value);
                }
            };
        }
        if (DynamicMethodMarshallerImpl.isAnyClass(cls)) {
            return anyRW;
        }
        if (DynamicMethodMarshallerImpl.isAbstractInterface(cls)) {
            return abstractInterfaceRW;
        }
        return new ReaderWriterBase("value(" + cls.getName() + ")"){

            @Override
            public java.lang.Object read(InputStream is) {
                return is.read_value(cls);
            }

            @Override
            public void write(OutputStream os, java.lang.Object value) {
                os.write_value((Serializable)value, cls);
            }
        };
    }

    public DynamicMethodMarshallerImpl(Method method2) {
        this.method = method2;
        this.ehandler = new ExceptionHandlerImpl(method2.getExceptionTypes());
        this.needsArgumentCopy = false;
        Class<?>[] argTypes = method2.getParameterTypes();
        boolean bl = this.hasArguments = argTypes.length > 0;
        if (this.hasArguments) {
            this.argRWs = new ReaderWriter[argTypes.length];
            for (int ctr = 0; ctr < argTypes.length; ++ctr) {
                if (!argTypes[ctr].isPrimitive()) {
                    this.needsArgumentCopy = true;
                }
                this.argRWs[ctr] = DynamicMethodMarshallerImpl.makeReaderWriter(argTypes[ctr]);
            }
        }
        Class<?> resultType = method2.getReturnType();
        this.needsResultCopy = false;
        this.hasVoidResult = resultType.equals(Void.TYPE);
        if (!this.hasVoidResult) {
            this.needsResultCopy = !resultType.isPrimitive();
            this.resultRW = DynamicMethodMarshallerImpl.makeReaderWriter(resultType);
        }
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public java.lang.Object[] copyArguments(java.lang.Object[] args2, ORB orb) throws RemoteException {
        if (this.needsArgumentCopy) {
            return Util.copyObjects(args2, orb);
        }
        return args2;
    }

    @Override
    public java.lang.Object[] readArguments(InputStream is) {
        java.lang.Object[] result = null;
        if (this.hasArguments) {
            result = new java.lang.Object[this.argRWs.length];
            for (int ctr = 0; ctr < this.argRWs.length; ++ctr) {
                result[ctr] = this.argRWs[ctr].read(is);
            }
        }
        return result;
    }

    @Override
    public void writeArguments(OutputStream os, java.lang.Object[] args2) {
        if (this.hasArguments) {
            if (args2.length != this.argRWs.length) {
                throw new IllegalArgumentException("Expected " + this.argRWs.length + " arguments, but got " + args2.length + " arguments.");
            }
            for (int ctr = 0; ctr < this.argRWs.length; ++ctr) {
                this.argRWs[ctr].write(os, args2[ctr]);
            }
        }
    }

    @Override
    public java.lang.Object copyResult(java.lang.Object result, ORB orb) throws RemoteException {
        if (this.needsResultCopy) {
            return Util.copyObject(result, orb);
        }
        return result;
    }

    @Override
    public java.lang.Object readResult(InputStream is) {
        if (this.hasVoidResult) {
            return null;
        }
        return this.resultRW.read(is);
    }

    @Override
    public void writeResult(OutputStream os, java.lang.Object result) {
        if (!this.hasVoidResult) {
            this.resultRW.write(os, result);
        }
    }

    @Override
    public boolean isDeclaredException(Throwable thr) {
        return this.ehandler.isDeclaredException(thr.getClass());
    }

    @Override
    public void writeException(OutputStream os, Exception ex) {
        this.ehandler.writeException(os, ex);
    }

    @Override
    public Exception readException(ApplicationException ae) {
        return this.ehandler.readException(ae);
    }

    static abstract class ReaderWriterBase
    implements ReaderWriter {
        String name;

        public ReaderWriterBase(String name) {
            this.name = name;
        }

        public String toString() {
            return "ReaderWriter[" + this.name + "]";
        }
    }

    public static interface ReaderWriter {
        public java.lang.Object read(InputStream var1);

        public void write(OutputStream var1, java.lang.Object var2);
    }
}

