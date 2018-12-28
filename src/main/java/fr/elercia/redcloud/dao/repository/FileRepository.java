package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.business.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends CrudRepository<File, Integer> {

}
