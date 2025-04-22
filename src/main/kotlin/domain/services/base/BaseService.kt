package domain.services.base



abstract class BaseService<T : Any>() {

    protected lateinit var repository: T
}
