package ro.pub.cs.aipi.lab10.general;

public interface Constants {

	final static String		FILE									= "file";
	final static String		TABLE									= "table";
	final static String		RESULTS_DESTINATION						= TABLE; // available options: FILE, TABLE
	
	final static int 		REDUCE_TASKS_NUMBER						= 1;
	
	final static int		CACHING_VALUE							= 500;
	final static boolean	CACHE_BLOCKS_VALUE						= false;
	
	final static String		KEY_VALUE_SEPARATOR_PROPERTY_NAME		= "mapreduce.input.keyvaluelinerecordreader.key.value.separator";
	final static String 	KEY_VALUE_SEPARATOR_VALUE				= "\t";
	
	final static String		INVOICE_TABLE_NAME						= "invoice";
	final static String		CONSUMER_FAMILY_COLUMN					= "consumer";
	final static String		PERSONAL_IDENTIFIER_COLUMN				= "personal_identifier";
	
	final static String 	INVOICE_DETAIL_TABLE_NAME				= "invoice_detail";
	final static String		REFERENCE_FAMILY_COLUMN					= "reference";
	final static String		INVOICE_IDENTIFIER_COLUMN				= "invoice_id";
	final static String		CONTENT_FAMILY_NAME						= "content";
	final static String		BOOK_IDENTIFIER_COLUMN					= "book_id";
	final static String		QUANTITY_COLUMN							= "quantity";
	
	final static String		BOOKS_TABLE_NAME						= "book";
	final static String		INVENTORY_FAMILY_COLUMN					= "inventory";
	final static String		PRICE_COLUMN							= "price";
	
	final static String		USERS_TABLE_NAME						= "user";
	final static String		APPELLATION_FAMILY_COLUMN				= "appellation";	
	final static String		FIRST_NAME_COLUMN						= "first_name";
	final static String		LAST_NAME_COLUMN						= "last_name";
	
	final static String		STATISTICS_TABLE_NAME					= "statistics";
	final static String		EXPENSE_FAMILY_COLUMN					= "expense";
	final static String		VALUE_COLUMN							= "value";
	
	final static String 	BILL_VALUE_JOB_NAME						= "billvalue";
	final static String 	BILL_VALUE_OUTPUT_PATH					= "hdfs://localhost:9000/user/aipi2014/billValues";
	final static String 	BILL_VALUE_EXCEPTION_MESSAGE			= "Error running bill value job!";
	
	final static String 	USER_EXPENSES_JOB_NAME					= "userexpenses";
	final static String 	USER_EXPENSES_OUTPUT_PATH				= "hdfs://localhost:9000/user/aipi2014/userExpenses";
	final static String 	USER_EXPENSES_EXCEPTION_MESSAGE			= "Error running user expenses job!";

}
