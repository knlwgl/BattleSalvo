- added ShipOrientation enum to ship
- added GameType enum (SINGLE / MULTI etc) to model
- changed way player is initialized : instead of waiting for board dimensions and then creating boards to pass to
player, passed empty boards to player and initialized them in setup() method when dimensions are provided.
- changed board initialization in general; instead of initBoard returning the board, it mutates the board field.
- notTaken list of coords moved from artificialplayer to board (to allow for initialization at the same time as the board)