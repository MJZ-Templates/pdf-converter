package arkain.dev.back.pdf.app.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class TextToHtmlTest {

    @Test
    void givenPlainText_whenConvertToHtml_thenReturnHtmlParagraphs() {
        // given
        String input = "First line\nSecond line\n\nThird paragraph";
        String expected = "<p>First line<br>Second line</p>\n<p>Third paragraph</p>";

        // when
        String html = convertTextToHtml(input);

        // then
        assertThat(html).isEqualTo(expected);
    }

    @Test
    void givenSingleParagraph_whenConvertToHtml_thenReturnOneParagraphWithBrTags() {
        // given
        String input = "This is a single paragraph\nwith a line break";
        String expected = "<p>This is a single paragraph<br>with a line break</p>";

        // when
        String html = convertTextToHtml(input);

        // then
        assertThat(html).isEqualTo(expected);
    }

    @Test
    void givenMultipleBlankLines_whenConvertToHtml_thenReturnSeparateParagraphs() {
        // given
        String input = "Paragraph one\n\n\nParagraph two";
        String expected = "<p>Paragraph one</p>\n<p>Paragraph two</p>";

        // when
        String html = convertTextToHtml(input);

        // then
        assertThat(html).isEqualTo(expected);
    }

    private static String convertTextToHtml(String text) {
        return Arrays.stream(text.split("\\n\\s*\\n"))
                .map(para -> "<p>" + para.replaceAll("\\n", "<br>") + "</p>")
                .collect(Collectors.joining("\n"));
    }
}
