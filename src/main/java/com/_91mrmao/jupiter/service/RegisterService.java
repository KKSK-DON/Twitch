package com._91mrmao.jupiter.service;

import com._91mrmao.jupiter.dao.RegisterDao;
import com._91mrmao.jupiter.entity.db.User;
import com._91mrmao.jupiter.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RegisterService {

    @Autowired
    private RegisterDao registerDao;

    public boolean register(User user) throws IOException {
        user.setPassword(Util.encryptPassword(user.getUserId(), user.getPassword()));
        return registerDao.register(user);
    }
}
