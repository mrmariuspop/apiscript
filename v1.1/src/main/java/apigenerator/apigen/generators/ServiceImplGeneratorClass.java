package apigenerator.apigen.generators;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceImplGeneratorClass {

    public static String getServiceImplJavaFile(String entityName, String author, int nrQueryParams) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
        String iniialFile = "import org.springframework.stereotype.Service;\n" +
                "\n" +
                "import java.util.List;\n" +
                "\n" +
                "/**\n" +
                " * @author " + author + "\n" +
                " * @since " + simpleDateFormat.format(new Date(System.currentTimeMillis())) + "\n" +
                " */\n" +
                "@Service\n" +
                "public class " + entityName + "ServiceImpl implements " + entityName + "Service{\n" +
                "\n" +
                "    private final " + entityName + "Dao " + entityName.substring(0, 1).toLowerCase() + entityName.substring(1) + "Dao;\n" +
                "\n" +
                "    public " + entityName + "ServiceImpl(" + entityName + "Dao " + entityName.substring(0, 1).toLowerCase() + entityName.substring(1) + "Dao) {\n" +
                "        this." + entityName.substring(0, 1).toLowerCase() + entityName.substring(1) + "Dao = " + entityName.substring(0, 1).toLowerCase() + entityName.substring(1) + "Dao;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public List<" + entityName + "> get" + entityName + "s(";
        for (int i = 1; i <= nrQueryParams; i++) {
            iniialFile += "String param" + i + ", ";
        }

        iniialFile = (nrQueryParams >0)?iniialFile.substring(0, iniialFile.length() - 2):iniialFile;

        iniialFile += ") {\n" +
                "        return " + entityName.substring(0, 1).toLowerCase() + entityName.substring(1) + "Dao.get" + entityName + "s(";

        for (int i = 1; i <= nrQueryParams; i++) {
            iniialFile += "param" + i + ", ";
        }

        iniialFile = (nrQueryParams >0)?iniialFile.substring(0, iniialFile.length() - 2):iniialFile;
        iniialFile += ");\n" +
                "    }\n" +
                "}";

        return iniialFile;
    }
}