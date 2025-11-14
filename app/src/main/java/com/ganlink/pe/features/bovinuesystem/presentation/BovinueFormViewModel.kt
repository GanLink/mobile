package com.ganlink.pe.features.bovinuesystem.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganlink.pe.features.bovinuesystem.domain.repositories.BovinueRepository
import com.ganlink.pe.features.bovinuesystem.presentation.models.Bovinue
import com.ganlink.pe.features.bovinuesystem.presentation.models.BovinueMetricDraft
import com.ganlink.pe.features.bovinuesystem.presentation.models.bovinueMetricDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

data class BovinueFormSubmissionState(
    val isSubmitting: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class BovinueFormViewModel @Inject constructor(
    private val repository: BovinueRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val farmId = savedStateHandle.get<Int>("farmId") ?: 0
    private val metricDetailsByTitle = bovinueMetricDetails.associateBy { it.title }

    private val _bovinues = MutableStateFlow<List<Bovinue>>(emptyList())
    val bovinues: StateFlow<List<Bovinue>> = _bovinues.asStateFlow()

    private val _submissionState = MutableStateFlow(BovinueFormSubmissionState())
    val submissionState: StateFlow<BovinueFormSubmissionState> = _submissionState.asStateFlow()

    init {
        fetchBovinues()
    }

    fun registerBovinue(metricValues: Map<String, String>) {
        if (farmId == 0) {
            _submissionState.value = BovinueFormSubmissionState(
                errorMessage = "No se encontró la granja para registrar el bovinue"
            )
            return
        }

        val drafts = try {
            buildMetricDrafts(metricValues)
        } catch (error: IllegalArgumentException) {
            _submissionState.value = BovinueFormSubmissionState(errorMessage = error.message)
            return
        }

        if (drafts.isEmpty()) {
            _submissionState.value = BovinueFormSubmissionState(
                errorMessage = "Ingresa al menos una métrica antes de registrar"
            )
            return
        }

        viewModelScope.launch {
            _submissionState.value = BovinueFormSubmissionState(isSubmitting = true)
            runCatching {
                repository.createBovinueWithMetrics(farmId, drafts)
            }.onSuccess {
                fetchBovinues()
                _submissionState.value = BovinueFormSubmissionState(isSuccess = true)
            }.onFailure { throwable ->
                _submissionState.value = BovinueFormSubmissionState(
                    errorMessage = throwable.message ?: "No se pudo crear el bovinue"
                )
            }
        }
    }

    fun clearSubmissionState() {
        _submissionState.value = BovinueFormSubmissionState()
    }

    private fun fetchBovinues() {
        if (farmId == 0) return

        viewModelScope.launch {
            runCatching { repository.getBovinueByFarmId(farmId) }
                .onSuccess { _bovinues.value = it }
                .onFailure { _bovinues.value = emptyList() }
        }
    }

    private fun buildMetricDrafts(metricValues: Map<String, String>): List<BovinueMetricDraft> {
        val timestamp = isoUtcNow()
        return metricValues.mapNotNull { (title, value) ->
            val detail = metricDetailsByTitle[title] ?: return@mapNotNull null
            if (value.isBlank()) return@mapNotNull null

            val quantity = value.toIntOrNull()
                ?: throw IllegalArgumentException("El valor de \"${detail.title}\" debe ser numérico")

            BovinueMetricDraft(
                bovinueMPId = detail.metricId,
                quantity = quantity,
                date = timestamp
            )
        }
    }

    private fun isoUtcNow(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.format(Date())
    }
}
