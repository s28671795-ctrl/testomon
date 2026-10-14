/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.japi.function.Function;
import akka.stream.ActorAttributes$;
import akka.stream.Attributes;
import akka.stream.StreamSubscriptionTimeoutTerminationMode;
import akka.stream.Supervision;
import java.io.Serializable;
import java.time.Duration;
import scala.Function1;
import scala.Product;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0015\u0015r\u0001CAI\u0003'C\t!!(\u0007\u0011\u0005\u0005\u00161\u0013E\u0001\u0003GCq!!-\u0002\t\u0003\t\u0019L\u0002\u0004\u00026\u0006\u0011\u0015q\u0017\u0005\u000b\u0003K\u001c!Q3A\u0005\u0002\u0005\u001d\bBCA}\u0007\tE\t\u0015!\u0003\u0002j\"9\u0011\u0011W\u0002\u0005\u0002\u0005m\b\"\u0003B\u0002\u0007\u0005\u0005I\u0011\u0001B\u0003\u0011%\u0011IaAI\u0001\n\u0003\u0011Y\u0001C\u0005\u0003\"\r\t\t\u0011\"\u0011\u0003$!I!1G\u0002\u0002\u0002\u0013\u0005!Q\u0007\u0005\n\u0005{\u0019\u0011\u0011!C\u0001\u0005\u007fA\u0011Ba\u0013\u0004\u0003\u0003%\tE!\u0014\t\u0013\tm3!!A\u0005\u0002\tu\u0003\"\u0003B4\u0007\u0005\u0005I\u0011\tB5\u0011%\u0011igAA\u0001\n\u0003\u0012y\u0007C\u0005\u0003r\r\t\t\u0011\"\u0011\u0003t!I!QO\u0002\u0002\u0002\u0013\u0005#qO\u0004\n\u0005w\n\u0011\u0011!E\u0001\u0005{2\u0011\"!.\u0002\u0003\u0003E\tAa \t\u000f\u0005E6\u0003\"\u0001\u0003\u0018\"I!\u0011O\n\u0002\u0002\u0013\u0015#1\u000f\u0005\n\u00053\u001b\u0012\u0011!CA\u00057C\u0011Ba(\u0014\u0003\u0003%\tI!)\t\u0013\t56#!A\u0005\n\t=fA\u0002B\\\u0003\t\u0013I\f\u0003\u0006\u0003<f\u0011)\u001a!C\u0001\u0005{C!B!4\u001a\u0005#\u0005\u000b\u0011\u0002B`\u0011\u001d\t\t,\u0007C\u0001\u0005\u001fD\u0011Ba\u0001\u001a\u0003\u0003%\tA!6\t\u0013\t%\u0011$%A\u0005\u0002\te\u0007\"\u0003B\u00113\u0005\u0005I\u0011\tB\u0012\u0011%\u0011\u0019$GA\u0001\n\u0003\u0011)\u0004C\u0005\u0003>e\t\t\u0011\"\u0001\u0003^\"I!1J\r\u0002\u0002\u0013\u0005#Q\n\u0005\n\u00057J\u0012\u0011!C\u0001\u0005CD\u0011Ba\u001a\u001a\u0003\u0003%\tE!:\t\u0013\t5\u0014$!A\u0005B\t=\u0004\"\u0003B93\u0005\u0005I\u0011\tB:\u0011%\u0011)(GA\u0001\n\u0003\u0012IoB\u0005\u0003n\u0006\t\t\u0011#\u0001\u0003p\u001aI!qW\u0001\u0002\u0002#\u0005!\u0011\u001f\u0005\b\u0003cKC\u0011\u0001B{\u0011%\u0011\t(KA\u0001\n\u000b\u0012\u0019\bC\u0005\u0003\u001a&\n\t\u0011\"!\u0003x\"I!qT\u0015\u0002\u0002\u0013\u0005%1 \u0005\n\u0005[K\u0013\u0011!C\u0005\u0005_C\u0011b!\u0001\u0002\u0005\u0004%\taa\u0001\t\u0011\r\u0015\u0011\u0001)A\u0005\u0003{Dq!!:\u0002\t\u0003\u00199\u0001C\u0004\u0004\u0012\u0005!\taa\u0005\t\u000f\r]\u0011\u0001\"\u0001\u0004\u001a!91\u0011H\u0001\u0005\u0002\rm\u0002bBB\u001d\u0003\u0011\u00051Q\f\u0005\b\u0007C\nA\u0011AB2\u0011%\u0019Y'AI\u0001\n\u0003\u0019i\u0007C\u0005\u0004r\u0005\t\n\u0011\"\u0001\u0004n!I11O\u0001\u0012\u0002\u0013\u00051Q\u000e\u0004\u0007\u0007k\n!ia\u001e\t\u0015\re$H!f\u0001\n\u0003\u0019Y\b\u0003\u0006\u0004~i\u0012\t\u0012)A\u0005\u0005?Bq!!-;\t\u0003\u0019y\bC\u0005\u0003\u0004i\n\t\u0011\"\u0001\u0004\u0006\"I!\u0011\u0002\u001e\u0012\u0002\u0013\u00051\u0011\u0012\u0005\n\u0005CQ\u0014\u0011!C!\u0005GA\u0011Ba\r;\u0003\u0003%\tA!\u000e\t\u0013\tu\"(!A\u0005\u0002\r5\u0005\"\u0003B&u\u0005\u0005I\u0011\tB'\u0011%\u0011YFOA\u0001\n\u0003\u0019\t\nC\u0005\u0003hi\n\t\u0011\"\u0011\u0004\u0016\"I!Q\u000e\u001e\u0002\u0002\u0013\u0005#q\u000e\u0005\n\u0005cR\u0014\u0011!C!\u0005gB\u0011B!\u001e;\u0003\u0003%\te!'\b\u0013\ru\u0015!!A\t\u0002\r}e!CB;\u0003\u0005\u0005\t\u0012ABQ\u0011\u001d\t\tL\u0013C\u0001\u0007KC\u0011B!\u001dK\u0003\u0003%)Ea\u001d\t\u0013\te%*!A\u0005\u0002\u000e\u001d\u0006\"\u0003BP\u0015\u0006\u0005I\u0011QBV\u0011%\u0011iKSA\u0001\n\u0013\u0011y\u000bC\u0004\u00042\u0006!\taa-\u0007\r\r]\u0016AQB]\u0011)\u0019Y,\u0015BK\u0002\u0013\u00051Q\u0018\u0005\u000b\u0007\u001f\f&\u0011#Q\u0001\n\r}\u0006BCBi#\nU\r\u0011\"\u0001\u0004T\"Q11\\)\u0003\u0012\u0003\u0006Ia!6\t\u000f\u0005E\u0016\u000b\"\u0001\u0004^\"I!1A)\u0002\u0002\u0013\u00051Q\u001d\u0005\n\u0005\u0013\t\u0016\u0013!C\u0001\u0007WD\u0011ba<R#\u0003%\ta!=\t\u0013\t\u0005\u0012+!A\u0005B\t\r\u0002\"\u0003B\u001a#\u0006\u0005I\u0011\u0001B\u001b\u0011%\u0011i$UA\u0001\n\u0003\u0019)\u0010C\u0005\u0003LE\u000b\t\u0011\"\u0011\u0003N!I!1L)\u0002\u0002\u0013\u00051\u0011 \u0005\n\u0005O\n\u0016\u0011!C!\u0007{D\u0011B!\u001cR\u0003\u0003%\tEa\u001c\t\u0013\tE\u0014+!A\u0005B\tM\u0004\"\u0003B;#\u0006\u0005I\u0011\tC\u0001\u000f%!)!AA\u0001\u0012\u0003!9AB\u0005\u00048\u0006\t\t\u0011#\u0001\u0005\n!9\u0011\u0011\u00173\u0005\u0002\u0011E\u0001\"\u0003B9I\u0006\u0005IQ\tB:\u0011%\u0011I\nZA\u0001\n\u0003#\u0019\u0002C\u0005\u0003 \u0012\f\t\u0011\"!\u0005\u001a!I!Q\u00163\u0002\u0002\u0013%!q\u0016\u0005\b\tK\tA\u0011\u0001C\u0014\u0011\u001d!)#\u0001C\u0001\t[1a\u0001b\u0010\u0002\u0005\u0012\u0005\u0003B\u0003C\"Y\nU\r\u0011\"\u0001\u00036!QAQ\t7\u0003\u0012\u0003\u0006IAa\u000e\t\u000f\u0005EF\u000e\"\u0001\u0005H!I!1\u00017\u0002\u0002\u0013\u0005AQ\n\u0005\n\u0005\u0013a\u0017\u0013!C\u0001\t#B\u0011B!\tm\u0003\u0003%\tEa\t\t\u0013\tMB.!A\u0005\u0002\tU\u0002\"\u0003B\u001fY\u0006\u0005I\u0011\u0001C+\u0011%\u0011Y\u0005\\A\u0001\n\u0003\u0012i\u0005C\u0005\u0003\\1\f\t\u0011\"\u0001\u0005Z!I!q\r7\u0002\u0002\u0013\u0005CQ\f\u0005\n\u0005[b\u0017\u0011!C!\u0005_B\u0011B!\u001dm\u0003\u0003%\tEa\u001d\t\u0013\tUD.!A\u0005B\u0011\u0005t!\u0003C3\u0003\u0005\u0005\t\u0012\u0001C4\r%!y$AA\u0001\u0012\u0003!I\u0007C\u0004\u00022r$\t\u0001\"\u001c\t\u0013\tED0!A\u0005F\tM\u0004\"\u0003BMy\u0006\u0005I\u0011\u0011C8\u0011%\u0011y\n`A\u0001\n\u0003#\u0019\bC\u0005\u0003.r\f\t\u0011\"\u0003\u00030\"9A\u0011P\u0001\u0005\u0002\u0011mdA\u0002C@\u0003\t#\t\tC\u0006\u0004z\u0005\u001d!Q3A\u0005\u0002\rm\u0004bCB?\u0003\u000f\u0011\t\u0012)A\u0005\u0005?B\u0001\"!-\u0002\b\u0011\u0005A1\u0011\u0005\u000b\u0005\u0007\t9!!A\u0005\u0002\u0011%\u0005B\u0003B\u0005\u0003\u000f\t\n\u0011\"\u0001\u0004\n\"Q!\u0011EA\u0004\u0003\u0003%\tEa\t\t\u0015\tM\u0012qAA\u0001\n\u0003\u0011)\u0004\u0003\u0006\u0003>\u0005\u001d\u0011\u0011!C\u0001\t\u001bC!Ba\u0013\u0002\b\u0005\u0005I\u0011\tB'\u0011)\u0011Y&a\u0002\u0002\u0002\u0013\u0005A\u0011\u0013\u0005\u000b\u0005O\n9!!A\u0005B\u0011U\u0005B\u0003B7\u0003\u000f\t\t\u0011\"\u0011\u0003p!Q!\u0011OA\u0004\u0003\u0003%\tEa\u001d\t\u0015\tU\u0014qAA\u0001\n\u0003\"IjB\u0005\u0005\u001e\u0006\t\t\u0011#\u0001\u0005 \u001aIAqP\u0001\u0002\u0002#\u0005A\u0011\u0015\u0005\t\u0003c\u000b9\u0003\"\u0001\u0005&\"Q!\u0011OA\u0014\u0003\u0003%)Ea\u001d\t\u0015\te\u0015qEA\u0001\n\u0003#9\u000b\u0003\u0006\u0003 \u0006\u001d\u0012\u0011!CA\tWC!B!,\u0002(\u0005\u0005I\u0011\u0002BX\u0011\u001d!y+\u0001C\u0001\tc3a\u0001\".\u0002\u0005\u0012]\u0006b\u0003C]\u0003k\u0011)\u001a!C\u0001\u0005kA1\u0002b/\u00026\tE\t\u0015!\u0003\u00038!A\u0011\u0011WA\u001b\t\u0003!i\f\u0003\u0006\u0003\u0004\u0005U\u0012\u0011!C\u0001\t\u0007D!B!\u0003\u00026E\u0005I\u0011\u0001C)\u0011)\u0011\t#!\u000e\u0002\u0002\u0013\u0005#1\u0005\u0005\u000b\u0005g\t)$!A\u0005\u0002\tU\u0002B\u0003B\u001f\u0003k\t\t\u0011\"\u0001\u0005H\"Q!1JA\u001b\u0003\u0003%\tE!\u0014\t\u0015\tm\u0013QGA\u0001\n\u0003!Y\r\u0003\u0006\u0003h\u0005U\u0012\u0011!C!\t\u001fD!B!\u001c\u00026\u0005\u0005I\u0011\tB8\u0011)\u0011\t(!\u000e\u0002\u0002\u0013\u0005#1\u000f\u0005\u000b\u0005k\n)$!A\u0005B\u0011Mw!\u0003Cl\u0003\u0005\u0005\t\u0012\u0001Cm\r%!),AA\u0001\u0012\u0003!Y\u000e\u0003\u0005\u00022\u0006UC\u0011\u0001Cp\u0011)\u0011\t(!\u0016\u0002\u0002\u0013\u0015#1\u000f\u0005\u000b\u00053\u000b)&!A\u0005\u0002\u0012\u0005\bB\u0003BP\u0003+\n\t\u0011\"!\u0005f\"Q!QVA+\u0003\u0003%IAa,\t\u000f\u0011%\u0018\u0001\"\u0001\u0005l\u001a1Aq^\u0001C\tcD1\u0002b\u0011\u0002d\tU\r\u0011\"\u0001\u00036!YAQIA2\u0005#\u0005\u000b\u0011\u0002B\u001c\u0011!\t\t,a\u0019\u0005\u0002\u0011M\bB\u0003B\u0002\u0003G\n\t\u0011\"\u0001\u0005z\"Q!\u0011BA2#\u0003%\t\u0001\"\u0015\t\u0015\t\u0005\u00121MA\u0001\n\u0003\u0012\u0019\u0003\u0003\u0006\u00034\u0005\r\u0014\u0011!C\u0001\u0005kA!B!\u0010\u0002d\u0005\u0005I\u0011\u0001C\u007f\u0011)\u0011Y%a\u0019\u0002\u0002\u0013\u0005#Q\n\u0005\u000b\u00057\n\u0019'!A\u0005\u0002\u0015\u0005\u0001B\u0003B4\u0003G\n\t\u0011\"\u0011\u0006\u0006!Q!QNA2\u0003\u0003%\tEa\u001c\t\u0015\tE\u00141MA\u0001\n\u0003\u0012\u0019\b\u0003\u0006\u0003v\u0005\r\u0014\u0011!C!\u000b\u00139\u0011\"\"\u0004\u0002\u0003\u0003E\t!b\u0004\u0007\u0013\u0011=\u0018!!A\t\u0002\u0015E\u0001\u0002CAY\u0003\u0007#\t!\"\u0006\t\u0015\tE\u00141QA\u0001\n\u000b\u0012\u0019\b\u0003\u0006\u0003\u001a\u0006\r\u0015\u0011!CA\u000b/A!Ba(\u0002\u0004\u0006\u0005I\u0011QC\u000e\u0011)\u0011i+a!\u0002\u0002\u0013%!q\u0016\u0005\b\u000b?\tA\u0011AC\u0011\u0003=\t5\r^8s\u0003R$(/\u001b2vi\u0016\u001c(\u0002BAK\u0003/\u000baa\u001d;sK\u0006l'BAAM\u0003\u0011\t7n[1\u0004\u0001A\u0019\u0011qT\u0001\u000e\u0005\u0005M%aD!di>\u0014\u0018\t\u001e;sS\n,H/Z:\u0014\u0007\u0005\t)\u000b\u0005\u0003\u0002(\u00065VBAAU\u0015\t\tY+A\u0003tG\u0006d\u0017-\u0003\u0003\u00020\u0006%&AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0003\u0003;\u0013!\u0002R5ta\u0006$8\r[3s'%\u0019\u0011QUA]\u0003\u000f\fi\r\u0005\u0003\u0002<\u0006\u0005g\u0002BAP\u0003{KA!a0\u0002\u0014\u0006Q\u0011\t\u001e;sS\n,H/Z:\n\t\u0005\r\u0017Q\u0019\u0002\u0013\u001b\u0006tG-\u0019;pef\fE\u000f\u001e:jEV$XM\u0003\u0003\u0002@\u0006M\u0005\u0003BAT\u0003\u0013LA!a3\u0002*\n9\u0001K]8ek\u000e$\b\u0003BAh\u0003?tA!!5\u0002\\:!\u00111[Am\u001b\t\t)N\u0003\u0003\u0002X\u0006m\u0015A\u0002\u001fs_>$h(\u0003\u0002\u0002,&!\u0011Q\\AU\u0003\u001d\u0001\u0018mY6bO\u0016LA!!9\u0002d\na1+\u001a:jC2L'0\u00192mK*!\u0011Q\\AU\u0003)!\u0017n\u001d9bi\u000eDWM]\u000b\u0003\u0003S\u0004B!a;\u0002t:!\u0011Q^Ax!\u0011\t\u0019.!+\n\t\u0005E\u0018\u0011V\u0001\u0007!J,G-\u001a4\n\t\u0005U\u0018q\u001f\u0002\u0007'R\u0014\u0018N\\4\u000b\t\u0005E\u0018\u0011V\u0001\fI&\u001c\b/\u0019;dQ\u0016\u0014\b\u0005\u0006\u0003\u0002~\n\u0005\u0001cAA\u0000\u00075\t\u0011\u0001C\u0004\u0002f\u001a\u0001\r!!;\u0002\t\r|\u0007/\u001f\u000b\u0005\u0003{\u00149\u0001C\u0005\u0002f\u001e\u0001\n\u00111\u0001\u0002j\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTC\u0001B\u0007U\u0011\tIOa\u0004,\u0005\tE\u0001\u0003\u0002B\n\u0005;i!A!\u0006\u000b\t\t]!\u0011D\u0001\nk:\u001c\u0007.Z2lK\u0012TAAa\u0007\u0002*\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\t}!Q\u0003\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0003&A!!q\u0005B\u0019\u001b\t\u0011IC\u0003\u0003\u0003,\t5\u0012\u0001\u00027b]\u001eT!Aa\f\u0002\t)\fg/Y\u0005\u0005\u0003k\u0014I#\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u00038A!\u0011q\u0015B\u001d\u0013\u0011\u0011Y$!+\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\t\u0005#q\t\t\u0005\u0003O\u0013\u0019%\u0003\u0003\u0003F\u0005%&aA!os\"I!\u0011J\u0006\u0002\u0002\u0003\u0007!qG\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\t=\u0003C\u0002B)\u0005/\u0012\t%\u0004\u0002\u0003T)!!QKAU\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u00053\u0012\u0019F\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003\u0002B0\u0005K\u0002B!a*\u0003b%!!1MAU\u0005\u001d\u0011un\u001c7fC:D\u0011B!\u0013\u000e\u0003\u0003\u0005\rA!\u0011\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0005K\u0011Y\u0007C\u0005\u0003J9\t\t\u00111\u0001\u00038\u0005A\u0001.Y:i\u0007>$W\r\u0006\u0002\u00038\u0005AAo\\*ue&tw\r\u0006\u0002\u0003&\u00051Q-];bYN$BAa\u0018\u0003z!I!\u0011J\t\u0002\u0002\u0003\u0007!\u0011I\u0001\u000b\t&\u001c\b/\u0019;dQ\u0016\u0014\bcAA\u0000'M)1C!!\u0003\u000eBA!1\u0011BE\u0003S\fi0\u0004\u0002\u0003\u0006*!!qQAU\u0003\u001d\u0011XO\u001c;j[\u0016LAAa#\u0003\u0006\n\t\u0012IY:ue\u0006\u001cGOR;oGRLwN\\\u0019\u0011\t\t=%QS\u0007\u0003\u0005#SAAa%\u0003.\u0005\u0011\u0011n\\\u0005\u0005\u0003C\u0014\t\n\u0006\u0002\u0003~\u0005)\u0011\r\u001d9msR!\u0011Q BO\u0011\u001d\t)O\u0006a\u0001\u0003S\fq!\u001e8baBd\u0017\u0010\u0006\u0003\u0003$\n%\u0006CBAT\u0005K\u000bI/\u0003\u0003\u0003(\u0006%&AB(qi&|g\u000eC\u0005\u0003,^\t\t\u00111\u0001\u0002~\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\tE\u0006\u0003\u0002B\u0014\u0005gKAA!.\u0003*\t1qJ\u00196fGR\u00141cU;qKJ4\u0018n]5p]N#(/\u0019;fOf\u001c\u0012\"GAS\u0003s\u000b9-!4\u0002\u000f\u0011,7-\u001b3feV\u0011!q\u0018\t\u0005\u0005\u0003\u00149M\u0004\u0003\u0002 \n\r\u0017\u0002\u0002Bc\u0003'\u000b1bU;qKJ4\u0018n]5p]&!!\u0011\u001aBf\u0005\u001d!UmY5eKJTAA!2\u0002\u0014\u0006AA-Z2jI\u0016\u0014\b\u0005\u0006\u0003\u0003R\nM\u0007cAA\u00003!9!1\u0018\u000fA\u0002\t}F\u0003\u0002Bi\u0005/D\u0011Ba/\u001e!\u0003\u0005\rAa0\u0016\u0005\tm'\u0006\u0002B`\u0005\u001f!BA!\u0011\u0003`\"I!\u0011J\u0011\u0002\u0002\u0003\u0007!q\u0007\u000b\u0005\u0005?\u0012\u0019\u000fC\u0005\u0003J\r\n\t\u00111\u0001\u0003BQ!!Q\u0005Bt\u0011%\u0011I\u0005JA\u0001\u0002\u0004\u00119\u0004\u0006\u0003\u0003`\t-\b\"\u0003B%O\u0005\u0005\t\u0019\u0001B!\u0003M\u0019V\u000f]3sm&\u001c\u0018n\u001c8TiJ\fG/Z4z!\r\ty0K\n\u0006S\tM(Q\u0012\t\t\u0005\u0007\u0013IIa0\u0003RR\u0011!q\u001e\u000b\u0005\u0005#\u0014I\u0010C\u0004\u0003<2\u0002\rAa0\u0015\t\tu(q \t\u0007\u0003O\u0013)Ka0\t\u0013\t-V&!AA\u0002\tE\u0017\u0001D%P\t&\u001c\b/\u0019;dQ\u0016\u0014XCAA\u007f\u00035Iu\nR5ta\u0006$8\r[3sAQ!1\u0011BB\b!\u0011\tyja\u0003\n\t\r5\u00111\u0013\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\bbBAsc\u0001\u0007\u0011\u0011^\u0001\u0014gV\u0004XM\u001d<jg&|gn\u0015;sCR,w-\u001f\u000b\u0005\u0007\u0013\u0019)\u0002C\u0004\u0003<J\u0002\rAa0\u0002/]LG\u000f[*va\u0016\u0014h/[:j_:\u001cFO]1uK\u001eLH\u0003BB\u0005\u00077AqAa/4\u0001\u0004\u0019i\u0002\u0005\u0005\u0004 \r%2QFB\u001a\u001b\t\u0019\tC\u0003\u0003\u0004$\r\u0015\u0012\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\t\r\u001d\u0012qS\u0001\u0005U\u0006\u0004\u0018.\u0003\u0003\u0004,\r\u0005\"\u0001\u0003$v]\u000e$\u0018n\u001c8\u0011\t\u0005=7qF\u0005\u0005\u0007c\t\u0019OA\u0005UQJ|w/\u00192mKB!!\u0011YB\u001b\u0013\u0011\u00199Da3\u0003\u0013\u0011K'/Z2uSZ,\u0017aD2sK\u0006$X\rT8h\u0019\u00164X\r\\:\u0015\u0011\r%1QHB+\u00073Bqaa\u00105\u0001\u0004\u0019\t%A\u0005p]\u0016cW-\\3oiB!11IB(\u001d\u0011\u0019)ea\u0013\u000e\u0005\r\u001d#\u0002BB%\u0003/\u000bQ!\u001a<f]RLAa!\u0014\u0004H\u00059Aj\\4hS:<\u0017\u0002BB)\u0007'\u0012\u0001\u0002T8h\u0019\u00164X\r\u001c\u0006\u0005\u0007\u001b\u001a9\u0005C\u0004\u0004XQ\u0002\ra!\u0011\u0002\u0011=tg)\u001b8jg\"Dqaa\u00175\u0001\u0004\u0019\t%A\u0005p]\u001a\u000b\u0017\u000e\\;sKR!1\u0011BB0\u0011\u001d\u0019y$\u000ea\u0001\u0007\u0003\n\u0011\u0002\\8h\u0019\u00164X\r\\:\u0015\u0011\r%1QMB4\u0007SB\u0011ba\u00107!\u0003\u0005\ra!\u0011\t\u0013\r]c\u0007%AA\u0002\r\u0005\u0003\"CB.mA\u0005\t\u0019AB!\u0003Mawn\u001a'fm\u0016d7\u000f\n3fM\u0006,H\u000e\u001e\u00132+\t\u0019yG\u000b\u0003\u0004B\t=\u0011a\u00057pO2+g/\u001a7tI\u0011,g-Y;mi\u0012\u0012\u0014a\u00057pO2+g/\u001a7tI\u0011,g-Y;mi\u0012\u001a$\u0001\u0004#fEV<Gj\\4hS:<7#\u0003\u001e\u0002&\u0006e\u0016qYAg\u0003\u001d)g.\u00192mK\u0012,\"Aa\u0018\u0002\u0011\u0015t\u0017M\u00197fI\u0002\"Ba!!\u0004\u0004B\u0019\u0011q \u001e\t\u000f\reT\b1\u0001\u0003`Q!1\u0011QBD\u0011%\u0019IH\u0010I\u0001\u0002\u0004\u0011y&\u0006\u0002\u0004\f*\"!q\fB\b)\u0011\u0011\tea$\t\u0013\t%#)!AA\u0002\t]B\u0003\u0002B0\u0007'C\u0011B!\u0013E\u0003\u0003\u0005\rA!\u0011\u0015\t\t\u00152q\u0013\u0005\n\u0005\u0013*\u0015\u0011!a\u0001\u0005o!BAa\u0018\u0004\u001c\"I!\u0011\n%\u0002\u0002\u0003\u0007!\u0011I\u0001\r\t\u0016\u0014Wo\u001a'pO\u001eLgn\u001a\t\u0004\u0003\u007fT5#\u0002&\u0004$\n5\u0005\u0003\u0003BB\u0005\u0013\u0013yf!!\u0015\u0005\r}E\u0003BBA\u0007SCqa!\u001fN\u0001\u0004\u0011y\u0006\u0006\u0003\u0004.\u000e=\u0006CBAT\u0005K\u0013y\u0006C\u0005\u0003,:\u000b\t\u00111\u0001\u0004\u0002\u0006aA-\u001a2vO2{wmZ5oOR!1\u0011BB[\u0011\u001d\u0019I\b\u0015a\u0001\u0005?\u0012\u0011d\u0015;sK\u0006l7+\u001e2tGJL\u0007\u000f^5p]RKW.Z8viNI\u0011+!*\u0002:\u0006\u001d\u0017QZ\u0001\bi&lWm\\;u+\t\u0019y\f\u0005\u0003\u0004B\u000e-WBABb\u0015\u0011\u0019)ma2\u0002\u0011\u0011,(/\u0019;j_:TAa!3\u0002*\u0006Q1m\u001c8dkJ\u0014XM\u001c;\n\t\r571\u0019\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u0003!!\u0018.\\3pkR\u0004\u0013\u0001B7pI\u0016,\"a!6\u0011\t\u0005}5q[\u0005\u0005\u00073\f\u0019J\u0001\u0015TiJ,\u0017-\\*vEN\u001c'/\u001b9uS>tG+[7f_V$H+\u001a:nS:\fG/[8o\u001b>$W-A\u0003n_\u0012,\u0007\u0005\u0006\u0004\u0004`\u000e\u000581\u001d\t\u0004\u0003\u007f\f\u0006bBB^-\u0002\u00071q\u0018\u0005\b\u0007#4\u0006\u0019ABk)\u0019\u0019yna:\u0004j\"I11X,\u0011\u0002\u0003\u00071q\u0018\u0005\n\u0007#<\u0006\u0013!a\u0001\u0007+,\"a!<+\t\r}&qB\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\u0019\u0019P\u000b\u0003\u0004V\n=A\u0003\u0002B!\u0007oD\u0011B!\u0013]\u0003\u0003\u0005\rAa\u000e\u0015\t\t}31 \u0005\n\u0005\u0013r\u0016\u0011!a\u0001\u0005\u0003\"BA!\n\u0004\u0000\"I!\u0011J0\u0002\u0002\u0003\u0007!q\u0007\u000b\u0005\u0005?\"\u0019\u0001C\u0005\u0003J\t\f\t\u00111\u0001\u0003B\u0005I2\u000b\u001e:fC6\u001cVOY:de&\u0004H/[8o)&lWm\\;u!\r\ty\u0010Z\n\u0006I\u0012-!Q\u0012\t\u000b\u0005\u0007#iaa0\u0004V\u000e}\u0017\u0002\u0002C\b\u0005\u000b\u0013\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c83)\t!9\u0001\u0006\u0004\u0004`\u0012UAq\u0003\u0005\b\u0007w;\u0007\u0019AB`\u0011\u001d\u0019\tn\u001aa\u0001\u0007+$B\u0001b\u0007\u0005$A1\u0011q\u0015BS\t;\u0001\u0002\"a*\u0005 \r}6Q[\u0005\u0005\tC\tIK\u0001\u0004UkBdWM\r\u0005\n\u0005WC\u0017\u0011!a\u0001\u0007?\f\u0011d\u001d;sK\u0006l7+\u001e2tGJL\u0007\u000f^5p]RKW.Z8viR11\u0011\u0002C\u0015\tWAqaa/k\u0001\u0004\u0019y\fC\u0004\u0004R*\u0004\ra!6\u0015\r\r%Aq\u0006C\u001f\u0011\u001d\u0019Yl\u001ba\u0001\tc\u0001B\u0001b\r\u0005:5\u0011AQ\u0007\u0006\u0005\to\u0011i#\u0001\u0003uS6,\u0017\u0002\u0002C\u001e\tk\u0011\u0001\u0002R;sCRLwN\u001c\u0005\b\u0007#\\\u0007\u0019ABk\u0005AyU\u000f\u001e9vi\n+(o\u001d;MS6LGoE\u0005m\u0003K\u000bI,a2\u0002N\u0006)A.[7ji\u00061A.[7ji\u0002\"B\u0001\"\u0013\u0005LA\u0019\u0011q 7\t\u000f\u0011\rs\u000e1\u0001\u00038Q!A\u0011\nC(\u0011%!\u0019\u0005\u001dI\u0001\u0002\u0004\u00119$\u0006\u0002\u0005T)\"!q\u0007B\b)\u0011\u0011\t\u0005b\u0016\t\u0013\t%C/!AA\u0002\t]B\u0003\u0002B0\t7B\u0011B!\u0013w\u0003\u0003\u0005\rA!\u0011\u0015\t\t\u0015Bq\f\u0005\n\u0005\u0013:\u0018\u0011!a\u0001\u0005o!BAa\u0018\u0005d!I!\u0011\n>\u0002\u0002\u0003\u0007!\u0011I\u0001\u0011\u001fV$\b/\u001e;CkJ\u001cH\u000fT5nSR\u00042!a@}'\u0015aH1\u000eBG!!\u0011\u0019I!#\u00038\u0011%CC\u0001C4)\u0011!I\u0005\"\u001d\t\u000f\u0011\rs\u00101\u0001\u00038Q!AQ\u000fC<!\u0019\t9K!*\u00038!Q!1VA\u0001\u0003\u0003\u0005\r\u0001\"\u0013\u0002!=,H\u000f];u\u0005V\u00148\u000f\u001e'j[&$H\u0003BB\u0005\t{B\u0001\u0002b\u0011\u0002\u0006\u0001\u0007!q\u0007\u0002\f\rVT(0\u001b8h\u001b>$Wm\u0005\u0006\u0002\b\u0005\u0015\u0016\u0011XAd\u0003\u001b$B\u0001\"\"\u0005\bB!\u0011q`A\u0004\u0011!\u0019I(!\u0004A\u0002\t}C\u0003\u0002CC\t\u0017C!b!\u001f\u0002\u0010A\u0005\t\u0019\u0001B0)\u0011\u0011\t\u0005b$\t\u0015\t%\u0013qCA\u0001\u0002\u0004\u00119\u0004\u0006\u0003\u0003`\u0011M\u0005B\u0003B%\u00037\t\t\u00111\u0001\u0003BQ!!Q\u0005CL\u0011)\u0011I%!\b\u0002\u0002\u0003\u0007!q\u0007\u000b\u0005\u0005?\"Y\n\u0003\u0006\u0003J\u0005\r\u0012\u0011!a\u0001\u0005\u0003\n1BR;{u&tw-T8eKB!\u0011q`A\u0014'\u0019\t9\u0003b)\u0003\u000eBA!1\u0011BE\u0005?\")\t\u0006\u0002\u0005 R!AQ\u0011CU\u0011!\u0019I(!\fA\u0002\t}C\u0003BBW\t[C!Ba+\u00020\u0005\u0005\t\u0019\u0001CC\u0003-1WO\u001f>j]\u001elu\u000eZ3\u0015\t\r%A1\u0017\u0005\t\u0007s\n\u0019\u00041\u0001\u0003`\t\u0011R*\u0019=GSb,GMQ;gM\u0016\u00148+\u001b>f')\t)$!*\u0002:\u0006\u001d\u0017QZ\u0001\u0005g&TX-A\u0003tSj,\u0007\u0005\u0006\u0003\u0005@\u0012\u0005\u0007\u0003BA\u0000\u0003kA\u0001\u0002\"/\u0002<\u0001\u0007!q\u0007\u000b\u0005\t\u007f#)\r\u0003\u0006\u0005:\u0006u\u0002\u0013!a\u0001\u0005o!BA!\u0011\u0005J\"Q!\u0011JA#\u0003\u0003\u0005\rAa\u000e\u0015\t\t}CQ\u001a\u0005\u000b\u0005\u0013\nI%!AA\u0002\t\u0005C\u0003\u0002B\u0013\t#D!B!\u0013\u0002L\u0005\u0005\t\u0019\u0001B\u001c)\u0011\u0011y\u0006\"6\t\u0015\t%\u0013\u0011KA\u0001\u0002\u0004\u0011\t%\u0001\nNCb4\u0015\u000e_3e\u0005V4g-\u001a:TSj,\u0007\u0003BA\u0000\u0003+\u001ab!!\u0016\u0005^\n5\u0005\u0003\u0003BB\u0005\u0013\u00139\u0004b0\u0015\u0005\u0011eG\u0003\u0002C`\tGD\u0001\u0002\"/\u0002\\\u0001\u0007!q\u0007\u000b\u0005\tk\"9\u000f\u0003\u0006\u0003,\u0006u\u0013\u0011!a\u0001\t\u007f\u000b!#\\1y\r&DX\r\u001a\"vM\u001a,'oU5{KR!1\u0011\u0002Cw\u0011!!I,!\u0019A\u0002\t]\"aE*z]\u000e\u0004&o\\2fgNLgn\u001a'j[&$8CCA2\u0003K\u000bI,a2\u0002NR!AQ\u001fC|!\u0011\ty0a\u0019\t\u0011\u0011\r\u0013\u0011\u000ea\u0001\u0005o!B\u0001\">\u0005|\"QA1IA6!\u0003\u0005\rAa\u000e\u0015\t\t\u0005Cq \u0005\u000b\u0005\u0013\n\u0019(!AA\u0002\t]B\u0003\u0002B0\u000b\u0007A!B!\u0013\u0002x\u0005\u0005\t\u0019\u0001B!)\u0011\u0011)#b\u0002\t\u0015\t%\u0013\u0011PA\u0001\u0002\u0004\u00119\u0004\u0006\u0003\u0003`\u0015-\u0001B\u0003B%\u0003\u007f\n\t\u00111\u0001\u0003B\u0005\u00192+\u001f8d!J|7-Z:tS:<G*[7jiB!\u0011q`AB'\u0019\t\u0019)b\u0005\u0003\u000eBA!1\u0011BE\u0005o!)\u0010\u0006\u0002\u0006\u0010Q!AQ_C\r\u0011!!\u0019%!#A\u0002\t]B\u0003\u0002C;\u000b;A!Ba+\u0002\f\u0006\u0005\t\u0019\u0001C{\u0003M\u0019\u0018P\\2Qe>\u001cWm]:j]\u001ed\u0015.\\5u)\u0011\u0019I!b\t\t\u0011\u0011\r\u0013q\u0012a\u0001\u0005o\u0001")
public final class ActorAttributes {
    public static Attributes syncProcessingLimit(int limit) {
        return ActorAttributes$.MODULE$.syncProcessingLimit(limit);
    }

    public static Attributes maxFixedBufferSize(int size) {
        return ActorAttributes$.MODULE$.maxFixedBufferSize(size);
    }

    public static Attributes fuzzingMode(boolean enabled) {
        return ActorAttributes$.MODULE$.fuzzingMode(enabled);
    }

    public static Attributes outputBurstLimit(int limit) {
        return ActorAttributes$.MODULE$.outputBurstLimit(limit);
    }

    public static Attributes streamSubscriptionTimeout(Duration timeout2, StreamSubscriptionTimeoutTerminationMode mode) {
        return ActorAttributes$.MODULE$.streamSubscriptionTimeout(timeout2, mode);
    }

    public static Attributes streamSubscriptionTimeout(FiniteDuration timeout2, StreamSubscriptionTimeoutTerminationMode mode) {
        return ActorAttributes$.MODULE$.streamSubscriptionTimeout(timeout2, mode);
    }

    public static Attributes debugLogging(boolean enabled) {
        return ActorAttributes$.MODULE$.debugLogging(enabled);
    }

    public static int logLevels$default$3() {
        return ActorAttributes$.MODULE$.logLevels$default$3();
    }

    public static int logLevels$default$2() {
        return ActorAttributes$.MODULE$.logLevels$default$2();
    }

    public static int logLevels$default$1() {
        return ActorAttributes$.MODULE$.logLevels$default$1();
    }

    public static Attributes logLevels(int onElement, int onFinish, int onFailure) {
        return ActorAttributes$.MODULE$.logLevels(onElement, onFinish, onFailure);
    }

    public static Attributes createLogLevels(int onElement) {
        return ActorAttributes$.MODULE$.createLogLevels(onElement);
    }

    public static Attributes createLogLevels(int onElement, int onFinish, int onFailure) {
        return ActorAttributes$.MODULE$.createLogLevels(onElement, onFinish, onFailure);
    }

    public static Attributes withSupervisionStrategy(Function<Throwable, Supervision.Directive> decider) {
        return ActorAttributes$.MODULE$.withSupervisionStrategy(decider);
    }

    public static Attributes supervisionStrategy(Function1<Throwable, Supervision.Directive> decider) {
        return ActorAttributes$.MODULE$.supervisionStrategy(decider);
    }

    public static Attributes dispatcher(String dispatcher) {
        return ActorAttributes$.MODULE$.dispatcher(dispatcher);
    }

    public static Dispatcher IODispatcher() {
        return ActorAttributes$.MODULE$.IODispatcher();
    }

    public static final class DebugLogging
    implements Attributes.MandatoryAttribute,
    Product,
    Serializable {
        private final boolean enabled;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public boolean enabled() {
            return this.enabled;
        }

        public DebugLogging copy(boolean enabled) {
            return new DebugLogging(enabled);
        }

        public boolean copy$default$1() {
            return this.enabled();
        }

        @Override
        public String productPrefix() {
            return "DebugLogging";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToBoolean(this.enabled());
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof DebugLogging;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "enabled";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.enabled() ? 1231 : 1237);
            return Statics.finalizeHash(n, 1);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof DebugLogging)) return false;
            boolean bl = true;
            if (!bl) return false;
            DebugLogging debugLogging = (DebugLogging)x$1;
            if (this.enabled() != debugLogging.enabled()) return false;
            return true;
        }

        public DebugLogging(boolean enabled) {
            this.enabled = enabled;
            Product.$init$(this);
        }
    }

    public static final class Dispatcher
    implements Attributes.MandatoryAttribute,
    Product,
    Serializable {
        private final String dispatcher;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String dispatcher() {
            return this.dispatcher;
        }

        public Dispatcher copy(String dispatcher) {
            return new Dispatcher(dispatcher);
        }

        public String copy$default$1() {
            return this.dispatcher();
        }

        @Override
        public String productPrefix() {
            return "Dispatcher";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.dispatcher();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof Dispatcher;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "dispatcher";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof Dispatcher)) return false;
            boolean bl = true;
            if (!bl) return false;
            Dispatcher dispatcher = (Dispatcher)x$1;
            String string = this.dispatcher();
            String string2 = dispatcher.dispatcher();
            if (string != null) {
                if (!string.equals(string2)) return false;
                return true;
            }
            if (string2 == null) return true;
            return false;
        }

        public Dispatcher(String dispatcher) {
            this.dispatcher = dispatcher;
            Product.$init$(this);
        }
    }

    public static final class FuzzingMode
    implements Attributes.MandatoryAttribute,
    Product,
    Serializable {
        private final boolean enabled;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public boolean enabled() {
            return this.enabled;
        }

        public FuzzingMode copy(boolean enabled) {
            return new FuzzingMode(enabled);
        }

        public boolean copy$default$1() {
            return this.enabled();
        }

        @Override
        public String productPrefix() {
            return "FuzzingMode";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToBoolean(this.enabled());
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof FuzzingMode;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "enabled";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.enabled() ? 1231 : 1237);
            return Statics.finalizeHash(n, 1);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof FuzzingMode)) return false;
            boolean bl = true;
            if (!bl) return false;
            FuzzingMode fuzzingMode = (FuzzingMode)x$1;
            if (this.enabled() != fuzzingMode.enabled()) return false;
            return true;
        }

        public FuzzingMode(boolean enabled) {
            this.enabled = enabled;
            Product.$init$(this);
        }
    }

    public static final class MaxFixedBufferSize
    implements Attributes.MandatoryAttribute,
    Product,
    Serializable {
        private final int size;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int size() {
            return this.size;
        }

        public MaxFixedBufferSize copy(int size) {
            return new MaxFixedBufferSize(size);
        }

        public int copy$default$1() {
            return this.size();
        }

        @Override
        public String productPrefix() {
            return "MaxFixedBufferSize";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToInteger(this.size());
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof MaxFixedBufferSize;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "size";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.size());
            return Statics.finalizeHash(n, 1);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof MaxFixedBufferSize)) return false;
            boolean bl = true;
            if (!bl) return false;
            MaxFixedBufferSize maxFixedBufferSize = (MaxFixedBufferSize)x$1;
            if (this.size() != maxFixedBufferSize.size()) return false;
            return true;
        }

        public MaxFixedBufferSize(int size) {
            this.size = size;
            Product.$init$(this);
        }
    }

    public static final class OutputBurstLimit
    implements Attributes.MandatoryAttribute,
    Product,
    Serializable {
        private final int limit;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int limit() {
            return this.limit;
        }

        public OutputBurstLimit copy(int limit) {
            return new OutputBurstLimit(limit);
        }

        public int copy$default$1() {
            return this.limit();
        }

        @Override
        public String productPrefix() {
            return "OutputBurstLimit";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToInteger(this.limit());
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof OutputBurstLimit;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "limit";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.limit());
            return Statics.finalizeHash(n, 1);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof OutputBurstLimit)) return false;
            boolean bl = true;
            if (!bl) return false;
            OutputBurstLimit outputBurstLimit = (OutputBurstLimit)x$1;
            if (this.limit() != outputBurstLimit.limit()) return false;
            return true;
        }

        public OutputBurstLimit(int limit) {
            this.limit = limit;
            Product.$init$(this);
        }
    }

    public static final class StreamSubscriptionTimeout
    implements Attributes.MandatoryAttribute,
    Product,
    Serializable {
        private final FiniteDuration timeout;
        private final StreamSubscriptionTimeoutTerminationMode mode;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public FiniteDuration timeout() {
            return this.timeout;
        }

        public StreamSubscriptionTimeoutTerminationMode mode() {
            return this.mode;
        }

        public StreamSubscriptionTimeout copy(FiniteDuration timeout2, StreamSubscriptionTimeoutTerminationMode mode) {
            return new StreamSubscriptionTimeout(timeout2, mode);
        }

        public FiniteDuration copy$default$1() {
            return this.timeout();
        }

        public StreamSubscriptionTimeoutTerminationMode copy$default$2() {
            return this.mode();
        }

        @Override
        public String productPrefix() {
            return "StreamSubscriptionTimeout";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.timeout();
                }
                case 1: {
                    return this.mode();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof StreamSubscriptionTimeout;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "timeout";
                }
                case 1: {
                    return "mode";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof StreamSubscriptionTimeout)) return false;
            boolean bl = true;
            if (!bl) return false;
            StreamSubscriptionTimeout streamSubscriptionTimeout = (StreamSubscriptionTimeout)x$1;
            FiniteDuration finiteDuration = this.timeout();
            FiniteDuration finiteDuration2 = streamSubscriptionTimeout.timeout();
            if (finiteDuration == null) {
                if (finiteDuration2 != null) {
                    return false;
                }
            } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
            StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode = this.mode();
            StreamSubscriptionTimeoutTerminationMode streamSubscriptionTimeoutTerminationMode2 = streamSubscriptionTimeout.mode();
            if (streamSubscriptionTimeoutTerminationMode == null) {
                if (streamSubscriptionTimeoutTerminationMode2 == null) return true;
                return false;
            } else {
                if (!streamSubscriptionTimeoutTerminationMode.equals(streamSubscriptionTimeoutTerminationMode2)) return false;
                return true;
            }
        }

        public StreamSubscriptionTimeout(FiniteDuration timeout2, StreamSubscriptionTimeoutTerminationMode mode) {
            this.timeout = timeout2;
            this.mode = mode;
            Product.$init$(this);
        }
    }

    public static final class SupervisionStrategy
    implements Attributes.MandatoryAttribute,
    Product,
    Serializable {
        private final Function1<Throwable, Supervision.Directive> decider;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Function1<Throwable, Supervision.Directive> decider() {
            return this.decider;
        }

        public SupervisionStrategy copy(Function1<Throwable, Supervision.Directive> decider) {
            return new SupervisionStrategy(decider);
        }

        public Function1<Throwable, Supervision.Directive> copy$default$1() {
            return this.decider();
        }

        @Override
        public String productPrefix() {
            return "SupervisionStrategy";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.decider();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof SupervisionStrategy;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "decider";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof SupervisionStrategy)) return false;
            boolean bl = true;
            if (!bl) return false;
            SupervisionStrategy supervisionStrategy = (SupervisionStrategy)x$1;
            Function1<Throwable, Supervision.Directive> function1 = this.decider();
            Function1<Throwable, Supervision.Directive> function12 = supervisionStrategy.decider();
            if (function1 != null) {
                if (!function1.equals(function12)) return false;
                return true;
            }
            if (function12 == null) return true;
            return false;
        }

        public SupervisionStrategy(Function1<Throwable, Supervision.Directive> decider) {
            this.decider = decider;
            Product.$init$(this);
        }
    }

    public static final class SyncProcessingLimit
    implements Attributes.MandatoryAttribute,
    Product,
    Serializable {
        private final int limit;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public int limit() {
            return this.limit;
        }

        public SyncProcessingLimit copy(int limit) {
            return new SyncProcessingLimit(limit);
        }

        public int copy$default$1() {
            return this.limit();
        }

        @Override
        public String productPrefix() {
            return "SyncProcessingLimit";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToInteger(this.limit());
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof SyncProcessingLimit;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "limit";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.limit());
            return Statics.finalizeHash(n, 1);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof SyncProcessingLimit)) return false;
            boolean bl = true;
            if (!bl) return false;
            SyncProcessingLimit syncProcessingLimit = (SyncProcessingLimit)x$1;
            if (this.limit() != syncProcessingLimit.limit()) return false;
            return true;
        }

        public SyncProcessingLimit(int limit) {
            this.limit = limit;
            Product.$init$(this);
        }
    }
}

