/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.oracle;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.UDTRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.util.oracle.Queue;

public class OracleDSL
extends DSL {
    protected OracleDSL() {
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<Integer> rownum() {
        return DSL.rownum();
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<String> rowid() {
        return OracleDSL.field("rowid", String.class);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<Long> rowscn() {
        return OracleDSL.oraRowscn();
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<Long> oraRowscn() {
        return OracleDSL.field("ora_rowscn", Long.class);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<Timestamp> scnToTimestamp(Number scn) {
        return OracleDSL.scnToTimestamp(OracleDSL.val(scn));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<Timestamp> scnToTimestamp(Field<? extends Number> scn) {
        return OracleDSL.function("scn_to_timestamp", SQLDataType.TIMESTAMP, scn);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<Long> timestampToScn(Date timestamp) {
        return OracleDSL.timestampToScn(OracleDSL.val(timestamp));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<Long> timestampToScn(Field<? extends Date> scn) {
        return OracleDSL.function("timestamp_to_scn", SQLDataType.BIGINT, scn);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<String> sysContext(String namespace, String parameter) {
        return OracleDSL.function("sys_context", SQLDataType.VARCHAR, OracleDSL.val(namespace), OracleDSL.val(parameter));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<String> sysContext(String namespace, String parameter, int length) {
        return OracleDSL.function("sys_context", SQLDataType.VARCHAR, OracleDSL.val(namespace), OracleDSL.val(parameter), OracleDSL.val(length));
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<String> toChar(Object value) {
        return DSL.toChar(value);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<String> toChar(Field<?> value) {
        return DSL.toChar(value);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<String> toChar(Object value, String formatMask) {
        return DSL.toChar(value, formatMask);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<String> toChar(Object value, Field<String> formatMask) {
        return DSL.toChar(value, formatMask);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<String> toChar(Field<?> value, String formatMask) {
        return DSL.toChar(value, formatMask);
    }

    @Deprecated(forRemoval=true, since="3.14")
    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<String> toChar(Field<?> value, Field<String> formatMask) {
        return DSL.toChar(value, formatMask);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> toNumber(String value) {
        return OracleDSL.toNumber(OracleDSL.val(value));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> toNumber(Field<String> value) {
        return OracleDSL.function("to_number", BigDecimal.class, value);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> toNumber(String value, String formatMask) {
        return OracleDSL.toNumber(OracleDSL.val(value), OracleDSL.val(formatMask));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> toNumber(String value, Field<String> formatMask) {
        return OracleDSL.toNumber(OracleDSL.val(value), formatMask);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> toNumber(Field<String> value, String formatMask) {
        return OracleDSL.toNumber(value, OracleDSL.val(formatMask));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> toNumber(Field<String> value, Field<String> formatMask) {
        return OracleDSL.function("to_number", BigDecimal.class, value, formatMask);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<Long> versionsStartscn() {
        return OracleDSL.field("{versions_startscn}", Long.class);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<Timestamp> versionsStarttime() {
        return OracleDSL.field("{versions_starttime}", Timestamp.class);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<Long> versionsEndscn() {
        return OracleDSL.field("{versions_endscn}", Long.class);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<Timestamp> versionsEndtime() {
        return OracleDSL.field("{versions_endtime}", Timestamp.class);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<String> versionsXid() {
        return OracleDSL.field("{versions_xid}", String.class);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<String> versionsOperation() {
        return OracleDSL.field("{versions_operation}", String.class);
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> contains(Field<String> field2, String query) {
        return OracleDSL.function("contains", SQLDataType.NUMERIC, field2, OracleDSL.val(query));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> contains(Field<String> field2, String query, int label) {
        return OracleDSL.function("contains", SQLDataType.NUMERIC, field2, OracleDSL.val(query), OracleDSL.inline(label));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> matches(Field<String> field2, String query) {
        return OracleDSL.function("matches", SQLDataType.NUMERIC, field2, OracleDSL.val(query));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> matches(Field<String> field2, String query, int label) {
        return OracleDSL.function("matches", SQLDataType.NUMERIC, field2, OracleDSL.val(query), OracleDSL.inline(label));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> catsearch(Field<String> field2, String textQuery, String structuredQuery) {
        return OracleDSL.function("catsearch", SQLDataType.NUMERIC, field2, OracleDSL.val(textQuery), OracleDSL.val(structuredQuery));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> score(int label) {
        return OracleDSL.function("score", SQLDataType.NUMERIC, OracleDSL.inline(label));
    }

    @Support(value={SQLDialect.ORACLE})
    @NotNull
    public static Field<BigDecimal> matchScore(int label) {
        return OracleDSL.function("match_score", SQLDataType.NUMERIC, OracleDSL.inline(label));
    }

    public static final class DBMS_AQ {
        public static <R extends UDTRecord<R>> void enqueue(Configuration configuration, Queue<R> queue, R payload) {
            DBMS_AQ.enqueue(configuration, queue, payload, null, null);
        }

        public static <R extends UDTRecord<R>> void enqueue(Configuration configuration, Queue<R> queue, R payload, ENQUEUE_OPTIONS_T options, MESSAGE_PROPERTIES_T properties) {
            if (options == null) {
                options = new ENQUEUE_OPTIONS_T();
            }
            if (properties == null) {
                properties = new MESSAGE_PROPERTIES_T();
            }
            ArrayList<Object> bindings = new ArrayList<Object>();
            if (options.transformation != null) {
                bindings.add(options.transformation);
            }
            if (properties.correlation != null) {
                bindings.add(properties.correlation);
            }
            if (properties.delay != null) {
                bindings.add(properties.delay);
            }
            if (properties.enqueue_time != null) {
                bindings.add(properties.enqueue_time);
            }
            if (properties.exception_queue != null) {
                bindings.add(properties.exception_queue);
            }
            if (properties.expires != null) {
                bindings.add(properties.expires);
            }
            if (properties.priority != null) {
                bindings.add(properties.priority);
            }
            if (properties.state != null) {
                bindings.add(properties.state);
            }
            if (properties.transaction_group != null) {
                bindings.add(properties.transaction_group);
            }
            bindings.add(queue.name());
            bindings.add(payload);
            DSL.using(configuration).execute("DECLARE\n  v_msgid              RAW(16);\n  v_enqueue_options    DBMS_AQ.enqueue_options_t;\n  v_message_properties DBMS_AQ.message_properties_t;\nBEGIN" + (options.transformation == null ? "" : "\n  v_enqueue_options.transformation       := ?;") + (String)(options.delivery_mode == null ? "" : "\n  v_enqueue_options.delivery_mode        := DBMS_AQ." + options.delivery_mode.name() + ";") + (String)(options.sequence_deviation == null ? "" : "\n  v_enqueue_options.sequence_deviation   := DBMS_AQ." + options.sequence_deviation.name() + ";") + (String)(options.visibility == null ? "" : "\n  v_enqueue_options.visibility           := DBMS_AQ." + options.visibility.name() + ";") + (properties.correlation == null ? "" : "\n  v_message_properties.correlation       := ?;") + (properties.delay == null ? "" : "\n  v_message_properties.delay             := ?;") + (String)(properties.delivery_mode == null ? "" : "\n  v_message_properties.delivery_mode     := DBMS_AQ." + properties.delivery_mode.name() + ";") + (properties.enqueue_time == null ? "" : "\n  v_message_properties.enqueue_time      := ?;") + (properties.exception_queue == null ? "" : "\n  v_message_properties.exception_queue   := ?;") + (properties.expires == null ? "" : "\n  v_message_properties.expiration        := ?;") + (properties.priority == null ? "" : "\n  v_message_properties.priority          := ?;") + (properties.state == null ? "" : "\n  v_message_properties.state             := ?;") + (properties.transaction_group == null ? "" : "\n  v_message_properties.transaction_group := ?;") + "\n  DBMS_AQ.ENQUEUE(\n    queue_name         => ?,\n    enqueue_options    => v_enqueue_options,\n    message_properties => v_message_properties,\n    payload            => ?,\n    msgid              => v_msgid\n  );\nEND;", bindings.toArray());
        }

        public static <R extends UDTRecord<R>> Iterable<R> dequeueIterable(Configuration configuration, Queue<R> queue) {
            return DBMS_AQ.dequeueIterable(configuration, queue, null, null);
        }

        public static <R extends UDTRecord<R>> Iterable<R> dequeueIterable(Configuration configuration, Queue<R> queue, DEQUEUE_OPTIONS_T options) {
            return DBMS_AQ.dequeueIterable(configuration, queue, options, null);
        }

        public static <R extends UDTRecord<R>> Iterable<R> dequeueIterable(final Configuration configuration, final Queue<R> queue, final DEQUEUE_OPTIONS_T options, final MESSAGE_PROPERTIES_T properties) {
            Iterator it = new Iterator<R>(){

                @Override
                public boolean hasNext() {
                    return true;
                }

                @Override
                public R next() {
                    return DSL.using(configuration).transactionResult(c -> DBMS_AQ.dequeue(c, queue, options, properties));
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException("remove");
                }
            };
            return () -> it;
        }

        public static <R extends UDTRecord<R>> Stream<R> dequeueStream(Configuration configuration, Queue<R> queue) {
            return DBMS_AQ.dequeueStream(configuration, queue, null, null);
        }

        public static <R extends UDTRecord<R>> Stream<R> dequeueStream(Configuration configuration, Queue<R> queue, DEQUEUE_OPTIONS_T options) {
            return DBMS_AQ.dequeueStream(configuration, queue, options, null);
        }

        public static <R extends UDTRecord<R>> Stream<R> dequeueStream(Configuration configuration, Queue<R> queue, DEQUEUE_OPTIONS_T options, MESSAGE_PROPERTIES_T properties) {
            return Stream.generate(() -> DSL.using(configuration).transactionResult(c -> DBMS_AQ.dequeue(c, queue, options, properties)));
        }

        public static <R extends UDTRecord<R>> CompletionStage<R> dequeueAsync(Configuration configuration, Queue<R> queue) {
            return DBMS_AQ.dequeueAsync(configuration, queue, null, null);
        }

        public static <R extends UDTRecord<R>> CompletionStage<R> dequeueAsync(Configuration configuration, Queue<R> queue, DEQUEUE_OPTIONS_T options) {
            return DBMS_AQ.dequeueAsync(configuration, queue, options, null);
        }

        public static <R extends UDTRecord<R>> CompletionStage<R> dequeueAsync(Configuration configuration, Queue<R> queue, DEQUEUE_OPTIONS_T options, MESSAGE_PROPERTIES_T properties) {
            return DSL.using(configuration).transactionResultAsync(c -> DBMS_AQ.dequeue(c, queue, options, properties));
        }

        public static <R extends UDTRecord<R>> CompletionStage<R> dequeueAsync(Executor executor2, Configuration configuration, Queue<R> queue) {
            return DBMS_AQ.dequeueAsync(executor2, configuration, queue, null, null);
        }

        public static <R extends UDTRecord<R>> CompletionStage<R> dequeueAsync(Executor executor2, Configuration configuration, Queue<R> queue, DEQUEUE_OPTIONS_T options) {
            return DBMS_AQ.dequeueAsync(executor2, configuration, queue, options, null);
        }

        public static <R extends UDTRecord<R>> CompletionStage<R> dequeueAsync(Executor executor2, Configuration configuration, Queue<R> queue, DEQUEUE_OPTIONS_T options, MESSAGE_PROPERTIES_T properties) {
            return DSL.using(configuration).transactionResultAsync(executor2, c -> DBMS_AQ.dequeue(c, queue, options, properties));
        }

        public static <R extends UDTRecord<R>> R dequeue(Configuration configuration, Queue<R> queue) {
            return DBMS_AQ.dequeue(configuration, queue, null, null);
        }

        public static <R extends UDTRecord<R>> R dequeue(Configuration configuration, Queue<R> queue, DEQUEUE_OPTIONS_T options) {
            return DBMS_AQ.dequeue(configuration, queue, options, null);
        }

        public static <R extends UDTRecord<R>> R dequeue(Configuration configuration, Queue<R> queue, DEQUEUE_OPTIONS_T options, MESSAGE_PROPERTIES_T properties) {
            DEQUEUE_OPTIONS_T opt = options != null ? options : new DEQUEUE_OPTIONS_T();
            String sql = "DECLARE\n  v_msgid              RAW(16);\n  v_dequeue_options    DBMS_AQ.dequeue_options_t;\n  v_message_properties DBMS_AQ.message_properties_t;\nBEGIN" + (opt.consumer_name == null ? "" : "\n  v_dequeue_options.consumer_name        := ?;") + (opt.correlation == null ? "" : "\n  v_dequeue_options.correlation          := ?;") + (opt.deq_condition == null ? "" : "\n  v_dequeue_options.deq_condition        := ?;") + (opt.transformation == null ? "" : "\n  v_dequeue_options.transformation       := ?;") + (String)(opt.delivery_mode == null ? "" : "\n  v_dequeue_options.delivery_mode        := DBMS_AQ." + opt.delivery_mode.name() + ";") + (String)(opt.dequeue_mode == null ? "" : "\n  v_dequeue_options.dequeue_mode         := DBMS_AQ." + opt.dequeue_mode.name() + ";") + (String)(opt.navigation == null ? "" : "\n  v_dequeue_options.navigation           := DBMS_AQ." + opt.navigation.name() + ";") + (String)(opt.visibility == null ? "" : "\n  v_dequeue_options.visibility           := DBMS_AQ." + opt.visibility.name() + ";") + (opt.wait_seconds == null ? "" : "\n  v_dequeue_options.wait                 := ?;") + (String)(opt.wait_seconds != null || opt.wait == null ? "" : "\n  v_dequeue_options.wait                 := DBMS_AQ." + opt.wait.name() + ";") + "\n  DBMS_AQ.DEQUEUE(\n    queue_name         => ?,\n    dequeue_options    => v_dequeue_options,\n    message_properties => v_message_properties,\n    payload            => ?,\n    msgid              => v_msgid\n  );" + (properties == null ? "" : "\n  ? := v_message_properties.attempts;") + (properties == null ? "" : "\n  ? := v_message_properties.correlation;") + (properties == null ? "" : "\n  ? := v_message_properties.delay;") + (properties == null ? "" : "\n  ? := CASE v_message_properties.delivery_mode \n       WHEN DBMS_AQ.BUFFERED   THEN 'BUFFERED'\n       WHEN DBMS_AQ.PERSISTENT THEN 'PERSISTENT'\n                               ELSE 'PERSISTENT_OR_BUFFERED' END;") + (properties == null ? "" : "\n  ? := v_message_properties.enqueue_time;") + (properties == null ? "" : "\n  ? := v_message_properties.exception_queue;") + (properties == null ? "" : "\n  ? := v_message_properties.expiration;") + (properties == null ? "" : "\n  ? := v_message_properties.priority;") + (properties == null ? "" : "\n  ? := v_message_properties.state;") + (properties == null ? "" : "\n  ? := v_message_properties.transaction_group;") + "\nEND;";
            Configuration c = configuration.derive();
            c.data("org.jooq.workaround.issue13827", "true");
            return (R)DSL.using(c).connectionResult(connection -> {
                try (CallableStatement stmt = connection.prepareCall(sql);){
                    int i = 1;
                    if (opt.consumer_name != null) {
                        stmt.setString(i++, opt.consumer_name);
                    }
                    if (opt.correlation != null) {
                        stmt.setString(i++, opt.correlation);
                    }
                    if (opt.deq_condition != null) {
                        stmt.setString(i++, opt.deq_condition);
                    }
                    if (opt.transformation != null) {
                        stmt.setString(i++, opt.transformation);
                    }
                    if (opt.wait_seconds != null) {
                        stmt.setInt(i++, (int)opt.wait_seconds);
                    }
                    int payloadIndex = 1;
                    int propertiesIndex = 1;
                    stmt.setString(i++, queue.name());
                    payloadIndex = i++;
                    stmt.registerOutParameter(payloadIndex, 2002, queue.type().getName());
                    if (properties != null) {
                        propertiesIndex = i;
                        stmt.registerOutParameter(i++, 4);
                        stmt.registerOutParameter(i++, 12);
                        stmt.registerOutParameter(i++, 2);
                        stmt.registerOutParameter(i++, 12);
                        stmt.registerOutParameter(i++, 93);
                        stmt.registerOutParameter(i++, 12);
                        stmt.registerOutParameter(i++, 2);
                        stmt.registerOutParameter(i++, 4);
                        stmt.registerOutParameter(i++, 4);
                        stmt.registerOutParameter(i++, 12);
                    }
                    stmt.execute();
                    HashMap map = new HashMap();
                    map.put(queue.type().getName(), queue.type().getRecordType());
                    UDTRecord result = (UDTRecord)stmt.getObject(payloadIndex, map);
                    if (properties != null) {
                        properties.attempts = stmt.getInt(propertiesIndex++);
                        properties.correlation = stmt.getString(propertiesIndex++);
                        properties.delay = stmt.getBigDecimal(propertiesIndex++);
                        properties.delivery_mode = DELIVERY_MODE.valueOf(stmt.getString(propertiesIndex++));
                        properties.enqueue_time = stmt.getTimestamp(propertiesIndex++);
                        properties.exception_queue = stmt.getString(propertiesIndex++);
                        properties.expires = stmt.getBigDecimal(propertiesIndex++);
                        properties.priority = stmt.getInt(propertiesIndex++);
                        properties.state = stmt.getInt(propertiesIndex++);
                        properties.transaction_group = stmt.getString(propertiesIndex++);
                    }
                    UDTRecord uDTRecord = result;
                    return uDTRecord;
                }
            });
        }

        private DBMS_AQ() {
        }

        public static final class ENQUEUE_OPTIONS_T {
            public VISIBILITY visibility;
            public SEQUENCE_DEVIATION sequence_deviation;
            public String transformation;
            public DELIVERY_MODE delivery_mode;

            public ENQUEUE_OPTIONS_T visibility(VISIBILITY newValue) {
                this.visibility = newValue;
                return this;
            }

            public ENQUEUE_OPTIONS_T sequence_deviation(SEQUENCE_DEVIATION newValue) {
                this.sequence_deviation = newValue;
                return this;
            }

            public ENQUEUE_OPTIONS_T transformation(String newValue) {
                this.transformation = newValue;
                return this;
            }

            public ENQUEUE_OPTIONS_T delivery_mode(DELIVERY_MODE newValue) {
                this.delivery_mode = newValue;
                return this;
            }
        }

        public static final class MESSAGE_PROPERTIES_T {
            public Integer priority;
            public BigDecimal delay;
            public BigDecimal expiration;
            public String correlation;
            public Integer attempts;
            public String exception_queue;
            public Timestamp enqueue_time;
            public Integer state;
            public String transaction_group;
            public DELIVERY_MODE delivery_mode;
            @Deprecated
            public BigDecimal expires;

            public MESSAGE_PROPERTIES_T priority(Integer newValue) {
                this.priority = newValue;
                return this;
            }

            public MESSAGE_PROPERTIES_T delay(BigDecimal newValue) {
                this.delay = newValue;
                return this;
            }

            public MESSAGE_PROPERTIES_T expiration(BigDecimal newValue) {
                this.expires = newValue;
                this.expiration = newValue;
                return this;
            }

            public MESSAGE_PROPERTIES_T correlation(String newValue) {
                this.correlation = newValue;
                return this;
            }

            public MESSAGE_PROPERTIES_T attempts(Integer newValue) {
                this.attempts = newValue;
                return this;
            }

            public MESSAGE_PROPERTIES_T exception_queue(String newValue) {
                this.exception_queue = newValue;
                return this;
            }

            public MESSAGE_PROPERTIES_T enqueue_time(Timestamp newValue) {
                this.enqueue_time = newValue;
                return this;
            }

            public MESSAGE_PROPERTIES_T state(Integer newValue) {
                this.state = newValue;
                return this;
            }

            public MESSAGE_PROPERTIES_T transaction_group(String newValue) {
                this.transaction_group = newValue;
                return this;
            }

            public MESSAGE_PROPERTIES_T delivery_mode(DELIVERY_MODE newValue) {
                this.delivery_mode = newValue;
                return this;
            }

            @Deprecated
            public MESSAGE_PROPERTIES_T expires(BigDecimal newValue) {
                this.expires = newValue;
                this.expiration = newValue;
                return this;
            }
        }

        public static enum DELIVERY_MODE {
            BUFFERED,
            PERSISTENT,
            PERSISTENT_OR_BUFFERED;

        }

        public static enum SEQUENCE_DEVIATION {
            BEFORE,
            TOP;

        }

        public static enum VISIBILITY {
            ON_COMMIT,
            IMMEDIATE;

        }

        public static final class DEQUEUE_OPTIONS_T {
            public String consumer_name;
            public DEQUEUE_MODE dequeue_mode;
            public NAVIGATION navigation;
            public VISIBILITY visibility;
            public WAIT wait;
            public Integer wait_seconds;
            public String correlation;
            public String deq_condition;
            public String transformation;
            public DELIVERY_MODE delivery_mode;

            public DEQUEUE_OPTIONS_T consumer_name(String newValue) {
                this.consumer_name = newValue;
                return this;
            }

            public DEQUEUE_OPTIONS_T dequeue_mode(DEQUEUE_MODE newValue) {
                this.dequeue_mode = newValue;
                return this;
            }

            public DEQUEUE_OPTIONS_T navigation(NAVIGATION newValue) {
                this.navigation = newValue;
                return this;
            }

            public DEQUEUE_OPTIONS_T visibility(VISIBILITY newValue) {
                this.visibility = newValue;
                return this;
            }

            public DEQUEUE_OPTIONS_T wait(WAIT newValue) {
                this.wait = newValue;
                this.wait_seconds = null;
                return this;
            }

            public DEQUEUE_OPTIONS_T wait(int newValue) {
                this.wait = null;
                this.wait_seconds = newValue;
                return this;
            }

            public DEQUEUE_OPTIONS_T correlation(String newValue) {
                this.correlation = newValue;
                return this;
            }

            public DEQUEUE_OPTIONS_T deq_condition(String newValue) {
                this.deq_condition = newValue;
                return this;
            }

            public DEQUEUE_OPTIONS_T transformation(String newValue) {
                this.transformation = newValue;
                return this;
            }

            public DEQUEUE_OPTIONS_T delivery_mode(DELIVERY_MODE newValue) {
                this.delivery_mode = newValue;
                return this;
            }
        }

        public static enum DEQUEUE_MODE {
            BROWSE,
            LOCKED,
            REMOVE,
            REMOVE_NODATA;

        }

        public static enum NAVIGATION {
            NEXT_MESSAGE,
            NEXT_TRANSACTION,
            NEXT_MESSAGE_MULTI_GROUP,
            FIRST_MESSAGE,
            FIRST_MESSAGE_MULTI_GROUP;

        }

        public static enum WAIT {
            FOREVER,
            NO_WAIT;

        }
    }
}

