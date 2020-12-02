package apigenerator.apigen.generators;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DaoGeneratorClass {

    public static String getDaoJavaFile(String entityName, String author, int nrQueryParams) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
        String iniialFile = "import java.util.List;\n" +
                "\n" +
                "/**\n" +
                " * @author " + author + "\n" +
                " * @since " + simpleDateFormat.format(new Date(System.currentTimeMillis())) + "\n" +
                " */\n" +
                "public interface " + entityName + "Dao {\n" +
                "    List<" + entityName + "> get" + entityName + "s(";

        for (int i = 1; i <= nrQueryParams; i++) {
            iniialFile += "String param" + i + ", ";
        }

        iniialFile = (nrQueryParams >0)?iniialFile.substring(0, iniialFile.length() - 2):iniialFile;

        iniialFile += ");\n" +
                "}";
        return iniialFile;
    }
}