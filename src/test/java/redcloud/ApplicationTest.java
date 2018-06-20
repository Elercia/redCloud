package redcloud;

import java.io.File;
import javax.xml.bind.JAXB;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Test
    void main() throws Exception {
        Configuration configuration = JAXB.unmarshal(new File("config.xml"), Configuration.class);
        configuration.getJdbc()
                .withUser("redcloud")
                .withPassword("root");

        GenerationTool.generate(configuration);
    }
}