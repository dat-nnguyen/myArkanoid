# Arkanoid Game

![Game Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/game.png?raw=true)
![Tutorial Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/tutorial.png?raw=true)
![Shop1 Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/shop.png?raw=true)
![Shop2 Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/shop2.png?raw=true)
![Play Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/play1.png?raw=true)
![Pause Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/pause.png?raw=true)
![Over Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/over.png?raw=true)
![Win Title](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/win.png?raw=true)

---

## II. Project Overview

**Arkanoid** is a classic brick–breaker game recreated using **Java 25** and **JavaFX**, built with Object-Oriented Programming (OOP) principles and clean architectural design.  
The project demonstrates structured game development, UI management, animation handling, and a modular system supporting upgrades, skins, and levels.

---

## 🎮 Technical Information

- **Programming Language**: Java 25
- **Framework**: JavaFX
- **Game Type**: Breakout / Brick Breaker
- **Architecture**: OOP-based, event-driven, modular game engine
- **Core Concepts Used**:
    - Encapsulation
    - Inheritance
    - Polymorphism
    - Composition
    - Basic design patterns (Factory, Singleton, Strategy applied in smaller modules)

---

## ⭐ Key Features

### 🎯 Core Gameplay
- Smooth paddle movement with real-time collision detection
- Brick-breaking mechanics with dynamic ball physics
- Level progression with increasing difficulty

### 🛒 In-Game Shop
- Multiple skins for both the **ball** and **paddle**
- Purchasable using score-based currency
- Persisted across sessions

### ⚡ Power-Up System
- Beneficial and harmful items dropped from bricks
- Each power-up affects:
    - Ball speed
    - Paddle size
    - Player health
    - Scoring multiplier

### 🔊 Sound System
- Real-time sound effects (collision, item pickup, UI interactions)

### 📊 Leaderboard & Save System
- Stores player name and high scores
- Displays top **8 highest-scoring players**

---

## UML Diagram

![UML Diagram](https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/Akanoi%20UML.png)

---

## Code Structure

<table align="center" style="border:none;">
  <tr>
    <td valign="top" style="border:none;">
      <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/3.png" alt="UI Structure" width="300"/>
    </td>
    <td valign="top" style="border:none;">
      <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/2.png" alt="PowerUp Structure" width="300"/>
    </td>
    <td valign="top" style="border:none;">
      <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/1.png" alt="General Structure" width="300"/>
    </td>
  </tr>
</table>

---

## IV. Skin Collection

### 🎱 Ball Skins
<div style="display:flex; flex-wrap:wrap; justify-content:center; gap:10px;">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin4.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin6.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin2.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin8.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin5.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin3.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/ballSkins/ballSkin7.png?raw=true">
</div>

### 🛹 Paddle Skins
<div style="display:flex; flex-wrap:wrap; justify-content:center; gap:10px; margin-top:20px;">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/greenPaddle.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/purplePaddle.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/bluePaddle.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/redPaddle.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/orangePaddle.png?raw=true">
  <img src="https://github.com/Group8-OOPProject/ARKANOID_PROJECT/blob/main/resources/images/shopImages/paddleSkins/pinkPaddle.png?raw=true">
</div>

---

## V. Gameplay Guide

### Controls
- **← / →** – Move paddle left/right
- **Space** – Launch the ball
- **Mouse** – Navigate UI menus and buttons

---

### 🎁 Beneficial Power-Ups

| Power-Up | Effect | Rarity |
|---------|--------|--------|
| **Paddle Size Up** | Paddle increases by 25% | Common |
| **Ball Slowdown** | Ball speed reduced by 30% | Common |
| **Heal** | +1 life | Rare |
| **Score Multiplier ×2** | Doubles current score | Very Rare |

---

### ⚠️ Harmful Power-Ups

| Power-Up | Effect | Warning |
|---------|--------|---------|
| **Paddle Size Down** | Paddle shrinks by 25% | Dangerous |
| **Ball Speed Up** | Ball speed increases by 40% | Very Dangerous |
| **Global Slowdown** | Slows paddle, ball, and effect duration | Caution |
| **Instant Death** | Player loses immediately | EXTREMELY DANGEROUS |

---

## VI. Conclusion

The **Arkanoid Project** is not only a fun arcade-style game but also a solid demonstration of applying OOP principles in a real-world project.  
Through this implementation, the team successfully practiced key concepts such as:

- Encapsulation
- Inheritance
- Polymorphism
- Modular code architecture
- Basic design patterns and game-loop structures

This project serves as a strong foundation for more advanced Java game development and OOP-based system design.

---
