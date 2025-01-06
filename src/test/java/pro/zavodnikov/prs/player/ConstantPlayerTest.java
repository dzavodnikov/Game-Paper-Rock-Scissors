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
package pro.zavodnikov.prs.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

import pro.zavodnikov.prs.Symbol;

/**
 * Tests for {@link ConstantPlayer}.
 *
 * @author Dmitry Zavodnikov
 */
class ConstantPlayerTest {

    @Test
    void testConstructor() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new ConstantPlayer("Constant Player"));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ConstantPlayer("Constant Player", Symbol.PAPER, null));
    }

    @Test
    void testName() {
        final var player = new ConstantPlayer("Constant Player", Symbol.PAPER);
        assertEquals("Constant Player", player.getName());
    }

    @Test
    void testSymbol() {
        final var player = new ConstantPlayer("Constant Player", Symbol.PAPER, Symbol.PAPER, Symbol.ROCK,
                Symbol.SCISSORS);
        for (var i = 0; i < 3; ++i) {
            assertEquals(Symbol.PAPER, player.getSymbol());
            assertEquals(Symbol.PAPER, player.getSymbol());
            assertEquals(Symbol.ROCK, player.getSymbol());
            assertEquals(Symbol.SCISSORS, player.getSymbol());
        }
    }
}
