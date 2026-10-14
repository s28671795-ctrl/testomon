/*
 * Decompiled with CFR 0.152.
 */
package jakarta.servlet;

import jakarta.servlet.AsyncListener;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public interface AsyncContext {
    public static final String ASYNC_REQUEST_URI = "jakarta.servlet.async.request_uri";
    public static final String ASYNC_CONTEXT_PATH = "jakarta.servlet.async.context_path";
    public static final String ASYNC_MAPPING = "jakarta.servlet.async.mapping";
    public static final String ASYNC_PATH_INFO = "jakarta.servlet.async.path_info";
    public static final String ASYNC_SERVLET_PATH = "jakarta.servlet.async.servlet_path";
    public static final String ASYNC_QUERY_STRING = "jakarta.servlet.async.query_string";

    public ServletRequest getRequest();

    public ServletResponse getResponse();

    public boolean hasOriginalRequestAndResponse();

    public void dispatch();

    public void dispatch(String var1);

    public void dispatch(ServletContext var1, String var2);

    public void complete();

    public void start(Runnable var1);

    public void addListener(AsyncListener var1);

    public void addListener(AsyncListener var1, ServletRequest var2, ServletResponse var3);

    public <T extends AsyncListener> T createListener(Class<T> var1) throws ServletException;

    public void setTimeout(long var1);

    public long getTimeout();
}

