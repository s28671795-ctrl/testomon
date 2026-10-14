/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.UniversalDERTag;

public class DERPath {
    public static final String PATH_SEPARATOR = "/";
    private static final Pattern NODE_PATTERN;
    private static final int HASH_CODE_SEED = 601;
    private final Deque<Node> nodeStack = new ArrayDeque<Node>();

    public DERPath() {
        this(PATH_SEPARATOR);
    }

    public DERPath(DERPath path) {
        this.nodeStack.addAll(path.nodeStack);
    }

    public DERPath(String pathSpec) {
        String[] nodes;
        for (String node : nodes = pathSpec.split(PATH_SEPARATOR)) {
            if ("".equals(node)) continue;
            this.nodeStack.add(DERPath.toNode(node.toUpperCase()));
        }
    }

    public DERPath pushNode(String name) {
        this.nodeStack.addLast(new Node(name));
        return this;
    }

    public DERPath pushNode(String name, int index) {
        this.nodeStack.addLast(new Node(name, index));
        return this;
    }

    public String peekNode() {
        return this.nodeStack.peek().toString();
    }

    public String popNode() {
        if (this.nodeStack.isEmpty()) {
            return null;
        }
        return this.nodeStack.removeLast().toString();
    }

    public int getSize() {
        return this.nodeStack.size();
    }

    public boolean isEmpty() {
        return this.nodeStack.isEmpty();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof DERPath) {
            DERPath v = (DERPath)o;
            return LdapUtils.areEqual(this.nodeStack != null ? this.nodeStack.toArray() : null, v.nodeStack != null ? v.nodeStack.toArray() : null);
        }
        return false;
    }

    public int hashCode() {
        int hc = 601;
        if (this.nodeStack != null && !this.nodeStack.isEmpty()) {
            for (Node n : this.nodeStack) {
                hc = 601 * hc + n.hashCode();
            }
        }
        return hc;
    }

    public String toString() {
        if (this.nodeStack == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(this.nodeStack.size() * 10);
        for (Node node : this.nodeStack) {
            sb.append(PATH_SEPARATOR);
            node.toString(sb);
        }
        return sb.toString();
    }

    static Node toNode(String node) {
        Matcher matcher = NODE_PATTERN.matcher(node);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid node: " + node);
        }
        String name = matcher.group(1);
        String index = matcher.group(2);
        if (index != null) {
            return new Node(name, Integer.parseInt(index));
        }
        return new Node(name);
    }

    static {
        StringBuilder validNames = new StringBuilder();
        validNames.append("APP").append("\\(\\d+\\)|");
        validNames.append("CTX").append("\\(\\d+\\)|");
        for (UniversalDERTag tag : UniversalDERTag.values()) {
            validNames.append('|').append(tag.name());
        }
        NODE_PATTERN = Pattern.compile(String.format("(%s)(?:\\[(\\d+)\\])?", validNames));
    }

    static class Node {
        private static final int HASH_CODE_SEED = 607;
        private final String name;
        private final int childIndex;

        Node(String n) {
            this.name = n;
            this.childIndex = -1;
        }

        Node(String n, int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Child index cannot be negative.");
            }
            this.name = n;
            this.childIndex = i;
        }

        public String getName() {
            return this.name;
        }

        public int getChildIndex() {
            return this.childIndex;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Node) {
                Node v = (Node)o;
                return LdapUtils.areEqual(this.name, v.name) && LdapUtils.areEqual(this.childIndex, v.childIndex);
            }
            return false;
        }

        public int hashCode() {
            int result = 607 + (this.name == null ? 0 : this.name.hashCode());
            result = 607 * result + this.childIndex;
            return result;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(this.name.length() + 4);
            this.toString(sb);
            return sb.toString();
        }

        public void toString(StringBuilder builder) {
            builder.append(this.name);
            if (this.childIndex < 0) {
                return;
            }
            builder.append('[').append(this.childIndex).append(']');
        }
    }
}

