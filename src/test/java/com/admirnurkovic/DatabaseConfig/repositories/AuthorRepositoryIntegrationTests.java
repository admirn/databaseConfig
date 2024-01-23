package com.admirnurkovic.DatabaseConfig.repositories;

import com.admirnurkovic.DatabaseConfig.TestDataUtils;
import com.admirnurkovic.DatabaseConfig.domain.Author;
import com.admirnurkovic.DatabaseConfig.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTests {

    private final AuthorRepository underTest;

    @Autowired
    public AuthorRepositoryIntegrationTests(AuthorRepository underTest) {
        this.underTest = underTest;
    }
    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtils.creteTestAuthor();
        underTest.save(author);
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRetrieved(){
        Author author1 = TestDataUtils.creteTestAuthorWithParams(1L, 35, "Samy Simple");
        underTest.save(author1);
        Author author2 = TestDataUtils.creteTestAuthorWithParams(2L, 30, "Stony Simple");
        underTest.save(author2);
        Author author3 = TestDataUtils.creteTestAuthorWithParams(3L, 32, "Greedy Simple");
        underTest.save(author3);

        Iterable<Author> result = underTest.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(author1, author2, author3);
    }
//
    @Test
    public void testThatAuthorCanBeUpdated(){
        Author author1 = TestDataUtils.creteTestAuthor();
        underTest.save(author1);
        author1.setName("Updated");
        underTest.save(author1);

        Optional<Author> result = underTest.findById(author1.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author1);

    }
//
    @Test
    public void testAuthorCanBeDeleted(){
        Author author = TestDataUtils.creteTestAuthor();
        underTest.save(author);

//        Book book = TestDataUtils.createSimpleBook(author);

        underTest.delete(author);
        Optional<Author> result = underTest.findById(author.getId());
        assertThat(result).isEmpty();
//       assertThat(book).isNull();

    }

    @Test
    public void testThatAuthorWithAgeLessThen(){
        Author author1 = TestDataUtils.creteTestAuthorWithParams(
                1L, 30, "Admir"
        );
        underTest.save(author1);
        Author author2 = TestDataUtils.creteTestAuthorWithParams(
                2L, 35, "Test 2"
        );
        underTest.save(author2);
        Author author3 = TestDataUtils.creteTestAuthorWithParams(
                3L, 40, "Test 3"
        );
        underTest.save(author3);

        Iterable<Author> authors = underTest.ageLessThan(40);

        assertThat(authors).containsExactly(author1, author2);
    }
    @Test
    public void testAuthorWithAgeGreaterThe(){
        Author author1 = TestDataUtils.creteTestAuthorWithParams(
                1L, 30, "Admir"
        );
        underTest.save(author1);
        Author author2 = TestDataUtils.creteTestAuthorWithParams(
                2L, 35, "Test 2"
        );
        underTest.save(author2);
        Author author3 = TestDataUtils.creteTestAuthorWithParams(
                3L, 40, "Test 3"
        );
        underTest.save(author3);

        Iterable<Author> result = underTest.ageGreaterThen(30);
        assertThat(result).containsExactly(author2, author3);
    }
}
