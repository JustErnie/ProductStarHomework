package com.product.star.homework;

import com.product.star.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Repository
public class ContactDao {

    private final SessionFactory sessionFactory;

    public ContactDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Contact> getAllContacts() {
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("from Contact", Contact.class).getResultList();
        }
    }

    public Contact getContact(long contactId) {
        try (Session session = sessionFactory.openSession()){
            return session.get(Contact.class, contactId);
        }
    }

    public long addContact(Contact contact) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            long returnedId = (long) session.save(contact);
            transaction.commit();
            return returnedId;
        }
    }

    public void updatePhoneNumber(long contactId, String phoneNumber) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();

            Contact contact = getContact(contactId);
            contact.setPhone(phoneNumber);
            session.update(contact);

            transaction.commit();
        }
    }

    public void updateEmail(long contactId, String email) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();

            Contact contact = getContact(contactId);
            contact.setEmail(email);
            session.update(contact);

            transaction.commit();
        }
    }

    public void deleteContact(long contactId) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Contact contact = getContact(contactId);
            if (contact != null){
                session.delete(contact);
            }
            transaction.commit();
        }
    }
}
