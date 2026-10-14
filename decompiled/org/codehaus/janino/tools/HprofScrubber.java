/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.codehaus.commons.nullanalysis.Nullable;

public final class HprofScrubber {
    private HprofScrubber() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void main(String[] args2) throws Exception {
        String fileName = args2.length == 0 ? "java.hprof.txt" : args2[0];
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            HashMap<Integer, String[]> traces = new HashMap<Integer, String[]>();
            ArrayList<Site> sites = new ArrayList<Site>();
            ArrayList<Sample> samples = new ArrayList<Sample>();
            String s2 = br.readLine();
            while (s2 != null) {
                if (s2.startsWith("SITES BEGIN")) {
                    br.readLine();
                    br.readLine();
                    while (!(s2 = br.readLine()).startsWith("SITES END")) {
                        StringTokenizer st = new StringTokenizer(s2);
                        st.nextToken();
                        st.nextToken();
                        st.nextToken();
                        st.nextToken();
                        st.nextToken();
                        sites.add(new Site(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), st.nextToken()));
                    }
                    continue;
                }
                if (s2.startsWith("TRACE ") && s2.endsWith(":")) {
                    int traceNumber = Integer.parseInt(s2.substring(6, s2.length() - 1));
                    ArrayList<String> l = new ArrayList<String>();
                    while ((s2 = br.readLine()).startsWith("\t")) {
                        l.add(s2.substring(1));
                    }
                    traces.put(new Integer(traceNumber), l.toArray(new String[l.size()]));
                    continue;
                }
                if (s2.startsWith("CPU SAMPLES BEGIN")) {
                    br.readLine();
                    while (!(s2 = br.readLine()).startsWith("CPU SAMPLES END")) {
                        StringTokenizer st = new StringTokenizer(s2);
                        st.nextToken();
                        st.nextToken();
                        st.nextToken();
                        int count = Integer.parseInt(st.nextToken());
                        if (count == 0) continue;
                        int trace = Integer.parseInt(st.nextToken());
                        samples.add(new Sample(count, trace));
                    }
                    continue;
                }
                s2 = br.readLine();
            }
            HprofScrubber.dumpSites(sites.toArray(new Site[sites.size()]), traces);
            HprofScrubber.dumpSamples(samples.toArray(new Sample[samples.size()]), traces);
        }
        finally {
            try {
                br.close();
            }
            catch (IOException iOException) {}
        }
    }

    private static void dumpSites(Site[] ss, Map<Integer, String[]> traces) {
        Arrays.sort(ss, new Comparator<Object>(){

            @Override
            public int compare(@Nullable Object o1, @Nullable Object o2) {
                assert (o1 != null);
                assert (o2 != null);
                return ((Site)o2).allocatedBytes - ((Site)o1).allocatedBytes;
            }
        });
        int totalAllocatedBytes = 0;
        int totalAllocatedObjects = 0;
        for (Site site : ss) {
            totalAllocatedBytes += site.allocatedBytes;
            totalAllocatedObjects += site.allocatedObjects;
        }
        System.out.println("          percent          alloc'ed");
        System.out.println("rank   self  accum      bytes  objects  class name");
        System.out.println("Total:              " + totalAllocatedBytes + "  " + totalAllocatedObjects);
        double accumulatedPercentage = 0.0;
        MessageFormat mf = new MessageFormat("{0,number,00000} {1,number,00.00}% {2,number,00.00}% {3,number,000000000} {4,number,000000000} {5}");
        for (int i = 0; i < ss.length; ++i) {
            Site site = ss[i];
            double selfPercentage = 100.0 * ((double)site.allocatedBytes / (double)totalAllocatedBytes);
            System.out.println(mf.format(new Object[]{new Integer(i + 1), new Double(selfPercentage), new Double(accumulatedPercentage += selfPercentage), new Integer(site.allocatedBytes), new Integer(site.allocatedObjects), site.className}, new StringBuffer(), new FieldPosition(0)));
            String[] stackFrames = traces.get(new Integer(site.traceNumber));
            if (stackFrames == null) continue;
            for (String stackFrame : stackFrames) {
                System.out.println("                           " + stackFrame);
            }
        }
    }

    private static void dumpSamples(Sample[] ss, Map<Integer, String[]> traces) {
        int totalCount = 0;
        for (Sample s2 : ss) {
            totalCount += s2.count;
        }
        System.out.println("          percent");
        System.out.println("rank   self  accum      count");
        System.out.println("Total:              " + totalCount);
        double accumulatedPercentage = 0.0;
        MessageFormat mf = new MessageFormat("{0,number,00000} {1,number,00.00}% {2,number,00.00}% {3,number,000000000}");
        for (int i = 0; i < ss.length; ++i) {
            Sample sample = ss[i];
            double selfPercentage = 100.0 * ((double)sample.count / (double)totalCount);
            System.out.println(mf.format(new Object[]{new Integer(i + 1), new Double(selfPercentage), new Double(accumulatedPercentage += selfPercentage), new Integer(sample.count)}, new StringBuffer(), new FieldPosition(0)));
            String[] stackFrames = traces.get(new Integer(sample.traceNumber));
            if (stackFrames == null) continue;
            for (String stackFrame : stackFrames) {
                System.out.println("                           " + stackFrame);
            }
        }
    }

    private static class Sample {
        public final int count;
        public final int traceNumber;

        Sample(int count, int traceNumber) {
            this.count = count;
            this.traceNumber = traceNumber;
        }
    }

    private static class Site {
        public final int allocatedBytes;
        public final int allocatedObjects;
        public final int traceNumber;
        public final String className;

        Site(int allocatedBytes, int allocatedObjects, int traceNumber, String className) {
            this.allocatedBytes = allocatedBytes;
            this.allocatedObjects = allocatedObjects;
            this.traceNumber = traceNumber;
            this.className = className;
        }
    }
}

