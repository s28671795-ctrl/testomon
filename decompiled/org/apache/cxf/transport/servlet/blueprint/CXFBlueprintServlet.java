/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  org.osgi.service.blueprint.container.BlueprintContainer
 */
package org.apache.cxf.transport.servlet.blueprint;

import javax.servlet.ServletConfig;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFNonSpringServlet;
import org.osgi.service.blueprint.container.BlueprintContainer;

public class CXFBlueprintServlet
extends CXFNonSpringServlet {
    private static final String CONTAINER_ATTRIBUTE = "org.apache.aries.blueprint.container";
    private static final long serialVersionUID = -5922443981969455305L;
    private boolean busCreated;

    @Override
    protected void loadBus(ServletConfig servletConfig) {
        BlueprintContainer container = (BlueprintContainer)servletConfig.getServletContext().getAttribute(CONTAINER_ATTRIBUTE);
        if (container != null) {
            Object busComponent = container.getComponentInstance("cxf");
            this.setBus((Bus)busComponent);
        } else {
            this.busCreated = true;
            this.setBus(BusFactory.newInstance().createBus());
        }
    }

    @Override
    public void destroyBus() {
        if (this.busCreated) {
            this.getBus().shutdown(true);
            this.setBus(null);
        }
    }
}

