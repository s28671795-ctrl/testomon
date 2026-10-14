/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.collection.AbstractIterator;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.MapOps;
import scala.collection.StrictOptimizedIterableOps;
import scala.collection.View;
import scala.collection.generic.DefaultSerializationProxy;
import scala.collection.immutable.Seq;
import scala.collection.mutable.AbstractMap;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.LongMap$;
import scala.collection.mutable.LongMap$ToBuildFrom$;
import scala.collection.mutable.LongMap$ToFactory$;
import scala.collection.mutable.ReusableBuilder;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.Statics;
import scala.util.Either;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\u0015Mf\u0001\u00029r\u0005aD!\"!\u0016\u0001\u0005\u0003\u0005\u000b\u0011BA,\u0011)\ti\u0006\u0001B\u0001B\u0003%\u0011q\f\u0005\u000b\u0003K\u0002!\u0011!Q\u0001\n\u0005\u001d\u0004\u0002CA7\u0001\u0011\u00051/a\u001c\t\u000f\u00055\u0004\u0001\"\u0001\u0002x!9\u0011\u0011\u0010\u0001\u0005R\u0005m\u0004bBAD\u0001\u0011E\u0013\u0011\u0012\u0005\b\u0003[\u0002A\u0011AAI\u0011\u001d\ti\u0007\u0001C\u0001\u0003+Cq!!\u001c\u0001\t\u0003\tI\n\u0003\u0005\u0002 \u0002\u0001\u000b\u0015BA0\u0011!\t\t\u000b\u0001Q!\n\u0005}\u0003\u0002CAR\u0001\u0001\u0006K!!*\t\u0011\u0005-\u0006\u0001)Q\u0005\u0003KC\u0001\"!,\u0001A\u0003&\u0011q\f\u0005\t\u0003_\u0003\u0001\u0015)\u0003\u0002`!A\u0011\u0011\u0017\u0001!B\u0013\t\u0019\f\u0003\u0005\u0002:\u0002\u0001\u000b\u0015BA^\u0011!\ti\f\u0001Q\u0005\n\u0005}\u0006\u0002CAf\u0001\u0011\u00051/!4\t\u000f\u0005=\b\u0001\"\u0011\u0002r\"9\u00111\u001f\u0001\u0005B\u0005E\bbBA{\u0001\u0011\u0005\u0013q\u001f\u0005\b\u0003s\u0004A\u0011IA~\u0011\u001d\ti\u0010\u0001C\u0005\u0003oDq!a@\u0001\t\u0013\u0011\t\u0001C\u0004\u0003\b\u0001!IA!\u0003\t\u000f\t5\u0001\u0001\"\u0003\u0003\u0010!9!1\u0003\u0001\u0005\n\tU\u0001b\u0002B\r\u0001\u0011\u0005#1\u0004\u0005\b\u0005C\u0001A\u0011\tB\u0012\u0011\u001d\u0011i\u0003\u0001C!\u0005_AqAa\u0012\u0001\t\u0003\u0012I\u0005C\u0004\u0003T\u0001!\tA!\u0016\t\u000f\te\u0003\u0001\"\u0011\u0003\\!9!q\b\u0001\u0005B\t}\u0003b\u0002B2\u0001\u0011%!Q\r\u0005\b\u0005G\u0002A\u0011\u0001B6\u0011\u001d\u0011i\u0007\u0001C!\u0005_BqAa\u001e\u0001\t\u0003\u0012I\bC\u0004\u0003\u0000\u0001!\tA!!\t\u000f\tu\u0005\u0001\"\u0002\u0003 \"9!Q\u0014\u0001\u0005F\t5\u0006b\u0002B[\u0001\u0011\u0005!q\u0017\u0005\b\u0005w\u0003A\u0011\u0001B_\u0011\u001d\u0011)\r\u0001C!\u0005\u000fDqAa3\u0001\t\u0003\u0012i\rC\u0004\u0003R\u0002!\tEa5\t\u000f\t\r\b\u0001\"\u0011\u0003f\"9!Q\u001f\u0001\u0005B\u0005]\u0004b\u0002B|\u0001\u0011\u0005#\u0011 \u0005\b\u0005o\u0004A\u0011IB\t\u0011\u001d\u0019)\u0004\u0001C!\u0007oAqa!\u0013\u0001\t\u0003\u001aY\u0005C\u0004\u0004\\\u0001!\te!\u0018\t\u000f\rE\u0004\u0001\"\u0001\u0004t!91\u0011\u0011\u0001\u0005\u0002\r\r\u0005bBBH\u0001\u0011\u00051\u0011\u0013\u0005\b\u0007?\u0003AQABQ\u0011\u001d\u0019y\u000b\u0001C\u0001\u0007cCqa!.\u0001\t\u0003\u00199\fC\u0004\u0004J\u0002!\taa3\t\u000f\ru\u0007\u0001\"\u0001\u0004`\"A1Q\u001f\u0001!\n#\u00199\u0010\u0003\u0005\u0004z\u0002\u0001K\u0011KB~\u000f\u001d!i!\u001dE\u0001\t\u001f1a\u0001]9\t\u0002\u0011E\u0001bBA7\u0007\u0012\u0005AQ\u0004\u0005\n\t?\u0019%\u0019!C\u0007\tCA\u0001\u0002b\nDA\u00035A1\u0005\u0005\n\tS\u0019%\u0019!C\u0007\tWA\u0001\u0002\"\rDA\u00035AQ\u0006\u0005\n\tg\u0019%\u0019!C\u0007\tkA\u0001\u0002b\u000fDA\u00035Aq\u0007\u0005\n\t{\u0019%\u0019!C\u0007\t\u007fA\u0001\u0002\"\u0012DA\u00035A\u0011\t\u0005\n\t\u000f\u001a%\u0019!C\u0005\t\u0013B\u0001\u0002\"\u0014DA\u0003%A1\n\u0004\u0007\t\u001f\u001a%\u0001\"\u0015\t\u000f\u00055t\n\"\u0001\u0005d!Q1qE(A\u0002\u0013\u00051\u000f\"\u001b\t\u0015\u0011-t\n1A\u0005\u0002M$i\u0007\u0003\u0005\u0005t=\u0003\u000b\u0015\u0002C1\u0011\u001d\u0011ij\u0014C!\tkBq\u0001\" P\t\u0003\u0011Y\u0007C\u0004\u0005\u0000=#\t\u0001\"!\t\u000f\u0005Mx\n\"\u0011\u0002r\"9!\u0011L\"\u0005\u0002\u0011\r\u0005b\u0002CJ\u0007\u0012%AQ\u0013\u0005\b\u0003s\u001cE\u0011\u0001CS\u0011\u001d!yk\u0011C\u0001\tcCq\u0001b0D\t\u0003!\t\rC\u0004\u0005T\u000e#\t\u0001\"6\t\u000f\u0011\r8\t\"\u0001\u0005f\"9A1]\"\u0005\u0002\u0011e\bbBC\u0007\u0007\u0012\rQqB\u0004\t\u000bO\u0019\u0005\u0015#\u0003\u0006*\u0019AQ1F\"!\u0012\u0013)i\u0003C\u0004\u0002n\t$\t!\"\u000e\t\u000f\u0005e$\r\"\u0001\u00068!9A1\u001b2\u0005\u0002\u0015}\u0002\"CB{E\u0006\u0005I\u0011BC\"\u0011\u001d)9f\u0011C\u0002\u000b3:q!b\u001cD\u0011\u0013)\tHB\u0004\u0006t\rCI!\"\u001e\t\u000f\u00055\u0014\u000e\"\u0001\u0006z!9\u0011\u0011P5\u0005\u0002\u0015m\u0004b\u0002CjS\u0012\u0005Q1\u0011\u0005\b\u000b\u0013\u001bE1ACF\u0011\u001d)Ij\u0011C\u0002\u000b7C\u0011b!>D\u0003\u0003%I!b\u0011\u0003\u000f1{gnZ'ba*\u0011!o]\u0001\b[V$\u0018M\u00197f\u0015\t!X/\u0001\u0006d_2dWm\u0019;j_:T\u0011A^\u0001\u0006g\u000e\fG.Y\u0002\u0001+\rI\u0018\u0011B\n\t\u0001i\fY\"!\u000b\u0002>A)1\u0010 @\u0002\u00065\t\u0011/\u0003\u0002~c\nY\u0011IY:ue\u0006\u001cG/T1q!\ry\u0018\u0011A\u0007\u0002k&\u0019\u00111A;\u0003\t1{gn\u001a\t\u0005\u0003\u000f\tI\u0001\u0004\u0001\u0005\u000f\u0005-\u0001A1\u0001\u0002\u000e\t\ta+\u0005\u0003\u0002\u0010\u0005U\u0001cA@\u0002\u0012%\u0019\u00111C;\u0003\u000f9{G\u000f[5oOB\u0019q0a\u0006\n\u0007\u0005eQOA\u0002B]f\u0004\"b_A\u000f}\u0006\u0015\u0011\u0011EA\u0014\u0013\r\ty\"\u001d\u0002\u0007\u001b\u0006\u0004x\n]:\u0011\u0007m\f\u0019#C\u0002\u0002&E\u00141!T1q!\u0011Y\b!!\u0002\u0011\u0015\u0005-\u0012QFA\u0019\u0003o\t9#D\u0001t\u0013\r\tyc\u001d\u0002\u001b'R\u0014\u0018n\u0019;PaRLW.\u001b>fI&#XM]1cY\u0016|\u0005o\u001d\t\u0007\u007f\u0006Mb0!\u0002\n\u0007\u0005URO\u0001\u0004UkBdWM\r\t\u0004w\u0006e\u0012bAA\u001ec\nA\u0011\n^3sC\ndW\r\u0005\u0003\u0002@\u0005=c\u0002BA!\u0003\u0017rA!a\u0011\u0002J5\u0011\u0011Q\t\u0006\u0004\u0003\u000f:\u0018A\u0002\u001fs_>$h(C\u0001w\u0013\r\ti%^\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\t\t&a\u0015\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0007\u00055S/\u0001\u0007eK\u001a\fW\u000f\u001c;F]R\u0014\u0018\u0010\u0005\u0004\u0000\u00033r\u0018QA\u0005\u0004\u00037*(!\u0003$v]\u000e$\u0018n\u001c82\u0003EIg.\u001b;jC2\u0014UO\u001a4feNK'0\u001a\t\u0004\u007f\u0006\u0005\u0014bAA2k\n\u0019\u0011J\u001c;\u0002\u0013%t\u0017\u000e\u001e\"mC:\\\u0007cA@\u0002j%\u0019\u00111N;\u0003\u000f\t{w\u000e\\3b]\u00061A(\u001b8jiz\"\u0002\"a\n\u0002r\u0005M\u0014Q\u000f\u0005\b\u0003+\"\u0001\u0019AA,\u0011\u001d\ti\u0006\u0002a\u0001\u0003?Bq!!\u001a\u0005\u0001\u0004\t9\u0007\u0006\u0002\u0002(\u0005aaM]8n'B,7-\u001b4jGR!\u0011qEA?\u0011\u001d\tyH\u0002a\u0001\u0003\u0003\u000bAaY8mYB1\u00111FAB\u0003cI1!!\"t\u00051IE/\u001a:bE2,wJ\\2f\u0003IqWm^*qK\u000eLg-[2Ck&dG-\u001a:\u0016\u0005\u0005-\u0005cB>\u0002\u000e\u0006E\u0012qE\u0005\u0004\u0003\u001f\u000b(a\u0002\"vS2$WM\u001d\u000b\u0005\u0003O\t\u0019\nC\u0004\u0002V!\u0001\r!a\u0016\u0015\t\u0005\u001d\u0012q\u0013\u0005\b\u0003;J\u0001\u0019AA0)\u0019\t9#a'\u0002\u001e\"9\u0011Q\u000b\u0006A\u0002\u0005]\u0003bBA/\u0015\u0001\u0007\u0011qL\u0001\u0005[\u0006\u001c8.A\u0005fqR\u0014\u0018mS3zg\u0006I!0\u001a:p-\u0006dW/\u001a\t\u0004\u007f\u0006\u001d\u0016bAAUk\n1\u0011I\\=SK\u001a\f\u0001\"\\5o-\u0006dW/Z\u0001\u0006?NL'0Z\u0001\b?Z\f7-\u00198u\u0003\u0015y6.Z=t!\u0011y\u0018Q\u0017@\n\u0007\u0005]VOA\u0003BeJ\f\u00170A\u0004`m\u0006dW/Z:\u0011\u000b}\f),!*\u0002#\u0011,g-Y;mi&s\u0017\u000e^5bY&TX\r\u0006\u0003\u0002B\u0006\u001d\u0007cA@\u0002D&\u0019\u0011QY;\u0003\tUs\u0017\u000e\u001e\u0005\b\u0003\u0013\u001c\u0002\u0019AA0\u0003\u0005q\u0017\u0001D5oSRL\u0017\r\\5{KR{GCEAa\u0003\u001f\f\u0019.a6\u0002\\\u0006}\u00171]At\u0003WDq!!5\u0015\u0001\u0004\ty&A\u0001n\u0011\u001d\t)\u000e\u0006a\u0001\u0003?\n!!Z6\t\u000f\u0005eG\u00031\u0001\u0002&\u0006\u0011!P\u001e\u0005\b\u0003;$\u0002\u0019AAS\u0003\tig\u000fC\u0004\u0002bR\u0001\r!a\u0018\u0002\u0005MT\bbBAs)\u0001\u0007\u0011qL\u0001\u0003m\u000eDq!!;\u0015\u0001\u0004\t\u0019,\u0001\u0002lu\"9\u0011Q\u001e\u000bA\u0002\u0005m\u0016A\u0001<{\u0003\u0011\u0019\u0018N_3\u0016\u0005\u0005}\u0013!C6o_^t7+\u001b>f\u0003\u001dI7/R7qif,\"!a\u001a\u0002\u000b\u0015l\u0007\u000f^=\u0016\u0005\u0005\u001d\u0012AC5nE\u0006d\u0017M\\2fI\u00069Ao\\%oI\u0016DH\u0003BA0\u0005\u0007AaA!\u0002\u001b\u0001\u0004q\u0018!A6\u0002\u0013M,Wm[#naRLH\u0003BA0\u0005\u0017AaA!\u0002\u001c\u0001\u0004q\u0018!C:fK.,e\u000e\u001e:z)\u0011\tyF!\u0005\t\r\t\u0015A\u00041\u0001\u007f\u0003=\u0019X-Z6F]R\u0014\u0018p\u0014:Pa\u0016tG\u0003BA0\u0005/AaA!\u0002\u001e\u0001\u0004q\u0018\u0001C2p]R\f\u0017N\\:\u0015\t\u0005\u001d$Q\u0004\u0005\u0007\u0005?q\u0002\u0019\u0001@\u0002\u0007-,\u00170A\u0002hKR$BA!\n\u0003,A)qPa\n\u0002\u0006%\u0019!\u0011F;\u0003\r=\u0003H/[8o\u0011\u0019\u0011yb\ba\u0001}\u0006Iq-\u001a;Pe\u0016c7/Z\u000b\u0005\u0005c\u0011)\u0004\u0006\u0004\u00034\tm\"Q\b\t\u0005\u0003\u000f\u0011)\u0004B\u0004\u00038\u0001\u0012\rA!\u000f\u0003\u0005Y\u000b\u0014\u0003BA\u0003\u0003+AaAa\b!\u0001\u0004q\b\u0002\u0003B A\u0011\u0005\rA!\u0011\u0002\u000f\u0011,g-Y;miB)qPa\u0011\u00034%\u0019!QI;\u0003\u0011q\u0012\u0017P\\1nKz\nqbZ3u\u001fJ,En]3Va\u0012\fG/\u001a\u000b\u0007\u0003\u000b\u0011YE!\u0014\t\r\t}\u0011\u00051\u0001\u007f\u0011!\u0011y%\tCA\u0002\tE\u0013\u0001\u00043fM\u0006,H\u000e\u001e,bYV,\u0007#B@\u0003D\u0005\u0015\u0011!C4fi>\u0013h*\u001e7m)\u0011\t)Aa\u0016\t\r\t}!\u00051\u0001\u007f\u0003\u0015\t\u0007\u000f\u001d7z)\u0011\t)A!\u0018\t\r\t}1\u00051\u0001\u007f)\u0011\t)A!\u0019\t\r\t}A\u00051\u0001\u007f\u0003\u0019\u0011X\r]1dWR!\u0011\u0011\u0019B4\u0011\u001d\u0011I'\na\u0001\u0003?\nqA\\3x\u001b\u0006\u001c8\u000e\u0006\u0002\u0002B\u0006\u0019\u0001/\u001e;\u0015\r\t\u0015\"\u0011\u000fB:\u0011\u0019\u0011yb\na\u0001}\"9!QO\u0014A\u0002\u0005\u0015\u0011!\u0002<bYV,\u0017AB;qI\u0006$X\r\u0006\u0004\u0002B\nm$Q\u0010\u0005\u0007\u0005?A\u0003\u0019\u0001@\t\u000f\tU\u0004\u00061\u0001\u0002\u0006\u0005AA\u0005\u001d7vg\u0012*\u0017\u000f\u0006\u0004\u0003\u0004\n\u0015%qQ\u0007\u0002\u0001!1!qD\u0015A\u0002yDqA!\u001e*\u0001\u0004\t)\u0001K\u0006*\u0005\u0017\u0013\tJa%\u0003\u0018\ne\u0005cA@\u0003\u000e&\u0019!qR;\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u0005\tU\u0015AZ+tK\u0002\u0002\u0017\r\u001a3P]\u0016\u0004\u0007e\u001c:!AV\u0004H-\u0019;fA\u0002Jgn\u001d;fC\u0012\\\u0004%\u001b8gSb\u0004s\u000e]3sCRLwN\\:!o&$\b\u000eI1oA=\u0004XM]1oI\u0002zg\rI7vYRL\u0007\u000f\\3!CJ<7\u000fI<jY2\u0004#-\u001a\u0011eKB\u0014XmY1uK\u0012\fQa]5oG\u0016\f#Aa'\u0002\rIr\u0013g\r\u00184\u0003\u0019\tG\rZ(oKR1!1\u0011BQ\u0005GCaAa\b+\u0001\u0004q\bb\u0002B;U\u0001\u0007\u0011Q\u0001\u0015\u0004U\t\u001d\u0006cA@\u0003*&\u0019!1V;\u0003\r%tG.\u001b8f)\u0011\u0011\u0019Ia,\t\u000f\tE6\u00061\u0001\u00022\u0005\u00111N\u001e\u0015\u0004W\t\u001d\u0016aC:vER\u0014\u0018m\u0019;P]\u0016$BAa!\u0003:\"1!q\u0004\u0017A\u0002y\f\u0001\"\u001b;fe\u0006$xN]\u000b\u0003\u0005\u007f\u0003b!a\u000b\u0003B\u0006E\u0012b\u0001Bbg\nA\u0011\n^3sCR|'/\u0001\u0007lKf\u001c\u0018\n^3sCR|'/\u0006\u0002\u0003JB)\u00111\u0006Ba}\u0006qa/\u00197vKNLE/\u001a:bi>\u0014XC\u0001Bh!\u0019\tYC!1\u0002\u0006\u00059am\u001c:fC\u000eDW\u0003\u0002Bk\u0005?$B!!1\u0003X\"9!\u0011\u001c\u0019A\u0002\tm\u0017!\u00014\u0011\u000f}\fI&!\r\u0003^B!\u0011q\u0001Bp\t\u001d\u0011\t\u000f\rb\u0001\u0003\u001b\u0011\u0011!V\u0001\rM>\u0014X-Y2i\u000b:$(/_\u000b\u0005\u0005O\u0014\u0019\u0010\u0006\u0003\u0002B\n%\bb\u0002Bmc\u0001\u0007!1\u001e\t\t\u007f\n5h0!\u0002\u0003r&\u0019!q^;\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004\u0003BA\u0004\u0005g$qA!92\u0005\u0004\ti!A\u0003dY>tW-A\u0003%a2,8/\u0006\u0003\u0003|\u000e\u0005A\u0003\u0002B\u007f\u0007\u0007\u0001Ba\u001f\u0001\u0003\u0000B!\u0011qAB\u0001\t\u001d\u00119d\rb\u0001\u0005sAqA!-4\u0001\u0004\u0019)\u0001\u0005\u0004\u0000\u0003gq(q \u0015\fg\t-%\u0011SB\u0005\u0005/\u001bi!\t\u0002\u0004\f\u0005q4i\u001c8tS\u0012,'\u000f\t:fcVL'/\u001b8hA\u0005t\u0007%[7nkR\f'\r\\3!\u001b\u0006\u0004\be\u001c:!M\u0006dG\u000e\t2bG.\u0004Co\u001c\u0011NCBt3m\u001c8dCR\f#aa\u0004\u0002\rIr\u0013g\r\u00181+\u0011\u0019\u0019b!\u0007\u0015\u0011\rU11DB\u0011\u0007K\u0001Ba\u001f\u0001\u0004\u0018A!\u0011qAB\r\t\u001d\u00119\u0004\u000eb\u0001\u0005sAqa!\b5\u0001\u0004\u0019y\"A\u0003fY\u0016l\u0017\u0007\u0005\u0004\u0000\u0003gq8q\u0003\u0005\b\u0007G!\u0004\u0019AB\u0010\u0003\u0015)G.Z73\u0011\u001d\u00199\u0003\u000ea\u0001\u0007S\tQ!\u001a7f[N\u0004Ra`B\u0016\u0007?I1a!\fv\u0005)a$/\u001a9fCR,GM\u0010\u0015\fi\t-%\u0011SB\u0019\u0005/\u001bi!\t\u0002\u00044\u0005)Uk]3!W-\u0002s/\u001b;iA\u0005t\u0007%\u001a=qY&\u001c\u0017\u000e\u001e\u0011d_2dWm\u0019;j_:\u0004\u0013M]4v[\u0016tG\u000fI5ogR,\u0017\r\u001a\u0011pM\u0002Z\u0003e^5uQ\u00022\u0018M]1sON\faaY8oG\u0006$X\u0003BB\u001d\u0007\u007f!Baa\u000f\u0004BA!1\u0010AB\u001f!\u0011\t9aa\u0010\u0005\u000f\t]RG1\u0001\u0003:!911I\u001bA\u0002\r\u0015\u0013A\u0001=t!\u0019\tY#a!\u0004HA1q0a\r\u007f\u0007{\t!\u0002\n9mkN$\u0003\u000f\\;t+\u0011\u0019iea\u0015\u0015\t\r=3Q\u000b\t\u0005w\u0002\u0019\t\u0006\u0005\u0003\u0002\b\rMCa\u0002B\u001cm\t\u0007!\u0011\b\u0005\b\u0007\u00072\u0004\u0019AB,!\u0019\tY#a!\u0004ZA1q0a\r\u007f\u0007#\nq!\u001e9eCR,G-\u0006\u0003\u0004`\r\u0015DCBB1\u0007O\u001aI\u0007\u0005\u0003|\u0001\r\r\u0004\u0003BA\u0004\u0007K\"qAa\u000e8\u0005\u0004\u0011I\u0004\u0003\u0004\u0003 ]\u0002\rA \u0005\b\u0005k:\u0004\u0019AB2Q-9$1\u0012BI\u0007[\u00129j!\u0004\"\u0005\r=\u0014\u0001N+tK\u0002jgf\u00197p]\u0016D\u0013FL1eI>sW\rK6-m&\u0002\u0013N\\:uK\u0006$\u0007e\u001c4![:*\b\u000fZ1uK\u0012D3\u000e\f\u0011wS\u0005Qam\u001c:fC\u000eD7*Z=\u0016\t\rU4Q\u0010\u000b\u0005\u0003\u0003\u001c9\bC\u0004\u0003Zb\u0002\ra!\u001f\u0011\r}\fIF`B>!\u0011\t9a! \u0005\u000f\r}\u0004H1\u0001\u0002\u000e\t\t\u0011)\u0001\u0007g_J,\u0017m\u00195WC2,X-\u0006\u0003\u0004\u0006\u000e5E\u0003BAa\u0007\u000fCqA!7:\u0001\u0004\u0019I\tE\u0004\u0000\u00033\n)aa#\u0011\t\u0005\u001d1Q\u0012\u0003\b\u0007\u007fJ$\u0019AA\u0007\u00031i\u0017\r\u001d,bYV,7OT8x+\u0011\u0019\u0019j!'\u0015\t\rU51\u0014\t\u0005w\u0002\u00199\n\u0005\u0003\u0002\b\reEa\u0002B\u001cu\t\u0007\u0011Q\u0002\u0005\b\u00053T\u0004\u0019ABO!\u001dy\u0018\u0011LA\u0003\u0007/\u000bq\u0002\u001e:b]N4wN]7WC2,Xm\u001d\u000b\u0005\u0005\u0007\u001b\u0019\u000bC\u0004\u0003Zn\u0002\ra!*\u0011\u000f}\fI&!\u0002\u0002\u0006!\u001a1Ha*)\u0017m\u0012YI!%\u0004,\n]5QB\u0011\u0003\u0007[\u000bQ'V:fAQ\u0014\u0018M\\:g_Jlg+\u00197vKNLe\u000e\u00157bG\u0016\u0004\u0013N\\:uK\u0006$\u0007e\u001c4!iJ\fgn\u001d4pe64\u0016\r\\;fg\u00061BO]1og\u001a|'/\u001c,bYV,7/\u00138QY\u0006\u001cW\r\u0006\u0003\u0003\u0004\u000eM\u0006b\u0002Bmy\u0001\u00071QU\u0001\u0004[\u0006\u0004X\u0003BB]\u0007\u007f#Baa/\u0004DB!1\u0010AB_!\u0011\t9aa0\u0005\u000f\r\u0005WH1\u0001\u0002\u000e\t\u0011aK\r\u0005\b\u00053l\u0004\u0019ABc!\u001dy\u0018\u0011LA\u0019\u0007\u000f\u0004ba`A\u001a}\u000eu\u0016a\u00024mCRl\u0015\r]\u000b\u0005\u0007\u001b\u001c\u0019\u000e\u0006\u0003\u0004P\u000eU\u0007\u0003B>\u0001\u0007#\u0004B!a\u0002\u0004T\u001291\u0011\u0019 C\u0002\u00055\u0001b\u0002Bm}\u0001\u00071q\u001b\t\b\u007f\u0006e\u0013\u0011GBm!\u0019\tY#a!\u0004\\B1q0a\r\u007f\u0007#\fqaY8mY\u0016\u001cG/\u0006\u0003\u0004b\u000e\u001dH\u0003BBr\u0007S\u0004Ba\u001f\u0001\u0004fB!\u0011qABt\t\u001d\u0019\tm\u0010b\u0001\u0003\u001bAqaa;@\u0001\u0004\u0019i/\u0001\u0002qMB9qpa<\u00022\rM\u0018bAByk\ny\u0001+\u0019:uS\u0006dg)\u001e8di&|g\u000e\u0005\u0004\u0000\u0003gq8Q]\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003K\u000b\u0011b\u00197bgNt\u0015-\\3\u0016\u0005\ru\b\u0003BB\u0000\t\u0013i!\u0001\"\u0001\u000b\t\u0011\rAQA\u0001\u0005Y\u0006twM\u0003\u0002\u0005\b\u0005!!.\u0019<b\u0013\u0011!Y\u0001\"\u0001\u0003\rM#(/\u001b8h\u0003\u001dauN\\4NCB\u0004\"a_\"\u0014\u000b\r\u000b)\u000bb\u0005\u0011\t\u0011UA1D\u0007\u0003\t/QA\u0001\"\u0007\u0005\u0006\u0005\u0011\u0011n\\\u0005\u0005\u0003#\"9\u0002\u0006\u0002\u0005\u0010\u0005I\u0011J\u001c3fq6\u000b7o[\u000b\u0003\tGy!\u0001\"\n\u001e\t}z\u0000\u0000\u0000\u0001\u000b\u0013:$W\r_'bg.\u0004\u0013AC'jgNLgn\u001a\"jiV\u0011AQF\b\u0003\t_iB\u0001!\u0001\u0001\u0001\u0005YQ*[:tS:<')\u001b;!\u0003%1\u0016mY1oi\nKG/\u0006\u0002\u00058=\u0011A\u0011H\u000f\u0005\u0001\u0002\u0001\u0001!\u0001\u0006WC\u000e\fg\u000e\u001e\"ji\u0002\n!\"T5tgZ\u000b7-\u00198u+\t!\te\u0004\u0002\u0005Du!\u0001\u0019\u0001\u0001\u0001\u0003-i\u0015n]:WC\u000e\fg\u000e\u001e\u0011\u0002!\u0015D8-\u001a9uS>tG)\u001a4bk2$XC\u0001C&!\u0019y\u0018\u0011\f@\u0002\u0010\u0005\tR\r_2faRLwN\u001c#fM\u0006,H\u000e\u001e\u0011\u0003\u001d1{gnZ'ba\n+\u0018\u000e\u001c3feV!A1\u000bC0'\u0015y\u0015Q\u0015C+!\u001dYHq\u000bC.\tCJ1\u0001\"\u0017r\u0005=\u0011V-^:bE2,')^5mI\u0016\u0014\bCB@\u00024y$i\u0006\u0005\u0003\u0002\b\u0011}CaBA\u0006\u001f\n\u0007\u0011Q\u0002\t\u0005w\u0002!i\u0006\u0006\u0002\u0005fA)AqM(\u0005^5\t1)\u0006\u0002\u0005b\u0005IQ\r\\3ng~#S-\u001d\u000b\u0005\u0003\u0003$y\u0007C\u0005\u0005rI\u000b\t\u00111\u0001\u0005b\u0005\u0019\u0001\u0010J\u0019\u0002\r\u0015dW-\\:!)\u0011!9\b\"\u001f\u000e\u0003=Cq\u0001b\u001fU\u0001\u0004!Y&A\u0003f]R\u0014\u00180A\u0003dY\u0016\f'/\u0001\u0004sKN,H\u000e\u001e\u000b\u0003\tC*B\u0001\"\"\u0005\fR!Aq\u0011CG!\u0011Y\b\u0001\"#\u0011\t\u0005\u001dA1\u0012\u0003\b\u0003\u0017A&\u0019AA\u0007\u0011\u001d\u00199\u0003\u0017a\u0001\t\u001f\u0003Ra`B\u0016\t#\u0003ba`A\u001a}\u0012%\u0015!\u00062vS2$gI]8n\u0013R,'/\u00192mK>s7-Z\u000b\u0005\t/#i\n\u0006\u0003\u0005\u001a\u0012}\u0005\u0003B>\u0001\t7\u0003B!a\u0002\u0005\u001e\u00129\u00111B-C\u0002\u00055\u0001bBB\u00143\u0002\u0007A\u0011\u0015\t\u0007\u0003W\t\u0019\tb)\u0011\r}\f\u0019D CN+\u0011!9\u000b\",\u0016\u0005\u0011%\u0006\u0003B>\u0001\tW\u0003B!a\u0002\u0005.\u00129\u00111\u0002.C\u0002\u00055\u0011aC<ji\"$UMZ1vYR,B\u0001b-\u0005:R!AQ\u0017C^!\u0011Y\b\u0001b.\u0011\t\u0005\u001dA\u0011\u0018\u0003\b\u0003\u0017Y&\u0019AA\u0007\u0011\u001d\u0011yd\u0017a\u0001\t{\u0003ba`A-}\u0012]\u0016\u0001\u00024s_6,B\u0001b1\u0005JR!AQ\u0019Cf!\u0011Y\b\u0001b2\u0011\t\u0005\u001dA\u0011\u001a\u0003\b\u0003\u0017a&\u0019AA\u0007\u0011\u001d!i\r\u0018a\u0001\t\u001f\faa]8ve\u000e,\u0007CBA\u0016\u0003\u0007#\t\u000e\u0005\u0004\u0000\u0003gqHqY\u0001\u000b]\u0016<()^5mI\u0016\u0014X\u0003\u0002Cl\t?,\"\u0001\"7\u0011\u000fm$9\u0006b7\u0005bB1q0a\r\u007f\t;\u0004B!a\u0002\u0005`\u00129\u00111B/C\u0002\u00055\u0001\u0003B>\u0001\t;\fqA\u001a:p[jK\u0007/\u0006\u0003\u0005h\u00125HC\u0002Cu\t_$\u0019\u0010\u0005\u0003|\u0001\u0011-\b\u0003BA\u0004\t[$q!a\u0003_\u0005\u0004\ti\u0001C\u0004\u0005rz\u0003\r!a-\u0002\t-,\u0017p\u001d\u0005\b\tkt\u0006\u0019\u0001C|\u0003\u00191\u0018\r\\;fgB)q0!.\u0005lV!A1`C\u0001)\u0019!i0b\u0001\u0006\nA!1\u0010\u0001C\u0000!\u0011\t9!\"\u0001\u0005\u000f\u0005-qL1\u0001\u0002\u000e!9A\u0011_0A\u0002\u0015\u0015\u0001#BA\u0016\u000b\u000fq\u0018bAA\u001eg\"9AQ_0A\u0002\u0015-\u0001CBA\u0016\u000b\u000f!y0A\u0005u_\u001a\u000b7\r^8ssV!Q\u0011CC\u000f)\u0011)\u0019\"\"\t\u0011\u0011\u0005-RQCC\r\u000b?I1!b\u0006t\u0005\u001d1\u0015m\u0019;pef\u0004ba`A\u001a}\u0016m\u0001\u0003BA\u0004\u000b;!q!a\u0003a\u0005\u0004\ti\u0001\u0005\u0003|\u0001\u0015m\u0001bBC\u0012A\u0002\u0007QQE\u0001\u0006IVlW.\u001f\b\u0003w\n\u000b\u0011\u0002V8GC\u000e$xN]=\u0011\u0007\u0011\u001d$MA\u0005U_\u001a\u000b7\r^8ssN9!-!*\u00060\u0005u\u0002\u0003CA\u0016\u000b+)\t$b\r\u0011\r}\f\u0019D`AS!\u0011Y\b!!*\u0015\u0005\u0015%B\u0003BC\u001a\u000bsAq!b\u000fe\u0001\u0004)i$\u0001\u0002jiB1\u00111FAB\u000bc)\"!\"\u0011\u0011\u000fm\fi)\"\r\u00064Q\u0011QQ\t\t\u0005\u0007\u007f,9%\u0003\u0003\u0006J\u0011\u0005!AB(cU\u0016\u001cG\u000fK\u0004c\u000b\u001b\u0012)(b\u0015\u0011\u0007},y%C\u0002\u0006RU\u0014\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u001f\u0003\rAs!YC'\u0005k*\u0019&A\u0006u_\n+\u0018\u000e\u001c3Ge>lW\u0003BC.\u000bO\"B!\"\u0018\u0006lAQ\u00111FC0\u0003+)\u0019'\"\u001b\n\u0007\u0015\u00054OA\u0005Ck&dGM\u0012:p[B1q0a\r\u007f\u000bK\u0002B!a\u0002\u0006h\u00119\u00111B4C\u0002\u00055\u0001\u0003B>\u0001\u000bKBq!\"\u001ch\u0001\u0004))#A\u0004gC\u000e$xN]=\u0002\u0017Q{')^5mI\u001a\u0013x.\u001c\t\u0004\tOJ'a\u0003+p\u0005VLG\u000e\u001a$s_6\u001cR![AS\u000bo\u0002\"\"a\u000b\u0006`\u0005UQ\u0011GC\u001a)\t)\t\b\u0006\u0003\u0006~\u0015\u0005E\u0003BC\u001a\u000b\u007fBq!b\u000fl\u0001\u0004)i\u0004C\u0004\u0005@.\u0004\r!!\u0006\u0015\t\u0015\u0015Uq\u0011\t\bw\u0012]S\u0011GC\u001a\u0011\u001d!y\f\u001ca\u0001\u0003+\tq\"\u001b;fe\u0006\u0014G.\u001a$bGR|'/_\u000b\u0005\u000b\u001b+)*\u0006\u0002\u0006\u0010BA\u00111FC\u000b\u000b#+9\n\u0005\u0004\u0000\u0003gqX1\u0013\t\u0005\u0003\u000f))\nB\u0004\u0002\f5\u0014\r!!\u0004\u0011\tm\u0004Q1S\u0001\u0011EVLG\u000e\u001a$s_6duN\\4NCB,B!\"(\u00060V\u0011Qq\u0014\t\u000b\u0003W)y&\")\u0006,\u0016E\u0006\u0007BCR\u000bO\u0003Ba\u001f\u0001\u0006&B!\u0011qACT\t-)IK\\A\u0001\u0002\u0003\u0015\t!!\u0004\u0003\u0007}#\u0013\u0007\u0005\u0004\u0000\u0003gqXQ\u0016\t\u0005\u0003\u000f)y\u000bB\u0004\u0002\f9\u0014\r!!\u0004\u0011\tm\u0004QQ\u0016")
public final class LongMap<V>
extends AbstractMap<Object, V>
implements StrictOptimizedIterableOps<Tuple2<Object, V>, scala.collection.mutable.Iterable, LongMap<V>>,
Serializable {
    private final Function1<Object, V> defaultEntry;
    private int mask;
    public int scala$collection$mutable$LongMap$$extraKeys;
    public Object scala$collection$mutable$LongMap$$zeroValue;
    public Object scala$collection$mutable$LongMap$$minValue;
    private int _size;
    private int _vacant;
    public long[] scala$collection$mutable$LongMap$$_keys;
    public Object[] scala$collection$mutable$LongMap$$_values;

    public static <V> BuildFrom<LongMap<?>, Tuple2<Object, V>, LongMap<V>> buildFromLongMap() {
        return LongMap$ToBuildFrom$.MODULE$;
    }

    public static <V> BuildFrom<Object, Tuple2<Object, V>, LongMap<V>> toBuildFrom(LongMap$ factory2) {
        return LongMap$ToBuildFrom$.MODULE$;
    }

    public static <V> Factory<Tuple2<Object, V>, LongMap<V>> toFactory(LongMap$ dummy) {
        return LongMap$ToFactory$.MODULE$;
    }

    public static <V> LongMap<V> fromZip(Iterable<Object> keys2, Iterable<V> values2) {
        return LongMap$.MODULE$.fromZip(keys2, values2);
    }

    public static <V> LongMap<V> fromZip(long[] keys2, Object values2) {
        return LongMap$.MODULE$.fromZip(keys2, values2);
    }

    public static <V> ReusableBuilder<Tuple2<Object, V>, LongMap<V>> newBuilder() {
        return new LongMapBuilder();
    }

    public static <V> LongMap<V> from(IterableOnce<Tuple2<Object, V>> source2) {
        return LongMap$.MODULE$.from(source2);
    }

    @Override
    public Tuple2<LongMap<V>, LongMap<V>> partition(Function1<Tuple2<Object, V>, Object> p) {
        return StrictOptimizedIterableOps.partition$(this, p);
    }

    @Override
    public Tuple2<LongMap<V>, LongMap<V>> span(Function1<Tuple2<Object, V>, Object> p) {
        return StrictOptimizedIterableOps.span$(this, p);
    }

    @Override
    public <A1, A2> Tuple2<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>> unzip(Function1<Tuple2<Object, V>, Tuple2<A1, A2>> asPair) {
        return StrictOptimizedIterableOps.unzip$(this, asPair);
    }

    @Override
    public <A1, A2, A3> Tuple3<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>, scala.collection.mutable.Iterable<A3>> unzip3(Function1<Tuple2<Object, V>, Tuple3<A1, A2, A3>> asTriple) {
        return StrictOptimizedIterableOps.unzip3$(this, asTriple);
    }

    @Override
    public Object map(Function1 f) {
        return StrictOptimizedIterableOps.map$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedMap(Builder<B, C2> b, Function1<Tuple2<Object, V>, B> f) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedMap$(this, b, f);
    }

    @Override
    public Object flatMap(Function1 f) {
        return StrictOptimizedIterableOps.flatMap$(this, f);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatMap(Builder<B, C2> b, Function1<Tuple2<Object, V>, IterableOnce<B>> f) {
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
    public final <B, C2> C2 strictOptimizedCollect(Builder<B, C2> b, PartialFunction<Tuple2<Object, V>, B> pf) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedCollect$(this, b, pf);
    }

    @Override
    public Object flatten(Function1 toIterableOnce) {
        return StrictOptimizedIterableOps.flatten$(this, toIterableOnce);
    }

    @Override
    public final <B, C2> C2 strictOptimizedFlatten(Builder<B, C2> b, Function1<Tuple2<Object, V>, IterableOnce<B>> toIterableOnce) {
        return (C2)StrictOptimizedIterableOps.strictOptimizedFlatten$(this, b, toIterableOnce);
    }

    @Override
    public Object zip(IterableOnce that) {
        return StrictOptimizedIterableOps.zip$(this, that);
    }

    @Override
    public final <B, C2> C2 strictOptimizedZip(IterableOnce<B> that, Builder<Tuple2<Tuple2<Object, V>, B>, C2> b) {
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
    public <A1, A2> Tuple2<scala.collection.mutable.Iterable<A1>, scala.collection.mutable.Iterable<A2>> partitionMap(Function1<Tuple2<Object, V>, Either<A1, A2>> f) {
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
    public LongMap<V> fromSpecific(IterableOnce<Tuple2<Object, V>> coll) {
        Builder<Tuple2<Object, V>, LongMap<V>> b = this.newSpecificBuilder();
        if (b == null) {
            throw null;
        }
        b.sizeHint(coll, 0);
        b.addAll(coll);
        return b.result();
    }

    @Override
    public Builder<Tuple2<Object, V>, LongMap<V>> newSpecificBuilder() {
        return new GrowableBuilder<Tuple2<Object, V>, LongMap<V>>(new LongMap<V>());
    }

    private void defaultInitialize(int n) {
        this.mask = n < 0 ? 7 : (1 << 32 - Integer.numberOfLeadingZeros(n - 1)) - 1 & 0x3FFFFFFF | 7;
        this.scala$collection$mutable$LongMap$$_keys = new long[this.mask + 1];
        this.scala$collection$mutable$LongMap$$_values = new Object[this.mask + 1];
    }

    public void initializeTo(int m4, int ek, Object zv, Object mv, int sz, int vc, long[] kz, Object[] vz) {
        this.mask = m4;
        this.scala$collection$mutable$LongMap$$extraKeys = ek;
        this.scala$collection$mutable$LongMap$$zeroValue = zv;
        this.scala$collection$mutable$LongMap$$minValue = mv;
        this._size = sz;
        this._vacant = vc;
        this.scala$collection$mutable$LongMap$$_keys = kz;
        this.scala$collection$mutable$LongMap$$_values = vz;
    }

    @Override
    public int size() {
        return this._size + (this.scala$collection$mutable$LongMap$$extraKeys + 1) / 2;
    }

    @Override
    public int knownSize() {
        return this.size();
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public LongMap<V> empty() {
        return new LongMap<V>();
    }

    private boolean imbalanced() {
        return (double)(this._size + this._vacant) > 0.5 * (double)this.mask || this._vacant > this._size;
    }

    private int toIndex(long k) {
        int h2 = (int)((k ^ k >>> 32) & 0xFFFFFFFFL);
        int x = (h2 ^ h2 >>> 16) * -2048144789;
        return (x ^ x >>> 13) & this.mask;
    }

    private int seekEmpty(long k) {
        int e = this.toIndex(k);
        int x = 0;
        while (this.scala$collection$mutable$LongMap$$_keys[e] != 0L) {
            e = e + 2 * (++x + 1) * x - 3 & this.mask;
        }
        return e;
    }

    private int seekEntry(long k) {
        int e = this.toIndex(k);
        int x = 0;
        while (true) {
            long q;
            if ((q = this.scala$collection$mutable$LongMap$$_keys[e]) == k) {
                return e;
            }
            if (q == 0L) break;
            e = e + 2 * (++x + 1) * x - 3 & this.mask;
        }
        return e | Integer.MIN_VALUE;
    }

    private int seekEntryOrOpen(long k) {
        long q;
        int e = this.toIndex(k);
        int x = 0;
        while (true) {
            if ((q = this.scala$collection$mutable$LongMap$$_keys[e]) == k) {
                return e;
            }
            if (q + q == 0L) break;
            e = e + 2 * (++x + 1) * x - 3 & this.mask;
        }
        if (q == 0L) {
            return e | Integer.MIN_VALUE;
        }
        int o = e | 0xC0000000;
        while (true) {
            if ((q = this.scala$collection$mutable$LongMap$$_keys[e]) == k) {
                return e;
            }
            if (q == 0L) break;
            e = e + 2 * (++x + 1) * x - 3 & this.mask;
        }
        return o;
    }

    @Override
    public boolean contains(long key) {
        if (key == -key) {
            return ((int)(key >>> 63) + 1 & this.scala$collection$mutable$LongMap$$extraKeys) != 0;
        }
        return this.seekEntry(key) >= 0;
    }

    @Override
    public Option<V> get(long key) {
        if (key == -key) {
            if (((int)(key >>> 63) + 1 & this.scala$collection$mutable$LongMap$$extraKeys) == 0) {
                return None$.MODULE$;
            }
            if (key == 0L) {
                return new Some<Object>(this.scala$collection$mutable$LongMap$$zeroValue);
            }
            return new Some<Object>(this.scala$collection$mutable$LongMap$$minValue);
        }
        int i = this.seekEntry(key);
        if (i < 0) {
            return None$.MODULE$;
        }
        return new Some<Object>(this.scala$collection$mutable$LongMap$$_values[i]);
    }

    @Override
    public <V1> V1 getOrElse(long key, Function0<V1> function0) {
        if (key == -key) {
            if (((int)(key >>> 63) + 1 & this.scala$collection$mutable$LongMap$$extraKeys) == 0) {
                return function0.apply();
            }
            if (key == 0L) {
                return (V1)this.scala$collection$mutable$LongMap$$zeroValue;
            }
            return (V1)this.scala$collection$mutable$LongMap$$minValue;
        }
        int i = this.seekEntry(key);
        if (i < 0) {
            return function0.apply();
        }
        return (V1)this.scala$collection$mutable$LongMap$$_values[i];
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public V getOrElseUpdate(long key, Function0<V> defaultValue) {
        if (key == -key) {
            int kbits = (int)(key >>> 63) + 1;
            if ((kbits & this.scala$collection$mutable$LongMap$$extraKeys) == 0) {
                V value = defaultValue.apply();
                this.scala$collection$mutable$LongMap$$extraKeys |= kbits;
                if (key == 0L) {
                    this.scala$collection$mutable$LongMap$$zeroValue = value;
                } else {
                    this.scala$collection$mutable$LongMap$$minValue = value;
                }
                return value;
            }
            if (key == 0L) {
                return (V)this.scala$collection$mutable$LongMap$$zeroValue;
            }
            return (V)this.scala$collection$mutable$LongMap$$minValue;
        }
        int i = this.seekEntryOrOpen(key);
        if (i < 0) {
            void var8_7;
            long[] ok = this.scala$collection$mutable$LongMap$$_keys;
            V ans = defaultValue.apply();
            if (ok != this.scala$collection$mutable$LongMap$$_keys && (i = this.seekEntryOrOpen(key)) >= 0) {
                --this._size;
            }
            ++this._size;
            int j = i & 0x3FFFFFFF;
            this.scala$collection$mutable$LongMap$$_keys[j] = key;
            this.scala$collection$mutable$LongMap$$_values[j] = var8_7;
            if ((i & 0x40000000) != 0) {
                --this._vacant;
            } else if (this.imbalanced()) {
                this.repack();
            }
            return var8_7;
        }
        return (V)this.scala$collection$mutable$LongMap$$_values[i];
    }

    public V getOrNull(long key) {
        if (key == -key) {
            if (((int)(key >>> 63) + 1 & this.scala$collection$mutable$LongMap$$extraKeys) == 0) {
                return null;
            }
            if (key == 0L) {
                return (V)this.scala$collection$mutable$LongMap$$zeroValue;
            }
            return (V)this.scala$collection$mutable$LongMap$$minValue;
        }
        int i = this.seekEntry(key);
        if (i < 0) {
            return null;
        }
        return (V)this.scala$collection$mutable$LongMap$$_values[i];
    }

    @Override
    public V apply(long key) {
        if (key == -key) {
            if (((int)(key >>> 63) + 1 & this.scala$collection$mutable$LongMap$$extraKeys) == 0) {
                return this.defaultEntry.apply(key);
            }
            if (key == 0L) {
                return (V)this.scala$collection$mutable$LongMap$$zeroValue;
            }
            return (V)this.scala$collection$mutable$LongMap$$minValue;
        }
        int i = this.seekEntry(key);
        if (i < 0) {
            return this.defaultEntry.apply(key);
        }
        return (V)this.scala$collection$mutable$LongMap$$_values[i];
    }

    @Override
    public V default(long key) {
        return this.defaultEntry.apply(key);
    }

    private void repack(int newMask) {
        long[] ok = this.scala$collection$mutable$LongMap$$_keys;
        Object[] ov = this.scala$collection$mutable$LongMap$$_values;
        this.mask = newMask;
        this.scala$collection$mutable$LongMap$$_keys = new long[this.mask + 1];
        this.scala$collection$mutable$LongMap$$_values = new Object[this.mask + 1];
        this._vacant = 0;
        for (int i = 0; i < ok.length; ++i) {
            long k = ok[i];
            if (k == -k) continue;
            int j = this.seekEmpty(k);
            this.scala$collection$mutable$LongMap$$_keys[j] = k;
            this.scala$collection$mutable$LongMap$$_values[j] = ov[i];
        }
    }

    public void repack() {
        int m4 = this.mask;
        if ((double)(this._size + this._vacant) >= 0.5 * (double)this.mask && !((double)this._vacant > 0.2 * (double)this.mask)) {
            m4 = (m4 << 1) + 1 & 0x3FFFFFFF;
        }
        while (m4 > 8 && 8 * this._size < m4) {
            m4 >>>= 1;
        }
        this.repack(m4);
    }

    @Override
    public Option<V> put(long key, V value) {
        if (key == -key) {
            if (key == 0L) {
                Option ans = (this.scala$collection$mutable$LongMap$$extraKeys & 1) == 1 ? new Some<Object>(this.scala$collection$mutable$LongMap$$zeroValue) : None$.MODULE$;
                this.scala$collection$mutable$LongMap$$zeroValue = value;
                this.scala$collection$mutable$LongMap$$extraKeys |= 1;
                return ans;
            }
            Option ans = (this.scala$collection$mutable$LongMap$$extraKeys & 2) == 1 ? new Some<Object>(this.scala$collection$mutable$LongMap$$minValue) : None$.MODULE$;
            this.scala$collection$mutable$LongMap$$minValue = value;
            this.scala$collection$mutable$LongMap$$extraKeys |= 2;
            return ans;
        }
        int i = this.seekEntryOrOpen(key);
        if (i < 0) {
            int j = i & 0x3FFFFFFF;
            this.scala$collection$mutable$LongMap$$_keys[j] = key;
            this.scala$collection$mutable$LongMap$$_values[j] = value;
            ++this._size;
            if ((i & 0x40000000) != 0) {
                --this._vacant;
            } else if (this.imbalanced()) {
                this.repack();
            }
            return None$.MODULE$;
        }
        Some<Object> ans = new Some<Object>(this.scala$collection$mutable$LongMap$$_values[i]);
        this.scala$collection$mutable$LongMap$$_keys[i] = key;
        this.scala$collection$mutable$LongMap$$_values[i] = value;
        return ans;
    }

    @Override
    public void update(long key, V value) {
        if (key == -key) {
            if (key == 0L) {
                this.scala$collection$mutable$LongMap$$zeroValue = value;
                this.scala$collection$mutable$LongMap$$extraKeys |= 1;
                return;
            }
            this.scala$collection$mutable$LongMap$$minValue = value;
            this.scala$collection$mutable$LongMap$$extraKeys |= 2;
            return;
        }
        int i = this.seekEntryOrOpen(key);
        if (i < 0) {
            int j = i & 0x3FFFFFFF;
            this.scala$collection$mutable$LongMap$$_keys[j] = key;
            this.scala$collection$mutable$LongMap$$_values[j] = value;
            ++this._size;
            if ((i & 0x40000000) != 0) {
                --this._vacant;
                return;
            }
            if (this.imbalanced()) {
                this.repack();
                return;
            }
            return;
        }
        this.scala$collection$mutable$LongMap$$_keys[i] = key;
        this.scala$collection$mutable$LongMap$$_values[i] = value;
    }

    public LongMap<V> $plus$eq(long key, V value) {
        this.update(key, value);
        return this;
    }

    public final LongMap<V> addOne(long key, V value) {
        this.update(key, value);
        return this;
    }

    @Override
    public final LongMap<V> addOne(Tuple2<Object, V> kv) {
        this.update(kv._1$mcJ$sp(), kv._2());
        return this;
    }

    @Override
    public LongMap<V> subtractOne(long key) {
        if (key == -key) {
            if (key == 0L) {
                this.scala$collection$mutable$LongMap$$extraKeys &= 2;
                this.scala$collection$mutable$LongMap$$zeroValue = null;
            } else {
                this.scala$collection$mutable$LongMap$$extraKeys &= 1;
                this.scala$collection$mutable$LongMap$$minValue = null;
            }
        } else {
            int i = this.seekEntry(key);
            if (i >= 0) {
                --this._size;
                ++this._vacant;
                this.scala$collection$mutable$LongMap$$_keys[i] = Long.MIN_VALUE;
                this.scala$collection$mutable$LongMap$$_values[i] = null;
            }
        }
        return this;
    }

    @Override
    public Iterator<Tuple2<Object, V>> iterator() {
        return new AbstractIterator<Tuple2<Object, V>>(this){
            private final long[] kz;
            private final Object[] vz;
            private Tuple2<Object, V> nextPair;
            private Tuple2<Object, V> anotherPair;
            private int index;

            public boolean hasNext() {
                block6: {
                    block5: {
                        if (this.nextPair != null) break block5;
                        if (this.index >= this.kz.length) break block6;
                        long q = this.kz[this.index];
                        while (q == -q) {
                            ++this.index;
                            if (this.index >= this.kz.length) {
                                return false;
                            }
                            q = this.kz[this.index];
                        }
                        this.nextPair = new Tuple2<Long, Object>(this.kz[this.index], this.vz[this.index]);
                        ++this.index;
                    }
                    return true;
                }
                return false;
            }

            public Tuple2<Object, V> next() {
                if (this.nextPair == null && !this.hasNext()) {
                    throw new NoSuchElementException("next");
                }
                Tuple2<Object, V> ans = this.nextPair;
                if (this.anotherPair != null) {
                    this.nextPair = this.anotherPair;
                    this.anotherPair = null;
                } else {
                    this.nextPair = null;
                }
                return ans;
            }
            {
                this.kz = $outer.scala$collection$mutable$LongMap$$_keys;
                this.vz = $outer.scala$collection$mutable$LongMap$$_values;
                this.nextPair = $outer.scala$collection$mutable$LongMap$$extraKeys == 0 ? null : (($outer.scala$collection$mutable$LongMap$$extraKeys & 1) == 1 ? new Tuple2<Long, Object>(0L, $outer.scala$collection$mutable$LongMap$$zeroValue) : new Tuple2<Long, Object>(Long.MIN_VALUE, $outer.scala$collection$mutable$LongMap$$minValue));
                this.anotherPair = $outer.scala$collection$mutable$LongMap$$extraKeys == 3 ? new Tuple2<Long, Object>(Long.MIN_VALUE, $outer.scala$collection$mutable$LongMap$$minValue) : null;
                this.index = 0;
            }
        };
    }

    @Override
    public Iterator<Object> keysIterator() {
        return MapOps.keysIterator$(this);
    }

    @Override
    public Iterator<V> valuesIterator() {
        return MapOps.valuesIterator$(this);
    }

    @Override
    public <U> void foreach(Function1<Tuple2<Object, V>, U> f) {
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 1) == 1) {
            f.apply(new Tuple2<Long, Object>(0L, this.scala$collection$mutable$LongMap$$zeroValue));
        }
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 2) == 2) {
            f.apply(new Tuple2<Long, Object>(Long.MIN_VALUE, this.scala$collection$mutable$LongMap$$minValue));
        }
        int i = 0;
        int j = 0;
        while (i < this.scala$collection$mutable$LongMap$$_keys.length & j < this._size) {
            long k = this.scala$collection$mutable$LongMap$$_keys[i];
            if (k != -k) {
                ++j;
                f.apply(new Tuple2<Long, Object>(k, this.scala$collection$mutable$LongMap$$_values[i]));
            }
            ++i;
        }
    }

    @Override
    public <U> void foreachEntry(Function2<Object, V, U> f) {
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 1) == 1) {
            f.apply(0L, this.scala$collection$mutable$LongMap$$zeroValue);
        }
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 2) == 2) {
            f.apply(Long.MIN_VALUE, this.scala$collection$mutable$LongMap$$minValue);
        }
        int i = 0;
        int j = 0;
        while (i < this.scala$collection$mutable$LongMap$$_keys.length & j < this._size) {
            long k = this.scala$collection$mutable$LongMap$$_keys[i];
            if (k != -k) {
                ++j;
                f.apply(k, this.scala$collection$mutable$LongMap$$_values[i]);
            }
            ++i;
        }
    }

    @Override
    public LongMap<V> clone() {
        long[] kz = Arrays.copyOf(this.scala$collection$mutable$LongMap$$_keys, this.scala$collection$mutable$LongMap$$_keys.length);
        Object[] vz = Arrays.copyOf(this.scala$collection$mutable$LongMap$$_values, this.scala$collection$mutable$LongMap$$_values.length);
        LongMap<V> lm = new LongMap<V>(this.defaultEntry, 1, false);
        lm.initializeTo(this.mask, this.scala$collection$mutable$LongMap$$extraKeys, this.scala$collection$mutable$LongMap$$zeroValue, this.scala$collection$mutable$LongMap$$minValue, this._size, this._vacant, kz, vz);
        return lm;
    }

    @Override
    public <V1> LongMap<V1> $plus(Tuple2<Object, V1> kv) {
        Object lm = this.clone();
        if (lm == null) {
            throw null;
        }
        ((LongMap)lm).update(kv._1$mcJ$sp(), kv._2());
        return lm;
    }

    @Override
    public <V1> LongMap<V1> $plus(Tuple2<Object, V1> elem1, Tuple2<Object, V1> elem2, Seq<Tuple2<Object, V1>> elems) {
        IterableOps m4 = ((LongMap)this.$plus((Tuple2)elem1)).$plus((Tuple2)elem2);
        if (elems.isEmpty()) {
            return m4;
        }
        return ((LongMap)m4).concat(elems);
    }

    @Override
    public <V1> LongMap<V1> concat(IterableOnce<Tuple2<Object, V1>> xs) {
        Object lm = this.clone();
        xs.iterator().foreach(arg_0 -> LongMap.$anonfun$concat$1((LongMap)lm, arg_0));
        return lm;
    }

    @Override
    public <V1> LongMap<V1> $plus$plus(IterableOnce<Tuple2<Object, V1>> xs) {
        return this.concat((IterableOnce)xs);
    }

    @Override
    public <V1> LongMap<V1> updated(long key, V1 value) {
        Object object = this.clone();
        if (object == null) {
            throw null;
        }
        Object addOne_this = object;
        ((LongMap)addOne_this).update(key, value);
        return addOne_this;
    }

    public <A> void foreachKey(Function1<Object, A> f) {
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 1) == 1) {
            f.apply(0L);
        }
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 2) == 2) {
            f.apply(Long.MIN_VALUE);
        }
        int i = 0;
        int j = 0;
        while (i < this.scala$collection$mutable$LongMap$$_keys.length & j < this._size) {
            long k = this.scala$collection$mutable$LongMap$$_keys[i];
            if (k != -k) {
                ++j;
                f.apply(k);
            }
            ++i;
        }
    }

    public <A> void foreachValue(Function1<V, A> f) {
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 1) == 1) {
            f.apply(this.scala$collection$mutable$LongMap$$zeroValue);
        }
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 2) == 2) {
            f.apply(this.scala$collection$mutable$LongMap$$minValue);
        }
        int i = 0;
        int j = 0;
        while (i < this.scala$collection$mutable$LongMap$$_keys.length & j < this._size) {
            long k = this.scala$collection$mutable$LongMap$$_keys[i];
            if (k != -k) {
                ++j;
                f.apply(this.scala$collection$mutable$LongMap$$_values[i]);
            }
            ++i;
        }
    }

    public <V1> LongMap<V1> mapValuesNow(Function1<V, V1> f) {
        Object zv = (this.scala$collection$mutable$LongMap$$extraKeys & 1) == 1 ? f.apply(this.scala$collection$mutable$LongMap$$zeroValue) : null;
        Object mv = (this.scala$collection$mutable$LongMap$$extraKeys & 2) == 2 ? f.apply(this.scala$collection$mutable$LongMap$$minValue) : null;
        LongMap<Nothing$> lm = new LongMap<Nothing$>(LongMap$.MODULE$.scala$collection$mutable$LongMap$$exceptionDefault(), 1, false);
        long[] kz = Arrays.copyOf(this.scala$collection$mutable$LongMap$$_keys, this.scala$collection$mutable$LongMap$$_keys.length);
        Object[] vz = new Object[this.scala$collection$mutable$LongMap$$_values.length];
        int i = 0;
        int j = 0;
        while (i < this.scala$collection$mutable$LongMap$$_keys.length & j < this._size) {
            long k = this.scala$collection$mutable$LongMap$$_keys[i];
            if (k != -k) {
                ++j;
                vz[i] = f.apply(this.scala$collection$mutable$LongMap$$_values[i]);
            }
            ++i;
        }
        lm.initializeTo(this.mask, this.scala$collection$mutable$LongMap$$extraKeys, zv, mv, this._size, this._vacant, kz, vz);
        return lm;
    }

    public final LongMap<V> transformValues(Function1<V, V> f) {
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 1) == 1) {
            this.scala$collection$mutable$LongMap$$zeroValue = f.apply(this.scala$collection$mutable$LongMap$$zeroValue);
        }
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 2) == 2) {
            this.scala$collection$mutable$LongMap$$minValue = f.apply(this.scala$collection$mutable$LongMap$$minValue);
        }
        int transformValuesInPlace_i = 0;
        int transformValuesInPlace_j = 0;
        while (transformValuesInPlace_i < this.scala$collection$mutable$LongMap$$_keys.length & transformValuesInPlace_j < this._size) {
            long transformValuesInPlace_k = this.scala$collection$mutable$LongMap$$_keys[transformValuesInPlace_i];
            if (transformValuesInPlace_k != -transformValuesInPlace_k) {
                ++transformValuesInPlace_j;
                this.scala$collection$mutable$LongMap$$_values[transformValuesInPlace_i] = f.apply(this.scala$collection$mutable$LongMap$$_values[transformValuesInPlace_i]);
            }
            ++transformValuesInPlace_i;
        }
        return this;
    }

    public LongMap<V> transformValuesInPlace(Function1<V, V> f) {
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 1) == 1) {
            this.scala$collection$mutable$LongMap$$zeroValue = f.apply(this.scala$collection$mutable$LongMap$$zeroValue);
        }
        if ((this.scala$collection$mutable$LongMap$$extraKeys & 2) == 2) {
            this.scala$collection$mutable$LongMap$$minValue = f.apply(this.scala$collection$mutable$LongMap$$minValue);
        }
        int i = 0;
        int j = 0;
        while (i < this.scala$collection$mutable$LongMap$$_keys.length & j < this._size) {
            long k = this.scala$collection$mutable$LongMap$$_keys[i];
            if (k != -k) {
                ++j;
                this.scala$collection$mutable$LongMap$$_values[i] = f.apply(this.scala$collection$mutable$LongMap$$_values[i]);
            }
            ++i;
        }
        return this;
    }

    @Override
    public <V2> LongMap<V2> map(Function1<Tuple2<Object, V>, Tuple2<Object, V2>> f) {
        return LongMap$.MODULE$.from(new View.Map<Tuple2<Object, V>, Tuple2<Object, V2>>(this, f));
    }

    @Override
    public <V2> LongMap<V2> flatMap(Function1<Tuple2<Object, V>, IterableOnce<Tuple2<Object, V2>>> f) {
        return LongMap$.MODULE$.from(new View.FlatMap<Tuple2<Object, V>, Tuple2<Object, V2>>(this, f));
    }

    @Override
    public <V2> LongMap<V2> collect(PartialFunction<Tuple2<Object, V>, Tuple2<Object, V2>> pf) {
        LongMapBuilder strictOptimizedCollect_b = new LongMapBuilder();
        Object strictOptimizedCollect_marker = Statics.pfMarker;
        AbstractIterator strictOptimizedCollect_it = new /* invalid duplicate definition of identical inner class */;
        while (strictOptimizedCollect_it.hasNext()) {
            Object strictOptimizedCollect_elem = strictOptimizedCollect_it.next();
            Object strictOptimizedCollect_v = pf.applyOrElse(strictOptimizedCollect_elem, arg_0 -> StrictOptimizedIterableOps.$anonfun$strictOptimizedCollect$1(strictOptimizedCollect_marker, arg_0));
            if (strictOptimizedCollect_marker == strictOptimizedCollect_v) continue;
            strictOptimizedCollect_b.addOne((Tuple2)strictOptimizedCollect_v);
        }
        return strictOptimizedCollect_b.elems();
    }

    public Object writeReplace() {
        return new DefaultSerializationProxy<Tuple2<Object, Object>>(LongMap$ToFactory$.MODULE$, this);
    }

    @Override
    public String className() {
        return "LongMap";
    }

    public static final /* synthetic */ LongMap $anonfun$concat$1(LongMap lm$1, Tuple2 kv) {
        return (LongMap)lm$1.$plus$eq(kv);
    }

    public LongMap(Function1<Object, V> defaultEntry, int initialBufferSize, boolean initBlank) {
        this.defaultEntry = defaultEntry;
        this.mask = 0;
        this.scala$collection$mutable$LongMap$$extraKeys = 0;
        this.scala$collection$mutable$LongMap$$zeroValue = null;
        this.scala$collection$mutable$LongMap$$minValue = null;
        this._size = 0;
        this._vacant = 0;
        this.scala$collection$mutable$LongMap$$_keys = null;
        this.scala$collection$mutable$LongMap$$_values = null;
        if (initBlank) {
            this.defaultInitialize(initialBufferSize);
        }
    }

    public LongMap() {
        this(LongMap$.MODULE$.scala$collection$mutable$LongMap$$exceptionDefault(), 16, true);
    }

    public LongMap(Function1<Object, V> defaultEntry) {
        this(defaultEntry, 16, true);
    }

    public LongMap(int initialBufferSize) {
        this(LongMap$.MODULE$.scala$collection$mutable$LongMap$$exceptionDefault(), initialBufferSize, true);
    }

    public LongMap(Function1<Object, V> defaultEntry, int initialBufferSize) {
        this(defaultEntry, initialBufferSize, true);
    }

    public static final class LongMapBuilder<V>
    implements ReusableBuilder<Tuple2<Object, V>, LongMap<V>> {
        private LongMap<V> elems = new LongMap();

        @Override
        public void sizeHint(int size) {
            Builder.sizeHint$(this, size);
        }

        @Override
        public final void sizeHint(IterableOnce<?> coll, int delta) {
            Builder.sizeHint$(this, coll, delta);
        }

        @Override
        public final int sizeHint$default$2() {
            return Builder.sizeHint$default$2$(this);
        }

        @Override
        public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
            Builder.sizeHintBounded$(this, size, boundingColl);
        }

        @Override
        public <NewTo> Builder<Tuple2<Object, V>, NewTo> mapResult(Function1<LongMap<V>, NewTo> f) {
            return Builder.mapResult$(this, f);
        }

        @Override
        public final Growable $plus$eq(Object elem) {
            return Growable.$plus$eq$(this, elem);
        }

        @Override
        public final Growable $plus$eq(Object elem1, Object elem2, Seq elems) {
            return Growable.$plus$eq$(this, elem1, elem2, elems);
        }

        @Override
        public Growable<Tuple2<Object, V>> addAll(IterableOnce<Tuple2<Object, V>> xs) {
            return Growable.addAll$(this, xs);
        }

        @Override
        public final Growable<Tuple2<Object, V>> $plus$plus$eq(IterableOnce<Tuple2<Object, V>> xs) {
            return Growable.$plus$plus$eq$(this, xs);
        }

        public LongMap<V> elems() {
            return this.elems;
        }

        public void elems_$eq(LongMap<V> x$1) {
            this.elems = x$1;
        }

        @Override
        public LongMapBuilder<V> addOne(Tuple2<Object, V> entry) {
            LongMap<V> longMap = this.elems();
            if (longMap == null) {
                throw null;
            }
            longMap.update(entry._1$mcJ$sp(), entry._2());
            return this;
        }

        @Override
        public void clear() {
            this.elems_$eq(new LongMap());
        }

        @Override
        public LongMap<V> result() {
            return this.elems();
        }

        @Override
        public int knownSize() {
            LongMap<V> longMap = this.elems();
            if (longMap == null) {
                throw null;
            }
            return longMap.size();
        }
    }
}

