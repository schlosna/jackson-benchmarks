package com.fasterxml.jackson.perf.avro;

import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Scope;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.avro.*;
import com.fasterxml.jackson.dataformat.avro.schema.AvroSchemaGenerator;
import com.fasterxml.jackson.perf.ReadPerfBaseBasicJackson;
import com.fasterxml.jackson.perf.data.MinimalInputConverter;
import com.fasterxml.jackson.perf.model.MediaItem;

@State(Scope.Group) // Thread, Group or Benchmark
public class AvroStdReadVanilla
    extends ReadPerfBaseBasicJackson
{
    private final static AvroFactory _f = new AvroFactory();
    private static final ObjectMapper MAPPER = new ObjectMapper(_f);

    private final static AvroSchema _mediaItemSchema;
    static {
	    AvroSchemaGenerator gen = new AvroSchemaGenerator();
	    try {
	    	MAPPER.acceptJsonFormatVisitor(MediaItem.class, gen);
	    } catch (Exception e) {
	    	throw new RuntimeException(e);
	    }
	    _mediaItemSchema = gen.getGeneratedSchema();
    }

    private final static MinimalInputConverter AVROS = MinimalInputConverter.minimalConverter(MAPPER, _mediaItemSchema);

    public AvroStdReadVanilla() {
        super(AVROS, MAPPER, _mediaItemSchema);
    }
}
