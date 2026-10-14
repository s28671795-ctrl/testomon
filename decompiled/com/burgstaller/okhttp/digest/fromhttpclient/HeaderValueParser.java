/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp.digest.fromhttpclient;

import com.burgstaller.okhttp.digest.fromhttpclient.CharArrayBuffer;
import com.burgstaller.okhttp.digest.fromhttpclient.HeaderElement;
import com.burgstaller.okhttp.digest.fromhttpclient.NameValuePair;
import com.burgstaller.okhttp.digest.fromhttpclient.ParseException;
import com.burgstaller.okhttp.digest.fromhttpclient.ParserCursor;

public interface HeaderValueParser {
    public HeaderElement[] parseElements(CharArrayBuffer var1, ParserCursor var2) throws ParseException;

    public HeaderElement parseHeaderElement(CharArrayBuffer var1, ParserCursor var2) throws ParseException;

    public NameValuePair[] parseParameters(CharArrayBuffer var1, ParserCursor var2) throws ParseException;

    public NameValuePair parseNameValuePair(CharArrayBuffer var1, ParserCursor var2) throws ParseException;
}

