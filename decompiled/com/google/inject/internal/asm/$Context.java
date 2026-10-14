/*
 * Decompiled with CFR 0.152.
 */
package com.google.inject.internal.asm;

import com.google.inject.internal.asm.$Attribute;
import com.google.inject.internal.asm.$Label;
import com.google.inject.internal.asm.$TypePath;

final class $Context {
    $Attribute[] attributePrototypes;
    int parsingOptions;
    char[] charBuffer;
    int currentMethodAccessFlags;
    String currentMethodName;
    String currentMethodDescriptor;
    $Label[] currentMethodLabels;
    int currentTypeAnnotationTarget;
    $TypePath currentTypeAnnotationTargetPath;
    $Label[] currentLocalVariableAnnotationRangeStarts;
    $Label[] currentLocalVariableAnnotationRangeEnds;
    int[] currentLocalVariableAnnotationRangeIndices;
    int currentFrameOffset;
    int currentFrameType;
    int currentFrameLocalCount;
    int currentFrameLocalCountDelta;
    Object[] currentFrameLocalTypes;
    int currentFrameStackCount;
    Object[] currentFrameStackTypes;

    $Context() {
    }
}

