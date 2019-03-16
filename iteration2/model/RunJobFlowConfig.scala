import com.amazonaws.services.elasticmapreduce.model._

import collection.JavaConverters._

case class RunJobFlowConfig(emrRegion: String,
                            sparkConfig: StepsConfig,
                            additionalInfo: String,
                            applications: List[ApplicationConfig],
                            autoScalingRole: String,
                            bootstrapActions: List[BootstrapConfig],
                            customAmiId: String,
                            instances: JobInstancesConfig,
                            jobFlowRole: String,
                            logUri: String,
                            name: String,
                            releaseLabel: String,
                            repoUpgradeOnBoot: String,
                            securityConfiguration: String,
                            serviceRole: String,
                            tags: List[TagConfig],
                            visibleToAllUsers: Boolean) {

  // Define AWS RunJobFlowRequest with RunJobFlowConfig values
  val runJobFlowRequest: RunJobFlowRequest =
    new RunJobFlowRequest()
      .withAdditionalInfo(additionalInfo)
      .withApplications(applications.map(app => app.emrApplication).asJava)
      .withAutoScalingRole(autoScalingRole)
      .withBootstrapActions(bootstrapActions.map(b => b.bootstrapActionConfig).asJava)
      .withCustomAmiId(ami)
      .withInstances(instances.jobFlowInstancesConfig)
      .withJobFlowRole(jobFlowRole)
      .withLogUri(logUri)
      .withName(name)
      .withReleaseLabel(releaseLabel)
      .withRepoUpgradeOnBoot(repoUpgradeOnBoot)
      .withSecurityConfiguration(securityConfiguration)
      .withServiceRole(serviceRole)
      .withSteps(sparkConfig.emrSteps)
      .withTags(tags.map(t => t.tag).asJava)
      .withVisibleToAllUsers(visibleToAllUsers)

}
