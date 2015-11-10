package test;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable>  {

	public void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException{
		String line = value.toString();
		String year = line.substring(4, 8);
		int airTemperature = Integer.parseInt(line.substring(13, 19));
		context.write(new Text(year), new IntWritable(airTemperature));
	}
		
}
