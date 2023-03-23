package org.telnyx.androidclient.util

import java.util.concurrent.TimeUnit

fun getFormattedStopWatch(second: Long): String {
    var milliseconds = second * 1000
    val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
    milliseconds -= TimeUnit.HOURS.toMillis(hours)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
    milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

    return "${if (hours < 10) "0" else ""}$hours:" +
            "${if (minutes < 10) "0" else ""}$minutes:" +
            "${if (seconds < 10) "0" else ""}$seconds"
}