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

}
