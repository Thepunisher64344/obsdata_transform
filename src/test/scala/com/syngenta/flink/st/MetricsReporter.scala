package com.syngenta.flink.st

import org.apache.flink.api.scala.metrics.ScalaGauge
import org.apache.flink.metrics.{Metric, MetricConfig, MetricGroup}
import org.apache.flink.metrics.reporter.MetricReporter

import scala.collection.mutable

class MetricsReporter extends MetricReporter {
  override def open(config: MetricConfig): Unit = {}

  override def close(): Unit = {}

  override def notifyOfAddedMetric(metric: Metric, metricName: String, group: MetricGroup): Unit = {
    metric match {
      case gauge: ScalaGauge[_] => {
        val gaugeKey = group.getScopeComponents.toSeq.drop(6).mkString(".") + "." + metricName
        MetricsReporter.gaugeMetrics(gaugeKey) = gauge.asInstanceOf[ScalaGauge[Long]]
      }
      case _ => // Do Nothing
    }
  }

  override def notifyOfRemovedMetric(metric: Metric, metricName: String, group: MetricGroup): Unit = {}
}

object MetricsReporter {
  val gaugeMetrics: mutable.Map[String, ScalaGauge[Long]] = mutable.Map[String, ScalaGauge[Long]]()
}

