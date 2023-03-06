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

import java.util.Random;

import pro.zavodnikov.prs.Symbol;

/**
 * Player that randomly select new symbol on every step.
 *
 * @author Dmitry Zavodnikov
 */
public class RandomPlayer extends AbstractPlayer {

    private final Random rand;

    public RandomPlayer(final String name, final Long seed) {
        super(name);
        this.rand = seed != null ? new Random(seed) : new Random();
    }

    public RandomPlayer(final String name) {
        this(name, null);
    }

    @Override
    public Symbol getSymbol() {
        final var symbolIdx = rand.nextInt(Symbol.values().length);
        return Symbol.values()[symbolIdx];
    }
}
