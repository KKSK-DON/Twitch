package com._91mrmao.jupiter.dao;

import com._91mrmao.jupiter.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {

    @Autowired
    private SessionFactory sessionFactory;

    // Verify if the given user Id and password are correct. Returns the user name when it passes
    public String verifyLogin(String userId, String password) {
        String name = "";

//https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, userId);
//            https://docs.jboss.org/hibernate/orm/3.5/javadocs/org/hibernate/Session.html
            if(user != null && user.getPassword().equals(password)) {
                name = user.getFirstName();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return name;
    }
}

