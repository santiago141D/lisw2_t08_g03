package co.edu.unicauca.taller08;

import co.edu.unicauca.taller08.adapter.ExternalQuestionAdapter;
import co.edu.unicauca.taller08.adapter.ExternalQuestionService;
import co.edu.unicauca.taller08.adapter.QuestionProvider;
import co.edu.unicauca.taller08.decorator.BasicQuestion;
import co.edu.unicauca.taller08.decorator.CompetencyQuestion;
import co.edu.unicauca.taller08.decorator.FeedbackQuestion;
import co.edu.unicauca.taller08.decorator.HighDifficultyQuestion;
import co.edu.unicauca.taller08.decorator.MarkedForReviewQuestion;
import co.edu.unicauca.taller08.decorator.QuestionComponent;
import co.edu.unicauca.taller08.domain.Question;
import co.edu.unicauca.taller08.facade.QuestionBankFacade;
import co.edu.unicauca.taller08.templatemethod.MultipleChoiceEvaluator;
import co.edu.unicauca.taller08.templatemethod.SingleChoiceEvaluator;
import co.edu.unicauca.taller08.templatemethod.TrueFalseEvaluator;

import java.util.List;


public class ApplicationMain {

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("       BANCO DE PREGUNTAS SABER PRO DEMOSTRACIÓN TALLER 08 PATRONES GOF       ");
        System.out.println("================================================================================\n");

        demoStatePattern();
        demoTemplateMethodPattern();
        demoDecoratorPattern();
        demoAdapterPattern();
        demoFacadePattern();

        System.out.println("================================================================================");
        System.out.println("       DEMOSTRACIÓN FINALIZADA CON ÉXITO  TODOS LOS PATRONES OPERATIVOS        ");
        System.out.println("================================================================================");
    }

    private static void demoStatePattern() {
        System.out.println(">>> 1. PATRÓN STATE (Ciclo de Vida de una Pregunta)");
        Question q = new Question("Q-101", "¿Cuál es el propósito del patrón State?", "SELECCION_UNICA");
        System.out.println("  [Estado Inicial]        : " + q.getState().getName());
        System.out.println("  [¿Apta para examen?]    : " + q.canBeUsedInExam());

        q.submitForReview();
        System.out.println("  [submitForReview()]     -> Estado: " + q.getState().getName());

        q.assignReviewer();
        System.out.println("  [assignReviewer()]      -> Estado: " + q.getState().getName());

        q.approve();
        System.out.println("  [approve()]             -> Estado: " + q.getState().getName());
        System.out.println("  [¿Apta para examen?]    : " + q.canBeUsedInExam());

        q.publish();
        System.out.println("  [publish()]             -> Estado: " + q.getState().getName());
        System.out.println("  [¿Apta para examen?]    : " + q.canBeUsedInExam());

        q.archive();
        System.out.println("  [archive()]             -> Estado: " + q.getState().getName());

   
        System.out.print("  [Validación excepción]  : Intentando publicar pregunta archivada... ");
        try {
            q.publish();
            System.out.println("ERROR: No lanzó excepción.");
        } catch (IllegalStateException ex) {
            System.out.println("CORRECTO: " + ex.getMessage());
        }
        System.out.println();
    }

    private static void demoTemplateMethodPattern() {
        System.out.println(">>> 2. PATRÓN TEMPLATE METHOD (Evaluación de Preguntas)");


        Question q1 = new Question("Q-201", "¿Capital del Cauca?", "SELECCION_UNICA",
                List.of("Cali", "Popayán", "Pasto"), List.of("Popayán"));
        SingleChoiceEvaluator evalSingle = new SingleChoiceEvaluator();
        boolean res1 = evalSingle.evaluate(q1, List.of("Popayán"));
        boolean res1Fail = evalSingle.evaluate(q1, List.of("Cali"));
        System.out.println("  [Selección Única]       Respuesta 'Popayán' -> " + (res1 ? "CORRECTA (OK)" : "FALLÓ"));
        System.out.println("  [Selección Única]       Respuesta 'Cali'    -> " + (!res1Fail ? "INCORRECTA (OK)" : "FALLÓ"));

    
        Question q2 = new Question("Q-202", "¿Patrones estructurales GoF?", "SELECCION_MULTIPLE",
                List.of("Adapter", "Decorator", "State", "Observer"), List.of("Adapter", "Decorator"));
        MultipleChoiceEvaluator evalMulti = new MultipleChoiceEvaluator();
        boolean res2 = evalMulti.evaluate(q2, List.of("Adapter", "Decorator"));
        System.out.println("  [Selección Múltiple]    Respuestas [Adapter, Decorator] -> " + (res2 ? "CORRECTA (OK)" : "FALLÓ"));

      
        Question q3 = new Question("Q-203", "¿Decorator permite añadir responsabilidades dinámicamente?",
                "VERDADERO_FALSO", List.of("Verdadero", "Falso"), List.of("VERDADERO"));
        TrueFalseEvaluator evalTF = new TrueFalseEvaluator();
        boolean res3 = evalTF.evaluate(q3, List.of("VERDADERO"));
        System.out.println("  [Verdadero / Falso]     Respuesta 'VERDADERO'           -> " + (res3 ? "CORRECTA (OK)" : "FALLÓ"));
        System.out.println();
    }

    private static void demoDecoratorPattern() {
        System.out.println(">>> 3. PATRÓN DECORATOR (Agregar Características Dinámicas)");

        QuestionComponent q = new BasicQuestion("¿Qué es acoplamiento eferente?");
        System.out.println("  [Base]                  : " + q.getDescription() + " | Puntaje: " + q.getScore());

        q = new HighDifficultyQuestion(q);
        System.out.println("  [+ Alta Dificultad]     : " + q.getDescription() + " | Puntaje: " + q.getScore());

        q = new CompetencyQuestion(q, "Ingeniería de Software");
        System.out.println("  [+ Competencia]         : " + q.getDescription() + " | Puntaje: " + q.getScore());

        q = new FeedbackQuestion(q, "Mide el número de clases de las que depende una clase dada");
        System.out.println("  [+ Retroalimentación]   : " + q.getDescription() + " | Puntaje: " + q.getScore());

        q = new MarkedForReviewQuestion(q);
        System.out.println("  [+ Para Revisión]       : " + q.getDescription() + " | Puntaje: " + q.getScore());
        System.out.println();
    }

    private static void demoAdapterPattern() {
        System.out.println(">>> 4. PATRÓN ADAPTER (Importar Preguntas Externas)");

        ExternalQuestionService externalService = new ExternalQuestionService();
        System.out.println("  [Formato Externo JSON]  :\n" + externalService.getQuestion().trim());

        QuestionProvider adapter = new ExternalQuestionAdapter(externalService);
        Question internalQuestion = adapter.getQuestion();

        System.out.println("  [Adaptado al Dominio]   : ID=" + internalQuestion.getId()
                + " | Enunciado='" + internalQuestion.getStatement() + "'"
                + " | Estado=" + internalQuestion.getState().getName()
                + " | Respuestas=" + internalQuestion.getCorrectAnswers());
        System.out.println();
    }

    private static void demoFacadePattern() {
        System.out.println(">>> 5. PATRÓN FACADE (Interacción Simplificada con el Banco)");

        QuestionBankFacade facade = new QuestionBankFacade();

    
        Question q = new Question("FAC-01", "¿Cuál es el beneficio de una Fachada?", "SELECCION_UNICA",
                List.of("Simplificar interfaz", "Aumentar acoplamiento"), List.of("Simplificar interfaz"));
        facade.createQuestion(q);
        System.out.println("  [Facade.createQuestion] -> Pregunta guardada con id: " + q.getId());

     
        facade.publishQuestion(q);
        System.out.println("  [Facade.publishQuestion]-> Estado actual: " + q.getState().getName());

    
        boolean correcta = facade.evaluateAnswer(q, List.of("Simplificar interfaz"));
        System.out.println("  [Facade.evaluateAnswer] -> Evaluación: " + (correcta ? "CORRECTA" : "INCORRECTA"));

   
        ExternalQuestionService extService = new ExternalQuestionService("""
        {
            "statement": "¿Qué principio aplica Facade?",
            "answer": "Principio de Menor Conocimiento"
        }
        """);
        Question importada = facade.importExternalQuestion(new ExternalQuestionAdapter(extService));
        System.out.println("  [Facade.importExternal] -> Importada con éxito: " + importada.getStatement());

       
        System.out.println("  [Facade.getPublished]   -> Total publicadas en banco: " + facade.getPublishedQuestions().size());
        System.out.println();
    }
}
