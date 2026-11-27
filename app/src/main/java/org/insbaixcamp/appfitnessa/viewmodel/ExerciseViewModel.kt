package org.insbaixcamp.appfitnessa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.insbaixcamp.appfitnessa.model.Exercise
import org.insbaixcamp.appfitnessa.model.ExerciseApiClient
import org.insbaixcamp.appfitnessa.model.ExerciseDbApi
import org.insbaixcamp.appfitnessa.model.ExerciseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciseViewModel(
    private val api: ExerciseDbApi = ExerciseApiClient.service
) : ViewModel() {

    var exercises by mutableStateOf<List<Exercise>>(
        emptyList()
    )
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var hasNextPage by mutableStateOf(false)
        private set

    private var nextCursor: String? = null
    private var currentCall: Call<ExerciseResponse>? = null

    init {
        loadExercises()
    }

    // Demana la primera pàgina
    fun loadExercises() {
        if (isLoading) return
        nextCursor = null
        hasNextPage = false
        exercises = emptyList()
        requestExercises(null)
    }

    // Demana la pàgina següent
    fun loadMore() {
        if (isLoading || !hasNextPage) return

        val cursor = nextCursor ?: return
        requestExercises(cursor)
    }

    private fun requestExercises(cursor: String?) {

        if (isLoading) return

        isLoading = true
        error = null

        val call = api.getExercises(limit = 25, after = cursor)

        currentCall = call

        call.enqueue(object : Callback<ExerciseResponse> {

            override fun onResponse(
                call: Call<ExerciseResponse>,
                response: Response<ExerciseResponse>
            ) {
                if (call !== currentCall) return

                isLoading = false
                currentCall = null

                val body = response.body()

                if (response.isSuccessful &&
                    body?.success == true) {

                    exercises = if (cursor == null) {
                        body.data
                    } else {
                        exercises + body.data
                    }.distinctBy { it.exerciseId }

                    nextCursor = body.meta.nextCursor

                    hasNextPage =
                        body.meta.hasNextPage &&
                                !nextCursor.isNullOrBlank() &&
                                nextCursor != cursor

                } else {
                    error = "Error HTTP: ${response.code()}"
                }
            }

            override fun onFailure(
                call: Call<ExerciseResponse>,
                t: Throwable
            ) {
                if (call !== currentCall) return

                isLoading = false
                currentCall = null

                if (!call.isCanceled) {
                    error = "Error de connexió: ${t.message}"
                }
            }
        })
    }

    override fun onCleared() {
        currentCall?.cancel()
        currentCall = null
        super.onCleared()
    }
}
