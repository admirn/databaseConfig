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
public class BookRepositoryIntegrationTests {

    private BookRepository underTest;
    private AuthorRepository authorDao;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository underTest, AuthorRepository authorDao){
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testBookCreationAndRetrival(){

        Author author = TestDataUtils.creteTestAuthor();
        Book book = TestDataUtils.createSimpleBook(author);
        underTest.save(book);
        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testMultipleBookCreationandFindMany(){
        Author author = TestDataUtils.creteTestAuthor();
        authorDao.save(author);

        Author author1 = TestDataUtils.creteTestAuthorWithParams(2L, 35, "George Orwell");
        authorDao.save(author1);

        Book book1 = TestDataUtils.createSimpleBookWithParams("253-253-656", "The oldman and the sea", author);
        underTest.save(book1);
        Book book2 = TestDataUtils.createSimpleBookWithParams(
                "355-355-355", "Gatsby", author
        );
        underTest.save(book2);
        Iterable<Book> result = underTest.findAll();

        assertThat(result).hasSize(2).containsExactly(book1, book2);
        for(Book bk: result){
            assertThat(bk.getAuthor()).isEqualTo(author);
        }

    }
    @Test
    public void testThatBookCanBeUpdated(){
        Author author = TestDataUtils.creteTestAuthor();
        authorDao.save(author);

        Book book = TestDataUtils.createSimpleBook(author);

        underTest.save(book);

        book.setTitle("UPDATED");
        underTest.save(book);

        Optional<Book> one = underTest.findById(book.getIsbn());

        assertThat(one).isPresent();
        assertThat(one.get()).isEqualTo(book);


    }
//
    @Test
    public void testThatBookCanBeDeleted(){
        Author author = TestDataUtils.creteTestAuthor();
        authorDao.save(author);
        Book book = TestDataUtils.createSimpleBook(author);
        underTest.save(book);

        underTest.delete(book);

        Optional<Book> one = underTest.findById(book.getIsbn());
        assertThat(one).isEmpty();


    }



}
