/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal;

import com.sun.research.ws.wadl.Application;
import com.sun.research.ws.wadl.Method;
import com.sun.research.ws.wadl.Param;
import com.sun.research.ws.wadl.ParamStyle;
import com.sun.research.ws.wadl.Representation;
import com.sun.research.ws.wadl.Request;
import com.sun.research.ws.wadl.Resource;
import com.sun.research.ws.wadl.Resources;
import com.sun.research.ws.wadl.Response;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.xml.namespace.QName;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.wadl.WadlGenerator;
import org.glassfish.jersey.server.wadl.internal.ApplicationDescription;
import org.glassfish.jersey.server.wadl.internal.WadlApplicationContextImpl;

public class WadlGeneratorImpl
implements WadlGenerator {
    @Override
    public String getRequiredJaxbContextPath() {
        String name = Application.class.getName();
        return name.substring(0, name.lastIndexOf(46));
    }

    @Override
    public void init() {
    }

    @Override
    public void setWadlGeneratorDelegate(WadlGenerator delegate) {
        throw new UnsupportedOperationException("No delegate supported.");
    }

    @Override
    public Resources createResources() {
        return new Resources();
    }

    @Override
    public Application createApplication() {
        return new Application();
    }

    @Override
    public Method createMethod(org.glassfish.jersey.server.model.Resource r, ResourceMethod m4) {
        Method wadlMethod = new Method();
        wadlMethod.setName(m4.getHttpMethod());
        wadlMethod.setId(m4.getInvocable().getDefinitionMethod().getName());
        if (m4.isExtended()) {
            wadlMethod.getAny().add(WadlApplicationContextImpl.EXTENDED_ELEMENT);
        }
        return wadlMethod;
    }

    @Override
    public Representation createRequestRepresentation(org.glassfish.jersey.server.model.Resource r, ResourceMethod m4, MediaType mediaType) {
        Representation wadlRepresentation = new Representation();
        wadlRepresentation.setMediaType(mediaType.toString());
        return wadlRepresentation;
    }

    @Override
    public Request createRequest(org.glassfish.jersey.server.model.Resource r, ResourceMethod m4) {
        return new Request();
    }

    @Override
    public Param createParam(org.glassfish.jersey.server.model.Resource r, ResourceMethod m4, Parameter p) {
        Class<?> pClass;
        if (p.getSource() == Parameter.Source.UNKNOWN) {
            return null;
        }
        Param wadlParam = new Param();
        wadlParam.setName(p.getSourceName());
        switch (p.getSource()) {
            case FORM: {
                wadlParam.setStyle(ParamStyle.QUERY);
                break;
            }
            case QUERY: {
                wadlParam.setStyle(ParamStyle.QUERY);
                break;
            }
            case MATRIX: {
                wadlParam.setStyle(ParamStyle.MATRIX);
                break;
            }
            case PATH: {
                wadlParam.setStyle(ParamStyle.TEMPLATE);
                break;
            }
            case HEADER: {
                wadlParam.setStyle(ParamStyle.HEADER);
                break;
            }
            case COOKIE: {
                wadlParam.setStyle(ParamStyle.HEADER);
                wadlParam.setName("Cookie");
                wadlParam.setPath(p.getSourceName());
                break;
            }
        }
        if (p.hasDefaultValue()) {
            wadlParam.setDefault(p.getDefaultValue());
        }
        if ((pClass = p.getRawType()).isArray()) {
            wadlParam.setRepeating(true);
            pClass = pClass.getComponentType();
        }
        if (pClass.equals(Integer.TYPE) || pClass.equals(Integer.class)) {
            wadlParam.setType(new QName("http://www.w3.org/2001/XMLSchema", "int", "xs"));
        } else if (pClass.equals(Boolean.TYPE) || pClass.equals(Boolean.class)) {
            wadlParam.setType(new QName("http://www.w3.org/2001/XMLSchema", "boolean", "xs"));
        } else if (pClass.equals(Long.TYPE) || pClass.equals(Long.class)) {
            wadlParam.setType(new QName("http://www.w3.org/2001/XMLSchema", "long", "xs"));
        } else if (pClass.equals(Short.TYPE) || pClass.equals(Short.class)) {
            wadlParam.setType(new QName("http://www.w3.org/2001/XMLSchema", "short", "xs"));
        } else if (pClass.equals(Byte.TYPE) || pClass.equals(Byte.class)) {
            wadlParam.setType(new QName("http://www.w3.org/2001/XMLSchema", "byte", "xs"));
        } else if (pClass.equals(Float.TYPE) || pClass.equals(Float.class)) {
            wadlParam.setType(new QName("http://www.w3.org/2001/XMLSchema", "float", "xs"));
        } else if (pClass.equals(Double.TYPE) || pClass.equals(Double.class)) {
            wadlParam.setType(new QName("http://www.w3.org/2001/XMLSchema", "double", "xs"));
        } else {
            wadlParam.setType(new QName("http://www.w3.org/2001/XMLSchema", "string", "xs"));
        }
        return wadlParam;
    }

    @Override
    public Resource createResource(org.glassfish.jersey.server.model.Resource resource, String path) {
        Resource wadlResource = new Resource();
        if (path != null) {
            wadlResource.setPath(path);
        } else if (resource.getPath() != null) {
            wadlResource.setPath(resource.getPath());
        }
        if (resource.isExtended()) {
            wadlResource.getAny().add(WadlApplicationContextImpl.EXTENDED_ELEMENT);
        }
        return wadlResource;
    }

    @Override
    public List<Response> createResponses(org.glassfish.jersey.server.model.Resource r, ResourceMethod m4) {
        Response response = new Response();
        if (this.hasEmptyProducibleMediaTypeSet(m4)) {
            Representation wadlRepresentation = this.createResponseRepresentation(r, m4, MediaType.WILDCARD_TYPE);
            response.getRepresentation().add(wadlRepresentation);
        } else {
            for (MediaType mediaType : m4.getProducedTypes()) {
                Representation wadlRepresentation = this.createResponseRepresentation(r, m4, mediaType);
                response.getRepresentation().add(wadlRepresentation);
            }
        }
        ArrayList<Response> responses = new ArrayList<Response>();
        responses.add(response);
        return responses;
    }

    private boolean hasEmptyProducibleMediaTypeSet(ResourceMethod method2) {
        return method2.getProducedTypes().isEmpty();
    }

    public Representation createResponseRepresentation(org.glassfish.jersey.server.model.Resource r, ResourceMethod m4, MediaType mediaType) {
        Representation wadlRepresentation = new Representation();
        wadlRepresentation.setMediaType(mediaType.toString());
        return wadlRepresentation;
    }

    @Override
    public WadlGenerator.ExternalGrammarDefinition createExternalGrammar() {
        return new WadlGenerator.ExternalGrammarDefinition();
    }

    @Override
    public void attachTypes(ApplicationDescription egd) {
    }
}

