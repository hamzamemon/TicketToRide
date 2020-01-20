# TicketToRide
Implementation of the UK version of Ticket to Ride written in Java, which can be found [here](https://www.daysofwonder.com/tickettoride/en/united-kingdom/).

#### Description
This group project was created in my CSIS 225: Object Oriented Programming course. It utilizes Swing
GUI component and a CardLayout to increase readability.

#### Gameplay
After starting the game, you are presented with the instructions and setup options to determine the
number of players and color and destination cards per player.

When choosing train cards, players can either
1. Choose two blind cards
2. Choose one face-up locomotive card
3. Choose two face-up non-locomotive cards
4. Choose one face-up non-locomotive card and one blind card

When choosing destination cards, you are given a choice between three cards and you *must* choose at
least one.

When choosing routes, routes are only highlighted if you have the technologies needed (like the
Concessions the Mechanical Stoker) and the appropriate train cards.

The game ends when a single player has at most 2 trains. Points are calculated by special
technologies, route lengths, summing up completed routes (which is calculated by Depth-First Search) and
subtracting off incomplete route.
