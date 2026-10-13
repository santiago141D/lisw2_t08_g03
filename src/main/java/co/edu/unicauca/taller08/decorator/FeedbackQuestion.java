package co.edu.unicauca.taller08.decorator;

/**
 * Decorador concreto para preguntas con retroalimentación explicativa o formativa.
 */
public class FeedbackQuestion extends QuestionDecorator {

    private final String feedback;

    public FeedbackQuestion(QuestionComponent question, String feedback) {
        super(question);
        this.feedback = feedback != null ? feedback.trim() : "";
    }

    @Override
    public String getDescription() {
        return question.getDescription() + " [RETROALIMENTACIÓN: " + feedback + "]";
    }
}
