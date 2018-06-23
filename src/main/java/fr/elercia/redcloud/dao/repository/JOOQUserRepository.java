package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.dao.entity.UserBase;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class JOOQUserRepository implements UserRepository {

    private DSLContext jooq;
 
    @Autowired
    public JOOQUserRepository(@Qualifier("dsl") DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public UserBase add(UserBase entity) {

        return null;
    }

    @Override
    public void update(UserBase entity) {

    }

    @Override
    public void delete(UserBase entity) {

    }

    @Override
    public List<UserBase> findAll() {
        return null;
    }

    @Override
    public UserBase findById(Long id) {
        return null;
    }

    @Override
    public UserBase findByResourceId(UUID id) {
        return null;
    }
}