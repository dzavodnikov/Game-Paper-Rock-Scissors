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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Console}.
 *
 * @author Dmotry Zavodnikov
 */
class ConsoleTest {

    @Test
    void testConstructor() {
        assertThrowsExactly(NullPointerException.class, () -> new Console(null, new ByteArrayOutputStream()));
        assertThrowsExactly(NullPointerException.class,
                () -> new Console(new ByteArrayInputStream("".getBytes()), null));
    }

    @Test
    void testPrint() {
        final var out = new ByteArrayOutputStream();
        try (Console console = new Console(new ByteArrayInputStream("".getBytes()), out)) {
            final var simpleStr = "Test string";
            console.print(simpleStr);
            assertEquals(simpleStr, out.toString());
            out.reset();

            final var formatStr = "Format string %s";
            final var frag = "TEST";
            console.print(formatStr, frag);
            assertEquals(String.format(formatStr, frag), out.toString());
            out.reset();
        }
    }

    @Test
    void testPrintLn() {
        final var out = new ByteArrayOutputStream();
        try (Console console = new Console(new ByteArrayInputStream("".getBytes()), out)) {
            final var line1 = "Line %d";
            console.println(line1, 1);

            final var line2 = "Line %d";
            console.println(line2, 2);

            assertArrayEquals(new String[] { String.format(line1, 1), String.format(line2, 2) },
                    out.toString().split("\n"));
        }
    }

    @Test
    void testRead() {
        final var input = "1,2,abc\n3,4,5";
        final var out = new ByteArrayOutputStream();
        try (Console console = new Console(new ByteArrayInputStream(input.getBytes()), out)) {

            final var welcome = "WELCOME: ";
            final var error = "ERROR";
            final Function<String, Integer[]> parser = s -> {
                try {
                    final var res = new ArrayList<Integer>();
                    for (var frag : s.split(",")) {
                        res.add(Integer.parseInt(frag));
                    }
                    return res.toArray(new Integer[res.size()]);
                } catch (NumberFormatException e) {
                    return null;
                }
            };

            assertArrayEquals(new Integer[] { 3, 4, 5 }, console.read(welcome, parser, error));
            assertEquals(welcome + error + "\n" + welcome, out.toString());
            out.reset();
        }
    }
}
