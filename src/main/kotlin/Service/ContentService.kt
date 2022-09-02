package Service

import Model.Base.APIReference
import State.State

interface ContentService {

    suspend fun getContentAsync(reference : APIReference) : State
    suspend fun getContentAsync(name : String) : State

    suspend fun getContentFromRefsAsync(
        references : List<APIReference>,
        onFetchFail : ((Throwable) -> Unit)? = null,
        onReferenceFetched : (State) -> Unit
    ) {
        references.forEach {
            try {
                val response = getContentAsync(it)
                onReferenceFetched(response)
            } catch (e : Exception) {
                onFetchFail?.let { it(e) }
            }
        }
    }

    suspend fun getContentFromNamesAsync(
        names : List<String>,
        onFetchFail : ((Throwable) -> Unit)? = null,
        onReferenceFetched : (State) -> Unit
    ) {
        try {
            names.forEach {
                val response = getContentAsync(it)
                onReferenceFetched(response)
            }
        } catch (e : Exception) {
            onFetchFail?.let { it(e) }
        }

    }
    companion object {

        fun getSpellService() : ContentService = SpellContentService()
        fun getMonsterService() : ContentService = MonsterContentService()
    }
}