package ise308.ozbakan.vahikutluhan.ozbakan_vahitkutluhanozbakan_moviechecker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var db: Database
    }

    lateinit var adapter: MovieAdapter
    lateinit var movieList: ArrayList<Movie>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<ImageView>(R.id.addmoviebut)
        // val fab = findViewById<FloatingActionButton>(R.id.fab)
        db = Database(this)
        viewMovies()


        fab.setOnClickListener() {
            val i = Intent(this, AddMovieActivity::class.java)
            startActivity(i)
        }




    }

    private fun viewMovies() {
        val movieList = db.getMovies(this)
        adapter = MovieAdapter(this, movieList)
        val rv: RecyclerView = findViewById(R.id.recyclerview)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    override fun onResume() {
        viewMovies()
        super.onResume()
    }


}