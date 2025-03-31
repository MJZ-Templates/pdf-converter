package arkain.dev.back.pdf.domain;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class PdfCacheTest {

    @Test
    void whenCreateHundredTimeConcurrently_thenLastElementIdMustBeHundred() throws InterruptedException {
        int threadCount = 1000;
        Pdf pdf = new Pdf("html content", "markdown content");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.execute(() -> {
                new PdfCache(pdf);
                latch.countDown();
            });
        }

        latch.await();
        executor.close();
        PdfCache lastPdfCache = new PdfCache(pdf);
        long lastId = lastPdfCache.getId();
        assertThat(lastId).isEqualTo(threadCount);
    }

    @Test
    void givenPdf_thenCreateNewCache() {
        Pdf pdf = new Pdf("html content", "markdown content");
        PdfCache pdfCache = new PdfCache(pdf);
        assertThat(pdfCache.getPdf()).isEqualTo(pdf);
        assertThat(pdfCache.getHtml()).isEqualTo(pdf.getHtml());
        assertThat(pdfCache.getMarkdown()).isEqualTo(pdf.getMarkdown());
    }
}