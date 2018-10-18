package at.mikemitterer.dbuilder

import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec
import io.kotlintest.specs.Test
import java.io.File

/**
 * @since 27.07.18, 11:53
 */
@Suppress("unused")
class FileopsKtTest: AnnotationSpec() {
    private val resourceDir = "${System.getProperty("user.dir")!!}/src/test/resources"

    @Test
    fun testReplaceMapContent() {
        val content = "Hallo {{name}}"

        replaceVars(content,
                mapOf(
                        "name" to "Mike",
                        "age" to "99"
                )).shouldBe("Hallo Mike")
    }

    @Test
    fun replaceName() {
        val source = File("${resourceDir}/vars.to.replace.in")
        val target = File("${resourceDir}/vars.to.replace.out")

        if(target.exists()) { target.delete() }

        cpyFile(source,target, mapOf("name" to "Mike"))

        target.exists().shouldBe(true)
        target.readText().trim().shouldBe("Hallo Mike")
    }
}