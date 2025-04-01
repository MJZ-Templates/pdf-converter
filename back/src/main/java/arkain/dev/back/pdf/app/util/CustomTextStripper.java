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

        float indentX = textPositions.get(0).getXDirAdj();
        String trimmed = string.trim();

        // if bullet point, asterisk, hyphen, or numbered list
        if (trimmed.matches("^(•|\\*|-|\\d+\\.)\\s+.*")) {
            result.append("- ").append(trimmed.replaceFirst("^(•|\\*|-|\\d+\\.)\\s+", "")).append("\n");
        }

        // if indent is greater than threshold
        else if (indentX > INDENT_THRESHOLD) {
            result.append("    ").append(trimmed).append("\n");
        }

        // append normal text
        else {
            result.append(trimmed).append("\n");
        }
    }

    public String getProcessedText() {
        return result.toString();
    }
}
