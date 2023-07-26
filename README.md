# Java fluent codes

This library can be used to generate fluent codes.

## Example

To generate code with four words:

```java
FluentCodes.generateCodeWithFourWords()
```
```text
Output: fluffy-vacuum-misuse-deadly
```

To generate code with three words and six digits:

```java
FluentCodes.generateCodeWithThreeWordsAndSixDigits()
```
```text
Output: calmer-taints-fourty-887709
```
Or you can use builder methods:

```java
new FluentCodes()
        .withJoiner("_")
        .withMinLength(4)
        .withMaxLength(10)
        .adjective()
        .verb()
        .noun()
        .sixDigits()
        .toString()
```

```text
Output: acadmic_provided_writings_741319
```

### Words

Words are generated using code @ https://github.com/sam-mmm/word_generator

Definitions of terms adjective, adposition, adverb, auxiliary, coordinatingConjunction, determiner, interjection, noun,
numeral, particle, pronoun, properNoun, punctuation, subordinatingConjunction, symbol, verb
are from https://universaldependencies.org/u/pos/
### License

http://www.apache.org/licenses/LICENSE-2.0