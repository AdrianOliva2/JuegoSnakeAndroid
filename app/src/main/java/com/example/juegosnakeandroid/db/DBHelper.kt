package com.example.juegosnakeandroid.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.juegosnakeandroid.classes.Player

class DBHelper(private val context: Context) : SQLiteOpenHelper(context, dbName, null, version) {

    private companion object {
        private const val dbName: String = "SnakeScore.db"
        private const val version: Int = 1
    }

    private fun createDB(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE \"PLAYER\" (\n" +
                    "\t\"ID\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t\"NAME\"\tTEXT NOT NULL,\n" +
                    "\t\"SCORE\"\tINTEGER NOT NULL,\n" +
                    "\tPRIMARY KEY(\"ID\" AUTOINCREMENT)\n" +
                    ");"
        )
    }

    override fun onCreate(db: SQLiteDatabase?) {
        createDB(db)
    }

    fun getScores(): MutableList<Player>? {
        try {
            val scores: MutableList<Player> = mutableListOf()
            val db: SQLiteDatabase = this.readableDatabase
            val cursorUsuario: Cursor = db.rawQuery("SELECT \"NAME\", \"SCORE\" FROM \"PLAYER\";", null)
            while (cursorUsuario.moveToNext()) {
                scores.plus(Player(cursorUsuario.getString(0), cursorUsuario.getInt(1)))
            }
            cursorUsuario.close()
            return scores
        } catch (e: Exception) {
            println("Algo falló")
        }
        return null
    }

    fun insertScore(player: Player): Long? {
        var inserted: Long? = null
        try {
            var db: SQLiteDatabase = this.writableDatabase
            var contentValues = ContentValues()
            contentValues.put("NAME", player.name)
            contentValues.put("SCORE", player.score)
            inserted = db.insert("PLAYER", null, contentValues)
            println("Se añadio bien")
        } catch (e: Exception) {
            println("Algo falló")
        }
        return inserted
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {
        db?.execSQL("DROP TABLE PIZZA_INGREDIENTE")
        db?.execSQL("DROP TABLE PIZZA_USUARIO")
        db?.execSQL("DROP TABLE INGREDIENTE")
        db?.execSQL("DROP TABLE PIZZA")
        db?.execSQL("DROP TABLE USUARIO")

        createDB(db)
    }

}