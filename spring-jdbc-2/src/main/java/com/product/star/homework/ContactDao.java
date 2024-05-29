package com.product.star.homework;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collection;
import java.util.List;

public class ContactDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public ContactDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public Contact getContact(long id) {
        String sql = "SELECT * FROM my_contact WHERE id= :id";
        return namedJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id), Contact.contactRowMapper);
    }
    public List<Contact> getAllContacts() {
        String sql = "SELECT * FROM my_contact";
        return namedJdbcTemplate.query(sql, Contact.contactRowMapper);
    }

    public void saveAll(Collection<Contact> contacts) {
        for (Contact contact : contacts) {
            save(contact);
        }
    }

    public Contact save(Contact contact) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO my_contact (name, surname, email, phone) VALUES (:name, :surname, :email, :phone)";
        namedJdbcTemplate.update(
                sql,
                new MapSqlParameterSource()
                        .addValue("name", contact.getName())
                        .addValue("surname", contact.getSurname())
                        .addValue("email", contact.getEmail())
                        .addValue("phone", contact.getPhone()),
                keyHolder, new String[] {"id"});

        contact.setId(keyHolder.getKey().longValue());
        return contact;
    }

    public void deleteContact(long id){
        String sql = "DELETE FROM my_contact WHERE id = :id";
        namedJdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }

    public void setEmail(long id, String newEmail){
        String sql = "UPDATE my_contact SET email= :email WHERE id= :id";
        namedJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("email", newEmail));
    }

    public void setPhone(long id, String newPhone){
        String sql = "UPDATE my_contact SET phone= :phone WHERE id= :id";
        namedJdbcTemplate.update(sql, new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("phone", newPhone));
    }
}
