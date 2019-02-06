package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.business.entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

    Token findByAccessToken(String accessToken);

    void deleteByAccessToken(String accessToken);
}
