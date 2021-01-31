package ise308.ozbakan.vahikutluhan.ozbakan_vahitkutluhanozbakan_moviechecker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {
    companion object {
        //We create the contents of our database.
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "MovieCheckerDataBase.db"

        //TABLE

        private val TABLE_NAME = "MovieNewTable"
        private val MOV_ID = "Id"
        private val MOV_NAME = "Name"
        private val MOV_DATE = "Date"
        private val MOV_DIRECTOR = "Director"
        private val MOV_ACTIVE = "Active"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //We write our database query that will be created when the application is first opened.
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE  $TABLE_NAME ($MOV_ID INTEGER PRIMARY KEY AUTOINCREMENT,$MOV_NAME TEXT,$MOV_DATE TEXT,$MOV_DIRECTOR TEXT,$MOV_ACTIVE BOOLEAN )")
        db!!.execSQL(CREATE_TABLE_QUERY);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //CRUD
    //With the get Movie function, we pull the data from the table.
    fun getMovies(): ArrayList<Movie> {
        val resultQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(resultQuery, null)
        val movies = ArrayList<Movie>()
        //We navigate through the array with the cursor function.
        if (cursor.moveToFirst()) {
            do {
                val movie = Movie(
                    cursor.getInt(cursor.getColumnIndex(MOV_ID)),
                    cursor.getString(cursor.getColumnIndex(MOV_NAME)),
                    cursor.getString(cursor.getColumnIndex(MOV_DATE)),
                    cursor.getString(cursor.getColumnIndex(MOV_DIRECTOR)),
                    cursor.isNull(cursor.getColumnIndex(MOV_ACTIVE))
                )


                Log.i(ContentValues.TAG, "getMovies: $movie")
                movies.add(movie)
            } while (cursor.moveToNext())
        }
        db.close()
        return movies
    }

    fun addMovie(movie: Movie) {
        //With the add movie function, we print the database variables, the values ​​we get from the user, with the database query.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(MOV_ID, movie.id)
        values.put(MOV_NAME, movie.movieName)
        values.put(MOV_DATE, movie.movieDate)
        values.put(MOV_DIRECTOR, movie.movieDirector)
        if (movie.active == false) {
            movie.active = true
            values.put(MOV_ACTIVE, movie.active)
        }


        db.insert(TABLE_NAME, null, values)
        db.close()


    }

    fun deleteMovie(movieID: Int): Boolean {
        //With the delete Movie function, we take the ID of the data the user wants to delete and delete it with the sql function.
        Log.e(ContentValues.TAG, "Gelen movieID : " + movieID.toString())
        val qry = "DELETE FROM $TABLE_NAME WHERE $MOV_ID = $movieID"
        val db = this.writableDatabase
        var result: Boolean = false
        try {
            db.execSQL(qry)
            result = true


        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error Deleting")
        }
        db.close()
        return result
    }
//With the update Movie function, we take the id of the data that the user wants to edit and update it with a sql query.
    fun updateMovie(
        movieID: String,
        movieName: String,
        movieDate: String,
        movieDirec: String,
        movieActiv: Boolean
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
    var result: Boolean
    contentValues.put(MOV_NAME, movieName)
        contentValues.put(MOV_DATE, movieDate)
        contentValues.put(MOV_DIRECTOR, movieDirec)
        contentValues.put(MOV_ACTIVE, movieActiv)
        try {
            db.update(TABLE_NAME, contentValues, "$MOV_ID = ?", arrayOf(movieID))
            result = true
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error Updating")
            result = false
        }

        return result

    }
}