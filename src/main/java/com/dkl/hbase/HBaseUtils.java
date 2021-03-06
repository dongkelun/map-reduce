package com.dkl.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.rest.protobuf.generated.CellMessage.Cell;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HBaseUtils {
	private static Logger logger = LoggerFactory.getLogger(HBaseUtils.class);

	private static Configuration conf = null;
	private static HConnection conn = null;

	static {
		// // Properties properties = ConfigUtil.getProps("hbase.properties");
		// conf = HBaseConfiguration.create();
		// // conf.set("hbase.zookeeper.quorum",
		// // properties.getProperty("hbase.zookeeper.quorum"));
		// // conf.setInt("hbase.zookeeper.property.clientPort",
		// //
		// Integer.parseInt(properties.getProperty("hbase.zookeeper.property.clientPort")));
		// // try {
		// // conn = HConnectionManager.createConnection(conf);
		// // } catch (IOException e) {
		// // logger.error(e.getMessage());
		// // }
	}

	public static void createTable(String tableName, String[] families)
			throws MasterNotRunningException, ZooKeeperConnectionException,
			IOException {
		HBaseAdmin admin = new HBaseAdmin(conf);
		if (admin.tableExists(tableName)) {
			logger.info("table" + tableName + "already exists!");
		} else {

			HTableDescriptor descriptor = new HTableDescriptor(tableName);
			for (String family : families) {
				descriptor.addFamily(new HColumnDescriptor(family));
			}
			admin.createTable(descriptor);
			logger.info("create table " + tableName + " success!");
		}
		admin.shutdown();

	}

	public static boolean addSingleColumn(String tableName, String rowKey,
			String family, String qualifier, String value) {
		try {
			HTableInterface table = null;
			try {
				table = new HTable(tableName);
				Put put = new Put(Bytes.toBytes(rowKey));
				put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier),
						Bytes.toBytes(value));
				table.put(put);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean put(String tableName, Put put) {
		try {
			HTableInterface table = null;
			try {
				table = new HTable(tableName);
				table.put(put);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean put(String tableName, List<Put> put) {
		try {
			HTableInterface table = null;
			try {
				table = new HTable(tableName);
				table.put(put);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	public static boolean incrementColumn(String tableName, String rowKey,
			String family, String qualifier, long value) {
		try {
			HTableInterface table = null;
			try {
				table = new HTable(tableName);
				Increment incre = new Increment(Bytes.toBytes(rowKey));
				incre.addColumn(Bytes.toBytes(family),
						Bytes.toBytes(qualifier), value);
				table.increment(incre);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean scanRows(String tableName, String startRow,
			String stopRow) {
		try {
			HTableInterface table = null;
			try {
				table = new HTable(tableName);
				Scan scan = new Scan(Bytes.toBytes(startRow),
						Bytes.toBytes(stopRow));
				ResultScanner scanner = table.getScanner(scan);
				for (Result result : scanner) {
					
					for (KeyValue cell : result.list()) {
						System.out.println(cell.toString());
					}

				}
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean scanByFilter(String tableName, String rowKey,
			String family, String qualifier, String value) {
		try {
			HTableInterface table = null;
			try {
				table = new HTable(tableName);
				Scan scan = new Scan();
				scan.addColumn(family.getBytes(), qualifier.getBytes());
				Filter filter = new SingleColumnValueFilter(
						Bytes.toBytes(family), Bytes.toBytes(qualifier),
						CompareOp.EQUAL, Bytes.toBytes(value));
				scan.setFilter(filter);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteRow(String tableName, String rowKey) {
		try {
			HTableInterface table = null;
			try {
				table = new HTable(tableName);
				Delete delete = new Delete(Bytes.toBytes(rowKey));
				table.delete(delete);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteColumn(String tableName, String rowKey,
			String family, String qualifier) {
		try {
			HTableInterface table = null;
			try {
				table = new HTable(tableName);
				Delete delete = new Delete(Bytes.toBytes(rowKey));
				delete.deleteColumns(Bytes.toBytes(family),
						Bytes.toBytes(qualifier));
				table.delete(delete);
				return true;
			} finally {
				table.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void close() throws IOException {
		conn.clearRegionCache();
	}

	public static void main(String[] args) {
		String tableName = "twits";
		String[] families = { "twits" };
		try {
			HBaseUtils.createTable(tableName, families);
			// System.out.println(HBaseUtils.addData("users", "TheRealMT",
			// "info", "name", "Mark Twain2"));
			// System.out.println(HBaseUtils.appendData("users", "TheRealMT",
			// "info", "name", "append"));
			// System.out.println(HBaseUtils.addData("users", "TheRealMT",
			// "info", "password", "example"));
			// System.out.println(HBaseUtils.getOneRow("users", "TheRealMT",
			// "info"));
			// System.out.println(HBaseUtils.getAllVersion("users", "TheRealMT",
			// "info", "name"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// HBaseUtils.close();
		}

	}
}
