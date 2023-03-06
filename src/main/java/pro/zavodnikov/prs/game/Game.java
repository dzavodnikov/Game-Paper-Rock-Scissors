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

import java.util.Objects;

import pro.zavodnikov.prs.Symbol;
import pro.zavodnikov.prs.player.Player;

/**
 * Implement base actions for tournament.
 *
 * @author Dmitry Zavodnikov
 */
public class Game {

    private final Player firstPlayer;
    private final Player secondPlayer;

    private int firstScore = 0;
    private int secondScore = 0;

    private final int rounds;

    public Game(final Player firstPlayer, final Player secondPlayer, final int rounds) {
        this.firstPlayer = Objects.requireNonNull(firstPlayer);
        this.secondPlayer = Objects.requireNonNull(secondPlayer);
        if (this.firstPlayer == this.secondPlayer) {
            throw new IllegalArgumentException("Players are the same");
        }
        if (this.firstPlayer.getName().equals(this.secondPlayer.getName())) {
            throw new IllegalArgumentException("Players have same name");
        }

        if (rounds < 1) {
            throw new IllegalArgumentException("Rounds number should not be less than one");
        }
        this.rounds = rounds;
    }

    /**
     * @return rounds in current game.
     */
    public int getRounds() {
        return this.rounds;
    }

    /**
     * @return score of first player.
     */
    public int getFirstScore() {
        return this.firstScore;
    }

    /**
     * @return score of second player.
     */
    public int getSecondScore() {
        return this.secondScore;
    }

    /**
     * Play the round.
     */
    protected void round(final int roundNum) {
        roundStart(roundNum);

        final var s1 = this.firstPlayer.getSymbol();
        final var s2 = this.secondPlayer.getSymbol();

        final var res1 = s1.beat(s2);
        if (res1 != null) {
            ++this.firstScore;
            roundResult(s1, s2, this.firstPlayer, res1);
            return;
        }

        final var res2 = s2.beat(s1);
        if (res2 != null) {
            ++this.secondScore;
            roundResult(s1, s2, this.secondPlayer, res2);
            return;
        }

        roundResult(s1, s2, null, "Draw in the round");
    }

    /**
     * Method that called before every game round and receive information about the
     * round.
     *
     * @param roundNum number of started round.
     */
    public void roundStart(final int roundNum) {
        // Do nothing.
    }

    /**
     * Method that called after every game round and receive information about round
     * result.
     *
     * @param firstSymbol  symbol that choose first player;
     * @param secondSymbol symbol that choose seconds player;
     * @param winner       player who win in round or <code>null</code> if draw in
     *                     the round;
     * @param comment      comment of the round.
     */
    protected void roundResult(final Symbol firstSymbol, final Symbol secondSymbol, final Player winner,
            final String comment) {
        // Do nothing.
    }

    /**
     * Play the game.
     *
     * @return player who win in the tour or <code>null</code> if draw in the tour.
     */
    public Player play() {
        for (var i = 0; i < this.rounds; ++i) {
            round(i);
        }

        if (this.firstScore > this.secondScore) {
            return this.firstPlayer;
        }
        if (this.firstScore < this.secondScore) {
            return this.secondPlayer;
        }
        return null;
    }
}
