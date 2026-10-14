/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.addressing;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.apache.cxf.common.jaxb.JAXBContextCache;
import org.apache.cxf.ws.addressing.ContextUtils;

public final class ContextJAXBUtils {
    private static JAXBContext jaxbContext;
    private static Set<Class<?>> jaxbContextClasses;

    private ContextJAXBUtils() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static JAXBContext getJAXBContext() throws JAXBException {
        Class<ContextJAXBUtils> clazz = ContextJAXBUtils.class;
        synchronized (ContextJAXBUtils.class) {
            if (jaxbContext == null || jaxbContextClasses == null) {
                HashSet tmp = new HashSet();
                JAXBContextCache.addPackage(tmp, ContextUtils.WSA_OBJECT_FACTORY.getClass().getPackage().getName(), ContextUtils.WSA_OBJECT_FACTORY.getClass().getClassLoader());
                JAXBContextCache.scanPackages(tmp);
                JAXBContextCache.CachedContextAndSchemas ccs = JAXBContextCache.getCachedContextAndSchemas(tmp, null, null, null, false);
                jaxbContextClasses = ccs.getClasses();
                jaxbContext = ccs.getContext();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            return jaxbContext;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setJAXBContext(JAXBContext ctx) {
        Class<ContextJAXBUtils> clazz = ContextJAXBUtils.class;
        synchronized (ContextJAXBUtils.class) {
            jaxbContext = ctx;
            jaxbContextClasses = new HashSet();
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }
}

