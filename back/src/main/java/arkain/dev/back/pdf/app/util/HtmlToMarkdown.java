package arkain.dev.back.pdf.app.util;

import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class HtmlToMarkdown {

    private static final FlexmarkHtmlConverter converter;

    static {
        MutableDataSet options = new MutableDataSet();
        options.set(FlexmarkHtmlConverter.SETEXT_HEADINGS, false);

        converter = FlexmarkHtmlConverter.builder(options).build();
    }

    public static String convertHtmlToMarkdown(String html) {
        if (html == null || html.trim().isEmpty()) {
            return "";
        }
        return converter.convert(html).trim();
    }
}
