package br.com.tiagocalixto.pokedex.use_case;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public interface Prepare<T> {

    T execute (T entity);

    default List<T> executeList (List<T> entityList){

        if (entityList == null || entityList.isEmpty()){
            return Collections.emptyList();
        }

        return entityList.stream().filter(Objects::nonNull)
                .map(this::execute).collect(Collectors.toList());
    }
}
