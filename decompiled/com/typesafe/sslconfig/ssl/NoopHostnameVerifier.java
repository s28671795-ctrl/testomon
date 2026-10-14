/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u2Aa\u0001\u0003\u0003\u001b!)q\u0004\u0001C\u0001A!)1\u0005\u0001C\u0001I\t!bj\\8q\u0011>\u001cHO\\1nKZ+'/\u001b4jKJT!!\u0002\u0004\u0002\u0007M\u001cHN\u0003\u0002\b\u0011\u0005I1o\u001d7d_:4\u0017n\u001a\u0006\u0003\u0013)\t\u0001\u0002^=qKN\fg-\u001a\u0006\u0002\u0017\u0005\u00191m\\7\u0004\u0001M\u0019\u0001A\u0004\f\u0011\u0005=!R\"\u0001\t\u000b\u0005E\u0011\u0012\u0001\u00027b]\u001eT\u0011aE\u0001\u0005U\u00064\u0018-\u0003\u0002\u0016!\t1qJ\u00196fGR\u0004\"aF\u000f\u000e\u0003aQ!!B\r\u000b\u0005iY\u0012a\u00018fi*\tA$A\u0003kCZ\f\u00070\u0003\u0002\u001f1\t\u0001\u0002j\\:u]\u0006lWMV3sS\u001aLWM]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003\u0005\u0002\"A\t\u0001\u000e\u0003\u0011\taA^3sS\u001aLHcA\u0013,qA\u0011a%K\u0007\u0002O)\t\u0001&A\u0003tG\u0006d\u0017-\u0003\u0002+O\t9!i\\8mK\u0006t\u0007\"\u0002\u0017\u0003\u0001\u0004i\u0013\u0001\u00035pgRt\u0017-\\3\u0011\u00059*dBA\u00184!\t\u0001t%D\u00012\u0015\t\u0011D\"\u0001\u0004=e>|GOP\u0005\u0003i\u001d\na\u0001\u0015:fI\u00164\u0017B\u0001\u001c8\u0005\u0019\u0019FO]5oO*\u0011Ag\n\u0005\u0006s\t\u0001\rAO\u0001\u000bgNd7+Z:tS>t\u0007CA\f<\u0013\ta\u0004D\u0001\u0006T'2\u001bVm]:j_:\u0004")
public final class NoopHostnameVerifier
implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession sslSession) {
        return true;
    }
}

