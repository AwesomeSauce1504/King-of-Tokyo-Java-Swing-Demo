# Personal Project Idea

## The idea: King of Tokyo

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

## Instructions for Grader (Phase 3)
GENERAL RUNNING INSTRUCTIONS
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

SPECIFIC GRADED INFORMATION
- You can generate the first required action related 
to adding Xs to a Y (adding Cards to a Player)
by clicking one of the three"buy card" buttons near 
the bottom left of the screen. Make sure that the 
current player has enough energy to purchase the 
card, otherwise nothing will happen. The
card will then show up under the player's cards.
- You can generate the second required action related 
to adding Xs to a Y (clearing all Cards owned by all
Players) by pressing the "CLEAR PLAYER
CARDS" button. This will clear all cards from
all players.
- You can locate my visual component by loading the
game. You will see a photo of the King Of Tokyo
box cover taken by me. 
- You can save the state of my application by pressing
the save button in game.
- You can reload the state of my application by
loading the saved game from the start screen.

## Phase 4: Task 2
NOTE: log will only print if the Exit Game button is 
pressed. Closing the window by pressing the "X" at 
the top right will not result in log printing.

Sun Apr 09 20:48:47 PDT 2023
Player 2 bought Card: Rapid Healing

Sun Apr 09 20:48:50 PDT 2023
Player 2 bought Card: National Guard

Sun Apr 09 20:48:54 PDT 2023
Player 1 bought Card: Frenzy

Sun Apr 09 20:48:56 PDT 2023
All player cards cleared.

Sun Apr 09 20:49:00 PDT 2023
Player 2 bought Card: Background Dweller

Sun Apr 09 20:49:00 PDT 2023
Player 2 bought Card: Alpha Monster

Sun Apr 09 20:49:01 PDT 2023
Player 2 bought Card: Commuter Train

Sun Apr 09 20:49:03 PDT 2023
Player 3 bought Card: Burrowing

Sun Apr 09 20:49:06 PDT 2023
All player cards cleared.

Sun Apr 09 20:49:07 PDT 2023
Player 1 bought Card: Complete Destruction

Sun Apr 09 20:49:16 PDT 2023
Player 3 bought Card: Alien Metabolism

Sun Apr 09 20:49:16 PDT 2023
Player 3 bought Card: Energy Hoarder

## Phase 4: Task 3
If I had more time to work on the project, I would 
improve my design by using a HashMap in my 
DieCollection class instead of 6 different integer 
fields to store dice results (as my TA suggested). 
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
