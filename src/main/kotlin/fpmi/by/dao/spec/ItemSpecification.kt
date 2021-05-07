package fpmi.by.dao.spec

interface ItemSpecification<T> {
    fun specify(item : T): Boolean;
}
