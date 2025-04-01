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
                        // 이전 리스트 닫기
                        if (currentListType != null) {
                            bodyContent.append("</").append(currentListType).append(">\n");
                        }
                        // 새로운 리스트 열기
                        bodyContent.append("<").append(listType).append(">\n");
                        currentListType = listType;
                    }

                    // 리스트 아이템 추가
                    String item = trimmed.replaceFirst("^(\\*|-|•|\\d+\\.)\\s+", "");
                    bodyContent.append("  <li>").append(item).append("</li>\n");
                } else {
                    // 리스트 아닌 줄이 나오면 현재 리스트 닫기
                    if (currentListType != null) {
                        bodyContent.append("</").append(currentListType).append(">\n");
                        currentListType = null;
                    }

                    // 들여쓰기 처리
                    if (isIndented) {
                        bodyContent.append("<blockquote>").append(trimmed).append("</blockquote>\n");
                    } else {
                        bodyContent.append("<p>").append(trimmed).append("</p>\n");
                    }
                }
            }

            // 문단이 끝났는데도 리스트가 열려 있으면 닫아줌
            if (currentListType != null) {
                bodyContent.append("</").append(currentListType).append(">\n");
            }

            bodyContent.append("\n"); // 문단 간 여백
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
