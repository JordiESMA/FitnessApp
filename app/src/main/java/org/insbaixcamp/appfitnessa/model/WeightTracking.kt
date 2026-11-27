package org.insbaixcamp.appfitnessa.model

import android.provider.ContactsContract
import java.util.Calendar
import java.util.Date

data class WeightTracking (
    //data is date in millis
    var data: Long ,
    var pes: Double
)