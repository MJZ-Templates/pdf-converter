### 📄 PDF to Markdown/HTML Converter
A lightweight and fast PDF conversion tool built with **Spring Boot** (backend) and **React** (frontend).  
This project allows users to **upload PDF files**, **extract text**, and **download results as Markdown or HTML**, enabling efficient and structured content analysis.

### 🚀 Features
- One-click upload and conversion of PDF files
- Supports both **Markdown (.md)** and **HTML (.html)** formats
- Clean, intuitive UI for non-technical users
- Fast parsing using reliable PDF parsing libraries (e.g., PDFBox)
- Automatic section handling and line-break clean-up
- Download-ready output for immediate use in documentation or analysis
- Easily extendable to support additional formats or preprocessing

### 💻 Installation & Setup
1. **Check URL and Port**
    - Hover over the [Preview] → [Running URL and Port] button in the top menu
    - Ensure ports **80 (frontend)** and **8080 (backend)** are properly registered
    - If not, register the domain and port manually

2. **Install Dependencies**
   **Backend**
   ```bash
   cd /workspace/pdf-converter/back
   ./gradlew classes
   ```

3. **Ensure CORS**
   - Add your domain into allowedOrigins `pdf/back/.../config/CorsConfig.java`
   
