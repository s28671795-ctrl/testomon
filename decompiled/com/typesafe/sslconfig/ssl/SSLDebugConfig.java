/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLDebugConfig$;
import com.typesafe.sslconfig.ssl.SSLDebugHandshakeOptions;
import com.typesafe.sslconfig.ssl.SSLDebugRecordOptions;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tmd\u0001\u0002)R\u0005iC\u0001\"\u0019\u0001\u0003\u0006\u0004%\tA\u0019\u0005\tM\u0002\u0011\t\u0011)A\u0005G\"Aq\r\u0001BC\u0002\u0013\u0005!\r\u0003\u0005s\u0001\t\u0005\t\u0015!\u0003d\u0011!!\bA!b\u0001\n\u0003\u0011\u0007\u0002\u0003<\u0001\u0005\u0003\u0005\u000b\u0011B2\t\u0011a\u0004!Q1A\u0005\u0002eD\u0011\"!\u0002\u0001\u0005\u0003\u0005\u000b\u0011\u0002>\t\u0013\u0005%\u0001A!b\u0001\n\u0003\u0011\u0007\"CA\u0007\u0001\t\u0005\t\u0015!\u0003d\u0011%\t\t\u0002\u0001BC\u0002\u0013\u0005!\rC\u0005\u0002\u0014\u0001\u0011\t\u0011)A\u0005G\"I\u0011Q\u0003\u0001\u0003\u0006\u0004%\tA\u0019\u0005\n\u00033\u0001!\u0011!Q\u0001\n\rD\u0011\"!\b\u0001\u0005\u000b\u0007I\u0011\u00012\t\u0013\u0005\u0005\u0002A!A!\u0002\u0013\u0019\u0007BCA\u0013\u0001\t\u0015\r\u0011\"\u0001\u0002(!Q\u00111\u0007\u0001\u0003\u0002\u0003\u0006I!!\u000b\t\u0013\u0005]\u0002A!b\u0001\n\u0003\u0011\u0007\"CA\u001e\u0001\t\u0005\t\u0015!\u0003d\u0011%\ty\u0004\u0001BC\u0002\u0013\u0005!\rC\u0005\u0002D\u0001\u0011\t\u0011)A\u0005G\"A!\u000b\u0001BC\u0002\u0013\u0005!\rC\u0005\u0002H\u0001\u0011\t\u0011)A\u0005G\"I\u0011\u0011\n\u0001\u0003\u0006\u0004%\tA\u0019\u0005\n\u0003\u0017\u0002!\u0011!Q\u0001\n\rD\u0011\"!\u0014\u0001\u0005\u000b\u0007I\u0011\u00012\t\u0013\u0005=\u0003A!A!\u0002\u0013\u0019\u0007\u0002CA)\u0001\u0011\u00051+a\u0015\t\r\u0005M\u0004\u0001\"\u0001c\u0011\u001d\t)\b\u0001C\u0001\u0003oBq!! \u0001\t\u0003\ty\bC\u0004\u0002\u0006\u0002!\t!a\"\t\u000f\u00055\u0005\u0001\"\u0001\u0002\u0010\"9\u0011Q\u0013\u0001\u0005\u0002\u0005]\u0005bBAO\u0001\u0011\u0005\u0011q\u0014\u0005\b\u0003G\u0003A\u0011AAS\u0011\u001d\tY\u000b\u0001C\u0001\u0003[Cq!a-\u0001\t\u0003\t)\fC\u0004\u0002<\u0002!\t!!0\t\u000f\u0005\r\u0007\u0001\"\u0001\u0002F\"9\u00111\u001a\u0001\u0005\u0002\u00055\u0007bBAi\u0001\u0011\u0005\u00111\u001b\u0005\b\u0003/\u0004A\u0011AAm\u0011\u001d\ti\u000e\u0001C\u0005\u0003?D\u0011\"!@\u0001#\u0003%I!a@\t\u0013\tU\u0001!%A\u0005\n\u0005}\b\"\u0003B\f\u0001E\u0005I\u0011BA\u0000\u0011%\u0011I\u0002AI\u0001\n\u0013\u0011Y\u0002C\u0005\u0003 \u0001\t\n\u0011\"\u0003\u0002\u0000\"I!\u0011\u0005\u0001\u0012\u0002\u0013%\u0011q \u0005\n\u0005G\u0001\u0011\u0013!C\u0005\u0003\u007fD\u0011B!\n\u0001#\u0003%I!a@\t\u0013\t\u001d\u0002!%A\u0005\n\t%\u0002\"\u0003B\u0017\u0001E\u0005I\u0011BA\u0000\u0011%\u0011y\u0003AI\u0001\n\u0013\ty\u0010C\u0005\u00032\u0001\t\n\u0011\"\u0003\u0002\u0000\"I!1\u0007\u0001\u0012\u0002\u0013%\u0011q \u0005\n\u0005k\u0001\u0011\u0013!C\u0005\u0003\u007fDqAa\u000e\u0001\t\u0003\u0012IdB\u0004\u0003REC\tAa\u0015\u0007\rA\u000b\u0006\u0012\u0001B+\u0011\u001d\t\tF\u0010C\u0001\u0005/BqA!\u0017?\t\u0003\u0011Y\u0006C\u0004\u0003^y\"\tAa\u0017\t\u0015\t}c(%A\u0005\u0002M\u000by\u0010\u0003\u0006\u0003by\n\n\u0011\"\u0001T\u0003\u007fD!Ba\u0019?#\u0003%\taUA\u0000\u0011)\u0011)GPI\u0001\n\u0003\u0019&1\u0004\u0005\u000b\u0005Or\u0014\u0013!C\u0001'\u0006}\bB\u0003B5}E\u0005I\u0011A*\u0002\u0000\"Q!1\u000e \u0012\u0002\u0013\u00051+a@\t\u0015\t5d(%A\u0005\u0002M\u000by\u0010\u0003\u0006\u0003py\n\n\u0011\"\u0001T\u0005SA!B!\u001d?#\u0003%\taUA\u0000\u0011)\u0011\u0019HPI\u0001\n\u0003\u0019\u0016q \u0005\u000b\u0005kr\u0014\u0013!C\u0001'\u0006}\bB\u0003B<}E\u0005I\u0011A*\u0002\u0000\"Q!\u0011\u0010 \u0012\u0002\u0013\u00051+a@\u0003\u001dM\u001bF\nR3ck\u001e\u001cuN\u001c4jO*\u0011!kU\u0001\u0004gNd'B\u0001+V\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u0002W/\u0006AA/\u001f9fg\u00064WMC\u0001Y\u0003\r\u0019w.\\\u0002\u0001'\t\u00011\f\u0005\u0002]?6\tQLC\u0001_\u0003\u0015\u00198-\u00197b\u0013\t\u0001WL\u0001\u0004B]f\u0014VMZ\u0001\u0004C2dW#A2\u0011\u0005q#\u0017BA3^\u0005\u001d\u0011un\u001c7fC:\fA!\u00197mA\u0005A1-\u001a:ua\u0006$\b\u000e\u000b\u0004\u0004S2lw\u000e\u001d\t\u00039*L!a[/\u0003\u0015\u0011,\u0007O]3dCR,G-A\u0004nKN\u001c\u0018mZ3\"\u00039\fQB\\8uA=\u0004XM]1uSZ,\u0017!B:j]\u000e,\u0017%A9\u0002\u000bArCG\f\u0019\u0002\u0013\r,'\u000f\u001e9bi\"\u0004\u0003F\u0002\u0003jY6|\u0007/\u0001\u0006eK\u001a\fW\u000f\u001c;dibDc!B5m[>\u0004\u0018a\u00033fM\u0006,H\u000e^2uq\u0002BcAB5m[>\u0004\u0018!\u00035b]\u0012\u001c\b.Y6f+\u0005Q\bc\u0001/|{&\u0011A0\u0018\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005y|X\"A)\n\u0007\u0005\u0005\u0011K\u0001\rT'2#UMY;h\u0011\u0006tGm\u001d5bW\u0016|\u0005\u000f^5p]NDcaB5m[>\u0004\u0018A\u00035b]\u0012\u001c\b.Y6fA!2\u0001\"\u001b7n_B\faa[3zO\u0016t\u0007FB\u0005jY6|\u0007/A\u0004lKf<WM\u001c\u0011)\r)IG.\\8q\u0003)YW-_7b]\u0006<WM]\u0001\fW\u0016LX.\u00198bO\u0016\u0014\b%\u0001\u0003pGN\u0004\bFB\u0007jY6|\u0007/A\u0003pGN\u0004\b\u0005\u000b\u0004\u000fS2lw\u000e]\u0001\ra2,xmZ1cS2LG/\u001f\u0015\u0007\u001f%dWn\u001c9\u0002\u001bAdWoZ4bE&d\u0017\u000e^=!Q\u0019\u0001\u0012\u000e\\7pa\u00061!/Z2pe\u0012,\"!!\u000b\u0011\tq[\u00181\u0006\t\u0004}\u00065\u0012bAA\u0018#\n)2k\u0015'EK\n,xMU3d_J$w\n\u001d;j_:\u001c\bFB\tjY6|\u0007/A\u0004sK\u000e|'\u000f\u001a\u0011)\rIIG.\\8q\u0003\u001d\u0019Xm]:j_:DcaE5m[>\u0004\u0018\u0001C:fgNLwN\u001c\u0011)\rQIG.\\8q\u00031\u0019Xm]:j_:\u001c\u0017m\u00195fQ\u0019)\u0012\u000e\\7pa\u0006i1/Z:tS>t7-Y2iK\u0002BcAF5m[>\u0004\u0018\u0001B:tY\u0002\naa]:mGRD\u0018aB:tY\u000e$\b\u0010I\u0001\riJ,8\u000f^7b]\u0006<WM]\u0001\u000eiJ,8\u000f^7b]\u0006<WM\u001d\u0011\u0002\rqJg.\u001b;?)y\t)&a\u0016\u0002Z\u0005m\u0013QLA0\u0003C\n\u0019'!\u001a\u0002h\u0005%\u00141NA7\u0003_\n\t\b\u0005\u0002\u007f\u0001!9\u0011-\bI\u0001\u0002\u0004\u0019\u0007bB4\u001e!\u0003\u0005\ra\u0019\u0005\biv\u0001\n\u00111\u0001d\u0011\u001dAX\u0004%AA\u0002iD\u0001\"!\u0003\u001e!\u0003\u0005\ra\u0019\u0005\t\u0003#i\u0002\u0013!a\u0001G\"A\u0011QC\u000f\u0011\u0002\u0003\u00071\r\u0003\u0005\u0002\u001eu\u0001\n\u00111\u0001d\u0011%\t)#\bI\u0001\u0002\u0004\tI\u0003\u0003\u0005\u00028u\u0001\n\u00111\u0001d\u0011!\ty$\bI\u0001\u0002\u0004\u0019\u0007b\u0002*\u001e!\u0003\u0005\ra\u0019\u0005\t\u0003\u0013j\u0002\u0013!a\u0001G\"A\u0011QJ\u000f\u0011\u0002\u0003\u00071-A\u0004f]\u0006\u0014G.\u001a3\u0002\u000f]LG\u000f[!mYR!\u0011QKA=\u0011\u0019\tYh\ba\u0001G\u0006)a/\u00197vK\u0006aq/\u001b;i\u0007\u0016\u0014H\u000fU1uQR!\u0011QKAA\u0011\u0019\tY\b\ta\u0001G\"2\u0001%\u001b7n_B\f!c^5uQ\u0012+g-Y;mi\u000e{g\u000e^3yiR!\u0011QKAE\u0011\u0019\tY(\ta\u0001G\"2\u0011%\u001b7n_B\fQb^5uQ\"\u000bg\u000eZ:iC.,G\u0003BA+\u0003#Ca!a\u001f#\u0001\u0004Q\bF\u0002\u0012jY6|\u0007/\u0001\u0006xSRD7*Z=hK:$B!!\u0016\u0002\u001a\"1\u00111P\u0012A\u0002\rDcaI5m[>\u0004\u0018AD<ji\"\\U-_7b]\u0006<WM\u001d\u000b\u0005\u0003+\n\t\u000b\u0003\u0004\u0002|\u0011\u0002\raY\u0001\to&$\bnT2taR!\u0011QKAT\u0011\u0019\tY(\na\u0001G\"2Q%\u001b7n_B\f\u0001c^5uQBcWoZ4bE&d\u0017\u000e^=\u0015\t\u0005U\u0013q\u0016\u0005\u0007\u0003w2\u0003\u0019A2)\r\u0019JG.\\8q\u0003)9\u0018\u000e\u001e5SK\u000e|'\u000f\u001a\u000b\u0005\u0003+\n9\fC\u0004\u0002|\u001d\u0002\r!!\u000b)\r\u001dJG.\\8q\u0003-9\u0018\u000e\u001e5TKN\u001c\u0018n\u001c8\u0015\t\u0005U\u0013q\u0018\u0005\u0007\u0003wB\u0003\u0019A2)\r!JG.\\8q\u0003A9\u0018\u000e\u001e5TKN\u001c\u0018n\u001c8dC\u000eDW\r\u0006\u0003\u0002V\u0005\u001d\u0007BBA>S\u0001\u00071\r\u000b\u0004*S2lw\u000e]\u0001\bo&$\bnU:m)\u0011\t)&a4\t\r\u0005m$\u00061\u0001d\u0003)9\u0018\u000e\u001e5Tg2\u001cG\u000f\u001f\u000b\u0005\u0003+\n)\u000e\u0003\u0004\u0002|-\u0002\raY\u0001\u0011o&$\b\u000e\u0016:vgRl\u0017M\\1hKJ$B!!\u0016\u0002\\\"1\u00111\u0010\u0017A\u0002\r\fAaY8qsRq\u0012QKAq\u0003G\f)/a:\u0002j\u0006-\u0018Q^Ax\u0003c\f\u00190!>\u0002x\u0006e\u00181 \u0005\bC6\u0002\n\u00111\u0001d\u0011\u001d9W\u0006%AA\u0002\rDq\u0001^\u0017\u0011\u0002\u0003\u00071\rC\u0004y[A\u0005\t\u0019\u0001>\t\u0011\u0005%Q\u0006%AA\u0002\rD\u0001\"!\u0005.!\u0003\u0005\ra\u0019\u0005\t\u0003+i\u0003\u0013!a\u0001G\"A\u0011QD\u0017\u0011\u0002\u0003\u00071\rC\u0005\u0002&5\u0002\n\u00111\u0001\u0002*!A\u0011qG\u0017\u0011\u0002\u0003\u00071\r\u0003\u0005\u0002@5\u0002\n\u00111\u0001d\u0011\u001d\u0011V\u0006%AA\u0002\rD\u0001\"!\u0013.!\u0003\u0005\ra\u0019\u0005\t\u0003\u001bj\u0003\u0013!a\u0001G\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTC\u0001B\u0001U\r\u0019'1A\u0016\u0003\u0005\u000b\u0001BAa\u0002\u0003\u00125\u0011!\u0011\u0002\u0006\u0005\u0005\u0017\u0011i!A\u0005v]\u000eDWmY6fI*\u0019!qB/\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003\u0014\t%!!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012\u0014AD2paf$C-\u001a4bk2$HeM\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00135+\t\u0011iBK\u0002{\u0005\u0007\tabY8qs\u0012\"WMZ1vYR$S'\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001c\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%o\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012B\u0014AD2paf$C-\u001a4bk2$H%O\u000b\u0003\u0005WQC!!\u000b\u0003\u0004\u0005y1m\u001c9zI\u0011,g-Y;mi\u0012\n\u0004'A\bd_BLH\u0005Z3gCVdG\u000fJ\u00192\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\u0012\u0014aD2paf$C-\u001a4bk2$H%M\u001a\u0002\u001f\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cQ\n\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0005w\u0001BA!\u0010\u0003L9!!q\bB$!\r\u0011\t%X\u0007\u0003\u0005\u0007R1A!\u0012Z\u0003\u0019a$o\\8u}%\u0019!\u0011J/\u0002\rA\u0013X\rZ3g\u0013\u0011\u0011iEa\u0014\u0003\rM#(/\u001b8h\u0015\r\u0011I%X\u0001\u000f'NcE)\u001a2vO\u000e{gNZ5h!\tqhh\u0005\u0002?7R\u0011!1K\u0001\u0006CB\u0004H.\u001f\u000b\u0003\u0003+\n1bZ3u\u0013:\u001cH/\u00198dK\u0006YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$3'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H\u0005N\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u001b\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00137\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%o\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIa\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012J\u0014\u0001\b\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013\u0007M\u0001\u001dI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u00192\u0003q!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%cI\nA\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\n4'\u0001\u000f%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H%\r\u001b")
public final class SSLDebugConfig {
    private final boolean all;
    private final boolean certpath;
    private final boolean defaultctx;
    private final Option<SSLDebugHandshakeOptions> handshake;
    private final boolean keygen;
    private final boolean keymanager;
    private final boolean ocsp;
    private final boolean pluggability;
    private final Option<SSLDebugRecordOptions> record;
    private final boolean session;
    private final boolean sessioncache;
    private final boolean ssl;
    private final boolean sslctx;
    private final boolean trustmanager;

    public static SSLDebugConfig getInstance() {
        return SSLDebugConfig$.MODULE$.getInstance();
    }

    public static SSLDebugConfig apply() {
        return SSLDebugConfig$.MODULE$.apply();
    }

    public boolean all() {
        return this.all;
    }

    public boolean certpath() {
        return this.certpath;
    }

    public boolean defaultctx() {
        return this.defaultctx;
    }

    public Option<SSLDebugHandshakeOptions> handshake() {
        return this.handshake;
    }

    public boolean keygen() {
        return this.keygen;
    }

    public boolean keymanager() {
        return this.keymanager;
    }

    public boolean ocsp() {
        return this.ocsp;
    }

    public boolean pluggability() {
        return this.pluggability;
    }

    public Option<SSLDebugRecordOptions> record() {
        return this.record;
    }

    public boolean session() {
        return this.session;
    }

    public boolean sessioncache() {
        return this.sessioncache;
    }

    public boolean ssl() {
        return this.ssl;
    }

    public boolean sslctx() {
        return this.sslctx;
    }

    public boolean trustmanager() {
        return this.trustmanager;
    }

    public boolean enabled() {
        return this.all() || this.ssl() || this.sslctx() || this.keymanager() || this.trustmanager();
    }

    public SSLDebugConfig withAll(boolean value) {
        return this.copy(value, this.copy$default$2(), this.copy$default$3(), this.copy$default$4(), this.copy$default$5(), this.copy$default$6(), this.copy$default$7(), this.copy$default$8(), this.copy$default$9(), this.copy$default$10(), this.copy$default$11(), this.copy$default$12(), this.copy$default$13(), this.copy$default$14());
    }

    public SSLDebugConfig withCertPath(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$3();
        Option<SSLDebugHandshakeOptions> x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        boolean x$6 = this.copy$default$6();
        boolean x$7 = this.copy$default$7();
        boolean x$8 = this.copy$default$8();
        Option<SSLDebugRecordOptions> x$9 = this.copy$default$9();
        boolean x$10 = this.copy$default$10();
        boolean x$11 = this.copy$default$11();
        boolean x$12 = this.copy$default$12();
        boolean x$13 = this.copy$default$13();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$1, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public SSLDebugConfig withDefaultContext(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        Option<SSLDebugHandshakeOptions> x$4 = this.copy$default$4();
        boolean x$5 = this.copy$default$5();
        boolean x$6 = this.copy$default$6();
        boolean x$7 = this.copy$default$7();
        boolean x$8 = this.copy$default$8();
        Option<SSLDebugRecordOptions> x$9 = this.copy$default$9();
        boolean x$10 = this.copy$default$10();
        boolean x$11 = this.copy$default$11();
        boolean x$12 = this.copy$default$12();
        boolean x$13 = this.copy$default$13();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$1, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public SSLDebugConfig withHandshake(Option<SSLDebugHandshakeOptions> value) {
        Option<SSLDebugHandshakeOptions> x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        boolean x$5 = this.copy$default$5();
        boolean x$6 = this.copy$default$6();
        boolean x$7 = this.copy$default$7();
        boolean x$8 = this.copy$default$8();
        Option<SSLDebugRecordOptions> x$9 = this.copy$default$9();
        boolean x$10 = this.copy$default$10();
        boolean x$11 = this.copy$default$11();
        boolean x$12 = this.copy$default$12();
        boolean x$13 = this.copy$default$13();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$1, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public SSLDebugConfig withKeygen(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<SSLDebugHandshakeOptions> x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$6();
        boolean x$7 = this.copy$default$7();
        boolean x$8 = this.copy$default$8();
        Option<SSLDebugRecordOptions> x$9 = this.copy$default$9();
        boolean x$10 = this.copy$default$10();
        boolean x$11 = this.copy$default$11();
        boolean x$12 = this.copy$default$12();
        boolean x$13 = this.copy$default$13();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$1, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public SSLDebugConfig withKeymanager(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<SSLDebugHandshakeOptions> x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$7();
        boolean x$8 = this.copy$default$8();
        Option<SSLDebugRecordOptions> x$9 = this.copy$default$9();
        boolean x$10 = this.copy$default$10();
        boolean x$11 = this.copy$default$11();
        boolean x$12 = this.copy$default$12();
        boolean x$13 = this.copy$default$13();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public SSLDebugConfig withOcsp(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<SSLDebugHandshakeOptions> x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        boolean x$8 = this.copy$default$8();
        Option<SSLDebugRecordOptions> x$9 = this.copy$default$9();
        boolean x$10 = this.copy$default$10();
        boolean x$11 = this.copy$default$11();
        boolean x$12 = this.copy$default$12();
        boolean x$13 = this.copy$default$13();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$1, x$8, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public SSLDebugConfig withPluggability(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<SSLDebugHandshakeOptions> x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        boolean x$8 = this.copy$default$7();
        Option<SSLDebugRecordOptions> x$9 = this.copy$default$9();
        boolean x$10 = this.copy$default$10();
        boolean x$11 = this.copy$default$11();
        boolean x$12 = this.copy$default$12();
        boolean x$13 = this.copy$default$13();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$1, x$9, x$10, x$11, x$12, x$13, x$14);
    }

    public SSLDebugConfig withRecord(Option<SSLDebugRecordOptions> value) {
        Option<SSLDebugRecordOptions> x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<SSLDebugHandshakeOptions> x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        boolean x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$8();
        boolean x$10 = this.copy$default$10();
        boolean x$11 = this.copy$default$11();
        boolean x$12 = this.copy$default$12();
        boolean x$13 = this.copy$default$13();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$1, x$10, x$11, x$12, x$13, x$14);
    }

    public SSLDebugConfig withSession(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<SSLDebugHandshakeOptions> x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        boolean x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$8();
        Option<SSLDebugRecordOptions> x$10 = this.copy$default$9();
        boolean x$11 = this.copy$default$11();
        boolean x$12 = this.copy$default$12();
        boolean x$13 = this.copy$default$13();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$1, x$11, x$12, x$13, x$14);
    }

    public SSLDebugConfig withSessioncache(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<SSLDebugHandshakeOptions> x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        boolean x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$8();
        Option<SSLDebugRecordOptions> x$10 = this.copy$default$9();
        boolean x$11 = this.copy$default$10();
        boolean x$12 = this.copy$default$12();
        boolean x$13 = this.copy$default$13();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$1, x$12, x$13, x$14);
    }

    public SSLDebugConfig withSsl(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<SSLDebugHandshakeOptions> x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        boolean x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$8();
        Option<SSLDebugRecordOptions> x$10 = this.copy$default$9();
        boolean x$11 = this.copy$default$10();
        boolean x$12 = this.copy$default$11();
        boolean x$13 = this.copy$default$13();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$1, x$13, x$14);
    }

    public SSLDebugConfig withSslctx(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<SSLDebugHandshakeOptions> x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        boolean x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$8();
        Option<SSLDebugRecordOptions> x$10 = this.copy$default$9();
        boolean x$11 = this.copy$default$10();
        boolean x$12 = this.copy$default$11();
        boolean x$13 = this.copy$default$12();
        boolean x$14 = this.copy$default$14();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$1, x$14);
    }

    public SSLDebugConfig withTrustmanager(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        boolean x$3 = this.copy$default$2();
        boolean x$4 = this.copy$default$3();
        Option<SSLDebugHandshakeOptions> x$5 = this.copy$default$4();
        boolean x$6 = this.copy$default$5();
        boolean x$7 = this.copy$default$6();
        boolean x$8 = this.copy$default$7();
        boolean x$9 = this.copy$default$8();
        Option<SSLDebugRecordOptions> x$10 = this.copy$default$9();
        boolean x$11 = this.copy$default$10();
        boolean x$12 = this.copy$default$11();
        boolean x$13 = this.copy$default$12();
        boolean x$14 = this.copy$default$13();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$11, x$12, x$13, x$14, x$1);
    }

    private SSLDebugConfig copy(boolean all, boolean certpath, boolean defaultctx, Option<SSLDebugHandshakeOptions> handshake2, boolean keygen, boolean keymanager, boolean ocsp, boolean pluggability, Option<SSLDebugRecordOptions> record, boolean session, boolean sessioncache, boolean ssl, boolean sslctx, boolean trustmanager) {
        return new SSLDebugConfig(all, certpath, defaultctx, handshake2, keygen, keymanager, ocsp, pluggability, record, session, sessioncache, ssl, sslctx, trustmanager);
    }

    private boolean copy$default$1() {
        return this.all();
    }

    private boolean copy$default$2() {
        return this.certpath();
    }

    private boolean copy$default$3() {
        return this.defaultctx();
    }

    private Option<SSLDebugHandshakeOptions> copy$default$4() {
        return this.handshake();
    }

    private boolean copy$default$5() {
        return this.keygen();
    }

    private boolean copy$default$6() {
        return this.keymanager();
    }

    private boolean copy$default$7() {
        return this.ocsp();
    }

    private boolean copy$default$8() {
        return this.pluggability();
    }

    private Option<SSLDebugRecordOptions> copy$default$9() {
        return this.record();
    }

    private boolean copy$default$10() {
        return this.session();
    }

    private boolean copy$default$11() {
        return this.sessioncache();
    }

    private boolean copy$default$12() {
        return this.ssl();
    }

    private boolean copy$default$13() {
        return this.sslctx();
    }

    private boolean copy$default$14() {
        return this.trustmanager();
    }

    public String toString() {
        return new StringBuilder(73).append("SSLDebugConfig(all = ").append(this.all()).append(", ssl = ").append(this.ssl()).append(", sslctx = ").append(this.sslctx()).append(", keymanager = ").append(this.keymanager()).append(", trustmanager = ").append(this.trustmanager()).append(")").toString();
    }

    public SSLDebugConfig(boolean all, boolean certpath, boolean defaultctx, Option<SSLDebugHandshakeOptions> handshake2, boolean keygen, boolean keymanager, boolean ocsp, boolean pluggability, Option<SSLDebugRecordOptions> record, boolean session, boolean sessioncache, boolean ssl, boolean sslctx, boolean trustmanager) {
        this.all = all;
        this.certpath = certpath;
        this.defaultctx = defaultctx;
        this.handshake = handshake2;
        this.keygen = keygen;
        this.keymanager = keymanager;
        this.ocsp = ocsp;
        this.pluggability = pluggability;
        this.record = record;
        this.session = session;
        this.sessioncache = sessioncache;
        this.ssl = ssl;
        this.sslctx = sslctx;
        this.trustmanager = trustmanager;
    }
}

