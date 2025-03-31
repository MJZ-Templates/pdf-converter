package arkain.dev.back.pdf.ui;

import arkain.dev.back.common.dto.ResponseDto;
import arkain.dev.back.pdf.app.PdfService;
import arkain.dev.back.pdf.app.dto.PdfResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pdf")
public class PdfController {

    private final PdfService pdfService;

    @PostMapping
    public ResponseDto<PdfResponseDto> convert(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "type", defaultValue = "md") String type
    ) {
        return ResponseDto.ok(pdfService.convert(id, file, type));
    }
}
