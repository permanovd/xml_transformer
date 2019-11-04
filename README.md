Xml transformation tool
----

### Task

Create runnable jar, which would take as arguments:

* name of xml file
* name of xsd file (xml has to be formed by this schema)
* name of xslt file
* name of result file (xml)

1. Validate given `xml` file by given `xsd` file
2. Transform given `xml` file by given `xslt` file
3. Validate transformed `xml` file by given `xsd` file.
Output `xml` file has to match one of templates in given `xsd` schema.
4. Write result to output `xml` file.

Cover with unit test on jUnit 5.

Calculate coverage using jacoco.

Coverage should be 100% or explained why it is not possible.

### Run

In project root: 
`java -jar xml_transformer.jar -s testSources/input.xml -o testSources/output.xml -sc testSources/schema.xsd -t testSources/transform.xsl`

Options:

* `s` - source (xml) file path.
* `o` - output (xml) file path.
* `sc` - schema (xsd) file path.
* `t` - transform (xslt) file path.


### Coverage

Coverage: **93**%

Could not reach 100% of coverage because:

1. I dont see any benefit of covering main Application class.

So this, left uncovered.

```java
public class Application {

    public static void main(String[] args) {
        TransformationServiceFacade serviceFacade =
                new TransformationServiceFacade(new TransformationService(), new DefaultParser());

        serviceFacade.validateAndTransform(args, new PrintWriter(System.out));
    }
}
```

2. IoException on logger creation. Same as first, dont see any benefit from writing tests on that section.


### Logging

Logs are dumped into `app.log` file in current working directory.

### Test sources:

Some xml, xsd files were taken from: 

* https://docs.microsoft.com/ru-ru/dotnet/csharp/programming-guide/concepts/linq/sample-xsd-file-customers-and-orders1
* https://docs.microsoft.com/ru-ru/dotnet/csharp/programming-guide/concepts/linq/sample-xml-file-customers-and-orders-linq-to-xml-2
