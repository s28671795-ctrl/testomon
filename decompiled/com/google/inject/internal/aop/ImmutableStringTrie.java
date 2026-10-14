/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.aop;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.ToIntFunction;

final class ImmutableStringTrie
implements ToIntFunction<String> {
    private static final char LEAF_MARKER = '\u8000';
    private static final char BUD_MARKER = '\u4000';
    private static final int MAX_ROWS_PER_TRIE = 16384;
    private final char[] trie;

    private static int singletonTrie(String key) {
        return 0;
    }

    @Override
    public int applyAsInt(String key) {
        char branchCount;
        int branchIndex;
        int keyLength = key.length();
        int keyIndex = 0;
        int dataIndex = 0;
        char[] data = this.trie;
        while (keyIndex < keyLength && (branchIndex = Arrays.binarySearch(data, dataIndex, dataIndex + (branchCount = data[dataIndex++]), key.charAt(keyIndex))) >= 0) {
            int resultIndex = branchIndex + branchCount;
            char result = data[resultIndex];
            if ((result & 0x8000) != 0) {
                return result & 0xFFFF7FFF;
            }
            if ((result & 0x4000) != 0) {
                if (keyIndex == keyLength - 1) {
                    return result & 0xFFFFBFFF;
                }
                result = '\u0001';
            }
            keyIndex += result;
            if (branchIndex > dataIndex) {
                int jumpIndex = resultIndex + branchCount - 1;
                dataIndex += data[jumpIndex];
            }
            dataIndex += branchCount * 3 - 1;
        }
        return -1;
    }

    public static ToIntFunction<String> buildTrie(Collection<String> table) {
        int numRows = table.size();
        if (numRows > 1) {
            return ImmutableStringTrie.buildTrie(new StringBuilder(), table.toArray(new String[numRows]), 0, numRows);
        }
        return ImmutableStringTrie::singletonTrie;
    }

    private static ToIntFunction<String> buildTrie(StringBuilder buf, String[] table, int row, int rowLimit) {
        int trieLimit = row + 16384;
        if (rowLimit <= trieLimit) {
            ImmutableStringTrie.buildSubTrie(buf, table, 0, row, rowLimit);
            char[] data = new char[buf.length()];
            buf.getChars(0, data.length, data, 0);
            return new ImmutableStringTrie(data);
        }
        ImmutableStringTrie.buildSubTrie(buf, table, 0, row, trieLimit);
        char[] data = new char[buf.length()];
        buf.getChars(0, data.length, data, 0);
        buf.setLength(0);
        return new Overflow(data, table[trieLimit], ImmutableStringTrie.buildTrie(buf, table, trieLimit, rowLimit));
    }

    ImmutableStringTrie(char[] data) {
        this.trie = data;
    }

    private static void buildSubTrie(StringBuilder buf, String[] table, int column, int row, int rowLimit) {
        int trieStart = buf.length();
        int prevRow = row;
        int branchCount = 0;
        int nextJump = 0;
        boolean allLeaves = true;
        while (prevRow < rowLimit) {
            String cells = table[prevRow];
            int columnLimit = cells.length();
            char pivot = cells.charAt(column);
            int nextRow = ImmutableStringTrie.nextPivotRow(table, pivot, column, prevRow, rowLimit);
            int nextColumn = ImmutableStringTrie.nextPivotColumn(table, column, prevRow, nextRow);
            if (nextColumn == columnLimit && nextColumn - column > 1 && nextRow - prevRow > 1) {
                --nextColumn;
            }
            int branchIndex = trieStart + branchCount;
            buf.insert(branchIndex, pivot);
            int resultIndex = branchIndex + 1 + branchCount;
            int subTrieStart = buf.length() + 1;
            if (nextColumn < columnLimit) {
                buf.insert(resultIndex, (char)(nextColumn - column));
                ImmutableStringTrie.buildSubTrie(buf, table, nextColumn, prevRow, nextRow);
                allLeaves = false;
            } else {
                ImmutableStringTrie.buildSubTrie(buf, table, nextColumn, prevRow + 1, nextRow);
                boolean isLeaf = subTrieStart > buf.length();
                int marker = isLeaf ? 32768 : 16384;
                buf.insert(resultIndex, (char)(prevRow & 0x3FFF | marker));
                boolean bl = allLeaves = allLeaves && isLeaf;
            }
            if (nextRow < rowLimit) {
                int jumpIndex = resultIndex + 1 + branchCount;
                buf.insert(jumpIndex, (char)(nextJump += buf.length() - subTrieStart));
            }
            prevRow = nextRow;
            ++branchCount;
        }
        if (branchCount > 0) {
            buf.insert(trieStart, (char)branchCount);
            if (allLeaves) {
                int jumpStart = trieStart + 1 + branchCount * 2;
                buf.delete(jumpStart, jumpStart + branchCount);
            }
        }
    }

    private static int nextPivotRow(String[] table, char pivot, int column, int row, int rowLimit) {
        for (int r = row + 1; r < rowLimit; ++r) {
            String cells = table[r];
            if (cells.length() > column && cells.charAt(column) == pivot) continue;
            return r;
        }
        return rowLimit;
    }

    private static int nextPivotColumn(String[] table, int column, int row, int rowLimit) {
        String cells = table[row];
        int columnLimit = cells.length();
        for (int c = column + 1; c < columnLimit; ++c) {
            if (ImmutableStringTrie.nextPivotRow(table, cells.charAt(c), c, row, rowLimit) >= rowLimit) continue;
            return c;
        }
        return columnLimit;
    }

    private static final class Overflow
    implements ToIntFunction<String> {
        private final ImmutableStringTrie trie;
        private final String overflowKey;
        private final ToIntFunction<String> next;

        Overflow(char[] data, String overflowKey, ToIntFunction<String> next) {
            this.trie = new ImmutableStringTrie(data);
            this.overflowKey = overflowKey;
            this.next = next;
        }

        @Override
        public int applyAsInt(String key) {
            return key.compareTo(this.overflowKey) < 0 ? this.trie.applyAsInt(key) : 16384 + this.next.applyAsInt(key);
        }
    }
}

