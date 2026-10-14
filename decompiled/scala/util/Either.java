/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.$less$colon$less;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Seq$;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Either$MergeableEither$;
import scala.util.Failure;
import scala.util.Left;
import scala.util.Right;
import scala.util.Success;
import scala.util.Try;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u00155e!\u00027n\u0003C\u0011\bbBA\u0003\u0001\u0011\u0005\u0011q\u0001\u0005\b\u0003S\u0001A\u0011AA\u0016\u0011\u001d\t\u0019\u0007\u0001C\u0001\tWBq\u0001\"\u001e\u0001\t\u0003!9\bC\u0004\u0005\u000e\u0002!\t\u0001b$\t\u000f\u0011M\u0005\u0001\"\u0001\u0005\u0016\"9AQ\u0017\u0001\u0005\u0002\u0011]\u0006b\u0002B\u0018\u0001\u0011\u0005AQ\u001a\u0005\b\u0005\u0017\u0002A\u0011\u0001Cm\u0011\u001d!)\u000f\u0001C\u0001\tODq\u0001\"?\u0001\t\u000b!Y\u0010C\u0004\u0003`\u0001!\t!b\u0002\t\u000f\t%\u0004\u0001\"\u0001\u0006\u000e!9!q\u000e\u0001\u0005\u0002\u0015E\u0001bBC\u0012\u0001\u0011\u0005QQ\u0005\u0005\b\u0005\u000f\u0003A\u0011AC\u001c\u0011\u001d))\u0005\u0001C\u0001\u000b\u000fBqA!1\u0001\t\u0003)I\u0006C\u0004\u0003L\u0002!\t!\"\u001a\t\u000f\u0015%\u0004\u0001\"\u0001\u0006l!9QQ\u0010\u0001\u0007\u0002\u0015}\u0004bBCA\u0001\u0019\u0005QqP\u0004\b\u0003ci\u0007\u0012AA\u001a\r\u0019aW\u000e#\u0001\u00026!9\u0011Q\u0001\r\u0005\u0002\u0005\u0015\u0003bBA$1\u0011\u0005\u0011\u0011\n\u0004\u0007\u0003_B2!!\u001d\t\u001d\u0005m4\u0004\"A\u0001\u0006\u000b\u0015\r\u0011\"\u0003\u0002~!Y\u0011QQ\u000e\u0003\u0006\u0003\u0005\u000b\u0011BA@\u0011\u001d\t)a\u0007C\u0001\u0003\u000fCq!!%\u001c\t\u0003\t\u0019\nC\u0005\u0002\u0016n\t\t\u0011\"\u0011\u0002\u0018\"I\u0011qT\u000e\u0002\u0002\u0013\u0005\u0013\u0011U\u0004\n\u0003OC\u0012\u0011!E\u0001\u0003S3\u0011\"a\u001c\u0019\u0003\u0003E\t!a+\t\u000f\u0005\u00151\u0005\"\u0001\u0002.\"9\u0011qV\u0012\u0005\u0006\u0005E\u0006\"CA`G\u0005\u0005IQAAa\u0011%\timIA\u0001\n\u000b\ty\rC\u0005\u0002(b\t\t\u0011b\u0001\u0002`\u001a1\u0011Q\u001e\rC\u0003_D!B!\u0001*\u0005+\u0007I\u0011\u0001B\u0002\u0011)\u0011y!\u000bB\tB\u0003%!Q\u0001\u0005\b\u0003\u000bIC\u0011\u0001B\t\u0011\u001d\u00119\"\u000bC\u0001\u00053AqAa\f*\t\u0003\u0011\t\u0004C\u0004\u0003L%\"\tA!\u0014\t\u000f\t}\u0013\u0006\"\u0001\u0003b!9!\u0011N\u0015\u0005\u0002\t-\u0004b\u0002B8S\u0011\u0005!\u0011\u000f\u0005\b\u0005\u000fKC\u0011\u0001BE\u0011\u001d\u00119*\u000bC\u0001\u00053CqA!-*\t\u0003\u0011\u0019\fC\u0004\u0003B&\"\tAa1\t\u000f\t-\u0017\u0006\"\u0001\u0003N\"I!\u0011[\u0015\u0002\u0002\u0013\u0005!1\u001b\u0005\n\u0005KL\u0013\u0013!C\u0001\u0005OD\u0011ba\u0001*\u0003\u0003%\te!\u0002\t\u0013\rM\u0011&!A\u0005\u0002\rU\u0001\"CB\fS\u0005\u0005I\u0011AB\r\u0011%\u0019i\"KA\u0001\n\u0003\u001ay\u0002C\u0005\u0004.%\n\t\u0011\"\u0001\u00040!I11G\u0015\u0002\u0002\u0013\u00053Q\u0007\u0005\n\u0003+K\u0013\u0011!C!\u0003/C\u0011b!\u000f*\u0003\u0003%\tea\u000f\t\u0013\u0005}\u0015&!A\u0005B\rur!CB!1\u0005\u0005\t\u0012AB\"\r%\ti\u000fGA\u0001\u0012\u0003\u0019)\u0005C\u0004\u0002\u0006\u0011#\taa\u0012\t\u0013\reB)!A\u0005F\rm\u0002\"CB%\t\u0006\u0005I\u0011QB&\u0011%\u0019i\u0006RA\u0001\n\u0003\u001by\u0006C\u0005\u0004v\u0011\u000b\t\u0011\"\u0003\u0004x\u001911q\u0010\rC\u0007\u0003C!B!\u0001K\u0005+\u0007I\u0011ABC\u0011)\u0011yA\u0013B\tB\u0003%1q\u0011\u0005\b\u0003\u000bQE\u0011ABI\u0011\u001d\u00119B\u0013C\u0001\u0007/CqAa\fK\t\u0003\u0019y\nC\u0004\u0003L)#\taa+\t\u000f\t}#\n\"\u0001\u0004:\"9!\u0011\u000e&\u0005\u0002\r}\u0006b\u0002B8\u0015\u0012\u000511\u0019\u0005\b\u0005\u000fSE\u0011ABl\u0011\u001d\u00119J\u0013C\u0001\u0007KDqA!-K\t\u0003\u0019)\u0010C\u0004\u0003B*#\t\u0001b\u0001\t\u000f\t-'\n\"\u0001\u0005\b!I!\u0011\u001b&\u0002\u0002\u0013\u0005A1\u0002\u0005\n\u0005KT\u0015\u0013!C\u0001\t;A\u0011ba\u0001K\u0003\u0003%\te!\u0002\t\u0013\rM!*!A\u0005\u0002\rU\u0001\"CB\f\u0015\u0006\u0005I\u0011\u0001C\u0014\u0011%\u0019iBSA\u0001\n\u0003\u001ay\u0002C\u0005\u0004.)\u000b\t\u0011\"\u0001\u0005,!I11\u0007&\u0002\u0002\u0013\u0005Cq\u0006\u0005\n\u0003+S\u0015\u0011!C!\u0003/C\u0011b!\u000fK\u0003\u0003%\tea\u000f\t\u0013\u0005}%*!A\u0005B\u0011Mr!\u0003C\u001f1\u0005\u0005\t\u0012\u0001C \r%\u0019y\bGA\u0001\u0012\u0003!\t\u0005C\u0004\u0002\u0006\u0015$\t\u0001b\u0011\t\u0013\reR-!A\u0005F\rm\u0002\"CB%K\u0006\u0005I\u0011\u0011C#\u0011%\u0019i&ZA\u0001\n\u0003#9\u0006C\u0005\u0004v\u0015\f\t\u0011\"\u0003\u0004x!I1Q\u000f\r\u0002\u0002\u0013%1q\u000f\u0002\u0007\u000b&$\b.\u001a:\u000b\u00059|\u0017\u0001B;uS2T\u0011\u0001]\u0001\u0006g\u000e\fG.Y\u0002\u0001+\u0015\u0019\u0018\u0011CA\u0013'\u0011\u0001A\u000f_>\u0011\u0005U4X\"A8\n\u0005]|'AB!osJ+g\r\u0005\u0002vs&\u0011!p\u001c\u0002\b!J|G-^2u!\taxP\u0004\u0002v{&\u0011ap\\\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\t\t!a\u0001\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u0005y|\u0017A\u0002\u001fj]&$h\b\u0006\u0002\u0002\nA9\u00111\u0002\u0001\u0002\u000e\u0005\rR\"A7\u0011\t\u0005=\u0011\u0011\u0003\u0007\u0001\t!\t\u0019\u0002\u0001CC\u0002\u0005U!!A!\u0012\t\u0005]\u0011Q\u0004\t\u0004k\u0006e\u0011bAA\u000e_\n9aj\u001c;iS:<\u0007cA;\u0002 %\u0019\u0011\u0011E8\u0003\u0007\u0005s\u0017\u0010\u0005\u0003\u0002\u0010\u0005\u0015B\u0001CA\u0014\u0001\u0011\u0015\r!!\u0006\u0003\u0003\t\u000bA\u0001\\3giV\u0011\u0011Q\u0006\t\b\u0003_I\u0013QBA\u0012\u001d\r\tYaF\u0001\u0007\u000b&$\b.\u001a:\u0011\u0007\u0005-\u0001d\u0005\u0003\u0019i\u0006]\u0002\u0003BA\u001d\u0003\u0007j!!a\u000f\u000b\t\u0005u\u0012qH\u0001\u0003S>T!!!\u0011\u0002\t)\fg/Y\u0005\u0005\u0003\u0003\tY\u0004\u0006\u0002\u00024\u0005!1m\u001c8e+\u0019\tY%!\u0015\u0002VQA\u0011QJA,\u0003C\nY\u0007E\u0004\u0002\f\u0001\ty%a\u0015\u0011\t\u0005=\u0011\u0011\u000b\u0003\b\u0003'Q\"\u0019AA\u000b!\u0011\ty!!\u0016\u0005\u000f\u0005\u001d\"D1\u0001\u0002\u0016!9\u0011\u0011\f\u000eA\u0002\u0005m\u0013\u0001\u0002;fgR\u00042!^A/\u0013\r\tyf\u001c\u0002\b\u0005>|G.Z1o\u0011!\t\u0019G\u0007CA\u0002\u0005\u0015\u0014!\u0002:jO\"$\b#B;\u0002h\u0005M\u0013bAA5_\nAAHY=oC6,g\b\u0003\u0005\u0002*i!\t\u0019AA7!\u0015)\u0018qMA(\u0005=iUM]4fC\ndW-R5uQ\u0016\u0014X\u0003BA:\u0003\u0007\u001b2aGA;!\r)\u0018qO\u0005\u0004\u0003sz'AB!osZ\u000bG.\u0001\u0013tG\u0006d\u0017\rJ;uS2$S)\u001b;iKJ$S*\u001a:hK\u0006\u0014G.Z#ji\",'\u000f\n\u0013y+\t\ty\bE\u0004\u0002\f\u0001\t\t)!!\u0011\t\u0005=\u00111\u0011\u0003\b\u0003'Y\"\u0019AA\u000b\u0003\u0015\u001a8-\u00197bIU$\u0018\u000e\u001c\u0013FSRDWM\u001d\u0013NKJ<W-\u00192mK\u0016KG\u000f[3sI\u0011B\b\u0005\u0006\u0003\u0002\n\u00065\u0005#BAF7\u0005\u0005U\"\u0001\r\t\u000f\u0005=e\u00041\u0001\u0002\u0000\u0005\t\u00010A\u0003nKJ<W-\u0006\u0002\u0002\u0002\u0006A\u0001.Y:i\u0007>$W\r\u0006\u0002\u0002\u001aB\u0019Q/a'\n\u0007\u0005uuNA\u0002J]R\fa!Z9vC2\u001cH\u0003BA.\u0003GC\u0011\"!*\"\u0003\u0003\u0005\r!!\b\u0002\u0007a$\u0013'A\bNKJ<W-\u00192mK\u0016KG\u000f[3s!\r\tYiI\n\u0003GQ$\"!!+\u0002\u001f5,'oZ3%Kb$XM\\:j_:,B!a-\u00028R!\u0011QWA]!\u0011\ty!a.\u0005\u000f\u0005MQE1\u0001\u0002\u0016!9\u00111X\u0013A\u0002\u0005u\u0016!\u0002\u0013uQ&\u001c\b#BAF7\u0005U\u0016A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:,B!a1\u0002LR!\u0011qSAc\u0011\u001d\tYL\na\u0001\u0003\u000f\u0004R!a#\u001c\u0003\u0013\u0004B!a\u0004\u0002L\u00129\u00111\u0003\u0014C\u0002\u0005U\u0011\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o+\u0011\t\t.!8\u0015\t\u0005M\u0017q\u001b\u000b\u0005\u00037\n)\u000eC\u0005\u0002&\u001e\n\t\u00111\u0001\u0002\u001e!9\u00111X\u0014A\u0002\u0005e\u0007#BAF7\u0005m\u0007\u0003BA\b\u0003;$q!a\u0005(\u0005\u0004\t)\"\u0006\u0003\u0002b\u0006\u001dH\u0003BAr\u0003S\u0004R!a#\u001c\u0003K\u0004B!a\u0004\u0002h\u00129\u00111\u0003\u0015C\u0002\u0005U\u0001bBAHQ\u0001\u0007\u00111\u001e\t\b\u0003\u0017\u0001\u0011Q]As\u00059aUM\u001a;Qe>TWm\u0019;j_:,b!!=\u0003\n\t51#B\u0015uq\u0006M\bcAA{\u007f:\u0019\u0011q_?\u000f\t\u0005e\u0018q`\u0007\u0003\u0003wT1!!@r\u0003\u0019a$o\\8u}%\t\u0001/A\u0001f+\t\u0011)\u0001E\u0004\u0002\f\u0001\u00119Aa\u0003\u0011\t\u0005=!\u0011\u0002\u0003\t\u0003'ICQ1\u0001\u0002\u0016A!\u0011q\u0002B\u0007\t!\t9#\u000bCC\u0002\u0005U\u0011AA3!)\u0011\u0011\u0019B!\u0006\u0011\u000f\u0005-\u0015Fa\u0002\u0003\f!9!\u0011\u0001\u0017A\u0002\t\u0015\u0011aA4fiV\u0011!q\u0001\u0015\f[\tu!1\u0005B\u0013\u0005S\u0011Y\u0003E\u0002v\u0005?I1A!\tp\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\u00119#A\u0012vg\u0016\u0004\u0003-R5uQ\u0016\u0014hf]<ba::W\r^(s\u000b2\u001cX\r\u0019\u0011j]N$X-\u00193\u0002\u000bMLgnY3\"\u0005\t5\u0012A\u0002\u001a/cMr\u0003'A\u0004g_J,\u0017m\u00195\u0016\t\tM\"q\t\u000b\u0005\u0005k\u0011Y\u0004E\u0002v\u0005oI1A!\u000fp\u0005\u0011)f.\u001b;\t\u000f\tub\u00061\u0001\u0003@\u0005\ta\rE\u0004v\u0005\u0003\u00129A!\u0012\n\u0007\t\rsNA\u0005Gk:\u001cG/[8ocA!\u0011q\u0002B$\t\u001d\u0011IE\fb\u0001\u0003+\u0011\u0011!V\u0001\nO\u0016$xJ]#mg\u0016,BAa\u0014\u0003TQ!!\u0011\u000bB-!\u0011\tyAa\u0015\u0005\u000f\tUsF1\u0001\u0003X\t\u0011\u0011)M\t\u0005\u0005\u000f\ti\u0002\u0003\u0005\u0003\\=\"\t\u0019\u0001B/\u0003\ty'\u000fE\u0003v\u0003O\u0012\t&\u0001\u0004g_J\fG\u000e\u001c\u000b\u0005\u00037\u0012\u0019\u0007C\u0004\u0003fA\u0002\rAa\u001a\u0002\u0003A\u0004r!\u001eB!\u0005\u000f\tY&\u0001\u0004fq&\u001cHo\u001d\u000b\u0005\u00037\u0012i\u0007C\u0004\u0003fE\u0002\rAa\u001a\u0002\u000f\u0019d\u0017\r^'baV1!1\u000fB=\u0005{\"BA!\u001e\u0003\u0004B9\u00111\u0002\u0001\u0003x\tm\u0004\u0003BA\b\u0005s\"qA!\u00163\u0005\u0004\t)\u0002\u0005\u0003\u0002\u0010\tuDa\u0002B@e\t\u0007!\u0011\u0011\u0002\u0003\u0005F\nBAa\u0003\u0002\u001e!9!Q\b\u001aA\u0002\t\u0015\u0005cB;\u0003B\t\u001d!QO\u0001\u0004[\u0006\u0004X\u0003\u0002BF\u0005##BA!$\u0003\u0014B9\u00111\u0002\u0001\u0003\u0010\n-\u0001\u0003BA\b\u0005##qA!\u00164\u0005\u0004\t)\u0002C\u0004\u0003>M\u0002\rA!&\u0011\u000fU\u0014\tEa\u0002\u0003\u0010\u00061a-\u001b7uKJ,BAa'\u0003(R!!Q\u0014BU!\u0015)(q\u0014BR\u0013\r\u0011\tk\u001c\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000f\u0005-\u0001Aa\u0002\u0003&B!\u0011q\u0002BT\t\u001d\u0011y\b\u000eb\u0001\u0003+AqA!\u001a5\u0001\u0004\u00119\u0007K\u00065\u0005;\u0011\u0019C!,\u0003*\t-\u0012E\u0001BX\u0003\u0011+6/\u001a\u0011aM&dG/\u001a:U_>\u0003H/[8oA2\u0002s\u000f[5dQ\u0002jwN]3!C\u000e\u001cWO]1uK2L\bE]3gY\u0016\u001cGo\u001d\u0011uQ\u0016\u0004#/\u001a;ve:\u0004C/\u001f9f\u000391\u0017\u000e\u001c;feR{w\n\u001d;j_:,BA!.\u0003>R!!q\u0017B`!\u0015)(q\u0014B]!\u001d\tY\u0001\u0001B\u0004\u0005w\u0003B!a\u0004\u0003>\u00129!qP\u001bC\u0002\u0005U\u0001b\u0002B3k\u0001\u0007!qM\u0001\u0006i>\u001cV-]\u000b\u0003\u0005\u000b\u0004R\u0001 Bd\u0005\u000fIAA!3\u0002\u0004\t\u00191+Z9\u0002\u0011Q|w\n\u001d;j_:,\"Aa4\u0011\u000bU\u0014yJa\u0002\u0002\t\r|\u0007/_\u000b\u0007\u0005+\u0014YNa8\u0015\t\t]'\u0011\u001d\t\b\u0003\u0017K#\u0011\u001cBo!\u0011\tyAa7\u0005\u000f\u0005M\u0001H1\u0001\u0002\u0016A!\u0011q\u0002Bp\t\u001d\t9\u0003\u000fb\u0001\u0003+A\u0011B!\u00019!\u0003\u0005\rAa9\u0011\u000f\u0005-\u0001A!7\u0003^\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTC\u0002Bu\u0005\u007f\u001c\t!\u0006\u0002\u0003l*\"!Q\u0001BwW\t\u0011y\u000f\u0005\u0003\u0003r\nmXB\u0001Bz\u0015\u0011\u0011)Pa>\u0002\u0013Ut7\r[3dW\u0016$'b\u0001B}_\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\tu(1\u001f\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,GaBA\ns\t\u0007\u0011Q\u0003\u0003\b\u0003OI$\u0019AA\u000b\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u00111q\u0001\t\u0005\u0007\u0013\u0019y!\u0004\u0002\u0004\f)!1QBA \u0003\u0011a\u0017M\\4\n\t\rE11\u0002\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005e\u0015A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003;\u0019Y\u0002C\u0005\u0002&r\n\t\u00111\u0001\u0002\u001a\u0006y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0004\"A111EB\u0015\u0003;i!a!\n\u000b\u0007\r\u001dr.\u0001\u0006d_2dWm\u0019;j_:LAaa\u000b\u0004&\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\tYf!\r\t\u0013\u0005\u0015f(!AA\u0002\u0005u\u0011A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$Baa\u0002\u00048!I\u0011QU \u0002\u0002\u0003\u0007\u0011\u0011T\u0001\ti>\u001cFO]5oOR\u00111q\u0001\u000b\u0005\u00037\u001ay\u0004C\u0005\u0002&\n\u000b\t\u00111\u0001\u0002\u001e\u0005qA*\u001a4u!J|'.Z2uS>t\u0007cAAF\tN!A\t^A\u001c)\t\u0019\u0019%A\u0003baBd\u00170\u0006\u0004\u0004N\rM3q\u000b\u000b\u0005\u0007\u001f\u001aI\u0006E\u0004\u0002\f&\u001a\tf!\u0016\u0011\t\u0005=11\u000b\u0003\b\u0003'9%\u0019AA\u000b!\u0011\tyaa\u0016\u0005\u000f\u0005\u001drI1\u0001\u0002\u0016!9!\u0011A$A\u0002\rm\u0003cBA\u0006\u0001\rE3QK\u0001\bk:\f\u0007\u000f\u001d7z+\u0019\u0019\tg!\u001b\u0004nQ!11MB8!\u0015)(qTB3!\u001d\tY\u0001AB4\u0007W\u0002B!a\u0004\u0004j\u00119\u00111\u0003%C\u0002\u0005U\u0001\u0003BA\b\u0007[\"q!a\nI\u0005\u0004\t)\u0002C\u0005\u0004r!\u000b\t\u00111\u0001\u0004t\u0005\u0019\u0001\u0010\n\u0019\u0011\u000f\u0005-\u0015fa\u001a\u0004l\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u00111\u0011\u0010\t\u0005\u0007\u0013\u0019Y(\u0003\u0003\u0004~\r-!AB(cU\u0016\u001cGOA\bSS\u001eDG\u000f\u0015:pU\u0016\u001cG/[8o+\u0019\u0019\u0019ia#\u0004\u0010N)!\n\u001e=\u0002tV\u00111q\u0011\t\b\u0003\u0017\u00011\u0011RBG!\u0011\tyaa#\u0005\u0011\u0005M!\n\"b\u0001\u0003+\u0001B!a\u0004\u0004\u0010\u0012A\u0011q\u0005&\u0005\u0006\u0004\t)\u0002\u0006\u0003\u0004\u0014\u000eU\u0005cBAF\u0015\u000e%5Q\u0012\u0005\b\u0005\u0003i\u0005\u0019ABD+\t\u0019i\tK\u0006O\u0005;\u0011\u0019ca'\u0003*\t-\u0012EABO\u0003\u0005*6/\u001a\u0011a\u000b&$\b.\u001a:/i>|\u0005\u000f^5p]::W\r\u001e1!S:\u001cH/Z1e+\u0011\u0019\tk!+\u0015\t\tU21\u0015\u0005\b\u0005{y\u0005\u0019ABS!\u001d)(\u0011IBG\u0007O\u0003B!a\u0004\u0004*\u00129!\u0011J(C\u0002\u0005UQ\u0003BBW\u0007c#Baa,\u00046B!\u0011qBBY\t\u001d\u0011y\b\u0015b\u0001\u0007g\u000bBa!$\u0002\u001e!A!1\f)\u0005\u0002\u0004\u00199\fE\u0003v\u0003O\u001ay\u000b\u0006\u0003\u0002\\\rm\u0006b\u0002B\u001f#\u0002\u00071Q\u0018\t\bk\n\u00053QRA.)\u0011\tYf!1\t\u000f\t\u0015$\u000b1\u0001\u0004>V11QYBf\u0007#$Baa2\u0004TB9\u00111\u0002\u0001\u0004J\u000e=\u0007\u0003BA\b\u0007\u0017$qA!\u0016T\u0005\u0004\u0019i-\u0005\u0003\u0004\n\u0006u\u0001\u0003BA\b\u0007#$qAa T\u0005\u0004\t)\u0002C\u0004\u0003>M\u0003\ra!6\u0011\u000fU\u0014\te!$\u0004HV!1\u0011\\Bp)\u0011\u0019Yn!9\u0011\u000f\u0005-\u0001a!#\u0004^B!\u0011qBBp\t\u001d\u0011y\b\u0016b\u0001\u0003+AqA!\u0010U\u0001\u0004\u0019\u0019\u000fE\u0004v\u0005\u0003\u001aii!8\u0016\t\r\u001d8q\u001e\u000b\u0005\u0007S\u001c\t\u0010E\u0003v\u0005?\u001bY\u000fE\u0004\u0002\f\u0001\u0019io!$\u0011\t\u0005=1q\u001e\u0003\b\u0005+*&\u0019AA\u000b\u0011\u001d\u0011)'\u0016a\u0001\u0007{C3\"\u0016B\u000f\u0005G\u0011iK!\u000b\u0003,U!1q_B\u0000)\u0011\u0019I\u0010\"\u0001\u0011\u000bU\u0014yja?\u0011\u000f\u0005-\u0001a!@\u0004\u000eB!\u0011qBB\u0000\t\u001d\u0011)F\u0016b\u0001\u0003+AqA!\u001aW\u0001\u0004\u0019i,\u0006\u0002\u0005\u0006A)APa2\u0004\u000eV\u0011A\u0011\u0002\t\u0006k\n}5QR\u000b\u0007\t\u001b!\u0019\u0002b\u0006\u0015\t\u0011=A\u0011\u0004\t\b\u0003\u0017SE\u0011\u0003C\u000b!\u0011\ty\u0001b\u0005\u0005\u000f\u0005M\u0011L1\u0001\u0002\u0016A!\u0011q\u0002C\f\t\u001d\t9#\u0017b\u0001\u0003+A\u0011B!\u0001Z!\u0003\u0005\r\u0001b\u0007\u0011\u000f\u0005-\u0001\u0001\"\u0005\u0005\u0016U1Aq\u0004C\u0012\tK)\"\u0001\"\t+\t\r\u001d%Q\u001e\u0003\b\u0003'Q&\u0019AA\u000b\t\u001d\t9C\u0017b\u0001\u0003+!B!!\b\u0005*!I\u0011QU/\u0002\u0002\u0003\u0007\u0011\u0011\u0014\u000b\u0005\u00037\"i\u0003C\u0005\u0002&~\u000b\t\u00111\u0001\u0002\u001eQ!1q\u0001C\u0019\u0011%\t)\u000bYA\u0001\u0002\u0004\tI\n\u0006\u0003\u0002\\\u0011U\u0002\"CASG\u0006\u0005\t\u0019AA\u000fQ-Q%Q\u0004B\u0012\ts\u0011ICa\u000b\"\u0005\u0011m\u0012AP#ji\",'\u000fI5tA9|w\u000f\t:jO\"$XFY5bg\u0016$G\u0006I2bY2\u001c\b\u0005^8!AJLw\r\u001b;aAMDw.\u001e7eA\t,\u0007E]3n_Z,G-A\bSS\u001eDG\u000f\u0015:pU\u0016\u001cG/[8o!\r\tY)Z\n\u0005KR\f9\u0004\u0006\u0002\u0005@U1Aq\tC'\t#\"B\u0001\"\u0013\u0005TA9\u00111\u0012&\u0005L\u0011=\u0003\u0003BA\b\t\u001b\"q!a\u0005i\u0005\u0004\t)\u0002\u0005\u0003\u0002\u0010\u0011ECaBA\u0014Q\n\u0007\u0011Q\u0003\u0005\b\u0005\u0003A\u0007\u0019\u0001C+!\u001d\tY\u0001\u0001C&\t\u001f*b\u0001\"\u0017\u0005b\u0011\u0015D\u0003\u0002C.\tO\u0002R!\u001eBP\t;\u0002r!a\u0003\u0001\t?\"\u0019\u0007\u0005\u0003\u0002\u0010\u0011\u0005DaBA\nS\n\u0007\u0011Q\u0003\t\u0005\u0003\u001f!)\u0007B\u0004\u0002(%\u0014\r!!\u0006\t\u0013\rE\u0014.!AA\u0002\u0011%\u0004cBAF\u0015\u0012}C1M\u000b\u0003\t[\u0002r!a\fK\u0003\u001b\t\u0019\u0003K\u0006\u0004\u0005;\u0011\u0019\u0003\"\u001d\u0003*\t-\u0012E\u0001C:\u0003i*\u0015\u000e\u001e5fe\u0002J7\u000f\t8po\u0002\u0012\u0018n\u001a5u[\tL\u0017m]3eY\u0001*8/\u001a\u0011nKRDw\u000eZ:!I&\u0014Xm\u0019;ms\u0002zg\u000eI#ji\",'/\u0001\u0003g_2$W\u0003\u0002C=\t{\"b\u0001b\u001f\u0005\u0002\u0012\u001d\u0005\u0003BA\b\t{\"q\u0001b \u0005\u0005\u0004\t)BA\u0001D\u0011\u001d!\u0019\t\u0002a\u0001\t\u000b\u000b!AZ1\u0011\u000fU\u0014\t%!\u0004\u0005|!9A\u0011\u0012\u0003A\u0002\u0011-\u0015A\u00014c!\u001d)(\u0011IA\u0012\tw\nAa]<baV\u0011A\u0011\u0013\t\b\u0003\u0017\u0001\u00111EA\u0007\u0003%Qw.\u001b8SS\u001eDG/\u0006\u0005\u0005\u0018\u0012uE\u0011\u0017CR)\u0011!I\n\"*\u0011\u000f\u0005-\u0001\u0001b'\u0005\"B!\u0011q\u0002CO\t\u001d\u0011)F\u0002b\u0001\t?\u000bB!!\u0004\u0002\u001eA!\u0011q\u0002CR\t\u001d!yH\u0002b\u0001\u0003+Aq\u0001b*\u0007\u0001\b!I+\u0001\u0002fmB9Q\u000fb+\u00050\u0012e\u0015b\u0001CW_\n\u0001B\u0005\\3tg\u0012\u001aw\u000e\\8oI1,7o\u001d\t\u0005\u0003\u001f!\t\fB\u0004\u0003\u0000\u0019\u0011\r\u0001b-\u0012\t\u0005\r\u0012QD\u0001\tU>Lg\u000eT3giVAA\u0011\u0018Cf\t\u0007$y\f\u0006\u0003\u0005<\u0012\u0015\u0007cBA\u0006\u0001\u0011uF\u0011\u0019\t\u0005\u0003\u001f!y\fB\u0004\u0005\u0000\u001d\u0011\r!!\u0006\u0011\t\u0005=A1\u0019\u0003\b\u0005\u007f:!\u0019\u0001CZ\u0011\u001d!9k\u0002a\u0002\t\u000f\u0004r!\u001eCV\t\u0013$Y\f\u0005\u0003\u0002\u0010\u0011-Ga\u0002B+\u000f\t\u0007AqT\u000b\u0005\t\u001f$9\u000e\u0006\u0003\u00036\u0011E\u0007b\u0002B\u001f\u0011\u0001\u0007A1\u001b\t\bk\n\u0005\u00131\u0005Ck!\u0011\ty\u0001b6\u0005\u000f\t%\u0003B1\u0001\u0002\u0016U!A1\u001cCp)\u0011!i\u000e\"9\u0011\t\u0005=Aq\u001c\u0003\b\u0005\u007fJ!\u0019\u0001CZ\u0011!\u0011Y&\u0003CA\u0002\u0011\r\b#B;\u0002h\u0011u\u0017AB8s\u000b2\u001cX-\u0006\u0004\u0005j\u0012=H1\u001f\u000b\u0005\tW$)\u0010E\u0004\u0002\f\u0001!i\u000f\"=\u0011\t\u0005=Aq\u001e\u0003\b\u0005+R!\u0019\u0001CP!\u0011\ty\u0001b=\u0005\u000f\t}$B1\u0001\u00054\"A!1\f\u0006\u0005\u0002\u0004!9\u0010E\u0003v\u0003O\"Y/\u0001\u0005d_:$\u0018-\u001b8t+\u0011!i0\"\u0002\u0015\t\u0005mCq \u0005\b\u000b\u0003Y\u0001\u0019AC\u0002\u0003\u0011)G.Z7\u0011\t\u0005=QQ\u0001\u0003\b\u0005\u007fZ!\u0019\u0001CZ)\u0011\tY&\"\u0003\t\u000f\tuB\u00021\u0001\u0006\fA9QO!\u0011\u0002$\u0005mC\u0003BA.\u000b\u001fAqA!\u001a\u000e\u0001\u0004)Y!\u0006\u0004\u0006\u0014\u0015eQQ\u0004\u000b\u0005\u000b+)y\u0002E\u0004\u0002\f\u0001)9\"b\u0007\u0011\t\u0005=Q\u0011\u0004\u0003\b\u0005+r!\u0019\u0001CP!\u0011\ty!\"\b\u0005\u000f\t}dB1\u0001\u0002\u0016!9!Q\b\bA\u0002\u0015\u0005\u0002cB;\u0003B\u0005\rRQC\u0001\bM2\fG\u000f^3o+\u0019)9#\"\f\u00062Q!Q\u0011FC\u001a!\u001d\tY\u0001AC\u0016\u000b_\u0001B!a\u0004\u0006.\u00119!QK\bC\u0002\u0011}\u0005\u0003BA\b\u000bc!qAa \u0010\u0005\u0004\t)\u0002C\u0004\u0005(>\u0001\u001d!\"\u000e\u0011\u000fU$Y+a\t\u0006*U!Q\u0011HC )\u0011)Y$\"\u0011\u0011\u000f\u0005-\u0001!!\u0004\u0006>A!\u0011qBC \t\u001d\u0011y\b\u0005b\u0001\u0003+AqA!\u0010\u0011\u0001\u0004)\u0019\u0005E\u0004v\u0005\u0003\n\u0019#\"\u0010\u0002\u0019\u0019LG\u000e^3s\u001fJ,En]3\u0016\t\u0015%Sq\n\u000b\u0007\u000b\u0017*\t&b\u0015\u0011\u000f\u0005-\u0001!\"\u0014\u0002$A!\u0011qBC(\t\u001d\u0011)&\u0005b\u0001\t?CqA!\u001a\u0012\u0001\u0004)Y\u0001\u0003\u0005\u0006VE!\t\u0019AC,\u0003\u0011QXM]8\u0011\u000bU\f9'\"\u0014\u0016\u0005\u0015m\u0003CBC/\u000bG\n\u0019#\u0004\u0002\u0006`)!Q\u0011MB\u0013\u0003%IW.\\;uC\ndW-\u0003\u0003\u0003J\u0016}SCAC4!\u0015)(qTA\u0012\u0003\u0015!x\u000e\u0016:z)\u0011)i'b\u001d\u0011\r\u0005-QqNA\u0012\u0013\r)\t(\u001c\u0002\u0004)JL\bb\u0002CT)\u0001\u000fQQ\u000f\t\bk\u0012-\u0016QBC<!\raX\u0011P\u0005\u0005\u000bw\n\u0019AA\u0005UQJ|w/\u00192mK\u00061\u0011n\u001d'fMR,\"!a\u0017\u0002\u000f%\u001c(+[4ii&*\u0001!\"\"\u0006\n&\u0019QqQ7\u0003\t1+g\r^\u0005\u0004\u000b\u0017k'!\u0002*jO\"$\b")
public abstract class Either<A, B>
implements Product,
Serializable {
    public static Either MergeableEither(Either x) {
        return x;
    }

    public static <A, B> Either<A, B> cond(boolean test, Function0<B> right, Function0<A> left) {
        if (test) {
            return new Right(right.apply());
        }
        return new Left(left.apply());
    }

    @Override
    public Iterator<Object> productIterator() {
        return Product.productIterator$(this);
    }

    @Override
    public String productPrefix() {
        return Product.productPrefix$(this);
    }

    @Override
    public String productElementName(int n) {
        return Product.productElementName$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public LeftProjection<A, B> left() {
        return new LeftProjection(this);
    }

    public RightProjection<A, B> right() {
        return new RightProjection(this);
    }

    public <C> C fold(Function1<A, C> fa, Function1<B, C> fb) {
        if (this instanceof Right) {
            Object b = ((Right)this).value();
            return fb.apply(b);
        }
        if (this instanceof Left) {
            Object a = ((Left)this).value();
            return fa.apply(a);
        }
        throw new MatchError(this);
    }

    public Either<B, A> swap() {
        if (this instanceof Left) {
            Object a = ((Left)this).value();
            return new Right(a);
        }
        if (this instanceof Right) {
            Object b = ((Right)this).value();
            return new Left(b);
        }
        throw new MatchError(this);
    }

    public <A1, B1, C> Either<A1, C> joinRight($less$colon$less<B1, Either<A1, C>> ev) {
        if (this instanceof Right) {
            Object b = ((Right)this).value();
            return ev.apply((B1)b);
        }
        return this;
    }

    public <A1, B1, C> Either<C, B1> joinLeft($less$colon$less<A1, Either<C, B1>> ev) {
        if (this instanceof Left) {
            Object a = ((Left)this).value();
            return ev.apply((A1)a);
        }
        return this;
    }

    public <U> void foreach(Function1<B, U> f) {
        if (this instanceof Right) {
            Object b = ((Right)this).value();
            f.apply(b);
            return;
        }
    }

    public <B1> B1 getOrElse(Function0<B1> or) {
        if (this instanceof Right) {
            return (B1)((Right)this).value();
        }
        return or.apply();
    }

    public <A1, B1> Either<A1, B1> orElse(Function0<Either<A1, B1>> or) {
        if (this instanceof Right) {
            return this;
        }
        return or.apply();
    }

    public final <B1> boolean contains(B1 elem) {
        if (this instanceof Right) {
            return BoxesRunTime.equals(((Right)this).value(), elem);
        }
        return false;
    }

    public boolean forall(Function1<B, Object> f) {
        if (this instanceof Right) {
            Object b = ((Right)this).value();
            return BoxesRunTime.unboxToBoolean(f.apply(b));
        }
        return true;
    }

    public boolean exists(Function1<B, Object> p) {
        if (this instanceof Right) {
            Object b = ((Right)this).value();
            return BoxesRunTime.unboxToBoolean(p.apply(b));
        }
        return false;
    }

    public <A1, B1> Either<A1, B1> flatMap(Function1<B, Either<A1, B1>> f) {
        if (this instanceof Right) {
            Object b = ((Right)this).value();
            return f.apply(b);
        }
        return this;
    }

    public <A1, B1> Either<A1, B1> flatten($less$colon$less<B, Either<A1, B1>> ev) {
        if (this instanceof Right) {
            Object flatMap_b = ((Right)this).value();
            return (Either)ev.apply(flatMap_b);
        }
        return this;
    }

    public <B1> Either<A, B1> map(Function1<B, B1> f) {
        if (this instanceof Right) {
            Object b = ((Right)this).value();
            return new Right(f.apply(b));
        }
        return this;
    }

    public <A1> Either<A1, B> filterOrElse(Function1<B, Object> p, Function0<A1> zero) {
        Object b;
        if (this instanceof Right && !BoxesRunTime.unboxToBoolean(p.apply(b = ((Right)this).value()))) {
            return new Left(zero.apply());
        }
        return this;
    }

    public Seq<B> toSeq() {
        if (this instanceof Right) {
            Object b = ((Right)this).value();
            return (Seq)Seq$.MODULE$.apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{b}));
        }
        return (Seq)Seq$.MODULE$.empty();
    }

    public Option<B> toOption() {
        if (this instanceof Right) {
            Object b = ((Right)this).value();
            return new Some(b);
        }
        return None$.MODULE$;
    }

    public Try<B> toTry($less$colon$less<A, Throwable> ev) {
        if (this instanceof Right) {
            Object b = ((Right)this).value();
            return new Success(b);
        }
        if (this instanceof Left) {
            Object a = ((Left)this).value();
            return new Failure(ev.apply((A)a));
        }
        throw new MatchError(this);
    }

    public abstract boolean isLeft();

    public abstract boolean isRight();

    public static final class LeftProjection<A, B>
    implements Product,
    Serializable {
        private final Either<A, B> e;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Either<A, B> e() {
            return this.e;
        }

        public A get() {
            Either<A, B> either = this.e();
            if (either instanceof Left) {
                return ((Left)either).value();
            }
            throw new NoSuchElementException("Either.left.get on Right");
        }

        public <U> void foreach(Function1<A, U> f) {
            Either<A, B> either = this.e();
            if (either instanceof Left) {
                Object a = ((Left)either).value();
                f.apply(a);
                return;
            }
        }

        public <A1> A1 getOrElse(Function0<A1> or) {
            Either<A, B> either = this.e();
            if (either instanceof Left) {
                return (A1)((Left)either).value();
            }
            return or.apply();
        }

        public boolean forall(Function1<A, Object> p) {
            Either<A, B> either = this.e();
            if (either instanceof Left) {
                Object a = ((Left)either).value();
                return BoxesRunTime.unboxToBoolean(p.apply(a));
            }
            return true;
        }

        public boolean exists(Function1<A, Object> p) {
            Either<A, B> either = this.e();
            if (either instanceof Left) {
                Object a = ((Left)either).value();
                return BoxesRunTime.unboxToBoolean(p.apply(a));
            }
            return false;
        }

        public <A1, B1> Either<A1, B1> flatMap(Function1<A, Either<A1, B1>> f) {
            Either<A, B> either = this.e();
            if (either instanceof Left) {
                Object a = ((Left)either).value();
                return f.apply(a);
            }
            return this.e();
        }

        public <A1> Either<A1, B> map(Function1<A, A1> f) {
            Either<A, B> either = this.e();
            if (either instanceof Left) {
                Object a = ((Left)either).value();
                return new Left(f.apply(a));
            }
            return this.e();
        }

        public <B1> Option<Either<A, B1>> filter(Function1<A, Object> p) {
            Left left;
            Object a;
            Either<A, B> either = this.e();
            if (either instanceof Left && BoxesRunTime.unboxToBoolean(p.apply(a = (left = (Left)either).value()))) {
                return new Some<Either<A, B1>>(left);
            }
            return None$.MODULE$;
        }

        public <B1> Option<Either<A, B1>> filterToOption(Function1<A, Object> p) {
            Left left;
            Object a;
            Either<A, B> either = this.e();
            if (either instanceof Left && BoxesRunTime.unboxToBoolean(p.apply(a = (left = (Left)either).value()))) {
                return new Some<Either<A, B1>>(left);
            }
            return None$.MODULE$;
        }

        public Seq<A> toSeq() {
            Either<A, B> either = this.e();
            if (either instanceof Left) {
                Object a = ((Left)either).value();
                return (Seq)package$.MODULE$.Seq().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{a}));
            }
            return (Seq)package$.MODULE$.Seq().empty();
        }

        public Option<A> toOption() {
            Either<A, B> either = this.e();
            if (either instanceof Left) {
                Object a = ((Left)either).value();
                return new Some(a);
            }
            return None$.MODULE$;
        }

        public <A, B> LeftProjection<A, B> copy(Either<A, B> e) {
            return new LeftProjection<A, B>(e);
        }

        public <A, B> Either<A, B> copy$default$1() {
            return this.e();
        }

        @Override
        public String productPrefix() {
            return "LeftProjection";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            switch (x$1) {
                case 0: {
                    return this.e();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return new AbstractIterator<T>(this){
                private int c;
                private final int cmax;
                private final Product x$2;

                public boolean hasNext() {
                    return this.c < this.cmax;
                }

                public T next() {
                    Object result = this.x$2.productElement(this.c);
                    ++this.c;
                    return (T)result;
                }
                {
                    this.x$2 = x$2;
                    this.c = 0;
                    this.cmax = x$2.productArity();
                }
            };
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof LeftProjection;
        }

        @Override
        public String productElementName(int x$1) {
            switch (x$1) {
                case 0: {
                    return "e";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return MurmurHash3$.MODULE$.productHash(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        @Override
        public boolean equals(Object x$1) {
            block3: {
                block2: {
                    if (this == x$1) break block2;
                    if (!(x$1 instanceof LeftProjection)) break block3;
                    LeftProjection leftProjection = (LeftProjection)x$1;
                    Either<A, B> either = this.e();
                    Either<A, B> either2 = leftProjection.e();
                    if (either != null ? !either.equals(either2) : either2 != null) break block3;
                }
                return true;
            }
            return false;
        }

        public LeftProjection(Either<A, B> e) {
            this.e = e;
        }
    }

    public static final class MergeableEither<A> {
        private final Either<A, A> scala$util$Either$MergeableEither$$x;

        public Either<A, A> scala$util$Either$MergeableEither$$x() {
            return this.scala$util$Either$MergeableEither$$x;
        }

        public A merge() {
            return Either$MergeableEither$.MODULE$.merge$extension(this.scala$util$Either$MergeableEither$$x());
        }

        public int hashCode() {
            return this.scala$util$Either$MergeableEither$$x().hashCode();
        }

        public boolean equals(Object x$1) {
            return Either$MergeableEither$.MODULE$.equals$extension(this.scala$util$Either$MergeableEither$$x(), x$1);
        }

        public MergeableEither(Either<A, A> x) {
            this.scala$util$Either$MergeableEither$$x = x;
        }
    }

    public static final class RightProjection<A, B>
    implements Product,
    Serializable {
        private final Either<A, B> e;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Either<A, B> e() {
            return this.e;
        }

        public B get() {
            Either<A, B> either = this.e();
            if (either instanceof Right) {
                return ((Right)either).value();
            }
            throw new NoSuchElementException("Either.right.get on Left");
        }

        public <U> void foreach(Function1<B, U> f) {
            Either<A, B> either = this.e();
            if (either instanceof Right) {
                Object b = ((Right)either).value();
                f.apply(b);
                return;
            }
        }

        public <B1> B1 getOrElse(Function0<B1> or) {
            Either<A, B> either = this.e();
            if (either instanceof Right) {
                return (B1)((Right)either).value();
            }
            return or.apply();
        }

        public boolean forall(Function1<B, Object> f) {
            Either<A, B> either = this.e();
            if (either instanceof Right) {
                Object b = ((Right)either).value();
                return BoxesRunTime.unboxToBoolean(f.apply(b));
            }
            return true;
        }

        public boolean exists(Function1<B, Object> p) {
            Either<A, B> either = this.e();
            if (either instanceof Right) {
                Object b = ((Right)either).value();
                return BoxesRunTime.unboxToBoolean(p.apply(b));
            }
            return false;
        }

        public <A1, B1> Either<A1, B1> flatMap(Function1<B, Either<A1, B1>> f) {
            Either<A, B> either = this.e();
            if (either instanceof Right) {
                Object b = ((Right)either).value();
                return f.apply(b);
            }
            return this.e();
        }

        public <B1> Either<A, B1> map(Function1<B, B1> f) {
            Either<A, B> either = this.e();
            if (either instanceof Right) {
                Object b = ((Right)either).value();
                return new Right(f.apply(b));
            }
            return this.e();
        }

        public <A1> Option<Either<A1, B>> filter(Function1<B, Object> p) {
            Object b;
            Either<A, B> either = this.e();
            if (either instanceof Right && BoxesRunTime.unboxToBoolean(p.apply(b = ((Right)either).value()))) {
                return new Some<Either<A1, B>>(new Right(b));
            }
            return None$.MODULE$;
        }

        public <A1> Option<Either<A1, B>> filterToOption(Function1<B, Object> p) {
            Right right;
            Object b;
            Either<A, B> either = this.e();
            if (either instanceof Right && BoxesRunTime.unboxToBoolean(p.apply(b = (right = (Right)either).value()))) {
                return new Some<Either<A1, B>>(right);
            }
            return None$.MODULE$;
        }

        public Seq<B> toSeq() {
            Either<A, B> either = this.e();
            if (either instanceof Right) {
                Object b = ((Right)either).value();
                return (Seq)package$.MODULE$.Seq().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{b}));
            }
            return (Seq)package$.MODULE$.Seq().empty();
        }

        public Option<B> toOption() {
            Either<A, B> either = this.e();
            if (either instanceof Right) {
                Object b = ((Right)either).value();
                return new Some(b);
            }
            return None$.MODULE$;
        }

        public <A, B> RightProjection<A, B> copy(Either<A, B> e) {
            return new RightProjection<A, B>(e);
        }

        public <A, B> Either<A, B> copy$default$1() {
            return this.e();
        }

        @Override
        public String productPrefix() {
            return "RightProjection";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            switch (x$1) {
                case 0: {
                    return this.e();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return new /* invalid duplicate definition of identical inner class */;
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof RightProjection;
        }

        @Override
        public String productElementName(int x$1) {
            switch (x$1) {
                case 0: {
                    return "e";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return MurmurHash3$.MODULE$.productHash(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        @Override
        public boolean equals(Object x$1) {
            block3: {
                block2: {
                    if (this == x$1) break block2;
                    if (!(x$1 instanceof RightProjection)) break block3;
                    RightProjection rightProjection = (RightProjection)x$1;
                    Either<A, B> either = this.e();
                    Either<A, B> either2 = rightProjection.e();
                    if (either != null ? !either.equals(either2) : either2 != null) break block3;
                }
                return true;
            }
            return false;
        }

        public RightProjection(Either<A, B> e) {
            this.e = e;
        }
    }
}

