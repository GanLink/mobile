package com.ganlink.pe.features.bovinuesystem.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganlink.pe.features.bovinuesystem.domain.repositories.BovinueRepository
import com.ganlink.pe.features.bovinuesystem.presentation.models.UpdateBovinueMetric
import com.ganlink.pe.features.bovinuesystem.presentation.models.bovinueMetricDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class BovinueMetricUi(
    val id: Int,
    val bovinueId: Int,
    val bovinueMPId: Int,
    val title: String,
    val description: String,
    val quantity: Int,
    val rawDate: String,
    val dateLabel: String
)

data class BovinueDetailsUiState(
    val isLoading: Boolean = true,
    val metrics: List<BovinueMetricUi> = emptyList(),
    val isUpdating: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class BovinueDetailsViewModel @Inject constructor(
    private val repository: BovinueRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val targetBovinueId = savedStateHandle.get<Int>("bovinueId") ?: 0
    val bovinueId: Int get() = targetBovinueId

    private val _state = MutableStateFlow(BovinueDetailsUiState())
    val state: StateFlow<BovinueDetailsUiState> = _state.asStateFlow()

    private val metricCatalog = bovinueMetricDetails.associateBy { it.metricId }
    private val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("es", "PE"))
        .withZone(ZoneId.systemDefault())

    init {
        refresh()
    }

    fun refresh() {
        if (targetBovinueId == 0) {
            _state.value = BovinueDetailsUiState(
                isLoading = false,
                errorMessage = "No pudimos identificar el bovinue seleccionado"
            )
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            runCatching {
                repository.getMetricByBovinueId(targetBovinueId)
            }.onSuccess { metrics ->
                val uiMetrics = metrics.map { metric ->
                    val detail = metricCatalog[metric.bovinueMPId]
                    BovinueMetricUi(
                        id = metric.id,
                        bovinueId = metric.bovinueId,
                        bovinueMPId = metric.bovinueMPId,
                        title = detail?.title ?: metric.parameterName,
                        description = detail?.description ?: metric.categoryName,
                        quantity = metric.quantity,
                        rawDate = metric.date,
                        dateLabel = formatDate(metric.date)
                    )
                }
                _state.value = BovinueDetailsUiState(
                    isLoading = false,
                    metrics = uiMetrics,
                    isUpdating = false,
                    errorMessage = null
                )
            }.onFailure { throwable ->
                _state.value = BovinueDetailsUiState(
                    isLoading = false,
                    metrics = emptyList(),
                    isUpdating = false,
                    errorMessage = throwable.message ?: "No se pudieron obtener las métricas"
                )
            }
        }
    }

    private fun formatDate(raw: String): String {
        return runCatching {
            val instant = Instant.parse(raw)
            dateFormatter.format(instant)
        }.getOrElse { raw }
    }

    fun updateMetric(metric: BovinueMetricUi, newQuantity: Int) {
        if (targetBovinueId == 0) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isUpdating = true, errorMessage = null)
            runCatching {
                repository.updateBovinueMetric(
                    UpdateBovinueMetric(
                        id = metric.id,
                        bovinueId = metric.bovinueId,
                        bovinueMPId = metric.bovinueMPId,
                        date = metric.rawDate,
                        quantity = newQuantity
                    )
                )
            }.onSuccess {
                refresh()
            }.onFailure { throwable ->
                _state.value = _state.value.copy(
                    isUpdating = false,
                    errorMessage = throwable.message ?: "No se pudo actualizar la métrica"
                )
            }
        }
    }
}
