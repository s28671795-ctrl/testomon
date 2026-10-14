/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.InternalApi;
import akka.util.HashCode$;
import akka.util.Version$;
import scala.Array$;
import scala.MatchError;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.StringOps$;
import scala.math.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichChar$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005q!B\f\u0019\u0011\u0003ib!B\u0010\u0019\u0011\u0003\u0001\u0003\"B\u0014\u0002\t\u0003A\u0003bB\u0015\u0002\u0005\u0004%\tA\u000b\u0005\u0007s\u0006\u0001\u000b\u0011B\u0016\t\u000fi\f!\u0019!C\u0005w\"1A0\u0001Q\u0001\n)CQ!`\u0001\u0005\u0002y4Aa\b\r\u0003Y!AQ\u0007\u0003BC\u0002\u0013\u0005a\u0007\u0003\u0005C\u0011\t\u0005\t\u0015!\u00038\u0011\u00159\u0003\u0002\"\u0001D\u0011\u001d)\u0005\u00021A\u0005\n\u0019Cq!\u0014\u0005A\u0002\u0013%a\n\u0003\u0004U\u0011\u0001\u0006Ka\u0012\u0005\b3\"\u0001\r\u0011\"\u00037\u0011\u001dQ\u0006\u00021A\u0005\nmCa!\u0018\u0005!B\u00139\u0004B\u00020\t\t\u0003Qr\fC\u0003h\u0011\u0011\u0005\u0003\u000eC\u0003l\u0011\u0011\u0005C\u000eC\u0003v\u0011\u0011\u0005c\u000fC\u0003x\u0011\u0011\u0005\u00030A\u0004WKJ\u001c\u0018n\u001c8\u000b\u0005eQ\u0012\u0001B;uS2T\u0011aG\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005y\tQ\"\u0001\r\u0003\u000fY+'o]5p]N\u0011\u0011!\t\t\u0003E\u0015j\u0011a\t\u0006\u0002I\u0005)1oY1mC&\u0011ae\t\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005i\u0012\u0001\u0002.fe>,\u0012a\u000b\t\u0003=!\u00192\u0001C\u0011.!\rq3gK\u0007\u0002_)\u0011\u0001'M\u0001\u0005Y\u0006twMC\u00013\u0003\u0011Q\u0017M^1\n\u0005Qz#AC\"p[B\f'/\u00192mK\u00069a/\u001a:tS>tW#A\u001c\u0011\u0005azdBA\u001d>!\tQ4%D\u0001<\u0015\taD$\u0001\u0004=e>|GOP\u0005\u0003}\r\na\u0001\u0015:fI\u00164\u0017B\u0001!B\u0005\u0019\u0019FO]5oO*\u0011ahI\u0001\tm\u0016\u00148/[8oAQ\u00111\u0006\u0012\u0005\u0006k-\u0001\raN\u0001\b]Vl'-\u001a:t+\u00059\u0005c\u0001\u0012I\u0015&\u0011\u0011j\t\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u0003E-K!\u0001T\u0012\u0003\u0007%sG/A\u0006ok6\u0014WM]:`I\u0015\fHCA(S!\t\u0011\u0003+\u0003\u0002RG\t!QK\\5u\u0011\u001d\u0019V\"!AA\u0002\u001d\u000b1\u0001\u001f\u00132\u0003!qW/\u001c2feN\u0004\u0003F\u0001\bW!\t\u0011s+\u0003\u0002YG\tAao\u001c7bi&dW-\u0001\u0003sKN$\u0018\u0001\u0003:fgR|F%Z9\u0015\u0005=c\u0006bB*\u0011\u0003\u0003\u0005\raN\u0001\u0006e\u0016\u001cH\u000fI\u0001\u0006a\u0006\u00148/\u001a\u000b\u0002W!\u0012!#\u0019\t\u0003E\u0016l\u0011a\u0019\u0006\u0003Ij\t!\"\u00198o_R\fG/[8o\u0013\t17MA\u0006J]R,'O\\1m\u0003BL\u0017!C2p[B\f'/\u001a+p)\tQ\u0015\u000eC\u0003k'\u0001\u00071&A\u0003pi\",'/\u0001\u0004fcV\fGn\u001d\u000b\u0003[B\u0004\"A\t8\n\u0005=\u001c#a\u0002\"p_2,\u0017M\u001c\u0005\u0006cR\u0001\rA]\u0001\u0002_B\u0011!e]\u0005\u0003i\u000e\u00121!\u00118z\u0003!A\u0017m\u001d5D_\u0012,G#\u0001&\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012aN\u0001\u00065\u0016\u0014x\u000eI\u0001\n+:$WMZ5oK\u0012,\u0012AS\u0001\u000b+:$WMZ5oK\u0012\u0004\u0013!B1qa2LHCA\u0016\u0000\u0011\u0015)t\u00011\u00018\u0001")
public final class Version
implements Comparable<Version> {
    private final String version;
    private volatile int[] numbers;
    private String rest;

    public static Version apply(String version) {
        return Version$.MODULE$.apply(version);
    }

    public static Version Zero() {
        return Version$.MODULE$.Zero();
    }

    public String version() {
        return this.version;
    }

    private int[] numbers() {
        return this.numbers;
    }

    private void numbers_$eq(int[] x$1) {
        this.numbers = x$1;
    }

    private String rest() {
        return this.rest;
    }

    private void rest_$eq(String x$1) {
        this.rest = x$1;
    }

    @InternalApi
    public Version parse() {
        block13: {
            String string;
            if (this.numbers().length != 0) break block13;
            int[] nbrs = new int[4];
            String[] segments = StringOps$.MODULE$.split$extension(Predef$.MODULE$.augmentString(this.version()), '.');
            if (segments.length == 1) {
                String s2 = segments[0];
                if (s2.isEmpty()) {
                    throw new IllegalArgumentException("Empty version not supported.");
                }
                nbrs[1] = Version$.MODULE$.akka$util$Version$$Undefined();
                nbrs[2] = Version$.MODULE$.akka$util$Version$$Undefined();
                nbrs[3] = Version$.MODULE$.akka$util$Version$$Undefined();
                if (RichChar$.MODULE$.isDigit$extension(Predef$.MODULE$.charWrapper(s2.charAt(0)))) {
                    try {
                        nbrs[0] = StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(s2));
                        string = "";
                    }
                    catch (NumberFormatException numberFormatException) {
                        string = s2;
                    }
                } else {
                    string = s2;
                }
            } else if (segments.length == 2) {
                Tuple3 tuple3 = Version.parseLastParts$1(segments[1]);
                if (tuple3 == null) {
                    throw new MatchError(tuple3);
                }
                int n1 = BoxesRunTime.unboxToInt(tuple3._1());
                int n2 = BoxesRunTime.unboxToInt(tuple3._2());
                String rest = (String)tuple3._3();
                Tuple3<Integer, Integer, String> tuple32 = new Tuple3<Integer, Integer, String>(BoxesRunTime.boxToInteger(n1), BoxesRunTime.boxToInteger(n2), rest);
                int n12 = BoxesRunTime.unboxToInt(tuple32._1());
                int n22 = BoxesRunTime.unboxToInt(tuple32._2());
                String rest2 = tuple32._3();
                nbrs[0] = StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(segments[0]));
                nbrs[1] = n12;
                nbrs[2] = n22;
                nbrs[3] = Version$.MODULE$.akka$util$Version$$Undefined();
                string = rest2;
            } else if (segments.length == 3) {
                Tuple3 tuple3 = Version.parseLastParts$1(segments[2]);
                if (tuple3 == null) {
                    throw new MatchError(tuple3);
                }
                int n1 = BoxesRunTime.unboxToInt(tuple3._1());
                int n2 = BoxesRunTime.unboxToInt(tuple3._2());
                String rest = (String)tuple3._3();
                Tuple3<Integer, Integer, String> tuple33 = new Tuple3<Integer, Integer, String>(BoxesRunTime.boxToInteger(n1), BoxesRunTime.boxToInteger(n2), rest);
                int n13 = BoxesRunTime.unboxToInt(tuple33._1());
                int n23 = BoxesRunTime.unboxToInt(tuple33._2());
                String rest3 = tuple33._3();
                nbrs[0] = StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(segments[0]));
                nbrs[1] = StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(segments[1]));
                nbrs[2] = n13;
                nbrs[3] = n23;
                string = rest3;
            } else {
                throw new IllegalArgumentException(new StringBuilder(50).append("Only 3 digits separated with '.' are supported. [").append(this.version()).append("]").toString());
            }
            String rst = string;
            this.rest_$eq(rst);
            this.numbers_$eq(nbrs);
        }
        return this;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int compareTo(Version other) {
        String string = this.version();
        String string2 = other.version();
        if (string == null) {
            if (string2 == null) return 0;
        } else if (string.equals(string2)) {
            return 0;
        }
        this.parse();
        other.parse();
        int diff = 0;
        diff = this.numbers()[0] - other.numbers()[0];
        if (diff != 0) return diff;
        diff = this.numbers()[1] - other.numbers()[1];
        if (diff != 0) return diff;
        diff = this.numbers()[2] - other.numbers()[2];
        if (diff != 0) return diff;
        diff = this.numbers()[3] - other.numbers()[3];
        if (diff != 0) return diff;
        String string3 = this.rest();
        String string4 = "";
        if (!(string3 != null ? !string3.equals(string4) : string4 != null)) {
            String string5 = other.rest();
            String string6 = "";
            if (string5 == null ? string6 != null : !string5.equals(string6)) {
                diff = 1;
            }
        }
        String string7 = other.rest();
        String string8 = "";
        if (string7 == null) {
            if (string8 != null) {
                return this.rest().compareTo(other.rest());
            }
        } else if (!string7.equals(string8)) return this.rest().compareTo(other.rest());
        String string9 = this.rest();
        String string10 = "";
        if (string9 == null) {
            if (string10 == null) return this.rest().compareTo(other.rest());
            return -1;
        } else if (string9.equals(string10)) return this.rest().compareTo(other.rest());
        return -1;
    }

    public boolean equals(Object o) {
        Object object = o;
        if (object instanceof Version) {
            Version version = (Version)object;
            return this.compareTo(version) == 0;
        }
        return false;
    }

    public int hashCode() {
        this.parse();
        int result = HashCode$.MODULE$.SEED();
        result = HashCode$.MODULE$.hash(result, this.numbers()[0]);
        result = HashCode$.MODULE$.hash(result, this.numbers()[1]);
        result = HashCode$.MODULE$.hash(result, this.numbers()[2]);
        result = HashCode$.MODULE$.hash(result, this.numbers()[3]);
        result = HashCode$.MODULE$.hash(result, this.rest());
        return result;
    }

    public String toString() {
        return this.version();
    }

    private static final Tuple2 parseLastPart$1(String s2) {
        int k;
        if (s2.length() == 0) {
            return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(Version$.MODULE$.akka$util$Version$$Undefined())), s2);
        }
        int i = s2.indexOf(45);
        int j = s2.indexOf(43);
        int n = i == -1 ? j : (k = j == -1 ? i : package$.MODULE$.min(i, j));
        if (k == -1) {
            return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(s2)))), "");
        }
        return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(s2.substring(0, k))))), s2.substring(k + 1));
    }

    private static final Tuple2 parseDynverPart$1(String s2) {
        Tuple2<Integer, String> tuple2;
        if (s2.isEmpty() || !RichChar$.MODULE$.isDigit$extension(Predef$.MODULE$.charWrapper(s2.charAt(0)))) {
            return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(Version$.MODULE$.akka$util$Version$$Undefined())), s2);
        }
        int n = s2.indexOf(45);
        switch (n) {
            case -1: {
                return Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(Version$.MODULE$.akka$util$Version$$Undefined())), s2);
            }
        }
        try {
            tuple2 = Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(StringOps$.MODULE$.toInt$extension(Predef$.MODULE$.augmentString(s2.substring(0, n))))), s2.substring(n + 1));
        }
        catch (NumberFormatException numberFormatException) {
            tuple2 = Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(Version$.MODULE$.akka$util$Version$$Undefined())), s2);
        }
        return tuple2;
    }

    private static final Tuple3 parseLastParts$1(String s2) {
        String rest;
        Tuple2 tuple2 = Version.parseLastPart$1(s2);
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        int lastNumber = tuple2._1$mcI$sp();
        String rest2 = (String)tuple2._2();
        Tuple2<Integer, String> tuple22 = new Tuple2<Integer, String>(BoxesRunTime.boxToInteger(lastNumber), rest2);
        int lastNumber2 = tuple22._1$mcI$sp();
        String string = rest = tuple22._2();
        String string2 = "";
        if (!(string != null ? !string.equals(string2) : string2 != null)) {
            return new Tuple3<Integer, Integer, String>(BoxesRunTime.boxToInteger(lastNumber2), BoxesRunTime.boxToInteger(Version$.MODULE$.akka$util$Version$$Undefined()), rest);
        }
        Tuple2 tuple23 = Version.parseDynverPart$1(rest);
        if (tuple23 == null) {
            throw new MatchError(tuple23);
        }
        int dynverNumber = tuple23._1$mcI$sp();
        String rest22 = (String)tuple23._2();
        Tuple2<Integer, String> tuple24 = new Tuple2<Integer, String>(BoxesRunTime.boxToInteger(dynverNumber), rest22);
        int dynverNumber2 = tuple24._1$mcI$sp();
        String rest23 = tuple24._2();
        return new Tuple3<Integer, Integer, String>(BoxesRunTime.boxToInteger(lastNumber2), BoxesRunTime.boxToInteger(dynverNumber2), rest23);
    }

    public Version(String version) {
        this.version = version;
        this.numbers = Array$.MODULE$.emptyIntArray();
        this.rest = "";
    }
}

