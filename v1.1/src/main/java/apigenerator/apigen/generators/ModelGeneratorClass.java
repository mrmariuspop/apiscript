package apigenerator.apigen.generators;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ModelGeneratorClass {

    public static String getModelJavaFile(List<String> listOfAttributes, String entityName, String author){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
        String iniialFile  =
                "import lombok.Data;\n" +
                "\n" +
                "import java.math.BigDecimal;\n" +
                "import java.util.Date;\n" +
                "\n" +
                "/**\n" +
                " * @author "+author+"\n" +
                " * @since "+simpleDateFormat.format(new Date(System.currentTimeMillis()))+"\n" +
                " */\n" +
                "@Data\n" +
                "public class "+entityName+" {\n" +
                "\n";

        for (String iterator:listOfAttributes) {
            String type = getAttType(iterator);
            iniialFile = iniialFile +
                    "    "+type+" "+iterator+";\n" ;

        }
        iniialFile = iniialFile + "}";
        return iniialFile;
    }

    public static String getAttType(String field){
        if (field.contains("id")) return "BigDecimal";
        if (field.contains("Id")) return "BigDecimal";
        if (field.contains("Date")) return "Date";
        if (field.contains("Data")) return "Date";
        if (field.contains("date")) return "Date";
        if (field.contains("data")) return "Date";
        if (field.contains("suma")) return "BigDecimal";
        if (field.contains("sum")) return "BigDecimal";
        if (field.contains("max")) return "BigDecimal";
        if (field.contains("Max")) return "BigDecimal";
        if (field.contains("nr")) return "BigDecimal";
        if (field.contains("Nr")) return "BigDecimal";
        else return "String";
    }


}
