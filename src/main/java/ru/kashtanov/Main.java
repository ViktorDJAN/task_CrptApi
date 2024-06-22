package ru.kashtanov;

import ru.kashtanov.crpt.CrptApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello world!");
        CrptApi crptApi = new CrptApi(TimeUnit.SECONDS, 1);
        CrptApi.Document document = new CrptApi.Document();
        String signature = "It is mine signature ";
        System.out.println(document);
        crptApi.createDocument(document, signature);
    }
}