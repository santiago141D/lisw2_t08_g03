package co.edu.unicauca.taller08.adapter;

import co.edu.unicauca.taller08.domain.Question;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Adaptador que transforma los datos devueltos por {@link ExternalQuestionService}
 * en una entidad de dominio {@link Question} utilizable por el sistema.
 */
public class ExternalQuestionAdapter implements QuestionProvider {

    private final ExternalQuestionService service;

    public ExternalQuestionAdapter(ExternalQuestionService service) {
        this.service = Objects.requireNonNull(service, "El servicio externo no puede ser nulo.");
    }

    @Override
    public Question getQuestion() {
        String data = service.getQuestion();
        if (data == null || data.isBlank()) {
            throw new IllegalStateException("El servicio externo retornó una respuesta vacía.");
        }

        String statement = extractField(data, "statement");
        String answer = extractField(data, "answer");

        if (statement == null || statement.isBlank()) {
            statement = "Pregunta importada sin enunciado";
        }

        Question question = new Question();
        question.setId("EXT-" + Math.abs(statement.hashCode()));
        question.setStatement(statement);
        question.setType("SELECCION_UNICA");

        if (answer != null && !answer.isBlank()) {
            question.addOption(answer);
            question.addCorrectAnswer(answer);
        }

        return question;
    }

    /**
     * Extrae el valor de una clave textual simple en formato JSON o pseudo-JSON.
     */
    private String extractField(String json, String key) {
        // Busca patrones como "key": "valor" o "key" : "valor"
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }
}
