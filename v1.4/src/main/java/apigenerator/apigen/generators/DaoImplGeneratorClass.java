package apigenerator.apigen.generators;

import apigenerator.apigen.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DaoImplGeneratorClass {

    public static String getDaoImplJavaFile(String entityName, String author, int nrQueryParams, Set<String> queryParams, List<String> listOfParams) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
        Iterator<String> iterator = queryParams.iterator();
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
                    iniialFile += ""+ModelGeneratorClass.getAttType(listOfParams.get(i-1))+" "+listOfParams.get(i-1)+""+""+", ";
                }

                 iniialFile = (nrQueryParams >0)?iniialFile.substring(0, iniialFile.length() - 2):iniialFile;

                iniialFile+=") {\n" +
                "        return UtilsRepository.getDataAsListByAliasBean"+nrQueryParams+"Params(sessionFactory, "+entityName+"Query.GET_"+ Utils.getQueryName(entityName).toUpperCase()+"S, "+entityName+".class,\n" +
                        "                ";

                for (int i =1; i<= nrQueryParams; i++) {
                    iniialFile += "\""+iterator.next()+""+""+"\", "+listOfParams.get(i-1)+""+""+",\n" +
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