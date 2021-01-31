package ise308.ozbakan.vahikutluhan.ozbakan_vahitkutluhanozbakan_moviechecker
//First of all, we create our data class for the project.
// We will operate using these variables for the rest of the project.
data class Movie(
    var id: Int?,
    var movieName: String,
    var movieDate: String,
    var movieDirector: String,
    var active: Boolean
)
