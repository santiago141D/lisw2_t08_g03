package co.edu.unicauca.taller08.adapter;

/**
 * Servicio externo (Adaptee) que proporciona preguntas en un formato incompatible (JSON).
 */
public class ExternalQuestionService {

    private String rawData;

    public ExternalQuestionService() {
        this.rawData = """
        {
            "statement": "¿Cuál es la capital de Colombia?",
            "answer": "Bogotá"
        }
        """;
    }

    public ExternalQuestionService(String rawData) {
        this.rawData = rawData;
    }

    public String getQuestion() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }
}
