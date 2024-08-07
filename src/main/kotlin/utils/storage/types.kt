package utils.storage

import androidx.compose.ui.graphics.Color
import java.awt.Dimension
import kotlin.IllegalArgumentException
import kotlin.reflect.KClass

/**
 * An image as a byte mask
 */
typealias ImageMask = Array<Array<Byte>>

/**
 * A single position on an image
 */
typealias PositionNode = Pair<Int, Int>

/**
 * List of node generation functions
 *
 * @property NONE No set generator type, similar to a null, should be used as a fallback value
 * @property SQUARE Square grid of pieces
 * @property SQUARE_NOISE Square grid of pieces with noisy edges
 */
enum class GeneratorType {
    NONE,
    SQUARE,
    SQUARE_NOISE
}

/**
 * A masked region of an image with its top-left coordinate
 *
 * @param size The size of the mask
 *
 * @property bits The array of bits in the mask
 * @property position The position of the top-left corner of the mask
 */
class Mask(val size: Dimension) {
    var bits: ImageMask = Array(size.height) {
        Array(size.width) { 0 }
    }
    var position: PositionNode = PositionNode(-1, -1)

    /**
     * Used for debugging, prints the mask array showing only 1s
     */
    fun print() {
        for(y in bits) {
            for(x in y) {
                print(1)
            }
            println()
        }
    }
}

/**
 * Protects a variable from race conditions by "locking" the value
 * It will also hold any values attempted to be set while locked and update after it is unlocked
 *
 * @param T The type of the data being stored
 * @param lockVal The data to be stored, of type [T]
 *
 * @property value The current value of the variable
 * @property locked If the variable is locked
 * @property holdVal Any queued value
 */
class LockType<T>(lockVal: T) {
    private var value: T = lockVal
    private var locked: Boolean = false
    private var holdVal: T? = null

    /**
     * Returns if the value is locked
     */
    fun locked() : Boolean { return locked }

    /**
     * Locks the value, it cannot be modified
     */
    fun lock() { locked = true }

    /**
     * Unlocks the property, it can be modified
     */
    fun unlock() { locked = false }

    /**
     * Returns the value of the data, updates the data if there is a value queued
     */
    fun value() : T {
        if(holdVal != null && !locked) {
            value = holdVal!!
            holdVal = null
        }

        return value
    }

    /**
     * Sets the value of the data, if locked it queues the value in [holdVal]
     */
    fun set(newVal: T) {
        if(locked) {
            holdVal = newVal
            return
        }

        value = newVal
    }
}

/**
 * Represents an array of masks with modification functions
 *
 * @property masks An array of all masks
 */
class ImageMaskArray() {
    var masks = ArrayList<Mask>()

    /**
     * Adds a mask to the array
     */
    fun add(m: Mask) { masks.add(m) }

    /**
     * Removes a mask from the array by
     */
    fun remove(m: Mask) { masks.remove(m) }

    /**
     * Removes a mask from the array at a given index
     */
    fun remove(idx: Int) { masks.removeAt(idx) }

    /**
     * Removes the last mask in the array
     */
    fun removeLast() { masks.remove(masks.last()) }

    /**
     * Removes the first mask in the array
     */
    fun removeFirst() { masks.remove(masks[0]) }

    /**
     * Returns the mask at a given index
     */
    fun get(idx: Int) : Mask { return masks[idx] }

    /**
     * Returns the last mask in the array
     */
    fun last() : Mask { return masks.last() }

    /**
     * Returns the first mask in the array
     */
    fun first() : Mask { return masks[0] }
}

/**
 * Casts from Any to designated type
 *
 * @param value The value to be cast
 * @param targetType The target type to cast to
 *
 * @return The inputted value cast to the target type
 */
fun anyCast(value: Any, targetType: KClass<*>): Any {
    return when(targetType) {
        String::class -> value as String
        Boolean::class -> value as Boolean
        Int::class -> value as Int
        Short::class -> value as Short
        Long::class -> value as Long
        Double::class -> value as Double
        Char::class -> value as Char
        else -> throw IllegalArgumentException("Unsupported anyCast type conversion : ${targetType.simpleName}")
    }
}

/**
 * Casts an ArrayList of name / value pairs to a designated map collection type
 */
fun mapCast(list: ArrayList<Pair<String, Any>>, mapType: KClass<*>, targetType: KClass<*>): Any {
    return when(mapType) {
        Map::class -> {
            val buildMap: MutableMap<String, Any> = mutableMapOf()
            list.forEach {  buildMap[it.first] = anyCast(it.second, targetType)  }
            buildMap
        }
        HashMap::class -> {
            val buildMap: HashMap<String, Any> = hashMapOf()
            list.forEach {  buildMap[it.first] = anyCast(it.second, targetType)  }
            buildMap
        }
        else -> throw IllegalArgumentException("Unsupported mapCast type conversion : ${targetType.simpleName}")
    }
}

/**
 * Empty class for theme data to be parsed into
 *
 * @property name The name of the theme
 * @property icon The icon color
 * @property textColors Map of colors for all text variations
 * @property backgroundColors Map of colors for all background variations
 */

class ThemeData(var name: String) {
    var icon: Long = 0
    var header: Long = 0
    var button: Long = 0
    var border: Long = 0
    var fab: Long = 0
    var card: Map<String, Long> = mapOf()
    var textColors: Map<String, Long> = mapOf()
    var backgroundColors: Map<String, Long> = mapOf()
    var textFields: Map<String, Long> = mapOf()
}

class ConfigData() {

}

open class DecoratedError(type: String, message: String) : Throwable(message) {
    init {
        print("\u001b[31m")
        println("\u001b[1m[\u001B[0m\u001b[31m ${type.uppercase().replace(" ", "-")}-ERROR : $message \u001B[1m]\u001B[0m")
        print("\u001b[0m")
    }
}

open class DecoratedWarning(type: String, message: String) : Exception(message) {
    init {
        print("\u001b[31m")
        println("\u001b[1m[\u001B[0m\u001b[31m ${type.uppercase()}-WARNING : $message \u001B[1m]\u001B[0m")
        print("\u001b[0m")
    }
}

class InvalidTSFFile(message: String) : DecoratedError("TSF", message)