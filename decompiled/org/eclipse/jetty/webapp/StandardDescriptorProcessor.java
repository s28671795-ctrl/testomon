/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.webapp;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.SessionTrackingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.http.pathmap.ServletPathSpec;
import org.eclipse.jetty.security.ConstraintAware;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.Holder;
import org.eclipse.jetty.servlet.ListenerHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.eclipse.jetty.servlet.Source;
import org.eclipse.jetty.util.ArrayUtil;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.webapp.DefaultsDescriptor;
import org.eclipse.jetty.webapp.Descriptor;
import org.eclipse.jetty.webapp.FragmentDescriptor;
import org.eclipse.jetty.webapp.IterativeDescriptorProcessor;
import org.eclipse.jetty.webapp.Origin;
import org.eclipse.jetty.webapp.OverrideDescriptor;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebDescriptor;
import org.eclipse.jetty.xml.XmlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StandardDescriptorProcessor
extends IterativeDescriptorProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(StandardDescriptorProcessor.class);
    public static final String STANDARD_PROCESSOR = "org.eclipse.jetty.standardDescriptorProcessor";
    final Map<String, FilterHolder> _filterHolderMap = new HashMap<String, FilterHolder>();
    final List<FilterHolder> _filterHolders = new ArrayList<FilterHolder>();
    final List<FilterMapping> _filterMappings = new ArrayList<FilterMapping>();
    final Map<String, ServletHolder> _servletHolderMap = new HashMap<String, ServletHolder>();
    final List<ServletHolder> _servletHolders = new ArrayList<ServletHolder>();
    final List<ServletMapping> _servletMappings = new ArrayList<ServletMapping>();

    public StandardDescriptorProcessor() {
        try {
            this.registerVisitor("context-param", this.getClass().getMethod("visitContextParam", __signature));
            this.registerVisitor("display-name", this.getClass().getMethod("visitDisplayName", __signature));
            this.registerVisitor("servlet", this.getClass().getMethod("visitServlet", __signature));
            this.registerVisitor("servlet-mapping", this.getClass().getMethod("visitServletMapping", __signature));
            this.registerVisitor("session-config", this.getClass().getMethod("visitSessionConfig", __signature));
            this.registerVisitor("mime-mapping", this.getClass().getMethod("visitMimeMapping", __signature));
            this.registerVisitor("welcome-file-list", this.getClass().getMethod("visitWelcomeFileList", __signature));
            this.registerVisitor("locale-encoding-mapping-list", this.getClass().getMethod("visitLocaleEncodingList", __signature));
            this.registerVisitor("error-page", this.getClass().getMethod("visitErrorPage", __signature));
            this.registerVisitor("taglib", this.getClass().getMethod("visitTagLib", __signature));
            this.registerVisitor("jsp-config", this.getClass().getMethod("visitJspConfig", __signature));
            this.registerVisitor("security-constraint", this.getClass().getMethod("visitSecurityConstraint", __signature));
            this.registerVisitor("login-config", this.getClass().getMethod("visitLoginConfig", __signature));
            this.registerVisitor("security-role", this.getClass().getMethod("visitSecurityRole", __signature));
            this.registerVisitor("filter", this.getClass().getMethod("visitFilter", __signature));
            this.registerVisitor("filter-mapping", this.getClass().getMethod("visitFilterMapping", __signature));
            this.registerVisitor("listener", this.getClass().getMethod("visitListener", __signature));
            this.registerVisitor("deny-uncovered-http-methods", this.getClass().getMethod("visitDenyUncoveredHttpMethods", __signature));
            this.registerVisitor("default-context-path", this.getClass().getMethod("visitDefaultContextPath", __signature));
            this.registerVisitor("request-character-encoding", this.getClass().getMethod("visitRequestCharacterEncoding", __signature));
            this.registerVisitor("response-character-encoding", this.getClass().getMethod("visitResponseCharacterEncoding", __signature));
        }
        catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void start(WebAppContext context, Descriptor descriptor) {
        for (FilterHolder filterHolder : context.getServletHandler().getFilters()) {
            this._filterHolderMap.put(filterHolder.getName(), filterHolder);
            this._filterHolders.add(filterHolder);
        }
        if (context.getServletHandler().getFilterMappings() != null) {
            this._filterMappings.addAll(Arrays.asList(context.getServletHandler().getFilterMappings()));
        }
        for (Holder holder : context.getServletHandler().getServlets()) {
            this._servletHolderMap.put(holder.getName(), (ServletHolder)holder);
            this._servletHolders.add((ServletHolder)holder);
        }
        if (context.getServletHandler().getServletMappings() != null) {
            this._servletMappings.addAll(Arrays.asList(context.getServletHandler().getServletMappings()));
        }
    }

    @Override
    public void end(WebAppContext context, Descriptor descriptor) {
        context.getServletHandler().setFilters(this._filterHolders.toArray(new FilterHolder[this._filterHolderMap.size()]));
        context.getServletHandler().setServlets(this._servletHolders.toArray(new ServletHolder[this._servletHolderMap.size()]));
        context.getServletHandler().setFilterMappings(this._filterMappings.toArray(new FilterMapping[this._filterMappings.size()]));
        context.getServletHandler().setServletMappings(this._servletMappings.toArray(new ServletMapping[this._servletMappings.size()]));
        this._filterHolderMap.clear();
        this._filterHolders.clear();
        this._filterMappings.clear();
        this._servletHolderMap.clear();
        this._servletHolders.clear();
        this._servletMappings.clear();
    }

    public void visitContextParam(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        String name = node.getString("param-name", false, true);
        String value = node.getString("param-value", false, true);
        Origin origin = context.getMetaData().getOrigin("context-param." + name);
        switch (origin) {
            case NotSet: {
                context.getInitParams().put(name, value);
                context.getMetaData().setOrigin("context-param." + name, descriptor);
                break;
            }
            case WebXml: 
            case WebDefaults: 
            case WebOverride: {
                if (descriptor instanceof FragmentDescriptor) break;
                context.getInitParams().put(name, value);
                context.getMetaData().setOrigin("context-param." + name, descriptor);
                break;
            }
            case WebFragment: {
                if (!(descriptor instanceof FragmentDescriptor) || context.getInitParams().get(name).equals(value)) break;
                throw new IllegalStateException("Conflicting context-param " + name + "=" + value + " in " + descriptor.getResource());
            }
            default: {
                this.unknownOrigin(origin);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("ContextParam: {}={}", (Object)name, (Object)value);
        }
    }

    public void visitDisplayName(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        if (!(descriptor instanceof FragmentDescriptor)) {
            context.setDisplayName(node.toString(false, true));
            context.getMetaData().setOrigin("display-name", descriptor);
        }
    }

    public void visitServlet(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        XmlParser.Node multipart;
        String enabled;
        String async;
        Origin origin;
        String roleName;
        XmlParser.Node startup;
        String jspFile;
        Origin origin2;
        String id = node.getAttribute("id");
        String name = node.getString("servlet-name", false, true);
        ServletHolder holder = this._servletHolderMap.get(name);
        if (holder == null) {
            holder = context.getServletHandler().newServletHolder(new Source(Source.Origin.DESCRIPTOR, descriptor.getResource().toString()));
            holder.setName(name);
            this._servletHolderMap.put(name, holder);
            this._servletHolders.add(holder);
        }
        Iterator<XmlParser.Node> iParamsIter = node.iterator("init-param");
        block44: while (iParamsIter.hasNext()) {
            XmlParser.Node paramNode = iParamsIter.next();
            String pname = paramNode.getString("param-name", false, true);
            String pvalue = paramNode.getString("param-value", false, true);
            String originName = name + ".servlet.init-param." + pname;
            Descriptor originDescriptor = context.getMetaData().getOriginDescriptor(originName);
            origin2 = context.getMetaData().getOrigin(originName);
            switch (origin2) {
                case NotSet: {
                    holder.setInitParameter(pname, pvalue);
                    context.getMetaData().setOrigin(originName, descriptor);
                    continue block44;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor || descriptor == originDescriptor) continue block44;
                    holder.setInitParameter(pname, pvalue);
                    context.getMetaData().setOrigin(originName, descriptor);
                    continue block44;
                }
                case WebFragment: {
                    if (descriptor == originDescriptor || holder.getInitParameter(pname).equals(pvalue)) continue block44;
                    throw new IllegalStateException("Mismatching init-param " + pname + "=" + pvalue + " in " + descriptor.getResource());
                }
            }
            this.unknownOrigin(origin2);
        }
        String servletClass = node.getString("servlet-class", false, true);
        if ("".equals(servletClass)) {
            servletClass = null;
        }
        if (id != null && id.equals("jsp") && servletClass != null) {
            try {
                Loader.loadClass(servletClass);
            }
            catch (ClassNotFoundException e) {
                LOG.info("NO JSP Support for {}, did not find {}", (Object)context.getContextPath(), (Object)servletClass);
                servletClass = "org.eclipse.jetty.servlet.NoJspServlet";
            }
        }
        if (servletClass != null) {
            ((WebDescriptor)descriptor).addClassName(servletClass);
            Origin origin3 = context.getMetaData().getOrigin(name + ".servlet.servlet-class");
            switch (origin3) {
                case NotSet: {
                    holder.setClassName(servletClass);
                    context.getMetaData().setOrigin(name + ".servlet.servlet-class", descriptor);
                    break;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor) break;
                    holder.setClassName(servletClass);
                    context.getMetaData().setOrigin(name + ".servlet.servlet-class", descriptor);
                    break;
                }
                case WebFragment: {
                    if (servletClass.equals(holder.getClassName())) break;
                    throw new IllegalStateException("Conflicting servlet-class " + servletClass + " in " + descriptor.getResource());
                }
                default: {
                    this.unknownOrigin(origin3);
                }
            }
        }
        if ((jspFile = node.getString("jsp-file", false, true)) != null) {
            holder.setForcedPath(jspFile);
        }
        if ((startup = node.get("load-on-startup")) != null) {
            String s2 = startup.toString(false, true).toLowerCase(Locale.ENGLISH);
            int order = 0;
            if (s2.startsWith("t")) {
                LOG.warn("Deprecated boolean load-on-startup.  Please use integer");
                order = 1;
            } else {
                try {
                    if (s2 != null && s2.trim().length() > 0) {
                        order = Integer.parseInt(s2);
                    }
                }
                catch (Exception e) {
                    LOG.warn("Cannot parse load-on-startup {}. Please use integer", (Object)s2);
                    LOG.trace("IGNORED", e);
                }
            }
            origin2 = context.getMetaData().getOrigin(name + ".servlet.load-on-startup");
            switch (origin2) {
                case NotSet: {
                    holder.setInitOrder(order);
                    context.getMetaData().setOrigin(name + ".servlet.load-on-startup", descriptor);
                    break;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor) break;
                    holder.setInitOrder(order);
                    context.getMetaData().setOrigin(name + ".servlet.load-on-startup", descriptor);
                    break;
                }
                case WebFragment: {
                    if (order == holder.getInitOrder()) break;
                    throw new IllegalStateException("Conflicting load-on-startup value in " + descriptor.getResource());
                }
                default: {
                    this.unknownOrigin(origin2);
                }
            }
        }
        Iterator<XmlParser.Node> sRefsIter = node.iterator("security-role-ref");
        block45: while (sRefsIter.hasNext()) {
            XmlParser.Node securityRef = sRefsIter.next();
            roleName = securityRef.getString("role-name", false, true);
            String roleLink = securityRef.getString("role-link", false, true);
            if (roleName != null && roleName.length() > 0 && roleLink != null && roleLink.length() > 0) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("link role {} to {} for {}", roleName, roleLink, this);
                }
                origin = context.getMetaData().getOrigin(name + ".servlet.role-name." + roleName);
                switch (origin) {
                    case NotSet: {
                        holder.setUserRoleLink(roleName, roleLink);
                        context.getMetaData().setOrigin(name + ".servlet.role-name." + roleName, descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (descriptor instanceof FragmentDescriptor) continue block45;
                        holder.setUserRoleLink(roleName, roleLink);
                        context.getMetaData().setOrigin(name + ".servlet.role-name." + roleName, descriptor);
                        break;
                    }
                    case WebFragment: {
                        if (holder.getUserRoleLink(roleName).equals(roleLink)) continue block45;
                        throw new IllegalStateException("Conflicting role-link for role-name " + roleName + " for servlet " + name + " in " + descriptor.getResource());
                    }
                    default: {
                        this.unknownOrigin(origin);
                        break;
                    }
                }
                continue;
            }
            LOG.warn("Ignored invalid security-role-ref element: servlet-name={}, {}", (Object)holder.getName(), (Object)securityRef);
        }
        XmlParser.Node runAs = node.get("run-as");
        if (runAs != null && (roleName = runAs.getString("role-name", false, true)) != null) {
            Origin origin4 = context.getMetaData().getOrigin(name + ".servlet.run-as");
            switch (origin4) {
                case NotSet: {
                    holder.setRunAsRole(roleName);
                    context.getMetaData().setOrigin(name + ".servlet.run-as", descriptor);
                    break;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor) break;
                    holder.setRunAsRole(roleName);
                    context.getMetaData().setOrigin(name + ".servlet.run-as", descriptor);
                    break;
                }
                case WebFragment: {
                    if (holder.getRunAsRole().equals(roleName)) break;
                    throw new IllegalStateException("Conflicting run-as role " + roleName + " for servlet " + name + " in " + descriptor.getResource());
                }
                default: {
                    this.unknownOrigin(origin4);
                }
            }
        }
        if ((async = node.getString("async-supported", false, true)) != null) {
            boolean val = async.length() == 0 || Boolean.parseBoolean(async);
            origin = context.getMetaData().getOrigin(name + ".servlet.async-supported");
            switch (origin) {
                case NotSet: {
                    holder.setAsyncSupported(val);
                    context.getMetaData().setOrigin(name + ".servlet.async-supported", descriptor);
                    break;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor) break;
                    holder.setAsyncSupported(val);
                    context.getMetaData().setOrigin(name + ".servlet.async-supported", descriptor);
                    break;
                }
                case WebFragment: {
                    if (holder.isAsyncSupported() == val) break;
                    throw new IllegalStateException("Conflicting async-supported=" + async + " for servlet " + name + " in " + descriptor.getResource());
                }
                default: {
                    this.unknownOrigin(origin);
                }
            }
        }
        if ((enabled = node.getString("enabled", false, true)) != null) {
            boolean isEnabled = enabled.length() == 0 || Boolean.parseBoolean(enabled);
            Origin origin5 = context.getMetaData().getOrigin(name + ".servlet.enabled");
            switch (origin5) {
                case NotSet: {
                    holder.setEnabled(isEnabled);
                    context.getMetaData().setOrigin(name + ".servlet.enabled", descriptor);
                    break;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor) break;
                    holder.setEnabled(isEnabled);
                    context.getMetaData().setOrigin(name + ".servlet.enabled", descriptor);
                    break;
                }
                case WebFragment: {
                    if (holder.isEnabled() == isEnabled) break;
                    throw new IllegalStateException("Conflicting value of servlet enabled for servlet " + name + " in " + descriptor.getResource());
                }
                default: {
                    this.unknownOrigin(origin5);
                }
            }
        }
        if ((multipart = node.get("multipart-config")) != null) {
            String location = multipart.getString("location", false, true);
            String maxFile = multipart.getString("max-file-size", false, true);
            String maxRequest = multipart.getString("max-request-size", false, true);
            String threshold = multipart.getString("file-size-threshold", false, true);
            MultipartConfigElement element = new MultipartConfigElement(location, maxFile == null || "".equals(maxFile) ? -1L : Long.parseLong(maxFile), maxRequest == null || "".equals(maxRequest) ? -1L : Long.parseLong(maxRequest), threshold == null || "".equals(threshold) ? 0 : Integer.parseInt(threshold));
            Origin origin6 = context.getMetaData().getOrigin(name + ".servlet.multipart-config");
            switch (origin6) {
                case NotSet: {
                    holder.getRegistration().setMultipartConfig(element);
                    context.getMetaData().setOrigin(name + ".servlet.multipart-config", descriptor);
                    break;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor) break;
                    holder.getRegistration().setMultipartConfig(element);
                    context.getMetaData().setOrigin(name + ".servlet.multipart-config", descriptor);
                    break;
                }
                case WebFragment: {
                    MultipartConfigElement cfg = ((ServletHolder.Registration)holder.getRegistration()).getMultipartConfig();
                    if (cfg.getMaxFileSize() != element.getMaxFileSize()) {
                        throw new IllegalStateException("Conflicting multipart-config max-file-size for servlet " + name + " in " + descriptor.getResource());
                    }
                    if (cfg.getMaxRequestSize() != element.getMaxRequestSize()) {
                        throw new IllegalStateException("Conflicting multipart-config max-request-size for servlet " + name + " in " + descriptor.getResource());
                    }
                    if (cfg.getFileSizeThreshold() != element.getFileSizeThreshold()) {
                        throw new IllegalStateException("Conflicting multipart-config file-size-threshold for servlet " + name + " in " + descriptor.getResource());
                    }
                    if ((cfg.getLocation() == null || element.getLocation() != null && element.getLocation().length() != 0) && (cfg.getLocation() != null || element.getLocation() == null && element.getLocation().length() <= 0)) break;
                    throw new IllegalStateException("Conflicting multipart-config location for servlet " + name + " in " + descriptor.getResource());
                }
                default: {
                    this.unknownOrigin(origin6);
                }
            }
        }
    }

    public void visitServletMapping(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        String servletName = node.getString("servlet-name", false, true);
        Origin origin = context.getMetaData().getOrigin(servletName + ".servlet.mappings");
        switch (origin) {
            case NotSet: {
                context.getMetaData().setOrigin(servletName + ".servlet.mappings", descriptor);
                this.addServletMapping(servletName, node, context, descriptor);
                break;
            }
            case WebXml: 
            case WebDefaults: 
            case WebOverride: {
                if (descriptor instanceof FragmentDescriptor) break;
                this.addServletMapping(servletName, node, context, descriptor);
                break;
            }
            case WebFragment: {
                this.addServletMapping(servletName, node, context, descriptor);
                break;
            }
            default: {
                this.unknownOrigin(origin);
            }
        }
    }

    public void visitSessionConfig(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        XmlParser.Node cookieConfig;
        Iterator<XmlParser.Node> iter;
        if (context.getSessionHandler() == null) {
            return;
        }
        XmlParser.Node tNode = node.get("session-timeout");
        if (tNode != null) {
            long mins = Long.parseLong(tNode.toString(false, true));
            if (TimeUnit.MINUTES.toSeconds(mins) > Integer.MAX_VALUE) {
                throw new IllegalStateException("Max session-timeout in minutes is " + TimeUnit.SECONDS.toMinutes(Integer.MAX_VALUE));
            }
            context.getServletContext().setSessionTimeout((int)mins);
            context.getMetaData().setOrigin("session.timeout", descriptor);
        }
        if ((iter = node.iterator("tracking-mode")).hasNext()) {
            HashSet<SessionTrackingMode> modes = null;
            Origin o = context.getMetaData().getOrigin("session.tracking-modes");
            switch (o) {
                case NotSet: 
                case WebDefaults: {
                    modes = new HashSet<SessionTrackingMode>();
                    context.getMetaData().setOrigin("session.tracking-modes", descriptor);
                    break;
                }
                case WebXml: 
                case WebOverride: 
                case WebFragment: {
                    modes = descriptor instanceof OverrideDescriptor ? new HashSet() : new HashSet<SessionTrackingMode>(context.getSessionHandler().getEffectiveSessionTrackingModes());
                    context.getMetaData().setOrigin("session.tracking-modes", descriptor);
                    break;
                }
                default: {
                    this.unknownOrigin(o);
                }
            }
            while (iter.hasNext()) {
                XmlParser.Node mNode = iter.next();
                String trackMode = mNode.toString(false, true);
                SessionTrackingMode mode = SessionTrackingMode.valueOf(trackMode);
                modes.add(mode);
                context.getMetaData().setOrigin("session.tracking-mode." + mode, descriptor);
            }
            context.getSessionHandler().setSessionTrackingModes(modes);
        }
        if ((cookieConfig = node.get("cookie-config")) != null) {
            Origin origin;
            String comment;
            String path;
            String domain;
            String name = cookieConfig.getString("name", false, true);
            if (name != null) {
                Origin origin2 = context.getMetaData().getOrigin("cookie-config.name");
                switch (origin2) {
                    case NotSet: {
                        context.getSessionHandler().getSessionCookieConfig().setName(name);
                        context.getMetaData().setOrigin("cookie-config.name", descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (descriptor instanceof FragmentDescriptor) break;
                        context.getSessionHandler().getSessionCookieConfig().setName(name);
                        context.getMetaData().setOrigin("cookie-config.name", descriptor);
                        break;
                    }
                    case WebFragment: {
                        if (name.equals(SessionHandler.getSessionCookieName(context.getSessionHandler().getSessionCookieConfig()))) break;
                        throw new IllegalStateException("Conflicting cookie-config name " + name + " in " + descriptor.getResource());
                    }
                    default: {
                        this.unknownOrigin(origin2);
                    }
                }
            }
            if ((domain = cookieConfig.getString("domain", false, true)) != null) {
                Origin origin3 = context.getMetaData().getOrigin("cookie-config.domain");
                switch (origin3) {
                    case NotSet: {
                        context.getSessionHandler().getSessionCookieConfig().setDomain(domain);
                        context.getMetaData().setOrigin("cookie-config.domain", descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (descriptor instanceof FragmentDescriptor) break;
                        context.getSessionHandler().getSessionCookieConfig().setDomain(domain);
                        context.getMetaData().setOrigin("cookie-config.domain", descriptor);
                        break;
                    }
                    case WebFragment: {
                        if (context.getSessionHandler().getSessionCookieConfig().getDomain().equals(domain)) break;
                        throw new IllegalStateException("Conflicting cookie-config domain " + domain + " in " + descriptor.getResource());
                    }
                    default: {
                        this.unknownOrigin(origin3);
                    }
                }
            }
            if ((path = cookieConfig.getString("path", false, true)) != null) {
                Origin origin4 = context.getMetaData().getOrigin("cookie-config.path");
                switch (origin4) {
                    case NotSet: {
                        context.getSessionHandler().getSessionCookieConfig().setPath(path);
                        context.getMetaData().setOrigin("cookie-config.path", descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (descriptor instanceof FragmentDescriptor) break;
                        context.getSessionHandler().getSessionCookieConfig().setPath(path);
                        context.getMetaData().setOrigin("cookie-config.path", descriptor);
                        break;
                    }
                    case WebFragment: {
                        if (path.equals(context.getSessionHandler().getSessionCookieConfig().getPath())) break;
                        throw new IllegalStateException("Conflicting cookie-config path " + path + " in " + descriptor.getResource());
                    }
                    default: {
                        this.unknownOrigin(origin4);
                    }
                }
            }
            if ((comment = cookieConfig.getString("comment", false, true)) != null) {
                Origin origin5 = context.getMetaData().getOrigin("cookie-config.comment");
                switch (origin5) {
                    case NotSet: {
                        context.getSessionHandler().getSessionCookieConfig().setComment(comment);
                        context.getMetaData().setOrigin("cookie-config.comment", descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (descriptor instanceof FragmentDescriptor) break;
                        context.getSessionHandler().getSessionCookieConfig().setComment(comment);
                        context.getMetaData().setOrigin("cookie-config.comment", descriptor);
                        break;
                    }
                    case WebFragment: {
                        if (context.getSessionHandler().getSessionCookieConfig().getComment().equals(comment)) break;
                        throw new IllegalStateException("Conflicting cookie-config comment " + comment + " in " + descriptor.getResource());
                    }
                    default: {
                        this.unknownOrigin(origin5);
                    }
                }
            }
            if ((tNode = cookieConfig.get("http-only")) != null) {
                boolean httpOnly = Boolean.parseBoolean(tNode.toString(false, true));
                origin = context.getMetaData().getOrigin("cookie-config.http-only");
                switch (origin) {
                    case NotSet: {
                        context.getSessionHandler().getSessionCookieConfig().setHttpOnly(httpOnly);
                        context.getMetaData().setOrigin("cookie-config.http-only", descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (descriptor instanceof FragmentDescriptor) break;
                        context.getSessionHandler().getSessionCookieConfig().setHttpOnly(httpOnly);
                        context.getMetaData().setOrigin("cookie-config.http-only", descriptor);
                        break;
                    }
                    case WebFragment: {
                        if (context.getSessionHandler().getSessionCookieConfig().isHttpOnly() == httpOnly) break;
                        throw new IllegalStateException("Conflicting cookie-config http-only " + httpOnly + " in " + descriptor.getResource());
                    }
                    default: {
                        this.unknownOrigin(origin);
                    }
                }
            }
            if ((tNode = cookieConfig.get("secure")) != null) {
                boolean secure = Boolean.parseBoolean(tNode.toString(false, true));
                origin = context.getMetaData().getOrigin("cookie-config.secure");
                switch (origin) {
                    case NotSet: {
                        context.getSessionHandler().getSessionCookieConfig().setSecure(secure);
                        context.getMetaData().setOrigin("cookie-config.secure", descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (descriptor instanceof FragmentDescriptor) break;
                        context.getSessionHandler().getSessionCookieConfig().setSecure(secure);
                        context.getMetaData().setOrigin("cookie-config.secure", descriptor);
                        break;
                    }
                    case WebFragment: {
                        if (context.getSessionHandler().getSessionCookieConfig().isSecure() == secure) break;
                        throw new IllegalStateException("Conflicting cookie-config secure " + secure + " in " + descriptor.getResource());
                    }
                    default: {
                        this.unknownOrigin(origin);
                    }
                }
            }
            if ((tNode = cookieConfig.get("max-age")) != null) {
                int maxAge = Integer.parseInt(tNode.toString(false, true));
                origin = context.getMetaData().getOrigin("cookie-config.max-age");
                switch (origin) {
                    case NotSet: {
                        context.getSessionHandler().getSessionCookieConfig().setMaxAge(maxAge);
                        context.getMetaData().setOrigin("cookie-config.max-age", descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (descriptor instanceof FragmentDescriptor) break;
                        context.getSessionHandler().getSessionCookieConfig().setMaxAge(maxAge);
                        context.getMetaData().setOrigin("cookie-config.max-age", descriptor);
                        break;
                    }
                    case WebFragment: {
                        if (context.getSessionHandler().getSessionCookieConfig().getMaxAge() == maxAge) break;
                        throw new IllegalStateException("Conflicting cookie-config max-age " + maxAge + " in " + descriptor.getResource());
                    }
                    default: {
                        this.unknownOrigin(origin);
                    }
                }
            }
        }
    }

    public void visitMimeMapping(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        String extension = node.getString("extension", false, true);
        if (extension != null && extension.startsWith(".")) {
            extension = extension.substring(1);
        }
        String mimeType = node.getString("mime-type", false, true);
        if (extension != null) {
            Origin origin = context.getMetaData().getOrigin("extension." + extension);
            switch (origin) {
                case NotSet: {
                    context.getMimeTypes().addMimeMapping(extension, mimeType);
                    context.getMetaData().setOrigin("extension." + extension, descriptor);
                    break;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor) break;
                    context.getMimeTypes().addMimeMapping(extension, mimeType);
                    context.getMetaData().setOrigin("extension." + extension, descriptor);
                    break;
                }
                case WebFragment: {
                    if (context.getMimeTypes().getMimeByExtension("." + extension).equals(mimeType)) break;
                    throw new IllegalStateException("Conflicting mime-type " + mimeType + " for extension " + extension + " in " + descriptor.getResource());
                }
                default: {
                    this.unknownOrigin(origin);
                }
            }
        }
    }

    public void visitWelcomeFileList(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        Origin origin = context.getMetaData().getOrigin("welcome-file-list");
        switch (origin) {
            case NotSet: {
                context.getMetaData().setOrigin("welcome-file-list", descriptor);
                this.addWelcomeFiles(context, node, descriptor);
                break;
            }
            case WebXml: {
                this.addWelcomeFiles(context, node, descriptor);
                break;
            }
            case WebDefaults: {
                if (!(descriptor instanceof DefaultsDescriptor || descriptor instanceof OverrideDescriptor || descriptor instanceof FragmentDescriptor)) {
                    context.setWelcomeFiles(new String[0]);
                }
                this.addWelcomeFiles(context, node, descriptor);
                break;
            }
            case WebOverride: {
                this.addWelcomeFiles(context, node, descriptor);
                break;
            }
            case WebFragment: {
                this.addWelcomeFiles(context, node, descriptor);
                break;
            }
            default: {
                this.unknownOrigin(origin);
            }
        }
    }

    public void visitLocaleEncodingList(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        Iterator<XmlParser.Node> iter = node.iterator("locale-encoding-mapping");
        block5: while (iter.hasNext()) {
            XmlParser.Node mapping = iter.next();
            String locale = mapping.getString("locale", false, true);
            String encoding = mapping.getString("encoding", false, true);
            if (encoding == null) continue;
            Origin origin = context.getMetaData().getOrigin("locale-encoding." + locale);
            switch (origin) {
                case NotSet: {
                    context.addLocaleEncoding(locale, encoding);
                    context.getMetaData().setOrigin("locale-encoding." + locale, descriptor);
                    continue block5;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor) continue block5;
                    context.addLocaleEncoding(locale, encoding);
                    context.getMetaData().setOrigin("locale-encoding." + locale, descriptor);
                    continue block5;
                }
                case WebFragment: {
                    if (encoding.equals(context.getLocaleEncoding(locale))) continue block5;
                    throw new IllegalStateException("Conflicting loacle-encoding mapping for locale " + locale + " in " + descriptor.getResource());
                }
            }
            this.unknownOrigin(origin);
        }
    }

    public void visitErrorPage(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        String location;
        String error = node.getString("error-code", false, true);
        int code = 0;
        if (error == null || error.length() == 0) {
            error = node.getString("exception-type", false, true);
            if (error == null || error.length() == 0) {
                error = "org.eclipse.jetty.server.error_page.global";
            }
        } else {
            code = Integer.parseInt(error);
        }
        if (!(location = node.getString("location", false, true)).startsWith("/")) {
            throw new IllegalStateException("Missing leading '/' for location: " + location);
        }
        ErrorPageErrorHandler handler = (ErrorPageErrorHandler)context.getErrorHandler();
        String originName = "error." + error;
        Origin origin = context.getMetaData().getOrigin(originName);
        switch (origin) {
            case NotSet: {
                if (code > 0) {
                    handler.addErrorPage(code, location);
                } else {
                    handler.addErrorPage(error, location);
                }
                context.getMetaData().setOrigin("error." + error, descriptor);
                break;
            }
            case WebXml: 
            case WebDefaults: 
            case WebOverride: {
                if (descriptor instanceof FragmentDescriptor) break;
                Descriptor originDescriptor = context.getMetaData().getOriginDescriptor(originName);
                if (descriptor == originDescriptor) {
                    throw new IllegalStateException("Duplicate error-page " + error + " at " + location);
                }
                if (code > 0) {
                    handler.addErrorPage(code, location);
                } else {
                    handler.addErrorPage(error, location);
                }
                context.getMetaData().setOrigin("error." + error, descriptor);
                break;
            }
            case WebFragment: {
                if (handler.getErrorPages().get(error).equals(location)) break;
                throw new IllegalStateException("Conflicting error-code or exception-type " + error + " in " + descriptor.getResource());
            }
            default: {
                this.unknownOrigin(origin);
            }
        }
    }

    public void addWelcomeFiles(WebAppContext context, XmlParser.Node node, Descriptor descriptor) {
        Iterator<XmlParser.Node> iter = node.iterator("welcome-file");
        while (iter.hasNext()) {
            XmlParser.Node indexNode = iter.next();
            String welcome = indexNode.toString(false, true);
            context.getMetaData().setOrigin("welcome-file." + welcome, descriptor);
            if (welcome == null || welcome.trim().length() <= 0) continue;
            context.setWelcomeFiles(ArrayUtil.addToArray(context.getWelcomeFiles(), welcome, String.class));
        }
    }

    public ServletMapping addServletMapping(String servletName, XmlParser.Node node, WebAppContext context, Descriptor descriptor) {
        ServletMapping mapping = new ServletMapping(new Source(Source.Origin.DESCRIPTOR, descriptor.getResource().toString()));
        mapping.setServletName(servletName);
        mapping.setFromDefaultDescriptor(descriptor instanceof DefaultsDescriptor);
        context.getMetaData().setOrigin(servletName + ".servlet.mapping." + Long.toHexString(mapping.hashCode()), descriptor);
        ArrayList<String> paths = new ArrayList<String>();
        Iterator<XmlParser.Node> iter = node.iterator("url-pattern");
        while (iter.hasNext()) {
            String p = iter.next().toString(false, true);
            p = ServletPathSpec.normalize(p);
            ListIterator<ServletMapping> listItor = this._servletMappings.listIterator();
            boolean found = false;
            block1: while (listItor.hasNext() && !found) {
                ServletMapping sm = listItor.next();
                if (sm.getPathSpecs() == null) continue;
                for (String ps : sm.getPathSpecs()) {
                    String[] updatedPaths;
                    if (!p.equals(ps) || !sm.isFromDefaultDescriptor() && !servletName.equals(sm.getServletName())) continue;
                    if (sm.isFromDefaultDescriptor()) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("{} in mapping {} from defaults descriptor is overridden by {}", ps, sm, servletName);
                        }
                    } else {
                        LOG.warn("Duplicate mapping from {} to {}", (Object)p, (Object)servletName);
                    }
                    if ((updatedPaths = ArrayUtil.removeFromArray(sm.getPathSpecs(), ps)) == null || updatedPaths.length == 0) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Removed empty mapping {}", (Object)sm);
                        }
                        listItor.remove();
                    } else {
                        sm.setPathSpecs(updatedPaths);
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Removed path {} from mapping {}", (Object)p, (Object)sm);
                        }
                    }
                    found = true;
                    continue block1;
                }
            }
            paths.add(p);
            context.getMetaData().setOrigin(servletName + ".servlet.mapping.url" + p, descriptor);
        }
        mapping.setPathSpecs(paths.toArray(new String[paths.size()]));
        if (LOG.isDebugEnabled()) {
            LOG.debug("Added mapping {} ", (Object)mapping);
        }
        this._servletMappings.add(mapping);
        return mapping;
    }

    public void addFilterMapping(String filterName, XmlParser.Node node, WebAppContext context, Descriptor descriptor) {
        FilterMapping mapping = new FilterMapping();
        mapping.setFilterName(filterName);
        context.getMetaData().setOrigin(filterName + ".filter.mapping." + Long.toHexString(mapping.hashCode()), descriptor);
        ArrayList<String> paths = new ArrayList<String>();
        Iterator<XmlParser.Node> iter = node.iterator("url-pattern");
        while (iter.hasNext()) {
            String p = iter.next().toString(false, true);
            p = ServletPathSpec.normalize(p);
            paths.add(p);
            context.getMetaData().setOrigin(filterName + ".filter.mapping.url" + p, descriptor);
        }
        mapping.setPathSpecs(paths.toArray(new String[paths.size()]));
        ArrayList<String> names = new ArrayList<String>();
        iter = node.iterator("servlet-name");
        while (iter.hasNext()) {
            String n = iter.next().toString(false, true);
            context.getMetaData().setOrigin(filterName + ".filter.mapping.servlet" + n, descriptor);
            names.add(n);
        }
        mapping.setServletNames(names.toArray(new String[names.size()]));
        ArrayList<DispatcherType> dispatches = new ArrayList<DispatcherType>();
        iter = node.iterator("dispatcher");
        while (iter.hasNext()) {
            String d = iter.next().toString(false, true);
            dispatches.add(FilterMapping.dispatch(d));
        }
        if (dispatches.size() > 0) {
            mapping.setDispatcherTypes(EnumSet.copyOf(dispatches));
        }
        this._filterMappings.add(mapping);
    }

    public void visitTagLib(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        String uri = node.getString("taglib-uri", false, true);
        String location = node.getString("taglib-location", false, true);
        context.setResourceAlias(uri, location);
        ServletContextHandler.JspConfig config = (ServletContextHandler.JspConfig)context.getServletContext().getJspConfigDescriptor();
        if (config == null) {
            config = new ServletContextHandler.JspConfig();
            context.getServletContext().setJspConfigDescriptor(config);
        }
        ServletContextHandler.TagLib tl = new ServletContextHandler.TagLib();
        tl.setTaglibLocation(location);
        tl.setTaglibURI(uri);
        config.addTaglibDescriptor(tl);
    }

    public void visitJspConfig(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        ServletContextHandler.JspConfig config = (ServletContextHandler.JspConfig)context.getServletContext().getJspConfigDescriptor();
        if (config == null) {
            config = new ServletContextHandler.JspConfig();
            context.getServletContext().setJspConfigDescriptor(config);
        }
        for (int i = 0; i < node.size(); ++i) {
            Object o = node.get(i);
            if (!(o instanceof XmlParser.Node) || !"taglib".equals(((XmlParser.Node)o).getTag())) continue;
            this.visitTagLib(context, descriptor, (XmlParser.Node)o);
        }
        Iterator<XmlParser.Node> iter = node.iterator("jsp-property-group");
        ArrayList<String> paths = new ArrayList<String>();
        while (iter.hasNext()) {
            ServletContextHandler.JspPropertyGroup jpg = new ServletContextHandler.JspPropertyGroup();
            config.addJspPropertyGroup(jpg);
            XmlParser.Node group = iter.next();
            Iterator<XmlParser.Node> iter2 = group.iterator("url-pattern");
            while (iter2.hasNext()) {
                String url2 = iter2.next().toString(false, true);
                url2 = ServletPathSpec.normalize(url2);
                paths.add(url2);
                jpg.addUrlPattern(url2);
            }
            jpg.setElIgnored(group.getString("el-ignored", false, true));
            jpg.setPageEncoding(group.getString("page-encoding", false, true));
            jpg.setScriptingInvalid(group.getString("scripting-invalid", false, true));
            jpg.setIsXml(group.getString("is-xml", false, true));
            jpg.setDeferredSyntaxAllowedAsLiteral(group.getString("deferred-syntax-allowed-as-literal", false, true));
            jpg.setTrimDirectiveWhitespaces(group.getString("trim-directive-whitespaces", false, true));
            jpg.setDefaultContentType(group.getString("default-content-type", false, true));
            jpg.setBuffer(group.getString("buffer", false, true));
            jpg.setErrorOnUndeclaredNamespace(group.getString("error-on-undeclared-namespace", false, true));
            Iterator<XmlParser.Node> preludes = group.iterator("include-prelude");
            while (preludes.hasNext()) {
                String prelude = preludes.next().toString(false, true);
                jpg.addIncludePrelude(prelude);
            }
            Iterator<XmlParser.Node> codas = group.iterator("include-coda");
            while (codas.hasNext()) {
                String coda = codas.next().toString(false, true);
                jpg.addIncludeCoda(coda);
            }
            if (!LOG.isDebugEnabled()) continue;
            LOG.debug(config.toString());
        }
        if (paths.size() > 0) {
            ServletMapping jspMapping = null;
            for (ServletMapping m4 : this._servletMappings) {
                if (!m4.getServletName().equals("jsp")) continue;
                jspMapping = m4;
                break;
            }
            if (jspMapping != null) {
                if (jspMapping.getPathSpecs() == null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Adding all paths from jsp-config to jsp servlet mapping");
                    }
                    jspMapping.setPathSpecs(paths.toArray(new String[paths.size()]));
                } else {
                    ListIterator piterator = paths.listIterator();
                    while (piterator.hasNext()) {
                        String p = (String)piterator.next();
                        if (!jspMapping.containsPathSpec(p)) continue;
                        piterator.remove();
                    }
                    if (paths.size() > 0) {
                        for (String p : jspMapping.getPathSpecs()) {
                            paths.add(p);
                        }
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Adding extra paths from jsp-config to jsp servlet mapping");
                        }
                        jspMapping.setPathSpecs(paths.toArray(new String[paths.size()]));
                    }
                }
            } else {
                ServletMapping mapping = new ServletMapping(new Source(Source.Origin.DESCRIPTOR, descriptor.getResource().toString()));
                mapping.setServletName("jsp");
                mapping.setPathSpecs(paths.toArray(new String[paths.size()]));
                this._servletMappings.add(mapping);
            }
        }
    }

    public void visitSecurityConstraint(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        if (context.getSecurityHandler() == null) {
            LOG.warn("security-constraint declared but SecurityHandler==null");
            return;
        }
        Constraint scBase = new Constraint();
        try {
            XmlParser.Node data;
            XmlParser.Node auths = node.get("auth-constraint");
            if (auths != null) {
                scBase.setAuthenticate(true);
                Iterator<XmlParser.Node> iter = auths.iterator("role-name");
                ArrayList<String> roles = new ArrayList<String>();
                while (iter.hasNext()) {
                    String role = iter.next().toString(false, true);
                    roles.add(role);
                }
                scBase.setRoles(roles.toArray(new String[roles.size()]));
            }
            if ((data = node.get("user-data-constraint")) != null) {
                String guarantee = (data = data.get("transport-guarantee")).toString(false, true).toUpperCase(Locale.ENGLISH);
                if (guarantee == null || guarantee.length() == 0 || "NONE".equals(guarantee)) {
                    scBase.setDataConstraint(0);
                } else if ("INTEGRAL".equals(guarantee)) {
                    scBase.setDataConstraint(1);
                } else if ("CONFIDENTIAL".equals(guarantee)) {
                    scBase.setDataConstraint(2);
                } else {
                    LOG.warn("Unknown user-data-constraint: {}", (Object)guarantee);
                    scBase.setDataConstraint(2);
                }
            }
            Iterator<XmlParser.Node> iter = node.iterator("web-resource-collection");
            while (iter.hasNext()) {
                XmlParser.Node collection = iter.next();
                String name = collection.getString("web-resource-name", false, true);
                Constraint sc = (Constraint)scBase.clone();
                sc.setName(name);
                Iterator<XmlParser.Node> iter2 = collection.iterator("url-pattern");
                while (iter2.hasNext()) {
                    ConstraintMapping mapping;
                    String method2;
                    String url2 = iter2.next().toString(false, true);
                    url2 = ServletPathSpec.normalize(url2);
                    context.getMetaData().setOrigin("constraint.url." + url2, descriptor);
                    Iterator<XmlParser.Node> methods = collection.iterator("http-method");
                    Iterator<XmlParser.Node> ommissions = collection.iterator("http-method-omission");
                    if (methods.hasNext()) {
                        if (ommissions.hasNext()) {
                            throw new IllegalStateException("web-resource-collection cannot contain both http-method and http-method-omission");
                        }
                        while (methods.hasNext()) {
                            method2 = methods.next().toString(false, true);
                            mapping = new ConstraintMapping();
                            mapping.setMethod(method2);
                            mapping.setPathSpec(url2);
                            mapping.setConstraint(sc);
                            ((ConstraintAware)((Object)context.getSecurityHandler())).addConstraintMapping(mapping);
                        }
                        continue;
                    }
                    if (ommissions.hasNext()) {
                        while (ommissions.hasNext()) {
                            method2 = ommissions.next().toString(false, true);
                            mapping = new ConstraintMapping();
                            mapping.setMethodOmissions(new String[]{method2});
                            mapping.setPathSpec(url2);
                            mapping.setConstraint(sc);
                            ((ConstraintAware)((Object)context.getSecurityHandler())).addConstraintMapping(mapping);
                        }
                        continue;
                    }
                    ConstraintMapping mapping2 = new ConstraintMapping();
                    mapping2.setPathSpec(url2);
                    mapping2.setConstraint(sc);
                    ((ConstraintAware)((Object)context.getSecurityHandler())).addConstraintMapping(mapping2);
                }
            }
        }
        catch (CloneNotSupportedException e) {
            LOG.warn("Unable to clone {}", (Object)scBase, (Object)e);
        }
    }

    public void visitLoginConfig(WebAppContext context, Descriptor descriptor, XmlParser.Node node) throws Exception {
        block24: {
            block25: {
                XmlParser.Node method2 = node.get("auth-method");
                if (method2 == null) break block24;
                Origin origin = context.getMetaData().getOrigin("auth-method");
                switch (origin) {
                    case NotSet: {
                        context.getSecurityHandler().setAuthMethod(method2.toString(false, true));
                        context.getMetaData().setOrigin("auth-method", descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (descriptor instanceof FragmentDescriptor) break;
                        context.getSecurityHandler().setAuthMethod(method2.toString(false, true));
                        context.getMetaData().setOrigin("auth-method", descriptor);
                        break;
                    }
                    case WebFragment: {
                        if (context.getSecurityHandler().getAuthMethod().equals(method2.toString(false, true))) break;
                        throw new IllegalStateException("Conflicting auth-method value in " + descriptor.getResource());
                    }
                    default: {
                        this.unknownOrigin(origin);
                    }
                }
                XmlParser.Node name = node.get("realm-name");
                String nameStr = name == null ? "default" : name.toString(false, true);
                Origin originRealmName = context.getMetaData().getOrigin("realm-name");
                switch (originRealmName) {
                    case NotSet: {
                        context.getSecurityHandler().setRealmName(nameStr);
                        context.getMetaData().setOrigin("realm-name", descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (descriptor instanceof FragmentDescriptor) break;
                        context.getSecurityHandler().setRealmName(nameStr);
                        context.getMetaData().setOrigin("realm-name", descriptor);
                        break;
                    }
                    case WebFragment: {
                        if (context.getSecurityHandler().getRealmName().equals(nameStr)) break;
                        throw new IllegalStateException("Conflicting realm-name value in " + descriptor.getResource());
                    }
                    default: {
                        this.unknownOrigin(originRealmName);
                    }
                }
                if (!"FORM".equalsIgnoreCase(context.getSecurityHandler().getAuthMethod())) break block24;
                XmlParser.Node formConfig = node.get("form-login-config");
                if (formConfig == null) break block25;
                String loginPageName = null;
                XmlParser.Node loginPage = formConfig.get("form-login-page");
                if (loginPage != null) {
                    loginPageName = loginPage.toString(false, true);
                }
                String errorPageName = null;
                XmlParser.Node errorPage = formConfig.get("form-error-page");
                if (errorPage != null) {
                    errorPageName = errorPage.toString(false, true);
                }
                Origin originFormLoginPage = context.getMetaData().getOrigin("form-login-page");
                switch (originFormLoginPage) {
                    case NotSet: {
                        context.getSecurityHandler().setInitParameter("org.eclipse.jetty.security.form_login_page", loginPageName);
                        context.getMetaData().setOrigin("form-login-page", descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (descriptor instanceof FragmentDescriptor) break;
                        context.getSecurityHandler().setInitParameter("org.eclipse.jetty.security.form_login_page", loginPageName);
                        context.getMetaData().setOrigin("form-login-page", descriptor);
                        break;
                    }
                    case WebFragment: {
                        if (context.getSecurityHandler().getInitParameter("org.eclipse.jetty.security.form_login_page").equals(loginPageName)) break;
                        throw new IllegalStateException("Conflicting form-login-page value in " + descriptor.getResource());
                    }
                    default: {
                        this.unknownOrigin(originFormLoginPage);
                    }
                }
                Origin originFormErrorPage = context.getMetaData().getOrigin("form-error-page");
                switch (originFormErrorPage) {
                    case NotSet: {
                        context.getSecurityHandler().setInitParameter("org.eclipse.jetty.security.form_error_page", errorPageName);
                        context.getMetaData().setOrigin("form-error-page", descriptor);
                        break;
                    }
                    case WebXml: 
                    case WebDefaults: 
                    case WebOverride: {
                        if (!(descriptor instanceof FragmentDescriptor)) {
                            context.getSecurityHandler().setInitParameter("org.eclipse.jetty.security.form_error_page", errorPageName);
                            context.getMetaData().setOrigin("form-error-page", descriptor);
                            break;
                        }
                        break block24;
                    }
                    case WebFragment: {
                        if (!context.getSecurityHandler().getInitParameter("org.eclipse.jetty.security.form_error_page").equals(errorPageName)) {
                            throw new IllegalStateException("Conflicting form-error-page value in " + descriptor.getResource());
                        }
                        break block24;
                    }
                    default: {
                        this.unknownOrigin(originFormErrorPage);
                        break;
                    }
                }
                break block24;
            }
            throw new IllegalStateException("!form-login-config");
        }
    }

    public void visitSecurityRole(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        if (context.getSecurityHandler() == null) {
            LOG.warn("security-role declared but SecurityHandler==null");
            return;
        }
        XmlParser.Node roleNode = node.get("role-name");
        String role = roleNode.toString(false, true);
        ((ConstraintAware)((Object)context.getSecurityHandler())).addRole(role);
        context.getMetaData().setOrigin("security-role." + role, descriptor);
    }

    public void visitFilter(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        String filterClass;
        String name = node.getString("filter-name", false, true);
        FilterHolder holder = this._filterHolderMap.get(name);
        if (holder == null) {
            holder = context.getServletHandler().newFilterHolder(new Source(Source.Origin.DESCRIPTOR, descriptor.getResource().toString()));
            holder.setName(name);
            this._filterHolderMap.put(name, holder);
            this._filterHolders.add(holder);
        }
        if ((filterClass = node.getString("filter-class", false, true)) != null) {
            ((WebDescriptor)descriptor).addClassName(filterClass);
            Origin origin = context.getMetaData().getOrigin(name + ".filter.filter-class");
            switch (origin) {
                case NotSet: {
                    holder.setClassName(filterClass);
                    context.getMetaData().setOrigin(name + ".filter.filter-class", descriptor);
                    break;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor) break;
                    holder.setClassName(filterClass);
                    context.getMetaData().setOrigin(name + ".filter.filter-class", descriptor);
                    break;
                }
                case WebFragment: {
                    if (holder.getClassName().equals(filterClass)) break;
                    throw new IllegalStateException("Conflicting filter-class for filter " + name + " in " + descriptor.getResource());
                }
                default: {
                    this.unknownOrigin(origin);
                }
            }
        }
        Iterator<XmlParser.Node> iter = node.iterator("init-param");
        block15: while (iter.hasNext()) {
            XmlParser.Node paramNode = iter.next();
            String pname = paramNode.getString("param-name", false, true);
            String pvalue = paramNode.getString("param-value", false, true);
            Origin origin = context.getMetaData().getOrigin(name + ".filter.init-param." + pname);
            switch (origin) {
                case NotSet: {
                    holder.setInitParameter(pname, pvalue);
                    context.getMetaData().setOrigin(name + ".filter.init-param." + pname, descriptor);
                    continue block15;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor) continue block15;
                    holder.setInitParameter(pname, pvalue);
                    context.getMetaData().setOrigin(name + ".filter.init-param." + pname, descriptor);
                    continue block15;
                }
                case WebFragment: {
                    if (holder.getInitParameter(pname).equals(pvalue)) continue block15;
                    throw new IllegalStateException("Mismatching init-param " + pname + "=" + pvalue + " in " + descriptor.getResource());
                }
            }
            this.unknownOrigin(origin);
        }
        String async = node.getString("async-supported", false, true);
        if (async != null) {
            holder.setAsyncSupported(async.length() == 0 || Boolean.parseBoolean(async));
        }
        if (async != null) {
            boolean val = async.length() == 0 || Boolean.parseBoolean(async);
            Origin origin = context.getMetaData().getOrigin(name + ".filter.async-supported");
            switch (origin) {
                case NotSet: {
                    holder.setAsyncSupported(val);
                    context.getMetaData().setOrigin(name + ".filter.async-supported", descriptor);
                    break;
                }
                case WebXml: 
                case WebDefaults: 
                case WebOverride: {
                    if (descriptor instanceof FragmentDescriptor) break;
                    holder.setAsyncSupported(val);
                    context.getMetaData().setOrigin(name + ".filter.async-supported", descriptor);
                    break;
                }
                case WebFragment: {
                    if (holder.isAsyncSupported() == val) break;
                    throw new IllegalStateException("Conflicting async-supported=" + async + " for filter " + name + " in " + descriptor.getResource());
                }
                default: {
                    this.unknownOrigin(origin);
                }
            }
        }
    }

    public void visitFilterMapping(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        String filterName = node.getString("filter-name", false, true);
        Origin origin = context.getMetaData().getOrigin(filterName + ".filter.mappings");
        switch (origin) {
            case NotSet: {
                context.getMetaData().setOrigin(filterName + ".filter.mappings", descriptor);
                this.addFilterMapping(filterName, node, context, descriptor);
                break;
            }
            case WebXml: 
            case WebDefaults: 
            case WebOverride: {
                if (descriptor instanceof FragmentDescriptor) break;
                this.addFilterMapping(filterName, node, context, descriptor);
                break;
            }
            case WebFragment: {
                this.addFilterMapping(filterName, node, context, descriptor);
                break;
            }
            default: {
                this.unknownOrigin(origin);
            }
        }
    }

    public void visitListener(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        String className = node.getString("listener-class", false, true);
        try {
            if (className != null && className.length() > 0) {
                for (ListenerHolder holder : context.getServletHandler().getListeners()) {
                    if (!holder.getClassName().equals(className)) continue;
                    return;
                }
                ((WebDescriptor)descriptor).addClassName(className);
                ListenerHolder h2 = context.getServletHandler().newListenerHolder(new Source(Source.Origin.DESCRIPTOR, descriptor.getResource().toString()));
                h2.setClassName(className);
                context.getServletHandler().addListener(h2);
                context.getMetaData().setOrigin(className + ".listener", descriptor);
            }
        }
        catch (Exception e) {
            LOG.warn("Could not instantiate listener {}", (Object)className, (Object)e);
            return;
        }
    }

    public void visitDenyUncoveredHttpMethods(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        if (context.getSecurityHandler() == null) {
            LOG.warn("deny-uncovered-http-methods declared but SecurityHandler==null");
            return;
        }
        ((ConstraintAware)((Object)context.getSecurityHandler())).setDenyUncoveredHttpMethods(true);
    }

    public void visitDefaultContextPath(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        if (!(descriptor instanceof FragmentDescriptor)) {
            String path = node.toString(false, true);
            context.setAttribute("default-context-path", path);
            if (context.isContextPathDefault()) {
                context.setDefaultContextPath(path);
                context.getMetaData().setOrigin("default-context-path", descriptor);
            }
        }
    }

    public void visitRequestCharacterEncoding(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        if (!(descriptor instanceof FragmentDescriptor)) {
            String encoding = node.toString(false, true);
            context.setAttribute("request-character-encoding", encoding);
            context.setDefaultRequestCharacterEncoding(encoding);
            context.getMetaData().setOrigin("request-character-encoding", descriptor);
        }
    }

    public void visitResponseCharacterEncoding(WebAppContext context, Descriptor descriptor, XmlParser.Node node) {
        if (!(descriptor instanceof FragmentDescriptor)) {
            String encoding = node.toString(false, true);
            context.setAttribute("response-character-encoding", encoding);
            context.setDefaultResponseCharacterEncoding(encoding);
            context.getMetaData().setOrigin("response-character-encoding", descriptor);
        }
    }

    private void unknownOrigin(Origin origin) {
        LOG.warn("Unknown descriptor origin {}", (Object)origin, (Object)new Throwable());
    }
}

