package Model.Storage

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.CurrentDate
import org.jetbrains.exposed.sql.javatime.date


object Spells : IntIdTable() {
    val name = varchar("name", 100)
    val lastAccessed = date("last_accessed").defaultExpression( CurrentDate )
    val json = text("body", eagerLoading = true)
}

object Monsters : IntIdTable() {
    val name = varchar("name", 100)
    val lastAccessed = date("last_accessed").defaultExpression( CurrentDate )
    val json = text("body", eagerLoading = true)
}