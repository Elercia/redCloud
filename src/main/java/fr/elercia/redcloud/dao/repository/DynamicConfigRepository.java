package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.business.entity.DynamicConfig;
import fr.elercia.redcloud.business.entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DynamicConfigRepository extends CrudRepository<DynamicConfig, DynamicConfig.DynamicConfigName> {

    DynamicConfig findByName(DynamicConfig.DynamicConfigName name);
}
