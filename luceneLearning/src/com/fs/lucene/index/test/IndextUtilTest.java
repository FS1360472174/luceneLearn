package com.fs.lucene.index.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.fs.lucene.index.IndexUtil;

public class IndextUtilTest {

	@Test
	public void testCreateIndex() throws IOException {

		IndexUtil indexUtil = new IndexUtil();
		File file = new File("");
		String base = file.getCanonicalPath();
		//System.out.println(base);
		String indexPath = base + "/src/com/fs/lucene/index/test/index";
		String dataPath = base + "/src/com/fs/lucene/index/test/data";
		indexUtil.createIndex(indexPath, dataPath);
	}

}
