/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.authentication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import org.apache.geronimo.javamail.authentication.ClientAuthenticator;
import org.apache.geronimo.mail.util.Base64;
import org.apache.geronimo.mail.util.Hex;

public class DigestMD5Authenticator
implements ClientAuthenticator {
    protected static final int AUTHENTICATE_CLIENT = 0;
    protected static final int AUTHENTICATE_SERVER = 1;
    protected static final int AUTHENTICATION_COMPLETE = 2;
    protected String host;
    protected String username;
    protected String password;
    protected String realm;
    MessageDigest digest;
    protected String clientResponse;
    protected String authenticationResponse = null;
    protected ArrayList realms;
    protected String nonce;
    protected int stage = 0;

    public DigestMD5Authenticator(String host2, String username, String password, String realm) {
        this.host = host2;
        this.username = username;
        this.password = password;
        this.realm = realm;
    }

    public boolean hasInitialResponse() {
        return false;
    }

    public boolean isComplete() {
        return this.stage == 2;
    }

    public String getMechanismName() {
        return "DIGEST-MD5";
    }

    public byte[] evaluateChallenge(byte[] challenge) throws MessagingException {
        switch (this.stage) {
            case 0: {
                byte[] response = this.authenticateClient(challenge);
                this.stage = 1;
                return response;
            }
            case 1: {
                byte[] response = this.authenticateServer(challenge);
                this.stage = 2;
                return response;
            }
        }
        throw new MessagingException("Invalid LOGIN challenge");
    }

    public byte[] authenticateServer(byte[] challenge) throws MessagingException {
        if (!this.parseChallenge(challenge)) {
            return null;
        }
        try {
            this.digest.update((":smtp/" + this.host).getBytes("US-ASCII"));
            String responseString = this.clientResponse + new String(Hex.encode(this.digest.digest()), "US-ASCII");
            this.digest.update(responseString.getBytes("US-ASCII"));
            String validationText = new String(Hex.encode(this.digest.digest()), "US-ASCII");
            if (validationText.equals(this.authenticationResponse)) {
                return new byte[0];
            }
            throw new AuthenticationFailedException("Invalid DIGEST-MD5 response from server");
        }
        catch (UnsupportedEncodingException e) {
            throw new MessagingException("Invalid character encodings");
        }
    }

    public byte[] authenticateClient(byte[] challenge) throws MessagingException {
        SecureRandom randomGenerator;
        if (!this.parseChallenge(challenge)) {
            return null;
        }
        try {
            randomGenerator = new SecureRandom();
            this.digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new MessagingException("Unable to access cryptography libraries");
        }
        if (this.realm == null) {
            this.realm = this.realms.isEmpty() ? this.host : (String)this.realms.get(0);
        }
        byte[] cnonceBytes = new byte[32];
        randomGenerator.nextBytes(cnonceBytes);
        try {
            String cnonce = new String(Base64.encode(cnonceBytes), "US-ASCII");
            String idString = this.username + ":" + this.realm + ":" + this.password;
            this.digest.update(this.digest.digest(idString.getBytes("US-ASCII")));
            String nonceString = ":" + this.nonce + ":" + cnonce;
            this.digest.update(nonceString.getBytes("US-ASCII"));
            this.clientResponse = new String(Hex.encode(this.digest.digest()), "US-ASCII") + ":" + this.nonce + ":00000001:" + cnonce + ":auth:";
            String authString = "AUTHENTICATE:smtp/" + this.host;
            this.digest.update(authString.getBytes("US-ASCII"));
            String responseString = this.clientResponse + new String(Hex.encode(this.digest.digest()), "US-ASCII");
            this.digest.update(responseString.getBytes("US-ASCII"));
            String challengeResponse = new String(Hex.encode(this.digest.digest()), "US-ASCII");
            StringBuffer response = new StringBuffer();
            response.append("username=\"");
            response.append(this.username);
            response.append("\"");
            response.append(",realm=\"");
            response.append(this.realm);
            response.append("\"");
            response.append(",qop=auth");
            response.append(",nc=00000001");
            response.append(",nonce=\"");
            response.append(this.nonce);
            response.append("\"");
            response.append(",cnonce=\"");
            response.append(cnonce);
            response.append("\"");
            response.append(",digest-uri=\"smtp/");
            response.append(this.host);
            response.append("\"");
            response.append(",response=");
            response.append(challengeResponse);
            return response.toString().getBytes("US-ASCII");
        }
        catch (UnsupportedEncodingException e) {
            throw new MessagingException("Invalid character encodings");
        }
    }

    protected boolean parseChallenge(byte[] challenge) throws MessagingException {
        this.realms = new ArrayList();
        DigestParser parser = null;
        try {
            parser = new DigestParser(new String(challenge, "US-ASCII"));
        }
        catch (UnsupportedEncodingException ex) {
            // empty catch block
        }
        while (parser.hasMore()) {
            NameValuePair pair = parser.parseNameValuePair();
            String name = pair.name;
            if (name.equalsIgnoreCase("realm")) {
                this.realms.add(pair.value);
                continue;
            }
            if (name.equalsIgnoreCase("nonce")) {
                this.nonce = pair.value;
                continue;
            }
            if (!name.equalsIgnoreCase("rspauth")) continue;
            this.authenticationResponse = pair.value;
        }
        return true;
    }

    public class NameValuePair {
        public String name;
        public String value;

        NameValuePair(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    class DigestParser {
        String challenge;
        int length;
        int position;

        public DigestParser(String challenge) {
            this.challenge = challenge;
            this.length = challenge.length();
            this.position = 0;
        }

        private boolean hasMore() {
            return this.position < this.length;
        }

        private char currentChar() {
            return this.challenge.charAt(this.position);
        }

        private void nextChar() {
            ++this.position;
        }

        private void skipSpaces() {
            while (this.position < this.length && Character.isWhitespace(this.currentChar())) {
                ++this.position;
            }
        }

        private String parseQuotedValue() {
            this.nextChar();
            StringBuffer value = new StringBuffer();
            while (this.hasMore()) {
                char ch = this.currentChar();
                if (ch == '\\') {
                    this.nextChar();
                    if (!this.hasMore()) {
                        return null;
                    }
                    value.append(this.currentChar());
                } else {
                    if (ch == '\"') {
                        this.nextChar();
                        return value.toString();
                    }
                    value.append(ch);
                }
                this.nextChar();
            }
            return null;
        }

        private String parseTokenValue() {
            StringBuffer value = new StringBuffer();
            while (this.hasMore()) {
                char ch = this.currentChar();
                switch (ch) {
                    case '\t': 
                    case ' ': 
                    case '\"': 
                    case '(': 
                    case ')': 
                    case ',': 
                    case '/': 
                    case ':': 
                    case ';': 
                    case '<': 
                    case '=': 
                    case '>': 
                    case '?': 
                    case '@': 
                    case '[': 
                    case '\\': 
                    case ']': 
                    case '{': 
                    case '}': {
                        if (value.length() == 0) {
                            return null;
                        }
                        return value.toString();
                    }
                }
                if (ch < ' ' || ch > '\u007f') {
                    if (value.length() == 0) {
                        return null;
                    }
                    return value.toString();
                }
                value.append(ch);
                this.nextChar();
            }
            if (value.length() == 0) {
                return null;
            }
            return value.toString();
        }

        private String parseName() {
            this.skipSpaces();
            return this.parseTokenValue();
        }

        private String parseValue() {
            this.skipSpaces();
            if (this.currentChar() == '\"') {
                return this.parseQuotedValue();
            }
            return this.parseTokenValue();
        }

        public NameValuePair parseNameValuePair() throws MessagingException {
            String name = this.parseName();
            if (name == null) {
                throw new MessagingException("Name syntax error");
            }
            if (!this.hasMore() || this.currentChar() != '=') {
                throw new MessagingException("Name/value pair syntax error");
            }
            this.nextChar();
            String value = this.parseValue();
            if (value == null) {
                throw new MessagingException("Name/value pair syntax error");
            }
            this.skipSpaces();
            if (this.hasMore()) {
                if (this.currentChar() != ',') {
                    throw new MessagingException("Name/value pair syntax error");
                }
                this.nextChar();
                this.skipSpaces();
            }
            return new NameValuePair(name, value);
        }
    }
}

