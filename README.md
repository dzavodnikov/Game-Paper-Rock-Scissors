# Paper-Rock-Scissors game on Java

This program allowed to play in Paper-Rock-Scissors game for two players. Each player simultaneously choose the symbol.

The winner is determined by the following schema:

- Paper beats (wraps) rock;
- Rock beats (blunts) scissors;
- Scissors beats (cuts) paper.

This program plays the game between the computer and a real player using console mode. Console player able to play the
game few times before the program exits.

## Build the application

Execute in command line:

    $ ./gradlew build

and find distribution in `build/distributions`.

## Run the application

Execute in command line:

    $ ./gradlew run --console=plain

and play in console.

## License

Distributed under MIT License.
