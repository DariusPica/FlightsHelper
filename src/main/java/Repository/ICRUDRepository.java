package Repository;

import Domain.Entity;

public interface ICRUDRepository<ID,T extends Entity<ID>>
{
    T save(T entity);

    T update(T entity);

    T delete(T entity);

    T get(ID id);

    Iterable<T> getAll();


}
