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

import pro.zavodnikov.prs.Console;
import pro.zavodnikov.prs.Symbol;
import pro.zavodnikov.prs.player.Player;

/**
 * Implement multiple games between players.
 *
 * @author Dmitry Zavodnikov
 */
public class ConsoleGame extends Game {

    private final Console console;

    public ConsoleGame(final Console console, final Player player1, final Player player2) {
        super(player1, player2,
                console.read("How many rounds do you want to play? Input number and press <Enter>: ", s -> {
                    try {
                        final var n = Integer.parseInt(s);
                        return n > 0 ? n : null;
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }, "Wrong number, try again"));

        this.console = console;
    }

    @Override
    public void roundStart(final int roundNum) {
        this.console.println("Round %d/%d", roundNum + 1, getRounds());
    }

    @Override
    public void roundResult(final Symbol firstSymbol, final Symbol secondSymbol, final Player winner,
            final String comment) {
        if (winner != null) {
            this.console.println("%s. Player %s win!", comment, winner);
        } else {
            this.console.println(comment);
        }
    }

    @Override
    public Player play() {
        final var winner = super.play();
        final var score = String.format("%d:%d", getFirstScore(), getSecondScore());
        if (winner != null) {
            this.console.println("Player %s win the tour with score %s!", winner, score);
        } else {
            this.console.println("Draw in the game with score %s", score);
        }
        return winner;
    }
}
