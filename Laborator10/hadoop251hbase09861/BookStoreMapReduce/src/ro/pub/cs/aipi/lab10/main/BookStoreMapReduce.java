package ro.pub.cs.aipi.lab10.main;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import ro.pub.cs.aipi.lab10.general.Constants;
import ro.pub.cs.aipi.lab10.mapper.BillValueMapper;
import ro.pub.cs.aipi.lab10.mapper.UserExpensesMapper;
import ro.pub.cs.aipi.lab10.reducer.BillValueReducer;
import ro.pub.cs.aipi.lab10.reducer.UserExpensesFileReducer;
import ro.pub.cs.aipi.lab10.reducer.UserExpensesTableReducer;
import ro.pub.cs.aipi.lab10.utilities.CustomFileInputFormat;


public class BookStoreMapReduce {

	public static void main(String[] args) throws Exception {
		Configuration configuration = HBaseConfiguration.create();
		configuration.set(Constants.KEY_VALUE_SEPARATOR_PROPERTY_NAME, Constants.KEY_VALUE_SEPARATOR_VALUE);
		
		Job billValue = Job.getInstance(configuration, Constants.BILL_VALUE_JOB_NAME);
		billValue.setJarByClass(BookStoreMapReduce.class);
		Scan scan = new Scan();
		scan.setCaching(Constants.CACHING_VALUE);
		scan.setCacheBlocks(Constants.CACHE_BLOCKS_VALUE);
		TableMapReduceUtil.initTableMapperJob(
			Constants.INVOICE_DETAIL_TABLE_NAME,
			scan,
			BillValueMapper.class,
			Text.class,
			LongWritable.class,
			billValue
		);
		billValue.setReducerClass(BillValueReducer.class);
		billValue.setNumReduceTasks(Constants.REDUCE_TASKS_NUMBER);
		FileOutputFormat.setOutputPath(billValue, new Path(Constants.BILL_VALUE_OUTPUT_PATH));
		boolean billValueResult = billValue.waitForCompletion(true);
		if (!billValueResult)
			throw new IOException(Constants.BILL_VALUE_EXCEPTION_MESSAGE);
		
		Job userExpenses = Job.getInstance(configuration, Constants.USER_EXPENSES_JOB_NAME);
		userExpenses.setJarByClass(BookStoreMapReduce.class);
		switch (Constants.RESULTS_DESTINATION) {
			case Constants.FILE:
				userExpenses.setMapperClass(UserExpensesMapper.class);
				userExpenses.setReducerClass(UserExpensesFileReducer.class);
				userExpenses.setNumReduceTasks(Constants.REDUCE_TASKS_NUMBER);
				userExpenses.setOutputFormatClass(TextOutputFormat.class);
				userExpenses.setOutputKeyClass(Text.class);
				userExpenses.setOutputValueClass(LongWritable.class);
				FileOutputFormat.setOutputPath(userExpenses, new Path(Constants.USER_EXPENSES_OUTPUT_PATH));
				break;
			case Constants.TABLE:
				userExpenses.setMapOutputKeyClass(Text.class);
				userExpenses.setMapOutputValueClass(LongWritable.class);
				TableMapReduceUtil.initTableReducerJob(
						Constants.STATISTICS_TABLE_NAME,
						UserExpensesTableReducer.class,
						userExpenses);
				userExpenses.setNumReduceTasks(Constants.REDUCE_TASKS_NUMBER);
				break;
		}
		FileInputFormat.setInputPaths(userExpenses, new Path(Constants.BILL_VALUE_OUTPUT_PATH));
		userExpenses.setInputFormatClass(CustomFileInputFormat.class);
		boolean userExpensesResult = userExpenses.waitForCompletion(true);
		if (!userExpensesResult)
			throw new IOException(Constants.USER_EXPENSES_EXCEPTION_MESSAGE);
	}
}
