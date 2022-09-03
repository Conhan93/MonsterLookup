package Storage

import Model.Base.Base
import Model.Monster.Monster
import Model.Spell.Spell
import Service.JsonService
import Service.decodeFromString
import Util.formatSearchName

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.*

import org.jetbrains.exposed.sql.javatime.CurrentDate
import org.jetbrains.exposed.sql.transactions.transaction

class LocalStorage(
    properties: StorageProperties
) : ILocalStorage, JsonService {
    private val db : Database

    init {
        // connect to database
        db = Database.connect(url = properties.url, driver = properties.driver)

        // create tables
        transaction {
            SchemaUtils.create(Spells)
            SchemaUtils.create(Monsters)
        }
    }

    override fun getSpellByName(name: String): Spell? {

        val body = try {
            transaction(db) {
                Spells.select { Spells.name eq name.formatSearchName() }.first()[Spells.json]
            }
        } catch (e: java.util.NoSuchElementException) {
            return null
        }

        return decodeFromString(body)
    }



    override fun getMonsterByName(name: String): Monster? {

        val body = try {
            transaction(db) {
                Monsters.select { Monsters.name eq name.formatSearchName() }.first()[Monsters.json]
            }
        } catch (e: java.util.NoSuchElementException) {
            return null
        }

        return decodeFromString(body)
    }

    override fun store(model: Base) {

        val string = modelToJsonString(model)

        when(model) {
            is Spell -> {
                transaction(db) {
                    Spells.insertAndGetId {
                        it[json] = string
                        it[name] = model.name!!.formatSearchName()
                        it[lastAccessed] = CurrentDate
                    }
                }
            }
            is Monster -> {
                transaction(db) {
                    Monsters.insertAndGetId {
                        it[json] = string
                        it[name] = model.name!!.formatSearchName()
                        it[lastAccessed] = CurrentDate
                    }
                }
            }
            else -> throw Exception("Woopsie") // TODO: implement storage exceptions
        }
    }

    override fun getMonsterNames(limit: Int?): List<String> {

        val result = transaction(db) {

            val query = Monsters
                .slice(Monsters.name)
                .selectAll()

            limit?.let { query.limit(it) }

            query.map {
                it[Monsters.name]
            }
        }

        return result
    }

    override fun clear() {
        transaction(db) {

            // Drop tables
            SchemaUtils.drop(Spells)
            SchemaUtils.drop(Monsters)

            // Recreate tables
            SchemaUtils.create(Spells)
            SchemaUtils.create(Monsters)
        }
    }

    private fun modelToJsonString(model: Base) = when(model) {
        is Spell -> Json.encodeToString(model)
        is Monster -> Json.encodeToString(model)
        else -> throw Exception("woopsie")
    }
}

