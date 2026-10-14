/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.wadl;

import com.sun.research.ws.wadl.Application;
import com.sun.research.ws.wadl.Method;
import com.sun.research.ws.wadl.Param;
import com.sun.research.ws.wadl.Representation;
import com.sun.research.ws.wadl.Request;
import com.sun.research.ws.wadl.Resource;
import com.sun.research.ws.wadl.Resources;
import com.sun.research.ws.wadl.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.xml.namespace.QName;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.wadl.internal.ApplicationDescription;

public interface WadlGenerator {
    public void setWadlGeneratorDelegate(WadlGenerator var1);

    public void init() throws Exception;

    public String getRequiredJaxbContextPath();

    public Application createApplication();

    public Resources createResources();

    public Resource createResource(org.glassfish.jersey.server.model.Resource var1, String var2);

    public Method createMethod(org.glassfish.jersey.server.model.Resource var1, ResourceMethod var2);

    public Request createRequest(org.glassfish.jersey.server.model.Resource var1, ResourceMethod var2);

    public Representation createRequestRepresentation(org.glassfish.jersey.server.model.Resource var1, ResourceMethod var2, MediaType var3);

    public List<Response> createResponses(org.glassfish.jersey.server.model.Resource var1, ResourceMethod var2);

    public Param createParam(org.glassfish.jersey.server.model.Resource var1, ResourceMethod var2, Parameter var3);

    public ExternalGrammarDefinition createExternalGrammar();

    public void attachTypes(ApplicationDescription var1);

    public static class ExternalGrammarDefinition {
        public final Map<String, ApplicationDescription.ExternalGrammar> map = new HashMap<String, ApplicationDescription.ExternalGrammar>();
        private List<Resolver> typeResolvers = new ArrayList<Resolver>();

        public void addResolver(Resolver resolver2) {
            assert (!this.typeResolvers.contains(resolver2)) : "Already in list";
            this.typeResolvers.add(resolver2);
        }

        public QName resolve(Class type) {
            Resolver resolver2;
            QName name = null;
            Iterator<Resolver> iterator2 = this.typeResolvers.iterator();
            while (iterator2.hasNext() && (name = (resolver2 = iterator2.next()).resolve(type)) == null) {
            }
            return name;
        }
    }

    public static interface Resolver {
        public QName resolve(Class var1);
    }
}

