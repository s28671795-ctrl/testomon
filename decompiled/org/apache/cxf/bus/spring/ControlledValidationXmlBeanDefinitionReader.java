/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanDefinitionStoreException
 *  org.springframework.beans.factory.support.BeanDefinitionRegistry
 *  org.springframework.beans.factory.xml.DocumentLoader
 *  org.springframework.beans.factory.xml.XmlBeanDefinitionReader
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.UrlResource
 *  org.springframework.core.io.support.EncodedResource
 */
package org.apache.cxf.bus.spring;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.apache.cxf.bus.spring.TunedDocumentLoader;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.DocumentLoader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.EncodedResource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class ControlledValidationXmlBeanDefinitionReader
extends XmlBeanDefinitionReader {
    private boolean noFastinfoset;
    private int visibleValidationMode = 1;
    private TunedDocumentLoader tunedDocumentLoader = new TunedDocumentLoader();

    public ControlledValidationXmlBeanDefinitionReader(BeanDefinitionRegistry beanFactory) {
        super(beanFactory);
        this.setDocumentLoader((DocumentLoader)this.tunedDocumentLoader);
        this.noFastinfoset = SystemPropertyAction.getPropertyOrNull("org.apache.cxf.nofastinfoset") != null || !TunedDocumentLoader.hasFastInfoSet();
    }

    protected int doLoadBeanDefinitions(InputSource inputSource, Resource resource) {
        boolean suppressValidation = false;
        try {
            URL url2 = resource.getURL();
            if (url2.getFile().contains("META-INF/cxf/")) {
                suppressValidation = true;
            }
        }
        catch (IOException url2) {
            // empty catch block
        }
        int savedValidation = this.visibleValidationMode;
        if (suppressValidation) {
            this.setValidationMode(0);
        }
        int r = super.doLoadBeanDefinitions(inputSource, resource);
        this.setValidationMode(savedValidation);
        return r;
    }

    public void setValidationMode(int validationMode) {
        this.visibleValidationMode = validationMode;
        super.setValidationMode(validationMode);
    }

    public int loadBeanDefinitions(final EncodedResource encodedResource) {
        if (!this.noFastinfoset) {
            try {
                return this.fastInfosetLoadBeanDefinitions(encodedResource);
            }
            catch (BeanDefinitionStoreException bdse) {
                throw bdse;
            }
            catch (Throwable bdse) {
                // empty catch block
            }
        }
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Integer>(){

                @Override
                public Integer run() throws Exception {
                    return ControlledValidationXmlBeanDefinitionReader.this.internalLoadBeanDefinitions(encodedResource);
                }
            });
        }
        catch (PrivilegedActionException e) {
            if (e.getException() instanceof RuntimeException) {
                throw (RuntimeException)e.getException();
            }
            throw (BeanDefinitionStoreException)((Object)e.getException());
        }
    }

    private int internalLoadBeanDefinitions(EncodedResource encodedResource) {
        return super.loadBeanDefinitions(encodedResource);
    }

    private int fastInfosetLoadBeanDefinitions(EncodedResource encodedResource) throws IOException, StaleFastinfosetException, ParserConfigurationException, XMLStreamException {
        URL resUrl = encodedResource.getResource().getURL();
        if (!resUrl.getPath().endsWith(".xml")) {
            throw new StaleFastinfosetException();
        }
        String fixmlPath = resUrl.getPath().replaceFirst("\\.xml$", ".fixml");
        String protocol = resUrl.getProtocol();
        if ("jar".equals(protocol)) {
            fixmlPath = fixmlPath.replaceFirst("^.*!", "");
        }
        URL fixmlUrl = new URL(resUrl, fixmlPath);
        if ("file".equals(protocol)) {
            URLConnection resCon = resUrl.openConnection();
            URLConnection fixCon = fixmlUrl.openConnection();
            if (resCon.getLastModified() > fixCon.getLastModified()) {
                throw new StaleFastinfosetException();
            }
        }
        UrlResource newResource = new UrlResource(fixmlUrl);
        Document doc = TunedDocumentLoader.loadFastinfosetDocument(fixmlUrl);
        if (doc == null) {
            throw new StaleFastinfosetException();
        }
        return this.registerBeanDefinitions(doc, (Resource)newResource);
    }

    private static class StaleFastinfosetException
    extends Exception {
        private static final long serialVersionUID = -3594973504794187383L;

        private StaleFastinfosetException() {
        }
    }
}

