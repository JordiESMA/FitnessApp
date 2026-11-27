package org.insbaixcamp.appfitnessa

import java.io.IOException
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Timeout
import org.insbaixcamp.appfitnessa.model.Exercise
import org.insbaixcamp.appfitnessa.model.ExerciseDbApi
import org.insbaixcamp.appfitnessa.model.ExerciseMeta
import org.insbaixcamp.appfitnessa.model.ExerciseResponse
import org.insbaixcamp.appfitnessa.viewmodel.ExerciseViewModel
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciseViewModelTest {
    private val api = DeferredExerciseApi()

    @Test fun firstRequestUses25AndPreventsConcurrentRequests() {
        val model = ExerciseViewModel(api)
        model.loadExercises()
        model.loadMore()
        assertTrue(model.isLoading)
        assertEquals(listOf(25 to null), api.requests)
    }

    @Test fun nextPageKeepsPreviousExercisesAndRemovesDuplicateIds() {
        val model = ExerciseViewModel(api)
        api.respond(listOf("1", "2"), "page2")
        model.loadMore()
        model.loadMore()
        assertEquals(listOf(25 to null, 25 to "page2"), api.requests)
        api.respond(listOf("2", "3"), null)
        assertEquals(listOf("1", "2", "3"), model.exercises.map { it.exerciseId })
        assertFalse(model.hasNextPage)
        assertFalse(model.isLoading)
        model.loadMore()
        assertEquals(2, api.requests.size)
    }

    @Test fun connectionFailureKeepsDataAndRetriesTheSameCursor() {
        val model = ExerciseViewModel(api)
        api.respond(listOf("1"), "page2")
        model.loadMore()
        api.fail()
        assertNotNull(model.error)
        assertFalse(model.isLoading)
        assertEquals(1, model.exercises.size)
        model.loadMore()
        assertNull(model.error)
        assertEquals("page2", api.requests.last().second)
        api.respond(listOf("2"), null)
        assertEquals(2, model.exercises.size)
    }

    @Test fun failedInitialPageCanBeRetried() {
        val model = ExerciseViewModel(api)
        api.fail()
        model.loadExercises()
        assertEquals(null, api.requests.last().second)
        api.respond(listOf("1"), null)
        assertEquals(1, model.exercises.size)
        assertNull(model.error)
    }

    @Test fun httpErrorKeepsCursorForRetry() {
        val model = ExerciseViewModel(api)
        api.respond(listOf("1"), "page2")
        model.loadMore()
        api.call.callback.onResponse(api.call, Response.error(503, "unavailable".toResponseBody()))
        assertEquals("Error HTTP: 503", model.error)
        model.loadMore()
        assertEquals("page2", api.requests.last().second)
    }

    @Test fun repeatedCursorStopsPaginationEvenWithNoNewIds() {
        val model = ExerciseViewModel(api)
        api.respond(listOf("1"), "same")
        model.loadMore()
        api.respond(listOf("1"), "same")
        assertFalse(model.hasNextPage)
        model.loadMore()
        assertEquals(2, api.requests.size)
    }

    @Test fun missingOrBlankCursorStopsPagination() {
        for (cursor in listOf(null, "", " ")) {
            val service = DeferredExerciseApi()
            val model = ExerciseViewModel(service)
            service.respond(listOf("1"), cursor, hasNext = true)
            model.loadMore()
            assertFalse(model.hasNextPage)
            assertEquals(1, service.requests.size)
        }
    }

    @Test fun lastPageFlagStopsEvenIfServerReturnsCursor() {
        val model = ExerciseViewModel(api)
        api.respond(listOf("1"), "ignored", hasNext = false)
        model.loadMore()
        assertEquals(1, api.requests.size)
    }

    @Test fun refreshWhileLoadingDoesNotEraseExistingData() {
        val model = ExerciseViewModel(api)
        api.respond(listOf("1"), "page2")
        model.loadMore()
        model.loadExercises()
        assertEquals(1, model.exercises.size)
        api.respond(listOf("2"), null)
        assertEquals(listOf("1", "2"), model.exercises.map { it.exerciseId })
    }
}

private class DeferredExerciseApi : ExerciseDbApi {
    val requests = mutableListOf<Pair<Int, String?>>()
    lateinit var call: DeferredExerciseCall
    override fun getExercises(limit: Int, after: String?): Call<ExerciseResponse> {
        requests.add(limit to after)
        return DeferredExerciseCall().also { call = it }
    }
    fun respond(ids: List<String>, cursor: String?, hasNext: Boolean = cursor != null) {
        call.callback.onResponse(call, Response.success(ExerciseResponse(
            true, ExerciseMeta(100, hasNext, cursor), ids.map {
                Exercise(it, "Exercici $it", "", emptyList(), emptyList(), emptyList(), emptyList())
            }
        )))
    }
    fun fail() = call.callback.onFailure(call, IOException("Sense connexió"))
}

private class DeferredExerciseCall : Call<ExerciseResponse> {
    lateinit var callback: Callback<ExerciseResponse>
    private var canceled = false
    override fun enqueue(callback: Callback<ExerciseResponse>) { this.callback = callback }
    override fun execute(): Response<ExerciseResponse> = error("Use enqueue")
    override fun isExecuted() = ::callback.isInitialized
    override fun cancel() { canceled = true }
    override fun isCanceled() = canceled
    override fun clone(): Call<ExerciseResponse> = DeferredExerciseCall()
    override fun request(): Request = Request.Builder().url("https://example.test/exercises").build()
    override fun timeout() = Timeout.NONE
}
