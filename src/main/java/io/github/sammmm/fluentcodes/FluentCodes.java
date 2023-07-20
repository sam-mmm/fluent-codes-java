package io.github.sammmm.fluentcodes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is builder class for fluent codes
 * <p>
 * Default joiner is '-'
 * Default length of words are 6
 *
 * <p>
 * Examples:
 * 1.
 * FluentCodes.generateCodeWithFourWords()
 * 'fluffy-vacuum-misuse-deadly'
 * <p>
 * 2.
 * FluentCodes.generateCodeWithThreeWordsAndSixDigits()
 * 'calmer-taints-fourty-887709'
 * <p>
 * 3.
 * new FluentCodes()
 * .withJoiner("_")
 * .withMinLength(4)
 * .withMaxLength(10)
 * .adjective()
 * .verb()
 * .noun()
 * .sixDigits()
 * .toString()
 * 'acadmic_provided_writings_741319'
 * <p>
 * <p>
 * Definitions of terms adjective, adposition, adverb, auxiliary, coordinatingConjunction, determiner, interjection,
 * noun, numeral, particle, pronoun, properNoun, punctuation, subordinatingConjunction, symbol, verb
 * are from https://universaldependencies.org/u/pos/
 *
 * @author Sam-mmm
 */
public class FluentCodes {
    private final Random random = new Random();
    private final List<String> words = new ArrayList<>();
    Connection connection = null;
    private String joiner = "-";
    private int minLength = 6;
    private int maxLength = 6;

    /**
     * Generates code with four words
     * Ex: 'fluffy-vacuum-misuse-deadly'
     *
     * @return generated code
     */
    public static String generateCodeWithFourWords() {
        return new FluentCodes().adjective().verb().noun().adjective().toString();
    }

    /**
     * Generates code with three words and six digits
     * Ex: 'calmer-taints-fourty-887709'
     *
     * @return generated code
     */
    public static String generateCodeWithThreeWordsAndSixDigits() {
        return new FluentCodes().adjective().verb().noun().sixDigits().toString();
    }

    private void connectionCheck() {
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(10)) {
                connection = DriverManager.getConnection("jdbc:sqlite::resource:words_release.db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public FluentCodes adjective() {
        selectWord("adj");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes adposition() {
        selectWord("adp");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes adverb() {
        selectWord("adv");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes auxiliary() {
        selectWord("aux");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes coordinatingConjunction() {
        selectWord("cconj");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes determiner() {
        selectWord("det");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes interjection() {
        selectWord("intj");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes noun() {
        selectWord("noun");
        return this;
    }

//        public FluentCodes numeral(){
//        selectWord("num");
//        return this;
//    }

    /**
     * @return
     */
    public FluentCodes particle() {
        selectWord("part");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes pronoun() {
        selectWord("pron");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes properNoun() {
        selectWord("propn");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes punctuation() {
        selectWord("punct");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes subordinatingConjunction() {
        selectWord("sconj");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes symbol() {
        selectWord("sym");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes verb() {
        selectWord("verb");
        return this;
    }

    /**
     * @return
     */
    public FluentCodes sixDigits() {
        words.add(String.format("%06d", random.nextInt(999999)));
        return this;
    }

    /**
     * @param table table name
     */
    private void selectWord(String table) {
        try {
            connectionCheck();
            String sb = "SELECT LOWER(word) FROM " +
                    table +
                    " where length(word) between " +
                    minLength + " and " + maxLength +
                    " ORDER BY RANDOM() LIMIT 1";
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(sb);
            if (rs.next()) {
                // read the result set
                words.add(rs.getString(1));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            e.printStackTrace();
        }
    }

    /**
     * @param joiner default joiner is '-'
     * @return
     */
    public FluentCodes withJoiner(String joiner) {
        this.joiner = joiner;
        return this;
    }


    /**
     * @param min default min length is 6
     * @return
     * @throws LengthMismatchException
     */
    public FluentCodes withMinLength(int min) throws LengthMismatchException {
        if (min > maxLength) {
            throw new LengthMismatchException("minLength is larger than maxLength");
        }
        this.minLength = min;
        return this;
    }

    /**
     * @param max default max length is 6
     * @return
     * @throws LengthMismatchException
     */
    public FluentCodes withMaxLength(int max) throws LengthMismatchException {
        if (max < minLength) {
            throw new LengthMismatchException("minLength is larger than maxLength");
        }
        this.maxLength = max;
        return this;
    }

    /**
     * Produces a word based on used builder methods.
     *
     * @return
     */
    @Override
    public String toString() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            if (i != 0) {
                sb.append(joiner);
            }
            sb.append(words.get(i));
        }
        return sb.toString();
    }


}
