package Model.Data.Util

data class ItemRoll<T>(
    val rolls: List<T> = listOf(),
    val hitRoll: HitRoll? = null,
    val itemName: String,
    val itemDescr: String,
)