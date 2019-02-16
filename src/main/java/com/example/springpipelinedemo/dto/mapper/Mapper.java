package com.example.springpipelinedemo.dto.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rimantas Jacikevičius on 18.8.8.
 */
public interface Mapper<From,To> {

    To map(From from);

    default List<To> map(Collection<From> from) {
        if (from == null) {
            return null;
        }
        return from.stream().map(this::map).collect(Collectors.toList());
    }

}
