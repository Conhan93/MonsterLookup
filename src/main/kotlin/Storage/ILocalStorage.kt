package Storage

import Model.Base.Base
import Model.Monster.Monster
import Model.Spell.Spell


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

    companion object : ILocalStorage {

        val storageProperties = getStorageProperties("data/db", StorageTypes.H2)

        private var storage = LocalStorage(storageProperties)

        fun changeStorageProperties(properties: StorageProperties) {
            storage = LocalStorage(properties)
        }

        override fun getSpellByName(name: String): Spell? {
           return storage.getSpellByName(name)
        }

        override fun getMonsterByName(name: String): Monster? {
            return storage.getMonsterByName(name)
        }

        override fun store(model: Base) {
            storage.store(model)
        }

        override fun clear() {
            storage.clear()
        }
    }
}