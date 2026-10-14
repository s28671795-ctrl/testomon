/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jmx;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;
import org.h2.util.Utils;

public class DocumentedMBean
extends StandardMBean {
    private final String interfaceName;
    private Properties resources;

    public <T> DocumentedMBean(T t2, Class<T> clazz) throws NotCompliantMBeanException {
        super(t2, clazz);
        this.interfaceName = t2.getClass().getName() + "MBean";
    }

    private Properties getResources() {
        if (this.resources == null) {
            this.resources = new Properties();
            String string = "/org/h2/res/javadoc.properties";
            try {
                byte[] byArray = Utils.getResource(string);
                if (byArray != null) {
                    this.resources.load(new ByteArrayInputStream(byArray));
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return this.resources;
    }

    @Override
    protected String getDescription(MBeanInfo mBeanInfo) {
        String string = this.getResources().getProperty(this.interfaceName);
        return string == null ? super.getDescription(mBeanInfo) : string;
    }

    @Override
    protected String getDescription(MBeanOperationInfo mBeanOperationInfo) {
        String string = this.getResources().getProperty(this.interfaceName + "." + mBeanOperationInfo.getName());
        return string == null ? super.getDescription(mBeanOperationInfo) : string;
    }

    @Override
    protected String getDescription(MBeanAttributeInfo mBeanAttributeInfo) {
        String string = mBeanAttributeInfo.isIs() ? "is" : "get";
        String string2 = this.getResources().getProperty(this.interfaceName + "." + string + mBeanAttributeInfo.getName());
        return string2 == null ? super.getDescription(mBeanAttributeInfo) : string2;
    }

    @Override
    protected int getImpact(MBeanOperationInfo mBeanOperationInfo) {
        if (mBeanOperationInfo.getName().startsWith("list")) {
            return 0;
        }
        return 1;
    }
}

