package Service

import Model.Base.APIReference
import State.State

interface ContentService {
    suspend fun getContentAsync(name : String) : State
    suspend fun getContentAsync(reference : APIReference) : State
}