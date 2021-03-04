# todo.md

  + Implement game/scoring UI
      - ~~Cans thrown~~
      - ~~Cans delivered~~
      - ~~Score~~
      - ~~Time~~
      - Quit
          - ESC (exit) on ~~desktop~~/html
          - X button on Android/iOS
      - Make this look a lot better
      - Save these on exit
      
  + Implement localization
      
  + Implement statistics preferences
      - Loaded by Title screen and Game screen
      - During GameScreen these are incremented according to game actions
      - Saved out to file when exiting GameScreen
      
  + Implement Exit UI from GameScreen
  
  + Add score particle to Animal/Plant when points are scored
      
  + Create a tutorial at start of game
      
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
          - Total time played
      - Settings
          - Music volume
          - Sound volume
          - Credits
  
  + Re-evaluate how much Y-offset there is from touch point on mobile
      - Currently it feels like it might be too much
      - Now setting Player x/y to centre of sprite. Has this made a difference?

  + Do Animals need a smiling had-a-can state before exploding?
  
  + Consider a more composed method for distributing plants/animals
      - On a grid?
      - Max number of items per grid square?
      - Rules?
      - Is this procgen?
      
  + Investigate using Pools for
      - AnimatedCan
      - Explosion
      - Animal
      - Plant
          
  + **BUG**: There is a 1 (?) pixel gap between the side banners and the game area in GameScreen
      - Do I care enough to fix?
      - The easy fix is probably just going back to and ExtendViewport
      
  + **BUG**:  Launching from app menu of Moto G test device shows a black bar where bottom
    system bar would display. Doesn't seem to when "running" game from Android Studio
      - Problem doesn't seem to happen on AVD device so maybe I'll see if this goes away once I
        implement a loading screens, menu screens, etc

## Done
      
  + ~~Create destroyed tree sprite~~
      - ~~After you hit a tree with 3 cans~~
          
  + ~~Create Hittable interface~~
      - ~~Implemented by Animal and Plant classes~~
      - ~~Provides getHitBox() function~~
      - ~~Provides hit() function~~
      
  + ~~Create animal objects~~
      - ~~Coco, Horse1, Horse2~~
      - ~~States:~~
          - ~~Normal~~
          - ~~Hit~~
          - ~~Superhit (has exploded, black silhouette)~~
      - ~~All animals: wiggle~~
          - ~~Define hitbox before wiggle starts~~
      - ~~Change state when hit by can~~
          - ~~Superhit state!~~
          
  + ~~Create explosion animation~~
      - ~~Used for trees and animals~~
      - ~~Also a tiny one for cans~~
      - ~~Actually a single ParticleEffect that I modify for different sizes/colours~~

  + ~~Create animated small can for throwing~~
      - ~~Each colour of big can gets its own small can~~
          - ~~Blue can~~
          - ~~Orange can~~
          - ~~Purple can~~
          - ~~Silver can~~
          - ~~Yellow can~~
      - ~~AnimatedCan object that does things~~
          - ~~Update y position based on delta~~
          - ~~New can added to GameScreen only after a time has elapsed~~
          - ~~Only add new can when "firing"~~
      - ~~Create getHitBox() function~~
      - ~~GameScreen uses getHitBox() to check against GameObjects~~
      - ~~Explodes when it hits something Hittable~~
      
  + ~~Change grass colour to differentiate from plants~~

  + ~~Set grass/plant/animal spawn randomisation using Constants~~

  + ~~Try out the different soda colours to see if this game is at all legible~~
      - ~~Add drop shadow to small cans to help stand out~~
      
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
  