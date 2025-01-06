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
package pro.zavodnikov.prs.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;

import pro.zavodnikov.prs.Symbol;
import pro.zavodnikov.prs.player.ConstantPlayer;

/**
 * Tests for {@link Game}.
 *
 * @author Dmotry Zavodnikov
 */
class GameTest {

    @Test
    void testConstructor() {
        final var firstPlayer = new ConstantPlayer("First Player", Symbol.PAPER, Symbol.ROCK, Symbol.SCISSORS);
        final var secondPlayer = new ConstantPlayer("Second Player", Symbol.SCISSORS, Symbol.ROCK, Symbol.PAPER);
        final var wrongPlayer = new ConstantPlayer(firstPlayer.getName(), Symbol.SCISSORS, Symbol.ROCK, Symbol.PAPER);

        assertThrowsExactly(NullPointerException.class, () -> new Game(firstPlayer, null, 3));
        assertThrowsExactly(NullPointerException.class, () -> new Game(null, secondPlayer, 3));
        assertThrowsExactly(IllegalArgumentException.class, () -> new Game(firstPlayer, secondPlayer, 0));
        assertThrowsExactly(IllegalArgumentException.class, () -> new Game(firstPlayer, firstPlayer, 3));
        assertThrowsExactly(IllegalArgumentException.class, () -> new Game(firstPlayer, wrongPlayer, 3));
    }

    @Test
    void testGameDraw() {
        final var firstPlayer = new ConstantPlayer("First Player", Symbol.PAPER, Symbol.ROCK, Symbol.SCISSORS);
        final var secondPlayer = new ConstantPlayer("Second Player", Symbol.SCISSORS, Symbol.ROCK, Symbol.PAPER);

        final var game = new Game(firstPlayer, secondPlayer, 3);
        final var winner = game.play();
        assertNull(winner);
        assertEquals(1, game.getFirstScore());
        assertEquals(1, game.getSecondScore());
    }

    @Test
    void testGameWinner() {
        final var firstPlayer = new ConstantPlayer("First Player", Symbol.PAPER, Symbol.PAPER, Symbol.PAPER);
        final var secondPlayer = new ConstantPlayer("Second Player", Symbol.SCISSORS, Symbol.ROCK, Symbol.SCISSORS);
        final var game = new Game(firstPlayer, secondPlayer, 3);
        final var winner = game.play();
        assertEquals(secondPlayer, winner);
        assertEquals(1, game.getFirstScore());
        assertEquals(2, game.getSecondScore());
    }
}
