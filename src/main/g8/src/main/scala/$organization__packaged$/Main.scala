package $organization$

import org.apache.flink.streaming.api.functions.sink.SinkFunction
import org.apache.flink.streaming.api.scala.DataStream
import org.codefeedr.pipeline.PipelineBuilder
import org.codefeedr.stages.OutputStage
import org.codefeedr.stages.utilities.{StringInput, StringType}

object Main {
  def main(args: Array[String]): Unit = {
    new PipelineBuilder()
      .append(new StringInput("Hello World! How are you doing?"))
      .append (new WordCountOutput)
      .build()
      .start(args)
  }
}

class WordCountOutput extends OutputStage[StringType] {
  override def main(source: DataStream[StringType]): Unit = {
    source
      .map { item => (item.value, 1) }
      .keyBy(0)
      .sum(1)
      .print()
  }
}