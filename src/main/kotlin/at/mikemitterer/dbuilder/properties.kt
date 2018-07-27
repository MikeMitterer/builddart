package at.mikemitterer.dbuilder

import ch.qos.logback.classic.Level
import org.apache.commons.io.FilenameUtils
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * Handelt die Properties
 *
 * @since   27.07.18, 09:14
 */


/**
 * Mit diesem Pfad kann der Pfad zu den Properties überschrieben werden
 *
 * Usage:
 *      System.setProperty("builddart.props.path","test/resources")
 */
val SYSTEM_PROP_PATH = "builddart.props.path"

/**
 * Name des properties Files
 *
 * Als Pfad wird immer das aktuelle Directory verwendet
 * Der Pfad kann aber mit setProperty("builddart.props.path"...)
 * überschrieben werden
 *
 * Usage:
 *      System.setProperty("builddart.props.path","test/resources")
 */
val PROPSFILE: String = ".prep.config.properties"

/** Backup des Properties-Getters */
private val _props = Properties()

/** Properties können im kscript abgefragt werden */
val properties: Properties
    get() {
        if (_props.isEmpty) {
            fun pathToProps(): String {
                val path = System.getProperty(SYSTEM_PROP_PATH, "")
                return if (path == null || path.isEmpty()) {
                    "."
                } else {
                    FilenameUtils.normalizeNoEndSeparator(path) ?: "."
                }
            }

            val file = File("${pathToProps()}/$PROPSFILE")
            if(file.exists()) {
                FileInputStream(file).use { _props.load(it) }
            }

        }
        return _props
    }


val logLevel: Level
    get() = when (properties.getProperty("log.level", "info")) {
        "debug" -> Level.DEBUG
        "warning" -> Level.WARN
        "error" -> Level.ERROR
        "off" -> Level.OFF
        else -> Level.INFO
    }
