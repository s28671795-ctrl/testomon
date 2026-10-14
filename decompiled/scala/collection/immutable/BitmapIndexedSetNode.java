/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.util.Arrays;
import scala.Array$;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.collection.Hashing$;
import scala.collection.IterableOnce;
import scala.collection.immutable.HashCollisionSetNode;
import scala.collection.immutable.Node;
import scala.collection.immutable.Node$;
import scala.collection.immutable.SetNode;
import scala.collection.immutable.SetNode$;
import scala.collection.immutable.Vector$;
import scala.collection.mutable.AbstractBuffer;
import scala.collection.mutable.Queue;
import scala.collection.mutable.Queue$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\tmf\u0001B\u001c9\r}B\u0001\"\u0015\u0001\u0003\u0002\u0004%\tA\u0015\u0005\t-\u0002\u0011\t\u0019!C\u0001/\"AQ\f\u0001B\u0001B\u0003&1\u000b\u0003\u0005_\u0001\t\u0005\r\u0011\"\u0001S\u0011!y\u0006A!a\u0001\n\u0003\u0001\u0007\u0002\u00032\u0001\u0005\u0003\u0005\u000b\u0015B*\t\u0011\r\u0004!\u00111A\u0005\u0002\u0011D\u0001\u0002\u001b\u0001\u0003\u0002\u0004%\t!\u001b\u0005\tW\u0002\u0011\t\u0011)Q\u0005K\"AA\u000e\u0001BA\u0002\u0013\u0005Q\u000e\u0003\u0005p\u0001\t\u0005\r\u0011\"\u0001q\u0011!\u0011\bA!A!B\u0013q\u0007\u0002C:\u0001\u0005\u0003\u0007I\u0011\u0001*\t\u0011Q\u0004!\u00111A\u0005\u0002UD\u0001b\u001e\u0001\u0003\u0002\u0003\u0006Ka\u0015\u0005\tq\u0002\u0011\t\u0019!C\u0001%\"A\u0011\u0010\u0001BA\u0002\u0013\u0005!\u0010\u0003\u0005}\u0001\t\u0005\t\u0015)\u0003T\u0011\u0015i\b\u0001\"\u0001\u007f\u0011\u001d\ti\u0001\u0001C\u0001\u0003\u001fAq!!\u0006\u0001\t\u0003\n9\u0002C\u0004\u0002\u001c\u0001!\t!!\b\t\u000f\u0005\u0005\u0002\u0001\"\u0001\u0002$!9\u00111\b\u0001\u0005\u0002\u0005u\u0002bBA$\u0001\u0011\u0005\u0011\u0011\n\u0005\b\u0003/\u0002A\u0011AA-\u0011\u001d\t\u0019\u0007\u0001C\u0001\u0003KBq!a\u001c\u0001\t\u0003\t\t\bC\u0004\u0002\u000e\u0002!\t!a$\t\r\u0005E\u0005\u0001\"\u0001S\u0011\u001d\t\u0019\n\u0001C\u0001\u0003\u001fCa!!&\u0001\t\u0003\u0011\u0006bBAL\u0001\u0011\u0005\u0011\u0011\u0014\u0005\b\u0003?\u0003A\u0011AAQ\u0011\u001d\t)\u000b\u0001C\u0001\u0003OCq!a-\u0001\t\u0003\t)\fC\u0004\u0002B\u0002!\t!a1\t\u000f\u00055\u0007\u0001\"\u0001\u0002P\"9\u0011Q\u001b\u0001\u0005\u0002\u0005]\u0007bBAq\u0001\u0011\u0005\u00111\u001d\u0005\b\u0003[\u0004A\u0011AAx\u0011\u001d\tI\u0010\u0001C\u0001\u0003wDqAa\u0002\u0001\t\u0003\u0011I\u0001C\u0004\u0003\u001e\u0001!\tAa\b\t\u000f\t\u001d\u0002\u0001\"\u0011\u0003*!9!Q\u0007\u0001\u0005B\t]\u0002\u0002\u0003B\u001f\u0001\u0001&IAa\u0010\t\u000f\te\u0004\u0001\"\u0011\u0003|!9!q\u0010\u0001\u0005\n\t\u0005\u0005b\u0002BL\u0001\u0011\u0005#\u0011\u0014\u0005\b\u00057\u0003A\u0011\tBO\u0011\u001d\u0011y\n\u0001C!\u0005CCqAa*\u0001\t\u0003\u0012I\u000bC\u0004\u00034\u0002!\tE!.\u0003)\tKG/\\1q\u0013:$W\r_3e'\u0016$hj\u001c3f\u0015\tI$(A\u0005j[6,H/\u00192mK*\u00111\bP\u0001\u000bG>dG.Z2uS>t'\"A\u001f\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011\u0001iR\n\u0003\u0001\u0005\u00032AQ\"F\u001b\u0005A\u0014B\u0001#9\u0005\u001d\u0019V\r\u001e(pI\u0016\u0004\"AR$\r\u0001\u0011)\u0001\n\u0001b\u0001\u0013\n\t\u0011)\u0005\u0002K\u001dB\u00111\nT\u0007\u0002y%\u0011Q\n\u0010\u0002\b\u001d>$\b.\u001b8h!\tYu*\u0003\u0002Qy\t\u0019\u0011I\\=\u0002\u000f\u0011\fG/Y'baV\t1\u000b\u0005\u0002L)&\u0011Q\u000b\u0010\u0002\u0004\u0013:$\u0018a\u00033bi\u0006l\u0015\r]0%KF$\"\u0001W.\u0011\u0005-K\u0016B\u0001.=\u0005\u0011)f.\u001b;\t\u000fq\u0013\u0011\u0011!a\u0001'\u0006\u0019\u0001\u0010J\u0019\u0002\u0011\u0011\fG/Y'ba\u0002\nqA\\8eK6\u000b\u0007/A\u0006o_\u0012,W*\u00199`I\u0015\fHC\u0001-b\u0011\u001daV!!AA\u0002M\u000b\u0001B\\8eK6\u000b\u0007\u000fI\u0001\bG>tG/\u001a8u+\u0005)\u0007cA&g\u001d&\u0011q\r\u0010\u0002\u0006\u0003J\u0014\u0018-_\u0001\fG>tG/\u001a8u?\u0012*\u0017\u000f\u0006\u0002YU\"9A\fCA\u0001\u0002\u0004)\u0017\u0001C2p]R,g\u000e\u001e\u0011\u0002\u001d=\u0014\u0018nZ5oC2D\u0015m\u001d5fgV\ta\u000eE\u0002LMN\u000b!c\u001c:jO&t\u0017\r\u001c%bg\",7o\u0018\u0013fcR\u0011\u0001,\u001d\u0005\b9.\t\t\u00111\u0001o\u0003=y'/[4j]\u0006d\u0007*Y:iKN\u0004\u0013\u0001B:ju\u0016\f\u0001b]5{K~#S-\u001d\u000b\u00031ZDq\u0001\u0018\b\u0002\u0002\u0003\u00071+A\u0003tSj,\u0007%\u0001\rdC\u000eDW\r\u001a&bm\u0006\\U-_*fi\"\u000b7\u000f[\"pI\u0016\fAdY1dQ\u0016$'*\u0019<b\u0017\u0016L8+\u001a;ICND7i\u001c3f?\u0012*\u0017\u000f\u0006\u0002Yw\"9A,EA\u0001\u0002\u0004\u0019\u0016!G2bG\",GMS1wC.+\u0017pU3u\u0011\u0006\u001c\bnQ8eK\u0002\na\u0001P5oSRtD#D@\u0002\u0002\u0005\r\u0011QAA\u0004\u0003\u0013\tY\u0001E\u0002C\u0001\u0015CQ!U\nA\u0002MCQAX\nA\u0002MCQaY\nA\u0002\u0015DQ\u0001\\\nA\u00029DQa]\nA\u0002MCQ\u0001_\nA\u0002M\u000b!bZ3u!\u0006LHn\\1e)\r)\u0015\u0011\u0003\u0005\u0007\u0003'!\u0002\u0019A*\u0002\u000b%tG-\u001a=\u0002\u000f\u001d,G\u000fS1tQR\u00191+!\u0007\t\r\u0005MQ\u00031\u0001T\u0003\u001d9W\r\u001e(pI\u0016$2!QA\u0010\u0011\u0019\t\u0019B\u0006a\u0001'\u0006A1m\u001c8uC&t7\u000f\u0006\u0006\u0002&\u0005-\u0012qFA\u001a\u0003o\u00012aSA\u0014\u0013\r\tI\u0003\u0010\u0002\b\u0005>|G.Z1o\u0011\u0019\tic\u0006a\u0001\u000b\u00069Q\r\\3nK:$\bBBA\u0019/\u0001\u00071+\u0001\u0007pe&<\u0017N\\1m\u0011\u0006\u001c\b\u000e\u0003\u0004\u00026]\u0001\raU\u0001\fK2,W.\u001a8u\u0011\u0006\u001c\b\u000e\u0003\u0004\u0002:]\u0001\raU\u0001\u0006g\"Lg\r^\u0001\bkB$\u0017\r^3e)%y\u0018qHA!\u0003\u0007\n)\u0005\u0003\u0004\u0002.a\u0001\r!\u0012\u0005\u0007\u0003cA\u0002\u0019A*\t\r\u0005U\u0002\u00041\u0001T\u0011\u0019\tI\u0004\u0007a\u0001'\u0006QR\u000f\u001d3bi\u0016<\u0016\u000e\u001e5TQ\u0006dGn\\<NkR\fG/[8ogRY1+a\u0013\u0002N\u0005=\u0013\u0011KA*\u0011\u0019\ti#\u0007a\u0001\u000b\"1\u0011\u0011G\rA\u0002MCa!!\u000e\u001a\u0001\u0004\u0019\u0006BBA\u001d3\u0001\u00071\u000b\u0003\u0004\u0002Ve\u0001\raU\u0001\u0018g\"\fG\u000e\\8xYflU\u000f^1cY\u0016tu\u000eZ3NCB\fqA]3n_Z,G\rF\u0005\u0000\u00037\ni&a\u0018\u0002b!1\u0011Q\u0006\u000eA\u0002\u0015Ca!!\r\u001b\u0001\u0004\u0019\u0006BBA\u001b5\u0001\u00071\u000b\u0003\u0004\u0002:i\u0001\raU\u0001\u001be\u0016lwN^3XSRD7\u000b[1mY><X*\u001e;bi&|gn\u001d\u000b\t\u0003O\nI'a\u001b\u0002n5\t\u0001\u0001\u0003\u0004\u0002.m\u0001\r!\u0012\u0005\u0007\u0003cY\u0002\u0019A*\t\r\u0005U2\u00041\u0001T\u0003MiWM]4f)^|7*Z=WC2\u0004\u0016-\u001b:t)=\t\u00151OA<\u0003w\ny(a!\u0002\b\u0006-\u0005BBA;9\u0001\u0007Q)\u0001\u0003lKf\u0004\u0004BBA=9\u0001\u00071+\u0001\tpe&<\u0017N\\1m\u0017\u0016L\b*Y:ia!1\u0011Q\u0010\u000fA\u0002M\u000b\u0001b[3z\u0011\u0006\u001c\b\u000e\r\u0005\u0007\u0003\u0003c\u0002\u0019A#\u0002\t-,\u00170\r\u0005\u0007\u0003\u000bc\u0002\u0019A*\u0002!=\u0014\u0018nZ5oC2\\U-\u001f%bg\"\f\u0004BBAE9\u0001\u00071+\u0001\u0005lKfD\u0015m\u001d52\u0011\u0019\tI\u0004\ba\u0001'\u0006Q\u0001.Y:QCfdw.\u00193\u0016\u0005\u0005\u0015\u0012\u0001\u00049bs2|\u0017\rZ!sSRL\u0018\u0001\u00035bg:{G-Z:\u0002\u00139|G-Z!sSRL\u0018!\u00033bi\u0006Le\u000eZ3y)\r\u0019\u00161\u0014\u0005\u0007\u0003;\u000b\u0003\u0019A*\u0002\r\tLG\u000f]8t\u0003%qw\u000eZ3J]\u0012,\u0007\u0010F\u0002T\u0003GCa!!(#\u0001\u0004\u0019\u0016AD2paf\fe\u000eZ*fi:{G-\u001a\u000b\b\u007f\u0006%\u00161VAX\u0011\u0019\tij\ta\u0001'\"1\u0011QV\u0012A\u0002\u0005\u000bqa\u001c7e\u001d>$W\r\u0003\u0004\u00022\u000e\u0002\r!Q\u0001\b]\u0016<hj\u001c3f\u0003I\u0019w\u000e]=B]\u0012Len]3siZ\u000bG.^3\u0015\u0013}\f9,!/\u0002>\u0006}\u0006BBAOI\u0001\u00071\u000b\u0003\u0004\u0002<\u0012\u0002\r!R\u0001\u0004W\u0016L\bBBA\u0019I\u0001\u00071\u000b\u0003\u0004\u00026\u0011\u0002\raU\u0001\u0010G>\u0004\u00180\u00118e'\u0016$h+\u00197vKRIq0!2\u0002H\u0006%\u00171\u001a\u0005\u0007\u0003;+\u0003\u0019A*\t\r\u0005mV\u00051\u0001F\u0011\u0019\t\t$\na\u0001'\"1\u0011QG\u0013A\u0002M\u000b!cY8qs\u0006sGMU3n_Z,g+\u00197vKR)q0!5\u0002T\"1\u0011Q\u0014\u0014A\u0002MCa!!\u000e'\u0001\u0004\u0019\u0016AH2paf\fe\u000eZ'jOJ\fG/\u001a$s_6Le\u000e\\5oKR{gj\u001c3f)\u001dy\u0018\u0011\\An\u0003;Da!!((\u0001\u0004\u0019\u0006BBA\u001bO\u0001\u00071\u000b\u0003\u0004\u0002`\u001e\u0002\r!Q\u0001\u0005]>$W-\u0001\u0010nS\u001e\u0014\u0018\r^3Ge>l\u0017J\u001c7j]\u0016$vNT8eK&s\u0007\u000b\\1dKRA\u0011qMAs\u0003O\fY\u000f\u0003\u0004\u0002\u001e\"\u0002\ra\u0015\u0005\u0007\u0003SD\u0003\u0019A*\u0002\u000f-,\u0017\u0010S1tQ\"1\u0011q\u001c\u0015A\u0002\u0005\u000badY8qs\u0006sG-T5he\u0006$XM\u0012:p[:{G-\u001a+p\u0013:d\u0017N\\3\u0015\u0013}\f\t0a=\u0002v\u0006]\bBBAOS\u0001\u00071\u000b\u0003\u0004\u00026%\u0002\ra\u0015\u0005\u0007\u0003[K\u0003\u0019A!\t\r\u0005}\u0017\u00061\u0001B\u0003yi\u0017n\u001a:bi\u00164%o\\7O_\u0012,Gk\\%oY&tW-\u00138QY\u0006\u001cW\rF\u0006Y\u0003{\fyP!\u0001\u0003\u0004\t\u0015\u0001BBAOU\u0001\u00071\u000b\u0003\u0004\u00022)\u0002\ra\u0015\u0005\u0007\u0003kQ\u0003\u0019A*\t\r\u00055&\u00061\u0001B\u0011\u0019\tyN\u000ba\u0001\u0003\u00069am\u001c:fC\u000eDW\u0003\u0002B\u0006\u00053!2\u0001\u0017B\u0007\u0011\u001d\u0011ya\u000ba\u0001\u0005#\t\u0011A\u001a\t\u0007\u0017\nMQIa\u0006\n\u0007\tUAHA\u0005Gk:\u001cG/[8ocA\u0019aI!\u0007\u0005\r\tm1F1\u0001J\u0005\u0005)\u0016\u0001C:vEN,Go\u00144\u0015\r\u0005\u0015\"\u0011\u0005B\u0013\u0011\u0019\u0011\u0019\u0003\fa\u0001\u0003\u0006!A\u000f[1u\u0011\u0019\tI\u0004\fa\u0001'\u0006Qa-\u001b7uKJLU\u000e\u001d7\u0015\u000b}\u0014YC!\r\t\u000f\t5R\u00061\u0001\u00030\u0005!\u0001O]3e!\u0019Y%1C#\u0002&!9!1G\u0017A\u0002\u0005\u0015\u0012a\u00024mSB\u0004X\rZ\u0001\u0005I&4g\rF\u0003\u0000\u0005s\u0011Y\u0004\u0003\u0004\u0003$9\u0002\r!\u0011\u0005\u0007\u0003sq\u0003\u0019A*\u0002\u00179,wOT8eK\u001a\u0013x.\u001c\u000b\u0018\u007f\n\u0005#Q\tB%\u0005\u001b\u0012\tF!\u0016\u0003Z\tu#Q\u000eB9\u0005kBaAa\u00110\u0001\u0004\u0019\u0016a\u00028foNK'0\u001a\u0005\u0007\u0005\u000fz\u0003\u0019A*\u0002\u00159,w\u000fR1uC6\u000b\u0007\u000f\u0003\u0004\u0003L=\u0002\raU\u0001\u000b]\u0016<hj\u001c3f\u001b\u0006\u0004\bB\u0002B(_\u0001\u00071+\u0001\u0007nS:LW.^7J]\u0012,\u0007\u0010\u0003\u0004\u0003T=\u0002\raU\u0001\u0013_2$G)\u0019;b!\u0006\u001c8\u000f\u00165s_V<\u0007\u000e\u0003\u0004\u0003X=\u0002\raU\u0001\u0016]>$Wm\u001d+p!\u0006\u001c8\u000f\u00165s_V<\u0007.T1q\u0011\u0019\u0011Yf\fa\u0001'\u0006Qbn\u001c3f\u001b&<'/\u0019;f)>$\u0015\r^1UCJ<W\r^'ba\"9!qL\u0018A\u0002\t\u0005\u0014\u0001\u00068pI\u0016\u001cHk\\'jOJ\fG/\u001a+p\t\u0006$\u0018\rE\u0003\u0003d\t%\u0014)\u0004\u0002\u0003f)\u0019!q\r\u001e\u0002\u000f5,H/\u00192mK&!!1\u000eB3\u0005\u0015\tV/Z;f\u0011\u0019\u0011yg\fa\u0001'\u0006iQ.\u00199PM:+wOT8eKNDqAa\u001d0\u0001\u0004\u0011\t'\u0001\u0005oK^tu\u000eZ3t\u0011\u0019\u00119h\fa\u0001'\u0006\tb.Z<DC\u000eDW\r\u001a%bg\"\u001cu\u000eZ3\u0002\r\u0015\fX/\u00197t)\u0011\t)C! \t\r\t\r\u0002\u00071\u0001O\u0003M!W-\u001a9D_:$XM\u001c;FcV\fG.\u001b;z)!\t)Ca!\u0003\b\n-\u0005B\u0002BCc\u0001\u0007Q-\u0001\u0002bc!1!\u0011R\u0019A\u0002\u0015\f!!\u0019\u001a\t\r\t5\u0015\u00071\u0001T\u0003\u0019aWM\\4uQ\"\u001a\u0011G!%\u0011\u0007-\u0013\u0019*C\u0002\u0003\u0016r\u0012a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003M\u000bAaY8qsR\tq0\u0001\u0004d_:\u001c\u0017\r\u001e\u000b\u0006\u007f\n\r&Q\u0015\u0005\u0007\u0005G!\u0004\u0019A!\t\r\u0005eB\u00071\u0001T\u0003=1wN]3bG\"<\u0016\u000e\u001e5ICNDGc\u0001-\u0003,\"9!qB\u001bA\u0002\t5\u0006CB&\u00030\u0016\u001b\u0006,C\u0002\u00032r\u0012\u0011BR;oGRLwN\u001c\u001a\u0002)\u0019|'/Z1dQ^KG\u000f\u001b%bg\"<\u0006.\u001b7f)\u0011\t)Ca.\t\u000f\t=a\u00071\u0001\u0003:B91Ja,F'\u0006\u0015\u0002")
public final class BitmapIndexedSetNode<A>
extends SetNode<A> {
    private int dataMap;
    private int nodeMap;
    private Object[] content;
    private int[] originalHashes;
    private int size;
    private int cachedJavaKeySetHashCode;

    public int dataMap() {
        return this.dataMap;
    }

    public void dataMap_$eq(int x$1) {
        this.dataMap = x$1;
    }

    public int nodeMap() {
        return this.nodeMap;
    }

    public void nodeMap_$eq(int x$1) {
        this.nodeMap = x$1;
    }

    public Object[] content() {
        return this.content;
    }

    public void content_$eq(Object[] x$1) {
        this.content = x$1;
    }

    public int[] originalHashes() {
        return this.originalHashes;
    }

    public void originalHashes_$eq(int[] x$1) {
        this.originalHashes = x$1;
    }

    @Override
    public int size() {
        return this.size;
    }

    public void size_$eq(int x$1) {
        this.size = x$1;
    }

    @Override
    public int cachedJavaKeySetHashCode() {
        return this.cachedJavaKeySetHashCode;
    }

    public void cachedJavaKeySetHashCode_$eq(int x$1) {
        this.cachedJavaKeySetHashCode = x$1;
    }

    @Override
    public A getPayload(int index) {
        return (A)this.content()[index];
    }

    @Override
    public int getHash(int index) {
        return this.originalHashes()[index];
    }

    @Override
    public SetNode<A> getNode(int index) {
        return (SetNode)this.content()[this.content().length - 1 - index];
    }

    @Override
    public boolean contains(A element, int originalHash, int elementHash, int shift) {
        int mask = elementHash >>> shift & 0x1F;
        int bitpos = 1 << mask;
        if ((this.dataMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
            return this.originalHashes()[index] == originalHash && BoxesRunTime.equals(element, this.content()[index]);
        }
        if ((this.nodeMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos);
            return ((SetNode)this.getNode(index)).contains(element, originalHash, elementHash, shift + 5);
        }
        return false;
    }

    @Override
    public BitmapIndexedSetNode<A> updated(A element, int originalHash, int elementHash, int shift) {
        int mask = elementHash >>> shift & 0x1F;
        int bitpos = 1 << mask;
        if ((this.dataMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
            Object element0 = this.content()[index];
            if (element0 == element) {
                return this;
            }
            int element0UnimprovedHash = this.originalHashes()[index];
            int element0Hash = Hashing$.MODULE$.improve(element0UnimprovedHash);
            if (originalHash == element0UnimprovedHash && BoxesRunTime.equals(element0, element)) {
                return this;
            }
            SetNode<Object> subNodeNew = this.mergeTwoKeyValPairs(element0, element0UnimprovedHash, element0Hash, element, originalHash, elementHash, shift + 5);
            return this.copyAndMigrateFromInlineToNode(bitpos, element0Hash, subNodeNew);
        }
        if ((this.nodeMap() & bitpos) != 0) {
            SetNode<A> subNodeNew;
            int index = Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos);
            Node subNode = this.getNode(index);
            if (subNode == (subNodeNew = ((SetNode)subNode).updated(element, originalHash, elementHash, shift + 5))) {
                return this;
            }
            return this.copyAndSetNode(bitpos, (SetNode<A>)subNode, subNodeNew);
        }
        return this.copyAndInsertValue(bitpos, element, originalHash, elementHash);
    }

    /*
     * Unable to fully structure code
     */
    public int updateWithShallowMutations(A element, int originalHash, int elementHash, int shift, int shallowlyMutableNodeMap) {
        block5: {
            mask = elementHash >>> shift & 31;
            bitpos = 1 << mask;
            if ((this.dataMap() & bitpos) != 0) {
                index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
                element0 = this.content()[index];
                element0UnimprovedHash = this.originalHashes()[index];
                if (element0UnimprovedHash == originalHash && BoxesRunTime.equals(element0, element)) {
                    return shallowlyMutableNodeMap;
                }
                element0Hash = Hashing$.MODULE$.improve(element0UnimprovedHash);
                subNodeNew = this.mergeTwoKeyValPairs(element0, element0UnimprovedHash, element0Hash, element, originalHash, elementHash, shift + 5);
                this.migrateFromInlineToNodeInPlace(bitpos, element0Hash, subNodeNew);
                return shallowlyMutableNodeMap | bitpos;
            }
            if ((this.nodeMap() & bitpos) == 0) break block5;
            index = Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos);
            subNode = this.getNode(index);
            subNodeSize = subNode.size();
            subNodeCachedJavaKeySetHashCode = subNode.cachedJavaKeySetHashCode();
            returnNodeMap = shallowlyMutableNodeMap;
            if (!(subNode instanceof BitmapIndexedSetNode)) ** GOTO lbl-1000
            var19_18 = (BitmapIndexedSetNode)subNode;
            if ((bitpos & shallowlyMutableNodeMap) != 0) {
                var19_18.updateWithShallowMutations(element, originalHash, elementHash, shift + 5, 0);
                v0 = var19_18;
            } else lbl-1000:
            // 2 sources

            {
                if ((subNodeNew = subNode.updated(element, originalHash, elementHash, shift + 5)) != subNode) {
                    returnNodeMap = shallowlyMutableNodeMap | bitpos;
                }
                v0 = subNodeNew;
            }
            subNodeNew = v0;
            this.content()[this.content().length - 1 - this.nodeIndex((int)bitpos)] = subNodeNew;
            this.size_$eq(this.size() - subNodeSize + subNodeNew.size());
            this.cachedJavaKeySetHashCode_$eq(this.cachedJavaKeySetHashCode() - subNodeCachedJavaKeySetHashCode + subNodeNew.cachedJavaKeySetHashCode());
            return returnNodeMap;
        }
        dataIx = this.dataIndex(bitpos);
        src = this.content();
        dst = new Object[src.length + 1];
        System.arraycopy(src, 0, dst, 0, dataIx);
        dst[dataIx] = element;
        System.arraycopy(src, dataIx, dst, dataIx + 1, src.length - dataIx);
        dstHashes = this.insertElement(this.originalHashes(), dataIx, originalHash);
        this.dataMap_$eq(this.dataMap() | bitpos);
        this.content_$eq(dst);
        this.originalHashes_$eq(dstHashes);
        this.size_$eq(this.size() + 1);
        this.cachedJavaKeySetHashCode_$eq(this.cachedJavaKeySetHashCode() + elementHash);
        return shallowlyMutableNodeMap;
    }

    @Override
    public BitmapIndexedSetNode<A> removed(A element, int originalHash, int elementHash, int shift) {
        int mask = elementHash >>> shift & 0x1F;
        int bitpos = 1 << mask;
        if ((this.dataMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
            if (BoxesRunTime.equals(this.content()[index], element)) {
                if (this.payloadArity() == 2 && this.nodeArity() == 0) {
                    int newDataMap;
                    int n;
                    if (shift == 0) {
                        n = this.dataMap() ^ bitpos;
                    } else {
                        int maskFrom_shift = 0;
                        int bitposFrom_mask = elementHash >>> maskFrom_shift & 0x1F;
                        n = newDataMap = 1 << bitposFrom_mask;
                    }
                    if (index == 0) {
                        Object[] objectArray = new Object[1];
                        int getPayload_index = 1;
                        objectArray[0] = this.content()[getPayload_index];
                        return new BitmapIndexedSetNode<A>(newDataMap, 0, objectArray, new int[]{this.originalHashes()[1]}, this.size() - 1, Hashing$.MODULE$.improve(this.originalHashes()[1]));
                    }
                    Object[] objectArray = new Object[1];
                    int getPayload_index = 0;
                    objectArray[0] = this.content()[getPayload_index];
                    return new BitmapIndexedSetNode<A>(newDataMap, 0, objectArray, new int[]{this.originalHashes()[0]}, this.size() - 1, Hashing$.MODULE$.improve(this.originalHashes()[0]));
                }
                return this.copyAndRemoveValue(bitpos, elementHash);
            }
            return this;
        }
        if ((this.nodeMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos);
            Node subNode = this.getNode(index);
            SetNode<A> subNodeNew = ((SetNode)subNode).removed(element, originalHash, elementHash, shift + 5);
            if (subNodeNew == subNode) {
                return this;
            }
            int subNodeNewSize = subNodeNew.size();
            if (subNodeNewSize == 1) {
                if (this.size() == ((SetNode)subNode).size()) {
                    return (BitmapIndexedSetNode)subNodeNew;
                }
                return this.copyAndMigrateFromNodeToInline(bitpos, elementHash, (SetNode<A>)subNode, subNodeNew);
            }
            if (subNodeNewSize > 1) {
                return this.copyAndSetNode(bitpos, (SetNode<A>)subNode, subNodeNew);
            }
        }
        return this;
    }

    public BitmapIndexedSetNode<A> removeWithShallowMutations(A element, int originalHash, int elementHash) {
        int maskFrom_shift = 0;
        int mask = elementHash >>> maskFrom_shift & 0x1F;
        int bitpos = 1 << mask;
        if ((this.dataMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.dataMap(), mask, bitpos);
            if (BoxesRunTime.equals(this.content()[index], element)) {
                if (this.payloadArity() == 2 && this.nodeArity() == 0) {
                    int newDataMap = this.dataMap() ^ bitpos;
                    if (index == 0) {
                        Object[] objectArray = new Object[1];
                        int getPayload_index = 1;
                        objectArray[0] = this.content()[getPayload_index];
                        Object[] newContent = objectArray;
                        int[] newOriginalHashes = new int[]{this.originalHashes()[1]};
                        int getHash_index = 1;
                        int newCachedJavaKeySetHashCode = Hashing$.MODULE$.improve(this.originalHashes()[getHash_index]);
                        this.content_$eq(newContent);
                        this.originalHashes_$eq(newOriginalHashes);
                        this.cachedJavaKeySetHashCode_$eq(newCachedJavaKeySetHashCode);
                    } else {
                        Object[] objectArray = new Object[1];
                        int getPayload_index = 0;
                        objectArray[0] = this.content()[getPayload_index];
                        Object[] newContent = objectArray;
                        int[] newOriginalHashes = new int[]{this.originalHashes()[0]};
                        int getHash_index = 0;
                        int newCachedJavaKeySetHashCode = Hashing$.MODULE$.improve(this.originalHashes()[getHash_index]);
                        this.content_$eq(newContent);
                        this.originalHashes_$eq(newOriginalHashes);
                        this.cachedJavaKeySetHashCode_$eq(newCachedJavaKeySetHashCode);
                    }
                    this.dataMap_$eq(newDataMap);
                    this.nodeMap_$eq(0);
                    this.size_$eq(1);
                    return this;
                }
                int dataIx = this.dataIndex(bitpos);
                int idx = 1 * dataIx;
                Object[] src = this.content();
                Object[] dst = new Object[src.length - 1];
                System.arraycopy(src, 0, dst, 0, idx);
                System.arraycopy(src, idx + 1, dst, idx, src.length - idx - 1);
                int[] dstHashes = this.removeElement(this.originalHashes(), dataIx);
                this.dataMap_$eq(this.dataMap() ^ bitpos);
                this.content_$eq(dst);
                this.originalHashes_$eq(dstHashes);
                this.size_$eq(this.size() - 1);
                this.cachedJavaKeySetHashCode_$eq(this.cachedJavaKeySetHashCode() - elementHash);
                return this;
            }
            return this;
        }
        if ((this.nodeMap() & bitpos) != 0) {
            int index = Node$.MODULE$.indexFrom(this.nodeMap(), mask, bitpos);
            Node subNode = this.getNode(index);
            BitmapIndexedSetNode subNodeNew = (BitmapIndexedSetNode)((SetNode)subNode).removed(element, originalHash, elementHash, 5);
            if (subNodeNew == subNode) {
                return this;
            }
            if (subNodeNew.size() == 1) {
                if (this.payloadArity() == 0 && this.nodeArity() == 1) {
                    this.dataMap_$eq(subNodeNew.dataMap());
                    this.nodeMap_$eq(subNodeNew.nodeMap());
                    this.content_$eq(subNodeNew.content());
                    this.originalHashes_$eq(subNodeNew.originalHashes());
                    this.size_$eq(subNodeNew.size());
                    this.cachedJavaKeySetHashCode_$eq(subNodeNew.cachedJavaKeySetHashCode());
                    return this;
                }
                this.migrateFromNodeToInlineInPlace(bitpos, originalHash, elementHash, (SetNode<A>)subNode, subNodeNew);
                return this;
            }
            this.content()[this.content().length - 1 - this.nodeIndex((int)bitpos)] = subNodeNew;
            this.size_$eq(this.size() - 1);
            this.cachedJavaKeySetHashCode_$eq(this.cachedJavaKeySetHashCode() - subNode.cachedJavaKeySetHashCode() + subNodeNew.cachedJavaKeySetHashCode());
            return this;
        }
        return this;
    }

    public SetNode<A> mergeTwoKeyValPairs(A key0, int originalKeyHash0, int keyHash0, A key1, int originalKeyHash1, int keyHash1, int shift) {
        if (shift >= 32) {
            return new HashCollisionSetNode(originalKeyHash0, keyHash0, Vector$.MODULE$.from((IterableOnce)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{key0, key1})));
        }
        int mask0 = keyHash0 >>> shift & 0x1F;
        int mask1 = keyHash1 >>> shift & 0x1F;
        if (mask0 != mask1) {
            int dataMap = 1 << mask0 | 1 << mask1;
            int newCachedHashCode = keyHash0 + keyHash1;
            if (mask0 < mask1) {
                return new BitmapIndexedSetNode<A>(dataMap, 0, new Object[]{key0, key1}, new int[]{originalKeyHash0, originalKeyHash1}, 2, newCachedHashCode);
            }
            return new BitmapIndexedSetNode<A>(dataMap, 0, new Object[]{key1, key0}, new int[]{originalKeyHash1, originalKeyHash0}, 2, newCachedHashCode);
        }
        int nodeMap = 1 << mask0;
        SetNode<A> node = this.mergeTwoKeyValPairs(key0, originalKeyHash0, keyHash0, key1, originalKeyHash1, keyHash1, shift + 5);
        return new BitmapIndexedSetNode<A>(0, nodeMap, new Object[]{node}, Array$.MODULE$.emptyIntArray(), node.size(), node.cachedJavaKeySetHashCode());
    }

    @Override
    public boolean hasPayload() {
        return this.dataMap() != 0;
    }

    @Override
    public int payloadArity() {
        return Integer.bitCount(this.dataMap());
    }

    @Override
    public boolean hasNodes() {
        return this.nodeMap() != 0;
    }

    @Override
    public int nodeArity() {
        return Integer.bitCount(this.nodeMap());
    }

    public int dataIndex(int bitpos) {
        return Integer.bitCount(this.dataMap() & bitpos - 1);
    }

    public int nodeIndex(int bitpos) {
        return Integer.bitCount(this.nodeMap() & bitpos - 1);
    }

    public BitmapIndexedSetNode<A> copyAndSetNode(int bitpos, SetNode<A> oldNode, SetNode<A> newNode) {
        int idx = this.content().length - 1 - this.nodeIndex(bitpos);
        Object[] src = this.content();
        Object[] dst = new Object[src.length];
        System.arraycopy(src, 0, dst, 0, src.length);
        dst[idx] = newNode;
        return new BitmapIndexedSetNode<A>(this.dataMap(), this.nodeMap(), dst, this.originalHashes(), this.size() - oldNode.size() + newNode.size(), this.cachedJavaKeySetHashCode() - oldNode.cachedJavaKeySetHashCode() + newNode.cachedJavaKeySetHashCode());
    }

    public BitmapIndexedSetNode<A> copyAndInsertValue(int bitpos, A key, int originalHash, int elementHash) {
        int dataIx = this.dataIndex(bitpos);
        int idx = 1 * dataIx;
        Object[] src = this.content();
        Object[] dst = new Object[src.length + 1];
        System.arraycopy(src, 0, dst, 0, idx);
        dst[idx] = key;
        System.arraycopy(src, idx, dst, idx + 1, src.length - idx);
        int[] dstHashes = this.insertElement(this.originalHashes(), dataIx, originalHash);
        return new BitmapIndexedSetNode<A>(this.dataMap() | bitpos, this.nodeMap(), dst, dstHashes, this.size() + 1, this.cachedJavaKeySetHashCode() + elementHash);
    }

    public BitmapIndexedSetNode<A> copyAndSetValue(int bitpos, A key, int originalHash, int elementHash) {
        int dataIx = this.dataIndex(bitpos);
        int idx = 1 * dataIx;
        Object[] src = this.content();
        Object[] dst = new Object[src.length];
        System.arraycopy(src, 0, dst, 0, src.length);
        dst[idx] = key;
        return new BitmapIndexedSetNode<A>(this.dataMap() | bitpos, this.nodeMap(), dst, this.originalHashes(), this.size(), this.cachedJavaKeySetHashCode());
    }

    public BitmapIndexedSetNode<A> copyAndRemoveValue(int bitpos, int elementHash) {
        int dataIx = this.dataIndex(bitpos);
        int idx = 1 * dataIx;
        Object[] src = this.content();
        Object[] dst = new Object[src.length - 1];
        System.arraycopy(src, 0, dst, 0, idx);
        System.arraycopy(src, idx + 1, dst, idx, src.length - idx - 1);
        int[] dstHashes = this.removeElement(this.originalHashes(), dataIx);
        return new BitmapIndexedSetNode<A>(this.dataMap() ^ bitpos, this.nodeMap(), dst, dstHashes, this.size() - 1, this.cachedJavaKeySetHashCode() - elementHash);
    }

    public BitmapIndexedSetNode<A> copyAndMigrateFromInlineToNode(int bitpos, int elementHash, SetNode<A> node) {
        int dataIx = this.dataIndex(bitpos);
        int idxOld = 1 * dataIx;
        int idxNew = this.content().length - 1 - this.nodeIndex(bitpos);
        Object[] src = this.content();
        Object[] dst = new Object[src.length - 1 + 1];
        System.arraycopy(src, 0, dst, 0, idxOld);
        System.arraycopy(src, idxOld + 1, dst, idxOld, idxNew - idxOld);
        dst[idxNew] = node;
        System.arraycopy(src, idxNew + 1, dst, idxNew + 1, src.length - idxNew - 1);
        int[] dstHashes = this.removeElement(this.originalHashes(), dataIx);
        return new BitmapIndexedSetNode<A>(this.dataMap() ^ bitpos, this.nodeMap() | bitpos, dst, dstHashes, this.size() - 1 + node.size(), this.cachedJavaKeySetHashCode() - elementHash + node.cachedJavaKeySetHashCode());
    }

    public BitmapIndexedSetNode<A> migrateFromInlineToNodeInPlace(int bitpos, int keyHash, SetNode<A> node) {
        int dataIx = this.dataIndex(bitpos);
        int idxOld = 1 * dataIx;
        int idxNew = this.content().length - 1 - this.nodeIndex(bitpos);
        System.arraycopy(this.content(), idxOld + 1, this.content(), idxOld, idxNew - idxOld);
        this.content()[idxNew] = node;
        this.dataMap_$eq(this.dataMap() ^ bitpos);
        this.nodeMap_$eq(this.nodeMap() | bitpos);
        this.originalHashes_$eq(this.removeElement(this.originalHashes(), dataIx));
        this.size_$eq(this.size() - 1 + node.size());
        this.cachedJavaKeySetHashCode_$eq(this.cachedJavaKeySetHashCode() - keyHash + node.cachedJavaKeySetHashCode());
        return this;
    }

    public BitmapIndexedSetNode<A> copyAndMigrateFromNodeToInline(int bitpos, int elementHash, SetNode<A> oldNode, SetNode<A> node) {
        int idxOld = this.content().length - 1 - this.nodeIndex(bitpos);
        int dataIxNew = this.dataIndex(bitpos);
        int idxNew = 1 * dataIxNew;
        Object[] src = this.content();
        Object[] dst = new Object[src.length - 1 + 1];
        System.arraycopy(src, 0, dst, 0, idxNew);
        dst[idxNew] = node.getPayload(0);
        System.arraycopy(src, idxNew, dst, idxNew + 1, idxOld - idxNew);
        System.arraycopy(src, idxOld + 1, dst, idxOld + 1, src.length - idxOld - 1);
        int hash = node.getHash(0);
        int[] dstHashes = this.insertElement(this.originalHashes(), dataIxNew, hash);
        return new BitmapIndexedSetNode<A>(this.dataMap() | bitpos, this.nodeMap() ^ bitpos, dst, dstHashes, this.size() - oldNode.size() + 1, this.cachedJavaKeySetHashCode() - oldNode.cachedJavaKeySetHashCode() + node.cachedJavaKeySetHashCode());
    }

    public void migrateFromNodeToInlineInPlace(int bitpos, int originalHash, int elementHash, SetNode<A> oldNode, SetNode<A> node) {
        int idxOld = this.content().length - 1 - this.nodeIndex(bitpos);
        int dataIxNew = this.dataIndex(bitpos);
        A element = node.getPayload(0);
        System.arraycopy(this.content(), dataIxNew, this.content(), dataIxNew + 1, idxOld - dataIxNew);
        this.content()[dataIxNew] = element;
        int hash = node.getHash(0);
        int[] dstHashes = this.insertElement(this.originalHashes(), dataIxNew, hash);
        this.dataMap_$eq(this.dataMap() | bitpos);
        this.nodeMap_$eq(this.nodeMap() ^ bitpos);
        this.originalHashes_$eq(dstHashes);
        this.size_$eq(this.size() - oldNode.size() + 1);
        this.cachedJavaKeySetHashCode_$eq(this.cachedJavaKeySetHashCode() - oldNode.cachedJavaKeySetHashCode() + node.cachedJavaKeySetHashCode());
    }

    @Override
    public <U> void foreach(Function1<A, U> f) {
        int thisPayloadArity = this.payloadArity();
        for (int i = 0; i < thisPayloadArity; ++i) {
            f.apply(this.content()[i]);
        }
        int thisNodeArity = this.nodeArity();
        for (int j = 0; j < thisNodeArity; ++j) {
            ((SetNode)this.getNode(j)).foreach(f);
        }
    }

    @Override
    public boolean subsetOf(SetNode<A> that, int shift) {
        if (this == that) {
            return true;
        }
        if (that instanceof HashCollisionSetNode) {
            return false;
        }
        if (that instanceof BitmapIndexedSetNode) {
            int nodeBitmap;
            BitmapIndexedSetNode bitmapIndexedSetNode = (BitmapIndexedSetNode)that;
            int thisBitmap = this.dataMap() | this.nodeMap();
            if ((thisBitmap | (nodeBitmap = bitmapIndexedSetNode.dataMap() | bitmapIndexedSetNode.nodeMap())) != nodeBitmap) {
                return false;
            }
            int bitmap = thisBitmap & nodeBitmap;
            int bitsToSkip = Integer.numberOfTrailingZeros(bitmap);
            boolean isValidSubset = true;
            while (isValidSubset && bitsToSkip < 32) {
                int newBitmap;
                boolean bl;
                int bitpos = 1 << bitsToSkip;
                if ((this.dataMap() & bitpos) != 0) {
                    if ((bitmapIndexedSetNode.dataMap() & bitpos) != 0) {
                        int getPayload_index = Integer.bitCount(this.dataMap() & bitpos - 1);
                        Object payload0 = this.content()[getPayload_index];
                        int getPayload_index2 = Integer.bitCount(bitmapIndexedSetNode.dataMap() & bitpos - 1);
                        Object payload1 = bitmapIndexedSetNode.content()[getPayload_index2];
                        bl = BoxesRunTime.equals(payload0, payload1);
                    } else {
                        int thisDataIndex = Integer.bitCount(this.dataMap() & bitpos - 1);
                        Object payload = this.content()[thisDataIndex];
                        SetNode<Object> subNode = that.getNode(Integer.bitCount(bitmapIndexedSetNode.nodeMap() & bitpos - 1));
                        int elementUnimprovedHash = this.originalHashes()[thisDataIndex];
                        int elementHash = Hashing$.MODULE$.improve(elementUnimprovedHash);
                        bl = subNode.contains(payload, elementUnimprovedHash, elementHash, shift + 5);
                    }
                } else {
                    Node subNode0 = this.getNode(Integer.bitCount(this.nodeMap() & bitpos - 1));
                    Node subNode1 = bitmapIndexedSetNode.getNode(Integer.bitCount(bitmapIndexedSetNode.nodeMap() & bitpos - 1));
                    bl = ((SetNode)subNode0).subsetOf(subNode1, shift + 5);
                }
                isValidSubset = bl;
                bitmap = newBitmap = bitmap ^ bitpos;
                bitsToSkip = Integer.numberOfTrailingZeros(newBitmap);
            }
            return isValidSubset;
        }
        throw new MatchError(that);
    }

    @Override
    public BitmapIndexedSetNode<A> filterImpl(Function1<A, Object> pred, boolean flipped) {
        if (this.size() == 0) {
            return this;
        }
        if (this.size() == 1) {
            int getPayload_index = 0;
            if (BoxesRunTime.unboxToBoolean(pred.apply(this.content()[getPayload_index])) != flipped) {
                return this;
            }
            return SetNode$.MODULE$.empty();
        }
        if (this.nodeMap() == 0) {
            int minimumIndex = Integer.numberOfTrailingZeros(this.dataMap());
            int maximumIndex = 32 - Integer.numberOfLeadingZeros(this.dataMap());
            int newDataMap = 0;
            int newCachedHashCode = 0;
            int dataIndex = 0;
            for (int i = minimumIndex; i < maximumIndex; ++i) {
                int bitpos = 1 << i;
                if ((bitpos & this.dataMap()) == 0) continue;
                Object payload = this.content()[dataIndex];
                if (BoxesRunTime.unboxToBoolean(pred.apply(payload)) != flipped) {
                    newDataMap |= bitpos;
                    newCachedHashCode += Hashing$.MODULE$.improve(this.originalHashes()[dataIndex]);
                }
                ++dataIndex;
            }
            if (newDataMap == 0) {
                return SetNode$.MODULE$.empty();
            }
            if (newDataMap == this.dataMap()) {
                return this;
            }
            int newSize = Integer.bitCount(newDataMap);
            Object[] newContent = new Object[newSize];
            int[] newOriginalHashCodes = new int[newSize];
            int newMaximumIndex = 32 - Integer.numberOfLeadingZeros(newDataMap);
            int newDataIndex = 0;
            for (int j = Integer.numberOfTrailingZeros(newDataMap); j < newMaximumIndex; ++j) {
                int bitpos = 1 << j;
                if ((bitpos & newDataMap) == 0) continue;
                int oldIndex = Integer.bitCount(this.dataMap() & bitpos - 1);
                newContent[newDataIndex] = this.content()[oldIndex];
                newOriginalHashCodes[newDataIndex] = this.originalHashes()[oldIndex];
                ++newDataIndex;
            }
            return new BitmapIndexedSetNode<A>(newDataMap, 0, newContent, newOriginalHashCodes, newSize, newCachedHashCode);
        }
        int allMap = this.dataMap() | this.nodeMap();
        int minimumIndex = Integer.numberOfTrailingZeros(allMap);
        int maximumIndex = 32 - Integer.numberOfLeadingZeros(allMap);
        int oldDataPassThrough = 0;
        int nodeMigrateToDataTargetMap = 0;
        Object nodesToMigrateToData = null;
        int nodesToPassThroughMap = 0;
        int mapOfNewNodes = 0;
        Object newNodes = null;
        int newDataMap = 0;
        int newNodeMap = 0;
        int newSize = 0;
        int newCachedHashCode = 0;
        int dataIndex = 0;
        int nodeIndex = 0;
        for (int i = minimumIndex; i < maximumIndex; ++i) {
            int bitpos = 1 << i;
            if ((bitpos & this.dataMap()) != 0) {
                Object payload = this.content()[dataIndex];
                if (BoxesRunTime.unboxToBoolean(pred.apply(payload)) != flipped) {
                    newDataMap |= bitpos;
                    oldDataPassThrough |= bitpos;
                    ++newSize;
                    newCachedHashCode += Hashing$.MODULE$.improve(this.originalHashes()[dataIndex]);
                }
                ++dataIndex;
                continue;
            }
            if ((bitpos & this.nodeMap()) == 0) continue;
            Node oldSubNode = this.getNode(nodeIndex);
            SetNode<A> newSubNode = ((SetNode)oldSubNode).filterImpl(pred, flipped);
            newSize += newSubNode.size();
            newCachedHashCode += newSubNode.cachedJavaKeySetHashCode();
            if (newSubNode.size() > 1) {
                newNodeMap |= bitpos;
                if (oldSubNode == newSubNode) {
                    nodesToPassThroughMap |= bitpos;
                } else {
                    mapOfNewNodes |= bitpos;
                    if (newNodes == null) {
                        newNodes = Queue$.MODULE$.empty();
                    }
                    ((AbstractBuffer)newNodes).$plus$eq(newSubNode);
                }
            } else if (newSubNode.size() == 1) {
                newDataMap |= bitpos;
                nodeMigrateToDataTargetMap |= bitpos;
                if (nodesToMigrateToData == null) {
                    nodesToMigrateToData = Queue$.MODULE$.empty();
                }
                ((AbstractBuffer)nodesToMigrateToData).$plus$eq(newSubNode);
            }
            ++nodeIndex;
        }
        return this.newNodeFrom(newSize, newDataMap, newNodeMap, minimumIndex, oldDataPassThrough, nodesToPassThroughMap, nodeMigrateToDataTargetMap, (Queue<SetNode<A>>)nodesToMigrateToData, mapOfNewNodes, (Queue<SetNode<A>>)newNodes, newCachedHashCode);
    }

    @Override
    public BitmapIndexedSetNode<A> diff(SetNode<A> that, int shift) {
        if (that instanceof BitmapIndexedSetNode) {
            BitmapIndexedSetNode bitmapIndexedSetNode = (BitmapIndexedSetNode)that;
            if (this.size() == 0) {
                return this;
            }
            if (this.size() == 1) {
                int getHash_index = 0;
                int h2 = this.originalHashes()[getHash_index];
                int getPayload_index = 0;
                if (that.contains(this.content()[getPayload_index], h2, Hashing$.MODULE$.improve(h2), shift)) {
                    return SetNode$.MODULE$.empty();
                }
                return this;
            }
            int allMap = this.dataMap() | this.nodeMap();
            int minimumIndex = Integer.numberOfTrailingZeros(allMap);
            int maximumIndex = 32 - Integer.numberOfLeadingZeros(allMap);
            int oldDataPassThrough = 0;
            int nodeMigrateToDataTargetMap = 0;
            Object nodesToMigrateToData = null;
            int nodesToPassThroughMap = 0;
            int mapOfNewNodes = 0;
            Object newNodes = null;
            int newDataMap = 0;
            int newNodeMap = 0;
            int newSize = 0;
            int newCachedHashCode = 0;
            int dataIndex = 0;
            int nodeIndex = 0;
            for (int i = minimumIndex; i < maximumIndex; ++i) {
                SetNode<Object> setNode;
                int bitpos = 1 << i;
                if ((bitpos & this.dataMap()) != 0) {
                    int hash;
                    int originalHash;
                    Object payload = this.content()[dataIndex];
                    if (!bitmapIndexedSetNode.contains(payload, originalHash = this.originalHashes()[dataIndex], hash = Hashing$.MODULE$.improve(originalHash), shift)) {
                        newDataMap |= bitpos;
                        oldDataPassThrough |= bitpos;
                        ++newSize;
                        newCachedHashCode += hash;
                    }
                    ++dataIndex;
                    continue;
                }
                if ((bitpos & this.nodeMap()) == 0) continue;
                SetNode<Object> oldSubNode = this.getNode(nodeIndex);
                if ((bitpos & bitmapIndexedSetNode.dataMap()) != 0) {
                    int thatDataIndex = Integer.bitCount(bitmapIndexedSetNode.dataMap() & bitpos - 1);
                    Object thatPayload = bitmapIndexedSetNode.content()[thatDataIndex];
                    int thatOriginalHash = bitmapIndexedSetNode.originalHashes()[thatDataIndex];
                    int thatHash = Hashing$.MODULE$.improve(thatOriginalHash);
                    setNode = oldSubNode.removed(thatPayload, thatOriginalHash, thatHash, shift + 5);
                } else {
                    setNode = (bitpos & bitmapIndexedSetNode.nodeMap()) != 0 ? oldSubNode.diff((SetNode<Object>)bitmapIndexedSetNode.getNode(Integer.bitCount(bitmapIndexedSetNode.nodeMap() & bitpos - 1)), shift + 5) : oldSubNode;
                }
                SetNode<Object> newSubNode = setNode;
                newSize += newSubNode.size();
                newCachedHashCode += newSubNode.cachedJavaKeySetHashCode();
                if (newSubNode.size() > 1) {
                    newNodeMap |= bitpos;
                    if (oldSubNode == newSubNode) {
                        nodesToPassThroughMap |= bitpos;
                    } else {
                        mapOfNewNodes |= bitpos;
                        if (newNodes == null) {
                            newNodes = Queue$.MODULE$.empty();
                        }
                        ((AbstractBuffer)newNodes).$plus$eq(newSubNode);
                    }
                } else if (newSubNode.size() == 1) {
                    newDataMap |= bitpos;
                    nodeMigrateToDataTargetMap |= bitpos;
                    if (nodesToMigrateToData == null) {
                        nodesToMigrateToData = Queue$.MODULE$.empty();
                    }
                    ((AbstractBuffer)nodesToMigrateToData).$plus$eq(newSubNode);
                }
                ++nodeIndex;
            }
            return this.newNodeFrom(newSize, newDataMap, newNodeMap, minimumIndex, oldDataPassThrough, nodesToPassThroughMap, nodeMigrateToDataTargetMap, (Queue<SetNode<A>>)nodesToMigrateToData, mapOfNewNodes, (Queue<SetNode<A>>)newNodes, newCachedHashCode);
        }
        if (that instanceof HashCollisionSetNode) {
            throw new RuntimeException("BitmapIndexedSetNode diff HashCollisionSetNode");
        }
        throw new MatchError(that);
    }

    private BitmapIndexedSetNode<A> newNodeFrom(int newSize, int newDataMap, int newNodeMap, int minimumIndex, int oldDataPassThrough, int nodesToPassThroughMap, int nodeMigrateToDataTargetMap, Queue<SetNode<A>> nodesToMigrateToData, int mapOfNewNodes, Queue<SetNode<A>> newNodes, int newCachedHashCode) {
        if (newSize == 0) {
            return SetNode$.MODULE$.empty();
        }
        if (newSize == this.size()) {
            return this;
        }
        int newDataSize = Integer.bitCount(newDataMap);
        int newContentSize = newDataSize + Integer.bitCount(newNodeMap);
        Object[] newContent = new Object[newContentSize];
        int[] newOriginalHashes = new int[newDataSize];
        int newAllMap = newDataMap | newNodeMap;
        int maxIndex = 32 - Integer.numberOfLeadingZeros(newAllMap);
        int oldDataIndex = 0;
        int oldNodeIndex = 0;
        int newDataIndex = 0;
        int newNodeIndex = 0;
        for (int i = minimumIndex; i < maxIndex; ++i) {
            int bitpos = 1 << i;
            if ((bitpos & oldDataPassThrough) != 0) {
                newContent[newDataIndex] = this.content()[oldDataIndex];
                newOriginalHashes[newDataIndex] = this.originalHashes()[oldDataIndex];
                ++newDataIndex;
                ++oldDataIndex;
                continue;
            }
            if ((bitpos & nodesToPassThroughMap) != 0) {
                newContent[newContentSize - newNodeIndex - 1] = this.getNode(oldNodeIndex);
                ++newNodeIndex;
                ++oldNodeIndex;
                continue;
            }
            if ((bitpos & nodeMigrateToDataTargetMap) != 0) {
                SetNode<A> node = nodesToMigrateToData.dequeue();
                newContent[newDataIndex] = node.getPayload(0);
                newOriginalHashes[newDataIndex] = node.getHash(0);
                ++newDataIndex;
                ++oldNodeIndex;
                continue;
            }
            if ((bitpos & mapOfNewNodes) != 0) {
                newContent[newContentSize - newNodeIndex - 1] = newNodes.dequeue();
                ++newNodeIndex;
                ++oldNodeIndex;
                continue;
            }
            if ((bitpos & this.dataMap()) != 0) {
                ++oldDataIndex;
                continue;
            }
            if ((bitpos & this.nodeMap()) == 0) continue;
            ++oldNodeIndex;
        }
        return new BitmapIndexedSetNode<A>(newDataMap, newNodeMap, newContent, newOriginalHashes, newSize, newCachedHashCode);
    }

    /*
     * WARNING - void declaration
     */
    public boolean equals(Object that) {
        block6: {
            block8: {
                block7: {
                    boolean bl;
                    void deepContentEquality_a2;
                    if (!(that instanceof BitmapIndexedSetNode)) break block6;
                    BitmapIndexedSetNode bitmapIndexedSetNode = (BitmapIndexedSetNode)that;
                    if (this == bitmapIndexedSetNode) break block7;
                    if (this.cachedJavaKeySetHashCode() != bitmapIndexedSetNode.cachedJavaKeySetHashCode() || this.nodeMap() != bitmapIndexedSetNode.nodeMap() || this.dataMap() != bitmapIndexedSetNode.dataMap() || this.size() != bitmapIndexedSetNode.size() || !Arrays.equals(this.originalHashes(), bitmapIndexedSetNode.originalHashes())) break block8;
                    int n = this.content().length;
                    Object[] objectArray = bitmapIndexedSetNode.content();
                    Object[] deepContentEquality_a1 = this.content();
                    if (deepContentEquality_a1 == deepContentEquality_a2) {
                        bl = true;
                    } else {
                        void deepContentEquality_length;
                        boolean deepContentEquality_isEqual = true;
                        for (int deepContentEquality_i = 0; deepContentEquality_isEqual && deepContentEquality_i < deepContentEquality_length; ++deepContentEquality_i) {
                            deepContentEquality_isEqual = BoxesRunTime.equals(deepContentEquality_a1[deepContentEquality_i], deepContentEquality_a2[deepContentEquality_i]);
                        }
                        bl = deepContentEquality_isEqual;
                    }
                    Object var3_5 = null;
                    objectArray = null;
                    if (!bl) break block8;
                }
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean deepContentEquality(Object[] a1, Object[] a2, int length) {
        if (a1 == a2) {
            return true;
        }
        boolean isEqual = true;
        for (int i = 0; isEqual && i < length; ++i) {
            isEqual = BoxesRunTime.equals(a1[i], a2[i]);
        }
        return isEqual;
    }

    public int hashCode() {
        throw new UnsupportedOperationException("Trie nodes do not support hashing.");
    }

    @Override
    public BitmapIndexedSetNode<A> copy() {
        Object[] contentClone = (Object[])this.content().clone();
        int contentLength = contentClone.length;
        for (int i = Integer.bitCount(this.dataMap()); i < contentLength; ++i) {
            contentClone[i] = ((SetNode)contentClone[i]).copy();
        }
        return new BitmapIndexedSetNode<A>(this.dataMap(), this.nodeMap(), contentClone, (int[])this.originalHashes().clone(), this.size(), this.cachedJavaKeySetHashCode());
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public BitmapIndexedSetNode<A> concat(SetNode<A> that, int shift) {
        if (that instanceof BitmapIndexedSetNode) {
            BitmapIndexedSetNode bitmapIndexedSetNode = (BitmapIndexedSetNode)that;
            if (this.size() == 0) {
                return bitmapIndexedSetNode;
            }
            if (bitmapIndexedSetNode.size() == 0 || bitmapIndexedSetNode == this) {
                return this;
            }
            if (bitmapIndexedSetNode.size() == 1) {
                int originalHash = bitmapIndexedSetNode.getHash(0);
                return this.updated((Object)bitmapIndexedSetNode.getPayload(0), originalHash, Hashing$.MODULE$.improve(originalHash), shift);
            }
            boolean anyChangesMadeSoFar = false;
            int allMap = this.dataMap() | bitmapIndexedSetNode.dataMap() | this.nodeMap() | bitmapIndexedSetNode.nodeMap();
            int minimumBitPos = Node$.MODULE$.bitposFrom(Integer.numberOfTrailingZeros(allMap));
            int maximumBitPos = Node$.MODULE$.bitposFrom(32 - Integer.numberOfLeadingZeros(allMap) - 1);
            int leftNodeRightNode = 0;
            int leftDataRightNode = 0;
            int leftNodeRightData = 0;
            int leftDataOnly = 0;
            int rightDataOnly = 0;
            int leftNodeOnly = 0;
            int rightNodeOnly = 0;
            int leftDataRightDataMigrateToNode = 0;
            int leftDataRightDataLeftOverwrites = 0;
            int dataToNodeMigrationTargets = 0;
            int bitpos = minimumBitPos;
            int leftIdx = 0;
            int rightIdx = 0;
            boolean finished = false;
            while (!finished) {
                if ((bitpos & this.dataMap()) != 0) {
                    if ((bitpos & bitmapIndexedSetNode.dataMap()) != 0) {
                        if (this.getHash(leftIdx) == bitmapIndexedSetNode.getHash(rightIdx) && BoxesRunTime.equals(this.getPayload(leftIdx), bitmapIndexedSetNode.getPayload(rightIdx))) {
                            leftDataRightDataLeftOverwrites |= bitpos;
                        } else {
                            leftDataRightDataMigrateToNode |= bitpos;
                            dataToNodeMigrationTargets |= Node$.MODULE$.bitposFrom(Node$.MODULE$.maskFrom(Hashing$.MODULE$.improve(this.getHash(leftIdx)), shift));
                        }
                        ++rightIdx;
                    } else if ((bitpos & bitmapIndexedSetNode.nodeMap()) != 0) {
                        leftDataRightNode |= bitpos;
                    } else {
                        leftDataOnly |= bitpos;
                    }
                    ++leftIdx;
                } else if ((bitpos & this.nodeMap()) != 0) {
                    if ((bitpos & bitmapIndexedSetNode.dataMap()) != 0) {
                        leftNodeRightData |= bitpos;
                        ++rightIdx;
                    } else if ((bitpos & bitmapIndexedSetNode.nodeMap()) != 0) {
                        leftNodeRightNode |= bitpos;
                    } else {
                        leftNodeOnly |= bitpos;
                    }
                } else if ((bitpos & bitmapIndexedSetNode.dataMap()) != 0) {
                    rightDataOnly |= bitpos;
                    ++rightIdx;
                } else if ((bitpos & bitmapIndexedSetNode.nodeMap()) != 0) {
                    rightNodeOnly |= bitpos;
                }
                if (bitpos == maximumBitPos) {
                    finished = true;
                    continue;
                }
                bitpos <<= 1;
            }
            int newDataMap = leftDataOnly | rightDataOnly | leftDataRightDataLeftOverwrites;
            int newNodeMap = leftNodeRightNode | leftDataRightNode | leftNodeRightData | leftNodeOnly | rightNodeOnly | dataToNodeMigrationTargets;
            if (newDataMap == (leftDataOnly | leftDataRightDataLeftOverwrites) && newNodeMap == leftNodeOnly) {
                return this;
            }
            int newDataSize = Integer.bitCount(newDataMap);
            int newContentSize = newDataSize + Integer.bitCount(newNodeMap);
            Object[] newContent = new Object[newContentSize];
            int[] newOriginalHashes = new int[newDataSize];
            int newSize = 0;
            int newCachedHashCode = 0;
            int leftDataIdx = 0;
            int rightDataIdx = 0;
            int leftNodeIdx = 0;
            int rightNodeIdx = 0;
            int nextShift = shift + 5;
            int compressedDataIdx = 0;
            int compressedNodeIdx = 0;
            int bitpos2 = minimumBitPos;
            boolean finished2 = false;
            while (!finished2) {
                if ((bitpos2 & leftNodeRightNode) != 0) {
                    SetNode newNode;
                    Node leftNode = this.getNode(leftNodeIdx);
                    if (leftNode != (newNode = ((SetNode)leftNode).concat(bitmapIndexedSetNode.getNode(rightNodeIdx), nextShift))) {
                        anyChangesMadeSoFar = true;
                    }
                    newContent[newContentSize - compressedNodeIdx - 1] = newNode;
                    ++compressedNodeIdx;
                    ++rightNodeIdx;
                    ++leftNodeIdx;
                    newSize += newNode.size();
                    newCachedHashCode += newNode.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & leftDataRightNode) != 0) {
                    SetNode<A> newNode;
                    anyChangesMadeSoFar = true;
                    Node n = bitmapIndexedSetNode.getNode(rightNodeIdx);
                    A leftPayload = this.getPayload(leftDataIdx);
                    int leftOriginalHash = this.getHash(leftDataIdx);
                    int leftImproved = Hashing$.MODULE$.improve(leftOriginalHash);
                    newContent[newContentSize - compressedNodeIdx - 1] = newNode = ((SetNode)n).updated(leftPayload, leftOriginalHash, leftImproved, nextShift);
                    ++compressedNodeIdx;
                    ++rightNodeIdx;
                    ++leftDataIdx;
                    newSize += newNode.size();
                    newCachedHashCode += newNode.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & leftNodeRightData) != 0) {
                    void var49_49;
                    int rightOriginalHash = bitmapIndexedSetNode.getHash(rightDataIdx);
                    Node leftNode = this.getNode(leftNodeIdx);
                    SetNode<A> updated = ((SetNode)leftNode).updated(bitmapIndexedSetNode.getPayload(rightDataIdx), bitmapIndexedSetNode.getHash(rightDataIdx), Hashing$.MODULE$.improve(rightOriginalHash), nextShift);
                    if (updated != leftNode) {
                        anyChangesMadeSoFar = true;
                    }
                    newContent[newContentSize - compressedNodeIdx - 1] = var49_49;
                    ++compressedNodeIdx;
                    ++leftNodeIdx;
                    ++rightDataIdx;
                    newSize += var49_49.size();
                    newCachedHashCode += var49_49.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & leftDataOnly) != 0) {
                    int originalHash = this.originalHashes()[leftDataIdx];
                    newContent[compressedDataIdx] = this.getPayload(leftDataIdx);
                    newOriginalHashes[compressedDataIdx] = originalHash;
                    ++compressedDataIdx;
                    ++leftDataIdx;
                    ++newSize;
                    newCachedHashCode += Hashing$.MODULE$.improve(originalHash);
                } else if ((bitpos2 & rightDataOnly) != 0) {
                    anyChangesMadeSoFar = true;
                    int originalHash = bitmapIndexedSetNode.originalHashes()[rightDataIdx];
                    newContent[compressedDataIdx] = bitmapIndexedSetNode.getPayload(rightDataIdx);
                    newOriginalHashes[compressedDataIdx] = originalHash;
                    ++compressedDataIdx;
                    ++rightDataIdx;
                    ++newSize;
                    newCachedHashCode += Hashing$.MODULE$.improve(originalHash);
                } else if ((bitpos2 & leftNodeOnly) != 0) {
                    Node newNode = this.getNode(leftNodeIdx);
                    newContent[newContentSize - compressedNodeIdx - 1] = newNode;
                    ++compressedNodeIdx;
                    ++leftNodeIdx;
                    newSize += ((SetNode)newNode).size();
                    newCachedHashCode += newNode.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & rightNodeOnly) != 0) {
                    anyChangesMadeSoFar = true;
                    Node newNode = bitmapIndexedSetNode.getNode(rightNodeIdx);
                    newContent[newContentSize - compressedNodeIdx - 1] = newNode;
                    ++compressedNodeIdx;
                    ++rightNodeIdx;
                    newSize += ((SetNode)newNode).size();
                    newCachedHashCode += newNode.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & leftDataRightDataMigrateToNode) != 0) {
                    SetNode<A> newNode;
                    anyChangesMadeSoFar = true;
                    int leftOriginalHash = this.getHash(leftDataIdx);
                    int rightOriginalHash = bitmapIndexedSetNode.getHash(rightDataIdx);
                    newContent[newContentSize - compressedNodeIdx - 1] = newNode = bitmapIndexedSetNode.mergeTwoKeyValPairs(this.getPayload(leftDataIdx), leftOriginalHash, Hashing$.MODULE$.improve(leftOriginalHash), bitmapIndexedSetNode.getPayload(rightDataIdx), rightOriginalHash, Hashing$.MODULE$.improve(rightOriginalHash), nextShift);
                    ++compressedNodeIdx;
                    ++leftDataIdx;
                    ++rightDataIdx;
                    newSize += newNode.size();
                    newCachedHashCode += newNode.cachedJavaKeySetHashCode();
                } else if ((bitpos2 & leftDataRightDataLeftOverwrites) != 0) {
                    int originalHash = bitmapIndexedSetNode.originalHashes()[rightDataIdx];
                    newContent[compressedDataIdx] = bitmapIndexedSetNode.getPayload(rightDataIdx);
                    newOriginalHashes[compressedDataIdx] = originalHash;
                    ++compressedDataIdx;
                    ++rightDataIdx;
                    ++newSize;
                    newCachedHashCode += Hashing$.MODULE$.improve(originalHash);
                    ++leftDataIdx;
                }
                if (bitpos2 == maximumBitPos) {
                    finished2 = true;
                    continue;
                }
                bitpos2 <<= 1;
            }
            if (anyChangesMadeSoFar) {
                return new BitmapIndexedSetNode<A>(newDataMap, newNodeMap, newContent, newOriginalHashes, newSize, newCachedHashCode);
            }
            return this;
        }
        throw new UnsupportedOperationException("Cannot concatenate a HashCollisionSetNode with a BitmapIndexedSetNode");
    }

    @Override
    public void foreachWithHash(Function2<A, Object, BoxedUnit> f) {
        int iN = this.payloadArity();
        for (int i = 0; i < iN; ++i) {
            f.apply(this.content()[i], this.originalHashes()[i]);
        }
        int jN = this.nodeArity();
        for (int j = 0; j < jN; ++j) {
            ((SetNode)this.getNode(j)).foreachWithHash(f);
        }
    }

    @Override
    public boolean foreachWithHashWhile(Function2<A, Object, Object> f) {
        int thisPayloadArity = this.payloadArity();
        boolean pass = true;
        for (int i = 0; i < thisPayloadArity && pass; ++i) {
            pass = pass && BoxesRunTime.unboxToBoolean(f.apply(this.content()[i], this.originalHashes()[i]));
        }
        int thisNodeArity = this.nodeArity();
        for (int j = 0; j < thisNodeArity && pass; ++j) {
            pass = pass && ((SetNode)this.getNode(j)).foreachWithHashWhile(f);
        }
        return pass;
    }

    public BitmapIndexedSetNode(int dataMap, int nodeMap, Object[] content, int[] originalHashes, int size, int cachedJavaKeySetHashCode) {
        this.dataMap = dataMap;
        this.nodeMap = nodeMap;
        this.content = content;
        this.originalHashes = originalHashes;
        this.size = size;
        this.cachedJavaKeySetHashCode = cachedJavaKeySetHashCode;
    }
}

