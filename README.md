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

The Farmer should now be able to farm! When the farmer is over farmable land they should be able to take action to farm the land.

The cycle is as above from left to right (till soil, then plant, then water, then harvest) and **only the next appropriate action should be available** to the farmer, meaning only one action needs to be added to the menu. This should be determined by a Farming Behaviour.

**Farmable land** includes Blight and Soil, and of course any of these grounds that have undergone any of the above actions (eg. soil with a plant on it is still deemed farmable and hence waterAction should still be an option)

For instance, when over untilled soil, a new TillSoilAction is available for selection in the menu. If the soil is tilled, then a PlantAction should be available instead, and so on and so forth.
