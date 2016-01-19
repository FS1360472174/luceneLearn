package com.fs.lucene.index;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexUtil {

	/**
	 * create Index
	 * 
	 * @param indexPath
	 * @param dataPath
	 */
	@SuppressWarnings("deprecation")
	public void createIndex(String indexPath, String dataPath) {

		File dataDir = new File(dataPath);
		File[] dataFiles = dataDir.listFiles();

		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig indexConfig = new IndexWriterConfig(analyzer);

		Directory indexDirectory = getDirectory(indexPath);
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(indexDirectory, indexConfig);
			for (int i = 0; i < dataFiles.length; i++) {
				if (dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt")) {
					Document docu = new Document();
					Reader txtReader = new FileReader(dataFiles[i]);
					// docu.add(new Field("path",
					// dataFiles[i].getCanonicalPath(), new FieldType()));
					docu.add(new Field("content", txtReader));
					indexWriter.addDocument(docu);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			indexWriter.flush();
			indexWriter.commit();
		} catch (IOException e) {
			try {
				indexWriter.rollback();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				indexWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * get Directory
	 * 
	 * @param indexPath
	 * @return
	 */
	public Directory getDirectory(String indexPath) {
		Path path = Paths.get(indexPath);
		Directory directory = null;
		try {
			directory = FSDirectory.open(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return directory;
	}
}
