/**
 * The main module of the joker calculator.
 */
module cz.matfyz.rudad.joker {
    requires com.google.common;
    requires info.picocli;
    exports cz.matfyz.rudad.joker.calculator;
    exports cz.matfyz.rudad.joker.evaluator;
    exports cz.matfyz.rudad.joker.card;

    opens cz.matfyz.rudad.joker.calculator to info.picocli;
}