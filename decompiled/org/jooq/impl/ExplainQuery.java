/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.DSLContext;
import org.jooq.Explain;
import org.jooq.Field;
import org.jooq.Query;
import org.jooq.Record;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.conf.ParamType;
import org.jooq.conf.StatementType;

final class ExplainQuery {
    ExplainQuery() {
    }

    static Explain explain(DSLContext ctx, Query query) {
        Result<Record> result = null;
        switch (ctx.family()) {
            case ORACLE: {
                result = ctx.connectionResult(connection -> {
                    try (CallableStatement c = connection.prepareCall("DECLARE  r sys_refcursor;BEGIN  EXECUTE IMMEDIATE 'EXPLAIN PLAN FOR " + ((RenderContext)((RenderContext)((RenderContext)ctx.renderContext().paramType(ParamType.NAMED)).stringLiteral(true)).visit(query)).render() + "';  OPEN r FOR SELECT * FROM TABLE (dbms_xplan.display);  ? := r;END;");){
                        c.registerOutParameter(1, -10);
                        c.execute();
                        Result<Record> result = ctx.fetch((ResultSet)c.getObject(1));
                        return result;
                    }
                });
                break;
            }
            case H2: {
                result = ctx.fetch("{explain analyze} {0}", query);
                break;
            }
            case HSQLDB: {
                result = ctx.configuration().deriveSettings(s2 -> s2.withStatementType(StatementType.STATIC_STATEMENT)).dsl().fetch("{explain plan for} {0}", query);
                break;
            }
            default: {
                result = ctx.fetch("{explain} {0}", query);
            }
        }
        double cost = Double.NaN;
        double rows = Double.NaN;
        switch (ctx.family()) {
            case ORACLE: {
                StringBuilder sb = new StringBuilder();
                for (Record record : result) {
                    String row = record.get(0, String.class);
                    if (row.startsWith("-") || row.startsWith("|")) {
                        sb.append(row).append("\n");
                        continue;
                    }
                    if (sb.length() <= 0) continue;
                    break;
                }
                Result<Record> plan = ctx.fetchFromTXT(sb.toString());
                for (Field<?> field2 : plan.fields()) {
                    if (!field2.getName().startsWith("Cost")) continue;
                    cost = Double.parseDouble(((Record)plan.get(0)).get(field2, String.class).replaceAll("(\\d+).*", "$1"));
                }
                rows = ((Record)plan.get(0)).get("Rows", Double.TYPE);
            }
            case H2: 
            case HSQLDB: {
                break;
            }
            case MARIADB: 
            case MYSQL: {
                rows = ((Record)result.get(0)).get("rows", Double.TYPE);
                break;
            }
            case AURORA_POSTGRES: 
            case COCKROACHDB: 
            case POSTGRES: 
            case YUGABYTEDB: {
                Matcher matcher = Pattern.compile(".*\\bcost=\\d+\\.\\d+\\.\\.(\\d+\\.\\d+)\\s+rows=(\\d+).*").matcher(((Record)result.get(0)).get(0, String.class));
                cost = Double.parseDouble(matcher.replaceAll("$1"));
                rows = Double.parseDouble(matcher.replaceAll("$2"));
                break;
            }
        }
        return new ExplainImpl(rows, cost, result.format());
    }

    private record ExplainImpl(double rows, double cost, String plan) implements Explain
    {
        @Override
        public String toString() {
            return String.format("Explain [cost=%.2f, rows=%.2f]\n\n", this.cost, this.rows) + this.plan;
        }
    }
}

