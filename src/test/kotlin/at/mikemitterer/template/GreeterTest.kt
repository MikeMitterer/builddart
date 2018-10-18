package at.mikemitterer.template

import io.kotlintest.shouldBe
import io.kotlintest.specs.FunSpec
import org.slf4j.LoggerFactory

/**
 * @since 28.02.18, 09:38
 */
class GreeterTest : FunSpec( {
    val logger = LoggerFactory.getLogger(GreeterTest::class.java)
    
    test("Greeter Message should be 'Hello, Mike!'") {
        val greeter = Greeter("Mike")

        logger.info(greeter.message)
        greeter.message.shouldBe("Hello, Mike!")
    }
})
