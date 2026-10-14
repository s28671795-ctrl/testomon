/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal;

import com.sun.research.ws.wadl.Application;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.Marshaller;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.model.ExtendedResource;
import org.glassfish.jersey.server.wadl.WadlApplicationContext;
import org.glassfish.jersey.server.wadl.internal.ApplicationDescription;
import org.glassfish.jersey.server.wadl.internal.WadlUtils;

@Singleton
@Path(value="application.wadl")
@ExtendedResource
public final class WadlResource {
    public static final String HTTPDATEFORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
    private volatile URI lastBaseUri;
    private volatile boolean lastDetailedWadl;
    private byte[] wadlXmlRepresentation;
    private String lastModified = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(new Date());
    @Context
    private WadlApplicationContext wadlContext;

    private boolean isCached(UriInfo uriInfo, boolean detailedWadl) {
        return this.lastBaseUri != null && this.lastBaseUri.equals(uriInfo.getBaseUri()) && this.lastDetailedWadl == detailedWadl;
    }

    @Produces(value={"application/vnd.sun.wadl+xml", "application/xml"})
    @GET
    public synchronized Response getWadl(@Context UriInfo uriInfo) {
        try {
            if (!this.wadlContext.isWadlGenerationEnabled()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            boolean detailedWadl = WadlUtils.isDetailedWadlRequested(uriInfo);
            if (this.wadlXmlRepresentation == null || !this.isCached(uriInfo, detailedWadl)) {
                this.lastBaseUri = uriInfo.getBaseUri();
                this.lastDetailedWadl = detailedWadl;
                this.lastModified = new SimpleDateFormat(HTTPDATEFORMAT).format(new Date());
                ApplicationDescription applicationDescription = this.wadlContext.getApplication(uriInfo, detailedWadl);
                Application application = applicationDescription.getApplication();
                try {
                    Marshaller marshaller = this.wadlContext.getJAXBContext().createMarshaller();
                    marshaller.setProperty("jaxb.formatted.output", true);
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    marshaller.marshal((Object)application, os);
                    this.wadlXmlRepresentation = os.toByteArray();
                    os.close();
                }
                catch (Exception e) {
                    throw new ProcessingException("Could not marshal the wadl Application.", e);
                }
            }
            return Response.ok(new ByteArrayInputStream(this.wadlXmlRepresentation)).header("Last-modified", this.lastModified).build();
        }
        catch (Exception e) {
            throw new ProcessingException("Error generating /application.wadl.", e);
        }
    }

    @Produces(value={"application/xml"})
    @GET
    @Path(value="{path}")
    public synchronized Response getExternalGrammar(@Context UriInfo uriInfo, @PathParam(value="path") String path) {
        try {
            if (!this.wadlContext.isWadlGenerationEnabled()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            ApplicationDescription applicationDescription = this.wadlContext.getApplication(uriInfo, WadlUtils.isDetailedWadlRequested(uriInfo));
            ApplicationDescription.ExternalGrammar externalMetadata = applicationDescription.getExternalGrammar(path);
            if (externalMetadata == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok().type(externalMetadata.getType()).entity(externalMetadata.getContent()).build();
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_RESOURCE_EXTERNAL_GRAMMAR(), e);
        }
    }
}

