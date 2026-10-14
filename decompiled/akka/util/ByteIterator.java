/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.ByteIterator$ByteArrayIterator$;
import akka.util.ByteIterator$MultiByteArrayIterator$;
import akka.util.ByteString;
import akka.util.ByteString$;
import akka.util.ByteString$ByteString1$;
import akka.util.ByteString$ByteString1C$;
import akka.util.Collections$EmptyImmutableSeq$;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.NoSuchElementException;
import scala.$less$colon$less;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.BufferedIterator;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableFactory$;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.LinearSeq;
import scala.collection.SeqOps;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Set;
import scala.collection.immutable.Stream;
import scala.collection.immutable.Vector;
import scala.collection.mutable.Buffer;
import scala.collection.mutable.ListBuffer;
import scala.collection.mutable.StringBuilder;
import scala.math.Numeric;
import scala.math.Ordering;
import scala.package$;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BooleanRef;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ByteRef;
import scala.runtime.CharRef;
import scala.runtime.DoubleRef;
import scala.runtime.FloatRef;
import scala.runtime.IntRef;
import scala.runtime.LongRef;
import scala.runtime.Nothing$;
import scala.runtime.ObjectRef;
import scala.runtime.RichInt$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.ShortRef;

@ScalaSignature(bytes="\u0006\u0005\u0015ew\u0001CA\t\u0003'A\t!!\b\u0007\u0011\u0005\u0005\u00121\u0003E\u0001\u0003GAq!!\r\u0002\t\u0003\t\u0019dB\u0004\u00026\u0005A\t!a\u000e\u0007\u000f\u0005m\u0012\u0001#\u0001\u0002>!9\u0011\u0011\u0007\u0003\u0005\u0002\u0005}\u0002\"CA!\t\u0011E\u0011qCA\"\u0011%\t\t\u0005\u0002C\t\u0003/!\t\rC\u0005\u0005J\u0012\u0011\r\u0011\"\u0001\u0005L\"AAQ\u001a\u0003!\u0002\u0013\t)E\u0002\u0004\u0002<\u0005\u0001\u0011q\t\u0005\u000f\u0007OSA\u0011!A\u0003\u0006\u0003\u0007I\u0011BBU\u0011)\u0019YK\u0003BA\u0002\u0013%1Q\u0016\u0005\f\u0007gS!Q!A!B\u0013\u0011Y\u0010\u0003\b\u00046*!\t\u0011!B\u0003\u0002\u0004%I!a\u0019\t\u0015\r]&B!a\u0001\n\u0013\u0019I\fC\u0006\u0004>*\u0011)\u0011!Q!\n\u0005\u0015\u0004BDB`\u0015\u0011\u0005\tQ!BA\u0002\u0013%\u00111\r\u0005\u000b\u0007\u0003T!\u00111A\u0005\n\r\r\u0007bCBd\u0015\t\u0015\t\u0011)Q\u0005\u0003KBq!!\r\u000b\t\u0013\u0019I\rC\u0004\u0002b)!)!a\u0019\t\u000f\rm'\u0002\"\u0002\u0004^\"9\u00111\u000e\u0006\u0005\u0006\u00055\u0004bBA8\u0015\u0011\u0015\u0011\u0011\u000f\u0005\b\u0003gRA\u0011AA;\u0011\u001d\u0019\u0019O\u0003C#\u0003GBq!! \u000b\t\u000b\u001a)\u000fC\u0004\u0002\u001e*!)e!;\t\u000f\u0005%&\u0002\"\u0012\u0004l\"9\u00111\u0017\u0006\u0005F\rE\bbBAc\u0015\u0011\u00153Q\u001f\u0005\b\u00033TAQIB}\u0011\u001d\u0019iP\u0003C!\u0007\u007fDqa!@\u000b\t\u0003\"y\u0001C\u0004\u0004~*!)\u0005b\u0007\t\u000f\tU\"\u0002\"\u0012\u00038!9!1\u001f\u0006\u0005\u0002\u0011-\u0002b\u0002C>\u0015\u0011%AQ\u0010\u0005\b\u0007#QA\u0011\u0001C@\u0011\u001d\u0019IC\u0003C\u0001\t\u0017Cqa!\u0011\u000b\t\u0003!9\nC\u0004\u0004Z)!\t\u0001b)\t\u000f\rE$\u0002\"\u0001\u00050\"91\u0011\u0012\u0006\u0005\u0002\u0011m\u0006bBBL\u0015\u0011\u00051\u0011T\u0004\b\t\u001f\f\u0001\u0012\u0001Ci\r\u001d!\u0019.\u0001E\u0001\t+Dq!!\r0\t\u0003!9\u000eC\u0005\u0005Z>\u0012\r\u0011\"\u0005\u0005\\\"AA1]\u0018!\u0002\u0013!i\u000eC\u0005\u0005J>\u0012\r\u0011\"\u0001\u0005f\"AAQZ\u0018!\u0002\u0013!9\u000fC\u0005\u0002B=\"\t\"a\u0006\u0006V\u001a1A1[\u0001\u0001\tSDa\u0002b;7\t\u0003\u0005)Q!a\u0001\n\u0013!i\u000f\u0003\b\u0005vZ\"\t\u0011!B\u0003\u0002\u0004%I\u0001b>\t\u0017\u0011mhG!B\u0001B\u0003&Aq\u001e\u0005\b\u0003c1D\u0011\u0002C\u007f\u0011\u001d)\u0019A\u000eC\u0005\u000b\u000bAa\"\"\u00037\t\u0003\u0005)\u0011!A\u0001\n\u0013!Y\rC\u0004\u0006\u000eY\"I!!\u001e\t\u000f\u0005Md\u0007\"\u0001\u0002v!911\u001c\u001c\u0005\u0006\ru\u0007bBA6m\u0011\u0015\u0011Q\u000e\u0005\b\u0003_2DQAA9\u0011\u001d\t\tG\u000eC#\u0003GBqaa97\t\u000b\n\u0019\u0007C\u0005\u0006\u0018Y\"\t!a\u0006\u0006\u001a!9\u0011Q\u0010\u001c\u0005F\u0015u\u0001bBAOm\u0011\u0015S\u0011\u0005\u0005\b\u0003S3DQIC\u0012\u0011\u001d\t\u0019L\u000eC#\u000bOAq!!27\t\u000b*Y\u0003C\u0004\u0002ZZ\")%b\f\t\u000f\ruh\u0007\"\u0012\u0006>!9!\u0011\t\u001c\u0005B\u00155\u0003b\u0002B\u001bm\u0011\u0015#q\u0007\u0005\b\u000b72DQCC/\u0011\u001d\u0011\u0019P\u000eC\u0001\u000b\u001bCqa!\u00057\t\u0003))\nC\u0004\u0004*Y\"\t!\")\t\u000f\r\u0005c\u0007\"\u0001\u0006.\"91\u0011\f\u001c\u0005\u0002\u0015e\u0006bBB9m\u0011\u0005QQ\u0019\u0005\b\u0007\u00133D\u0011ICi\u0011\u001d\u00199J\u000eC\u0001\u000733\u0001\"!\t\u0002\u0014\u0005\u0005\u00111\n\u0005\b\u0003c9F\u0011AA0\u0011\u001d\t\tg\u0016D\u0001\u0003GBq!a\u001bX\r\u0003\ti\u0007C\u0004\u0002p]3\t!!\u001d\t\u000f\u0005MtK\"\u0005\u0002v!9\u0011QP,\u0005\u0002\u0005}\u0004bBAO/\u0012\u0005\u0013q\f\u0005\b\u0003?;F\u0011IAQ\u0011\u001d\tIk\u0016C!\u0003WCq!a-X\t\u0003\n)\fC\u0004\u0002:^#\t%a/\t\u000f\u0005\u0015w\u000b\"\u0011\u0002H\"9\u0011\u0011\\,\u0005B\u0005m\u0007bBAp/\u0012\u0005\u0013\u0011\u001d\u0005\b\u0003K<F\u0011IAt\u0011%\tioVI\u0001\n\u0003\ty\u000fC\u0004\u0003\u0006]#\tAa\u0002\t\u000f\t\u0015q\u000b\"\u0001\u0003\u000e!9!QA,\u0005B\tM\u0001b\u0002B\u0003/\u0012\u0005#\u0011\u0006\u0005\b\u0005k9f\u0011\u0001B\u001c\u0011\u001d\u0011yd\u0016C!\u0005oAqA!\u0011X\t\u0003\u0012\u0019\u0005C\u0004\u0003d]#\tE!\u001a\t\u000f\t}t\u000b\"\u0011\u0003\u0002\"9!qT,\u0005\u0002\u00055\u0004b\u0002BQ/\u0012\u0005!1\u0015\u0005\b\u0005\u007f;F\u0011\u0001Ba\u0011\u001d\u0011)m\u0016C\u0001\u0005\u000fDqA!5X\t\u0003\u0011\u0019\u000eC\u0004\u0003\\^#\tA!8\t\u000f\t\u001dx\u000b\"\u0001\u0003j\"9!1_,\u0005\u0002\tU\bb\u0002Bz/\u001a\u0005!Q \u0005\b\u0005g<F\u0011AB\u0004\u0011\u001d\u0019Ya\u0016C\u0001\u0007\u001bAqa!\u0005X\t\u0003\u0019\u0019\u0002C\u0004\u0004\u0012]3\ta!\b\t\u000f\r%r\u000b\"\u0001\u0004,!91\u0011F,\u0007\u0002\rU\u0002bBB!/\u0012\u000511\t\u0005\b\u0007\u0003:f\u0011AB'\u0011\u001d\u0019If\u0016C\u0001\u00077Bqa!\u0017X\r\u0003\u0019)\u0007C\u0004\u0004r]#\taa\u001d\t\u000f\rEtK\"\u0001\u0004~!91\u0011R,\u0007\u0002\r-\u0005bBBL/\u001a\u00051\u0011T\u0001\r\u0005f$X-\u0013;fe\u0006$xN\u001d\u0006\u0005\u0003+\t9\"\u0001\u0003vi&d'BAA\r\u0003\u0011\t7n[1\u0004\u0001A\u0019\u0011qD\u0001\u000e\u0005\u0005M!\u0001\u0004\"zi\u0016LE/\u001a:bi>\u00148cA\u0001\u0002&A!\u0011qEA\u0017\u001b\t\tIC\u0003\u0002\u0002,\u0005)1oY1mC&!\u0011qFA\u0015\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\"!!\b\u0002#\tKH/Z!se\u0006L\u0018\n^3sCR|'\u000fE\u0002\u0002:\u0011i\u0011!\u0001\u0002\u0012\u0005f$X-\u0011:sCfLE/\u001a:bi>\u00148c\u0001\u0003\u0002&Q\u0011\u0011qG\u0001\u0006CB\u0004H.\u001f\u000b\u0005\u0003\u000b\"y\fE\u0002\u0002:)\u00192ACA%!\r\tybV\n\u0006/\u0006\u0015\u0012Q\n\t\u0007\u0003\u001f\n)&!\u0017\u000e\u0005\u0005E#\u0002BA*\u0003S\t!bY8mY\u0016\u001cG/[8o\u0013\u0011\t9&!\u0015\u0003!\t+hMZ3sK\u0012LE/\u001a:bi>\u0014\b\u0003BA\u0014\u00037JA!!\u0018\u0002*\t!!)\u001f;f)\t\tI%A\u0002mK:,\"!!\u001a\u0011\t\u0005\u001d\u0012qM\u0005\u0005\u0003S\nICA\u0002J]R\fA\u0001[3bIV\u0011\u0011\u0011L\u0001\u0005]\u0016DH\u000f\u0006\u0002\u0002Z\u0005)1\r\\3beR\u0011\u0011q\u000f\t\u0005\u0003O\tI(\u0003\u0003\u0002|\u0005%\"\u0001B+oSR\f!\u0002\n9mkN$\u0003\u000f\\;t)\u0011\tI%!!\t\u000f\u0005\rU\f1\u0001\u0002\u0006\u0006!A\u000f[1u!\u0019\t9)a&\u0002Z9!\u0011\u0011RAJ\u001d\u0011\tY)!%\u000e\u0005\u00055%\u0002BAH\u00037\ta\u0001\u0010:p_Rt\u0014BAA\u0016\u0013\u0011\t)*!\u000b\u0002\u000fA\f7m[1hK&!\u0011\u0011TAN\u00051IE/\u001a:bE2,wJ\\2f\u0015\u0011\t)*!\u000b\u0002\u000b\rdwN\\3\u0002\u0013\u0011,\b\u000f\\5dCR,WCAAR!!\t9#!*\u0002J\u0005%\u0013\u0002BAT\u0003S\u0011a\u0001V;qY\u0016\u0014\u0014\u0001\u0002;bW\u0016$B!!,\u000206\tq\u000bC\u0004\u00022\u0002\u0004\r!!\u001a\u0002\u00039\fA\u0001\u001a:paR!\u0011QVA\\\u0011\u001d\t\t,\u0019a\u0001\u0003K\nQa\u001d7jG\u0016$b!!,\u0002>\u0006\u0005\u0007bBA`E\u0002\u0007\u0011QM\u0001\u0005MJ|W\u000eC\u0004\u0002D\n\u0004\r!!\u001a\u0002\u000bUtG/\u001b7\u0002\u0013Q\f7.Z,iS2,G\u0003BAW\u0003\u0013Dq!a3d\u0001\u0004\ti-A\u0001q!!\t9#a4\u0002Z\u0005M\u0017\u0002BAi\u0003S\u0011\u0011BR;oGRLwN\\\u0019\u0011\t\u0005\u001d\u0012Q[\u0005\u0005\u0003/\fICA\u0004C_>dW-\u00198\u0002\u0013\u0011\u0014x\u000e],iS2,G\u0003BAW\u0003;Dq!a3e\u0001\u0004\ti-\u0001\u0003ta\u0006tG\u0003BAR\u0003GDq!a3f\u0001\u0004\ti-\u0001\u0006j]\u0012,\u0007p\u00165fe\u0016$b!!\u001a\u0002j\u0006-\bbBAfM\u0002\u0007\u0011Q\u001a\u0005\n\u0003\u007f3\u0007\u0013!a\u0001\u0003K\nA#\u001b8eKb<\u0006.\u001a:fI\u0011,g-Y;mi\u0012\u0012TCAAyU\u0011\t)'a=,\u0005\u0005U\b\u0003BA|\u0005\u0003i!!!?\u000b\t\u0005m\u0018Q`\u0001\nk:\u001c\u0007.Z2lK\u0012TA!a@\u0002*\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\t\r\u0011\u0011 \u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017aB5oI\u0016DxJ\u001a\u000b\u0005\u0003K\u0012I\u0001C\u0004\u0003\f!\u0004\r!!\u0017\u0002\t\u0015dW-\u001c\u000b\u0007\u0003K\u0012yA!\u0005\t\u000f\t-\u0011\u000e1\u0001\u0002Z!9\u0011qX5A\u0002\u0005\u0015T\u0003\u0002B\u000b\u0005;!B!!\u001a\u0003\u0018!9!1\u00026A\u0002\te\u0001\u0003\u0002B\u000e\u0005;a\u0001\u0001B\u0004\u0003 )\u0014\rA!\t\u0003\u0003\t\u000bB!!\u0017\u0003$A!\u0011q\u0005B\u0013\u0013\u0011\u00119#!\u000b\u0003\u0007\u0005s\u00170\u0006\u0003\u0003,\tEBCBA3\u0005[\u0011\u0019\u0004C\u0004\u0003\f-\u0004\rAa\f\u0011\t\tm!\u0011\u0007\u0003\b\u0005?Y'\u0019\u0001B\u0011\u0011\u001d\tyl\u001ba\u0001\u0003K\nA\u0002^8CsR,7\u000b\u001e:j]\u001e,\"A!\u000f\u0011\t\u0005}!1H\u0005\u0005\u0005{\t\u0019B\u0001\u0006CsR,7\u000b\u001e:j]\u001e\fQ\u0001^8TKF\fqAZ8sK\u0006\u001c\u0007.\u0006\u0003\u0003F\t=C\u0003BA<\u0005\u000fBqA!\u0013o\u0001\u0004\u0011Y%A\u0001g!!\t9#a4\u0002Z\t5\u0003\u0003\u0002B\u000e\u0005\u001f\"1B!\u0015oA\u0003\u0005\tQ1\u0001\u0003T\t\tQ+\u0005\u0003\u0003V\t\r\u0002\u0003BA\u0014\u0005/JAA!\u0017\u0002*\t9aj\u001c;iS:<\u0007\u0006\u0002B(\u0005;\u0002B!a\n\u0003`%!!\u0011MA\u0015\u0005-\u0019\b/Z2jC2L'0\u001a3\u0002\u0011\u0019|G\u000e\u001a'fMR,BAa\u001a\u0003nQ!!\u0011\u000eB>)\u0011\u0011YG!\u001d\u0011\t\tm!Q\u000e\u0003\f\u0005?y\u0007\u0015!A\u0001\u0006\u0004\u0011\u0019\u0006\u000b\u0003\u0003n\tu\u0003b\u0002B:_\u0002\u0007!QO\u0001\u0003_B\u0004\"\"a\n\u0003x\t-\u0014\u0011\fB6\u0013\u0011\u0011I(!\u000b\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004b\u0002B?_\u0002\u0007!1N\u0001\u0002u\u00069Ao\\!se\u0006LX\u0003\u0002BB\u0005\u001b#BA!\"\u0003\u0010B1\u0011q\u0005BD\u0005\u0017KAA!#\u0002*\t)\u0011I\u001d:bsB!!1\u0004BG\t\u001d\u0011y\u0002\u001db\u0001\u0005CAqA!%q\u0001\b\u0011\u0019*\u0001\u0003be\u001e\u0004\u0004C\u0002BK\u00057\u0013Y)\u0004\u0002\u0003\u0018*!!\u0011TA\u0015\u0003\u001d\u0011XM\u001a7fGRLAA!(\u0003\u0018\nA1\t\\1tgR\u000bw-A\u0004hKR\u0014\u0015\u0010^3\u0002\u0011\u001d,Go\u00155peR$BA!*\u0003,B!\u0011q\u0005BT\u0013\u0011\u0011I+!\u000b\u0003\u000bMCwN\u001d;\t\u000f\t5&\u000fq\u0001\u00030\u0006I!-\u001f;f\u001fJ$WM\u001d\t\u0005\u0005c\u0013Y,\u0004\u0002\u00034*!!Q\u0017B\\\u0003\rq\u0017n\u001c\u0006\u0003\u0005s\u000bAA[1wC&!!Q\u0018BZ\u0005%\u0011\u0015\u0010^3Pe\u0012,'/\u0001\u0004hKRLe\u000e\u001e\u000b\u0005\u0003K\u0012\u0019\rC\u0004\u0003.N\u0004\u001dAa,\u0002\u000f\u001d,G\u000fT8oOR!!\u0011\u001aBh!\u0011\t9Ca3\n\t\t5\u0017\u0011\u0006\u0002\u0005\u0019>tw\rC\u0004\u0003.R\u0004\u001dAa,\u0002\u0017\u001d,G\u000fT8oOB\u000b'\u000f\u001e\u000b\u0005\u0005+\u0014I\u000e\u0006\u0003\u0003J\n]\u0007b\u0002BWk\u0002\u000f!q\u0016\u0005\b\u0003c+\b\u0019AA3\u0003!9W\r\u001e$m_\u0006$H\u0003\u0002Bp\u0005K\u0004B!a\n\u0003b&!!1]A\u0015\u0005\u00151En\\1u\u0011\u001d\u0011iK\u001ea\u0002\u0005_\u000b\u0011bZ3u\t>,(\r\\3\u0015\t\t-(\u0011\u001f\t\u0005\u0003O\u0011i/\u0003\u0003\u0003p\u0006%\"A\u0002#pk\ndW\rC\u0004\u0003.^\u0004\u001dAa,\u0002\u0011\u001d,GOQ=uKN$B!!,\u0003x\"9!\u0011 =A\u0002\tm\u0018A\u0001=t!\u0019\t9Ca\"\u0002ZQA\u0011Q\u0016B\u0000\u0007\u0003\u0019)\u0001C\u0004\u0003zf\u0004\rAa?\t\u000f\r\r\u0011\u00101\u0001\u0002f\u00051qN\u001a4tKRDq!!-z\u0001\u0004\t)\u0007\u0006\u0003\u0003|\u000e%\u0001bBAYu\u0002\u0007\u0011QM\u0001\u000eO\u0016$()\u001f;f'R\u0014\u0018N\\4\u0015\t\te2q\u0002\u0005\b\u0003c[\b\u0019AA3\u0003%9W\r^*i_J$8\u000f\u0006\u0003\u0004\u0016\reA\u0003BAW\u0007/AqA!,}\u0001\b\u0011y\u000bC\u0004\u0003zr\u0004\raa\u0007\u0011\r\u0005\u001d\"q\u0011BS)!\u0019yba\t\u0004&\r\u001dB\u0003BAW\u0007CAqA!,~\u0001\b\u0011y\u000bC\u0004\u0003zv\u0004\raa\u0007\t\u000f\r\rQ\u00101\u0001\u0002f!9\u0011\u0011W?A\u0002\u0005\u0015\u0014aB4fi&sGo\u001d\u000b\u0005\u0007[\u0019\t\u0004\u0006\u0003\u0002.\u000e=\u0002b\u0002BW}\u0002\u000f!q\u0016\u0005\b\u0005st\b\u0019AB\u001a!\u0019\t9Ca\"\u0002fQA1qGB\u001e\u0007{\u0019y\u0004\u0006\u0003\u0002.\u000ee\u0002b\u0002BW\u007f\u0002\u000f!q\u0016\u0005\b\u0005s|\b\u0019AB\u001a\u0011\u001d\u0019\u0019a a\u0001\u0003KBq!!-\u0000\u0001\u0004\t)'\u0001\u0005hKRduN\\4t)\u0011\u0019)e!\u0013\u0015\t\u000556q\t\u0005\t\u0005[\u000b\t\u0001q\u0001\u00030\"A!\u0011`A\u0001\u0001\u0004\u0019Y\u0005\u0005\u0004\u0002(\t\u001d%\u0011\u001a\u000b\t\u0007\u001f\u001a\u0019f!\u0016\u0004XQ!\u0011QVB)\u0011!\u0011i+a\u0001A\u0004\t=\u0006\u0002\u0003B}\u0003\u0007\u0001\raa\u0013\t\u0011\r\r\u00111\u0001a\u0001\u0003KB\u0001\"!-\u0002\u0004\u0001\u0007\u0011QM\u0001\nO\u0016$h\t\\8biN$Ba!\u0018\u0004bQ!\u0011QVB0\u0011!\u0011i+!\u0002A\u0004\t=\u0006\u0002\u0003B}\u0003\u000b\u0001\raa\u0019\u0011\r\u0005\u001d\"q\u0011Bp)!\u00199ga\u001b\u0004n\r=D\u0003BAW\u0007SB\u0001B!,\u0002\b\u0001\u000f!q\u0016\u0005\t\u0005s\f9\u00011\u0001\u0004d!A11AA\u0004\u0001\u0004\t)\u0007\u0003\u0005\u00022\u0006\u001d\u0001\u0019AA3\u0003)9W\r\u001e#pk\ndWm\u001d\u000b\u0005\u0007k\u001aI\b\u0006\u0003\u0002.\u000e]\u0004\u0002\u0003BW\u0003\u0013\u0001\u001dAa,\t\u0011\te\u0018\u0011\u0002a\u0001\u0007w\u0002b!a\n\u0003\b\n-H\u0003CB@\u0007\u0007\u001b)ia\"\u0015\t\u000556\u0011\u0011\u0005\t\u0005[\u000bY\u0001q\u0001\u00030\"A!\u0011`A\u0006\u0001\u0004\u0019Y\b\u0003\u0005\u0004\u0004\u0005-\u0001\u0019AA3\u0011!\t\t,a\u0003A\u0002\u0005\u0015\u0014\u0001D2paf$vNQ;gM\u0016\u0014H\u0003BA3\u0007\u001bC\u0001ba$\u0002\u000e\u0001\u00071\u0011S\u0001\u0007EV4g-\u001a:\u0011\t\tE61S\u0005\u0005\u0007+\u0013\u0019L\u0001\u0006CsR,')\u001e4gKJ\fQ\"Y:J]B,Ho\u0015;sK\u0006lWCABN!\u0011\u0019ija)\u000e\u0005\r}%\u0002BBQ\u0005o\u000b!![8\n\t\r\u00156q\u0014\u0002\f\u0013:\u0004X\u000f^*ue\u0016\fW.A\u0018bW.\fG%\u001e;jY\u0012\u0012\u0015\u0010^3Ji\u0016\u0014\u0018\r^8sI\tKH/Z!se\u0006L\u0018\n^3sCR|'\u000f\n\u0013beJ\f\u00170\u0006\u0002\u0003|\u0006I\u0011M\u001d:bs~#S-\u001d\u000b\u0005\u0003o\u001ay\u000bC\u0005\u000422\t\t\u00111\u0001\u0003|\u0006\u0019\u0001\u0010J\u0019\u0002a\u0005\\7.\u0019\u0013vi&dGEQ=uK&#XM]1u_J$#)\u001f;f\u0003J\u0014\u0018-_%uKJ\fGo\u001c:%I\u0005\u0014(/Y=!\u00039\n7n[1%kRLG\u000e\n\"zi\u0016LE/\u001a:bi>\u0014HEQ=uK\u0006\u0013(/Y=Ji\u0016\u0014\u0018\r^8sI\u00112'o\\7\u0002\u0011\u0019\u0014x.\\0%KF$B!a\u001e\u0004<\"I1\u0011W\b\u0002\u0002\u0003\u0007\u0011QM\u00010C.\\\u0017\rJ;uS2$#)\u001f;f\u0013R,'/\u0019;pe\u0012\u0012\u0015\u0010^3BeJ\f\u00170\u0013;fe\u0006$xN\u001d\u0013%MJ|W\u000eI\u00010C.\\\u0017\rJ;uS2$#)\u001f;f\u0013R,'/\u0019;pe\u0012\u0012\u0015\u0010^3BeJ\f\u00170\u0013;fe\u0006$xN\u001d\u0013%k:$\u0018\u000e\\\u0001\nk:$\u0018\u000e\\0%KF$B!a\u001e\u0004F\"I1\u0011\u0017\n\u0002\u0002\u0003\u0007\u0011QM\u00011C.\\\u0017\rJ;uS2$#)\u001f;f\u0013R,'/\u0019;pe\u0012\u0012\u0015\u0010^3BeJ\f\u00170\u0013;fe\u0006$xN\u001d\u0013%k:$\u0018\u000e\u001c\u0011\u0015\u0011\u0005\u001531ZBh\u0007#Dqa!4\u0015\u0001\u0004\u0011Y0A\u0003beJ\f\u0017\u0010C\u0004\u0002@R\u0001\r!!\u001a\t\u000f\u0005\rG\u00031\u0001\u0002f!\u001aQc!6\u0011\t\u0005\u001d2q[\u0005\u0005\u00073\fIC\u0001\u0004j]2Lg.Z\u0001\bQ\u0006\u001ch*\u001a=u+\t\t\u0019\u000eK\u0002\u0017\u0007+D3aFBk\u0003\u0011\u0019\u0018N_3\u0015\t\u0005%3q\u001d\u0005\b\u0003\u0007[\u0002\u0019AAC)\t\t)\u0005\u0006\u0003\u0004n\u000e=X\"\u0001\u0006\t\u000f\u0005EV\u00041\u0001\u0002fQ!1Q^Bz\u0011\u001d\t\tL\ba\u0001\u0003K\"Ba!<\u0004x\"9\u00111Z\u0010A\u0002\u00055G\u0003BBw\u0007wDq!a3!\u0001\u0004\ti-A\u0006d_BLHk\\!se\u0006LX\u0003\u0002C\u0001\t\u0013!b!!\u001a\u0005\u0004\u0011-\u0001b\u0002B}C\u0001\u0007AQ\u0001\t\u0007\u0003O\u00119\tb\u0002\u0011\t\tmA\u0011\u0002\u0003\b\u0005?\t#\u0019\u0001B\u0011\u0011\u001d!i!\ta\u0001\u0003K\nQa\u001d;beR,B\u0001\"\u0005\u0005\u001aQ!\u0011Q\rC\n\u0011\u001d\u0011IP\ta\u0001\t+\u0001b!a\n\u0003\b\u0012]\u0001\u0003\u0002B\u000e\t3!qAa\b#\u0005\u0004\u0011\t#\u0006\u0003\u0005\u001e\u0011\u0015B\u0003CA3\t?!9\u0003\"\u000b\t\u000f\te8\u00051\u0001\u0005\"A1\u0011q\u0005BD\tG\u0001BAa\u0007\u0005&\u00119!qD\u0012C\u0002\t\u0005\u0002b\u0002C\u0007G\u0001\u0007\u0011Q\r\u0005\b\u0003C\u001a\u0003\u0019AA3)!\u0019i\u000f\"\f\u00050\u0011E\u0002b\u0002B}K\u0001\u0007!1 \u0005\b\u0007\u0007)\u0003\u0019AA3\u0011\u001d\t\t,\na\u0001\u0003KBS!\nC\u001b\t\u0003\u0002b!a\n\u00058\u0011m\u0012\u0002\u0002C\u001d\u0003S\u0011a\u0001\u001e5s_^\u001c\b\u0003BAD\t{IA\u0001b\u0010\u0002\u001c\n1bj\\*vG\",E.Z7f]R,\u0005pY3qi&|g.M\u0004\u001f\t\u0007\"\u0019\u0006\"\u001f\u0011\t\u0011\u0015CQ\n\b\u0005\t\u000f\"I\u0005\u0005\u0003\u0002\f\u0006%\u0012\u0002\u0002C&\u0003S\ta\u0001\u0015:fI\u00164\u0017\u0002\u0002C(\t#\u0012aa\u0015;sS:<'\u0002\u0002C&\u0003S\t\u0014b\tC+\t;\"y\u0007b\u0018\u0016\t\u0011]C\u0011L\u000b\u0003\t\u0007\"q\u0001b\u0017\u0001\u0005\u0004!)GA\u0001U\u0013\u0011!y\u0006\"\u0019\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132\u0015\u0011!\u0019'!\u000b\u0002\rQD'o\\<t#\u0011\u0011)\u0006b\u001a\u0011\t\u0011%D1\u000e\b\u0005\u0003O\t\u0019*\u0003\u0003\u0005n\u0005m%!\u0003+ie><\u0018M\u00197fc%\u0019C\u0011\u000fC:\tk\"\u0019G\u0004\u0003\u0002(\u0011M\u0014\u0002\u0002C2\u0003S\ttAIA\u0014\u0003S!9HA\u0003tG\u0006d\u0017-M\u0002'\tw\t\u0011c\u001e:baB,GMQ=uK\n+hMZ3s+\t\u0019\t\n\u0006\u0005\u0005\u0002\u0012\u0015Eq\u0011CE)\u0011\u0019i\u000fb!\t\u000f\t5v\u0005q\u0001\u00030\"9!\u0011`\u0014A\u0002\rm\u0001bBB\u0002O\u0001\u0007\u0011Q\r\u0005\b\u0003c;\u0003\u0019AA3)!!i\t\"%\u0005\u0014\u0012UE\u0003BBw\t\u001fCqA!,)\u0001\b\u0011y\u000bC\u0004\u0003z\"\u0002\raa\r\t\u000f\r\r\u0001\u00061\u0001\u0002f!9\u0011\u0011\u0017\u0015A\u0002\u0005\u0015D\u0003\u0003CM\t;#y\n\")\u0015\t\r5H1\u0014\u0005\b\u0005[K\u00039\u0001BX\u0011\u001d\u0011I0\u000ba\u0001\u0007\u0017Bqaa\u0001*\u0001\u0004\t)\u0007C\u0004\u00022&\u0002\r!!\u001a\u0015\u0011\u0011\u0015F\u0011\u0016CV\t[#Ba!<\u0005(\"9!Q\u0016\u0016A\u0004\t=\u0006b\u0002B}U\u0001\u000711\r\u0005\b\u0007\u0007Q\u0003\u0019AA3\u0011\u001d\t\tL\u000ba\u0001\u0003K\"\u0002\u0002\"-\u00056\u0012]F\u0011\u0018\u000b\u0005\u0007[$\u0019\fC\u0004\u0003..\u0002\u001dAa,\t\u000f\te8\u00061\u0001\u0004|!911A\u0016A\u0002\u0005\u0015\u0004bBAYW\u0001\u0007\u0011Q\r\u000b\u0005\u0003K\"i\fC\u0004\u0004\u00102\u0002\ra!%\t\u000f\r5g\u00011\u0001\u0003|RA\u0011Q\tCb\t\u000b$9\rC\u0004\u0004N\u001e\u0001\rAa?\t\u000f\u0005}v\u00011\u0001\u0002f!9\u00111Y\u0004A\u0002\u0005\u0015\u0014!B3naRLXCAA#\u0003\u0019)W\u000e\u001d;zA\u00051R*\u001e7uS\nKH/Z!se\u0006L\u0018\n^3sCR|'\u000fE\u0002\u0002:=\u0012a#T;mi&\u0014\u0015\u0010^3BeJ\f\u00170\u0013;fe\u0006$xN]\n\u0004_\u0005\u0015BC\u0001Ci\u0003-\u0019G.Z1sK\u0012d\u0015n\u001d;\u0016\u0005\u0011u\u0007CBAD\t?\f)%\u0003\u0003\u0005b\u0006m%\u0001\u0002'jgR\fAb\u00197fCJ,G\rT5ti\u0002*\"\u0001b:\u0011\u0007\u0005ebgE\u00027\u0003\u0013\n\u0001(Y6lC\u0012*H/\u001b7%\u0005f$X-\u0013;fe\u0006$xN\u001d\u0013Nk2$\u0018NQ=uK\u0006\u0013(/Y=Ji\u0016\u0014\u0018\r^8sI\u0011JG/\u001a:bi>\u00148/\u0006\u0002\u0005pB1\u0011q\nCy\u0003\u000bJA\u0001b=\u0002R\tIA*\u001b8fCJ\u001cV-]\u0001=C.\\\u0017\rJ;uS2$#)\u001f;f\u0013R,'/\u0019;pe\u0012jU\u000f\u001c;j\u0005f$X-\u0011:sCfLE/\u001a:bi>\u0014H\u0005J5uKJ\fGo\u001c:t?\u0012*\u0017\u000f\u0006\u0003\u0002x\u0011e\b\"CBYq\u0005\u0005\t\u0019\u0001Cx\u0003e\n7n[1%kRLG\u000e\n\"zi\u0016LE/\u001a:bi>\u0014H%T;mi&\u0014\u0015\u0010^3BeJ\f\u00170\u0013;fe\u0006$xN\u001d\u0013%SR,'/\u0019;peN\u0004C\u0003\u0002Ct\t\u007fDq!\"\u0001;\u0001\u0004!y/A\u0005ji\u0016\u0014\u0018\r^8sg\u0006Ian\u001c:nC2L'0\u001a\u000b\u0003\u000b\u000fi\u0011AN\u00017C.\\\u0017\rJ;uS2$#)\u001f;f\u0013R,'/\u0019;pe\u0012jU\u000f\u001c;j\u0005f$X-\u0011:sCfLE/\u001a:bi>\u0014H\u0005J2veJ,g\u000e\u001e\u0015\u0004y\rU\u0017a\u00033s_B\u001cUO\u001d:f]RD3!PBkQ\rq4Q\u001b\u0015\u0004\u007f\rU\u0007f\u0001!\u0004V\u0006\u0001B\u0005\u001d7vg\u0012\u0002H.^:%G>dwN\u001c\u000b\u0005\u000b\u000f)Y\u0002C\u0004\u0002\u0004\u0012\u0003\r!!\u0012\u0015\t\u0005%Sq\u0004\u0005\b\u0003\u0007+\u0005\u0019AAC)\t!9\u000f\u0006\u0003\u0006\b\u0015\u0015\u0002bBAY\u000f\u0002\u0007\u0011Q\r\u000b\u0005\u000b\u000f)I\u0003C\u0004\u00022\"\u0003\r!!\u001a\u0015\t\u0015\u001dQQ\u0006\u0005\b\u0003\u0017L\u0005\u0019AAg)\u0011)9!\"\r\t\u000f\u0005-'\n1\u0001\u0002N\"\u001a!*\"\u000e\u0011\t\u0015]R\u0011H\u0007\u0003\u0003{LA!b\u000f\u0002~\n9A/Y5me\u0016\u001cW\u0003BC \u000b\u000f\"\u0002\"!\u001a\u0006B\u0015%S1\n\u0005\b\u0005s\\\u0005\u0019AC\"!\u0019\t9Ca\"\u0006FA!!1DC$\t\u001d\u0011yb\u0013b\u0001\u0005CAq\u0001\"\u0004L\u0001\u0004\t)\u0007C\u0004\u0002b-\u0003\r!!\u001a\u0016\t\u0015=Sq\u000b\u000b\u0005\u0003o*\t\u0006C\u0004\u0003J1\u0003\r!b\u0015\u0011\u0011\u0005\u001d\u0012qZA-\u000b+\u0002BAa\u0007\u0006X\u0011Y!\u0011\u000b'!\u0002\u0003\u0005)\u0019\u0001B*Q\u0011)9F!\u0018\u0002\u0015\u001d,G\u000fV8BeJ\f\u00170\u0006\u0003\u0006`\u0015MDCCC1\u000b\u0003+\u0019)\"\"\u0006\bR!Q1MC<)\u0011)9!\"\u001a\t\u000f\u0015\u001dd\n1\u0001\u0006j\u00059q-\u001a;Nk2$\b\u0003DA\u0014\u000bW*y'!\u001a\u0002f\u0005]\u0014\u0002BC7\u0003S\u0011\u0011BR;oGRLwN\\\u001a\u0011\r\u0005\u001d\"qQC9!\u0011\u0011Y\"b\u001d\u0005\u000f\u0015UdJ1\u0001\u0003T\t\t\u0011\t\u0003\u0005\u0006z9#\t\u0019AC>\u0003%9W\r^*j]\u001edW\r\u0005\u0004\u0002(\u0015uT\u0011O\u0005\u0005\u000b\u007f\nIC\u0001\u0005=Eft\u0017-\\3?\u0011\u001d\u0011IP\u0014a\u0001\u000b_Bqaa\u0001O\u0001\u0004\t)\u0007C\u0004\u00022:\u0003\r!!\u001a\t\u000f\u0015%e\n1\u0001\u0002f\u0005AQ\r\\3n'&TX\rK\u0002O\u000bk!\u0002\"b\u0002\u0006\u0010\u0016EU1\u0013\u0005\b\u0005s|\u0005\u0019\u0001B~\u0011\u001d\u0019\u0019a\u0014a\u0001\u0003KBq!!-P\u0001\u0004\t)\u0007\u0006\u0005\u0006\u0018\u0016mUQTCP)\u0011)9!\"'\t\u000f\t5\u0006\u000bq\u0001\u00030\"9!\u0011 )A\u0002\rm\u0001bBB\u0002!\u0002\u0007\u0011Q\r\u0005\b\u0003c\u0003\u0006\u0019AA3)!)\u0019+b*\u0006*\u0016-F\u0003BC\u0004\u000bKCqA!,R\u0001\b\u0011y\u000bC\u0004\u0003zF\u0003\raa\r\t\u000f\r\r\u0011\u000b1\u0001\u0002f!9\u0011\u0011W)A\u0002\u0005\u0015D\u0003CCX\u000bg+),b.\u0015\t\u0015\u001dQ\u0011\u0017\u0005\b\u0005[\u0013\u00069\u0001BX\u0011\u001d\u0011IP\u0015a\u0001\u0007\u0017Bqaa\u0001S\u0001\u0004\t)\u0007C\u0004\u00022J\u0003\r!!\u001a\u0015\u0011\u0015mVqXCa\u000b\u0007$B!b\u0002\u0006>\"9!QV*A\u0004\t=\u0006b\u0002B}'\u0002\u000711\r\u0005\b\u0007\u0007\u0019\u0006\u0019AA3\u0011\u001d\t\tl\u0015a\u0001\u0003K\"\u0002\"b2\u0006L\u00165Wq\u001a\u000b\u0005\u000b\u000f)I\rC\u0004\u0003.R\u0003\u001dAa,\t\u000f\teH\u000b1\u0001\u0004|!911\u0001+A\u0002\u0005\u0015\u0004bBAY)\u0002\u0007\u0011Q\r\u000b\u0005\u0003K*\u0019\u000eC\u0004\u0004\u0010V\u0003\ra!%\u0015\t\u0011\u001dXq\u001b\u0005\b\u000b\u0003)\u0004\u0019\u0001Cx\u0001")
public abstract class ByteIterator
implements BufferedIterator<Object> {
    @Override
    public Option<Object> headOption() {
        return BufferedIterator.headOption$(this);
    }

    @Override
    public BufferedIterator<Object> buffered() {
        return BufferedIterator.buffered$(this);
    }

    @Override
    public final boolean hasDefiniteSize() {
        return Iterator.hasDefiniteSize$(this);
    }

    @Override
    public final Iterator<Object> iterator() {
        return Iterator.iterator$(this);
    }

    @Override
    public Option<Object> nextOption() {
        return Iterator.nextOption$(this);
    }

    @Override
    public boolean contains(Object elem) {
        return Iterator.contains$(this, elem);
    }

    @Override
    public <B> Iterator<B> padTo(int len, B elem) {
        return Iterator.padTo$(this, len, elem);
    }

    @Override
    public Tuple2<Iterator<Object>, Iterator<Object>> partition(Function1<Object, Object> p) {
        return Iterator.partition$(this, p);
    }

    @Override
    public <B> Iterator.GroupedIterator<B> grouped(int size) {
        return Iterator.grouped$(this, size);
    }

    @Override
    public <B> Iterator.GroupedIterator<B> sliding(int size, int step) {
        return Iterator.sliding$(this, size, step);
    }

    @Override
    public <B> int sliding$default$2() {
        return Iterator.sliding$default$2$(this);
    }

    @Override
    public <B> Iterator<B> scanLeft(B z, Function2<B, Object, B> op) {
        return Iterator.scanLeft$(this, z, op);
    }

    @Override
    public <B> Iterator<B> scanRight(B z, Function2<Object, B, B> op) {
        return Iterator.scanRight$(this, z, op);
    }

    @Override
    public final int length() {
        return Iterator.length$(this);
    }

    @Override
    public boolean isEmpty() {
        return Iterator.isEmpty$(this);
    }

    @Override
    public Iterator<Object> filter(Function1<Object, Object> p) {
        return Iterator.filter$(this, p);
    }

    @Override
    public Iterator<Object> filterNot(Function1<Object, Object> p) {
        return Iterator.filterNot$(this, p);
    }

    @Override
    public Iterator<Object> filterImpl(Function1<Object, Object> p, boolean isFlipped) {
        return Iterator.filterImpl$(this, p, isFlipped);
    }

    @Override
    public Iterator<Object> withFilter(Function1<Object, Object> p) {
        return Iterator.withFilter$(this, p);
    }

    @Override
    public <B> Iterator<B> collect(PartialFunction<Object, B> pf) {
        return Iterator.collect$(this, pf);
    }

    @Override
    public Iterator<Object> distinct() {
        return Iterator.distinct$(this);
    }

    @Override
    public <B> Iterator<Object> distinctBy(Function1<Object, B> f) {
        return Iterator.distinctBy$(this, f);
    }

    @Override
    public <B> Iterator<B> map(Function1<Object, B> f) {
        return Iterator.map$(this, f);
    }

    @Override
    public <B> Iterator<B> flatMap(Function1<Object, IterableOnce<B>> f) {
        return Iterator.flatMap$(this, f);
    }

    @Override
    public <B> Iterator<B> flatten(Function1<Object, IterableOnce<B>> ev) {
        return Iterator.flatten$(this, ev);
    }

    @Override
    public <B> Iterator<B> concat(Function0<IterableOnce<B>> xs) {
        return Iterator.concat$(this, xs);
    }

    @Override
    public final <B> Iterator<B> $plus$plus(Function0<IterableOnce<B>> xs) {
        return Iterator.$plus$plus$(this, xs);
    }

    @Override
    public Iterator<Object> sliceIterator(int from, int until) {
        return Iterator.sliceIterator$(this, from, until);
    }

    @Override
    public <B> Iterator<Tuple2<Object, B>> zip(IterableOnce<B> that) {
        return Iterator.zip$(this, that);
    }

    @Override
    public <A1, B> Iterator<Tuple2<A1, B>> zipAll(IterableOnce<B> that, A1 thisElem, B thatElem) {
        return Iterator.zipAll$(this, that, thisElem, thatElem);
    }

    @Override
    public Iterator<Tuple2<Object, Object>> zipWithIndex() {
        return Iterator.zipWithIndex$(this);
    }

    @Override
    public <B> boolean sameElements(IterableOnce<B> that) {
        return Iterator.sameElements$(this, that);
    }

    @Override
    public <B> Iterator<B> patch(int from, Iterator<B> patchElems, int replaced) {
        return Iterator.patch$(this, from, patchElems, replaced);
    }

    @Override
    public <U> Iterator<Object> tapEach(Function1<Object, U> f) {
        return Iterator.tapEach$(this, f);
    }

    @Override
    public String toString() {
        return Iterator.toString$(this);
    }

    @Override
    public Iterator<Object> seq() {
        return Iterator.seq$(this);
    }

    @Override
    public Tuple2<Iterator<Object>, Iterator<Object>> splitAt(int n) {
        return IterableOnceOps.splitAt$(this, n);
    }

    @Override
    public boolean isTraversableAgain() {
        return IterableOnceOps.isTraversableAgain$(this);
    }

    @Override
    public boolean forall(Function1<Object, Object> p) {
        return IterableOnceOps.forall$(this, p);
    }

    @Override
    public boolean exists(Function1<Object, Object> p) {
        return IterableOnceOps.exists$(this, p);
    }

    @Override
    public int count(Function1<Object, Object> p) {
        return IterableOnceOps.count$(this, p);
    }

    @Override
    public Option<Object> find(Function1<Object, Object> p) {
        return IterableOnceOps.find$(this, p);
    }

    @Override
    public <B> B foldRight(B z, Function2<Object, B, B> op) {
        return (B)IterableOnceOps.foldRight$(this, z, op);
    }

    @Override
    public final <B> B $div$colon(B z, Function2<B, Object, B> op) {
        return (B)IterableOnceOps.$div$colon$(this, z, op);
    }

    @Override
    public final <B> B $colon$bslash(B z, Function2<Object, B, B> op) {
        return (B)IterableOnceOps.$colon$bslash$(this, z, op);
    }

    @Override
    public <A1> A1 fold(A1 z, Function2<A1, A1, A1> op) {
        return (A1)IterableOnceOps.fold$(this, z, op);
    }

    @Override
    public <B> B reduce(Function2<B, B, B> op) {
        return (B)IterableOnceOps.reduce$(this, op);
    }

    @Override
    public <B> Option<B> reduceOption(Function2<B, B, B> op) {
        return IterableOnceOps.reduceOption$(this, op);
    }

    @Override
    public <B> B reduceLeft(Function2<B, Object, B> op) {
        return (B)IterableOnceOps.reduceLeft$(this, op);
    }

    @Override
    public <B> B reduceRight(Function2<Object, B, B> op) {
        return (B)IterableOnceOps.reduceRight$(this, op);
    }

    @Override
    public <B> Option<B> reduceLeftOption(Function2<B, Object, B> op) {
        return IterableOnceOps.reduceLeftOption$(this, op);
    }

    @Override
    public <B> Option<B> reduceRightOption(Function2<Object, B, B> op) {
        return IterableOnceOps.reduceRightOption$(this, op);
    }

    @Override
    public boolean nonEmpty() {
        return IterableOnceOps.nonEmpty$(this);
    }

    @Override
    public int size() {
        return IterableOnceOps.size$(this);
    }

    @Override
    public final <B> void copyToBuffer(Buffer<B> dest) {
        IterableOnceOps.copyToBuffer$(this, dest);
    }

    @Override
    public <B> int copyToArray(Object xs) {
        return IterableOnceOps.copyToArray$(this, xs);
    }

    @Override
    public <B> int copyToArray(Object xs, int start2) {
        return IterableOnceOps.copyToArray$(this, xs, start2);
    }

    @Override
    public <B> int copyToArray(Object xs, int start2, int len) {
        return IterableOnceOps.copyToArray$(this, xs, start2, len);
    }

    @Override
    public <B> B sum(Numeric<B> num) {
        return (B)IterableOnceOps.sum$(this, num);
    }

    @Override
    public <B> B product(Numeric<B> num) {
        return (B)IterableOnceOps.product$(this, num);
    }

    @Override
    public Object min(Ordering ord) {
        return IterableOnceOps.min$(this, ord);
    }

    @Override
    public <B> Option<Object> minOption(Ordering<B> ord) {
        return IterableOnceOps.minOption$(this, ord);
    }

    @Override
    public Object max(Ordering ord) {
        return IterableOnceOps.max$(this, ord);
    }

    @Override
    public <B> Option<Object> maxOption(Ordering<B> ord) {
        return IterableOnceOps.maxOption$(this, ord);
    }

    @Override
    public Object maxBy(Function1 f, Ordering cmp) {
        return IterableOnceOps.maxBy$(this, f, cmp);
    }

    @Override
    public <B> Option<Object> maxByOption(Function1<Object, B> f, Ordering<B> cmp) {
        return IterableOnceOps.maxByOption$(this, f, cmp);
    }

    @Override
    public Object minBy(Function1 f, Ordering cmp) {
        return IterableOnceOps.minBy$(this, f, cmp);
    }

    @Override
    public <B> Option<Object> minByOption(Function1<Object, B> f, Ordering<B> cmp) {
        return IterableOnceOps.minByOption$(this, f, cmp);
    }

    @Override
    public <B> Option<B> collectFirst(PartialFunction<Object, B> pf) {
        return IterableOnceOps.collectFirst$(this, pf);
    }

    @Override
    public <B> B aggregate(Function0<B> z, Function2<B, Object, B> seqop, Function2<B, B, B> combop) {
        return (B)IterableOnceOps.aggregate$(this, z, seqop, combop);
    }

    @Override
    public <B> boolean corresponds(IterableOnce<B> that, Function2<Object, B, Object> p) {
        return IterableOnceOps.corresponds$(this, that, p);
    }

    @Override
    public final String mkString(String start2, String sep, String end) {
        return IterableOnceOps.mkString$(this, start2, sep, end);
    }

    @Override
    public final String mkString(String sep) {
        return IterableOnceOps.mkString$(this, sep);
    }

    @Override
    public final String mkString() {
        return IterableOnceOps.mkString$(this);
    }

    @Override
    public StringBuilder addString(StringBuilder b, String start2, String sep, String end) {
        return IterableOnceOps.addString$(this, b, start2, sep, end);
    }

    @Override
    public final StringBuilder addString(StringBuilder b, String sep) {
        return IterableOnceOps.addString$(this, b, sep);
    }

    @Override
    public final StringBuilder addString(StringBuilder b) {
        return IterableOnceOps.addString$(this, b);
    }

    @Override
    public <C1> C1 to(Factory<Object, C1> factory2) {
        return (C1)IterableOnceOps.to$(this, factory2);
    }

    @Override
    public final Iterator<Object> toIterator() {
        return IterableOnceOps.toIterator$(this);
    }

    @Override
    public List<Object> toList() {
        return IterableOnceOps.toList$(this);
    }

    @Override
    public Vector<Object> toVector() {
        return IterableOnceOps.toVector$(this);
    }

    @Override
    public <K, V> Map<K, V> toMap($less$colon$less<Object, Tuple2<K, V>> ev) {
        return IterableOnceOps.toMap$(this, ev);
    }

    @Override
    public <B> Set<B> toSet() {
        return IterableOnceOps.toSet$(this);
    }

    @Override
    public IndexedSeq<Object> toIndexedSeq() {
        return IterableOnceOps.toIndexedSeq$(this);
    }

    @Override
    public final Stream<Object> toStream() {
        return IterableOnceOps.toStream$(this);
    }

    @Override
    public final <B> Buffer<B> toBuffer() {
        return IterableOnceOps.toBuffer$(this);
    }

    @Override
    public Iterable<Object> reversed() {
        return IterableOnceOps.reversed$(this);
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<Object, S> shape) {
        return (S)IterableOnce.stepper$(this, shape);
    }

    @Override
    public int knownSize() {
        return IterableOnce.knownSize$(this);
    }

    public abstract int len();

    @Override
    public abstract byte head();

    @Override
    public abstract byte next();

    public abstract void clear();

    public ByteIterator $plus$plus(IterableOnce<Object> that) {
        Iterator<Object> it = that.iterator();
        if (it.isEmpty()) {
            return this;
        }
        return ByteIterator$ByteArrayIterator$.MODULE$.apply((byte[])it.toArray(ClassTag$.MODULE$.Byte()));
    }

    public ByteIterator clone() {
        throw new UnsupportedOperationException("Method clone is not implemented in ByteIterator");
    }

    @Override
    public Tuple2<ByteIterator, ByteIterator> duplicate() {
        return new Tuple2<ByteIterator, ByteIterator>(this, this.clone());
    }

    @Override
    public ByteIterator take(int n) {
        throw new UnsupportedOperationException("Method take is not implemented in ByteIterator");
    }

    @Override
    public ByteIterator drop(int n) {
        throw new UnsupportedOperationException("Method drop is not implemented in ByteIterator");
    }

    @Override
    public ByteIterator slice(int from, int until) {
        if (from > 0) {
            return this.drop(from).take(until - from);
        }
        return this.take(until);
    }

    @Override
    public ByteIterator takeWhile(Function1<Object, Object> p) {
        throw new UnsupportedOperationException("Method takeWhile is not implemented in ByteIterator");
    }

    @Override
    public ByteIterator dropWhile(Function1<Object, Object> p) {
        throw new UnsupportedOperationException("Method dropWhile is not implemented in ByteIterator");
    }

    @Override
    public Tuple2<ByteIterator, ByteIterator> span(Function1<Object, Object> p) {
        ByteIterator that = this.clone();
        this.takeWhile((Function1)p);
        that.drop(this.len());
        return new Tuple2<ByteIterator, ByteIterator>(this, that);
    }

    @Override
    public int indexWhere(Function1<Object, Object> p, int from) {
        int index;
        for (index = 0; index < from; ++index) {
            this.next();
        }
        boolean found = false;
        while (!found && this.hasNext()) {
            if (BoxesRunTime.unboxToBoolean(p.apply(BoxesRunTime.boxToByte(this.next())))) {
                found = true;
                continue;
            }
            ++index;
        }
        if (found) {
            return index;
        }
        return -1;
    }

    @Override
    public int indexWhere$default$2() {
        return 0;
    }

    @Override
    public int indexOf(byte elem) {
        return this.indexOf(elem, 0);
    }

    @Override
    public int indexOf(byte elem, int from) {
        return this.indexWhere((Function1<Object, Object> & Serializable)(Object x$27) -> BoxesRunTime.boxToBoolean(ByteIterator.$anonfun$indexOf$1(elem, BoxesRunTime.unboxToByte(x$27))), from);
    }

    @Override
    public <B> int indexOf(B elem) {
        return this.indexOf(elem, 0);
    }

    @Override
    public <B> int indexOf(B elem, int from) {
        return this.indexWhere((Function1<Object, Object> & Serializable)(Object x$28) -> BoxesRunTime.boxToBoolean(ByteIterator.$anonfun$indexOf$2(elem, BoxesRunTime.unboxToByte(x$28))), from);
    }

    public abstract ByteString toByteString();

    public ByteString toSeq() {
        return this.toByteString();
    }

    @Override
    public <U> void foreach(Function1<Object, U> f) {
        while (this.hasNext()) {
            f.apply(BoxesRunTime.boxToByte(this.next()));
        }
    }

    @Override
    public <B> B foldLeft(B z, Function2<B, Object, B> op) {
        ObjectRef acc = ObjectRef.create(z);
        this.foreach$mVc$sp((Function1<Object, BoxedUnit>)(Function1<Object, Object> & Serializable)object -> {
            acc.elem = op.apply(acc.elem, BoxesRunTime.boxToByte(BoxesRunTime.unboxToByte(object)));
            return BoxedUnit.UNIT;
        });
        return (B)acc.elem;
    }

    @Override
    public <B> Object toArray(ClassTag<B> arg0) {
        Object target = arg0.newArray(this.len());
        this.copyToArray(target);
        return target;
    }

    public byte getByte() {
        return this.next();
    }

    public short getShort(ByteOrder byteOrder) {
        ByteOrder byteOrder2 = byteOrder;
        ByteOrder byteOrder3 = ByteOrder.BIG_ENDIAN;
        if (!(byteOrder2 != null ? !byteOrder2.equals(byteOrder3) : byteOrder3 != null)) {
            return (short)((this.next() & 0xFF) << 8 | (this.next() & 0xFF) << 0);
        }
        ByteOrder byteOrder4 = byteOrder;
        ByteOrder byteOrder5 = ByteOrder.LITTLE_ENDIAN;
        if (!(byteOrder4 != null ? !byteOrder4.equals(byteOrder5) : byteOrder5 != null)) {
            return (short)((this.next() & 0xFF) << 0 | (this.next() & 0xFF) << 8);
        }
        throw new IllegalArgumentException(new java.lang.StringBuilder(19).append("Unknown byte order ").append(byteOrder).toString());
    }

    public int getInt(ByteOrder byteOrder) {
        ByteOrder byteOrder2 = byteOrder;
        ByteOrder byteOrder3 = ByteOrder.BIG_ENDIAN;
        if (!(byteOrder2 != null ? !byteOrder2.equals(byteOrder3) : byteOrder3 != null)) {
            return (this.next() & 0xFF) << 24 | (this.next() & 0xFF) << 16 | (this.next() & 0xFF) << 8 | (this.next() & 0xFF) << 0;
        }
        ByteOrder byteOrder4 = byteOrder;
        ByteOrder byteOrder5 = ByteOrder.LITTLE_ENDIAN;
        if (!(byteOrder4 != null ? !byteOrder4.equals(byteOrder5) : byteOrder5 != null)) {
            return (this.next() & 0xFF) << 0 | (this.next() & 0xFF) << 8 | (this.next() & 0xFF) << 16 | (this.next() & 0xFF) << 24;
        }
        throw new IllegalArgumentException(new java.lang.StringBuilder(19).append("Unknown byte order ").append(byteOrder).toString());
    }

    public long getLong(ByteOrder byteOrder) {
        ByteOrder byteOrder2 = byteOrder;
        ByteOrder byteOrder3 = ByteOrder.BIG_ENDIAN;
        if (!(byteOrder2 != null ? !byteOrder2.equals(byteOrder3) : byteOrder3 != null)) {
            return ((long)this.next() & 0xFFL) << 56 | ((long)this.next() & 0xFFL) << 48 | ((long)this.next() & 0xFFL) << 40 | ((long)this.next() & 0xFFL) << 32 | ((long)this.next() & 0xFFL) << 24 | ((long)this.next() & 0xFFL) << 16 | ((long)this.next() & 0xFFL) << 8 | ((long)this.next() & 0xFFL) << 0;
        }
        ByteOrder byteOrder4 = byteOrder;
        ByteOrder byteOrder5 = ByteOrder.LITTLE_ENDIAN;
        if (!(byteOrder4 != null ? !byteOrder4.equals(byteOrder5) : byteOrder5 != null)) {
            return ((long)this.next() & 0xFFL) << 0 | ((long)this.next() & 0xFFL) << 8 | ((long)this.next() & 0xFFL) << 16 | ((long)this.next() & 0xFFL) << 24 | ((long)this.next() & 0xFFL) << 32 | ((long)this.next() & 0xFFL) << 40 | ((long)this.next() & 0xFFL) << 48 | ((long)this.next() & 0xFFL) << 56;
        }
        throw new IllegalArgumentException(new java.lang.StringBuilder(19).append("Unknown byte order ").append(byteOrder).toString());
    }

    public long getLongPart(int n, ByteOrder byteOrder) {
        ByteOrder byteOrder2 = byteOrder;
        ByteOrder byteOrder3 = ByteOrder.BIG_ENDIAN;
        if (!(byteOrder2 != null ? !byteOrder2.equals(byteOrder3) : byteOrder3 != null)) {
            LongRef x = LongRef.create(0L);
            RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), n).foreach$mVc$sp(x$29 -> {
                x$30.elem = x$30.elem << 8 | (long)(this.next() & 0xFF);
            });
            return x.elem;
        }
        ByteOrder byteOrder4 = byteOrder;
        ByteOrder byteOrder5 = ByteOrder.LITTLE_ENDIAN;
        if (!(byteOrder4 != null ? !byteOrder4.equals(byteOrder5) : byteOrder5 != null)) {
            LongRef x = LongRef.create(0L);
            RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), n).foreach$mVc$sp(i -> x$31.elem |= (long)((this.next() & 0xFF) << 8 * i));
            return x.elem;
        }
        throw new IllegalArgumentException(new java.lang.StringBuilder(19).append("Unknown byte order ").append(byteOrder).toString());
    }

    public float getFloat(ByteOrder byteOrder) {
        return Float.intBitsToFloat(this.getInt(byteOrder));
    }

    public double getDouble(ByteOrder byteOrder) {
        return Double.longBitsToDouble(this.getLong(byteOrder));
    }

    public ByteIterator getBytes(byte[] xs) {
        return this.getBytes(xs, 0, xs.length);
    }

    public abstract ByteIterator getBytes(byte[] var1, int var2, int var3);

    public byte[] getBytes(int n) {
        byte[] bytes = new byte[n];
        this.getBytes(bytes, 0, n);
        return bytes;
    }

    public ByteString getByteString(int n) {
        ByteString bs = this.clone().take(n).toByteString();
        this.drop(n);
        return bs;
    }

    public ByteIterator getShorts(short[] xs, ByteOrder byteOrder) {
        return this.getShorts(xs, 0, xs.length, byteOrder);
    }

    public abstract ByteIterator getShorts(short[] var1, int var2, int var3, ByteOrder var4);

    public ByteIterator getInts(int[] xs, ByteOrder byteOrder) {
        return this.getInts(xs, 0, xs.length, byteOrder);
    }

    public abstract ByteIterator getInts(int[] var1, int var2, int var3, ByteOrder var4);

    public ByteIterator getLongs(long[] xs, ByteOrder byteOrder) {
        return this.getLongs(xs, 0, xs.length, byteOrder);
    }

    public abstract ByteIterator getLongs(long[] var1, int var2, int var3, ByteOrder var4);

    public ByteIterator getFloats(float[] xs, ByteOrder byteOrder) {
        return this.getFloats(xs, 0, xs.length, byteOrder);
    }

    public abstract ByteIterator getFloats(float[] var1, int var2, int var3, ByteOrder var4);

    public ByteIterator getDoubles(double[] xs, ByteOrder byteOrder) {
        return this.getDoubles(xs, 0, xs.length, byteOrder);
    }

    public abstract ByteIterator getDoubles(double[] var1, int var2, int var3, ByteOrder var4);

    public abstract int copyToBuffer(ByteBuffer var1);

    public abstract InputStream asInputStream();

    public void foreach$mZc$sp(Function1<Object, Object> f) {
        while (this.hasNext()) {
            f.apply(BoxesRunTime.boxToByte(this.next()));
        }
    }

    public void foreach$mBc$sp(Function1<Object, Object> f) {
        while (this.hasNext()) {
            f.apply(BoxesRunTime.boxToByte(this.next()));
        }
    }

    public void foreach$mCc$sp(Function1<Object, Object> f) {
        while (this.hasNext()) {
            f.apply(BoxesRunTime.boxToByte(this.next()));
        }
    }

    public void foreach$mDc$sp(Function1<Object, Object> f) {
        while (this.hasNext()) {
            f.apply(BoxesRunTime.boxToByte(this.next()));
        }
    }

    public void foreach$mFc$sp(Function1<Object, Object> f) {
        while (this.hasNext()) {
            f.apply(BoxesRunTime.boxToByte(this.next()));
        }
    }

    public void foreach$mIc$sp(Function1<Object, Object> f) {
        while (this.hasNext()) {
            f.apply(BoxesRunTime.boxToByte(this.next()));
        }
    }

    public void foreach$mJc$sp(Function1<Object, Object> f) {
        while (this.hasNext()) {
            f.apply(BoxesRunTime.boxToByte(this.next()));
        }
    }

    public void foreach$mSc$sp(Function1<Object, Object> f) {
        while (this.hasNext()) {
            f.apply(BoxesRunTime.boxToByte(this.next()));
        }
    }

    public void foreach$mVc$sp(Function1<Object, BoxedUnit> f) {
        while (this.hasNext()) {
            f.apply(BoxesRunTime.boxToByte(this.next()));
        }
    }

    public boolean foldLeft$mZc$sp(boolean z, Function2<Object, Object, Object> op) {
        BooleanRef acc = BooleanRef.create(z);
        this.foreach$mVc$sp((Function1<Object, BoxedUnit>)(Function1<Object, Object> & Serializable)object -> {
            acc.elem = BoxesRunTime.unboxToBoolean(op.apply(BoxesRunTime.boxToBoolean(acc.elem), BoxesRunTime.boxToByte(BoxesRunTime.unboxToByte(object))));
            return BoxedUnit.UNIT;
        });
        return acc.elem;
    }

    public byte foldLeft$mBc$sp(byte z, Function2<Object, Object, Object> op) {
        ByteRef acc = ByteRef.create(z);
        this.foreach$mVc$sp((Function1<Object, BoxedUnit>)(Function1<Object, Object> & Serializable)object -> {
            acc.elem = BoxesRunTime.unboxToByte(op.apply(BoxesRunTime.boxToByte(acc.elem), BoxesRunTime.boxToByte(BoxesRunTime.unboxToByte(object))));
            return BoxedUnit.UNIT;
        });
        return acc.elem;
    }

    public char foldLeft$mCc$sp(char z, Function2<Object, Object, Object> op) {
        CharRef acc = CharRef.create(z);
        this.foreach$mVc$sp((Function1<Object, BoxedUnit>)(Function1<Object, Object> & Serializable)object -> {
            acc.elem = BoxesRunTime.unboxToChar(op.apply(BoxesRunTime.boxToCharacter(acc.elem), BoxesRunTime.boxToByte(BoxesRunTime.unboxToByte(object))));
            return BoxedUnit.UNIT;
        });
        return acc.elem;
    }

    public double foldLeft$mDc$sp(double z, Function2<Object, Object, Object> op) {
        DoubleRef acc = DoubleRef.create(z);
        this.foreach$mVc$sp((Function1<Object, BoxedUnit>)(Function1<Object, Object> & Serializable)object -> {
            acc.elem = BoxesRunTime.unboxToDouble(op.apply(BoxesRunTime.boxToDouble(acc.elem), BoxesRunTime.boxToByte(BoxesRunTime.unboxToByte(object))));
            return BoxedUnit.UNIT;
        });
        return acc.elem;
    }

    public float foldLeft$mFc$sp(float z, Function2<Object, Object, Object> op) {
        FloatRef acc = FloatRef.create(z);
        this.foreach$mVc$sp((Function1<Object, BoxedUnit>)(Function1<Object, Object> & Serializable)object -> {
            acc.elem = BoxesRunTime.unboxToFloat(op.apply(BoxesRunTime.boxToFloat(acc.elem), BoxesRunTime.boxToByte(BoxesRunTime.unboxToByte(object))));
            return BoxedUnit.UNIT;
        });
        return acc.elem;
    }

    public int foldLeft$mIc$sp(int z, Function2<Object, Object, Object> op) {
        IntRef acc = IntRef.create(z);
        this.foreach$mVc$sp((Function1<Object, BoxedUnit>)(Function1<Object, Object> & Serializable)object -> {
            acc.elem = BoxesRunTime.unboxToInt(op.apply(BoxesRunTime.boxToInteger(acc.elem), BoxesRunTime.boxToByte(BoxesRunTime.unboxToByte(object))));
            return BoxedUnit.UNIT;
        });
        return acc.elem;
    }

    public long foldLeft$mJc$sp(long z, Function2<Object, Object, Object> op) {
        LongRef acc = LongRef.create(z);
        this.foreach$mVc$sp((Function1<Object, BoxedUnit>)(Function1<Object, Object> & Serializable)object -> {
            acc.elem = BoxesRunTime.unboxToLong(op.apply(BoxesRunTime.boxToLong(acc.elem), BoxesRunTime.boxToByte(BoxesRunTime.unboxToByte(object))));
            return BoxedUnit.UNIT;
        });
        return acc.elem;
    }

    public short foldLeft$mSc$sp(short z, Function2<Object, Object, Object> op) {
        ShortRef acc = ShortRef.create(z);
        this.foreach$mVc$sp((Function1<Object, BoxedUnit>)(Function1<Object, Object> & Serializable)object -> {
            acc.elem = BoxesRunTime.unboxToShort(op.apply(BoxesRunTime.boxToShort(acc.elem), BoxesRunTime.boxToByte(BoxesRunTime.unboxToByte(object))));
            return BoxedUnit.UNIT;
        });
        return acc.elem;
    }

    public void foldLeft$mVc$sp(BoxedUnit z, Function2<BoxedUnit, Object, BoxedUnit> op) {
        ObjectRef<BoxedUnit> acc = ObjectRef.create(z);
        this.foreach$mVc$sp((Function1<Object, BoxedUnit>)(Function1<Object, Object> & Serializable)object -> {
            acc.elem = (BoxedUnit)op.apply((BoxedUnit)acc.elem, BoxesRunTime.boxToByte(BoxesRunTime.unboxToByte(object)));
            return BoxedUnit.UNIT;
        });
        BoxedUnit cfr_ignored_0 = (BoxedUnit)acc.elem;
    }

    public static final /* synthetic */ boolean $anonfun$indexOf$1(byte elem$1, byte x$27) {
        return x$27 == elem$1;
    }

    public static final /* synthetic */ boolean $anonfun$indexOf$2(Object elem$2, byte x$28) {
        return BoxesRunTime.equals(BoxesRunTime.boxToByte(x$28), elem$2);
    }

    public ByteIterator() {
        IterableOnce.$init$(this);
        IterableOnceOps.$init$(this);
        Iterator.$init$(this);
        BufferedIterator.$init$(this);
    }

    public static class ByteArrayIterator
    extends ByteIterator {
        private byte[] akka$util$ByteIterator$ByteArrayIterator$$array;
        private int akka$util$ByteIterator$ByteArrayIterator$$from;
        private int akka$util$ByteIterator$ByteArrayIterator$$until;

        public byte[] akka$util$ByteIterator$ByteArrayIterator$$array() {
            return this.akka$util$ByteIterator$ByteArrayIterator$$array;
        }

        private void array_$eq(byte[] x$1) {
            this.akka$util$ByteIterator$ByteArrayIterator$$array = x$1;
        }

        public int akka$util$ByteIterator$ByteArrayIterator$$from() {
            return this.akka$util$ByteIterator$ByteArrayIterator$$from;
        }

        private void from_$eq(int x$1) {
            this.akka$util$ByteIterator$ByteArrayIterator$$from = x$1;
        }

        public int akka$util$ByteIterator$ByteArrayIterator$$until() {
            return this.akka$util$ByteIterator$ByteArrayIterator$$until;
        }

        private void until_$eq(int x$1) {
            this.akka$util$ByteIterator$ByteArrayIterator$$until = x$1;
        }

        @Override
        public final int len() {
            return this.akka$util$ByteIterator$ByteArrayIterator$$until() - this.akka$util$ByteIterator$ByteArrayIterator$$from();
        }

        @Override
        public final boolean hasNext() {
            return this.akka$util$ByteIterator$ByteArrayIterator$$from() < this.akka$util$ByteIterator$ByteArrayIterator$$until();
        }

        @Override
        public final byte head() {
            return this.akka$util$ByteIterator$ByteArrayIterator$$array()[this.akka$util$ByteIterator$ByteArrayIterator$$from()];
        }

        @Override
        public final byte next() {
            if (!this.hasNext()) {
                return BoxesRunTime.unboxToByte(Collections$EmptyImmutableSeq$.MODULE$.iterator().next());
            }
            int i = this.akka$util$ByteIterator$ByteArrayIterator$$from();
            this.from_$eq(this.akka$util$ByteIterator$ByteArrayIterator$$from() + 1);
            return this.akka$util$ByteIterator$ByteArrayIterator$$array()[i];
        }

        @Override
        public void clear() {
            this.array_$eq(Array$.MODULE$.emptyByteArray());
            this.from_$eq(0);
            this.until_$eq(this.akka$util$ByteIterator$ByteArrayIterator$$from());
        }

        @Override
        public final int size() {
            int l = this.len();
            this.clear();
            return l;
        }

        @Override
        public final ByteIterator $plus$plus(IterableOnce<Object> that) {
            IterableOnce<Object> iterableOnce = that;
            if (iterableOnce instanceof ByteIterator) {
                ByteIterator byteIterator = (ByteIterator)iterableOnce;
                if (byteIterator.isEmpty()) {
                    return this;
                }
                if (this.isEmpty()) {
                    return byteIterator;
                }
                ByteIterator byteIterator2 = byteIterator;
                if (byteIterator2 instanceof ByteArrayIterator) {
                    ByteArrayIterator byteArrayIterator = (ByteArrayIterator)byteIterator2;
                    if (this.akka$util$ByteIterator$ByteArrayIterator$$array() == byteArrayIterator.akka$util$ByteIterator$ByteArrayIterator$$array() && this.akka$util$ByteIterator$ByteArrayIterator$$until() == byteArrayIterator.akka$util$ByteIterator$ByteArrayIterator$$from()) {
                        this.until_$eq(byteArrayIterator.akka$util$ByteIterator$ByteArrayIterator$$until());
                        byteArrayIterator.clear();
                        return this;
                    }
                    MultiByteArrayIterator result = ByteIterator$MultiByteArrayIterator$.MODULE$.apply(new $colon$colon<Nothing$>((Nothing$)((Object)this), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)byteArrayIterator), Nil$.MODULE$)));
                    this.clear();
                    return result;
                }
                if (byteIterator2 instanceof MultiByteArrayIterator) {
                    MultiByteArrayIterator multiByteArrayIterator = (MultiByteArrayIterator)byteIterator2;
                    ByteArrayIterator byteArrayIterator = this;
                    return multiByteArrayIterator.$plus$plus$colon(byteArrayIterator);
                }
                return super.$plus$plus(byteIterator2);
            }
            return super.$plus$plus(iterableOnce);
        }

        @Override
        public final ByteArrayIterator clone() {
            return new ByteArrayIterator(this.akka$util$ByteIterator$ByteArrayIterator$$array(), this.akka$util$ByteIterator$ByteArrayIterator$$from(), this.akka$util$ByteIterator$ByteArrayIterator$$until());
        }

        @Override
        public final ByteArrayIterator take(int n) {
            block0: {
                if (n >= this.len()) break block0;
                this.until_$eq(n > 0 ? this.akka$util$ByteIterator$ByteArrayIterator$$from() + n : this.akka$util$ByteIterator$ByteArrayIterator$$from());
            }
            return this;
        }

        @Override
        public final ByteArrayIterator drop(int n) {
            block0: {
                if (n <= 0) break block0;
                this.from_$eq(n < this.len() ? this.akka$util$ByteIterator$ByteArrayIterator$$from() + n : this.akka$util$ByteIterator$ByteArrayIterator$$until());
            }
            return this;
        }

        @Override
        public final ByteArrayIterator takeWhile(Function1<Object, Object> p) {
            int prev = this.akka$util$ByteIterator$ByteArrayIterator$$from();
            this.dropWhile((Function1)p);
            this.until_$eq(this.akka$util$ByteIterator$ByteArrayIterator$$from());
            this.from_$eq(prev);
            return this;
        }

        @Override
        public final ByteArrayIterator dropWhile(Function1<Object, Object> p) {
            boolean stop = false;
            while (!stop && this.hasNext()) {
                if (BoxesRunTime.unboxToBoolean(p.apply(BoxesRunTime.boxToByte(this.akka$util$ByteIterator$ByteArrayIterator$$array()[this.akka$util$ByteIterator$ByteArrayIterator$$from()])))) {
                    this.from_$eq(this.akka$util$ByteIterator$ByteArrayIterator$$from() + 1);
                    continue;
                }
                stop = true;
            }
            return this;
        }

        @Override
        public <B> int copyToArray(Object xs, int start2) {
            return this.copyToArray(xs, start2, ScalaRunTime$.MODULE$.array_length(xs));
        }

        @Override
        public <B> int copyToArray(Object xs) {
            return this.copyToArray(xs, 0, ScalaRunTime$.MODULE$.array_length(xs));
        }

        @Override
        public final <B> int copyToArray(Object xs, int start2, int len) {
            int n = RichInt$.MODULE$.max$extension(Predef$.MODULE$.intWrapper(0), RichInt$.MODULE$.min$extension(Predef$.MODULE$.intWrapper(RichInt$.MODULE$.min$extension(Predef$.MODULE$.intWrapper(ScalaRunTime$.MODULE$.array_length(xs) - start2), this.len())), len));
            Array$.MODULE$.copy(this.akka$util$ByteIterator$ByteArrayIterator$$array(), this.akka$util$ByteIterator$ByteArrayIterator$$from(), xs, start2, n);
            this.drop(n);
            return n;
        }

        @Override
        public final ByteString toByteString() {
            ByteString.ByteString1 result = this.akka$util$ByteIterator$ByteArrayIterator$$from() == 0 && this.akka$util$ByteIterator$ByteArrayIterator$$until() == this.akka$util$ByteIterator$ByteArrayIterator$$array().length ? ByteString$ByteString1C$.MODULE$.apply(this.akka$util$ByteIterator$ByteArrayIterator$$array()) : ByteString$ByteString1$.MODULE$.apply(this.akka$util$ByteIterator$ByteArrayIterator$$array(), this.akka$util$ByteIterator$ByteArrayIterator$$from(), this.len());
            this.clear();
            return result;
        }

        @Override
        public ByteArrayIterator getBytes(byte[] xs, int offset, int n) throws NoSuchElementException {
            if (n <= this.len()) {
                Array$.MODULE$.copy(this.akka$util$ByteIterator$ByteArrayIterator$$array(), this.akka$util$ByteIterator$ByteArrayIterator$$from(), xs, offset, n);
                return this.drop(n);
            }
            throw new NoSuchElementException("next on empty iterator");
        }

        private ByteBuffer wrappedByteBuffer() {
            return ByteBuffer.wrap(this.akka$util$ByteIterator$ByteArrayIterator$$array(), this.akka$util$ByteIterator$ByteArrayIterator$$from(), this.len()).asReadOnlyBuffer();
        }

        @Override
        public ByteArrayIterator getShorts(short[] xs, int offset, int n, ByteOrder byteOrder) {
            this.wrappedByteBuffer().order(byteOrder).asShortBuffer().get(xs, offset, n);
            return this.drop(2 * n);
        }

        @Override
        public ByteArrayIterator getInts(int[] xs, int offset, int n, ByteOrder byteOrder) {
            this.wrappedByteBuffer().order(byteOrder).asIntBuffer().get(xs, offset, n);
            return this.drop(4 * n);
        }

        @Override
        public ByteArrayIterator getLongs(long[] xs, int offset, int n, ByteOrder byteOrder) {
            this.wrappedByteBuffer().order(byteOrder).asLongBuffer().get(xs, offset, n);
            return this.drop(8 * n);
        }

        @Override
        public ByteArrayIterator getFloats(float[] xs, int offset, int n, ByteOrder byteOrder) {
            this.wrappedByteBuffer().order(byteOrder).asFloatBuffer().get(xs, offset, n);
            return this.drop(4 * n);
        }

        @Override
        public ByteArrayIterator getDoubles(double[] xs, int offset, int n, ByteOrder byteOrder) {
            this.wrappedByteBuffer().order(byteOrder).asDoubleBuffer().get(xs, offset, n);
            return this.drop(8 * n);
        }

        @Override
        public int copyToBuffer(ByteBuffer buffer) {
            Object object;
            int copyLength = scala.math.package$.MODULE$.min(buffer.remaining(), this.len());
            if (copyLength > 0) {
                buffer.put(this.akka$util$ByteIterator$ByteArrayIterator$$array(), this.akka$util$ByteIterator$ByteArrayIterator$$from(), copyLength);
                object = this.drop(copyLength);
            } else {
                object = BoxedUnit.UNIT;
            }
            return copyLength;
        }

        @Override
        public InputStream asInputStream() {
            return new InputStream(this){
                private final /* synthetic */ ByteArrayIterator $outer;

                public int available() {
                    return this.$outer.len();
                }

                public int read() {
                    if (this.$outer.hasNext()) {
                        return this.$outer.next() & 0xFF;
                    }
                    return -1;
                }

                public int read(byte[] b, int off, int len) {
                    if (off < 0 || len < 0 || off + len > b.length) {
                        throw new IndexOutOfBoundsException();
                    }
                    if (len == 0) {
                        return 0;
                    }
                    if (!this.$outer.isEmpty()) {
                        int nRead = scala.math.package$.MODULE$.min(this.available(), len);
                        this.$outer.copyToArray(b, off, nRead);
                        return nRead;
                    }
                    return -1;
                }

                public long skip(long n) {
                    int nSkip = scala.math.package$.MODULE$.min(this.$outer.len(), (int)n);
                    this.$outer.drop(nSkip);
                    return nSkip;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                }
            };
        }

        public ByteArrayIterator(byte[] array, int from, int until) {
            this.akka$util$ByteIterator$ByteArrayIterator$$array = array;
            this.akka$util$ByteIterator$ByteArrayIterator$$from = from;
            this.akka$util$ByteIterator$ByteArrayIterator$$until = until;
        }
    }

    public static class MultiByteArrayIterator
    extends ByteIterator {
        private LinearSeq<ByteArrayIterator> akka$util$ByteIterator$MultiByteArrayIterator$$iterators;

        public LinearSeq<ByteArrayIterator> akka$util$ByteIterator$MultiByteArrayIterator$$iterators() {
            return this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators;
        }

        public void akka$util$ByteIterator$MultiByteArrayIterator$$iterators_$eq(LinearSeq<ByteArrayIterator> x$1) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators = x$1;
        }

        public MultiByteArrayIterator akka$util$ByteIterator$MultiByteArrayIterator$$normalize() {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators_$eq(this.norm$1(this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators()));
            return this;
        }

        public ByteArrayIterator akka$util$ByteIterator$MultiByteArrayIterator$$current() {
            return (ByteArrayIterator)this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().head();
        }

        private void dropCurrent() {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators_$eq((LinearSeq)this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().tail());
        }

        @Override
        public void clear() {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators_$eq(ByteIterator$MultiByteArrayIterator$.MODULE$.empty().akka$util$ByteIterator$MultiByteArrayIterator$$iterators());
        }

        @Override
        public final boolean hasNext() {
            return this.akka$util$ByteIterator$MultiByteArrayIterator$$current().hasNext();
        }

        @Override
        public final byte head() {
            return this.akka$util$ByteIterator$MultiByteArrayIterator$$current().head();
        }

        @Override
        public final byte next() {
            byte result = this.akka$util$ByteIterator$MultiByteArrayIterator$$current().next();
            this.akka$util$ByteIterator$MultiByteArrayIterator$$normalize();
            return result;
        }

        @Override
        public final int len() {
            return BoxesRunTime.unboxToInt(this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foldLeft(BoxesRunTime.boxToInteger(0), (Function2<Object, ByteArrayIterator, Object> & Serializable)(B x$1, A x$2) -> BoxesRunTime.boxToInteger(MultiByteArrayIterator.$anonfun$len$1(BoxesRunTime.unboxToInt(x$1), x$2))));
        }

        @Override
        public final int size() {
            int result = this.len();
            this.clear();
            return result;
        }

        public MultiByteArrayIterator $plus$plus$colon(ByteArrayIterator that) {
            ByteArrayIterator byteArrayIterator = that;
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators_$eq((LinearSeq)this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().$plus$colon(byteArrayIterator));
            return this;
        }

        @Override
        public final ByteIterator $plus$plus(IterableOnce<Object> that) {
            IterableOnce<Object> iterableOnce = that;
            if (iterableOnce instanceof ByteIterator) {
                ByteIterator byteIterator = (ByteIterator)iterableOnce;
                if (byteIterator.isEmpty()) {
                    return this;
                }
                if (this.isEmpty()) {
                    return byteIterator;
                }
                ByteIterator byteIterator2 = byteIterator;
                if (byteIterator2 instanceof ByteArrayIterator) {
                    ByteArrayIterator byteArrayIterator = (ByteArrayIterator)byteIterator2;
                    this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators_$eq((LinearSeq)this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().$colon$plus(byteArrayIterator));
                    byteArrayIterator.clear();
                    return this;
                }
                if (byteIterator2 instanceof MultiByteArrayIterator) {
                    MultiByteArrayIterator multiByteArrayIterator = (MultiByteArrayIterator)byteIterator2;
                    this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators_$eq((LinearSeq)this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().$plus$plus(multiByteArrayIterator.akka$util$ByteIterator$MultiByteArrayIterator$$iterators()));
                    multiByteArrayIterator.clear();
                    return this;
                }
                return super.$plus$plus(byteIterator2);
            }
            return super.$plus$plus(iterableOnce);
        }

        @Override
        public final MultiByteArrayIterator clone() {
            List clonedIterators = this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().iterator().map((Function1<ByteArrayIterator, ByteArrayIterator> & Serializable)(A x$3) -> x$3.clone()).to(IterableFactory$.MODULE$.toFactory(package$.MODULE$.List()));
            return new MultiByteArrayIterator(clonedIterators);
        }

        @Override
        public final MultiByteArrayIterator take(int n) {
            int rest = n;
            ListBuffer<ByteArrayIterator> builder = new ListBuffer<ByteArrayIterator>();
            while (rest > 0 && !this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().isEmpty()) {
                Object object;
                this.akka$util$ByteIterator$MultiByteArrayIterator$$current().take(rest);
                if (this.akka$util$ByteIterator$MultiByteArrayIterator$$current().hasNext()) {
                    rest -= this.akka$util$ByteIterator$MultiByteArrayIterator$$current().len();
                    object = builder.$plus$eq(this.akka$util$ByteIterator$MultiByteArrayIterator$$current());
                } else {
                    object = BoxedUnit.UNIT;
                }
                this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators_$eq((LinearSeq)this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().tail());
            }
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators_$eq((LinearSeq<ByteArrayIterator>)builder.result());
            return this.akka$util$ByteIterator$MultiByteArrayIterator$$normalize();
        }

        @Override
        public final MultiByteArrayIterator drop(int n) {
            while (n > 0 && !this.isEmpty()) {
                int nCurrent = scala.math.package$.MODULE$.min(n, this.akka$util$ByteIterator$MultiByteArrayIterator$$current().len());
                this.akka$util$ByteIterator$MultiByteArrayIterator$$current().drop(n);
                int rest = n - nCurrent;
                Predef$.MODULE$.assert(this.akka$util$ByteIterator$MultiByteArrayIterator$$current().isEmpty() || rest == 0);
                this.akka$util$ByteIterator$MultiByteArrayIterator$$normalize();
                n = rest;
            }
            return this;
        }

        @Override
        public final MultiByteArrayIterator takeWhile(Function1<Object, Object> p) {
            boolean stop = false;
            ListBuffer<ByteArrayIterator> builder = new ListBuffer<ByteArrayIterator>();
            while (!stop && !this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().isEmpty()) {
                int lastLen = this.akka$util$ByteIterator$MultiByteArrayIterator$$current().len();
                this.akka$util$ByteIterator$MultiByteArrayIterator$$current().takeWhile((Function1)p);
                Object object = this.akka$util$ByteIterator$MultiByteArrayIterator$$current().hasNext() ? builder.$plus$eq(this.akka$util$ByteIterator$MultiByteArrayIterator$$current()) : BoxedUnit.UNIT;
                if (this.akka$util$ByteIterator$MultiByteArrayIterator$$current().len() < lastLen) {
                    stop = true;
                }
                this.dropCurrent();
            }
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators_$eq((LinearSeq<ByteArrayIterator>)builder.result());
            return this.akka$util$ByteIterator$MultiByteArrayIterator$$normalize();
        }

        @Override
        public final MultiByteArrayIterator dropWhile(Function1<Object, Object> p) {
            while (!this.isEmpty()) {
                this.akka$util$ByteIterator$MultiByteArrayIterator$$current().dropWhile((Function1)p);
                boolean dropMore = this.akka$util$ByteIterator$MultiByteArrayIterator$$current().isEmpty();
                this.akka$util$ByteIterator$MultiByteArrayIterator$$normalize();
                if (dropMore) continue;
                return this;
            }
            return this;
        }

        @Override
        public final <B> int copyToArray(Object xs, int start2, int len) {
            int rest;
            int n;
            int pos = start2;
            for (rest = len; rest > 0 && !this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().isEmpty() && pos < ScalaRunTime$.MODULE$.array_length(xs); pos += n, rest -= n) {
                n = RichInt$.MODULE$.max$extension(Predef$.MODULE$.intWrapper(0), RichInt$.MODULE$.min$extension(Predef$.MODULE$.intWrapper(RichInt$.MODULE$.min$extension(Predef$.MODULE$.intWrapper(ScalaRunTime$.MODULE$.array_length(xs) - pos), this.akka$util$ByteIterator$MultiByteArrayIterator$$current().len())), rest));
                this.akka$util$ByteIterator$MultiByteArrayIterator$$current().copyToArray(xs, pos, n);
                if (!this.akka$util$ByteIterator$MultiByteArrayIterator$$current().isEmpty()) continue;
                this.dropCurrent();
            }
            this.akka$util$ByteIterator$MultiByteArrayIterator$$normalize();
            return len - rest;
        }

        @Override
        public <U> void foreach(Function1<Object, U> f) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foreach((Function1<ByteArrayIterator, Object> & Serializable)(A x$4) -> {
                x$4.foreach(f);
                return BoxedUnit.UNIT;
            });
            this.clear();
        }

        @Override
        public final ByteString toByteString() {
            if (((SeqOps)this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().tail()).isEmpty()) {
                return ((ByteArrayIterator)this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().head()).toByteString();
            }
            ByteString result = this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foldLeft(ByteString$.MODULE$.empty(), (Function2<ByteString, ByteArrayIterator, ByteString> & Serializable)(B x$5, A x$6) -> x$5.$plus$plus(x$6.toByteString()));
            this.clear();
            return result;
        }

        public final <A> MultiByteArrayIterator getToArray(Object xs, int offset, int n, int elemSize, Function0<A> getSingle, Function3<Object, Object, Object, BoxedUnit> getMult) {
            while (n > 0) {
                int n2;
                Serializable serializable = this.isEmpty() ? Collections$EmptyImmutableSeq$.MODULE$.iterator().next() : BoxedUnit.UNIT;
                if (this.akka$util$ByteIterator$MultiByteArrayIterator$$current().len() >= elemSize) {
                    int nCurrent = scala.math.package$.MODULE$.min(n, this.akka$util$ByteIterator$MultiByteArrayIterator$$current().len() / elemSize);
                    getMult.apply(xs, BoxesRunTime.boxToInteger(offset), BoxesRunTime.boxToInteger(nCurrent));
                    n2 = nCurrent;
                } else {
                    ScalaRunTime$.MODULE$.array_update(xs, offset, getSingle.apply());
                    n2 = 1;
                }
                int nDone = n2;
                this.akka$util$ByteIterator$MultiByteArrayIterator$$normalize();
                n -= nDone;
                offset += nDone;
            }
            return this;
        }

        @Override
        public MultiByteArrayIterator getBytes(byte[] xs, int offset, int n) {
            return this.getToArray(xs, offset, n, 1, () -> this.getByte(), (Function3<Object, Object, Object, BoxedUnit>)(Function3<byte[], Object, Object, Object> & Serializable)(x$7, x$8, x$9) -> {
                MultiByteArrayIterator.$anonfun$getBytes$2(this, x$7, BoxesRunTime.unboxToInt(x$8), BoxesRunTime.unboxToInt(x$9));
                return BoxedUnit.UNIT;
            });
        }

        @Override
        public MultiByteArrayIterator getShorts(short[] xs, int offset, int n, ByteOrder byteOrder) {
            return this.getToArray(xs, offset, n, 2, () -> this.getShort(byteOrder), (Function3<Object, Object, Object, BoxedUnit>)(Function3<short[], Object, Object, Object> & Serializable)(x$10, x$11, x$12) -> {
                MultiByteArrayIterator.$anonfun$getShorts$2(this, byteOrder, x$10, BoxesRunTime.unboxToInt(x$11), BoxesRunTime.unboxToInt(x$12));
                return BoxedUnit.UNIT;
            });
        }

        @Override
        public MultiByteArrayIterator getInts(int[] xs, int offset, int n, ByteOrder byteOrder) {
            return this.getToArray(xs, offset, n, 4, () -> this.getInt(byteOrder), (Function3<Object, Object, Object, BoxedUnit>)(Function3<int[], Object, Object, Object> & Serializable)(x$13, x$14, x$15) -> {
                MultiByteArrayIterator.$anonfun$getInts$2(this, byteOrder, x$13, BoxesRunTime.unboxToInt(x$14), BoxesRunTime.unboxToInt(x$15));
                return BoxedUnit.UNIT;
            });
        }

        @Override
        public MultiByteArrayIterator getLongs(long[] xs, int offset, int n, ByteOrder byteOrder) {
            return this.getToArray(xs, offset, n, 8, () -> this.getLong(byteOrder), (Function3<Object, Object, Object, BoxedUnit>)(Function3<long[], Object, Object, Object> & Serializable)(x$16, x$17, x$18) -> {
                MultiByteArrayIterator.$anonfun$getLongs$2(this, byteOrder, x$16, BoxesRunTime.unboxToInt(x$17), BoxesRunTime.unboxToInt(x$18));
                return BoxedUnit.UNIT;
            });
        }

        @Override
        public MultiByteArrayIterator getFloats(float[] xs, int offset, int n, ByteOrder byteOrder) {
            return this.getToArray(xs, offset, n, 8, () -> this.getFloat(byteOrder), (Function3<Object, Object, Object, BoxedUnit>)(Function3<float[], Object, Object, Object> & Serializable)(x$19, x$20, x$21) -> {
                MultiByteArrayIterator.$anonfun$getFloats$2(this, byteOrder, x$19, BoxesRunTime.unboxToInt(x$20), BoxesRunTime.unboxToInt(x$21));
                return BoxedUnit.UNIT;
            });
        }

        @Override
        public MultiByteArrayIterator getDoubles(double[] xs, int offset, int n, ByteOrder byteOrder) {
            return this.getToArray(xs, offset, n, 8, () -> this.getDouble(byteOrder), (Function3<Object, Object, Object, BoxedUnit>)(Function3<double[], Object, Object, Object> & Serializable)(x$22, x$23, x$24) -> {
                MultiByteArrayIterator.$anonfun$getDoubles$2(this, byteOrder, x$22, BoxesRunTime.unboxToInt(x$23), BoxesRunTime.unboxToInt(x$24));
                return BoxedUnit.UNIT;
            });
        }

        @Override
        public int copyToBuffer(ByteBuffer buffer) {
            int n = BoxesRunTime.unboxToInt(this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foldLeft(BoxesRunTime.boxToInteger(0), (Function2<Object, ByteArrayIterator, Object> & Serializable)(B x$25, A x$26) -> BoxesRunTime.boxToInteger(MultiByteArrayIterator.$anonfun$copyToBuffer$1(buffer, BoxesRunTime.unboxToInt(x$25), x$26))));
            this.akka$util$ByteIterator$MultiByteArrayIterator$$normalize();
            return n;
        }

        @Override
        public InputStream asInputStream() {
            return new InputStream(this){
                private final /* synthetic */ MultiByteArrayIterator $outer;

                public int available() {
                    return this.$outer.akka$util$ByteIterator$MultiByteArrayIterator$$current().len();
                }

                public int read() {
                    if (this.$outer.hasNext()) {
                        return this.$outer.next() & 0xFF;
                    }
                    return -1;
                }

                public int read(byte[] b, int off, int len) {
                    int nRead = this.$outer.akka$util$ByteIterator$MultiByteArrayIterator$$current().asInputStream().read(b, off, len);
                    this.$outer.akka$util$ByteIterator$MultiByteArrayIterator$$normalize();
                    return nRead;
                }

                public long skip(long n) {
                    return this.skipImpl$1(n, 0L);
                }

                private final long skipImpl$1(long n, long skipped) {
                    while (n > 0L) {
                        if (!this.$outer.isEmpty()) {
                            long m4 = this.$outer.akka$util$ByteIterator$MultiByteArrayIterator$$current().asInputStream().skip(n);
                            this.$outer.akka$util$ByteIterator$MultiByteArrayIterator$$normalize();
                            long newN = n - m4;
                            long newSkipped = skipped + m4;
                            if (newN > 0L) {
                                skipped = newSkipped;
                                n = newN;
                                continue;
                            }
                            return newSkipped;
                        }
                        return 0L;
                    }
                    return 0L;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                }
            };
        }

        @Override
        public void foreach$mZc$sp(Function1<Object, Object> f) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foreach((Function1<ByteArrayIterator, Object> & Serializable)(A x$4) -> {
                x$4.foreach$mZc$sp(f);
                return BoxedUnit.UNIT;
            });
            this.clear();
        }

        @Override
        public void foreach$mBc$sp(Function1<Object, Object> f) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foreach((Function1<ByteArrayIterator, Object> & Serializable)(A x$4) -> {
                x$4.foreach$mBc$sp(f);
                return BoxedUnit.UNIT;
            });
            this.clear();
        }

        @Override
        public void foreach$mCc$sp(Function1<Object, Object> f) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foreach((Function1<ByteArrayIterator, Object> & Serializable)(A x$4) -> {
                x$4.foreach$mCc$sp(f);
                return BoxedUnit.UNIT;
            });
            this.clear();
        }

        @Override
        public void foreach$mDc$sp(Function1<Object, Object> f) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foreach((Function1<ByteArrayIterator, Object> & Serializable)(A x$4) -> {
                x$4.foreach$mDc$sp(f);
                return BoxedUnit.UNIT;
            });
            this.clear();
        }

        @Override
        public void foreach$mFc$sp(Function1<Object, Object> f) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foreach((Function1<ByteArrayIterator, Object> & Serializable)(A x$4) -> {
                x$4.foreach$mFc$sp(f);
                return BoxedUnit.UNIT;
            });
            this.clear();
        }

        @Override
        public void foreach$mIc$sp(Function1<Object, Object> f) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foreach((Function1<ByteArrayIterator, Object> & Serializable)(A x$4) -> {
                x$4.foreach$mIc$sp(f);
                return BoxedUnit.UNIT;
            });
            this.clear();
        }

        @Override
        public void foreach$mJc$sp(Function1<Object, Object> f) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foreach((Function1<ByteArrayIterator, Object> & Serializable)(A x$4) -> {
                x$4.foreach$mJc$sp(f);
                return BoxedUnit.UNIT;
            });
            this.clear();
        }

        @Override
        public void foreach$mSc$sp(Function1<Object, Object> f) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foreach((Function1<ByteArrayIterator, Object> & Serializable)(A x$4) -> {
                x$4.foreach$mSc$sp(f);
                return BoxedUnit.UNIT;
            });
            this.clear();
        }

        @Override
        public void foreach$mVc$sp(Function1<Object, BoxedUnit> f) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators().foreach((Function1<ByteArrayIterator, Object> & Serializable)(A x$4) -> {
                x$4.foreach$mVc$sp(f);
                return BoxedUnit.UNIT;
            });
            this.clear();
        }

        private final LinearSeq norm$1(LinearSeq xs) {
            while (true) {
                if (xs.isEmpty()) {
                    return ByteIterator$MultiByteArrayIterator$.MODULE$.clearedList();
                }
                if (!((Iterator)xs.head()).isEmpty()) break;
                xs = (LinearSeq)xs.tail();
            }
            return xs;
        }

        public static final /* synthetic */ int $anonfun$len$1(int x$1, ByteArrayIterator x$2) {
            return x$1 + x$2.len();
        }

        public static final /* synthetic */ void $anonfun$getBytes$2(MultiByteArrayIterator $this, byte[] x$7, int x$8, int x$9) {
            $this.akka$util$ByteIterator$MultiByteArrayIterator$$current().getBytes(x$7, x$8, x$9);
        }

        public static final /* synthetic */ void $anonfun$getShorts$2(MultiByteArrayIterator $this, ByteOrder byteOrder$1, short[] x$10, int x$11, int x$12) {
            $this.akka$util$ByteIterator$MultiByteArrayIterator$$current().getShorts(x$10, x$11, x$12, byteOrder$1);
        }

        public static final /* synthetic */ void $anonfun$getInts$2(MultiByteArrayIterator $this, ByteOrder byteOrder$2, int[] x$13, int x$14, int x$15) {
            $this.akka$util$ByteIterator$MultiByteArrayIterator$$current().getInts(x$13, x$14, x$15, byteOrder$2);
        }

        public static final /* synthetic */ void $anonfun$getLongs$2(MultiByteArrayIterator $this, ByteOrder byteOrder$3, long[] x$16, int x$17, int x$18) {
            $this.akka$util$ByteIterator$MultiByteArrayIterator$$current().getLongs(x$16, x$17, x$18, byteOrder$3);
        }

        public static final /* synthetic */ void $anonfun$getFloats$2(MultiByteArrayIterator $this, ByteOrder byteOrder$4, float[] x$19, int x$20, int x$21) {
            $this.akka$util$ByteIterator$MultiByteArrayIterator$$current().getFloats(x$19, x$20, x$21, byteOrder$4);
        }

        public static final /* synthetic */ void $anonfun$getDoubles$2(MultiByteArrayIterator $this, ByteOrder byteOrder$5, double[] x$22, int x$23, int x$24) {
            $this.akka$util$ByteIterator$MultiByteArrayIterator$$current().getDoubles(x$22, x$23, x$24, byteOrder$5);
        }

        public static final /* synthetic */ int $anonfun$copyToBuffer$1(ByteBuffer buffer$1, int x$25, ByteArrayIterator x$26) {
            return x$25 + x$26.copyToBuffer(buffer$1);
        }

        public MultiByteArrayIterator(LinearSeq<ByteArrayIterator> iterators) {
            this.akka$util$ByteIterator$MultiByteArrayIterator$$iterators = iterators;
            this.akka$util$ByteIterator$MultiByteArrayIterator$$normalize();
        }
    }
}

