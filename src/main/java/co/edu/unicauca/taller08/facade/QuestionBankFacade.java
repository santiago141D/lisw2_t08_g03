package co.edu.unicauca.taller08.facade;

import co.edu.unicauca.taller08.adapter.QuestionProvider;
import co.edu.unicauca.taller08.decorator.BasicQuestion;
import co.edu.unicauca.taller08.decorator.QuestionComponent;
import co.edu.unicauca.taller08.domain.Question;
import co.edu.unicauca.taller08.state.PublishedState;
import co.edu.unicauca.taller08.templatemethod.MultipleChoiceEvaluator;
import co.edu.unicauca.taller08.templatemethod.QuestionEvaluator;
import co.edu.unicauca.taller08.templatemethod.SingleChoiceEvaluator;
import co.edu.unicauca.taller08.templatemethod.TrueFalseEvaluator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Fachada principal del Banco de Preguntas Saber Pro (Patrón Facade).
 * Oculta la complejidad de interactuar directamente con los múltiples subsistemas:
 * QuestionService, QuestionValidator, QuestionPublisher, QuestionImporter y QuestionEvaluator.
 */
public class QuestionBankFacade {

    private final QuestionService questionService;
    private final QuestionValidator validator;
    private final QuestionPublisher publisher;
    private final QuestionImporter importer;
    private final QuestionRepository repository;

    private final SingleChoiceEvaluator singleChoiceEvaluator;
    private final MultipleChoiceEvaluator multipleChoiceEvaluator;
    private final TrueFalseEvaluator trueFalseEvaluator;

    /**
     * Constructor por defecto que inicializa e interconecta todos los subsistemas.
     */
    public QuestionBankFacade() {
        this.repository = new QuestionRepository();
        this.questionService = new QuestionService(this.repository);
        this.validator = new QuestionValidator();
        this.publisher = new QuestionPublisher();
        this.importer = new QuestionImporter();

        this.singleChoiceEvaluator = new SingleChoiceEvaluator();
        this.multipleChoiceEvaluator = new MultipleChoiceEvaluator();
        this.trueFalseEvaluator = new TrueFalseEvaluator();
    }

    /**
     * Constructor con inyección de subsistemas.
     */
    public QuestionBankFacade(
            QuestionService questionService,
            QuestionValidator validator,
            QuestionPublisher publisher,
            QuestionImporter importer,
            QuestionRepository repository) {
        this.questionService = Objects.requireNonNull(questionService);
        this.validator = Objects.requireNonNull(validator);
        this.publisher = Objects.requireNonNull(publisher);
        this.importer = Objects.requireNonNull(importer);
        this.repository = repository;

        this.singleChoiceEvaluator = new SingleChoiceEvaluator();
        this.multipleChoiceEvaluator = new MultipleChoiceEvaluator();
        this.trueFalseEvaluator = new TrueFalseEvaluator();
    }

    /**
     * Crea y valida una nueva pregunta en el banco.
     */
    public void createQuestion(Question question) {
        validator.validate(question);
        questionService.save(question);
    }

    /**
     * Publica una pregunta: valida, aprueba y publica oficialmente.
     */
    public void publishQuestion(Question question) {
        validator.validate(question);
        questionService.approve(question);
        publisher.publish(question);
        questionService.save(question);
    }

    /**
     * Consulta una pregunta por su identificador único.
     */
    public Optional<Question> findQuestionById(String id) {
        return questionService.findById(id);
    }

    /**
     * Retorna todas las preguntas registradas en el banco.
     */
    public List<Question> getAllQuestions() {
        return questionService.findAll();
    }

    /**
     * Retorna únicamente las preguntas que se encuentran publicadas y listas para exámenes.
     */
    public List<Question> getPublishedQuestions() {
        return questionService.findAll().stream()
                .filter(Question::canBeUsedInExam)
                .collect(Collectors.toList());
    }

    /**
     * Evalúa la respuesta de un estudiante utilizando el evaluador adecuado
     * según el tipo de pregunta (Template Method).
     */
    public boolean evaluateAnswer(Question question, List<String> studentAnswers) {
        if (question == null) {
            throw new IllegalArgumentException("La pregunta no puede ser nula.");
        }
        QuestionEvaluator evaluator = resolveEvaluator(question.getType());
        return evaluator.evaluate(question, studentAnswers);
    }

    /**
     * Evalúa una respuesta consultando la pregunta por ID.
     */
    public boolean evaluateAnswer(String questionId, List<String> studentAnswers) {
        Question question = findQuestionById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada con id: " + questionId));
        return evaluateAnswer(question, studentAnswers);
    }

    /**
     * Importa una pregunta desde un proveedor externo (Adapter) y la guarda en el banco.
     */
    public Question importExternalQuestion(QuestionProvider provider) {
        Question question = importer.importQuestion(provider);
        createQuestion(question);
        return question;
    }

    /**
     * Crea un componente decorable básico a partir de una pregunta del banco.
     */
    public QuestionComponent asComponent(Question question) {
        return new BasicQuestion(question);
    }

    // ==========================================
    // Resolución de Subsistemas Auxiliares
    // ==========================================

    private QuestionEvaluator resolveEvaluator(String type) {
        if (type == null) {
            return singleChoiceEvaluator;
        }
        String normalized = type.trim().toUpperCase();
        if (normalized.contains("MULTIPLE") && !normalized.contains("UNICA")) {
            return multipleChoiceEvaluator;
        }
        if (normalized.contains("VERDADERO") || normalized.contains("FALSO") || normalized.equals("TF")) {
            return trueFalseEvaluator;
        }
        return singleChoiceEvaluator;
    }

    public QuestionService getQuestionService() {
        return questionService;
    }

    public QuestionValidator getValidator() {
        return validator;
    }

    public QuestionPublisher getPublisher() {
        return publisher;
    }

    public QuestionImporter getImporter() {
        return importer;
    }

    public QuestionRepository getRepository() {
        return repository;
    }
}
