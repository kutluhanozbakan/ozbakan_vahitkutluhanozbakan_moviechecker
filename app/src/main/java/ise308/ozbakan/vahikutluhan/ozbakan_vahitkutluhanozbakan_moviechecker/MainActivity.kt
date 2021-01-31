package ise308.ozbakan.vahikutluhan.ozbakan_vahitkutluhanozbakan_moviechecker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    //First we assign a variable to use the database. Then we create our adapter and finally we create the movie array.
    companion object {
        lateinit var db: Database
    }

    lateinit var adapter: MovieAdapter
    lateinit var movieList: ArrayList<Movie>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //We create an image that appears on the main menu for the user to add data.
        val fab = findViewById<ImageView>(R.id.addmoviebut)

        db = Database(this)
        //We ensure that the existing movies (if any) are displayed with viewMovies () at the first launch.
        viewMovies()

        //AddMovieActivity class is called upon clicking the add movie button.
        fab.setOnClickListener() {
            val i = Intent(this, AddMovieActivity::class.java)
            startActivity(i)
        }


    }

    private fun viewMovies() {
        //ViewMovies function retrieves existing movies from the database and puts them in a row. Then we show it to the user thanks to the adapter and recyclerview.
        val movieList = db.getMovies()
        adapter = MovieAdapter(this, movieList)
        val rv: RecyclerView = findViewById(R.id.recyclerview)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    override fun onResume() {
        //After any change, we call the viewMovies function in the onResume function for the current state of the data again.
        viewMovies()
        super.onResume()
    }


}