package Model.Util

data class ItemRoll<T>(
    val rolls: List<T> = listOf(),
    val itemName: String,
    val itemDescr: String,
)