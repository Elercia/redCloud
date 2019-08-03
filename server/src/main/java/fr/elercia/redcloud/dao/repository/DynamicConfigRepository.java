package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.business.entity.DynamicConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicConfigRepository extends CrudRepository<DynamicConfig, DynamicConfig.DynamicConfigName> {

    DynamicConfig findByName(DynamicConfig.DynamicConfigName name);
}
