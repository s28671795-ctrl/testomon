/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.AlgorithmConstraint;
import com.typesafe.sslconfig.util.LoggerFactory;
import javax.security.cert.X509Certificate;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U3A\u0001C\u0005\u0001%!A\u0011\u0004\u0001B\u0001B\u0003%!\u0004\u0003\u0005!\u0001\t\u0015\r\u0011\"\u0001\"\u0011!\t\u0004A!A!\u0002\u0013\u0011\u0003\u0002\u0003\u001a\u0001\u0005\u000b\u0007I\u0011A\u0011\t\u0011M\u0002!\u0011!Q\u0001\n\tBQ\u0001\u000e\u0001\u0005\u0002UBQA\u000f\u0001\u0005\u0002m\u0012\u0001#\u00117h_JLG\u000f[7DQ\u0016\u001c7.\u001a:\u000b\u0005)Y\u0011aA:tY*\u0011A\"D\u0001\ngNd7m\u001c8gS\u001eT!AD\b\u0002\u0011QL\b/Z:bM\u0016T\u0011\u0001E\u0001\u0004G>l7\u0001A\n\u0003\u0001M\u0001\"\u0001F\f\u000e\u0003UQ\u0011AF\u0001\u0006g\u000e\fG.Y\u0005\u00031U\u0011a!\u00118z%\u00164\u0017\u0001C7l\u0019><w-\u001a:\u0011\u0005mqR\"\u0001\u000f\u000b\u0005uY\u0011\u0001B;uS2L!a\b\u000f\u0003\u001b1{wmZ3s\r\u0006\u001cGo\u001c:z\u0003Q\u0019\u0018n\u001a8biV\u0014XmQ8ogR\u0014\u0018-\u001b8ugV\t!\u0005E\u0002$U5r!\u0001\n\u0015\u0011\u0005\u0015*R\"\u0001\u0014\u000b\u0005\u001d\n\u0012A\u0002\u001fs_>$h(\u0003\u0002*+\u00051\u0001K]3eK\u001aL!a\u000b\u0017\u0003\u0007M+GO\u0003\u0002*+A\u0011afL\u0007\u0002\u0013%\u0011\u0001'\u0003\u0002\u0014\u00032<wN]5uQ6\u001cuN\\:ue\u0006Lg\u000e^\u0001\u0016g&<g.\u0019;ve\u0016\u001cuN\\:ue\u0006Lg\u000e^:!\u00039YW-_\"p]N$(/Y5oiN\fqb[3z\u0007>t7\u000f\u001e:bS:$8\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\tY:\u0004(\u000f\t\u0003]\u0001AQ!\u0007\u0004A\u0002iAQ\u0001\t\u0004A\u0002\tBQA\r\u0004A\u0002\t\n!c\u00195fG.\\U-_!mO>\u0014\u0018\u000e\u001e5ngR\u0011Ah\u0010\t\u0003)uJ!AP\u000b\u0003\tUs\u0017\u000e\u001e\u0005\u0006\u0001\u001e\u0001\r!Q\u0001\tqV\u0002\u0014hQ3siB\u0011!)S\u0007\u0002\u0007*\u0011A)R\u0001\u0005G\u0016\u0014HO\u0003\u0002G\u000f\u0006A1/Z2ve&$\u0018PC\u0001I\u0003\u0015Q\u0017M^1y\u0013\tQ5IA\bYkAJ4)\u001a:uS\u001aL7-\u0019;fQ\u0019\u0001Aj\u0014)S'B\u0011A#T\u0005\u0003\u001dV\u0011!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f\u0013!U\u0001\u000e]>$\be\u001c9fe\u0006$\u0018N^3\u0002\u000bMLgnY3\"\u0003Q\u000bQ\u0001\r\u00186]A\u0002")
public class AlgorithmChecker {
    private final Set<AlgorithmConstraint> signatureConstraints;
    private final Set<AlgorithmConstraint> keyConstraints;

    public Set<AlgorithmConstraint> signatureConstraints() {
        return this.signatureConstraints;
    }

    public Set<AlgorithmConstraint> keyConstraints() {
        return this.keyConstraints;
    }

    public void checkKeyAlgorithms(X509Certificate x509Cert) {
    }

    public AlgorithmChecker(LoggerFactory mkLogger, Set<AlgorithmConstraint> signatureConstraints, Set<AlgorithmConstraint> keyConstraints) {
        this.signatureConstraints = signatureConstraints;
        this.keyConstraints = keyConstraints;
    }
}

