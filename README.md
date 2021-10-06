# Project Neon

<h2>Description</h2>
Project neon is a small 2D-Platformer designed in late 2016 as a part-time hobby project. The key motivation behind making this game was to practive software-rendering techniques as well as to test the culmination of my software and Java development skills, at the time. This project was the start of the 'from scratch' challenges that shaped my development as a programmer.

<h2>Features</h2>
Below are listed the design choices imposed during the creation of the game:
<ul>
  <li><h3>Sofware rendered</h3></li>
  With the only exception being the UI, the entire game is rendered using hand-written rasterisation techniques. All graphics are drawn, pixel-by-pixel onto an image buffer which is then draw onto the screen.
  <li><h3>Saving/Loading</h3></li>
  Rudimentary save game mechanics were implemented before any knowledge of standardised methods such as xml files.
  <li><h3>From scratch</h3></li>
  As briefly mentioned in the description, the goal was to limit the dependancy on external resources and built-in Graphics2D library. The physics, collision detection, management and graphics are all handled with self-written code. 
</ul>
