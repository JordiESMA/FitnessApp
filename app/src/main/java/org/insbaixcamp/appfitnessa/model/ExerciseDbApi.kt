package org.insbaixcamp.appfitnessa.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseDbApi {

    @GET("api/v1/exercises")
    fun getExercises(
        @Query("after") after: String? = null
    ): Call<ExerciseResponse>

}