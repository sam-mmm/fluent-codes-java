# Java fluent codes

This library can be use to generate fluent codes.

## Example

To generate code with four words:

```java
FluentCodes.generateCodeWithFourWords()
```
```text
Output:fluffy-vacuum-misuse-deadly
```

To generate code with three words and six digits:

```java
FluentCodes.generateCodeWithThreeWordsAndSixDigits()
```
```text
Output:calmer-taints-fourty-887709
```
Or you can use builder methods:

```java
new FluentCodes().withJoiner("_").withMinLength(4).withMaxLength(10).adjective().verb().noun().sixDigits().toString()
```
```text
Output:acadmic_provided_writings_741319
```


## License

https://opensource.org/licenses/Apache-2.0