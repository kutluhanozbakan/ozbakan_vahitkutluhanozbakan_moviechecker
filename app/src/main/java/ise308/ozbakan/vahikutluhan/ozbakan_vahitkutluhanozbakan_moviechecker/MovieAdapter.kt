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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var mo_name = view.findViewById<View>(R.id.movie_name) as TextView
        internal var mo_date = view.findViewById<View>(R.id.movie_date) as TextView
        //internal var mo_id = view.findViewById<View>(R.id.id) as TextView
        internal var mo_director = view.findViewById<View>(R.id.movie_director) as TextView
        internal var mo_delete = view.findViewById<Button>(R.id.btn_del)
        internal var mo_update = view.findViewById<Button>(R.id.btn_upt)
        internal var clayout = view.findViewById<ConstraintLayout>(R.id.clayout)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_layout, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Film.size
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {

        val movie: Movie = Film[position]
        holder.mo_name.text = movie.movieName
        holder.mo_date.text = movie.movieDate
        holder.mo_director.text = movie.movieDirector
        //holder.mo_id.text = movie.id.toString()
        if (movie.active == true)
        {
            holder.clayout.setBackgroundColor(Color.parseColor("#ADD88D"))

        }
        else
        {
            holder.clayout.setBackgroundColor(Color.parseColor("#A9A9A9"))
        }



        holder.mo_delete.setOnClickListener {
            Log.e("MainActivity", "HATA")
            val moviName = movie.movieName
            val moviDate = movie.movieDate
            val moviDirector = movie.movieDirector

            var alertDialog = android.app.AlertDialog.Builder(context)
                .setTitle("Warning")
                .setMessage("Are you sure to delete: $moviName ?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
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
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which -> })
                .setIcon(R.drawable.ic_baseline_restore_from_trash_24)
                .show()

        }

        holder.mo_update.setOnClickListener {
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
                .setTitle("Update Customer Info.")
                .setView(view)
                .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
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
                }).setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> })
            val alert = builder.create()
            alert.show()
        }
    }
}
