/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.constraintvalidators.hv.pl;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import javax.validation.ConstraintValidator;
import org.hibernate.validator.constraints.pl.PESEL;
import org.hibernate.validator.internal.constraintvalidators.hv.ModCheckBase;
import org.hibernate.validator.internal.util.ModUtil;

public class PESELValidator
extends ModCheckBase
implements ConstraintValidator<PESEL, CharSequence> {
    private static final int[] WEIGHTS_PESEL = new int[]{1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

    @Override
    public void initialize(PESEL constraintAnnotation) {
        super.initialize(0, Integer.MAX_VALUE, -1, false);
    }

    @Override
    public boolean isCheckDigitValid(List<Integer> digits, char checkDigit) {
        if (digits.size() != WEIGHTS_PESEL.length) {
            return false;
        }
        int monthCode = this.doubleDigitNumberFromSubList(digits, 2);
        try {
            LocalDate.of(this.year(this.doubleDigitNumberFromSubList(digits, 0), monthCode / 20), monthCode % 20, this.doubleDigitNumberFromSubList(digits, 4));
        }
        catch (DateTimeException e) {
            return false;
        }
        Collections.reverse(digits);
        int modResult = ModUtil.calculateModXCheckWithWeights(digits, 10, Integer.MAX_VALUE, WEIGHTS_PESEL);
        switch (modResult) {
            case 10: {
                return checkDigit == '0';
            }
        }
        return Character.isDigit(checkDigit) && modResult == this.extractDigit(checkDigit);
    }

    private int doubleDigitNumberFromSubList(List<Integer> digits, int start2) {
        return digits.get(start2) * 10 + digits.get(start2 + 1);
    }

    private int year(int year, int centuryCode) {
        switch (centuryCode) {
            case 4: {
                return 1800 + year;
            }
            case 0: {
                return 1900 + year;
            }
            case 1: {
                return 2000 + year;
            }
            case 2: {
                return 2100 + year;
            }
            case 3: {
                return 2200 + year;
            }
        }
        throw new IllegalStateException("Invalid century code.");
    }
}

