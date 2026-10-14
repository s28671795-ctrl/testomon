/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl;

import com.sun.research.ws.wadl.Application;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.wadl.internal.ApplicationDescription;

public interface WadlApplicationContext {
    public ApplicationDescription getApplication(UriInfo var1, boolean var2);

    public Application getApplication(UriInfo var1, Resource var2, boolean var3);

    public JAXBContext getJAXBContext();

    public void setWadlGenerationEnabled(boolean var1);

    public boolean isWadlGenerationEnabled();
}

