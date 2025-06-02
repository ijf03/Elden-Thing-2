# FIT2099 Assignment (Semester 1, 2025)

```
`7MM"""YMM  `7MMF'      `7MM"""Yb. `7MM"""YMM  `7MN.   `7MF'    MMP""MM""YMM `7MMF'  `7MMF'`7MMF'`7MN.   `7MF' .g8"""bgd  
  MM    `7    MM          MM    `Yb. MM    `7    MMN.    M      P'   MM   `7   MM      MM    MM    MMN.    M .dP'     `M  
  MM   d      MM          MM     `Mb MM   d      M YMb   M           MM        MM      MM    MM    M YMb   M dM'       `  
  MMmmMM      MM          MM      MM MMmmMM      M  `MN. M           MM        MMmmmmmmMM    MM    M  `MN. M MM           
  MM   Y  ,   MM      ,   MM     ,MP MM   Y  ,   M   `MM.M           MM        MM      MM    MM    M   `MM.M MM.    `7MMF'
  MM     ,M   MM     ,M   MM    ,dP' MM     ,M   M     YMM           MM        MM      MM    MM    M     YMM `Mb.     MM  
.JMMmmmmMMM .JMMmmmmMMM .JMMmmmdP' .JMMmmmmMMM .JML.    YM         .JMML.    .JMML.  .JMML..JMML..JML.    YM   `"bmmmdPY  
```

---
## Group Members

| Name              | Student ID |
|-------------------|------------|
| Chi Cheng Chan    | 34841369   |
| Sabi Fernan       | 34095756   |
| Sher Tan          | 33591660   |
| Trinh Nha Nguyen  | 33849293   |

---
## Contribution Log

You can view our live contribution log here:  
[Google Sheet Contribution Log](https://docs.google.com/spreadsheets/d/1a6TJRk4f_l8SX6GhBRNF8QUyOzPBJjpAmuNIMIC7yZ8/edit?usp=sharing)

---

## Working Design Rationale

Working design rationale document for the project can be found here:  
[Design Rationale Document](https://docs.google.com/document/d/1vlJXVJUXUDfD8AN_qWMRiZZTqDbkYoo2uu0D6eJDeLo/edit?usp=sharing)

## A3 Creative Mode Requirements

### Requirement 3
REQ3: Magic

The farmer reaches a state where they can gather mana and wield magic weapons. These magic weapons appear randomly on the game map.  
The Farmer can absorb mana through interactions: talking to a merchant adds 3 mana, purchasing a weapon adds 10 mana, attacking a non-hostile actor adds 10 mana and attacking a hostile actor adds 20 mana. Harvesting crops gains 3 mana, and if no action is taken, each turn adds 1 mana.

There are three magic weapons:

Lifesteal Fist (L): This replaces the bare fist. Each use costs 10 mana and has a 100% hit rate. It absorbs HP from the target, healing the farmer with 30% of the target maximum HP. In other words, damage made to the target is equal to the amount the farmer heals.

Frozen Armament (A): Each time it costs 20 mana and has a 100% hit rate. On hit, it applies a freezing effect to the target that costs 30 damage continuously for 5 turns.

Flaming Strike (F): Each use costs 30 mana and has a 100% hit rate. If the Farmer’s current mana is above 100, each attack costs 150 damage. If it is less than 100 mana, it deals 100 damage. And if the Farmer’s mana is 200 mana, it will inflict a burning effect that deals 150 damage for the next 3 turns.


### Requirement 4

A potion is a usable item in the players’ inventory. It is used by the player throwing it at other actors, and once thrown cannot be used again, it shatters on impact and an effect is given to the other actor for a certain amount of turns. This throwing action takes 5 stamina from the player.

Each potion is given an effect, which will extend from the StatusEffect interface in the engine, which the potion passes onto the other actor through the throw potion action. The effects are:

Invincibility
- The actor cannot be attacked, none of their stats will change and their number of remaining ticks will be frozen

Speed
- The actor will either speed up or slow down depending on the potion type, moving 2 spaces in one turn or 1 space in 2 turns respectively

Speak
- Non-speaking actors will now be able to speak and make sounds

  - If turns left is less than or equal to 5: “The rot is unforgiving but I would hope you are not, please friend save me if not for a few days more”
  - If turns left are more than 5: “Safe travels friend”