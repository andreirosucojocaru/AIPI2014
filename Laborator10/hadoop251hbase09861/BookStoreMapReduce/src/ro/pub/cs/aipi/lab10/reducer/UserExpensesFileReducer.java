package ro.pub.cs.aipi.lab10.reducer;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class UserExpensesFileReducer extends Reducer<Text, LongWritable, Text, LongWritable> {

	public void reduce(Text userFirstLastName, Iterable<LongWritable> billValues, Context context) throws IOException, InterruptedException {
		long userExpense = 0;
		for (LongWritable billValue : billValues)
			userExpense += billValue.get();
		
		context.write(userFirstLastName, new LongWritable(userExpense));
	}

}
