/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.Duration;
import org.apache.cxf.common.logging.LogUtils;

public final class DatatypeFactory {
    public static final Duration PT0S;
    private static final Logger LOG;

    private DatatypeFactory() {
    }

    public static Duration createDuration(String s2) {
        try {
            return javax.xml.datatype.DatatypeFactory.newInstance().newDuration(s2);
        }
        catch (DatatypeConfigurationException ex) {
            LogUtils.log(LOG, Level.SEVERE, "DATATYPE_FACTORY_INSTANTIATION_EXC", ex);
            return null;
        }
    }

    static {
        LOG = LogUtils.getL7dLogger(DatatypeFactory.class);
        PT0S = DatatypeFactory.createDuration("PT0S");
    }
}

