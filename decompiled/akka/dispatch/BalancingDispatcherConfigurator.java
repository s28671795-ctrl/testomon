/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.BalancingDispatcher;
import akka.dispatch.BalancingDispatcherConfigurator$;
import akka.dispatch.DispatcherPrerequisites;
import akka.dispatch.MailboxType;
import akka.dispatch.Mailboxes;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.MessageDispatcherConfigurator;
import akka.dispatch.MultipleConsumerSemantics;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import com.typesafe.config.Config;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E;aAD\b\t\u0002E\u0019bAB\u000b\u0010\u0011\u0003\tb\u0003C\u0003\u001e\u0003\u0011\u0005q\u0004C\u0004!\u0003\t\u0007I\u0011B\u0011\t\r1\n\u0001\u0015!\u0003#\u0011\u0015i\u0013\u0001\"\u0001/\r\u0011)r\u0002\u0001\u0019\t\u0011Q2!\u0011!Q\u0001\n\tB\u0011\"\u000e\u0004\u0003\u0002\u0003\u0006IAN\u001d\t\u000bu1A\u0011A\u001e\t\u000f}2!\u0019!C\u0005\u0001\"1AI\u0002Q\u0001\n\u0005CQ!\u0012\u0004\u0005\u0012\u0019CQ\u0001\u0014\u0004\u0005B5\u000bqDQ1mC:\u001c\u0017N\\4ESN\u0004\u0018\r^2iKJ\u001cuN\u001c4jOV\u0014\u0018\r^8s\u0015\t\u0001\u0012#\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005\u0011\u0012\u0001B1lW\u0006\u0004\"\u0001F\u0001\u000e\u0003=\u0011qDQ1mC:\u001c\u0017N\\4ESN\u0004\u0018\r^2iKJ\u001cuN\u001c4jOV\u0014\u0018\r^8s'\t\tq\u0003\u0005\u0002\u001975\t\u0011DC\u0001\u001b\u0003\u0015\u00198-\u00197b\u0013\ta\u0012D\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t1#\u0001\neK\u001a\fW\u000f\u001c;SKF,\u0018N]3nK:$X#\u0001\u0012\u0011\u0005\rRS\"\u0001\u0013\u000b\u0005\u00152\u0013AB2p]\u001aLwM\u0003\u0002(Q\u0005AA/\u001f9fg\u00064WMC\u0001*\u0003\r\u0019w.\\\u0005\u0003W\u0011\u0012aaQ8oM&<\u0017a\u00053fM\u0006,H\u000e\u001e*fcVL'/Z7f]R\u0004\u0013aC1nK:$7i\u001c8gS\u001e$\"AI\u0018\t\u000b\u0015*\u0001\u0019\u0001\u0012\u0014\u0005\u0019\t\u0004C\u0001\u000b3\u0013\t\u0019tBA\u000fNKN\u001c\u0018mZ3ESN\u0004\u0018\r^2iKJ\u001cuN\u001c4jOV\u0014\u0018\r^8s\u0003\u001dy6m\u001c8gS\u001e\fab\u00189sKJ,\u0017/^5tSR,7\u000f\u0005\u0002\u0015o%\u0011\u0001h\u0004\u0002\u0018\t&\u001c\b/\u0019;dQ\u0016\u0014\bK]3sKF,\u0018n]5uKNL!A\u000f\u001a\u0002\u001bA\u0014XM]3rk&\u001c\u0018\u000e^3t)\raTH\u0010\t\u0003)\u0019AQ\u0001N\u0005A\u0002\tBQ!N\u0005A\u0002Y\n\u0001\"\u001b8ti\u0006t7-Z\u000b\u0002\u0003B\u0011ACQ\u0005\u0003\u0007>\u00111CQ1mC:\u001c\u0017N\\4ESN\u0004\u0018\r^2iKJ\f\u0011\"\u001b8ti\u0006t7-\u001a\u0011\u0002\r\r\u0014X-\u0019;f)\t\tu\tC\u0003I\u0019\u0001\u0007\u0011*A\u0006nC&d'm\u001c=UsB,\u0007C\u0001\u000bK\u0013\tYuBA\u0006NC&d'm\u001c=UsB,\u0017A\u00033jgB\fGo\u00195feR\ta\n\u0005\u0002\u0015\u001f&\u0011\u0001k\u0004\u0002\u0012\u001b\u0016\u001c8/Y4f\t&\u001c\b/\u0019;dQ\u0016\u0014\b")
public class BalancingDispatcherConfigurator
extends MessageDispatcherConfigurator {
    private final BalancingDispatcher instance;

    public static Config amendConfig(Config config) {
        return BalancingDispatcherConfigurator$.MODULE$.amendConfig(config);
    }

    private BalancingDispatcher instance() {
        return this.instance;
    }

    public BalancingDispatcher create(MailboxType mailboxType) {
        return new BalancingDispatcher(this, this.config().getString("id"), this.config().getInt("throughput"), Helpers$ConfigOps$.MODULE$.getNanosDuration$extension(Helpers$.MODULE$.ConfigOps(this.config()), "throughput-deadline-time"), mailboxType, this.configureExecutor(), Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(this.config()), "shutdown-timeout"), this.config().getBoolean("attempt-teamwork"));
    }

    @Override
    public MessageDispatcher dispatcher() {
        return this.instance();
    }

    public BalancingDispatcherConfigurator(Config _config, DispatcherPrerequisites _prerequisites) {
        super(BalancingDispatcherConfigurator$.MODULE$.amendConfig(_config), _prerequisites);
        MailboxType mailboxType;
        Mailboxes mailboxes = this.prerequisites().mailboxes();
        String id = this.config().getString("id");
        Class<?> requirement = mailboxes.getMailboxRequirement(this.config());
        if (!MultipleConsumerSemantics.class.isAssignableFrom(requirement)) {
            throw new IllegalArgumentException(new StringBuilder(130).append("BalancingDispatcher must have 'mailbox-requirement' which implements akka.dispatch.MultipleConsumerSemantics; ").append("dispatcher [").append(id).append("] has [").append(requirement).append("]").toString());
        }
        if (this.config().hasPath("mailbox")) {
            MailboxType mt = mailboxes.lookup(this.config().getString("mailbox"));
            if (!requirement.isAssignableFrom(mailboxes.getProducedMessageQueueType(mt))) {
                throw new IllegalArgumentException(new StringBuilder(91).append("BalancingDispatcher [").append(id).append("] has 'mailbox' [").append(mt.getClass()).append("] which is incompatible with 'mailbox-requirement' [").append(requirement).append("]").toString());
            }
            mailboxType = mt;
        } else if (this.config().hasPath("mailbox-type")) {
            MailboxType mt = mailboxes.lookup(id);
            if (!requirement.isAssignableFrom(mailboxes.getProducedMessageQueueType(mt))) {
                throw new IllegalArgumentException(new StringBuilder(96).append("BalancingDispatcher [").append(id).append("] has 'mailbox-type' [").append(mt.getClass()).append("] which is incompatible with 'mailbox-requirement' [").append(requirement).append("]").toString());
            }
            mailboxType = mt;
        } else {
            mailboxType = mailboxes.lookupByQueueType(requirement);
        }
        MailboxType mailboxType2 = mailboxType;
        this.instance = this.create(mailboxType2);
    }
}

