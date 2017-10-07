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

# Update 2017/10/06

In order to facilitate the networking aspect of the game, I consulted a lot of different networking frameworks. At first, my thought was to use a REST based framework (like Play), but that would end up requiring too much additional complexity and overhead with database calls and the like.

Right now I'm trying to learn/use Netty to facilitate the networking. The current modus operandi is to rework the networking architecture as follows:

The Client:
    -Sends [Action]s to the server
    -Receives [VisibleGameState]s from the server
    -Registers its handler as a listener to the UI. When UI elements are interacted with, they will construct [Action]s and send them to the server.
    -Register the UI as a listener to the handler. When the handler receives game state changes from the server, it will change the UI to reflect that.
    -Note: Now, the role of generating the game UI is done completely in the Client construction, rather than the Main method. Obviously the Server will not need a UI.

The Server:
    -Sends [VisibleGameState]s to the clients
    -Receives [Action]s from the clients.
    -When it receives the [Action], it acknowledges the context of the channel that passed it. The server will maintain the relations between channels/players, so the server can determine whether the action was legal. If the action is legal, the server possibly updates its game state and pushes changes to the clients.
    -For each connected channel, the server can deduce which player the user is and construct the [VisibleGameState] based on what the player is allowed to see.

That's effectively the current state of the networking architecture. I'm also in the midst of a fairly wide scale Scala refactor, which I believe will make writing the code in the future a bit easier, so right now the code is definitely a mess. With a cross between adapting pre-existing code and throwing out old code if it isn't needed for the new networking paradigm, there are definitely a lot of files which will be deleted in the near future.

I'm still eager to continue work on this project, but schoolwork and the like may keep me from devoting as much time as I'd like.