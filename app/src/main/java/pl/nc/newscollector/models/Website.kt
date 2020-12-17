package pl.nc.newscollector.models

data class Website(
        val feeds: ArrayList<Feed>,
        val name: String,
        var isExpanded: Boolean = false
) {
}