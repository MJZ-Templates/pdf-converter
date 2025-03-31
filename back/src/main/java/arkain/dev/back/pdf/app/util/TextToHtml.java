package arkain.dev.back.pdf.app.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TextToHtml {

    public static String convertTextToHtml(String text) {
        return Arrays.stream(text.split("\\n\\s*\\n")) // split paragraphs by new line
                .map(para -> "<p>" + para.replaceAll("\\n", "<br>") + "</p>")
                .collect(Collectors.joining("\n"));
    }
}
