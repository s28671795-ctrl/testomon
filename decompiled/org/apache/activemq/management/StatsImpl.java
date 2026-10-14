/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.management;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.management.j2ee.statistics.Statistic;
import javax.management.j2ee.statistics.Stats;
import org.apache.activemq.management.Resettable;
import org.apache.activemq.management.StatisticImpl;

public class StatsImpl
extends StatisticImpl
implements Stats,
Resettable {
    private Set<StatisticImpl> set;

    public StatsImpl() {
        this(new CopyOnWriteArraySet<StatisticImpl>());
    }

    public StatsImpl(Set<StatisticImpl> set) {
        super("stats", "many", "Used only as container, not Statistic");
        this.set = set;
    }

    @Override
    public void reset() {
        for (Statistic stat : this.getStatistics()) {
            if (!(stat instanceof Resettable)) continue;
            Resettable r = (Resettable)((Object)stat);
            r.reset();
        }
    }

    @Override
    public Statistic getStatistic(String name) {
        for (StatisticImpl stat : this.set) {
            if (stat.getName() == null || !stat.getName().equals(name)) continue;
            return stat;
        }
        return null;
    }

    @Override
    public String[] getStatisticNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (StatisticImpl stat : this.set) {
            names.add(stat.getName());
        }
        String[] answer = new String[names.size()];
        names.toArray(answer);
        return answer;
    }

    @Override
    public Statistic[] getStatistics() {
        Statistic[] answer = new Statistic[this.set.size()];
        this.set.toArray(answer);
        return answer;
    }

    protected void addStatistic(String name, StatisticImpl statistic) {
        this.set.add(statistic);
    }
}

