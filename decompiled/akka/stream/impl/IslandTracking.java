/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.ActorMaterializerSettings;
import akka.stream.Attributes;
import akka.stream.InPort;
import akka.stream.OutPort;
import akka.stream.impl.ForwardWire;
import akka.stream.impl.IslandTag;
import akka.stream.impl.Phase;
import akka.stream.impl.PhaseIsland;
import akka.stream.impl.PhasedFusingActorMaterializer;
import akka.stream.impl.PhasedFusingActorMaterializer$;
import akka.stream.impl.SavedIslandData;
import akka.stream.impl.SegmentInfo;
import java.util.ArrayList;
import org.reactivestreams.Publisher;
import scala.Predef$;
import scala.collection.immutable.Map;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t\ra!\u0002\u001a4\u0001]J\u0004\u0002\u0003!\u0001\u0005\u000b\u0007I\u0011\u0001\"\t\u0011U\u0003!\u0011!Q\u0001\n\rC\u0001B\u0016\u0001\u0003\u0006\u0004%\ta\u0016\u0005\t9\u0002\u0011\t\u0011)A\u00051\"AQ\f\u0001B\u0001B\u0003%a\f\u0003\u0005b\u0001\t\u0005\t\u0015!\u0003P\u0011!\u0011\u0007A!b\u0001\n\u0003\u0019\u0007\u0002C4\u0001\u0005\u0003\u0005\u000b\u0011\u00023\t\u0011!\u0004!\u0011!Q\u0001\n%DQ\u0001\u001e\u0001\u0005\u0002UDq! \u0001A\u0002\u0013%a\u0010C\u0005\u0002\u0006\u0001\u0001\r\u0011\"\u0003\u0002\b!9\u00111\u0003\u0001!B\u0013y\bbBA\u000b\u0001\u0011%\u0011q\u0003\u0005\t\u00033\u0001\u0001\u0019!C\u0005}\"I\u00111\u0004\u0001A\u0002\u0013%\u0011Q\u0004\u0005\b\u0003C\u0001\u0001\u0015)\u0003\u0000\u0011!\t\u0019\u0003\u0001a\u0001\n\u0013q\b\"CA\u0013\u0001\u0001\u0007I\u0011BA\u0014\u0011\u001d\tY\u0003\u0001Q!\n}D\u0001\"!\f\u0001\u0001\u0004%IA \u0005\n\u0003_\u0001\u0001\u0019!C\u0005\u0003cAq!!\u000e\u0001A\u0003&q\u0010\u0003\u0005\u00028\u0001\u0001\r\u0011\"\u0003\u007f\u0011%\tI\u0004\u0001a\u0001\n\u0013\tY\u0004C\u0004\u0002@\u0001\u0001\u000b\u0015B@\t\u0017\u0005\u0005\u0003\u00011AA\u0002\u0013%\u00111\t\u0005\f\u00037\u0002\u0001\u0019!a\u0001\n\u0013\ti\u0006C\u0006\u0002b\u0001\u0001\r\u0011!Q!\n\u0005\u0015\u0003bCA2\u0001\u0001\u0007\t\u0019!C\u0005\u0003KB1\"a\u001c\u0001\u0001\u0004\u0005\r\u0011\"\u0003\u0002r!Y\u0011Q\u000f\u0001A\u0002\u0003\u0005\u000b\u0015BA4\u0011-\t9\b\u0001a\u0001\u0002\u0004%I!!\u001f\t\u0017\u0005\r\u0005\u00011AA\u0002\u0013%\u0011Q\u0011\u0005\f\u0003\u0013\u0003\u0001\u0019!A!B\u0013\tY\bC\u0006\u0002\f\u0002\u0001\r\u00111A\u0005\n\u00055\u0005bCAL\u0001\u0001\u0007\t\u0019!C\u0005\u00033C1\"!(\u0001\u0001\u0004\u0005\t\u0015)\u0003\u0002\u0010\"I\u0011q\u0014\u0001A\u0002\u0013%\u0011\u0011\u0015\u0005\n\u0003G\u0003\u0001\u0019!C\u0005\u0003KC\u0001\"!+\u0001A\u0003&\u0011\u0011\u000e\u0005\t\u0003W\u0003A\u0011A\u001c\u0002\"\"9\u00111\u0018\u0001\u0005\u0002]r\bbBA`\u0001\u0011%\u0011\u0011\u0019\u0005\t\u0003\u0007\u0004A\u0011A\u001c\u0002F\"A\u0011q\u001a\u0001\u0005\u0002]\n\t\r\u0003\u0005\u0002T\u0002!\taNAk\u0011!\t9\u000f\u0001C\u0001o\u0005%\b\u0002CA\u007f\u0001\u0011\u0005q'!1\u0003\u001d%\u001bH.\u00198e)J\f7m[5oO*\u0011A'N\u0001\u0005S6\u0004HN\u0003\u00027o\u000511\u000f\u001e:fC6T\u0011\u0001O\u0001\u0005C.\\\u0017m\u0005\u0002\u0001uA\u00111HP\u0007\u0002y)\tQ(A\u0003tG\u0006d\u0017-\u0003\u0002@y\t1\u0011I\\=SK\u001a\fa\u0001\u001d5bg\u0016\u001c8\u0001A\u000b\u0002\u0007B!A)S&P\u001b\u0005)%B\u0001$H\u0003%IW.\\;uC\ndWM\u0003\u0002Iy\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005)+%aA'baB\u0011A*T\u0007\u0002g%\u0011aj\r\u0002\n\u0013Nd\u0017M\u001c3UC\u001e\u00042\u0001\u0014)S\u0013\t\t6GA\u0003QQ\u0006\u001cX\r\u0005\u0002<'&\u0011A\u000b\u0010\u0002\u0004\u0003:L\u0018a\u00029iCN,7\u000fI\u0001\tg\u0016$H/\u001b8hgV\t\u0001\f\u0005\u0002Z56\tQ'\u0003\u0002\\k\tI\u0012i\u0019;pe6\u000bG/\u001a:jC2L'0\u001a:TKR$\u0018N\\4t\u0003%\u0019X\r\u001e;j]\u001e\u001c\b%\u0001\u0006biR\u0014\u0018NY;uKN\u0004\"!W0\n\u0005\u0001,$AC!uiJL'-\u001e;fg\u0006aA-\u001a4bk2$\b\u000b[1tK\u0006aQ.\u0019;fe&\fG.\u001b>feV\tA\r\u0005\u0002MK&\u0011am\r\u0002\u001e!\"\f7/\u001a3GkNLgnZ!di>\u0014X*\u0019;fe&\fG.\u001b>fe\u0006iQ.\u0019;fe&\fG.\u001b>fe\u0002\n\u0001#[:mC:$g*Y7f!J,g-\u001b=\u0011\u0005)\fhBA6p!\taG(D\u0001n\u0015\tq\u0017)\u0001\u0004=e>|GOP\u0005\u0003ar\na\u0001\u0015:fI\u00164\u0017B\u0001:t\u0005\u0019\u0019FO]5oO*\u0011\u0001\u000fP\u0001\u0007y%t\u0017\u000e\u001e \u0015\u000fY<\b0\u001f>|yB\u0011A\n\u0001\u0005\u0006\u0001*\u0001\ra\u0011\u0005\u0006-*\u0001\r\u0001\u0017\u0005\u0006;*\u0001\rA\u0018\u0005\u0006C*\u0001\ra\u0014\u0005\u0006E*\u0001\r\u0001\u001a\u0005\u0006Q*\u0001\r![\u0001\u0012SNd\u0017M\u001c3OC6,7i\\;oi\u0016\u0014X#A@\u0011\u0007m\n\t!C\u0002\u0002\u0004q\u00121!\u00138u\u0003UI7\u000f\\1oI:\u000bW.Z\"pk:$XM]0%KF$B!!\u0003\u0002\u0010A\u00191(a\u0003\n\u0007\u00055AH\u0001\u0003V]&$\b\u0002CA\t\u0019\u0005\u0005\t\u0019A@\u0002\u0007a$\u0013'\u0001\njg2\fg\u000e\u001a(b[\u0016\u001cu.\u001e8uKJ\u0004\u0013A\u00048fqRL5\u000f\\1oI:\u000bW.\u001a\u000b\u0002S\u0006\u00192-\u001e:sK:$x\t\\8cC2|eMZ:fi\u000692-\u001e:sK:$x\t\\8cC2|eMZ:fi~#S-\u001d\u000b\u0005\u0003\u0013\ty\u0002\u0003\u0005\u0002\u0012A\t\t\u00111\u0001\u0000\u0003Q\u0019WO\u001d:f]R<En\u001c2bY>3gm]3uA\u0005Q2-\u001e:sK:$8+Z4nK:$x\t\\8cC2|eMZ:fi\u0006q2-\u001e:sK:$8+Z4nK:$x\t\\8cC2|eMZ:fi~#S-\u001d\u000b\u0005\u0003\u0013\tI\u0003\u0003\u0005\u0002\u0012M\t\t\u00111\u0001\u0000\u0003m\u0019WO\u001d:f]R\u001cVmZ7f]R<En\u001c2bY>3gm]3uA\u0005I2-\u001e:sK:$\u0018j\u001d7b]\u0012<En\u001c2bY>3gm]3u\u0003u\u0019WO\u001d:f]RL5\u000f\\1oI\u001ecwNY1m\u001f\u001a47/\u001a;`I\u0015\fH\u0003BA\u0005\u0003gA\u0001\"!\u0005\u0017\u0003\u0003\u0005\ra`\u0001\u001bGV\u0014(/\u001a8u\u0013Nd\u0017M\u001c3HY>\u0014\u0017\r\\(gMN,G\u000fI\u0001\u001aGV\u0014(/\u001a8u\u0013Nd\u0017M\u001c3TW&\u0004\b/\u001a3TY>$8/A\u000fdkJ\u0014XM\u001c;Jg2\fg\u000eZ*lSB\u0004X\rZ*m_R\u001cx\fJ3r)\u0011\tI!!\u0010\t\u0011\u0005E\u0011$!AA\u0002}\f!dY;se\u0016tG/S:mC:$7k[5qa\u0016$7\u000b\\8ug\u0002\n\u0001b]3h[\u0016tGo]\u000b\u0003\u0003\u000b\u0002b!a\u0012\u0002R\u0005USBAA%\u0015\u0011\tY%!\u0014\u0002\tU$\u0018\u000e\u001c\u0006\u0003\u0003\u001f\nAA[1wC&!\u00111KA%\u0005%\t%O]1z\u0019&\u001cH\u000fE\u0002M\u0003/J1!!\u00174\u0005-\u0019VmZ7f]RLeNZ8\u0002\u0019M,w-\\3oiN|F%Z9\u0015\t\u0005%\u0011q\f\u0005\n\u0003#a\u0012\u0011!a\u0001\u0003\u000b\n\u0011b]3h[\u0016tGo\u001d\u0011\u0002\u0019\u0005\u001cG/\u001b<f!\"\f7/Z:\u0016\u0005\u0005\u001d\u0004CBA$\u0003#\nI\u0007\u0005\u0003M\u0003W\u0012\u0016bAA7g\tY\u0001\u000b[1tK&\u001bH.\u00198e\u0003A\t7\r^5wKBC\u0017m]3t?\u0012*\u0017\u000f\u0006\u0003\u0002\n\u0005M\u0004\"CA\t?\u0005\u0005\t\u0019AA4\u00035\t7\r^5wKBC\u0017m]3tA\u0005aam\u001c:xCJ$w+\u001b:fgV\u0011\u00111\u0010\t\u0007\u0003\u000f\n\t&! \u0011\u00071\u000by(C\u0002\u0002\u0002N\u00121BR8so\u0006\u0014HmV5sK\u0006\u0001bm\u001c:xCJ$w+\u001b:fg~#S-\u001d\u000b\u0005\u0003\u0013\t9\tC\u0005\u0002\u0012\t\n\t\u00111\u0001\u0002|\u0005iam\u001c:xCJ$w+\u001b:fg\u0002\n\u0001#[:mC:$7\u000b^1uKN#\u0018mY6\u0016\u0005\u0005=\u0005CBA$\u0003#\n\t\nE\u0002M\u0003'K1!!&4\u0005=\u0019\u0016M^3e\u0013Nd\u0017M\u001c3ECR\f\u0017\u0001F5tY\u0006tGm\u0015;bi\u0016\u001cF/Y2l?\u0012*\u0017\u000f\u0006\u0003\u0002\n\u0005m\u0005\"CA\tK\u0005\u0005\t\u0019AAH\u0003EI7\u000f\\1oIN#\u0018\r^3Ti\u0006\u001c7\u000eI\u0001\rGV\u0014(/\u001a8u!\"\f7/Z\u000b\u0003\u0003S\n\u0001cY;se\u0016tG\u000f\u00155bg\u0016|F%Z9\u0015\t\u0005%\u0011q\u0015\u0005\n\u0003#A\u0013\u0011!a\u0001\u0003S\nQbY;se\u0016tG\u000f\u00155bg\u0016\u0004\u0013aD4fi\u000e+(O]3oiBC\u0017m]3)\u0007)\ny\u000b\u0005\u0003\u00022\u0006]VBAAZ\u0015\r\t)lN\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA]\u0003g\u00131\"\u00138uKJt\u0017\r\\!qS\u0006\u0001r-\u001a;DkJ\u0014XM\u001c;PM\u001a\u001cX\r\u001e\u0015\u0004W\u0005=\u0016aD2p[BdW\r^3TK\u001elWM\u001c;\u0015\u0005\u0005%\u0011aC3oi\u0016\u0014\u0018j\u001d7b]\u0012$b!!\u0003\u0002H\u0006-\u0007BBAe[\u0001\u00071*A\u0002uC\u001eDQ!X\u0017A\u0002yC3!LAX\u0003))\u00070\u001b;Jg2\fg\u000e\u001a\u0015\u0004]\u0005=\u0016AB<je\u0016Le\u000e\u0006\u0004\u0002\n\u0005]\u0017\u0011\u001d\u0005\b\u00033|\u0003\u0019AAn\u0003\tIg\u000eE\u0002Z\u0003;L1!a86\u0005\u0019Ie\u000eU8si\"1\u00111]\u0018A\u0002I\u000bQ\u0001\\8hS\u000eD3aLAX\u0003\u001d9\u0018N]3PkR$\u0002\"!\u0003\u0002l\u0006U\u0018\u0011 \u0005\b\u0003[\u0004\u0004\u0019AAx\u0003\ryW\u000f\u001e\t\u00043\u0006E\u0018bAAzk\t9q*\u001e;Q_J$\bBBA|a\u0001\u0007q0\u0001\bbEN|G.\u001e;f\u001f\u001a47/\u001a;\t\r\u0005\r\b\u00071\u0001SQ\r\u0001\u0014qV\u0001\u0016C2dg*Z:uK\u0012L5\u000f\\1oIN\u0014V-\u00193zQ\r\t\u0014q\u0016\u0015\u0004\u0001\u0005=\u0006")
@InternalApi
public class IslandTracking {
    private final Map<IslandTag, Phase<Object>> phases;
    private final ActorMaterializerSettings settings;
    private final PhasedFusingActorMaterializer materializer;
    private final String islandNamePrefix;
    private int islandNameCounter;
    private int currentGlobalOffset;
    private int currentSegmentGlobalOffset;
    private int currentIslandGlobalOffset;
    private int currentIslandSkippedSlots;
    private ArrayList<SegmentInfo> segments;
    private ArrayList<PhaseIsland<Object>> activePhases;
    private ArrayList<ForwardWire> forwardWires;
    private ArrayList<SavedIslandData> islandStateStack;
    private PhaseIsland<Object> currentPhase;

    public Map<IslandTag, Phase<Object>> phases() {
        return this.phases;
    }

    public ActorMaterializerSettings settings() {
        return this.settings;
    }

    public PhasedFusingActorMaterializer materializer() {
        return this.materializer;
    }

    private int islandNameCounter() {
        return this.islandNameCounter;
    }

    private void islandNameCounter_$eq(int x$1) {
        this.islandNameCounter = x$1;
    }

    private String nextIslandName() {
        String s2 = new StringBuilder(0).append(this.islandNamePrefix).append(this.islandNameCounter()).toString();
        this.islandNameCounter_$eq(this.islandNameCounter() + 1);
        return s2;
    }

    private int currentGlobalOffset() {
        return this.currentGlobalOffset;
    }

    private void currentGlobalOffset_$eq(int x$1) {
        this.currentGlobalOffset = x$1;
    }

    private int currentSegmentGlobalOffset() {
        return this.currentSegmentGlobalOffset;
    }

    private void currentSegmentGlobalOffset_$eq(int x$1) {
        this.currentSegmentGlobalOffset = x$1;
    }

    private int currentIslandGlobalOffset() {
        return this.currentIslandGlobalOffset;
    }

    private void currentIslandGlobalOffset_$eq(int x$1) {
        this.currentIslandGlobalOffset = x$1;
    }

    private int currentIslandSkippedSlots() {
        return this.currentIslandSkippedSlots;
    }

    private void currentIslandSkippedSlots_$eq(int x$1) {
        this.currentIslandSkippedSlots = x$1;
    }

    private ArrayList<SegmentInfo> segments() {
        return this.segments;
    }

    private void segments_$eq(ArrayList<SegmentInfo> x$1) {
        this.segments = x$1;
    }

    private ArrayList<PhaseIsland<Object>> activePhases() {
        return this.activePhases;
    }

    private void activePhases_$eq(ArrayList<PhaseIsland<Object>> x$1) {
        this.activePhases = x$1;
    }

    private ArrayList<ForwardWire> forwardWires() {
        return this.forwardWires;
    }

    private void forwardWires_$eq(ArrayList<ForwardWire> x$1) {
        this.forwardWires = x$1;
    }

    private ArrayList<SavedIslandData> islandStateStack() {
        return this.islandStateStack;
    }

    private void islandStateStack_$eq(ArrayList<SavedIslandData> x$1) {
        this.islandStateStack = x$1;
    }

    private PhaseIsland<Object> currentPhase() {
        return this.currentPhase;
    }

    private void currentPhase_$eq(PhaseIsland<Object> x$1) {
        this.currentPhase = x$1;
    }

    @InternalApi
    public PhaseIsland<Object> getCurrentPhase() {
        return this.currentPhase();
    }

    @InternalApi
    public int getCurrentOffset() {
        return this.currentGlobalOffset();
    }

    private void completeSegment() {
        int length = this.currentGlobalOffset() - this.currentSegmentGlobalOffset();
        if (this.activePhases() == null) {
            this.activePhases_$eq(new ArrayList<PhaseIsland<Object>>(8));
            this.islandStateStack_$eq(new ArrayList<SavedIslandData>(4));
        }
        if (length > 0) {
            SegmentInfo previousSegment = new SegmentInfo(this.currentIslandGlobalOffset(), this.currentGlobalOffset() - this.currentSegmentGlobalOffset(), this.currentSegmentGlobalOffset(), this.currentSegmentGlobalOffset() - this.currentIslandGlobalOffset() - this.currentIslandSkippedSlots(), this.currentPhase());
            if (this.segments() == null) {
                this.segments_$eq(new ArrayList<SegmentInfo>(8));
            }
            this.segments().add(previousSegment);
            if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                Predef$.MODULE$.println(new StringBuilder(18).append("Completed segment ").append(previousSegment).toString());
                return;
            }
            return;
        }
        if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
            Predef$.MODULE$.println("Skipped zero length segment");
            return;
        }
    }

    @InternalApi
    public void enterIsland(IslandTag tag, Attributes attributes) {
        this.completeSegment();
        PhaseIsland<Object> previousPhase = this.currentPhase();
        int previousIslandOffset = this.currentIslandGlobalOffset();
        this.islandStateStack().add(new SavedIslandData(previousIslandOffset, this.currentGlobalOffset(), this.currentIslandSkippedSlots(), previousPhase));
        this.currentPhase_$eq(((Phase)this.phases().apply(tag)).apply(this.settings(), attributes, this.materializer(), this.nextIslandName()));
        this.activePhases().add(this.currentPhase());
        this.currentIslandGlobalOffset_$eq(this.currentGlobalOffset());
        this.currentSegmentGlobalOffset_$eq(this.currentGlobalOffset());
        this.currentIslandSkippedSlots_$eq(0);
        if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
            Predef$.MODULE$.println(new StringBuilder(46).append("Entering island starting at offset = ").append(this.currentIslandGlobalOffset()).append(" phase = ").append(this.currentPhase()).toString());
            return;
        }
    }

    @InternalApi
    public void exitIsland() {
        SavedIslandData parentIsland = this.islandStateStack().remove(this.islandStateStack().size() - 1);
        this.completeSegment();
        this.currentSegmentGlobalOffset_$eq(this.currentGlobalOffset());
        this.currentIslandGlobalOffset_$eq(parentIsland.islandGlobalOffset());
        this.currentPhase_$eq(parentIsland.phase());
        this.currentIslandSkippedSlots_$eq(parentIsland.skippedSlots() + (this.currentGlobalOffset() - parentIsland.lastVisitedOffset()));
        if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
            Predef$.MODULE$.println(new StringBuilder(47).append("Exited to island starting at offset = ").append(this.currentIslandGlobalOffset()).append(" phase = ").append(this.currentPhase()).toString());
            return;
        }
    }

    @InternalApi
    public void wireIn(InPort in, Object logic) {
        int localInSlot = this.currentGlobalOffset() - this.currentIslandGlobalOffset() - this.currentIslandSkippedSlots();
        if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
            Predef$.MODULE$.println(new StringBuilder(42).append("  wiring port ").append(in).append(" inOffs absolute = ").append(this.currentGlobalOffset()).append(" local = ").append(localInSlot).toString());
        }
        this.currentPhase().assignPort(in, localInSlot, logic);
        ForwardWire forwardWire = null;
        if (this.forwardWires() != null && !this.forwardWires().isEmpty()) {
            int i = 0;
            while (i < this.forwardWires().size()) {
                forwardWire = this.forwardWires().get(i);
                if (forwardWire.toGlobalOffset() == this.currentGlobalOffset()) {
                    if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                        Predef$.MODULE$.println(new StringBuilder(41).append("    there is a forward wire to this slot ").append(forwardWire).toString());
                    }
                    this.forwardWires().remove(i);
                    i = Integer.MAX_VALUE;
                    continue;
                }
                forwardWire = null;
                ++i;
            }
        }
        if (forwardWire != null) {
            if (forwardWire.phase() == this.currentPhase()) {
                if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                    Predef$.MODULE$.println(new StringBuilder(62).append("    in-island forward wiring from port ").append(forwardWire.from()).append(" wired to local slot = ").append(localInSlot).toString());
                }
                forwardWire.phase().assignPort(forwardWire.from(), localInSlot, forwardWire.outStage());
            } else {
                if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                    Predef$.MODULE$.println(new StringBuilder(65).append("    cross island forward wiring from port ").append(forwardWire.from()).append(" wired to local slot = ").append(localInSlot).toString());
                }
                Publisher<Object> publisher = forwardWire.phase().createPublisher(forwardWire.from(), forwardWire.outStage());
                this.currentPhase().takePublisher(localInSlot, publisher, null);
            }
        }
        this.currentGlobalOffset_$eq(this.currentGlobalOffset() + 1);
    }

    @InternalApi
    public void wireOut(OutPort out, int absoluteOffset, Object logic) {
        if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
            Predef$.MODULE$.println(new StringBuilder(24).append("  wiring ").append(out).append(" to absolute = ").append(absoluteOffset).toString());
        }
        if (absoluteOffset < this.currentGlobalOffset()) {
            if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                Predef$.MODULE$.println("    backward wiring");
            }
            if (absoluteOffset >= this.currentSegmentGlobalOffset()) {
                int localInSlot = absoluteOffset - this.currentIslandGlobalOffset() - this.currentIslandSkippedSlots();
                if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                    Predef$.MODULE$.println(new StringBuilder(42).append("    in-segment wiring to local (").append(absoluteOffset).append(" - ").append(this.currentIslandGlobalOffset()).append(" - ").append(this.currentIslandSkippedSlots()).append(") = ").append(localInSlot).toString());
                }
                this.currentPhase().assignPort(out, localInSlot, logic);
                return;
            }
            int i = this.segments().size() - 1;
            SegmentInfo targetSegment = this.segments().get(i);
            while (i > 0 && targetSegment.globalBaseOffset() > absoluteOffset) {
                targetSegment = this.segments().get(--i);
            }
            int distanceFromSegmentStart = absoluteOffset - targetSegment.globalBaseOffset();
            int localInSlot = distanceFromSegmentStart + targetSegment.relativeBaseOffset();
            if (targetSegment.phase() == this.currentPhase()) {
                if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                    Predef$.MODULE$.println(new StringBuilder(50).append("    cross-segment, in-island wiring to local slot ").append(localInSlot).toString());
                }
                this.currentPhase().assignPort(out, localInSlot, logic);
                return;
            }
            if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
                Predef$.MODULE$.println(new StringBuilder(55).append("    cross-island wiring to local slot ").append(localInSlot).append(" in target island").toString());
            }
            Publisher<Object> publisher = this.currentPhase().createPublisher(out, logic);
            targetSegment.phase().takePublisher(localInSlot, publisher, null);
            return;
        }
        if (this.forwardWires() == null) {
            this.forwardWires_$eq(new ArrayList<ForwardWire>(8));
        }
        ForwardWire forwardWire = new ForwardWire(this.currentIslandGlobalOffset(), out, absoluteOffset, logic, this.currentPhase());
        if (PhasedFusingActorMaterializer$.MODULE$.Debug()) {
            Predef$.MODULE$.println(new StringBuilder(33).append("    wiring is forward, recording ").append(forwardWire).toString());
        }
        this.forwardWires().add(forwardWire);
    }

    @InternalApi
    public void allNestedIslandsReady() {
        if (this.activePhases() != null) {
            for (int i = 0; i < this.activePhases().size(); ++i) {
                this.activePhases().get(i).onIslandReady();
            }
            return;
        }
    }

    public IslandTracking(Map<IslandTag, Phase<Object>> phases, ActorMaterializerSettings settings, Attributes attributes, Phase<Object> defaultPhase, PhasedFusingActorMaterializer materializer, String islandNamePrefix) {
        this.phases = phases;
        this.settings = settings;
        this.materializer = materializer;
        this.islandNamePrefix = islandNamePrefix;
        this.islandNameCounter = 0;
        this.currentGlobalOffset = 0;
        this.currentSegmentGlobalOffset = 0;
        this.currentIslandGlobalOffset = 0;
        this.currentIslandSkippedSlots = 0;
        this.currentPhase = defaultPhase.apply(settings, attributes, materializer, this.nextIslandName());
    }
}

