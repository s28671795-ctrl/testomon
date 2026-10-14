/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.StringJoiner;
import org.h2.command.Token;
import org.h2.command.Tokenizer;
import org.h2.engine.Database;
import org.h2.engine.DbSettings;
import org.h2.engine.SessionLocal;
import org.h2.expression.Parameter;
import org.h2.message.DbException;
import org.h2.util.ParserUtil;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.Value;

public class ParserBase {
    final Database database;
    final SessionLocal session;
    private final boolean identifiersToLower;
    private final boolean identifiersToUpper;
    final boolean variableBinary;
    final BitSet nonKeywords;
    ArrayList<Token> tokens;
    int tokenIndex;
    Token token;
    int currentTokenType;
    String currentToken;
    String sqlCommand;
    ArrayList<Parameter> parameters;
    BitSet usedParameters = new BitSet();
    private boolean literalsChecked;
    ArrayList<String> expectedList;

    public static String quoteIdentifier(String string, int n) {
        if (string == null) {
            return "\"\"";
        }
        if ((n & 1) != 0 && ParserUtil.isSimpleIdentifier(string, false, false)) {
            return string;
        }
        return StringUtils.quoteIdentifier(string);
    }

    public static BitSet parseNonKeywords(String[] stringArray) {
        if (stringArray.length == 0) {
            return null;
        }
        BitSet bitSet = new BitSet();
        for (String string : stringArray) {
            int n = Arrays.binarySearch(Token.TOKENS, 3, 92, string);
            if (n < 0) continue;
            bitSet.set(n);
        }
        return bitSet.isEmpty() ? null : bitSet;
    }

    public static String formatNonKeywords(BitSet bitSet) {
        if (bitSet == null || bitSet.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = -1;
        while ((n = bitSet.nextSetBit(n + 1)) >= 0) {
            if (n < 3 || n > 91) continue;
            if (stringBuilder.length() > 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(Token.TOKENS[n]);
        }
        return stringBuilder.toString();
    }

    static boolean isKeyword(int n) {
        return n >= 3 && n <= 91;
    }

    ParserBase(SessionLocal sessionLocal) {
        this.database = sessionLocal.getDatabase();
        DbSettings dbSettings = this.database.getSettings();
        this.identifiersToLower = dbSettings.databaseToLower;
        this.identifiersToUpper = dbSettings.databaseToUpper;
        this.variableBinary = sessionLocal.isVariableBinary();
        this.nonKeywords = sessionLocal.getNonKeywords();
        this.session = sessionLocal;
    }

    public ParserBase() {
        this.database = null;
        this.identifiersToLower = false;
        this.identifiersToUpper = false;
        this.variableBinary = false;
        this.nonKeywords = null;
        this.session = null;
    }

    public final void setLiteralsChecked(boolean bl) {
        this.literalsChecked = bl;
    }

    public final void setSuppliedParameters(ArrayList<Parameter> arrayList) {
        this.parameters = arrayList;
    }

    public Object parseColumnList(String string, int n) {
        this.initialize(string, null, true);
        int n2 = this.tokens.size();
        for (int i = 0; i < n2; ++i) {
            if (this.tokens.get(i).start() < n) continue;
            this.setTokenIndex(i);
            break;
        }
        this.read(105);
        if (this.readIf(106)) {
            return Utils.EMPTY_INT_ARRAY;
        }
        if (this.isIdentifier()) {
            ArrayList<String> arrayList = Utils.newSmallArrayList();
            do {
                if (!this.isIdentifier()) {
                    throw this.getSyntaxError();
                }
                arrayList.add(this.currentToken);
                this.read();
            } while (this.readIfMore());
            return arrayList.toArray(new String[0]);
        }
        if (this.currentTokenType == 94) {
            ArrayList arrayList = Utils.newSmallArrayList();
            do {
                arrayList.add(this.readInt());
            } while (this.readIfMore());
            n2 = arrayList.size();
            int[] nArray = new int[n2];
            for (int i = 0; i < n2; ++i) {
                nArray[i] = (Integer)arrayList.get(i);
            }
            return nArray;
        }
        throw this.getSyntaxError();
    }

    final void initialize(String string, ArrayList<Token> arrayList, boolean bl) {
        if (string == null) {
            string = "";
        }
        this.sqlCommand = string;
        if (arrayList == null) {
            BitSet bitSet = new BitSet();
            this.tokens = new Tokenizer(this.database, this.identifiersToUpper, this.identifiersToLower, this.nonKeywords).tokenize(string, bl, bitSet);
            if (this.parameters == null) {
                int n = bitSet.length();
                if (n > 100000) {
                    throw DbException.getInvalidValueException("parameter index", n);
                }
                if (n > 0) {
                    this.parameters = new ArrayList(n);
                    for (int i = 0; i < n; ++i) {
                        this.parameters.add(new Parameter(i));
                    }
                } else {
                    this.parameters = new ArrayList();
                }
            }
        } else {
            this.tokens = arrayList;
        }
        this.resetTokenIndex();
    }

    final void resetTokenIndex() {
        this.tokenIndex = -1;
        this.token = null;
        this.currentTokenType = -1;
        this.currentToken = null;
    }

    final void setTokenIndex(int n) {
        if (n != this.tokenIndex) {
            if (this.expectedList != null) {
                this.expectedList.clear();
            }
            this.token = this.tokens.get(n);
            this.tokenIndex = n;
            this.currentTokenType = this.token.tokenType();
            this.currentToken = this.token.asIdentifier();
        }
    }

    final BitSet openParametersScope() {
        BitSet bitSet = this.usedParameters;
        this.usedParameters = new BitSet();
        return bitSet;
    }

    final ArrayList<Parameter> closeParametersScope(BitSet bitSet) {
        BitSet bitSet2 = this.usedParameters;
        int n = bitSet2.cardinality();
        ArrayList<Parameter> arrayList = new ArrayList<Parameter>(n);
        if (n > 0) {
            int n2 = -1;
            while ((n2 = bitSet2.nextSetBit(n2 + 1)) >= 0) {
                arrayList.add(this.parameters.get(n2));
            }
        }
        bitSet.or(bitSet2);
        this.usedParameters = bitSet;
        return arrayList;
    }

    final void read(String string) {
        if (!this.testToken(string, this.token)) {
            this.addExpected(string);
            throw this.getSyntaxError();
        }
        this.read();
    }

    final void read(int n) {
        if (n != this.currentTokenType) {
            this.addExpected(n);
            throw this.getSyntaxError();
        }
        this.read();
    }

    final void readCompat(int n) {
        if (n != this.currentTokenType) {
            throw this.getSyntaxError();
        }
        this.read();
    }

    final boolean readIf(String string) {
        if (this.testToken(string, this.token)) {
            this.read();
            return true;
        }
        this.addExpected(string);
        return false;
    }

    final boolean readIfCompat(String string) {
        if (this.testToken(string, this.token)) {
            this.read();
            return true;
        }
        return false;
    }

    final boolean readIf(String string, String string2) {
        int n = this.tokenIndex + 1;
        if (n + 1 < this.tokens.size() && this.testToken(string, this.token) && this.testToken(string2, this.tokens.get(n))) {
            this.setTokenIndex(n + 1);
            return true;
        }
        this.addExpected(string, string2);
        return false;
    }

    final boolean readIf(String string, int n) {
        int n2 = this.tokenIndex + 1;
        if (n2 + 1 < this.tokens.size() && this.tokens.get(n2).tokenType() == n && this.testToken(string, this.token)) {
            this.setTokenIndex(n2 + 1);
            return true;
        }
        this.addExpected(string, Token.TOKENS[n]);
        return false;
    }

    final boolean readIf(int n) {
        if (n == this.currentTokenType) {
            this.read();
            return true;
        }
        this.addExpected(n);
        return false;
    }

    final boolean readIfCompat(int n) {
        if (n == this.currentTokenType) {
            this.read();
            return true;
        }
        return false;
    }

    final boolean readIf(int n, int n2) {
        int n3;
        if (n == this.currentTokenType && this.tokens.get(n3 = this.tokenIndex + 1).tokenType() == n2) {
            this.setTokenIndex(n3 + 1);
            return true;
        }
        this.addExpected(n, n2);
        return false;
    }

    final boolean readIfCompat(int n, int n2) {
        int n3;
        if (n == this.currentTokenType && this.tokens.get(n3 = this.tokenIndex + 1).tokenType() == n2) {
            this.setTokenIndex(n3 + 1);
            return true;
        }
        return false;
    }

    final boolean readIf(int n, String string) {
        int n2;
        if (n == this.currentTokenType && this.testToken(string, this.tokens.get(n2 = this.tokenIndex + 1))) {
            this.setTokenIndex(n2 + 1);
            return true;
        }
        this.addExpected(Token.TOKENS[n], string);
        return false;
    }

    final boolean readIfCompat(int n, String string) {
        int n2;
        if (n == this.currentTokenType && this.testToken(string, this.tokens.get(n2 = this.tokenIndex + 1))) {
            this.setTokenIndex(n2 + 1);
            return true;
        }
        return false;
    }

    final boolean readIf(Object ... objectArray) {
        block3: {
            int n = this.tokenIndex;
            int n2 = objectArray.length;
            int n3 = this.tokens.size();
            if (n + n2 < n3) {
                for (Object object : objectArray) {
                    if (this.testToken(object, this.tokens.get(n++))) {
                        continue;
                    }
                    break block3;
                }
                this.setTokenIndex(n);
                return true;
            }
        }
        this.addExpected(objectArray);
        return false;
    }

    final boolean readIfCompat(Object ... objectArray) {
        block3: {
            int n = this.tokenIndex;
            int n2 = objectArray.length;
            int n3 = this.tokens.size();
            if (n + n2 < n3) {
                for (Object object : objectArray) {
                    if (this.testToken(object, this.tokens.get(n++))) {
                        continue;
                    }
                    break block3;
                }
                this.setTokenIndex(n);
                return true;
            }
        }
        return false;
    }

    final boolean isToken(String string) {
        if (this.testToken(string, this.token)) {
            return true;
        }
        this.addExpected(string);
        return false;
    }

    final boolean isTokenCompat(String string) {
        return this.testToken(string, this.token);
    }

    private boolean testToken(Object object, Token token) {
        return object instanceof Integer ? ((Integer)object).intValue() == token.tokenType() : this.testToken((String)object, token);
    }

    private boolean testToken(String string, Token token) {
        if (!token.isQuoted()) {
            String string2 = token.asIdentifier();
            return this.identifiersToUpper ? string.equals(string2) : string.equalsIgnoreCase(string2);
        }
        return false;
    }

    final boolean isToken(int n) {
        if (n == this.currentTokenType) {
            return true;
        }
        this.addExpected(n);
        return false;
    }

    final boolean equalsToken(String string, String string2) {
        if (string == null) {
            return string2 == null;
        }
        return string.equals(string2) || !this.identifiersToUpper && string.equalsIgnoreCase(string2);
    }

    final boolean isIdentifier() {
        return this.currentTokenType == 2 || this.nonKeywords != null && this.nonKeywords.get(this.currentTokenType);
    }

    final void addExpected(String string) {
        if (this.expectedList != null) {
            this.expectedList.add(string);
        }
    }

    final void addExpected(int n) {
        if (this.expectedList != null) {
            this.expectedList.add(Token.TOKENS[n]);
        }
    }

    private void addExpected(int n, int n2) {
        if (this.expectedList != null) {
            this.expectedList.add(Token.TOKENS[n] + ' ' + Token.TOKENS[n2]);
        }
    }

    private void addExpected(String string, String string2) {
        if (this.expectedList != null) {
            this.expectedList.add(string + ' ' + string2);
        }
    }

    private void addExpected(Object ... objectArray) {
        if (this.expectedList != null) {
            StringJoiner stringJoiner = new StringJoiner(" ");
            for (Object object : objectArray) {
                stringJoiner.add(object instanceof Integer ? Token.TOKENS[(Integer)object] : (String)object);
            }
            this.expectedList.add(stringJoiner.toString());
        }
    }

    final void addMultipleExpected(int ... nArray) {
        for (int n : nArray) {
            this.expectedList.add(Token.TOKENS[n]);
        }
    }

    final void read() {
        int n;
        if (this.expectedList != null) {
            this.expectedList.clear();
        }
        if (this.tokenIndex + 1 < (n = this.tokens.size())) {
            this.token = this.tokens.get(++this.tokenIndex);
            this.currentTokenType = this.token.tokenType();
            this.currentToken = this.token.asIdentifier();
            if (this.currentToken != null && this.currentToken.length() > 256) {
                throw DbException.get(42622, this.currentToken.substring(0, 32), "256");
            }
            if (this.currentTokenType == 94) {
                this.checkLiterals();
            }
        } else {
            throw this.getSyntaxError();
        }
    }

    private void checkLiterals() {
        int n;
        if (!this.literalsChecked && this.session != null && !this.session.getAllowLiterals() && ((n = this.database.getAllowLiterals()) == 0 || (this.token instanceof Token.CharacterStringToken || this.token instanceof Token.BinaryStringToken) && n != 2)) {
            throw DbException.get(90116);
        }
    }

    final boolean readIfMore() {
        if (this.readIf(109)) {
            return true;
        }
        this.read(106);
        return false;
    }

    final int readNonNegativeInt() {
        int n = this.readInt();
        if (n < 0) {
            throw DbException.getInvalidValueException("non-negative integer", n);
        }
        return n;
    }

    final int readInt() {
        boolean bl = false;
        if (this.currentTokenType == 102) {
            bl = true;
            this.read();
        } else if (this.currentTokenType == 103) {
            this.read();
        }
        if (this.currentTokenType != 94) {
            throw DbException.getSyntaxError(this.sqlCommand, this.token.start(), "integer");
        }
        Value value = this.token.value(this.session);
        if (bl) {
            value = value.negate();
        }
        int n = value.getInt();
        this.read();
        return n;
    }

    final long readPositiveLong() {
        long l = this.readLong();
        if (l <= 0L) {
            throw DbException.getInvalidValueException("positive long", l);
        }
        return l;
    }

    final long readLong() {
        boolean bl = false;
        if (this.currentTokenType == 102) {
            bl = true;
            this.read();
        } else if (this.currentTokenType == 103) {
            this.read();
        }
        if (this.currentTokenType != 94) {
            throw DbException.getSyntaxError(this.sqlCommand, this.token.start(), "long");
        }
        Value value = this.token.value(this.session);
        if (bl) {
            value = value.negate();
        }
        long l = value.getLong();
        this.read();
        return l;
    }

    final boolean readBooleanSetting() {
        switch (this.currentTokenType) {
            case 60: 
            case 77: {
                this.read();
                return true;
            }
            case 31: {
                this.read();
                return false;
            }
            case 94: {
                boolean bl = this.token.value(this.session).getBoolean();
                this.read();
                return bl;
            }
        }
        if (this.readIf("OFF")) {
            return false;
        }
        if (this.expectedList != null) {
            this.addMultipleExpected(60, 77, 31);
        }
        throw this.getSyntaxError();
    }

    final Parameter readParameter() {
        int n = ((Token.ParameterToken)this.token).index() - 1;
        this.read();
        this.usedParameters.set(n);
        return this.parameters.get(n);
    }

    final boolean isKeyword(String string) {
        return ParserUtil.isKeyword(string, !this.identifiersToUpper);
    }

    final String upperName(String string) {
        return this.identifiersToUpper ? string : StringUtils.toUpperEnglish(string);
    }

    public final int getLastParseIndex() {
        return this.token.start();
    }

    final ArrayList<Token> getRemainingTokens(int n) {
        List<Token> list = this.tokens.subList(this.tokenIndex, this.tokens.size());
        ArrayList<Token> arrayList = new ArrayList<Token>(list);
        list.clear();
        this.tokens.add(new Token.EndOfInputToken(n));
        for (Token token : arrayList) {
            token.subtractFromStart(n);
        }
        return arrayList;
    }

    final DbException getSyntaxError() {
        if (this.expectedList == null || this.expectedList.isEmpty()) {
            return DbException.getSyntaxError(this.sqlCommand, this.token.start());
        }
        return DbException.getSyntaxError(this.sqlCommand, this.token.start(), String.join((CharSequence)", ", this.expectedList));
    }

    public final String toString() {
        return StringUtils.addAsterisk(this.sqlCommand, this.token.start());
    }
}

