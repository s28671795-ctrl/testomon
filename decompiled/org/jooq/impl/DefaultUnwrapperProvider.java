/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Wrapper;
import org.jooq.Unwrapper;
import org.jooq.UnwrapperProvider;
import org.jooq.tools.reflect.Reflect;
import org.jooq.tools.reflect.ReflectException;

final class DefaultUnwrapperProvider
implements UnwrapperProvider {
    static final Unwrapper INSTANCE = new DefaultUnwrapper();

    DefaultUnwrapperProvider() {
    }

    @Override
    public Unwrapper provide() {
        return INSTANCE;
    }

    static final class DefaultUnwrapper
    implements Unwrapper {
        private static int maxUnwrappedConnections = 256;
        private static int maxUnwrappedStatements = 256;

        DefaultUnwrapper() {
        }

        static final boolean isWrapperFor(Wrapper w, Class<?> iface) {
            try {
                return w.isWrapperFor(iface);
            }
            catch (SQLException ignore) {
                return false;
            }
        }

        @Override
        public <T> T unwrap(Wrapper wrapper, Class<T> iface) {
            if (wrapper instanceof Connection) {
                Connection c = (Connection)wrapper;
                return this.unwrap(c, iface);
            }
            if (wrapper instanceof Statement) {
                Statement s2 = (Statement)wrapper;
                return this.unwrap(s2, iface);
            }
            throw new IllegalArgumentException("Cannot unwrap: " + String.valueOf(wrapper));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private <T> T unwrap(Connection wrapper, Class<T> iface) {
            Connection result = wrapper;
            int i = 0;
            while (i < maxUnwrappedConnections) {
                block13: {
                    Connection r2;
                    block14: {
                        block12: {
                            try {
                                try {
                                    result.getClass().getMethod("createARRAY", String.class, Object.class);
                                    return (T)result;
                                }
                                catch (NoSuchMethodException noSuchMethodException) {
                                    r2 = (Connection)result.unwrap(iface);
                                    if (result != r2 && r2 != null) {
                                        result = r2;
                                        return (T)result;
                                    }
                                }
                            }
                            catch (SQLException r2) {
                                // empty catch block
                            }
                            try {
                                r2 = (Connection)Reflect.on(result).call("getTargetConnection").get();
                                if (result == r2 || r2 == null) break block12;
                                result = r2;
                                break block13;
                            }
                            catch (ReflectException r3) {
                                // empty catch block
                            }
                        }
                        try {
                            r2 = (Connection)Reflect.on(result).call("getDelegate").get();
                            if (result == r2 || r2 == null) break block14;
                            result = r2;
                            break block13;
                        }
                        catch (ReflectException r4) {
                            // empty catch block
                        }
                    }
                    try {
                        r2 = (Connection)Reflect.on(result).call("getUnderlyingConnection").get();
                        if (result == r2) return (T)result;
                        if (r2 == null) return (T)result;
                        result = r2;
                    }
                    catch (ReflectException reflectException) {
                        return (T)result;
                    }
                }
                ++i;
            }
            return (T)result;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private <T> T unwrap(Statement wrapper, Class<T> iface) {
            Statement result = wrapper;
            int i = 0;
            while (i < maxUnwrappedStatements) {
                block11: {
                    PreparedStatement r2;
                    block10: {
                        try {
                            try {
                                result.getClass().getMethod("setBinaryDouble", Integer.TYPE, Double.TYPE);
                                return (T)result;
                            }
                            catch (NoSuchMethodException noSuchMethodException) {
                                r2 = (PreparedStatement)result.unwrap(iface);
                                if (result != r2 && r2 != null) {
                                    result = r2;
                                    return (T)result;
                                }
                            }
                        }
                        catch (SQLException r2) {
                            // empty catch block
                        }
                        try {
                            r2 = (PreparedStatement)Reflect.on(result).call("getDelegate").get();
                            if (result == r2 || r2 == null) break block10;
                            result = r2;
                            break block11;
                        }
                        catch (ReflectException r3) {
                            // empty catch block
                        }
                    }
                    try {
                        r2 = (PreparedStatement)Reflect.on(result).call("getUnderlyingStatement").get();
                        if (result == r2) return (T)result;
                        if (r2 == null) return (T)result;
                        result = r2;
                    }
                    catch (ReflectException reflectException) {
                        return (T)result;
                    }
                }
                ++i;
            }
            return (T)result;
        }
    }
}

