/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.processor;

import com.sun.research.ws.wadl.Application;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.Marshaller;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ExtendedUriInfo;
import org.glassfish.jersey.server.model.ModelProcessor;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceModel;
import org.glassfish.jersey.server.model.RuntimeResource;
import org.glassfish.jersey.server.model.internal.ModelProcessorUtil;
import org.glassfish.jersey.server.wadl.WadlApplicationContext;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.glassfish.jersey.server.wadl.internal.WadlUtils;

@Priority(value=10000)
public class WadlModelProcessor
implements ModelProcessor {
    private final List<ModelProcessorUtil.Method> methodList = new ArrayList<ModelProcessorUtil.Method>();

    public WadlModelProcessor() {
        this.methodList.add(new ModelProcessorUtil.Method("OPTIONS", MediaType.WILDCARD_TYPE, MediaTypes.WADL_TYPE, OptionsHandler.class));
    }

    @Override
    public ResourceModel processResourceModel(ResourceModel resourceModel, Configuration configuration) {
        boolean disabled = PropertiesHelper.isProperty(configuration.getProperty("jersey.config.server.wadl.disableWadl"));
        if (disabled) {
            return resourceModel;
        }
        ResourceModel.Builder builder = ModelProcessorUtil.enhanceResourceModel(resourceModel, false, this.methodList, true);
        if (!configuration.getClasses().contains(WadlResource.class)) {
            Resource wadlResource = Resource.builder(WadlResource.class).build();
            builder.addResource(wadlResource);
        }
        return builder.build();
    }

    @Override
    public ResourceModel processSubResource(ResourceModel resourceModel, Configuration configuration) {
        boolean disabled = PropertiesHelper.isProperty(configuration.getProperty("jersey.config.server.wadl.disableWadl"));
        if (disabled) {
            return resourceModel;
        }
        return ModelProcessorUtil.enhanceResourceModel(resourceModel, true, this.methodList, true).build();
    }

    public static class OptionsHandler
    implements Inflector<ContainerRequestContext, Response> {
        private final String lastModified = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").format(new Date());
        @Inject
        private Provider<ExtendedUriInfo> extendedUriInfo;
        @Context
        private WadlApplicationContext wadlApplicationContext;

        @Override
        public Response apply(ContainerRequestContext containerRequestContext) {
            byte[] bytes;
            RuntimeResource resource = this.extendedUriInfo.get().getMatchedRuntimeResources().get(0);
            UriInfo uriInfo = containerRequestContext.getUriInfo();
            Application wadlApplication = this.wadlApplicationContext.getApplication(uriInfo, resource.getResources().get(0), WadlUtils.isDetailedWadlRequested(uriInfo));
            if (wadlApplication == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            try {
                Marshaller marshaller = this.wadlApplicationContext.getJAXBContext().createMarshaller();
                marshaller.setProperty("jaxb.formatted.output", true);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                marshaller.marshal((Object)wadlApplication, os);
                bytes = os.toByteArray();
                os.close();
            }
            catch (Exception e) {
                throw new ProcessingException("Could not marshal the wadl Application.", e);
            }
            return Response.ok().type(MediaTypes.WADL_TYPE).allow(ModelProcessorUtil.getAllowedMethods(resource)).header("Last-modified", this.lastModified).entity(bytes).build();
        }
    }
}

