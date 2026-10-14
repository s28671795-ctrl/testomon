/*
 * Decompiled with CFR 0.152.
 */
package org.apache.geronimo.javamail.transport.nntp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;

public class NNTPReply {
    public static final int CAPABILITY_LIST = 101;
    public static final int POSTING_ALLOWED = 200;
    public static final int NO_POSTING_ALLOWED = 201;
    public static final int EXTENSIONS_SUPPORTED = 202;
    public static final int SERVICE_DISCONTINUED = 400;
    public static final int COMMAND_NOT_RECOGNIZED = 500;
    public static final int COMMAND_SYNTAX_ERROR = 501;
    public static final int PERMISSION_DENIED = 502;
    public static final int PROGRAM_FAULT = 503;
    public static final int ARTICLE_FOLLOWS = 220;
    public static final int HEAD_FOLLOWS = 221;
    public static final int BODY_FOLLOWS = 222;
    public static final int REQUEST_TEXT_SEPARATELY = 223;
    public static final int OVERVIEW_FOLLOWS = 224;
    public static final int NEW_ARTICLES_FOLLOWS = 230;
    public static final int NEW_GROUPS_FOLLOWS = 231;
    public static final int ARTICLE_TRANSFERRED = 235;
    public static final int NO_NEWSGROUP_SELECTED = 412;
    public static final int NO_ARTICLE_SELECTED = 420;
    public static final int NO_ARTICLE_NUMBER = 423;
    public static final int NO_ARTICLE_FOUND = 430;
    public static final int GROUP_SELECTED = 211;
    public static final int NO_SUCH_NEWSGROUP = 411;
    public static final int POSTED_OK = 240;
    public static final int SEND_ARTICLE = 340;
    public static final int POSTING_NOT_ALLOWED = 440;
    public static final int POSTING_FAILED = 441;
    public static final int CLOSING_CONNECTION = 205;
    public static final int AUTHINFO_ACCEPTED = 250;
    public static final int AUTHINFO_ACCEPTED_FINAL = 251;
    public static final int AUTHINFO_CONTINUE = 350;
    public static final int AUTHINFO_CHALLENGE = 350;
    public static final int AUTHINFO_SIMPLE_REJECTED = 402;
    public static final int AUTHENTICATION_ACCEPTED = 281;
    public static final int MORE_AUTHENTICATION_REQUIRED = 381;
    public static final int AUTHINFO_REQUIRED = 480;
    public static final int AUTHINFO_SIMPLE_REQUIRED = 450;
    public static final int AUTHENTICATION_REJECTED = 482;
    public static final int LIST_FOLLOWS = 215;
    private final String reply;
    private final int code;
    private final String message;
    private ArrayList data;

    NNTPReply(String s2) throws MessagingException {
        this.reply = s2;
        if (s2 == null || s2.length() < 3) {
            this.code = -1;
            this.message = s2;
            return;
        }
        try {
            this.code = Integer.parseInt(s2.substring(0, 3));
            this.message = s2.length() > 4 ? s2.substring(4) : "";
        }
        catch (NumberFormatException e) {
            throw new MessagingException("error in parsing reply code", e);
        }
    }

    public void retrieveData(BufferedReader in) throws MessagingException {
        try {
            this.data = new ArrayList();
            String line = in.readLine();
            while (line != null && !line.equals(".")) {
                if (line.startsWith(".")) {
                    line = line.substring(1);
                }
                this.data.add(line);
                line = in.readLine();
            }
        }
        catch (IOException e) {
            throw new MessagingException("Error reading message reply", e);
        }
    }

    public List getData() {
        return this.data;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getReply() {
        return this.reply;
    }

    boolean isError() {
        return this.code >= 400;
    }

    public String toString() {
        return "CODE = " + this.getCode() + " : MSG = " + this.getMessage();
    }
}

