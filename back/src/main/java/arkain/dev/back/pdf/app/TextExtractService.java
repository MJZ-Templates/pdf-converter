package arkain.dev.back.pdf.app;

import arkain.dev.back.common.exception.CommonException;
import arkain.dev.back.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextExtractService {

    public String extractTextFromPdf(MultipartFile file) {
        File tempFile = null;
        try {
            tempFile = convertMultipartFileToFile(file);
            return extractText(tempFile);
        } catch (IOException e) {
            log.info("Failed to extract text from PDF file: {}", e.getMessage());
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        } finally {
            deleteTempFile(tempFile);
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File tempFile = File.createTempFile("uploaded-", ".pdf");
        try (InputStream inputStream = multipartFile.getInputStream();
             OutputStream outputStream = new FileOutputStream(tempFile)) {
            inputStream.transferTo(outputStream);
        }
        return tempFile;
    }

    private String extractText(File file) throws IOException {
        try (RandomAccessReadBufferedFile randomAccessFile = new RandomAccessReadBufferedFile(file);
             PDDocument document = Loader.loadPDF(randomAccessFile)) {
            PDFTextStripper textStripper = new PDFTextStripper();
            return textStripper.getText(document);
        }
    }

    private void deleteTempFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        if (!file.delete()) {
            log.error("Failed to delete temporary file: {}", file.getAbsolutePath());
        }
    }
}
