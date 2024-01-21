package com.admirnurkovic.DatabaseConfig;

import com.admirnurkovic.DatabaseConfig.domain.Author;
import com.admirnurkovic.DatabaseConfig.domain.Book;

public final class TestDataUtils {

    private TestDataUtils(){}

    public static Author creteTestAuthor() {
        return Author.builder()
                .id(1L)
                .age(80)
                .name("Admir Nurkovic")
                .build();
    }

    public static Book createSimpleBook() {
        return Book.builder()
                .isbn("978-1-2345")
                .title("The shadow in the Athic")
                .authorId(1L)
                .build();
    }

    public static Author creteTestAuthorWithParams(Long id, Integer age, String name ) {
        return Author.builder()
                .id(id)
                .age(age)
                .name(name)
                .build();
    }

    public static Book createSimpleBookWithParams(String isbn, String title, Long authorId ) {
        return Book.builder()
                .isbn(isbn)
                .title(title)
                .authorId(authorId)
                .build();
    }
}
