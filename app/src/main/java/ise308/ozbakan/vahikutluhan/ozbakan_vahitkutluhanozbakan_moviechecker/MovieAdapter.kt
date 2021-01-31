package ise308.ozbakan.vahikutluhan.ozbakan_vahitkutluhanozbakan_moviechecker

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class MovieAdapter(context: Context, val Film: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    val context = context

    //First, we pull our variables to the viewholder through movie_layout.
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var mo_name = view.findViewById<View>(R.id.movie_name) as TextView
        internal var mo_date = view.findViewById<View>(R.id.movie_date) as TextView
        internal var mo_director = view.findViewById<View>(R.id.movie_director) as TextView
        internal var mo_delete = view.findViewById<Button>(R.id.btn_del)
        internal var mo_update = view.findViewById<Button>(R.id.btn_upt)
        internal var clayout = view.findViewById<ConstraintLayout>(R.id.clayout)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_layout, parent, false)
        //We inflate the viewholder.
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        //We're keeping the size of our film array.
        return Film.size
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        //We synchronize the variables in the data class to use the variables in the layout.
        val movie: Movie = Film[position]
        holder.mo_name.text = movie.movieName
        holder.mo_date.text = movie.movieDate
        holder.mo_director.text = movie.movieDirector
        //In this section, we adjust the color of our "active" variable according to its true and false values.
        if (movie.active) {
            holder.clayout.setBackgroundColor(Color.parseColor("#D0F7C0"))
            holder.mo_name.setTextColor(Color.parseColor("#000000"))
            holder.mo_date.setTextColor(Color.parseColor("#000000"))
            holder.mo_director.setTextColor(Color.parseColor("#000000"))
        } else {
            holder.clayout.setBackgroundColor(Color.parseColor("#ff6969"))
            holder.mo_name.setTextColor(Color.parseColor("#FFFFFF"))
            holder.mo_date.setTextColor(Color.parseColor("#FFFFFF"))
            holder.mo_director.setTextColor(Color.parseColor("#FFFFFF"))
        }



        holder.mo_delete.setOnClickListener {
            //In this section, we give a warning to the user to delete the existing data, if the user presses the delete button, we want to delete the data by finding its current position.
            val moviName = movie.movieName

            android.app.AlertDialog.Builder(context)
                .setTitle("Warning")
                .setMessage("Are you sure to delete: $moviName ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                    if (MainActivity.db.deleteMovie(movie.id!!)) {
                        Film.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, Film.size)
                        Toast.makeText(context, "Movie $moviName Deleted", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Error Deleting", Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { _, _ -> })
                .setIcon(R.drawable.ic_baseline_restore_from_trash_24)
                .show()

        }

        holder.mo_update.setOnClickListener {
            //In this section, we show a dialog to update the current data. After the dialog is opened, we add the existing data there and after the update is done, we update the data with the database connection.
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.movie_update, null)

            val txtMovName: TextView = view.findViewById(R.id.updatemName)
            val txtMoveDate: TextView = view.findViewById(R.id.updateMDate)
            val txtMoveDirector: TextView = view.findViewById(R.id.updateMDir)
            val txtactv: CheckBox = view.findViewById(R.id.checkBoxact)

            txtMovName.text = movie.movieName
            txtMoveDate.text = movie.movieDate
            txtMoveDirector.text = movie.movieDirector

            val builder = AlertDialog.Builder(context)
                .setTitle("Update Movie Information.")
                .setView(view)
                .setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->
                    val isUpdate = MainActivity.db.updateMovie(
                        movie.id.toString(),
                        txtMovName.text.toString(),
                        txtMoveDate.text.toString(),
                        txtMoveDirector.text.toString(),
                        txtactv.isChecked


                    )
                    if (isUpdate == true) {
                        Film[position].movieName = txtMovName.text.toString()
                        Film[position].movieDate = txtMoveDate.text.toString()
                        Film[position].movieDirector = txtMoveDirector.text.toString()
                        Film[position].active = txtactv.isChecked
                        notifyDataSetChanged()
                        Toast.makeText(context, "Updated Succesfull", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error Updating", Toast.LENGTH_SHORT).show()
                    }
                }).setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ -> })
            val alert = builder.create()
            alert.show()
        }
    }
}
