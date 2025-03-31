const SERVER_URL = "http://localhost:8080";

function pdfConverter() {
  return {
    pdfFile: null,
    id: null,
    convertedText: "",
    selectedOutputType: "md",

    handleFileUpload(event) {
      this.pdfFile = event.target.files[0];
      this.id = null; // Reset id when a new file is uploaded
    },
    uploadPdf() {
      if (!this.pdfFile) {
        alert("Please select a PDF file.");
        return;
      }

      const formData = new FormData();
      formData.append("file", this.pdfFile);

      const queryParams = new URLSearchParams();
      queryParams.append("type", this.selectedOutputType);

      // id = 0 is valid, so use != null instead of !== null
      if (this.id != null) {
        queryParams.append("id", this.id);
      }

      const endpoint = `/api/pdf?${queryParams.toString()}`;
      const requestUrl = `${SERVER_URL}${endpoint}`;

      axios
        .post(requestUrl, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((response) => {
          const data = response.data.data;
          console.log("Conversion successful:", data);
          this.id = data.id;
          this.convertedText = data.text;
        })
        .catch((error) => {
          console.error("Conversion failed:", error);
          alert("An error occurred during conversion.");
        });
    },

    downloadResult() {
      const blob = new Blob([this.convertedText], {
        type: "text/plain;charset=utf-8",
      });

      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = `converted.${this.selectedOutputType}`;
      a.click();
      URL.revokeObjectURL(url);
    },
  };
}
