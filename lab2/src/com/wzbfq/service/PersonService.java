package com.wzbfq.service;

import com.wzbfq.bean.Person;
import com.wzbfq.util.DButil;

import java.util.List;


public interface PersonService {
    int addOrModifyPerson(Person person, DButil dButil);
    //void addaddOrModifyPerson(Person person);
    //void delPersonOnUsername(String username);
    List<Person> queAll(DButil dButil);
}
