/*
 * Decompiled with CFR 0.152.
 */
package scala.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.collection.AbstractIterator;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.collection.Iterator$;
import scala.collection.StringOps$;
import scala.io.Codec;
import scala.io.Source;
import scala.io.Source$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.java8.JFunction0$mcI$sp;

@ScalaSignature(bytes="\u0006\u0005\u0005-b\u0001\u0002\r\u001a\u0001yA\u0001b\t\u0001\u0003\u0002\u0003\u0006I\u0001\n\u0005\tW\u0001\u0011\t\u0011)A\u0005Y!A\u0001\u0007\u0001BC\u0002\u0013\r\u0011\u0007\u0003\u00056\u0001\t\u0005\t\u0015!\u00033\u0011\u00151\u0004\u0001\"\u00018\u0011\u00151\u0004\u0001\"\u0001>\u0011\u0015\t\u0005\u0001\"\u0001C\u0011\u00151\u0005\u0001\"\u0001H\u0011\u0019Y\u0005\u0001)Q\u0005\u0019\"Aq\n\u0001ECB\u0013%\u0001\u000bC\u0004R\u0001\t\u0007I\u0011\t*\t\rq\u0003\u0001\u0015!\u0003T\u0011\u0015i\u0006\u0001\"\u0003Q\r\u0011q\u0006\u0001A0\t\u000bYrA\u0011A8\t\rIt\u0001\u0015!\u0003I\u0011\u001d\u0019h\u00021A\u0005\u0002QDq!\u001e\bA\u0002\u0013\u0005a\u000f\u0003\u0004}\u001d\u0001\u0006Ka\u0019\u0005\u0006{:!\tE \u0005\u0007\u007f:!\t%!\u0001\t\u000f\u0005\r\u0001\u0001\"\u0011\u0002\u0006!9\u0011q\u0001\u0001\u0005B\u0005%!A\u0004\"vM\u001a,'/\u001a3T_V\u00148-\u001a\u0006\u00035m\t!![8\u000b\u0003q\tQa]2bY\u0006\u001c\u0001a\u0005\u0002\u0001?A\u0011\u0001%I\u0007\u00023%\u0011!%\u0007\u0002\u0007'>,(oY3\u0002\u0017%t\u0007/\u001e;TiJ,\u0017-\u001c\t\u0003K%j\u0011A\n\u0006\u00035\u001dR\u0011\u0001K\u0001\u0005U\u00064\u0018-\u0003\u0002+M\tY\u0011J\u001c9viN#(/Z1n\u0003)\u0011WO\u001a4feNK'0\u001a\t\u0003[9j\u0011aG\u0005\u0003_m\u00111!\u00138u\u0003\u0015\u0019w\u000eZ3d+\u0005\u0011\u0004C\u0001\u00114\u0013\t!\u0014DA\u0003D_\u0012,7-\u0001\u0004d_\u0012,7\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007aZD\b\u0006\u0002:uA\u0011\u0001\u0005\u0001\u0005\u0006a\u0015\u0001\u001dA\r\u0005\u0006G\u0015\u0001\r\u0001\n\u0005\u0006W\u0015\u0001\r\u0001\f\u000b\u0003}\u0001#\"!O \t\u000bA2\u00019\u0001\u001a\t\u000b\r2\u0001\u0019\u0001\u0013\u0002\rI,\u0017\rZ3s)\u0005\u0019\u0005CA\u0013E\u0013\t)eEA\tJ]B,Ho\u0015;sK\u0006l'+Z1eKJ\faBY;gM\u0016\u0014X\r\u001a*fC\u0012,'\u000fF\u0001I!\t)\u0013*\u0003\u0002KM\tq!)\u001e4gKJ,GMU3bI\u0016\u0014\u0018!E2iCJ\u0014V-\u00193fe\u000e\u0013X-\u0019;fIB\u0011Q&T\u0005\u0003\u001dn\u0011qAQ8pY\u0016\fg.\u0001\u0006dQ\u0006\u0014(+Z1eKJ,\u0012\u0001S\u0001\u0005SR,'/F\u0001T!\r!v+W\u0007\u0002+*\u0011akG\u0001\u000bG>dG.Z2uS>t\u0017B\u0001-V\u0005!IE/\u001a:bi>\u0014\bCA\u0017[\u0013\tY6D\u0001\u0003DQ\u0006\u0014\u0018!B5uKJ\u0004\u0013A\u00043fG\u0006\u001c\u0007.\u001a3SK\u0006$WM\u001d\u0002\u0015\u0005V4g-\u001a:fI2Kg.Z%uKJ\fGo\u001c:\u0014\u00079\u0001g\u000eE\u0002UC\u000eL!AY+\u0003!\u0005\u00137\u000f\u001e:bGRLE/\u001a:bi>\u0014\bC\u00013l\u001d\t)\u0017\u000e\u0005\u0002g75\tqM\u0003\u0002i;\u00051AH]8pizJ!A[\u000e\u0002\rA\u0013X\rZ3g\u0013\taWN\u0001\u0004TiJLgn\u001a\u0006\u0003Un\u00012\u0001V,d)\u0005\u0001\bCA9\u000f\u001b\u0005\u0001\u0011A\u00037j]\u0016\u0014V-\u00193fe\u0006Aa.\u001a=u\u0019&tW-F\u0001d\u00031qW\r\u001f;MS:,w\fJ3r)\t9(\u0010\u0005\u0002.q&\u0011\u0011p\u0007\u0002\u0005+:LG\u000fC\u0004|%\u0005\u0005\t\u0019A2\u0002\u0007a$\u0013'A\u0005oKb$H*\u001b8fA\u00059\u0001.Y:OKb$X#\u0001'\u0002\t9,\u0007\u0010\u001e\u000b\u0002G\u0006Aq-\u001a;MS:,7\u000fF\u0001o\u0003%\tG\rZ*ue&tw\r\u0006\u0006\u0002\f\u0005=\u0011qDA\u0012\u0003OqA!!\u0004\u0002\u00101\u0001\u0001bBA\t/\u0001\u0007\u00111C\u0001\u0003g\n\u0004B!!\u0006\u0002\u001c5\u0011\u0011q\u0003\u0006\u0004\u00033)\u0016aB7vi\u0006\u0014G.Z\u0005\u0005\u0003;\t9BA\u0007TiJLgn\u001a\"vS2$WM\u001d\u0005\u0007\u0003C9\u0002\u0019A2\u0002\u000bM$\u0018M\u001d;\t\r\u0005\u0015r\u00031\u0001d\u0003\r\u0019X\r\u001d\u0005\u0007\u0003S9\u0002\u0019A2\u0002\u0007\u0015tG\r")
public class BufferedSource
extends Source {
    private BufferedReader charReader;
    private final InputStream inputStream;
    private final int bufferSize;
    private final Codec codec;
    private boolean charReaderCreated;
    private final Iterator<Object> iter;
    private volatile boolean bitmap$0;

    public Codec codec() {
        return this.codec;
    }

    public InputStreamReader reader() {
        return new InputStreamReader(this.inputStream, this.codec().decoder());
    }

    public BufferedReader bufferedReader() {
        return new BufferedReader(this.reader(), this.bufferSize);
    }

    private BufferedReader charReader$lzycompute() {
        synchronized (this) {
            if (!this.bitmap$0) {
                this.charReaderCreated = true;
                this.charReader = this.bufferedReader();
                this.bitmap$0 = true;
            }
        }
        return this.charReader;
    }

    private BufferedReader charReader() {
        if (!this.bitmap$0) {
            return this.charReader$lzycompute();
        }
        return this.charReader;
    }

    @Override
    public Iterator<Object> iter() {
        return this.iter;
    }

    public BufferedReader scala$io$BufferedSource$$decachedReader() {
        if (this.charReaderCreated && this.iter().hasNext()) {
            PushbackReader pb = new PushbackReader(this.charReader());
            pb.unread(BoxesRunTime.unboxToChar(this.iter().next()));
            return new BufferedReader(pb, this.bufferSize);
        }
        return this.charReader();
    }

    @Override
    public Iterator<String> getLines() {
        return new BufferedLineIterator();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public scala.collection.mutable.StringBuilder addString(scala.collection.mutable.StringBuilder sb, String start2, String sep, String end) {
        void mkString_end;
        if (sep.isEmpty()) {
            BufferedReader allReader = this.scala$io$BufferedSource$$decachedReader();
            char[] buf = new char[this.bufferSize];
            StringBuilder jsb = sb.underlying();
            if (start2.length() != 0) {
                jsb.append(start2);
            }
            int n = allReader.read(buf);
            while (n != -1) {
                jsb.append(buf, 0, n);
                n = allReader.read(buf);
            }
            if (end.length() != 0) {
                jsb.append(end);
            }
            return sb;
        }
        String mkString_mkString_sep = "";
        String string = "";
        String mkString_start = "";
        Object var10_11 = null;
        string = null;
        Object var9_9 = null;
        return StringOps$.MODULE$.addString$extension(IterableOnceOps.mkString$(this, mkString_start, mkString_mkString_sep, (String)mkString_end), sb, start2, sep, end);
    }

    public static final /* synthetic */ char $anonfun$iter$4(int x$2) {
        return (char)x$2;
    }

    public BufferedSource(InputStream inputStream2, int bufferSize, Codec codec) {
        this.inputStream = inputStream2;
        this.bufferSize = bufferSize;
        this.codec = codec;
        this.charReaderCreated = false;
        JFunction0$mcI$sp continually_elem = () -> this.codec().wrap(() -> this.charReader().read());
        Object var4_4 = null;
        this.iter = new AbstractIterator<A>(continually_elem){
            private final Function0 elem$5;

            public boolean hasNext() {
                return true;
            }

            public A next() {
                return (A)this.elem$5.apply();
            }
            {
                this.elem$5 = elem$5;
            }
        }.takeWhile((A x$1) -> x$1 != -1).map((Function1<Object, Object> & Serializable)(A x$2) -> BoxesRunTime.boxToCharacter(BufferedSource.$anonfun$iter$4(BoxesRunTime.unboxToInt(x$2))));
    }

    public BufferedSource(InputStream inputStream2, Codec codec) {
        this(inputStream2, Source$.MODULE$.DefaultBufSize(), codec);
    }

    public class BufferedLineIterator
    extends AbstractIterator<String> {
        private final BufferedReader lineReader;
        private String nextLine;

        public String nextLine() {
            return this.nextLine;
        }

        public void nextLine_$eq(String x$1) {
            this.nextLine = x$1;
        }

        @Override
        public boolean hasNext() {
            if (this.nextLine() == null) {
                this.nextLine_$eq(this.lineReader.readLine());
            }
            return this.nextLine() != null;
        }

        @Override
        public String next() {
            String string;
            if (this.nextLine() == null) {
                string = this.lineReader.readLine();
            } else {
                try {
                    string = this.nextLine();
                }
                finally {
                    this.nextLine_$eq(null);
                }
            }
            String result = string;
            if (result == null) {
                return (String)((Object)Iterator$.scala$collection$Iterator$$_empty.next());
            }
            return result;
        }

        public /* synthetic */ BufferedSource scala$io$BufferedSource$BufferedLineIterator$$$outer() {
            return BufferedSource.this;
        }

        public BufferedLineIterator() {
            if (BufferedSource.this == null) {
                throw null;
            }
            this.lineReader = BufferedSource.this.scala$io$BufferedSource$$decachedReader();
            this.nextLine = null;
        }
    }
}

