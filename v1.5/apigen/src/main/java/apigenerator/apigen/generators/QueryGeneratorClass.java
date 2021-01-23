package apigenerator.apigen.generators;

import apigenerator.apigen.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryGeneratorClass {
    public static String generateQueryClass(String entityName, String author, String sqlQuery) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");

        String initialString = "package apigenerator.apigen;\n" +
                "\n" +
                "/**\n" +
                " * @author "+author+"\n" +
                " * @since "+simpleDateFormat.format(new Date(System.currentTimeMillis()))+"\n" +
                " */\n" +
                "public class "+entityName+"Query {\n" +
                "\n" +
                "    public static String GET_"+ Utils.getQueryName(entityName).toUpperCase()+"S = " +
                "\""+sqlQuery.replace("\n","").replace("   "," ").replace(", ",",\" +\n            \"") +"\";"+
                "\n}";
        return initialString;
    }
}
