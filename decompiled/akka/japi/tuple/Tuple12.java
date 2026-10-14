/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple12$;
import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0011}v!B\u001d;\u0011\u0003\te!B\";\u0011\u0003!\u0005\"B*\u0002\t\u0003!\u0006\"B+\u0002\t\u00031\u0006\"\u0003C\u0011\u0003\u0005\u0005I\u0011\u0011C\u0012\u0011%!\t(AA\u0001\n\u0003#\u0019\bC\u0005\u00056\u0006\t\t\u0011\"\u0003\u00058\u001a!1I\u000f\"Z\u0011!IwA!f\u0001\n\u0003Q\u0007\u0002\u0003<\b\u0005#\u0005\u000b\u0011B6\t\u0011]<!Q3A\u0005\u0002aD\u0001\u0002`\u0004\u0003\u0012\u0003\u0006I!\u001f\u0005\t{\u001e\u0011)\u001a!C\u0001}\"I\u0011QA\u0004\u0003\u0012\u0003\u0006Ia \u0005\u000b\u0003\u000f9!Q3A\u0005\u0002\u0005%\u0001BCA\t\u000f\tE\t\u0015!\u0003\u0002\f!Q\u00111C\u0004\u0003\u0016\u0004%\t!!\u0006\t\u0015\u0005uqA!E!\u0002\u0013\t9\u0002\u0003\u0006\u0002 \u001d\u0011)\u001a!C\u0001\u0003CA!\"!\u000b\b\u0005#\u0005\u000b\u0011BA\u0012\u0011)\tYc\u0002BK\u0002\u0013\u0005\u0011Q\u0006\u0005\u000b\u0003k9!\u0011#Q\u0001\n\u0005=\u0002BCA\u001c\u000f\tU\r\u0011\"\u0001\u0002:!Q\u0011\u0011I\u0004\u0003\u0012\u0003\u0006I!a\u000f\t\u0015\u0005\rsA!f\u0001\n\u0003\t)\u0005\u0003\u0006\u0002N\u001d\u0011\t\u0012)A\u0005\u0003\u000fB!\"a\u0014\b\u0005+\u0007I\u0011AA)\u0011)\tIf\u0002B\tB\u0003%\u00111\u000b\u0005\u000b\u00037:!Q3A\u0005\u0002\u0005u\u0003BCA3\u000f\tE\t\u0015!\u0003\u0002`!Q\u0011qM\u0004\u0003\u0016\u0004%\t!!\u001b\t\u0015\u0005EtA!E!\u0002\u0013\tY\u0007\u0003\u0004T\u000f\u0011\u0005\u00111\u000f\u0005\n\u0003\u001f;!\u0019!C\u0001\u0003#C\u0001\"a&\bA\u0003%\u00111\u0013\u0005\n\u00033;\u0011\u0011!C\u0001\u00037C\u0011\"!;\b#\u0003%\t!a;\t\u0013\tmq!%A\u0005\u0002\tu\u0001\"\u0003B\u001e\u000fE\u0005I\u0011\u0001B\u001f\u0011%\u0011YfBI\u0001\n\u0003\u0011i\u0006C\u0005\u0003|\u001d\t\n\u0011\"\u0001\u0003~!I!1T\u0004\u0012\u0002\u0013\u0005!Q\u0014\u0005\n\u0005w;\u0011\u0013!C\u0001\u0005{C\u0011Ba7\b#\u0003%\tA!8\t\u0013\tmx!%A\u0005\u0002\tu\b\"CB\u000e\u000fE\u0005I\u0011AB\u000f\u0011%\u0019YdBI\u0001\n\u0003\u0019i\u0004C\u0005\u0004\\\u001d\t\n\u0011\"\u0001\u0004^!I11P\u0004\u0002\u0002\u0013\u00053Q\u0010\u0005\n\u0007\u0017;\u0011\u0011!C\u0001\u0007\u001bC\u0011b!&\b\u0003\u0003%\taa&\t\u0013\ruu!!A\u0005B\r}\u0005\"CBW\u000f\u0005\u0005I\u0011ABX\u0011%\u0019IlBA\u0001\n\u0003\u001aY\fC\u0005\u0004@\u001e\t\t\u0011\"\u0011\u0004B\"I11Y\u0004\u0002\u0002\u0013\u00053Q\u0019\u0005\n\u0007\u000f<\u0011\u0011!C!\u0007\u0013\fq\u0001V;qY\u0016\f$G\u0003\u0002<y\u0005)A/\u001e9mK*\u0011QHP\u0001\u0005U\u0006\u0004\u0018NC\u0001@\u0003\u0011\t7n[1\u0004\u0001A\u0011!)A\u0007\u0002u\t9A+\u001e9mKF\u00124cA\u0001F\u0017B\u0011a)S\u0007\u0002\u000f*\t\u0001*A\u0003tG\u0006d\u0017-\u0003\u0002K\u000f\n1\u0011I\\=SK\u001a\u0004\"\u0001T)\u000e\u00035S!AT(\u0002\u0005%|'\"\u0001)\u0002\t)\fg/Y\u0005\u0003%6\u0013AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRtD#A!\u0002\r\r\u0014X-\u0019;f+e961\\Bp\u0007G\u001c9oa;\u0004p\u000eM8q_B~\u0007\u007f$\u0019\u0001b\u0002\u00153a#I\u0001b\u0003\u0005\u000e\u0011=A\u0011\u0003C\n\t+!9\u0002\"\u0007\u0005\u001c\u0011uAq\u0004\t\u001b\u0005\u001e\u0019In!8\u0004b\u000e\u00158\u0011^Bw\u0007c\u001c)p!?\u0004~\u0012\u0005AQA\u000b\u001856T\u0018\u0011AA\u0007\u00033\t)#!\r\u0002>\u0005%\u0013QKA1\u0003[\u001aBaB#\\=B\u0011a\tX\u0005\u0003;\u001e\u0013q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002`O:\u0011\u0001-\u001a\b\u0003C\u0012l\u0011A\u0019\u0006\u0003G\u0002\u000ba\u0001\u0010:p_Rt\u0014\"\u0001%\n\u0005\u0019<\u0015a\u00029bG.\fw-Z\u0005\u0003%\"T!AZ$\u0002\u0005Q\fT#A6\u0011\u00051lG\u0002\u0001\u0003\u0006]\u001e\u0011\ra\u001c\u0002\u0003)F\n\"\u0001]:\u0011\u0005\u0019\u000b\u0018B\u0001:H\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0012;\n\u0005U<%aA!os\u0006\u0019A/\r\u0011\u0002\u0005Q\u0014T#A=\u0011\u00051TH!B>\b\u0005\u0004y'A\u0001+3\u0003\r!(\u0007I\u0001\u0003iN*\u0012a \t\u0004Y\u0006\u0005AABA\u0002\u000f\t\u0007qN\u0001\u0002Ug\u0005\u0019Ao\r\u0011\u0002\u0005Q$TCAA\u0006!\ra\u0017Q\u0002\u0003\u0007\u0003\u001f9!\u0019A8\u0003\u0005Q#\u0014a\u0001;5A\u0005\u0011A/N\u000b\u0003\u0003/\u00012\u0001\\A\r\t\u0019\tYb\u0002b\u0001_\n\u0011A+N\u0001\u0004iV\u0002\u0013A\u0001;7+\t\t\u0019\u0003E\u0002m\u0003K!a!a\n\b\u0005\u0004y'A\u0001+7\u0003\r!h\u0007I\u0001\u0003i^*\"!a\f\u0011\u00071\f\t\u0004\u0002\u0004\u00024\u001d\u0011\ra\u001c\u0002\u0003)^\n1\u0001^\u001c!\u0003\t!\b(\u0006\u0002\u0002<A\u0019A.!\u0010\u0005\r\u0005}rA1\u0001p\u0005\t!\u0006(A\u0002uq\u0001\n!\u0001^\u001d\u0016\u0005\u0005\u001d\u0003c\u00017\u0002J\u00111\u00111J\u0004C\u0002=\u0014!\u0001V\u001d\u0002\u0007QL\u0004%A\u0002ucA*\"!a\u0015\u0011\u00071\f)\u0006\u0002\u0004\u0002X\u001d\u0011\ra\u001c\u0002\u0004)F\u0002\u0014\u0001\u0002;2a\u0001\n1\u0001^\u00192+\t\ty\u0006E\u0002m\u0003C\"a!a\u0019\b\u0005\u0004y'a\u0001+2c\u0005!A/M\u0019!\u0003\r!\u0018GM\u000b\u0003\u0003W\u00022\u0001\\A7\t\u0019\tyg\u0002b\u0001_\n\u0019A+\r\u001a\u0002\tQ\f$\u0007\t\u000b\u001b\u0003k\n9(!\u001f\u0002|\u0005u\u0014qPAA\u0003\u0007\u000b))a\"\u0002\n\u0006-\u0015Q\u0012\t\u0018\u0005\u001eY\u0017p`A\u0006\u0003/\t\u0019#a\f\u0002<\u0005\u001d\u00131KA0\u0003WBQ!\u001b\u0011A\u0002-DQa\u001e\u0011A\u0002eDQ! \u0011A\u0002}Dq!a\u0002!\u0001\u0004\tY\u0001C\u0004\u0002\u0014\u0001\u0002\r!a\u0006\t\u000f\u0005}\u0001\u00051\u0001\u0002$!9\u00111\u0006\u0011A\u0002\u0005=\u0002bBA\u001cA\u0001\u0007\u00111\b\u0005\b\u0003\u0007\u0002\u0003\u0019AA$\u0011\u001d\ty\u0005\ta\u0001\u0003'Bq!a\u0017!\u0001\u0004\ty\u0006C\u0004\u0002h\u0001\u0002\r!a\u001b\u0002\u000fQ|7kY1mCV\u0011\u00111\u0013\t\u0019\r\u0006U5._@\u0002\f\u0005]\u00111EA\u0018\u0003w\t9%a\u0015\u0002`\u0005-\u0014BA\"H\u0003!!xnU2bY\u0006\u0004\u0013\u0001B2paf,\"$!(\u0002$\u0006\u001d\u00161VAX\u0003g\u000b9,a/\u0002@\u0006\r\u0017qYAf\u0003\u001f$\"$a(\u0002R\u0006M\u0017Q[Al\u00033\fY.!8\u0002`\u0006\u0005\u00181]As\u0003O\u0004\"DQ\u0004\u0002\"\u0006\u0015\u0016\u0011VAW\u0003c\u000b),!/\u0002>\u0006\u0005\u0017QYAe\u0003\u001b\u00042\u0001\\AR\t\u0015q7E1\u0001p!\ra\u0017q\u0015\u0003\u0006w\u000e\u0012\ra\u001c\t\u0004Y\u0006-FABA\u0002G\t\u0007q\u000eE\u0002m\u0003_#a!a\u0004$\u0005\u0004y\u0007c\u00017\u00024\u00121\u00111D\u0012C\u0002=\u00042\u0001\\A\\\t\u0019\t9c\tb\u0001_B\u0019A.a/\u0005\r\u0005M2E1\u0001p!\ra\u0017q\u0018\u0003\u0007\u0003\u007f\u0019#\u0019A8\u0011\u00071\f\u0019\r\u0002\u0004\u0002L\r\u0012\ra\u001c\t\u0004Y\u0006\u001dGABA,G\t\u0007q\u000eE\u0002m\u0003\u0017$a!a\u0019$\u0005\u0004y\u0007c\u00017\u0002P\u00121\u0011qN\u0012C\u0002=D\u0001\"[\u0012\u0011\u0002\u0003\u0007\u0011\u0011\u0015\u0005\to\u000e\u0002\n\u00111\u0001\u0002&\"AQp\tI\u0001\u0002\u0004\tI\u000bC\u0005\u0002\b\r\u0002\n\u00111\u0001\u0002.\"I\u00111C\u0012\u0011\u0002\u0003\u0007\u0011\u0011\u0017\u0005\n\u0003?\u0019\u0003\u0013!a\u0001\u0003kC\u0011\"a\u000b$!\u0003\u0005\r!!/\t\u0013\u0005]2\u0005%AA\u0002\u0005u\u0006\"CA\"GA\u0005\t\u0019AAa\u0011%\tye\tI\u0001\u0002\u0004\t)\rC\u0005\u0002\\\r\u0002\n\u00111\u0001\u0002J\"I\u0011qM\u0012\u0011\u0002\u0003\u0007\u0011QZ\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+i\tiOa\u0001\u0003\u0006\t\u001d!\u0011\u0002B\u0006\u0005\u001b\u0011yA!\u0005\u0003\u0014\tU!q\u0003B\r+\t\tyOK\u0002l\u0003c\\#!a=\u0011\t\u0005U\u0018q`\u0007\u0003\u0003oTA!!?\u0002|\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003{<\u0015AC1o]>$\u0018\r^5p]&!!\u0011AA|\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006]\u0012\u0012\ra\u001c\u0003\u0006w\u0012\u0012\ra\u001c\u0003\u0007\u0003\u0007!#\u0019A8\u0005\r\u0005=AE1\u0001p\t\u0019\tY\u0002\nb\u0001_\u00121\u0011q\u0005\u0013C\u0002=$a!a\r%\u0005\u0004yGABA I\t\u0007q\u000e\u0002\u0004\u0002L\u0011\u0012\ra\u001c\u0003\u0007\u0003/\"#\u0019A8\u0005\r\u0005\rDE1\u0001p\t\u0019\ty\u0007\nb\u0001_\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012TC\u0007B\u0010\u0005G\u0011)Ca\n\u0003*\t-\"Q\u0006B\u0018\u0005c\u0011\u0019D!\u000e\u00038\teRC\u0001B\u0011U\rI\u0018\u0011\u001f\u0003\u0006]\u0016\u0012\ra\u001c\u0003\u0006w\u0016\u0012\ra\u001c\u0003\u0007\u0003\u0007)#\u0019A8\u0005\r\u0005=QE1\u0001p\t\u0019\tY\"\nb\u0001_\u00121\u0011qE\u0013C\u0002=$a!a\r&\u0005\u0004yGABA K\t\u0007q\u000e\u0002\u0004\u0002L\u0015\u0012\ra\u001c\u0003\u0007\u0003/*#\u0019A8\u0005\r\u0005\rTE1\u0001p\t\u0019\ty'\nb\u0001_\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTC\u0007B \u0005\u0007\u0012)Ea\u0012\u0003J\t-#Q\nB(\u0005#\u0012\u0019F!\u0016\u0003X\teSC\u0001B!U\ry\u0018\u0011\u001f\u0003\u0006]\u001a\u0012\ra\u001c\u0003\u0006w\u001a\u0012\ra\u001c\u0003\u0007\u0003\u00071#\u0019A8\u0005\r\u0005=aE1\u0001p\t\u0019\tYB\nb\u0001_\u00121\u0011q\u0005\u0014C\u0002=$a!a\r'\u0005\u0004yGABA M\t\u0007q\u000e\u0002\u0004\u0002L\u0019\u0012\ra\u001c\u0003\u0007\u0003/2#\u0019A8\u0005\r\u0005\rdE1\u0001p\t\u0019\tyG\nb\u0001_\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\"TC\u0007B0\u0005G\u0012)Ga\u001a\u0003j\t-$Q\u000eB8\u0005c\u0012\u0019H!\u001e\u0003x\teTC\u0001B1U\u0011\tY!!=\u0005\u000b9<#\u0019A8\u0005\u000bm<#\u0019A8\u0005\r\u0005\rqE1\u0001p\t\u0019\tya\nb\u0001_\u00121\u00111D\u0014C\u0002=$a!a\n(\u0005\u0004yGABA\u001aO\t\u0007q\u000e\u0002\u0004\u0002@\u001d\u0012\ra\u001c\u0003\u0007\u0003\u0017:#\u0019A8\u0005\r\u0005]sE1\u0001p\t\u0019\t\u0019g\nb\u0001_\u00121\u0011qN\u0014C\u0002=\fabY8qs\u0012\"WMZ1vYR$S'\u0006\u000e\u0003\u0000\t\r%Q\u0011BD\u0005\u0013\u0013YI!$\u0003\u0010\nE%1\u0013BK\u0005/\u0013I*\u0006\u0002\u0003\u0002*\"\u0011qCAy\t\u0015q\u0007F1\u0001p\t\u0015Y\bF1\u0001p\t\u0019\t\u0019\u0001\u000bb\u0001_\u00121\u0011q\u0002\u0015C\u0002=$a!a\u0007)\u0005\u0004yGABA\u0014Q\t\u0007q\u000e\u0002\u0004\u00024!\u0012\ra\u001c\u0003\u0007\u0003\u007fA#\u0019A8\u0005\r\u0005-\u0003F1\u0001p\t\u0019\t9\u0006\u000bb\u0001_\u00121\u00111\r\u0015C\u0002=$a!a\u001c)\u0005\u0004y\u0017AD2paf$C-\u001a4bk2$HEN\u000b\u001b\u0005?\u0013\u0019K!*\u0003(\n%&1\u0016BW\u0005_\u0013\tLa-\u00036\n]&\u0011X\u000b\u0003\u0005CSC!a\t\u0002r\u0012)a.\u000bb\u0001_\u0012)10\u000bb\u0001_\u00121\u00111A\u0015C\u0002=$a!a\u0004*\u0005\u0004yGABA\u000eS\t\u0007q\u000e\u0002\u0004\u0002(%\u0012\ra\u001c\u0003\u0007\u0003gI#\u0019A8\u0005\r\u0005}\u0012F1\u0001p\t\u0019\tY%\u000bb\u0001_\u00121\u0011qK\u0015C\u0002=$a!a\u0019*\u0005\u0004yGABA8S\t\u0007q.\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001c\u00165\t}&1\u0019Bc\u0005\u000f\u0014IMa3\u0003N\n='\u0011\u001bBj\u0005+\u00149N!7\u0016\u0005\t\u0005'\u0006BA\u0018\u0003c$QA\u001c\u0016C\u0002=$Qa\u001f\u0016C\u0002=$a!a\u0001+\u0005\u0004yGABA\bU\t\u0007q\u000e\u0002\u0004\u0002\u001c)\u0012\ra\u001c\u0003\u0007\u0003OQ#\u0019A8\u0005\r\u0005M\"F1\u0001p\t\u0019\tyD\u000bb\u0001_\u00121\u00111\n\u0016C\u0002=$a!a\u0016+\u0005\u0004yGABA2U\t\u0007q\u000e\u0002\u0004\u0002p)\u0012\ra\\\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00139+i\u0011yNa9\u0003f\n\u001d(\u0011\u001eBv\u0005[\u0014yO!=\u0003t\nU(q\u001fB}+\t\u0011\tO\u000b\u0003\u0002<\u0005EH!\u00028,\u0005\u0004yG!B>,\u0005\u0004yGABA\u0002W\t\u0007q\u000e\u0002\u0004\u0002\u0010-\u0012\ra\u001c\u0003\u0007\u00037Y#\u0019A8\u0005\r\u0005\u001d2F1\u0001p\t\u0019\t\u0019d\u000bb\u0001_\u00121\u0011qH\u0016C\u0002=$a!a\u0013,\u0005\u0004yGABA,W\t\u0007q\u000e\u0002\u0004\u0002d-\u0012\ra\u001c\u0003\u0007\u0003_Z#\u0019A8\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%sUQ\"q`B\u0002\u0007\u000b\u00199a!\u0003\u0004\f\r51qBB\t\u0007'\u0019)ba\u0006\u0004\u001aU\u00111\u0011\u0001\u0016\u0005\u0003\u000f\n\t\u0010B\u0003oY\t\u0007q\u000eB\u0003|Y\t\u0007q\u000e\u0002\u0004\u0002\u00041\u0012\ra\u001c\u0003\u0007\u0003\u001fa#\u0019A8\u0005\r\u0005mAF1\u0001p\t\u0019\t9\u0003\fb\u0001_\u00121\u00111\u0007\u0017C\u0002=$a!a\u0010-\u0005\u0004yGABA&Y\t\u0007q\u000e\u0002\u0004\u0002X1\u0012\ra\u001c\u0003\u0007\u0003Gb#\u0019A8\u0005\r\u0005=DF1\u0001p\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\u0002TCGB\u0010\u0007G\u0019)ca\n\u0004*\r-2QFB\u0018\u0007c\u0019\u0019d!\u000e\u00048\reRCAB\u0011U\u0011\t\u0019&!=\u0005\u000b9l#\u0019A8\u0005\u000bml#\u0019A8\u0005\r\u0005\rQF1\u0001p\t\u0019\ty!\fb\u0001_\u00121\u00111D\u0017C\u0002=$a!a\n.\u0005\u0004yGABA\u001a[\t\u0007q\u000e\u0002\u0004\u0002@5\u0012\ra\u001c\u0003\u0007\u0003\u0017j#\u0019A8\u0005\r\u0005]SF1\u0001p\t\u0019\t\u0019'\fb\u0001_\u00121\u0011qN\u0017C\u0002=\fqbY8qs\u0012\"WMZ1vYR$\u0013'M\u000b\u001b\u0007\u007f\u0019\u0019e!\u0012\u0004H\r%31JB'\u0007\u001f\u001a\tfa\u0015\u0004V\r]3\u0011L\u000b\u0003\u0007\u0003RC!a\u0018\u0002r\u0012)aN\fb\u0001_\u0012)1P\fb\u0001_\u00121\u00111\u0001\u0018C\u0002=$a!a\u0004/\u0005\u0004yGABA\u000e]\t\u0007q\u000e\u0002\u0004\u0002(9\u0012\ra\u001c\u0003\u0007\u0003gq#\u0019A8\u0005\r\u0005}bF1\u0001p\t\u0019\tYE\fb\u0001_\u00121\u0011q\u000b\u0018C\u0002=$a!a\u0019/\u0005\u0004yGABA8]\t\u0007q.A\bd_BLH\u0005Z3gCVdG\u000fJ\u00193+i\u0019yfa\u0019\u0004f\r\u001d4\u0011NB6\u0007[\u001ayg!\u001d\u0004t\rU4qOB=+\t\u0019\tG\u000b\u0003\u0002l\u0005EH!\u000280\u0005\u0004yG!B>0\u0005\u0004yGABA\u0002_\t\u0007q\u000e\u0002\u0004\u0002\u0010=\u0012\ra\u001c\u0003\u0007\u00037y#\u0019A8\u0005\r\u0005\u001drF1\u0001p\t\u0019\t\u0019d\fb\u0001_\u00121\u0011qH\u0018C\u0002=$a!a\u00130\u0005\u0004yGABA,_\t\u0007q\u000e\u0002\u0004\u0002d=\u0012\ra\u001c\u0003\u0007\u0003_z#\u0019A8\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u0019y\b\u0005\u0003\u0004\u0002\u000e\u001dUBABB\u0015\r\u0019)iT\u0001\u0005Y\u0006tw-\u0003\u0003\u0004\n\u000e\r%AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0006\u0002\u0004\u0010B\u0019ai!%\n\u0007\rMuIA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000fF\u0002t\u00073C\u0011ba'3\u0003\u0003\u0005\raa$\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\u0019\t\u000bE\u0003\u0004$\u000e%6/\u0004\u0002\u0004&*\u00191qU$\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0004,\u000e\u0015&\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$Ba!-\u00048B\u0019aia-\n\u0007\rUvIA\u0004C_>dW-\u00198\t\u0011\rmE'!AA\u0002M\f!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!1qPB_\u0011%\u0019Y*NA\u0001\u0002\u0004\u0019y)\u0001\u0005iCND7i\u001c3f)\t\u0019y)\u0001\u0005u_N#(/\u001b8h)\t\u0019y(\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0007c\u001bY\r\u0003\u0005\u0004\u001cb\n\t\u00111\u0001tQ\u001d91qZBk\u0007/\u00042ARBi\u0013\r\u0019\u0019n\u0012\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!\u0001\t\u0004Y\u000emG!\u00028\u0004\u0005\u0004y\u0007c\u00017\u0004`\u0012)1p\u0001b\u0001_B\u0019Ana9\u0005\r\u0005\r1A1\u0001p!\ra7q\u001d\u0003\u0007\u0003\u001f\u0019!\u0019A8\u0011\u00071\u001cY\u000f\u0002\u0004\u0002\u001c\r\u0011\ra\u001c\t\u0004Y\u000e=HABA\u0014\u0007\t\u0007q\u000eE\u0002m\u0007g$a!a\r\u0004\u0005\u0004y\u0007c\u00017\u0004x\u00121\u0011qH\u0002C\u0002=\u00042\u0001\\B~\t\u0019\tYe\u0001b\u0001_B\u0019Ana@\u0005\r\u0005]3A1\u0001p!\raG1\u0001\u0003\u0007\u0003G\u001a!\u0019A8\u0011\u00071$9\u0001\u0002\u0004\u0002p\r\u0011\ra\u001c\u0005\u0007S\u000e\u0001\ra!7\t\r]\u001c\u0001\u0019ABo\u0011\u0019i8\u00011\u0001\u0004b\"9\u0011qA\u0002A\u0002\r\u0015\bbBA\n\u0007\u0001\u00071\u0011\u001e\u0005\b\u0003?\u0019\u0001\u0019ABw\u0011\u001d\tYc\u0001a\u0001\u0007cDq!a\u000e\u0004\u0001\u0004\u0019)\u0010C\u0004\u0002D\r\u0001\ra!?\t\u000f\u0005=3\u00011\u0001\u0004~\"9\u00111L\u0002A\u0002\u0011\u0005\u0001bBA4\u0007\u0001\u0007AQA\u0001\u0006CB\u0004H._\u000b\u001b\tK!Y\u0003b\f\u00054\u0011]B1\bC \t\u0007\"9\u0005b\u0013\u0005P\u0011MCq\u000b\u000b\u001b\tO!I\u0006b\u0017\u0005^\u0011}C\u0011\rC2\tK\"9\u0007\"\u001b\u0005l\u00115Dq\u000e\t\u001b\u0005\u001e!I\u0003\"\f\u00052\u0011UB\u0011\bC\u001f\t\u0003\")\u0005\"\u0013\u0005N\u0011ECQ\u000b\t\u0004Y\u0012-B!\u00028\u0005\u0005\u0004y\u0007c\u00017\u00050\u0011)1\u0010\u0002b\u0001_B\u0019A\u000eb\r\u0005\r\u0005\rAA1\u0001p!\raGq\u0007\u0003\u0007\u0003\u001f!!\u0019A8\u0011\u00071$Y\u0004\u0002\u0004\u0002\u001c\u0011\u0011\ra\u001c\t\u0004Y\u0012}BABA\u0014\t\t\u0007q\u000eE\u0002m\t\u0007\"a!a\r\u0005\u0005\u0004y\u0007c\u00017\u0005H\u00111\u0011q\b\u0003C\u0002=\u00042\u0001\u001cC&\t\u0019\tY\u0005\u0002b\u0001_B\u0019A\u000eb\u0014\u0005\r\u0005]CA1\u0001p!\raG1\u000b\u0003\u0007\u0003G\"!\u0019A8\u0011\u00071$9\u0006\u0002\u0004\u0002p\u0011\u0011\ra\u001c\u0005\u0007S\u0012\u0001\r\u0001\"\u000b\t\r]$\u0001\u0019\u0001C\u0017\u0011\u0019iH\u00011\u0001\u00052!9\u0011q\u0001\u0003A\u0002\u0011U\u0002bBA\n\t\u0001\u0007A\u0011\b\u0005\b\u0003?!\u0001\u0019\u0001C\u001f\u0011\u001d\tY\u0003\u0002a\u0001\t\u0003Bq!a\u000e\u0005\u0001\u0004!)\u0005C\u0004\u0002D\u0011\u0001\r\u0001\"\u0013\t\u000f\u0005=C\u00011\u0001\u0005N!9\u00111\f\u0003A\u0002\u0011E\u0003bBA4\t\u0001\u0007AQK\u0001\bk:\f\u0007\u000f\u001d7z+i!)\b\"!\u0005\u0006\u0012%EQ\u0012CI\t+#I\n\"(\u0005\"\u0012\u0015F\u0011\u0016CW)\u0011!9\bb,\u0011\u000b\u0019#I\b\" \n\u0007\u0011mtI\u0001\u0004PaRLwN\u001c\t\u001c\r\u0006UEq\u0010CB\t\u000f#Y\tb$\u0005\u0014\u0012]E1\u0014CP\tG#9\u000bb+\u0011\u00071$\t\tB\u0003o\u000b\t\u0007q\u000eE\u0002m\t\u000b#Qa_\u0003C\u0002=\u00042\u0001\u001cCE\t\u0019\t\u0019!\u0002b\u0001_B\u0019A\u000e\"$\u0005\r\u0005=QA1\u0001p!\raG\u0011\u0013\u0003\u0007\u00037)!\u0019A8\u0011\u00071$)\n\u0002\u0004\u0002(\u0015\u0011\ra\u001c\t\u0004Y\u0012eEABA\u001a\u000b\t\u0007q\u000eE\u0002m\t;#a!a\u0010\u0006\u0005\u0004y\u0007c\u00017\u0005\"\u00121\u00111J\u0003C\u0002=\u00042\u0001\u001cCS\t\u0019\t9&\u0002b\u0001_B\u0019A\u000e\"+\u0005\r\u0005\rTA1\u0001p!\raGQ\u0016\u0003\u0007\u0003_*!\u0019A8\t\u0013\u0011EV!!AA\u0002\u0011M\u0016a\u0001=%aAQ\"i\u0002C@\t\u0007#9\tb#\u0005\u0010\u0012MEq\u0013CN\t?#\u0019\u000bb*\u0005,\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011A\u0011\u0018\t\u0005\u0007\u0003#Y,\u0003\u0003\u0005>\u000e\r%AB(cU\u0016\u001cG\u000f")
public final class Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final T1 t1;
    private final T2 t2;
    private final T3 t3;
    private final T4 t4;
    private final T5 t5;
    private final T6 t6;
    private final T7 t7;
    private final T8 t8;
    private final T9 t9;
    private final T10 t10;
    private final T11 t11;
    private final T12 t12;
    private final scala.Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> toScala;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Option<scala.Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> unapply(Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> x$0) {
        return Tuple12$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
        return Tuple12$.MODULE$.apply(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
        return Tuple12$.MODULE$.create(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public T1 t1() {
        return this.t1;
    }

    public T2 t2() {
        return this.t2;
    }

    public T3 t3() {
        return this.t3;
    }

    public T4 t4() {
        return this.t4;
    }

    public T5 t5() {
        return this.t5;
    }

    public T6 t6() {
        return this.t6;
    }

    public T7 t7() {
        return this.t7;
    }

    public T8 t8() {
        return this.t8;
    }

    public T9 t9() {
        return this.t9;
    }

    public T10 t10() {
        return this.t10;
    }

    public T11 t11() {
        return this.t11;
    }

    public T12 t12() {
        return this.t12;
    }

    public scala.Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> toScala() {
        return this.toScala;
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> copy(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
        return new Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T1 copy$default$1() {
        return this.t1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T10 copy$default$10() {
        return this.t10();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T11 copy$default$11() {
        return this.t11();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T12 copy$default$12() {
        return this.t12();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T2 copy$default$2() {
        return this.t2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T3 copy$default$3() {
        return this.t3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T4 copy$default$4() {
        return this.t4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T5 copy$default$5() {
        return this.t5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T6 copy$default$6() {
        return this.t6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T7 copy$default$7() {
        return this.t7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T8 copy$default$8() {
        return this.t8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> T9 copy$default$9() {
        return this.t9();
    }

    @Override
    public String productPrefix() {
        return "Tuple12";
    }

    @Override
    public int productArity() {
        return 12;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.t1();
            }
            case 1: {
                return this.t2();
            }
            case 2: {
                return this.t3();
            }
            case 3: {
                return this.t4();
            }
            case 4: {
                return this.t5();
            }
            case 5: {
                return this.t6();
            }
            case 6: {
                return this.t7();
            }
            case 7: {
                return this.t8();
            }
            case 8: {
                return this.t9();
            }
            case 9: {
                return this.t10();
            }
            case 10: {
                return this.t11();
            }
            case 11: {
                return this.t12();
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
        return x$1 instanceof Tuple12;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "t1";
            }
            case 1: {
                return "t2";
            }
            case 2: {
                return "t3";
            }
            case 3: {
                return "t4";
            }
            case 4: {
                return "t5";
            }
            case 5: {
                return "t6";
            }
            case 6: {
                return "t7";
            }
            case 7: {
                return "t8";
            }
            case 8: {
                return "t9";
            }
            case 9: {
                return "t10";
            }
            case 10: {
                return "t11";
            }
            case 11: {
                return "t12";
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
        if (!(object instanceof Tuple12)) return false;
        boolean bl = true;
        if (!bl) return false;
        Tuple12 tuple12 = (Tuple12)x$1;
        if (!BoxesRunTime.equals(this.t1(), tuple12.t1())) return false;
        if (!BoxesRunTime.equals(this.t2(), tuple12.t2())) return false;
        if (!BoxesRunTime.equals(this.t3(), tuple12.t3())) return false;
        if (!BoxesRunTime.equals(this.t4(), tuple12.t4())) return false;
        if (!BoxesRunTime.equals(this.t5(), tuple12.t5())) return false;
        if (!BoxesRunTime.equals(this.t6(), tuple12.t6())) return false;
        if (!BoxesRunTime.equals(this.t7(), tuple12.t7())) return false;
        if (!BoxesRunTime.equals(this.t8(), tuple12.t8())) return false;
        if (!BoxesRunTime.equals(this.t9(), tuple12.t9())) return false;
        if (!BoxesRunTime.equals(this.t10(), tuple12.t10())) return false;
        if (!BoxesRunTime.equals(this.t11(), tuple12.t11())) return false;
        if (!BoxesRunTime.equals(this.t12(), tuple12.t12())) return false;
        return true;
    }

    public Tuple12(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
        this.t9 = t9;
        this.t10 = t10;
        this.t11 = t11;
        this.t12 = t12;
        Product.$init$(this);
        this.toScala = new scala.Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
    }
}

