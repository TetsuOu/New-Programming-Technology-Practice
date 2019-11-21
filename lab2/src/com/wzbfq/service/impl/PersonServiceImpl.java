package com.wzbfq.service.impl;

import com.wzbfq.bean.Person;
import com.wzbfq.dao.PersonDao;
import com.wzbfq.service.PersonService;
import com.wzbfq.util.DButil;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    private PersonDao personDao = new PersonDao();

    @Override
    public int addOrModifyPerson(Person person,DButil dButil) {
        int ans=-1;
        try{
            ans = personDao.addOrModifyPerson(person,dButil);
        }catch (Exce0ption e){
            e.printStackTrace();
        }
        return ans;
    }

    @Override
    public List<Person> queAll(DButil dButil) {
        List<Person> list = null;
        try{
            list = personDao.queAll(dButil);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
