package com.company.explorecalifornia.web.dto.converter;

/**
 * The interface DtoConverter specifies the behavior for converting an object to a dto and vice versa.
 *
 * @param <E> Entity type
 * @param <D> DtoEntity type
 */
public interface DtoConverter<E, D> {

    E convertToEntity(D dto);
    D convertToDto(E entity);
}
