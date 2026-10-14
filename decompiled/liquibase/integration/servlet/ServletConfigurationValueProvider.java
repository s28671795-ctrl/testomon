/*
 * Decompiled with CFR 0.152.
 */
package liquibase.integration.servlet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import liquibase.configuration.AbstractConfigurationValueProvider;
import liquibase.configuration.ProvidedValue;
import liquibase.integration.servlet.GenericServletWrapper;

public class ServletConfigurationValueProvider
extends AbstractConfigurationValueProvider {
    private static final String JAVA_COMP_ENV = "java:comp/env";
    private final GenericServletWrapper.ServletContext servletContext;
    private final InitialContext initialContext;

    @Override
    public int getPrecedence() {
        return 30;
    }

    public ServletConfigurationValueProvider(GenericServletWrapper.ServletContext servletContext, InitialContext initialContext) {
        this.servletContext = servletContext;
        this.initialContext = initialContext;
    }

    @Override
    public ProvidedValue getProvidedValue(String ... keyAndAliases) {
        if (this.initialContext != null) {
            for (String key : keyAndAliases) {
                try {
                    Context envCtx = (Context)this.initialContext.lookup(JAVA_COMP_ENV);
                    String valueFromJndi = (String)envCtx.lookup(key);
                    return new ProvidedValue(keyAndAliases[0], "java:comp/env/" + key, valueFromJndi, "JNDI", this);
                }
                catch (NamingException envCtx) {
                }
            }
        }
        if (this.servletContext != null) {
            for (String key : keyAndAliases) {
                String valueFromServletContext = this.servletContext.getInitParameter(key);
                if (valueFromServletContext == null) continue;
                return new ProvidedValue(keyAndAliases[0], key, valueFromServletContext, "Servlet context", this);
            }
        }
        return null;
    }
}

