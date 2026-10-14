/*
 * Decompiled with CFR 0.152.
 */
package akka;

import akka.UnsupportedAkkaVersion;
import akka.Version$;
import akka.annotation.InternalApi;
import scala.Option;
import scala.Predef$;
import scala.Tuple4;
import scala.collection.LinearSeqOps;
import scala.collection.StringOps$;
import scala.collection.immutable.List;
import scala.runtime.Nothing$;
import scala.util.matching.Regex;

public final class AkkaVersion$ {
    public static final AkkaVersion$ MODULE$ = new AkkaVersion$();

    public void require(String libraryName, String requiredVersion) {
        this.require(libraryName, requiredVersion, Version$.MODULE$.current());
    }

    /*
     * Enabled aggressive block sorting
     */
    @InternalApi
    public void require(String libraryName, String requiredVersion, String currentVersion) {
        String string = requiredVersion;
        String string2 = currentVersion;
        if (string == null) {
            if (string2 == null) return;
        } else if (string.equals(string2)) return;
        Regex VersionPattern2 = StringOps$.MODULE$.r$extension(Predef$.MODULE$.augmentString("(\\d+)\\.(\\d+)\\.(\\d+)(?:-(M|RC)(\\d+))?"));
        String string3 = currentVersion;
        if (string3 == null) return;
        Option<List<String>> option = VersionPattern2.unapplySeq(string3);
        if (option.isEmpty()) return;
        if (option.get() == null) return;
        if (option.get().lengthCompare(5) != 0) return;
        String currentMajorStr = (String)((LinearSeqOps)option.get()).apply(0);
        String currentMinorStr = (String)((LinearSeqOps)option.get()).apply(1);
        String currentPatchStr = (String)((LinearSeqOps)option.get()).apply(2);
        String mOrRc = (String)((LinearSeqOps)option.get()).apply(3);
        String mOrRcNrStr = (String)((LinearSeqOps)option.get()).apply(4);
        String string4 = requiredVersion;
        if (string4 == null) return;
        Option<List<String>> option2 = VersionPattern2.unapplySeq(string4);
        if (option2.isEmpty()) return;
        if (option2.get() == null) return;
        if (option2.get().lengthCompare(5) != 0) return;
        String requiredMajorStr = (String)((LinearSeqOps)option2.get()).apply(0);
        String requiredMinorStr = (String)((LinearSeqOps)option2.get()).apply(1);
        String requiredPatchStr = (String)((LinearSeqOps)option2.get()).apply(2);
        String requiredMorRc = (String)((LinearSeqOps)option2.get()).apply(3);
        String requiredMOrRcNrStr = (String)((LinearSeqOps)option2.get()).apply(4);
        String string5 = requiredMajorStr;
        String string6 = currentMajorStr;
        if (string5 == null) {
            if (string6 != null) {
                throw AkkaVersion$.throwUnsupported$1(currentVersion, libraryName, string4);
            }
        } else if (!string5.equals(string6)) throw AkkaVersion$.throwUnsupported$1(currentVersion, libraryName, string4);
        String string7 = requiredMinorStr;
        String string8 = currentMinorStr;
        if (!(string7 != null ? !string7.equals(string8) : string8 != null)) {
            String string9 = requiredPatchStr;
            String string10 = currentPatchStr;
            if (!(string9 != null ? !string9.equals(string10) : string10 != null)) {
                if (requiredMorRc == null && mOrRc != null) {
                    throw AkkaVersion$.throwUnsupported$1(currentVersion, libraryName, string4);
                }
                if (requiredMorRc == null) return;
                Tuple4<String, String, String, String> tuple4 = new Tuple4<String, String, String, String>(requiredMorRc, requiredMOrRcNrStr, mOrRc, mOrRcNrStr);
                if (tuple4 != null) {
                    String string11 = tuple4._1();
                    String reqN = tuple4._2();
                    String string12 = tuple4._3();
                    String n = tuple4._4();
                    if ("M".equals(string11) && "M".equals(string12)) {
                        if (StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(reqN)) <= StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(n))) return;
                        throw AkkaVersion$.throwUnsupported$1(currentVersion, libraryName, string4);
                    }
                }
                if (tuple4 != null) {
                    String string13 = tuple4._1();
                    String string14 = tuple4._3();
                    if ("M".equals(string13) && "RC".equals(string14)) {
                        return;
                    }
                }
                if (tuple4 != null) {
                    String string15 = tuple4._1();
                    String string16 = tuple4._3();
                    if ("RC".equals(string15) && "M".equals(string16)) {
                        throw AkkaVersion$.throwUnsupported$1(currentVersion, libraryName, string4);
                    }
                }
                if (tuple4 != null) {
                    String string17 = tuple4._1();
                    String reqN = tuple4._2();
                    String string18 = tuple4._3();
                    String n = tuple4._4();
                    if ("RC".equals(string17) && "RC".equals(string18)) {
                        if (StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(reqN)) <= StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(n))) return;
                        throw AkkaVersion$.throwUnsupported$1(currentVersion, libraryName, string4);
                    }
                }
                if (tuple4 == null) throw new IllegalArgumentException(new StringBuilder(37).append("Should never happen, comparing M/RC: ").append(tuple4).toString());
                String string19 = tuple4._3();
                if (string19 != null) throw new IllegalArgumentException(new StringBuilder(37).append("Should never happen, comparing M/RC: ").append(tuple4).toString());
                return;
            }
            if (StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(requiredPatchStr)) <= StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(currentPatchStr))) return;
            throw AkkaVersion$.throwUnsupported$1(currentVersion, libraryName, string4);
        }
        if (StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(requiredMinorStr)) <= StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(currentMinorStr))) return;
        throw AkkaVersion$.throwUnsupported$1(currentVersion, libraryName, string4);
    }

    private static final Nothing$ throwUnsupported$1(String currentVersion$1, String libraryName$1, String x1$1) {
        throw new UnsupportedAkkaVersion(new StringBuilder(55).append("Current version of Akka is [").append(currentVersion$1).append("], but ").append(libraryName$1).append(" requires version [").append(x1$1).append("]").toString());
    }

    private AkkaVersion$() {
    }
}

