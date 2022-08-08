package Service

import Model.Base.APIReference
import State.State

interface ContentService {
    fun getContent(name : String) : State
    fun getContent(reference : APIReference) : State
}