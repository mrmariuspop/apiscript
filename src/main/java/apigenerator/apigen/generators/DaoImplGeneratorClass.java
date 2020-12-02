package apigenerator.apigen.generators;

import apigenerator.apigen.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DaoImplGeneratorClass {

    public static String getDaoImplJavaFile(String entityName, String author, int nrQueryParams) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
        String iniialFile = "import org.springframework.stereotype.Repository;\n" +
                "\n" +
                "/**\n" +
                " * @author "+author+"\n" +
                " * @since "+simpleDateFormat.format(new Date(System.currentTimeMillis()))+"\n" +
                " */\n" +
                "@Repository\n" +
                "@Transactional\n" +
                "public class "+entityName+"DaoImpl implements "+entityName+"Dao {\n" +
                "\n" +
                "    private final SessionFactory sessionFactory;\n" +
                "\n" +
                "    public "+entityName+"DaoImpl(SessionFactory sessionFactory) {\n" +
                "        this.sessionFactory = sessionFactory;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public List<"+entityName+"> get"+entityName+"s(" ;

                for (int i =1; i<= nrQueryParams; i++) {
                    iniialFile += "String param"+i+", ";
                }

                 iniialFile = (nrQueryParams >0)?iniialFile.substring(0, iniialFile.length() - 2):iniialFile;

                iniialFile+=") {\n" +
                "        return UtilsRepository.getDataAsListByAliasBean"+nrQueryParams+"Params(sessionFactory, "+entityName+"Query.GET_"+ Utils.getQueryName(entityName).toUpperCase()+"S, "+entityName+".class,\n" +
                        "                ";

                for (int i =1; i<= nrQueryParams; i++) {
                    iniialFile += "\"P_PARAM"+i+"\", param"+i+",\n" +
                            "                ";
                }

                iniialFile = (nrQueryParams > 0)?iniialFile.substring(0,iniialFile.length()-18)+");\n" +
                        "    }\n" +
                        "}":iniialFile.substring(0,iniialFile.length()-18) +");\n" +
                        "    }\n" +
                        "}";

        return iniialFile;
    }
}