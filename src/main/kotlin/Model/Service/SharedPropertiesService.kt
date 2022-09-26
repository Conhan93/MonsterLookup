package Model.Service


import kotlinx.coroutines.flow.StateFlow

interface SharedPropertiesService {

    /**
     *  Fetches the current value of the property
     *
     *  @param id the id used to create the property
     *  @return the current value of the property or
     *  null if the property doesn't exist
     */
    fun getPropertyValue(id : String): Any?

    /**
     *  Sets the value of the property or creates a new property
     *  with the passed value if one doesn't exist
     *
     *  @param id the id to be associated with the property
     *  @param value the value the property is to be set to
     */
    suspend fun setPropertyValue(id: String, value: Any?)

    /**
     * Returns a read only state flow from the property.
     * Will create a new property with the passed id with
     * initial value null if a property with the id doesn't
     * already exist.
     *
     *  @param id the key of the property
     *  @return read-only flow to property of nullable type T
     */
    fun <T> observeProperty(id: String): StateFlow<T?>

    /**
     *  Removes the property if it has no active subscribers.
     *
     *  @param id of the property to remove
     *  @return true if the property was removed, false if not
     */
    fun removeProperty(id: String) : Boolean

    /**
     * Removes all properties - risks cutting of subscribers
     * of properties since they don't automatically get resubscribed.
     */
    fun clear()
}