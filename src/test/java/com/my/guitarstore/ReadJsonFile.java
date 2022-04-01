package com.my.guitarstore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.guitarstore.model.Item;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ReadJsonFile {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Item> getItemFromJson() throws IOException {
        InputStream inputStream = new FileInputStream(new File("src/test/java/resources/ItemList.json"));
        TypeReference<List<Item>> typeReference = new TypeReference<List<Item>>() {};
        return objectMapper.readValue(inputStream, typeReference);
    }

    /**
        refactored above method to return any POJO object by passing json path.
     */
    public static <T> List<T> getListFromJson(String path) throws IOException {
        InputStream inputStream = new FileInputStream(new File(path));
        TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {};
        return objectMapper.readValue(inputStream, typeReference);
    }


}
