package ru.kashtanov;

import com.google.gson.Gson;
import ru.kashtanov.crpt.CrptApi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        CrptApi crptApi1 = new CrptApi(TimeUnit.SECONDS, 1);
        String stringForJson = "";

        try{
            byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/json_doc.json"));
            stringForJson = new String(bytes, StandardCharsets.UTF_8);
        }catch (IOException e){
            e.printStackTrace();
        }

        CrptApi.Document document1 = new Gson().fromJson(stringForJson, CrptApi.Document.class);
        crptApi1.createDocument(document1,"Sd");


    }
}