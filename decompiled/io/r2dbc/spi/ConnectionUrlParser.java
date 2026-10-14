/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import io.r2dbc.spi.Assert;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Nullable;
import io.r2dbc.spi.Option;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class ConnectionUrlParser {
    private static final Set<String> PROHIBITED_QUERY_OPTIONS = Stream.of(ConnectionFactoryOptions.DATABASE, ConnectionFactoryOptions.DRIVER, ConnectionFactoryOptions.HOST, ConnectionFactoryOptions.PASSWORD, ConnectionFactoryOptions.PORT, ConnectionFactoryOptions.PROTOCOL, ConnectionFactoryOptions.USER).map(Option::name).collect(Collectors.toSet());
    private static final String R2DBC_SCHEME = "r2dbc";
    private static final String R2DBC_SSL_SCHEME = "r2dbcs";

    static void validate(String url2) {
        Assert.requireNonNull(url2, "URL must not be null");
        if (!url2.startsWith("r2dbc:") && !url2.startsWith("r2dbcs:")) {
            throw new IllegalArgumentException(String.format("URL %s does not start with the %s scheme", url2, R2DBC_SCHEME));
        }
        int schemeSpecificPartIndex = url2.indexOf("://");
        int driverPartIndex = url2.startsWith(R2DBC_SSL_SCHEME) ? R2DBC_SSL_SCHEME.length() + 1 : R2DBC_SCHEME.length() + 1;
        if (schemeSpecificPartIndex == -1 || driverPartIndex >= schemeSpecificPartIndex) {
            throw new IllegalArgumentException(String.format("Invalid URL: %s", url2));
        }
        String[] schemeParts = url2.split(":", 3);
        String driver = schemeParts[1];
        if (driver.trim().isEmpty()) {
            throw new IllegalArgumentException(String.format("Empty driver in URL: %s", url2));
        }
    }

    static ConnectionFactoryOptions parseQuery(CharSequence url2) {
        String path;
        String urlToUse = url2.toString();
        ConnectionUrlParser.validate(urlToUse);
        String[] schemeParts = urlToUse.split(":", 3);
        String scheme2 = schemeParts[0];
        String driver = schemeParts[1];
        String protocol = schemeParts[2];
        int schemeSpecificPartIndex = urlToUse.indexOf("://");
        String rewrittenUrl = scheme2 + urlToUse.substring(schemeSpecificPartIndex);
        URI uri = URI.create(rewrittenUrl);
        ConnectionFactoryOptions.Builder builder = ConnectionFactoryOptions.builder();
        if (scheme2.equals(R2DBC_SSL_SCHEME)) {
            builder.option(ConnectionFactoryOptions.SSL, true);
        }
        builder.option(ConnectionFactoryOptions.DRIVER, driver);
        int protocolEnd = protocol.indexOf("://");
        if (protocolEnd != -1 && !(protocol = protocol.substring(0, protocolEnd)).trim().isEmpty()) {
            builder.option(ConnectionFactoryOptions.PROTOCOL, protocol);
        }
        if (ConnectionUrlParser.hasText(uri.getHost())) {
            builder.option(ConnectionFactoryOptions.HOST, ConnectionUrlParser.decode(uri.getHost().trim()).toString());
            if (ConnectionUrlParser.hasText(uri.getRawUserInfo())) {
                ConnectionUrlParser.parseUserinfo(uri.getRawUserInfo(), builder);
            }
        } else if (ConnectionUrlParser.hasText(uri.getRawAuthority())) {
            String authorityToUse = uri.getRawAuthority();
            if (authorityToUse.contains("@")) {
                int atIndex = authorityToUse.lastIndexOf(64);
                String userinfo = authorityToUse.substring(0, atIndex);
                authorityToUse = authorityToUse.substring(atIndex + 1);
                if (!userinfo.isEmpty()) {
                    ConnectionUrlParser.parseUserinfo(userinfo, builder);
                }
            }
            builder.option(ConnectionFactoryOptions.HOST, ConnectionUrlParser.decode(authorityToUse.trim()).toString());
        }
        if (uri.getPort() != -1) {
            builder.option(ConnectionFactoryOptions.PORT, uri.getPort());
        }
        if (ConnectionUrlParser.hasText(uri.getPath()) && ConnectionUrlParser.hasText(path = uri.getPath().substring(1).trim())) {
            builder.option(ConnectionFactoryOptions.DATABASE, path);
        }
        if (ConnectionUrlParser.hasText(uri.getRawQuery())) {
            ConnectionUrlParser.parseQuery(uri.getRawQuery().trim(), (k, v) -> {
                if (PROHIBITED_QUERY_OPTIONS.contains(k)) {
                    throw new IllegalArgumentException(String.format("URL %s must not declare option %s in the query string", url2, k));
                }
                builder.option(Option.valueOf(k), v);
            });
        }
        return builder.build();
    }

    static void parseQuery(CharSequence s2, BiConsumer<String, String> tupleConsumer) {
        QueryStringParser parser = QueryStringParser.create(s2);
        while (!parser.isFinished()) {
            CharSequence value;
            CharSequence name = parser.parseName();
            CharSequence charSequence = value = parser.isFinished() ? null : parser.parseValue();
            if (name.length() == 0 || value == null) continue;
            tupleConsumer.accept(ConnectionUrlParser.decode(name).toString(), ConnectionUrlParser.decode(value).toString());
        }
    }

    private static void parseUserinfo(String s2, ConnectionFactoryOptions.Builder builder) {
        CharSequence password;
        if (!s2.contains(":")) {
            String user = ConnectionUrlParser.decode(s2).toString();
            builder.option(ConnectionFactoryOptions.USER, user);
            return;
        }
        String[] userinfo = s2.split(":", 2);
        String user = ConnectionUrlParser.decode(userinfo[0]).toString();
        if (!user.isEmpty()) {
            builder.option(ConnectionFactoryOptions.USER, user);
        }
        if ((password = ConnectionUrlParser.decode(userinfo[1])).length() != 0) {
            builder.option(ConnectionFactoryOptions.PASSWORD, password);
        }
    }

    private static CharSequence decode(CharSequence s2) {
        boolean encoded = false;
        int numChars = s2.length();
        StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
        int i = 0;
        byte[] bytes = null;
        block6: while (i < numChars) {
            char c = s2.charAt(i);
            switch (c) {
                case '+': {
                    sb.append(' ');
                    ++i;
                    encoded = true;
                    continue block6;
                }
                case '%': {
                    try {
                        if (bytes == null) {
                            bytes = new byte[(numChars - i) / 3];
                        }
                        int pos = 0;
                        while (i + 2 < numChars && c == '%') {
                            int v = Integer.parseInt(s2.subSequence(i + 1, i + 3).toString(), 16);
                            if (v < 0) {
                                throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                            }
                            bytes[pos++] = (byte)v;
                            if ((i += 3) >= numChars) continue;
                            c = s2.charAt(i);
                        }
                        if (i < numChars && c == '%') {
                            throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");
                        }
                        sb.append(StandardCharsets.UTF_8.decode(ByteBuffer.wrap(bytes, 0, pos)));
                    }
                    catch (NumberFormatException e) {
                        throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - " + e.getMessage());
                    }
                    encoded = true;
                    continue block6;
                }
            }
            sb.append(c);
            ++i;
        }
        return encoded ? sb : s2;
    }

    private static boolean hasText(@Nullable String s2) {
        return s2 != null && !s2.isEmpty();
    }

    private ConnectionUrlParser() {
    }

    private static class Cursor {
        private final int upperBound;
        private int pos;

        Cursor(int upperBound) {
            this.upperBound = upperBound;
            this.pos = 0;
        }

        void incrementParsePosition() {
            this.updatePos(this.getParsePosition() + 1);
        }

        int getUpperBound() {
            return this.upperBound;
        }

        int getParsePosition() {
            return this.pos;
        }

        void updatePos(int pos) {
            this.pos = pos;
        }

        boolean isFinished() {
            return this.pos >= this.upperBound;
        }
    }

    static class QueryStringParser {
        static final char CR = '\r';
        static final char LF = '\n';
        static final char SPACE = ' ';
        static final char TAB = '\t';
        private final CharSequence input;
        private final Cursor state;
        private final BitSet delimiters = new BitSet(256);

        private QueryStringParser(CharSequence input) {
            this.input = input;
            this.state = new Cursor(input.length());
            this.delimiters.set(38);
        }

        static QueryStringParser create(CharSequence input) {
            return new QueryStringParser(input);
        }

        boolean isFinished() {
            return this.state.isFinished();
        }

        CharSequence parseName() {
            if (this.state.isFinished()) {
                throw new IllegalStateException("Parsing is finished");
            }
            this.delimiters.set(61);
            return this.parseToken();
        }

        @Nullable
        CharSequence parseValue() {
            if (this.state.isFinished()) {
                throw new IllegalStateException("Parsing is finished");
            }
            char delim = this.input.charAt(this.state.getParsePosition());
            this.state.incrementParsePosition();
            if (delim == '=') {
                this.delimiters.clear(61);
                try {
                    CharSequence charSequence = this.parseToken();
                    return charSequence;
                }
                finally {
                    if (!this.isFinished()) {
                        this.state.incrementParsePosition();
                    }
                }
            }
            return null;
        }

        private CharSequence parseToken() {
            char current;
            StringBuilder dst = new StringBuilder();
            boolean whitespace = false;
            while (!this.state.isFinished() && !this.delimiters.get(current = this.input.charAt(this.state.getParsePosition()))) {
                if (QueryStringParser.isWhitespace(current)) {
                    this.skipWhiteSpace();
                    whitespace = true;
                    continue;
                }
                if (whitespace && dst.length() > 0) {
                    dst.append(' ');
                }
                this.copyContent(dst);
                whitespace = false;
            }
            return dst;
        }

        private void skipWhiteSpace() {
            char current;
            int pos = this.state.getParsePosition();
            for (int i = this.state.getParsePosition(); i < this.state.getUpperBound() && QueryStringParser.isWhitespace(current = this.input.charAt(i)); ++i) {
                ++pos;
            }
            this.state.updatePos(pos);
        }

        private void copyContent(StringBuilder target) {
            char current;
            int pos = this.state.getParsePosition();
            for (int i = this.state.getParsePosition(); i < this.state.getUpperBound() && !this.delimiters.get(current = this.input.charAt(i)) && !QueryStringParser.isWhitespace(current); ++i) {
                ++pos;
                target.append(current);
            }
            this.state.updatePos(pos);
        }

        private static boolean isWhitespace(char ch) {
            return ch == ' ' || ch == '\t' || ch == '\r' || ch == '\n';
        }
    }
}

