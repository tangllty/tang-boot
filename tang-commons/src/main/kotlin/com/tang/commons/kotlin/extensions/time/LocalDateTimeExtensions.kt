package com.tang.commons.kotlin.extensions.time

import java.time.LocalDateTime

/**
 * @author Tang
 */

val LocalDateTime.micro: Int get() = this.nano / 1000

val LocalDateTime.milli: Int get() = this.nano / 1000 / 1000
