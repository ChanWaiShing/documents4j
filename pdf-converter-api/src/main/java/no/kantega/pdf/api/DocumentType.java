package no.kantega.pdf.api;

public class DocumentType {

    public static final DocumentType MS_WORD = new DocumentType(Value.APPLICATION, Value.WORD_ANY);
    public static final DocumentType RTF = new DocumentType(Value.APPLICATION, Value.RTF);
    public static final DocumentType DOCX = new DocumentType(Value.APPLICATION, Value.DOCX);
    public static final DocumentType DOC = new DocumentType(Value.APPLICATION, Value.DOC);
    public static final DocumentType HTML = new DocumentType(Value.TEXT, Value.HTML);
    public static final DocumentType XML = new DocumentType(Value.APPLICATION, Value.XML);
    public static final DocumentType PDF = new DocumentType(Value.APPLICATION, Value.PDF);
    private final String type;
    private final String subtype;

    public DocumentType(String type, String subtype) {
        if (type == null || subtype == null) {
            throw new NullPointerException("Type elements must not be null");
        }
        this.type = type;
        this.subtype = subtype;
    }

    public DocumentType(String inputType) {
        int separator = inputType.indexOf('/');
        if (separator == -1 || inputType.length() == separator + 1) {
            throw new IllegalArgumentException("Not a legal */* document type: " + inputType);
        } else {
            type = inputType.substring(0, separator);
            subtype = inputType.substring(separator + 1);
        }
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        DocumentType documentType = (DocumentType) other;
        return subtype.equals(documentType.subtype) && type.equals(documentType.type);
    }

    @Override
    public int hashCode() {
        return 31 * type.hashCode() + subtype.hashCode();
    }

    @Override
    public String toString() {
        return type + "/" + subtype;
    }

    public static class Value {

        public static final String APPLICATION = "application";
        public static final String TEXT = "text";

        public static final String DOC = "msword";
        public static final String DOCX = "vnd.openxmlformats-officedocument.wordprocessingml.document";
        public static final String WORD_ANY = "vnd.no.kantega.pdf.any-msword";
        public static final String PDF = "pdf";
        public static final String RTF = "rtf";
        public static final String HTML = "html";
        public static final String XML = "xml";

        private Value() {
            throw new UnsupportedOperationException();
        }
    }
}
