import at.mikemitterer.dbuilder.mergeHtmlTemplate
import at.mikemitterer.dbuilder.splitMarkdown
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec

/**
 *
 *
 * @since   2018-10-18, 09:53
 */
class SplitMDContentTest : FunSpec() {
    private val fileContent: String = """
                name: Mike
                fullname: "Mike Mitterer    "
                age: 99
                ~~~~
                I am a MD content
                This is the second line
                My name is {{name}}
            """.trimIndent()

    private val htmlTemplate = """
                <html>
                    <head>Title {{age}}</head>
                    <body>
                        {{_content}}
                    </body>
                </html>
            """.trimIndent()

    init {
        test("Splits Markdown-Content into Yaml- and MD part") {
            val result = fileContent.split(Regex("~{3,}\n", RegexOption.MULTILINE))

            result.size.shouldBe(2)

            val yamlBlock = result.first()
            val mdBlock = result.last()

            val lines = yamlBlock.split(Regex("\n", RegexOption.MULTILINE))
                    .filterNot { line ->
                        line.isEmpty() || line.isBlank()
                    }

            lines.size.shouldBe(3)

            val keyValuePairs = mutableListOf<Pair<String, String>>()

            lines.forEach { line ->
                val (key, value) = line.split(Regex(":")).map {
                    it.trim().replace(Regex("^[\"'](.*)[\"']$"), "$1")
                }
                keyValuePairs.add(Pair(key, value))
                // println("Key: $key, value: >$value<")
            }

            var content = mdBlock
            keyValuePairs.forEach { pair ->
                content = content.replace("{{${pair.first}}}", pair.second)
            }
            content.split(Regex("\n", RegexOption.MULTILINE)).last()
                    .shouldBe("My name is Mike")
        }

        test("should return 3 KV-Pairs") {
            val content = splitMarkdown(fileContent) { kvPairs, mdBlock ->

                kvPairs.size.shouldBe(3)

                var content = mdBlock
                kvPairs.forEach { pair ->
                    content = content.replace("{{${pair.first}}}", pair.second)
                }
                content
            }

            content.split(Regex("\n", RegexOption.MULTILINE)).last()
                    .shouldBe("My name is Mike")

        }

        test("html-block should contain fileContent-Block") {

            val content = splitMarkdown(fileContent) { kvPairs, mdBlock ->

                kvPairs.size.shouldBe(3)

                var content = htmlTemplate.replace("{{_content}}", mdBlock)
                kvPairs.forEach { pair ->
                    content = content.replace("{{${pair.first}}}", pair.second)
                }

                content
            }

            val lines = content.split(Regex("\n", RegexOption.MULTILINE))

            lines[1].trim().shouldBe("<head>Title 99</head>")
            lines[5].trim().shouldBe("My name is Mike")
        }

        test("merge should generate the HTML-Output") {
            val content = splitMarkdown(fileContent) {
                kvPairs, mdBlock ->
                mergeHtmlTemplate(htmlTemplate, kvPairs, mdBlock)
            }

            val lines = content.split(Regex("\n", RegexOption.MULTILINE))

            lines[1].trim().shouldBe("<head>Title 99</head>")
            lines[5].trim().shouldBe("My name is Mike")
        }
    }
}