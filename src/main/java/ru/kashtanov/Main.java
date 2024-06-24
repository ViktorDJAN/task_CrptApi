package ru.kashtanov;

import com.google.gson.Gson;
import ru.kashtanov.crpt.CrptApi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        // CrptApi an instance creating
        CrptApi crptApi1 = new CrptApi(TimeUnit.SECONDS, 2,"https://ismp.crpt.ru/api/v3/lk/documents/create");

        //Getting a document form a file
        String stringForJson = "";
        try{
            byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/json_doc.json"));
            stringForJson = new String(bytes, StandardCharsets.UTF_8);
        }catch (IOException e){
            e.printStackTrace();
        }
        CrptApi.Document document1 = new Gson().fromJson(stringForJson, CrptApi.Document.class);

        //Simulation of usage our crptApi
        new Thread(()->{
            for (int i =0; i<5; i++){
                try {
                    crptApi1.createDocument(document1, "first");
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(()->{
            for (int i =5; i<8; i++){
                try {
                    crptApi1.createDocument(document1, "second");
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(()->{
            for (int i =8; i<13; i++){
                try {
                    crptApi1.createDocument(document1, "second");
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }
}