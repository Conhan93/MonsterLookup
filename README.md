# MonsterLookup
___
[![Kotlin version][kotlin_img]][kotlin_url]
[![Compose version][compose_img]][compose_url]

A side project to familiarise myself with Compose.

## About

Desktop app that lets you search for monsters from Dungeons and Dragons and easily view them and their actions, abilities and stats.
For your DM:ing needs. Currently only supports monsters and spells that pops up when you select an ability containing spells.

The app UI is built with [Compose Multiplatform](https://www.jetbrains.com/lp/compose-mpp/) and supports both dark and light themes.
Uses an embedded H2 database for local storage to cache recently fetched monsters and spells.

The information is fetched from an open Dungeons and Dragons [API](https://www.dnd5eapi.co/) 
that lets you fetch information from monsters, items, spells, etc in JSON format. 

## Images
<figure>
<img src="Images/LookupDisplay.PNG" alt="Image showing the display screen for Adult White Dragon">
<figcaption>Display Screen</figcaption>
</figure>
<br>
<figure>
<img src="Images/LookupSpellPop.gif">
<figcaption>Pop up</figcaption>
</figure>
<br>
<figure>
<img src="Images/LookupStart.PNG" alt="Image showing the start screen">  
<figcaption>Starting Screen</figcaption>
</figure>



## Planned

- Fetch information for spells and other abilities that come packed in other actions/abilities and display them
- Add a loading animation when searching
- Add proper fonts
- Make better use of themes by moving shapes and typography into it
- UI Tests


[compose_url]: https://github.com/JetBrains/compose-jb/releases/tag/v1.2.0-alpha01-dev774
[compose_img]: https://img.shields.io/static/v1?style=for-the-badge&message=Compose&color=4285F4&logo=Jetpack+Compose&logoColor=FFFFFF&label=
[kotlin_url]: https://github.com/JetBrains/kotlin/releases/tag/v1.7.10
[kotlin_img]: https://img.shields.io/github/v/release/Jetbrains/kotlin?color=7f53ff&label=Kotlin&logo=kotlin&logoColor=7f53ff&style=for-the-badge