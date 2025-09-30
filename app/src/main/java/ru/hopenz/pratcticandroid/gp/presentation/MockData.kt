package ru.hopenz.pratcticandroid.gp.presentation

import ru.hopenz.pratcticandroid.gp.presentation.model.CharacterUiModel

object MockData {
    fun getCharacters(): List<CharacterUiModel> = listOf(
        CharacterUiModel(
            index = 0,
            fullName = "Harry James Potter",
            nickname = "Harry",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Daniel Radcliffe",
            children = listOf("James Sirius Potter", "Albus Severus Potter", "Lily Luna Potter"),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/harry_potter.png",
            birthdate = "Jul 31, 1980"
        ),
        CharacterUiModel(
            index = 1,
            fullName = "Hermione Jean Granger",
            nickname = "Hermione",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Emma Watson",
            children = listOf("Rose Granger-Weasley", "Hugo Granger-Weasley"),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/hermione_granger.png",
            birthdate = "Sep 19, 1979"
        ),
        CharacterUiModel(
            index = 2,
            fullName = "Ron Weasley",
            nickname = "Ron",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Rupert Grint",
            children = listOf("Rose Granger-Weasley", "Hugo Granger-Weasley"),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/ron_weasley.png",
            birthdate = "Mar 1, 1980"
        ),
        CharacterUiModel(
            index = 3,
            fullName = "Fred Weasley",
            nickname = "Fred",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "James Phelps",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/fred_weasley.png",
            birthdate = "Apr 1, 1978"
        ),
        CharacterUiModel(
            index = 4,
            fullName = "George Weasley",
            nickname = "George",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Oliver Phelps",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/george_weasley.png",
            birthdate = "Apr 1, 1978"
        ),
        CharacterUiModel(
            index = 5,
            fullName = "Bill Weasley",
            nickname = "Bill",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Domhnall Gleeson",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/bill_weasley.png",
            birthdate = "Nov 29, 1970"
        ),
        CharacterUiModel(
            index = 6,
            fullName = "Percy Weasley",
            nickname = "Percy",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Chris Rankin",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/percy_weasley.png",
            birthdate = "Aug 22, 1976"
        ),
        CharacterUiModel(
            index = 7,
            fullName = "Charlie Weasley",
            nickname = "Charlie",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/charlie_weasley.png",
            birthdate = "Dec 12, 1972"
        ),
        CharacterUiModel(
            index = 8,
            fullName = "Ginny Weasley",
            nickname = "Ginny",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Bonnie Right",
            children = listOf("James Sirius Potter", "Albus Severus Potter", "Lily Luna Potter"),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/ginny_weasley.png",
            birthdate = "Aug 11, 1981"
        ),
        CharacterUiModel(
            index = 9,
            fullName = "Molly Weasley",
            nickname = "Molly",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Julie Walters",
            children = listOf(
                "Ron Weasley",
                "Fred Weasley",
                "George Weasley",
                "Bill Weasley",
                "Percy Weasley",
                "Charlie Weasley",
                "Ginny Weasley"
            ),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/molly_weasley.png",
            birthdate = "Oct 30, 1949"
        ),
        CharacterUiModel(
            index = 10,
            fullName = "Arthur Weasley",
            nickname = "Arthur",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Mark Williams",
            children = listOf(
                "Ron Weasley",
                "Fred Weasley",
                "George Weasley",
                "Bill Weasley",
                "Percy Weasley",
                "Charlie Weasley",
                "Ginny Weasley"
            ),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/arthur_weasley.png",
            birthdate = "Feb 6, 1950"
        ),
        CharacterUiModel(
            index = 11,
            fullName = "Neville Longbottom",
            nickname = "Neville",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Matthew Lewis",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/neville_longbottom.png",
            birthdate = "Jul 30, 1980"
        ),
        CharacterUiModel(
            index = 12,
            fullName = "Luna Lovegood",
            nickname = "Luna",
            hogwartsHouse = "Ravenclaw",
            interpretedBy = "Evanna Lynch",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/luna_lovegood.png",
            birthdate = "Feb 13, 1981"
        ),
        CharacterUiModel(
            index = 13,
            fullName = "Draco Malfoy",
            nickname = "Draco",
            hogwartsHouse = "Slytherin",
            interpretedBy = "Tom Felton",
            children = listOf("Scorpius Malfoy"),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/draco_malfoy.png",
            birthdate = "Jun 5, 1980"
        ),
        CharacterUiModel(
            index = 14,
            fullName = "Albus Percival Wulfric Brian Dumbledore",
            nickname = "Dumbledore",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Richard Harris",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/albus_dumbledore.png",
            birthdate = "Aug 29, 1881"
        ),
        CharacterUiModel(
            index = 15,
            fullName = "Minerva McGonagall",
            nickname = "Minerva",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Maggie Smith",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/minerva_mcgonagall.png",
            birthdate = "Oct 4, 1935"
        ),
        CharacterUiModel(
            index = 16,
            fullName = "Remus Lupin",
            nickname = "Lupin",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "David Thewils",
            children = listOf("Ted Lupin"),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/remus_lupin.png",
            birthdate = "Mar 10, 1960"
        ),
        CharacterUiModel(
            index = 17,
            fullName = "Rubeus Hagrid",
            nickname = "Hagrid",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Robbie Coltrane",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/rubeus_hagrid.png",
            birthdate = "Dec 6, 1928"
        ),
        CharacterUiModel(
            index = 18,
            fullName = "Sirius Black",
            nickname = "Sirius",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Gary Oldman",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/sirius_black.png",
            birthdate = "Nov 3, 1959"
        ),
        CharacterUiModel(
            index = 19,
            fullName = "Severus Snape",
            nickname = "Snape",
            hogwartsHouse = "Slytherin",
            interpretedBy = "Alan Rickman",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/severus_snape.png",
            birthdate = "Jan 9, 1960"
        ),
        CharacterUiModel(
            index = 20,
            fullName = "Bellatrix Lestrange",
            nickname = "Bella",
            hogwartsHouse = "Slytherin",
            interpretedBy = "Helena Bonham Carter",
            children = listOf("Delphi"),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/bellatrix_lestrange.png",
            birthdate = "Dec 13, 1951"
        ),
        CharacterUiModel(
            index = 21,
            fullName = "Lord Voldemort",
            nickname = "Voldemort",
            hogwartsHouse = "Slytherin",
            interpretedBy = "Ralph Fiennes",
            children = listOf("Delphi"),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/lord_voldemort.png",
            birthdate = "Dec 31, 1926"
        ),
        CharacterUiModel(
            index = 22,
            fullName = "Cedric Diggory",
            nickname = "Cedric",
            hogwartsHouse = "Hufflepuff",
            interpretedBy = "Robert Pattinson",
            children = emptyList(),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/cedric_diggory.png",
            birthdate = "Sep 29, 1977"
        ),
        CharacterUiModel(
            index = 23,
            fullName = "Nymphadora Tonks",
            nickname = "Tonks",
            hogwartsHouse = "Hufflepuff",
            interpretedBy = "Natalia Tena",
            children = listOf("Ted Lupin"),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/nymphadora_tonks.png",
            birthdate = "Dec 25, 1972"
        ),
        CharacterUiModel(
            index = 24,
            fullName = "James Potter",
            nickname = "James",
            hogwartsHouse = "Gryffindor",
            interpretedBy = "Adrian Rawlins",
            children = listOf("Harry Potter"),
            imageUrl = "https://raw.githubusercontent.com/fedeperin/potterapi/main/public/images/characters/james_potter.png",
            birthdate = "Mar 27, 1960"
        )
    )
}
