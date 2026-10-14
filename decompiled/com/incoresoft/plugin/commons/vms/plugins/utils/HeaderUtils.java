/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.vms.plugins.utils;

import java.util.Set;
import java.util.stream.Collectors;

public class HeaderUtils {
    private static final Set<String> RESTRICTED_HTTP_HEADERS = Set.of("Accept", "Authorization", "Cookie", "Set-Cookie", "Connection", "Content-Length", "Date", "Expect", "Host", "If-Modified-Since", "Range", "Referer", "Transfer-Encoding", "User-Agent", "Proxy-Connection", "Keep-Alive").stream().map(String::toLowerCase).collect(Collectors.toSet());
    private static final Set<String> RESTRICTED_WS_HEADERS = Set.of("Authorization", "Sec-WebSocket-Key", "Sec-WebSocket-Accept", "Sec-WebSocket-Version", "Sec-WebSocket-Extensions", "Sec-WebSocket-Protocol", "Upgrade", "Host", "Connection").stream().map(String::toLowerCase).collect(Collectors.toSet());

    public static boolean isAllowedHttpHeader(String header) {
        return !RESTRICTED_HTTP_HEADERS.contains(header.toLowerCase());
    }

    public static boolean isAllowedWsHeader(String header) {
        return !RESTRICTED_WS_HEADERS.contains(header.toLowerCase());
    }
}

