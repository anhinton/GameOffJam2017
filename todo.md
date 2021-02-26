# todo.md
      
  + Create animal objects
      - ~~Coco, Horse1, Horse2~~
      - States:
          - Normal
          - Sated (smiling)
          - Super-sated (has exploded, still smiling, black silhouette)
      - ~~All animals: wiggle~~
          - Use range of values to randomise wiggle for each animal
          - Define hitbox before wiggle starts
      - Change state when hit by can
      
  + Change grass colour to differentiate from plants

  + Create animated small can for throwing
      - Each colour of big can gets its own small can
          
  + Create explosion animation
      - Used for trees and animals
      
  + Create destroyed tree sprite
      - After you hit a tree with X cans
      
  + Player can gets stuck on plants/animals
  
  + Consider a more composed method for distributing plants/animals
      - On a grid?
      - Max number of items per grid square?
      - Rules?
      - Is this procgen?
      
  + Title screen
      - New game
          - Choose from one of five cans
          - Cans unlocked via progression?
      - Statistics
          - Number of sodas thrown
          - Number of sodas drunk
          - Animals sated
              - Horses
              - Guinea pigs
              - Others?
          - Trees destroyed
          - Sodas unlocked
          - Longest session
          - Time played
      - Settings
          - Music volume
          - Sound volume
          - Credits
          
  + **BUG**: There is a 1 (?) pixel gap between the side banners and the game area in GameScreen
      - Do I care enough to fix?

## Done
      
  + ~~Add animal sprites to the game~~
      - ~~Like grass/trees but more exciting!~~
      - ~~Game scrolls over time~~
      - ~~New animals "randomly" added to the top~~
  
  + ~~Add trees to game~~
      - ~~"Randomly" distributed~~
      - ~~Game scrolls over time~~
      - ~~New trees "randomly" added to the top~~
  
  + ~~Add grass to game~~
      - ~~"Randomly" distributed~~
      - ~~Game scrolls over time~~
      - ~~New grass "randomly" added to the top~~
      
  + ~~The higher on the screen an object is, the "further back" from the camera it is~~
      - ~~Sort gameObjectArray by Y value~~
      - ~~Draw objects in gameObjectArray from highest to lowest Y value~~

  + ~~GameScreen side banners displayed using FillViewport~~
  