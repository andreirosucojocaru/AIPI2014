package ro.pub.cs.aipi.lab10.reducer;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import ro.pub.cs.aipi.lab10.general.Constants;

public class UserExpensesTableReducer extends TableReducer<Text, LongWritable, ImmutableBytesWritable>  {
   	
	public void reduce(Text userFirstLastName, Iterable<LongWritable> billValues, Context context) throws IOException, InterruptedException {
		long userExpense = 0;
		for (LongWritable billValue: billValues)
			userExpense += billValue.get();

		Put put = new Put(Bytes.toBytes(userFirstLastName.toString()));
		put.add(Constants.EXPENSE_FAMILY_COLUMN.getBytes(), Constants.VALUE_COLUMN.getBytes(), Long.toString(userExpense).getBytes());
		context.write(null, put);
	}
}
  