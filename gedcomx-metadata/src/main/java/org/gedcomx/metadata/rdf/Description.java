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
package org.gedcomx.metadata.rdf;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonTypeIdResolver;
import org.gedcomx.common.URI;
import org.gedcomx.rt.CommonModels;
import org.gedcomx.rt.SupportsExtensionAttributes;
import org.gedcomx.rt.SupportsExtensionElements;
import org.gedcomx.rt.XmlTypeIdResolver;
import org.gedcomx.types.ResourceType;
import org.gedcomx.types.TypeReference;

import javax.xml.XMLConstants;
import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A description of a resource.
 *
 * @author Ryan Heaton
 */
@XmlRootElement( name = "Description" )
@XmlType ( name = "Description" )
@JsonTypeInfo ( use =JsonTypeInfo.Id.CUSTOM, property = XmlTypeIdResolver.TYPE_PROPERTY_NAME)
@JsonTypeIdResolver (XmlTypeIdResolver.class)
@XmlSeeAlso({RDFValue.class, RDFLiteral.class})
public class Description implements SupportsExtensionAttributes, SupportsExtensionElements {

  private String id;
  private URI about;
  private TypeReference<ResourceType> type;
  private Map<QName, String> extensionAttributes;
  private List<Object> extensionElements;

  /**
   * The id of this piece of metadata.
   *
   * @return The id of this piece of metadata.
   */
  @XmlAttribute( name = "ID" )
  @XmlID
  public String getId() {
    return id;
  }

  /**
   * The id of this piece of metadata.
   *
   * @param id The id of this piece of metadata.
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * URI to the resource that this metadata is describing.
   *
   * @return URI to the resource that this metadata is describing.
   */
  @XmlAttribute
  @XmlSchemaType(name = "anyURI", namespace = XMLConstants.W3C_XML_SCHEMA_NS_URI)
  public URI getAbout() {
    return about;
  }

  /**
   * URI to the resource that this metadata is describing.
   *
   * @param about URI to the resource that this metadata is describing.
   */
  public void setAbout(URI about) {
    this.about = about;
  }

  /**
   * The type of the resource being described.
   *
   * @return The type of the resource being described.
   */
  @XmlElement(name = "type", namespace = CommonModels.RDF_NAMESPACE )
  public TypeReference<ResourceType> getType() {
    return type;
  }

  /**
   * The type of the resource being described.
   *
   * @param type The type of the resource being described.
   */
  public void setType(TypeReference<ResourceType> type) {
    this.type = type;
  }

  /**
   * The enum referencing the known type of the resource being referenced, or {@link org.gedcomx.types.ResourceType#OTHER} if not known.
   *
   * @return The enum referencing the known type of the source reference, or {@link org.gedcomx.types.ResourceType#OTHER} if not known.
   */
  @XmlTransient
  @JsonIgnore
  public ResourceType getKnownType() {
    return getType() == null ? null : ResourceType.fromQNameURI(getType().getType());
  }

  /**
   * Set the type of this source reference from an enumeration of known source reference types.
   *
   * @param knownType The source reference type.
   */
  @JsonIgnore
  public void setKnownType(ResourceType knownType) {
    setType(knownType == null ? null : new TypeReference<ResourceType>(knownType));
  }

  /**
   * Custom attributes applicable as part of this metadata.
   *
   * @return Custom attributes applicable as part of this metadata.
   */
  @XmlAnyAttribute
  @JsonIgnore
  public Map<QName, String> getExtensionAttributes() {
    return extensionAttributes;
  }

  /**
   * Custom attributes applicable as part of this metadata.
   *
   * @param extensionAttributes Custom attributes applicable as part of this metadata.
   */
  @JsonIgnore
  public void setExtensionAttributes(Map<QName, String> extensionAttributes) {
    this.extensionAttributes = extensionAttributes;
  }

  /**
   * Add an extension element.
   *
   * @param element The extension element to add.
   */
  public void addExtensionElement(Object element) {
    if (this.extensionElements == null) {
      this.extensionElements = new ArrayList<Object>();
    }

    this.extensionElements.add(element);
  }

  /**
   * Finds the first extension of a specified type.
   *
   * @param clazz The type.
   * @return The extension, or null if none found.
   */
  @SuppressWarnings ( {"unchecked"} )
  public <E> E findExtensionOfType(Class<E> clazz) {
    if (this.extensionElements != null) {
      for (Object extension : extensionElements) {
        if (clazz.isInstance(extension)) {
          return (E) extension;
        }
      }
    }

    return null;
  }

  /**
   * Find the extensions of a specified type.
   *
   * @param clazz The type.
   * @return The extensions, possibly empty but not null.
   */
  @SuppressWarnings ( {"unchecked"} )
  public <E> List<E> findExtensionsOfType(Class<E> clazz) {
    List<E> ext = new ArrayList<E>();
    if (this.extensionElements != null) {
      for (Object extension : extensionElements) {
        if (clazz.isInstance(extension)) {
          ext.add((E) extension);
        }
      }
    }

    return ext;
  }

  /**
   * Custom elements applicable as part of this metadata.
   *
   * @return Custom elements applicable as part of this metadata.
   */
  @XmlAnyElement ( lax = true )
  @JsonIgnore
  public List<Object> getExtensionElements() {
    return extensionElements;
  }

  /**
   * Custom elements applicable as part of this metadata.
   *
   * @param extensionElements Custom elements applicable as part of this metadata.
   */
  @JsonIgnore
  public void setExtensionElements(List<Object> extensionElements) {
    this.extensionElements = extensionElements;
  }

  /**
   * Add a custom extension attribute.
   *
   * @param qname The qname of the attribute.
   * @param value The value of the attribute.
   */
  public void addExtensionAttribute(QName qname, String value) {
    if (this.extensionAttributes == null) {
      this.extensionAttributes = new HashMap<QName, String>();
    }

    this.extensionAttributes.put(qname, value);
  }
}
