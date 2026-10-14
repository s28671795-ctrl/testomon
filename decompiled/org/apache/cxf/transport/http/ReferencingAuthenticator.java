/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import org.apache.cxf.common.util.ReflectionUtil;

public class ReferencingAuthenticator
extends Authenticator {
    final Reference<Authenticator> auth;
    final Authenticator wrapped;

    public ReferencingAuthenticator(Authenticator cxfauth, Authenticator wrapped) {
        this.auth = new WeakReference<Authenticator>(cxfauth);
        this.wrapped = wrapped;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        Authenticator cxfauth = this.auth.get();
        if (cxfauth == null) {
            this.remove();
        }
        PasswordAuthentication pauth = null;
        if (this.wrapped != null) {
            try {
                pauth = this.tryWith(this.wrapped);
                if (pauth != null) {
                    return pauth;
                }
            }
            catch (Exception e) {
                pauth = null;
            }
        }
        if (cxfauth != null) {
            try {
                pauth = this.tryWith(cxfauth);
            }
            catch (Exception e1) {
                pauth = null;
            }
        }
        return pauth;
    }

    public final void check() {
        Authenticator cxfauth = this.auth.get();
        if (cxfauth == null) {
            this.remove();
        }
        if (this.wrapped != null && this.wrapped.getClass().getName().equals(ReferencingAuthenticator.class.getName())) {
            try {
                Method m4 = this.wrapped.getClass().getMethod("check", new Class[0]);
                m4.setAccessible(true);
                m4.invoke((Object)this.wrapped, new Object[0]);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    private void remove() {
        try {
            for (Field f : Authenticator.class.getDeclaredFields()) {
                if (!f.getType().equals(Authenticator.class)) continue;
                try {
                    f.setAccessible(true);
                    Authenticator o = (Authenticator)f.get(null);
                    if (o == this) {
                        Authenticator.setDefault(this.wrapped);
                        continue;
                    }
                    this.removeFromChain(o);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private void removeFromChain(Authenticator a) {
        try {
            if (a.getClass().getName().equals(ReferencingAuthenticator.class.getName())) {
                Field f2 = a.getClass().getDeclaredField("wrapped");
                f2.setAccessible(true);
                Authenticator a2 = (Authenticator)f2.get(a);
                if (a2 == this) {
                    f2.set(a, this.wrapped);
                } else {
                    this.removeFromChain(a2);
                }
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    PasswordAuthentication tryWith(Authenticator a) throws Exception {
        if (a == null) {
            return null;
        }
        for (Field f : ReflectionUtil.getDeclaredFields(Authenticator.class)) {
            if (Modifier.isStatic(f.getModifiers())) continue;
            f.setAccessible(true);
            Object o = f.get(this);
            f.set(a, o);
        }
        Method m4 = Authenticator.class.getDeclaredMethod("getPasswordAuthentication", new Class[0]);
        m4.setAccessible(true);
        return (PasswordAuthentication)m4.invoke((Object)a, new Object[0]);
    }
}

