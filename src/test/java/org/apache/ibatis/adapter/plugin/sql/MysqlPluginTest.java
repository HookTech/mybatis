package org.apache.ibatis.adapter.plugin.sql;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.domain.blog.Author;
import org.apache.ibatis.domain.blog.mappers.AuthorMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionManager;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Reader;

import static org.junit.Assert.assertNotNull;

/**
 * @author philo
 * @create 5/9/18 22:18
 **/
public class MysqlPluginTest extends BaseDataTest {

	private static SqlSessionManager manager;

	@BeforeClass
	public static void setup() throws Exception {
		createBlogDataSource();
		final String resource = "org/apache/ibatis/adapter/plugin/sql/MapperConfig.xml";
		final Reader reader = Resources.getResourceAsReader(resource);
		manager = SqlSessionManager.newInstance(reader);
	}

	@Test
	public void testPlugin() {
		try {
			manager.startManagedSession();
			Author expected = new Author(500, "cbegin", "******", "cbegin@somewhere.com", "Something...", null);
			AuthorMapper mapper = manager.getMapper(AuthorMapper.class);
			mapper.insertAuthor(expected);
			manager.commit();
			Author actual = mapper.selectAuthor(500);
			assertNotNull(actual);
		} catch (Exception e) {
		} finally {
			manager.close();
		}
	}
}
