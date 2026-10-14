/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.IDLNameTranslatorImpl;
import org.omg.CORBA.UserException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public class ExceptionHandlerImpl
implements ExceptionHandler {
    private ExceptionRW[] rws;
    private final ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.presentation");

    public ExceptionHandlerImpl(Class<?>[] exceptions) {
        int count = 0;
        for (int ctr = 0; ctr < exceptions.length; ++ctr) {
            Class<?> cls = exceptions[ctr];
            if (RemoteException.class.isAssignableFrom(cls)) continue;
            ++count;
        }
        this.rws = new ExceptionRW[count];
        int index = 0;
        for (int ctr = 0; ctr < exceptions.length; ++ctr) {
            Class<?> cls = exceptions[ctr];
            if (RemoteException.class.isAssignableFrom(cls)) continue;
            ExceptionRWBase erw = null;
            erw = UserException.class.isAssignableFrom(cls) ? new ExceptionRWIDLImpl(cls) : new ExceptionRWRMIImpl(cls);
            this.rws[index++] = erw;
        }
    }

    private int findDeclaredException(Class<?> cls) {
        for (int ctr = 0; ctr < this.rws.length; ++ctr) {
            Class<?> next = this.rws[ctr].getExceptionClass();
            if (!next.isAssignableFrom(cls)) continue;
            return ctr;
        }
        return -1;
    }

    private int findDeclaredException(String repositoryId) {
        for (int ctr = 0; ctr < this.rws.length; ++ctr) {
            if (this.rws[ctr] == null) {
                return -1;
            }
            String rid = this.rws[ctr].getId();
            if (!repositoryId.equals(rid)) continue;
            return ctr;
        }
        return -1;
    }

    @Override
    public boolean isDeclaredException(Class<?> cls) {
        return this.findDeclaredException(cls) >= 0;
    }

    @Override
    public void writeException(OutputStream os, Exception ex) {
        int index = this.findDeclaredException(ex.getClass());
        if (index < 0) {
            throw this.wrapper.writeUndeclaredException(ex, (Object)ex.getClass().getName());
        }
        this.rws[index].write(os, ex);
    }

    @Override
    public Exception readException(ApplicationException ae) {
        InputStream is = (InputStream)ae.getInputStream();
        String excName = ae.getId();
        int index = this.findDeclaredException(excName);
        if (index < 0) {
            excName = is.read_string();
            UnexpectedException res = new UnexpectedException(excName);
            res.initCause(ae);
            return res;
        }
        return this.rws[index].read(is);
    }

    public ExceptionRW getRMIExceptionRW(Class<?> cls) {
        return new ExceptionRWRMIImpl(cls);
    }

    public class ExceptionRWRMIImpl
    extends ExceptionRWBase {
        public ExceptionRWRMIImpl(Class<?> cls) {
            super(cls);
            this.setId(IDLNameTranslatorImpl.getExceptionId(cls));
        }

        @Override
        public void write(OutputStream os, Exception ex) {
            os.write_string(this.getId());
            os.write_value((Serializable)ex, this.getExceptionClass());
        }

        @Override
        public Exception read(InputStream is) {
            is.read_string();
            return (Exception)is.read_value(this.getExceptionClass());
        }
    }

    public class ExceptionRWIDLImpl
    extends ExceptionRWBase {
        private Method readMethod;
        private Method writeMethod;

        public ExceptionRWIDLImpl(Class<?> cls) {
            Class[] argTypes;
            Class<?> helperClass;
            super(cls);
            String helperName = cls.getName() + "Helper";
            ClassLoader loader = cls.getClassLoader();
            try {
                helperClass = Class.forName(helperName, true, loader);
                Method idMethod = helperClass.getDeclaredMethod("id", null);
                this.setId((String)idMethod.invoke(null, (Object[])null));
            }
            catch (Exception ex) {
                throw ExceptionHandlerImpl.this.wrapper.badHelperIdMethod(ex, (Object)helperName);
            }
            try {
                argTypes = new Class[]{org.omg.CORBA.portable.OutputStream.class, cls};
                this.writeMethod = helperClass.getDeclaredMethod("write", argTypes);
            }
            catch (Exception ex) {
                throw ExceptionHandlerImpl.this.wrapper.badHelperWriteMethod(ex, (Object)helperName);
            }
            try {
                argTypes = new Class[]{org.omg.CORBA.portable.InputStream.class};
                this.readMethod = helperClass.getDeclaredMethod("read", argTypes);
            }
            catch (Exception ex) {
                throw ExceptionHandlerImpl.this.wrapper.badHelperReadMethod(ex, (Object)helperName);
            }
        }

        @Override
        public void write(OutputStream os, Exception ex) {
            try {
                Object[] args2 = new Object[]{os, ex};
                this.writeMethod.invoke(null, args2);
            }
            catch (Exception exc) {
                throw ExceptionHandlerImpl.this.wrapper.badHelperWriteMethod(exc, (Object)this.writeMethod.getDeclaringClass().getName());
            }
        }

        @Override
        public Exception read(InputStream is) {
            try {
                Object[] args2 = new Object[]{is};
                return (Exception)this.readMethod.invoke(null, args2);
            }
            catch (Exception ex) {
                throw ExceptionHandlerImpl.this.wrapper.badHelperReadMethod(ex, (Object)this.readMethod.getDeclaringClass().getName());
            }
        }
    }

    public abstract class ExceptionRWBase
    implements ExceptionRW {
        private Class<?> cls;
        private String id;

        public ExceptionRWBase(Class<?> cls) {
            this.cls = cls;
        }

        @Override
        public Class<?> getExceptionClass() {
            return this.cls;
        }

        @Override
        public String getId() {
            return this.id;
        }

        void setId(String id) {
            this.id = id;
        }
    }

    public static interface ExceptionRW {
        public Class<?> getExceptionClass();

        public String getId();

        public void write(OutputStream var1, Exception var2);

        public Exception read(InputStream var1);
    }
}

