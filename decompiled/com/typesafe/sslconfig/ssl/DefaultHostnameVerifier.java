/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import scala.reflect.ScalaSignature;
import sun.security.util.HostnameChecker;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015a\u0001\u0002\u0006\f\u0001QA\u0001B\n\u0001\u0003\u0002\u0003\u0006Ia\n\u0005\u0006[\u0001!\tA\f\u0005\be\u0001\u0011\r\u0011\"\u00034\u0011\u00199\u0004\u0001)A\u0005i!)\u0001\b\u0001C\u0001s!)1\t\u0001C\u0001\t\")q\f\u0001C\u0001A\")!\r\u0001C\u0001G\")!\u000e\u0001C\u0001W\n9B)\u001a4bk2$\bj\\:u]\u0006lWMV3sS\u001aLWM\u001d\u0006\u0003\u00195\t1a]:m\u0015\tqq\"A\u0005tg2\u001cwN\u001c4jO*\u0011\u0001#E\u0001\tif\u0004Xm]1gK*\t!#A\u0002d_6\u001c\u0001aE\u0002\u0001+u\u0001\"AF\u000e\u000e\u0003]Q!\u0001G\r\u0002\t1\fgn\u001a\u0006\u00025\u0005!!.\u0019<b\u0013\tarC\u0001\u0004PE*,7\r\u001e\t\u0003=\u0011j\u0011a\b\u0006\u0003\u0019\u0001R!!\t\u0012\u0002\u00079,GOC\u0001$\u0003\u0015Q\u0017M^1y\u0013\t)sD\u0001\tI_N$h.Y7f-\u0016\u0014\u0018NZ5fe\u0006AQn\u001b'pO\u001e,'\u000f\u0005\u0002)W5\t\u0011F\u0003\u0002+\u001b\u0005!Q\u000f^5m\u0013\ta\u0013FA\u0007M_\u001e<WM\u001d$bGR|'/_\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005=\n\u0004C\u0001\u0019\u0001\u001b\u0005Y\u0001\"\u0002\u0014\u0003\u0001\u00049\u0013A\u00027pO\u001e,'/F\u00015!\tAS'\u0003\u00027S\taaj\u001c#faNdunZ4fe\u00069An\\4hKJ\u0004\u0013a\u00045pgRt\u0017-\\3DQ\u0016\u001c7.\u001a:\u0016\u0003i\u0002\"aO!\u000e\u0003qR!AK\u001f\u000b\u0005yz\u0014\u0001C:fGV\u0014\u0018\u000e^=\u000b\u0003\u0001\u000b1a];o\u0013\t\u0011EHA\bI_N$h.Y7f\u0007\",7m[3s\u00035i\u0017\r^2i\u0017\u0016\u0014(-\u001a:pgR\u0019Qi\u0013-\u0011\u0005\u0019KU\"A$\u000b\u0003!\u000bQa]2bY\u0006L!AS$\u0003\u000f\t{w\u000e\\3b]\")AJ\u0002a\u0001\u001b\u0006A\u0001n\\:u]\u0006lW\r\u0005\u0002O+:\u0011qj\u0015\t\u0003!\u001ek\u0011!\u0015\u0006\u0003%N\ta\u0001\u0010:p_Rt\u0014B\u0001+H\u0003\u0019\u0001&/\u001a3fM&\u0011ak\u0016\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005Q;\u0005\"B-\u0007\u0001\u0004Q\u0016!\u00039sS:\u001c\u0017\u000e]1m!\tYV,D\u0001]\u0015\tq\u0014$\u0003\u0002_9\nI\u0001K]5oG&\u0004\u0018\r\\\u0001\u000bSN\\UM\u001d2fe>\u001cHCA#b\u0011\u0015Iv\u00011\u0001[\u0003\u00191XM]5gsR\u0019Q\tZ3\t\u000b1C\u0001\u0019A'\t\u000b\u0019D\u0001\u0019A4\u0002\u000fM,7o]5p]B\u0011a\u0004[\u0005\u0003S~\u0011!bU*M'\u0016\u001c8/[8o\u0003Ei\u0017\r^2i\u0007\u0016\u0014H/\u001b4jG\u0006$Xm\u001d\u000b\u0004\u000b2l\u0007\"\u0002'\n\u0001\u0004i\u0005\"\u00028\n\u0001\u0004y\u0017\u0001\u00059fKJ\u001cUM\u001d;jM&\u001c\u0017\r^3t!\r1\u0005O]\u0005\u0003c\u001e\u0013Q!\u0011:sCf\u0004\"a\u001d<\u000e\u0003QT!!\u001e/\u0002\t\r,'\u000f^\u0005\u0003oR\u00141bQ3si&4\u0017nY1uK\":\u0001!\u001f?~\u007f\u0006\u0005\u0001C\u0001${\u0013\tYxI\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001\u007f\u00039$UMZ1vYRDun\u001d;oC6,g+\u001a:jM&,'\u000f\t5bg\u0002\u0012W-\u001a8!I\u0016\u0004(/Z2bi\u0016$\u0007%\u00198eA\u0011|Wm\u001d\u0011o_RD\u0017N\\4/A\u0001\u0002F.Z1tK\u0002*8/\u001a\u0011uQ\u0016\u0004#.\u0019<bq:rW\r\u001e\u0018eK\n,x\rI:zgR,W\u000e\t9s_B,'\u000f^=/\u0003\u0015\u0019\u0018N\\2fC\t\t\u0019!A\u00031]Qr\u0003\u0007")
public class DefaultHostnameVerifier
implements HostnameVerifier {
    private final NoDepsLogger logger;

    private NoDepsLogger logger() {
        return this.logger;
    }

    public HostnameChecker hostnameChecker() {
        this.logger().warn("DefaultHostnameVerifier has been deprecated and does nothing.  Please use the javax.net.debug system property.");
        return HostnameChecker.getInstance((byte)1);
    }

    public boolean matchKerberos(String hostname, Principal principal) {
        this.logger().warn("DefaultHostnameVerifier has been deprecated and does nothing.  Please use the javax.net.debug system property.");
        return true;
    }

    public boolean isKerberos(Principal principal) {
        this.logger().warn("DefaultHostnameVerifier has been deprecated and does nothing.  Please use the javax.net.debug system property.");
        return true;
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        this.logger().warn("DefaultHostnameVerifier has been deprecated and does nothing.  Please use the javax.net.debug system property.");
        return true;
    }

    public boolean matchCertificates(String hostname, Certificate[] peerCertificates2) {
        this.logger().warn("DefaultHostnameVerifier has been deprecated and does nothing.  Please use the javax.net.debug system property.");
        return true;
    }

    public DefaultHostnameVerifier(LoggerFactory mkLogger) {
        this.logger = mkLogger.apply(this.getClass());
    }
}

