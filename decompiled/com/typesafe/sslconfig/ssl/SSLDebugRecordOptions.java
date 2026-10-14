/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.SSLDebugRecordOptions$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000554Aa\u0005\u000b\u0003;!AA\u0005\u0001BC\u0002\u0013\u0005Q\u0005\u0003\u0005*\u0001\t\u0005\t\u0015!\u0003'\u0011!Q\u0003A!b\u0001\n\u0003)\u0003\u0002C\u0016\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0014\t\r1\u0002A\u0011\u0001\f.\u0011\u0015\u0011\u0004\u0001\"\u00014\u0011\u00151\u0004\u0001\"\u00018\u0011\u0015I\u0004\u0001\"\u0003;\u0011\u001di\u0004!%A\u0005\nyBq!\u0013\u0001\u0012\u0002\u0013%a\bC\u0003K\u0001\u0011\u00053jB\u0003b)!\u0005!MB\u0003\u0014)!\u00051\rC\u0003-\u001b\u0011\u0005A\rC\u0003f\u001b\u0011\u0005a\rC\u0003h\u001b\u0011\u0005a\r\u0003\u0005i\u001bE\u0005I\u0011\u0001\f?\u0011!IW\"%A\u0005\u0002Yq$!F*T\u0019\u0012+'-^4SK\u000e|'\u000fZ(qi&|gn\u001d\u0006\u0003+Y\t1a]:m\u0015\t9\u0002$A\u0005tg2\u001cwN\u001c4jO*\u0011\u0011DG\u0001\tif\u0004Xm]1gK*\t1$A\u0002d_6\u001c\u0001a\u0005\u0002\u0001=A\u0011qDI\u0007\u0002A)\t\u0011%A\u0003tG\u0006d\u0017-\u0003\u0002$A\t1\u0011I\\=SK\u001a\fa\u0001]1dW\u0016$X#\u0001\u0014\u0011\u0005}9\u0013B\u0001\u0015!\u0005\u001d\u0011un\u001c7fC:\fq\u0001]1dW\u0016$\b%A\u0005qY\u0006Lg\u000e^3yi\u0006Q\u0001\u000f\\1j]R,\u0007\u0010\u001e\u0011\u0002\rqJg.\u001b;?)\rq\u0003'\r\t\u0003_\u0001i\u0011\u0001\u0006\u0005\bI\u0015\u0001\n\u00111\u0001'\u0011\u001dQS\u0001%AA\u0002\u0019\n!b^5uQB\u000b7m[3u)\tqC\u0007C\u00036\r\u0001\u0007a%A\u0003wC2,X-A\u0007xSRD\u0007\u000b\\1j]R,\u0007\u0010\u001e\u000b\u0003]aBQ!N\u0004A\u0002\u0019\nAaY8qsR\u0019af\u000f\u001f\t\u000f\u0011B\u0001\u0013!a\u0001M!9!\u0006\u0003I\u0001\u0002\u00041\u0013AD2paf$C-\u001a4bk2$H%M\u000b\u0002\u007f)\u0012a\u0005Q\u0016\u0002\u0003B\u0011!iR\u0007\u0002\u0007*\u0011A)R\u0001\nk:\u001c\u0007.Z2lK\u0012T!A\u0012\u0011\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002I\u0007\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u0005AAo\\*ue&tw\rF\u0001M!\tiEK\u0004\u0002O%B\u0011q\nI\u0007\u0002!*\u0011\u0011\u000bH\u0001\u0007yI|w\u000e\u001e \n\u0005M\u0003\u0013A\u0002)sK\u0012,g-\u0003\u0002V-\n11\u000b\u001e:j]\u001eT!a\u0015\u0011)\r\u0001A6\f\u00180`!\ty\u0012,\u0003\u0002[A\tQA-\u001a9sK\u000e\fG/\u001a3\u0002\u000f5,7o]1hK\u0006\nQ,A\u0007o_R\u0004s\u000e]3sCRLg/Z\u0001\u0006g&t7-Z\u0011\u0002A\u0006)\u0001G\f\u001b/a\u0005)2k\u0015'EK\n,xMU3d_J$w\n\u001d;j_:\u001c\bCA\u0018\u000e'\tia\u0004F\u0001c\u0003\u0015\t\u0007\u000f\u001d7z)\u0005q\u0013aC4fi&s7\u000f^1oG\u0016\f1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\n\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$#\u0007\u000b\u0004\u000e1ncfl\u0018\u0015\u0003\u001baCc\u0001\u0004-\\9z{\u0006")
public final class SSLDebugRecordOptions {
    private final boolean packet;
    private final boolean plaintext;

    public static SSLDebugRecordOptions getInstance() {
        return SSLDebugRecordOptions$.MODULE$.getInstance();
    }

    public static SSLDebugRecordOptions apply() {
        return SSLDebugRecordOptions$.MODULE$.apply();
    }

    public boolean packet() {
        return this.packet;
    }

    public boolean plaintext() {
        return this.plaintext;
    }

    public SSLDebugRecordOptions withPacket(boolean value) {
        return this.copy(value, this.copy$default$2());
    }

    public SSLDebugRecordOptions withPlaintext(boolean value) {
        boolean x$1 = value;
        boolean x$2 = this.copy$default$1();
        return this.copy(x$2, x$1);
    }

    private SSLDebugRecordOptions copy(boolean packet, boolean plaintext) {
        return new SSLDebugRecordOptions(packet, plaintext);
    }

    private boolean copy$default$1() {
        return this.packet();
    }

    private boolean copy$default$2() {
        return this.plaintext();
    }

    public String toString() {
        return new StringBuilder(24).append("SSLDebugRecordOptions(").append(this.packet()).append(",").append(this.plaintext()).append(")").toString();
    }

    public SSLDebugRecordOptions(boolean packet, boolean plaintext) {
        this.packet = packet;
        this.plaintext = plaintext;
    }
}

