package com.capitalone.dmsl.lineage.model.emr

import com.amazonaws.services.elasticmapreduce.model._

import scala.collection.JavaConverters._

case class JobInstancesConfig(ec2SubnetIds: List[String],
                                instanceGroups: List[InstanceConfig],
                                keepJobFlowAliveWhenNoSteps: Boolean,
                                terminationProtected: Boolean) {

  val jobFlowInstancesConfig: JobFlowInstancesConfig =
    new JobFlowInstancesConfig()
      .withEc2SubnetIds(ec2SubnetIds.asJava)
      .withInstanceGroups(instanceGroups.map(ig => ig.instanceGroupConfig).asJava)
      .withKeepJobFlowAliveWhenNoSteps(keepJobFlowAliveWhenNoSteps)
      .withTerminationProtected(terminationProtected)

}

case class InstanceConfig(ebsConfiguration: EbsConfig,
                             instanceCount: Int,
                             instanceRole: String,
                             instanceType: String,
                             name: String) {

  val instanceGroupConfig: InstanceGroupConfig =
    new InstanceGroupConfig()
      .withEbsConfiguration(ebsConfiguration.ebsConfig)
      .withInstanceCount(instanceCount)
      .withInstanceRole(instanceRole)
      .withInstanceType(instanceType)
      .withName(name)

}

case class EbsConfig(ebsBlockDeviceConfigs: List[EbsBlockConfig]) {

  val ebsConfig: EbsConfiguration =
    new EbsConfiguration()
      .withEbsBlockDeviceConfigs(ebsBlockDeviceConfigs.map(ebs => ebs.awsEbsBlockDeviceConfig).asJava)

}

case class EbsBlockConfig(volumeSpecification: VolumeSpecConfig, volumesPerInstance: Int) {

  val awsEbsBlockDeviceConfig: EbsBlockDeviceConfig =
    new EbsBlockDeviceConfig()
      .withVolumeSpecification(volumeSpecification.ebsVolumeSpecification)
      .withVolumesPerInstance(volumesPerInstance)

}

case class VolumeSpecConfig(sizeInGB: Int, volumeType: String) {

  val ebsVolumeSpecification: VolumeSpecification =
    new VolumeSpecification()
      .withSizeInGB(sizeInGB)
      .withVolumeType(volumeType)

}
