package arkain.dev.back.pdf.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PdfTest {

    @Test
    void givenEqualPdfInstance_whenCompare_thenMustEquals() {
        Pdf pdf1 = new Pdf("html content", "markdown content");
        Pdf pdf2 = new Pdf("html content", "markdown content");

        assertThat(pdf1).isEqualTo(pdf2);
        assertThat(pdf1.hashCode()).isEqualTo(pdf2.hashCode());
    }

    @Test
    void givenParameters_whenCreatePdf_thenMustHaveSameValues() {
        String html = "html content";
        String markdown = "markdown content";

        Pdf pdf = new Pdf(html, markdown);

        assertThat(pdf.getHtml()).isEqualTo(html);
        assertThat(pdf.getMarkdown()).isEqualTo(markdown);
    }
}