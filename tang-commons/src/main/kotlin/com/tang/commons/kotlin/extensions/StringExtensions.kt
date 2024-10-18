package com.tang.commons.kotlin.extensions

/**
 * @author Tang
 */

/**
 * Calculate the width of a string.
 */
fun String.width(): Int {
    return toCharArray().map { if (it.isFullWidth()) 2 else 1 }.sum()
}
