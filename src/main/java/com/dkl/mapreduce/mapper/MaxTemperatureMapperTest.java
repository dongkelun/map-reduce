package com.dkl.mapreduce.mapper;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

import test.MaxTemperatureMapper;

import com.dkl.mapreduce.reducer.MaxTemperatureReducer;

public class MaxTemperatureMapperTest {

	@Test
	public void processesValidRecord() throws IOException, InterruptedException {
		Text value = new Text("10231950adjkl+00011asd123qe");
		new MapDriver<LongWritable, Text, Text, IntWritable>()
				.withMapper(new MaxTemperatureMapper()).withInputValue(value)
				.withOutput(new Text("1950"), new IntWritable(11)).runTest();
	}

	@Test
	public void returnsMax() throws IOException, InterruptedException {
		new ReduceDriver<Text, IntWritable, Text, IntWritable>()
				.withReducer(
						 new MaxTemperatureReducer())
				.withInputKey(new Text("1950"))
				.withInputValues(
						Arrays.asList(new IntWritable(10), new IntWritable(5)))
				.withOutput(new Text("1950"), new IntWritable(10)).runTest();

	}
}
