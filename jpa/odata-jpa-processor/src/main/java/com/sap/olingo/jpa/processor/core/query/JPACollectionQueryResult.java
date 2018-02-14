package com.sap.olingo.jpa.processor.core.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;

import org.apache.olingo.server.api.ODataApplicationException;

import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPAAssociationPath;
import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPAEntityType;
import com.sap.olingo.jpa.processor.core.converter.JPACollectionResult;
import com.sap.olingo.jpa.processor.core.converter.JPAExpandResult;
import com.sap.olingo.jpa.processor.core.converter.JPATupleChildConverter;

public class JPACollectionQueryResult implements JPAExpandResult, JPACollectionResult {
  private final Map<JPAAssociationPath, JPAExpandResult> childrenResult;
  private final Map<String, List<Tuple>> jpaResult;
  private Map<String, List<Object>> collectionResult;
  private final Map<String, Long> counts;
  private final JPAEntityType jpaEntityType;
  private final JPAAssociationPath assoziation;

  public JPACollectionQueryResult(final Map<String, List<Tuple>> result, final Map<String, Long> counts,
      final JPAEntityType jpaEntityType, final JPAAssociationPath assoziation) {
    super();
    this.childrenResult = new HashMap<>(1);
    this.jpaResult = result;
    this.counts = counts;
    this.jpaEntityType = jpaEntityType;
    this.assoziation = assoziation;
  }

  @Override
  public JPAExpandResult getChild(final JPAAssociationPath associationPath) {
    return null;
  }

  @Override
  public Map<JPAAssociationPath, JPAExpandResult> getChildren() {
    return childrenResult;
  }

  @Override
  public Long getCount(final String key) {
    return counts != null ? counts.get(key) : null;
  }

  @Override
  public JPAEntityType getEntityType() {
    return jpaEntityType;
  }

  @Override
  public List<Tuple> getResult(final String key) {
    return jpaResult.get(key);
  }

  @Override
  public Map<String, List<Tuple>> getResults() {
    return jpaResult;
  }

  @Override
  public boolean hasCount() {
    return counts != null;
  }

  @Override
  public List<Object> getPropertyCollection(final String key) {
    return collectionResult.containsKey(key) ? collectionResult.get(key) : new ArrayList<>(1);
  }

  @Override
  public void convert(JPATupleChildConverter converter) throws ODataApplicationException {
    this.collectionResult = converter.getCollectionResult(this);
  }

  @Override
  public JPAAssociationPath getAssoziation() {
    return assoziation;
  }
}
