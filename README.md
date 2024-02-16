# AudioPolo
because I want to play MarcoPolo without swimming.

This project was made for my Computer Science Class's 2D-Array Project.

## Controls
Controls may vary based on release. For the controls of older versions, you can find information in the releases tab. The following are the most up to date controls:

### Outside of Game (if you cannot hear audio and your computer volume setting is not at 0)
- q + enter: quit (end program)
- b + enter: print last game board
- [any other char] + enter: start game

### In Game (if you can hear a "bouncing" audio)
- {w/a/s/d} + enter: move character {forward/left/backward/right}
- b + enter: show board

Immediately after clicking "Run", you will be "in game" and not "outside of [the] game".

## Objective
When in game, use w/a/s/d keys to try to move towards the "bouncing" sound without looking at the board itself (meaning you have to rely on directional audio). Not including the "bounce", you may hear two other sound effects:
- "smack": Means you hit a wall (and your position did not change). You cannot walk through walls, and you must walk around them to reach the target ("bouncing" sound). You are guarenteed to be able to reach the target from your start position, and the walls are stationary.
- "coin": Means you reached the target and have won! You are now outside of the game.

## Board Display (if you choose to click [b + enter] and show it):
### Outside of Game
```
x x x x x x x x 
x             x 
x     x I •   x 
x       x • x x 
x       • •   x 
x       •     x 
x     x T     x 
x x x x x x x x 
```
- '```I```': The initial position of the player (at game start)
- '```T```': The position of the target
- '```•```': A position the player has travelled to
- '```x```': A wall
- '``` ```': An empty position

### In Game
```
x x x x x x x x 
x             x 
x     x P     x 
x       x   x x 
x             x 
x             x 
x     x T     x 
x x x x x x x x
```
- '```P```': The position of the player
All other chars mean the same as in outside of game. Note that in-game board displays will not show where the player has travelled to already ('```•```')

## Installation:
Check the "releases" tab.
