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
        //In the movie adding class, we first assign variables from the activity_add_movie layout to a value.
        val save = findViewById<Button>(R.id.btnSave) as Button
        val del = findViewById<Button>(R.id.btnCancel) as Button
        val mname = findViewById<TextView>(R.id.editMovieName)
        val mdate = findViewById<TextView>(R.id.editMovieYear)
        val mdirector = findViewById<TextView>(R.id.editMovieDirector)
        val mactive = findViewById<CheckBox>(R.id.checkBoxActive)


        //If the user presses the save button after entering the data into the textbox, the addMovie function in our database class works and we send the data to that function.
        // In case of leaving the box blank, we provide a warning to the user that it was entered blank.
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

                MainActivity.db.addMovie(movie)
                mname.requestFocus()
                super.onBackPressed()

            }
        }
        //If the user stops entering data, it is enabled to return to the main menu.
        del.setOnClickListener()
        {
            super.onBackPressed()
        }


    }
}

