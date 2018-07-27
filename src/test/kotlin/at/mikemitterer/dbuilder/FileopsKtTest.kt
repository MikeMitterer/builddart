package at.mikemitterer.dbuilder

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @since 27.07.18, 11:53
 */
class FileopsKtTest {

    private val resourceDir = "${System.getProperty("user.dir")!!}/src/test/resources"

    @Test
    fun testReplaceMapContent() {
        val content = "Hallo {{name}}"
        assertEquals("Hallo Mike", replaceVars(content,
                mapOf(
                        "name" to "Mike",
                        "age" to "99"
                )))
    }

    @Test
    fun replaceName() {
        val source = File("${resourceDir}/vars.to.replace.in")
        val target = File("${resourceDir}/vars.to.replace.out")

        if(target.exists()) { target.delete() }

        cpyFile(source,target, mapOf("name" to "Mike"))

        assertTrue { target.exists() }
        assertEquals("Hallo Mike",target.readText().trim())

    }
}