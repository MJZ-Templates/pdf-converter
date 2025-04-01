package arkain.dev.back.pdf.app.util;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;

public class CustomTextStripper extends PDFTextStripper {

    private final StringBuilder result = new StringBuilder();
    private static final float INDENT_THRESHOLD = 100f;

    public CustomTextStripper() throws IOException {
        super();
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) {
        if (textPositions.isEmpty()) return;

        float indentX = textPositions.get(0).getXDirAdj(); // 들여쓰기 기준
        String trimmed = string.trim();

        // 불릿 항목일 경우
        if (trimmed.matches("^(•|\\*|-|\\d+\\.)\\s+.*")) {
            result.append("- ").append(trimmed.replaceFirst("^(•|\\*|-|\\d+\\.)\\s+", "")).append("\n");
        }
        // 들여쓰기된 경우
        else if (indentX > INDENT_THRESHOLD) {
            result.append("    ").append(trimmed).append("\n");
        }
        // 일반 문장
        else {
            result.append(trimmed).append("\n");
        }
    }

    public String getProcessedText() {
        return result.toString();
    }
}
