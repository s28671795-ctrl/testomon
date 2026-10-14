/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.KeyManagerConfig;
import com.typesafe.sslconfig.ssl.SSLConfigSettings$;
import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.SSLLooseConfig;
import com.typesafe.sslconfig.ssl.SSLParametersConfig;
import com.typesafe.sslconfig.ssl.TrustManagerConfig;
import java.net.URL;
import java.security.SecureRandom;
import javax.net.ssl.HostnameVerifier;
import scala.Option;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\rea\u0001B'O\u0005]C\u0001B\u0018\u0001\u0003\u0006\u0004%\ta\u0018\u0005\tG\u0002\u0011\t\u0011)A\u0005A\"AA\r\u0001BC\u0002\u0013\u0005Q\r\u0003\u0005r\u0001\t\u0005\t\u0015!\u0003g\u0011!\u0011\bA!b\u0001\n\u0003\u0019\b\u0002C<\u0001\u0005\u0003\u0005\u000b\u0011\u0002;\t\u0011a\u0004!Q1A\u0005\u0002eD\u0011\"a\u0006\u0001\u0005\u0003\u0005\u000b\u0011\u0002>\t\u0015\u0005e\u0001A!b\u0001\n\u0003\tY\u0002\u0003\u0006\u0002\"\u0001\u0011\t\u0011)A\u0005\u0003;A!\"a\t\u0001\u0005\u000b\u0007I\u0011AA\u000e\u0011)\t)\u0003\u0001B\u0001B\u0003%\u0011Q\u0004\u0005\u000b\u0003O\u0001!Q1A\u0005\u0002\u0005%\u0002BCA \u0001\t\u0005\t\u0015!\u0003\u0002 !Q\u00111\t\u0001\u0003\u0006\u0004%\t!!\u0012\t\u0015\u0005=\u0003A!A!\u0002\u0013\t9\u0005\u0003\u0006\u0002R\u0001\u0011)\u0019!C\u0001\u0003'B!\"a\u0017\u0001\u0005\u0003\u0005\u000b\u0011BA+\u0011)\ti\u0006\u0001BC\u0002\u0013\u0005\u0011q\f\u0005\u000b\u0003O\u0002!\u0011!Q\u0001\n\u0005\u0005\u0004BCA5\u0001\t\u0015\r\u0011\"\u0001\u0002l!Q\u0011Q\u0010\u0001\u0003\u0002\u0003\u0006I!!\u001c\t\u0015\u0005]\u0005A!b\u0001\n\u0003\tI\n\u0003\u0006\u0002*\u0002\u0011\t\u0011)A\u0005\u00037C!\"a+\u0001\u0005\u000b\u0007I\u0011AAW\u0011)\t)\f\u0001B\u0001B\u0003%\u0011q\u0016\u0005\u000b\u0003o\u0003!Q1A\u0005\u0002\u0005e\u0006BCAa\u0001\t\u0005\t\u0015!\u0003\u0002<\"A\u00111\u0019\u0001\u0005\u0002A\u000b)\rC\u0004\u0002n\u0002!\t!a<\t\u000f\u0005U\b\u0001\"\u0001\u0002x\"9\u00111 \u0001\u0005\u0002\u0005u\bb\u0002B\u0001\u0001\u0011\u0005!1\u0001\u0005\b\u0005\u000f\u0001A\u0011\u0001B\u0005\u0011\u001d\u0011i\u0001\u0001C\u0001\u0005\u001fAqA!\b\u0001\t\u0003\u0011y\u0002C\u0004\u0003$\u0001!\tA!\n\t\u000f\t%\u0002\u0001\"\u0001\u0003,!9!q\u0006\u0001\u0005\u0002\tE\u0002b\u0002B\u001b\u0001\u0011\u0005!q\u0007\u0005\b\u0005w\u0001A\u0011\u0001B\u001f\u0011\u001d\u0011\t\u0005\u0001C\u0001\u0005\u0007BqAa\u0012\u0001\t\u0013\u0011I\u0005C\u0005\u0003p\u0001\t\n\u0011\"\u0003\u0003r!I!q\u0011\u0001\u0012\u0002\u0013%!\u0011\u0012\u0005\n\u0005\u001b\u0003\u0011\u0013!C\u0005\u0005\u001fC\u0011Ba%\u0001#\u0003%IA!&\t\u0013\te\u0005!%A\u0005\n\tU\u0005\"\u0003BN\u0001E\u0005I\u0011\u0002BO\u0011%\u0011\t\fAI\u0001\n\u0013\u0011\u0019\fC\u0005\u00038\u0002\t\n\u0011\"\u0003\u0003:\"I!Q\u0018\u0001\u0012\u0002\u0013%!q\u0018\u0005\n\u0005\u0007\u0004\u0011\u0013!C\u0005\u0005\u000bD\u0011B!3\u0001#\u0003%IAa3\t\u0013\t=\u0007!%A\u0005\n\tE\u0007\"\u0003Bk\u0001E\u0005I\u0011\u0002Bl\u0011\u001d\u0011Y\u000e\u0001C!\u0005;<qAa8O\u0011\u0003\u0011\tO\u0002\u0004N\u001d\"\u0005!1\u001d\u0005\b\u0003\u0007\\D\u0011\u0001Bs\u0011\u001d\u00119o\u000fC\u0001\u0005SDqAa;<\t\u0003\u0011I\u000f\u0003\u0006\u0003nn\n\n\u0011\"\u0001Q\u0005\u001fC!Ba<<#\u0003%\t\u0001\u0015B`\u0011)\u0011\tpOI\u0001\n\u0003\u0001&\u0011\u000f\u0005\u000b\u0005g\\\u0014\u0013!C\u0001!\n\u0015\u0007B\u0003B{wE\u0005I\u0011\u0001)\u0003\u0016\"Q!q_\u001e\u0012\u0002\u0013\u0005\u0001K!&\t\u0015\te8(%A\u0005\u0002A\u0013Y\u0010\u0003\u0006\u0003\u0000n\n\n\u0011\"\u0001Q\u0005#D!b!\u0001<#\u0003%\t\u0001\u0015BZ\u0011)\u0019\u0019aOI\u0001\n\u0003\u0001&q\u001b\u0005\u000b\u0007\u000bY\u0014\u0013!C\u0001!\u000e\u001d\u0001BCB\nwE\u0005I\u0011\u0001)\u0003L\"Q1QC\u001e\u0012\u0002\u0013\u0005\u0001K!#\t\u0015\r]1(%A\u0005\u0002A\u0013ILA\tT'2\u001buN\u001c4jON+G\u000f^5oONT!a\u0014)\u0002\u0007M\u001cHN\u0003\u0002R%\u0006I1o\u001d7d_:4\u0017n\u001a\u0006\u0003'R\u000b\u0001\u0002^=qKN\fg-\u001a\u0006\u0002+\u0006\u00191m\\7\u0004\u0001M\u0011\u0001\u0001\u0017\t\u00033rk\u0011A\u0017\u0006\u00027\u0006)1oY1mC&\u0011QL\u0017\u0002\u0007\u0003:L(+\u001a4\u0002\u000f\u0011,g-Y;miV\t\u0001\r\u0005\u0002ZC&\u0011!M\u0017\u0002\b\u0005>|G.Z1o\u0003!!WMZ1vYR\u0004\u0013\u0001\u00039s_R|7m\u001c7\u0016\u0003\u0019\u0004\"a\u001a8\u000f\u0005!d\u0007CA5[\u001b\u0005Q'BA6W\u0003\u0019a$o\\8u}%\u0011QNW\u0001\u0007!J,G-\u001a4\n\u0005=\u0004(AB*ue&twM\u0003\u0002n5\u0006I\u0001O]8u_\u000e|G\u000eI\u0001\u0010G\",7m\u001b*fm>\u001c\u0017\r^5p]V\tA\u000fE\u0002Zk\u0002L!A\u001e.\u0003\r=\u0003H/[8o\u0003A\u0019\u0007.Z2l%\u00164xnY1uS>t\u0007%A\bsKZ|7-\u0019;j_:d\u0015n\u001d;t+\u0005Q\bcA-vwB)A0a\u0001\u0002\b5\tQP\u0003\u0002\u007f\u007f\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0004\u0003\u0003Q\u0016AC2pY2,7\r^5p]&\u0019\u0011QA?\u0003\u0007M+\u0017\u000f\u0005\u0003\u0002\n\u0005MQBAA\u0006\u0015\u0011\ti!a\u0004\u0002\u00079,GO\u0003\u0002\u0002\u0012\u0005!!.\u0019<b\u0013\u0011\t)\"a\u0003\u0003\u0007U\u0013F*\u0001\tsKZ|7-\u0019;j_:d\u0015n\u001d;tA\u0005\u0019RM\\1cY\u0016$7)\u001b9iKJ\u001cV/\u001b;fgV\u0011\u0011Q\u0004\t\u00053V\fy\u0002\u0005\u0003}\u0003\u00071\u0017\u0001F3oC\ndW\rZ\"ja\",'oU;ji\u0016\u001c\b%\u0001\tf]\u0006\u0014G.\u001a3Qe>$xnY8mg\u0006\tRM\\1cY\u0016$\u0007K]8u_\u000e|Gn\u001d\u0011\u0002+\u0011L7/\u00192mK\u0012\\U-_!mO>\u0014\u0018\u000e\u001e5ngV\u0011\u0011q\u0004\u0015\f\u001b\u00055\u00121GA\u001b\u0003s\tY\u0004E\u0002Z\u0003_I1!!\r[\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\t9$A\u0007o_R\u0004s\u000e]3sCRLg/Z\u0001\u0006g&t7-Z\u0011\u0003\u0003{\tQ\u0001\r\u00186]A\na\u0003Z5tC\ndW\rZ&fs\u0006cwm\u001c:ji\"l7\u000f\t\u0015\f\u001d\u00055\u00121GA\u001b\u0003s\tY$A\ntg2\u0004\u0016M]1nKR,'o]\"p]\u001aLw-\u0006\u0002\u0002HA!\u0011\u0011JA&\u001b\u0005q\u0015bAA'\u001d\n\u00192k\u0015'QCJ\fW.\u001a;feN\u001cuN\u001c4jO\u0006!2o\u001d7QCJ\fW.\u001a;feN\u001cuN\u001c4jO\u0002\n\u0001c[3z\u001b\u0006t\u0017mZ3s\u0007>tg-[4\u0016\u0005\u0005U\u0003\u0003BA%\u0003/J1!!\u0017O\u0005AYU-_'b]\u0006<WM]\"p]\u001aLw-A\tlKfl\u0015M\\1hKJ\u001cuN\u001c4jO\u0002\n!\u0003\u001e:vgRl\u0015M\\1hKJ\u001cuN\u001c4jOV\u0011\u0011\u0011\r\t\u0005\u0003\u0013\n\u0019'C\u0002\u0002f9\u0013!\u0003\u0016:vgRl\u0015M\\1hKJ\u001cuN\u001c4jO\u0006\u0019BO];ti6\u000bg.Y4fe\u000e{gNZ5hA\u0005)\u0002n\\:u]\u0006lWMV3sS\u001aLWM]\"mCN\u001cXCAA7a\u0011\ty'!\u001f\u0011\u000b\u001d\f\t(!\u001e\n\u0007\u0005M\u0004OA\u0003DY\u0006\u001c8\u000f\u0005\u0003\u0002x\u0005eD\u0002\u0001\u0003\f\u0003w2\u0012\u0011!A\u0001\u0006\u0003\tyHA\u0002`IE\na\u0003[8ti:\fW.\u001a,fe&4\u0017.\u001a:DY\u0006\u001c8\u000fI\t\u0005\u0003\u0003\u000b9\tE\u0002Z\u0003\u0007K1!!\"[\u0005\u001dqu\u000e\u001e5j]\u001e\u0004B!!#\u0002\u00146\u0011\u00111\u0012\u0006\u0004\u001f\u00065%\u0002BA\u0007\u0003\u001fS!!!%\u0002\u000b)\fg/\u0019=\n\t\u0005U\u00151\u0012\u0002\u0011\u0011>\u001cHO\\1nKZ+'/\u001b4jKJ\fAb]3dkJ,'+\u00198e_6,\"!a'\u0011\te+\u0018Q\u0014\t\u0005\u0003?\u000b)+\u0004\u0002\u0002\"*!\u00111UA\b\u0003!\u0019XmY;sSRL\u0018\u0002BAT\u0003C\u0013AbU3dkJ,'+\u00198e_6\fQb]3dkJ,'+\u00198e_6\u0004\u0013!\u00023fEV<WCAAX!\u0011\tI%!-\n\u0007\u0005MfJ\u0001\bT'2#UMY;h\u0007>tg-[4\u0002\r\u0011,'-^4!\u0003\u0015awn\\:f+\t\tY\f\u0005\u0003\u0002J\u0005u\u0016bAA`\u001d\nq1k\u0015'M_>\u001cXmQ8oM&<\u0017A\u00027p_N,\u0007%\u0001\u0004=S:LGO\u0010\u000b\u001f\u0003\u000f\fI-a3\u0002N\u0006=\u0017\u0011[Aj\u0003+\f9.!7\u0002\\\u0006u\u0017q]Au\u0003W\u00042!!\u0013\u0001\u0011\u001dqV\u0004%AA\u0002\u0001Dq\u0001Z\u000f\u0011\u0002\u0003\u0007a\rC\u0004s;A\u0005\t\u0019\u0001;\t\u000fal\u0002\u0013!a\u0001u\"I\u0011\u0011D\u000f\u0011\u0002\u0003\u0007\u0011Q\u0004\u0005\n\u0003Gi\u0002\u0013!a\u0001\u0003;A\u0011\"a\n\u001e!\u0003\u0005\r!a\b\t\u0013\u0005\rS\u0004%AA\u0002\u0005\u001d\u0003\"CA);A\u0005\t\u0019AA+\u0011%\ti&\bI\u0001\u0002\u0004\t\t\u0007C\u0005\u0002ju\u0001\n\u00111\u0001\u0002`B\"\u0011\u0011]As!\u00159\u0017\u0011OAr!\u0011\t9(!:\u0005\u0019\u0005m\u0014Q\\A\u0001\u0002\u0003\u0015\t!a \t\u0013\u0005]U\u0004%AA\u0002\u0005m\u0005\"CAV;A\u0005\t\u0019AAX\u0011%\t9,\bI\u0001\u0002\u0004\tY,A\nxSRD7\t[3dWJ+go\\2bi&|g\u000e\u0006\u0003\u0002H\u0006E\bBBAz=\u0001\u0007A/A\u0003wC2,X-A\u0005xSRDG)\u001a2vOR!\u0011qYA}\u0011\u001d\t\u0019p\ba\u0001\u0003_\u000b1b^5uQ\u0012+g-Y;miR!\u0011qYA\u0000\u0011\u0019\t\u0019\u0010\ta\u0001A\u00069r/\u001b;i\u000b:\f'\r\\3e\u0007&\u0004\b.\u001a:Tk&$Xm\u001d\u000b\u0005\u0003\u000f\u0014)\u0001C\u0004\u0002t\u0006\u0002\r!!\b\u0002)]LG\u000f[#oC\ndW\r\u001a)s_R|7m\u001c7t)\u0011\t9Ma\u0003\t\u000f\u0005M(\u00051\u0001\u0002\u001e\u0005Ir/\u001b;i\u0011>\u001cHO\\1nKZ+'/\u001b4jKJ\u001cE.Y:t)\u0011\t9M!\u0005\t\u000f\u0005M8\u00051\u0001\u0003\u0014A\"!Q\u0003B\r!\u00159\u0017\u0011\u000fB\f!\u0011\t9H!\u0007\u0005\u0019\tm!\u0011CA\u0001\u0002\u0003\u0015\t!a \u0003\u0007}##'\u0001\u000bxSRD7*Z=NC:\fw-\u001a:D_:4\u0017n\u001a\u000b\u0005\u0003\u000f\u0014\t\u0003C\u0004\u0002t\u0012\u0002\r!!\u0016\u0002\u0013]LG\u000f\u001b'p_N,G\u0003BAd\u0005OAq!a=&\u0001\u0004\tY,\u0001\u0007xSRD\u0007K]8u_\u000e|G\u000e\u0006\u0003\u0002H\n5\u0002BBAzM\u0001\u0007a-A\nxSRD'+\u001a<pG\u0006$\u0018n\u001c8MSN$8\u000f\u0006\u0003\u0002H\nM\u0002BBAzO\u0001\u0007!0\u0001\txSRD7+Z2ve\u0016\u0014\u0016M\u001c3p[R!\u0011q\u0019B\u001d\u0011\u001d\t\u0019\u0010\u000ba\u0001\u00037\u000bqc^5uQN\u001bH\u000eU1sC6,G/\u001a:t\u0007>tg-[4\u0015\t\u0005\u001d'q\b\u0005\b\u0003gL\u0003\u0019AA$\u0003Y9\u0018\u000e\u001e5UeV\u001cH/T1oC\u001e,'oQ8oM&<G\u0003BAd\u0005\u000bBq!a=+\u0001\u0004\t\t'\u0001\u0003d_BLH\u0003HAd\u0005\u0017\u0012iEa\u0014\u0003R\tM#Q\u000bB1\u0005G\u0012)Ga\u001a\u0003j\t-$Q\u000e\u0005\be.\u0002\n\u00111\u0001u\u0011%\tYk\u000bI\u0001\u0002\u0004\ty\u000bC\u0004_WA\u0005\t\u0019\u00011\t\u0013\u0005e1\u0006%AA\u0002\u0005u\u0001\"CA\u0012WA\u0005\t\u0019AA\u000f\u0011%\tIg\u000bI\u0001\u0002\u0004\u00119\u0006\r\u0003\u0003Z\tu\u0003#B4\u0002r\tm\u0003\u0003BA<\u0005;\"ABa\u0018\u0003V\u0005\u0005\t\u0011!B\u0001\u0003\u007f\u00121a\u0018\u00134\u0011%\t\tf\u000bI\u0001\u0002\u0004\t)\u0006C\u0005\u00028.\u0002\n\u00111\u0001\u0002<\"9Am\u000bI\u0001\u0002\u00041\u0007b\u0002=,!\u0003\u0005\rA\u001f\u0005\n\u0003/[\u0003\u0013!a\u0001\u00037C\u0011\"a\u0011,!\u0003\u0005\r!a\u0012\t\u0013\u0005u3\u0006%AA\u0002\u0005\u0005\u0014AD2paf$C-\u001a4bk2$H%M\u000b\u0003\u0005gR3\u0001\u001eB;W\t\u00119\b\u0005\u0003\u0003z\t\rUB\u0001B>\u0015\u0011\u0011iHa \u0002\u0013Ut7\r[3dW\u0016$'b\u0001BA5\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\t\u0015%1\u0010\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u000b\u0003\u0005\u0017SC!a,\u0003v\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTC\u0001BIU\r\u0001'QO\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00135+\t\u00119J\u000b\u0003\u0002\u001e\tU\u0014AD2paf$C-\u001a4bk2$H%N\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+\t\u0011y\n\r\u0003\u0003\"\n=&\u0006\u0002BR\u0005k\u0002bA!*\u0003,\n5VB\u0001BT\u0015\u0011\u0011I+a\u0004\u0002\t1\fgnZ\u0005\u0005\u0003g\u00129\u000b\u0005\u0003\u0002x\t=Fa\u0003B0c\u0005\u0005\t\u0011!B\u0001\u0003\u007f\nabY8qs\u0012\"WMZ1vYR$s'\u0006\u0002\u00036*\"\u0011Q\u000bB;\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIa*\"Aa/+\t\u0005m&QO\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u0013:+\t\u0011\tMK\u0002g\u0005k\nqbY8qs\u0012\"WMZ1vYR$\u0013\u0007M\u000b\u0003\u0005\u000fT3A\u001fB;\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\nTC\u0001BgU\u0011\tYJ!\u001e\u0002\u001f\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cI*\"Aa5+\t\u0005\u001d#QO\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132gU\u0011!\u0011\u001c\u0016\u0005\u0003C\u0012)(\u0001\u0005u_N#(/\u001b8h)\u00051\u0017!E*T\u0019\u000e{gNZ5h'\u0016$H/\u001b8hgB\u0019\u0011\u0011J\u001e\u0014\u0005mBFC\u0001Bq\u0003\u0015\t\u0007\u000f\u001d7z)\t\t9-A\u0006hKRLen\u001d;b]\u000e,\u0017a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEM\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u001a\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00135\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%k\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIY\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012:TC\u0001B\u007fU\u0011\tyB!\u001e\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00139\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%s\u0005aB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE\u0002\u0014\u0001\b\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013'M\u000b\u0003\u0007\u0013\u0001Daa\u0003\u0004\u0012)\"1Q\u0002B;!\u0019\u0011)Ka+\u0004\u0010A!\u0011qOB\t\t-\tY(SA\u0001\u0002\u0003\u0015\t!a \u00029\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132e\u0005aB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE\u001a\u0014\u0001\b\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013\u0007\u000e")
public final class SSLConfigSettings {
    private final boolean default;
    private final String protocol;
    private final Option<Object> checkRevocation;
    private final Option<Seq<URL>> revocationLists;
    private final Option<Seq<String>> enabledCipherSuites;
    private final Option<Seq<String>> enabledProtocols;
    private final Seq<String> disabledKeyAlgorithms;
    private final SSLParametersConfig sslParametersConfig;
    private final KeyManagerConfig keyManagerConfig;
    private final TrustManagerConfig trustManagerConfig;
    private final Class<? extends HostnameVerifier> hostnameVerifierClass;
    private final Option<SecureRandom> secureRandom;
    private final SSLDebugConfig debug;
    private final SSLLooseConfig loose;

    public static SSLConfigSettings getInstance() {
        return SSLConfigSettings$.MODULE$.getInstance();
    }

    public static SSLConfigSettings apply() {
        return SSLConfigSettings$.MODULE$.apply();
    }

    public boolean default() {
        return this.default;
    }

    public String protocol() {
        return this.protocol;
    }

    public Option<Object> checkRevocation() {
        return this.checkRevocation;
    }

    public Option<Seq<URL>> revocationLists() {
        return this.revocationLists;
    }

    public Option<Seq<String>> enabledCipherSuites() {
        return this.enabledCipherSuites;
    }

    public Option<Seq<String>> enabledProtocols() {
        return this.enabledProtocols;
    }

    public Seq<String> disabledKeyAlgorithms() {
        return this.disabledKeyAlgorithms;
    }

    public SSLParametersConfig sslParametersConfig() {
        return this.sslParametersConfig;
    }

    public KeyManagerConfig keyManagerConfig() {
        return this.keyManagerConfig;
    }

    public TrustManagerConfig trustManagerConfig() {
        return this.trustManagerConfig;
    }

    public Class<? extends HostnameVerifier> hostnameVerifierClass() {
        return this.hostnameVerifierClass;
    }

    public Option<SecureRandom> secureRandom() {
        return this.secureRandom;
    }

    public SSLDebugConfig debug() {
        return this.debug;
    }

    public SSLLooseConfig loose() {
        return this.loose;
    }

    public SSLConfigSettings withCheckRevocation(Option<Object> value) {
        return this.copy(value, this.copy$default$2(), this.copy$default$3(), this.copy$default$4(), this.copy$default$5(), this.copy$default$6(), this.copy$default$7(), this.copy$default$8(), this.copy$default$9(), this.copy$default$10(), this.copy$default$11(), this.copy$default$12(), this.copy$default$13());
    }

    public SSLConfigSettings withDebug(SSLDebugConfig value) {
        SSLDebugConfig x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$3();
        Option<Seq<String>> x$4 = this.copy$default$4();
        Option<Seq<String>> x$5 = this.copy$default$5();
        Class<? extends HostnameVerifier> x$6 = this.copy$default$6();
        KeyManagerConfig x$7 = this.copy$default$7();
        SSLLooseConfig x$8 = this.copy$default$8();
        String x$9 = this.copy$default$9();
        Option<Seq<URL>> x$10 = this.copy$default$10();
        Option<SecureRandom> x$11 = this.copy$default$11();
        SSLParametersConfig x$12 = this.copy$default$12();
        TrustManagerConfig x$13 = this.copy$default$13();
        return this.copy(x$2, x$1, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13);
    }

    public SSLConfigSettings withDefault(boolean value) {
        boolean x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        SSLDebugConfig x$3 = this.copy$default$2();
        Option<Seq<String>> x$4 = this.copy$default$4();
        Option<Seq<String>> x$5 = this.copy$default$5();
        Class<? extends HostnameVerifier> x$6 = this.copy$default$6();
        KeyManagerConfig x$7 = this.copy$default$7();
        SSLLooseConfig x$8 = this.copy$default$8();
        String x$9 = this.copy$default$9();
        Option<Seq<URL>> x$10 = this.copy$default$10();
        Option<SecureRandom> x$11 = this.copy$default$11();
        SSLParametersConfig x$12 = this.copy$default$12();
        TrustManagerConfig x$13 = this.copy$default$13();
        return this.copy(x$2, x$3, x$1, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13);
    }

    public SSLConfigSettings withEnabledCipherSuites(Option<Seq<String>> value) {
        Option<Seq<String>> x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        SSLDebugConfig x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<Seq<String>> x$5 = this.copy$default$5();
        Class<? extends HostnameVerifier> x$6 = this.copy$default$6();
        KeyManagerConfig x$7 = this.copy$default$7();
        SSLLooseConfig x$8 = this.copy$default$8();
        String x$9 = this.copy$default$9();
        Option<Seq<URL>> x$10 = this.copy$default$10();
        Option<SecureRandom> x$11 = this.copy$default$11();
        SSLParametersConfig x$12 = this.copy$default$12();
        TrustManagerConfig x$13 = this.copy$default$13();
        return this.copy(x$2, x$3, x$4, x$1, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13);
    }

    public SSLConfigSettings withEnabledProtocols(Option<Seq<String>> value) {
        Option<Seq<String>> x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        SSLDebugConfig x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<Seq<String>> x$5 = this.copy$default$4();
        Class<? extends HostnameVerifier> x$6 = this.copy$default$6();
        KeyManagerConfig x$7 = this.copy$default$7();
        SSLLooseConfig x$8 = this.copy$default$8();
        String x$9 = this.copy$default$9();
        Option<Seq<URL>> x$10 = this.copy$default$10();
        Option<SecureRandom> x$11 = this.copy$default$11();
        SSLParametersConfig x$12 = this.copy$default$12();
        TrustManagerConfig x$13 = this.copy$default$13();
        return this.copy(x$2, x$3, x$4, x$5, x$1, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13);
    }

    public SSLConfigSettings withHostnameVerifierClass(Class<? extends HostnameVerifier> value) {
        Class<? extends HostnameVerifier> x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        SSLDebugConfig x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<Seq<String>> x$5 = this.copy$default$4();
        Option<Seq<String>> x$6 = this.copy$default$5();
        KeyManagerConfig x$7 = this.copy$default$7();
        SSLLooseConfig x$8 = this.copy$default$8();
        String x$9 = this.copy$default$9();
        Option<Seq<URL>> x$10 = this.copy$default$10();
        Option<SecureRandom> x$11 = this.copy$default$11();
        SSLParametersConfig x$12 = this.copy$default$12();
        TrustManagerConfig x$13 = this.copy$default$13();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1, x$7, x$8, x$9, x$10, x$11, x$12, x$13);
    }

    public SSLConfigSettings withKeyManagerConfig(KeyManagerConfig value) {
        KeyManagerConfig x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        SSLDebugConfig x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<Seq<String>> x$5 = this.copy$default$4();
        Option<Seq<String>> x$6 = this.copy$default$5();
        Class<? extends HostnameVerifier> x$7 = this.copy$default$6();
        SSLLooseConfig x$8 = this.copy$default$8();
        String x$9 = this.copy$default$9();
        Option<Seq<URL>> x$10 = this.copy$default$10();
        Option<SecureRandom> x$11 = this.copy$default$11();
        SSLParametersConfig x$12 = this.copy$default$12();
        TrustManagerConfig x$13 = this.copy$default$13();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$1, x$8, x$9, x$10, x$11, x$12, x$13);
    }

    public SSLConfigSettings withLoose(SSLLooseConfig value) {
        SSLLooseConfig x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        SSLDebugConfig x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<Seq<String>> x$5 = this.copy$default$4();
        Option<Seq<String>> x$6 = this.copy$default$5();
        Class<? extends HostnameVerifier> x$7 = this.copy$default$6();
        KeyManagerConfig x$8 = this.copy$default$7();
        String x$9 = this.copy$default$9();
        Option<Seq<URL>> x$10 = this.copy$default$10();
        Option<SecureRandom> x$11 = this.copy$default$11();
        SSLParametersConfig x$12 = this.copy$default$12();
        TrustManagerConfig x$13 = this.copy$default$13();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$1, x$9, x$10, x$11, x$12, x$13);
    }

    public SSLConfigSettings withProtocol(String value) {
        String x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        SSLDebugConfig x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<Seq<String>> x$5 = this.copy$default$4();
        Option<Seq<String>> x$6 = this.copy$default$5();
        Class<? extends HostnameVerifier> x$7 = this.copy$default$6();
        KeyManagerConfig x$8 = this.copy$default$7();
        SSLLooseConfig x$9 = this.copy$default$8();
        Option<Seq<URL>> x$10 = this.copy$default$10();
        Option<SecureRandom> x$11 = this.copy$default$11();
        SSLParametersConfig x$12 = this.copy$default$12();
        TrustManagerConfig x$13 = this.copy$default$13();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$1, x$10, x$11, x$12, x$13);
    }

    public SSLConfigSettings withRevocationLists(Option<Seq<URL>> value) {
        Option<Seq<URL>> x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        SSLDebugConfig x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<Seq<String>> x$5 = this.copy$default$4();
        Option<Seq<String>> x$6 = this.copy$default$5();
        Class<? extends HostnameVerifier> x$7 = this.copy$default$6();
        KeyManagerConfig x$8 = this.copy$default$7();
        SSLLooseConfig x$9 = this.copy$default$8();
        String x$10 = this.copy$default$9();
        Option<SecureRandom> x$11 = this.copy$default$11();
        SSLParametersConfig x$12 = this.copy$default$12();
        TrustManagerConfig x$13 = this.copy$default$13();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$1, x$11, x$12, x$13);
    }

    public SSLConfigSettings withSecureRandom(Option<SecureRandom> value) {
        Option<SecureRandom> x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        SSLDebugConfig x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<Seq<String>> x$5 = this.copy$default$4();
        Option<Seq<String>> x$6 = this.copy$default$5();
        Class<? extends HostnameVerifier> x$7 = this.copy$default$6();
        KeyManagerConfig x$8 = this.copy$default$7();
        SSLLooseConfig x$9 = this.copy$default$8();
        String x$10 = this.copy$default$9();
        Option<Seq<URL>> x$11 = this.copy$default$10();
        SSLParametersConfig x$12 = this.copy$default$12();
        TrustManagerConfig x$13 = this.copy$default$13();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$1, x$12, x$13);
    }

    public SSLConfigSettings withSslParametersConfig(SSLParametersConfig value) {
        SSLParametersConfig x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        SSLDebugConfig x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<Seq<String>> x$5 = this.copy$default$4();
        Option<Seq<String>> x$6 = this.copy$default$5();
        Class<? extends HostnameVerifier> x$7 = this.copy$default$6();
        KeyManagerConfig x$8 = this.copy$default$7();
        SSLLooseConfig x$9 = this.copy$default$8();
        String x$10 = this.copy$default$9();
        Option<Seq<URL>> x$11 = this.copy$default$10();
        Option<SecureRandom> x$12 = this.copy$default$11();
        TrustManagerConfig x$13 = this.copy$default$13();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$1, x$13);
    }

    public SSLConfigSettings withTrustManagerConfig(TrustManagerConfig value) {
        TrustManagerConfig x$1 = value;
        Option<Object> x$2 = this.copy$default$1();
        SSLDebugConfig x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<Seq<String>> x$5 = this.copy$default$4();
        Option<Seq<String>> x$6 = this.copy$default$5();
        Class<? extends HostnameVerifier> x$7 = this.copy$default$6();
        KeyManagerConfig x$8 = this.copy$default$7();
        SSLLooseConfig x$9 = this.copy$default$8();
        String x$10 = this.copy$default$9();
        Option<Seq<URL>> x$11 = this.copy$default$10();
        Option<SecureRandom> x$12 = this.copy$default$11();
        SSLParametersConfig x$13 = this.copy$default$12();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$1);
    }

    private SSLConfigSettings copy(Option<Object> checkRevocation, SSLDebugConfig debug, boolean bl, Option<Seq<String>> enabledCipherSuites, Option<Seq<String>> enabledProtocols, Class<? extends HostnameVerifier> hostnameVerifierClass, KeyManagerConfig keyManagerConfig, SSLLooseConfig loose, String protocol, Option<Seq<URL>> revocationLists, Option<SecureRandom> secureRandom, SSLParametersConfig sslParametersConfig, TrustManagerConfig trustManagerConfig) {
        Option<Object> x$1 = checkRevocation;
        SSLDebugConfig x$2 = debug;
        boolean x$3 = bl;
        Option<Seq<String>> x$4 = enabledCipherSuites;
        Option<Seq<String>> x$5 = enabledProtocols;
        Class<? extends HostnameVerifier> x$6 = hostnameVerifierClass;
        KeyManagerConfig x$7 = keyManagerConfig;
        SSLLooseConfig x$8 = loose;
        String x$9 = protocol;
        Option<Seq<URL>> x$10 = revocationLists;
        Option<SecureRandom> x$11 = secureRandom;
        SSLParametersConfig x$12 = sslParametersConfig;
        TrustManagerConfig x$13 = trustManagerConfig;
        Seq<String> x$14 = SSLConfigSettings$.MODULE$.$lessinit$greater$default$7();
        return new SSLConfigSettings(x$3, x$9, x$1, x$10, x$4, x$5, x$14, x$12, x$7, x$13, x$6, x$11, x$2, x$8);
    }

    private Option<Object> copy$default$1() {
        return this.checkRevocation();
    }

    private SSLDebugConfig copy$default$2() {
        return this.debug();
    }

    private boolean copy$default$3() {
        return this.default();
    }

    private Option<Seq<String>> copy$default$4() {
        return this.enabledCipherSuites();
    }

    private Option<Seq<String>> copy$default$5() {
        return this.enabledProtocols();
    }

    private Class<? extends HostnameVerifier> copy$default$6() {
        return this.hostnameVerifierClass();
    }

    private KeyManagerConfig copy$default$7() {
        return this.keyManagerConfig();
    }

    private SSLLooseConfig copy$default$8() {
        return this.loose();
    }

    private String copy$default$9() {
        return this.protocol();
    }

    private Option<Seq<URL>> copy$default$10() {
        return this.revocationLists();
    }

    private Option<SecureRandom> copy$default$11() {
        return this.secureRandom();
    }

    private SSLParametersConfig copy$default$12() {
        return this.sslParametersConfig();
    }

    private TrustManagerConfig copy$default$13() {
        return this.trustManagerConfig();
    }

    public String toString() {
        return new StringBuilder(23).append("SSLConfig(").append(this.checkRevocation()).append(",").append(this.debug()).append(",").append(this.default()).append(",").append(this.enabledCipherSuites()).append(",").append(this.enabledProtocols()).append(",").append(this.hostnameVerifierClass()).append(",").append(this.keyManagerConfig()).append(",").append(this.loose()).append(",").append(this.protocol()).append(",").append(this.revocationLists()).append(",").append(this.secureRandom()).append(",").append(this.sslParametersConfig()).append(",").append(this.trustManagerConfig()).append(")").toString();
    }

    public SSLConfigSettings(boolean bl, String protocol, Option<Object> checkRevocation, Option<Seq<URL>> revocationLists, Option<Seq<String>> enabledCipherSuites, Option<Seq<String>> enabledProtocols, Seq<String> disabledKeyAlgorithms, SSLParametersConfig sslParametersConfig, KeyManagerConfig keyManagerConfig, TrustManagerConfig trustManagerConfig, Class<? extends HostnameVerifier> hostnameVerifierClass, Option<SecureRandom> secureRandom, SSLDebugConfig debug, SSLLooseConfig loose) {
        this.default = bl;
        this.protocol = protocol;
        this.checkRevocation = checkRevocation;
        this.revocationLists = revocationLists;
        this.enabledCipherSuites = enabledCipherSuites;
        this.enabledProtocols = enabledProtocols;
        this.disabledKeyAlgorithms = disabledKeyAlgorithms;
        this.sslParametersConfig = sslParametersConfig;
        this.keyManagerConfig = keyManagerConfig;
        this.trustManagerConfig = trustManagerConfig;
        this.hostnameVerifierClass = hostnameVerifierClass;
        this.secureRandom = secureRandom;
        this.debug = debug;
        this.loose = loose;
    }
}

