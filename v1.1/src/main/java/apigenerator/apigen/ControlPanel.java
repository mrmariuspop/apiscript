package apigenerator.apigen;

import java.io.IOException;
import static apigenerator.apigen.Utils.*;

public class ControlPanel {

    public static void generateClasses(String sqlQuery, String entityName, String author, int nrQueryParam) throws IOException {

        generateModelClass(sqlQuery, entityName, author);
        generateQueryClass(entityName, author, sqlQuery);
        generateDaoClass(entityName, author, nrQueryParam);
        generateDaoImplClass(entityName, author, nrQueryParam);
        generateServiceClass(entityName, author, nrQueryParam);
        generateServiceImplClass(entityName, author, nrQueryParam);
        generateControllerClass(entityName, author, nrQueryParam);
    }
}

