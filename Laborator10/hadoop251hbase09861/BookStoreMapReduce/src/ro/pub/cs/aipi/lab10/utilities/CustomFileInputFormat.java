package ro.pub.cs.aipi.lab10.utilities;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class CustomFileInputFormat extends FileInputFormat<Text,LongWritable> {
 
    @Override
    public RecordReader<Text, LongWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return new CustomFileInputFormatRecordReader();
    }

}