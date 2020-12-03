package apigenerator.apigen;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static apigenerator.apigen.Utils.*;

public class ControlPanel {

    public static void generateClasses(String sqlQuery, String entityName, String author, int nrQueryParam, Set<String> queryParams, List<String> listOfParams) throws Exception {

        try {
            generateModelClass(sqlQuery, entityName, author);
            generateQueryClass(entityName, author, sqlQuery);
            generateDaoClass(entityName, author, nrQueryParam, listOfParams);
            generateDaoImplClass(entityName, author, nrQueryParam, queryParams, listOfParams);
            generateServiceClass(entityName, author, nrQueryParam,listOfParams);
            generateServiceImplClass(entityName, author, nrQueryParam, listOfParams);
            generateControllerClass(entityName, author, nrQueryParam, listOfParams);
        } catch (IOException e) {
            throw new Exception("samsing went wrong");
        }
    }
}

