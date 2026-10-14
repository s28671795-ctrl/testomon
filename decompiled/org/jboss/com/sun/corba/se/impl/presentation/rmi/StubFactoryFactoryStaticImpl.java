/*
 * Decompiled with CFR 0.152.
 */
package org.jboss.com.sun.corba.se.impl.presentation.rmi;

import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.Util;
import org.jboss.com.sun.corba.se.impl.logging.ORBUtilSystemException;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryFactoryBase;
import org.jboss.com.sun.corba.se.impl.presentation.rmi.StubFactoryStaticImpl;
import org.jboss.com.sun.corba.se.impl.util.PackagePrefixChecker;
import org.jboss.com.sun.corba.se.impl.util.Utility;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.omg.CORBA.CompletionStatus;

public class StubFactoryFactoryStaticImpl
extends StubFactoryFactoryBase {
    private ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.presentation");

    @Override
    public PresentationManager.StubFactory createStubFactory(String className, boolean isIDLStub, String remoteCodeBase, Class<?> expectedClass, ClassLoader classLoader) {
        String stubName = null;
        stubName = isIDLStub ? Utility.idlStubName(className) : Utility.stubNameForCompiler(className);
        ClassLoader expectedTypeClassLoader = expectedClass == null ? classLoader : expectedClass.getClassLoader();
        String firstStubName = stubName;
        String secondStubName = stubName;
        if (PackagePrefixChecker.hasOffendingPrefix(stubName)) {
            firstStubName = PackagePrefixChecker.packagePrefix() + stubName;
        } else {
            secondStubName = PackagePrefixChecker.packagePrefix() + stubName;
        }
        Class<?> clz = null;
        try {
            clz = Util.loadClass(firstStubName, remoteCodeBase, expectedTypeClassLoader);
        }
        catch (ClassNotFoundException e1) {
            this.wrapper.classNotFound1(CompletionStatus.COMPLETED_MAYBE, e1, firstStubName);
            try {
                clz = Util.loadClass(secondStubName, remoteCodeBase, expectedTypeClassLoader);
            }
            catch (ClassNotFoundException e2) {
                throw this.wrapper.classNotFound2(CompletionStatus.COMPLETED_MAYBE, e2, secondStubName);
            }
        }
        if (clz == null || expectedClass != null && !expectedClass.isAssignableFrom(clz)) {
            try {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                if (cl == null) {
                    cl = ClassLoader.getSystemClassLoader();
                }
                clz = cl.loadClass(className);
            }
            catch (Exception exc) {
                IllegalStateException ise = new IllegalStateException("Could not load class " + stubName);
                ise.initCause(exc);
                throw ise;
            }
        }
        return new StubFactoryStaticImpl(clz);
    }

    @Override
    public Tie getTie(Class<?> cls) {
        Class<?> tieClass = null;
        String className = Utility.tieName(cls.getName());
        try {
            tieClass = Utility.loadClassForClass(className, Util.getCodebase(cls), null, cls, cls.getClassLoader());
            return (Tie)tieClass.newInstance();
        }
        catch (Exception err) {
            try {
                tieClass = Utility.loadClassForClass(PackagePrefixChecker.packagePrefix() + className, Util.getCodebase(cls), null, cls, cls.getClassLoader());
                return (Tie)tieClass.newInstance();
            }
            catch (Exception err2) {
                return null;
            }
        }
    }

    @Override
    public boolean createsDynamicStubs() {
        return false;
    }
}

