/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import scala.runtime.ClassValueCompat;
import scala.runtime.ModuleSerializationProxy;

public final class ModuleSerializationProxy$
implements Serializable {
    public static final ModuleSerializationProxy$ MODULE$ = new ModuleSerializationProxy$();
    private static final ClassValueCompat<Object> scala$runtime$ModuleSerializationProxy$$instances = new ClassValueCompat<Object>(){

        public Object getModule(Class<?> cls) {
            return AccessController.doPrivileged(() -> cls.getField("MODULE$").get(null));
        }

        public Object computeValue(Class<?> cls) {
            try {
                return this.getModule(cls);
            }
            catch (PrivilegedActionException e) {
                return ModuleSerializationProxy$.MODULE$.scala$runtime$ModuleSerializationProxy$$rethrowRuntime(e.getCause());
            }
        }
    };

    public ClassValueCompat<Object> scala$runtime$ModuleSerializationProxy$$instances() {
        return scala$runtime$ModuleSerializationProxy$$instances;
    }

    public Object scala$runtime$ModuleSerializationProxy$$rethrowRuntime(Throwable e) {
        if (e instanceof RuntimeException) {
            throw (RuntimeException)e;
        }
        throw new RuntimeException(e);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ModuleSerializationProxy$.class);
    }

    private ModuleSerializationProxy$() {
    }
}

