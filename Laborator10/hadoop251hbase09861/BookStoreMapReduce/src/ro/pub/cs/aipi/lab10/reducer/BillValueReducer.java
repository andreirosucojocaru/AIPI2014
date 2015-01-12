package ro.pub.cs.aipi.lab10.reducer;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import ro.pub.cs.aipi.lab10.general.Constants;

public class BillValueReducer extends Reducer<Text, LongWritable, Text, LongWritable>  {

	public void reduce(Text billIdentifier, Iterable<LongWritable> billProductValues, Context context) throws IOException, InterruptedException {
		long billValue = 0;
		for (LongWritable billProductValue: billProductValues)
			billValue += billProductValue.get();
		
		Configuration configuration = HBaseConfiguration.create();
      	
		HTable billsTable = new HTable(configuration, Constants.INVOICE_TABLE_NAME);
      	Get get1 = new Get(billIdentifier.toString().getBytes());
      	get1.addColumn(Constants.CONSUMER_FAMILY_COLUMN.getBytes(), Constants.PERSONAL_IDENTIFIER_COLUMN.getBytes());
      	Result result1 = billsTable.get(get1);
      	String personalIdentifier = new String(result1.getValue(Constants.CONSUMER_FAMILY_COLUMN.getBytes(),Constants.PERSONAL_IDENTIFIER_COLUMN.getBytes()));
      	billsTable.close();
      	
      	HTable usersTable = new HTable(configuration, Constants.USERS_TABLE_NAME);
      	Get get2 = new Get(personalIdentifier.getBytes());
      	get2.addColumn(Constants.APPELLATION_FAMILY_COLUMN.getBytes(),Constants.LAST_NAME_COLUMN.getBytes());
      	get2.addColumn(Constants.APPELLATION_FAMILY_COLUMN.getBytes(),Constants.FIRST_NAME_COLUMN.getBytes());
      	Result result2 = usersTable.get(get2);
      	String userFirstLastName = new String(result2.getValue(Constants.APPELLATION_FAMILY_COLUMN.getBytes(),Constants.FIRST_NAME_COLUMN.getBytes()))+" "+new String(result2.getValue(Constants.APPELLATION_FAMILY_COLUMN.getBytes(),Constants.LAST_NAME_COLUMN.getBytes()));
      	usersTable.close();
      	
      	context.write(new Text(userFirstLastName.getBytes()), new LongWritable(billValue));
	}
}
  
  
