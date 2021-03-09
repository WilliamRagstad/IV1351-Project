# SoundGood Project

IV1351 Data Storage Paradigms application for project task 4 (Programmatic Access).

Program is written in Eclipse IDE 2020-06 (4.16.0).

## Introduction

The program can handle the following transactions with the SoundGood database designed in previous tasks.

- **List instruments** of a certain kind (guitar, saxophone, etc) that are available to rent. Instruments which are already rented are not included in the listing. The listing show brand and price for each listed instrument.
- **Rent instrument**. Since different instruments of the same kind might have different prices, it is possible to specify exactly which particular instrument to rent, not just any instrument of the desired kind. Remember that students are not allowed to rent more than two instruments at the same time, your program must check that this limit is not exceeded.
- **Terminate rental** of an ongoing student rental. The database will still contain all information about the rental, but also show that the rental has been terminated.
