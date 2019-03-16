package com.capitalone.dmsl.lineage.model.emr

import com.amazonaws.services.elasticmapreduce.model.{BootstrapActionConfig, ScriptBootstrapActionConfig}

import collection.JavaConverters._

case class BootstrapConfig(name: String, scriptBootstrapAction: ScriptBootstrapActionConf) {

  val bootstrapActionConfig: BootstrapActionConfig =
    new BootstrapActionConfig()
      .withName(name)
      .withScriptBootstrapAction(scriptBootstrapAction.scriptBootstrapAction)

}

case class ScriptBootstrapActionConf(path: String, args: List[String] = List()) {

  val scriptBootstrapAction: ScriptBootstrapActionConfig =
    new ScripdtBootstrapActionConfig()
      .withPath(path)
      .withArgs(args.asJava)

}
