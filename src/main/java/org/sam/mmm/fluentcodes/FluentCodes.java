package org.sam.mmm.fluentcodes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FluentCodes {
    private final Random random = new Random();
    private final List<String> words = new ArrayList<>();
    Connection connection = null;
    private String joiner = "-";
    private int minLength = 6;
    private int maxLength = 6;


    public FluentCodes() {
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite::resource:words_release.db");
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

    public static String generateCodeWithFourWords() {
        return new FluentCodes().adjective().verb().noun().adjective().toString();
    }

    public static String generateCodeWithThreeWordsAndSixDigits() {
        return new FluentCodes().adjective().verb().noun().sixDigits().toString();
    }

    public FluentCodes adjective() {
        selectWord("adj");
        return this;
    }

    public FluentCodes adposition() {
        selectWord("adp");
        return this;
    }

    public FluentCodes adverb() {
        selectWord("adv");
        return this;
    }

    public FluentCodes auxiliary() {
        selectWord("aux");
        return this;
    }

    public FluentCodes coordinatingConjunction() {
        selectWord("cconj");
        return this;
    }

    public FluentCodes determiner() {
        selectWord("det");
        return this;
    }

    public FluentCodes interjection() {
        selectWord("intj");
        return this;
    }

    public FluentCodes noun() {
        selectWord("noun");
        return this;
    }

    //    public FluentCodes numeral(){
//        selectWord("num");
//        return this;
//    }
    public FluentCodes particle() {
        selectWord("part");
        return this;
    }

    public FluentCodes pronoun() {
        selectWord("pron");
        return this;
    }

    public FluentCodes properNoun() {
        selectWord("propn");
        return this;
    }

    public FluentCodes punctuation() {
        selectWord("punct");
        return this;
    }

    public FluentCodes subordinatingConjunction() {
        selectWord("sconj");
        return this;
    }

    public FluentCodes symbol() {
        selectWord("sym");
        return this;
    }

    public FluentCodes verb() {
        selectWord("verb");
        return this;
    }

    public FluentCodes sixDigits() {
        words.add(String.format("%06d", random.nextInt(999999)));
        return this;
    }

    private void selectWord(String table) {
        try {
            StringBuilder sb = new StringBuilder("SELECT LOWER(word) FROM ")
                    .append(table)
                    .append(" where length(word) between ")
                    .append(minLength).append(" and ").append(maxLength)
                    .append(" ORDER BY RANDOM() LIMIT 1");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            ResultSet rs = statement.executeQuery(sb.toString());
            if (rs.next()) {
                // read the result set
                words.add(rs.getString(1));
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }

    public FluentCodes withJoiner(String joiner) {
        this.joiner = joiner;
        return this;
    }

    public FluentCodes withMinLength(int min) throws LengthMismatchException {
        if (min > maxLength) {
            throw new LengthMismatchException("minLength is larger than maxLength");
        }
        this.minLength = min;
        return this;
    }

    public FluentCodes withMaxLength(int max) throws LengthMismatchException {
        if (max < minLength) {
            throw new LengthMismatchException("minLength is larger than maxLength");
        }
        this.maxLength = max;
        return this;
    }

    @Override
    public String toString() {
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
