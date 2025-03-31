package arkain.dev.back.pdf.app;

import arkain.dev.back.pdf.app.dto.PdfResponseDto;
import arkain.dev.back.pdf.app.util.HtmlToMarkdown;
import arkain.dev.back.pdf.app.util.TextToHtml;
import arkain.dev.back.pdf.domain.Pdf;
import arkain.dev.back.pdf.domain.PdfCache;
import arkain.dev.back.pdf.repo.PdfCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final TextExtractService textExtractService;
    private final PdfCacheRepository pdfCacheRepository;

    public PdfResponseDto convert(Long id, MultipartFile file, String type) {
        return "html".equalsIgnoreCase(type)
                ? convertWithCache(id, file, PdfCache::getHtml)
                : convertWithCache(id, file, PdfCache::getMarkdown);
    }

    private PdfResponseDto convertWithCache(Long id, MultipartFile file, Function<PdfCache, String> extractor) {
        PdfCache cache = getCache(id).map(pdfCacheRepository::put)
                .orElseGet(() -> extractAndCache(file));

        return new PdfResponseDto(cache.getId(), extractor.apply(cache));
    }

    private Optional<PdfCache> getCache(Long id) {
        return id == null ? Optional.empty() : pdfCacheRepository.get(id);
    }

    private PdfCache extractAndCache(MultipartFile file) {
        String extractedText = textExtractService.extractTextFromPdf(file);
        String html = TextToHtml.convertTextToHtml(extractedText);
        String markdown = HtmlToMarkdown.convertHtmlToMarkdown(html);
        Pdf pdf = new Pdf(html, markdown);

        return pdfCacheRepository.put(pdf);
    }
}
