package arkain.dev.back.pdf.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@EqualsAndHashCode
public class PdfCache {

    private static final AtomicLong PDF_CACHE_ID = new AtomicLong(0);
    private final long ttl = 5 * 60 * 1000; // 5 minutes

    private final Long id;
    private final Pdf pdf;
    private final long expireAt;

    public PdfCache(Pdf pdf) {
        this.id = PDF_CACHE_ID.getAndIncrement();
        this.pdf = pdf;
        this.expireAt = System.currentTimeMillis() + ttl;
    }

    public PdfCache(Long id, Pdf pdf) {
        this.id = id;
        this.pdf = pdf;
        this.expireAt = System.currentTimeMillis() + ttl;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expireAt;
    }

    public String getHtml() {
        return pdf.getHtml();
    }

    public String getMarkdown() {
        return pdf.getMarkdown();
    }
}
