package com.jovo.ScienceCenter.util;


import com.jovo.ScienceCenter.model.elasticsearch.SearchType;
import org.apache.lucene.queryparser.classic.ParseException;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;



public class QueryBuilder {
	
	private static int maxEdits = 1;
	
	public static int getMaxEdits(){
		return maxEdits;
	}
	
	public static void setMaxEdits(int maxEdits){
		QueryBuilder.maxEdits = maxEdits;
	}
	
	public static org.elasticsearch.index.query.QueryBuilder buildQuery(SearchType queryType, String field, String value) throws IllegalArgumentException, ParseException{
		String errorMessage = "";
		if(field == null || field.equals("")){
			errorMessage += "Field not specified";
		}
		if(value == null){
			if(!errorMessage.equals("")) errorMessage += "\n";
			errorMessage += "Value not specified";
		}
		if(!errorMessage.equals("")){
			throw new IllegalArgumentException(errorMessage);
		}
		
		org.elasticsearch.index.query.QueryBuilder retVal = null;
		if(queryType.equals(SearchType.regular)) {
			// Ovaj tip pretrage sluzi za pretrazivanje term-ova iz index-a.
			// Prilikom index-iranja na recima ce biti izvrseno preprocesirnaje,
			// a na upitu prilikom pretrazivanja, nece biti izvrseno preprocesiranje.
			// Znaci ovaj tip pretrage ce vratiti nesto jedino u slucaju kad u upitu navedemo
			// rec koja se u potpunosti poklapa sa nekim od term-ova iz inedx-a
			retVal = QueryBuilders.termQuery(field, value);
		}else if(queryType.equals(SearchType.match)) {
			// To search text field values, use the match query instead term query.
			// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-term-query.html
			// Za ovaj tip pretrage, isto preprocesiranje se izvrsava i prilikom indeksiranja i prilikom pretrage
			retVal = QueryBuilders.matchQuery(field, value);
		}else if(queryType.equals(SearchType.fuzzy)){
			retVal = QueryBuilders.fuzzyQuery(field, value).fuzziness(Fuzziness.fromEdits(maxEdits));
		}else if(queryType.equals(SearchType.prefix)){
			retVal = QueryBuilders.prefixQuery(field, value);
		}else if(queryType.equals(SearchType.range)){
			String[] values = value.split(" ");
			retVal = QueryBuilders.rangeQuery(field).from(values[0]).to(values[1]);
		}else if(queryType.equals(SearchType.moreLikeThis)){
			retVal = QueryBuilders.moreLikeThisQuery(new String[] { field }, new String[] { value }, null)
									.minTermFreq(1)
									.minDocFreq(1);
		}else if(queryType.equals(SearchType.geospatial)){
			String[] values = value.split(",");
			retVal = QueryBuilders.geoDistanceQuery(field)
									.point(Double.parseDouble(values[0]), Double.parseDouble(values[1]))
									.distance(100, DistanceUnit.KILOMETERS);
		}else{
			retVal = QueryBuilders.matchPhraseQuery(field, value);
		}

		return retVal;
	}

}
