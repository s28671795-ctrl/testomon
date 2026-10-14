/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.debug;

import com.typesafe.sslconfig.ssl.debug.FixLoggingAction;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q4A\u0001F\u000b\u0001A!Aq\u0005\u0001B\u0001B\u0003%\u0001\u0006C\u0003/\u0001\u0011\u0005q\u0006C\u00044\u0001\t\u0007I\u0011\u0002\u001b\t\ra\u0002\u0001\u0015!\u00036\r\u0011I\u0004\u0001\u0001\u001e\t\u0011y*!Q1A\u0005\u0002}B\u0001bS\u0003\u0003\u0002\u0003\u0006I\u0001\u0011\u0005\u0006]\u0015!\t\u0001\u0014\u0005\bg\u0015\u0011\r\u0011\"\u00115\u0011\u0019AT\u0001)A\u0005k!9\u0001+\u0002b\u0001\n\u0003\t\u0006BB-\u0006A\u0003%!\u000bC\u0004[\u000b\t\u0007I\u0011A)\t\rm+\u0001\u0015!\u0003S\u0011\u0015aV\u0001\"\u0001^\u0011\u0015\u0019W\u0001\"\u0001e\u0011\u0015)W\u0001\"\u0001g\u0011\u0015QW\u0001\"\u0001l\u0011\u0015Q\u0007\u0001\"\u0001x\u0005]1\u0015\u000e_%oi\u0016\u0014h.\u00197EK\n,x\rT8hO&twM\u0003\u0002\u0017/\u0005)A-\u001a2vO*\u0011\u0001$G\u0001\u0004gNd'B\u0001\u000e\u001c\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u0002\u001d;\u0005AA/\u001f9fg\u00064WMC\u0001\u001f\u0003\r\u0019w.\\\u0002\u0001'\t\u0001\u0011\u0005\u0005\u0002#K5\t1EC\u0001%\u0003\u0015\u00198-\u00197b\u0013\t13E\u0001\u0004B]f\u0014VMZ\u0001\t[.dunZ4feB\u0011\u0011\u0006L\u0007\u0002U)\u00111&G\u0001\u0005kRLG.\u0003\u0002.U\tiAj\\4hKJ4\u0015m\u0019;pef\fa\u0001P5oSRtDC\u0001\u00193!\t\t\u0004!D\u0001\u0016\u0011\u00159#\u00011\u0001)\u0003\u0019awnZ4feV\tQ\u0007\u0005\u0002*m%\u0011qG\u000b\u0002\r\u001d>$U\r]:M_\u001e<WM]\u0001\bY><w-\u001a:!\u0005\u0005juN\\6fsB\u000bGo\u00195J]R,'O\\1m'NdG)\u001a2vO\u0006\u001bG/[8o'\t)1\b\u0005\u00022y%\u0011Q(\u0006\u0002\u0011\r&DHj\\4hS:<\u0017i\u0019;j_:\f!B\\3x\u001fB$\u0018n\u001c8t+\u0005\u0001\u0005CA!I\u001d\t\u0011e\t\u0005\u0002DG5\tAI\u0003\u0002F?\u00051AH]8pizJ!aR\u0012\u0002\rA\u0013X\rZ3g\u0013\tI%J\u0001\u0004TiJLgn\u001a\u0006\u0003\u000f\u000e\n1B\\3x\u001fB$\u0018n\u001c8tAQ\u0011Qj\u0014\t\u0003\u001d\u0016i\u0011\u0001\u0001\u0005\u0006}!\u0001\r\u0001Q\u0001\u0010S:LG/[1m%\u0016\u001cx.\u001e:dKV\t!\u000b\u0005\u0002T16\tAK\u0003\u0002V-\u0006!A.\u00198h\u0015\u00059\u0016\u0001\u00026bm\u0006L!!\u0013+\u0002!%t\u0017\u000e^5bYJ+7o\\;sG\u0016\u0004\u0013A\u00043fEV<7\t\\1tg:\u000bW.Z\u0001\u0010I\u0016\u0014WoZ\"mCN\u001ch*Y7fA\u0005a\u0011n\u001d,bY&$7\t\\1tgR\u0011a,\u0019\t\u0003E}K!\u0001Y\u0012\u0003\u000f\t{w\u000e\\3b]\")!m\u0004a\u0001\u0001\u0006I1\r\\1tg:\u000bW.Z\u0001\rSN,6/\u001b8h\t\u0016\u0014WoZ\u000b\u0002=\u0006\u0019!/\u001e8\u0015\u0003\u001d\u0004\"A\t5\n\u0005%\u001c#\u0001B+oSR\fQ!\u00199qYf$\"a\u001a7\t\u000by\u0012\u0002\u0019\u0001!)\r\u0015q\u0017O\u001d;v!\t\u0011s.\u0003\u0002qG\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\n1/\u0001=N_:\\W-\u001f)bi\u000eD\u0017J\u001c;fe:\fGnU:m\t\u0016\u0014WoZ!di&|g\u000e\t5bg\u0002\u0012W-\u001a8!I\u0016\u0004(/Z2bi\u0016$\u0007%\u00198eA\u0011|Wm\u001d\u0011o_RD\u0017N\\4/A\u0001\u0002F.Z1tK\u0002*8/\u001a\u0011uQ\u0016\u0004#.\u0019<bq:rW\r\u001e\u0018eK\n,x\rI:zgR,W\u000e\t9s_B,'\u000f^=/\u0003\u0015\u0019\u0018N\\2fC\u00051\u0018!\u0002\u0019/i9\u0002DCA4y\u0011\u0015q4\u00031\u0001AQ\u0019\u0001a.\u001d>uk\u0006\n10\u00018GSbLe\u000e^3s]\u0006dG)\u001a2vO2{wmZ5oO\u0002B\u0017m\u001d\u0011cK\u0016t\u0007\u0005Z3qe\u0016\u001c\u0017\r^3eA\u0005tG\r\t3pKN\u0004cn\u001c;iS:<g\u0006\t\u0011QY\u0016\f7/\u001a\u0011vg\u0016\u0004C\u000f[3!U\u00064\u0018\r\u001f\u0018oKRtC-\u001a2vO\u0002\u001a\u0018p\u001d;f[\u0002\u0002(o\u001c9feRLh\u0006")
public class FixInternalDebugLogging {
    public final LoggerFactory com$typesafe$sslconfig$ssl$debug$FixInternalDebugLogging$$mkLogger;
    private final NoDepsLogger logger;

    private NoDepsLogger logger() {
        return this.logger;
    }

    public void apply(String newOptions) {
        this.logger().warn("FixInternalDebugLogging has been deprecated and does nothing.  Please use the javax.net.debug system property.");
    }

    public FixInternalDebugLogging(LoggerFactory mkLogger) {
        this.com$typesafe$sslconfig$ssl$debug$FixInternalDebugLogging$$mkLogger = mkLogger;
        this.logger = mkLogger.apply("com.typesafe.sslconfig.ssl.debug.FixInternalDebugLogging");
    }

    public class MonkeyPatchInternalSslDebugAction
    extends FixLoggingAction {
        private final String newOptions;
        private final NoDepsLogger logger;
        private final String initialResource;
        private final String debugClassName;
        public final /* synthetic */ FixInternalDebugLogging $outer;

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

        public String debugClassName() {
            return this.debugClassName;
        }

        @Override
        public boolean isValidClass(String className) {
            this.logger().warn("MonkeyPatchInternalSslDebugAction has been deprecated and does nothing.  Please use the javax.net.debug system property.");
            if (className.startsWith("com.sun.net.ssl.internal.ssl")) {
                return true;
            }
            return className.startsWith("sun.security.ssl");
        }

        public boolean isUsingDebug() {
            this.logger().warn("MonkeyPatchInternalSslDebugAction has been deprecated and does nothing.  Please use the javax.net.debug system property.");
            return this.newOptions() != null && !this.newOptions().isEmpty();
        }

        @Override
        public void run() {
            this.logger().warn("MonkeyPatchInternalSslDebugAction has been deprecated and does nothing.  Please use the javax.net.debug system property.");
        }

        public void apply(String newOptions) {
            this.logger().warn("MonkeyPatchInternalSslDebugAction has been deprecated and does nothing.  Please use the javax.net.debug system property.");
        }

        public /* synthetic */ FixInternalDebugLogging com$typesafe$sslconfig$ssl$debug$FixInternalDebugLogging$MonkeyPatchInternalSslDebugAction$$$outer() {
            return this.$outer;
        }

        public MonkeyPatchInternalSslDebugAction(FixInternalDebugLogging $outer, String newOptions) {
            this.newOptions = newOptions;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            this.logger = $outer.com$typesafe$sslconfig$ssl$debug$FixInternalDebugLogging$$mkLogger.apply("com.typesafe.sslconfig.ssl.debug.FixInternalDebugLogging.MonkeyPatchInternalSslDebugAction");
            this.initialResource = "/sun/security/ssl/Debug.class";
            this.debugClassName = "sun.security.ssl.Debug";
        }
    }
}

