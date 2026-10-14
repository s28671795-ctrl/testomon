/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.omg.stub.java.rmi._Remote_Stub
 */
package org.jboss.com.sun.corba.se.impl.util;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.rmi.CORBA.Tie;
import javax.rmi.CORBA.Util;
import javax.rmi.PortableRemoteObject;
import org.jboss.com.sun.corba.se.impl.logging.OMGSystemException;
import org.jboss.com.sun.corba.se.impl.logging.UtilSystemException;
import org.jboss.com.sun.corba.se.impl.util.IdentityHashtable;
import org.jboss.com.sun.corba.se.impl.util.PackagePrefixChecker;
import org.jboss.com.sun.corba.se.impl.util.RepositoryId;
import org.jboss.com.sun.corba.se.impl.util.StubEntry;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.PresentationManager;
import org.jboss.com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import org.omg.CORBA.BAD_INV_ORDER;
import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.BoxedValueHelper;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.ValueFactory;
import org.omg.CORBA_2_3.ORB;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.PortableServer.Servant;
import org.omg.stub.java.rmi._Remote_Stub;

public final class Utility {
    public static final String STUB_PREFIX = "_";
    public static final String RMI_STUB_SUFFIX = "_Stub";
    public static final String DYNAMIC_STUB_SUFFIX = "_DynamicStub";
    public static final String IDL_STUB_SUFFIX = "Stub";
    public static final String TIE_SUFIX = "_Tie";
    private static IdentityHashtable tieCache = new IdentityHashtable();
    private static IdentityHashtable tieToStubCache = new IdentityHashtable();
    private static IdentityHashtable stubToTieCache = new IdentityHashtable();
    private static java.lang.Object CACHE_MISS = new java.lang.Object();
    private static UtilSystemException wrapper = UtilSystemException.get("util");
    private static OMGSystemException omgWrapper = OMGSystemException.get("util");

    public static java.lang.Object autoConnect(java.lang.Object obj, org.omg.CORBA.ORB orb, boolean convertToStub) {
        if (obj == null) {
            return obj;
        }
        if (StubAdapter.isStub(obj)) {
            try {
                StubAdapter.getDelegate(obj);
            }
            catch (BAD_OPERATION okay) {
                try {
                    StubAdapter.connect(obj, orb);
                }
                catch (RemoteException e) {
                    throw wrapper.objectNotConnected(e, (java.lang.Object)obj.getClass().getName());
                }
            }
            return obj;
        }
        if (obj instanceof Remote) {
            Remote remoteObj = (Remote)obj;
            Tie theTie = Util.getTie(remoteObj);
            if (theTie != null) {
                try {
                    theTie.orb();
                }
                catch (SystemException okay) {
                    theTie.orb(orb);
                }
                if (convertToStub) {
                    Remote result = Utility.loadStub(theTie, null, null, true);
                    if (result != null) {
                        return result;
                    }
                    throw wrapper.couldNotLoadStub(obj.getClass().getName());
                }
                return StubAdapter.activateTie(theTie);
            }
            throw wrapper.objectNotExported(obj.getClass().getName());
        }
        return obj;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Tie loadTie(Remote obj) {
        Tie result = null;
        Class<?> objClass = obj.getClass();
        IdentityHashtable identityHashtable = tieCache;
        synchronized (identityHashtable) {
            java.lang.Object it = tieCache.get(obj);
            if (it == null) {
                try {
                    result = Utility.loadTie(objClass);
                    while (result == null && (objClass = objClass.getSuperclass()) != null && objClass != PortableRemoteObject.class && objClass != java.lang.Object.class) {
                        result = Utility.loadTie(objClass);
                    }
                }
                catch (Exception ex) {
                    wrapper.loadTieFailed(ex, (java.lang.Object)objClass.getName());
                }
                if (result == null) {
                    tieCache.put(obj, CACHE_MISS);
                } else {
                    tieCache.put(obj, result);
                }
            } else if (it != CACHE_MISS) {
                try {
                    result = (Tie)it.getClass().newInstance();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        return result;
    }

    private static Tie loadTie(Class<?> theClass) {
        return org.jboss.com.sun.corba.se.spi.orb.ORB.getStubFactoryFactory().getTie(theClass);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void clearCaches() {
        IdentityHashtable identityHashtable = tieToStubCache;
        synchronized (identityHashtable) {
            tieToStubCache.clear();
        }
        identityHashtable = tieCache;
        synchronized (identityHashtable) {
            tieCache.clear();
        }
        identityHashtable = stubToTieCache;
        synchronized (identityHashtable) {
            stubToTieCache.clear();
        }
    }

    static Class<?> loadClassOfType(String className, String remoteCodebase, ClassLoader loader, Class<?> expectedType, ClassLoader expectedTypeClassLoader) throws ClassNotFoundException {
        Class<?> loadedClass;
        block10: {
            loadedClass = null;
            try {
                try {
                    loadedClass = !PackagePrefixChecker.hasOffendingPrefix(PackagePrefixChecker.withoutPackagePrefix(className)) ? Util.loadClass(PackagePrefixChecker.withoutPackagePrefix(className), remoteCodebase, loader) : Util.loadClass(className, remoteCodebase, loader);
                }
                catch (ClassNotFoundException cnfe) {
                    loadedClass = Util.loadClass(className, remoteCodebase, loader);
                }
                if (expectedType == null) {
                    return loadedClass;
                }
            }
            catch (ClassNotFoundException cnfe) {
                if (expectedType != null) break block10;
                throw cnfe;
            }
        }
        if (loadedClass == null || !expectedType.isAssignableFrom(loadedClass)) {
            if (expectedType.getClassLoader() != expectedTypeClassLoader) {
                throw new IllegalArgumentException("expectedTypeClassLoader not class loader of expected Type.");
            }
            if (expectedTypeClassLoader != null) {
                loadedClass = expectedTypeClassLoader.loadClass(className);
            } else {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                if (cl == null) {
                    cl = ClassLoader.getSystemClassLoader();
                }
                loadedClass = cl.loadClass(className);
            }
        }
        return loadedClass;
    }

    public static Class<?> loadClassForClass(String className, String remoteCodebase, ClassLoader loader, Class<?> relatedType, ClassLoader relatedTypeClassLoader) throws ClassNotFoundException {
        Class<?> loadedClass;
        block6: {
            if (relatedType == null) {
                return Util.loadClass(className, remoteCodebase, loader);
            }
            loadedClass = null;
            try {
                loadedClass = Util.loadClass(className, remoteCodebase, loader);
            }
            catch (ClassNotFoundException cnfe) {
                if (relatedType.getClassLoader() != null) break block6;
                throw cnfe;
            }
        }
        if (loadedClass == null || loadedClass.getClassLoader() != null && loadedClass.getClassLoader().loadClass(relatedType.getName()) != relatedType) {
            if (relatedType.getClassLoader() != relatedTypeClassLoader) {
                throw new IllegalArgumentException("relatedTypeClassLoader not class loader of relatedType.");
            }
            if (relatedTypeClassLoader != null) {
                loadedClass = relatedTypeClassLoader.loadClass(className);
            }
        }
        return loadedClass;
    }

    public static BoxedValueHelper getHelper(Class<?> clazz, String codebase, String repId) {
        String className = null;
        if (clazz != null) {
            className = clazz.getName();
            if (codebase == null) {
                codebase = Util.getCodebase(clazz);
            }
        } else {
            if (repId != null) {
                className = RepositoryId.cache.getId(repId).getClassName();
            }
            if (className == null) {
                throw wrapper.unableLocateValueHelper(CompletionStatus.COMPLETED_MAYBE);
            }
        }
        try {
            ClassLoader clazzLoader = clazz == null ? null : clazz.getClassLoader();
            Class<?> helperClass = Utility.loadClassForClass(className + "Helper", codebase, clazzLoader, clazz, clazzLoader);
            return (BoxedValueHelper)helperClass.newInstance();
        }
        catch (ClassNotFoundException cnfe) {
            throw wrapper.unableLocateValueHelper(CompletionStatus.COMPLETED_MAYBE, cnfe);
        }
        catch (IllegalAccessException iae) {
            throw wrapper.unableLocateValueHelper(CompletionStatus.COMPLETED_MAYBE, iae);
        }
        catch (InstantiationException ie) {
            throw wrapper.unableLocateValueHelper(CompletionStatus.COMPLETED_MAYBE, ie);
        }
        catch (ClassCastException cce) {
            throw wrapper.unableLocateValueHelper(CompletionStatus.COMPLETED_MAYBE, cce);
        }
    }

    public static ValueFactory getFactory(Class<?> clazz, String codebase, org.omg.CORBA.ORB orb, String repId) {
        ValueFactory factory2 = null;
        if (orb != null && repId != null) {
            try {
                factory2 = ((ORB)orb).lookup_value_factory(repId);
            }
            catch (BAD_PARAM bAD_PARAM) {
                // empty catch block
            }
        }
        String className = null;
        if (clazz != null) {
            className = clazz.getName();
            if (codebase == null) {
                codebase = Util.getCodebase(clazz);
            }
        } else {
            if (repId != null) {
                className = RepositoryId.cache.getId(repId).getClassName();
            }
            if (className == null) {
                throw omgWrapper.unableLocateValueFactory(CompletionStatus.COMPLETED_MAYBE);
            }
        }
        if (factory2 != null && (!factory2.getClass().getName().equals(className + "DefaultFactory") || clazz == null && codebase == null)) {
            return factory2;
        }
        try {
            ClassLoader clazzLoader = clazz == null ? null : clazz.getClassLoader();
            Class<?> factoryClass = Utility.loadClassForClass(className + "DefaultFactory", codebase, clazzLoader, clazz, clazzLoader);
            return (ValueFactory)factoryClass.newInstance();
        }
        catch (ClassNotFoundException cnfe) {
            throw omgWrapper.unableLocateValueFactory(CompletionStatus.COMPLETED_MAYBE, cnfe);
        }
        catch (IllegalAccessException iae) {
            throw omgWrapper.unableLocateValueFactory(CompletionStatus.COMPLETED_MAYBE, iae);
        }
        catch (InstantiationException ie) {
            throw omgWrapper.unableLocateValueFactory(CompletionStatus.COMPLETED_MAYBE, ie);
        }
        catch (ClassCastException cce) {
            throw omgWrapper.unableLocateValueFactory(CompletionStatus.COMPLETED_MAYBE, cce);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Remote loadStub(Tie tie, PresentationManager.StubFactory stubFactory, String remoteCodebase, boolean onlyMostDerived) {
        StubEntry entry = null;
        IdentityHashtable identityHashtable = tieToStubCache;
        synchronized (identityHashtable) {
            java.lang.Object cached = tieToStubCache.get(tie);
            if (cached == null) {
                entry = Utility.loadStubAndUpdateCache(tie, stubFactory, remoteCodebase, onlyMostDerived);
            } else if (cached != CACHE_MISS) {
                entry = (StubEntry)cached;
                if (!entry.mostDerived && onlyMostDerived) {
                    entry = Utility.loadStubAndUpdateCache(tie, null, remoteCodebase, true);
                } else if (stubFactory != null && !StubAdapter.getTypeIds(entry.stub)[0].equals(stubFactory.getTypeIds()[0])) {
                    entry = Utility.loadStubAndUpdateCache(tie, null, remoteCodebase, true);
                    if (entry == null) {
                        entry = Utility.loadStubAndUpdateCache(tie, stubFactory, remoteCodebase, onlyMostDerived);
                    }
                } else {
                    try {
                        StubAdapter.getDelegate(entry.stub);
                    }
                    catch (Exception e2) {
                        try {
                            Delegate del = StubAdapter.getDelegate(tie);
                            StubAdapter.setDelegate(entry.stub, del);
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                    }
                }
            }
        }
        if (entry != null) {
            return (Remote)((java.lang.Object)entry.stub);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    private static StubEntry loadStubAndUpdateCache(Tie tie, PresentationManager.StubFactory stubFactory, String remoteCodebase, boolean onlyMostDerived) {
        stub = null;
        entry = null;
        tieIsStub = StubAdapter.isStub(tie);
        if (stubFactory != null) {
            try {
                stub = stubFactory.makeStub();
            }
            catch (Throwable e) {
                Utility.wrapper.stubFactoryCouldNotMakeStub(e);
                if (!(e instanceof ThreadDeath)) ** GOTO lbl37
                throw (ThreadDeath)e;
            }
        } else {
            ids = null;
            ids = tieIsStub != false ? StubAdapter.getTypeIds(tie) : ((Servant)tie)._all_interfaces(null, null);
            if (remoteCodebase == null) {
                remoteCodebase = Util.getCodebase(tie.getClass());
            }
            if (ids.length == 0) {
                stub = new _Remote_Stub();
            } else {
                for (i = 0; i < ids.length; ++i) {
                    if (ids[i].length() == 0) {
                        stub = new _Remote_Stub();
                        break;
                    }
                    try {
                        stubFactoryFactory = org.jboss.com.sun.corba.se.spi.orb.ORB.getStubFactoryFactory();
                        rid = RepositoryId.cache.getId(ids[i]);
                        className = rid.getClassName();
                        isIDLInterface = rid.isIDLType();
                        stubFactory = stubFactoryFactory.createStubFactory(className, isIDLInterface, remoteCodebase, null, tie.getTarget().getClass().getClassLoader());
                        stub = stubFactory.makeStub();
                        break;
                    }
                    catch (Exception e) {
                        Utility.wrapper.errorInMakeStubFromRepositoryId(e);
                        if (onlyMostDerived) break;
                        continue;
                    }
                }
            }
        }
lbl37:
        // 7 sources

        if (stub == null) {
            Utility.tieToStubCache.put(tie, Utility.CACHE_MISS);
        } else {
            if (tieIsStub) {
                try {
                    del = StubAdapter.getDelegate(tie);
                    StubAdapter.setDelegate(stub, del);
                }
                catch (Exception e1) {
                    var8_13 = Utility.stubToTieCache;
                    synchronized (var8_13) {
                        Utility.stubToTieCache.put(stub, tie);
                    }
                }
            } else {
                try {
                    delegate = StubAdapter.getDelegate(tie);
                    StubAdapter.setDelegate(stub, delegate);
                }
                catch (BAD_INV_ORDER bad) {
                    var8_14 = Utility.stubToTieCache;
                    synchronized (var8_14) {
                        Utility.stubToTieCache.put(stub, tie);
                    }
                }
                catch (Exception e) {
                    throw Utility.wrapper.noPoa(e);
                }
            }
            entry = new StubEntry(stub, onlyMostDerived);
            Utility.tieToStubCache.put(tie, entry);
        }
        return entry;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Tie getAndForgetTie(Object stub) {
        IdentityHashtable identityHashtable = stubToTieCache;
        synchronized (identityHashtable) {
            return (Tie)stubToTieCache.remove(stub);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void purgeStubForTie(Tie tie) {
        StubEntry entry;
        IdentityHashtable identityHashtable = tieToStubCache;
        synchronized (identityHashtable) {
            entry = (StubEntry)tieToStubCache.remove(tie);
        }
        if (entry != null) {
            identityHashtable = stubToTieCache;
            synchronized (identityHashtable) {
                stubToTieCache.remove(entry.stub);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void purgeTieAndServant(Tie tie) {
        IdentityHashtable identityHashtable = tieCache;
        synchronized (identityHashtable) {
            Remote target = tie.getTarget();
            if (target != null) {
                tieCache.remove(target);
            }
        }
    }

    public static String stubNameFromRepID(String repID) {
        RepositoryId id = RepositoryId.cache.getId(repID);
        String className = id.getClassName();
        className = id.isIDLType() ? Utility.idlStubName(className) : Utility.stubName(className);
        return className;
    }

    public static java.lang.Object loadStub(Object narrowFrom, final Class<?> narrowTo) {
        Object result = null;
        try {
            String codebase = null;
            try {
                Delegate delegate = StubAdapter.getDelegate(narrowFrom);
                codebase = ((org.omg.CORBA_2_3.portable.Delegate)delegate).get_codebase(narrowFrom);
            }
            catch (ClassCastException e) {
                throw wrapper.classCastExceptionInLoadStub(e);
            }
            ClassLoader classLoader = AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return narrowTo.getClassLoader();
                }
            });
            PresentationManager.StubFactoryFactory sff = org.jboss.com.sun.corba.se.spi.orb.ORB.getStubFactoryFactory();
            PresentationManager.StubFactory sf = sff.createStubFactory(narrowTo.getName(), false, codebase, narrowTo, classLoader);
            result = sf.makeStub();
            StubAdapter.setDelegate(result, StubAdapter.getDelegate(narrowFrom));
        }
        catch (Exception err) {
            throw wrapper.exceptionInLoadStub(err);
        }
        return result;
    }

    public static Class<?> loadStubClass(String repID, String remoteCodebase, Class<?> expectedType) throws ClassNotFoundException {
        if (repID.length() == 0) {
            throw new ClassNotFoundException();
        }
        String className = Utility.stubNameFromRepID(repID);
        ClassLoader expectedTypeClassLoader = expectedType == null ? null : expectedType.getClassLoader();
        try {
            return Utility.loadClassOfType(className, remoteCodebase, expectedTypeClassLoader, expectedType, expectedTypeClassLoader);
        }
        catch (ClassNotFoundException e) {
            return Utility.loadClassOfType(PackagePrefixChecker.packagePrefix() + className, remoteCodebase, expectedTypeClassLoader, expectedType, expectedTypeClassLoader);
        }
    }

    public static String stubName(String className) {
        return Utility.stubName(className, false);
    }

    public static String dynamicStubName(String className) {
        return Utility.stubName(className, true);
    }

    private static String stubName(String className, boolean isDynamic) {
        String name = Utility.stubNameForCompiler(className, isDynamic);
        if (PackagePrefixChecker.hasOffendingPrefix(name)) {
            name = PackagePrefixChecker.packagePrefix() + name;
        }
        return name;
    }

    public static String stubNameForCompiler(String className) {
        return Utility.stubNameForCompiler(className, false);
    }

    private static String stubNameForCompiler(String className, boolean isDynamic) {
        String suffix;
        int index = className.indexOf(36);
        if (index < 0) {
            index = className.lastIndexOf(46);
        }
        String string = suffix = isDynamic ? DYNAMIC_STUB_SUFFIX : RMI_STUB_SUFFIX;
        if (index > 0) {
            return className.substring(0, index + 1) + STUB_PREFIX + className.substring(index + 1) + suffix;
        }
        return STUB_PREFIX + className + suffix;
    }

    public static String tieName(String className) {
        return PackagePrefixChecker.hasOffendingPrefix(Utility.tieNameForCompiler(className)) ? PackagePrefixChecker.packagePrefix() + Utility.tieNameForCompiler(className) : Utility.tieNameForCompiler(className);
    }

    public static String tieNameForCompiler(String className) {
        int index = className.indexOf(36);
        if (index < 0) {
            index = className.lastIndexOf(46);
        }
        if (index > 0) {
            return className.substring(0, index + 1) + STUB_PREFIX + className.substring(index + 1) + TIE_SUFIX;
        }
        return STUB_PREFIX + className + TIE_SUFIX;
    }

    public static void throwNotSerializableForCorba(String className) {
        throw omgWrapper.notSerializable(CompletionStatus.COMPLETED_MAYBE, (java.lang.Object)className);
    }

    public static String idlStubName(String className) {
        String result = null;
        int index = className.lastIndexOf(46);
        result = index > 0 ? className.substring(0, index + 1) + STUB_PREFIX + className.substring(index + 1) + IDL_STUB_SUFFIX : STUB_PREFIX + className + IDL_STUB_SUFFIX;
        return result;
    }

    public static void printStackTrace() {
        Throwable thr = new Throwable("Printing stack trace:");
        thr.fillInStackTrace();
        thr.printStackTrace();
    }

    public static java.lang.Object readObjectAndNarrow(org.omg.CORBA.portable.InputStream in, Class<?> narrowTo) throws ClassCastException {
        Object result = in.read_Object();
        if (result != null) {
            return PortableRemoteObject.narrow(result, narrowTo);
        }
        return null;
    }

    public static java.lang.Object readAbstractAndNarrow(InputStream in, Class<?> narrowTo) throws ClassCastException {
        java.lang.Object result = in.read_abstract_interface();
        if (result != null) {
            return PortableRemoteObject.narrow(result, narrowTo);
        }
        return null;
    }

    static int hexOf(char x) {
        int val = x - 48;
        if (val >= 0 && val <= 9) {
            return val;
        }
        val = x - 97 + 10;
        if (val >= 10 && val <= 15) {
            return val;
        }
        val = x - 65 + 10;
        if (val >= 10 && val <= 15) {
            return val;
        }
        throw wrapper.badHexDigit();
    }
}

