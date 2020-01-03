package com.wzbfq.service;

import com.wzbfq.bean.Person;
import com.wzbfq.util.DButil;

import java.util.List;

public interface PersonService {
    public List<Person> queAll(DButil dButil);
    public boolean addPerson (Person p, DButil dButil);
    public boolean isCorrectTelenum(String username, String telenum, DButil dButil);
}
