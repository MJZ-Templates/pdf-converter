package arkain.dev.back.pdf.app.util;

import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class HtmlToMarkdown {

    public static String convertHtmlToMarkdown(String html) {
        MutableDataSet options = new MutableDataSet();
        options.set(FlexmarkHtmlConverter.SETEXT_HEADINGS, false);
        FlexmarkHtmlConverter markdownConverter = FlexmarkHtmlConverter.builder(options)
                .build();
        return markdownConverter.convert(html);
    }
}
