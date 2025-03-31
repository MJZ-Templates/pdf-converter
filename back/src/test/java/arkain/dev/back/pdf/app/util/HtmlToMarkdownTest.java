package arkain.dev.back.pdf.app.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlToMarkdownTest {

    @Test
    void givenHtml_whenConvertToMarkdown_thenMustReturnMarkdown() {
        String html = "<h1><b>Hello</b> World</h1>";
        String expectedMarkdown = "# **Hello** World";

        String markdown = HtmlToMarkdown.convertHtmlToMarkdown(html);
        assertThat(markdown.trim()).isEqualTo(expectedMarkdown.trim());
    }
}