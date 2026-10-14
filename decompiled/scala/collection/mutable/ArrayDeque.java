/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.IndexedSeqOps;
import scala.collection.IndexedSeqView;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.Searching;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.StrictOptimizedSeqOps;
import scala.collection.View;
import scala.collection.generic.DefaultSerializable;
import scala.collection.immutable.Seq$;
import scala.collection.mutable.AbstractBuffer;
import scala.collection.mutable.ArrayDeque$;
import scala.collection.mutable.ArrayDequeOps;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.IndexedBuffer;
import scala.collection.mutable.IndexedSeq;
import scala.collection.mutable.IndexedSeq$;
import scala.math.Integral;
import scala.math.Ordering;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.util.Either;

@ScalaSignature(bytes="\u0006\u0005\ruh\u0001B*U\u0001mC!\"!\u0005\u0001\u0005\u0003\u0007I\u0011CA\n\u0011)\t\t\u0003\u0001BA\u0002\u0013E\u00111\u0005\u0005\u000b\u0003_\u0001!\u0011!Q!\n\u0005U\u0001bCA\u0019\u0001\t\u0015\t\u0011)Q\u0005\u0003gA1\"!\u000f\u0001\u0005\u000b\u0005\t\u0015)\u0003\u00024!9\u00111\b\u0001\u0005\u0012\u0005u\u0002\u0002CA%\u0001\u0001&I!a\u0013\t\u000f\u0005m\u0002\u0001\"\u0001\u0002T!9\u0011\u0011\f\u0001\u0005B\u0005m\u0003bBA/\u0001\u0011\u0005\u0013q\f\u0005\b\u0003K\u0003A\u0011AAT\u0011\u001d\ti\u000b\u0001C\u0001\u0003_Cq!a.\u0001\t\u0003\tI\fC\u0004\u0002@\u0002!\t!!1\t\u000f\u0005\u0015\u0007\u0001\"\u0011\u0002H\"9\u00111\u001b\u0001\u0005B\u0005U\u0007bBAm\u0001\u0011\u0005\u00111\u001c\u0005\b\u0003C\u0004A\u0011AAr\u0011\u001d\tI\u000f\u0001C\u0001\u0003WDq!!;\u0001\t\u0003\t\u0019\u0010C\u0004\u0002x\u0002!\t%!?\t\u000f\u0005u\b\u0001\"\u0001\u0002\u0000\"I!\u0011\u0003\u0001\u0012\u0002\u0013\u0005!1\u0003\u0005\b\u0005S\u0001A\u0011\u0001B\u0016\u0011%\u0011y\u0003AI\u0001\n\u0003\u0011\u0019\u0002\u0003\u0005\u00032\u0001\u0001K\u0011\u0002B\u001a\u0011%\u0011y\u0004AI\u0001\n\u0013\u0011\u0019\u0002C\u0004\u0003B\u0001!\tAa\u0011\t\u0013\t\u001d\u0003!%A\u0005\u0002\tM\u0001b\u0002B%\u0001\u0011\u0005!1\n\u0005\n\u0005\u001f\u0002\u0011\u0013!C\u0001\u0005'A\u0001B!\u0015\u0001A\u0013%!1\u000b\u0005\n\u00053\u0002\u0011\u0013!C\u0005\u0005'AqAa\u0017\u0001\t\u0003\u0011i\u0006C\u0004\u0003l\u0001!\tA!\u0018\t\u000f\t5\u0004\u0001\"\u0001\u0003p!9!1\u0010\u0001\u0005\u0002\tu\u0004b\u0002BA\u0001\u0011\u0005!1\u0011\u0005\n\u0005\u001b\u0003\u0011\u0013!C\u0001\u0005\u001fCqAa\u0017\u0001\t\u0003\u0011\u0019\nC\u0004\u0003\u0018\u0002!\tA!'\t\u000f\t\u0005\u0006\u0001\"\u0001\u0002\\!9!1\u0015\u0001\u0005B\t\u0015\u0006b\u0002BT\u0001\u0011E#\u0011\u0016\u0005\b\u0005W\u0003A\u0011\tBW\u0011\u001d\u0011)\f\u0001C\u0001\u0005oCqA!/\u0001\t\u0003\u0011Y\fC\u0005\u0003B\u0002\t\n\u0011\"\u0001\u0003\u0010\"9!1\u0019\u0001\u0005\u0012\t\u0015\u0007b\u0002Bf\u0001\u0011\u0005#Q\u001a\u0005\b\u0005O\u0004A\u0011\tBu\u0011\u001d\u0019\u0019\u0001\u0001C\u0001\u0005oCqa!\u0002\u0001\t#\u00199\u0001\u0003\b\u0004\u000e\u0001!\t\u0011!B\u0001\u0002\u0003&Iaa\u0004\t\u001d\rU\u0001\u0001\"A\u0001\u0006\u0003\u0005\t\u0015\"\u0003\u0004\u0018!q1Q\u0004\u0001\u0005\u0002\u0003\u0015\t\u0011!Q\u0005\n\r}\u0001BDB\u0013\u0001\u0011\u0005\tQ!A\u0001B\u0013%1q\u0005\u0005\t\u0007[\u0001\u0001\u0015\"\u0003\u00040!A1Q\u0007\u0001!\n\u0013\u00199\u0004\u0003\u0005\u0004>\u0001\u0001K\u0011BB \u0011!\u0019)\u0005\u0001Q\u0005\n\r\u001d\u0003BDB(\u0001\u0011\u0005\tQ!A\u0001B\u0013%1\u0011\u000b\u0005\t\u0007+\u0002\u0001\u0015\"\u0015\u0004X!a1\u0011\u000e\u0001\u0003\u0006\u0003\u0007I\u0011\u0001\u0001\u0002\\!a11\u000e\u0001\u0003\u0006\u0003\u0007I\u0011\u0001\u0001\u0004n!a1\u0011\u000f\u0001\u0003\u0006\u0003\u0007I\u0011\u0001\u0001\u0002\\!a11\u000f\u0001\u0003\u0006\u0003\u0007I\u0011\u0001\u0001\u0004v!a1\u0011\u0010\u0001\u0003\u0002\u0003\u0005I\u0011\u0001\u0001\u0004|!a1\u0011\u0011\u0001\u0003\u0002\u0003\u0005I\u0011\u0001\u0001\u0004\u0004\u001e91\u0011\u0012+\t\u0002\r-eAB*U\u0011\u0003\u0019i\tC\u0004\u0002<\u001d#\ta!&\t\u000f\t-u\t\"\u0001\u0004\u0018\"91qU$\u0005\u0002\r%\u0006bBB]\u000f\u0012\u000511\u0018\u0005\n\u0007\u000b<%\u0019!C\u0003\u0007\u000fD\u0001b!4HA\u000351\u0011\u001a\u0005\u000b\u0007\u001f<%\u0019!C\u0003\u000f\u000eE\u0007\u0002CBl\u000f\u0002\u0006iaa5\t\u0011\rew\t\"\u0001U\u00077D\u0011ba8H#\u0003%\ta!9\t\u0013\r\u0015x)!A\u0005\n\r\u001d(AC!se\u0006LH)Z9vK*\u0011QKV\u0001\b[V$\u0018M\u00197f\u0015\t9\u0006,\u0001\u0006d_2dWm\u0019;j_:T\u0011!W\u0001\u0006g\u000e\fG.Y\u0002\u0001+\ta6m\u0005\u0006\u0001;6\u0004X/\u001f?\u0000\u0003\u000b\u00012AX0b\u001b\u0005!\u0016B\u00011U\u00059\t%m\u001d;sC\u000e$()\u001e4gKJ\u0004\"AY2\r\u0001\u0011)A\r\u0001b\u0001K\n\t\u0011)\u0005\u0002gUB\u0011q\r[\u0007\u00021&\u0011\u0011\u000e\u0017\u0002\b\u001d>$\b.\u001b8h!\t97.\u0003\u0002m1\n\u0019\u0011I\\=\u0011\u0007ys\u0017-\u0003\u0002p)\ni\u0011J\u001c3fq\u0016$')\u001e4gKJ\u0004RAX9bgRL!A\u001d+\u0003\u001b%sG-\u001a=fIN+\u0017o\u00149t!\tq\u0006\u0001E\u0002_\u0001\u0005\u0004RA^<bgRl\u0011AV\u0005\u0003qZ\u0013Qc\u0015;sS\u000e$x\n\u001d;j[&TX\rZ*fc>\u00038\u000f\u0005\u0003wu\u0006\u001c\u0018BA>W\u0005]IE/\u001a:bE2,g)Y2u_JLH)\u001a4bk2$8\u000fE\u0003_{\u0006\u001cH/\u0003\u0002\u007f)\ni\u0011I\u001d:bs\u0012+\u0017/^3PaN\u0004BAXA\u0001i&\u0019\u00111\u0001+\u0003\u0013\rcwN\\3bE2,\u0007\u0003BA\u0004\u0003\u001bi!!!\u0003\u000b\u0007\u0005-a+A\u0004hK:,'/[2\n\t\u0005=\u0011\u0011\u0002\u0002\u0014\t\u00164\u0017-\u001e7u'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0006CJ\u0014\u0018-_\u000b\u0003\u0003+\u0001RaZA\f\u00037I1!!\u0007Y\u0005\u0015\t%O]1z!\r9\u0017QD\u0005\u0004\u0003?A&AB!osJ+g-A\u0005beJ\f\u0017p\u0018\u0013fcR!\u0011QEA\u0016!\r9\u0017qE\u0005\u0004\u0003SA&\u0001B+oSRD\u0011\"!\f\u0003\u0003\u0003\u0005\r!!\u0006\u0002\u0007a$\u0013'\u0001\u0004beJ\f\u0017\u0010I\u0001,g\u000e\fG.\u0019\u0013d_2dWm\u0019;j_:$S.\u001e;bE2,G%\u0011:sCf$U-];fI\u0011\u001aH/\u0019:uAA\u0019q-!\u000e\n\u0007\u0005]\u0002LA\u0002J]R\f\u0011f]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%[V$\u0018M\u00197fI\u0005\u0013(/Y=EKF,X\r\n\u0013f]\u0012\u0004\u0013A\u0002\u001fj]&$h\bF\u0004u\u0003\u007f\t\t%!\u0012\t\u000f\u0005Ea\u00011\u0001\u0002\u0016!9\u00111\t\u0004A\u0002\u0005M\u0012!B:uCJ$\bbBA$\r\u0001\u0007\u00111G\u0001\u0004K:$\u0017!\u0002:fg\u0016$H\u0003CA\u0013\u0003\u001b\ny%!\u0015\t\u000f\u0005Eq\u00011\u0001\u0002\u0016!9\u00111I\u0004A\u0002\u0005M\u0002bBA$\u000f\u0001\u0007\u00111\u0007\u000b\u0004i\u0006U\u0003\"CA,\u0011A\u0005\t\u0019AA\u001a\u0003-Ig.\u001b;jC2\u001c\u0016N_3\u0002\u0013-twn\u001e8TSj,WCAA\u001a\u0003\u001d\u0019H/\u001a9qKJ,B!!\u0019\u0002lQ!\u00111MAN%\u0019\t)'!\u001b\u0002\u0000\u00191\u0011q\r\u0001\u0001\u0003G\u0012A\u0002\u0010:fM&tW-\\3oiz\u00022AYA6\t\u001d\tiG\u0003b\u0001\u0003_\u0012\u0011aU\t\u0004M\u0006E\u0004\u0007BA:\u0003w\u0002RA^A;\u0003sJ1!a\u001eW\u0005\u001d\u0019F/\u001a9qKJ\u00042AYA>\t-\ti(a\u001b\u0002\u0002\u0003\u0005)\u0011A3\u0003\u0007}#\u0013\u0007\u0005\u0003\u0002\u0002\u0006Ue\u0002BAB\u0003#sA!!\"\u0002\u0010:!\u0011qQAG\u001b\t\tIIC\u0002\u0002\fj\u000ba\u0001\u0010:p_Rt\u0014\"A-\n\u0005]C\u0016bAAJ-\u000691\u000b^3qa\u0016\u0014\u0018\u0002BAL\u00033\u0013a\"\u00124gS\u000eLWM\u001c;Ta2LGOC\u0002\u0002\u0014ZCq!!(\u000b\u0001\b\ty*A\u0003tQ\u0006\u0004X\r\u0005\u0004w\u0003C\u000b\u0017\u0011N\u0005\u0004\u0003G3&\u0001D*uKB\u0004XM]*iCB,\u0017!B1qa2LHcA1\u0002*\"9\u00111V\u0006A\u0002\u0005M\u0012aA5eq\u00061Q\u000f\u001d3bi\u0016$b!!\n\u00022\u0006M\u0006bBAV\u0019\u0001\u0007\u00111\u0007\u0005\u0007\u0003kc\u0001\u0019A1\u0002\t\u0015dW-\\\u0001\u0007C\u0012$wJ\\3\u0015\t\u0005m\u0016QX\u0007\u0002\u0001!1\u0011QW\u0007A\u0002\u0005\fq\u0001\u001d:fa\u0016tG\r\u0006\u0003\u0002<\u0006\r\u0007BBA[\u001d\u0001\u0007\u0011-\u0001\u0006qe\u0016\u0004XM\u001c3BY2$B!a/\u0002J\"9\u00111Z\bA\u0002\u00055\u0017!B3mK6\u001c\b\u0003\u0002<\u0002P\u0006L1!!5W\u00051IE/\u001a:bE2,wJ\\2f\u0003\u0019\tG\rZ!mYR!\u00111XAl\u0011\u001d\tY\r\u0005a\u0001\u0003\u001b\fa!\u001b8tKJ$HCBA\u0013\u0003;\fy\u000eC\u0004\u0002,F\u0001\r!a\r\t\r\u0005U\u0016\u00031\u0001b\u0003%Ign]3si\u0006cG\u000e\u0006\u0004\u0002&\u0005\u0015\u0018q\u001d\u0005\b\u0003W\u0013\u0002\u0019AA\u001a\u0011\u001d\tYM\u0005a\u0001\u0003\u001b\faA]3n_Z,GCBA\u0013\u0003[\fy\u000fC\u0004\u0002,N\u0001\r!a\r\t\u000f\u0005E8\u00031\u0001\u00024\u0005)1m\\;oiR\u0019\u0011-!>\t\u000f\u0005-F\u00031\u0001\u00024\u0005Y1/\u001e2ue\u0006\u001cGo\u00148f)\u0011\tY,a?\t\r\u0005UV\u00031\u0001b\u0003A\u0011X-\\8wK\"+\u0017\rZ(qi&|g\u000e\u0006\u0003\u0003\u0002\t\u001d\u0001\u0003B4\u0003\u0004\u0005L1A!\u0002Y\u0005\u0019y\u0005\u000f^5p]\"I!\u0011\u0002\f\u0011\u0002\u0003\u0007!1B\u0001\u0013e\u0016\u001c\u0018N_3J]R,'O\\1m%\u0016\u0004(\u000fE\u0002h\u0005\u001bI1Aa\u0004Y\u0005\u001d\u0011un\u001c7fC:\f!D]3n_Z,\u0007*Z1e\u001fB$\u0018n\u001c8%I\u00164\u0017-\u001e7uIE*\"A!\u0006+\t\t-!qC\u0016\u0003\u00053\u0001BAa\u0007\u0003&5\u0011!Q\u0004\u0006\u0005\u0005?\u0011\t#A\u0005v]\u000eDWmY6fI*\u0019!1\u0005-\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003(\tu!!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006Q!/Z7pm\u0016DU-\u00193\u0015\u0007\u0005\u0014i\u0003C\u0005\u0003\na\u0001\n\u00111\u0001\u0003\f\u0005!\"/Z7pm\u0016DU-\u00193%I\u00164\u0017-\u001e7uIE\n!D]3n_Z,\u0007*Z1e\u0003N\u001cX/\\5oO:{g.R7qif$2!\u0019B\u001b\u0011%\u0011IA\u0007I\u0001\u0002\u0004\u0011Y\u0001K\u0002\u001b\u0005s\u00012a\u001aB\u001e\u0013\r\u0011i\u0004\u0017\u0002\u0007S:d\u0017N\\3\u0002II,Wn\u001c<f\u0011\u0016\fG-Q:tk6Lgn\u001a(p]\u0016k\u0007\u000f^=%I\u00164\u0017-\u001e7uIE\n\u0001C]3n_Z,G*Y:u\u001fB$\u0018n\u001c8\u0015\t\t\u0005!Q\t\u0005\n\u0005\u0013a\u0002\u0013!a\u0001\u0005\u0017\t!D]3n_Z,G*Y:u\u001fB$\u0018n\u001c8%I\u00164\u0017-\u001e7uIE\n!B]3n_Z,G*Y:u)\r\t'Q\n\u0005\n\u0005\u0013q\u0002\u0013!a\u0001\u0005\u0017\tAC]3n_Z,G*Y:uI\u0011,g-Y;mi\u0012\n\u0014A\u0007:f[>4X\rT1ti\u0006\u001b8/^7j]\u001etuN\\#naRLHcA1\u0003V!I!\u0011\u0002\u0011\u0011\u0002\u0003\u0007!1\u0002\u0015\u0004A\te\u0012\u0001\n:f[>4X\rT1ti\u0006\u001b8/^7j]\u001etuN\\#naRLH\u0005Z3gCVdG\u000fJ\u0019\u0002\u0013I,Wn\u001c<f\u00032dGC\u0001B0!\u0015\u0011\tGa\u001ab\u001b\t\u0011\u0019GC\u0002\u0003fY\u000b\u0011\"[7nkR\f'\r\\3\n\t\t%$1\r\u0002\u0004'\u0016\f\u0018\u0001\u0005:f[>4X-\u00117m%\u00164XM]:f\u0003=\u0011X-\\8wK\"+\u0017\rZ,iS2,G\u0003\u0002B0\u0005cBqAa\u001d%\u0001\u0004\u0011)(A\u0001g!\u00199'qO1\u0003\f%\u0019!\u0011\u0010-\u0003\u0013\u0019+hn\u0019;j_:\f\u0014a\u0004:f[>4X\rT1ti^C\u0017\u000e\\3\u0015\t\t}#q\u0010\u0005\b\u0005g*\u0003\u0019\u0001B;\u0003-\u0011X-\\8wK\u001aK'o\u001d;\u0015\r\t\u0005!Q\u0011BE\u0011\u001d\u00119I\na\u0001\u0005k\n\u0011\u0001\u001d\u0005\n\u0005\u00173\u0003\u0013!a\u0001\u0003g\tAA\u001a:p[\u0006)\"/Z7pm\u00164\u0015N]:uI\u0011,g-Y;mi\u0012\u0012TC\u0001BIU\u0011\t\u0019Da\u0006\u0015\t\t}#Q\u0013\u0005\b\u0005\u000fC\u0003\u0019\u0001B;\u0003))gn];sKNK'0\u001a\u000b\u0005\u0003K\u0011Y\nC\u0004\u0003\u001e&\u0002\r!a\r\u0002\t!Lg\u000e\u001e\u0015\u0004S\te\u0012A\u00027f]\u001e$\b.A\u0004jg\u0016k\u0007\u000f^=\u0016\u0005\t-\u0011!B6m_:,G#\u0001;\u0002\u001f%$XM]1cY\u00164\u0015m\u0019;pef,\"Aa,\u0011\tY\u0014\tl]\u0005\u0004\u0005g3&AC*fc\u001a\u000b7\r^8ss\u0006)1\r\\3beR\u0011\u0011QE\u0001\u000fG2,\u0017M]!oINC'/\u001b8l)\u0011\tYL!0\t\u0013\t}v\u0006%AA\u0002\u0005M\u0012\u0001B:ju\u0016\f\u0001d\u00197fCJ\fe\u000eZ*ie&t7\u000e\n3fM\u0006,H\u000e\u001e\u00132\u0003\u001dyg-\u0011:sCf$R\u0001\u001eBd\u0005\u0013Dq!!\u00052\u0001\u0004\t)\u0002C\u0004\u0002HE\u0002\r!a\r\u0002\u0017\r|\u0007/\u001f+p\u0003J\u0014\u0018-_\u000b\u0005\u0005\u001f\u0014I\u000e\u0006\u0005\u00024\tE'q\u001cBr\u0011\u001d\u0011\u0019N\ra\u0001\u0005+\fA\u0001Z3tiB)q-a\u0006\u0003XB\u0019!M!7\u0005\u000f\tm'G1\u0001\u0003^\n\t!)\u0005\u0002bU\"9!\u0011\u001d\u001aA\u0002\u0005M\u0012!\u00033fgR\u001cF/\u0019:u\u0011\u001d\u0011)O\ra\u0001\u0003g\t1\u0001\\3o\u0003\u001d!x.\u0011:sCf,BAa;\u0003rR!!Q\u001eBz!\u00159\u0017q\u0003Bx!\r\u0011'\u0011\u001f\u0003\b\u00057\u001c$\u0019\u0001Bo\u0011%\u0011)pMA\u0001\u0002\b\u001190\u0001\u0006fm&$WM\\2fIE\u0002bA!?\u0003\u0000\n=XB\u0001B~\u0015\r\u0011i\u0010W\u0001\be\u00164G.Z2u\u0013\u0011\u0019\tAa?\u0003\u0011\rc\u0017m]:UC\u001e\f!\u0002\u001e:j[R{7+\u001b>f\u0003-\u0019H/\u0019:u?\u0012\u0002H.^:\u0015\t\u0005M2\u0011\u0002\u0005\b\u0003W+\u0004\u0019AA\u001aQ\r)$\u0011H\u00012g\u000e\fG.\u0019\u0013d_2dWm\u0019;j_:$S.\u001e;bE2,G%\u0011:sCf$U-];fI\u0011\u001aH/\u0019:u?\u0012j\u0017N\\;t)\u0011\t\u0019d!\u0005\t\u000f\u0005-f\u00071\u0001\u00024!\u001aaG!\u000f\u0002]M\u001c\u0017\r\\1%G>dG.Z2uS>tG%\\;uC\ndW\rJ!se\u0006LH)Z9vK\u0012\"SM\u001c3`IAdWo\u001d\u000b\u0005\u0003g\u0019I\u0002C\u0004\u0002,^\u0002\r!a\r)\u0007]\u0012I$A\u0018tG\u0006d\u0017\rJ2pY2,7\r^5p]\u0012jW\u000f^1cY\u0016$\u0013I\u001d:bs\u0012+\u0017/^3%I\u0015tGm\u0018\u0013nS:,8\u000f\u0006\u0003\u00024\r\u0005\u0002bBAVq\u0001\u0007\u00111\u0007\u0015\u0004q\te\u0012!L:dC2\fGeY8mY\u0016\u001cG/[8oI5,H/\u00192mK\u0012\n%O]1z\t\u0016\fX/\u001a\u0013%[V\u001cHo\u0012:poR!!1BB\u0015\u0011\u001d\u0011)/\u000fa\u0001\u0003gA3!\u000fB\u001d\u00031\u0019\bn\\;mINC'/\u001b8l)\u0011\u0011Ya!\r\t\u000f\t\u0015(\b1\u0001\u00024!\u001a!H!\u000f\u0002\u0013\r\fgn\u00155sS:\\G\u0003\u0002B\u0006\u0007sAqA!:<\u0001\u0004\t\u0019\u0004K\u0002<\u0005s\tAaX4fiR\u0019\u0011m!\u0011\t\u000f\u0005-F\b1\u0001\u00024!\u001aAH!\u000f\u0002\t}\u001bX\r\u001e\u000b\u0007\u0003K\u0019Iea\u0013\t\u000f\u0005-V\b1\u0001\u00024!1\u0011QW\u001fA\u0002\u0005D3!\u0010B\u001d\u0003-\u001a8-\u00197bI\r|G\u000e\\3di&|g\u000eJ7vi\u0006\u0014G.\u001a\u0013BeJ\f\u0017\u0010R3rk\u0016$CE]3tSj,G\u0003BA\u0013\u0007'BqA!:?\u0001\u0004\t\u0019$\u0001\u0007tiJLgn\u001a)sK\u001aL\u00070\u0006\u0002\u0004ZA!11LB3\u001b\t\u0019iF\u0003\u0003\u0004`\r\u0005\u0014\u0001\u00027b]\u001eT!aa\u0019\u0002\t)\fg/Y\u0005\u0005\u0007O\u001aiF\u0001\u0004TiJLgnZ\u0001+g\u000e\fG.\u0019\u0013d_2dWm\u0019;j_:$S.\u001e;bE2,G%\u0011:sCf$U-];fI\u0011\u001aH/\u0019:u\u00039\u001a8-\u00197bI\r|G\u000e\\3di&|g\u000eJ7vi\u0006\u0014G.\u001a\u0013BeJ\f\u0017\u0010R3rk\u0016$Ce\u001d;beR|F%Z9\u0015\t\u0005\u00152q\u000e\u0005\n\u0003[\t\u0015\u0011!a\u0001\u0003g\t\u0001f]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%[V$\u0018M\u00197fI\u0005\u0013(/Y=EKF,X\r\n\u0013f]\u0012\fAf]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%[V$\u0018M\u00197fI\u0005\u0013(/Y=EKF,X\r\n\u0013f]\u0012|F%Z9\u0015\t\u0005\u00152q\u000f\u0005\n\u0003[\u0019\u0015\u0011!a\u0001\u0003g\t1h]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%[V$\u0018M\u00197fI\u0005\u0013(/Y=EKF,X\r\n\u0013baB,g\u000eZ!tgVl\u0017N\\4DCB\f7-\u001b;z)\u0011\tYl! \t\r\u0005UF\t1\u0001bQ\r!%\u0011H\u0001=g\u000e\fG.\u0019\u0013d_2dWm\u0019;j_:$S.\u001e;bE2,G%\u0011:sCf$U-];fI\u0011\u0002(/\u001a9f]\u0012\f5o];nS:<7)\u00199bG&$\u0018\u0010\u0006\u0003\u0002<\u000e\u0015\u0005BBA[\u000b\u0002\u0007\u0011\rK\u0002F\u0005s\t!\"\u0011:sCf$U-];f!\tqviE\u0003H\u00037\u0019y\t\u0005\u0003w\u0007#\u001b\u0018bABJ-\nI2\u000b\u001e:jGR|\u0005\u000f^5nSj,GmU3r\r\u0006\u001cGo\u001c:z)\t\u0019Y)\u0006\u0003\u0004\u001a\u000e}E\u0003BBN\u0007C\u0003BA\u0018\u0001\u0004\u001eB\u0019!ma(\u0005\r\tm\u0017J1\u0001f\u0011\u001d\u0019\u0019+\u0013a\u0001\u0007K\u000bAaY8mYB)a/a4\u0004\u001e\u0006Qa.Z<Ck&dG-\u001a:\u0016\t\r-6QW\u000b\u0003\u0007[\u0003rAXBX\u0007g\u001b9,C\u0002\u00042R\u0013qAQ;jY\u0012,'\u000fE\u0002c\u0007k#Q\u0001\u001a&C\u0002\u0015\u0004BA\u0018\u0001\u00044\u0006)Q-\u001c9usV!1QXBb+\t\u0019y\f\u0005\u0003_\u0001\r\u0005\u0007c\u00012\u0004D\u0012)Am\u0013b\u0001K\u0006\u0011B)\u001a4bk2$\u0018J\\5uS\u0006d7+\u001b>f+\t\u0019Im\u0004\u0002\u0004Lv\t\u0001#A\nEK\u001a\fW\u000f\u001c;J]&$\u0018.\u00197TSj,\u0007%\u0001\u0006Ti\u0006\u0014G.Z*ju\u0016,\"aa5\u0010\u0005\rUWD\u0001\u0001\u0001\u0004-\u0019F/\u00192mKNK'0\u001a\u0011\u0002\u000b\u0005dGn\\2\u0015\t\u0005U1Q\u001c\u0005\b\u0005K\u0004\u0006\u0019AA\u001a\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%cU!!qRBr\t\u0015!\u0017K1\u0001f\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0019I\u000f\u0005\u0003\u0004\\\r-\u0018\u0002BBw\u0007;\u0012aa\u00142kK\u000e$\bfB$\u0004r\u000e]8\u0011 \t\u0004O\u000eM\u0018bAB{1\n\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0007!:ai!=\u0004x\u000ee\b")
public class ArrayDeque<A>
extends AbstractBuffer<A>
implements IndexedBuffer<A>,
ArrayDequeOps<A, ArrayDeque, ArrayDeque<A>>,
DefaultSerializable {
    private Object[] array;
    private int scala$collection$mutable$ArrayDeque$$start;
    private int scala$collection$mutable$ArrayDeque$$end;

    public static <A> int $lessinit$greater$default$1() {
        return 16;
    }

    public static int DefaultInitialSize() {
        return 16;
    }

    public static <A> Builder<A, ArrayDeque<A>> newBuilder() {
        return new GrowableBuilder<A, ArrayDeque<A>>(){

            public void sizeHint(int size) {
                ((ArrayDeque)this.elems()).ensureSize(size);
            }
        };
    }

    public static <B> ArrayDeque<B> from(IterableOnce<B> coll) {
        return ArrayDeque$.MODULE$.from((IterableOnce)coll);
    }

    public static SeqOps tabulate(int n, Function1 f) {
        GrowableBuilder tabulate_b = new /* invalid duplicate definition of identical inner class */;
        tabulate_b.sizeHint(n);
        for (int tabulate_i = 0; tabulate_i < n; ++tabulate_i) {
            Object tabulate_$plus$eq_elem = f.apply(tabulate_i);
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var4_4 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static SeqOps fill(int n, Function0 elem) {
        GrowableBuilder fill_b = new /* invalid duplicate definition of identical inner class */;
        fill_b.sizeHint(n);
        for (int fill_i = 0; fill_i < n; ++fill_i) {
            Object fill_$plus$eq_elem = elem.apply();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var4_4 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        GrowableBuilder tabulate_b = new /* invalid duplicate definition of identical inner class */;
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var11_11;
            GrowableBuilder tabulate_b2 = new /* invalid duplicate definition of identical inner class */;
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var14_14;
                GrowableBuilder tabulate_b3 = new /* invalid duplicate definition of identical inner class */;
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var17_17;
                    GrowableBuilder tabulate_b4 = new /* invalid duplicate definition of identical inner class */;
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object var20_20;
                        GrowableBuilder tabulate_b5 = new /* invalid duplicate definition of identical inner class */;
                        tabulate_b5.sizeHint(n5);
                        for (int tabulate_i5 = 0; tabulate_i5 < n5; ++tabulate_i5) {
                            Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4, tabulate_i5);
                            tabulate_b5.addOne(tabulate_$plus$eq_elem);
                            var20_20 = null;
                        }
                        Object var18_18 = null;
                        var20_20 = null;
                        SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b5.result();
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var17_17 = null;
                    }
                    Object var15_15 = null;
                    var17_17 = null;
                    SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var14_14 = null;
                }
                Object var12_12 = null;
                var14_14 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var8_8 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        GrowableBuilder tabulate_b = new /* invalid duplicate definition of identical inner class */;
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var10_10;
            GrowableBuilder tabulate_b2 = new /* invalid duplicate definition of identical inner class */;
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var13_13;
                GrowableBuilder tabulate_b3 = new /* invalid duplicate definition of identical inner class */;
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object var16_16;
                    GrowableBuilder tabulate_b4 = new /* invalid duplicate definition of identical inner class */;
                    tabulate_b4.sizeHint(n4);
                    for (int tabulate_i4 = 0; tabulate_i4 < n4; ++tabulate_i4) {
                        Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3, tabulate_i4);
                        tabulate_b4.addOne(tabulate_$plus$eq_elem);
                        var16_16 = null;
                    }
                    Object var14_14 = null;
                    var16_16 = null;
                    SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b4.result();
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var13_13 = null;
                }
                Object var11_11 = null;
                var13_13 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var7_7 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        GrowableBuilder tabulate_b = new /* invalid duplicate definition of identical inner class */;
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var9_9;
            GrowableBuilder tabulate_b2 = new /* invalid duplicate definition of identical inner class */;
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object var12_12;
                GrowableBuilder tabulate_b3 = new /* invalid duplicate definition of identical inner class */;
                tabulate_b3.sizeHint(n3);
                for (int tabulate_i3 = 0; tabulate_i3 < n3; ++tabulate_i3) {
                    Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2, tabulate_i3);
                    tabulate_b3.addOne(tabulate_$plus$eq_elem);
                    var12_12 = null;
                }
                Object var10_10 = null;
                var12_12 = null;
                SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b3.result();
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var9_9 = null;
            }
            Object var7_7 = null;
            var9_9 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var6_6 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        GrowableBuilder tabulate_b = new /* invalid duplicate definition of identical inner class */;
        tabulate_b.sizeHint(n1);
        for (int tabulate_i = 0; tabulate_i < n1; ++tabulate_i) {
            Object var8_8;
            GrowableBuilder tabulate_b2 = new /* invalid duplicate definition of identical inner class */;
            tabulate_b2.sizeHint(n2);
            for (int tabulate_i2 = 0; tabulate_i2 < n2; ++tabulate_i2) {
                Object tabulate_$plus$eq_elem = f.apply(tabulate_i, tabulate_i2);
                tabulate_b2.addOne(tabulate_$plus$eq_elem);
                var8_8 = null;
            }
            Object var6_6 = null;
            var8_8 = null;
            SeqOps tabulate_$plus$eq_elem = (SeqOps)tabulate_b2.result();
            tabulate_b.addOne(tabulate_$plus$eq_elem);
            Object var5_5 = null;
        }
        return (SeqOps)tabulate_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        GrowableBuilder fill_b = new /* invalid duplicate definition of identical inner class */;
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var11_11;
            GrowableBuilder fill_b2 = new /* invalid duplicate definition of identical inner class */;
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var14_14;
                GrowableBuilder fill_b3 = new /* invalid duplicate definition of identical inner class */;
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var17_17;
                    GrowableBuilder fill_b4 = new /* invalid duplicate definition of identical inner class */;
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object var20_20;
                        GrowableBuilder fill_b5 = new /* invalid duplicate definition of identical inner class */;
                        fill_b5.sizeHint(n5);
                        for (int fill_i5 = 0; fill_i5 < n5; ++fill_i5) {
                            Object fill_$plus$eq_elem = elem.apply();
                            fill_b5.addOne(fill_$plus$eq_elem);
                            var20_20 = null;
                        }
                        Object var18_18 = null;
                        var20_20 = null;
                        SeqOps fill_$plus$eq_elem = (SeqOps)fill_b5.result();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var17_17 = null;
                    }
                    Object var15_15 = null;
                    var17_17 = null;
                    SeqOps fill_$plus$eq_elem = (SeqOps)fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var14_14 = null;
                }
                Object var12_12 = null;
                var14_14 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var11_11 = null;
            }
            Object var9_9 = null;
            var11_11 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var8_8 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        GrowableBuilder fill_b = new /* invalid duplicate definition of identical inner class */;
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var10_10;
            GrowableBuilder fill_b2 = new /* invalid duplicate definition of identical inner class */;
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var13_13;
                GrowableBuilder fill_b3 = new /* invalid duplicate definition of identical inner class */;
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object var16_16;
                    GrowableBuilder fill_b4 = new /* invalid duplicate definition of identical inner class */;
                    fill_b4.sizeHint(n4);
                    for (int fill_i4 = 0; fill_i4 < n4; ++fill_i4) {
                        Object fill_$plus$eq_elem = elem.apply();
                        fill_b4.addOne(fill_$plus$eq_elem);
                        var16_16 = null;
                    }
                    Object var14_14 = null;
                    var16_16 = null;
                    SeqOps fill_$plus$eq_elem = (SeqOps)fill_b4.result();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var13_13 = null;
                }
                Object var11_11 = null;
                var13_13 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var10_10 = null;
            }
            Object var8_8 = null;
            var10_10 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var7_7 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        GrowableBuilder fill_b = new /* invalid duplicate definition of identical inner class */;
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var9_9;
            GrowableBuilder fill_b2 = new /* invalid duplicate definition of identical inner class */;
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object var12_12;
                GrowableBuilder fill_b3 = new /* invalid duplicate definition of identical inner class */;
                fill_b3.sizeHint(n3);
                for (int fill_i3 = 0; fill_i3 < n3; ++fill_i3) {
                    Object fill_$plus$eq_elem = elem.apply();
                    fill_b3.addOne(fill_$plus$eq_elem);
                    var12_12 = null;
                }
                Object var10_10 = null;
                var12_12 = null;
                SeqOps fill_$plus$eq_elem = (SeqOps)fill_b3.result();
                fill_b2.addOne(fill_$plus$eq_elem);
                var9_9 = null;
            }
            Object var7_7 = null;
            var9_9 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var6_6 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        GrowableBuilder fill_b = new /* invalid duplicate definition of identical inner class */;
        fill_b.sizeHint(n1);
        for (int fill_i = 0; fill_i < n1; ++fill_i) {
            Object var8_8;
            GrowableBuilder fill_b2 = new /* invalid duplicate definition of identical inner class */;
            fill_b2.sizeHint(n2);
            for (int fill_i2 = 0; fill_i2 < n2; ++fill_i2) {
                Object fill_$plus$eq_elem = elem.apply();
                fill_b2.addOne(fill_$plus$eq_elem);
                var8_8 = null;
            }
            Object var6_6 = null;
            var8_8 = null;
            SeqOps fill_$plus$eq_elem = (SeqOps)fill_b2.result();
            fill_b.addOne(fill_$plus$eq_elem);
            Object var5_5 = null;
        }
        return (SeqOps)fill_b.result();
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(ArrayDeque$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(ArrayDeque$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        ArrayDeque$ unfold_this = ArrayDeque$.MODULE$;
        View.Unfold from_source = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        ArrayDeque$ iterate_this = ArrayDeque$.MODULE$;
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_source);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public final Object clone() {
        return ArrayDequeOps.clone$(this);
    }

    @Override
    public final void requireBounds(int idx, int until) {
        ArrayDequeOps.requireBounds$(this, idx, until);
    }

    @Override
    public final int requireBounds$default$2() {
        return ArrayDequeOps.requireBounds$default$2$(this);
    }

    @Override
    public Object copySliceToArray(int srcStart, Object dest, int destStart, int maxItems) {
        return ArrayDequeOps.copySliceToArray$(this, srcStart, dest, destStart, maxItems);
    }

    @Override
    public Object reverse() {
        return ArrayDequeOps.reverse$(this);
    }

    @Override
    public Object slice(int from, int until) {
        return ArrayDequeOps.slice$(this, from, until);
    }

    @Override
    public Iterator<ArrayDeque<A>> sliding(int window, int step) {
        return ArrayDequeOps.sliding$(this, window, step);
    }

    @Override
    public Iterator<ArrayDeque<A>> grouped(int n) {
        return ArrayDequeOps.grouped$(this, n);
    }

    @Override
    public Object distinctBy(Function1 f) {
        return StrictOptimizedSeqOps.distinctBy$(this, f);
    }

    @Override
    public Object prepended(Object elem) {
        return StrictOptimizedSeqOps.prepended$(this, elem);
    }

    @Override
    public Object appended(Object elem) {
        return StrictOptimizedSeqOps.appended$(this, elem);
    }

    @Override
    public Object appendedAll(IterableOnce suffix) {
        return StrictOptimizedSeqOps.appendedAll$(this, suffix);
    }

    @Override
    public Object prependedAll(IterableOnce prefix) {
        return StrictOptimizedSeqOps.prependedAll$(this, prefix);
    }

    @Override
    public Object padTo(int len, Object elem) {
        return StrictOptimizedSeqOps.padTo$(this, len, elem);
    }

    @Override
    public Object diff(Seq that) {
        return StrictOptimizedSeqOps.diff$(this, that);
    }

    @Override
    public Object intersect(Seq that) {
        return StrictOptimizedSeqOps.intersect$(this, that);
    }

    @Override
    public Tuple2<ArrayDeque<A>, ArrayDeque<A>> partition(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<ArrayDeque<A>, ArrayDeque<A>> span(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<ArrayDeque<A1>, ArrayDeque<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<ArrayDeque<A1>, ArrayDeque<A2>, ArrayDeque<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<A, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<A, IterableOnce<B>> f) {
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
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<A, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<A, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<A, B>, C2> b) {
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
    public <A1, A2> Tuple2<ArrayDeque<A1>, ArrayDeque<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
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
    public IndexedBuffer<A> flatMapInPlace(Function1<A, IterableOnce<A>> f) {
        return IndexedBuffer.flatMapInPlace$(this, f);
    }

    @Override
    public IndexedBuffer<A> filterInPlace(Function1<A, Object> p) {
        return IndexedBuffer.filterInPlace$(this, p);
    }

    @Override
    public IndexedBuffer<A> patchInPlace(int from, IterableOnce<A> patch, int replaced) {
        return IndexedBuffer.patchInPlace$(this, from, patch, replaced);
    }

    @Override
    public scala.collection.mutable.IndexedSeqOps<A, ?, ArrayDeque<A>> mapInPlace(Function1<A, A> f) {
        return scala.collection.mutable.IndexedSeqOps.mapInPlace$(this, f);
    }

    @Override
    public <B> scala.collection.mutable.IndexedSeqOps<A, ?, ArrayDeque<A>> sortInPlace(Ordering<B> ord) {
        return scala.collection.mutable.IndexedSeqOps.sortInPlace$(this, ord);
    }

    @Override
    public scala.collection.mutable.IndexedSeqOps<A, ?, ArrayDeque<A>> sortInPlaceWith(Function2<A, A, Object> lt) {
        return scala.collection.mutable.IndexedSeqOps.sortInPlaceWith$(this, lt);
    }

    @Override
    public <B> scala.collection.mutable.IndexedSeqOps<A, ?, ArrayDeque<A>> sortInPlaceBy(Function1<A, B> f, Ordering<B> ord) {
        return scala.collection.mutable.IndexedSeqOps.sortInPlaceBy$(this, f, ord);
    }

    @Override
    public Iterator<A> iterator() {
        return IndexedSeqOps.iterator$(this);
    }

    @Override
    public Iterator<A> reverseIterator() {
        return IndexedSeqOps.reverseIterator$(this);
    }

    @Override
    public <B$> B$ foldRight(B$ z, Function2<A, B$, B$> op) {
        return (B$)IndexedSeqOps.foldRight$(this, z, op);
    }

    @Override
    public IndexedSeqView<A> view() {
        return IndexedSeqOps.view$(this);
    }

    @Override
    public IndexedSeqView<A> view(int from, int until) {
        return IndexedSeqOps.view$(this, from, until);
    }

    @Override
    public Iterable<A> reversed() {
        return IndexedSeqOps.reversed$(this);
    }

    @Override
    public Object take(int n) {
        return IndexedSeqOps.take$(this, n);
    }

    @Override
    public Object drop(int n) {
        return IndexedSeqOps.drop$(this, n);
    }

    @Override
    public A head() {
        return (A)IndexedSeqOps.head$(this);
    }

    @Override
    public Option<A> headOption() {
        return IndexedSeqOps.headOption$(this);
    }

    @Override
    public A last() {
        return (A)IndexedSeqOps.last$(this);
    }

    @Override
    public final int lengthCompare(int len) {
        return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, len);
    }

    @Override
    public final int lengthCompare(Iterable<?> that) {
        return IndexedSeqOps.lengthCompare$((IndexedSeqOps)this, that);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, Ordering<B> ord) {
        return IndexedSeqOps.search$(this, elem, ord);
    }

    @Override
    public <B> Searching.SearchResult search(B elem, int from, int to, Ordering<B> ord) {
        return IndexedSeqOps.search$(this, elem, from, to, ord);
    }

    @Override
    public Object[] array() {
        return this.array;
    }

    public void array_$eq(Object[] x$1) {
        this.array = x$1;
    }

    public int scala$collection$mutable$ArrayDeque$$start() {
        return this.scala$collection$mutable$ArrayDeque$$start;
    }

    public void scala$collection$mutable$ArrayDeque$$start_$eq(int x$1) {
        this.scala$collection$mutable$ArrayDeque$$start = x$1;
    }

    public int scala$collection$mutable$ArrayDeque$$end() {
        return this.scala$collection$mutable$ArrayDeque$$end;
    }

    public void scala$collection$mutable$ArrayDeque$$end_$eq(int x$1) {
        this.scala$collection$mutable$ArrayDeque$$end = x$1;
    }

    private void reset(Object[] array, int start2, int end) {
        if (!((array.length & array.length - 1) == 0)) {
            throw new AssertionError((Object)new StringBuilder(18).append("assertion failed: ").append((Object)"Array.length must be power of 2").toString());
        }
        int requireBounds_until = array.length;
        if (start2 < 0 || start2 >= requireBounds_until) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(start2).append(" is out of bounds (min 0, max ").append(requireBounds_until - 1).append(")").toString());
        }
        int requireBounds_until2 = array.length;
        if (end < 0 || end >= requireBounds_until2) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(end).append(" is out of bounds (min 0, max ").append(requireBounds_until2 - 1).append(")").toString());
        }
        this.array_$eq(array);
        this.scala$collection$mutable$ArrayDeque$$start_$eq(start2);
        this.scala$collection$mutable$ArrayDeque$$end_$eq(end);
    }

    @Override
    public int knownSize() {
        return IndexedSeqOps.knownSize$(this);
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        return (S)IndexedSeqOps.stepper$(this, shape);
    }

    @Override
    public A apply(int idx) {
        int requireBounds_until = this.length();
        if (idx < 0 || idx >= requireBounds_until) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(requireBounds_until - 1).append(")").toString());
        }
        return (A)this.array()[this.start_$plus(idx)];
    }

    @Override
    public void update(int idx, A elem) {
        int requireBounds_until = this.length();
        if (idx < 0 || idx >= requireBounds_until) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(requireBounds_until - 1).append(")").toString());
        }
        this.array()[this.start_$plus((int)idx)] = elem;
    }

    @Override
    public ArrayDeque<A> addOne(A elem) {
        this.ensureSize(this.length() + 1);
        return this.scala$collection$mutable$ArrayDeque$$appendAssumingCapacity(elem);
    }

    @Override
    public ArrayDeque<A> prepend(A elem) {
        this.ensureSize(this.length() + 1);
        return this.scala$collection$mutable$ArrayDeque$$prependAssumingCapacity(elem);
    }

    public ArrayDeque<A> scala$collection$mutable$ArrayDeque$$appendAssumingCapacity(A elem) {
        this.array()[this.scala$collection$mutable$ArrayDeque$$end()] = elem;
        int scala$collection$mutable$ArrayDeque$$end_$plus_idx = 1;
        this.scala$collection$mutable$ArrayDeque$$end_$eq(this.scala$collection$mutable$ArrayDeque$$end() + scala$collection$mutable$ArrayDeque$$end_$plus_idx & this.array().length - 1);
        return this;
    }

    public ArrayDeque<A> scala$collection$mutable$ArrayDeque$$prependAssumingCapacity(A elem) {
        int scala$collection$mutable$ArrayDeque$$start_$minus_idx = 1;
        this.scala$collection$mutable$ArrayDeque$$start_$eq(this.scala$collection$mutable$ArrayDeque$$start() - scala$collection$mutable$ArrayDeque$$start_$minus_idx & this.array().length - 1);
        this.array()[this.scala$collection$mutable$ArrayDeque$$start()] = elem;
        return this;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public ArrayDeque<A> prependAll(IterableOnce<A> elems) {
        Iterator it = elems.iterator();
        if (it.nonEmpty()) {
            int n = this.length();
            int n2 = elems.knownSize();
            switch (n2) {
                default: 
            }
            if (n2 < 0) {
                IndexedSeq$ toFactory_factory = IndexedSeq$.MODULE$;
                Object var10_5 = null;
                this.prependAll((IterableOnce)it.to(new IterableFactory.ToFactory(toFactory_factory)));
            } else if (n2 + n >= this.array().length) {
                int finalLength = n2 + n;
                Object[] array2 = ArrayDeque$.MODULE$.alloc(finalLength);
                it.copyToArray(array2);
                this.copySliceToArray(0, array2, n2, n);
                this.reset(array2, 0, finalLength);
            } else {
                for (int i = 0; i < n2; ++i) {
                    void _set_elem;
                    A a = it.next();
                    int _set_idx = i - n2;
                    this.array()[this.start_$plus((int)_set_idx)] = _set_elem;
                    a = null;
                }
                this.scala$collection$mutable$ArrayDeque$$start_$eq(this.scala$collection$mutable$ArrayDeque$$start() - n2 & this.array().length - 1);
            }
        }
        return this;
    }

    @Override
    public ArrayDeque<A> addAll(IterableOnce<A> elems) {
        int n = elems.knownSize();
        switch (n) {
            default: 
        }
        if (n > 0) {
            this.ensureSize(n + this.length());
            elems.iterator().foreach((Function1<Object, ArrayDeque> & Serializable)elem -> this.scala$collection$mutable$ArrayDeque$$appendAssumingCapacity(elem));
        } else {
            elems.iterator().foreach((Function1<Object, ArrayDeque> & Serializable)elem -> (ArrayDeque)this.$plus$eq(elem));
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void insert(int idx, A elem) {
        int requireBounds_until = this.length() + 1;
        if (idx < 0 || idx >= requireBounds_until) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(requireBounds_until - 1).append(")").toString());
        }
        int n = this.length();
        if (idx == 0) {
            this.prepend((Object)elem);
            return;
        }
        if (idx == n) {
            this.addOne((Object)elem);
            return;
        }
        int finalLength = n + 1;
        if (finalLength >= this.array().length) {
            Object[] array2 = ArrayDeque$.MODULE$.alloc(finalLength);
            this.copySliceToArray(0, array2, 0, idx);
            array2[idx] = elem;
            this.copySliceToArray(idx, array2, idx + 1, n);
            this.reset(array2, 0, finalLength);
            return;
        }
        if (n <= idx * 2) {
            for (int i = n - 1; i >= idx; --i) {
                void _set_elem;
                Object object = this.array()[this.start_$plus(i)];
                int _set_idx = i + 1;
                this.array()[this.start_$plus((int)_set_idx)] = _set_elem;
                object = null;
            }
            int scala$collection$mutable$ArrayDeque$$end_$plus_idx = 1;
            this.scala$collection$mutable$ArrayDeque$$end_$eq(this.scala$collection$mutable$ArrayDeque$$end() + scala$collection$mutable$ArrayDeque$$end_$plus_idx & this.array().length - 1);
            this.array()[this.start_$plus((int)(++i))] = elem;
            return;
        }
        for (int i = 0; i < idx; ++i) {
            void _set_elem;
            Object object = this.array()[this.start_$plus(i)];
            int _set_idx = i - 1;
            this.array()[this.start_$plus((int)_set_idx)] = _set_elem;
            object = null;
        }
        int scala$collection$mutable$ArrayDeque$$start_$minus_idx = 1;
        this.scala$collection$mutable$ArrayDeque$$start_$eq(this.scala$collection$mutable$ArrayDeque$$start() - scala$collection$mutable$ArrayDeque$$start_$minus_idx & this.array().length - 1);
        this.array()[this.start_$plus((int)i)] = elem;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void insertAll(int idx, IterableOnce<A> elems) {
        void var6_8;
        int n;
        Iterator<A> iterator2;
        int requireBounds_until = this.length() + 1;
        if (idx < 0 || idx >= requireBounds_until) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(requireBounds_until - 1).append(")").toString());
        }
        int n2 = this.length();
        if (idx == 0) {
            this.prependAll((IterableOnce)elems);
            return;
        }
        if (idx == n2) {
            this.addAll((IterableOnce)elems);
            return;
        }
        int _srcLength = elems.knownSize();
        if (_srcLength >= 0) {
            iterator2 = elems.iterator();
            n = _srcLength;
        } else {
            IndexedSeq indexed = (IndexedSeq)IndexedSeq$.MODULE$.from((IterableOnce)elems);
            iterator2 = indexed.iterator();
            n = indexed.length();
        }
        int n3 = n;
        Iterator<A> it = iterator2;
        if (var6_8.nonEmpty()) {
            int i;
            int finalLength = n3 + n2;
            if (finalLength >= this.array().length) {
                Object[] array2 = ArrayDeque$.MODULE$.alloc(finalLength);
                this.copySliceToArray(0, array2, 0, idx);
                var6_8.copyToArray(array2, idx);
                this.copySliceToArray(idx, array2, idx + n3, n2);
                this.reset(array2, 0, finalLength);
                return;
            }
            if (2 * idx >= n2) {
                for (int i2 = n2 - 1; i2 >= idx; --i2) {
                    void _set_elem;
                    Object object = this.array()[this.start_$plus(i2)];
                    int _set_idx = i2 + n3;
                    this.array()[this.start_$plus((int)_set_idx)] = _set_elem;
                    object = null;
                }
                this.scala$collection$mutable$ArrayDeque$$end_$eq(this.scala$collection$mutable$ArrayDeque$$end() + n3 & this.array().length - 1);
                while (var6_8.hasNext()) {
                    Object _set_elem = var6_8.next();
                    this.array()[this.start_$plus((int)(++i2))] = _set_elem;
                    Object var14_14 = null;
                }
                return;
            }
            for (i = 0; i < idx; ++i) {
                void _set_elem;
                Object object = this.array()[this.start_$plus(i)];
                int _set_idx = i - n3;
                this.array()[this.start_$plus((int)_set_idx)] = _set_elem;
                object = null;
            }
            this.scala$collection$mutable$ArrayDeque$$start_$eq(this.scala$collection$mutable$ArrayDeque$$start() - n3 & this.array().length - 1);
            while (var6_8.hasNext()) {
                Object _set_elem = var6_8.next();
                this.array()[this.start_$plus((int)i)] = _set_elem;
                Object var17_18 = null;
                ++i;
            }
            return;
        }
    }

    @Override
    public void remove(int idx, int count) {
        if (count > 0) {
            int i;
            int requireBounds_until = this.length();
            if (idx < 0 || idx >= requireBounds_until) {
                throw new IndexOutOfBoundsException(new StringBuilder(31).append(idx).append(" is out of bounds (min 0, max ").append(requireBounds_until - 1).append(")").toString());
            }
            int n = this.length();
            int removals = Math.min(n - idx, count);
            int finalLength = n - removals;
            int suffixStart = idx + removals;
            if (this.array().length > 128 && this.array().length - finalLength - (finalLength >> 1) > finalLength) {
                Object[] array2 = ArrayDeque$.MODULE$.alloc(finalLength);
                this.copySliceToArray(0, array2, 0, idx);
                this.copySliceToArray(suffixStart, array2, idx, n);
                this.reset(array2, 0, finalLength);
                return;
            }
            if (2 * idx <= finalLength) {
                int i2;
                for (i2 = suffixStart - 1; i2 >= removals; --i2) {
                    Object _set_elem;
                    int _get_idx = i2 - removals;
                    this.array()[this.start_$plus((int)i2)] = _set_elem = this.array()[this.start_$plus(_get_idx)];
                    Object var12_11 = null;
                }
                while (i2 >= 0) {
                    this.array()[this.start_$plus((int)i2)] = null;
                    --i2;
                }
                this.scala$collection$mutable$ArrayDeque$$start_$eq(this.start_$plus(removals));
                return;
            }
            for (i = idx; i < finalLength; ++i) {
                Object _set_elem;
                int _get_idx = i + removals;
                this.array()[this.start_$plus((int)i)] = _set_elem = this.array()[this.start_$plus(_get_idx)];
                Object var14_14 = null;
            }
            while (i < n) {
                this.array()[this.start_$plus((int)i)] = null;
                ++i;
            }
            this.scala$collection$mutable$ArrayDeque$$end_$eq(this.scala$collection$mutable$ArrayDeque$$end() - removals & this.array().length - 1);
            return;
        }
        if (!(count == 0)) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)ArrayDeque.$anonfun$remove$1(count)).toString());
        }
    }

    @Override
    public A remove(int idx) {
        A elem = this.apply(idx);
        this.remove(idx, 1);
        return elem;
    }

    @Override
    public ArrayDeque<A> subtractOne(A elem) {
        int idx = this.indexOf(elem);
        if (idx >= 0) {
            this.remove(idx, 1);
        }
        return this;
    }

    public Option<A> removeHeadOption(boolean resizeInternalRepr) {
        Object var2_2;
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        Object removeHeadAssumingNonEmpty_elem = this.array()[this.scala$collection$mutable$ArrayDeque$$start()];
        this.array()[this.scala$collection$mutable$ArrayDeque$$start()] = null;
        this.scala$collection$mutable$ArrayDeque$$start_$eq(this.start_$plus(1));
        if (resizeInternalRepr) {
            this.scala$collection$mutable$ArrayDeque$$resize(this.length());
        }
        void v0 = var2_2;
        var2_2 = null;
        return new Some<void>(v0);
    }

    /*
     * WARNING - void declaration
     */
    public A removeHead(boolean resizeInternalRepr) {
        void var2_2;
        if (this.isEmpty()) {
            throw new NoSuchElementException("empty collection");
        }
        Object removeHeadAssumingNonEmpty_elem = this.array()[this.scala$collection$mutable$ArrayDeque$$start()];
        this.array()[this.scala$collection$mutable$ArrayDeque$$start()] = null;
        this.scala$collection$mutable$ArrayDeque$$start_$eq(this.start_$plus(1));
        if (resizeInternalRepr) {
            this.scala$collection$mutable$ArrayDeque$$resize(this.length());
        }
        return var2_2;
    }

    public boolean removeHeadOption$default$1() {
        return false;
    }

    public boolean removeHead$default$1() {
        return false;
    }

    private A removeHeadAssumingNonEmpty(boolean resizeInternalRepr) {
        Object elem = this.array()[this.scala$collection$mutable$ArrayDeque$$start()];
        this.array()[this.scala$collection$mutable$ArrayDeque$$start()] = null;
        this.scala$collection$mutable$ArrayDeque$$start_$eq(this.start_$plus(1));
        if (resizeInternalRepr) {
            this.scala$collection$mutable$ArrayDeque$$resize(this.length());
        }
        return (A)elem;
    }

    private boolean removeHeadAssumingNonEmpty$default$1() {
        return false;
    }

    public Option<A> removeLastOption(boolean resizeInternalRepr) {
        Object var2_3;
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        int removeLastAssumingNonEmpty_scala$collection$mutable$ArrayDeque$$end_$minus_idx = 1;
        this.scala$collection$mutable$ArrayDeque$$end_$eq(this.scala$collection$mutable$ArrayDeque$$end() - removeLastAssumingNonEmpty_scala$collection$mutable$ArrayDeque$$end_$minus_idx & this.array().length - 1);
        Object removeLastAssumingNonEmpty_elem = this.array()[this.scala$collection$mutable$ArrayDeque$$end()];
        this.array()[this.scala$collection$mutable$ArrayDeque$$end()] = null;
        if (resizeInternalRepr) {
            this.scala$collection$mutable$ArrayDeque$$resize(this.length());
        }
        void v0 = var2_3;
        var2_3 = null;
        return new Some<void>(v0);
    }

    /*
     * WARNING - void declaration
     */
    public A removeLast(boolean resizeInternalRepr) {
        void var2_3;
        if (this.isEmpty()) {
            throw new NoSuchElementException("empty collection");
        }
        int removeLastAssumingNonEmpty_scala$collection$mutable$ArrayDeque$$end_$minus_idx = 1;
        this.scala$collection$mutable$ArrayDeque$$end_$eq(this.scala$collection$mutable$ArrayDeque$$end() - removeLastAssumingNonEmpty_scala$collection$mutable$ArrayDeque$$end_$minus_idx & this.array().length - 1);
        Object removeLastAssumingNonEmpty_elem = this.array()[this.scala$collection$mutable$ArrayDeque$$end()];
        this.array()[this.scala$collection$mutable$ArrayDeque$$end()] = null;
        if (resizeInternalRepr) {
            this.scala$collection$mutable$ArrayDeque$$resize(this.length());
        }
        return var2_3;
    }

    public boolean removeLastOption$default$1() {
        return false;
    }

    public boolean removeLast$default$1() {
        return false;
    }

    private A removeLastAssumingNonEmpty(boolean resizeInternalRepr) {
        int scala$collection$mutable$ArrayDeque$$end_$minus_idx = 1;
        this.scala$collection$mutable$ArrayDeque$$end_$eq(this.scala$collection$mutable$ArrayDeque$$end() - scala$collection$mutable$ArrayDeque$$end_$minus_idx & this.array().length - 1);
        Object elem = this.array()[this.scala$collection$mutable$ArrayDeque$$end()];
        this.array()[this.scala$collection$mutable$ArrayDeque$$end()] = null;
        if (resizeInternalRepr) {
            this.scala$collection$mutable$ArrayDeque$$resize(this.length());
        }
        return (A)elem;
    }

    private boolean removeLastAssumingNonEmpty$default$1() {
        return false;
    }

    public scala.collection.immutable.Seq<A> removeAll() {
        Builder elems = Seq$.MODULE$.newBuilder();
        elems.sizeHint(this.length());
        while (this.nonEmpty()) {
            boolean removeHeadAssumingNonEmpty_resizeInternalRepr = false;
            Object removeHeadAssumingNonEmpty_elem = this.array()[this.scala$collection$mutable$ArrayDeque$$start()];
            this.array()[this.scala$collection$mutable$ArrayDeque$$start()] = null;
            this.scala$collection$mutable$ArrayDeque$$start_$eq(this.start_$plus(1));
            if (removeHeadAssumingNonEmpty_resizeInternalRepr) {
                this.scala$collection$mutable$ArrayDeque$$resize(this.length());
            }
            Object var3_3 = null;
            void $plus$eq_elem = var3_3;
            elems.addOne($plus$eq_elem);
            Object var4_4 = null;
        }
        return (scala.collection.immutable.Seq)elems.result();
    }

    public scala.collection.immutable.Seq<A> removeAllReverse() {
        Builder elems = Seq$.MODULE$.newBuilder();
        elems.sizeHint(this.length());
        while (this.nonEmpty()) {
            boolean removeLastAssumingNonEmpty_resizeInternalRepr = false;
            int removeLastAssumingNonEmpty_scala$collection$mutable$ArrayDeque$$end_$minus_idx = 1;
            this.scala$collection$mutable$ArrayDeque$$end_$eq(this.scala$collection$mutable$ArrayDeque$$end() - removeLastAssumingNonEmpty_scala$collection$mutable$ArrayDeque$$end_$minus_idx & this.array().length - 1);
            Object removeLastAssumingNonEmpty_elem = this.array()[this.scala$collection$mutable$ArrayDeque$$end()];
            this.array()[this.scala$collection$mutable$ArrayDeque$$end()] = null;
            if (removeLastAssumingNonEmpty_resizeInternalRepr) {
                this.scala$collection$mutable$ArrayDeque$$resize(this.length());
            }
            Object var3_3 = null;
            void $plus$eq_elem = var3_3;
            elems.addOne($plus$eq_elem);
            Object var5_5 = null;
        }
        return (scala.collection.immutable.Seq)elems.result();
    }

    /*
     * WARNING - void declaration
     */
    public scala.collection.immutable.Seq<A> removeHeadWhile(Function1<A, Object> f) {
        Builder elems = Seq$.MODULE$.newBuilder();
        while (true) {
            void $plus$eq_elem;
            Option<A> exists_this;
            if (this.headOption() == null) {
                throw null;
            }
            Object var3_3 = null;
            if (!(!exists_this.isEmpty() && BoxesRunTime.unboxToBoolean(f.apply(exists_this.get())))) break;
            boolean removeHeadAssumingNonEmpty_resizeInternalRepr = false;
            Object removeHeadAssumingNonEmpty_elem = this.array()[this.scala$collection$mutable$ArrayDeque$$start()];
            this.array()[this.scala$collection$mutable$ArrayDeque$$start()] = null;
            this.scala$collection$mutable$ArrayDeque$$start_$eq(this.start_$plus(1));
            if (removeHeadAssumingNonEmpty_resizeInternalRepr) {
                this.scala$collection$mutable$ArrayDeque$$resize(this.length());
            }
            Object var5_5 = null;
            Object object = removeHeadAssumingNonEmpty_elem;
            if (elems == null) {
                throw null;
            }
            elems.addOne($plus$eq_elem);
            object = null;
        }
        return (scala.collection.immutable.Seq)elems.result();
    }

    /*
     * WARNING - void declaration
     */
    public scala.collection.immutable.Seq<A> removeLastWhile(Function1<A, Object> f) {
        Builder elems = Seq$.MODULE$.newBuilder();
        while (true) {
            void $plus$eq_elem;
            Option exists_this;
            if (this.lastOption() == null) {
                throw null;
            }
            Object var3_3 = null;
            if (!(!exists_this.isEmpty() && BoxesRunTime.unboxToBoolean(f.apply(exists_this.get())))) break;
            boolean removeLastAssumingNonEmpty_resizeInternalRepr = false;
            int removeLastAssumingNonEmpty_scala$collection$mutable$ArrayDeque$$end_$minus_idx = 1;
            this.scala$collection$mutable$ArrayDeque$$end_$eq(this.scala$collection$mutable$ArrayDeque$$end() - removeLastAssumingNonEmpty_scala$collection$mutable$ArrayDeque$$end_$minus_idx & this.array().length - 1);
            Object removeLastAssumingNonEmpty_elem = this.array()[this.scala$collection$mutable$ArrayDeque$$end()];
            this.array()[this.scala$collection$mutable$ArrayDeque$$end()] = null;
            if (removeLastAssumingNonEmpty_resizeInternalRepr) {
                this.scala$collection$mutable$ArrayDeque$$resize(this.length());
            }
            Object var5_5 = null;
            Object object = removeLastAssumingNonEmpty_elem;
            if (elems == null) {
                throw null;
            }
            elems.addOne($plus$eq_elem);
            object = null;
        }
        return (scala.collection.immutable.Seq)elems.result();
    }

    public Option<A> removeFirst(Function1<A, Object> p, int from) {
        int i = this.indexWhere(p, from);
        if (i < 0) {
            return None$.MODULE$;
        }
        return new Some<A>(this.remove(i));
    }

    public int removeFirst$default$2() {
        return 0;
    }

    /*
     * WARNING - void declaration
     */
    public scala.collection.immutable.Seq<A> removeAll(Function1<A, Object> p) {
        int i;
        Builder res = Seq$.MODULE$.newBuilder();
        int j = 0;
        for (i = 0; i < this.length(); ++i) {
            if (BoxesRunTime.unboxToBoolean(p.apply(this.apply(i)))) {
                void $plus$eq_elem;
                A a = this.apply(i);
                if (res == null) {
                    throw null;
                }
                res.addOne($plus$eq_elem);
                a = null;
                continue;
            }
            if (i != j) {
                this.update(j, this.apply(i));
            }
            ++j;
        }
        if (i != j) {
            this.takeInPlace(j);
        }
        return (scala.collection.immutable.Seq)res.result();
    }

    public void ensureSize(int hint) {
        if (hint > this.length() && hint >= this.array().length) {
            this.scala$collection$mutable$ArrayDeque$$resize(hint);
            return;
        }
    }

    @Override
    public int length() {
        int scala$collection$mutable$ArrayDeque$$end_$minus_idx = this.scala$collection$mutable$ArrayDeque$$start();
        return this.scala$collection$mutable$ArrayDeque$$end() - scala$collection$mutable$ArrayDeque$$end_$minus_idx & this.array().length - 1;
    }

    @Override
    public boolean isEmpty() {
        return this.scala$collection$mutable$ArrayDeque$$start() == this.scala$collection$mutable$ArrayDeque$$end();
    }

    @Override
    public ArrayDeque<A> klone() {
        return new ArrayDeque<A>((Object[])this.array().clone(), this.scala$collection$mutable$ArrayDeque$$start(), this.scala$collection$mutable$ArrayDeque$$end());
    }

    @Override
    public SeqFactory<ArrayDeque> iterableFactory() {
        return ArrayDeque$.MODULE$;
    }

    @Override
    public void clear() {
        while (this.nonEmpty()) {
            boolean removeHeadAssumingNonEmpty_resizeInternalRepr = false;
            Object cfr_ignored_0 = this.array()[this.scala$collection$mutable$ArrayDeque$$start()];
            this.array()[this.scala$collection$mutable$ArrayDeque$$start()] = null;
            this.scala$collection$mutable$ArrayDeque$$start_$eq(this.start_$plus(1));
            if (!removeHeadAssumingNonEmpty_resizeInternalRepr) continue;
            this.scala$collection$mutable$ArrayDeque$$resize(this.length());
        }
    }

    public ArrayDeque<A> clearAndShrink(int size) {
        this.reset(ArrayDeque$.MODULE$.alloc(size), 0, 0);
        return this;
    }

    public int clearAndShrink$default$1() {
        return 16;
    }

    @Override
    public ArrayDeque<A> ofArray(Object[] array, int end) {
        return new ArrayDeque<A>(array, 0, end);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> int copyToArray(Object dest, int destStart, int len) {
        void elemsToCopyToArray_destLen;
        int n = Array.getLength(dest);
        int elemsToCopyToArray_srcLen = this.length();
        int copied = Math.max(Math.min(Math.min(len, elemsToCopyToArray_srcLen), (int)(elemsToCopyToArray_destLen - destStart)), 0);
        if (copied > 0) {
            this.copySliceToArray(0, dest, destStart, len);
        }
        return copied;
    }

    @Override
    public <B> Object toArray(ClassTag<B> evidence$1) {
        return this.copySliceToArray(0, evidence$1.newArray(this.length()), 0, this.length());
    }

    public void trimToSize() {
        this.scala$collection$mutable$ArrayDeque$$resize(this.length());
    }

    @Override
    public int start_$plus(int idx) {
        return this.scala$collection$mutable$ArrayDeque$$start() + idx & this.array().length - 1;
    }

    public int scala$collection$mutable$ArrayDeque$$start_$minus(int idx) {
        return this.scala$collection$mutable$ArrayDeque$$start() - idx & this.array().length - 1;
    }

    public int scala$collection$mutable$ArrayDeque$$end_$plus(int idx) {
        return this.scala$collection$mutable$ArrayDeque$$end() + idx & this.array().length - 1;
    }

    public int scala$collection$mutable$ArrayDeque$$end_$minus(int idx) {
        return this.scala$collection$mutable$ArrayDeque$$end() - idx & this.array().length - 1;
    }

    public boolean scala$collection$mutable$ArrayDeque$$mustGrow(int len) {
        return len >= this.array().length;
    }

    private boolean shouldShrink(int len) {
        return this.array().length > 128 && this.array().length - len - (len >> 1) > len;
    }

    private boolean canShrink(int len) {
        return this.array().length > 16 && this.array().length - len > len;
    }

    private A _get(int idx) {
        return (A)this.array()[this.start_$plus(idx)];
    }

    private void _set(int idx, A elem) {
        this.array()[this.start_$plus((int)idx)] = elem;
    }

    public void scala$collection$mutable$ArrayDeque$$resize(int len) {
        if (len >= this.array().length || this.array().length > 16 && this.array().length - len > len) {
            int n = this.length();
            Object[] array2 = (Object[])this.copySliceToArray(0, ArrayDeque$.MODULE$.alloc(len), 0, n);
            this.reset(array2, 0, n);
            return;
        }
    }

    @Override
    public String stringPrefix() {
        return "ArrayDeque";
    }

    public static final /* synthetic */ String $anonfun$reset$1() {
        return "Array.length must be power of 2";
    }

    public static final /* synthetic */ String $anonfun$remove$1(int count$1) {
        return new StringBuilder(38).append("removing negative number of elements: ").append(count$1).toString();
    }

    public ArrayDeque(Object[] array, int start2, int end) {
        this.array = array;
        this.scala$collection$mutable$ArrayDeque$$start = start2;
        this.scala$collection$mutable$ArrayDeque$$end = end;
        this.reset(this.array(), this.scala$collection$mutable$ArrayDeque$$start(), this.scala$collection$mutable$ArrayDeque$$end());
    }

    public ArrayDeque(int initialSize) {
        this(ArrayDeque$.MODULE$.alloc(initialSize), 0, 0);
    }
}

