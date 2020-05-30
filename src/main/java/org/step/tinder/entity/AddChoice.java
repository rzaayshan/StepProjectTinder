package org.step.tinder.entity;

import org.step.tinder.DAO.DaoLikes;

public class AddChoice {
    private final DaoLikes daoLikes;

    public AddChoice(DaoLikes daoLikes) {
        this.daoLikes = daoLikes;
    }

    public void add(String who, String whom, String choice){
        if(choice.equals("like"))
            daoLikes.addLikes(who,whom);
    }
}
