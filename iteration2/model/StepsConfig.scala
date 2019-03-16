import com.amazonaws.services.elasticmapreduce.model.{HadoopJarStepConfig, StepConfig}

import collection.JavaConverters._

case class StepsConfig(name: String,
                       actionOnFailure: String,
                       jar: String,
                       args: List[String]) {

  val emrSteps: StepConfig = {

    val hadoopJarStep = new HadoopJarStepConfig()
      .withJar(jar)
      .withArgs(args.asJava)

    new StepConfig()
      .withName(name)
      .withActionOnFailure(actionOnFailure)
      .withHadoopJarStep(hadoopJarStep)

  }

}
