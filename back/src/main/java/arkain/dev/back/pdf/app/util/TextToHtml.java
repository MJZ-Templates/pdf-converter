package arkain.dev.back.pdf.app.util;

public class TextToHtml {

    public static String convertTextToHtml(String text) {
        String escapedText = escapeHtml(text);
        String[] paragraphs = escapedText.split("\\n\\s*\\n");

        StringBuilder bodyContent = new StringBuilder();

        for (String para : paragraphs) {
            String[] lines = para.split("\\n");
            String currentListType = null; // "ul" or "ol"

            for (String line : lines) {
                String trimmed = line.stripLeading();

                boolean isIndented = line.startsWith("  ") || line.startsWith("\t");
                boolean isUnorderedBullet = trimmed.matches("^(\\*|-|•)\\s+.+");
                boolean isOrderedBullet = trimmed.matches("^\\d+\\.\\s+.+");

                String listType = null;
                if (isUnorderedBullet) listType = "ul";
                else if (isOrderedBullet) listType = "ol";

                if (listType != null) {
                    if (!listType.equals(currentListType)) {
                        // close the previous list if it's open
                        if (currentListType != null) {
                            bodyContent.append("</").append(currentListType).append(">\n");
                        }
                        // open the new list
                        bodyContent.append("<").append(listType).append(">\n");
                        currentListType = listType;
                    }

                    // add list item
                    String item = trimmed.replaceFirst("^(\\*|-|•|\\d+\\.)\\s+", "");
                    bodyContent.append("  <li>").append(item).append("</li>\n");
                } else {
                    // close the current list if it's open
                    if (currentListType != null) {
                        bodyContent.append("</").append(currentListType).append(">\n");
                        currentListType = null;
                    }

                    // process indent
                    if (isIndented) {
                        bodyContent.append("<blockquote>").append(trimmed).append("</blockquote>\n");
                    } else {
                        bodyContent.append("<p>").append(trimmed).append("</p>\n");
                    }
                }
            }

            // close the current list if it's open
            if (currentListType != null) {
                bodyContent.append("</").append(currentListType).append(">\n");
            }

            bodyContent.append("\n"); // add a blank line between paragraphs
        }

        return """
                <!DOCTYPE html>
                <html>
                  <head>
                    <meta charset="UTF-8">
                    <title>Converted PDF</title>
                    <style>
                      blockquote { margin-left: 1.5em; color: #555; }
                      ul, ol { padding-left: 1.5em; margin-bottom: 1em; }
                    </style>
                  </head>
                  <body>
                    %s
                  </body>
                </html>
                """.formatted(bodyContent.toString().strip());
    }

    private static String escapeHtml(String text) {
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
