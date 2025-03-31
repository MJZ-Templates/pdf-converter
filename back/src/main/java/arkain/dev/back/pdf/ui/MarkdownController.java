package arkain.dev.back.pdf.ui;

import arkain.dev.back.pdf.app.MarkdownService;
import arkain.dev.back.pdf.app.dto.FileDownloadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/download")
@RequiredArgsConstructor
public class MarkdownController {

    private final MarkdownService markdownService;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadMarkdown(
            @PathVariable("id") Long id,
            @RequestParam(value = "type", required = false, defaultValue = "markdown") String type
    ) {
        FileDownloadDto dto = markdownService.downloadFile(id, type);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_MARKDOWN);
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("converted." + dto.format())
                .build());

        return new ResponseEntity<>(dto.content(), headers, HttpStatus.OK);
    }
}
