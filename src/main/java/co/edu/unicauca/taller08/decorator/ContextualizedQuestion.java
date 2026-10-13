package co.edu.unicauca.taller08.decorator;

/**
 * Decorador concreto para preguntas asociadas a un contexto, caso de estudio o situación problemática.
 */
public class ContextualizedQuestion extends QuestionDecorator {

    private final String context;

    public ContextualizedQuestion(QuestionComponent question, String context) {
        super(question);
        this.context = context != null ? context.trim() : "";
    }

    @Override
    public String getDescription() {
        return question.getDescription() + " [CONTEXTO: " + context + "]";
    }
}
