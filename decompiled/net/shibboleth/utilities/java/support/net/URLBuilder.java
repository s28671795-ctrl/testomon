/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.net;

import com.google.common.base.Strings;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.net.URISupport;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class URLBuilder {
    @Nullable
    private String scheme;
    @Nullable
    private String username;
    @Nullable
    private String password;
    @Nullable
    private String host;
    @Nullable
    private Integer port;
    @Nullable
    private String path;
    @Nonnull
    private List<Pair<String, String>> queryParams;
    private String fragement;

    public URLBuilder() {
        this.queryParams = new ArrayList<Pair<String, String>>();
    }

    public URLBuilder(@Nonnull @NotEmpty String baseURL) throws MalformedURLException {
        URL url2 = new URL(baseURL);
        this.setScheme(url2.getProtocol());
        String userInfo = url2.getUserInfo();
        if (!Strings.isNullOrEmpty(userInfo)) {
            if (userInfo.contains(":")) {
                String[] userInfoComps = userInfo.split(":");
                this.setUsername(URISupport.doURLDecode(userInfoComps[0]));
                this.setPassword(URISupport.doURLDecode(userInfoComps[1]));
            } else {
                this.setUsername(userInfo);
            }
        }
        this.setHost(url2.getHost());
        if (url2.getPort() > 0) {
            this.setPort(url2.getPort());
        }
        this.setPath(url2.getPath());
        this.queryParams = new ArrayList<Pair<String, String>>();
        String queryString = url2.getQuery();
        if (!Strings.isNullOrEmpty(queryString)) {
            String[] queryComps = queryString.split("&");
            for (int i = 0; i < queryComps.length; ++i) {
                String paramName;
                String queryComp = queryComps[i];
                if (!queryComp.contains("=")) {
                    paramName = URISupport.doURLDecode(queryComp);
                    this.queryParams.add(new Pair<String, Object>(paramName, null));
                    continue;
                }
                String[] paramComps = queryComp.split("=");
                paramName = URISupport.doURLDecode(paramComps[0]);
                String paramValue = URISupport.doURLDecode(paramComps[1]);
                this.queryParams.add(new Pair<String, String>(paramName, paramValue));
            }
        }
        this.setFragment(url2.getRef());
    }

    @Nullable
    public String getFragment() {
        return this.fragement;
    }

    public void setFragment(@Nullable String newFragment) {
        this.fragement = StringSupport.trimOrNull(newFragment);
    }

    @Nullable
    public String getHost() {
        return this.host;
    }

    public void setHost(@Nullable String newHost) {
        this.host = StringSupport.trimOrNull(newHost);
    }

    @Nullable
    public String getPassword() {
        return this.password;
    }

    public void setPassword(@Nullable String newPassword) {
        this.password = StringSupport.trimOrNull(newPassword);
    }

    @Nullable
    public String getPath() {
        return this.path;
    }

    public void setPath(@Nullable String newPath) {
        this.path = StringSupport.trimOrNull(newPath);
    }

    @Nullable
    public Integer getPort() {
        return this.port;
    }

    public void setPort(@Nullable Integer newPort) {
        this.port = newPort;
    }

    @Nonnull
    public List<Pair<String, String>> getQueryParams() {
        return this.queryParams;
    }

    @Nullable
    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(@Nullable String newScheme) {
        this.scheme = StringSupport.trimOrNull(newScheme);
    }

    @Nullable
    public String getUsername() {
        return this.username;
    }

    public void setUsername(@Nullable String newUsername) {
        this.username = StringSupport.trimOrNull(newUsername);
    }

    @Nullable
    public String buildURL() {
        String queryString;
        StringBuilder builder = new StringBuilder();
        if (!Strings.isNullOrEmpty(this.scheme)) {
            builder.append(this.scheme);
            builder.append("://");
        }
        if (!Strings.isNullOrEmpty(this.username)) {
            builder.append(this.username);
            if (!Strings.isNullOrEmpty(this.password)) {
                builder.append(":");
                builder.append(this.password);
            }
            builder.append("@");
        }
        if (!Strings.isNullOrEmpty(this.host)) {
            builder.append(this.host);
            if (this.port != null && this.port > 0) {
                builder.append(":");
                builder.append(this.port);
            }
        }
        if (!Strings.isNullOrEmpty(this.path)) {
            if (!this.path.startsWith("/")) {
                builder.append("/");
            }
            builder.append(this.path);
        }
        if (!Strings.isNullOrEmpty(queryString = this.buildQueryString())) {
            builder.append("?");
            builder.append(queryString);
        }
        if (!Strings.isNullOrEmpty(this.fragement)) {
            builder.append("#");
            builder.append(this.fragement);
        }
        return builder.toString();
    }

    @Nullable
    public String buildQueryString() {
        if (this.queryParams.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < this.queryParams.size(); ++i) {
                Pair<String, String> param2 = this.queryParams.get(i);
                String name = StringSupport.trimOrNull(param2.getFirst());
                if (name == null) continue;
                builder.append(URISupport.doURLEncode(name));
                String value = StringSupport.trimOrNull(param2.getSecond());
                if (value != null) {
                    builder.append("=");
                    builder.append(URISupport.doURLEncode(value));
                }
                if (i >= this.queryParams.size() - 1) continue;
                builder.append("&");
            }
            return builder.toString();
        }
        return null;
    }
}

