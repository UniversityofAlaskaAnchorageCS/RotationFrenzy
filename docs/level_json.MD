# Level file Example

```json
{
  "chapterID": 1,
  "levelID": 1,
  "levelName": "Angles in Degrees",
  "levelQuestion": [
    "This arctic ground squirrel needs to get into its den.",
    "Help it "ride the wheel" <some number of degrees> to the den entrance…",
    "(If you miss, an eagle swoops in and eats the squirrel.)"],
  "type": "Enter",
  "attempts": 3,
  "timeLimit": 60,
  "angleUnitType": "Degrees",
  "denExists": true,
  "denStartVisible": false,
  "denStartRotationMin": 30,
  "denStartRotationMax": 330,
  "eagleStartPositions": [ {"x": -50, "y": 50} ],
  "eagleStartRotations": [ ],
  "eagleStartVisible": true,
  "eagleMovementType": "Attack",
  "eagleRotationSpeedType": "None",
  "eagleRotationSpeedMin": 0,
  "eagleRotationSpeedMax": 0,
  "acornStartRotations": [ ]
}
```

---
# Level file Definitions
---
## Level Specific variables
### _chapterID_ << (Required)

* **Data Type:** Integer
* **Purpose:** Used to populate the Chapter and Level select screen.

### _levelID_ << (Required)

* **Data Type:** Integer
* **Purpose:** Used to populate the Chapter and Level select screen.

### _levelName_ << (Required)

* **Data Type:** String
* **Purpose:** Used to populate the Chapter and Level select screen.

### _levelQuestion_ << (Required)

* **Data Type:** ArrayList
* **Purpose:** Text to display to the user describing the level.  
* **Example:** "Move the squirrel N degrees."
* **NOTE:** This can be a multi line array with [ "line1",\n "line2", \n etc] with real new lines like the example JSON above.

### _type_ << (Required)

* **Data Type:** String
* **Purpose:** To define the behavior for the level.
* **Values:**
  * Enter = Level where the player has to type or select the rotation value.
  * Touch = Level where the player must move the wheel with their finger/mouse to the approx rotation value.
* **Example:** Enter

### _attempts_ << (Required)

* **Data Type:** Integer
* **Purpose:** Controls how many times the player can be wrong on the level
* **Values:**
  * 0 = infinite
  * 1+ = # of attempts
* **Example:** 3
* **Meaning:* a value of 3 means the get 3 tries.

### _timeLimit_ << (Required)

* **Data Type:** Integer
* **Purpose:** Number of seconds the player has on the level before they fail.
* **Example:** 60

### _angleUnitType_ << (Required)

* **Data Type:** String
* **Purpose:** Define the expected input/output measuring format.
* **Values:** 
  * Degrees
  * Radians
  * Random = Level will randomly pick a type.
* **Example:** Degrees

---
## Den Specific variables

### _denExists_

* **Data Type:** Boolean
* **Purpose:** Determines if the den is part of the level or not
* **Values:** true, false.
* **Example:** true
* **NOTE:** We could possible code this to be optional and if null don't add den.

### _denStartVisible_

* **Data Type:** Boolean
* **Purpose:** When the level starts, should the den be visible, or does the user have to first touch the screen, and release to see if their guess was correct.
* **Values:** true, false.
* **Example:** true

### _denStartRotationMin_

* **Data Type:** Float
* **Purpose:** The minimum angle (in degrees) that the den can be placed at along the outside of the wheel.
* **Example:** 90
* **Meaning:** The den would be somewhere between 90 degrees and the max rotation value `denStartRotationMax`.
* **Note:** The den does NOT move after being placed.

### _denStartRotationMax_

* **Data Type:** Float
* **Purpose:** The maximum angle (in degrees) that the den can be placed at along the outside of the wheel.
* **Example:** 180
* **Meaning:** The den would be somewhere between `denStartRotationMin` and 180 degrees.
* **Note:** The den does NOT move after being placed.

---
## Eagle Specific variables

### _eagleStartPositions_

* **Data Type:** Vector2 Array
* **Purpose:** Determines how many eagles there are.  Starting positions of the eagles, can not use this and `eagleStartRotations`, negative means from bottom or from top
* **Values:**
  * Positive values = exact (X,Y) position on the screen.
  * Negative values = subtract from the other side of the screen.
* **Example1**
  * **Value:** [{x: 123, y: 567}]
  * **Meaning:** Place the eagle at position (123,567)
* **Example2:**
  * **Value:** [{x: 123, y: 567}, {x: -50, y: 100}]
  * **Meaning:** Place two eagles.  One at position (123,567), and the second at (screenWidth-50, 100)

### _eagleStartRotations_

* **Data Type:** Float Array
* **Purpose:** Determines how many eagles there are.  Starting rotations of the eagles (in degrees), can not use this and `eagleStartPositions`.
* **Example1**
  * **Value:** [60]
  * **Meaning:** Place one eagle on the edge of the wheel at 60 degrees.
* **Example2:**
  * **Value:** [60, 300]
  * **Meaning:** Place two eagles.  One at 60 degrees, and the second at 300 degrees


### _eagleStartVisible_

* **Data Type:** Boolean
* **Purpose:** Should the eagle be visible when the level starts?
* **Example:** true

### _eagleMovementType_

* **Data Type:** String
* **Purpose:** Determine how the eagle will move on the level.
* **Values:**
  * Attack = The eagle(s) will swoop in and eat the squirrel after the level `timelimit` is up.
  * Rotate = The eagle(s) will rotate (fly) around the wheel at a given speed.  Used in conjunction with `eagleRotationSpeedType`, `eagleRotationSpeedMin`, and `eagleRotationSpeedMax`.
* **Example:** Attack

### _eagleRotationSpeedType_

* **Data Type:** String
* **Purpose:** 
* **Values:**
  * PickBetween = Randomly choose a rotation speed between `eagleRotationSpeedMin` and `eagleRotationSpeedMax`.
  * IncreaseOverTime = Slowly increment from `eagleRotationSpeedMin` to `eagleRotationSpeedMax` over the level's `timelimit`.
  * None = No rotation
* **Example:** PickBetween

### _eagleRotationSpeedMin_

* **Data Type:** Float
* **Purpose:** Lowest rotation speed for the eagles.
* **Example:** ?? TODO: Define this speed

### _eagleRotationSpeedMax_

* **Data Type:** Float
* **Purpose:** Highest rotation speed for the eagles.
* **Example:** ?? TODO: Define this speed

---
## Acorn Specific variables

### _acornStartRotations_

* **Data Type:** Float Array
* **Purpose:** Determines how many Acorns there are.  Rotation angle to place the acorns in Degrees.
* **Example1**
  * **Value:** [60]
  * **Meaning:** Place one acorn on the edge of the wheel at 60 degrees.
* **Example2:**
  * **Value:** [60, 150]
  * **Meaning:** Place two acorns.  One at 60 degrees, and the second at 150 degrees



# History

* 03/21/2018 - Jeremy Swartwood - Initial version 
* 04/29/2018 - Jeremy Swartwood - Added chapterID, levelName, and Added "required" hints.


