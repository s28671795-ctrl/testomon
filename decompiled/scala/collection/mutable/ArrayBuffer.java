/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.lang.reflect.Array;
import java.util.Arrays;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.IndexedSeqOps;
import scala.collection.IndexedSeqView;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
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
import scala.collection.convert.impl.ObjectArrayStepper;
import scala.collection.generic.DefaultSerializable;
import scala.collection.mutable.AbstractBuffer;
import scala.collection.mutable.ArrayBuffer$;
import scala.collection.mutable.ArrayBufferView;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.IndexedBuffer;
import scala.math.Integral;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.util.Either;
import scala.util.Sorting$;

@ScalaSignature(bytes="\u0006\u0005\r-h\u0001\u0002 @\u0001\u0019C\u0001\"\u001c\u0001\u0003\u0002\u0003\u0006IA\u001c\u0005\ti\u0002\u0011\t\u0011)A\u0005k\")\u0001\u0010\u0001C\u0005s\")\u0001\u0010\u0001C\u0001y\")\u0001\u0010\u0001C\u0001{\"1q\u0010\u0001Q!\nUD!\"!\u0003\u0001\u0001\u0004%\t\"QA\u0006\u0011)\ti\u0001\u0001a\u0001\n#\t\u0015q\u0002\u0005\b\u00037\u0001\u0001\u0015)\u0003o\u0011%\ti\u0002\u0001a\u0001\n#\ty\u0002C\u0005\u0002\"\u0001\u0001\r\u0011\"\u0005\u0002$!9\u0011q\u0005\u0001!B\u0013)\bbBA\u0015\u0001\u0011\u0005\u00131\u0006\u0005\b\u0003c\u0002A\u0011IA\u0010\u0011\u001d\t\u0019\b\u0001C\t\u0003kB\u0001\"a\u001f\u0001\t\u0003y\u0014Q\u0010\u0005\b\u0003\u0003\u0003A\u0011AAB\u0011\u001d\tI\t\u0001C\u0005\u0003\u0017Cq!a$\u0001\t\u0003\t\t\nC\u0004\u0002\u0014\u0002!I!!&\t\u000f\u0005m\u0005\u0001\"\u0003\u0002\u001e\"9\u0011q\u0016\u0001\u0005\u0002\u0005E\u0006bBA[\u0001\u0011\u0005\u0011q\u0017\u0005\b\u0003\u001f\u0004A\u0011AA\u0010\u0011\u001d\t\t\u000e\u0001C!\u0003'Dq!a7\u0001\t\u0003\ni\u000eC\u0004\u0002f\u0002!\t!!%\t\u000f\u0005\u001d\b\u0001\"\u0001\u0002j\"I\u0011q\u001e\u0001\u0012\u0002\u0013\u0005\u0011\u0011\u001f\u0005\b\u0005\u000f\u0001A\u0011\u0001B\u0005\u0011\u001d\u0011i\u0001\u0001C!\u0005\u001fAqAa\u0007\u0001\t\u0003\u0011i\u0002C\u0004\u0003&\u0001!\tAa\n\t\u000f\t-\u0002\u0001\"\u0001\u0003.!9!Q\u0007\u0001\u0005\u0002\t]\u0002b\u0002B\u001b\u0001\u0011\u0005!Q\b\u0005\b\u0005\u000f\u0002A\u0011\u0001B%\u0011\u001d\u0011I\u0007\u0001C\u0001\u0005WB\u0001Ba$\u0001A\u0013E#\u0011\u0013\u0005\b\u0005G\u0003A\u0011\tBS\u0011\u001d\u0011y\f\u0001C!\u0005\u0003DqA!8\u0001\t\u0013\u0011y\u000eC\u0004\u0004\u0006\u0001!Iaa\u0002\t\u000f\rm\u0001\u0001\"\u0011\u0004\u001e!91Q\u0006\u0001\u0005B\r=\u0002bBB \u0001\u0011\u00053\u0011\t\u0005\b\u0007\u001b\u0002A\u0011IB(\u000f\u001d\u00199g\u0010E\u0001\u0007S2aAP \t\u0002\r-\u0004B\u0002=2\t\u0003\u0019\u0019\bC\u0005\u0004vE\u0012\r\u0011\"\u0002\u0004x!A1QP\u0019!\u0002\u001b\u0019I\bC\u0004\u0004\u0000E\u0002\u000b\u0011\u00028\t\u000f\r\u0005\u0015\u0007\"\u0001\u0004\u0004\"911S\u0019\u0005\u0002\rU\u0005bBBQc\u0011\u000511\u0015\u0005\b\u0007[\u000bD\u0011BBX\u0011\u001d\t\u0019(\rC\u0005\u0007\u007fCqaa32\t\u0013\u0019i\rC\u0004\u0004TF\"Ia!6\t\u0013\rm\u0017'!A\u0005\n\ru'aC!se\u0006L()\u001e4gKJT!\u0001Q!\u0002\u000f5,H/\u00192mK*\u0011!iQ\u0001\u000bG>dG.Z2uS>t'\"\u0001#\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011qIT\n\b\u0001!C6\f\u00193h!\rI%\nT\u0007\u0002\u007f%\u00111j\u0010\u0002\u000f\u0003\n\u001cHO]1di\n+hMZ3s!\tie\n\u0004\u0001\u0005\u000b=\u0003!\u0019\u0001)\u0003\u0003\u0005\u000b\"!U+\u0011\u0005I\u001bV\"A\"\n\u0005Q\u001b%a\u0002(pi\"Lgn\u001a\t\u0003%ZK!aV\"\u0003\u0007\u0005s\u0017\u0010E\u0002J32K!AW \u0003\u001b%sG-\u001a=fI\n+hMZ3s!\u0015IE\f\u00140`\u0013\tivHA\u0007J]\u0012,\u00070\u001a3TKF|\u0005o\u001d\t\u0003\u0013\u0002\u00012!\u0013\u0001M!\u0015\t'\r\u00140`\u001b\u0005\t\u0015BA2B\u0005U\u0019FO]5di>\u0003H/[7ju\u0016$7+Z9PaN\u0004B!Y3M=&\u0011a-\u0011\u0002\u0018\u0013R,'/\u00192mK\u001a\u000b7\r^8ss\u0012+g-Y;miN\u0004\"\u0001[6\u000e\u0003%T!A[!\u0002\u000f\u001d,g.\u001a:jG&\u0011A.\u001b\u0002\u0014\t\u00164\u0017-\u001e7u'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0010S:LG/[1m\u000b2,W.\u001a8ugB\u0019!k\\9\n\u0005A\u001c%!B!se\u0006L\bC\u0001*s\u0013\t\u00198I\u0001\u0004B]f\u0014VMZ\u0001\fS:LG/[1m'&TX\r\u0005\u0002Sm&\u0011qo\u0011\u0002\u0004\u0013:$\u0018A\u0002\u001fj]&$h\bF\u0002`unDQ!\\\u0002A\u00029DQ\u0001^\u0002A\u0002U$\u0012a\u0018\u000b\u0003?zDQ\u0001^\u0003A\u0002U\fQ\"\\;uCRLwN\\\"pk:$\bf\u0001\u0004\u0002\u0004A\u0019!+!\u0002\n\u0007\u0005\u001d1IA\u0005ue\u0006t7/[3oi\u0006)\u0011M\u001d:bsV\ta.A\u0005beJ\f\u0017p\u0018\u0013fcR!\u0011\u0011CA\f!\r\u0011\u00161C\u0005\u0004\u0003+\u0019%\u0001B+oSRD\u0001\"!\u0007\t\u0003\u0003\u0005\rA\\\u0001\u0004q\u0012\n\u0014AB1se\u0006L\b%A\u0003tSj,\u0007'F\u0001v\u0003%\u0019\u0018N_31?\u0012*\u0017\u000f\u0006\u0003\u0002\u0012\u0005\u0015\u0002\u0002CA\r\u0017\u0005\u0005\t\u0019A;\u0002\rML'0\u001a\u0019!\u0003\u001d\u0019H/\u001a9qKJ,B!!\f\u00028Q!\u0011qFA4%\u0019\t\t$!\u000e\u0002L\u00191\u00111\u0007\u0001\u0001\u0003_\u0011A\u0002\u0010:fM&tW-\\3oiz\u00022!TA\u001c\t\u001d\tI$\u0004b\u0001\u0003w\u0011\u0011aU\t\u0004#\u0006u\u0002\u0007BA \u0003\u000f\u0002R!YA!\u0003\u000bJ1!a\u0011B\u0005\u001d\u0019F/\u001a9qKJ\u00042!TA$\t-\tI%a\u000e\u0002\u0002\u0003\u0005)\u0011\u0001)\u0003\u0007}#\u0013\u0007\u0005\u0003\u0002N\u0005\u0005d\u0002BA(\u0003;rA!!\u0015\u0002\\9!\u00111KA-\u001b\t\t)FC\u0002\u0002X\u0015\u000ba\u0001\u0010:p_Rt\u0014\"\u0001#\n\u0005\t\u001b\u0015bAA0\u0003\u000691\u000b^3qa\u0016\u0014\u0018\u0002BA2\u0003K\u0012a\"\u00124gS\u000eLWM\u001c;Ta2LGOC\u0002\u0002`\u0005Cq!!\u001b\u000e\u0001\b\tY'A\u0003tQ\u0006\u0004X\r\u0005\u0004b\u0003[b\u0015QG\u0005\u0004\u0003_\n%\u0001D*uKB\u0004XM]*iCB,\u0017!C6o_^t7+\u001b>f\u0003))gn];sKNK'0\u001a\u000b\u0005\u0003#\t9\b\u0003\u0004\u0002z=\u0001\r!^\u0001\u0002]\u0006!RM\\:ve\u0016\fE\rZ5uS>t\u0017\r\\*ju\u0016$B!!\u0005\u0002\u0000!1\u0011\u0011\u0010\tA\u0002U\f\u0001b]5{K\"Kg\u000e\u001e\u000b\u0005\u0003#\t)\t\u0003\u0004\u0002\bF\u0001\r!^\u0001\u0005g&TX-\u0001\u0007sK\u0012,8-\u001a+p'&TX\r\u0006\u0003\u0002\u0012\u00055\u0005BBA=%\u0001\u0007Q/\u0001\u0006ue&lGk\\*ju\u0016$\"!!\u0005\u0002\rI,7/\u001b>f)\u0011\t\t\"a&\t\r\u0005eE\u00031\u0001v\u00039\u0011X-];je\u0016$G*\u001a8hi\"\f\u0011c\u00195fG.<\u0016\u000e\u001e5j]\n{WO\u001c3t)\u0019\t\t\"a(\u0002$\"1\u0011\u0011U\u000bA\u0002U\f!\u0001\\8\t\r\u0005\u0015V\u00031\u0001v\u0003\tA\u0017\u000eK\u0002\u0016\u0003S\u00032AUAV\u0013\r\tik\u0011\u0002\u0007S:d\u0017N\\3\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u00071\u000b\u0019\f\u0003\u0004\u0002zY\u0001\r!^\u0001\u0007kB$\u0017\r^3\u0015\r\u0005E\u0011\u0011XAf\u0011\u0019\tYl\u0006a\u0001k\u0006)\u0011N\u001c3fq\"B\u0011\u0011XA`\u0003\u000b\f9\rE\u0002S\u0003\u0003L1!a1D\u00059!W\r\u001d:fG\u0006$X\r\u001a(b[\u0016\f#!!\u001f\"\u0005\u0005%\u0017A\u0002\u001a/cMr\u0003\u0007\u0003\u0004\u0002N^\u0001\r\u0001T\u0001\u0005K2,W.\u0001\u0004mK:<G\u000f[\u0001\u0005m&,w/\u0006\u0002\u0002VB!\u0011*a6M\u0013\r\tIn\u0010\u0002\u0010\u0003J\u0014\u0018-\u001f\"vM\u001a,'OV5fo\u0006y\u0011\u000e^3sC\ndWMR1di>\u0014\u00180\u0006\u0002\u0002`B!\u0011-!9_\u0013\r\t\u0019/\u0011\u0002\u000b'\u0016\fh)Y2u_JL\u0018!B2mK\u0006\u0014\u0018AD2mK\u0006\u0014\u0018I\u001c3TQJLgn\u001b\u000b\u0005\u0003W\fi/D\u0001\u0001\u0011!\t9\t\bI\u0001\u0002\u0004)\u0018\u0001G2mK\u0006\u0014\u0018I\u001c3TQJLgn\u001b\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u00111\u001f\u0016\u0004k\u0006U8FAA|!\u0011\tIPa\u0001\u000e\u0005\u0005m(\u0002BA\u007f\u0003\u007f\f\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\t\u00051)\u0001\u0006b]:|G/\u0019;j_:LAA!\u0002\u0002|\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\r\u0005$Gm\u00148f)\u0011\tYOa\u0003\t\r\u00055g\u00041\u0001M\u0003\u0019\tG\rZ!mYR!\u00111\u001eB\t\u0011\u001d\u0011\u0019b\ba\u0001\u0005+\tQ!\u001a7f[N\u0004B!\u0019B\f\u0019&\u0019!\u0011D!\u0003\u0019%#XM]1cY\u0016|enY3\u0002\r%t7/\u001a:u)\u0019\t\tBa\b\u0003$!1\u00111\u0018\u0011A\u0002UD\u0003Ba\b\u0002@\u0006\u0015\u0017q\u0019\u0005\u0007\u0003\u001b\u0004\u0003\u0019\u0001'\u0002\u000fA\u0014X\r]3oIR!\u00111\u001eB\u0015\u0011\u0019\ti-\ta\u0001\u0019\u0006I\u0011N\\:feR\fE\u000e\u001c\u000b\u0007\u0003#\u0011yCa\r\t\r\u0005m&\u00051\u0001vQ!\u0011y#a0\u0002F\u0006\u001d\u0007b\u0002B\nE\u0001\u0007!QC\u0001\u0007e\u0016lwN^3\u0015\u00071\u0013I\u0004\u0003\u0004\u0002<\u000e\u0002\r!\u001e\u0015\t\u0005s\ty,!2\u0002HR1\u0011\u0011\u0003B \u0005\u0007Ba!a/%\u0001\u0004)\b\u0006\u0003B \u0003\u007f\u000b)-a2\t\r\t\u0015C\u00051\u0001v\u0003\u0015\u0019w.\u001e8u\u0003\u0019\u0011Xm];miR\u0011\u00111\u001e\u0015\u0004K\u0005%\u0006fC\u0013\u0003P\tU#q\u000bB.\u0003\u000f\u00042A\u0015B)\u0013\r\u0011\u0019f\u0011\u0002\u0015I\u0016\u0004(/Z2bi\u0016$wJ^3se&$\u0017N\\4\u0002\u000f5,7o]1hK\u0006\u0012!\u0011L\u0001<\u0003J\u0014\u0018-\u001f\"vM\u001a,'oW!^A9|\u0007\u0005\\8oO\u0016\u0014\b%\u001a=uK:$7\u000f\t\"vS2$WM].BY\u0001\n%O]1z\u0005V4g-\u001a:\\\u0003vk\u0016!B:j]\u000e,\u0007fC\u0013\u0003`\tU#Q\rB.\u0003\u000f\u00042A\u0015B1\u0013\r\u0011\u0019g\u0011\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017E\u0001B4\u0003m)6/\u001a\u0011(i\"L7o\n\u0011j]N$\u0018M\\2fA%t7\u000f^3bI\u0006IQ.\u00199SKN,H\u000e^\u000b\u0005\u0005[\u00129\b\u0006\u0003\u0003p\tm\u0004CB%\u0003r1\u0013)(C\u0002\u0003t}\u0012qAQ;jY\u0012,'\u000fE\u0002N\u0005o\"aA!\u001f'\u0005\u0004\u0001&!\u0002(foR{\u0007b\u0002B?M\u0001\u0007!qP\u0001\u0002MB1!K!!`\u0005kJ1Aa!D\u0005%1UO\\2uS>t\u0017\u0007K\u0002'\u0003SC3B\nB(\u0005+\u00129Fa\u0017\u0002H\"ZaEa\u0018\u0003V\t-%1LAdC\t\u0011i)\u0001\u001bVg\u0016\u0004sE\\3xA\u001d\u0013xn^1cY\u0016\u0014U/\u001b7eKJDC\u000f[5tS9j\u0017\r\u001d*fgVdG\u000f\u000b4*O\u0001Jgn\u001d;fC\u0012\fAb\u001d;sS:<\u0007K]3gSb,\"Aa%\u0011\t\tU%qT\u0007\u0003\u0005/SAA!'\u0003\u001c\u0006!A.\u00198h\u0015\t\u0011i*\u0001\u0003kCZ\f\u0017\u0002\u0002BQ\u0005/\u0013aa\u0015;sS:<\u0017aC2paf$v.\u0011:sCf,BAa*\u00032R9QO!+\u00038\nm\u0006b\u0002BVQ\u0001\u0007!QV\u0001\u0003qN\u0004BAU8\u00030B\u0019QJ!-\u0005\u000f\tM\u0006F1\u0001\u00036\n\t!)\u0005\u0002M+\"1!\u0011\u0018\u0015A\u0002U\fQa\u001d;beRDaA!0)\u0001\u0004)\u0018a\u00017f]\u0006Y1o\u001c:u\u0013:\u0004F.Y2f+\u0011\u0011\u0019Ma7\u0015\u0005\t\u0015G\u0003BAv\u0005\u000fDqA!3*\u0001\b\u0011Y-A\u0002pe\u0012\u0004bA!4\u0003T\negb\u0001*\u0003P&\u0019!\u0011[\"\u0002\u000fA\f7m[1hK&!!Q\u001bBl\u0005!y%\u000fZ3sS:<'b\u0001Bi\u0007B\u0019QJa7\u0005\u000f\tM\u0016F1\u0001\u00036\u0006)am\u001c7eYV!!\u0011\u001dBs))\u0011\u0019Oa:\u0003j\n5(\u0011\u001f\t\u0004\u001b\n\u0015HA\u0002BZU\t\u0007\u0001\u000b\u0003\u0004\u0003:*\u0002\r!\u001e\u0005\u0007\u0005WT\u0003\u0019A;\u0002\u0007\u0015tG\rC\u0004\u0003p*\u0002\rAa9\u0002\u0003iDqAa=+\u0001\u0004\u0011)0\u0001\u0002paBA!Ka>\u0003d2\u0013\u0019/C\u0002\u0003z\u000e\u0013\u0011BR;oGRLwN\u001c\u001a)\u0007)\u0012i\u0010\u0005\u0003\u0003\u0000\u000e\u0005QBAA\u0000\u0013\u0011\u0019\u0019!a@\u0003\u000fQ\f\u0017\u000e\u001c:fG\u0006)am\u001c7eeV!1\u0011BB\u0007))\u0019Yaa\u0004\u0004\u0012\rM1Q\u0003\t\u0004\u001b\u000e5AA\u0002BZW\t\u0007\u0001\u000b\u0003\u0004\u0003:.\u0002\r!\u001e\u0005\u0007\u0005W\\\u0003\u0019A;\t\u000f\t=8\u00061\u0001\u0004\f!9!1_\u0016A\u0002\r]\u0001\u0003\u0003*\u0003x2\u001bYaa\u0003)\u0007-\u0012i0\u0001\u0005g_2$G*\u001a4u+\u0011\u0019yb!\n\u0015\t\r\u000521\u0006\u000b\u0005\u0007G\u00199\u0003E\u0002N\u0007K!aAa--\u0005\u0004\u0001\u0006b\u0002BzY\u0001\u00071\u0011\u0006\t\t%\n]81\u0005'\u0004$!9!q\u001e\u0017A\u0002\r\r\u0012!\u00034pY\u0012\u0014\u0016n\u001a5u+\u0011\u0019\tda\u000e\u0015\t\rM2Q\b\u000b\u0005\u0007k\u0019I\u0004E\u0002N\u0007o!aAa-.\u0005\u0004\u0001\u0006b\u0002Bz[\u0001\u000711\b\t\t%\n]Hj!\u000e\u00046!9!q^\u0017A\u0002\rU\u0012A\u0003:fIV\u001cW\rT3giV!11IB$)\u0011\u0019)e!\u0013\u0011\u00075\u001b9\u0005B\u0004\u00034:\u0012\rA!.\t\u000f\tMh\u00061\u0001\u0004LAA!Ka>\u0004F1\u001b)%A\u0006sK\u0012,8-\u001a*jO\"$X\u0003BB)\u0007+\"Baa\u0015\u0004XA\u0019Qj!\u0016\u0005\u000f\tMvF1\u0001\u00036\"9!1_\u0018A\u0002\re\u0003\u0003\u0003*\u0003x2\u001b\u0019fa\u0015)\u000f\u0001\u0019ifa\u0019\u0004fA\u0019!ka\u0018\n\u0007\r\u00054I\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKzA!^\u0003\u0003\n_Cy\u0002*A\u0006BeJ\f\u0017PQ;gM\u0016\u0014\bCA%2'\u0011\t\u0014o!\u001c\u0011\t\u0005\u001cyGX\u0005\u0004\u0007c\n%!G*ue&\u001cGo\u00149uS6L'0\u001a3TKF4\u0015m\u0019;pef$\"a!\u001b\u0002%\u0011+g-Y;mi&s\u0017\u000e^5bYNK'0Z\u000b\u0003\u0007sz!aa\u001f\u001e\u0003A\t1\u0003R3gCVdG/\u00138ji&\fGnU5{K\u0002\n!\"Z7qif\f%O]1z\u0003\u00111'o\\7\u0016\t\r\u001551\u0012\u000b\u0005\u0007\u000f\u001bi\t\u0005\u0003J\u0001\r%\u0005cA'\u0004\f\u00121!1\u0017\u001cC\u0002ACqaa$7\u0001\u0004\u0019\t*\u0001\u0003d_2d\u0007#B1\u0003\u0018\r%\u0015A\u00038fo\n+\u0018\u000e\u001c3feV!1qSBO+\t\u0019I\nE\u0004J\u0005c\u001aYja(\u0011\u00075\u001bi\nB\u0003Po\t\u0007\u0001\u000b\u0005\u0003J\u0001\rm\u0015!B3naRLX\u0003BBS\u0007W+\"aa*\u0011\t%\u00031\u0011\u0016\t\u0004\u001b\u000e-F!B(9\u0005\u0004\u0001\u0016\u0001\u0003:fg&TX-\u00169\u0015\u000bU\u001c\tla/\t\u000f\rM\u0016\b1\u0001\u00046\u0006A\u0011M\u001d:bs2+g\u000eE\u0002S\u0007oK1a!/D\u0005\u0011auN\\4\t\u000f\ru\u0016\b1\u0001\u00046\u0006IA/\u0019:hKRdUM\u001c\u000b\b]\u000e\u000571YBd\u0011\u0019\tIA\u000fa\u0001]\"11Q\u0019\u001eA\u0002U\fqaY;s'&TX\rC\u0004\u0004Jj\u0002\ra!.\u0002\u0015Q\f'oZ3u'&TX-\u0001\u0006sKNL'0\u001a#po:$R!^Bh\u0007#Daaa-<\u0001\u0004)\bBBB_w\u0001\u0007Q/\u0001\u0005e_^t7/\u001b>f)\u0015q7q[Bm\u0011\u0019\tI\u0001\u0010a\u0001]\"11\u0011\u001a\u001fA\u0002U\fAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"aa8\u0011\t\tU5\u0011]\u0005\u0005\u0007G\u00149J\u0001\u0004PE*,7\r\u001e\u0015\bc\ru31MBt=\u0005\u0019\u0001f\u0002\u0019\u0004^\r\r4q\u001d")
public class ArrayBuffer<A>
extends AbstractBuffer<A>
implements IndexedBuffer<A>,
StrictOptimizedSeqOps<A, ArrayBuffer, ArrayBuffer<A>>,
DefaultSerializable {
    private static final long serialVersionUID = -1582447879429021880L;
    private transient int mutationCount;
    private Object[] array;
    private int size0;

    public static <A> Builder<A, ArrayBuffer<A>> newBuilder() {
        return new GrowableBuilder<A, ArrayBuffer<A>>(){

            public void sizeHint(int size) {
                ((ArrayBuffer)this.elems()).ensureSize(size);
            }
        };
    }

    public static <B> ArrayBuffer<B> from(IterableOnce<B> coll) {
        return ArrayBuffer$.MODULE$.from((IterableOnce)coll);
    }

    public static int DefaultInitialSize() {
        return 16;
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
        return IterableFactory.range$(ArrayBuffer$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(ArrayBuffer$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        ArrayBuffer$ unfold_this = ArrayBuffer$.MODULE$;
        View.Unfold from_source = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        ArrayBuffer$ iterate_this = ArrayBuffer$.MODULE$;
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_source);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
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
    public Tuple2<ArrayBuffer<A>, ArrayBuffer<A>> partition(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<ArrayBuffer<A>, ArrayBuffer<A>> span(Function1<A, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<ArrayBuffer<A1>, ArrayBuffer<A2>> unzip(Function1<A, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<ArrayBuffer<A1>, ArrayBuffer<A2>, ArrayBuffer<A3>> unzip3(Function1<A, Tuple3<A1, A2, A3>> asTriple) {
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
    public <A1, A2> Tuple2<ArrayBuffer<A1>, ArrayBuffer<A2>> partitionMap(Function1<A, Either<A1, A2>> f) {
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
    public scala.collection.mutable.IndexedSeqOps<A, ?, ArrayBuffer<A>> mapInPlace(Function1<A, A> f) {
        return scala.collection.mutable.IndexedSeqOps.mapInPlace$(this, f);
    }

    @Override
    public scala.collection.mutable.IndexedSeqOps<A, ?, ArrayBuffer<A>> sortInPlaceWith(Function2<A, A, Object> lt) {
        return scala.collection.mutable.IndexedSeqOps.sortInPlaceWith$(this, lt);
    }

    @Override
    public <B> scala.collection.mutable.IndexedSeqOps<A, ?, ArrayBuffer<A>> sortInPlaceBy(Function1<A, B> f, Ordering<B> ord) {
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
    public Object reverse() {
        return IndexedSeqOps.reverse$(this);
    }

    @Override
    public Object slice(int from, int until) {
        return IndexedSeqOps.slice$(this, from, until);
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

    public Object[] array() {
        return this.array;
    }

    public void array_$eq(Object[] x$1) {
        this.array = x$1;
    }

    public int size0() {
        return this.size0;
    }

    public void size0_$eq(int x$1) {
        this.size0 = x$1;
    }

    @Override
    public <S extends Stepper<?>> S stepper(StepperShape<A, S> shape) {
        return shape.parUnbox(new ObjectArrayStepper<Object>(this.array(), 0, this.length()));
    }

    @Override
    public int knownSize() {
        return IndexedSeqOps.knownSize$(this);
    }

    public void ensureSize(int n) {
        this.array_$eq(ArrayBuffer$.MODULE$.scala$collection$mutable$ArrayBuffer$$ensureSize(this.array(), this.size0(), n));
    }

    public void ensureAdditionalSize(int n) {
        this.array_$eq(ArrayBuffer$.MODULE$.scala$collection$mutable$ArrayBuffer$$ensureSize(this.array(), this.size0(), (long)this.size0() + (long)n));
    }

    public void sizeHint(int size) {
        if (size > this.length() && size >= 1) {
            this.ensureSize(size);
            return;
        }
    }

    private void reduceToSize(int n) {
        ++this.mutationCount;
        Arrays.fill(this.array(), n, this.size0(), null);
        this.size0_$eq(n);
    }

    public void trimToSize() {
        this.resize(this.length());
    }

    private void resize(int requiredLength) {
        this.array_$eq(ArrayBuffer$.MODULE$.scala$collection$mutable$ArrayBuffer$$downsize(this.array(), requiredLength));
    }

    private void checkWithinBounds(int lo, int hi) {
        if (lo < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(lo).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
        if (hi > this.size0()) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(hi - 1).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
    }

    @Override
    public A apply(int n) {
        int checkWithinBounds_hi = n + 1;
        if (n < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(n).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
        if (checkWithinBounds_hi > this.size0()) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(checkWithinBounds_hi - 1).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
        return (A)this.array()[n];
    }

    @Override
    public void update(int index, A elem) {
        int checkWithinBounds_hi = index + 1;
        if (index < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(index).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
        if (checkWithinBounds_hi > this.size0()) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(checkWithinBounds_hi - 1).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
        ++this.mutationCount;
        this.array()[index] = elem;
    }

    @Override
    public int length() {
        return this.size0();
    }

    @Override
    public ArrayBufferView<A> view() {
        return new ArrayBufferView(this, () -> $this.mutationCount);
    }

    @Override
    public SeqFactory<ArrayBuffer> iterableFactory() {
        return ArrayBuffer$.MODULE$;
    }

    @Override
    public void clear() {
        this.reduceToSize(0);
    }

    public ArrayBuffer<A> clearAndShrink(int size) {
        this.clear();
        this.resize(size);
        return this;
    }

    public int clearAndShrink$default$1() {
        return 16;
    }

    @Override
    public ArrayBuffer<A> addOne(A elem) {
        ++this.mutationCount;
        this.ensureAdditionalSize(1);
        int oldSize = this.size0();
        this.size0_$eq(oldSize + 1);
        this.update(oldSize, elem);
        return this;
    }

    @Override
    public ArrayBuffer<A> addAll(IterableOnce<A> elems) {
        if (elems instanceof ArrayBuffer) {
            ArrayBuffer arrayBuffer = (ArrayBuffer)elems;
            int elemsLength = arrayBuffer.size0();
            if (elemsLength > 0) {
                ++this.mutationCount;
                this.ensureAdditionalSize(elemsLength);
                Array$.MODULE$.copy(arrayBuffer.array(), 0, this.array(), this.length(), elemsLength);
                this.size0_$eq(this.length() + elemsLength);
            }
        } else {
            Growable.addAll$(this, elems);
        }
        return this;
    }

    @Override
    public void insert(int index, A elem) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(index).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
        if (index > this.size0()) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(index - 1).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
        ++this.mutationCount;
        this.ensureAdditionalSize(1);
        Array$.MODULE$.copy(this.array(), index, this.array(), index + 1, this.size0() - index);
        this.size0_$eq(this.size0() + 1);
        this.update(index, elem);
    }

    @Override
    public ArrayBuffer<A> prepend(A elem) {
        this.insert(0, elem);
        return this;
    }

    @Override
    public void insertAll(int index, IterableOnce<A> elems) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(index).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
        if (index > this.size0()) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(index - 1).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
        if (elems instanceof Iterable) {
            Iterable iterable = (Iterable)elems;
            int elemsLength = iterable.size();
            if (elemsLength > 0) {
                ++this.mutationCount;
                this.ensureAdditionalSize(elemsLength);
                int len = this.size0();
                Array$.MODULE$.copy(this.array(), index, this.array(), index + elemsLength, len - index);
                Object[] copyElemsToArray_xs = this.array();
                iterable.copyToArray(copyElemsToArray_xs, index, elemsLength);
                Object var6_6 = null;
                this.size0_$eq(len + elemsLength);
                return;
            }
            return;
        }
        this.insertAll(index, (IterableOnce<A>)ArrayBuffer$.MODULE$.from((IterableOnce)elems));
    }

    @Override
    public A remove(int index) {
        int checkWithinBounds_hi = index + 1;
        if (index < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(index).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
        if (checkWithinBounds_hi > this.size0()) {
            throw new IndexOutOfBoundsException(new StringBuilder(31).append(checkWithinBounds_hi - 1).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
        }
        A res = this.apply(index);
        Array$.MODULE$.copy(this.array(), index + 1, this.array(), index, this.size0() - (index + 1));
        this.reduceToSize(this.size0() - 1);
        return res;
    }

    @Override
    public void remove(int index, int count) {
        if (count > 0) {
            int checkWithinBounds_hi = index + count;
            if (index < 0) {
                throw new IndexOutOfBoundsException(new StringBuilder(31).append(index).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
            }
            if (checkWithinBounds_hi > this.size0()) {
                throw new IndexOutOfBoundsException(new StringBuilder(31).append(checkWithinBounds_hi - 1).append(" is out of bounds (min 0, max ").append(this.size0() - 1).append(")").toString());
            }
            Array$.MODULE$.copy(this.array(), index + count, this.array(), index, this.size0() - (index + count));
            this.reduceToSize(this.size0() - count);
            return;
        }
        if (count < 0) {
            throw new IllegalArgumentException(new StringBuilder(38).append("removing negative number of elements: ").append(count).toString());
        }
    }

    public ArrayBuffer<A> result() {
        return this;
    }

    public <NewTo> Builder<A, NewTo> mapResult(Function1<ArrayBuffer<A>, NewTo> f) {
        return new GrowableBuilder(this).mapResult(f);
    }

    @Override
    public String stringPrefix() {
        return "ArrayBuffer";
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> int copyToArray(Object xs, int start2, int len) {
        void elemsToCopyToArray_destLen;
        int n = Array.getLength(xs);
        int elemsToCopyToArray_srcLen = this.length();
        int copied = Math.max(Math.min(Math.min(len, elemsToCopyToArray_srcLen), (int)(elemsToCopyToArray_destLen - start2)), 0);
        if (copied > 0) {
            Array$.MODULE$.copy(this.array(), 0, xs, start2, copied);
        }
        return copied;
    }

    public <B> ArrayBuffer<A> sortInPlace(Ordering<B> ord) {
        if (this.length() > 1) {
            ++this.mutationCount;
            Sorting$.MODULE$.stableSort((Object)this.array(), 0, this.length(), ord);
        }
        return this;
    }

    private <B> B foldl(int start2, int end, B z, Function2<B, A, B> op) {
        while (start2 != end) {
            B b = op.apply(z, this.array()[start2]);
            z = b;
            ++start2;
        }
        return z;
    }

    private <B> B foldr(int start2, int end, B z, Function2<A, B, B> op) {
        while (start2 != end) {
            B b = op.apply(this.array()[end - 1], z);
            z = b;
            --end;
        }
        return z;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> B foldLeft(B z, Function2<B, A, B> op) {
        B b = z;
        int n = this.length();
        int foldl_start = 0;
        B foldl_z;
        void foldl_end;
        while (foldl_start != foldl_end) {
            foldl_z = op.apply(foldl_z, this.array()[foldl_start]);
            ++foldl_start;
        }
        return foldl_z;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> B foldRight(B z, Function2<A, B, B> op) {
        B b = z;
        int n = this.length();
        boolean foldr_start = false;
        B foldr_z;
        void foldr_end;
        while (foldr_start != foldr_end) {
            foldr_z = op.apply(this.array()[foldr_end - true], foldr_z);
            --foldr_end;
        }
        return foldr_z;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <B> B reduceLeft(Function2<B, A, B> op) {
        if (this.length() > 0) {
            Object object = this.array()[0];
            int foldl_end = this.length();
            int foldl_start = 1;
            while (true) {
                B foldl_z;
                if (foldl_start == foldl_end) {
                    return foldl_z;
                }
                foldl_z = op.apply(foldl_z, this.array()[foldl_start]);
                ++foldl_start;
            }
        }
        if (this.length() > 0) {
            Object reduceLeft_foldl_z;
            Object r = reduceLeft_foldl_z = this.apply(false);
            int n = this.length();
            int reduceLeft_foldl_loop$1_at = 1;
            while (true) {
                B reduceLeft_foldl_loop$1_acc;
                void reduceLeft_foldl_loop$1_end;
                if (reduceLeft_foldl_loop$1_at == reduceLeft_foldl_loop$1_end) {
                    return reduceLeft_foldl_loop$1_acc;
                }
                reduceLeft_foldl_loop$1_acc = op.apply(reduceLeft_foldl_loop$1_acc, this.apply(reduceLeft_foldl_loop$1_at));
                ++reduceLeft_foldl_loop$1_at;
            }
        }
        Iterator reduceLeft_it = this.iterator();
        if (reduceLeft_it.isEmpty()) {
            throw new UnsupportedOperationException("empty.reduceLeft");
        }
        boolean reduceLeft_first = true;
        Object reduceLeft_acc = null;
        while (reduceLeft_it.hasNext()) {
            Object reduceLeft_x = reduceLeft_it.next();
            if (reduceLeft_first) {
                reduceLeft_acc = reduceLeft_x;
                reduceLeft_first = false;
                continue;
            }
            reduceLeft_acc = op.apply(reduceLeft_acc, reduceLeft_x);
        }
        return (B)reduceLeft_acc;
    }

    @Override
    public <B> B reduceRight(Function2<A, B, B> op) {
        if (this.length() > 0) {
            Object object = this.array()[this.length() - 1];
            int foldr_end = this.length() - 1;
            int foldr_start = 0;
            while (true) {
                B foldr_z;
                if (foldr_start == foldr_end) {
                    return foldr_z;
                }
                foldr_z = op.apply(this.array()[foldr_end - 1], foldr_z);
                --foldr_end;
            }
        }
        if (this.length() > 0) {
            Object r = this.apply(this.length() - 1);
            int reduceRight_foldr_loop$2_at = this.length() - 1;
            while (true) {
                B reduceRight_foldr_loop$2_acc;
                if (reduceRight_foldr_loop$2_at == 0) {
                    return reduceRight_foldr_loop$2_acc;
                }
                reduceRight_foldr_loop$2_acc = op.apply(this.apply(reduceRight_foldr_loop$2_at - 1), reduceRight_foldr_loop$2_acc);
                --reduceRight_foldr_loop$2_at;
            }
        }
        if (this.iterator().isEmpty()) {
            throw new UnsupportedOperationException("empty.reduceRight");
        }
        return this.reversed().reduceLeft((arg_0, arg_1) -> IterableOnceOps.$anonfun$reduceRight$1(op, arg_0, arg_1));
    }

    public ArrayBuffer(Object[] initialElements, int initialSize) {
        this.mutationCount = 0;
        this.array = initialElements;
        this.size0 = initialSize;
    }

    public ArrayBuffer() {
        this(new Object[16], 0);
    }

    public ArrayBuffer(int initialSize) {
        int max$extension_that = 1;
        this(new Object[Math.max(initialSize, max$extension_that)], 0);
    }
}

