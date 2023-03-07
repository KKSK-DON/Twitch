package com._91mrmao.jupiter.service;

import com._91mrmao.jupiter.dao.FavoriteDao;
import com._91mrmao.jupiter.entity.db.Item;
import com._91mrmao.jupiter.entity.db.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;

    public void setFavoriteItem(String userId, Item item) {
        favoriteDao.setFavoriteItem(userId, item);
    }

    public void unsetFavoriteItem(String userId, String itemId) {
        favoriteDao.unsetFavoriteItem(userId, itemId);
    }

    public Map<String, List<Item>> getFavoriteItems(String userId) {
        Map<String, List<Item>> itemMap = new HashMap<>();
        for (ItemType type : ItemType.values()) {
            itemMap.put(type.toString(), new ArrayList<>());
        }
        Set<Item> favorites = favoriteDao.getFavoriteItems(userId);
        //把set 改成map
        for(Item item : favorites) {
            itemMap.get(item.getType().toString()).add(item);
        }
        return itemMap;
    }
}

