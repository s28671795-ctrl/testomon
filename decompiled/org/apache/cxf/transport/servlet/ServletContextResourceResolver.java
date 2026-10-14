/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 */
package org.apache.cxf.transport.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import org.apache.cxf.resource.ResourceResolver;

public class ServletContextResourceResolver
implements ResourceResolver {
    ServletContext servletContext;
    Map<String, URL> urlMap = new ConcurrentHashMap<String, URL>();

    public ServletContextResourceResolver(ServletContext sc) {
        this.servletContext = sc;
    }

    @Override
    public final InputStream getAsStream(String string) {
        if (this.urlMap.containsKey(string)) {
            try {
                return this.urlMap.get(string).openStream();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return this.servletContext.getResourceAsStream(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final <T> T resolve(String entryName, Class<T> clz) {
        Object obj;
        block19: {
            obj = null;
            try {
                if (entryName == null) break block19;
                try (InitialContext ic = new InitialContext();){
                    obj = ic.lookup(entryName);
                }
            }
            catch (Throwable ic) {
                // empty catch block
            }
        }
        if (obj != null && clz.isInstance(obj)) {
            return clz.cast(obj);
        }
        if (clz.isAssignableFrom(URL.class)) {
            URL url22;
            if (this.urlMap.containsKey(entryName)) {
                return clz.cast(this.urlMap.get(entryName));
            }
            try {
                url22 = this.servletContext.getResource(entryName);
                if (url22 != null && "file".equals(url22.getProtocol()) && !new File(url22.toURI()).exists()) {
                    url22 = null;
                }
                if (url22 != null) {
                    this.urlMap.put(url22.toString(), url22);
                    return clz.cast(url22);
                }
            }
            catch (MalformedURLException url22) {
            }
            catch (URISyntaxException url22) {
                // empty catch block
            }
            try {
                url22 = this.servletContext.getResource("/" + entryName);
                if (url22 != null && "file".equals(url22.getProtocol()) && !new File(url22.toURI()).exists()) {
                    url22 = null;
                }
                if (url22 != null) {
                    this.urlMap.put(url22.toString(), url22);
                    return clz.cast(url22);
                }
            }
            catch (MalformedURLException | URISyntaxException exception) {}
        } else if (clz.isAssignableFrom(InputStream.class)) {
            return clz.cast(this.getAsStream(entryName));
        }
        return null;
    }
}

