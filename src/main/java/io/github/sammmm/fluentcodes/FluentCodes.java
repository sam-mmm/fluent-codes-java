// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package io.github.sammmm.fluentcodes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h1>Java fluent codes</h1>
 * <p>
 * This library can be use to generate fluent codes.
 * </p>
 * <p>
 * Default joiner is '-'
 * Default length of words are 6
 * </p>
 * <p>
 * Examples:
 * 1.
 * FluentCodes.generateCodeWithFourWords()
 * 'fluffy-vacuum-misuse-deadly'
 * </p>
 * <p>
 * 2.
 * FluentCodes.generateCodeWithThreeWordsAndSixDigits()
 * 'calmer-taints-fourty-887709'
 *  </p>
 *  <p>
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
 *  </p>
 *
 * <p>
 * Definitions of terms adjective, adposition, adverb, auxiliary, coordinatingConjunction, determiner, interjection,
 * noun, numeral, particle, pronoun, properNoun, punctuation, subordinatingConjunction, symbol, verb
 * are from https://universaldependencies.org/u/pos/
 *
 * Words are generated using code @ https://github.com/sam-mmm/word_generator
 * </p>
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
     * @return Generated code
     */
    public static String generateCodeWithFourWords() {
        return new FluentCodes().adjective().verb().noun().adjective().toString();
    }

    /**
     * Generates code with three words and six digits
     * Ex: 'calmer-taints-fourty-887709'
     *
     * @return Generated code
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
     * @return FluentCodes
     */
    public FluentCodes adposition() {
        selectWord("adp");
        return this;
    }

    /**
     * @return FluentCodes
     */
    public FluentCodes adverb() {
        selectWord("adv");
        return this;
    }

    /**
     * @return FluentCodes
     */
    public FluentCodes auxiliary() {
        selectWord("aux");
        return this;
    }

    /**
     * @return FluentCodes
     */
    public FluentCodes coordinatingConjunction() {
        selectWord("cconj");
        return this;
    }

    /**
     * @return FluentCodes
     */
    public FluentCodes determiner() {
        selectWord("det");
        return this;
    }

    /**
     * @return FluentCodes
     */
    public FluentCodes interjection() {
        selectWord("intj");
        return this;
    }

    /**
     * @return FluentCodes
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
     * @return FluentCodes
     */
    public FluentCodes particle() {
        selectWord("part");
        return this;
    }

    /**
     * @return FluentCodes
     */
    public FluentCodes pronoun() {
        selectWord("pron");
        return this;
    }

    /**
     * @return FluentCodes
     */
    public FluentCodes properNoun() {
        selectWord("propn");
        return this;
    }

    /**
     * @return FluentCodes
     */
    public FluentCodes punctuation() {
        selectWord("punct");
        return this;
    }

    /**
     * @return FluentCodes
     */
    public FluentCodes subordinatingConjunction() {
        selectWord("sconj");
        return this;
    }

    /**
     * @return FluentCodes
     */
    public FluentCodes symbol() {
        selectWord("sym");
        return this;
    }

    /**
     * @return FluentCodes
     */
    public FluentCodes verb() {
        selectWord("verb");
        return this;
    }

    /**
     * @return FluentCodes
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
     * Set joiner
     * @param joiner default joiner is '-'
     * @return FluentCodes
     */
    public FluentCodes withJoiner(String joiner) {
        this.joiner = joiner;
        return this;
    }


    /**
     * Set min length for word
     * @param min default min length is 6
     * @return FluentCodes
     * @throws LengthMismatchException When min length is larger than max length
     */
    public FluentCodes withMinLength(int min) throws LengthMismatchException {
        if (min > maxLength) {
            throw new LengthMismatchException("minLength is larger than maxLength");
        }
        this.minLength = min;
        return this;
    }

    /**
     * Set max length for word
     * @param max default max length is 6
     * @return FluentCodes
     * @throws LengthMismatchException When min length is larger than max length
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
     * @return Generated code
     */
    @Override
    public String toString() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.join(joiner, words);
    }


}
