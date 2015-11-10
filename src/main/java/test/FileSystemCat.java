package test;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.io.IOUtils;

public class FileSystemCat {
	public static void main(String[] args) throws Exception {
		String uri = args[0];
		// "hdfs://localhost/user/dkl/input/file01";
		Configuration configuration = new Configuration();
		FileSystem fileSystem = FileSystem.get(URI.create(uri), configuration);
		InputStream in = null;
		try {
			in = new URL(uri).openStream();
			IOUtils.copyBytes(in, System.out, 4096, false);
		} catch (Exception e) {
			IOUtils.closeStream(in);
		}
	}
}
