package co.edu.unicauca.taller08.domain;

import co.edu.unicauca.taller08.state.DraftState;
import co.edu.unicauca.taller08.state.QuestionState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidad de dominio que representa una pregunta en el Banco de Preguntas Saber Pro.
 * Implementa el patrón State delegando el ciclo de vida en {@link QuestionState}.
 */
public class Question {

    private String id;
    private String statement;
    private String type;
    private List<String> options;
    private List<String> correctAnswers;
    private QuestionState state;

    /**
     * Constructor por defecto requerido por la especificación de la guía.
     * Inicializa el estado en DraftState.
     */
    public Question() {
        this.options = new ArrayList<>();
        this.correctAnswers = new ArrayList<>();
        this.state = new DraftState();
    }

    /**
     * Constructor con atributos principales.
     */
    public Question(String id, String statement, String type) {
        this();
        this.id = id;
        this.statement = statement;
        this.type = type;
    }

    /**
     * Constructor completo.
     */
    public Question(String id, String statement, String type, List<String> options, List<String> correctAnswers) {
        this();
        this.id = id;
        this.statement = statement;
        this.type = type;
        if (options != null) {
            this.options.addAll(options);
        }
        if (correctAnswers != null) {
            this.correctAnswers.addAll(correctAnswers);
        }
    }

    // ==========================================
    // Métodos del Patrón State
    // ==========================================

    public QuestionState getState() {
        return state;
    }

    public void setState(QuestionState state) {
        this.state = Objects.requireNonNull(state, "El estado no puede ser nulo.");
    }

    /**
     * Envía la pregunta a revisión.
     */
    public void submitForReview() {
        state.submitForReview(this);
    }

    /**
     * Asigna un revisor y pasa a evaluación.
     */
    public void assignReviewer() {
        state.assignReviewer(this);
    }

    /**
     * Aprueba la pregunta.
     */
    public void approve() {
        state.approve(this);
    }

    /**
     * Rechaza la pregunta.
     */
    public void reject() {
        state.reject(this);
    }

    /**
     * Retorna la pregunta rechazada a estado borrador para ajustes.
     */
    public void returnToDraft() {
        state.returnToDraft(this);
    }

    /**
     * Publica la pregunta para su uso en exámenes.
     */
    public void publish() {
        state.publish(this);
    }

    /**
     * Archiva la pregunta (pasa a histórico/inactiva).
     */
    public void archive() {
        state.archive(this);
    }

    /**
     * Verifica si la pregunta puede ser utilizada activamente en una prueba o simulacro.
     */
    public boolean canBeUsedInExam() {
        return state.canBeUsedInExam();
    }

    // ==========================================
    // Getters y Setters
    // ==========================================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getOptions() {
        return Collections.unmodifiableList(options);
    }

    public void setOptions(List<String> options) {
        this.options = options != null ? new ArrayList<>(options) : new ArrayList<>();
    }

    public void addOption(String option) {
        if (option != null && !option.isBlank()) {
            this.options.add(option.trim());
        }
    }

    public List<String> getCorrectAnswers() {
        return Collections.unmodifiableList(correctAnswers);
    }

    public void setCorrectAnswers(List<String> correctAnswers) {
        this.correctAnswers = correctAnswers != null ? new ArrayList<>(correctAnswers) : new ArrayList<>();
    }

    public void addCorrectAnswer(String correctAnswer) {
        if (correctAnswer != null && !correctAnswer.isBlank()) {
            this.correctAnswers.add(correctAnswer.trim());
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", statement='" + statement + '\'' +
                ", type='" + type + '\'' +
                ", state=" + (state != null ? state.getName() : "null") +
                '}';
    }
}
