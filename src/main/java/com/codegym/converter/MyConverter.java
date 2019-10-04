package com.codegym.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.datetime.DateFormatter;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MyConverter implements Converter<String,LocalDate> {
    private String datePattern;

    public MyConverter(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public LocalDate convert(String source) {
        try {
            return LocalDate.parse(source, DateTimeFormatter.ofPattern(datePattern));
        }catch (DateTimeParseException e) {
            throw  new IllegalArgumentException();
        }
    }
}
