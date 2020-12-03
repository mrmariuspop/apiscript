package apigenerator.apigen;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static apigenerator.apigen.Utils.*;

public class ControlPanel {

    public static void generateClasses(String sqlQuery,
                                       String entityName,
                                       String author,
                                       int nrQueryParam,
                                       Set<String> queryParams,
                                       List<String> listOfParams,
                                       boolean createDir) throws Exception {

        try {
            generateModelClass(sqlQuery, entityName, author, createDir);
            generateQueryClass(entityName, author, sqlQuery, createDir);
            generateDaoClass(entityName, author, nrQueryParam, listOfParams,createDir);
            generateDaoImplClass(entityName, author, nrQueryParam, queryParams, listOfParams,createDir);
            generateServiceClass(entityName, author, nrQueryParam,listOfParams,createDir);
            generateServiceImplClass(entityName, author, nrQueryParam, listOfParams, createDir);
            generateControllerClass(entityName, author, nrQueryParam, listOfParams, createDir);
        } catch (IOException e) {
            throw new Exception("samsing went wrong");
        }
    }
}

