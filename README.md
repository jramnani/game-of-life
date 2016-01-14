# game-of-life

This project is a code kata for implementing [Conway's Game of Life].

## Rules

The rules of the game are as follows.

The universe of the Game of Life is an infinite two-dimensional orthogonal grid of square cells, each of which is in one of two possible states, alive or dead. Every cell interacts with its eight neighbours, which are the cells that are horizontally, vertically, or diagonally adjacent. At each step in time, the following transitions occur:

* Any live cell with fewer than two live neighbours dies, as if caused by under-population.
* Any live cell with two or three live neighbours lives on to the next generation.
* Any live cell with more than three live neighbours dies, as if by over-population.
* Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.


The initial pattern constitutes the seed of the system. The first generation is
created by applying the above rules simultaneously to every cell in the
seed—births and deaths occur simultaneously, and the discrete moment at which
this happens is sometimes called a tick (in other words, each generation is a
pure function of the preceding one). The rules continue to be applied repeatedly
to create further generations.


## Usage

FIXME

## License

Copyright © 2016 Jeff Ramnani using the [MIT License].


[Conway's Game of Life]: https://en.wikipedia.org/wiki/Conway's_Game_of_Life
[MIT License]: http://choosealicense.com/licenses/mit/
