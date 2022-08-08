package Service

import Model.Base.APIReference
import State.State

interface ContentService<T> {
    fun getContent(name : String) : State<T>
    fun getContent(reference : APIReference) : State<T>
    //fun getContent(url : URL) : State<T>
}