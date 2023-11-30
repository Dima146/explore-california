package com.company.explorecalifornia.hateaos;

import org.springframework.hateoas.RepresentationModel;

/**
 * The {@code HateoasProvider} interface defines a method for building HATEOAS hyperlinks to objects of type T
 * based on the methods of a particular Controller class.
 *
 *
 * @param <T> the type parameter
 */

public interface HateoasProvider<T extends RepresentationModel<T>> {

    /**
     * Adds links to entity objects.
     *
     * @param entity the entity to which links will be added.
     */
    void addLinks(T entity);
}