package com.ganlink.pe.features.bovinuesystem.presentation.models

data class MetricDetail(
    val title: String,
    val description: String,
    val metricId: Int
)

val bovinueMetricDetails = listOf(
    MetricDetail(
        title = "Producción de leche por vaca/día",
        description = "Nivel de rendimiento individual",
        metricId = 1
    ),
    MetricDetail(
        title = "Contenido de grasa y proteína",
        description = "Calidad nutricional de la leche",
        metricId = 2
    ),
    MetricDetail(
        title = "Ganancia de peso diaria (GMD)",
        description = "Crecimiento y eficiencia de engorde",
        metricId = 3
    ),
    MetricDetail(
        title = "Índice de conversión alimenticia",
        description = "Eficiencia: kg de alimento necesarios para ganar 1 kg de peso",
        metricId = 4
    ),
    MetricDetail(
        title = "Tasa de preñez",
        description = "Porcentaje de vacas que quedan gestantes",
        metricId = 5
    ),
    MetricDetail(
        title = "Tasa de concepción",
        description = "Éxito de preñez respecto a servicios realizados",
        metricId = 6
    )
)
