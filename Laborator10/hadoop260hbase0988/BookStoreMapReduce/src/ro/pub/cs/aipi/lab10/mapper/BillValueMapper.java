package ro.pub.cs.aipi.lab10.mapper.bin;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.conf.Configuration;

import ro.pub.cs.aipi.lab10.general.Constants;

public class BillValueMapper extends TableMapper<Text, LongWritable>  {

   	public void map(ImmutableBytesWritable billDetailIdentifier, Result billDetailValue, Context context) throws IOException, InterruptedException {
   			Text billIdentifier = new Text(billDetailValue.getValue(Constants.REFERENCE_FAMILY_COLUMN.getBytes(), Constants.INVOICE_IDENTIFIER_COLUMN.getBytes()));
   			String bookIdentifier = new String(billDetailValue.getValue(Constants.CONTENT_FAMILY_NAME.getBytes(), Constants.BOOK_IDENTIFIER_COLUMN.getBytes()));       	
   			String bookQuantity = new String(billDetailValue.getValue(Constants.CONTENT_FAMILY_NAME.getBytes(), Constants.QUANTITY_COLUMN.getBytes()));
   			
   			Configuration configuration = HBaseConfiguration.create();
          	
   			HTable booksTable = new HTable(configuration,Constants.BOOKS_TABLE_NAME);
          	Get get = new Get(bookIdentifier.getBytes());
          	get.addColumn(Constants.INVENTORY_FAMILY_COLUMN.getBytes(),Constants.PRICE_COLUMN.getBytes());
          	Result result = booksTable.get(get);
          	String bookPrice = new String(result.getValue(Constants.INVENTORY_FAMILY_COLUMN.getBytes(),Constants.PRICE_COLUMN.getBytes()));
        	booksTable.close();
          	
        	Long billProductValue = Long.parseLong(bookPrice)*Long.parseLong(bookQuantity);
        	context.write(billIdentifier, new LongWritable(billProductValue));
   	}
}
   
   
