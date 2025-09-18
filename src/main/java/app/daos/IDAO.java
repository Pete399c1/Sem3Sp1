package app.daos;

import java.util.List;

public interface IDAO<T>{
    T create(T t);
    List<T> getAll();
}
