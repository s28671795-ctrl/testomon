/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl.internal;

import com.sun.research.ws.wadl.Application;
import com.sun.research.ws.wadl.Doc;
import com.sun.research.ws.wadl.Grammars;
import com.sun.research.ws.wadl.Include;
import com.sun.research.ws.wadl.Resource;
import com.sun.research.ws.wadl.Resources;
import java.net.URI;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.util.ReflectionHelper;
import org.glassfish.jersey.server.ExtendedResourceContext;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.wadl.WadlApplicationContext;
import org.glassfish.jersey.server.wadl.WadlGenerator;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorConfig;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorConfigLoader;
import org.glassfish.jersey.server.wadl.internal.ApplicationDescription;
import org.glassfish.jersey.server.wadl.internal.WadlBuilder;
import org.glassfish.jersey.server.wadl.internal.generators.WadlGeneratorJAXBGrammarGenerator;

public final class WadlApplicationContextImpl
implements WadlApplicationContext {
    private static final Logger LOGGER = Logger.getLogger(WadlApplicationContextImpl.class.getName());
    static final String WADL_JERSEY_NAMESPACE = "http://jersey.java.net/";
    static final JAXBElement EXTENDED_ELEMENT = new JAXBElement<String>(new QName("http://jersey.java.net/", "extended", "jersey"), String.class, "true");
    private final ExtendedResourceContext resourceContext;
    private final InjectionManager injectionManager;
    private final WadlGeneratorConfig wadlGeneratorConfig;
    private final JAXBContext jaxbContext;
    private volatile boolean wadlGenerationEnabled = true;

    @Inject
    public WadlApplicationContextImpl(InjectionManager injectionManager, Configuration configuration, ExtendedResourceContext resourceContext) {
        this.injectionManager = injectionManager;
        this.wadlGeneratorConfig = WadlGeneratorConfigLoader.loadWadlGeneratorsFromConfig(configuration.getProperties());
        this.resourceContext = resourceContext;
        WadlGenerator wadlGenerator = this.wadlGeneratorConfig.createWadlGenerator(injectionManager);
        try {
            this.jaxbContext = WadlApplicationContextImpl.getJAXBContextFromWadlGenerator(wadlGenerator);
        }
        catch (JAXBException ex) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_JAXB_CONTEXT(), ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static JAXBContext getJAXBContextFromWadlGenerator(WadlGenerator wadlGenerator) throws JAXBException {
        JAXBContext jaxbContextCandidate = null;
        ClassLoader contextClassLoader = AccessController.doPrivileged(ReflectionHelper.getContextClassLoaderPA());
        try {
            ClassLoader jerseyModuleClassLoader = AccessController.doPrivileged(ReflectionHelper.getClassLoaderPA(wadlGenerator.getClass()));
            AccessController.doPrivileged(ReflectionHelper.setContextClassLoaderPA(jerseyModuleClassLoader));
            jaxbContextCandidate = JAXBContext.newInstance(wadlGenerator.getRequiredJaxbContextPath(), jerseyModuleClassLoader);
        }
        catch (JAXBException ex) {
            try {
                LOGGER.log(Level.FINE, LocalizationMessages.WADL_JAXB_CONTEXT_FALLBACK(), ex);
                jaxbContextCandidate = JAXBContext.newInstance(wadlGenerator.getRequiredJaxbContextPath());
            }
            catch (JAXBException innerEx) {
                throw ex;
            }
        }
        finally {
            AccessController.doPrivileged(ReflectionHelper.setContextClassLoaderPA(contextClassLoader));
        }
        return jaxbContextCandidate;
    }

    @Override
    public ApplicationDescription getApplication(UriInfo uriInfo, boolean detailedWadl) {
        ApplicationDescription applicationDescription = this.getWadlBuilder(detailedWadl, uriInfo).generate(this.resourceContext.getResourceModel().getRootResources());
        Application application = applicationDescription.getApplication();
        for (Resources resources : application.getResources()) {
            if (resources.getBase() != null) continue;
            resources.setBase(uriInfo.getBaseUri().toString());
        }
        this.attachExternalGrammar(application, applicationDescription, uriInfo.getRequestUri());
        return applicationDescription;
    }

    @Override
    public Application getApplication(UriInfo info, org.glassfish.jersey.server.model.Resource resource, boolean detailedWadl) {
        ApplicationDescription description = this.getApplication(info, detailedWadl);
        WadlGenerator wadlGenerator = this.wadlGeneratorConfig.createWadlGenerator(this.injectionManager);
        Application application = new WadlBuilder(wadlGenerator, detailedWadl, info).generate(description, resource);
        if (application == null) {
            return null;
        }
        for (Resources resources : application.getResources()) {
            resources.setBase(info.getBaseUri().toString());
        }
        this.attachExternalGrammar(application, description, info.getRequestUri());
        for (Resources resources : application.getResources()) {
            Resource r = resources.getResource().get(0);
            r.setPath(info.getBaseUri().relativize(info.getAbsolutePath()).toString());
            r.getParam().clear();
        }
        return application;
    }

    @Override
    public JAXBContext getJAXBContext() {
        return this.jaxbContext;
    }

    private WadlBuilder getWadlBuilder(boolean detailedWadl, UriInfo uriInfo) {
        return this.wadlGenerationEnabled ? new WadlBuilder(this.wadlGeneratorConfig.createWadlGenerator(this.injectionManager), detailedWadl, uriInfo) : null;
    }

    @Override
    public void setWadlGenerationEnabled(boolean wadlGenerationEnabled) {
        this.wadlGenerationEnabled = wadlGenerationEnabled;
    }

    @Override
    public boolean isWadlGenerationEnabled() {
        return this.wadlGenerationEnabled;
    }

    private void attachExternalGrammar(Application application, ApplicationDescription applicationDescription, URI requestURI) {
        try {
            Grammars grammars;
            URI rootURI;
            String root;
            String requestURIPath = requestURI.getPath();
            if (requestURIPath.endsWith("application.wadl")) {
                requestURI = UriBuilder.fromUri(requestURI).replacePath(requestURIPath.substring(0, requestURIPath.lastIndexOf(47) + 1)).build(new Object[0]);
            }
            UriBuilder extendedPath = (root = application.getResources().get(0).getBase()) != null ? UriBuilder.fromPath(root).path("/application.wadl/") : UriBuilder.fromPath("./application.wadl/");
            URI uRI = rootURI = root != null ? UriBuilder.fromPath(root).build(new Object[0]) : null;
            if (application.getGrammars() != null) {
                LOGGER.info(LocalizationMessages.ERROR_WADL_GRAMMAR_ALREADY_CONTAINS());
                grammars = application.getGrammars();
            } else {
                grammars = new Grammars();
                application.setGrammars(grammars);
            }
            for (String path : applicationDescription.getExternalMetadataKeys()) {
                URI schemaURI = extendedPath.clone().path(path).build(new Object[0]);
                String schemaPath = rootURI != null ? requestURI.relativize(schemaURI).toString() : schemaURI.toString();
                Include include = new Include();
                include.setHref(schemaPath);
                Doc doc = new Doc();
                doc.setLang("en");
                doc.setTitle("Generated");
                include.getDoc().add(doc);
                grammars.getInclude().add(include);
            }
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_WADL_EXTERNAL_GRAMMAR(), e);
        }
    }

    public static boolean isJaxbImplAvailable() {
        try {
            return null != WadlApplicationContextImpl.getJAXBContextFromWadlGenerator(new WadlGeneratorJAXBGrammarGenerator());
        }
        catch (JAXBException je) {
            return false;
        }
    }
}

