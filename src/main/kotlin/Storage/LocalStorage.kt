package Storage

import Model.Base.Base
import Model.Monster.Monster
import Model.Spell.Spell
import Util.formatSearchName

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*

import org.jetbrains.exposed.sql.javatime.CurrentDate
import org.jetbrains.exposed.sql.transactions.transaction

class LocalStorage : ILocalStorage {
    private val db : Database

    constructor(
        properties: StorageProperties
    ) {
        // connect to database
        db = Database.connect(url = properties.url, driver = properties.driver)

        // create tables
        transaction {
            SchemaUtils.create(StoredModels)
        }
    }

    override fun getSpellByName(name: String): Spell? {

        val body = try {
            transaction(db) {
                StoredModels.select { StoredModels.name eq name.formatSearchName() }.first()[StoredModels.json]
            }
        } catch (e: java.util.NoSuchElementException) {
            return null
        }

        return decode(body)
    }



    override fun getMonsterByName(name: String): Monster? {

        val body = try {
            transaction(db) {
                StoredModels.select { StoredModels.name eq name.formatSearchName() }.first()[StoredModels.json]
            }
        } catch (e: java.util.NoSuchElementException) {
            return null
        }

        return decode(body)
    }

    override fun store(model: Base) {

        val string = modelToJsonString(model)


        transaction(db) {
            StoredModels.insertAndGetId {
                it[json] = string
                it[name] = model.name!!.formatSearchName()
                it[lastAccessed] = CurrentDate
            }
        }

    }

    override fun clear() {
        transaction(db) {
            SchemaUtils.drop(StoredModels)
            SchemaUtils.create(StoredModels)
        }
    }

    private fun modelToJsonString(model: Base) = when(model) {
        is Spell -> Json.encodeToString(model)
        is Monster -> Json.encodeToString(model)
        else -> throw Exception("woopsie")
    }

    private inline fun <reified T> decode(string : String) : T {
        val json = Json { ignoreUnknownKeys = true }

        return json.decodeFromString(string)
    }

}

