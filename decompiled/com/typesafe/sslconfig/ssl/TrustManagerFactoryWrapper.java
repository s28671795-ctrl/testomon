/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import java.security.InvalidAlgorithmParameterException;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3qa\u0001\u0003\u0011\u0002G\u0005Q\u0002C\u0003\u0015\u0001\u0019\u0005Q\u0003C\u0003W\u0001\u0019\u0005qK\u0001\u000eUeV\u001cH/T1oC\u001e,'OR1di>\u0014\u0018p\u0016:baB,'O\u0003\u0002\u0006\r\u0005\u00191o\u001d7\u000b\u0005\u001dA\u0011!C:tY\u000e|gNZ5h\u0015\tI!\"\u0001\u0005usB,7/\u00194f\u0015\u0005Y\u0011aA2p[\u000e\u00011C\u0001\u0001\u000f!\ty!#D\u0001\u0011\u0015\u0005\t\u0012!B:dC2\f\u0017BA\n\u0011\u0005\u0019\te.\u001f*fM\u0006!\u0011N\\5u)\t1\u0012\u0004\u0005\u0002\u0010/%\u0011\u0001\u0004\u0005\u0002\u0005+:LG\u000fC\u0003\u001b\u0003\u0001\u00071$\u0001\u0003ta\u0016\u001c\u0007C\u0001\u000f#\u001b\u0005i\"BA\u0003\u001f\u0015\ty\u0002%A\u0002oKRT\u0011!I\u0001\u0006U\u00064\u0018\r_\u0005\u0003Gu\u0011\u0001$T1oC\u001e,'OR1di>\u0014\u0018\u0010U1sC6,G/\u001a:tQ\r\tQ\u0005\r\t\u0004\u001f\u0019B\u0013BA\u0014\u0011\u0005\u0019!\bN]8xgB\u0011\u0011FL\u0007\u0002U)\u00111\u0006L\u0001\tg\u0016\u001cWO]5us*\tQ&\u0001\u0003kCZ\f\u0017BA\u0018+\u0005\tJeN^1mS\u0012\fEnZ8sSRDW\u000eU1sC6,G/\u001a:Fq\u000e,\u0007\u000f^5p]F\"a$\r\u001fV!\t\u0011\u0014H\u0004\u00024oA\u0011A\u0007E\u0007\u0002k)\u0011a\u0007D\u0001\u0007yI|w\u000e\u001e \n\u0005a\u0002\u0012A\u0002)sK\u0012,g-\u0003\u0002;w\t11\u000b\u001e:j]\u001eT!\u0001\u000f\t2\u000b\rj\u0014\t\u0015\"\u0016\u0005yzT#A\u0019\u0005\u000b\u0001c!\u0019A#\u0003\u0003QK!AQ\"\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132\u0015\t!\u0005#\u0001\u0004uQJ|wo]\t\u0003\r&\u0003\"aD$\n\u0005!\u0003\"a\u0002(pi\"Lgn\u001a\t\u0003\u00156s!aD&\n\u00051\u0003\u0012a\u00029bG.\fw-Z\u0005\u0003\u001d>\u0013\u0011\u0002\u00165s_^\f'\r\\3\u000b\u00051\u0003\u0012'B\u0012R%N#eBA\bS\u0013\t!\u0005#\r\u0003#\u001fA!&!B:dC2\f\u0017G\u0001\u0014)\u0003A9W\r\u001e+skN$X*\u00198bO\u0016\u00148/F\u0001Y!\ry\u0011lW\u0005\u00035B\u0011Q!\u0011:sCf\u0004\"\u0001\b/\n\u0005uk\"\u0001\u0004+skN$X*\u00198bO\u0016\u0014\b")
public interface TrustManagerFactoryWrapper {
    public void init(ManagerFactoryParameters var1) throws InvalidAlgorithmParameterException;

    public TrustManager[] getTrustManagers();
}

