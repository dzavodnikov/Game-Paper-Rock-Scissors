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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Symbol}.
 *
 * @author Dmitry Zavodnikov
 */
class SymbolTest {

    @Test
    void testUniquePrefix() {
        final Function<Symbol, Character> getFirstCharAsUpperCase = s -> s.toString().toUpperCase().charAt(0);
        final var values = Symbol.values();
        for (var i = 0; i < values.length - 1; ++i) {
            final var s1 = values[i];
            final var c1 = getFirstCharAsUpperCase.apply(s1);
            for (var j = i + 1; j < values.length; ++j) {
                assert i != j; // To be sure that test is correct itself.
                final var s2 = values[j];
                assert s1 != s2; // To be sure that test is correct itself.
                final var c2 = getFirstCharAsUpperCase.apply(s2);
                assertNotEquals(c1, c2, String.format("Symbols '%s' and '%s' starts with same letter", s1, s2));
            }
        }
    }

    @Test
    void testParse() {
        assertEquals(Symbol.PAPER, Symbol.parse("p"));
        assertEquals(Symbol.PAPER, Symbol.parse("P"));

        assertEquals(Symbol.PAPER, Symbol.parse("paper"));
        assertEquals(Symbol.PAPER, Symbol.parse("Paper"));
        assertEquals(Symbol.PAPER, Symbol.parse("PAPER"));

        assertNull(Symbol.parse("wrongSymbolName"));
    }

    @Test
    void testSymbolsHaveNoSelfOrMutualBeats() {
        for (var s1 : Symbol.values()) {
            assertNull(s1.beat(s1), String.format("'%s' can beat itself", s1));

            for (var s2 : Symbol.values()) {
                if (!s1.equals(s2)) { // Skip the same.
                    if (s1.beat(s2) != null) { // Can beat.
                        assertNull(s2.beat(s1), String.format("'%s' and '%s' beat each other", s1, s2));
                    }
                }
            }
        }
    }

    @Test
    void testSymbolsCanBeat() {
        for (var s1 : Symbol.values()) {
            var beatSymbol = false;
            for (var s2 : Symbol.values()) {
                if (!s1.equals(s2)) { // Skip the same.
                    if (s1.beat(s2) != null) { // Can beat.
                        beatSymbol = true;
                    }
                }
            }
            if (!beatSymbol) {
                fail(String.format("Symbol '%s' can't beat any other symbol", s1));
            }
        }
    }

    @Test
    void testSymbolsCanBeBeat() {
        for (var s1 : Symbol.values()) {
            var wasBeat = false;
            for (var s2 : Symbol.values()) {
                if (!s1.equals(s2)) { // Skip the same.
                    if (s2.beat(s1) != null) { // Was beat.
                        wasBeat = true;
                    }
                }
            }
            if (!wasBeat) {
                fail(String.format("Symbol '%s' can't be beat by any other symbol", s1));
            }
        }
    }

    @Test
    void testSymbolPaperBeat() {
        assertEquals("Paper wraps Rock", Symbol.PAPER.beat(Symbol.ROCK));
        assertNull(Symbol.PAPER.beat(Symbol.SCISSORS));
    }

    @Test
    void testSymbolRockBeat() {
        assertEquals("Rock blunts Scissors", Symbol.ROCK.beat(Symbol.SCISSORS));
        assertNull(Symbol.ROCK.beat(Symbol.PAPER));
    }

    @Test
    void testSymbolScissorsBeat() {
        assertEquals("Scissors cuts Paper", Symbol.SCISSORS.beat(Symbol.PAPER));
        assertNull(Symbol.SCISSORS.beat(Symbol.ROCK));
    }
}
