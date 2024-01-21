package com.admirnurkovic.DatabaseConfig.dao.impl;

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
public class BookDaoImplIntegrationTest {

    private BookDaoImpl underTest;
    private AuthorDaoImpl authorDao;

    @Autowired
    public BookDaoImplIntegrationTest(BookDaoImpl underTest, AuthorDaoImpl authorDao){
        this.underTest = underTest;
        this.authorDao = authorDao;
    }

    @Test
    public void testBookCreationAndRetrival(){

        Author author = TestDataUtils.creteTestAuthor();
        authorDao.create(author);
        Book book = TestDataUtils.createSimpleBook();
        book.setAuthorId(author.getId());
        underTest.create(book);
        Optional<Book> result = underTest.findOne(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testMultipleBookCreationandFindMany(){
        Author author = TestDataUtils.creteTestAuthor();
        authorDao.create(author);

        Author author1 = TestDataUtils.creteTestAuthorWithParams(2L, 35, "Gorge Orwell");
        authorDao.create(author1);

        Book book1 = TestDataUtils.createSimpleBookWithParams("253-253-656", "The oldman and the sea", author.getId());
        underTest.create(book1);
        Book book2 = TestDataUtils.createSimpleBookWithParams(
                "355-355-355", "Gatsby", author1.getId()
        );
        underTest.create(book2);
        List<Book> result = underTest.find();

        assertThat(result).hasSize(2).containsExactly(book1, book2);
        assertThat(result.get(0).getAuthorId()).isEqualTo(author.getId());
        assertThat(result.get(1).getAuthorId()).isEqualTo(author1.getId());

    }
    @Test
    public void testThatBookCanBeUpdated(){
        Author author = TestDataUtils.creteTestAuthor();
        authorDao.create(author);

        Book book = TestDataUtils.createSimpleBook();
        book.setAuthorId(author.getId());

        underTest.create(book);

        book.setTitle("UPDATED");
        underTest.update(book.getIsbn(), book);

        Optional<Book> one = underTest.findOne(book.getIsbn());

        assertThat(one).isPresent();
        assertThat(one.get()).isEqualTo(book);


    }

    @Test
    public void testThatBookCanBeDeleted(){
        Author author = TestDataUtils.creteTestAuthor();
        authorDao.create(author);
        Book book = TestDataUtils.createSimpleBook();
        book.setAuthorId(author.getId());
        underTest.create(book);

        underTest.delete(book.getIsbn());

        Optional<Book> one = underTest.findOne(book.getIsbn());
        assertThat(one).isEmpty();


    }



}
