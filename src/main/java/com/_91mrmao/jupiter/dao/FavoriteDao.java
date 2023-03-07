package com._91mrmao.jupiter.dao;

import com._91mrmao.jupiter.entity.db.Item;
import com._91mrmao.jupiter.entity.db.ItemType;
import com._91mrmao.jupiter.entity.db.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
//hibernate自动生成的API
public class FavoriteDao {

    //@autowire ： dependency injection
    @Autowired
    private SessionFactory sessionFactory;

    // Insert a favorite record to the database
    public void setFavoriteItem(String userId, Item item){

        Session session = null;
        //db的一次操作

        try {
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            user.getItemSet().add(item);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    // Remove a favorite record from the database
    public void unsetFavoriteItem(String userId, String itemId) {
        Session session = null;
        try{
            session = sessionFactory.openSession();
            User user = session.get(User.class, userId);
            Item item = session.get(Item.class, itemId);
            user.getItemSet().remove(item);

            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            if (session != null) session.close();
        }
    }
    //read
    public Set<Item> getFavoriteItems(String userId) {
        //和上面的写法没有区别
        Session session = null;
        try  {
            session = sessionFactory.openSession();
            return session.get(User.class, userId).getItemSet();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if (session != null) session.close();
        }
        return new HashSet<>();
    }

    // Get favorite item ids for the given user


    // Get favorite items for the given user. The returned map includes three entries like {"Video": [item1, item2, item3], "Stream": [item4, item5, item6], "Clip": [item7, item8, ...]}
    public Map<String, List<String>> getFavoriteGameIds(Set<Item> items) {
        Map<String, List<String>> itemMap = new HashMap<>();
        for (ItemType type : ItemType.values()) {
            itemMap.put(type.toString(), new ArrayList<>());
        }
        for (Item item : items) {
            itemMap.get(item.getType().toString()).add(item.getGameId());
        }
        return itemMap;
    }




}
