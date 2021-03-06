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

import org.codehaus.enunciate.json.JsonName;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonTypeIdResolver;
import org.gedcomx.common.AlternateId;
import org.gedcomx.common.GenealogicalResource;
import org.gedcomx.common.Note;
import org.gedcomx.common.URI;
import org.gedcomx.rt.JsonElementWrapper;
import org.gedcomx.rt.XmlTypeIdResolver;
import org.gedcomx.types.FactType;

import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * A person.
 *
 * @author Ryan Heaton
 */
@XmlRootElement
@JsonElementWrapper (name = "persons")
@JsonTypeInfo ( use =JsonTypeInfo.Id.CUSTOM, property = XmlTypeIdResolver.TYPE_PROPERTY_NAME)
@JsonTypeIdResolver (XmlTypeIdResolver.class)
@XmlType ( name = "Person", propOrder = { "persistentId", "alternateIds", "living", "genders", "names", "facts", "sources", "notes" } )
public class Person extends GenealogicalResource implements HasFacts, HasNotes, ReferencesSources {

  private URI persistentId;
  private List<AlternateId> alternateIds;
  private Boolean living;
  private List<Gender> genders;
  private List<Name> names;
  private List<Fact> facts;
  private List<SourceReference> sources;
  private List<Note> notes;

  /**
   * A long-term, persistent, globally unique identifier for this person.
   *
   * @return A long-term, persistent, globally unique identifier for this person.
   */
  @XmlSchemaType (name = "anyURI", namespace = XMLConstants.W3C_XML_SCHEMA_NS_URI)
  public URI getPersistentId() {
    return persistentId;
  }

  /**
   * A long-term, persistent, globally unique identifier for this person.
   *
   * @param persistentId A long-term, persistent, globally unique identifier for this person.
   */
  public void setPersistentId(URI persistentId) {
    this.persistentId = persistentId;
  }

  /**
   * The list of alternate ids of the person.
   *
   * @return The list of alternate ids of the person.
   */
  @XmlElement (name="alternateId")
  @JsonProperty ("alternateIds")
  @JsonName ("alternateIds")
  public List<AlternateId> getAlternateIds() {
    return alternateIds;
  }

  /**
   * The list of alternate ids of the person.
   *
   * @param alternateIds The list of alternate ids of the person.
   */
  @JsonProperty ("alternateIds")
  public void setAlternateIds(List<AlternateId> alternateIds) {
    this.alternateIds = alternateIds;
  }

  /**
   * Living status of the person as treated by the system. The value of this property is intended
   * to be based on a system-specific calculation and therefore has limited portability. Conclusions
   * about the living status of a person can be modeled with a fact.
   *
   * @return Living status of the person as treated by the system.
   */
  public Boolean getLiving() {
    return living;
  }

  /**
   * Living status of the person as treated by the system. The value of this property is intended
   * to be based on a system-specific calculation and therefore has limited portability. Conclusions
   * about the living status of a person can be modeled with a fact.
   *
   * @param living Living status of the person as treated by the system.
   */
  public void setLiving(Boolean living) {
    this.living = living;
  }

  /**
   * The gender conclusions for the person.
   *
   * @return The gender conclusions for the person.
   */
  @XmlElement(name="gender")
  @JsonProperty("genders")
  @JsonName("genders")
  public List<Gender> getGenders() {
    return genders;
  }

  /**
   * The gender conclusions for the person.
   *
   * @param genders The gender conclusions for the person.
   */
  @JsonProperty("genders")
  public void setGenders(List<Gender> genders) {
    this.genders = genders;
  }

  /**
   * The name conclusions for the person.
   *
   * @return The name conclusions for the person.
   */
  @XmlElement(name="name")
  @JsonProperty("names")
  @JsonName("names")
  public List<Name> getNames() {
    return names;
  }

  /**
   * The name conclusions for the person.
   *
   * @param names The name conclusions for the person.
   */
  @JsonProperty("names")
  public void setNames(List<Name> names) {
    this.names = names;
  }

  /**
   * The fact conclusions for the person.
   *
   * @return The fact conclusions for the person.
   */
  @XmlElement(name="fact")
  @JsonProperty("facts")
  @JsonName("facts")
  public List<Fact> getFacts() {
    return facts;
  }

  /**
   * Helper method for obtaining specific fact conclusions.
   *
   * @param factType The type of facts to return.
   * @return The fact conclusions that match the factType. An empty list will be returned if no facts are found.
   */
  @JsonIgnore
  public List<Fact> getFacts(FactType factType) {
    ArrayList<Fact> factsToReturn = new ArrayList<Fact>();
    if (facts != null && factType != null) {
      for (Fact fact : facts) {
        if (fact.getKnownType() != null && fact.getKnownType().equals(factType)) {
          factsToReturn.add(fact);
        }
      }
    }
    return factsToReturn;
  }

  /**
   * The fact conclusions for the person.
   *
   * @param facts The fact conclusions for the person.
   */
  @JsonProperty("facts")
  public void setFacts(List<Fact> facts) {
    this.facts = facts;
  }

  /**
   * Add a fact conclusion to the person.
   *
   * @param fact The fact conclusion to be added.
   */
  public void addFact(Fact fact) {
    if (fact != null) {
      if (facts == null) {
        facts = new ArrayList<Fact>();
      }
      facts.add(fact);
    }
  }

  /**
   * The source references for a resource.
   *
   * @return The source references for a resource.
   */
  @XmlElement (name="source")
  @JsonProperty ("sources")
  @JsonName ("sources")
  public List<SourceReference> getSources() {
    return sources;
  }

  /**
   * The source references for a person.
   *
   * @param sources The source references for a person.
   */
  @JsonProperty("sources")
  public void setSources(List<SourceReference> sources) {
    this.sources = sources;
  }

  /**
   * Notes about a person.
   *
   * @return Notes about a person.
   */
  @XmlElement (name = "note")
  @JsonProperty ("notes")
  @JsonName ("notes")
  public List<Note> getNotes() {
    return notes;
  }

  /**
   * Notes about a person.
   *
   * @param notes Notes about a person.
   */
  @JsonProperty ("notes")
  public void setNotes(List<Note> notes) {
    this.notes = notes;
  }
}
