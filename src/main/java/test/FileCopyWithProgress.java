package test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class FileCopyWithProgress {

	public static void main(String[] args) throws Exception {
		// String localSrc = args[0];
		// "/home/dkl/关键词.txt";
		// String dst = args[1];
		// "hdfs://localhost/user/dkl/dkl.txt";
		System.out.println(1 / -0.0);
		System.out.println('b'+1+"c");
		// InputStream in = new BufferedInputStream(new
		// FileInputStream(localSrc));
		// Configuration configuration = new Configuration();
		// FileSystem fileSystem = FileSystem.get(URI.create(dst),
		// configuration);
		// OutputStream outputStream = fileSystem.create(new Path(dst),
		// new Progressable() {
		// public void progress() {
		// System.out.println(".");
		// }
		// });
		// IOUtils.copyBytes(in, outputStream, 4096, false);
	}
}
