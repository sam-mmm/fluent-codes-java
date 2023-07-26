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