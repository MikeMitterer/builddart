package at.mikemitterer.dbuilder

import io.kotlintest.Description
import io.kotlintest.Spec
import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import org.apache.commons.io.FilenameUtils
import org.slf4j.LoggerFactory

/**
 * @since 28.02.18, 09:38
 */
class PropertiesTest : FunSpec() {
    private val logger = LoggerFactory.getLogger(PropertiesTest::class.java)

    override fun beforeSpec(description: Description, spec: Spec) {
        super.beforeSpec(description, spec)
        System.setProperty(SYSTEM_PROP_PATH, "${System.getProperty("user.dir")}/src/test/resources")
    }

    override fun afterSpec(description: Description, spec: Spec) {
        super.afterSpec(description, spec)
        System.getProperties().remove(SYSTEM_PROP_PATH)
    }

    init {

        test("Normalizing a dot-Dir should return .") {
            val filename = FilenameUtils.normalizeNoEndSeparator(".")
            filename.shouldBe("")
        }

        test("Name property should return 'Mike'") {
            val name = properties["name"]

            logger.info("Working Directory = " + System.getProperty("user.dir"))
            name.shouldBe("Mike")
        }

        test("Should show default Value") {
            System.setProperty(SYSTEM_PROP_PATH, "${System.getProperty("user.dir")}/does/not/exist")

            properties.getProperty("firstname", "defaultValue").shouldBe("defaultValue")
        }

    }
}
