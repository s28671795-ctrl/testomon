/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal;

import com.sun.research.ws.wadl.Application;
import com.sun.research.ws.wadl.Doc;
import com.sun.research.ws.wadl.Method;
import com.sun.research.ws.wadl.Param;
import com.sun.research.ws.wadl.ParamStyle;
import com.sun.research.ws.wadl.Representation;
import com.sun.research.ws.wadl.Request;
import com.sun.research.ws.wadl.Resource;
import com.sun.research.ws.wadl.Resources;
import com.sun.research.ws.wadl.Response;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.FormParam;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.namespace.QName;
import org.glassfish.jersey.internal.Version;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.wadl.WadlGenerator;
import org.glassfish.jersey.server.wadl.internal.ApplicationDescription;
import org.glassfish.jersey.server.wadl.internal.WadlApplicationContextImpl;

public class WadlBuilder {
    private final WadlGenerator _wadlGenerator;
    private final UriInfo uriInfo;
    private final boolean detailedWadl;

    public WadlBuilder(WadlGenerator wadlGenerator, boolean detailedWadl, UriInfo uriInfo) {
        this.detailedWadl = detailedWadl;
        this._wadlGenerator = wadlGenerator;
        this.uriInfo = uriInfo;
    }

    public ApplicationDescription generate(List<org.glassfish.jersey.server.model.Resource> resources) {
        Application wadlApplication = this._wadlGenerator.createApplication();
        Resources wadlResources = this._wadlGenerator.createResources();
        for (org.glassfish.jersey.server.model.Resource r : resources) {
            Resource wadlResource = this.generateResource(r, r.getPath());
            if (wadlResource == null) continue;
            wadlResources.getResource().add(wadlResource);
        }
        wadlApplication.getResources().add(wadlResources);
        this.addVersion(wadlApplication);
        this.addHint(wadlApplication);
        WadlGenerator.ExternalGrammarDefinition external = this._wadlGenerator.createExternalGrammar();
        ApplicationDescription description = new ApplicationDescription(wadlApplication, external);
        this._wadlGenerator.attachTypes(description);
        return description;
    }

    public Application generate(ApplicationDescription description, org.glassfish.jersey.server.model.Resource resource) {
        try {
            Application wadlApplication = this._wadlGenerator.createApplication();
            Resources wadlResources = this._wadlGenerator.createResources();
            Resource wadlResource = this.generateResource(resource, null);
            if (wadlResource == null) {
                return null;
            }
            wadlResources.getResource().add(wadlResource);
            wadlApplication.getResources().add(wadlResources);
            this.addVersion(wadlApplication);
            this._wadlGenerator.attachTypes(description);
            return wadlApplication;
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_BUILDER_GENERATION_RESOURCE(resource), e);
        }
    }

    private void addVersion(Application wadlApplication) {
        Doc d = new Doc();
        d.getOtherAttributes().put(new QName("http://jersey.java.net/", "generatedBy", "jersey"), Version.getBuildId());
        wadlApplication.getDoc().add(d);
    }

    private void addHint(Application wadlApplication) {
        if (this.uriInfo != null) {
            String message;
            Doc d = new Doc();
            if (this.detailedWadl) {
                String uriWithoutQueryParam = UriBuilder.fromUri(this.uriInfo.getRequestUri()).replaceQuery("").build(new Object[0]).toString();
                message = LocalizationMessages.WADL_DOC_EXTENDED_WADL("detail", uriWithoutQueryParam);
            } else {
                String uriWithQueryParam = UriBuilder.fromUri(this.uriInfo.getRequestUri()).queryParam("detail", "true").build(new Object[0]).toString();
                message = LocalizationMessages.WADL_DOC_SIMPLE_WADL("detail", uriWithQueryParam);
            }
            d.getOtherAttributes().put(new QName("http://jersey.java.net/", "hint", "jersey"), message);
            wadlApplication.getDoc().add(d);
        }
    }

    private Method generateMethod(org.glassfish.jersey.server.model.Resource parentResource, Map<String, Param> wadlResourceParams, ResourceMethod resourceMethod) {
        try {
            List<Response> responses;
            if (!this.detailedWadl && resourceMethod.isExtended()) {
                return null;
            }
            Method wadlMethod = this._wadlGenerator.createMethod(parentResource, resourceMethod);
            Request wadlRequest = this.generateRequest(parentResource, resourceMethod, wadlResourceParams);
            if (wadlRequest != null) {
                wadlMethod.setRequest(wadlRequest);
            }
            if ((responses = this.generateResponses(parentResource, resourceMethod)) != null) {
                wadlMethod.getResponse().addAll(responses);
            }
            return wadlMethod;
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_BUILDER_GENERATION_METHOD(resourceMethod, parentResource), e);
        }
    }

    private Request generateRequest(org.glassfish.jersey.server.model.Resource parentResource, ResourceMethod resourceMethod, Map<String, Param> wadlResourceParams) {
        try {
            LinkedList<Parameter> requestParams = new LinkedList<Parameter>(resourceMethod.getInvocable().getParameters());
            requestParams.addAll(resourceMethod.getInvocable().getHandler().getParameters());
            if (requestParams.isEmpty()) {
                return null;
            }
            Request wadlRequest = this._wadlGenerator.createRequest(parentResource, resourceMethod);
            this.processRequestParameters(parentResource, resourceMethod, wadlResourceParams, requestParams, wadlRequest);
            if (wadlRequest.getRepresentation().size() + wadlRequest.getParam().size() == 0) {
                return null;
            }
            return wadlRequest;
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_BUILDER_GENERATION_REQUEST(resourceMethod, parentResource), e);
        }
    }

    private void processRequestParameters(org.glassfish.jersey.server.model.Resource parentResource, ResourceMethod resourceMethod, Map<String, Param> wadlResourceParams, Collection<Parameter> requestParameters, Request wadlRequest) {
        for (Parameter parameter : requestParameters) {
            Param wadlParam;
            Representation wadlRepresentation;
            List<MediaType> supportedInputTypes;
            if (parameter.getSource() == Parameter.Source.ENTITY || parameter.getSource() == Parameter.Source.UNKNOWN) {
                for (MediaType mediaType : resourceMethod.getConsumedTypes()) {
                    this.setRepresentationForMediaType(parentResource, resourceMethod, mediaType, wadlRequest);
                }
                continue;
            }
            if (parameter.getSourceAnnotation().annotationType() == FormParam.class) {
                supportedInputTypes = resourceMethod.getConsumedTypes();
                if (supportedInputTypes.isEmpty() || supportedInputTypes.size() == 1 && supportedInputTypes.get(0).isWildcardType()) {
                    supportedInputTypes = Collections.singletonList(MediaType.APPLICATION_FORM_URLENCODED_TYPE);
                }
                for (MediaType mediaType : supportedInputTypes) {
                    wadlRepresentation = this.setRepresentationForMediaType(parentResource, resourceMethod, mediaType, wadlRequest);
                    if (this.getParamByName(wadlRepresentation.getParam(), parameter.getSourceName()) != null || (wadlParam = this.generateParam(parentResource, resourceMethod, parameter)) == null) continue;
                    wadlRepresentation.getParam().add(wadlParam);
                }
                continue;
            }
            if ("org.glassfish.jersey.media.multipart.FormDataParam".equals(parameter.getSourceAnnotation().annotationType().getName())) {
                supportedInputTypes = resourceMethod.getConsumedTypes();
                if (supportedInputTypes.isEmpty() || supportedInputTypes.size() == 1 && supportedInputTypes.get(0).isWildcardType()) {
                    supportedInputTypes = Collections.singletonList(MediaType.MULTIPART_FORM_DATA_TYPE);
                }
                for (MediaType mediaType : supportedInputTypes) {
                    wadlRepresentation = this.setRepresentationForMediaType(parentResource, resourceMethod, mediaType, wadlRequest);
                    if (this.getParamByName(wadlRepresentation.getParam(), parameter.getSourceName()) != null || (wadlParam = this.generateParam(parentResource, resourceMethod, parameter)) == null) continue;
                    wadlRepresentation.getParam().add(wadlParam);
                }
                continue;
            }
            if (parameter instanceof Parameter.BeanParameter) {
                this.processRequestParameters(parentResource, resourceMethod, wadlResourceParams, ((Parameter.BeanParameter)parameter).getParameters(), wadlRequest);
                continue;
            }
            Param wadlParam2 = this.generateParam(parentResource, resourceMethod, parameter);
            if (wadlParam2 == null) continue;
            if (wadlParam2.getStyle() == ParamStyle.TEMPLATE || wadlParam2.getStyle() == ParamStyle.MATRIX) {
                wadlResourceParams.put(wadlParam2.getName(), wadlParam2);
                continue;
            }
            wadlRequest.getParam().add(wadlParam2);
        }
    }

    private Param getParamByName(List<Param> params, String name) {
        for (Param param2 : params) {
            if (!param2.getName().equals(name)) continue;
            return param2;
        }
        return null;
    }

    private Representation setRepresentationForMediaType(org.glassfish.jersey.server.model.Resource r, ResourceMethod m4, MediaType mediaType, Request wadlRequest) {
        try {
            Representation wadlRepresentation = this.getRepresentationByMediaType(wadlRequest.getRepresentation(), mediaType);
            if (wadlRepresentation == null) {
                wadlRepresentation = this._wadlGenerator.createRequestRepresentation(r, m4, mediaType);
                wadlRequest.getRepresentation().add(wadlRepresentation);
            }
            return wadlRepresentation;
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_BUILDER_GENERATION_REQUEST_MEDIA_TYPE(mediaType, m4, r), e);
        }
    }

    private Representation getRepresentationByMediaType(List<Representation> representations, MediaType mediaType) {
        for (Representation representation : representations) {
            if (!mediaType.toString().equals(representation.getMediaType())) continue;
            return representation;
        }
        return null;
    }

    private Param generateParam(org.glassfish.jersey.server.model.Resource resource, ResourceMethod method2, Parameter param2) {
        try {
            if (param2.getSource() == Parameter.Source.ENTITY || param2.getSource() == Parameter.Source.CONTEXT) {
                return null;
            }
            return this._wadlGenerator.createParam(resource, method2, param2);
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_BUILDER_GENERATION_PARAM(param2, resource, method2), e);
        }
    }

    private Resource generateResource(org.glassfish.jersey.server.model.Resource r, String path) {
        return this.generateResource(r, path, Collections.emptySet());
    }

    private Resource generateResource(org.glassfish.jersey.server.model.Resource resource, String path, Set<org.glassfish.jersey.server.model.Resource> visitedResources) {
        try {
            if (!this.detailedWadl && resource.isExtended()) {
                return null;
            }
            Resource wadlResource = this._wadlGenerator.createResource(resource, path);
            if (visitedResources.contains(resource)) {
                return wadlResource;
            }
            visitedResources = new HashSet<org.glassfish.jersey.server.model.Resource>(visitedResources);
            visitedResources.add(resource);
            ResourceMethod locator = resource.getResourceLocator();
            if (locator != null) {
                try {
                    org.glassfish.jersey.server.model.Resource subResource;
                    Resource wadlSubResource;
                    Resource.Builder builder = org.glassfish.jersey.server.model.Resource.builder(locator.getInvocable().getRawResponseType());
                    if (builder == null) {
                        builder = org.glassfish.jersey.server.model.Resource.builder().path(resource.getPath());
                    }
                    if ((wadlSubResource = this.generateResource(subResource = builder.build(), resource.getPath(), visitedResources)) == null) {
                        return null;
                    }
                    if (locator.isExtended()) {
                        wadlSubResource.getAny().add(WadlApplicationContextImpl.EXTENDED_ELEMENT);
                    }
                    for (Parameter param2 : locator.getInvocable().getParameters()) {
                        Param wadlParam = this.generateParam(resource, locator, param2);
                        if (wadlParam == null || wadlParam.getStyle() != ParamStyle.TEMPLATE) continue;
                        wadlSubResource.getParam().add(wadlParam);
                    }
                    return wadlSubResource;
                }
                catch (RuntimeException e) {
                    throw new ProcessingException(LocalizationMessages.ERROR_WADL_BUILDER_GENERATION_RESOURCE_LOCATOR(locator, resource), e);
                }
            }
            HashMap<String, Param> wadlResourceParams = new HashMap<String, Param>();
            for (ResourceMethod method2 : resource.getResourceMethods()) {
                if (!this.detailedWadl && method2.isExtended()) continue;
                Method wadlMethod = this.generateMethod(resource, wadlResourceParams, method2);
                wadlResource.getMethodOrResource().add(wadlMethod);
            }
            for (Param wadlParam : wadlResourceParams.values()) {
                wadlResource.getParam().add(wadlParam);
            }
            HashMap wadlSubResources = new HashMap();
            HashMap wadlSubResourcesParams = new HashMap();
            for (org.glassfish.jersey.server.model.Resource childResource : resource.getChildResources()) {
                Resource childWadlResource = this.generateResource(childResource, childResource.getPath(), visitedResources);
                if (childWadlResource == null) continue;
                wadlResource.getMethodOrResource().add(childWadlResource);
            }
            return wadlResource;
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_BUILDER_GENERATION_RESOURCE_PATH(resource, path), e);
        }
    }

    private List<Response> generateResponses(org.glassfish.jersey.server.model.Resource r, ResourceMethod m4) {
        try {
            if (m4.getInvocable().getRawResponseType() == Void.TYPE) {
                return null;
            }
            return this._wadlGenerator.createResponses(r, m4);
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_BUILDER_GENERATION_RESPONSE(m4, r), e);
        }
    }
}

