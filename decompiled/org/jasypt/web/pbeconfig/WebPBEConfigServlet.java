/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletException
 *  javax.servlet.http.HttpServlet
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package org.jasypt.web.pbeconfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jasypt.commons.CommonUtils;
import org.jasypt.encryption.pbe.config.WebPBEConfig;
import org.jasypt.web.pbeconfig.WebPBEConfigHtmlUtils;
import org.jasypt.web.pbeconfig.WebPBEConfigRegistry;

public final class WebPBEConfigServlet
extends HttpServlet {
    private static final long serialVersionUID = -7201635392816652667L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.execute(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.execute(req, resp);
    }

    private void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            WebPBEConfigRegistry registry = WebPBEConfigRegistry.getInstance();
            if (registry.isWebConfigurationDone()) {
                this.writeResponse(WebPBEConfigHtmlUtils.createConfigurationDoneHtml(), resp);
            } else {
                String settingFlag = req.getParameter("jasyptPwSetting");
                if (CommonUtils.isEmpty(settingFlag)) {
                    this.writeResponse(WebPBEConfigHtmlUtils.createInputFormHtml(req, false), resp);
                } else {
                    List configs = registry.getConfigs();
                    Iterator configsIter = configs.iterator();
                    int i = 0;
                    int valid = 0;
                    while (configsIter.hasNext()) {
                        WebPBEConfig config = (WebPBEConfig)configsIter.next();
                        String validation = req.getParameter("jasyptVa" + i);
                        String password = req.getParameter("jasyptPw" + i);
                        String retypedPassword = req.getParameter("jasyptRPw" + i);
                        if (!CommonUtils.isEmpty(validation) && !CommonUtils.isEmpty(password) && password.equals(retypedPassword) && config.getValidationWord().equals(validation)) {
                            ++valid;
                        }
                        ++i;
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat();
                    Calendar now = Calendar.getInstance();
                    if (valid < configs.size()) {
                        this.getServletContext().log("Failed attempt to set PBE Configuration from " + req.getRemoteAddr() + " [" + dateFormat.format(now.getTime()) + "]");
                        this.writeResponse(WebPBEConfigHtmlUtils.createInputFormHtml(req, true), resp);
                    } else {
                        configsIter = configs.iterator();
                        i = 0;
                        while (configsIter.hasNext()) {
                            WebPBEConfig config = (WebPBEConfig)configsIter.next();
                            String password = req.getParameter("jasyptPw" + i);
                            config.setPassword(password);
                            ++i;
                        }
                        registry.setWebConfigurationDone(true);
                        this.getServletContext().log("PBE Configuration succesfully set from " + req.getRemoteAddr() + " [" + dateFormat.format(now.getTime()) + "]");
                        this.writeResponse(WebPBEConfigHtmlUtils.createConfigurationDoneHtml(), resp);
                    }
                }
            }
        }
        catch (IOException e) {
            this.getServletContext().log("Exception raised during servlet execution", (Throwable)e);
            throw e;
        }
        catch (Throwable t2) {
            this.getServletContext().log("Exception raised during servlet execution", t2);
            throw new ServletException(t2);
        }
    }

    private void writeResponse(String html, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        printWriter.write(html);
        printWriter.flush();
    }
}

