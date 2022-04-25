package com.mainApp.randommusicalnotes

class Model {

    val PracticeType : List<String> = listOf("Notes","Chords")
    val AllNotes : List<String> = listOf("C","D","E","F","G","A","B","C#","D#","F#","G#","A#")
    val NaturalNotes : List<String> = listOf("C","D","E","F","G","A","B")
    val DegreeOfNotes : List<String> = listOf("1","2","3","4","5","6","7")
    val RootNotes : List<String> = listOf("C","C#","D","D#","E","F","F#","G","G#","A","A#","B")
    val ChordTypes : Array<String> = arrayOf("Maj","min" ,"dim" ,"Maj7","min7","dom7","sus2","sus4")

    val NotesType : List<String> = listOf(
        "Only Natural Notes",
        "All Notes",
        "Major Scale Notes",
        "Minor Scale Notes")

    val ScaleType : List<String> = listOf(
        "Major",
        "Dorian",
        "Phrygian",
        "Lydian",
        "Mixolydian",
        "Natural Minor",
        "Locrian",)

    var ChordInAScale = arrayOf(
        arrayOf("Maj","Maj7","sus2","sus4"),
        arrayOf("min","min7","sus2","sus4"),
        arrayOf("min","min7","sus4"),
        arrayOf("Maj","Maj7","sus2"),
        arrayOf("Maj","dom7","sus2","sus4"),
        arrayOf("min","min7","sus2","sus4"),
        arrayOf("dim")
    )

}