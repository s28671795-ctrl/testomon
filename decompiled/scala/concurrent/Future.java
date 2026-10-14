/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceExtensionMethods$;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.collection.mutable.ArrayBuffer$;
import scala.collection.mutable.Builder;
import scala.concurrent.Awaitable;
import scala.concurrent.BatchingExecutor;
import scala.concurrent.ExecutionContext;
import scala.concurrent.ExecutionContext$parasitic$;
import scala.concurrent.Future$;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u001d\rdaB-[!\u0003\r\ta\u0018\u0005\u0006i\u0002!\t!\u001e\u0005\u0006s\u00021\tA\u001f\u0005\b\u0003C\u0001a\u0011AA\u0012\u0011\u001d\tY\u0003\u0001D\u0001\u0003[Aq!!\u000e\u0001\t\u0003\t9\u0004C\u0004\u0002T\u0001!\t!!\u0016\t\u000f\u0005\u0015\u0004\u0001\"\u0001\u0002h!9\u0011Q\r\u0001\u0007\u0002\u0005\u0005\u0005bBAK\u0001\u0019\u0005\u0011q\u0013\u0005\b\u0003S\u0003A\u0011AAV\u0011\u001d\ti\f\u0001C\u0001\u0003\u007fCq!!5\u0001\t\u0003\t\u0019\u000eC\u0004\u0002h\u0002!\t!!;\t\u000f\u0005]\b\u0001\"\u0002\u0002z\"9!\u0011\u0001\u0001\u0005\u0002\t\r\u0001b\u0002B\u000e\u0001\u0011\u0005!Q\u0004\u0005\b\u0005c\u0001A\u0011\u0001B\u001a\u0011\u001d\u0011)\u0005\u0001C\u0001\u0005\u000fBqA!\u0018\u0001\t\u0003\u0011y\u0006C\u0004\u0003\u0002\u0002!\tAa!\t\u000f\t=\u0005\u0001\"\u0001\u0003\u0012\"9!1\u0016\u0001\u0005\u0002\t5va\u0002B_5\"\u0005!q\u0018\u0004\u00073jC\tA!1\t\u000f\t\r\u0007\u0004\"\u0001\u0003F\"Q!q\u0019\rC\u0002\u0013\u0015!L!3\t\u0011\tM\b\u0004)A\u0007\u0005\u0017D\u0001Ba@\u0019A\u000351\u0011\u0001\u0005\t\u0007\u0007ABQ\u0001.\u0004\u0006!Q1q\u0002\rC\u0002\u0013\u0015!l!\u0005\t\u0011\rU\u0001\u0004)A\u0007\u0007'A!ba\u0006\u0019\u0005\u0004%)AWB\r\u0011!\u0019\t\u0003\u0007Q\u0001\u000e\rm\u0001\u0002CB\u00121\u0001\u0006iaa\u0007\t\u0015\r\u0015\u0002D1A\u0005\u0006i\u001b9\u0003\u0003\u0005\u0004,a\u0001\u000bQBB\u0015\u0011!\u0019i\u0003\u0007Q\u0001\u000e\r=\u0002\u0002CB\u001b1\u0011\u0015!la\u000e\t\u0015\r\r\u0003D1A\u0005\u0006i\u001b9\u0003\u0003\u0005\u0004Fa\u0001\u000bQBB\u0015\u0011)\u00199\u0005\u0007b\u0001\n\u000bQ6\u0011\n\u0005\t\u0007\u001bB\u0002\u0015!\u0004\u0004L!A1q\n\r!\u0002\u001b\u0019\t\u0006\u0003\u0005\u0004Va!)AWB,\u0011!\u00199\u0007\u0007Q\u0001\u000e\r%\u0004\u0002CB<1\u0011\u0015!l!\u001f\b\u000f\r5\u0005\u0004#\u0001\u0004\u0010\u001a911\u0013\r\t\u0002\rU\u0005b\u0002Bba\u0011\u00051q\u0013\u0005\b\u00073\u0003DQIBN\u0011\u001d!9\u0002\rC#\t3Aa!\u001f\u0019\u0005F\u0011e\u0002bBA\u0011a\u0011\u0015\u00131\u0005\u0005\b\u0003W\u0001DQ\tC&\u0011\u001d\t)\u0004\rC#\u0003oAq!a\u00151\t\u000b\"y\u0005C\u0004\u0002fA\")\u0005b\u0018\t\u000f\u0005\u0015\u0004\u0007\"\u0012\u0005t!9\u0011Q\u0013\u0019\u0005F\u0011\u001d\u0005bBAUa\u0011\u0015C\u0011\u0014\u0005\b\u0003{\u0003DQ\tCV\u0011\u001d\t\t\u000e\rC#\t{Cq!a:1\t\u000b\"Y\rC\u0004\u0003\u0002A\")\u0005\"6\t\u000f\tm\u0001\u0007\"\u0012\u0005h\"9!\u0011\u0007\u0019\u0005F\u0011e\bb\u0002B#a\u0011\u0015S1\u0002\u0005\b\u0005;\u0002DQIC\u000e\u0011\u001d\u0011\t\t\rC#\u000boAqAa$1\t\u000b*\u0019\u0005C\u0004\u0003,B\")%\"\u0015\t\u000f\u0015\u0005\u0004\u0007\"\u0012\u0006d!IQQ\r\rC\u0002\u0013\u0015Qq\r\u0005\t\u000bWB\u0002\u0015!\u0004\u0006j!9\u0011Q\u0007\r\u0005\u0006\u00155\u0004bBC>1\u0011\u0015QQ\u0010\u0005\b\u000b\u0013CBQACF\u0011\u001d)I\n\u0007C\u0003\u000b7Cq!b-\u0019\t\u000b))\fC\u0004\u0006Hb!)!\"3\t\u000f\u0019\u0015\u0001\u0004\"\u0002\u0007\b!9a1\u0004\r\u0005\u0006\u0019u\u0001b\u0002D\u001f1\u0011\u0015aq\b\u0005\t\rKB\u0002\u0015\"\u0004\u0007h!9aQ\u0012\r\u0005\u0002\u0019=\u0005b\u0002Dr1\u0011\u0015aQ\u001d\u0005\b\u000f\u0017ABQAD\u0007\u0011\u001d9i\u0003\u0007C\u0003\u000f_\u0011aAR;ukJ,'BA.]\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0002;\u0006)1oY1mC\u000e\u0001QC\u00011l'\r\u0001\u0011-\u001a\t\u0003E\u000el\u0011\u0001X\u0005\u0003Ir\u0013a!\u00118z%\u00164\u0007c\u00014hS6\t!,\u0003\u0002i5\nI\u0011i^1ji\u0006\u0014G.\u001a\t\u0003U.d\u0001\u0001\u0002\u0004m\u0001\u0011\u0015\r!\u001c\u0002\u0002)F\u0011a.\u001d\t\u0003E>L!\u0001\u001d/\u0003\u000f9{G\u000f[5oOB\u0011!M]\u0005\u0003gr\u00131!\u00118z\u0003\u0019!\u0013N\\5uIQ\ta\u000f\u0005\u0002co&\u0011\u0001\u0010\u0018\u0002\u0005+:LG/\u0001\u0006p]\u000e{W\u000e\u001d7fi\u0016,2a_A\u000f)\ra\u0018Q\u0001\u000b\u0003mvDQA \u0002A\u0004}\f\u0001\"\u001a=fGV$xN\u001d\t\u0004M\u0006\u0005\u0011bAA\u00025\n\u0001R\t_3dkRLwN\\\"p]R,\u0007\u0010\u001e\u0005\b\u0003\u000f\u0011\u0001\u0019AA\u0005\u0003\u00051\u0007c\u00022\u0002\f\u0005=\u00111D\u0005\u0004\u0003\u001ba&!\u0003$v]\u000e$\u0018n\u001c82!\u0015\t\t\"a\u0006j\u001b\t\t\u0019BC\u0002\u0002\u0016q\u000bA!\u001e;jY&!\u0011\u0011DA\n\u0005\r!&/\u001f\t\u0004U\u0006uAABA\u0010\u0005\t\u0007QNA\u0001V\u0003-I7oQ8na2,G/\u001a3\u0016\u0005\u0005\u0015\u0002c\u00012\u0002(%\u0019\u0011\u0011\u0006/\u0003\u000f\t{w\u000e\\3b]\u0006)a/\u00197vKV\u0011\u0011q\u0006\t\u0006E\u0006E\u0012qB\u0005\u0004\u0003ga&AB(qi&|g.\u0001\u0004gC&dW\rZ\u000b\u0003\u0003s\u0001BA\u001a\u0001\u0002<A!\u0011QHA'\u001d\u0011\ty$!\u0013\u000f\t\u0005\u0005\u0013qI\u0007\u0003\u0003\u0007R1!!\u0012_\u0003\u0019a$o\\8u}%\tQ,C\u0002\u0002Lq\u000bq\u0001]1dW\u0006<W-\u0003\u0003\u0002P\u0005E#!\u0003+ie><\u0018M\u00197f\u0015\r\tY\u0005X\u0001\bM>\u0014X-Y2i+\u0011\t9&a\u0019\u0015\t\u0005e\u0013Q\f\u000b\u0004m\u0006m\u0003\"\u0002@\u0007\u0001\by\bbBA\u0004\r\u0001\u0007\u0011q\f\t\u0007E\u0006-\u0011.!\u0019\u0011\u0007)\f\u0019\u0007\u0002\u0004\u0002 \u0019\u0011\r!\\\u0001\niJ\fgn\u001d4pe6,B!!\u001b\u0002rQ1\u00111NA<\u0003{\"B!!\u001c\u0002vA!a\rAA8!\rQ\u0017\u0011\u000f\u0003\u0007\u0003g:!\u0019A7\u0003\u0003MCQA`\u0004A\u0004}Dq!!\u001f\b\u0001\u0004\tY(A\u0001t!\u0019\u0011\u00171B5\u0002p!9\u0011qA\u0004A\u0002\u0005}\u0004c\u00022\u0002\f\u0005m\u00121H\u000b\u0005\u0003\u0007\u000bY\t\u0006\u0003\u0002\u0006\u0006=E\u0003BAD\u0003\u001b\u0003BA\u001a\u0001\u0002\nB\u0019!.a#\u0005\r\u0005M\u0004B1\u0001n\u0011\u0015q\b\u0002q\u0001\u0000\u0011\u001d\t9\u0001\u0003a\u0001\u0003#\u0003rAYA\u0006\u0003\u001f\t\u0019\n\u0005\u0004\u0002\u0012\u0005]\u0011\u0011R\u0001\u000eiJ\fgn\u001d4pe6<\u0016\u000e\u001e5\u0016\t\u0005e\u0015\u0011\u0015\u000b\u0005\u00037\u000b)\u000b\u0006\u0003\u0002\u001e\u0006\r\u0006\u0003\u00024\u0001\u0003?\u00032A[AQ\t\u0019\t\u0019(\u0003b\u0001[\")a0\u0003a\u0002\u007f\"9\u0011qA\u0005A\u0002\u0005\u001d\u0006c\u00022\u0002\f\u0005=\u0011QT\u0001\u0004[\u0006\u0004X\u0003BAW\u0003k#B!a,\u0002:R!\u0011\u0011WA\\!\u00111\u0007!a-\u0011\u0007)\f)\f\u0002\u0004\u0002t)\u0011\r!\u001c\u0005\u0006}*\u0001\u001da \u0005\b\u0003\u000fQ\u0001\u0019AA^!\u0019\u0011\u00171B5\u00024\u00069a\r\\1u\u001b\u0006\u0004X\u0003BAa\u0003\u0013$B!a1\u0002NR!\u0011QYAf!\u00111\u0007!a2\u0011\u0007)\fI\r\u0002\u0004\u0002t-\u0011\r!\u001c\u0005\u0006}.\u0001\u001da \u0005\b\u0003\u000fY\u0001\u0019AAh!\u0019\u0011\u00171B5\u0002F\u00069a\r\\1ui\u0016tW\u0003BAk\u00037$B!a6\u0002^B!a\rAAm!\rQ\u00171\u001c\u0003\u0007\u0003gb!\u0019A7\t\u000f\u0005}G\u0002q\u0001\u0002b\u0006\u0011QM\u001e\t\u0007E\u0006\r\u0018.a6\n\u0007\u0005\u0015HL\u0001\t%Y\u0016\u001c8\u000fJ2pY>tG\u0005\\3tg\u00061a-\u001b7uKJ$B!a;\u0002rR!\u0011Q^Ax!\r1\u0007!\u001b\u0005\u0006}6\u0001\u001da \u0005\b\u0003gl\u0001\u0019AA{\u0003\u0005\u0001\bC\u00022\u0002\f%\f)#\u0001\u0006xSRDg)\u001b7uKJ$B!a?\u0002\u0000R!\u0011Q^A\u007f\u0011\u0015qh\u0002q\u0001\u0000\u0011\u001d\t\u0019P\u0004a\u0001\u0003k\fqaY8mY\u0016\u001cG/\u0006\u0003\u0003\u0006\t5A\u0003\u0002B\u0004\u0005#!BA!\u0003\u0003\u0010A!a\r\u0001B\u0006!\rQ'Q\u0002\u0003\u0007\u0003gz!\u0019A7\t\u000by|\u00019A@\t\u000f\tMq\u00021\u0001\u0003\u0016\u0005\u0011\u0001O\u001a\t\u0007E\n]\u0011Na\u0003\n\u0007\teALA\bQCJ$\u0018.\u00197Gk:\u001cG/[8o\u0003\u001d\u0011XmY8wKJ,BAa\b\u0003(Q!!\u0011\u0005B\u0017)\u0011\u0011\u0019Ca\u000b\u0011\t\u0019\u0004!Q\u0005\t\u0004U\n\u001dBaBA\u0010!\t\u0007!\u0011F\t\u0003SFDQA \tA\u0004}DqAa\u0005\u0011\u0001\u0004\u0011y\u0003E\u0004c\u0005/\tYD!\n\u0002\u0017I,7m\u001c<fe^KG\u000f[\u000b\u0005\u0005k\u0011i\u0004\u0006\u0003\u00038\t\u0005C\u0003\u0002B\u001d\u0005\u007f\u0001BA\u001a\u0001\u0003<A\u0019!N!\u0010\u0005\u000f\u0005}\u0011C1\u0001\u0003*!)a0\u0005a\u0002\u007f\"9!1C\tA\u0002\t\r\u0003c\u00022\u0003\u0018\u0005m\"\u0011H\u0001\u0004u&\u0004X\u0003\u0002B%\u0005+\"BAa\u0013\u0003XA!a\r\u0001B'!\u0019\u0011'qJ5\u0003T%\u0019!\u0011\u000b/\u0003\rQ+\b\u000f\\33!\rQ'Q\u000b\u0003\u0007\u0003?\u0011\"\u0019A7\t\u000f\te#\u00031\u0001\u0003\\\u0005!A\u000f[1u!\u00111\u0007Aa\u0015\u0002\u000fiL\u0007oV5uQV1!\u0011\rB>\u0005W\"BAa\u0019\u0003~Q!!Q\rB9)\u0011\u00119Ga\u001c\u0011\t\u0019\u0004!\u0011\u000e\t\u0004U\n-DA\u0002B7'\t\u0007QNA\u0001S\u0011\u0015q8\u0003q\u0001\u0000\u0011\u001d\t9a\u0005a\u0001\u0005g\u0002\u0002B\u0019B;S\ne$\u0011N\u0005\u0004\u0005ob&!\u0003$v]\u000e$\u0018n\u001c83!\rQ'1\u0010\u0003\u0007\u0003?\u0019\"\u0019A7\t\u000f\te3\u00031\u0001\u0003\u0000A!a\r\u0001B=\u0003)1\u0017\r\u001c7cC\u000e\\Gk\\\u000b\u0005\u0005\u000b\u0013Y\t\u0006\u0003\u0003\b\n5\u0005\u0003\u00024\u0001\u0005\u0013\u00032A\u001bBF\t\u001d\ty\u0002\u0006b\u0001\u0005SAqA!\u0017\u0015\u0001\u0004\u00119)A\u0003nCB$v.\u0006\u0003\u0003\u0014\neE\u0003\u0002BK\u00057\u0003BA\u001a\u0001\u0003\u0018B\u0019!N!'\u0005\r\u0005MTC1\u0001n\u0011\u001d\u0011i*\u0006a\u0002\u0005?\u000b1\u0001^1h!\u0019\u0011\tKa*\u0003\u00186\u0011!1\u0015\u0006\u0004\u0005Kc\u0016a\u0002:fM2,7\r^\u0005\u0005\u0005S\u0013\u0019K\u0001\u0005DY\u0006\u001c8\u000fV1h\u0003\u001d\tg\u000e\u001a+iK:,BAa,\u0003<R!!\u0011\u0017B[)\u0011\tiOa-\t\u000by4\u00029A@\t\u000f\tMa\u00031\u0001\u00038B9!Ma\u0006\u0002\u0010\te\u0006c\u00016\u0003<\u00121\u0011q\u0004\fC\u00025\faAR;ukJ,\u0007C\u00014\u0019'\tA\u0012-\u0001\u0004=S:LGO\u0010\u000b\u0003\u0005\u007f\u000bq\u0001^8C_b,G-\u0006\u0002\u0003LBA!Q\u001aBl\u00057\u0014)0\u0004\u0002\u0003P*!!\u0011\u001bBj\u0003%IW.\\;uC\ndWMC\u0002\u0003Vr\u000b!bY8mY\u0016\u001cG/[8o\u0013\u0011\u0011INa4\u0003\u00075\u000b\u0007\u000f\r\u0003\u0003^\n=\bC\u0002Bp\u0005S\u0014i/\u0004\u0002\u0003b*!!1\u001dBs\u0003\u0011a\u0017M\\4\u000b\u0005\t\u001d\u0018\u0001\u00026bm\u0006LAAa;\u0003b\n)1\t\\1tgB\u0019!Na<\u0005\u0015\tE8$!A\u0001\u0002\u000b\u0005QNA\u0002`IE\n\u0001\u0002^8C_b,G\r\t\u0019\u0005\u0005o\u0014Y\u0010\u0005\u0004\u0003`\n%(\u0011 \t\u0004U\nmHA\u0003B\u007f7\u0005\u0005\t\u0011!B\u0001[\n\u0019q\f\n\u001a\u0002\u0013}\u001b\u0017m\u00195fI&#\u0007#\u00022\u0002\f\u0005\f\u0017AA5e+\u0011\u00199a!\u0004\u0016\u0005\r%\u0001c\u00022\u0002\f\r-11\u0002\t\u0004U\u000e5A!\u00027\u001e\u0005\u0004i\u0017!D2pY2,7\r\u001e$bS2,G-\u0006\u0002\u0004\u0014A)!-a\u0003r]\u0006q1m\u001c7mK\u000e$h)Y5mK\u0012\u0004\u0013!\u00044jYR,'OR1jYV\u0014X-\u0006\u0002\u0004\u001cA)\u0011\u0011CB\u000f]&!1qDA\n\u0005\u001d1\u0015-\u001b7ve\u0016\faBZ5mi\u0016\u0014h)Y5mkJ,\u0007%A\u0007gC&dW\r\u001a$bS2,(/Z\u0001\u0014M\u0006LG.\u001a3GC&dWO]3GkR,(/Z\u000b\u0003\u0007S\u00012A\u001a\u0001o\u0003Q1\u0017-\u001b7fI\u001a\u000b\u0017\u000e\\;sK\u001a+H/\u001e:fA\u0005QqLZ1jY\u0016$g)\u001e8\u0011\u000f\t\fYa!\r\u00044A)\u0011\u0011CA\fcB1\u0011\u0011CA\f\u0003w\t\u0011BZ1jY\u0016$g)\u001e8\u0016\t\re2\u0011I\u000b\u0003\u0007w\u0001rAYA\u0006\u0007{\u0019\u0019\u0004\u0005\u0004\u0002\u0012\u0005]1q\b\t\u0004U\u000e\u0005C!\u00027'\u0005\u0004i\u0017a\u0006:fG>4XM],ji\"4\u0015-\u001b7fI6\u000b'o[3s\u0003a\u0011XmY8wKJ<\u0016\u000e\u001e5GC&dW\rZ'be.,'\u000fI\u0001\u0012e\u0016\u001cwN^3s/&$\bNR1jY\u0016$WCAB&!\u001d\u0011\u00171BA\u001e\u0007S\t!C]3d_Z,'oV5uQ\u001a\u000b\u0017\u000e\\3eA\u0005qqL_5q/&$\b\u000eV;qY\u0016\u0014\u0004c\u00022\u0003vE\f81\u000b\t\u0006E\n=\u0013/]\u0001\u0011u&\u0004x+\u001b;i)V\u0004H.\u001a\u001aGk:,ba!\u0017\u0004`\r\rTCAB.!%\u0011'QOB/\u0007C\u001a)\u0007E\u0002k\u0007?\"Q\u0001\u001c\u0017C\u00025\u00042A[B2\t\u0019\ty\u0002\fb\u0001[B9!Ma\u0014\u0004^\r\u0005\u0014\u0001E0bI\u0012$vNQ;jY\u0012,'OR;o!!\u0011'QOB6c\u000e-\u0004CBB7\u0007g\nh.\u0004\u0002\u0004p)!1\u0011\u000fBj\u0003\u001diW\u000f^1cY\u0016LAa!\u001e\u0004p\t9!)^5mI\u0016\u0014\u0018aD1eIR{')^5mI\u0016\u0014h)\u001e8\u0016\r\rm41QBE+\t\u0019i\bE\u0005c\u0005k\u001ayh!!\u0004\u0000AA1QNB:\u0007\u0003\u001b9\tE\u0002k\u0007\u0007#aa!\"/\u0005\u0004i'!A!\u0011\u0007)\u001cI\t\u0002\u0004\u0004\f:\u0012\r!\u001c\u0002\u0002\u001b\u0006)a.\u001a<feB\u00191\u0011\u0013\u0019\u000e\u0003a\u0011QA\\3wKJ\u001cB\u0001M1\u0004*Q\u00111qR\u0001\u0006e\u0016\fG-\u001f\u000b\u0005\u0007;\u001bY\u000b\u0006\u0003\u0004 \u000e\u0005V\"\u0001\u0019\t\u000f\r\r&\u0007q\u0001\u0004&\u00061\u0001/\u001a:nSR\u00042AZBT\u0013\r\u0019IK\u0017\u0002\t\u0007\u0006t\u0017i^1ji\"91Q\u0016\u001aA\u0002\r=\u0016AB1u\u001b>\u001cH\u000f\u0005\u0003\u00042\u000e]VBABZ\u0015\r\u0019)LW\u0001\tIV\u0014\u0018\r^5p]&!1\u0011XBZ\u0005!!UO]1uS>t\u0007&\u0002\u001a\u0004>\u000e%\u0007#\u00022\u0004@\u000e\r\u0017bABa9\n1A\u000f\u001b:poN\u0004B!!\u0010\u0004F&!1qYA)\u0005QIe\u000e^3seV\u0004H/\u001a3Fq\u000e,\u0007\u000f^5p]F:ada3\u0004\\\u000em\b\u0003BBg\u0007+tAaa4\u0004RB\u0019\u0011\u0011\t/\n\u0007\rMG,\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0007/\u001cIN\u0001\u0004TiJLgn\u001a\u0006\u0004\u0007'd\u0016'C\u0012\u0004^\u000e\r8\u0011_Bs+\u0011\u0019yn!9\u0016\u0005\r-GA\u00027_\u0005\u0004\u0019Y/\u0003\u0003\u0004f\u000e\u001d\u0018a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013GC\u0002\u0004jr\u000ba\u0001\u001e5s_^\u001c\u0018c\u00018\u0004nB!1q^A'\u001d\r\u0011\u0017\u0011J\u0019\nG\rM8Q_B|\u0007St1AYB{\u0013\r\u0019I\u000fX\u0019\u0006E\td6\u0011 \u0002\u0006g\u000e\fG.Y\u0019\u0004M\r\r\u0007&\u0002\u001a\u0004\u0000\u00125\u0001#\u00022\u0004@\u0012\u0005\u0001\u0003\u0002C\u0002\t\u000fq1A\u001aC\u0003\u0013\r\tYEW\u0005\u0005\t\u0013!YA\u0001\tUS6,w.\u001e;Fq\u000e,\u0007\u000f^5p]*\u0019\u00111\n.2\u000fy\u0019Y\rb\u0004\u0005\u0016EJ1e!8\u0004d\u0012E1Q]\u0019\nG\rM8Q\u001fC\n\u0007S\fTA\t2]\u0007s\f4A\nC\u0001\u0003\u0019\u0011Xm];miR!A1\u0004C\u0010)\rqGQ\u0004\u0005\b\u0007G\u001b\u00049ABS\u0011\u001d\u0019ik\ra\u0001\u0007_CSaMB_\tG\ttAHBf\tK!Y#M\u0005$\u0007;\u001c\u0019\u000fb\n\u0004fFJ1ea=\u0004v\u0012%2\u0011^\u0019\u0006E\td6\u0011`\u0019\u0004M\r\r\u0007&B\u001a\u0004\u0000\u0012=\u0012g\u0002\u0010\u0004L\u0012EBqG\u0019\nG\ru71\u001dC\u001a\u0007K\f\u0014bIBz\u0007k$)d!;2\u000b\t\u0012Gl!?2\u0007\u0019\"\t!\u0006\u0003\u0005<\u0011%C\u0003\u0002C\u001f\t\u0003\"2A\u001eC \u0011\u0015qH\u0007q\u0001\u0000\u0011\u001d\t9\u0001\u000ea\u0001\t\u0007\u0002rAYA\u0006\t\u000b\"9\u0005E\u0003\u0002\u0012\u0005]a\u000eE\u0002k\t\u0013\"a!a\b5\u0005\u0004iWC\u0001C'!\u0015\u0011\u0017\u0011\u0007C#+\u0011!\t\u0006\"\u0018\u0015\t\u0011MCq\u000b\u000b\u0004m\u0012U\u0003\"\u0002@9\u0001\by\bbBA\u0004q\u0001\u0007A\u0011\f\t\u0007E\u0006-a\u000eb\u0017\u0011\u0007)$i\u0006\u0002\u0004\u0002 a\u0012\r!\\\u000b\u0005\tC\"I\u0007\u0006\u0004\u0005d\u00115D\u0011\u000f\u000b\u0005\tK\"Y\u0007\u0005\u0003g\u0001\u0011\u001d\u0004c\u00016\u0005j\u00111\u00111O\u001dC\u00025DQA`\u001dA\u0004}Dq!!\u001f:\u0001\u0004!y\u0007\u0005\u0004c\u0003\u0017qGq\r\u0005\b\u0003\u000fI\u0004\u0019AA@+\u0011!)\b\" \u0015\t\u0011]D\u0011\u0011\u000b\u0005\ts\"y\b\u0005\u0003g\u0001\u0011m\u0004c\u00016\u0005~\u00111\u00111\u000f\u001eC\u00025DQA \u001eA\u0004}Dq!a\u0002;\u0001\u0004!\u0019\tE\u0004c\u0003\u0017!)\u0005\"\"\u0011\r\u0005E\u0011q\u0003C>+\u0011!I\t\"%\u0015\t\u0011-EQ\u0013\u000b\u0005\t\u001b#\u0019\n\u0005\u0003g\u0001\u0011=\u0005c\u00016\u0005\u0012\u00121\u00111O\u001eC\u00025DQA`\u001eA\u0004}Dq!a\u0002<\u0001\u0004!9\nE\u0004c\u0003\u0017!)\u0005\"$\u0016\t\u0011mE1\u0015\u000b\u0005\t;#9\u000b\u0006\u0003\u0005 \u0012\u0015\u0006\u0003\u00024\u0001\tC\u00032A\u001bCR\t\u0019\t\u0019\b\u0010b\u0001[\")a\u0010\u0010a\u0002\u007f\"9\u0011q\u0001\u001fA\u0002\u0011%\u0006C\u00022\u0002\f9$\t+\u0006\u0003\u0005.\u0012UF\u0003\u0002CX\ts#B\u0001\"-\u00058B!a\r\u0001CZ!\rQGQ\u0017\u0003\u0007\u0003gj$\u0019A7\t\u000byl\u00049A@\t\u000f\u0005\u001dQ\b1\u0001\u0005<B1!-a\u0003o\tc+B\u0001b0\u0005FR!A\u0011\u0019Cd!\u00111\u0007\u0001b1\u0011\u0007)$)\r\u0002\u0004\u0002ty\u0012\r!\u001c\u0005\b\u0003?t\u00049\u0001Ce!\u0019\u0011\u00171\u001d8\u0005BR!AQ\u001aCi)\u0011\u0019I\u0003b4\t\u000by|\u00049A@\t\u000f\u0005Mx\b1\u0001\u0005TB1!-a\u0003o\u0003K)B\u0001b6\u0005`R!A\u0011\u001cCr)\u0011!Y\u000e\"9\u0011\t\u0019\u0004AQ\u001c\t\u0004U\u0012}GABA:\u0001\n\u0007Q\u000eC\u0003\u007f\u0001\u0002\u000fq\u0010C\u0004\u0003\u0014\u0001\u0003\r\u0001\":\u0011\r\t\u00149B\u001cCo+\u0011!I\u000f\"=\u0015\t\u0011-HQ\u001f\u000b\u0005\t[$\u0019\u0010\u0005\u0003g\u0001\u0011=\bc\u00016\u0005r\u00121\u0011qD!C\u00025DQA`!A\u0004}DqAa\u0005B\u0001\u0004!9\u0010E\u0004c\u0005/\tY\u0004b<\u0016\t\u0011mX1\u0001\u000b\u0005\t{,9\u0001\u0006\u0003\u0005\u0000\u0016\u0015\u0001\u0003\u00024\u0001\u000b\u0003\u00012A[C\u0002\t\u0019\tyB\u0011b\u0001[\")aP\u0011a\u0002\u007f\"9!1\u0003\"A\u0002\u0015%\u0001c\u00022\u0003\u0018\u0005mBq`\u000b\u0005\u000b\u001b))\u0002\u0006\u0003\u0006\u0010\u0015]\u0001\u0003\u00024\u0001\u000b#\u0001bA\u0019B(]\u0016M\u0001c\u00016\u0006\u0016\u00111\u0011qD\"C\u00025DqA!\u0017D\u0001\u0004)I\u0002\u0005\u0003g\u0001\u0015MQCBC\u000f\u000bc)9\u0003\u0006\u0003\u0006 \u0015MB\u0003BC\u0011\u000bW!B!b\t\u0006*A!a\rAC\u0013!\rQWq\u0005\u0003\u0007\u0005[\"%\u0019A7\t\u000by$\u00059A@\t\u000f\u0005\u001dA\t1\u0001\u0006.AA!M!\u001eo\u000b_))\u0003E\u0002k\u000bc!a!a\bE\u0005\u0004i\u0007b\u0002B-\t\u0002\u0007QQ\u0007\t\u0005M\u0002)y#\u0006\u0003\u0006:\u0015}B\u0003BC\u001e\u000b\u0003\u0002BA\u001a\u0001\u0006>A\u0019!.b\u0010\u0005\r\u0005}QI1\u0001n\u0011\u001d\u0011I&\u0012a\u0001\u000bw)B!\"\u0012\u0006LQ!QqIC'!\u00111\u0007!\"\u0013\u0011\u0007),Y\u0005\u0002\u0004\u0002t\u0019\u0013\r!\u001c\u0005\b\u0005;3\u00059AC(!\u0019\u0011\tKa*\u0006JU!Q1KC0)\u0011))&\"\u0017\u0015\t\r%Rq\u000b\u0005\u0006}\u001e\u0003\u001da \u0005\b\u0005'9\u0005\u0019AC.!\u001d\u0011'q\u0003C#\u000b;\u00022A[C0\t\u0019\tyb\u0012b\u0001[\u0006AAo\\*ue&tw\r\u0006\u0002\u0004L\u0006!QO\\5u+\t)I\u0007E\u0002g\u0001Y\fQ!\u001e8ji\u0002*B!b\u001c\u0006vQ!Q\u0011OC<!\u00111\u0007!b\u001d\u0011\u0007),)\bB\u0003m\u0017\n\u0007Q\u000eC\u0004\u0006z-\u0003\r!a\u000f\u0002\u0013\u0015D8-\u001a9uS>t\u0017AC:vG\u000e,7o\u001d4vYV!QqPCC)\u0011)\t)b\"\u0011\t\u0019\u0004Q1\u0011\t\u0004U\u0016\u0015E!\u00027M\u0005\u0004i\u0007b\u0002C\f\u0019\u0002\u0007Q1Q\u0001\bMJ|W\u000e\u0016:z+\u0011)i)b%\u0015\t\u0015=UQ\u0013\t\u0005M\u0002)\t\nE\u0002k\u000b'#Q\u0001\\'C\u00025Dq\u0001b\u0006N\u0001\u0004)9\n\u0005\u0004\u0002\u0012\u0005]Q\u0011S\u0001\u0006CB\u0004H._\u000b\u0005\u000b;+)\u000b\u0006\u0003\u0006 \u0016%F\u0003BCQ\u000bO\u0003BA\u001a\u0001\u0006$B\u0019!.\"*\u0005\u000b1t%\u0019A7\t\u000byt\u00059A@\t\u0011\u0015-f\n\"a\u0001\u000b[\u000bAAY8esB)!-b,\u0006$&\u0019Q\u0011\u0017/\u0003\u0011q\u0012\u0017P\\1nKz\n\u0001\u0002Z3mK\u001e\fG/Z\u000b\u0005\u000bo+y\f\u0006\u0003\u0006:\u0016\rG\u0003BC^\u000b\u0003\u0004BA\u001a\u0001\u0006>B\u0019!.b0\u0005\u000b1|%\u0019A7\t\u000by|\u00059A@\t\u0011\u0015-v\n\"a\u0001\u000b\u000b\u0004RAYCX\u000bw\u000b\u0001b]3rk\u0016t7-Z\u000b\t\u000b\u0017,i0\":\u0006TR!QQ\u001aD\u0001)\u0019)y-b6\u0006\u0000B!a\rACi!\rQW1\u001b\u0003\u0007\u000b+\u0004&\u0019A7\u0003\u0005Q{\u0007bBCm!\u0002\u000fQ1\\\u0001\u0003E\u001a\u0004\"\"\"8\u0006`\u0016\rX1`Ci\u001b\t\u0011\u0019.\u0003\u0003\u0006b\nM'!\u0003\"vS2$gI]8n!\u0015QWQ]C}\t\u001d)9\u000f\u0015b\u0001\u000bS\u0014!aQ\"\u0016\t\u0015-XQ_\t\u0004]\u00165\bCBA\u001f\u000b_,\u00190\u0003\u0003\u0006r\u0006E#\u0001D%uKJ\f'\r\\3P]\u000e,\u0007c\u00016\u0006v\u00129Qq_Cs\u0005\u0004i'!\u0001-\u0011\t\u0019\u0004Q1 \t\u0004U\u0016uHABBC!\n\u0007Q\u000eC\u0003\u007f!\u0002\u000fq\u0010C\u0004\u0007\u0004A\u0003\r!b9\u0002\u0005%t\u0017\u0001\u00054jeN$8i\\7qY\u0016$X\rZ(g+\u00111IA\"\u0005\u0015\t\u0019-aQ\u0003\u000b\u0005\r\u001b1\u0019\u0002\u0005\u0003g\u0001\u0019=\u0001c\u00016\u0007\u0012\u0011)A.\u0015b\u0001[\")a0\u0015a\u0002\u007f\"9aqC)A\u0002\u0019e\u0011a\u00024viV\u0014Xm\u001d\t\u0007\u0003{)yO\"\u0004\u0002\t\u0019Lg\u000eZ\u000b\u0005\r?1Y\u0003\u0006\u0003\u0007\"\u0019MB\u0003\u0002D\u0012\r_!BA\"\n\u0007.A!a\r\u0001D\u0014!\u0015\u0011\u0017\u0011\u0007D\u0015!\rQg1\u0006\u0003\u0006YJ\u0013\r!\u001c\u0005\u0006}J\u0003\u001da \u0005\b\u0003g\u0014\u0006\u0019\u0001D\u0019!\u001d\u0011\u00171\u0002D\u0015\u0003KAqAb\u0006S\u0001\u00041)\u0004\u0005\u0004\u0003N\u001a]b1H\u0005\u0005\rs\u0011yM\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\u00111\u0007A\"\u000b\u0002\u0011\u0019|G\u000e\u001a'fMR,bA\"\u0011\u0007Z\u00195C\u0003\u0002D\"\r?\"BA\"\u0012\u0007\\Q!aq\tD))\u00111IEb\u0014\u0011\t\u0019\u0004a1\n\t\u0004U\u001a5CA\u0002B7'\n\u0007Q\u000eC\u0003\u007f'\u0002\u000fq\u0010C\u0004\u0007TM\u0003\rA\"\u0016\u0002\u0005=\u0004\b#\u00032\u0003v\u0019-cq\u000bD&!\rQg\u0011\f\u0003\u0006YN\u0013\r!\u001c\u0005\b\r;\u001a\u0006\u0019\u0001D&\u0003\u0011QXM]8\t\u000f\u0019]1\u000b1\u0001\u0007bA1!Q\u001aD\u001c\rG\u0002BA\u001a\u0001\u0007X\u0005Aam\u001c7e\u001d\u0016DH/\u0006\u0004\u0007j\u0019\re\u0011\u000f\u000b\t\rW2)H\"\"\u0007\nR!aQ\u000eD:!\u00111\u0007Ab\u001c\u0011\u0007)4\t\b\u0002\u0004\u0003nQ\u0013\r!\u001c\u0005\u0006}R\u0003\u001da \u0005\b\ro\"\u0006\u0019\u0001D=\u0003\u0005I\u0007CBA\u001f\rw2y(\u0003\u0003\u0007~\u0005E#\u0001C%uKJ\fGo\u001c:\u0011\t\u0019\u0004a\u0011\u0011\t\u0004U\u001a\rE!\u00027U\u0005\u0004i\u0007b\u0002DD)\u0002\u0007aqN\u0001\naJ,gOV1mk\u0016DqAb\u0015U\u0001\u00041Y\tE\u0005c\u0005k2yG\"!\u0007p\u0005!am\u001c7e+\u00191\tJb*\u0007\u001eR!a1\u0013De)\u00111)Jb2\u0015\t\u0019]e\u0011\u0015\u000b\u0005\r33y\n\u0005\u0003g\u0001\u0019m\u0005c\u00016\u0007\u001e\u00121!QN+C\u00025DQA`+A\u0004}DqAb\u0015V\u0001\u00041\u0019\u000bE\u0005c\u0005k2YJ\"*\u0007\u001cB\u0019!Nb*\u0005\u000b1,&\u0019A7)\u0011\u0019\u0005f1\u0016DY\rk\u00032A\u0019DW\u0013\r1y\u000b\u0018\u0002\u000fI\u0016\u0004(/Z2bi\u0016$g*Y7fC\t1\u0019,A\u0004g_2$g)\u001e82\u0013\r\u001aYMb.\u0007@\u001ae\u0016\u0002\u0002D]\rw\u000b1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012$b\u0001D_9\u0006qA-\u001a9sK\u000e\fG/\u001a3OC6,\u0017'C\u0012\u0007B\u001a\rgQ\u0019D_\u001d\r\u0011g1Y\u0005\u0004\r{c\u0016'\u0002\u0012c9\u000ee\bb\u0002D/+\u0002\u0007a1\u0014\u0005\b\r/)\u0006\u0019\u0001Df!\u0019\ti$b<\u0007NB!a\r\u0001DSQ-)f\u0011\u001bDl\r34iNb8\u0011\u0007\t4\u0019.C\u0002\u0007Vr\u0013!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f#Ab7\u00027U\u001cX\r\t$viV\u0014XM\f4pY\u0012dUM\u001a;!S:\u001cH/Z1e\u0003\u0015\u0019\u0018N\\2fC\t1\t/\u0001\u00043]E\u0012d\u0006M\u0001\u0007e\u0016$WoY3\u0016\r\u0019\u001dhq\u001fDy)\u00111IOb@\u0015\t\u0019-h1 \u000b\u0005\r[4I\u0010\u0005\u0003g\u0001\u0019=\bc\u00016\u0007r\u00129!Q\u000e,C\u0002\u0019M\u0018c\u0001D{cB\u0019!Nb>\u0005\u000b14&\u0019A7\t\u000by4\u00069A@\t\u000f\u0019Mc\u000b1\u0001\u0007~BI!M!\u001e\u0007p\u001aUhq\u001e\u0005\b\r/1\u0006\u0019AD\u0001!\u0019\ti$b<\b\u0004A!a\r\u0001D{Q-1f\u0011\u001bDl\u000f\u000f1iNb8\"\u0005\u001d%\u0011!H;tK\u00022U\u000f^;sK:\u0012X\rZ;dK2+g\r\u001e\u0011j]N$X-\u00193\u0002\u0015I,G-^2f\u0019\u00164G/\u0006\u0004\b\u0010\u001d}q\u0011\u0004\u000b\u0005\u000f#99\u0003\u0006\u0003\b\u0014\u001d\rB\u0003BD\u000b\u000fC\u0001BA\u001a\u0001\b\u0018A\u0019!n\"\u0007\u0005\u000f\t5tK1\u0001\b\u001cE\u0019qQD9\u0011\u0007)<y\u0002B\u0003m/\n\u0007Q\u000eC\u0003\u007f/\u0002\u000fq\u0010C\u0004\u0007T]\u0003\ra\"\n\u0011\u0013\t\u0014)hb\u0006\b\u001e\u001d]\u0001b\u0002D\f/\u0002\u0007q\u0011\u0006\t\u0007\u0005\u001b49db\u000b\u0011\t\u0019\u0004qQD\u0001\tiJ\fg/\u001a:tKVAq\u0011GD+\u000f\u0013:Y\u0004\u0006\u0003\b4\u001d\u0005D\u0003BD\u001b\u000f3\"bab\u000e\bN\u001d]\u0003\u0003\u00024\u0001\u000fs\u0001RA[D\u001e\u000f\u000f\"qaa#Y\u0005\u00049i$\u0006\u0003\b@\u001d\u0015\u0013c\u00018\bBA1\u0011QHCx\u000f\u0007\u00022A[D#\t\u001d)9pb\u000fC\u00025\u00042A[D%\t\u00199Y\u0005\u0017b\u0001[\n\t!\tC\u0004\u0006Zb\u0003\u001dab\u0014\u0011\u0015\u0015uWq\\D)\u000f\u000f:I\u0004E\u0003k\u000fw9\u0019\u0006E\u0002k\u000f+\"aa!\"Y\u0005\u0004i\u0007\"\u0002@Y\u0001\by\bbBD.1\u0002\u0007qQL\u0001\u0003M:\u0004rAYA\u0006\u000f':y\u0006\u0005\u0003g\u0001\u001d\u001d\u0003b\u0002D\u00021\u0002\u0007q\u0011\u000b")
public interface Future<T>
extends Awaitable<T> {
    public static <A, B, M extends IterableOnce<Object>> Future<M> traverse(M in, Function1<A, Future<B>> fn, BuildFrom<M, B, M> bf, ExecutionContext executor2) {
        Future$ traverse_this = Future$.MODULE$;
        return in.iterator().foldLeft(traverse_this.successful(bf.newBuilder(in)), (arg_0, arg_1) -> Future$.$anonfun$traverse$1(fn, executor2, arg_0, arg_1)).map(Future$::$anonfun$traverse$2, executor2 instanceof BatchingExecutor ? executor2 : ExecutionContext$parasitic$.MODULE$);
    }

    public static <T, R> Future<R> reduceLeft(Iterable<Future<T>> futures, Function2<R, T, R> op, ExecutionContext executor2) {
        Future$ reduceLeft_this = Future$.MODULE$;
        Iterator reduceLeft_i = futures.iterator();
        if (!reduceLeft_i.hasNext()) {
            return reduceLeft_this.failed(new NoSuchElementException("reduceLeft attempted on empty collection"));
        }
        return ((Future)reduceLeft_i.next()).flatMap(arg_0 -> Future$.$anonfun$reduceLeft$1(reduceLeft_i, op, executor2, arg_0), executor2);
    }

    public static <T, R> Future<R> reduce(IterableOnce<Future<T>> futures, Function2<R, T, R> op, ExecutionContext executor2) {
        Future$ reduce_this = Future$.MODULE$;
        if (IterableOnceExtensionMethods$.MODULE$.isEmpty$extension(futures)) {
            return reduce_this.failed(new NoSuchElementException("reduce attempted on empty collection"));
        }
        ArrayBuffer$ reduce_toBuildFrom_factory = ArrayBuffer$.MODULE$;
        Object var4_4 = null;
        return reduce_this.sequence(futures, new BuildFrom<Object, A, CC>(reduce_toBuildFrom_factory){
            private final IterableFactory factory$1;

            public Builder<A, CC> apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory<A, CC> toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public CC fromSpecific(Object from, IterableOnce<A> it) {
                return this.factory$1.from(it);
            }

            public Builder<A, CC> newBuilder(Object from) {
                return this.factory$1.newBuilder();
            }
            {
                this.factory$1 = factory$1;
            }
        }, executor2).map(arg_0 -> Future$.$anonfun$reduce$1(op, arg_0), executor2);
    }

    public static <T, R> Future<R> fold(IterableOnce<Future<T>> futures, R zero, Function2<R, T, R> op, ExecutionContext executor2) {
        Future$ fold_this = Future$.MODULE$;
        if (IterableOnceExtensionMethods$.MODULE$.isEmpty$extension(futures)) {
            return fold_this.successful(zero);
        }
        ArrayBuffer$ fold_toBuildFrom_factory = ArrayBuffer$.MODULE$;
        Object var5_5 = null;
        return fold_this.sequence(futures, new /* invalid duplicate definition of identical inner class */, executor2).map(arg_0 -> Future$.$anonfun$fold$1(zero, op, arg_0), executor2);
    }

    public static <T, R> Future<R> foldLeft(Iterable<Future<T>> futures, R zero, Function2<R, T, R> op, ExecutionContext executor2) {
        Future$ foldLeft_this = Future$.MODULE$;
        Iterator foldLeft_foldNext_i = futures.iterator();
        if (!foldLeft_foldNext_i.hasNext()) {
            return foldLeft_this.successful(zero);
        }
        return ((Future)foldLeft_foldNext_i.next()).flatMap(arg_0 -> Future$.$anonfun$foldNext$1(foldLeft_foldNext_i, op, zero, executor2, arg_0), executor2);
    }

    public static <T> Future<Option<T>> find(Iterable<Future<T>> futures, Function1<T, Object> p, ExecutionContext executor2) {
        Future$ find_this = Future$.MODULE$;
        Iterator find_searchNext$1_i = futures.iterator();
        if (!find_searchNext$1_i.hasNext()) {
            return find_this.successful(None$.MODULE$);
        }
        return ((Future)find_searchNext$1_i.next()).transformWith(arg_0 -> Future$.$anonfun$find$1(find_this, p, find_searchNext$1_i, executor2, arg_0), executor2);
    }

    public static <T> Future<T> firstCompletedOf(IterableOnce<Future<T>> futures, ExecutionContext executor2) {
        return Future$.MODULE$.firstCompletedOf(futures, executor2);
    }

    public static <A, CC extends IterableOnce<Object>, To> Future<To> sequence(CC in, BuildFrom<CC, A, To> bf, ExecutionContext executor2) {
        return Future$.MODULE$.sequence(in, bf, executor2);
    }

    public static <T> Future<T> delegate(Function0<Future<T>> body2, ExecutionContext executor2) {
        return Future$.MODULE$.unit().flatMap(arg_0 -> Future$.$anonfun$delegate$1(body2, arg_0), executor2);
    }

    public static <T> Future<T> apply(Function0<T> body2, ExecutionContext executor2) {
        return Future$.MODULE$.unit().map(arg_0 -> Future$.$anonfun$apply$1(body2, arg_0), executor2);
    }

    public static <T> Future<T> fromTry(Try<T> result) {
        return Future$.MODULE$.fromTry(result);
    }

    public static <T> Future<T> successful(T result) {
        return Future$.MODULE$.successful(result);
    }

    public static Future<BoxedUnit> unit() {
        return Future$.MODULE$.unit();
    }

    public <U> void onComplete(Function1<Try<T>, U> var1, ExecutionContext var2);

    public boolean isCompleted();

    public Option<Try<T>> value();

    public static /* synthetic */ Future failed$(Future $this) {
        return $this.failed();
    }

    default public Future<Throwable> failed() {
        return this.transform(Future$.MODULE$.failedFun(), ExecutionContext$parasitic$.MODULE$);
    }

    public static /* synthetic */ void foreach$(Future $this, Function1 f, ExecutionContext executor2) {
        $this.foreach(f, executor2);
    }

    default public <U> void foreach(Function1<T, U> f, ExecutionContext executor2) {
        this.onComplete((Function1<Try, Object> & Serializable)x$1 -> {
            x$1.foreach(f);
            return BoxedUnit.UNIT;
        }, executor2);
    }

    public static /* synthetic */ Future transform$(Future $this, Function1 s2, Function1 f, ExecutionContext executor2) {
        return $this.transform(s2, f, executor2);
    }

    default public <S> Future<S> transform(Function1<T, S> s2, Function1<Throwable, Throwable> f, ExecutionContext executor2) {
        return this.transform((Function1<Try, Try> & Serializable)t2 -> {
            if (t2 instanceof Success) {
                return t2.map(s2);
            }
            throw (Throwable)f.apply(((Failure)t2).exception());
        }, executor2);
    }

    public <S> Future<S> transform(Function1<Try<T>, Try<S>> var1, ExecutionContext var2);

    public <S> Future<S> transformWith(Function1<Try<T>, Future<S>> var1, ExecutionContext var2);

    public static /* synthetic */ Future map$(Future $this, Function1 f, ExecutionContext executor2) {
        return $this.map(f, executor2);
    }

    default public <S> Future<S> map(Function1<T, S> f, ExecutionContext executor2) {
        return this.transform((Function1<Try, Try> & Serializable)x$2 -> x$2.map(f), executor2);
    }

    public static /* synthetic */ Future flatMap$(Future $this, Function1 f, ExecutionContext executor2) {
        return $this.flatMap(f, executor2);
    }

    default public <S> Future<S> flatMap(Function1<T, Future<S>> f, ExecutionContext executor2) {
        return this.transformWith((Function1<Try, Future> & Serializable)t2 -> {
            if (t2 instanceof Success) {
                return (Future)f.apply(((Success)t2).value());
            }
            return this;
        }, executor2);
    }

    public static /* synthetic */ Future flatten$(Future $this, $less$colon$less ev) {
        return $this.flatten(ev);
    }

    default public <S> Future<S> flatten($less$colon$less<T, Future<S>> ev) {
        return this.flatMap(ev, ExecutionContext$parasitic$.MODULE$);
    }

    public static /* synthetic */ Future filter$(Future $this, Function1 p, ExecutionContext executor2) {
        return $this.filter(p, executor2);
    }

    default public Future<T> filter(Function1<T, Object> p, ExecutionContext executor2) {
        return this.transform((Function1<Try, Try> & Serializable)t2 -> {
            if (t2 instanceof Success) {
                if (BoxesRunTime.unboxToBoolean(p.apply(((Success)t2).value()))) {
                    return t2;
                }
                return Future$.MODULE$.filterFailure();
            }
            return t2;
        }, executor2);
    }

    public static /* synthetic */ Future withFilter$(Future $this, Function1 p, ExecutionContext executor2) {
        return $this.withFilter(p, executor2);
    }

    default public Future<T> withFilter(Function1<T, Object> p, ExecutionContext executor2) {
        return this.filter(p, executor2);
    }

    public static /* synthetic */ Future collect$(Future $this, PartialFunction pf, ExecutionContext executor2) {
        return $this.collect(pf, executor2);
    }

    default public <S> Future<S> collect(PartialFunction<T, S> pf, ExecutionContext executor2) {
        return this.transform((Function1<Try, Try> & Serializable)t2 -> {
            if (t2 instanceof Success) {
                return new Success<Nothing$>(pf.applyOrElse(((Success)t2).value(), Future$.MODULE$.collectFailed()));
            }
            return (Failure)t2;
        }, executor2);
    }

    public static /* synthetic */ Future recover$(Future $this, PartialFunction pf, ExecutionContext executor2) {
        return $this.recover(pf, executor2);
    }

    default public <U> Future<U> recover(PartialFunction<Throwable, U> pf, ExecutionContext executor2) {
        return this.transform((Function1<Try, Try> & Serializable)x$3 -> x$3.recover(pf), executor2);
    }

    public static /* synthetic */ Future recoverWith$(Future $this, PartialFunction pf, ExecutionContext executor2) {
        return $this.recoverWith(pf, executor2);
    }

    default public <U> Future<U> recoverWith(PartialFunction<Throwable, Future<U>> pf, ExecutionContext executor2) {
        return this.transformWith((Function1<Try, Future> & Serializable)t2 -> {
            if (t2 instanceof Failure) {
                Future<Nothing$> result = pf.applyOrElse(((Failure)t2).exception(), Future$.MODULE$.recoverWithFailed());
                if (result != Future$.MODULE$.recoverWithFailedMarker()) {
                    return result;
                }
                return this;
            }
            return this;
        }, executor2);
    }

    public static /* synthetic */ Future zip$(Future $this, Future that) {
        return $this.zip(that);
    }

    default public <U> Future<Tuple2<T, U>> zip(Future<U> that) {
        return this.zipWith(that, Future$.MODULE$.zipWithTuple2Fun(), ExecutionContext$parasitic$.MODULE$);
    }

    public static /* synthetic */ Future zipWith$(Future $this, Future that, Function2 f, ExecutionContext executor2) {
        return $this.zipWith(that, f, executor2);
    }

    default public <U, R> Future<R> zipWith(Future<U> that, Function2<T, U, R> f, ExecutionContext executor2) {
        return this.flatMap((Function1<Object, Future> & Serializable)r1 -> that.map((Function1<Object, Object> & Serializable)r2 -> f.apply(r1, r2), executor2), executor2 instanceof BatchingExecutor ? executor2 : ExecutionContext$parasitic$.MODULE$);
    }

    public static /* synthetic */ Future fallbackTo$(Future $this, Future that) {
        return $this.fallbackTo(that);
    }

    default public <U> Future<U> fallbackTo(Future<U> that) {
        if (this == that) {
            return this;
        }
        ExecutionContext$parasitic$ ec = ExecutionContext$parasitic$.MODULE$;
        return this.transformWith((Function1<Try, Future> & Serializable)t2 -> {
            if (t2 instanceof Success) {
                return this;
            }
            return that.transform((Function1<Try, Try> & Serializable)tt -> {
                if (tt instanceof Success) {
                    return tt;
                }
                return t2;
            }, ec);
        }, ec);
    }

    public static /* synthetic */ Future mapTo$(Future $this, ClassTag tag) {
        return $this.mapTo(tag);
    }

    default public <S> Future<S> mapTo(ClassTag<S> tag) {
        ExecutionContext$parasitic$ ec = ExecutionContext$parasitic$.MODULE$;
        Class c = tag.runtimeClass();
        Class boxedClass = c.isPrimitive() ? (Class)Future$.MODULE$.toBoxed().apply(c) : c;
        Predef$.MODULE$.require(boxedClass != null);
        return this.map((Function1<Object, Object> & Serializable)s2 -> boxedClass.cast(s2), ec);
    }

    public static /* synthetic */ Future andThen$(Future $this, PartialFunction pf, ExecutionContext executor2) {
        return $this.andThen(pf, executor2);
    }

    default public <U> Future<T> andThen(PartialFunction<Try<T>, U> pf, ExecutionContext executor2) {
        return this.transform((Function1<Try, Try> & Serializable)result -> {
            try {
                pf.applyOrElse(result, Future$.MODULE$.id());
            }
            catch (Throwable throwable) {
                if (NonFatal$.MODULE$.apply(throwable)) {
                    executor2.reportFailure(throwable);
                }
                throw throwable;
            }
            return result;
        }, executor2);
    }

    public static void $init$(Future $this) {
    }
}

