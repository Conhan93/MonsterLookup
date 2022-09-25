package Model.Storage

data class StorageProperties(
    val url : String,
    val driver : String
)

sealed class StorageTypes {
    object H2 : StorageTypes()
}

/**
 * Generates storage property with paths to app data folder or user home depending on system
 *
 * @param pathToStorage path to storage from MonsterLookup/(app data or user.home)
 * should start without a slash like "data/db"
 * @param storageType the storage type
 */
fun getStorageProperties(
    pathToStorage : String,
    storageType: StorageTypes
) : StorageProperties {

    var (url, driver) = getFromStorageType(storageType)

    var workingDirectory = System.getenv("APPDATA")

    // if not on a Windows machine
    if(workingDirectory.isNullOrEmpty()) {
        workingDirectory = System.getProperty("user.home")

        if (workingDirectory.isNullOrEmpty())
            throw Exception("Unable to get directory from environment variables")
    }
    workingDirectory += "/MonsterLookup/"
    url += workingDirectory
    url += pathToStorage

    return StorageProperties(url, driver)
}

private fun getFromStorageType(type : StorageTypes) : Pair<String, String> {
    when(type) {
        is StorageTypes.H2 -> {
            return "jdbc:h2:file:" to "org.h2.Driver"
        }
    }
}