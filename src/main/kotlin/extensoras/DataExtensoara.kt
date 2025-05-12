package extensoras

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

const val formatoData = "yyyy-MM-dd"

fun String.paraData(formato: String = formatoData): Date {
    val formatter = SimpleDateFormat(formato)
    return formatter.parse(this)
}

fun String.paraLocalDateTimeInicio(formato: String = formatoData): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(formato)
    val data = LocalDate.parse(this, formatter)
    return data.atStartOfDay()
}

fun String.paraLocalDateTimeFim(formato: String = formatoData): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(formato)
    val data = LocalDate.parse(this, formatter)
    return data.atTime(23, 59, 59)
}