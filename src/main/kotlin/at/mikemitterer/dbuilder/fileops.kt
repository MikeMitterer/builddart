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