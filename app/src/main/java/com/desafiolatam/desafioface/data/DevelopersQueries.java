package com.desafiolatam.desafioface.data;

import com.desafiolatam.desafioface.models.Developer;

import java.util.List;

/**
 * Created by Soporte on 16-Oct-17.
 */

public class DevelopersQueries {

    public List<Developer> all (){
        return Developer.listAll(Developer.class);
    }

    public List<Developer> findByName(String name){
        String query = "name LIKE ?";
        String argument = "%"+name+"%";
        return Developer.find(Developer.class, query, argument);
    }
}
