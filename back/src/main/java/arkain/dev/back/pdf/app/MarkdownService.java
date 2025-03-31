package arkain.dev.back.pdf.app;

import arkain.dev.back.pdf.app.dto.FileDownloadDto;
import arkain.dev.back.pdf.domain.PdfCache;
import arkain.dev.back.pdf.repo.PdfCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarkdownService {

    private static final String MARKDOWN_FORMAT = "md";
    private static final String HTML_FORMAT = "html";

    private final PdfCacheRepository pdfCacheRepository;

    private PdfCache getPdfCache(Long id) {
        return pdfCacheRepository.get(id)
                .orElseThrow(() -> new RuntimeException("pdf cache not found"));
    }

    public FileDownloadDto downloadFile(Long id, String type) {
        switch (type) {
            case HTML_FORMAT -> {
                return downloadHtml(id);
            }
            case MARKDOWN_FORMAT -> {
                return downloadMarkdown(id);
            }
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    private FileDownloadDto downloadMarkdown(Long id) {
        PdfCache pdfCache = getPdfCache(id);
        return new FileDownloadDto(pdfCache.getMarkdown().getBytes(), MARKDOWN_FORMAT);
    }

    private FileDownloadDto downloadHtml(Long id) {
        PdfCache pdfCache = getPdfCache(id);
        return new FileDownloadDto(pdfCache.getHtml().getBytes(), HTML_FORMAT);
    }
}
