/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.building;

import java.util.List;
import org.apache.maven.building.DefaultProblemCollector;
import org.apache.maven.building.Problem;
import org.apache.maven.building.ProblemCollector;

public class ProblemCollectorFactory {
    public static ProblemCollector newInstance(List<Problem> problems) {
        return new DefaultProblemCollector(problems);
    }
}

