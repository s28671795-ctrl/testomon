/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.Deploy$;
import akka.actor.ExtendedActorSystem;
import akka.actor.Props$;
import akka.dispatch.MessageDispatcher;
import akka.io.BufferPool;
import akka.io.DirectByteBufferPool;
import akka.io.IO;
import akka.io.SelectionHandlerSettings;
import akka.io.TcpManager;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import akka.util.Helpers$Requiring$;
import com.typesafe.config.Config;
import java.io.Serializable;
import scala.Function0;
import scala.Predef$;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.math.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005uc\u0001\u0002\u0015*\u00019B\u0001\"\u0010\u0001\u0003\u0002\u0003\u0006IA\u0010\u0005\u0006\t\u0002!\t!\u0012\u0005\b\u0011\u0002\u0011\r\u0011\"\u0001J\u0011\u001d\ty\u0003\u0001Q\u0001\n)3A\u0001\u0014\u0001\u0001\u001b\"A\u0011+\u0002B\u0001B\u0003%!\u000b\u0003\u0004E\u000b\u0011\u0005\u0001\u0001\u0018\u0005\b=\u0016\u0011\r\u0011\"\u0001`\u0011\u0019\u0019W\u0001)A\u0005A\"9A-\u0002b\u0001\n\u0003y\u0006BB3\u0006A\u0003%\u0001\rC\u0004g\u000b\t\u0007I\u0011A0\t\r\u001d,\u0001\u0015!\u0003a\u0011\u001dAWA1A\u0005\u0002}Ca![\u0003!\u0002\u0013\u0001\u0007b\u00026\u0006\u0005\u0004%\ta\u001b\u0005\u0007i\u0016\u0001\u000b\u0011\u00027\t\u000fU,!\u0019!C\u0001?\"1a/\u0002Q\u0001\n\u0001Dqa^\u0003C\u0002\u0013\u0005\u0001\u0010C\u0004\u0002\n\u0015\u0001\u000b\u0011B=\t\u0011\u0005-QA1A\u0005\u0002aDq!!\u0004\u0006A\u0003%\u0011\u0010\u0003\u0005\u0002\u0010\u0015\u0011\r\u0011\"\u0001`\u0011\u001d\t\t\"\u0002Q\u0001\n\u0001D\u0001\"a\u0005\u0006\u0005\u0004%\ta\u0018\u0005\b\u0003+)\u0001\u0015!\u0003a\u0011!\t9\"\u0002b\u0001\n\u0003y\u0006bBA\r\u000b\u0001\u0006I\u0001\u0019\u0005\n\u00037)!\u0019!C\u0001\u0003;A\u0001\"!\n\u0006A\u0003%\u0011q\u0004\u0005\t\u0003O)\u0001\u0015\"\u0003\u0002*!I\u0011\u0011\u0007\u0001C\u0002\u0013\u0005\u00111\u0007\u0005\t\u0003w\u0001\u0001\u0015!\u0003\u00026!9\u0011Q\b\u0001\u0005\u0002\u0005M\u0002\"CA \u0001\t\u0007I\u0011AA!\u0011!\tI\u0005\u0001Q\u0001\n\u0005\r\u0003\"CA&\u0001\t\u0007I\u0011AA'\u0011!\tY\u0006\u0001Q\u0001\n\u0005=#A\u0002+da\u0016CHO\u0003\u0002+W\u0005\u0011\u0011n\u001c\u0006\u0002Y\u0005!\u0011m[6b\u0007\u0001\u00192\u0001A\u00186!\t\u00014'D\u00012\u0015\u0005\u0011\u0014!B:dC2\f\u0017B\u0001\u001b2\u0005\u0019\te.\u001f*fMB\u0011aG\u000f\b\u0003oaj\u0011!K\u0005\u0003s%\n!!S(\n\u0005mb$!C#yi\u0016t7/[8o\u0015\tI\u0014&\u0001\u0004tsN$X-\u001c\t\u0003\u007f\tk\u0011\u0001\u0011\u0006\u0003\u0003.\nQ!Y2u_JL!a\u0011!\u0003'\u0015CH/\u001a8eK\u0012\f5\r^8s'f\u001cH/Z7\u0002\rqJg.\u001b;?)\t1u\t\u0005\u00028\u0001!)QH\u0001a\u0001}\u0005A1+\u001a;uS:<7/F\u0001K!\tYU!D\u0001\u0001\u0005!\u0019V\r\u001e;j]\u001e\u001c8CA\u0003O!\t9t*\u0003\u0002QS\tA2+\u001a7fGRLwN\u001c%b]\u0012dWM]*fiRLgnZ:\u0002\u000f}\u001bwN\u001c4jOB\u00111KW\u0007\u0002)*\u0011QKV\u0001\u0007G>tg-[4\u000b\u0005]C\u0016\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0003e\u000b1aY8n\u0013\tYFK\u0001\u0004D_:4\u0017n\u001a\u000b\u0003\u0015vCQ!U\u0004A\u0002I\u000bQB\u0014:PMN+G.Z2u_J\u001cX#\u00011\u0011\u0005A\n\u0017B\u000122\u0005\rIe\u000e^\u0001\u000f\u001dJ|emU3mK\u000e$xN]:!\u0003A\u0011\u0015\r^2i\u0003\u000e\u001cW\r\u001d;MS6LG/A\tCCR\u001c\u0007.Q2dKB$H*[7ji\u0002\n\u0001\u0003R5sK\u000e$()\u001e4gKJ\u001c\u0016N_3\u0002#\u0011K'/Z2u\u0005V4g-\u001a:TSj,\u0007%A\fNCb$\u0015N]3di\n+hMZ3s!>|GnU5{K\u0006AR*\u0019=ESJ,7\r\u001e\"vM\u001a,'\u000fU8pYNK'0\u001a\u0011\u0002\u001fI+w-[:uKJ$\u0016.\\3pkR,\u0012\u0001\u001c\t\u0003[Jl\u0011A\u001c\u0006\u0003_B\f\u0001\u0002Z;sCRLwN\u001c\u0006\u0003cF\n!bY8oGV\u0014(/\u001a8u\u0013\t\u0019hN\u0001\u0005EkJ\fG/[8o\u0003A\u0011VmZ5ti\u0016\u0014H+[7f_V$\b%\u0001\rSK\u000e,\u0017N^3e\u001b\u0016\u001c8/Y4f'&TX\rT5nSR\f\u0011DU3dK&4X\rZ'fgN\fw-Z*ju\u0016d\u0015.\\5uA\u0005!R*\u00198bO\u0016lWM\u001c;ESN\u0004\u0018\r^2iKJ,\u0012!\u001f\t\u0004u\u0006\raBA>\u0000!\ta\u0018'D\u0001~\u0015\tqX&\u0001\u0004=e>|GOP\u0005\u0004\u0003\u0003\t\u0014A\u0002)sK\u0012,g-\u0003\u0003\u0002\u0006\u0005\u001d!AB*ue&twMC\u0002\u0002\u0002E\nQ#T1oC\u001e,W.\u001a8u\t&\u001c\b/\u0019;dQ\u0016\u0014\b%\u0001\tGS2,\u0017j\u0014#jgB\fGo\u00195fe\u0006\tb)\u001b7f\u0013>#\u0015n\u001d9bi\u000eDWM\u001d\u0011\u0002\u001fQ\u0013\u0018M\\:gKJ$v\u000eT5nSR\f\u0001\u0003\u0016:b]N4WM\u001d+p\u0019&l\u0017\u000e\u001e\u0011\u0002-5\u000b\u0007p\u00115b]:,Gn\u001d)feN+G.Z2u_J\fq#T1y\u0007\"\fgN\\3mgB+'oU3mK\u000e$xN\u001d\u0011\u0002)\u0019Kg.[:i\u0007>tg.Z2u%\u0016$(/[3t\u0003U1\u0015N\\5tQ\u000e{gN\\3diJ+GO]5fg\u0002\nqeV5oI><8oQ8o]\u0016\u001cG/[8o\u0003\n|'\u000f^,pe.\f'o\\;oI\u0016s\u0017M\u00197fIV\u0011\u0011q\u0004\t\u0004a\u0005\u0005\u0012bAA\u0012c\t9!i\\8mK\u0006t\u0017\u0001K,j]\u0012|wo]\"p]:,7\r^5p]\u0006\u0013wN\u001d;X_J\\\u0017M]8v]\u0012,e.\u00192mK\u0012\u0004\u0013aC4fi&sGOQ=uKN$2\u0001YA\u0016\u0011\u0019\ti\u0003\ta\u0001s\u0006!\u0001/\u0019;i\u0003%\u0019V\r\u001e;j]\u001e\u001c\b%A\u0004nC:\fw-\u001a:\u0016\u0005\u0005U\u0002cA \u00028%\u0019\u0011\u0011\b!\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\f\u0001\"\\1oC\u001e,'\u000fI\u0001\u000bO\u0016$X*\u00198bO\u0016\u0014\u0018A\u00032vM\u001a,'\u000fU8pYV\u0011\u00111\t\t\u0004o\u0005\u0015\u0013bAA$S\tQ!)\u001e4gKJ\u0004vn\u001c7\u0002\u0017\t,hMZ3s!>|G\u000eI\u0001\u0011M&dW-S8ESN\u0004\u0018\r^2iKJ,\"!a\u0014\u0011\t\u0005E\u0013qK\u0007\u0003\u0003'R1!!\u0016,\u0003!!\u0017n\u001d9bi\u000eD\u0017\u0002BA-\u0003'\u0012\u0011#T3tg\u0006<W\rR5ta\u0006$8\r[3s\u0003E1\u0017\u000e\\3J_\u0012K7\u000f]1uG\",'\u000f\t")
public class TcpExt
implements IO.Extension {
    private final Settings Settings;
    private final ActorRef manager;
    private final BufferPool bufferPool;
    private final MessageDispatcher fileIoDispatcher;

    public Settings Settings() {
        return this.Settings;
    }

    @Override
    public ActorRef manager() {
        return this.manager;
    }

    public ActorRef getManager() {
        return this.manager();
    }

    public BufferPool bufferPool() {
        return this.bufferPool;
    }

    public MessageDispatcher fileIoDispatcher() {
        return this.fileIoDispatcher;
    }

    public TcpExt(ExtendedActorSystem system) {
        this.Settings = new Settings(this, system.settings().config().getConfig("akka.io.tcp"));
        this.manager = system.systemActorOf(Props$.MODULE$.apply(TcpManager.class, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{this})).withDispatcher(this.Settings().ManagementDispatcher()).withDeploy(Deploy$.MODULE$.local()), "IO-TCP");
        this.bufferPool = new DirectByteBufferPool(this.Settings().DirectBufferSize(), this.Settings().MaxDirectBufferPoolSize());
        this.fileIoDispatcher = system.dispatchers().lookup(this.Settings().FileIODispatcher());
    }

    public class Settings
    extends SelectionHandlerSettings {
        private final Config _config;
        private final int NrOfSelectors;
        private final int BatchAcceptLimit;
        private final int DirectBufferSize;
        private final int MaxDirectBufferPoolSize;
        private final Duration RegisterTimeout;
        private final int ReceivedMessageSizeLimit;
        private final String ManagementDispatcher;
        private final String FileIODispatcher;
        private final int TransferToLimit;
        private final int MaxChannelsPerSelector;
        private final int FinishConnectRetries;
        private final boolean WindowsConnectionAbortWorkaroundEnabled;
        public final /* synthetic */ TcpExt $outer;

        public int NrOfSelectors() {
            return this.NrOfSelectors;
        }

        public int BatchAcceptLimit() {
            return this.BatchAcceptLimit;
        }

        public int DirectBufferSize() {
            return this.DirectBufferSize;
        }

        public int MaxDirectBufferPoolSize() {
            return this.MaxDirectBufferPoolSize;
        }

        public Duration RegisterTimeout() {
            return this.RegisterTimeout;
        }

        public int ReceivedMessageSizeLimit() {
            return this.ReceivedMessageSizeLimit;
        }

        public String ManagementDispatcher() {
            return this.ManagementDispatcher;
        }

        public String FileIODispatcher() {
            return this.FileIODispatcher;
        }

        public int TransferToLimit() {
            return this.TransferToLimit;
        }

        @Override
        public int MaxChannelsPerSelector() {
            return this.MaxChannelsPerSelector;
        }

        public int FinishConnectRetries() {
            return this.FinishConnectRetries;
        }

        public boolean WindowsConnectionAbortWorkaroundEnabled() {
            return this.WindowsConnectionAbortWorkaroundEnabled;
        }

        private int getIntBytes(String path) {
            Long size = this._config.getBytes(path);
            Predef$.MODULE$.require(Predef$.MODULE$.Long2long(size) < Integer.MAX_VALUE, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(16).append(path).append(" must be < 2 GiB").toString());
            Predef$.MODULE$.require(Predef$.MODULE$.Long2long(size) >= 0L, (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(21).append(path).append(" must be non-negative").toString());
            return (int)Predef$.MODULE$.Long2long(size);
        }

        public /* synthetic */ TcpExt akka$io$TcpExt$Settings$$$outer() {
            return this.$outer;
        }

        public Settings(TcpExt $outer, Config _config) {
            boolean bl;
            block16: {
                int n;
                block15: {
                    int n2;
                    block14: {
                        Duration duration;
                        block13: {
                            this._config = _config;
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            super(_config);
                            this.NrOfSelectors = BoxesRunTime.unboxToInt(Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(BoxesRunTime.boxToInteger(_config.getInt("nr-of-selectors"))), x$1 -> x$1 > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "nr-of-selectors must be > 0"));
                            this.BatchAcceptLimit = BoxesRunTime.unboxToInt(Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(BoxesRunTime.boxToInteger(_config.getInt("batch-accept-limit"))), x$2 -> x$2 > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "batch-accept-limit must be > 0"));
                            this.DirectBufferSize = this.getIntBytes("direct-buffer-size");
                            this.MaxDirectBufferPoolSize = _config.getInt("direct-buffer-pool-limit");
                            String string = _config.getString("register-timeout");
                            switch (string == null ? 0 : string.hashCode()) {
                                case 173173268: {
                                    if (!"infinite".equals(string)) break;
                                    duration = Duration$.MODULE$.Undefined();
                                    break block13;
                                }
                            }
                            duration = Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(_config), "register-timeout");
                        }
                        this.RegisterTimeout = duration;
                        String string = _config.getString("max-received-message-size");
                        switch (string == null ? 0 : string.hashCode()) {
                            case 1887918305: {
                                if (!"unlimited".equals(string)) break;
                                n2 = Integer.MAX_VALUE;
                                break block14;
                            }
                        }
                        n2 = this.getIntBytes("max-received-message-size");
                    }
                    this.ReceivedMessageSizeLimit = n2;
                    this.ManagementDispatcher = _config.getString("management-dispatcher");
                    this.FileIODispatcher = _config.getString("file-io-dispatcher");
                    String string = _config.getString("file-io-transferTo-limit");
                    switch (string == null ? 0 : string.hashCode()) {
                        case 1887918305: {
                            if (!"unlimited".equals(string)) break;
                            n = Integer.MAX_VALUE;
                            break block15;
                        }
                    }
                    n = this.getIntBytes("file-io-transferTo-limit");
                }
                this.TransferToLimit = n;
                this.MaxChannelsPerSelector = this.MaxChannels() == -1 ? -1 : package$.MODULE$.max(this.MaxChannels() / this.NrOfSelectors(), 1);
                this.FinishConnectRetries = BoxesRunTime.unboxToInt(Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(BoxesRunTime.boxToInteger(_config.getInt("finish-connect-retries"))), x$3 -> x$3 > 0, (Function0<Object>)(Function0<String> & Serializable)() -> "finish-connect-retries must be > 0"));
                String string = _config.getString("windows-connection-abort-workaround-enabled");
                switch (string == null ? 0 : string.hashCode()) {
                    case 3005871: {
                        if (!"auto".equals(string)) break;
                        bl = Helpers$.MODULE$.isWindows();
                        break block16;
                    }
                }
                bl = _config.getBoolean("windows-connection-abort-workaround-enabled");
            }
            this.WindowsConnectionAbortWorkaroundEnabled = bl;
        }
    }
}

