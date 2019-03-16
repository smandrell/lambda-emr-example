import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.elasticmapreduce.model._
import com.amazonaws.services.elasticmapreduce.{AmazonElasticMapReduce, AmazonElasticMapReduceClientBuilder}
import com.typesafe.config.Config

import collection.JavaConverters._

object EmrUtils {

  def createEmrClient(region: String, credentials: DefaultAWSCredentialsProviderChain): AmazonElasticMapReduce = {

    AmazonElasticMapReduceClientBuilder
      .standard()
      .withCredentials(credentials)
      .withRegion(region)
      .build()
  }

  def createRunJobFlowRequest(): RunJobFlowRequest = {

    // Declare spark application for RunJobFlowRequest
    val spark = new Application().withName("Spark")

    // Create bootstrap actions for RunJobFlowRequest
    val bootstrapActions = List(
      new BootstrapActionConfig()
        .withName("BootstrapAction1")
        .withScriptBootstrapAction(
          new ScriptBootstrapActionConfig().withPath("s3://yourBucket/path/to/bootstrapAction1.sh")),
      new BootstrapActionConfig()
        .withName("BootstrapAction2")
        .withScriptBootstrapAction(
          new ScriptBootstrapActionConfig()
            .withPath("s3://yourBucket/path/to/bootStrapAction2.sh")
        ).asJava

    // Create EBS Master config for InstanceGroups
    val ebsConfigMaster = new InstanceGroupConfig()
      .withEbsConfiguration(
        new EbsConfiguration()
          .withEbsBlockDeviceConfigs(
            List(
              new EbsBlockDeviceConfig()
                .withVolumeSpecification(new VolumeSpecification()
                  .withSizeInGB(10)
                  .withVolumeType("gp2"))
                .withVolumesPerInstance(1)).asJava))
      .withInstanceCount(1)
      .withInstanceRole("MASTER")
      .withInstanceType("m4.4xlarge")
      .withName("MasterGroup")

    // Create EBS Core config for InstanceGroups
    val ebsConfigCore = new InstanceGroupConfig()
      .withEbsConfiguration(
        new EbsConfiguration()
          .withEbsBlockDeviceConfigs(
            List(
              new EbsBlockDeviceConfig()
                .withVolumeSpecification(new VolumeSpecification()
                  .withSizeInGB(10)
                  .withVolumeType("gp2"))
                .withVolumesPerInstance(1)).asJava))
      .withInstanceCount(5)
      .withInstanceRole("CORE")
      .withInstanceType("m4.4xlarge")
      .withName("CoreGroup")

    // Create instance groups for Instance config
    val instanceGroups = List(ebsConfigMaster, ebsConfigCore).asJava

    // Create Instance config
    val instanceConfig = new JobFlowInstancesConfig()
      .withEc2SubnetIds(List("subnet-12345").asJava)
      .withInstanceGroups(instanceGroups)
      .withKeepJobFlowAliveWhenNoSteps(false)
      .withTerminationProtected(false)

    // Create tags for RunJobFlowRequest
    val tags = List(
      new Tag().withKey("Key1").withValue("Value1"),
      new Tag().withKey("Key2").withValue("Value2"),
      new Tag().withKey("OwnerContact").withValue("youEmail@example.com")
    ).asJava

    // Additional Info JSON string for RunJobFlowRequest
    val additionalInfoStr: String =
      s"""
         |{
         |  "instanceAwsClientConfiguration": {
         |    "proxyHost": "your.proxyHost.com",
         |    "proxyPort": "8099"
         |  }
         | }
          """.stripMargin

    // Create spark steps
    val sparkSteps = new StepConfig()
      .withName("Spark")
      .withActionOnFailure("TERMINATE")
      .withHadoopJarStep(
        new HadoopJarStepConfig()
          .withJar("command-runner.jar")
          .withArgs(
            "spark-submit",
            "--master",
            "yarn",
            "--deploy-mode",
            "cluster",
            "--class",
            "your.package.name.Main",
            "arg1",
            "arg2",
            "arg3"
          )
      )

    // Create RunJobFlowRequest
    new RunJobFlowRequest()
      .withAdditionalInfo(additionalInfoStr)
      .withApplications(spark)
      .withAutoScalingRole("EMR_AutoScaling_DefaultRole")
      .withBootstrapActions(bootstrapActions)
      .withCustomAmiId("ami-12345")
      .withInstances(instanceConfig)
      .withJobFlowRole("Role-For-Spark-Job")
      .withLogUri("s3://yourBucket/path/to/emrLogs")
      .withName("name-of-spark-job")
      .withReleaseLabel("emr-5.19.0")
      .withRepoUpgradeOnBoot("SECURITY")
      .withSecurityConfiguration("Your-Security-Config")
      .withServiceRole("EMR_DefaultRole")
      .withSteps(sparkSteps)
      .withTags(tags)
      .withVisibleToAllUsers(true)

  }

}