package com.admirnurkovic.DatabaseConfig.dao.impl;

import com.admirnurkovic.DatabaseConfig.dao.AuthorDao;
import com.admirnurkovic.DatabaseConfig.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {

    public final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update("INSERT INTO authors(id, name, age) VALUES (?, ?, ?)",
                author.getId(), author.getName(), author.getAge());
    }

    @Override
    public Optional<Author> findOne(Long id) {
       List<Author> results = jdbcTemplate.query("SELECT id, name, age FROM authors where id = ? LIMIT 1",
                new AuthorRowMapper(),
                id);
        return results.stream().findFirst();
    }

    @Override
    public List<Author> find() {
        return jdbcTemplate.query("SELECT id, name, age FROM authors",
                new AuthorRowMapper());
    }

    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
}
