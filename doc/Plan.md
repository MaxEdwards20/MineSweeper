# Development Plan

## Design
```java
mainActivity(){
    \\ Create buttons
        Place buttons on the screen
        Each button passes on its value to the GameActivity clas
        }


```
* Main Screen to allow user to select a difficulty
* Each button puts a new intent into the next activity

```java
public void GameActivity(){
    // Receive the intent type from mainActivity
        Add GameActivity to intents page
        Send the difficulty into a GameView constructor
        }
```

```java
public Class GameView()
        
        public void GestureDetector(){
    // method for long pres
            if long press send to handleLongpRes in game; pass in motionEvent
    // method for single tap
            if single tap sent to handleSingleTap in game; pass in motionEvent
        call invalidate() to set the values
        }
    
        onTouchListner(){
            calls the GestureDetector method
        }
    
        }
        }
```

```java
public cell
    method to draw the cell depending on which position it is placed in
```

## Testing
* Tested all three buttons on opening page for correct value passing
    * PASSED (easy, inter, exp)

* onSingleTap and onLongPress testing in logger
    * longPress works
    * onSingleTapUp is not functioning; no pickup from the screen
    * Rebuilt the program and both working properly now

# Questions:
* Line 84 of `Game.java`. Is this the correct way to be thinking about separating these characters?
* Line 130 of `Game.java`, how do I change paint color to be from the resources file?

