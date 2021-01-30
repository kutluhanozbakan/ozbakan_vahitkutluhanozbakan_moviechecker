package ise308.ozbakan.vahikutluhan.ozbakan_vahitkutluhanozbakan_moviechecker

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddMovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

        val save = findViewById<Button>(R.id.btnSave) as Button
        val del = findViewById<Button>(R.id.btnCancel) as Button
        val mname = findViewById<TextView>(R.id.editMovieName)
        val mdate = findViewById<TextView>(R.id.editMovieYear)
        val mdirector = findViewById<TextView>(R.id.editMovieDirector)
        val mactive = findViewById<CheckBox>(R.id.checkBoxActive)



        save.setOnClickListener()
        {
            if (mname.text.isEmpty()) {
                Toast.makeText(this, "Enter Customer Name", Toast.LENGTH_SHORT).show()
                mname.requestFocus()
            } else {
                val movie = Movie(
                    null, mname.text.toString(),
                    mdate.text.toString(),
                    mdirector.text.toString(),
                    mactive.isChecked
                )

                MainActivity.db.addMovie(this, movie)
                mname.requestFocus()
                super.onBackPressed()

            }
        }

        del.setOnClickListener()
        {
            super.onBackPressed()
        }


    }
}

