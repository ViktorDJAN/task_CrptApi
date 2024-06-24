package ru.kashtanov.crpt;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class CrptApi {

    private final TimeUnit timeUnit;
    private final HttpClient httpClient;
    private final String apiUrl;

    private final int requestLimit;

    private final Queue<Document> q = new LinkedList<>();

    private int count = 0;

    public CrptApi(TimeUnit timeUnit, int requestLimit, String apiUrl1) {
        this.timeUnit = timeUnit;
        this.requestLimit = requestLimit;
        this.apiUrl = apiUrl1;
        this.httpClient = HttpClient.newHttpClient();
    }

    public synchronized void createDocument(Document document, String signature) throws IOException, InterruptedException {
        count++;
        if (mustWait()) {
            q.add(document);
            try {
                System.out.println("  Waiting... " + Thread.currentThread().getName());
                timeUnit.timedJoin(Thread.currentThread(),1);
                System.out.println("  Go... " + Thread.currentThread().getName());
                count = 0;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (q.isEmpty()) {
            HttpRequest httpRequest = createRequest(document);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
            print(document);
        } else {
            while (!q.isEmpty()) {
                Document currentDoc = q.poll();
                createDocument(currentDoc, signature);
            }
        }
    }

    private boolean mustWait() {
        return count > requestLimit;
    }

    public HttpRequest createRequest(Document document) {
        return HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-type", "application/JSON")
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(document)))
                .build();
    }


    private void print(Document document) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        System.out.println("    Document " + document + " created at " + formatter.format(date) + " by " + Thread.currentThread().getName());
    }


    public static class Document {
        private Description description;
        private String doc_id;
        private String doc_status;
        private String doc_type;
        private boolean importRequest;
        private String owner_inn;
        private String participant_inn;
        private String producer_inn;
        private String production_date;
        private String production_type;
        private List<Product> products;
        private String reg_date;
        private String reg_number;

        public Document() {
        }

        public Document(Description description, String doc_id, String doc_status, String doc_type, boolean importRequest, String owner_inn, String participant_inn, String producer_inn, String production_date, String production_type, List<Product> products, String reg_date, String reg_number) {
            this.description = description;
            this.doc_id = doc_id;
            this.doc_status = doc_status;
            this.doc_type = doc_type;
            this.importRequest = importRequest;
            this.owner_inn = owner_inn;
            this.participant_inn = participant_inn;
            this.producer_inn = producer_inn;
            this.production_date = production_date;
            this.production_type = production_type;
            this.products = products;
            this.reg_date = reg_date;
            this.reg_number = reg_number;
        }

        public Description getDescription() {
            return description;
        }

        public String getDoc_id() {
            return doc_id;
        }

        public String getDoc_status() {
            return doc_status;
        }

        public String getDoc_type() {
            return doc_type;
        }

        public boolean isImportRequest() {
            return importRequest;
        }

        public String getOwner_inn() {
            return owner_inn;
        }

        public String getParticipant_inn() {
            return participant_inn;
        }

        public String getProducer_inn() {
            return producer_inn;
        }

        public String getProduction_date() {
            return production_date;
        }

        public String getProduction_type() {
            return production_type;
        }

        public List<Product> getProducts() {
            return products;
        }

        public String getReg_date() {
            return reg_date;
        }

        public String getReg_number() {
            return reg_number;
        }

        @Override
        public String toString() {
            return "Document{" +
                    "description=" + description +
                    ", doc_id='" + doc_id + '\'' +
                    ", doc_status='" + doc_status + '\'' +
                    ", doc_type='" + doc_type + '\'' +
                    ", importRequest=" + importRequest +
                    ", owner_inn='" + owner_inn + '\'' +
                    ", participant_inn='" + participant_inn + '\'' +
                    ", producer_inn='" + producer_inn + '\'' +
                    ", production_date='" + production_date + '\'' +
                    ", production_type='" + production_type + '\'' +
                    ", products=" + products +
                    ", reg_date='" + reg_date + '\'' +
                    ", reg_number='" + reg_number + '\'' +
                    '}';
        }
    }

    public static class Product {
        private String certificate_document;
        private String certificate_document_date;
        private String certificate_document_number;
        private String owner_inn;
        private String producer_inn;
        private String production_date;
        private String tnved_code;
        private String uit_code;
        private String uitu_code;

        public Product() {
        }

        public Product(String certificate_document, String certificate_document_date, String certificate_document_number, String owner_inn, String producer_inn, String production_date, String tnved_code, String uit_code, String uitu_code) {
            this.certificate_document = certificate_document;
            this.certificate_document_date = certificate_document_date;
            this.certificate_document_number = certificate_document_number;
            this.owner_inn = owner_inn;
            this.producer_inn = producer_inn;
            this.production_date = production_date;
            this.tnved_code = tnved_code;
            this.uit_code = uit_code;
            this.uitu_code = uitu_code;
        }

        public String getCertificate_document() {
            return certificate_document;
        }

        public String getCertificate_document_date() {
            return certificate_document_date;
        }

        public String getCertificate_document_number() {
            return certificate_document_number;
        }

        public String getOwner_inn() {
            return owner_inn;
        }

        public String getProducer_inn() {
            return producer_inn;
        }

        public String getProduction_date() {
            return production_date;
        }

        public String getTnved_code() {
            return tnved_code;
        }

        public String getUit_code() {
            return uit_code;
        }

        public String getUitu_code() {
            return uitu_code;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "certificate_document='" + certificate_document + '\'' +
                    ", certificate_document_date='" + certificate_document_date + '\'' +
                    ", certificate_document_number='" + certificate_document_number + '\'' +
                    ", owner_inn='" + owner_inn + '\'' +
                    ", producer_inn='" + producer_inn + '\'' +
                    ", production_date='" + production_date + '\'' +
                    ", tnved_code='" + tnved_code + '\'' +
                    ", uit_code='" + uit_code + '\'' +
                    ", uitu_code='" + uitu_code + '\'' +
                    '}';
        }
    }

    public static class Description {
        private String participantInn;

        public Description() {
        }

        public Description(String participantInn) {
            this.participantInn = participantInn;
        }

        public String getParticipantInn() {
            return participantInn;
        }

        @Override
        public String toString() {
            return "Description{" +
                    "participantInn='" + participantInn + '\'' +
                    '}';
        }
    }

}
