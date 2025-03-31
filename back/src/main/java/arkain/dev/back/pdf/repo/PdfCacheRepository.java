package arkain.dev.back.pdf.repo;

import arkain.dev.back.pdf.domain.Pdf;
import arkain.dev.back.pdf.domain.PdfCache;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PdfCacheRepository {

    private static final long REFRESH_INTERVAL = 1000L * 60 * 5; // 5 minutes

    private final Map<Long, PdfCache> cache = new ConcurrentHashMap<>();
    private final PriorityQueue<PdfCache> expireQueue = new PriorityQueue<>(Comparator.comparingLong(PdfCache::getExpireAt));

    public PdfCache put(Pdf pdf) {
        PdfCache pdfCache = new PdfCache(pdf);
        cache.put(pdfCache.getId(), pdfCache);

        return pdfCache;
    }

    public PdfCache put(PdfCache cache) {
        PdfCache refreshedCache = new PdfCache(cache.getId(), cache.getPdf());
        this.cache.put(cache.getId(), refreshedCache);

        return refreshedCache;
    }

    public Optional<PdfCache> get(Long id) {
        PdfCache pdfCache = cache.getOrDefault(id, null);
        if (pdfCache == null) {
            return Optional.empty();
        }
        return Optional.of(pdfCache);
    }

    @Scheduled(fixedRate = REFRESH_INTERVAL)
    public void cleanUp() {
        while (!expireQueue.isEmpty()) {
            PdfCache head = expireQueue.peek();
            if (!head.isExpired()) {
                break;
            }
            expireQueue.poll();
            cache.remove(head.getId());
        }
    }
}
