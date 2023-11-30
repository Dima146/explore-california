package com.company.explorecalifornia.service;

import com.company.explorecalifornia.exception.InvalidPaginationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * The class {@code PageableCreator} specifies the static method to create a Pageable instance
 * for control the number and page of results returned.
 */
public final class PageableCreator {

    private PageableCreator() {
    }

    public static Pageable createPageRequest(int page, int size) {
        Pageable pageRequest;
        try {
            pageRequest = PageRequest.of(page, size);
        } catch (IllegalArgumentException e) {
            throw new InvalidPaginationException("Invalid page number or page size for pagination " +
                    "(page number:"  + page + " size: " + size + "). Page number and size must be positive numbers.");
        }
        return pageRequest;
    }
}