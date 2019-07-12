/**
 * 日    期：12-2-27
 */
package com.rop.marshaller;

import com.zlw.commons.core.ResultData;
import com.rop.RopException;
import com.rop.RopMarshaller;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

/**
 * <pre>
 *    将响应对象流化成JSON。 {@link ObjectMapper}是线程安全的。
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public class JacksonJsonRopMarshaller implements RopMarshaller {

    private static ObjectMapper objectMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void marshaller(Object object, OutputStream outputStream) {
        try {
            JsonGenerator jsonGenerator = getObjectMapper().getJsonFactory().createJsonGenerator(outputStream, JsonEncoding.UTF8);
            if( object instanceof ResultData){
            	object = (ResultData)object;
            	Object data = ((ResultData) object).getData();
            	if( data == null ){
            		 ((ResultData) object).setData(new HashMap<String,String>());
            	}
            }
            getObjectMapper().writeValue(jsonGenerator, object);
        } catch (IOException e) {
            throw new RopException(e);
        }
    }

    @SuppressWarnings("static-access")
	private ObjectMapper getObjectMapper() throws IOException {
        if (this.objectMapper == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            AnnotationIntrospector introspector = new JaxbAnnotationIntrospector();
            SerializationConfig serializationConfig = objectMapper.getSerializationConfig();
            serializationConfig = serializationConfig.without(SerializationConfig.Feature.WRAP_ROOT_VALUE)
                    .with(SerializationConfig.Feature.INDENT_OUTPUT)
                    .withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL) //为null的不序列化
//                    .withSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY) //为null和“”的不序列化
                    .withAnnotationIntrospector(introspector);
            objectMapper.setSerializationConfig(serializationConfig);
//            objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//                @Override
//                public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//                	gen.writeString("");
//                }
//            });

            this.objectMapper = objectMapper;
        }
        return this.objectMapper;
    }
    
    
    
}

