package com.wzbfq.service.Impl;

import com.wzbfq.bean.Person;
import com.wzbfq.dao.PersonDao;
import com.wzbfq.service.PersonService;
import com.wzbfq.util.DButil;

import java.util.List;

public class PersonServiceImpl implements PersonService {
    PersonDao personDao = new PersonDao();
    @Override
    public List<Person> queAll(DButil dButil) {
        List<Person> personList = null;
        try{
            personList = personDao.queAll(dButil);
        }catch (Exception e){
            e.printStackTrace();
        }
        return personList;
    }

    @Override
    public boolean addPerson(Person p, DButil dButil) {
        boolean isok = false;
        try{
            isok = personDao.addPerson(p,dButil);
        }catch (Exception e){
            e.printStackTrace();
        }
        return isok;
    }

    @Override
    public boolean isCorrectTelenum(String username, String telenum, DButil dButil) {
        boolean isCorrect = false;
        List<Person> personList = null;
        try{
            personList = personDao.queAll(dButil);
        }catch (Exception e){
            e.printStackTrace();
        }
        for(Person p:personList){
            if(username.equals(p.getUsername())){
                if(telenum.equals(p.getTeleno())){
                    isCorrect = true;
                }
                break;
            }
        }
        return isCorrect;
    }
}
