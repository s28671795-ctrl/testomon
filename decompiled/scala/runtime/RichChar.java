/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Proxy;
import scala.collection.immutable.NumericRange;
import scala.math.Numeric$CharIsIntegral$;
import scala.math.Ordered;
import scala.math.Ordering$Char$;
import scala.math.ScalaNumericAnyConversions;
import scala.reflect.ScalaSignature;
import scala.runtime.IntegralProxy;
import scala.runtime.OrderedProxy;
import scala.runtime.RichChar$;
import scala.runtime.ScalaNumberProxy;
import scala.runtime.ScalaWholeNumberProxy;

@ScalaSignature(bytes="\u0006\u0005\t\u001df\u0001B*U\u0005eC\u0001\"\u001a\u0001\u0003\u0006\u0004%\tA\u001a\u0005\tO\u0002\u0011\t\u0011)A\u0005E\")\u0001\u000e\u0001C\u0001S\")A\u000e\u0001C\t[\")a\u0010\u0001C\t\u007f\"9\u0011q\u0002\u0001\u0005B\u0005E\u0001bBA\r\u0001\u0011\u0005\u00131\u0004\u0005\b\u0003G\u0001A\u0011IA\u0013\u0011\u001d\ti\u0003\u0001C!\u0003_Aq!a\u000e\u0001\t\u0003\nI\u0004C\u0004\u0002B\u0001!\t%a\u0011\t\u000f\u0005-\u0003\u0001\"\u0011\u0002N!1\u0011Q\u000b\u0001\u0005B\u0019Dq!a\u0016\u0001\t\u0003\nI\u0006C\u0004\u0002`\u0001!\t%!\u0019\t\u000f\u0005\u0015\u0004\u0001\"\u0001\u00020!9\u0011q\r\u0001\u0005\u0002\u00055\u0003bBA5\u0001\u0011\u0005\u0011Q\n\u0005\b\u0003W\u0002A\u0011AA'\u0011\u001d\ti\u0007\u0001C\u0001\u0003\u001bBq!a\u001c\u0001\t\u0003\ti\u0005C\u0004\u0002r\u0001!\t!!\u0014\t\u000f\u0005M\u0004\u0001\"\u0001\u0002N!9\u0011Q\u000f\u0001\u0005\u0002\u00055\u0003bBA<\u0001\u0011\u0005\u0011Q\n\u0005\b\u0003s\u0002A\u0011AA'\u0011\u001d\tY\b\u0001C\u0001\u0003\u001bBq!! \u0001\t\u0003\ti\u0005C\u0004\u0002\u0000\u0001!\t!!\u0014\t\u000f\u0005\u0005\u0005\u0001\"\u0001\u0002N!9\u00111\u0011\u0001\u0005\u0002\u00055\u0003bBAC\u0001\u0011\u0005\u0011Q\n\u0005\u0007\u0003\u000f\u0003A\u0011\u00014\t\r\u0005%\u0005\u0001\"\u0001g\u0011\u0019\tY\t\u0001C\u0001M\"9\u0011Q\u0012\u0001\u0005\u0002\u0005=\u0002bBAH\u0001\u0011\u0005\u0011q\u0006\u0005\b\u0003#\u0003A\u0011AA\u001d\u0011\u0019\t\u0019\n\u0001C\u0001M\"I\u0011Q\u0013\u0001\u0002\u0002\u0013\u0005\u0013q\u0013\u0005\n\u00033\u0003\u0011\u0011!C!\u00037;\u0011\"a*U\u0003\u0003E\t!!+\u0007\u0011M#\u0016\u0011!E\u0001\u0003WCa\u0001[\u0016\u0005\u0002\u0005M\u0006bBA[W\u0011\u0015\u0011q\u0017\u0005\b\u0003{[CQAA`\u0011\u001d\t\u0019m\u000bC\u0003\u0003\u000bDq!!3,\t\u000b\tY\rC\u0004\u0002P.\")!!5\t\u000f\u0005U7\u0006\"\u0002\u0002X\"9\u00111\\\u0016\u0005\u0006\u0005u\u0007bBAqW\u0011\u0015\u00111\u001d\u0005\b\u0003O\\CQAAu\u0011\u001d\tio\u000bC\u0003\u0003_Dq!a=,\t\u000b\t)\u0010C\u0004\u0002~.\")!a@\t\u000f\t\u001d1\u0006\"\u0002\u0003\n!9!QB\u0016\u0005\u0006\t=\u0001b\u0002B\nW\u0011\u0015!Q\u0003\u0005\b\u00053YCQ\u0001B\u000e\u0011\u001d\u0011yb\u000bC\u0003\u0005CAqA!\n,\t\u000b\u00119\u0003C\u0004\u0003,-\")A!\f\t\u000f\tE2\u0006\"\u0002\u00034!9!qG\u0016\u0005\u0006\te\u0002b\u0002B\u001fW\u0011\u0015!q\b\u0005\b\u0005\u0007ZCQ\u0001B#\u0011\u001d\u0011Ie\u000bC\u0003\u0005\u0017BqAa\u0014,\t\u000b\u0011\t\u0006C\u0004\u0003V-\")Aa\u0016\t\u000f\tm3\u0006\"\u0002\u0003^!9!\u0011M\u0016\u0005\u0006\t\r\u0004b\u0002B4W\u0011\u0015!\u0011\u000e\u0005\b\u0005[ZCQ\u0001B8\u0011\u001d\u0011\u0019h\u000bC\u0003\u0005kBqA!\u001f,\t\u000b\u0011Y\bC\u0004\u0003\u0000-\")A!!\t\u000f\t\u00155\u0006\"\u0002\u0003\b\"9!1R\u0016\u0005\u0006\t5\u0005b\u0002BIW\u0011\u0015!1\u0013\u0005\n\u0005/[\u0013\u0011!C\u0003\u00053C\u0011B!(,\u0003\u0003%)Aa(\u0003\u0011IK7\r[\"iCJT!!\u0016,\u0002\u000fI,h\u000e^5nK*\tq+A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001Qf\f\u0005\u0002\\96\ta+\u0003\u0002^-\n1\u0011I\\=WC2\u00042a\u00181c\u001b\u0005!\u0016BA1U\u00055Ie\u000e^3he\u0006d\u0007K]8ysB\u00111lY\u0005\u0003IZ\u0013Aa\u00115be\u0006!1/\u001a7g+\u0005\u0011\u0017!B:fY\u001a\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002kWB\u0011q\f\u0001\u0005\u0006K\u000e\u0001\rAY\u0001\u0004]VlW#\u00018\u0011\u0005=\\hB\u00019y\u001d\t\thO\u0004\u0002sk6\t1O\u0003\u0002u1\u00061AH]8pizJ\u0011aV\u0005\u0003oZ\u000bA!\\1uQ&\u0011\u0011P_\u0001\b\u001dVlWM]5d\u0015\t9hK\u0003\u0002}{\u0006q1\t[1s\u0013NLe\u000e^3he\u0006d'BA={\u0003\ry'\u000fZ\u000b\u0003\u0003\u0003\u0001B!a\u0001\u0002\n9\u0019\u0001/!\u0002\n\u0007\u0005\u001d!0\u0001\u0005Pe\u0012,'/\u001b8h\u0015\u0011\tY!!\u0004\u0002\t\rC\u0017M\u001d\u0006\u0004\u0003\u000fQ\u0018a\u00033pk\ndWMV1mk\u0016,\"!a\u0005\u0011\u0007m\u000b)\"C\u0002\u0002\u0018Y\u0013a\u0001R8vE2,\u0017A\u00034m_\u0006$h+\u00197vKV\u0011\u0011Q\u0004\t\u00047\u0006}\u0011bAA\u0011-\n)a\t\\8bi\u0006IAn\u001c8h-\u0006dW/Z\u000b\u0003\u0003O\u00012aWA\u0015\u0013\r\tYC\u0016\u0002\u0005\u0019>tw-\u0001\u0005j]R4\u0016\r\\;f+\t\t\t\u0004E\u0002\\\u0003gI1!!\u000eW\u0005\rIe\u000e^\u0001\nEf$XMV1mk\u0016,\"!a\u000f\u0011\u0007m\u000bi$C\u0002\u0002@Y\u0013AAQ=uK\u0006Q1\u000f[8siZ\u000bG.^3\u0016\u0005\u0005\u0015\u0003cA.\u0002H%\u0019\u0011\u0011\n,\u0003\u000bMCwN\u001d;\u0002\u0017%\u001ch+\u00197jI\u000eC\u0017M]\u000b\u0003\u0003\u001f\u00022aWA)\u0013\r\t\u0019F\u0016\u0002\b\u0005>|G.Z1o\u0003\r\t'm]\u0001\u0004[\u0006DHc\u00012\u0002\\!1\u0011Q\f\bA\u0002\t\fA\u0001\u001e5bi\u0006\u0019Q.\u001b8\u0015\u0007\t\f\u0019\u0007\u0003\u0004\u0002^=\u0001\rAY\u0001\bCN$\u0015nZ5u\u0003%I7oQ8oiJ|G.A\u0004jg\u0012Kw-\u001b;\u0002\u0011%\u001cH*\u001a;uKJ\fq\"[:MKR$XM](s\t&<\u0017\u000e^\u0001\rSN<\u0006.\u001b;fgB\f7-Z\u0001\fSN\u001c\u0006/Y2f\u0007\"\f'/A\bjg\"Kw\r[*veJ|w-\u0019;f\u00039I7\u000fT8x'V\u0014(o\\4bi\u0016\f1\"[:TkJ\u0014xnZ1uK\u0006A\u0012n]+oS\u000e|G-Z%eK:$\u0018NZ5feN#\u0018M\u001d;\u0002/%\u001cXK\\5d_\u0012,\u0017\nZ3oi&4\u0017.\u001a:QCJ$\u0018!F5t\u0013\u0012,g\u000e^5gS\u0016\u0014\u0018j\u001a8pe\u0006\u0014G.Z\u0001\u000bSNl\u0015N\u001d:pe\u0016$\u0017aB5t\u0019><XM]\u0001\bSN,\u0006\u000f]3s\u0003-I7\u000fV5uY\u0016\u001c\u0015m]3\u0002\u000fQ|Gj\\<fe\u00069Ao\\+qa\u0016\u0014\u0018a\u0003;p)&$H.Z\"bg\u0016\fqaZ3u)f\u0004X-A\bhKRtU/\\3sS\u000e4\u0016\r\\;f\u0003E9W\r\u001e#je\u0016\u001cG/[8oC2LG/_\u0001\re\u00164XM]:f\u0005f$Xm]\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011\u0011G\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005=\u0013Q\u0014\u0005\n\u0003?K\u0013\u0011!a\u0001\u0003C\u000b1\u0001\u001f\u00132!\rY\u00161U\u0005\u0004\u0003K3&aA!os\u0006A!+[2i\u0007\"\f'\u000f\u0005\u0002`WM\u00191&!,\u0011\u0007m\u000by+C\u0002\u00022Z\u0013a!\u00118z%\u00164GCAAU\u00035qW/\u001c\u0013fqR,gn]5p]R\u0019a.!/\t\r\u0005mV\u00061\u0001k\u0003\u0015!C\u000f[5t\u00035y'\u000f\u001a\u0013fqR,gn]5p]R!\u0011\u0011AAa\u0011\u0019\tYL\fa\u0001U\u0006)Bm\\;cY\u00164\u0016\r\\;fI\u0015DH/\u001a8tS>tG\u0003BA\n\u0003\u000fDa!a/0\u0001\u0004Q\u0017\u0001\u00064m_\u0006$h+\u00197vK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002\u001e\u00055\u0007BBA^a\u0001\u0007!.A\nm_:<g+\u00197vK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002(\u0005M\u0007BBA^c\u0001\u0007!.\u0001\nj]R4\u0016\r\\;fI\u0015DH/\u001a8tS>tG\u0003BA\u0019\u00033Da!a/3\u0001\u0004Q\u0017a\u00052zi\u00164\u0016\r\\;fI\u0015DH/\u001a8tS>tG\u0003BA\u001e\u0003?Da!a/4\u0001\u0004Q\u0017\u0001F:i_J$h+\u00197vK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002F\u0005\u0015\bBBA^i\u0001\u0007!.A\u000bjgZ\u000bG.\u001b3DQ\u0006\u0014H%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\u0005=\u00131\u001e\u0005\u0007\u0003w+\u0004\u0019\u00016\u0002\u001b\u0005\u00147\u000fJ3yi\u0016t7/[8o)\r\u0011\u0017\u0011\u001f\u0005\u0007\u0003w3\u0004\u0019\u00016\u0002\u001b5\f\u0007\u0010J3yi\u0016t7/[8o)\u0011\t90a?\u0015\u0007\t\fI\u0010\u0003\u0004\u0002^]\u0002\rA\u0019\u0005\u0007\u0003w;\u0004\u0019\u00016\u0002\u001b5Lg\u000eJ3yi\u0016t7/[8o)\u0011\u0011\tA!\u0002\u0015\u0007\t\u0014\u0019\u0001\u0003\u0004\u0002^a\u0002\rA\u0019\u0005\u0007\u0003wC\u0004\u0019\u00016\u0002#\u0005\u001cH)[4ji\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u00022\t-\u0001BBA^s\u0001\u0007!.A\njg\u000e{g\u000e\u001e:pY\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002P\tE\u0001BBA^u\u0001\u0007!.A\tjg\u0012Kw-\u001b;%Kb$XM\\:j_:$B!a\u0014\u0003\u0018!1\u00111X\u001eA\u0002)\f!#[:MKR$XM\u001d\u0013fqR,gn]5p]R!\u0011q\nB\u000f\u0011\u0019\tY\f\u0010a\u0001U\u0006I\u0012n\u001d'fiR,'o\u0014:ES\u001eLG\u000fJ3yi\u0016t7/[8o)\u0011\tyEa\t\t\r\u0005mV\b1\u0001k\u0003YI7o\u00165ji\u0016\u001c\b/Y2fI\u0015DH/\u001a8tS>tG\u0003BA(\u0005SAa!a/?\u0001\u0004Q\u0017!F5t'B\f7-Z\"iCJ$S\r\u001f;f]NLwN\u001c\u000b\u0005\u0003\u001f\u0012y\u0003\u0003\u0004\u0002<~\u0002\rA[\u0001\u001aSND\u0015n\u001a5TkJ\u0014xnZ1uK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002P\tU\u0002BBA^\u0001\u0002\u0007!.\u0001\rjg2{woU;se><\u0017\r^3%Kb$XM\\:j_:$B!a\u0014\u0003<!1\u00111X!A\u0002)\fQ#[:TkJ\u0014xnZ1uK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002P\t\u0005\u0003BBA^\u0005\u0002\u0007!.\u0001\u0012jgVs\u0017nY8eK&#WM\u001c;jM&,'o\u0015;beR$S\r\u001f;f]NLwN\u001c\u000b\u0005\u0003\u001f\u00129\u0005\u0003\u0004\u0002<\u000e\u0003\rA[\u0001\"SN,f.[2pI\u0016LE-\u001a8uS\u001aLWM\u001d)beR$S\r\u001f;f]NLwN\u001c\u000b\u0005\u0003\u001f\u0012i\u0005\u0003\u0004\u0002<\u0012\u0003\rA[\u0001 SNLE-\u001a8uS\u001aLWM]%h]>\u0014\u0018M\u00197fI\u0015DH/\u001a8tS>tG\u0003BA(\u0005'Ba!a/F\u0001\u0004Q\u0017\u0001F5t\u001b&\u0014(o\u001c:fI\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002P\te\u0003BBA^\r\u0002\u0007!.A\tjg2{w/\u001a:%Kb$XM\\:j_:$B!a\u0014\u0003`!1\u00111X$A\u0002)\f\u0011#[:VaB,'\u000fJ3yi\u0016t7/[8o)\u0011\tyE!\u001a\t\r\u0005m\u0006\n1\u0001k\u0003UI7\u000fV5uY\u0016\u001c\u0015m]3%Kb$XM\\:j_:$B!a\u0014\u0003l!1\u00111X%A\u0002)\f\u0011\u0003^8M_^,'\u000fJ3yi\u0016t7/[8o)\r\u0011'\u0011\u000f\u0005\u0007\u0003wS\u0005\u0019\u00016\u0002#Q|W\u000b\u001d9fe\u0012*\u0007\u0010^3og&|g\u000eF\u0002c\u0005oBa!a/L\u0001\u0004Q\u0017!\u0006;p)&$H.Z\"bg\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0004E\nu\u0004BBA^\u0019\u0002\u0007!.A\thKR$\u0016\u0010]3%Kb$XM\\:j_:$B!!\r\u0003\u0004\"1\u00111X'A\u0002)\f\u0011dZ3u\u001dVlWM]5d-\u0006dW/\u001a\u0013fqR,gn]5p]R!\u0011\u0011\u0007BE\u0011\u0019\tYL\u0014a\u0001U\u0006Yr-\u001a;ESJ,7\r^5p]\u0006d\u0017\u000e^=%Kb$XM\\:j_:$B!a\u000f\u0003\u0010\"1\u00111X(A\u0002)\faC]3wKJ\u001cXMQ=uKN$S\r\u001f;f]NLwN\u001c\u000b\u0004E\nU\u0005BBA^!\u0002\u0007!.\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tG\u0003BAL\u00057Ca!a/R\u0001\u0004Q\u0017\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\u0011\u0011\tK!*\u0015\t\u0005=#1\u0015\u0005\n\u0003?\u0013\u0016\u0011!a\u0001\u0003CCa!a/S\u0001\u0004Q\u0007")
public final class RichChar
implements IntegralProxy<Object> {
    private final char self;

    public static boolean equals$extension(char $this, Object x$1) {
        return RichChar$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(char $this) {
        return Character.hashCode($this);
    }

    public static char reverseBytes$extension(char $this) {
        return Character.reverseBytes($this);
    }

    public static byte getDirectionality$extension(char $this) {
        return Character.getDirectionality($this);
    }

    public static int getNumericValue$extension(char $this) {
        return Character.getNumericValue($this);
    }

    public static int getType$extension(char $this) {
        return Character.getType($this);
    }

    public static char toTitleCase$extension(char $this) {
        return Character.toTitleCase($this);
    }

    public static char toUpper$extension(char $this) {
        return Character.toUpperCase($this);
    }

    public static char toLower$extension(char $this) {
        return Character.toLowerCase($this);
    }

    public static boolean isTitleCase$extension(char $this) {
        return Character.isTitleCase($this);
    }

    public static boolean isUpper$extension(char $this) {
        return Character.isUpperCase($this);
    }

    public static boolean isLower$extension(char $this) {
        return Character.isLowerCase($this);
    }

    public static boolean isMirrored$extension(char $this) {
        return Character.isMirrored($this);
    }

    public static boolean isIdentifierIgnorable$extension(char $this) {
        return Character.isIdentifierIgnorable($this);
    }

    public static boolean isUnicodeIdentifierPart$extension(char $this) {
        return Character.isUnicodeIdentifierPart($this);
    }

    public static boolean isUnicodeIdentifierStart$extension(char $this) {
        return Character.isUnicodeIdentifierStart($this);
    }

    public static boolean isSurrogate$extension(char $this) {
        return RichChar$.MODULE$.isSurrogate$extension($this);
    }

    public static boolean isLowSurrogate$extension(char $this) {
        return Character.isLowSurrogate($this);
    }

    public static boolean isHighSurrogate$extension(char $this) {
        return Character.isHighSurrogate($this);
    }

    public static boolean isSpaceChar$extension(char $this) {
        return Character.isSpaceChar($this);
    }

    public static boolean isWhitespace$extension(char $this) {
        return Character.isWhitespace($this);
    }

    public static boolean isLetterOrDigit$extension(char $this) {
        return Character.isLetterOrDigit($this);
    }

    public static boolean isLetter$extension(char $this) {
        return Character.isLetter($this);
    }

    public static boolean isDigit$extension(char $this) {
        return Character.isDigit($this);
    }

    public static boolean isControl$extension(char $this) {
        return Character.isISOControl($this);
    }

    public static int asDigit$extension(char $this) {
        return Character.digit($this, 36);
    }

    public static char min$extension(char $this, char that) {
        return (char)Math.min($this, that);
    }

    public static char max$extension(char $this, char that) {
        return (char)Math.max($this, that);
    }

    public static char abs$extension(char $this) {
        return $this;
    }

    public static boolean isValidChar$extension(char $this) {
        return true;
    }

    public static short shortValue$extension(char $this) {
        return (short)$this;
    }

    public static byte byteValue$extension(char $this) {
        return (byte)$this;
    }

    public static int intValue$extension(char $this) {
        return $this;
    }

    public static long longValue$extension(char $this) {
        return $this;
    }

    public static float floatValue$extension(char $this) {
        return $this;
    }

    public static double doubleValue$extension(char $this) {
        return $this;
    }

    public static Ordering$Char$ ord$extension(char $this) {
        return Ordering$Char$.MODULE$;
    }

    public static Numeric$CharIsIntegral$ num$extension(char $this) {
        return Numeric$CharIsIntegral$.MODULE$;
    }

    @Override
    public NumericRange.Exclusive until(Object end) {
        return IntegralProxy.until$(this, end);
    }

    @Override
    public NumericRange.Exclusive until(Object end, Object step) {
        return IntegralProxy.until$(this, end, step);
    }

    @Override
    public NumericRange.Inclusive to(Object end) {
        return IntegralProxy.to$(this, end);
    }

    @Override
    public NumericRange.Inclusive to(Object end, Object step) {
        return IntegralProxy.to$(this, end, step);
    }

    @Override
    public boolean isWhole() {
        return ScalaWholeNumberProxy.isWhole$(this);
    }

    @Override
    public Object sign() {
        return ScalaNumberProxy.sign$(this);
    }

    @Override
    public int signum() {
        return ScalaNumberProxy.signum$(this);
    }

    @Override
    public int compare(Object y) {
        return OrderedProxy.compare$(this, y);
    }

    @Override
    public boolean $less(Object that) {
        return Ordered.$less$(this, that);
    }

    @Override
    public boolean $greater(Object that) {
        return Ordered.$greater$(this, that);
    }

    @Override
    public boolean $less$eq(Object that) {
        return Ordered.$less$eq$(this, that);
    }

    @Override
    public boolean $greater$eq(Object that) {
        return Ordered.$greater$eq$(this, that);
    }

    @Override
    public int compareTo(Object that) {
        return Ordered.compareTo$(this, that);
    }

    @Override
    public String toString() {
        return Proxy.toString$(this);
    }

    @Override
    public char toChar() {
        return ScalaNumericAnyConversions.toChar$(this);
    }

    @Override
    public byte toByte() {
        return ScalaNumericAnyConversions.toByte$(this);
    }

    @Override
    public short toShort() {
        return ScalaNumericAnyConversions.toShort$(this);
    }

    @Override
    public int toInt() {
        return ScalaNumericAnyConversions.toInt$(this);
    }

    @Override
    public long toLong() {
        return ScalaNumericAnyConversions.toLong$(this);
    }

    @Override
    public float toFloat() {
        return ScalaNumericAnyConversions.toFloat$(this);
    }

    @Override
    public double toDouble() {
        return ScalaNumericAnyConversions.toDouble$(this);
    }

    @Override
    public boolean isValidByte() {
        return ScalaNumericAnyConversions.isValidByte$(this);
    }

    @Override
    public boolean isValidShort() {
        return ScalaNumericAnyConversions.isValidShort$(this);
    }

    @Override
    public boolean isValidInt() {
        return ScalaNumericAnyConversions.isValidInt$(this);
    }

    @Override
    public int unifiedPrimitiveHashcode() {
        return ScalaNumericAnyConversions.unifiedPrimitiveHashcode$(this);
    }

    @Override
    public boolean unifiedPrimitiveEquals(Object x) {
        return ScalaNumericAnyConversions.unifiedPrimitiveEquals$(this, x);
    }

    @Override
    public char self() {
        return this.self;
    }

    public Numeric$CharIsIntegral$ num() {
        this.self();
        return Numeric$CharIsIntegral$.MODULE$;
    }

    public Ordering$Char$ ord() {
        this.self();
        return Ordering$Char$.MODULE$;
    }

    @Override
    public double doubleValue() {
        return this.self();
    }

    @Override
    public float floatValue() {
        return this.self();
    }

    @Override
    public long longValue() {
        return this.self();
    }

    @Override
    public int intValue() {
        return this.self();
    }

    @Override
    public byte byteValue() {
        return (byte)this.self();
    }

    @Override
    public short shortValue() {
        return (short)this.self();
    }

    @Override
    public boolean isValidChar() {
        this.self();
        return true;
    }

    @Override
    public char abs() {
        return this.self();
    }

    @Override
    public char max(char that) {
        char max$extension_$this = this.self();
        return (char)Math.max(max$extension_$this, that);
    }

    @Override
    public char min(char that) {
        char min$extension_$this = this.self();
        return (char)Math.min(min$extension_$this, that);
    }

    public int asDigit() {
        return Character.digit(this.self(), 36);
    }

    public boolean isControl() {
        return Character.isISOControl(this.self());
    }

    public boolean isDigit() {
        return Character.isDigit(this.self());
    }

    public boolean isLetter() {
        return Character.isLetter(this.self());
    }

    public boolean isLetterOrDigit() {
        return Character.isLetterOrDigit(this.self());
    }

    public boolean isWhitespace() {
        return Character.isWhitespace(this.self());
    }

    public boolean isSpaceChar() {
        return Character.isSpaceChar(this.self());
    }

    public boolean isHighSurrogate() {
        return Character.isHighSurrogate(this.self());
    }

    public boolean isLowSurrogate() {
        return Character.isLowSurrogate(this.self());
    }

    public boolean isSurrogate() {
        return RichChar$.MODULE$.isSurrogate$extension(this.self());
    }

    public boolean isUnicodeIdentifierStart() {
        return Character.isUnicodeIdentifierStart(this.self());
    }

    public boolean isUnicodeIdentifierPart() {
        return Character.isUnicodeIdentifierPart(this.self());
    }

    public boolean isIdentifierIgnorable() {
        return Character.isIdentifierIgnorable(this.self());
    }

    public boolean isMirrored() {
        return Character.isMirrored(this.self());
    }

    public boolean isLower() {
        return Character.isLowerCase(this.self());
    }

    public boolean isUpper() {
        return Character.isUpperCase(this.self());
    }

    public boolean isTitleCase() {
        return Character.isTitleCase(this.self());
    }

    public char toLower() {
        return Character.toLowerCase(this.self());
    }

    public char toUpper() {
        return Character.toUpperCase(this.self());
    }

    public char toTitleCase() {
        return Character.toTitleCase(this.self());
    }

    public int getType() {
        return Character.getType(this.self());
    }

    public int getNumericValue() {
        return Character.getNumericValue(this.self());
    }

    public byte getDirectionality() {
        return Character.getDirectionality(this.self());
    }

    public char reverseBytes() {
        return Character.reverseBytes(this.self());
    }

    @Override
    public int hashCode() {
        return Character.hashCode(this.self());
    }

    @Override
    public boolean equals(Object x$1) {
        return RichChar$.MODULE$.equals$extension(this.self(), x$1);
    }

    public RichChar(char self) {
        this.self = self;
    }
}

