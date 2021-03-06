/**
 * Copyright 2011-2012 Intellectual Reserve, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gedcomx.conclusion;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonTypeIdResolver;
import org.gedcomx.common.Attributable;
import org.gedcomx.common.Attribution;
import org.gedcomx.common.ResourceReference;
import org.gedcomx.rt.CommonModels;
import org.gedcomx.rt.JsonElementWrapper;
import org.gedcomx.rt.RDFRange;
import org.gedcomx.rt.XmlTypeIdResolver;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * An attributable reference to a source for genealogical conclusions.
 *
 * @author Ryan Heaton
 */
@XmlRootElement
@JsonElementWrapper ( name = "attribution" )
@JsonTypeInfo ( use = JsonTypeInfo.Id.CUSTOM, property = XmlTypeIdResolver.TYPE_PROPERTY_NAME)
@JsonTypeIdResolver (XmlTypeIdResolver.class)
@XmlType ( name = "SourceReference" )
public class SourceReference extends ResourceReference implements Attributable {

  private Attribution attribution;
  private ResourceReference description;

  /**
   * The attribution metadata for this source reference.
   *
   * @return The attribution metadata for this source reference.
   */
  @XmlElement ( namespace = CommonModels.GEDCOMX_COMMON_NAMESPACE )
  public Attribution getAttribution() {
    return attribution;
  }

  /**
   * The attribution metadata for this source reference.
   *
   * @param attribution The attribution metadata for this source reference.
   */
  public void setAttribution(Attribution attribution) {
    this.attribution = attribution;
  }

  /**
   * A reference to a description about the source being referenced.
   *
   * @return A reference to a description about the source being referenced.
   */
  @RDFRange( external = "org.gedcomx.metadata.rdf.RDFDescription" )
  public ResourceReference getDescription() {
    return description;
  }

  /**
   * A reference to a description about the source being referenced.
   *
   * @param description A reference to a description about the source being referenced.
   */
  public void setDescription(ResourceReference description) {
    this.description = description;
  }
}
