package at.mikemitterer.dbuilder

import ch.qos.logback.classic.Level
import org.apache.commons.io.FilenameUtils
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory

/**
 * @since 28.02.18, 09:38
 */
class LogLevelTest : Assert() {
    private val logger = LoggerFactory.getLogger(LogLevelTest::class.java)

    @Before
    fun setUp() {
        System.setProperty(SYSTEM_PROP_PATH,"${System.getProperty("user.dir")}/src/test/resources")
    }

    @After
    fun tearDown() {
        System.getProperties().remove(SYSTEM_PROP_PATH)
    }

    @Test
    fun testNormalizePath() {
        val filename = FilenameUtils.normalizeNoEndSeparator(".")
        assertEquals(".",filename)
    }

    /**
     * In den properties steht: log.level=debug
     */
    @Test
    fun testReadLogLevelFromProperties() {
        assertEquals(Level.DEBUG, logLevel)
    }
}
