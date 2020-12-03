package apigenerator.apigen;

import apigenerator.apigen.generators.QueryGeneratorClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static apigenerator.apigen.generators.ControllerGeneratorClass.getControllerJavaFile;
import static apigenerator.apigen.generators.DaoImplGeneratorClass.getDaoImplJavaFile;
import static apigenerator.apigen.generators.ModelGeneratorClass.getModelJavaFile;
import static apigenerator.apigen.generators.DaoGeneratorClass.getDaoJavaFile;
import static apigenerator.apigen.generators.ServiceGeneratorClass.getServiceJavaFile;
import static apigenerator.apigen.generators.ServiceImplGeneratorClass.getServiceImplJavaFile;

public class Utils {

    public static void generateModelClass(String sqlQuery, String entityName, String author) throws IOException {
        List<String> finalListOfAttributes = getFinalListOfAttributes(sqlQuery);
        BufferedWriter writer = new BufferedWriter(new FileWriter(entityName+".java"));
        writer.write(getModelJavaFile(finalListOfAttributes, entityName, author));
        writer.close();
    }

    public static void generateQueryClass(String entityName, String author, String sqlQuery) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(entityName+"Query.java"));
        writer.write(QueryGeneratorClass.generateQueryClass(entityName, author, sqlQuery));
        writer.close();
    }

    public static void generateDaoClass(String entityName, String author, int nrQueryParam, List<String> listOfParams) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(entityName+"Dao.java"));
        writer.write(getDaoJavaFile(entityName, author, nrQueryParam, listOfParams));
        writer.close();
    }

    public static void generateServiceClass(String entityName, String author, int nrQueryParam, List<String> listOfParams) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(entityName+"Service.java"));
        writer.write(getServiceJavaFile(entityName, author, nrQueryParam, listOfParams));
        writer.close();
    }

    public static void generateDaoImplClass(String entityName, String author, int nrOfQueryParams, Set<String> queryParams, List<String> listOfParams) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(entityName+"DaoImpl.java"));
        writer.write(getDaoImplJavaFile(entityName, author, nrOfQueryParams, queryParams, listOfParams));
        writer.close();
    }

    public static void generateServiceImplClass(String entityName, String author, int nrOfQueryParams, List<String> listOfParams) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(entityName+"ServiceImpl.java"));
        writer.write(getServiceImplJavaFile(entityName, author, nrOfQueryParams, listOfParams));
        writer.close();
    }

    public static void generateControllerClass(String entityName, String author, int nrOfQueryParams, List<String> listOfParams) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(entityName+"Controller.java"));
        writer.write(getControllerJavaFile(entityName, author, nrOfQueryParams, listOfParams));
        writer.close();
    }

    private static List<String> getFinalListOfAttributes(String sqlQuery) {
        List<String> listOfLowerCases = toLowerCases(sqlQuery);
        List<String> listOfAttributes = new ArrayList<>();
        List<String> listOfAggregatedAttributes = new ArrayList<>();
        List<String> listOfNoParantAttributes = new ArrayList<>();
        for (String iterator:listOfLowerCases) {
            listOfAttributes.add(getFinalAttributeName(iterator));
        }

        for (String iterator:listOfAttributes) {
            listOfAggregatedAttributes.add(removeAggregatedKeyWords(iterator).replace("t.","").trim());
        }

        for(String iterator:listOfAggregatedAttributes) {
            listOfNoParantAttributes.add(iterator.replaceAll("\\([^()]*\\)", "").trim());
        }
        System.out.println(listOfNoParantAttributes);
        return listOfNoParantAttributes;
    }

    public static String removeAggregatedKeyWords(String attribute) {
        if (attribute.contains("max") || attribute.contains("sum") || attribute.contains("avg")) {
            return attribute.substring(3);
        }
        if (attribute.contains("count")){
            return attribute.substring(5);
        }
        else return attribute;
    }

    private static String getFinalAttributeName(String field) {
        int i =1;
        while (i <=  StringUtils.countMatches(field,"_")) {
            field = processCapitals(field);
        }
        return field;
    }

    private static String processCapitals(String field) {
        int indexOfUnderscore = field.indexOf("_");
        if (field.contains("_")) {
            field = field.replace("_" + field.charAt(indexOfUnderscore + 1), "" + Character.toUpperCase(field.charAt(indexOfUnderscore + 1)));
        }
        return field;
    }

    public static String processUsername(String field) {
        int indexOfUnderscore = field.indexOf(".");
        if (field.contains(".")) {
            field = field.replace("." + field.charAt(indexOfUnderscore + 1), "" + Character.toUpperCase(field.charAt(indexOfUnderscore + 1)));
        }
        return field;
    }

    private static List<String> toLowerCases(String sqlQuery) {
        List<String> listOfAttributes = getListOfAttributes(sqlQuery);
        List<String> lowerCases = new ArrayList<>();
        for (String iterator : listOfAttributes) {
            lowerCases.add(iterator.toLowerCase());
        }
        return lowerCases;
    }


    private static List<String> getListOfAttributes(String sqlQuery) {
        String s = processQuery(sqlQuery);
        List<String> listOfAttributes = new ArrayList<>();
        String[] split = s.split(",");
        for (String aux : split) {
            listOfAttributes.add(aux.trim());
        }
        return listOfAttributes;
    }

    private static String processQuery(String sqlQuery) {
        return removeSelectFromQuery(removeFrom(sqlQuery)).trim().replace("\n", "");
    }

    private static String removeFrom(String sqlQuery) {
        sqlQuery = sqlQuery.toUpperCase();
        return sqlQuery.replace(sqlQuery.substring(sqlQuery.indexOf("FROM")), "").trim();
    }


    public static String removeSelectFromQuery(String query) {
        query = query.toUpperCase();
        return query.replace("SELECT", "");
    }

    public static int findIndexOfSecondCapitalLetter(String entityName) {
        for(int i = 1; i<entityName.length();i++) {
            if (entityName.charAt(i) == Character.toUpperCase(entityName.charAt(i))) return i-1;
        }
        return -1;
    }

    public static String getQueryName(String entityName) {
        if (findIndexOfSecondCapitalLetter(entityName) != -1) {
            return insertString(entityName,"_",findIndexOfSecondCapitalLetter(entityName));
        } else {
            return entityName;
        }
    }
    public static String insertString(
            String originalString,
            String stringToBeInserted,
            int index)
    {

        // Create a new string
        String newString = new String();

        for (int i = 0; i < originalString.length(); i++) {

            // Insert the original string character
            // into the new string
            newString += originalString.charAt(i);

            if (i == index) {

                // Insert the string to be inserted
                // into the new string
                newString += stringToBeInserted;
            }
        }

        // return the modified String
        return newString;
    }

    public static String getStringBetweenTwoChars(String input, String startChar, String endChar) {
        try {
            int start = input.indexOf(startChar);
            if (start != -1) {
                int end = input.indexOf(endChar, start + startChar.length());
                if (end != -1) {
                    return input.substring(start + startChar.length(), end);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // return null; || return "" ;
    }

    static Set<String> extractQueryParams(String sqlQuery) {
        Set<String> queryParams = new HashSet<String>() {};
        Pattern pattern = Pattern.compile(":\\w+");

        Matcher matcher = pattern.matcher(sqlQuery);
        while (matcher.find())
        {
            queryParams.add(matcher.group().replace(":",""));
        }
        return queryParams;
    }

    public static List<String> getAttributesFromSet(Set<String> attributes) {
        List<String> listOfAttributes = new ArrayList<>();
        Iterator<String> iterator = attributes.iterator();
        while(iterator.hasNext()) {
            listOfAttributes.add(iterator.next().toLowerCase());
        }
        return listOfAttributes;
    }

    public static List<String> formatQueryParams(List<String> listOfStrings){
        List<String> formattedList = new ArrayList<>();
        List<String> formattedListFinal = new ArrayList<>();
        for (String iterator:listOfStrings) {
            formattedList.add(iterator.replace("p_",""));
        }

        for (String iterator:formattedList) {
            while (iterator.contains("_")) {
                iterator = processCapitals(iterator);
            }
            formattedListFinal.add(iterator);
        }
        return formattedListFinal;
    }

    public static String getAuthorFromPrincipal(String username) {
        return WordUtils.capitalize(username.replaceAll("[^A-Za-z]"," ")).replace("  "," ");
    }
}
