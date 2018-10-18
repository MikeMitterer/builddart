package at.mikemitterer.dbuilder

import java.io.File

/**
 * Tool für File-Operations
 *
 * @since   27.07.18, 11:36
 */

/**
 * Alle Variablen aus content werden durch die Key/Value-Pairs aus varsToReplace ersetzt
 *
 * Basis: Hallo {{name}}
 * varsToReplace: mapOf("name" to "Mike")
 *
 * Result: Hallo Mike
 */
fun replaceVars(content:String, varsToReplace : Map<String,String>) : String {
    var retval = content

    varsToReplace.forEach { key, value -> retval = retval.replace("{{$key}}",value) }
    return retval
}

/**
 * Kopiert ein File von source nach target und ersetzt die
 * Variablen im File
 */
fun cpyFile(source: File, target:File, varsToReplace: Map<String, String>) {
    var content = source.readText()

    content = replaceVars(content,varsToReplace)
    target.writeText(content)
}

/**
 * Holt aus dem Yaml-Header von content die KeyValue-Liste heraus
 */
fun splitMarkdown(content: String, callback: (kvPairs : List<Pair<String,String>>,mdBlock: String) -> String) : String {
    val separator = "~{3,}\n"

    if(!content.contains(Regex(separator,RegexOption.MULTILINE))) {
        return callback(emptyList(),content)
    }
    val result = content.split(Regex(separator,RegexOption.MULTILINE))
    if(result.size != 2) {
        return callback(emptyList(),content)
    }

    val yamlBlock = result.first()
    val mdBlock = result.last()

    val lines = yamlBlock.split(Regex("\n",RegexOption.MULTILINE))
            .filterNot {
                line -> line.isEmpty() || line.isBlank()
            }

    val keyValuePairs = mutableListOf<Pair<String,String>>()

    lines.forEach { line ->
        val (key,value) = line.split(Regex(":")).map {
            it.trim().replace(Regex("^[\"'](.*)[\"']$"),"$1")
        }
        keyValuePairs.add(Pair(key, value))
    }

    return callback(keyValuePairs,mdBlock)
}

/**
 * Der mdBlock wird im HTML-Template in den {{_content}}-Platzhalter eingefügt.
 * Danach werden alle {{xxx}} Platzhalter durch die jeweiligen Key-Value-Pairs ersetzt
 */
fun mergeHtmlTemplate(htmlTemplate : String, kvPairs : List<Pair<String,String>>,mdBlock: String) : String {
    var content = htmlTemplate.replace("{{_content}}",mdBlock)

    kvPairs.forEach { pair ->
        content = content.replace("{{${pair.first}}}",pair.second)
    }

    return content
}