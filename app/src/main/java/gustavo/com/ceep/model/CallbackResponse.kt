package gustavo.com.ceep.model

interface CallbackResponse<T> {
    fun success(response: T)
}