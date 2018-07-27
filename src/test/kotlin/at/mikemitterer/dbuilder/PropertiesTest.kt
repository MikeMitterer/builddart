package at.mikemitterer.dbuilder

import org.apache.commons.io.FilenameUtils
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory

/**
 * @since 28.02.18, 09:38
 */
class PropertiesTest : Assert() {
    private val logger = LoggerFactory.getLogger(PropertiesTest::class.java)
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

    @Test
    fun testRead() {
        val name = properties["name"]

        logger.info("Working Directory = " + System.getProperty("user.dir"))
        assertEquals("Mike",name)
    }

    @Test
    fun testNotExistingPropertiesFile() {
        System.setProperty(SYSTEM_PROP_PATH,"${System.getProperty("user.dir")}/does/not/exist")
        assertEquals("defaultValue", properties.getProperty("name","defaultValue"))
    }
}
