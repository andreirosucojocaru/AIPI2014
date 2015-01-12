package ro.pub.cs.aipi.lab10.mapper;

import java.io.IOException;

import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class UserExpensesMapper extends Mapper<Text, LongWritable, Text, LongWritable>  {

   	public void map(ImmutableBytesWritable userFirstLastName, LongWritable billValues, Context context) throws IOException, InterruptedException {
   		context.write(new Text(userFirstLastName.get()), billValues);
   	}
}
   
   
