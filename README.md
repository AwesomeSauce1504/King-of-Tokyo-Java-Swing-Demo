# Personal Project: King of Tokyo

The application will be a replica of the board
game *King of Tokyo*. It will have an arbitrary
number of player boards each with life, 
energy, victory points, and current
Keep power cards. There will also be a deck with
an arbitrary number of buyable "cards" each
with energy cost and the effect (Note that to 
simplify the project, cards may
be simplified to effects that are easier
to track). There will also be some randomized 
"dice" system for player turns that resolve
to their proper effects. The game-state will be
able to be saved at any point and reloaded. 

## Why this idea?

People who like the board game *King of Tokyo*
might want a digital method to play this game. 
This project is of interest to me because I 
like *King of Tokyo* as a party game with 
friends. 

## User stories
- As a user, I want to be able to view my health,
energy, and current victory points as well as 
those of my opponents
- As a user, I want to be able to purchase cards
from an arbitrarily-sized deck and add them to
my arbitrarily-sized collection
- As a user, I want to be able to reroll the shop by
spending energy
- As a user, I want to be able to "roll" dice 
and gain energy from them in order to buy cards
- As a user, I want to be able to save the entire
game-state (turn, player energy, cards owned, etc.)
- As a user, I want to be able to reload a saved
game-state

## Stretch Goals
- As a user, I want to have a "turn" system that
still allows for out-of-turn effects
- As a user, I want to win when either all other
players are dead or once I reach a certain 
number of victory points
- As a user, I want every dice effect to actually 
resolve (not just energy)

## GENERAL RUNNING INSTRUCTIONS
- Run the game by running the main method 
- Get into the main game by either loading the saved
game or by starting a new game (input a number of 
players to be in game)
- Dice rolling automatically happens at the start of
a player's turn. Going to the next player's turn by 
clicking the "next player" button will roll the 
dice for you. This is the only way to gain more 
energy. The dice roll values are displayed
along the center of the screen.
- There is a reroll button that rerolls all shop cards
for 2 energy. Use it if you want.
- It is not recommended to load too many players in,
but you can. It will be harder to see what happens,
though. Scroll bars should automatically appear.

NOTE: log will only print if the Exit Game button is 
pressed. Closing the window by pressing the "X" at 
the top right will not result in log printing.

## Future ToDo
If I had more time to work on the project, I would 
improve my design by using a HashMap in my 
DieCollection class instead of 6 different integer 
fields to store dice results. 
This is beneficial because it reduces repetition 
in the code. This would be implemented by removing
all the fields referring to numberOfXs and instead
adding in a HashMap that maps certain dice values 
to HashMap keys. Another refactoring I could make 
is moving the createPlayerInfoText() method from 
GraphicalUserInterfaceApp into Player and 
createCardText() to Card. This would improve 
cohesion and fulfill the single responsibility 
principle better because those two methods have 
more to do with a Player and Card object rather 
than the GUI. This would also make these methods 
testable in the test suite. I could also refactor 
the GameManger class by removing one of either the
currentPlayer field or currentPlayerNumber field 
in GameManager because they essentially fulfill 
the same purpose of tracking which player is the 
current player. This would be beneficial because 
it removes a redundant field. However, it may 
also be disadvantageous because the fields are 
clear statements and easy inputs when each one 
is needed. If the refactoring were to be done, 
the currentPlayer field would be replaced by calls 
to playersInGame.get(index) or the 
currentPlayerNumber would be replaced by 
playersInGame.get(index).getPlayerNumber(). 
