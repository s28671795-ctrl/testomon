/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletException
 */
package org.apache.cxf.transport.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

public interface ServletConfigAware {
    default public void onServletConfigAvailable(ServletConfig config) throws ServletException {
    }
}

