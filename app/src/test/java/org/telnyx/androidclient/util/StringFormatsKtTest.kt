package org.telnyx.androidclient.util

import org.junit.Assert.*

import org.junit.Test

class StringFormatsKtTest {

    @Test
    fun get_Formatted_StopWatch_Test() {

        val correctTimes = arrayOf("00:00:30","00:02:00","01:00:00")

        //Test for Minutes, Seconds and Hours
        arrayOf(30L,120L,3600L).forEachIndexed { index, seconds ->
            assertEquals(getFormattedStopWatch(seconds),correctTimes[index])
        }

    }
}