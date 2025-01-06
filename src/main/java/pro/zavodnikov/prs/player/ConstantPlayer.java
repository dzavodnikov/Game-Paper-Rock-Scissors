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

import pro.zavodnikov.prs.Symbol;

/**
 * Player that use predefined symbols.
 *
 * @author Dmitry Zavodnikov
 */
public class ConstantPlayer extends AbstractPlayer {

    private final Symbol[] symbols;

    private int position;

    public ConstantPlayer(final String name, final Symbol... symbols) {
        super(name);

        if (symbols.length == 0) {
            throw new IllegalArgumentException("You are should define symbols");
        }
        for (var s : symbols) {
            if (s == null) {
                throw new IllegalArgumentException("Null is not allowed as a symbol");
            }
        }
        this.symbols = symbols;
        this.position = 0;
    }

    @Override
    public Symbol getSymbol() {
        final var s = this.symbols[this.position];
        ++this.position;
        if (this.position >= this.symbols.length) {
            this.position = 0;
        }
        return s;
    }
}
