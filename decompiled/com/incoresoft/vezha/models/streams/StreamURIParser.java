/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams;

import com.incoresoft.middleware.analytics.streams.authorization.BasicAuth;
import com.incoresoft.middleware.analytics.streams.authorization.DigestAuth;
import com.incoresoft.middleware.analytics.streams.params.LuxriotStreamParams;
import com.incoresoft.middleware.analytics.streams.params.MilestoneStreamParams;
import com.incoresoft.middleware.analytics.streams.params.NxStreamParams;
import com.incoresoft.middleware.model.stream.StreamAuth;
import com.incoresoft.middleware.model.stream.StreamParams;
import com.incoresoft.middleware.model.stream.StreamType;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.jetty.util.URIUtil;

public class StreamURIParser {
    public static URI parse(String host2, Integer port, String path, StreamAuth auth, StreamParams params) {
        String[] pathQuery;
        if (params.getType() == StreamType.embedded) {
            return null;
        }
        if (params.getType() == StreamType.file) {
            return Optional.ofNullable(StreamURIParser.getPath(path, params)).map(URI::create).orElse(null);
        }
        URIBuilder uriBuilder = new URIBuilder().setScheme(StreamURIParser.getScheme(params.getType())).setHost(host2);
        if (auth instanceof BasicAuth) {
            BasicAuth basicAuth = (BasicAuth)auth;
            uriBuilder.setUserInfo(basicAuth.getLogin(), basicAuth.getPassword());
        } else if (auth instanceof DigestAuth) {
            DigestAuth digestAuth = (DigestAuth)auth;
            uriBuilder.setUserInfo(digestAuth.getLogin(), digestAuth.getPassword());
        }
        if (port != -1) {
            uriBuilder.setPort(port);
        }
        if ((pathQuery = Objects.requireNonNullElse(StreamURIParser.getPath(path, params), "").split("\\?")).length > 0) {
            uriBuilder.setPath(pathQuery[0]);
        }
        if (pathQuery.length > 1) {
            String[] uriQuery;
            for (String item : uriQuery = pathQuery[1].split("&")) {
                Optional.of(item.split("=")).filter(it -> ((String[])it).length > 1).ifPresent(it -> uriBuilder.setParameter(it[0], it[1]));
            }
        }
        try {
            return uriBuilder.build();
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getPath(String path, StreamParams params) {
        if (path == null && params instanceof LuxriotStreamParams) {
            LuxriotStreamParams luxriotParams = (LuxriotStreamParams)params;
            path = String.format("/v2/live/%d/mainstream", luxriotParams.getChannelId());
        }
        if (params.getType() == StreamType.nx) {
            if (path != null && path.contains("auth")) {
                return path;
            }
            return "/" + ((NxStreamParams)params).getNxStreamId();
        }
        if (params.getType() == StreamType.milestone) {
            return "/live/" + ((MilestoneStreamParams)params).getId();
        }
        return path;
    }

    public static URI fromString(String s2) throws URISyntaxException {
        return URI.create(URIUtil.encodePath(s2));
    }

    public static String getScheme(StreamType type) {
        return switch (type) {
            case StreamType.rtsp, StreamType.hikvision, StreamType.milestone, StreamType.milestone_rtsp, StreamType.camtrace, StreamType.nx, StreamType.axis_rtsp, StreamType.genetec_rtsp -> "rtsp";
            case StreamType.smb_file -> "smb";
            case StreamType.luxriot -> "http";
            default -> type.name();
        };
    }

    public static String urlDecode(URI uri) {
        return StreamURIParser.urlDecode(uri.toString());
    }

    public static String urlDecode(String uri) {
        return URLDecoder.decode(uri, StandardCharsets.UTF_8);
    }
}

