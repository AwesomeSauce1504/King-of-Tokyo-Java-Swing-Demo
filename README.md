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

## Instructions for Grader
GENERAL RUNNING INSTRUCTIONS
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
to adding Xs to a Y by clicking one of the three 
"buy card" buttons near the bottom left of the screen.
Make sure that the current player has enough energy to
purchase the card, otherwise nothing will happen. The
card will then show up under the player's cards.
- You can generate the second required action related 
to adding Xs to a Y by pressing the "CLEAR PLAYER
CARDS" button. This will clear all cards from
all players.
- You can locate my visual component by loading the
game. You will see a photo of the King Of Tokyo
box cover taken by me. 
- You can save the state of my application by pressing
the save button in game.
- You can reload the state of my application by
loading the saved game from the start screen.