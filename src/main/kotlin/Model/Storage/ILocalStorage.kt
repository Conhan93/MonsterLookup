package Model.Storage

import Model.Data.Base.Base
import Model.Data.Monster.Monster
import Model.Data.Spell.Spell
import kotlin.reflect.KClass


interface ILocalStorage {
    /**
     *  Fetch a spell from the database by name
     *
     *  @param name the name of the spell
     *  @return returns the spell or null if not found
     */
    fun getSpellByName(name : String) : Spell?

    /**
     *  Fetch a monster from the database by name
     *
     *  @param name the name of the monster
     *  @return returns the monster or null if not found
     */
    fun getMonsterByName(name : String) : Monster?

    /**
     *  Store model in database
     *
     *  @param model a serializable model inheriting from the base model class
     */
    fun store(model : Base)

    /**
     *  Clears the local database of all data
     */
    fun clear()

    /**
     *  Get a list of monster names saved in the database
     *  limited by the limit parameter
     *
     * @param limit the amount of names to be fetched
     * @return list of strings of names of monsters
     */
    fun getMonsterNames(limit : Int? = null) : List<String>

    /**
     *  Fetch a stored base model from local storage by name
     *
     *  @param name the name of the monster or spell
     *  @param klass of the model, returns null if not available
     *  @return returns the result or null if not found
     */
    fun getByName(name: String, klass : KClass<*>) : Base? {
        return when(klass) {
            Spell::class -> getSpellByName(name)
            Monster::class -> getMonsterByName(name)
            else -> null
        }
    }
}