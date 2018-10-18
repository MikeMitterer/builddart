package at.mikemitterer.dbuilder

import ch.qos.logback.classic.Level
import io.kotlintest.Description
import io.kotlintest.TestResult
import io.kotlintest.shouldBe
import io.kotlintest.specs.AnnotationSpec
import io.kotlintest.specs.Test
import org.apache.commons.io.FilenameUtils
import org.slf4j.LoggerFactory

/**
 * @since 28.02.18, 09:38
 */
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LogLevelTest : AnnotationSpec() {
    private val logger = LoggerFactory.getLogger(LogLevelTest::class.java)

    override fun beforeTest(description: Description) {
        super.beforeTest(description)
        System.setProperty(SYSTEM_PROP_PATH,"${System.getProperty("user.dir")}/src/test/resources")
    }

    override fun afterTest(description: Description, result: TestResult) {
        super.afterTest(description, result)
        System.getProperties().remove(SYSTEM_PROP_PATH)
    }

    @Test
    fun testNormalizePath() {
        val filename = FilenameUtils.normalizeNoEndSeparator(".")
        filename.shouldBe(".");
    }

    /**
     * In den properties steht: log.level=debug
     */
    @Test
    fun testReadLogLevelFromProperties() {
        logLevel.shouldBe(Level.DEBUG)
    }
}
