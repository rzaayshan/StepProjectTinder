package org.step.tinder.DAO;


import java.util.List;
import java.util.Optional;

public interface DAO<A> {
    List<A> getAll();
    <T>Optional<A> get(T data);
    void put(A a);
    void delete(int id);

}
