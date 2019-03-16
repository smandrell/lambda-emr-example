package com.capitalone.dmsl.lineage.model.emr

import com.amazonaws.services.elasticmapreduce.model.Tag

case class TagConfig(key: String, value: String) {

  val tag: Tag =
    new Tag()
      .withKey(key)
      .withValue(value)

}
