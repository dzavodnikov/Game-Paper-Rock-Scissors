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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pro.zavodnikov.prs.Console;
import pro.zavodnikov.prs.Symbol;

/**
 * Tests for {@link ConsolePlayer}.
 *
 * @author Dmitry Zavodnikov
 */
class ConsolePlayerTest {

    @Test
    void testConstructor() {
        assertThrowsExactly(NullPointerException.class, () -> new ConsolePlayer(null));
    }

    @Test
    void testName() {
        final var username = "Test User";
        final var out = new ByteArrayOutputStream();
        final Console console = new Console(new ByteArrayInputStream(username.getBytes()), out);

        final var player = new ConsolePlayer(console);
        assertEquals("Please, introduce yourself: ", out.toString());
        assertEquals(username, player.getName());
    }

    @Test
    void testSymbol() {
        final var sb = new StringBuilder();
        sb.append("Test User");
        sb.append("\n");
        sb.append("wrong symbol"); // Will be skipped.
        sb.append("\n");
        sb.append("p");
        sb.append("\n");

        final var out = new ByteArrayOutputStream();

        final var console = new Console(new ByteArrayInputStream(sb.toString().getBytes()), out);

        final var player = new ConsolePlayer(console);
        assertEquals("Please, introduce yourself: ", out.toString());
        out.reset();

        Assertions.assertEquals(Symbol.PAPER, player.getSymbol());
        assertTrue(out.toString().contains("Unknown symbol name, try again\n"));
    }
}
