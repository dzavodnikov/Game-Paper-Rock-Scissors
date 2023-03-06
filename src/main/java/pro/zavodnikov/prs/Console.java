/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2009-2023 Dmitry Zavodnikov
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;
import java.util.function.Function;

/**
 * Read/Write data to/from console.
 *
 * @author Dmitry Zavodnikov
 */
public class Console {

    private final BufferedReader in;
    private final PrintStream out;

    public Console(final InputStream is, final OutputStream os) {
        this.in = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)));
        this.out = new PrintStream(Objects.requireNonNull(os));
    }

    /**
     * Print formatted string.
     */
    public void print(final String str, final Object... args) {
        this.out.print(String.format(str, args));
    }

    /**
     * Print formatted string with new line after.
     */
    public void println(final String str, final Object... args) {
        this.out.println(String.format(str, args));
    }

    /**
     * Read and parse value.
     *
     * @param welcomeMsg message to explain user what we are expecting;
     * @param parser     function that will convert string to proper value;
     * @param errorMsg   message that will be shown if parser return <code>null</code>
     * @return parsed value
     */
    public <T> T read(final String welcomeMsg, final Function<String, T> parser, final String errorMsg) {
        try {
            print(welcomeMsg);
            final var line = this.in.readLine();
            if (line == null) {
                throw new RuntimeException("No input found");
            }
            var obj = parser.apply(line);
            while (obj == null) {
                println(errorMsg);

                print(welcomeMsg);
                obj = parser.apply(this.in.readLine());
            }
            return obj;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
