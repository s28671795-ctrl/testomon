/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.CompositeCertificateException$;
import java.security.cert.CertificateException;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u001d4Aa\u0003\u0007\u0001+!A\u0001\u0005\u0001B\u0001B\u0003%\u0011\u0005\u0003\u0005/\u0001\t\u0015\r\u0011\"\u00010\u0011!i\u0004A!A!\u0002\u0013\u0001\u0004\"\u0002 \u0001\t\u0003y\u0004\"\u0002#\u0001\t\u0003ys!B#\r\u0011\u00031e!B\u0006\r\u0011\u00039\u0005\"\u0002 \b\t\u0003\t\u0006\"\u0002*\b\t\u0003\u0019\u0006bB0\b\u0003\u0003%I\u0001\u0019\u0002\u001e\u0007>l\u0007o\\:ji\u0016\u001cUM\u001d;jM&\u001c\u0017\r^3Fq\u000e,\u0007\u000f^5p]*\u0011QBD\u0001\u0004gNd'BA\b\u0011\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u0002\u0012%\u0005AA/\u001f9fg\u00064WMC\u0001\u0014\u0003\r\u0019w.\\\u0002\u0001'\t\u0001a\u0003\u0005\u0002\u0018=5\t\u0001D\u0003\u0002\u001a5\u0005!1-\u001a:u\u0015\tYB$\u0001\u0005tK\u000e,(/\u001b;z\u0015\u0005i\u0012\u0001\u00026bm\u0006L!a\b\r\u0003)\r+'\u000f^5gS\u000e\fG/Z#yG\u0016\u0004H/[8o\u0003\ri7o\u001a\t\u0003E-r!aI\u0015\u0011\u0005\u0011:S\"A\u0013\u000b\u0005\u0019\"\u0012A\u0002\u001fs_>$hHC\u0001)\u0003\u0015\u00198-\u00197b\u0013\tQs%\u0001\u0004Qe\u0016$WMZ\u0005\u0003Y5\u0012aa\u0015;sS:<'B\u0001\u0016(\u0003)!\bN]8xC\ndWm]\u000b\u0002aA\u0019\u0011G\r\u001b\u000e\u0003\u001dJ!aM\u0014\u0003\u000b\u0005\u0013(/Y=\u0011\u0005URdB\u0001\u001c9\u001d\t!s'C\u0001)\u0013\tIt%A\u0004qC\u000e\\\u0017mZ3\n\u0005mb$!\u0003+ie><\u0018M\u00197f\u0015\tIt%A\u0006uQJ|w/\u00192mKN\u0004\u0013A\u0002\u001fj]&$h\bF\u0002A\u0005\u000e\u0003\"!\u0011\u0001\u000e\u00031AQ\u0001\t\u0003A\u0002\u0005BQA\f\u0003A\u0002A\n1cZ3u'>,(oY3Fq\u000e,\u0007\u000f^5p]N\fQdQ8na>\u001c\u0018\u000e^3DKJ$\u0018NZ5dCR,W\t_2faRLwN\u001c\t\u0003\u0003\u001e\u00192a\u0002%L!\t\t\u0014*\u0003\u0002KO\t1\u0011I\\=SK\u001a\u0004\"\u0001T(\u000e\u00035S!A\u0014\u000f\u0002\u0005%|\u0017B\u0001)N\u00051\u0019VM]5bY&T\u0018M\u00197f)\u00051\u0015AB;ooJ\f\u0007\u000f\u0006\u0002U;R\u0011Q\u000b\u0017\t\u0003cYK!aV\u0014\u0003\tUs\u0017\u000e\u001e\u0005\u00063&\u0001\rAW\u0001\u0006E2|7m\u001b\t\u0005cm#T+\u0003\u0002]O\tIa)\u001e8di&|g.\r\u0005\u0006=&\u0001\r\u0001N\u0001\u0002K\u0006aqO]5uKJ+\u0007\u000f\\1dKR\t\u0011\r\u0005\u0002cK6\t1M\u0003\u0002e9\u0005!A.\u00198h\u0013\t17M\u0001\u0004PE*,7\r\u001e")
public class CompositeCertificateException
extends CertificateException {
    private final Throwable[] throwables;

    public static void unwrap(Throwable e, Function1<Throwable, BoxedUnit> block) {
        CompositeCertificateException$.MODULE$.unwrap(e, block);
    }

    public Throwable[] throwables() {
        return this.throwables;
    }

    public Throwable[] getSourceExceptions() {
        return this.throwables();
    }

    public CompositeCertificateException(String msg, Throwable[] throwables) {
        this.throwables = throwables;
        super(msg);
    }
}

