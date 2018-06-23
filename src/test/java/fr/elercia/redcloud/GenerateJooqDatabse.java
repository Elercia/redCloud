package fr.elercia.redcloud;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXB;
import java.io.File;

class GenerateJooqDatabse {

    @Test
    void main() throws Exception {
        Configuration configuration = JAXB.unmarshal(new File("config.xml"), Configuration.class);
        configuration.getJdbc()
                .withUser("redcloud")
                .withPassword("root");

        GenerationTool.generate(configuration);
    }
}