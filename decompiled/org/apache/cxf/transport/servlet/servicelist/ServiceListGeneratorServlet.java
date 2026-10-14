/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletConfig
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.apache.cxf.transport.servlet.servicelist;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.AbstractDestination;
import org.apache.cxf.transport.http.DestinationRegistry;
import org.apache.cxf.transport.servlet.servicelist.FormattedServiceListWriter;
import org.apache.cxf.transport.servlet.servicelist.ServiceListJAASAuthenticator;
import org.apache.cxf.transport.servlet.servicelist.ServiceListWriter;
import org.apache.cxf.transport.servlet.servicelist.UnformattedServiceListWriter;

public class ServiceListGeneratorServlet
extends HttpServlet {
    private static final long serialVersionUID = -113918058557537996L;
    private DestinationRegistry destinationRegistry;
    private Bus bus;
    private String serviceListStyleSheet;
    private String title;
    private boolean showForeignContexts = true;

    public ServiceListGeneratorServlet(DestinationRegistry destinationRegistry, Bus bus) {
        this.destinationRegistry = destinationRegistry;
        this.bus = bus;
        if (this.bus == null) {
            this.bus = BusFactory.getDefaultBus(false);
        }
        this.title = "CXF - Service list";
    }

    public void setServiceListStyleSheet(String serviceListStyleSheet) {
        this.serviceListStyleSheet = serviceListStyleSheet;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceListWriter serviceListWriter;
        Object obj = request.getAttribute("auth.service.list");
        boolean isAuthServiceList = false;
        if (obj != null) {
            isAuthServiceList = Boolean.valueOf(obj.toString());
        }
        if (isAuthServiceList) {
            String authServiceListRealm = (String)request.getAttribute("auth.service.list.realm");
            ServiceListJAASAuthenticator authenticator = new ServiceListJAASAuthenticator();
            authenticator.setRealm(authServiceListRealm);
            if (!authenticator.authenticate(request, response)) {
                return;
            }
            request.removeAttribute("auth.service.list");
            request.removeAttribute("auth.service.list.realm");
        }
        AbstractDestination[] destinations = this.destinationRegistry.getSortedDestinations();
        if (request.getParameter("stylesheet") != null) {
            this.renderStyleSheet(request, response);
            return;
        }
        if (this.bus == null) {
            this.bus = BusFactory.getDefaultBus(false);
        }
        List privateEndpoints = this.bus != null ? (List)this.bus.getProperty("org.apache.cxf.private.endpoints") : new ArrayList();
        AbstractDestination[] soapEndpoints = this.getSOAPEndpoints(destinations, privateEndpoints);
        AbstractDestination[] restEndpoints = this.getRestEndpoints(destinations, privateEndpoints);
        if ("false".equals(request.getParameter("formatted"))) {
            boolean renderWsdlList = "true".equals(request.getParameter("wsdlList"));
            serviceListWriter = new UnformattedServiceListWriter(renderWsdlList, this.bus);
        } else {
            String styleSheetPath;
            if (this.serviceListStyleSheet != null) {
                styleSheetPath = request.getContextPath() + "/" + this.serviceListStyleSheet;
            } else {
                String pathInfo;
                String servletPath;
                styleSheetPath = "";
                String contextPath = request.getContextPath();
                if (contextPath != null) {
                    styleSheetPath = styleSheetPath + contextPath;
                }
                if ((servletPath = request.getServletPath()) != null) {
                    styleSheetPath = styleSheetPath + servletPath;
                }
                if ((pathInfo = request.getPathInfo()) != null) {
                    styleSheetPath = styleSheetPath + pathInfo;
                }
                if (!styleSheetPath.endsWith("/")) {
                    styleSheetPath = styleSheetPath + "/";
                }
                styleSheetPath = styleSheetPath + "?stylesheet=1";
            }
            serviceListWriter = new FormattedServiceListWriter(styleSheetPath, this.title, this.showForeignContexts, this.bus);
        }
        response.setContentType(serviceListWriter.getContentType());
        Object basePath = request.getAttribute(Message.BASE_PATH);
        serviceListWriter.writeServiceList(response.getWriter(), basePath == null ? null : basePath.toString(), soapEndpoints, restEndpoints);
    }

    private boolean isPrivate(EndpointInfo ei, List<String> privateEndpoints) {
        if (privateEndpoints != null) {
            for (String s2 : privateEndpoints) {
                if (!ei.getAddress().endsWith(s2)) continue;
                return true;
            }
        }
        return false;
    }

    private AbstractDestination[] getSOAPEndpoints(AbstractDestination[] destinations, List<String> privateEndpoints) {
        ArrayList<AbstractDestination> soapEndpoints = new ArrayList<AbstractDestination>();
        for (AbstractDestination sd : destinations) {
            if (null == sd.getEndpointInfo().getName() || null == sd.getEndpointInfo().getInterface() || this.isPrivate(sd.getEndpointInfo(), privateEndpoints)) continue;
            soapEndpoints.add(sd);
        }
        return soapEndpoints.toArray(new AbstractDestination[0]);
    }

    private AbstractDestination[] getRestEndpoints(AbstractDestination[] destinations, List<String> privateEndpoints) {
        ArrayList<AbstractDestination> restfulDests = new ArrayList<AbstractDestination>();
        for (AbstractDestination sd : destinations) {
            if (null != sd.getEndpointInfo().getInterface() || this.isPrivate(sd.getEndpointInfo(), privateEndpoints)) continue;
            restfulDests.add(sd);
        }
        return restfulDests.toArray(new AbstractDestination[0]);
    }

    private void renderStyleSheet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/css; charset=UTF-8");
        URL url2 = ((Object)((Object)this)).getClass().getResource("servicelist.css");
        if (url2 != null) {
            IOUtils.copy(url2.openStream(), (OutputStream)response.getOutputStream());
        }
    }

    public void init(ServletConfig servletConfig) {
        String showAllContexts;
        String configTitle;
        String configServiceListStyleSheet = servletConfig.getInitParameter("service-list-stylesheet");
        if (!StringUtils.isEmpty(configServiceListStyleSheet)) {
            this.serviceListStyleSheet = configServiceListStyleSheet;
        }
        if (!StringUtils.isEmpty(configTitle = servletConfig.getInitParameter("service-list-title"))) {
            this.title = configTitle;
        }
        if (!StringUtils.isEmpty(showAllContexts = servletConfig.getInitParameter("service-list-all-contexts"))) {
            this.showForeignContexts = Boolean.valueOf(showAllContexts);
        }
    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {
    }
}

