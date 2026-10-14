/*
 * Decompiled with CFR 0.152.
 */
package javax.mail.internet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

class AddressParser {
    public static final int NONSTRICT = 0;
    public static final int PARSE_HEADER = 1;
    public static final int STRICT = 2;
    protected static final int UNKNOWN = 0;
    protected static final int ROUTE_ADDR = 1;
    protected static final int GROUP_ADDR = 2;
    protected static final int SIMPLE_ADDR = 3;
    protected static final int END_OF_TOKENS = 0;
    protected static final int PERIOD = 46;
    protected static final int LEFT_ANGLE = 60;
    protected static final int RIGHT_ANGLE = 62;
    protected static final int COMMA = 44;
    protected static final int AT_SIGN = 64;
    protected static final int SEMICOLON = 59;
    protected static final int COLON = 58;
    protected static final int QUOTED_LITERAL = 34;
    protected static final int DOMAIN_LITERAL = 91;
    protected static final int COMMENT = 40;
    protected static final int ATOM = 65;
    protected static final int WHITESPACE = 32;
    private String addresses;
    private int position;
    private int end;
    private int validationLevel;
    private static final byte[] CHARMAP = new byte[]{2, 2, 2, 2, 2, 2, 2, 2, 6, 2, 6, 2, 2, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2};
    private static final byte FLG_SPECIAL = 1;
    private static final byte FLG_CONTROL = 2;
    private static final byte FLG_SPACE = 4;

    public AddressParser(String addresses, int validation) {
        this.addresses = addresses;
        this.validationLevel = validation;
    }

    public InternetAddress[] parseAddressList() throws AddressException {
        AddressToken token;
        TokenStream tokens = this.tokenizeAddress();
        ArrayList addressList = new ArrayList();
        do {
            addressList.addAll(this.parseSingleAddress(tokens, false));
            token = tokens.nextToken();
        } while (token.type != 0);
        return addressList.toArray(new InternetAddress[0]);
    }

    public InternetAddress parseAddress() throws AddressException {
        TokenStream tokens = this.tokenizeAddress();
        List addressList = this.parseSingleAddress(tokens, false);
        if (addressList.isEmpty()) {
            throw new AddressException("Null address", this.addresses, 0);
        }
        if (addressList.size() > 1) {
            throw new AddressException("Illegal Address", this.addresses, 0);
        }
        AddressToken token = tokens.nextToken();
        if (token.type != 0) {
            this.illegalAddress("Illegal Address", token);
        }
        return (InternetAddress)addressList.get(0);
    }

    public void validateAddress() throws AddressException {
        TokenStream tokens = this.tokenizeAddress();
        List addressList = this.parseSingleAddress(tokens, false);
        if (addressList.isEmpty()) {
            throw new AddressException("Null address", this.addresses, 0);
        }
        if (addressList.size() > 1) {
            throw new AddressException("Illegal Address", this.addresses, 0);
        }
        InternetAddress address = (InternetAddress)addressList.get(0);
        if (address.personal != null) {
            throw new AddressException("Illegal Address", this.addresses, 0);
        }
        AddressToken token = tokens.nextToken();
        if (token.type != 0) {
            this.illegalAddress("Illegal Address", token);
        }
    }

    public InternetAddress[] extractGroupList() throws AddressException {
        TokenStream tokens = this.tokenizeAddress();
        ArrayList addresses = new ArrayList();
        AddressToken token = tokens.nextToken();
        while (token.type != 58) {
            if (token.type == 0) {
                this.illegalAddress("Missing ':'", token);
            }
            token = tokens.nextToken();
        }
        while (true) {
            addresses.addAll(this.parseSingleAddress(tokens, true));
            token = tokens.nextToken();
            if (token.type == 59) break;
            if (token.type != 0) continue;
            this.illegalAddress("Missing ';'", token);
        }
        return addresses.toArray(new InternetAddress[0]);
    }

    private List parseSingleAddress(TokenStream tokens, boolean inGroup) throws AddressException {
        ArrayList<InternetAddress> parsedAddresses = new ArrayList<InternetAddress>();
        AddressToken personalStart = null;
        AddressToken personalEnd = null;
        AddressToken addressStart = null;
        AddressToken addressEnd = null;
        boolean nonStrictRules = true;
        int addressType = 0;
        AddressToken first = tokens.nextToken();
        tokens.pushToken(first);
        while (addressType == 0) {
            AddressToken token = tokens.nextToken();
            switch (token.type) {
                case 40: {
                    nonStrictRules = false;
                    break;
                }
                case 59: {
                    if (inGroup) {
                        tokens.pushToken(token);
                        if (addressStart == null) {
                            return parsedAddresses;
                        }
                        addressEnd = tokens.previousToken(token);
                        personalStart = null;
                        addressType = 3;
                        break;
                    }
                }
                case 34: 
                case 91: {
                    nonStrictRules = false;
                }
                case 46: 
                case 64: 
                case 65: {
                    if (addressStart != null) break;
                    if (personalStart == null) {
                        personalStart = token;
                    }
                    addressStart = token;
                    break;
                }
                case 60: {
                    nonStrictRules = false;
                    addressType = 1;
                    addressStart = tokens.nextRealToken();
                    tokens.pushToken(addressStart);
                    if (personalStart != null) {
                        personalEnd = tokens.previousToken(token);
                    }
                    addressEnd = this.scanRouteAddress(tokens, false);
                    break;
                }
                case 58: {
                    nonStrictRules = false;
                    if (inGroup) {
                        this.illegalAddress("Nested group element", token);
                    }
                    addressType = 2;
                    personalStart = null;
                    addressStart = first;
                    addressEnd = this.scanGroupAddress(tokens);
                    break;
                }
                case 0: {
                    if (inGroup) {
                        this.illegalAddress("Missing ';'", token);
                    }
                }
                case 44: {
                    tokens.pushToken(token);
                    if (addressStart == null) {
                        return parsedAddresses;
                    }
                    addressEnd = tokens.previousToken(token);
                    personalStart = null;
                    addressType = 3;
                    break;
                }
                case 62: {
                    this.illegalAddress("Unexpected '>'", token);
                }
            }
        }
        String personal = null;
        if (personalStart != null) {
            TokenStream personalTokens = tokens.section(personalStart, personalEnd);
            personal = this.personalToString(personalTokens);
        } else if (addressType == 3 && first.type == 40) {
            personal = first.value;
        }
        TokenStream addressTokens = tokens.section(addressStart, addressEnd);
        if (this.validationLevel != 1) {
            switch (addressType) {
                case 2: {
                    this.validateGroup(addressTokens);
                    break;
                }
                case 1: {
                    this.validateRouteAddr(addressTokens, false);
                    break;
                }
                case 3: {
                    this.validateSimpleAddress(addressTokens);
                }
            }
        }
        if (this.validationLevel != 0 || addressType != 3 || !nonStrictRules) {
            addressTokens.reset();
            String address = this.addressToString(addressTokens);
            InternetAddress result = new InternetAddress();
            result.setAddress(address);
            try {
                result.setPersonal(personal);
            }
            catch (UnsupportedEncodingException e) {
                // empty catch block
            }
            parsedAddresses.add(result);
            return parsedAddresses;
        }
        addressTokens.reset();
        TokenStream nextAddress = addressTokens.getBlankDelimitedToken();
        while (nextAddress != null) {
            String address = this.addressToString(nextAddress);
            InternetAddress result = new InternetAddress();
            result.setAddress(address);
            parsedAddresses.add(result);
            nextAddress = addressTokens.getBlankDelimitedToken();
        }
        return parsedAddresses;
    }

    private AddressToken scanRouteAddress(TokenStream tokens, boolean inGroup) throws AddressException {
        AddressToken token = tokens.nextRealToken();
        AddressToken previous = null;
        boolean inRoute = token.type == 64;
        while (true) {
            switch (token.type) {
                case 34: 
                case 46: 
                case 64: 
                case 65: 
                case 91: {
                    break;
                }
                case 58: {
                    if (!inRoute) {
                        this.illegalAddress("Unexpected ':'", token);
                    }
                    inRoute = false;
                    break;
                }
                case 44: {
                    if (inRoute) break;
                    this.illegalAddress("Unexpected ','", token);
                    break;
                }
                case 62: {
                    if (previous == null) {
                        this.illegalAddress("Illegal address", token);
                    }
                    token = tokens.nextRealToken();
                    if (inGroup) {
                        if (token.type != 44 && token.type != 59) {
                            this.illegalAddress("Illegal address", token);
                        }
                    } else if (token.type != 44 && token.type != 0) {
                        this.illegalAddress("Illegal address", token);
                    }
                    tokens.pushToken(token);
                    return previous;
                }
                case 0: {
                    this.illegalAddress("Missing '>'", token);
                }
                case 59: {
                    this.illegalAddress("Unexpected ';'", token);
                }
                case 60: {
                    this.illegalAddress("Unexpected '<'", token);
                }
            }
            previous = token;
            token = tokens.nextRealToken();
        }
    }

    private AddressToken scanGroupAddress(TokenStream tokens) throws AddressException {
        AddressToken token = tokens.nextRealToken();
        while (true) {
            switch (token.type) {
                case 34: 
                case 44: 
                case 46: 
                case 64: 
                case 65: 
                case 91: {
                    break;
                }
                case 58: {
                    this.illegalAddress("Nested group", token);
                }
                case 60: {
                    this.scanRouteAddress(tokens, true);
                    break;
                }
                case 0: {
                    this.illegalAddress("Missing ';'", token);
                }
                case 59: {
                    AddressToken next = tokens.nextRealToken();
                    if (next.type != 44 && next.type != 0) {
                        this.illegalAddress("Illegal address", token);
                    }
                    tokens.pushToken(next);
                    return token;
                }
                case 62: {
                    this.illegalAddress("Unexpected '>'", token);
                }
            }
            token = tokens.nextRealToken();
        }
    }

    private TokenStream tokenizeAddress() throws AddressException {
        TokenStream tokens = new TokenStream();
        this.end = this.addresses.length();
        block15: while (this.moreCharacters()) {
            char ch = this.currentChar();
            switch (ch) {
                case '(': {
                    this.scanComment(tokens);
                    continue block15;
                }
                case ')': {
                    this.syntaxError("Unexpected ')'", this.position);
                }
                case '\"': {
                    this.scanQuotedLiteral(tokens);
                    continue block15;
                }
                case '[': {
                    this.scanDomainLiteral(tokens);
                    continue block15;
                }
                case ']': {
                    this.syntaxError("Unexpected ']'", this.position);
                }
                case '<': {
                    tokens.addToken(new AddressToken(60, this.position));
                    this.nextChar();
                    continue block15;
                }
                case '>': {
                    tokens.addToken(new AddressToken(62, this.position));
                    this.nextChar();
                    continue block15;
                }
                case ':': {
                    tokens.addToken(new AddressToken(58, this.position));
                    this.nextChar();
                    continue block15;
                }
                case ',': {
                    tokens.addToken(new AddressToken(44, this.position));
                    this.nextChar();
                    continue block15;
                }
                case '.': {
                    tokens.addToken(new AddressToken(46, this.position));
                    this.nextChar();
                    continue block15;
                }
                case ';': {
                    tokens.addToken(new AddressToken(59, this.position));
                    this.nextChar();
                    continue block15;
                }
                case '@': {
                    tokens.addToken(new AddressToken(64, this.position));
                    this.nextChar();
                    continue block15;
                }
                case '\t': 
                case '\n': 
                case '\r': 
                case ' ': {
                    char nextChar;
                    tokens.addToken(new AddressToken(32, this.position));
                    this.nextChar();
                    while (this.moreCharacters() && ((nextChar = this.currentChar()) == ' ' || nextChar == '\t' || nextChar == '\r' || nextChar == '\n')) {
                        this.nextChar();
                    }
                    continue block15;
                }
            }
            if (ch < ' ' || ch >= '\u007f') {
                this.syntaxError("Illegal character in address", this.position);
            }
            this.scanAtom(tokens);
        }
        tokens.addToken(new AddressToken(0, this.addresses.length()));
        return tokens;
    }

    private void nextChar() {
        ++this.position;
    }

    private char currentChar() {
        return this.addresses.charAt(this.position);
    }

    private boolean moreCharacters() {
        return this.position < this.end;
    }

    private void scanQuotedLiteral(TokenStream tokens) throws AddressException {
        StringBuffer value = new StringBuffer();
        int startPosition = this.position;
        this.nextChar();
        while (this.moreCharacters()) {
            char ch = this.currentChar();
            if (ch == '\\') {
                this.nextChar();
                if (!this.moreCharacters()) {
                    this.syntaxError("Missing '\"'", this.position);
                }
                value.append(this.currentChar());
            } else {
                if (ch == '\"') {
                    tokens.addToken(new AddressToken(value.toString(), 34, this.position));
                    this.nextChar();
                    return;
                }
                if (ch == '\r') {
                    this.syntaxError("Illegal line end in literal", this.position);
                } else {
                    value.append(ch);
                }
            }
            this.nextChar();
        }
        this.syntaxError("Missing '\"'", this.position);
    }

    private void scanDomainLiteral(TokenStream tokens) throws AddressException {
        StringBuffer value = new StringBuffer();
        int startPosition = this.position;
        this.nextChar();
        while (this.moreCharacters()) {
            char ch = this.currentChar();
            if (ch == '\\') {
                value.append(this.currentChar());
                this.nextChar();
                if (!this.moreCharacters()) {
                    this.syntaxError("Missing '\"'", this.position);
                }
                value.append(this.currentChar());
            } else {
                if (ch == ']') {
                    tokens.addToken(new AddressToken(value.toString(), 91, startPosition));
                    this.nextChar();
                    return;
                }
                if (ch == '[') {
                    this.syntaxError("Unexpected '['", this.position);
                } else if (ch == '\r') {
                    this.syntaxError("Illegal line end in domain literal", this.position);
                } else {
                    value.append(ch);
                }
            }
            this.nextChar();
        }
        this.syntaxError("Missing ']'", this.position);
    }

    private void scanAtom(TokenStream tokens) throws AddressException {
        char ch;
        int start2 = this.position;
        this.nextChar();
        while (this.moreCharacters() && AddressParser.isAtom(ch = this.currentChar())) {
            this.nextChar();
        }
        tokens.addToken(new AddressToken(this.addresses.substring(start2, this.position), 65, start2));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void scanComment(TokenStream tokens) throws AddressException {
        StringBuffer value = new StringBuffer();
        int startPosition = this.position;
        this.nextChar();
        int nest = 1;
        while (true) {
            if (!this.moreCharacters()) {
                this.syntaxError("Missing ')'", this.position);
                return;
            }
            char ch = this.currentChar();
            if (ch == '\\') {
                this.nextChar();
                if (!this.moreCharacters()) {
                    this.syntaxError("Missing ')'", this.position);
                }
                value.append(this.currentChar());
            } else if (ch == '(') {
                ++nest;
                value.append(ch);
            } else if (ch == ')') {
                if (--nest <= 0) {
                    this.nextChar();
                    tokens.addToken(new AddressToken(value.toString(), 40, startPosition));
                    return;
                }
                value.append(ch);
            } else if (ch == '\r') {
                this.syntaxError("Illegal line end in comment", this.position);
            } else {
                value.append(ch);
            }
            this.nextChar();
        }
    }

    private void validateGroup(TokenStream tokens) throws AddressException {
        int phraseCount = 0;
        AddressToken token = tokens.nextRealToken();
        while (token.type != 58) {
            if (token.type != 65 && token.type != 34) {
                this.invalidToken(token);
            }
            ++phraseCount;
            token = tokens.nextRealToken();
        }
        if (phraseCount == 0) {
            this.illegalAddress("Missing group identifier phrase", token);
        }
        while (true) {
            this.validateGroupMailbox(tokens);
            token = tokens.nextRealToken();
            if (token.type == 59) {
                token = tokens.nextRealToken();
                if (token.type != 0) {
                    this.illegalAddress("Illegal group address", token);
                }
                return;
            }
            if (token.type == 44) continue;
            this.illegalAddress("Illegal group address", token);
        }
    }

    private void validateGroupMailbox(TokenStream tokens) throws AddressException {
        AddressToken first = tokens.nextRealToken();
        if (first.type == 44 || first.type == 59) {
            tokens.pushToken(first);
            return;
        }
        AddressToken token = first;
        while (first != null) {
            switch (token.type) {
                case 34: 
                case 65: {
                    break;
                }
                case 60: {
                    tokens.pushToken(first);
                    this.validatePhrase(tokens, false);
                    this.validateRouteAddr(tokens, true);
                    return;
                }
                case 46: 
                case 64: {
                    tokens.pushToken(first);
                    this.validateAddressSpec(tokens);
                    return;
                }
                case 44: 
                case 59: {
                    tokens.pushToken(first);
                    this.validateAddressSpec(tokens);
                    return;
                }
                case 0: {
                    this.illegalAddress("Missing ';'", token);
                }
            }
            token = tokens.nextRealToken();
        }
    }

    private void invalidToken(AddressToken token) throws AddressException {
        this.illegalAddress("Unexpected '" + token.type + "'", token);
    }

    private void syntaxError(String message, int position) throws AddressException {
        throw new AddressException(message, this.addresses, position);
    }

    private void illegalAddress(String message, AddressToken token) throws AddressException {
        throw new AddressException(message, this.addresses, token.position);
    }

    private void validatePhrase(TokenStream tokens, boolean required) throws AddressException {
        AddressToken token = tokens.nextRealToken();
        if (token.type != 65 && token.type != 34 && required) {
            this.illegalAddress("Missing group phrase", token);
        }
        token = tokens.nextRealToken();
        while (token.type == 65 || token.type == 34) {
            token = tokens.nextRealToken();
        }
    }

    private void validateRouteAddr(TokenStream tokens, boolean ingroup) throws AddressException {
        AddressToken token = tokens.nextRealToken();
        if (token.type == 64) {
            tokens.pushToken(token);
            this.validateRoute(tokens);
        } else {
            tokens.pushToken(token);
        }
        this.validateAddressSpec(tokens);
        token = tokens.nextRealToken();
        if (ingroup) {
            if (token.type != 62) {
                this.illegalAddress("Missing '>'", token);
            }
        } else if (token.type != 0) {
            this.illegalAddress("Illegal Address", token);
        }
    }

    private void validateSimpleAddress(TokenStream tokens) throws AddressException {
        this.validateAddressSpec(tokens);
        AddressToken token = tokens.nextRealToken();
        if (token.type != 0) {
            this.illegalAddress("Illegal Address", token);
        }
    }

    private void validateAddressSpec(TokenStream tokens) throws AddressException {
        this.validateLocalPart(tokens);
        AddressToken token = tokens.nextRealToken();
        if (token.type == 64) {
            this.validateDomain(tokens);
        } else {
            tokens.pushToken(token);
        }
    }

    private void validateRoute(TokenStream tokens) throws AddressException {
        while (true) {
            AddressToken token = tokens.nextRealToken();
            if (token.type == 64) {
                this.validateDomain(tokens);
                continue;
            }
            if (token.type == 44) continue;
            if (token.type == 58) {
                return;
            }
            this.illegalAddress("Missing ':'", token);
        }
    }

    private void validateLocalPart(TokenStream tokens) throws AddressException {
        AddressToken token;
        do {
            token = tokens.nextRealToken();
            if (token.type != 65 && token.type != 34) {
                this.illegalAddress("Invalid local part", token);
            }
            token = tokens.nextRealToken();
        } while (token.type == 46);
        tokens.pushToken(token);
    }

    private void validateDomain(TokenStream tokens) throws AddressException {
        AddressToken token;
        do {
            token = tokens.nextRealToken();
            if (token.type != 65 && token.type != 91) {
                this.illegalAddress("Invalid domain", token);
            }
            token = tokens.nextRealToken();
        } while (token.type == 46);
        tokens.pushToken(token);
    }

    private String personalToString(TokenStream tokens) {
        AddressToken token = tokens.nextToken();
        if (token.type == 0) {
            return null;
        }
        AddressToken next = tokens.nextToken();
        if (next.type == 0) {
            return token.value;
        }
        tokens.pushToken(token);
        StringBuffer buffer = new StringBuffer();
        token = tokens.nextToken();
        this.addTokenValue(token, buffer);
        token = tokens.nextToken();
        while (token.type != 0) {
            buffer.append(' ');
            this.addTokenValue(token, buffer);
            token = tokens.nextToken();
        }
        return buffer.toString();
    }

    private String addressToString(TokenStream tokens) {
        StringBuffer buffer = new StringBuffer();
        boolean spaceRequired = false;
        AddressToken token = tokens.nextToken();
        while (token.type != 0) {
            switch (token.type) {
                case 34: 
                case 65: {
                    if (spaceRequired) {
                        buffer.append(' ');
                    }
                    this.addTokenValue(token, buffer);
                    spaceRequired = true;
                    break;
                }
                case 44: 
                case 46: 
                case 58: 
                case 59: 
                case 60: 
                case 62: 
                case 64: {
                    buffer.append((char)token.type);
                    spaceRequired = false;
                    break;
                }
                case 91: {
                    this.addTokenValue(token, buffer);
                    spaceRequired = false;
                    break;
                }
                case 40: {
                    this.addTokenValue(token, buffer);
                    spaceRequired = false;
                }
            }
            token = tokens.nextToken();
        }
        return buffer.toString();
    }

    private void addTokenValue(AddressToken token, StringBuffer buffer) {
        if (token.type == 65) {
            buffer.append(token.value);
        } else if (token.type == 34) {
            buffer.append(AddressParser.formatQuotedString(token.value));
        } else if (token.type == 91) {
            buffer.append('[');
            buffer.append(token.value);
            buffer.append(']');
        } else if (token.type == 40) {
            buffer.append('(');
            buffer.append(token.value);
            buffer.append(')');
        }
    }

    private static boolean isSpace(char ch) {
        if (ch > '\u007f') {
            return false;
        }
        return (CHARMAP[ch] & 4) != 0;
    }

    public static boolean isAtom(char ch) {
        if (ch > '\u007f') {
            return false;
        }
        if (ch == ' ') {
            return false;
        }
        return (CHARMAP[ch] & 3) == 0;
    }

    public static boolean containsCharacters(String s2, String chars) {
        for (int i = 0; i < s2.length(); ++i) {
            if (chars.indexOf(s2.charAt(i)) < 0) continue;
            return true;
        }
        return false;
    }

    public static boolean containsSpecials(String s2) {
        for (int i = 0; i < s2.length(); ++i) {
            char ch = s2.charAt(i);
            if (ch == ' ' || AddressParser.isAtom(ch)) continue;
            return true;
        }
        return false;
    }

    public static boolean isAtom(String s2) {
        for (int i = 0; i < s2.length(); ++i) {
            char ch = s2.charAt(i);
            if (AddressParser.isAtom(ch)) continue;
            return false;
        }
        return true;
    }

    public static String quoteString(String s2) {
        if (s2.indexOf(92) == -1 && s2.indexOf(34) == -1) {
            if (!AddressParser.containsSpecials(s2)) {
                return s2;
            }
            StringBuffer buffer = new StringBuffer(s2.length() + 2);
            buffer.append('\"');
            buffer.append(s2);
            buffer.append('\"');
            return buffer.toString();
        }
        StringBuffer buffer = new StringBuffer(s2.length() + 10);
        buffer.append('\"');
        for (int i = 0; i < s2.length(); ++i) {
            char ch = s2.charAt(i);
            if (ch == '\\' || ch == '\"') {
                buffer.append('\\');
            }
            buffer.append(ch);
        }
        buffer.append('\"');
        return buffer.toString();
    }

    public static String formatQuotedString(String s2) {
        if (s2.indexOf(92) == -1 && s2.indexOf(34) == -1) {
            StringBuffer buffer = new StringBuffer(s2.length() + 2);
            buffer.append('\"');
            buffer.append(s2);
            buffer.append('\"');
            return buffer.toString();
        }
        StringBuffer buffer = new StringBuffer(s2.length() + 10);
        buffer.append('\"');
        for (int i = 0; i < s2.length(); ++i) {
            char ch = s2.charAt(i);
            if (ch == '\\' || ch == '\"') {
                buffer.append('\\');
            }
            buffer.append(ch);
        }
        buffer.append('\"');
        return buffer.toString();
    }

    public class AddressToken {
        int type;
        String value;
        int position;

        AddressToken(int type, int position) {
            this.type = type;
            this.value = null;
            this.position = position;
        }

        AddressToken(String value, int type, int position) {
            this.type = type;
            this.value = value;
            this.position = position;
        }

        public String toString() {
            if (this.type == 0) {
                return "AddressToken:  type=END_OF_TOKENS";
            }
            if (this.value == null) {
                return "AddressToken:  type=" + (char)this.type;
            }
            return "AddressToken:  type=" + (char)this.type + " value=" + this.value;
        }
    }

    public class TokenStream {
        private List tokens;
        int currentToken = 0;

        public TokenStream() {
            this.tokens = new ArrayList();
        }

        public TokenStream(List tokens) {
            this.tokens = tokens;
            tokens.add(new AddressToken(0, -1));
        }

        public void addToken(AddressToken token) {
            this.tokens.add(token);
        }

        public AddressToken nextToken() {
            AddressToken token = (AddressToken)this.tokens.get(this.currentToken++);
            while (token.type == 32) {
                token = (AddressToken)this.tokens.get(this.currentToken++);
            }
            return token;
        }

        public AddressToken currentToken() {
            return (AddressToken)this.tokens.get(this.currentToken);
        }

        public AddressToken nextRealToken() {
            AddressToken token = this.nextToken();
            if (token.type == 40) {
                token = this.nextToken();
            }
            return token;
        }

        public void pushToken(AddressToken token) {
            this.currentToken = this.tokenIndex(token);
        }

        public AddressToken nextToken(AddressToken token) {
            return (AddressToken)this.tokens.get(this.tokenIndex(token) + 1);
        }

        public AddressToken previousToken(AddressToken token) {
            return (AddressToken)this.tokens.get(this.tokenIndex(token) - 1);
        }

        public AddressToken getToken(int index) {
            return (AddressToken)this.tokens.get(index);
        }

        public int tokenIndex(AddressToken token) {
            return this.tokens.indexOf(token);
        }

        public TokenStream section(AddressToken start2, AddressToken end) {
            int startIndex = this.tokenIndex(start2);
            int endIndex = this.tokenIndex(end);
            ArrayList list = new ArrayList(endIndex - startIndex + 2);
            for (int i = startIndex; i <= endIndex; ++i) {
                list.add(this.tokens.get(i));
            }
            return new TokenStream(list);
        }

        public void reset() {
            this.currentToken = 0;
        }

        public AddressToken getNonBlank() {
            AddressToken token = this.currentToken();
            while (token.type == 32) {
                ++this.currentToken;
                token = this.currentToken();
            }
            return token;
        }

        public TokenStream getBlankDelimitedToken() {
            AddressToken first = this.getNonBlank();
            if (first.type == 0) {
                return null;
            }
            AddressToken last = first;
            ++this.currentToken;
            AddressToken token = this.currentToken();
            while (token.type != 0 && token.type != 32) {
                last = token;
                ++this.currentToken;
                token = this.currentToken();
            }
            return this.section(first, last);
        }

        public int currentIndex() {
            return this.currentToken;
        }

        public void dumpTokens() {
            System.out.println(">>>>>>>>> Start dumping TokenStream tokens");
            for (int i = 0; i < this.tokens.size(); ++i) {
                System.out.println("-------- Token: " + this.tokens.get(i));
            }
            System.out.println("++++++++ cursor position=" + this.currentToken);
            System.out.println(">>>>>>>>> End dumping TokenStream tokens");
        }
    }
}

