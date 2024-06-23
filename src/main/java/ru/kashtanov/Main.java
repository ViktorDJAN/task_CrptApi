package ru.kashtanov;

import ru.kashtanov.crpt.CrptApi;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        CrptApi crptApi1 = new CrptApi(TimeUnit.SECONDS, 1);
        CrptApi.Document document = new CrptApi.Document();
        crptApi1.createDocument(document,"Sd");


    }
}