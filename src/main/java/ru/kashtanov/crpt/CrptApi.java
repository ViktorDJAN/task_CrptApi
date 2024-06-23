package ru.kashtanov.crpt;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.*;

public class CrptApi {

    private final TimeUnit timeUnit;

    private final int requestLimit;
    private final String POST_URL = "https://ismp.crpt.ru/api/v3/lk/documents/create";
    private final HttpClient httpClient;
    // private final RateLimiter rateLimiter;
    private ExecutorService executorService;
    private Semaphore semaphore;



    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.timeUnit = timeUnit;
        this.requestLimit = requestLimit;
        this.executorService = Executors.newSingleThreadExecutor();
        this.httpClient = HttpClient.newHttpClient();
        this.semaphore = new Semaphore(requestLimit);
    }

    public void createDocument(Document document, String signature) throws IOException, InterruptedException {
        System.out.println("Waiting of incoming requests...");
        semaphore.acquire();
        System.out.println(semaphore.availablePermits());
        executorService.submit(() -> semaphore.release(), timeUnit);
        try {
            // You can add your logic here for further processing
            HttpRequest httpRequest = createRequest(document);
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

    }

    public HttpRequest createRequest(Document document) {
        return HttpRequest.newBuilder()
                .uri(URI.create(POST_URL))
                .header("Content-type", "application/JSON")
                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(document)))
                .build();
    }


    public static class Document {
        private Description description;
        private String docId;
        private String docStatus;
        private String docType;
        private boolean importRequest;
        private String ownerInn;
        private String participantInn;
        private String producerInn;
        private String localDate;
        private String productionType;
        private List<Product> products;
        private String regDate;
        private String regNumber;

        public Document() {
        }

        public Document(Description description, String docId, String docStatus, String docType, boolean importRequest, String ownerInn, String participantInn, String producerInn, String localDate, String productionType, List<Product> products, String regDate, String regNumber) {
            this.description = description;
            this.docId = docId;
            this.docStatus = docStatus;
            this.docType = docType;
            this.importRequest = importRequest;
            this.ownerInn = ownerInn;
            this.participantInn = participantInn;
            this.producerInn = producerInn;
            this.localDate = localDate;
            this.productionType = productionType;
            this.products = products;
            this.regDate = regDate;
            this.regNumber = regNumber;
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public String getDocId() {
            return docId;
        }

        public void setDocId(String docId) {
            this.docId = docId;
        }

        public String getDocStatus() {
            return docStatus;
        }

        public void setDocStatus(String docStatus) {
            this.docStatus = docStatus;
        }

        public String getDocType() {
            return docType;
        }

        public void setDocType(String docType) {
            this.docType = docType;
        }

        public boolean isImportRequest() {
            return importRequest;
        }

        public void setImportRequest(boolean importRequest) {
            this.importRequest = importRequest;
        }

        public String getOwnerInn() {
            return ownerInn;
        }

        public void setOwnerInn(String ownerInn) {
            this.ownerInn = ownerInn;
        }

        public String getParticipantInn() {
            return participantInn;
        }

        public void setParticipantInn(String participantInn) {
            this.participantInn = participantInn;
        }

        public String getProducerInn() {
            return producerInn;
        }

        public void setProducerInn(String producerInn) {
            this.producerInn = producerInn;
        }

        public String getLocalDate() {
            return localDate;
        }

        public void setLocalDate(String localDate) {
            this.localDate = localDate;
        }

        public String getProductionType() {
            return productionType;
        }

        public void setProductionType(String productionType) {
            this.productionType = productionType;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public String getRegNumber() {
            return regNumber;
        }

        public void setRegNumber(String regNumber) {
            this.regNumber = regNumber;
        }
    }

    public static class Product {
        private String certificateDocument;
        private String certificateDocument_date;
        private String certificateDocumentNumber;
        private String ownerInn;
        private String producerInn;
        private String productionDate;
        private String tnvedCode;
        private String uitCode;
        private String uituCode;

        public Product() {
        }

        public Product(String certificateDocument, String certificateDocument_date, String certificateDocumentNumber, String ownerInn, String producerInn, String productionDate, String tnvedCode, String uitCode, String uituCode) {
            this.certificateDocument = certificateDocument;
            this.certificateDocument_date = certificateDocument_date;
            this.certificateDocumentNumber = certificateDocumentNumber;
            this.ownerInn = ownerInn;
            this.producerInn = producerInn;
            this.productionDate = productionDate;
            this.tnvedCode = tnvedCode;
            this.uitCode = uitCode;
            this.uituCode = uituCode;
        }

        public String getCertificateDocument() {
            return certificateDocument;
        }

        public void setCertificateDocument(String certificateDocument) {
            this.certificateDocument = certificateDocument;
        }

        public String getCertificateDocument_date() {
            return certificateDocument_date;
        }

        public void setCertificateDocument_date(String certificateDocument_date) {
            this.certificateDocument_date = certificateDocument_date;
        }

        public String getCertificateDocumentNumber() {
            return certificateDocumentNumber;
        }

        public void setCertificateDocumentNumber(String certificateDocumentNumber) {
            this.certificateDocumentNumber = certificateDocumentNumber;
        }

        public String getOwnerInn() {
            return ownerInn;
        }

        public void setOwnerInn(String ownerInn) {
            this.ownerInn = ownerInn;
        }

        public String getProducerInn() {
            return producerInn;
        }

        public void setProducerInn(String producerInn) {
            this.producerInn = producerInn;
        }

        public String getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(String productionDate) {
            this.productionDate = productionDate;
        }

        public String getTnvedCode() {
            return tnvedCode;
        }

        public void setTnvedCode(String tnvedCode) {
            this.tnvedCode = tnvedCode;
        }

        public String getUitCode() {
            return uitCode;
        }

        public void setUitCode(String uitCode) {
            this.uitCode = uitCode;
        }

        public String getUituCode() {
            return uituCode;
        }

        public void setUituCode(String uituCode) {
            this.uituCode = uituCode;
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

        public void setParticipantInn(String participantInn) {
            this.participantInn = participantInn;
        }
    }

}
