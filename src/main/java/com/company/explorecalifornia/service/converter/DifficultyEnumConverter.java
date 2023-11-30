package com.company.explorecalifornia.service.converter;

import com.company.explorecalifornia.domain.Difficulty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * The {@code DifficultyEnumConverter} class is an implementation of the {@link Converter} interface
 * containing a method which converts a query request parameter to a Difficulty enumeration
 *
 */
@Component
public class DifficultyEnumConverter implements Converter<String, Difficulty> {

    @Override
    public Difficulty convert(String source) {
        try {
            return Difficulty.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}