package io.github.sammmm.fluentcodes;

import org.testng.annotations.Test;

import java.io.IOException;

public class FluentCodesTest {

    @Test
    public void testTest1() throws IOException {
        System.out.println(FluentCodes.generateCodeWithFourWords());
        System.out.println(FluentCodes.generateCodeWithFourWords());
        System.out.println(FluentCodes.generateCodeWithFourWords());
        System.out.println(FluentCodes.generateCodeWithFourWords());
        System.out.println(FluentCodes.generateCodeWithFourWords());
        System.out.println(FluentCodes.generateCodeWithThreeWordsAndSixDigits());
        System.out.println(FluentCodes.generateCodeWithThreeWordsAndSixDigits());
        System.out.println(FluentCodes.generateCodeWithThreeWordsAndSixDigits());
        System.out.println(FluentCodes.generateCodeWithThreeWordsAndSixDigits());
        System.out.println(FluentCodes.generateCodeWithThreeWordsAndSixDigits());
        System.out.println(FluentCodes.generateCodeWithThreeWordsAndSixDigits());

        try {
            System.out.println(new FluentCodes().withJoiner("_").withMinLength(4).withMaxLength(10).adjective().verb().noun().sixDigits().toString());
            System.out.println(new FluentCodes().withJoiner("").withMinLength(4).withMaxLength(10).adjective().verb().noun().sixDigits().toString());
        } catch (LengthMismatchException e) {
            throw new RuntimeException(e);
        }
    }
}