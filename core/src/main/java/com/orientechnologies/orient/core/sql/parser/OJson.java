/* Generated By:JJTree: Do not edit this line. OJson.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.executor.OResult;

import java.util.*;

public class OJson extends SimpleNode {

  protected List<OJsonItem> items = new ArrayList<OJsonItem>();

  public OJson(int id) {
    super(id);
  }

  public OJson(OrientSql p, int id) {
    super(p, id);
  }

  /**
   * Accept the visitor. *
   */
  public Object jjtAccept(OrientSqlVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public void toString(Map<Object, Object> params, StringBuilder builder) {
    builder.append("{");
    boolean first = true;
    for (OJsonItem item : items) {
      if (!first) {
        builder.append(", ");
      }
      item.toString(params, builder);

      first = false;
    }
    builder.append("}");
  }

  public ODocument toDocument(OIdentifiable source, OCommandContext ctx) {
    String className = getClassNameForDocument(ctx);
    ODocument doc;
    if (className != null) {
      doc = new ODocument(className);
    } else {
      doc = new ODocument();
    }
    for (OJsonItem item : items) {
      String name = item.getLeftValue();
      if (name == null) {
        continue;
      }
      Object value;
      if (item.right.value instanceof OJson) {
        value = ((OJson) item.right.value).toDocument(source, ctx);
      } else {
        value = item.right.execute(source, ctx);
      }
      doc.field(name, value);
    }

    return doc;
  }

  public Map<String, Object> toMap(OIdentifiable source, OCommandContext ctx) {
    Map<String, Object> doc = new HashMap<String, Object>();
    for (OJsonItem item : items) {
      String name = item.getLeftValue();
      if (name == null) {
        continue;
      }
      Object value = item.right.execute(source, ctx);
      doc.put(name, value);
    }

    return doc;
  }

  public Map<String, Object> toMap(OResult source, OCommandContext ctx) {
    Map<String, Object> doc = new HashMap<String, Object>();
    for (OJsonItem item : items) {
      String name = item.getLeftValue();
      if (name == null) {
        continue;
      }
      Object value = item.right.execute(source, ctx);
      doc.put(name, value);
    }

    return doc;
  }

  private String getClassNameForDocument(OCommandContext ctx) {
    for (OJsonItem item : items) {
      String left = item.getLeftValue();
      if (left.toLowerCase().equals("@class")) {
        return "" + item.right.execute((OResult) null, ctx);
      }
    }
    return null;
  }

  public boolean needsAliases(Set<String> aliases) {
    for (OJsonItem item : items) {
      if (item.needsAliases(aliases)) {
        return true;
      }
    }
    return false;
  }

  public boolean isAggregate() {
    for (OJsonItem item : items) {
      if (item.isAggregate()) {
        return true;
      }
    }
    return false;
  }

  public OJson splitForAggregation(AggregateProjectionSplit aggregateSplit) {
    if(isAggregate()){
      OJson result = new OJson(-1);
      for(OJsonItem item:items){
        result.items.add(item.splitForAggregation(aggregateSplit));
      }
      return result;
    }else {
      return this;
    }
  }
}
/* JavaCC - OriginalChecksum=3beec9f6db486de944498588b51a505d (do not edit this line) */
