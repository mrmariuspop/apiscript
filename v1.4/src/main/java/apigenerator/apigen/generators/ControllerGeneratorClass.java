package apigenerator.apigen.generators;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static apigenerator.apigen.generators.ModelGeneratorClass.getAttType;

public class ControllerGeneratorClass {

    public static String getControllerJavaFile(String entityName, String author, int nrQueryParams, List<String> listOfParams) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/yyyy");
        String iniialFile =
                "import io.swagger.annotations.ApiOperation;\n" +
                "import org.springframework.web.bind.annotation.GetMapping;\n" +
                "import org.springframework.web.bind.annotation.PostMapping;\n" +
                "import org.springframework.web.bind.annotation.RequestBody;\n" +
                "import org.springframework.web.bind.annotation.RestController;\n"+
                "import org.springframework.http.HttpStatus;\n" +
                "import org.springframework.http.ResponseEntity;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import org.springframework.web.servlet.ModelAndView;\n" +
                "\n" +
                "import java.math.BigDecimal;\n" +
                "import java.util.List;\n" +
                        "\n" +
                        "/**\n" +
                        " * @author "+author+"\n" +
                        " * @since "+simpleDateFormat.format(new Date(System.currentTimeMillis()))+"\n" +
                        " */\n"+
                "@RestController\n" +
                "@RequestMapping(\"/api/" + entityName.toLowerCase() + "\")\n" +
                "public class " + entityName + "Controller {\n" +
                "\n" +
                "    private final " + entityName + "Service " + entityName.substring(0, 1).toLowerCase() + entityName.substring(1) + "Service;\n" +
                "\n" +
                "    public NplController(NplModelService " + entityName.substring(0, 1).toLowerCase() + entityName.substring(1) + "Service) {\n" +
                "        this." + entityName.substring(0, 1).toLowerCase() + entityName.substring(1) + "Service = " + entityName.substring(0, 1).toLowerCase() + entityName.substring(1) + "Service;\n" +
                "    }\n" +
                "\n" +
                "    @ApiOperation(\"get all " + entityName + "s\")\n" +
                "    @GetMapping(\"/all\")\n" +
                "    public List<" + entityName + "> getAll" + entityName + "s(";
        for (int i = 1; i <= nrQueryParams; i++) {
            iniialFile += "@RequestParam(name = \""+listOfParams.get(i-1)+""+""+"\", required = false) "+ getAttType(listOfParams.get(i-1))+" "+listOfParams.get(i-1)+""+""+",\n" +
                    "                                          ";
        }
        iniialFile = (nrQueryParams >0)?iniialFile.substring(0, iniialFile.length() - 44):iniialFile;

        iniialFile += ") {\n" +
                "        return " + entityName.substring(0, 1).toLowerCase() + entityName.substring(1) + "Service.get" + entityName + "s(";

        for (int i = 1; i <= nrQueryParams; i++) {
            iniialFile += ""+listOfParams.get(i-1)+"" + "" + ", ";
        }

        iniialFile = (nrQueryParams >0)?iniialFile.substring(0, iniialFile.length() - 2):iniialFile;
        iniialFile +=
                ");\n" +
                        "    }\n" +
                        "}";
        return iniialFile;
    }

}