# EscapeFromTheAliensInOuterSpace

Escape from the Aliens in Outer Space is a multiplayer board game, in which all players have a hidden identity (alien or human), and are tasked with navigating secretly throughout a game board. The humans are trying to get to an escape pod within 40 turns, and the aliens are trying to kill the humans before they can do so.
To make matters complex, however, players don't know the exact locations of other players on the game board. Only when a player steps in a dangerous sector is there any revealed information. When a player steps in a dangerous sector, he or she draws a card from a deck, and either calls out "Silence in all sectors" or "Noise in sector ##".
However, in the interest of deception, some cards force players to truthfully state their current location, whereas some cards allow the player to lie and state any location. This involves clever decision making and critical bluffing. If the player gives out locations that are nonsensical compared to previous calls, it may tip off to the others that the call is false. A well played bluff usually involves stating a location that's realistically close to their current location, but far enough away that it creates a false trail.
When an alien believes to have found a human, they may declare an attack. An attack instantly kills all other players who are in the same sector as the alien. However, this may include aliens as well, so hunting in too close a pack, or putting on a false charade as a human can be just as dangerous as it is clever.
Players win once their human reaches an escape pod, or if the player is an alien, when all humans are dead or 40 turns have passed.

# The Code

I undertook this project in an attempt to learn a bit about GUI programming and network programming. I was inspired to do so by my software design class, where we learned a lot of the design patterns I have tried to incorporate into this project as it develops.
The game's implementation is currently intended to involve the Command design pattern. Players will send movement/attack commands to the server, which will change state if proper conditions are met (e.g. correct player's turn, move is valid, etc). All clients will poll the server to get the changes to the state, and they will update their own views accordingly.

# Current Progress

Right now, I am trying to implement the networking code that will allow players to connect and transfer data between themselves and the host server. As it stands, the polling method is not very efficient, and I am currently learning more about best networking practices for multiplayer games. I intend that I will use a multithreaded server to accept clients' requests and send changes to the state when they occur, likely using callbacks and the Observer design pattern. That is the current hurdle to face.

Once that is done, moving the current pieces of game logic, which currently sit in the model package, unused, into state information that the server will distribute and update will be the next task. Once the basic commands are set up, the game loop/turn structure will be the next step.

After that point, extensibility and minor updates will be all that remains. I plan to more fully flesh out the features of the game, perhaps adding a more robust game lobby for players to connect to. Advanced settings, custom maps, and the additional rule variants that can be found in the board game are potential future extensions to the project, once the basic game has been implemented.
