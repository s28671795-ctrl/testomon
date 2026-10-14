/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.Map;
import scala.collection.SortedMapFactoryDefaults;
import scala.collection.SortedMapOps;
import scala.collection.SortedOps;
import scala.collection.SortedSet;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StepperShape$;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedMapOps;
import scala.collection.StrictOptimizedSortedMapOps;
import scala.collection.convert.impl.AnyBinaryTreeStepper;
import scala.collection.convert.impl.BinaryTreeStepper$;
import scala.collection.convert.impl.DoubleBinaryTreeStepper;
import scala.collection.convert.impl.IntBinaryTreeStepper;
import scala.collection.convert.impl.LongBinaryTreeStepper;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.Seq;
import scala.collection.mutable.AbstractMap;
import scala.collection.mutable.Builder;
import scala.collection.mutable.MapOps;
import scala.collection.mutable.RedBlackTree;
import scala.collection.mutable.RedBlackTree$;
import scala.collection.mutable.RedBlackTree$Tree$;
import scala.collection.mutable.SortedMap;
import scala.collection.mutable.SortedMapOps;
import scala.collection.mutable.TreeMap$;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.util.Either;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ScalaSignature(bytes="\u0006\u0005\r5d\u0001\u0002!B!!C!\"a\u0001\u0001\u0005\u0003\u0005\u000b\u0011BA\u0003\u0011)\t\u0019\u0002\u0001BC\u0002\u0013\r\u0011Q\u0003\u0005\u000b\u0003K\u0001!\u0011!Q\u0001\n\u0005]\u0001bBA\u0014\u0001\u0011%\u0011\u0011\u0006\u0005\b\u0003c\u0001A\u0011IA\u001a\u0011\u001d\t9\u0003\u0001C\u0001\u0003\u007fCq!a2\u0001\t\u0003\tI\rC\u0004\u0002R\u0002!\t%a5\t\u000f\u0005]\u0007\u0001\"\u0011\u0002Z\"9\u0011Q\u001c\u0001\u0005\u0002\u0005}\u0007bBAs\u0001\u0011\u0005\u0011q\u001d\u0005\b\u0003W\u0004A\u0011IAw\u0011\u001d\t\t\u0010\u0001C!\u0003gDqA!\u000f\u0001\t\u0003\u0012Y\u0004C\u0004\u0003X\u0001!\tE!\u0017\t\u000f\tU\u0004\u0001\"\u0001\u0003x!9!q\u0010\u0001\u0005\u0002\t\u0005\u0005b\u0002BC\u0001\u0011\u0005#q\u0011\u0005\b\u0005\u001f\u0003A\u0011\u0001BI\u0011\u001d\u0011i\n\u0001C\u0001\u0005?CqA!+\u0001\t\u0003\u0012Y\u000bC\u0004\u0003@\u0002!\tE!1\t\u000f\tE\u0007\u0001\"\u0011\u0003T\"9!1\u001c\u0001\u0005B\tM\u0007b\u0002Bo\u0001\u0011\u0005#q\u001c\u0005\b\u0005O\u0004A\u0011\tBu\u0011\u001d\u0011i\u000f\u0001C!\u0005_DqA!=\u0001\t\u0003\u0012y\u000fC\u0004\u0003t\u0002!\tE!>\t\u000f\tm\b\u0001\"\u0011\u0003~\"A1\u0011\u0001\u0001!\n#\u001a\u0019A\u0002\u0005\u0004\u0016\u0001\u0001\u000bQBB\f\u0011)\t9\u0005\tB\u0001B\u0003%!1\u0015\u0005\u000b\u0005O\u0003#\u0011!Q\u0001\n\t\r\u0006bBA\u0014A\u0011\u00051\u0011\u0004\u0005\t\u0007C\u0001\u0003\u0015\"\u0003\u0004$!A1\u0011\u0006\u0011!\n\u0013\u0019Y\u0003\u0003\u0005\u00042\u0001\u0002K\u0011BB\u001a\u0011\u001d\u0011i\n\tC!\u0007oAqAa$!\t\u0003\u001ai\u0004C\u0004\u0002H\u0002\"\t%!3\t\u000f\u0005E\u0007\u0005\"\u0011\u0002T\"9\u0011q\u001b\u0011\u0005B\u0005e\u0007bBAoA\u0011\u00053\u0011\t\u0005\b\u0003K\u0004C\u0011IB#\u0011\u001d\tY\u000f\tC!\u0007\u0013BqA!5!\t\u0003\u0012\u0019\u000eC\u0004\u0003\\\u0002\"\tEa5\t\u000f\tu\u0007\u0005\"\u0011\u0003`\"9!q\u001d\u0011\u0005B\r5\u0003b\u0002BwA\u0011\u0005#q\u001e\u0005\b\u0007#\u0002C\u0011IB*\u0011\u001d\u0011\t\u0010\tC!\u0005_Dqa!\u0016!\t\u0003\u001a\u0019\u0006C\u0004\u0003*\u0002\"\tea\u0016\t\u000f\r\r\u0004\u0005\"\u0011\u0004f\u001d91\u0011N!\t\u0002\u0005UbA\u0002!B\u0011\u0003\t9\u0004C\u0004\u0002(i\"\t!!\u0012\t\u000f\u0005\u001d#\b\"\u0001\u0002J!9\u00111\u000e\u001e\u0005\u0002\u00055\u0004bBAAu\u0011\u0005\u00111\u0011\u0005\n\u0003?S\u0014\u0011!C\u0005\u0003C\u0013q\u0001\u0016:fK6\u000b\u0007O\u0003\u0002C\u0007\u00069Q.\u001e;bE2,'B\u0001#F\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\r\u0006)1oY1mC\u000e\u0001QcA%Q7NI\u0001AS/aK>,\bp\u001f\t\u0005\u00172s%,D\u0001B\u0013\ti\u0015IA\u0006BEN$(/Y2u\u001b\u0006\u0004\bCA(Q\u0019\u0001!Q!\u0015\u0001C\u0002I\u0013\u0011aS\t\u0003'^\u0003\"\u0001V+\u000e\u0003\u0015K!AV#\u0003\u000f9{G\u000f[5oOB\u0011A\u000bW\u0005\u00033\u0016\u00131!\u00118z!\ty5\fB\u0003]\u0001\t\u0007!KA\u0001W!\u0011YeL\u0014.\n\u0005}\u000b%!C*peR,G-T1q!\u0019Y\u0015M\u0014.dI&\u0011!-\u0011\u0002\r'>\u0014H/\u001a3NCB|\u0005o\u001d\t\u0003\u0017\u0002\u0001Ba\u0013\u0001O5B)amZ5mI6\t1)\u0003\u0002i\u0007\nQ2\u000b\u001e:jGR|\u0005\u000f^5nSj,G-\u0013;fe\u0006\u0014G.Z(qgB!AK\u001b([\u0013\tYWI\u0001\u0004UkBdWM\r\t\u0003\u00176L!A\\!\u0003\u0011%#XM]1cY\u0016\u0004bA\u001a9O5J$\u0017BA9D\u0005U\u0019FO]5di>\u0003H/[7ju\u0016$W*\u00199PaN\u0004\"aS:\n\u0005Q\f%aA'baB1aM\u001e([G\u0012L!a^\"\u00037M#(/[2u\u001fB$\u0018.\\5{K\u0012\u001cvN\u001d;fI6\u000b\u0007o\u00149t!\u001d1\u0017P\u0014.dYJL!A_\"\u00031M{'\u000f^3e\u001b\u0006\u0004h)Y2u_JLH)\u001a4bk2$8\u000f\u0005\u0002}\u007f6\tQP\u0003\u0002\u007f\u0007\u00069q-\u001a8fe&\u001c\u0017bAA\u0001{\n\u0019B)\u001a4bk2$8+\u001a:jC2L'0\u00192mK\u0006!AO]3f!\u0019\t9!!\u0004O5:\u00191*!\u0003\n\u0007\u0005-\u0011)\u0001\u0007SK\u0012\u0014E.Y2l)J,W-\u0003\u0003\u0002\u0010\u0005E!\u0001\u0002+sK\u0016T1!a\u0003B\u0003!y'\u000fZ3sS:<WCAA\f!\u0015\tI\"a\bO\u001d\r!\u00161D\u0005\u0004\u0003;)\u0015a\u00029bG.\fw-Z\u0005\u0005\u0003C\t\u0019C\u0001\u0005Pe\u0012,'/\u001b8h\u0015\r\ti\"R\u0001\n_J$WM]5oO\u0002\na\u0001P5oSRtD\u0003BA\u0016\u0003_!2\u0001ZA\u0017\u0011\u001d\t\u0019\u0002\u0002a\u0002\u0003/Aq!a\u0001\u0005\u0001\u0004\t)!\u0001\tt_J$X\rZ'ba\u001a\u000b7\r^8ssV\u0011\u0011Q\u0007\t\u0003\u0017j\u001aRAOA\u001d\u0003\u007f\u00012\u0001VA\u001e\u0013\r\ti$\u0012\u0002\u0007\u0003:L(+\u001a4\u0011\t\u0019\f\teY\u0005\u0004\u0003\u0007\u001a%\u0001E*peR,G-T1q\r\u0006\u001cGo\u001c:z)\t\t)$\u0001\u0003ge>lWCBA&\u0003'\n9\u0006\u0006\u0003\u0002N\u0005}C\u0003BA(\u00033\u0002ba\u0013\u0001\u0002R\u0005U\u0003cA(\u0002T\u0011)\u0011\u000b\u0010b\u0001%B\u0019q*a\u0016\u0005\u000bqc$\u0019\u0001*\t\u0013\u0005mC(!AA\u0004\u0005u\u0013AC3wS\u0012,gnY3%cA1\u0011\u0011DA\u0010\u0003#Bq!!\u0019=\u0001\u0004\t\u0019'\u0001\u0002jiB)a-!\u001a\u0002j%\u0019\u0011qM\"\u0003\u0019%#XM]1cY\u0016|enY3\u0011\rQS\u0017\u0011KA+\u0003\u0015)W\u000e\u001d;z+\u0019\ty'!\u001e\u0002zQ!\u0011\u0011OA>!\u0019Y\u0005!a\u001d\u0002xA\u0019q*!\u001e\u0005\u000bEk$\u0019\u0001*\u0011\u0007=\u000bI\bB\u0003]{\t\u0007!\u000bC\u0005\u0002~u\n\t\u0011q\u0001\u0002\u0000\u0005QQM^5eK:\u001cW\r\n\u001a\u0011\r\u0005e\u0011qDA:\u0003)qWm\u001e\"vS2$WM]\u000b\u0007\u0003\u000b\u000b\t*!&\u0015\t\u0005\u001d\u0015\u0011\u0014\t\b\u0017\u0006%\u0015QRAL\u0013\r\tY)\u0011\u0002\b\u0005VLG\u000eZ3s!\u0019!&.a$\u0002\u0014B\u0019q*!%\u0005\u000bEs$\u0019\u0001*\u0011\u0007=\u000b)\nB\u0003]}\t\u0007!\u000b\u0005\u0004L\u0001\u0005=\u00151\u0013\u0005\n\u00037s\u0014\u0011!a\u0002\u0003;\u000b!\"\u001a<jI\u0016t7-\u001a\u00134!\u0019\tI\"a\b\u0002\u0010\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u00111\u0015\t\u0005\u0003K\u000by+\u0004\u0002\u0002(*!\u0011\u0011VAV\u0003\u0011a\u0017M\\4\u000b\u0005\u00055\u0016\u0001\u00026bm\u0006LA!!-\u0002(\n1qJ\u00196fGRDsAOA[\u0003w\u000bi\fE\u0002U\u0003oK1!!/F\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0004)\t\t\t\rF\u0002e\u0003\u0007Dq!!2\u0007\u0001\b\t9\"A\u0002pe\u0012\f\u0001\"\u001b;fe\u0006$xN]\u000b\u0003\u0003\u0017\u0004BAZAgS&\u0019\u0011qZ\"\u0003\u0011%#XM]1u_J\fAb[3zg&#XM]1u_J,\"!!6\u0011\t\u0019\fiMT\u0001\u000fm\u0006dW/Z:Ji\u0016\u0014\u0018\r^8s+\t\tY\u000e\u0005\u0003g\u0003\u001bT\u0016\u0001E6fsNLE/\u001a:bi>\u0014hI]8n)\u0011\t).!9\t\r\u0005\r(\u00021\u0001O\u0003\u0015\u0019H/\u0019:u\u00031IG/\u001a:bi>\u0014hI]8n)\u0011\tY-!;\t\r\u0005\r8\u00021\u0001O\u0003I1\u0018\r\\;fg&#XM]1u_J4%o\\7\u0015\t\u0005m\u0017q\u001e\u0005\u0007\u0003Gd\u0001\u0019\u0001(\u0002\u000fM$X\r\u001d9feV!\u0011Q_A\u0000)\u0011\t9Pa\f\u0013\r\u0005e\u0018Q B\n\r\u0019\tY\u0010\u0001\u0001\u0002x\naAH]3gS:,W.\u001a8u}A\u0019q*a@\u0005\u000f\t\u0005QB1\u0001\u0003\u0004\t\t1+E\u0002T\u0005\u000b\u0001DAa\u0002\u0003\u0010A)aM!\u0003\u0003\u000e%\u0019!1B\"\u0003\u000fM#X\r\u001d9feB\u0019qJa\u0004\u0005\u0017\tE\u0011q`A\u0001\u0002\u0003\u0015\tA\u0015\u0002\u0004?\u0012\n\u0004\u0003\u0002B\u000b\u0005SqAAa\u0006\u0003&9!!\u0011\u0004B\u0012\u001d\u0011\u0011YB!\t\u000e\u0005\tu!b\u0001B\u0010\u000f\u00061AH]8pizJ\u0011AR\u0005\u0003\t\u0016K1Aa\nD\u0003\u001d\u0019F/\u001a9qKJLAAa\u000b\u0003.\tqQI\u001a4jG&,g\u000e^*qY&$(b\u0001B\u0014\u0007\"9!\u0011G\u0007A\u0004\tM\u0012!B:iCB,\u0007C\u00024\u00036%\fi0C\u0002\u00038\r\u0013Ab\u0015;faB,'o\u00155ba\u0016\f!b[3z'R,\u0007\u000f]3s+\u0011\u0011iD!\u0012\u0015\t\t}\"1\u000b\n\u0007\u0005\u0003\u0012\u0019Ea\u0005\u0007\r\u0005m\b\u0001\u0001B !\ry%Q\t\u0003\b\u0005\u0003q!\u0019\u0001B$#\r\u0019&\u0011\n\u0019\u0005\u0005\u0017\u0012y\u0005E\u0003g\u0005\u0013\u0011i\u0005E\u0002P\u0005\u001f\"1B!\u0015\u0003F\u0005\u0005\t\u0011!B\u0001%\n\u0019q\f\n\u001a\t\u000f\tEb\u0002q\u0001\u0003VA1aM!\u000eO\u0005\u0007\nAB^1mk\u0016\u001cF/\u001a9qKJ,BAa\u0017\u0003dQ!!Q\fB9%\u0019\u0011yF!\u0019\u0003\u0014\u00191\u00111 \u0001\u0001\u0005;\u00022a\u0014B2\t\u001d\u0011\ta\u0004b\u0001\u0005K\n2a\u0015B4a\u0011\u0011IG!\u001c\u0011\u000b\u0019\u0014IAa\u001b\u0011\u0007=\u0013i\u0007B\u0006\u0003p\t\r\u0014\u0011!A\u0001\u0006\u0003\u0011&aA0%g!9!\u0011G\bA\u0004\tM\u0004C\u00024\u00036i\u0013\t'\u0001\u0004bI\u0012|e.\u001a\u000b\u0005\u0005s\u0012Y(D\u0001\u0001\u0011\u0019\u0011i\b\u0005a\u0001S\u0006!Q\r\\3n\u0003-\u0019XO\u0019;sC\u000e$xJ\\3\u0015\t\te$1\u0011\u0005\u0007\u0005{\n\u0002\u0019\u0001(\u0002\u000b\rdW-\u0019:\u0015\u0005\t%\u0005c\u0001+\u0003\f&\u0019!QR#\u0003\tUs\u0017\u000e^\u0001\u0004O\u0016$H\u0003\u0002BJ\u00053\u0003B\u0001\u0016BK5&\u0019!qS#\u0003\r=\u0003H/[8o\u0011\u0019\u0011Yj\u0005a\u0001\u001d\u0006\u00191.Z=\u0002\u0013I\fgnZ3J[BdG#\u00023\u0003\"\n\u0015\u0006bBA$)\u0001\u0007!1\u0015\t\u0005)\nUe\nC\u0004\u0003(R\u0001\rAa)\u0002\u000bUtG/\u001b7\u0002\u000f\u0019|'/Z1dQV!!Q\u0016B^)\u0011\u0011IIa,\t\u000f\tEV\u00031\u0001\u00034\u0006\ta\r\u0005\u0004U\u0005kK'\u0011X\u0005\u0004\u0005o+%!\u0003$v]\u000e$\u0018n\u001c82!\ry%1\u0018\u0003\u0007\u0005{+\"\u0019\u0001*\u0003\u0003U\u000bABZ8sK\u0006\u001c\u0007.\u00128uef,BAa1\u0003PR!!\u0011\u0012Bc\u0011\u001d\u0011\tL\u0006a\u0001\u0005\u000f\u0004r\u0001\u0016Be\u001dj\u0013i-C\u0002\u0003L\u0016\u0013\u0011BR;oGRLwN\u001c\u001a\u0011\u0007=\u0013y\r\u0002\u0004\u0003>Z\u0011\rAU\u0001\u0005g&TX-\u0006\u0002\u0003VB\u0019AKa6\n\u0007\teWIA\u0002J]R\f\u0011b\u001b8po:\u001c\u0016N_3\u0002\u000f%\u001cX)\u001c9usV\u0011!\u0011\u001d\t\u0004)\n\r\u0018b\u0001Bs\u000b\n9!i\\8mK\u0006t\u0017\u0001C2p]R\f\u0017N\\:\u0015\t\t\u0005(1\u001e\u0005\u0007\u00057S\u0002\u0019\u0001(\u0002\t!,\u0017\rZ\u000b\u0002S\u0006!A.Y:u\u0003!i\u0017N\\!gi\u0016\u0014H\u0003\u0002B|\u0005s\u0004B\u0001\u0016BKS\"1!1T\u000fA\u00029\u000b\u0011\"\\1y\u0005\u00164wN]3\u0015\t\t](q \u0005\u0007\u00057s\u0002\u0019\u0001(\u0002\u0013\rd\u0017m]:OC6,WCAB\u0003!\u0011\u00199aa\u0004\u000f\t\r%11\u0002\t\u0004\u00057)\u0015bAB\u0007\u000b\u00061\u0001K]3eK\u001aLAa!\u0005\u0004\u0014\t11\u000b\u001e:j]\u001eT1a!\u0004F\u0005E!&/Z3NCB\u0004&o\u001c6fGRLwN\\\n\u0003A\u0011$baa\u0007\u0004\u001e\r}\u0001c\u0001B=A!9\u0011qI\u0012A\u0002\t\r\u0006b\u0002BTG\u0001\u0007!1U\u0001\u000fa&\u001c7\u000eT8xKJ\u0014u.\u001e8e)\u0011\u0011\u0019k!\n\t\u000f\r\u001dB\u00051\u0001\u0003$\u00069a.Z<Ge>l\u0017A\u00049jG.,\u0006\u000f]3s\u0005>,h\u000e\u001a\u000b\u0005\u0005G\u001bi\u0003C\u0004\u00040\u0015\u0002\rAa)\u0002\u00119,w/\u00168uS2\f!#[:J]NLG-\u001a,jK^\u0014u.\u001e8egR!!\u0011]B\u001b\u0011\u0019\u0011YJ\na\u0001\u001dR)Am!\u000f\u0004<!9\u0011qI\u0014A\u0002\t\r\u0006b\u0002BTO\u0001\u0007!1\u0015\u000b\u0005\u0005'\u001by\u0004\u0003\u0004\u0003\u001c\"\u0002\rA\u0014\u000b\u0005\u0003+\u001c\u0019\u0005\u0003\u0004\u0002d2\u0002\rA\u0014\u000b\u0005\u0003\u0017\u001c9\u0005\u0003\u0004\u0002d6\u0002\rA\u0014\u000b\u0005\u00037\u001cY\u0005\u0003\u0004\u0002d:\u0002\rA\u0014\u000b\u0005\u0005C\u001cy\u0005\u0003\u0004\u0003\u001cJ\u0002\rAT\u0001\u000bQ\u0016\fGm\u00149uS>tWC\u0001B|\u0003)a\u0017m\u001d;PaRLwN\\\u000b\u0005\u00073\u001a\t\u0007\u0006\u0003\u0003\n\u000em\u0003b\u0002BYo\u0001\u00071Q\f\t\u0007)\nU\u0016na\u0018\u0011\u0007=\u001b\t\u0007\u0002\u0004\u0003>^\u0012\rAU\u0001\u0006G2|g.\u001a\u000b\u0002I&\u0012\u0001\u0001I\u0001\b)J,W-T1qQ\u001dI\u0014QWA^\u0003{\u0003")
public class TreeMap<K, V>
extends AbstractMap<K, V>
implements SortedMap<K, V>,
StrictOptimizedSortedMapOps<K, V, TreeMap, TreeMap<K, V>>,
DefaultSerializable {
    public final RedBlackTree.Tree<K, V> scala$collection$mutable$TreeMap$$tree;
    private final Ordering<K> ordering;

    public static <K, V> Builder<Tuple2<K, V>, TreeMap<K, V>> newBuilder(Ordering<K> evidence$3) {
        return TreeMap$.MODULE$.newBuilder(evidence$3);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public Map map(Function1 f, Ordering ordering) {
        return StrictOptimizedSortedMapOps.map$(this, f, ordering);
    }

    @Override
    public Map flatMap(Function1 f, Ordering ordering) {
        return StrictOptimizedSortedMapOps.flatMap$(this, f, ordering);
    }

    @Override
    public Map concat(IterableOnce xs) {
        return StrictOptimizedSortedMapOps.concat$(this, xs);
    }

    @Override
    public Map collect(PartialFunction pf, Ordering ordering) {
        return StrictOptimizedSortedMapOps.collect$(this, pf, ordering);
    }

    @Override
    public Map $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return StrictOptimizedSortedMapOps.$plus$(this, elem1, elem2, elems);
    }

    @Override
    public IterableOps map(Function1 f) {
        return StrictOptimizedMapOps.map$(this, f);
    }

    @Override
    public IterableOps flatMap(Function1 f) {
        return StrictOptimizedMapOps.flatMap$(this, f);
    }

    @Override
    public IterableOps collect(PartialFunction pf) {
        return StrictOptimizedMapOps.collect$(this, pf);
    }

    @Override
    public Tuple2<TreeMap<K, V>, TreeMap<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<TreeMap<K, V>, TreeMap<K, V>> span(Function1<Tuple2<K, V>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>> unzip(Function1<Tuple2<K, V>, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>, scala.collection.mutable.Iterable<A3>> unzip3(Function1<Tuple2<K, V>, Tuple3<A1, A2, A3>> asTriple) {
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
    public <A1, A2> Tuple2<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>> partitionMap(Function1<Tuple2<K, V>, Either<A1, A2>> f) {
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
    public scala.collection.mutable.Map<K, V> unsorted() {
        return SortedMap.unsorted$(this);
    }

    @Override
    public SortedMap<K, V> withDefault(Function1<K, V> d) {
        return SortedMap.withDefault$(this, d);
    }

    @Override
    public SortedMap<K, V> withDefaultValue(V d) {
        return SortedMap.withDefaultValue$(this, d);
    }

    @Override
    public scala.collection.mutable.Map updated(Object key, Object value) {
        return SortedMapOps.updated$(this, key, value);
    }

    @Override
    public /* synthetic */ boolean scala$collection$SortedMap$$super$equals(Object o) {
        return Map.equals$(this, o);
    }

    @Override
    public String stringPrefix() {
        return scala.collection.SortedMap.stringPrefix$(this);
    }

    @Override
    public boolean equals(Object that) {
        return scala.collection.SortedMap.equals$(this, that);
    }

    @Override
    public scala.collection.SortedMapOps empty() {
        return SortedMapFactoryDefaults.empty$(this);
    }

    @Override
    public scala.collection.SortedMapOps fromSpecific(IterableOnce coll) {
        return SortedMapFactoryDefaults.fromSpecific$(this, coll);
    }

    @Override
    public Builder<Tuple2<K, V>, TreeMap<K, V>> newSpecificBuilder() {
        return SortedMapFactoryDefaults.newSpecificBuilder$(this);
    }

    @Override
    public SortedMapOps.WithFilter<K, V, ?, ?, ?> withFilter(Function1<Tuple2<K, V>, Object> p) {
        return SortedMapFactoryDefaults.withFilter$(this, p);
    }

    @Override
    public final Map sortedMapFromIterable(Iterable it, Ordering ordering) {
        return scala.collection.SortedMapOps.sortedMapFromIterable$(this, it, ordering);
    }

    @Override
    public K firstKey() {
        return (K)scala.collection.SortedMapOps.firstKey$(this);
    }

    @Override
    public K lastKey() {
        return (K)scala.collection.SortedMapOps.lastKey$(this);
    }

    @Override
    public scala.collection.SortedMapOps rangeTo(Object to) {
        return scala.collection.SortedMapOps.rangeTo$(this, to);
    }

    @Override
    public SortedSet<K> keySet() {
        return scala.collection.SortedMapOps.keySet$(this);
    }

    @Override
    public final Map $plus$plus(IterableOnce xs) {
        return scala.collection.SortedMapOps.$plus$plus$(this, xs);
    }

    @Override
    public Map $plus(Tuple2 kv) {
        return scala.collection.SortedMapOps.$plus$(this, kv);
    }

    @Override
    public int compare(K k0, K k1) {
        return SortedOps.compare$(this, k0, k1);
    }

    @Override
    public Object range(Object from, Object until) {
        return SortedOps.range$(this, from, until);
    }

    @Override
    public final Object from(Object from) {
        return SortedOps.from$(this, from);
    }

    @Override
    public Object rangeFrom(Object from) {
        return SortedOps.rangeFrom$(this, from);
    }

    @Override
    public final Object until(Object until) {
        return SortedOps.until$(this, until);
    }

    @Override
    public Object rangeUntil(Object until) {
        return SortedOps.rangeUntil$(this, until);
    }

    @Override
    public final Object to(Object to) {
        return SortedOps.to$(this, to);
    }

    @Override
    public Ordering<K> ordering() {
        return this.ordering;
    }

    public TreeMap$ sortedMapFactory() {
        return TreeMap$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<Tuple2<K, V>> iterator() {
        void iterator_evidence$3;
        void iterator_end;
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        Ordering<K> ordering = this.ordering();
        None$ none$ = None$.MODULE$;
        None$ iterator_start = None$.MODULE$;
        RedBlackTree.Tree<K, V> iterator_tree = this.scala$collection$mutable$TreeMap$$tree;
        return new RedBlackTree.EntriesIterator<Nothing$, V>(iterator_tree, iterator_start, (Option<Nothing$>)iterator_end, (Ordering<Nothing$>)iterator_evidence$3);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<K> keysIterator() {
        void keysIterator_evidence$4;
        void keysIterator_end;
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        Ordering<K> ordering = this.ordering();
        None$ none$ = None$.MODULE$;
        None$ keysIterator_start = None$.MODULE$;
        RedBlackTree.Tree<K, V> keysIterator_tree = this.scala$collection$mutable$TreeMap$$tree;
        return new RedBlackTree.KeysIterator<Nothing$, V>(keysIterator_tree, keysIterator_start, (Option<Nothing$>)keysIterator_end, (Ordering<Nothing$>)keysIterator_evidence$4);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<V> valuesIterator() {
        void valuesIterator_evidence$5;
        void valuesIterator_end;
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        Ordering<K> ordering = this.ordering();
        None$ none$ = None$.MODULE$;
        None$ valuesIterator_start = None$.MODULE$;
        RedBlackTree.Tree<K, V> valuesIterator_tree = this.scala$collection$mutable$TreeMap$$tree;
        return new RedBlackTree.ValuesIterator<Nothing$, V>(valuesIterator_tree, valuesIterator_start, (Option<Nothing$>)valuesIterator_end, (Ordering<Nothing$>)valuesIterator_evidence$5);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<K> keysIteratorFrom(K start2) {
        void keysIterator_evidence$4;
        void keysIterator_end;
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        Ordering<K> ordering = this.ordering();
        None$ none$ = None$.MODULE$;
        Some<K> keysIterator_start = new Some<K>(start2);
        RedBlackTree.Tree<K, V> keysIterator_tree = this.scala$collection$mutable$TreeMap$$tree;
        return new RedBlackTree.KeysIterator<K, V>(keysIterator_tree, keysIterator_start, keysIterator_end, keysIterator_evidence$4);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<Tuple2<K, V>> iteratorFrom(K start2) {
        void iterator_evidence$3;
        void iterator_end;
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        Ordering<K> ordering = this.ordering();
        None$ none$ = None$.MODULE$;
        Some<K> iterator_start = new Some<K>(start2);
        RedBlackTree.Tree<K, V> iterator_tree = this.scala$collection$mutable$TreeMap$$tree;
        return new RedBlackTree.EntriesIterator<K, V>(iterator_tree, iterator_start, iterator_end, iterator_evidence$3);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public Iterator<V> valuesIteratorFrom(K start2) {
        void valuesIterator_evidence$5;
        void valuesIterator_end;
        if (this.isEmpty()) {
            return Iterator$.scala$collection$Iterator$$_empty;
        }
        Ordering<K> ordering = this.ordering();
        None$ none$ = None$.MODULE$;
        Some<K> valuesIterator_start = new Some<K>(start2);
        RedBlackTree.Tree<K, V> valuesIterator_tree = this.scala$collection$mutable$TreeMap$$tree;
        return new RedBlackTree.ValuesIterator<K, V>(valuesIterator_tree, valuesIterator_start, valuesIterator_end, valuesIterator_evidence$5);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<Tuple2<K, V>, S> shape) {
        void from_extract;
        void from_right;
        void from_left;
        Function1<RedBlackTree.Node, Tuple2> & Serializable intersect = (Function1<RedBlackTree.Node, Tuple2> & Serializable)x -> new Tuple2(x.key(), x.value());
        Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect2 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$2 -> x$2.right();
        Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect3 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$1 -> x$1.left();
        RedBlackTree.Node<K, V> from_root = this.scala$collection$mutable$TreeMap$$tree.root();
        int from_maxLength = this.size();
        AnyBinaryTreeStepper from_ans = new AnyBinaryTreeStepper(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left, (Function1<Object, Object>)from_right, from_extract);
        from_ans.initialize(from_root, from_maxLength);
        Object var3_5 = null;
        intersect3 = null;
        intersect2 = null;
        intersect = null;
        Object var7_7 = null;
        return shape.parUnbox(from_ans);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S keyStepper(StepperShape<K, S> shape) {
        void from_root;
        void from_extract;
        void from_right;
        void from_left;
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            void from_root2;
            void from_extract2;
            void from_right2;
            void from_left2;
            Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$5 -> BoxesRunTime.boxToInteger(TreeMap.$anonfun$keyStepper$3(x$5));
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect2 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$4 -> x$4.right();
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect3 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$3 -> x$3.left();
            RedBlackTree.Node<K, V> node = this.scala$collection$mutable$TreeMap$$tree.root();
            int from_maxLength = this.size();
            IntBinaryTreeStepper<Object> from_ans = new IntBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left2, (Function1<Object, Object>)from_right2, (Function1<Object, Object>)from_extract2);
            from_ans.initialize(from_root2, from_maxLength);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            void from_root3;
            void from_extract3;
            void from_right3;
            void from_left3;
            Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$8 -> BoxesRunTime.boxToLong(TreeMap.$anonfun$keyStepper$6(x$8));
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect4 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$7 -> x$7.right();
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect5 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$6 -> x$6.left();
            RedBlackTree.Node<K, V> node = this.scala$collection$mutable$TreeMap$$tree.root();
            int from_maxLength = this.size();
            LongBinaryTreeStepper<Object> from_ans = new LongBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left3, (Function1<Object, Object>)from_right3, (Function1<Object, Object>)from_extract3);
            from_ans.initialize(from_root3, from_maxLength);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            void from_root4;
            void from_extract4;
            void from_right4;
            void from_left4;
            Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$11 -> BoxesRunTime.boxToDouble(TreeMap.$anonfun$keyStepper$9(x$11));
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect6 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$10 -> x$10.right();
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect7 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$9 -> x$9.left();
            RedBlackTree.Node<K, V> node = this.scala$collection$mutable$TreeMap$$tree.root();
            int from_maxLength = this.size();
            DoubleBinaryTreeStepper<Object> from_ans = new DoubleBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left4, (Function1<Object, Object>)from_right4, (Function1<Object, Object>)from_extract4);
            from_ans.initialize(from_root4, from_maxLength);
            return (S)from_ans;
        }
        Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$14 -> x$14.key();
        Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect8 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$13 -> x$13.right();
        Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect9 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$12 -> x$12.left();
        RedBlackTree.Node<K, V> node = this.scala$collection$mutable$TreeMap$$tree.root();
        int from_maxLength = this.size();
        AnyBinaryTreeStepper from_ans = new AnyBinaryTreeStepper(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left, (Function1<Object, Object>)from_right, from_extract);
        from_ans.initialize(from_root, from_maxLength);
        node = null;
        intersect9 = null;
        intersect8 = null;
        intersect = null;
        Object var26_26 = null;
        return shape.parUnbox(from_ans);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <S extends Stepper<?>> S valueStepper(StepperShape<V, S> shape) {
        void from_root;
        void from_extract;
        void from_right;
        void from_left;
        int n = shape.shape();
        if (StepperShape$.MODULE$.IntShape() == n) {
            void from_root2;
            void from_extract2;
            void from_right2;
            void from_left2;
            Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$17 -> BoxesRunTime.boxToInteger(TreeMap.$anonfun$valueStepper$3(x$17));
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect2 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$16 -> x$16.right();
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect3 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$15 -> x$15.left();
            RedBlackTree.Node<K, V> node = this.scala$collection$mutable$TreeMap$$tree.root();
            int from_maxLength = this.size();
            IntBinaryTreeStepper<Object> from_ans = new IntBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left2, (Function1<Object, Object>)from_right2, (Function1<Object, Object>)from_extract2);
            from_ans.initialize(from_root2, from_maxLength);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.LongShape() == n) {
            void from_root3;
            void from_extract3;
            void from_right3;
            void from_left3;
            Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$20 -> BoxesRunTime.boxToLong(TreeMap.$anonfun$valueStepper$6(x$20));
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect4 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$19 -> x$19.right();
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect5 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$18 -> x$18.left();
            RedBlackTree.Node<K, V> node = this.scala$collection$mutable$TreeMap$$tree.root();
            int from_maxLength = this.size();
            LongBinaryTreeStepper<Object> from_ans = new LongBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left3, (Function1<Object, Object>)from_right3, (Function1<Object, Object>)from_extract3);
            from_ans.initialize(from_root3, from_maxLength);
            return (S)from_ans;
        }
        if (StepperShape$.MODULE$.DoubleShape() == n) {
            void from_root4;
            void from_extract4;
            void from_right4;
            void from_left4;
            Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$23 -> BoxesRunTime.boxToDouble(TreeMap.$anonfun$valueStepper$9(x$23));
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect6 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$22 -> x$22.right();
            Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect7 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$21 -> x$21.left();
            RedBlackTree.Node<K, V> node = this.scala$collection$mutable$TreeMap$$tree.root();
            int from_maxLength = this.size();
            DoubleBinaryTreeStepper<Object> from_ans = new DoubleBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left4, (Function1<Object, Object>)from_right4, (Function1<Object, Object>)from_extract4);
            from_ans.initialize(from_root4, from_maxLength);
            return (S)from_ans;
        }
        Function1<RedBlackTree.Node, Object> & Serializable intersect = (Function1<RedBlackTree.Node, Object> & Serializable)x$26 -> x$26.value();
        Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect8 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$25 -> x$25.right();
        Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable intersect9 = (Function1<RedBlackTree.Node, RedBlackTree.Node> & Serializable)x$24 -> x$24.left();
        RedBlackTree.Node<K, V> node = this.scala$collection$mutable$TreeMap$$tree.root();
        int from_maxLength = this.size();
        AnyBinaryTreeStepper from_ans = new AnyBinaryTreeStepper(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, (Function1<Object, Object>)from_left, (Function1<Object, Object>)from_right, from_extract);
        from_ans.initialize(from_root, from_maxLength);
        node = null;
        intersect9 = null;
        intersect8 = null;
        intersect = null;
        Object var26_26 = null;
        return shape.parUnbox(from_ans);
    }

    public TreeMap<K, V> addOne(Tuple2<K, V> elem) {
        RedBlackTree$.MODULE$.insert(this.scala$collection$mutable$TreeMap$$tree, elem._1(), elem._2(), this.ordering());
        return this;
    }

    public TreeMap<K, V> subtractOne(K elem) {
        RedBlackTree$.MODULE$.delete(this.scala$collection$mutable$TreeMap$$tree, elem, this.ordering());
        return this;
    }

    @Override
    public void clear() {
        RedBlackTree$.MODULE$.clear(this.scala$collection$mutable$TreeMap$$tree);
    }

    @Override
    public Option<V> get(K key) {
        return RedBlackTree$.MODULE$.get(this.scala$collection$mutable$TreeMap$$tree, key, this.ordering());
    }

    @Override
    public TreeMap<K, V> rangeImpl(Option<K> from, Option<K> until) {
        return new TreeMapProjection(this, from, until);
    }

    @Override
    public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
        RedBlackTree$.MODULE$.foreach(this.scala$collection$mutable$TreeMap$$tree, f);
    }

    @Override
    public <U> void foreachEntry(Function2<K, V, U> f) {
        RedBlackTree$.MODULE$.foreachEntry(this.scala$collection$mutable$TreeMap$$tree, f);
    }

    @Override
    public int size() {
        return this.scala$collection$mutable$TreeMap$$tree.size();
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    @Override
    public boolean isEmpty() {
        return RedBlackTree$.MODULE$.isEmpty(this.scala$collection$mutable$TreeMap$$tree);
    }

    @Override
    public boolean contains(K key) {
        return RedBlackTree$.MODULE$.contains(this.scala$collection$mutable$TreeMap$$tree, key, this.ordering());
    }

    @Override
    public Tuple2<K, V> head() {
        return RedBlackTree$.MODULE$.min(this.scala$collection$mutable$TreeMap$$tree).get();
    }

    @Override
    public Tuple2<K, V> last() {
        return RedBlackTree$.MODULE$.max(this.scala$collection$mutable$TreeMap$$tree).get();
    }

    @Override
    public Option<Tuple2<K, V>> minAfter(K key) {
        return RedBlackTree$.MODULE$.minAfter(this.scala$collection$mutable$TreeMap$$tree, key, this.ordering());
    }

    @Override
    public Option<Tuple2<K, V>> maxBefore(K key) {
        return RedBlackTree$.MODULE$.maxBefore(this.scala$collection$mutable$TreeMap$$tree, key, this.ordering());
    }

    @Override
    public String className() {
        return "TreeMap";
    }

    public static final /* synthetic */ int $anonfun$keyStepper$3(RedBlackTree.Node x$5) {
        return BoxesRunTime.unboxToInt(x$5.key());
    }

    public static final /* synthetic */ long $anonfun$keyStepper$6(RedBlackTree.Node x$8) {
        return BoxesRunTime.unboxToLong(x$8.key());
    }

    public static final /* synthetic */ double $anonfun$keyStepper$9(RedBlackTree.Node x$11) {
        return BoxesRunTime.unboxToDouble(x$11.key());
    }

    public static final /* synthetic */ int $anonfun$valueStepper$3(RedBlackTree.Node x$17) {
        return BoxesRunTime.unboxToInt(x$17.value());
    }

    public static final /* synthetic */ long $anonfun$valueStepper$6(RedBlackTree.Node x$20) {
        return BoxesRunTime.unboxToLong(x$20.value());
    }

    public static final /* synthetic */ double $anonfun$valueStepper$9(RedBlackTree.Node x$23) {
        return BoxesRunTime.unboxToDouble(x$23.value());
    }

    public TreeMap(RedBlackTree.Tree<K, V> tree, Ordering<K> ordering) {
        this.scala$collection$mutable$TreeMap$$tree = tree;
        this.ordering = ordering;
    }

    public TreeMap(Ordering<K> ord) {
        this(RedBlackTree$Tree$.MODULE$.empty(), ord);
    }

    private final class TreeMapProjection
    extends TreeMap<K, V> {
        private final Option<K> from;
        private final Option<K> until;
        private final /* synthetic */ TreeMap $outer;

        private Option<K> pickLowerBound(Option<K> newFrom) {
            Option option = this.from;
            if (option instanceof Some) {
                Object fr = ((Some)option).value();
                if (newFrom instanceof Some) {
                    Object newFr = ((Some)newFrom).value();
                    return new Some(this.ordering().max(fr, newFr));
                }
            }
            if (None$.MODULE$.equals(option)) {
                return newFrom;
            }
            return this.from;
        }

        private Option<K> pickUpperBound(Option<K> newUntil) {
            Option option = this.until;
            if (option instanceof Some) {
                Object unt = ((Some)option).value();
                if (newUntil instanceof Some) {
                    Object newUnt = ((Some)newUntil).value();
                    return new Some(this.ordering().min(unt, newUnt));
                }
            }
            if (None$.MODULE$.equals(option)) {
                return newUntil;
            }
            return this.until;
        }

        private boolean isInsideViewBounds(K key) {
            boolean beforeUntil;
            boolean afterFrom = this.from.isEmpty() || this.ordering().compare(this.from.get(), key) <= 0;
            boolean bl = beforeUntil = this.until.isEmpty() || this.ordering().compare(key, this.until.get()) < 0;
            return afterFrom && beforeUntil;
        }

        @Override
        public TreeMap<K, V> rangeImpl(Option<K> from, Option<K> until) {
            return new TreeMapProjection(this.$outer, this.pickLowerBound(from), this.pickUpperBound(until));
        }

        @Override
        public Option<V> get(K key) {
            if (this.isInsideViewBounds(key)) {
                return RedBlackTree$.MODULE$.get(this.$outer.scala$collection$mutable$TreeMap$$tree, key, this.ordering());
            }
            return None$.MODULE$;
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            void iterator_evidence$3;
            void iterator_end;
            if (this.$outer.scala$collection$mutable$TreeMap$$tree.size() == 0) {
                return Iterator$.scala$collection$Iterator$$_empty;
            }
            Ordering ordering = this.ordering();
            Option option = this.until;
            Option iterator_start = this.from;
            RedBlackTree.Tree iterator_tree = this.$outer.scala$collection$mutable$TreeMap$$tree;
            return new RedBlackTree.EntriesIterator(iterator_tree, iterator_start, iterator_end, iterator_evidence$3);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Iterator<K> keysIterator() {
            void keysIterator_evidence$4;
            void keysIterator_end;
            if (this.$outer.scala$collection$mutable$TreeMap$$tree.size() == 0) {
                return Iterator$.scala$collection$Iterator$$_empty;
            }
            Ordering ordering = this.ordering();
            Option option = this.until;
            Option keysIterator_start = this.from;
            RedBlackTree.Tree keysIterator_tree = this.$outer.scala$collection$mutable$TreeMap$$tree;
            return new RedBlackTree.KeysIterator(keysIterator_tree, keysIterator_start, keysIterator_end, keysIterator_evidence$4);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Iterator<V> valuesIterator() {
            void valuesIterator_evidence$5;
            void valuesIterator_end;
            if (this.$outer.scala$collection$mutable$TreeMap$$tree.size() == 0) {
                return Iterator$.scala$collection$Iterator$$_empty;
            }
            Ordering ordering = this.ordering();
            Option option = this.until;
            Option valuesIterator_start = this.from;
            RedBlackTree.Tree valuesIterator_tree = this.$outer.scala$collection$mutable$TreeMap$$tree;
            return new RedBlackTree.ValuesIterator(valuesIterator_tree, valuesIterator_start, valuesIterator_end, valuesIterator_evidence$5);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Iterator<K> keysIteratorFrom(K start2) {
            void keysIterator_evidence$4;
            void keysIterator_end;
            if (this.$outer.scala$collection$mutable$TreeMap$$tree.size() == 0) {
                return Iterator$.scala$collection$Iterator$$_empty;
            }
            Ordering ordering = this.ordering();
            Option option = this.until;
            Option keysIterator_start = this.pickLowerBound(new Some(start2));
            RedBlackTree.Tree keysIterator_tree = this.$outer.scala$collection$mutable$TreeMap$$tree;
            return new RedBlackTree.KeysIterator(keysIterator_tree, keysIterator_start, keysIterator_end, keysIterator_evidence$4);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Iterator<Tuple2<K, V>> iteratorFrom(K start2) {
            void iterator_evidence$3;
            void iterator_end;
            if (this.$outer.scala$collection$mutable$TreeMap$$tree.size() == 0) {
                return Iterator$.scala$collection$Iterator$$_empty;
            }
            Ordering ordering = this.ordering();
            Option option = this.until;
            Option iterator_start = this.pickLowerBound(new Some(start2));
            RedBlackTree.Tree iterator_tree = this.$outer.scala$collection$mutable$TreeMap$$tree;
            return new RedBlackTree.EntriesIterator(iterator_tree, iterator_start, iterator_end, iterator_evidence$3);
        }

        /*
         * WARNING - void declaration
         */
        @Override
        public Iterator<V> valuesIteratorFrom(K start2) {
            void valuesIterator_evidence$5;
            void valuesIterator_end;
            if (this.$outer.scala$collection$mutable$TreeMap$$tree.size() == 0) {
                return Iterator$.scala$collection$Iterator$$_empty;
            }
            Ordering ordering = this.ordering();
            Option option = this.until;
            Option valuesIterator_start = this.pickLowerBound(new Some(start2));
            RedBlackTree.Tree valuesIterator_tree = this.$outer.scala$collection$mutable$TreeMap$$tree;
            return new RedBlackTree.ValuesIterator(valuesIterator_tree, valuesIterator_start, valuesIterator_end, valuesIterator_evidence$5);
        }

        @Override
        public int size() {
            if (this.$outer.scala$collection$mutable$TreeMap$$tree.size() == 0) {
                return 0;
            }
            Iterator iterator2 = this.iterator();
            if (iterator2 == null) {
                throw null;
            }
            return iterator2.size();
        }

        @Override
        public int knownSize() {
            if (this.$outer.scala$collection$mutable$TreeMap$$tree.size() == 0) {
                return 0;
            }
            return -1;
        }

        @Override
        public boolean isEmpty() {
            return this.$outer.scala$collection$mutable$TreeMap$$tree.size() == 0 || !this.iterator().hasNext();
        }

        @Override
        public boolean contains(K key) {
            return this.isInsideViewBounds(key) && RedBlackTree$.MODULE$.contains(this.$outer.scala$collection$mutable$TreeMap$$tree, key, this.ordering());
        }

        @Override
        public Tuple2<K, V> head() {
            return this.headOption().get();
        }

        @Override
        public Option<Tuple2<K, V>> headOption() {
            Option entry = this.from.isDefined() ? RedBlackTree$.MODULE$.minAfter(this.$outer.scala$collection$mutable$TreeMap$$tree, this.from.get(), this.ordering()) : RedBlackTree$.MODULE$.min(this.$outer.scala$collection$mutable$TreeMap$$tree);
            Option option = this.until;
            if (entry instanceof Some) {
                Tuple2 e = (Tuple2)((Some)entry).value();
                if (option instanceof Some) {
                    Object unt = ((Some)option).value();
                    if (this.ordering().compare(e._1(), unt) >= 0) {
                        return None$.MODULE$;
                    }
                }
            }
            return entry;
        }

        @Override
        public Tuple2<K, V> last() {
            return this.lastOption().get();
        }

        @Override
        public Option<Tuple2<K, V>> lastOption() {
            Option entry = this.until.isDefined() ? RedBlackTree$.MODULE$.maxBefore(this.$outer.scala$collection$mutable$TreeMap$$tree, this.until.get(), this.ordering()) : RedBlackTree$.MODULE$.max(this.$outer.scala$collection$mutable$TreeMap$$tree);
            Option option = this.from;
            if (entry instanceof Some) {
                Tuple2 e = (Tuple2)((Some)entry).value();
                if (option instanceof Some) {
                    Object fr = ((Some)option).value();
                    if (this.ordering().compare(e._1(), fr) < 0) {
                        return None$.MODULE$;
                    }
                }
            }
            return entry;
        }

        @Override
        public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
            this.iterator().foreach(f);
        }

        @Override
        public TreeMap<K, V> clone() {
            return ((TreeMap)MapOps.clone$(this)).rangeImpl(this.from, this.until);
        }

        public TreeMapProjection(TreeMap $outer, Option<K> from, Option<K> until) {
            this.from = from;
            this.until = until;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            super($outer.scala$collection$mutable$TreeMap$$tree, $outer.ordering());
        }
    }
}

