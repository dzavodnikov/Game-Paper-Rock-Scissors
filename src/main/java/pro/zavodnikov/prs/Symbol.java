/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2023-2025 Dmitry Zavodnikov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pro.zavodnikov.prs;

/**
 * Define symbols that player can chose.
 *
 * @author Dmitry Zavodnikov
 */
public enum Symbol {
    PAPER {
        @Override
        public String beat(final Symbol symbol) {
            if (ROCK.equals(symbol)) {
                return String.format("%s wraps %s", this, symbol);
            }
            return null; // Can't beat.
        }
    },
    ROCK {
        @Override
        public String beat(final Symbol symbol) {
            if (SCISSORS.equals(symbol)) {
                return String.format("%s blunts %s", this, symbol);
            }
            return null; // Can't beat.
        }
    },
    SCISSORS {
        @Override
        public String beat(final Symbol symbol) {
            if (PAPER.equals(symbol)) {
                return String.format("%s cuts %s", this, symbol);
            }
            return null; // Can't beat.
        }
    };

    /**
     * Check if current symbol can beat another one.
     *
     * @param symbol another symbol
     * @return string with explanation how current symbol beat another or
     *         <code>null</code> otherwise.
     */
    public abstract String beat(Symbol symbol);

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        sb.append(name().substring(0, 1).toUpperCase());
        sb.append(name().substring(1).toLowerCase());
        return sb.toString();
    }

    /**
     * Parse string representation of symbol in case insensitive way and return
     * {@link Symbol} or <code>null</code>
     * otherwise.
     */
    public static Symbol parse(final String name) {
        if (name != null) {
            for (var s : Symbol.values()) {
                if (s.name().toUpperCase().startsWith(name.toUpperCase())) {
                    return s;
                }
            }
        }
        return null;
    }
}
