package com.example.juegosnakeandroid.classes

class Player(var name: String?, var score: Int) {
    override fun toString(): String {
        return "$name - $score"
    }
}
