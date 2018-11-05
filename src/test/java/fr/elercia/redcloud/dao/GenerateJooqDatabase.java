package fr.elercia.redcloud.dao;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXB;
import java.io.File;

class GenerateJooqDatabase {

    @Test
    void generate() throws Exception {
        Configuration configuration = JAXB.unmarshal(new File("sql/config.xml"), Configuration.class);

        GenerationTool.generate(configuration);
    }
}