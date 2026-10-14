/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.debug;

import com.typesafe.sslconfig.ssl.debug.FixLoggingAction;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import scala.None$;
import scala.Option;
import scala.reflect.ScalaSignature;
import sun.security.util.Debug;

@ScalaSignature(bytes="\u0006\u0005\u0005uc\u0001B\u000e\u001d\u0001\u001dB\u0001B\f\u0001\u0003\u0002\u0003\u0006Ia\f\u0005\u0006k\u0001!\tA\u000e\u0005\bu\u0001\u0011\r\u0011\"\u0001<\u0011\u0019y\u0004\u0001)A\u0005y\u0019!\u0001\t\u0001\u0001B\u0011!)UA!b\u0001\n\u00031\u0005\u0002\u0003)\u0006\u0005\u0003\u0005\u000b\u0011B$\t\u0011E+!Q1A\u0005\u0002IC\u0001BX\u0003\u0003\u0002\u0003\u0006Ia\u0015\u0005\u0006k\u0015!\ta\u0018\u0005\bu\u0015\u0011\r\u0011\"\u0001<\u0011\u0019yT\u0001)A\u0005y!9A-\u0002b\u0001\n\u0003)\u0007BB7\u0006A\u0003%a\rC\u0004o\u000b\t\u0007I\u0011A8\t\rM,\u0001\u0015!\u0003q\u0011\u0015!X\u0001\"\u0001v\u0011\u0015YX\u0001\"\u0001}\u0011\u0015iX\u0001\"\u0001\u007f\r\u0019\tI\u0002\u0001\u0001\u0002\u001c!A!\b\u0006B\u0001B\u0003%A\b\u0003\u00046)\u0011\u0005\u0011Q\u0004\u0005\b\u0003G!B\u0011IA\u0013\u0011\u0019\t\u0019\u0003\u0006C!}\"9\u0011q\u0006\u0001\u0005\u0002\u0005E\u0002\"CA \u0001E\u0005I\u0011AA!\u0005]1\u0015\u000e_\"feR\u0004\u0018\r\u001e5EK\n,x\rT8hO&twM\u0003\u0002\u001e=\u0005)A-\u001a2vO*\u0011q\u0004I\u0001\u0004gNd'BA\u0011#\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u0002$I\u0005AA/\u001f9fg\u00064WMC\u0001&\u0003\r\u0019w.\\\u0002\u0001'\t\u0001\u0001\u0006\u0005\u0002*Y5\t!FC\u0001,\u0003\u0015\u00198-\u00197b\u0013\ti#F\u0001\u0004B]f\u0014VMZ\u0001\t[.dunZ4feB\u0011\u0001gM\u0007\u0002c)\u0011!\u0007I\u0001\u0005kRLG.\u0003\u00025c\tiAj\\4hKJ4\u0015m\u0019;pef\fa\u0001P5oSRtDCA\u001c:!\tA\u0004!D\u0001\u001d\u0011\u0015q#\u00011\u00010\u0003\u0019awnZ4feV\tA\b\u0005\u00021{%\u0011a(\r\u0002\r\u001d>$U\r]:M_\u001e<WM]\u0001\bY><w-\u001a:!\u0005\u0015juN\\6fsB\u000bGo\u00195Tk:\u001cVmY;sSRLX\u000b^5m\t\u0016\u0014WoZ!di&|gn\u0005\u0002\u0006\u0005B\u0011\u0001hQ\u0005\u0003\tr\u0011\u0001CR5y\u0019><w-\u001b8h\u0003\u000e$\u0018n\u001c8\u0002\u00119,w\u000fR3ck\u001e,\u0012a\u0012\t\u0003\u0011:k\u0011!\u0013\u0006\u0003e)S!a\u0013'\u0002\u0011M,7-\u001e:jifT\u0011!T\u0001\u0004gVt\u0017BA(J\u0005\u0015!UMY;h\u0003%qWm\u001e#fEV<\u0007%\u0001\u0006oK^|\u0005\u000f^5p]N,\u0012a\u0015\t\u0003)ns!!V-\u0011\u0005YSS\"A,\u000b\u0005a3\u0013A\u0002\u001fs_>$h(\u0003\u0002[U\u00051\u0001K]3eK\u001aL!\u0001X/\u0003\rM#(/\u001b8h\u0015\tQ&&A\u0006oK^|\u0005\u000f^5p]N\u0004Cc\u00011cGB\u0011\u0011-B\u0007\u0002\u0001!)QI\u0003a\u0001\u000f\")\u0011K\u0003a\u0001'\u0006y\u0011N\\5uS\u0006d'+Z:pkJ\u001cW-F\u0001g!\t9G.D\u0001i\u0015\tI'.\u0001\u0003mC:<'\"A6\u0002\t)\fg/Y\u0005\u00039\"\f\u0001#\u001b8ji&\fGNU3t_V\u00148-\u001a\u0011\u0002\u0013\u0011,'-^4UsB,W#\u00019\u0011\u0007\u001d\fx)\u0003\u0002sQ\n)1\t\\1tg\u0006QA-\u001a2vORK\b/\u001a\u0011\u0002\u0019%\u001ch+\u00197jI\u000ec\u0017m]:\u0015\u0005YL\bCA\u0015x\u0013\tA(FA\u0004C_>dW-\u00198\t\u000bi\f\u0002\u0019A*\u0002\u0013\rd\u0017m]:OC6,\u0017\u0001D5t+NLgn\u001a#fEV<W#\u0001<\u0002\u0007I,h\u000eF\u0001\u0000!\rI\u0013\u0011A\u0005\u0004\u0003\u0007Q#\u0001B+oSRD3\"BA\u0004\u0003\u001b\ty!a\u0005\u0002\u0016A\u0019\u0011&!\u0003\n\u0007\u0005-!F\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-\t\u0002\u0002\u0012\u0005aXj\u001c8lKf\u0004\u0016\r^2i'Vt7+Z2ve&$\u00180\u0016;jY\u0012+'-^4BGRLwN\u001c\u0011iCN\u0004#-Z3oA\u0011,\u0007O]3dCR,G\rI1oI\u0002\"w.Z:!]>$\b.\u001b8h]\u0001\u0002\u0003\u000b\\3bg\u0016\u0004So]3!i\",\u0007E[1wCbtc.\u001a;/I\u0016\u0014Wo\u001a\u0011tsN$X-\u001c\u0011qe>\u0004XM\u001d;z]\u0005)1/\u001b8dK\u0006\u0012\u0011qC\u0001\u0006a9\"d\u0006\r\u0002\u001b'Vt7+Z2ve&$\u00180\u0016;jY\u0012+'-^4M_\u001e<WM]\n\u0003)\u001d#B!a\b\u0002\"A\u0011\u0011\r\u0006\u0005\u0006uY\u0001\r\u0001P\u0001\baJLg\u000e\u001e7o)\ry\u0018q\u0005\u0005\u0007\u0003\u001b9\u0002\u0019A*)\u0017Q\t9!!\u0004\u0002,\u0005M\u0011QC\u0011\u0003\u0003[\t\u0011oU;o'\u0016\u001cWO]5usV#\u0018\u000e\u001c#fEV<Gj\\4hKJ\u0004\u0003.Y:!E\u0016,g\u000e\t3faJ,7-\u0019;fI\u0002\ng\u000e\u001a\u0011e_\u0016\u001c\bE\\8uQ&twM\f\u0011!!2,\u0017m]3!kN,\u0007\u0005\u001e5fA)\fg/\u0019=/]\u0016$h\u0006Z3ck\u001e\u00043/_:uK6\u0004\u0003O]8qKJ$\u0018PL\u0001\u0006CB\u0004H.\u001f\u000b\u0006\u007f\u0006M\u0012Q\u0007\u0005\u0006#f\u0001\ra\u0015\u0005\n\u0003oI\u0002\u0013!a\u0001\u0003s\t1\u0002Z3ck\u001e|\u0005\u000f^5p]B!\u0011&a\u000fH\u0013\r\tiD\u000b\u0002\u0007\u001fB$\u0018n\u001c8\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uII*\"!a\u0011+\t\u0005e\u0012QI\u0016\u0003\u0003\u000f\u0002B!!\u0013\u0002T5\u0011\u00111\n\u0006\u0005\u0003\u001b\ny%A\u0005v]\u000eDWmY6fI*\u0019\u0011\u0011\u000b\u0016\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002V\u0005-#!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\"Z\u0001!a\u0002\u0002\u000e\u0005e\u00131CA\u000bC\t\tY&\u00018GSb\u001cUM\u001d;qCRDG)\u001a2vO2{wmZ5oO\u0002B\u0017m\u001d\u0011cK\u0016t\u0007\u0005Z3qe\u0016\u001c\u0017\r^3eA\u0005tG\r\t3pKN\u0004cn\u001c;iS:<g\u0006\t\u0011QY\u0016\f7/\u001a\u0011vg\u0016\u0004C\u000f[3!U\u00064\u0018\r\u001f\u0018oKRtC-\u001a2vO\u0002\u001a\u0018p\u001d;f[\u0002\u0002(o\u001c9feRLh\u0006")
public class FixCertpathDebugLogging {
    public final LoggerFactory com$typesafe$sslconfig$ssl$debug$FixCertpathDebugLogging$$mkLogger;
    private final NoDepsLogger logger;

    public NoDepsLogger logger() {
        return this.logger;
    }

    public void apply(String newOptions, Option<Debug> debugOption) {
        this.logger().warn("FixCertpathDebugLogging has been deprecated and does nothing.  Please use the javax.net.debug system property.");
    }

    public Option<Debug> apply$default$2() {
        return None$.MODULE$;
    }

    public FixCertpathDebugLogging(LoggerFactory mkLogger) {
        this.com$typesafe$sslconfig$ssl$debug$FixCertpathDebugLogging$$mkLogger = mkLogger;
        this.logger = mkLogger.apply("com.typesafe.sslconfig.ssl.debug.FixCertpathDebugLogging");
    }

    public class MonkeyPatchSunSecurityUtilDebugAction
    extends FixLoggingAction {
        private final Debug newDebug;
        private final String newOptions;
        private final NoDepsLogger logger;
        private final String initialResource;
        private final Class<Debug> debugType;
        public final /* synthetic */ FixCertpathDebugLogging $outer;

        public Debug newDebug() {
            return this.newDebug;
        }

        @Override
        public String newOptions() {
            return this.newOptions;
        }

        @Override
        public NoDepsLogger logger() {
            return this.logger;
        }

        @Override
        public String initialResource() {
            return this.initialResource;
        }

        public Class<Debug> debugType() {
            return this.debugType;
        }

        @Override
        public boolean isValidClass(String className) {
            this.logger().warn("MonkeyPatchSunSecurityUtilDebugAction has been deprecated and does nothing.  Please use the javax.net.debug system property.");
            if (className.startsWith("java.security.cert")) {
                return true;
            }
            if (className.startsWith("sun.security.provider.certpath")) {
                return true;
            }
            return className.equals("sun.security.x509.InhibitAnyPolicyExtension");
        }

        public boolean isUsingDebug() {
            this.logger().warn("MonkeyPatchSunSecurityUtilDebugAction has been deprecated and does nothing.  Please use the javax.net.debug system property.");
            return this.newOptions() != null && this.newOptions().contains("certpath");
        }

        @Override
        public void run() {
            this.logger().warn("MonkeyPatchSunSecurityUtilDebugAction has been deprecated and does nothing.  Please use the javax.net.debug system property.");
        }

        public /* synthetic */ FixCertpathDebugLogging com$typesafe$sslconfig$ssl$debug$FixCertpathDebugLogging$MonkeyPatchSunSecurityUtilDebugAction$$$outer() {
            return this.$outer;
        }

        public MonkeyPatchSunSecurityUtilDebugAction(FixCertpathDebugLogging $outer, Debug newDebug, String newOptions) {
            this.newDebug = newDebug;
            this.newOptions = newOptions;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            this.logger = $outer.com$typesafe$sslconfig$ssl$debug$FixCertpathDebugLogging$$mkLogger.apply("com.typesafe.sslconfig.ssl.debug.FixCertpathDebugLogging.MonkeyPatchSunSecurityUtilDebugAction");
            this.initialResource = "/sun/security/provider/certpath/Builder.class";
            this.debugType = Debug.class;
        }
    }

    public class SunSecurityUtilDebugLogger
    extends Debug {
        @Override
        public void println(String message) {
        }

        @Override
        public void println() {
        }

        public /* synthetic */ FixCertpathDebugLogging com$typesafe$sslconfig$ssl$debug$FixCertpathDebugLogging$SunSecurityUtilDebugLogger$$$outer() {
            return FixCertpathDebugLogging.this;
        }

        public SunSecurityUtilDebugLogger(NoDepsLogger logger2) {
            if (FixCertpathDebugLogging.this == null) {
                throw null;
            }
        }
    }
}

