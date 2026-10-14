/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal.generators;

import com.sun.research.ws.wadl.Application;
import com.sun.research.ws.wadl.Method;
import com.sun.research.ws.wadl.Param;
import com.sun.research.ws.wadl.Representation;
import com.sun.research.ws.wadl.Request;
import com.sun.research.ws.wadl.Resources;
import com.sun.research.ws.wadl.Response;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import org.glassfish.jersey.model.Parameter;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.wadl.WadlGenerator;
import org.glassfish.jersey.server.wadl.internal.ApplicationDescription;
import org.glassfish.jersey.server.wadl.internal.WadlGeneratorImpl;

public class WadlGeneratorJAXBGrammarGenerator
implements WadlGenerator {
    private static final Logger LOGGER = Logger.getLogger(WadlGeneratorJAXBGrammarGenerator.class.getName());
    private static final Set<Class> SPECIAL_GENERIC_TYPES = new HashSet<Class>(){
        {
            this.add(List.class);
        }
    };
    private WadlGenerator wadlGeneratorDelegate = new WadlGeneratorImpl();
    private Set<Class> seeAlsoClasses;
    private List<TypeCallbackPair> nameCallbacks;

    @Override
    public void setWadlGeneratorDelegate(WadlGenerator delegate) {
        this.wadlGeneratorDelegate = delegate;
    }

    @Override
    public String getRequiredJaxbContextPath() {
        return this.wadlGeneratorDelegate.getRequiredJaxbContextPath();
    }

    @Override
    public void init() throws Exception {
        this.wadlGeneratorDelegate.init();
        this.seeAlsoClasses = new HashSet<Class>();
        this.nameCallbacks = new ArrayList<TypeCallbackPair>();
    }

    @Override
    public Application createApplication() {
        return this.wadlGeneratorDelegate.createApplication();
    }

    @Override
    public Method createMethod(Resource ar, ResourceMethod arm) {
        return this.wadlGeneratorDelegate.createMethod(ar, arm);
    }

    @Override
    public Request createRequest(Resource ar, ResourceMethod arm) {
        return this.wadlGeneratorDelegate.createRequest(ar, arm);
    }

    @Override
    public Param createParam(Resource ar, ResourceMethod am, Parameter p) {
        final Param param2 = this.wadlGeneratorDelegate.createParam(ar, am, p);
        if (p.getSource() == Parameter.Source.ENTITY) {
            this.nameCallbacks.add(new TypeCallbackPair(new GenericType(p.getType()), new NameCallbackSetter(){

                @Override
                public void setName(QName name) {
                    param2.setType(name);
                }
            }));
        }
        return param2;
    }

    @Override
    public Representation createRequestRepresentation(Resource ar, ResourceMethod arm, MediaType mt) {
        final Representation rt = this.wadlGeneratorDelegate.createRequestRepresentation(ar, arm, mt);
        for (Parameter p : arm.getInvocable().getParameters()) {
            if (p.getSource() != Parameter.Source.ENTITY) continue;
            this.nameCallbacks.add(new TypeCallbackPair(new GenericType(p.getType()), new NameCallbackSetter(){

                @Override
                public void setName(QName name) {
                    rt.setElement(name);
                }
            }));
        }
        return rt;
    }

    @Override
    public com.sun.research.ws.wadl.Resource createResource(Resource ar, String path) {
        for (Class<?> resClass : ar.getHandlerClasses()) {
            XmlSeeAlso seeAlso = resClass.getAnnotation(XmlSeeAlso.class);
            if (seeAlso == null) continue;
            Collections.addAll(this.seeAlsoClasses, seeAlso.value());
        }
        return this.wadlGeneratorDelegate.createResource(ar, path);
    }

    @Override
    public Resources createResources() {
        return this.wadlGeneratorDelegate.createResources();
    }

    @Override
    public List<Response> createResponses(Resource resource, ResourceMethod resourceMethod) {
        List<Response> responses = this.wadlGeneratorDelegate.createResponses(resource, resourceMethod);
        if (responses != null) {
            for (Response response : responses) {
                for (final Representation representation : response.getRepresentation()) {
                    this.nameCallbacks.add(new TypeCallbackPair(new GenericType(resourceMethod.getInvocable().getResponseType()), new NameCallbackSetter(){

                        @Override
                        public void setName(QName name) {
                            representation.setElement(name);
                        }
                    }));
                }
            }
        }
        return responses;
    }

    @Override
    public WadlGenerator.ExternalGrammarDefinition createExternalGrammar() {
        HashMap<String, ApplicationDescription.ExternalGrammar> extraFiles = new HashMap<String, ApplicationDescription.ExternalGrammar>();
        WadlGenerator.Resolver resolver2 = this.buildModelAndSchemas(extraFiles);
        WadlGenerator.ExternalGrammarDefinition previous = this.wadlGeneratorDelegate.createExternalGrammar();
        previous.map.putAll(extraFiles);
        if (resolver2 != null) {
            previous.addResolver(resolver2);
        }
        return previous;
    }

    private WadlGenerator.Resolver buildModelAndSchemas(Map<String, ApplicationDescription.ExternalGrammar> extraFiles) {
        HashSet<Class> classSet = new HashSet<Class>(this.seeAlsoClasses);
        for (TypeCallbackPair pair : this.nameCallbacks) {
            Type parameterType;
            Type type;
            GenericType<?> genericType = pair.genericType;
            Class<?> clazz = genericType.getRawType();
            if (clazz.getAnnotation(XmlRootElement.class) != null) {
                classSet.add(clazz);
                continue;
            }
            if (!SPECIAL_GENERIC_TYPES.contains(clazz) || !((type = genericType.getType()) instanceof ParameterizedType) || !((parameterType = ((ParameterizedType)type).getActualTypeArguments()[0]) instanceof Class)) continue;
            classSet.add((Class)parameterType);
        }
        JAXBIntrospector introspector = null;
        try {
            JAXBContext context = JAXBContext.newInstance(classSet.toArray(new Class[classSet.size()]));
            final ArrayList results = new ArrayList();
            context.generateSchema(new SchemaOutputResolver(){
                int counter = 0;

                @Override
                public Result createOutput(String namespaceUri, String suggestedFileName) {
                    StreamResult result = new StreamResult(new CharArrayWriter());
                    result.setSystemId("xsd" + this.counter++ + ".xsd");
                    results.add(result);
                    return result;
                }
            });
            for (StreamResult result : results) {
                CharArrayWriter writer = (CharArrayWriter)result.getWriter();
                byte[] contents = writer.toString().getBytes("UTF8");
                extraFiles.put(result.getSystemId(), new ApplicationDescription.ExternalGrammar(MediaType.APPLICATION_XML_TYPE, contents));
            }
            introspector = context.createJAXBIntrospector();
        }
        catch (JAXBException e) {
            LOGGER.log(Level.SEVERE, "Failed to generate the schema for the JAX-B elements", e);
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to generate the schema for the JAX-B elements due to an IO error", e);
        }
        if (introspector != null) {
            final JAXBIntrospector copy = introspector;
            return new WadlGenerator.Resolver(){

                @Override
                public QName resolve(final Class type) {
                    Object parameterClassInstance = null;
                    try {
                        Constructor defaultConstructor = (Constructor)AccessController.doPrivileged(new PrivilegedExceptionAction<Constructor<?>>(){

                            @Override
                            public Constructor<?> run() throws NoSuchMethodException {
                                Constructor constructor = type.getDeclaredConstructor(new Class[0]);
                                constructor.setAccessible(true);
                                return constructor;
                            }
                        });
                        parameterClassInstance = defaultConstructor.newInstance(new Object[0]);
                    }
                    catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException | InvocationTargetException ex) {
                        LOGGER.log(Level.FINE, null, ex);
                    }
                    catch (PrivilegedActionException ex) {
                        LOGGER.log(Level.FINE, null, ex.getCause());
                    }
                    if (parameterClassInstance == null) {
                        return null;
                    }
                    try {
                        return copy.getElementName(parameterClassInstance);
                    }
                    catch (NullPointerException e) {
                        return null;
                    }
                }
            };
        }
        return null;
    }

    @Override
    public void attachTypes(ApplicationDescription introspector) {
        if (introspector != null) {
            for (TypeCallbackPair pair : this.nameCallbacks) {
                QName name;
                Class parameterClass = pair.genericType.getRawType();
                if (SPECIAL_GENERIC_TYPES.contains(parameterClass)) {
                    Type type = pair.genericType.getType();
                    if (ParameterizedType.class.isAssignableFrom(type.getClass()) && Class.class.isAssignableFrom(((ParameterizedType)type).getActualTypeArguments()[0].getClass())) {
                        parameterClass = (Class)((ParameterizedType)type).getActualTypeArguments()[0];
                    } else {
                        LOGGER.fine("Couldn't find JAX-B element due to nested parameterized type " + type);
                        return;
                    }
                }
                if ((name = introspector.resolve(parameterClass)) != null) {
                    pair.nameCallbackSetter.setName(name);
                    continue;
                }
                LOGGER.fine("Couldn't find JAX-B element for class " + parameterClass.getName());
            }
        }
    }

    private class TypeCallbackPair {
        GenericType<?> genericType;
        NameCallbackSetter nameCallbackSetter;

        public TypeCallbackPair(GenericType<?> genericType, NameCallbackSetter nameCallbackSetter) {
            this.genericType = genericType;
            this.nameCallbackSetter = nameCallbackSetter;
        }
    }

    private static interface NameCallbackSetter {
        public void setName(QName var1);
    }
}

