/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.LinearSeqOps;
import scala.collection.MapFactory;
import scala.collection.SeqMap;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.AbstractMap;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.List;
import scala.collection.immutable.ListMap$;
import scala.collection.immutable.ListMapBuilder;
import scala.collection.immutable.Map;
import scala.collection.immutable.MapOps;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.StrictOptimizedMapOps;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ReusableBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.Either;
import scala.util.hashing.MurmurHash3$;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005\r\u0005b\u0001B\u001c9!}BQ!\u001b\u0001\u0005\u0002)DQa\u001b\u0001\u0005B1DQ\u0001\u001d\u0001\u0005BEDQ!\u001e\u0001\u0005BYDQA\u001f\u0001\u0005BEDQa\u001f\u0001\u0005\u0002qDq!!\u0002\u0001\t\u0003\t9\u0001C\u0004\u0002\u001c\u0001!\t!!\b\t\u000f\u0005\u0005\u0002\u0001\"\u0001\u0002$!9\u0011\u0011\u0007\u0001\u0005B\u0005M\u0002bBA\u001c\u0001\u0011\u0005\u0013\u0011\b\u0005\t\u0003\u0007\u0001A\u0011\u0001\u001d\u0002<!A\u0011\u0011\u0004\u0001\u0005\u0002a\ni\u0004\u0003\u0005\u0002@\u0001!\t\u0001OA!\u0011\u001d\t\u0019\u0005\u0001C!\u0003\u000bB\u0001\"a\u0018\u0001A\u0013E\u0013\u0011M\u0004\b\u0007;A\u0004\u0012AAA\r\u00199\u0004\b#\u0001\u0002x!1\u0011N\u0005C\u0001\u0003\u007f2q!a!\u0013\u0005a\n)\tC\u0006\u0002\u0004Q\u0011)\u0019!C!q\u0005M\u0005BCAK)\t\u0005\t\u0015!\u0003\u0002\f\"Y\u0011q\u0013\u000b\u0003\u0002\u0004%\t\u0001OAM\u0011-\tY\n\u0006BA\u0002\u0013\u0005\u0001(!(\t\u0015\u0005%FC!A!B\u0013\ty\tC\u0006\u0002,R\u0011\t\u0019!C\u0001q\u00055\u0006bCAX)\t\u0005\r\u0011\"\u00019\u0003cC!\"!.\u0015\u0005\u0003\u0005\u000b\u0015BAE\u0011\u0019IG\u0003\"\u0001\u00028\"A\u0011\u0011\u0004\u000b\u0005Ba\nI\nC\u0003q)\u0011\u0005\u0013\u000f\u0003\u0005\u0002DR\u0001K\u0011BAc\u0011\u0015)H\u0003\"\u0011w\u0011\u0015QH\u0003\"\u0011r\u0011\u001d\ti\u000e\u0006C!\u0003?D\u0001Ba\u000e\u0015A\u0013%!\u0011\b\u0005\u0007wR!\tE!\u0011\t\u0011\t\u001dC\u0003)C\u0005\u0005\u0013BqA!\u0015\u0015\t\u0003\u0012\u0019\u0006\u0003\u0005\u0003XQ\u0001K\u0011\u0002B-\u0011\u001d\t)\u0001\u0006C!\u0005CB\u0001Ba\u001d\u0015A\u0013%!Q\u000f\u0005\b\u00037!B\u0011\tBC\u0011!\ty\u0004\u0006C!q\u00055\u0006b\u0002BE)\u0011\u0005#1\u0012\u0005\b\u0005\u001f#B\u0011IAW\u0011\u001d\u0011\tJ\u0005C\u0001\u0005';qA!)\u0013\u0011\u0013\u0011\u0019KB\u0004\u0002vIAIaa\u0006\t\r%\fD\u0011AB\u000e\u0011\u001d\u0011)K\u0005C\u0001\u0005OCqA!1\u0013\t\u0003\u0011\u0019\rC\u0004\u0003`J!IA!9\t\u0013\r\r!#!A\u0005\n\r\u0015!a\u0002'jgRl\u0015\r\u001d\u0006\u0003si\n\u0011\"[7nkR\f'\r\\3\u000b\u0005mb\u0014AC2pY2,7\r^5p]*\tQ(A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0007\u0001;%k\u0005\u0004\u0001\u0003R;Fl\u0019\t\u0005\u0005\u000e+\u0015+D\u00019\u0013\t!\u0005HA\u0006BEN$(/Y2u\u001b\u0006\u0004\bC\u0001$H\u0019\u0001!Q\u0001\u0013\u0001C\u0002%\u0013\u0011aS\t\u0003\u0015:\u0003\"a\u0013'\u000e\u0003qJ!!\u0014\u001f\u0003\u000f9{G\u000f[5oOB\u00111jT\u0005\u0003!r\u00121!\u00118z!\t1%\u000b\u0002\u0004T\u0001\u0011\u0015\r!\u0013\u0002\u0002-B!!)V#R\u0013\t1\u0006H\u0001\u0004TKFl\u0015\r\u001d\t\u0007\u0005b+\u0015KW.\n\u0005eC$!F*ue&\u001cGo\u00149uS6L'0\u001a3NCB|\u0005o\u001d\t\u0003\u0005\u0002\u0001BA\u0011\u0001F#B1QLX#R5\u0002l\u0011AO\u0005\u0003?j\u0012!#T1q\r\u0006\u001cGo\u001c:z\t\u00164\u0017-\u001e7ugB\u0011!)Y\u0005\u0003Eb\u0012\u0001\"\u0013;fe\u0006\u0014G.\u001a\t\u0003I\u001el\u0011!\u001a\u0006\u0003Mj\nqaZ3oKJL7-\u0003\u0002iK\n\u0019B)\u001a4bk2$8+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\"\u0012aW\u0001\u000b[\u0006\u0004h)Y2u_JLX#A7\u0011\u0007us',\u0003\u0002pu\tQQ*\u00199GC\u000e$xN]=\u0002\tML'0Z\u000b\u0002eB\u00111j]\u0005\u0003ir\u00121!\u00138u\u0003\u001dI7/R7qif,\u0012a\u001e\t\u0003\u0017bL!!\u001f\u001f\u0003\u000f\t{w\u000e\\3b]\u0006I1N\\8x]NK'0Z\u0001\u0004O\u0016$HcA?\u0002\u0002A\u00191J`)\n\u0005}d$AB(qi&|g\u000e\u0003\u0004\u0002\u0004\u0019\u0001\r!R\u0001\u0004W\u0016L\u0018aB;qI\u0006$X\rZ\u000b\u0005\u0003\u0013\ty\u0001\u0006\u0004\u0002\f\u0005U\u0011q\u0003\t\u0006\u0005\u0002)\u0015Q\u0002\t\u0004\r\u0006=AaBA\t\u000f\t\u0007\u00111\u0003\u0002\u0003-F\n\"!\u0015(\t\r\u0005\rq\u00011\u0001F\u0011\u001d\tIb\u0002a\u0001\u0003\u001b\tQA^1mk\u0016\fqA]3n_Z,G\rF\u0002\\\u0003?Aa!a\u0001\t\u0001\u0004)\u0015\u0001C5uKJ\fGo\u001c:\u0016\u0005\u0005\u0015\u0002#B/\u0002(\u0005-\u0012bAA\u0015u\tA\u0011\n^3sCR|'\u000fE\u0003L\u0003[)\u0015+C\u0002\u00020q\u0012a\u0001V;qY\u0016\u0014\u0014\u0001B6fsN,\"!!\u000e\u0011\u0007\t\u000bW)\u0001\u0005iCND7i\u001c3f)\u0005\u0011X#A#\u0016\u0003E\u000bAA\\3yiV\t1,A\u0005g_2$'+[4iiV!\u0011qIA')\u0011\tI%a\u0017\u0015\t\u0005-\u0013\u0011\u000b\t\u0004\r\u00065CABA(\u001f\t\u0007\u0011JA\u0001[\u0011\u001d\t\u0019f\u0004a\u0001\u0003+\n!a\u001c9\u0011\u0013-\u000b9&a\u000b\u0002L\u0005-\u0013bAA-y\tIa)\u001e8di&|gN\r\u0005\b\u0003;z\u0001\u0019AA&\u0003\u0005Q\u0018!C2mCN\u001ch*Y7f+\t\t\u0019\u0007\u0005\u0003\u0002f\u0005=TBAA4\u0015\u0011\tI'a\u001b\u0002\t1\fgn\u001a\u0006\u0003\u0003[\nAA[1wC&!\u0011\u0011OA4\u0005\u0019\u0019FO]5oO&\u001a\u0001!\r\u000b\u0003\u0019\u0015k\u0007\u000f^=MSN$X*\u00199\u0014\tI\tI(\u001c\t\u0004\u0017\u0006m\u0014bAA?y\t1\u0011I\\=SK\u001a$\"!!!\u0011\u0005\t\u0013\"\u0001\u0002(pI\u0016,b!a\"\u0002\u000e\u0006E5c\u0001\u000b\u0002\nB1!\tAAF\u0003\u001f\u00032ARAG\t\u0015AEC1\u0001J!\r1\u0015\u0011\u0013\u0003\u0006'R\u0011\r!S\u000b\u0003\u0003\u0017\u000bAa[3zA\u00051qL^1mk\u0016,\"!a$\u0002\u0015}3\u0018\r\\;f?\u0012*\u0017\u000f\u0006\u0003\u0002 \u0006\u0015\u0006cA&\u0002\"&\u0019\u00111\u0015\u001f\u0003\tUs\u0017\u000e\u001e\u0005\n\u0003OC\u0012\u0011!a\u0001\u0003\u001f\u000b1\u0001\u001f\u00132\u0003\u001dyf/\u00197vK\u0002\nQaX5oSR,\"!!#\u0002\u0013}Kg.\u001b;`I\u0015\fH\u0003BAP\u0003gC\u0011\"a*\u001c\u0003\u0003\u0005\r!!#\u0002\r}Kg.\u001b;!)!\tI,!0\u0002@\u0006\u0005\u0007cBA^)\u0005-\u0015qR\u0007\u0002%!9\u00111A\u000fA\u0002\u0005-\u0005bBAL;\u0001\u0007\u0011q\u0012\u0005\b\u0003Wk\u0002\u0019AAE\u00031\u0019\u0018N_3J]R,'O\\1m)\u0015\u0011\u0018qYAf\u0011\u001d\tI\r\ta\u0001\u0003\u0013\u000b1aY;s\u0011\u0019\ti\r\ta\u0001e\u0006\u0019\u0011mY2)\u0007\u0001\n\t\u000e\u0005\u0003\u0002T\u0006eWBAAk\u0015\r\t9\u000eP\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BAn\u0003+\u0014q\u0001^1jYJ,7-A\u0003baBd\u0017\u0010\u0006\u0003\u0002\u0010\u0006\u0005\bbBArG\u0001\u0007\u00111R\u0001\u0002W\"*1%a:\u0002|B)1*!;\u0002n&\u0019\u00111\u001e\u001f\u0003\rQD'o\\<t!\u0011\ty/!>\u000f\u0007-\u000b\t0C\u0002\u0002tr\nq\u0001]1dW\u0006<W-\u0003\u0003\u0002x\u0006e(A\u0006(p'V\u001c\u0007.\u00127f[\u0016tG/\u0012=dKB$\u0018n\u001c8\u000b\u0007\u0005MH(M\u0004\u001f\u0003{\u0014\tB!\u000e\u0011\t\u0005}(Q\u0002\b\u0005\u0005\u0003\u0011I\u0001E\u0002\u0003\u0004qj!A!\u0002\u000b\u0007\t\u001da(\u0001\u0004=e>|GOP\u0005\u0004\u0005\u0017a\u0014A\u0002)sK\u0012,g-\u0003\u0003\u0002r\t=!b\u0001B\u0006yEJ1Ea\u0005\u0003\u001c\t-\"QD\u000b\u0005\u0005+\u00119\"\u0006\u0002\u0002~\u00129!\u0011\u0004 C\u0002\t\r\"!\u0001+\n\t\tu!qD\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u0019\u000b\u0007\t\u0005B(\u0001\u0004uQJ|wo]\t\u0004\u0015\n\u0015\u0002\u0003BAx\u0005OIAA!\u000b\u0002z\nIA\u000b\u001b:po\u0006\u0014G.Z\u0019\nG\t5\"q\u0006B\u0019\u0005Cq1a\u0013B\u0018\u0013\r\u0011\t\u0003P\u0019\u0006E-c$1\u0007\u0002\u0006g\u000e\fG.Y\u0019\u0004M\u00055\u0018!D1qa2L\u0018J\u001c;fe:\fG\u000e\u0006\u0004\u0002\u0010\nm\"Q\b\u0005\b\u0003\u0013$\u0003\u0019AAE\u0011\u001d\t\u0019\u000f\na\u0001\u0003\u0017C3\u0001JAi)\u0011\u0011\u0019E!\u0012\u0011\t-s\u0018q\u0012\u0005\b\u0003G,\u0003\u0019AAF\u0003-9W\r^%oi\u0016\u0014h.\u00197\u0015\r\t\r#1\nB'\u0011\u001d\tIM\na\u0001\u0003\u0013Cq!a9'\u0001\u0004\tY\tK\u0002'\u0003#\f\u0001bY8oi\u0006Lgn\u001d\u000b\u0004o\nU\u0003bBArO\u0001\u0007\u00111R\u0001\u0011G>tG/Y5og&sG/\u001a:oC2$Ra\u001eB.\u0005;Bq!!3)\u0001\u0004\tI\tC\u0004\u0002d\"\u0002\r!a#)\u0007!\n\t.\u0006\u0003\u0003d\t%DC\u0002B3\u0005[\u0012y\u0007\u0005\u0004C\u0001\u0005-%q\r\t\u0004\r\n%DaBA\tS\t\u0007!1N\t\u0004\u0003\u001fs\u0005bBArS\u0001\u0007\u00111\u0012\u0005\b\u0005cJ\u0003\u0019\u0001B4\u0003\u00051\u0018A\u0004:f[>4X-\u00138uKJt\u0017\r\u001c\u000b\t\u0003\u0013\u00139H!\u001f\u0003|!9\u00111\u001d\u0016A\u0002\u0005-\u0005bBAeU\u0001\u0007\u0011\u0011\u0012\u0005\b\u0003\u001bT\u0003\u0019\u0001B?!\u0015\u0011%qPAE\u0013\r\u0011\t\t\u000f\u0002\u0005\u0019&\u001cH\u000fK\u0002+\u0003#$B!!#\u0003\b\"9\u00111]\u0016A\u0002\u0005-\u0015\u0001\u00027bgR,\"A!$\u0011\u000f-\u000bi#a#\u0002\u0010\u0006!\u0011N\\5u\u0003\u0015)W\u000e\u001d;z+\u0019\u0011)Ja'\u0003 V\u0011!q\u0013\t\u0007\u0005\u0002\u0011IJ!(\u0011\u0007\u0019\u0013Y\nB\u0003I_\t\u0007\u0011\nE\u0002G\u0005?#QaU\u0018C\u0002%\u000bA\"R7qifd\u0015n\u001d;NCB\u00042!a/2\u0003\u00111'o\\7\u0016\r\t%&q\u0016BZ)\u0011\u0011YK!.\u0011\r\t\u0003!Q\u0016BY!\r1%q\u0016\u0003\u0006\u0011N\u0012\r!\u0013\t\u0004\r\nMF!B*4\u0005\u0004I\u0005b\u0002B\\g\u0001\u0007!\u0011X\u0001\u0003SR\u0004R!\u0018B^\u0005\u007fK1A!0;\u00051IE/\u001a:bE2,wJ\\2f!\u001dY\u0015Q\u0006BW\u0005c\u000b!B\\3x\u0005VLG\u000eZ3s+\u0019\u0011)Ma6\u0003\\V\u0011!q\u0019\t\t\u0005\u0013\u0014yMa5\u0003^6\u0011!1\u001a\u0006\u0004\u0005\u001bT\u0014aB7vi\u0006\u0014G.Z\u0005\u0005\u0005#\u0014YMA\bSKV\u001c\u0018M\u00197f\u0005VLG\u000eZ3s!\u001dY\u0015Q\u0006Bk\u00053\u00042A\u0012Bl\t\u0015AEG1\u0001J!\r1%1\u001c\u0003\u0006'R\u0012\r!\u0013\t\u0007\u0005\u0002\u0011)N!7\u0002#\u0019|G\u000e\u001a*jO\"$\u0018J\u001c;fe:\fG.\u0006\u0005\u0003d\nE(Q\u001fBt)!\u0011)O!;\u0003x\nm\bc\u0001$\u0003h\u00121\u0011qJ\u001bC\u0002%CqAa;6\u0001\u0004\u0011i/A\u0002nCB\u0004bA\u0011\u0001\u0003p\nM\bc\u0001$\u0003r\u0012)\u0001*\u000eb\u0001\u0013B\u0019aI!>\u0005\u000bM+$\u0019A%\t\u000f\teX\u00071\u0001\u0003f\u0006I\u0001O]3w-\u0006dW/\u001a\u0005\b\u0003'*\u0004\u0019\u0001B\u007f!%Y\u0015q\u000bB\u0000\u0005K\u0014)\u000fE\u0004L\u0003[\u0011yOa=)\u0007U\n\t.\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004\bA!\u0011QMB\u0005\u0013\u0011\u0019Y!a\u001a\u0003\r=\u0013'.Z2uQ\u001d\u00112qBA\r\u0007+\u00012aSB\t\u0013\r\u0019\u0019\u0002\u0010\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012s\u0012aA\n\u0004c\re\u0001\u0003\u0002\"\u0001\u001d*#\"Aa)\u0002\u000f1K7\u000f^'ba\":\u0011ca\u0004\u0002\u001a\rU\u0001")
public class ListMap<K, V>
extends AbstractMap<K, V>
implements scala.collection.immutable.SeqMap<K, V>,
StrictOptimizedMapOps<K, V, ListMap, ListMap<K, V>>,
DefaultSerializable {
    public static <K, V> ReusableBuilder<Tuple2<K, V>, ListMap<K, V>> newBuilder() {
        return new ListMapBuilder();
    }

    public static <K, V> ListMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return ListMap$.MODULE$.from((IterableOnce)it);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public MapOps concat(IterableOnce that) {
        return StrictOptimizedMapOps.concat$(this, that);
    }

    @Override
    public IterableOps map(Function1 f) {
        return scala.collection.StrictOptimizedMapOps.map$(this, f);
    }

    @Override
    public IterableOps flatMap(Function1 f) {
        return scala.collection.StrictOptimizedMapOps.flatMap$(this, f);
    }

    @Override
    public IterableOps collect(PartialFunction pf) {
        return scala.collection.StrictOptimizedMapOps.collect$(this, pf);
    }

    @Override
    public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return scala.collection.StrictOptimizedMapOps.$plus$(this, elem1, elem2, elems);
    }

    @Override
    public Tuple2<ListMap<K, V>, ListMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<ListMap<K, V>, ListMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<Iterable<A1>, Iterable<A2>, Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Tuple2<K, V>, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatMap$(this, b, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedConcat(IterableOnce<B> that, Builder<B, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedConcat$(this, that, b);
    }

    @Override
    public Object collect(PartialFunction pf) {
        return StrictOptimizedIterableOps.collect$(this, pf);
    }

    @Override
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Tuple2<K, V>, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Tuple2<K, V>, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Tuple2<K, V>, B>, C2> b) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedZip$(this, that, b);
    }

    @Override
    public Object zipWithIndex() {
        return StrictOptimizedIterableOps.zipWithIndex$(this);
    }

    @Override
    public Object scanLeft(Object z, Function2 op) {
        return StrictOptimizedIterableOps.scanLeft$(this, z, op);
    }

    @Override
    public Object filter(Function1 pred) {
        return StrictOptimizedIterableOps.filter$(this, pred);
    }

    @Override
    public Object filterNot(Function1 pred) {
        return StrictOptimizedIterableOps.filterNot$(this, pred);
    }

    @Override
    public Object filterImpl(Function1 pred, boolean isFlipped) {
        return StrictOptimizedIterableOps.filterImpl$(this, pred, isFlipped);
    }

    @Override
    public <A1, A2> Tuple2<Iterable<A1>, Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
        return StrictOptimizedIterableOps.partitionMap$(this, f);
    }

    @Override
    public Object tapEach(Function1 f) {
        return StrictOptimizedIterableOps.tapEach$(this, f);
    }

    @Override
    public Object takeRight(int n) {
        return StrictOptimizedIterableOps.takeRight$(this, n);
    }

    @Override
    public Object dropRight(int n) {
        return StrictOptimizedIterableOps.dropRight$(this, n);
    }

    @Override
    public String stringPrefix() {
        return SeqMap.stringPrefix$(this);
    }

    @Override
    public MapFactory<ListMap> mapFactory() {
        return ListMap$.MODULE$;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int knownSize() {
        return 0;
    }

    @Override
    public Option<V> get(K key) {
        return None$.MODULE$;
    }

    @Override
    public <V1> ListMap<K, V1> updated(K key, V1 value) {
        return new Node<K, V1>(key, value, this);
    }

    @Override
    public ListMap<K, V> removed(K key) {
        return this;
    }

    @Override
    public Iterator<Tuple2<K, V>> iterator() {
        ListMap<K, V> curr = this;
        List res = Nil$.MODULE$;
        while (curr.nonEmpty()) {
            Tuple2<K, V> tuple2 = new Tuple2<K, V>(curr.key(), curr.value());
            res = res.$colon$colon(tuple2);
            curr = curr.next();
        }
        return ((List)res).iterator();
    }

    @Override
    public Iterable<K> keys() {
        ListMap<K, V> curr = this;
        List res = Nil$.MODULE$;
        while (curr.nonEmpty()) {
            K k = curr.key();
            res = res.$colon$colon(k);
            curr = curr.next();
        }
        return res;
    }

    @Override
    public int hashCode() {
        if (this.isEmpty()) {
            return MurmurHash3$.MODULE$.emptyMapHash();
        }
        AbstractMap _reversed = new AbstractMap<K, V>(this){
            private final /* synthetic */ ListMap $outer;

            public boolean isEmpty() {
                return this.$outer.isEmpty();
            }

            public Map<K, V> removed(K key) {
                return this.$outer.removed((Object)key);
            }

            public <V1> Map<K, V1> updated(K key, V1 value) {
                return this.$outer.updated((Object)key, (Object)value);
            }

            public Option<V> get(K key) {
                return this.$outer.get(key);
            }

            public Iterator<Tuple2<K, V>> iterator() {
                return this.$outer.iterator();
            }

            public <U> void foreachEntry(Function2<K, V, U> f) {
                ListMap<K, V> curr = this.$outer;
                while (curr.nonEmpty()) {
                    f.apply(curr.key(), curr.value());
                    curr = curr.next();
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
        return MurmurHash3$.MODULE$.mapHash(_reversed);
    }

    public K key() {
        throw new NoSuchElementException("key of empty map");
    }

    public V value() {
        throw new NoSuchElementException("value of empty map");
    }

    public ListMap<K, V> next() {
        throw new NoSuchElementException("next of empty map");
    }

    @Override
    public <Z> Z foldRight(Z z, Function2<Tuple2<K, V>, Z, Z> op) {
        Z z2 = z;
        ListMap scala$collection$immutable$ListMap$$foldRightInternal_map = this;
        Z scala$collection$immutable$ListMap$$foldRightInternal_prevValue;
        while (!scala$collection$immutable$ListMap$$foldRightInternal_map.isEmpty()) {
            scala$collection$immutable$ListMap$$foldRightInternal_prevValue = op.apply((Tuple2<K, V>)scala$collection$immutable$ListMap$$foldRightInternal_map.last(), scala$collection$immutable$ListMap$$foldRightInternal_prevValue);
            scala$collection$immutable$ListMap$$foldRightInternal_map = (ListMap)scala$collection$immutable$ListMap$$foldRightInternal_map.init();
        }
        return scala$collection$immutable$ListMap$$foldRightInternal_prevValue;
    }

    @Override
    public String className() {
        return "ListMap";
    }

    public static final class Node<K, V>
    extends ListMap<K, V> {
        private final K key;
        private V _value;
        private ListMap<K, V> _init;

        @Override
        public K key() {
            return this.key;
        }

        public V _value() {
            return this._value;
        }

        public void _value_$eq(V x$1) {
            this._value = x$1;
        }

        public ListMap<K, V> _init() {
            return this._init;
        }

        public void _init_$eq(ListMap<K, V> x$1) {
            this._init = x$1;
        }

        @Override
        public V value() {
            return this._value();
        }

        @Override
        public int size() {
            return this.sizeInternal(this, 0);
        }

        private int sizeInternal(ListMap<K, V> cur, int acc) {
            while (!cur.isEmpty()) {
                ++acc;
                cur = cur.next();
            }
            return acc;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public int knownSize() {
            return -1;
        }

        @Override
        public V apply(K k) throws NoSuchElementException {
            return this.applyInternal(this, k);
        }

        private V applyInternal(ListMap<K, V> cur, K k) {
            while (true) {
                if (cur.isEmpty()) {
                    throw new NoSuchElementException(new StringBuilder(15).append("key not found: ").append(k).toString());
                }
                if (BoxesRunTime.equals(k, cur.key())) {
                    return cur.value();
                }
                cur = cur.next();
            }
        }

        @Override
        public Option<V> get(K k) {
            return this.getInternal(this, k);
        }

        private Option<V> getInternal(ListMap<K, V> cur, K k) {
            while (!cur.isEmpty()) {
                if (BoxesRunTime.equals(k, cur.key())) {
                    return new Some<V>(cur.value());
                }
                cur = cur.next();
            }
            return None$.MODULE$;
        }

        @Override
        public boolean contains(K k) {
            return this.containsInternal(this, k);
        }

        private boolean containsInternal(ListMap<K, V> cur, K k) {
            while (!cur.isEmpty()) {
                if (BoxesRunTime.equals(k, cur.key())) {
                    return true;
                }
                cur = cur.next();
            }
            return false;
        }

        @Override
        public <V1> ListMap<K, V1> updated(K k, V1 v) {
            int index = -1;
            boolean found = false;
            boolean isDifferent = false;
            ListMap curr = this;
            while (curr.nonEmpty() && !found) {
                if (BoxesRunTime.equals(k, ((ListMap)curr).key())) {
                    found = true;
                    isDifferent = v != ((ListMap)curr).value();
                }
                ++index;
                curr = (ListMap)((AbstractIterable)curr).init();
            }
            if (found) {
                if (isDifferent) {
                    Node newHead = null;
                    Node prev = null;
                    ListMap curr2 = this;
                    for (int i = 0; i < index; ++i) {
                        Node temp = new Node(((ListMap)curr2).key(), ((ListMap)curr2).value(), null);
                        if (prev != null) {
                            prev._init_$eq(temp);
                        }
                        prev = temp;
                        curr2 = (ListMap)((AbstractIterable)curr2).init();
                        if (newHead != null) continue;
                        newHead = prev;
                    }
                    Node newNode = new Node(((ListMap)curr2).key(), v, (ListMap)((AbstractIterable)curr2).init());
                    if (prev != null) {
                        prev._init_$eq(newNode);
                    }
                    Statics.releaseFence();
                    if (newHead == null) {
                        return newNode;
                    }
                    return newHead;
                }
                return this;
            }
            return new Node<K, V1>(k, v, this);
        }

        /*
         * WARNING - void declaration
         */
        private ListMap<K, V> removeInternal(K k, ListMap<K, V> cur, List<ListMap<K, V>> acc) {
            while (!cur.isEmpty()) {
                if (BoxesRunTime.equals(k, cur.key())) {
                    void foldLeft_op;
                    void foldLeft_z;
                    Function2<ListMap, ListMap, Node> & Serializable intersect = (Function2<ListMap, ListMap, Node> & Serializable)(t2, h2) -> new Node(h2.key(), h2.value(), t2);
                    ListMap<K, V> listMap = cur.next();
                    if (acc == null) {
                        throw null;
                    }
                    return (ListMap)LinearSeqOps.foldLeft$(acc, (Object)foldLeft_z, (Function2)foldLeft_op);
                }
                if (acc == null) {
                    throw null;
                }
                acc = new $colon$colon<ListMap<K, V>>(cur, acc);
                cur = cur.next();
            }
            return acc.last();
        }

        @Override
        public ListMap<K, V> removed(K k) {
            return this.removeInternal(k, this, Nil$.MODULE$);
        }

        @Override
        public ListMap<K, V> next() {
            return this._init();
        }

        @Override
        public Tuple2<K, V> last() {
            return new Tuple2<K, V>(this.key(), this._value());
        }

        @Override
        public ListMap<K, V> init() {
            return this._init();
        }

        public Node(K key, V _value, ListMap<K, V> _init) {
            this.key = key;
            this._value = _value;
            this._init = _init;
            Statics.releaseFence();
        }
    }
}

