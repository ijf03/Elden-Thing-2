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


### Requirement 4

The Farmer should now be able to farm! When the farmer is over farmable land they should be able to take action to farm the land.

The cycle is as above from left to right (till soil, then plant, then water, then harvest) and **only the next appropriate action should be available** to the farmer, meaning only one action needs to be added to the menu. This should be determined by a Farming Behaviour.

**Farmable land** includes Blight and Soil, and of course any of these grounds that have undergone any of the above actions (eg. soil with a plant on it is still deemed farmable and hence waterAction should still be an option)

For instance, when over untilled soil, a new TillSoilAction is available for selection in the menu. If the soil is tilled, then a PlantAction should be available instead, and so on and so forth.
